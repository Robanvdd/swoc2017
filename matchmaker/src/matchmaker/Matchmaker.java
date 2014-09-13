package matchmaker;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.types.ObjectId;

/**
 *
 * @author SvZ
 */
public class Matchmaker implements AutoCloseable {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        String dbName = "test";
        if (args.length > 0)
        {
            dbName = args[0];
        }
        
        try (Matchmaker mm = new Matchmaker(dbName))
        {
            mm.run();
        }
    }
    
    private MongoClient mongoClient;
    private final DB db;

    private final String BOTTABLE = "bots";
    private final String MATCHTABLE = "matches";
    private final String WINNERFIELDNAME = "winnerBot";
    private final String RANKINGFIELDNAME = "ranking";

    public Matchmaker(String dbName)
    {
        db = createDBConnection(dbName);
    }
    
    @Override
    public void close()
    {
        mongoClient.close();
    }
    
    public void run()
    {
        if (db != null)
        {
            makeMatches(getHighestVersionBots(db));
        }
        System.out.println("Matchmaker done");
    }
    
    private DB createDBConnection(String dbName) {
        try {
            mongoClient = new MongoClient( "localhost" );
            DB db = mongoClient.getDB( dbName );
            System.out.println("DB connection made");
            return db;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Matchmaker.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UnknownHostException");
        }
        return null;
    }
    
    private DBCollection getMatchTable(DB database) {
        DBCollection coll = database.getCollection(MATCHTABLE);
        return coll;
    }
    
    private DBCollection getBotTable(DB database) {
        DBCollection coll = database.getCollection(BOTTABLE);
        return coll;
    }
    
    private DBObject getMatchfromTable(DBCollection table, ObjectId matchId) {
        BasicDBObject query = new BasicDBObject("_id", matchId);
        return table.findOne(query);
    }
    
    private ObjectId getWinnerOfMatch(DBCollection coll, ObjectId matchId) {
        DBObject match = getMatchfromTable(coll, matchId);
        if (!match.containsField(WINNERFIELDNAME)) {
            throw new IllegalArgumentException("No winnerBot field in this match entry!");
        }
        return (ObjectId)match.get(WINNERFIELDNAME);
    }
    
    private List<ObjectId> getAllBots(DB database) {
        DBCollection coll = getBotTable(database);
        
        List<ObjectId> botList = new LinkedList<ObjectId>();
        DBCursor cursor = coll.find();
        try {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                botList.add((ObjectId)object.get("_id"));
            }
        } finally {
            cursor.close();
        }
        return botList;
    }
    
    private List<ObjectId> getHighestVersionBots(DB database) {
        DBCollection allBotsColl = getBotTable(database);
        
        List<ObjectId> userList = allBotsColl.distinct("user");
        
        List<ObjectId> botList = new LinkedList<ObjectId>();
        for (ObjectId user: userList) {
            DBCollection coll = getBotTable(database);
            BasicDBObject query = new BasicDBObject("user", user);
            
            DBCursor cursor = coll.find(query);
            cursor.sort(new BasicDBObject("version", -1));
            try {
                if (cursor.hasNext()) {
                    DBObject object = cursor.next();
                    botList.add((ObjectId)object.get("_id"));
                }
            } finally {
                cursor.close();
            }
        }
        return botList;
    }
    
    private ObjectId runMatch(ObjectId botId1, ObjectId botId2) {
        ObjectId matchId = null;
        StringBuilder errorOutputString = new StringBuilder();
        try
        {
            System.out.println("running match " + botId1 + " vs " + botId2);
            Process p = Runtime.getRuntime().exec("java -jar gos-engine.jar " + botId1 + " " + botId2);

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String lastLine = "";
            String line;
            while ((line = input.readLine()) != null)
            {
                errorOutputString.append(line);
                errorOutputString.append('\n');
                lastLine = line;
            }
            p.waitFor();
            input.close();
            
            matchId = new ObjectId(lastLine);
        }
        catch (Exception err) {
            System.err.println("Exception when running match.");
            err.printStackTrace();
            System.err.println("Error output of engine was:");
            System.err.print(errorOutputString.toString());
        }
        return matchId;
    }
    
    private void makeAMatch(ObjectId botId1, ObjectId botId2) {
        DBObject botData1 = getBotData(db, botId1);
        DBObject botData2 = getBotData(db, botId2);
        
        int currentRanking1 = (Integer)botData1.get(RANKINGFIELDNAME);
        int currentRanking2 = (Integer)botData2.get(RANKINGFIELDNAME);
        
        double expectedResult1 = calculateExpectedResult(currentRanking1, currentRanking2);
        double expectedResult2 = 1.0 - expectedResult1;
        
        //Start Engine with two given bots: bot1 as white, 2 as black
        ObjectId matchId1 = runMatch(botId1, botId2);
        //Start Engine with two given bots: bot1 as black, 2 as white
        ObjectId matchId2 = runMatch(botId2, botId1);

        if (matchId1 == null || matchId2 == null)
        {
            System.out.println("The result of the match " + botId1 + " vs " + botId2 + ": no matches stored");
            return;
        }
        
        ObjectId winner1 = getWinnerOfMatch(getMatchTable(db), matchId1);
        ObjectId winner2 = getWinnerOfMatch(getMatchTable(db), matchId2);
        
        double actualScore1 = (winner1.equals(botId1) ? 1.0 : 0.0) + (winner2.equals(botId1) ? 1.0 : 0.0);
        double actualScore2 = (winner1.equals(botId2) ? 1.0 : 0.0) + (winner2.equals(botId2) ? 1.0 : 0.0);
        
        double actualResult1 = actualScore1 / 2.0;
        double actualResult2 = actualScore2 / 2.0;
        
        double newRanking1 = calculateNewRanking(currentRanking1, expectedResult1, actualResult1); 
        double newRanking2 = calculateNewRanking(currentRanking2, expectedResult2, actualResult2); 

        // round rankings to integers
        int newRankingInt1 = (int)Math.round(newRanking1);
        int newRankingInt2 = (int)Math.round(newRanking2);

        updateBotData(db, botId1, newRankingInt1);
        updateBotData(db, botId2, newRankingInt2);
        
        System.out.println("The result of the match " + botId1 + " vs " + botId2 + ": " + actualScore1 + " - " + actualScore2);
    }
    
    /**
     * Let every bot first against each other bot once
     * @param coll List of botIds
     */
    private void makeMatches(List<ObjectId> botIdList) {
        long count = botIdList.size();
        if (count < 2) {
            return; // We do not make a match with less then 2 bots
        }
        for (int i = 0; i < count-1; i++) {
            for (int j = i+1; j < count; j++) {
                makeAMatch(botIdList.get(i), botIdList.get(j));
            }
        }
        System.out.println("Made all matches between "+ count +" bots");
    }
    
    private DBObject getBotData(DB database, ObjectId botId)
    {
        DBCollection coll = getBotTable(database);
        BasicDBObject query = new BasicDBObject("_id", botId);
        return coll.findOne(query);
    }
    
    private void updateBotData(DB database, ObjectId botId, int newRanking)
    {
        DBCollection coll = getBotTable(database);

        BasicDBObject query = new BasicDBObject("_id", botId);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.append("$set", new BasicDBObject(RANKINGFIELDNAME, newRanking));

        coll.update(query, updateObject);
    }
    
    private double calculateExpectedResult(double rankingA, double rankingB)
    {
        double qa = Math.pow(10, rankingA / 400);
        double qb = Math.pow(10, rankingB / 400);
        
        return qa / (qa + qb);
    }
    
    private static final double EloK = 16;
    
    private double calculateNewRanking(double oldRanking, double expectedResult, double actualResult)
    {
        return  oldRanking + EloK * (actualResult - expectedResult);
    }
}

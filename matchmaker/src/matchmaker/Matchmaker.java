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
public class Matchmaker {
    private MongoClient mongoClient;
    private DB db = null;

    private final Map<ObjectId, Integer> scoreMap = new HashMap<ObjectId, Integer>();
    private final String BOTTABLE = "bots";
    private final String MATCHTABLE = "matches";
    private final String WINNERFIELDNAME = "winnerBot";
    private final String RANKINGFIELDNAME = "ranking";

    /**
     * TODO: attach Engine and test it
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String dbName = "test";
        if (args.length > 0)
            dbName = args[0];
        
        Matchmaker mm = new Matchmaker(dbName);
    }
    
    public Matchmaker(String dbName) {
        db = createDBConnection(dbName);
        if (db != null) {
            makeMatches(getAllBots(db));
            updateBotScores(db);
            mongoClient.close();
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
    
    private ObjectId runMatch(ObjectId botId1, ObjectId botId2) {
        ObjectId matchId = null;
        StringBuilder sb = new StringBuilder();
        try {
            System.out.println("running match " + botId1 + " vs " + botId2);
            Process p = Runtime.getRuntime().exec("java -jar gos-engine.jar " + botId1 + " " + botId2);
            p.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String lastLine = "";
            String line;
            while ((line = input.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
                lastLine = line;
            }
            input.close();
            matchId = new ObjectId(lastLine);
        }
        catch (Exception err) {
            System.err.println("Exception when running match.");
            err.printStackTrace();
            System.err.println("Output of engine was:");
            System.err.print(sb.toString());
        }
        return matchId;
    }
    
    private void makeAMatch(ObjectId botId1, ObjectId botId2) {
        //Start Engine with two given bots: bot1 as white, 2 as black
        ObjectId matchId1 = runMatch(botId1, botId2);
        ObjectId winner1 = getWinnerOfMatch(getMatchTable(db), matchId1);
        //Start Engine with two given bots: bot1 as black, 2 as white
        ObjectId matchId2 = runMatch(botId2, botId1);
        ObjectId winner2 = getWinnerOfMatch(getMatchTable(db), matchId2);
        
        //Update score
        if (winner1.equals(winner2)) {
            // One bot won both games
            int oldScore = 0;
            if (scoreMap.containsKey(winner1)) {
                oldScore = scoreMap.get(winner1);
            }
            scoreMap.put(winner1, oldScore + 3);
            System.out.println("The result of the match: " + botId1 + " vs " + botId2 + " : " + winner1 + " WON!");
        } else {
            // Each bot won one each
            int oldScore1 = 0;
            if (scoreMap.containsKey(botId1)) {
                oldScore1 = scoreMap.get(botId1);
            }
            scoreMap.put(botId1, oldScore1 + 1);

            int oldScore2 = 0;
            if (scoreMap.containsKey(botId2)) {
                oldScore2 = scoreMap.get(botId2);
            }
            scoreMap.put(botId2, oldScore2 + 1);
            System.out.println("The result of the match: " + botId1 + " vs " + botId2 + " : DRAW!");
        }
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
    
    private void updateBotData(DB database, ObjectId botId, int score) {
        DBCollection coll = getBotTable(database);

        BasicDBObject query = new BasicDBObject("_id", botId);

        DBObject currentObject = coll.findOne(query);
        if (currentObject == null)
        {
            throw new IllegalArgumentException("Invalid bot id");
        }

        Double currentRanking = (Double)currentObject.get(RANKINGFIELDNAME);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.append("$set", new BasicDBObject(RANKINGFIELDNAME, currentRanking + score));

        coll.update(query, updateObject);
    }
    
    private void updateBotScores(DB db) {
        for (ObjectId key : scoreMap.keySet()) {
            updateBotData(db, key, scoreMap.get(key));
        }
    }
}

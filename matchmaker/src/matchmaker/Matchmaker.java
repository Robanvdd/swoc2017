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

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.bson.types.ObjectId;

/**
 *
 * @author The Robbert-Jan
 */
public class Matchmaker implements AutoCloseable {

    private class Bot {
        Bot(DBObject user,  DBObject micro, DBObject macro) {
			this.user = user;
            this.micro = micro;
            this.macro = macro;
        }
		final DBObject user;
        final DBObject micro;
        final DBObject macro;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        String dbName = "swoc-dev";
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
    private final String USERTABLE = "users";
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

    private DBCollection getUserTable(DB database) {
        DBCollection coll = database.getCollection(USERTABLE);
        return coll;
    }
    
    private DBObject getMatchfromTable(DBCollection table, ObjectId matchId) {
        BasicDBObject query = new BasicDBObject("_id", matchId);
        return table.findOne(query);
    }
    //TODO needs to get the score of the match
    private ObjectId getWinnerOfMatch(DBCollection coll, ObjectId matchId) {
        DBObject match = getMatchfromTable(coll, matchId);
        if (!match.containsField(WINNERFIELDNAME)) {
            throw new IllegalArgumentException("No winnerBot field in this match entry!");
        }
        return (ObjectId)match.get(WINNERFIELDNAME);
    }


    private List<Bot> getHighestVersionBots(DB database) {
        DBCollection allBotsColl = getBotTable(database);
        
        List<ObjectId> userList = allBotsColl.distinct("user");
        
        List<Bot> botList = new LinkedList<Bot>();
        for (ObjectId user: userList) {
            DBCollection userColl = getUserTable(database);
            BasicDBObject userQuery = new BasicDBObject("_id", user);
            DBCursor userCursor = userColl.find(userQuery);

            DBCollection coll = getBotTable(database);
            BasicDBObject macroQuery = new BasicDBObject("user", user).append("kind","macro");
            BasicDBObject microQuery = new BasicDBObject("user", user).append("kind","micro");
            
            DBCursor macroCursor = coll.find(macroQuery);
            macroCursor.sort(new BasicDBObject("version", -1));
            DBCursor microCursor = coll.find(microQuery);
            microCursor.sort(new BasicDBObject("version", -1));
            try {
                if (userCursor.hasNext() && macroCursor.hasNext() && microCursor.hasNext()) {
                    DBObject userObject = userCursor.next();
                    DBObject macroObject = macroCursor.next();
                    DBObject microObject = microCursor.next();
                    Bot bot = new Bot(userObject, microObject, macroObject);
                    botList.add(bot);
                }
            } finally {
                macroCursor.close();
                microCursor.close();
            }
        }
        return botList;
    }
    
    private void runMatch(List<Bot> botList) {
        ObjectId matchId = null;
        StringBuilder sb = new StringBuilder();

        String botString = "";

        for(Bot bot : botList) {
            botString += bot.user.get("username") + " ";
            botString += bot.macro.get("workingDirectory") + " ";
            botString += bot.micro.get("workingDirectory") + " ";
        }

        try {
			//botString should be player_name/id macro_path micro_path
            Process p = Runtime.getRuntime().exec("java -jar micro-macro.jar " + botString);

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                if (line.startsWith("player ")) {
                    String[] playerScore = line.substring("player ".length()).split(" ");
                    if (playerScore.length != 2) throw new InvalidArgumentException(playerScore);
                    String player = playerScore[0];
                    String score = playerScore[1];
                    System.out.println("Parsed score for player: " + player + " with score: " + score);
                }
            }
            p.waitFor();
            input.close();
        }
        catch (Exception err) {
            System.err.println("Exception when running match.");
            err.printStackTrace();
        }
//        return matchId;
    }
    
    private void makeAMatch(List<Bot> botList) {

        String botString = "starting bots in: ";

        for(Bot bot : botList) {
            botString += bot.user.get("username") + " ";
            botString += bot.macro.get("workingDirectory") + " ";
            botString += bot.micro.get("workingDirectory") + " ";
        }

        System.out.println(botString);
        /*ObjectId matchId = */runMatch(botList);
//
//        if (matchId == null)
//        {
//            System.out.println("The match has not finished succesfully");
//            return;
//        }
//
//        System.out.println("The match has finished succesfully");
    }

    private void makeMatches(List<Bot> botList) {
        long count = botList.size();
        if (count < 2) {
            System.out.println("Only "+ count +" bots found, not starting a match");
            return; // We do not make a match with less then 2 bots
        }
        makeAMatch(botList);
        System.out.println("Made the match between "+ count +" bots");
    }
    
    private DBObject getLatestBotData(DB database, ObjectId botId)
    {
        DBCollection bots = getBotTable(database);

        DBObject bot = bots.findOne(new BasicDBObject("_id", botId));
        ObjectId user = (ObjectId)bot.get("user");
        DBObject latestBot = bots.
                find(new BasicDBObject("user", user)).
                sort(new BasicDBObject("version", -1)).
                one();

        return latestBot;
    }
    
    private void updateBotData(DB database, ObjectId botId, int newRanking)
    {
        DBCollection coll = getBotTable(database);

        BasicDBObject query = new BasicDBObject("_id", botId);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.append("$set", new BasicDBObject(RANKINGFIELDNAME, newRanking));

        coll.update(query, updateObject);
    }

    private String getBotName(DBObject bot)
    {
        int version = (Integer)bot.get("version");
        ObjectId userId = (ObjectId)bot.get("user");
        DBCollection users = db.getCollection("users");
        DBObject user = users.findOne(new BasicDBObject("_id", userId));
        String username = (String)user.get("username");
        return username + " v" + version;
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

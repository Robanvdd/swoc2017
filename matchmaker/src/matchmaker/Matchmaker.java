package matchmaker;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.types.ObjectId;
import utils.Zipper;

/**
 *
 * @author The Robbert-Jan
 */
public class Matchmaker implements AutoCloseable {

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

    private static final String BOTTABLE = "bots";
    private static final String MATCHTABLE = "matches";
    private static final String USERTABLE = "users";
    private static final String WINNERFIELDNAME = "winnerBot";
    private static final String RANKINGFIELDNAME = "ranking";

    private Matchmaker(String dbName)
    {
        db = createDBConnection(dbName);
    }
    
    @Override
    public void close()
    {
        mongoClient.close();
    }

    private void run()
    {
        if (db != null)
        {
            makeMatches(getHighestVersionBots());
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
    
    private DBCollection getMatchTable() {
        return db.getCollection(MATCHTABLE);
    }
    
    private DBCollection getBotTable() {
        return db.getCollection(BOTTABLE);
    }

    private DBCollection getUserTable() {
        return db.getCollection(USERTABLE);
    }


    private List<Bot> getHighestVersionBots() {
        DBCollection allBotsColl = getBotTable();
        
        List<ObjectId> userList = allBotsColl.distinct("user");
        
        List<Bot> botList = new LinkedList<>();
        for (ObjectId user: userList) {
            DBCollection userColl = getUserTable();
            BasicDBObject userQuery = new BasicDBObject("_id", user);
            DBCursor userCursor = userColl.find(userQuery);

            DBCollection coll = getBotTable();
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
    
    private GameResult runMatch(List<Bot> botList) {
        GameResult result = null;

        String botString = "";

        for(Bot bot : botList) {
            botString += bot.user.get("username") + " ";
            botString += bot.macro.get("workingDirectory") + " ";
            botString += bot.micro.get("workingDirectory") + " ";
        }

        try {
			//botString should be player_name/id macro_path micro_path
            Process p = Runtime.getRuntime().exec("MacroEngine.exe " + botString);
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String errorLine;
            while ((errorLine = error.readLine()) != null) {
                System.out.println(errorLine);
            }

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("parsing line: " + line);
                String[] words = line.split(" ");
                int wordCount = words.length;
                if (wordCount < 6 || wordCount % 2 != 0 )throw new Exception("Could not parse engine output");
                List<PlayerScore> playerScores = new ArrayList<>();
                System.out.println("parsing gameId: " + words[0]);
                int gameId = Integer.parseInt(words[0]);
                String gameDir = words[1];
                for(int i = 2; i < wordCount;) {
                    String playerName = words[i++];
                    int playerScore = Integer.parseInt(words[i++]);
                    playerScores.add(new PlayerScore(playerName, playerScore));
                }
                result = new GameResult(gameId, gameDir, playerScores);
            }
            p.waitFor();
            input.close();
        }
        catch (Exception err) {
            System.err.println("Exception when running match.");
            err.printStackTrace();
        }
        return result;
    }
    
    private void makeAMatch(List<Bot> botList) {

        String botString = "starting bots in: ";

        for(Bot bot : botList) {
            botString += bot.user.get("username") + " ";
            botString += bot.macro.get("workingDirectory") + " ";
            botString += bot.micro.get("workingDirectory") + " ";
        }

        System.out.println(botString);
        GameResult gameResult = runMatch(botList);

        if (gameResult == null)
        {
            System.out.println("The match has not finished successfully");
            return;
        } else {
            updateBotData(botList, gameResult);
            updateMatchData(botList, gameResult);
        }

        System.out.println("The match has finished succesfully");
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
    
    private DBObject getLatestBotData(ObjectId botId)
    {
        DBCollection bots = getBotTable();

        DBObject bot = bots.findOne(new BasicDBObject("_id", botId));
        ObjectId user = (ObjectId)bot.get("user");
        DBObject latestBot = bots.
                find(new BasicDBObject("user", user)).
                sort(new BasicDBObject("version", -1)).
                one();

        return latestBot;
    }
    
    private void updateBotData(List<Bot> botList, GameResult gameResult)
    {
        DBCollection coll = getBotTable();
        for(Bot bot : botList) {
            Integer newRanking = null;
            for(PlayerScore playerScore : gameResult.playerScores) {
                if (playerScore.name == bot.user.get("username")) {
                    newRanking = playerScore.score;
                }
            }

            BasicDBObject macro_query = new BasicDBObject("_id", bot.macro.get("_id"));
            BasicDBObject micro_query = new BasicDBObject("_id", bot.micro.get("_id"));

            BasicDBObject update_macro = new BasicDBObject();
            BasicDBObject update_micro = new BasicDBObject();
            update_macro.append("$set", new BasicDBObject(RANKINGFIELDNAME, newRanking));
            update_micro.append("$set", new BasicDBObject(RANKINGFIELDNAME, newRanking));
            coll.update(macro_query, update_macro);
            coll.update(micro_query, update_micro);
        }
    }

    private void updateMatchData(List<Bot> botList, GameResult gameResult) {
        Date now = Calendar.getInstance().getTime();
        String winner = getWinner(gameResult);
        String log = gameResult.dir + "/log.zip";
        try {
            Zipper.zip(gameResult.dir, log);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBCollection matchCol = getMatchTable();
        DBObject match = new BasicDBObject("_id", gameResult.id);
        match.put("time", now);
        match.put("winner", winner);
        match.put("log", log);
        matchCol.insert(match);
        //TODO insert entries into match-users table with individual scores
    }

    private String getWinner(GameResult gameResult) {
        int highestScore = 0;
        String winner = "";
        for(PlayerScore playerScore : gameResult.playerScores) {
            if (playerScore.score > highestScore) {
                winner = playerScore.name;
                highestScore = playerScore.score;
            }
        }
        return winner;
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

    private class PlayerScore {
        final String name;
        final int score;
        PlayerScore(String name, int score) {
            this.name = name;
            this.score=score;
        }
    }

    private class GameResult {
        final int id;
        final String dir;
        final List<PlayerScore> playerScores;
        GameResult(int id, String dir, List<PlayerScore> playerScores) {
            this.id = id;
            this.dir = dir;
            this.playerScores = playerScores;
        }
    }

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


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author SvZ
 */
public class Matchmaker {
    Map<Double, Integer> scoreMap = new HashMap<Double, Integer>();
    MongoClient mongoClient;
    private final String BOTTABLE = "Bot";
    private final String MATCHTABLE = "Match";
    private final String WINNERFIELDNAME = "winnerBot";
    private final String RANKINGFIELDNAME = "ranking";
    DB db = null;

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
    
    private DBObject getMatchfromTable(DBCollection table, Double matchId) {
        BasicDBObject query = new BasicDBObject("_id", matchId);
        
        DBCursor cursor = table.find(query);
        DBObject object = null;
        try {
            if (cursor.hasNext()) {
                object = cursor.next();
            }
        } finally {
            cursor.close();
        }
        return object;
    }
    
    private Double getWinnerOfMatch(DBCollection coll, Double matchId) {
        DBObject match = getMatchfromTable(coll, matchId);
        if (!match.containsField(WINNERFIELDNAME)) {
            throw new IllegalArgumentException("No winnerBot field in this match entry!");
        }
        return (Double)match.get(WINNERFIELDNAME);
    }
    
    private DBObject getBotData(DB database, Double botId) {
        DBCollection coll = getBotTable(database);
        
        BasicDBObject query = new BasicDBObject("_id", botId); // Hope this is the way to get the id of a row
        
        DBCursor cursor = coll.find(query);
        DBObject object = null;
        if (coll.count() > 1 || coll.count() < 1) {
            throw new IllegalArgumentException("Given botId does not exist or is duplicate!");
        }
        try {
            if (cursor.hasNext()) {
                object = cursor.next();
            }
        } finally {
            cursor.close();
        }
        return object;
    }
    
    
    private List<Double> getAllBotsOffline() {
        List<Double> botList = new ArrayList<Double>();
        Double[] bots = {1D,2D,3D,4D,5D,6D};
        botList.addAll(Arrays.asList(bots));
        
        return botList;
    }
    
    private List<Double> getAllBots(DB database) {
        DBCollection coll = getBotTable(database);
        
        DBCursor cursor = coll.find();
        DBObject object = null;
        List<Double> botList = new LinkedList<Double>();
        try {
            while (cursor.hasNext()) {
                object = cursor.next();
                botList.add((Double)object.get("_id")); // Hope this is the way to get the id of a row
            }
        } finally {
            cursor.close();
        }
        return botList;
    }
    
    private Double runMatch(Double botId1, Double botId2) {
        String lastLine = "";
        try {
            String line;
            Process p = Runtime.getRuntime().exec("gos-engine " + botId1 + " " + botId2);
            p.waitFor();
            BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                lastLine = line;
            }
            input.close();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        return Double.parseDouble(lastLine);
    }
    
    private void makeAMatch(Double botId1, Double botId2) {
        //Start Engine with two given bots: bot1 as white, 2 as black
        Double matchId1 = runMatch(botId1, botId2);
        Double winner1 = getWinnerOfMatch(getMatchTable(db), matchId1);
        //Start Engine with two given bots: bot1 as black, 2 as white
        Double matchId2 = runMatch(botId2, botId1);
        Double winner2 = getWinnerOfMatch(getMatchTable(db), matchId2);
        
        //Update score
        if (winner1.equals(winner2)) {
            // One bot won both games
            if (scoreMap.containsKey(winner1)) {
                scoreMap.put(winner1, scoreMap.get(winner1) + 3);
                System.out.println("The result of the match: " + botId1 + " vs " + botId2 + " : " + winner1 + " won!");
            } else {
                scoreMap.put(winner1, 3);
            }
            System.out.println("The result of the match: " + botId1 + " vs " + botId2 + " : " + winner1 + " WON!");
        } else {
            // Each bot won one each
            if (scoreMap.containsKey(botId1)) {
                scoreMap.put(botId1, scoreMap.get(botId1) + 1);
            } else {
                scoreMap.put(botId1, 1);
            }
            if (scoreMap.containsKey(botId2)) {
                scoreMap.put(botId2, scoreMap.get(botId2) + 1);
            } else {
                scoreMap.put(botId2, 1);
            }
            System.out.println("The result of the match: " + botId1 + " vs " + botId2 + " : DRAW!");
        }
    }
    
    /**
     * Let every bot first against each other bot once
     * @param coll List of botIds
     */
    private void makeMatches(List<Double> botIdList) {
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
    
    private boolean updateBotData(DB database, Double botId, int score) {
        DBCollection coll = getBotTable(database);
        
        BasicDBObject query = new BasicDBObject("_id", botId); // Hope this is the way to get the id of a row
        
        DBCursor cursor = coll.find(query);
        DBObject object = null;
        if (coll.count() > 1 || coll.count() < 1) {
            return false;
        }
        try {
            if (cursor.hasNext()) {
                object = cursor.next();
            }
        } finally {
            cursor.close();
        }
        
        object.put(RANKINGFIELDNAME, score);
        coll.update(query, object);
        return true;
    }
    
    private void updateBotScores(DB db) {
        for (Double key : scoreMap.keySet()) {
            updateBotData(db, key, scoreMap.get(key));
        }
    }
    
    
}

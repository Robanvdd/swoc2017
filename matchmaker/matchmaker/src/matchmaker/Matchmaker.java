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
    Map<String, Integer> scoreMap = new HashMap<String, Integer>();
    MongoClient mongoClient;

    /**
     * TODO: Test with database, attach Engine and test it
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String dbName = "mean-dev";
        if (args.length > 0)
            dbName = args[0];
        
        Matchmaker mm = new Matchmaker(dbName);
    }
    
    public Matchmaker(String dbName) {
        DB db = createDBConnection(dbName);
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
            
            return db;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Matchmaker.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UnknownHostException");
        }
        return null;
    }
    /*
    private DBCollection getMatchTable(DB database) {
        DBCollection coll = database.getCollection("matches");
        return coll;
    }
    
    private DBCollection getBotTable(DB database) {
        DBCollection coll = database.getCollection("bot");
        return coll;
    }
    
    private DBObject getMatchfromTable(DBCollection table, String matchId) {
        BasicDBObject query = new BasicDBObject("matchId", matchId);
        
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
    
    private DBObject getBotData(DB database, String botId) {
        DBCollection coll = database.getCollection("bot");
        
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
    */
    
    private List<String> getAllBotsOffline() {
        List<String> botList = new ArrayList<String>();
        String[] bots = {"bot1","bot2","bot3","bot4","bot5","bot6"};
        botList.addAll(Arrays.asList(bots));
        
        return botList;
    }
    
    private List<String> getAllBots(DB database) {
        DBCollection coll = database.getCollection("bots");
        
        DBCursor cursor = coll.find();
        DBObject object = null;
        List<String> botList = new LinkedList<String>();
        try {
            while (cursor.hasNext()) {
                object = cursor.next();
                botList.add((String)object.get("_id")); // Hope this is the way to get the id of a row
            }
        } finally {
            cursor.close();
        }
        return botList;
    }
    
    private String runMatch(String botId1, String botId2) {
        String lastLine = "";
        try {
            String line;
            Process p = Runtime.getRuntime().exec("Engine " + botId1 + " " + botId2);
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
        return lastLine;
    }
    
    private void makeAMatch(String botId1, String botId2) {
        //Start Engine with two given bots: bot1 as white, 2 as black
        String winner1 = runMatch(botId1, botId2);
        //Start Engine with two given bots: bot1 as black, 2 as white
        String winner2 = runMatch(botId2, botId1);;
        
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
    private void makeMatches(List<String> botIdList) {
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
    
    private boolean updateBotData(DB database, String botId, int score) {
        DBCollection coll = database.getCollection("bot");
        
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
        
        object.put("ranking", score);
        coll.update(query, object);
        return true;
    }
    
    private void updateBotScores(DB db) {
        for (String key : scoreMap.keySet()) {
            updateBotData(db, key, scoreMap.get(key));
        }
    }
    
    
}

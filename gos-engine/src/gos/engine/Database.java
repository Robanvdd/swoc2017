package gos.engine;


import gos.engine.protocol.Player;

import java.net.UnknownHostException;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Database implements IDatabase
{
    private final MongoClient client;
    private final DB db;

    private static final String MatchTableName = "matches";
    private static final String BotTableName = "bots";
    private static final String RunCommandFieldName = "runCommand";
    private static final String WorkingDirectoryPathFieldName = "workingDirectory";
    
    public Database(String dbHost, String dbName) throws UnknownHostException
    {
        client = new MongoClient(dbHost);
        db = client.getDB(dbName);
    }

    public String GetRunCommand(String botId)
    {
        DBObject object = FindBotById(botId);
        return (object != null) ? (String) object.get(RunCommandFieldName) : null;
    }

    public String GetWorkingDirectory(String botId)
    {
        DBObject object = FindBotById(botId);
        return (object != null) ? (String) object.get(WorkingDirectoryPathFieldName) : null;
    }
    
    private DBObject FindBotById(String id)
    {
        DBCollection table = db.getCollection(BotTableName);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        DBCursor cursor = table.find(query);
        return cursor.one();
    }
    
    public String StoreMatch(String log, Bot white, Bot black, Player winner, Date startedOn, Date completedOn)
    {
        DBCollection matches = db.getCollection(MatchTableName);
        
        String winnerId;
        if (white.Player == winner)
        {
            winnerId = white.Id;
        }
        else if (black.Player == winner)
        {
            winnerId = black.Id;
        }
        else
        {
            throw new IllegalArgumentException("Winner is not white or black");
        }
        
        BasicDBObject match = new BasicDBObject();
        match.put("whiteBot", new ObjectId(white.Id));
        match.put("blackBot", new ObjectId(black.Id));
        match.put("winnerBot", new ObjectId(winnerId));
        match.put("startedOn", startedOn);
        match.put("completedOn", completedOn);
        match.put("whiteStdin", white.GetInputLog());
        match.put("whiteStdOut", white.GetOutputLog());
        match.put("whiteStdErr", white.getErrors());
        match.put("blackStdin", black.GetInputLog());
        match.put("blackStdOut", black.GetOutputLog());
        match.put("blackStdErr", black.getErrors());
        match.put("moves", log);
        
        matches.insert(match);
        
        ObjectId matchId = (ObjectId)match.get("_id");
        return matchId.toString();
    }
}

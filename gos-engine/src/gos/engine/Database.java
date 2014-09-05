package gos.engine;


import java.net.UnknownHostException;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Database
{
    private final MongoClient client;
    private final DB db;

    private static final String MatchTableName = "Match";
    private static final String BotTableName = "Bot";
    private static final String ExecutablePathFieldName = "executablePath";

    public Database(String dbHost, String dbName) throws UnknownHostException
    {
        client = new MongoClient(dbHost);
        db = client.getDB(dbName);
    }

    public String GetBotExecutable(String botId)
    {
        DBObject object = FindBotById(botId);
        if (object == null)
        {
            return null;
        }
        
        return (String)object.get(ExecutablePathFieldName);
    }

    private DBObject FindBotById(String id)
    {
        DBCollection table = db.getCollection(BotTableName);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        DBCursor cursor = table.find(query);
        return cursor.one();
    }
    
    public String StoreMatch(String log, Bot white, Bot black, int winner, Date startedOn, Date completedOn)
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
        match.put("boardState", log);
        
        matches.insert(match);
        
        ObjectId matchId = (ObjectId)match.get("_id");
        return matchId.toString();
    }
}

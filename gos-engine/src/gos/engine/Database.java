package gos.engine;

import java.net.UnknownHostException;

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

    //private static final String MatchTableName = "Match";
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
}

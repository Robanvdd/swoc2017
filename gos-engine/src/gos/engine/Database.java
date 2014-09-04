package gos.engine;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

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

    public Database(String dbHost, String dbName) throws UnknownHostException
    {
        client = new MongoClient(dbHost);
        db = client.getDB(dbName);
    }

    private DBCollection GetMatchTable()
    {
        return db.getCollection(MatchTableName);
    }

    private DBCollection GetBotTable()
    {
        return db.getCollection(BotTableName);
    }

    public DBObject GetBotExecutable(int botId)
    {
        DBCollection coll = GetBotTable();

        BasicDBObject query = new BasicDBObject("_id", botId);

        DBCursor cursor = coll.find(query);
        DBObject object = null;
        if (coll.count() > 1 || coll.count() < 1)
        {
            throw new IllegalArgumentException("Given botId does not exist or is duplicate!");
        }
        try
        {
            if (cursor.hasNext())
            {
                object = cursor.next();
            }
        }
        finally
        {
            cursor.close();
        }
        return object;
    }

    public List<Double> GetAllBots()
    {
        DBCollection coll = GetBotTable();

        DBObject object = null;
        List<Double> botList = new LinkedList<Double>();
        try (DBCursor cursor = coll.find())
        {
            while (cursor.hasNext())
            {
                object = cursor.next();
                botList.add((Double)object.get("_id")); // Hope this is the way to get the id of a row
            }
        }
        return botList;
    }

}

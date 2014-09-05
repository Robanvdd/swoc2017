package gos.engine;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Main
{
    private static String executableWhite = null;
    private static String executableBlack = null;

    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.err.println("no bots given");
            //executableWhite = "C:\\Users\\Ralph\\Documents\\Sioux\\swoc\\swoc2014\\SharpBot\\Bin\\SharpBot.exe";
            //executableBlack = "C:\\Users\\Ralph\\Documents\\Sioux\\swoc\\swoc2014\\SharpBot\\Bin\\SharpBot.exe";
            return;
        }
        else
        {
            String botIdWhite = args[0];
            String botIdBlack = args[1];

            GetBotsFromDatabase(botIdWhite, botIdBlack);
            
            if (executableWhite == null || executableBlack == null)
            {
                return;
            }
        }

        try (EngineRunner runner = new EngineRunner(executableWhite, executableBlack))
        {
            runner.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void GetBotsFromDatabase(String botIdWhite, String botIdBlack)
    {
        try
        {
            MongoClient client = new MongoClient("localhost");

            DB db = client.getDB("swoc-dev");

            DBObject botWhite = FindBotById(db, botIdWhite);
            if (botWhite == null)
            {
                System.err.println("White bot could not be found");
                return;
            }
            DBObject botBlack = FindBotById(db, botIdBlack);
            if (botBlack == null)
            {
                System.err.println("Black bot could not be found");
                return;
            }

            client.close();

            executableWhite = (String)botWhite.get("executablePath");
            executableBlack = (String)botBlack.get("executablePath");
        }
        catch (UnknownHostException ex)
        {
            ex.printStackTrace();
        }
    }

    private static DBObject FindBotByName(DB db, String name)
    {
        DBCollection table = db.getCollection("Bot");
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);

        DBCursor cursor = table.find(query);
        return cursor.one();
    }

    private static DBObject FindBotById(DB db, String id)
    {
        DBCollection table = db.getCollection("Bot");
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        DBCursor cursor = table.find(query);
        return cursor.one();
    }

    private static void MakeABot(DB db, String botName)
    {
        DBCollection table = db.getCollection("Bot");
        BasicDBObject document = new BasicDBObject();
        document.put("name", botName);
        document.put("ranking", 0);
        document.put("version", 1);
        document.put("executablePath",
                "/home/swocslave/test/swoc2014/SharpBot/Bin/SharpBot.exe");
        table.insert(document);
    }

    private static void DumpAllDatabases(MongoClient client)
    {
        List<String> dbNames = client.getDatabaseNames();
        System.out.println(dbNames.size() + " databases");
        for (String dbName : dbNames)
        {
            System.out.println(dbName);

            DB db = client.getDB(dbName);
            DumpAllTables(db);
        }
    }

    private static void DumpAllTables(DB db)
    {
        Set<String> tables = db.getCollectionNames();
        System.out.println("  " + tables.size() + " tables");

        for (String coll : tables)
        {
            System.out.println("    " + coll);
        }
    }

}

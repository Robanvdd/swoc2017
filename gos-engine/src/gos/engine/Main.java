package gos.engine;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            MongoClient client = new MongoClient("localhost");

            DB db = client.getDB("swoc-dev");
            
            DumpAllTables(db);
            
//            MakeABot(db, "Bot One");
//            MakeABot(db, "Bot Two");
            
            client.close();
        } catch (UnknownHostException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private static void MakeABot(DB db, String botName)
    {
        DBCollection table = db.getCollection("Bot");
        BasicDBObject document = new BasicDBObject();
        document.put("name", botName);
        document.put("ranking", 0);
        document.put("version", 1);
        document.put("executablePath", "/home/swocslave/test/swoc2014/SharpBot/Bin/SharpBot.exe");
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

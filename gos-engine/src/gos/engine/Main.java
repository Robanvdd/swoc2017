package gos.engine;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            MongoClient client = new MongoClient("localhost");

            List<String> dbNames = client.getDatabaseNames();
            System.out.println(dbNames.size() + " databases");
            for (String dbName : dbNames)
            {
                System.out.println(dbName);

                DB db = client.getDB(dbName);
                Set<String> tables = db.getCollectionNames();
                System.out.println("  " + tables.size() + " tables");

                for (String coll : tables)
                {
                    System.out.println("    " + coll);
                }
            }
            client.close();
        } catch (UnknownHostException ex)
        {
            ex.printStackTrace();
        }
    }

}

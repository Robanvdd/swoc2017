package gos.engine;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            MongoClient client = new MongoClient("localhost");

            List<String> dbs = client.getDatabaseNames();
            System.out.println(dbs.size() + " databases");
            for(String db : dbs)
            {
                System.out.println(db);
            }            
            client.close();
        } 
        catch (UnknownHostException ex)
        {
            ex.printStackTrace();
        }
    }

}

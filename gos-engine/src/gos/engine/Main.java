package gos.engine;

import java.net.UnknownHostException;

public class Main
{
    private static IDatabase database;

    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.err.println("no bots given");
            return;
        }

        String botIdWhite = args[0];
        String botIdBlack = args[1];

        //database = new DummyDatabase();
        try
        {
            database = new Database("localhost", "swoc-dev");
        }
        catch (UnknownHostException ex)
        {
            System.err.println("Could not connect to database");
            ex.printStackTrace();
            return;
        }

        try (EngineRunner runner = new EngineRunner(database, botIdWhite, botIdBlack))
        {
            runner.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

package gos.engine;

import java.net.UnknownHostException;

public class Main
{
    private static Database database;

    public static void main(String[] args)
    {
        String executableWhite;
        String executableBlack;

        if (args.length != 2)
        {
            System.err.println("no bots given");
            return;
        }

        String botIdWhite = args[0];
        String botIdBlack = args[1];

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

        executableWhite = database.GetBotExecutable(botIdWhite);
        if (executableWhite == null)
        {
            System.err.println("White bot could not be found");
            return;
        }

        executableBlack = database.GetBotExecutable(botIdBlack);
        if (executableBlack == null)
        {
            System.err.println("Black bot could not be found");
            return;
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

}

package gos.engine;

import java.net.UnknownHostException;

public class Main
{
    private static String executableWhite = null;
    private static String executableBlack = null;
    
    private static Database database;

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

            try
            {
                database = new Database("locahost", "swoc-dev");
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

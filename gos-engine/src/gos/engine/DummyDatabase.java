package gos.engine;

import gos.engine.protocol.Player;

import java.util.Date;

public class DummyDatabase implements IDatabase
{
    public String GetRunCommand(String botId)
    {
        return botId.equals("0") ? "java -jar java-bot.jar" : "java -jar java-bot-broken.jar";
    }

    public String GetWorkingDirectory(String botId)
    {
        return "C:\\Users\\Ralph\\Documents\\Sioux\\swoc\\binaries";
    }

    public String StoreMatch(String log, Bot white, Bot black, Player winner, Date startedOn, Date completedOn)
    {
        return "matchid42";
    }

}

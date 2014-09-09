package gos.engine;

import gos.engine.protocol.Player;

import java.util.Date;

public class DummyDatabase implements IDatabase
{
    public String GetBotExecutable(String botId)
    {
        return botId.equals("0") ?
                "C:\\Users\\Ralph\\Documents\\Sioux\\swoc\\swoc2014\\java-bot\\build\\jar\\run.cmd" :
                    "C:\\Users\\Ralph\\Documents\\Sioux\\swoc\\swoc2014\\SharpBot\\SharpBot\\bin\\Release\\SharpBot.exe";
            
    }

    public String StoreMatch(String log, Bot white, Bot black, Player winner, Date startedOn, Date completedOn)
    {
        return "matchid42";
    }

}

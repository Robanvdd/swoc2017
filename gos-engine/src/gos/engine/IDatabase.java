package gos.engine;

import gos.engine.protocol.Player;

import java.util.Date;

public interface IDatabase
{
    public String GetBotExecutable(String botId);
    public String StoreMatch(String log, Bot white, Bot black, Player winner, Date startedOn, Date completedOn);
}

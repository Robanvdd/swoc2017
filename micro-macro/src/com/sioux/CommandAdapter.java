package com.sioux;

public class CommandAdapter {

    CommandAdapter(String playerName, CommandAdapterType t){
        this.playerID = playerName;
        this.type = t;
    }


    public CommandAdapterType type;
    public String playerID;
    public int ufoId;
    public String solarSystemName;
    public String planetName;

}

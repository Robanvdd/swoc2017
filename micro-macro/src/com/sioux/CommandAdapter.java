package com.sioux;

import com.sioux.game_objects.Game;

public class CommandAdapter {

    CommandAdapter(String playerName, CommandAdapterType t, Game game){
        this.playerID = playerName;
        this.type = t;
        this.game = game;
    }


    public CommandAdapterType type;
    public String playerID;
    public int ufoId;
    public String solarSystemName;
    public String planetName;
    public Game game;


}

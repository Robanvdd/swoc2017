package com.sioux;

public class CommandAdapter {

    CommandAdapter(String playerName, CommandAdapterType t){
        this.fromPlayer = playerName;
        this.type = t;
    }

    String fromPlayer;
    CommandAdapterType type;



}

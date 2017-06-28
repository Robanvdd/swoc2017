package com.sioux.game_scheduler;

import java.util.ArrayList;

/**
 * Created by Michael on 21/06/2017.
 */
public class ClientMoveCommand implements IClientCommand {

    int clientID;
    private String destination;
    private ArrayList ufoIds;

    ClientMoveCommand(int clientID, String destination, ArrayList ufoIds){
        this.clientID = clientID;
        this.destination = destination;
        this.ufoIds = ufoIds;
    }

    @Override
    public String GetCommandString() {
        return "[move]" + " " + ufoIds.toString() + " " + destination;
    }

    @Override
    public int getClientID() {
        return clientID;
    }
}

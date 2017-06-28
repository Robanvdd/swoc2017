package com.sioux.game_scheduler;

/**
 * Created by Michael on 25/06/2017.
 */
public class ClientConquerCommand implements IClientCommand {

    int clientID;
    private String objectToConquer;

    public ClientConquerCommand(int clientID, String objectToConquer) {

        this.clientID = clientID;
        this.objectToConquer = objectToConquer;
    }

    @Override
    public String GetCommandString() {
        return "[conquer]" + " " + objectToConquer;
    }

    @Override
    public int getClientID() {
        return clientID;
    }

    public String getObjectToConquer() {
        return objectToConquer;
    }
}

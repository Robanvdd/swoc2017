package com.sioux.game_scheduler;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;

/**
 * Created by Michael on 21/06/2017.
 */
public class Client {
    int clientID;
    IOScocket socketToClient; // raw communication
    //IClientCommand command; // usable command extracted from raw communication

    private LinkedList<IClientCommand> inputCommands;
    private LinkedList<IClientCommand> outputCommands;

    Client(int clientID){
        this.clientID = clientID;
        this.inputCommands = new LinkedList<IClientCommand>();
        this.outputCommands= new LinkedList<IClientCommand>();
    }

    public void StartUpClient(String pathToScript) {
        socketToClient = new IOScocket(pathToScript);
        socketToClient.Start();
        WaitForCommand();
    }

    private void WaitForCommand() {

        IClientCommand parsedCommandFromIO;
        while(true)
        {
            //if command received
                // parsedCommandFromIO.
                //addCommandToInputQueue

            //If outputCommand != empty
                //send outputCommand via IO; (socket)
        }

    }

    public IClientCommand GetNextCommand() {
        if(!inputCommands.isEmpty()){
            return inputCommands.removeFirst();
        }
        return null;
    }

    public void SendCommand(IClientCommand command){
        if (!outputCommands.isEmpty()) {
            outputCommands.add(command);
        }
        else {
            throw new NotImplementedException();
        }
    }

    private void IClientCommandToJson()
    {

    }

    private void SendCommandToSocket()
    {
        if(!outputCommands.isEmpty()){
            socketToClient.SendString(outputCommands.removeFirst().GetCommandString());
        }
        else{
            throw new NotImplementedException();
        }
    }

    private void addCommandToInputQueue(IClientCommand command)
    {
        if (!inputCommands.isEmpty()) {
            inputCommands.add(command);
        }
        else {
            throw new NotImplementedException();
        }
    }


}

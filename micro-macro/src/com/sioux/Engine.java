package com.sioux;

import java.util.LinkedList;

public class Engine implements Runnable {
    private LinkedList<ICommand> executeQueue;
    private BotShepherdThread botShepherd;
    private static final int FPS = 60;
    private static final long MAX_LOOP_TIME = (1000 / FPS);

    public Engine(BotShepherdThread botShepherd){
        this.executeQueue = new LinkedList<ICommand>();
        this.botShepherd = botShepherd;
    }

    private void ExecuteCommands(){
        for (ICommand command: executeQueue){
            command.Execute();
        }
        executeQueue.clear();
    }

    private void StoreCommand(ICommand command){
        if(command != null) {
            executeQueue.addLast(command);
        } else {
            System.out.print("[Engine]::Received empty command.\n");
        }
    }

    private void CommandsToExecuteQueue(){
        LinkedList<ICommand> tempCommandQueue = botShepherd.getReceiveQueue();
        for (ICommand command: tempCommandQueue) {
            StoreCommand(command);
        }
    }

    private void UpdateGame() {
        CommandsToExecuteQueue();
        ExecuteCommands();
    }

    private void GameLoop(){
        long previousTimestamp = System.currentTimeMillis();
        long currentTimestamp;
        while(!Thread.currentThread().isInterrupted())
        {
            currentTimestamp = System.currentTimeMillis();
            if((currentTimestamp - previousTimestamp) >= MAX_LOOP_TIME){
                UpdateGame();
                previousTimestamp = currentTimestamp;
            }
        }
    }

    @Override
    public void run() {
        GameLoop();
    }
}

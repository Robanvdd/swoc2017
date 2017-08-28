package com.sioux;

import com.google.gson.Gson;
import com.sioux.game_objects.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Engine implements Runnable {
    private LinkedList<ICommand> executeQueue;
    private BotShepherdThread botShepherd;
    private Gson gson;
    private static final int FPS = 60;
    private static final long MAX_LOOP_TIME = (1000 / FPS);

    public Engine(BotShepherdThread botShepherd){
        this.executeQueue = new LinkedList<ICommand>();
        this.botShepherd = botShepherd;
        this.gson = new Gson();
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

    private void SaveGameState(long timeStamp) {

        final String path = "tick_" + timeStamp + ".json";
        Game game = botShepherd.GetGameState();
        final String data = gson.toJson(game, Game.class);

        try {
            File file = new File(path);
//            file.getParentFile().mkdir();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void GameLoop(){
        long previousTimestamp = System.currentTimeMillis();
        long currentTimestamp;
        while(!Thread.currentThread().isInterrupted())
        {
            currentTimestamp = System.currentTimeMillis();
            if((currentTimestamp - previousTimestamp) >= MAX_LOOP_TIME){
                UpdateGame();
                SaveGameState(currentTimestamp);
                previousTimestamp = currentTimestamp;
            }
        }
    }

    @Override
    public void run() {
        GameLoop();
    }
}

package com.sioux;

import com.google.gson.Gson;
import com.sioux.game_objects.*;
import com.sioux.game_objects.Player;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BotShepherdThread implements Runnable {
    private LinkedList<ICommand> receiveQueue;
    private Semaphore mutex;
    private Map<String, BotProcess> bots;
    private List<com.sioux.game_objects.Player> players;
    private Gson gson;
    private String[] botFiles;


    public BotShepherdThread(String[] botFiles){
        this.receiveQueue = new LinkedList<ICommand>();
        this.mutex = new Semaphore(1);
        this.bots = new HashMap<String, BotProcess>();
        this.gson = new Gson();
        this.players = new ArrayList<Player>();
        this.botFiles = botFiles;
    }

    private void  StartBots(){
        int nameCount = 0;

        try {
            for (File file: GetAllScripts()) {
                nameCount++;
                Player p = new Player("player"+nameCount,10,null,1);
                players.add(p);
                bots.put(p.getName(),new BotProcess("/Users/Michael/Documents/testdir", "python " + file.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void TransformToCommand(CommandAdapter command){
        switch (command.type) {
            case MOVE: AddCommandsToReceiveQueue(new MoveCommand(MacroGameLogic.getInstance()));
        }
    }

    private void WaitForCommands(){
        for (Player player: players){
            String input;
            input = bots.get(player.getName()).readLine(1000);
            if(input != null) {
                System.out.print(input);
            }
            //CommandAdapter inputCommand = gson.fromJson(input,CommandAdapter.class);
            //TransformToCommand(inputCommand);
        }
    }

    private void SendMessage(){
        for (Player player: players){
            String output;
            bots.get(player.getName()).writeLine("hello");
        }
    }

    private void CommunicationLoop(){
        int counterForTesting = 0;

        while(!Thread.currentThread().isInterrupted()){
            //Add commands for testing. The commands need to come from BOTS;
            SendMessage();
            WaitForCommands();
            if(10 >= counterForTesting) {
                AddCommandsToReceiveQueue(new MoveCommand(MacroGameLogic.getInstance()));
                counterForTesting++;
            }
        }
    }

    private void AddCommandsToReceiveQueue(ICommand command){
        try{
            mutex.acquire();
                receiveQueue.addLast(command);
            mutex.release();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<File> GetAllScripts(){

        return Stream.of(botFiles).map(File::new).collect(Collectors.toList());
    }

    public LinkedList<ICommand> getReceiveQueue() {
        LinkedList<ICommand> commandsToReturn = new LinkedList<ICommand>();
        try{
            mutex.acquire();
                for (ICommand command:receiveQueue) {
                    commandsToReturn.addLast(command);
                }
                receiveQueue.clear();
            mutex.release();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return commandsToReturn;
    }

    @Override
    public void run() {
        StartBots();
        CommunicationLoop();
    }
}


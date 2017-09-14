package com.sioux;

import com.google.gson.Gson;
import com.sioux.game_objects.*;
import com.sioux.game_objects.Player;
import com.sun.javaws.exceptions.InvalidArgumentException;
import sun.awt.OSInfo;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;

public class BotShepherdThread implements Runnable {
    private LinkedList<ICommand> receiveQueue;
    private Semaphore mutex;
    private Map<String, BotProcess> bots;
    private Gson gson;
    private String[] botArgs;
    private static final int FPS = 60;
    private static final long MAX_LOOP_TIME = (2000 / FPS);

    public BotShepherdThread(String[] botArgs){
        this.receiveQueue = new LinkedList<ICommand>();
        this.mutex = new Semaphore(1);
        this.bots = new HashMap<String, BotProcess>();
        this.gson = new Gson();
        this.botArgs = botArgs;
    }

    private void SetupBots() {
        if(botArgs.length % 3 != 0) System.out.println("Invalid argument count, needs to be divisable by 3");
        try {
            Iterator<String> botIterator = Arrays.stream(botArgs).iterator();
            int id = 0;
            while(botIterator.hasNext()) {
                String name = botIterator.next();
                String macroPath = botIterator.next();
                String microPath = botIterator.next();
                Player p = new Player(name,10,null,id++);
                SetUpPlayer(p);
                if (OSInfo.getOSType() == OSInfo.OSType.WINDOWS) {
                    bots.put(name, new BotProcess(macroPath, "run.bat"));
                } else {
                    bots.put(name, new BotProcess(macroPath, "run.sh"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SetUpPlayer(Player p){
        MacroGameLogic.getInstance().AddPlayerToGame(p);
    }

    private void TransformToCommand(CommandAdapter command){
        switch (command.type) {
            case MOVE: AddCommandsToReceiveQueue(new MoveCommand(MacroGameLogic.getInstance(), command.playerID,command.ufoId,command.solarSystemName,command.planetName));
        }
    }

    public Game GetGameState(){
        try{
            Game _game;
            mutex.acquire();
                _game = MacroGameLogic.getInstance().GetGameState();
            mutex.release();
            return _game;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.print("[BotShepherd]:: GameState is null.");
        return null;
    }

    private void WaitForCommands(){
        try{
            mutex.acquire();
            ArrayList<Player> players = MacroGameLogic.getInstance().getPlayers();
            mutex.release();

            for (Player player: players){
                String input;
                input = bots.get(player.getName()).readLine(1000);
                if(input != null) {
                    System.out.print(input);
                }
                CommandAdapter inputCommand = gson.fromJson(input,CommandAdapter.class);
                TransformToCommand(inputCommand);
            }
        } catch (Exception e){ e.printStackTrace(); }
    }

    private void SendMessage(Player p, CommandAdapter commandAdapter){
        if (commandAdapter != null) {
            bots.get(p.getName()).writeLine(gson.toJson(commandAdapter));
        }
    }

    private void CommunicationLoop(){
        int counterForTesting = 0;

        long previousTimestamp = System.currentTimeMillis();
        long currentTimestamp;
        while(!Thread.currentThread().isInterrupted())
        {
            currentTimestamp = System.currentTimeMillis();
            if((currentTimestamp - previousTimestamp) >= MAX_LOOP_TIME){
                AddCommandsToReceiveQueue(new PlanetRotateCommand(MacroGameLogic.getInstance()));
                previousTimestamp = currentTimestamp;
            }

            for (Player p : MacroGameLogic.getInstance().getPlayers()){
                SendMessage(p,new CommandAdapter(p.getName(),CommandAdapterType.MOVE));
            }
            WaitForCommands();

            if(10 >= counterForTesting) {
                AddCommandsToReceiveQueue(new MoveCommand(MacroGameLogic.getInstance(),"",10,"",""));
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
        SetupBots();
        MacroGameLogic.getInstance().InitGameState();
        CommunicationLoop();
    }
}


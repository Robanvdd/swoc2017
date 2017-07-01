package com.sioux;

import com.google.gson.Gson;
import com.sioux.game_objects.Command;
import com.sioux.game_objects.CommandType;
import com.sioux.game_objects.Game;
import com.sioux.game_objects.InitialState;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Michael on 01/07/2017.
 */
public class MacroEngine {
    Game game;
    Gson gson;
    List<Bot> runningBots;
    Scripts s;
    CommandQueue incommingCommands;
    Boolean running = true;
    int fileCount = 0;//TODO Bad Hack

    MacroEngine(){
        gson = new Gson();
        s = new Scripts("/Users/Michael/Documents/testdir");
    }

    public void InitAndStart(){
        InitialState initGameState = new InitialState();
        game = new Game(1,"gameOne",1,initGameState.solarSystem,initGameState.player,initGameState.player,null); // one player for now.
        incommingCommands = new CommandQueue();
        try {
            SetUpRunningBots();
            Run(initGameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SaveGameState() {
        try {
            String messageStr = gson.toJson(game);
            fileCount ++;//TODO: Bad Hack
            File file = new File("//Users//Michael//Documents//testDirSavesFiles//file"+fileCount+".json");

            // creates the file
            file.createNewFile();

            // creates a FileWriter Object
            FileWriter writer = new FileWriter(file);

            // Writes the content to the file
            writer.write(messageStr);
            writer.flush();
            writer.close();

        } catch (IOException io){
            System.err.println("Caught IOException: " + io.getMessage());
        }
    }

    private void SetUpRunningBots() throws IOException {
        runningBots = s.StartAllBots();
    }

    private void CloseAllRunningBots(){
        for (Bot botToClose: runningBots) {
            botToClose.close();
        }
    }

    public void SendInitialGameState(InitialState initialState){

        for (Bot runningBot:runningBots) {
            runningBot.writeMessage(initialState);
        }
    }

    private <T> void ReceiveMessage() {
        for (Bot runningBot:runningBots) {
            incommingCommands.AddCommandToQueue(runningBot.writeAndReadMessage(new Command(CommandType.NEW_COMMAND,9999,"NoWhere"),Command.class));
        }
    }


    public void ExecuteCommands(){
        Command c = incommingCommands.GetCommandOutOffQueue();
        switch (c.Type) {
            case MOVE:
                System.out.print("Going to Move");
                break;
            case CONQUER:
                MicroBattle m = new MicroBattle(game); //For now just change an element
                game.setName(m.GetMicroBattleGameName());
                System.out.print("Going to Conquer");
                running = false;
                break;
        }
    }

    public void Run(InitialState initGameState){
        SendInitialGameState(initGameState);
        while(running){
            ReceiveMessage();
            ExecuteCommands();
            SaveGameState();//does not file data
        }
    }



}

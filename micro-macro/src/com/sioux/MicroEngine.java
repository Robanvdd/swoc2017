package com.sioux;

import com.google.gson.Gson;
import com.sioux.game_objects.MicroGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Ferdinand on 4-7-17.
 */
public class MicroEngine {
    MicroGame state;
    Bot p1_bot;
    CommandQueue p1_cmds;
    Bot p2_bot;
    CommandQueue p2_cmds;

    Gson gson;
    Integer ticks;
    Boolean running;

    final String script_dir;
    final String script_file;
    final String tick_dir;
    final String tick_file;
    final String python_command;

    MicroEngine() {
        state = null;
        p1_bot = null;
        p1_cmds = new CommandQueue();
        p2_bot = null;
        p2_cmds = new CommandQueue();

        gson = new Gson();
        ticks = 0;
        running = false;

        script_dir = "/home/luke/Projects/swoc2017/test-scripts/";
        script_file = "micro-bot.py";
        tick_dir = script_dir + "micro-game/";
        tick_file = "tick_%d.json";
        python_command = "python %s";
    }

    public void Run(MicroGame start) {
        state = start;

        try {
            String python_script = String.format(python_command, script_file);
            p1_bot = new Bot(script_dir, python_script, "p1");
            p2_bot = new Bot(script_dir, python_script, "p2");
        } catch (IOException e) {
            e.printStackTrace();
        }

        running = true;//(p1_bot.isRunning() && p2_bot.isRunning());
        while(running) {
            GameLogic();

            // Run for a specific amount of ticks.
            running = (++this.ticks < 100);
        }

        p1_bot.close();
        p2_bot.close();
    }

    private void GameLogic() {
        SendGameState();
        WaitForCommands();
        ExecuteCommands();
        SaveGameState();
    }

    private void SendGameState() {
        p1_bot.writeMessage(state);
        p2_bot.writeMessage(state);
    }

    private void WaitForCommands() {
        MicroGame p1_commands = p1_bot.readMessage(MicroGame.class, 1000);
        MicroGame p2_commands = p2_bot.readMessage(MicroGame.class, 1000);
    }

    private void ExecuteCommands() {
        // TODO
    }

    private void SaveGameState() {
        final String data = gson.toJson(state);
        final String name = String.format(this.tick_file, this.ticks);

        try {
            File file = new File(tick_dir + name);
            file.getParentFile().mkdir();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

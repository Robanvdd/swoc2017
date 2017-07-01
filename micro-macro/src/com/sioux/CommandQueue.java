package com.sioux;

import com.sioux.game_objects.Command;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Michael on 01/07/2017.
 */
public class CommandQueue {
    Queue<Command> incommingCommands;

    CommandQueue(){
        incommingCommands  = new LinkedList<Command>();
    }

    public Command GetCommandOutOffQueue(){
        return incommingCommands.remove();
    }

    public void AddCommandToQueue(Command command){
        incommingCommands.add(command);
    }
}

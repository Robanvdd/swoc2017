package com.sioux;

public class MoveCommand implements ICommand {
    private MacroGameLogic game;

    public MoveCommand(MacroGameLogic game){
        this.game = game;
    }

    @Override
    public void Execute() {
        game.Move();
    }
}

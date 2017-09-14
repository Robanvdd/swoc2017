package com.sioux;

public class PlanetRotateCommand implements ICommand {
    private MacroGameLogic game;

    public PlanetRotateCommand(MacroGameLogic game){
        this.game = game;
    }

    @Override
    public void Execute() {
        game.RotatePlanets();
    }


}

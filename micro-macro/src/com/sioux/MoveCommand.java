package com.sioux;

public class MoveCommand implements ICommand {
    private MacroGameLogic game;
    private String playerID;
    private int ufoId;
    private String solarSystemName;
    private String planetName;

    public MoveCommand(MacroGameLogic game, String playerID, int ufoId, String solarSystemName, String planetName){
        this.game = game;
        this.playerID = playerID;
        this.ufoId = ufoId;
        this.solarSystemName = solarSystemName;
        this.planetName = planetName;
    }

    @Override
    public void Execute() {
        game.Move(playerID,ufoId,solarSystemName,planetName);
    }

    public MacroGameLogic getGame() {
        return game;
    }

    public void setGame(MacroGameLogic game) {
        this.game = game;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public int getUfoId() {
        return ufoId;
    }

    public void setUfoId(int ufoId) {
        this.ufoId = ufoId;
    }

    public String getSolarSystemName() {
        return solarSystemName;
    }

    public void setSolarSystemName(String solarSystemName) {
        this.solarSystemName = solarSystemName;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }
}


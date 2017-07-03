package com.sioux.game_objects;

import java.io.Serializable;

/**
 * Created by Michael on 07/06/2017.
 * A game is a battle on a solarsystem
 */
public class Game implements Serializable{

    private int id;
    private String name;
    private int tick;
    private SolarSystem solarSystem;
    private Player playerOne;
    private Player playerTwo;
    private Battle battle;

    public Game(int id, String name, int tick, SolarSystem solarSystem, Player playerOne, Player playerTwo, Battle battle) {
        this.id = id;
        this.name = name;
        this.tick = tick;
        this.solarSystem = solarSystem;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.battle = battle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}

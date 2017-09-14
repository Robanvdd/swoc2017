package com.sioux.game_objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 07/06/2017.
 * A game is a battle on a solarsystem
 */
public class Game implements Serializable{

    private int id;
    private String name;
    private int tick;
    private SolarSystem solarSystem;
    private ArrayList<Player> players;
    private Battle battle;

    public Game(int id, String name, int tick, SolarSystem solarSystem, Battle battle) {
        this.id = id;
        this.name = name;
        this.tick = tick;
        this.solarSystem = solarSystem;
        this .players = new ArrayList<>();
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void AddPlayer(Player p){
        if(p != null){
            players.add(p);
        }
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}

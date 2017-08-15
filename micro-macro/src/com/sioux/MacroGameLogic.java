package com.sioux;

import com.sioux.game_objects.*;

import java.awt.*;
import java.util.ArrayList;

public class MacroGameLogic {
    //this class takes care of updating game state.

    private static Object mutex = new Object();
    private static MacroGameLogic instance;
    private Game gameState;
    private ArrayList<Player> players;

    private MacroGameLogic(){
        this.players = new ArrayList<>();
    }

    public static MacroGameLogic getInstance(){
        if(instance==null){
            synchronized (mutex){
                if(instance==null) instance= new MacroGameLogic();
            }
        }
        return instance;
    }

    public void InitGameState(){

        ArrayList planets = new ArrayList<Planet>();
        planets.add(new Planet(1,"earth",10,10,null));
        planets.add(new Planet(1,"venus",12,10,null));
        planets.add(new Planet(1,"deathStar",14,10,null));
        planets.add(new Planet(1,"mars",16,10,null));
        planets.add(new Planet(1,"jupiter",18,10,null));

        SolarSystem solarSystem = new SolarSystem(1,"sp1",new Point(10,10),planets);

        ArrayList ufos = new ArrayList<Ufo>();
        ufos.add(new Ufo(1,UfoType.FIGHTER,false,new Point(10,10)));
        ufos.add(new Ufo(1,UfoType.FIGHTER,false,new Point(10,10)));
        ufos.add(new Ufo(1, UfoType.FIGHTER,false,new Point(10,10)));
        ufos.add(new Ufo(1,UfoType.FIGHTER,false,new Point(10,10)));

        Game game = new Game(1,"macroBatle",(1000/60),solarSystem,players.get(0),players.get(0),null);

        gameState = game;
    }

    private Player GetPlayer(String playerName){
        for (Player p: players) {
            if(p.getName() == playerName) return p;
        }
        System.out.print("[MacroGameLogic]::No such player found");
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Game GetGameState(){
        return this.gameState;
    }

    public void AddPlayerToGame(Player p){
        players.add(p);
    }

    public void Move(String playerId, int ufoId, String SolarSystem, String ToPlanet){

        System.out.print("[Game]::Doing a Move\n");
    }

    public void Conquer() {
        System.out.print("[Game]::Going to Conquer\n");
    }
}

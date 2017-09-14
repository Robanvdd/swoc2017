package com.sioux;

import com.sioux.game_objects.*;

import java.awt.*;
import java.util.ArrayList;

public class MacroGameLogic {
    //this class takes care of updating game state.

    private static Object mutex = new Object();
    private static MacroGameLogic instance;
    private Game gameState;

    private MacroGameLogic() {
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
        ufos.add(new Ufo(1,UfoType.FIGHTER,false,new Point.Double(10,10)));
        ufos.add(new Ufo(1,UfoType.FIGHTER,false,new Point.Double(10,10)));
        ufos.add(new Ufo(1, UfoType.FIGHTER,false,new Point.Double(10,10)));
        ufos.add(new Ufo(1,UfoType.FIGHTER,false,new Point.Double(10,10)));

        Game game = new Game(1,"macroBatle",(1000/60),solarSystem,null);

        gameState = game;
    }

    private Player GetPlayer(String playerName){
        ArrayList<Player> playerList = gameState.getPlayers();
        for (Player p: playerList) {
            if(p.getName() == playerName) return p;
        }
        System.out.print("[MacroGameLogic]::No such player found");
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return gameState.getPlayers();
    }

    public Game GetGameState(){
        return this.gameState;
    }

    public void AddPlayerToGame(Player p) {
        gameState.AddPlayer(p);
    }

    public void RotatePlanets(){
        SolarSystem system = gameState.getSolarSystem();
        for(Planet planet:system.getPlanets()){
            planet.setOrbitRotated(planet.getOrbitRotated()+2);
        }
    }

    public Point.Double PolarToCartesian (double  radius, double angle){
        double radian = Math.toRadians(angle);
        double x = Math.cos( radian ) * radius;
        double y = Math.sin( radian ) * radius;
        return new Point.Double(x,y);
    }

    public PolarPoint CartesianToPolar(Point p){
        double x = p.getX();
        double y = p.getY();

        double r     = Math.sqrt((x*x + y*y));
        double theta = Math.toDegrees(Math.atan((y / x)));

        return new PolarPoint(r, theta);
    }

    public void Move(String playerId, int ufoId, String SolarSystem, String ToPlanet){
        Player player = GetPlayer(playerId);
        Ufo ufo = player.GetUfoById(ufoId);
        Point.Double p = ufo.getCoordinate();
        for (Planet planet:gameState.getSolarSystem().getPlanets()) {
            if (planet.getName() == ToPlanet){
                Point.Double planetPoint = PolarToCartesian(planet.getOrbitDistance(), planet.getOrbitRotated());
                double distance = planetPoint.distance(ufo.getCoordinate());
                double distanceToMove = (distance / 10); //1/10 every tick
                double newX = p.getX() + distanceToMove * Math.cos((planet.getOrbitRotated() * Math.PI / 180));
                double newY = p.getY() + distanceToMove * Math.sin((planet.getOrbitRotated() * Math.PI / 180));
                ufo.setCoordinate(new Point.Double(newX,newY));
            }
        }

        System.out.print("[Game]::Doing a Move\n");
    }

    public void Conquer(String playerID, int ufoId, String solarSystemName, String planetName) {
        //Todo
        System.out.print("[Game]::Going to Conquer\n");
    }
}

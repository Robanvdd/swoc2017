package com.sioux.game_objects;

import com.sun.org.apache.xml.internal.security.Init;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 01/07/2017.
 */
public class InitialState {

    public Player player;
    public SolarSystem solarSystem;

    public InitialState(){
        Defaults();
    }

    public InitialState(Player p, SolarSystem solarSystem) {
        this.player = p;
        solarSystem = solarSystem;
    }

    private void Defaults() {
        List<Ufo> ufos = new ArrayList<>();

        for(int i = 0; i< 10; i++){
            ufos.add(new Ufo(i, UfoType.FIGHTER,false,new Point.Double(0,0), 10));
        }

        List<Planet> planets = new ArrayList<Planet>();
        for(int i = 0; i<10; i++){
            planets.add(new Planet(1,"p",10,10,null));
        }

        solarSystem = new SolarSystem(1, "S1",new Point(0,0),planets);
        this.player = new Player("playerOne",0,ufos,1);
    }
}

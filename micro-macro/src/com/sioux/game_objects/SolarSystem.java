package com.sioux.game_objects;

import java.awt.*;
import java.util.List;

/**
 * Created by Michael on 07/06/2017.
 */
public class SolarSystem {

    private int id;
    private String name;
    private Point coordinate;
    private List<Planet> planets;

    public SolarSystem(int id, String name, Point coordinate, List<Planet> planets) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.planets = planets;
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

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }
}

package com.sioux.GameEngine;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Michael on 3/15/2017.
 */
public class SolorSystem {
    private int _id;
    private String _name;
    private Point _coordinate;
    private ArrayList<Planet> _planets;

    public SolorSystem(int id, String name, Point coord, ArrayList<Planet> planets) {
        this._id = id;
        this._name = name;
        this._coordinate = coord;
        this._planets = planets;
    }

    public int getId() { return this._id; }
    public void setId(int id) { this._id = id; }

    public String getName() { return this._name; }
    public void setName(String name) { this._name = name; }

    public Point getCoordinate() { return this._coordinate; }
    public void setCoordinate(Point coord) { this._coordinate = coord; }

    public ArrayList<Planet> getPlanet() { return this._planets; }
    public void setPlanet(ArrayList<Planet> planets) { this._planets = planets; }
}

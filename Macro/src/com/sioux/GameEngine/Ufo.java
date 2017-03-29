package com.sioux.GameEngine;

import java.awt.*;
/**
 * Created by Michael on 3/15/2017.
 */
public class Ufo {

    private int _id;
    private String _type;
    private boolean _inFlight;
    private Point _coord;

    public Ufo(int id, String type, boolean inFlight, Point coord) {
        this._id = id;
        this._type = type;
        this._inFlight = inFlight;
        this._coord = coord;
    }

    public void set_id(int _id) { this._id = _id; }
    public int get_id() { return this._id; }

    public void set_type(String _type) { this._type = _type; }
    public String get_type() { return this._type; }

    public void set_inFlight(boolean _inFlight) { this._inFlight = _inFlight; }
    public boolean is_inFlight() { return this._inFlight; }

    public void set_coord(Point _coord) { this._coord = _coord; }
    public Point get_coord() { return this._coord; }
}

package com.sioux.GameEngine;

import java.util.ArrayList;

/**
 * Created by Michael on 3/15/2017.
 */
public class Player {

    private int _id;
    private String _name;
    private int _credits;
    private ArrayList<Ufo> _ufos;

    public Player(int id, String name, int credits, ArrayList<Ufo> ufos) {
        this._id = id;
        this._name = name;
        this._credits = credits;
        this._ufos = ufos;
    }

    public void set_id(int _id) { this._id = _id; }
    public int get_id() { return this._id; }

    public void set_name(String _name) { this._name = _name; }
    public String get_name() { return this._name; }

    public void set_credits(int _credits) { this._credits = _credits; }
    public int get_credits() { return this._credits; }

    public void set_ufos(ArrayList<Ufo> _ufos) { this._ufos = _ufos; }
    public ArrayList<Ufo> get_ufos() { return this._ufos; }
}

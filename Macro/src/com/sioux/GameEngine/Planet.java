package com.sioux.GameEngine;


/**
 * Created by Michael on 3/15/2017.
 */
public class Planet {
    private int _id;
    private String _name;
    private int _orbitDistance;
    private int _orbitRotation;
    private int _ownedBy;

    public Planet(int id, String name, int orbitDistance, int orbitRotation, int ownedBy) {
        this._id = id;
        this._name = name;
        this._orbitDistance = orbitDistance;
        this._orbitRotation = orbitRotation;
        this._ownedBy = ownedBy;
    }

    public int get_id() { return this._id; }
    public void set_id(int id) { this._id = id; }

    public String get_name() { return this._name; }
    public void set_name(String name) { this._name = name; }

    public void set_orbitDistance(int _orbitDistance) { this._orbitDistance = _orbitDistance; }
    public int get_orbitDistance() { return this._orbitDistance; }

    public void set_orbitRotation(int _orbitRotation) { this._orbitRotation = _orbitRotation; }
    public int get_orbitRotation() { return this._orbitRotation; }

    public void set_ownedBy(int _ownedBy) { this._ownedBy = _ownedBy; }
    public int get_ownedBy() { return this._ownedBy; }
}

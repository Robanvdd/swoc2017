package com.sioux.game_objects;

/**
 * Created by Michael on 07/06/2017.
 */
public class Planet {

    private int id;
    private String name;
    private float orbitDistance;
    private float orbitRotated;
    private Player owned_by;

    public Planet(int id, String name, float orbitDistance, float orbitRotated, Player owned_by) {
        this.id = id;
        this.name = name;
        this.orbitDistance = orbitDistance;
        this.orbitRotated = orbitRotated;
        this.owned_by = owned_by;
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

    public float getOrbitDistance() {
        return orbitDistance;
    }

    public void setOrbitDistance(float orbitDistance) {
        this.orbitDistance = orbitDistance;
    }

    public float getOrbitRotated() {
        return orbitRotated;
    }

    public void setOrbitRotated(float orbitRotated) {
        this.orbitRotated = orbitRotated;
    }

    public Player getOwned_by() {
        return owned_by;
    }

    public void setOwned_by(Player owned_by) {
        this.owned_by = owned_by;
    }
}

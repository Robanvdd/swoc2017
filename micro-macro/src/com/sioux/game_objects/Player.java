package com.sioux.game_objects;

import java.util.List;

/**
 * Created by Michael on 07/06/2017.
 */
public class Player {

    private String name;
    private float credits;
    private List<Ufo> ufos;
    private int id;

    public Player(String name, float credits, List<Ufo> ufos, int id) {
        this.name = name;
        this.credits = credits;
        this.ufos = ufos;
        this.id = id;
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

    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    public List<Ufo> getUfos() {
        return ufos;
    }

    public void setUfos(List<Ufo> ufos) {
        this.ufos = ufos;
    }
}

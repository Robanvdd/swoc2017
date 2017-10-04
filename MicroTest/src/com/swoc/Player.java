package com.swoc;

import java.util.ArrayList;

/**
 * Created by Michael on 04/10/2017.
 */
public class Player {
    private int id;
    private String name;
    private ArrayList<Bot> bots;

    public Player(int id, String name, ArrayList<Bot> bots) {
        this.id = id;
        this.name = name;
        this.bots = bots;
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

    public ArrayList<Bot> getBots() {
        return bots;
    }

    public void setBots(ArrayList<Bot> bots) {
        this.bots = bots;
    }
}

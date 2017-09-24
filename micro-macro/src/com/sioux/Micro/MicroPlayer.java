package com.sioux.Micro;

import java.util.ArrayList;
import java.util.List;

class MicroPlayer {
    private String name;
    private String color;
    private List<MicroBot> bots;

    public MicroPlayer(String name, String color) {
        this.name = name;
        this.color = color;
        this.bots = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<MicroBot> getBots() {
        return bots;
    }

    public void Add(MicroBot bot) {
        this.bots.add(bot);
    }
}

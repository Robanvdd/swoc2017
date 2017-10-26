package com.sioux.Micro;

import java.util.ArrayList;
import java.util.List;

class MicroPlayer {
    private  int id;
    private String name;
    private String color;
    private double hue;
    private List<MicroBot> ufos;

    // Non-serialized members (transient)
    private transient String script;

    public MicroPlayer(int id, String name, String color, double hue, String script) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.hue = hue;
        this.ufos = new ArrayList<>();
        this.script = script;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public double getHue() {
        return hue;
    }

    public List<MicroBot> getBots() {
        return ufos;
    }

    public String getScript() {
        return script;
    }

    public void addBot(MicroBot bot) {
        this.ufos.add(bot);
    }

    public boolean hasLivingBots() {
        for (MicroBot bot : ufos) {
            if (bot.isAlive()) return true;
        }
        return false;
    }
}

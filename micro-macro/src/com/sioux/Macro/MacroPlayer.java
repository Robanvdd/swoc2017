package com.sioux.Macro;

import java.util.List;

/**
 * Created by Ferdinand on 27-9-17.
 */
public class MacroPlayer {
    private int id;
    private String name;
    private String bot;
    private List<Integer> ufos;
    private List<Integer> survivors;
    private List<Integer> casualties;
    private String color;
    private double hue; // Huehueheuhue!

    public MacroPlayer(int id, String name, String bot, List<Integer> ufos,
                       List<Integer> survivors, List<Integer> casualties, String color, double hue) {
        this.id = id;
        this.name = name;
        this.bot = bot;
        this.ufos = ufos;
        this.survivors = survivors;
        this.casualties = casualties;
        this.color = color;
        this.hue = hue;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBot() {
        return bot;
    }

    public List<Integer> getUfos() {
        return ufos;
    }

    public String getColor() {
        return color;
    }

    public double getHue() {
        return hue;
    }
}

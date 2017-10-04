package com.swoc;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Michael on 04/10/2017.
 */
public class Bot {
    private int id;
    private String name;
    private float hitpoints;
    private Point2D.Float position;

    public Bot(int id, String name, float hitpoints, Point2D.Float position) {
        this.id = id;
        this.name = name;
        this.hitpoints = hitpoints;
        this.position = position;
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

    public float getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(float hitpoints) {
        this.hitpoints = hitpoints;
    }

    public Point2D.Float getPosition() {
        return position;
    }

    public void setPosition(Point2D.Float position) {
        this.position = position;
    }
}

package com.sioux.game_objects;

import java.awt.*;

/**
 * Created by Michael on 07/06/2017.
 */
public class Ufo {

    private int id;
    private UfoType ufoType;
    private boolean InFight;
    private Point.Double coordinate;

    public Ufo(int id, UfoType ufoType, boolean inFight, Point.Double coordinate) {
        this.id = id;
        this.ufoType = ufoType;
        InFight = inFight;
        this.coordinate = coordinate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UfoType getUfoType() {
        return ufoType;
    }

    public void setUfoType(UfoType ufoType) {
        this.ufoType = ufoType;
    }

    public boolean isInFight() {
        return InFight;
    }

    public void setInFight(boolean inFight) {
        InFight = inFight;
    }

    public Point.Double getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point.Double coordinate) {
        this.coordinate = coordinate;
    }
}

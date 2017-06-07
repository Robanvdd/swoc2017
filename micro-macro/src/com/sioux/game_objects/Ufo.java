package com.sioux.game_objects;

import java.awt.*;

/**
 * Created by Michael on 07/06/2017.
 */
public class Ufo {

    private int id;
    private UfoType ufoType;
    private boolean InFight;
    private Point coordinate;

    public Ufo(int id, UfoType ufoType, boolean inFight, Point coordinate) {
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

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }
}

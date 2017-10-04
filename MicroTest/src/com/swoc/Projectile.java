package com.swoc;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Michael on 04/10/2017.
 */
public class Projectile {
    private Point2D.Float position;
    private float direction;

    public Projectile(Point2D.Float position, float direction) {
        this.position = position;
        this.direction = direction;
    }

    public Point2D.Float getPosition() {
        return position;
    }

    public void setPosition(Point2D.Float position) {
        this.position = position;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }
}

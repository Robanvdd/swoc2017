package com.sioux.Micro;

import java.awt.*;

class MicroProjectile {
    private Point.Double position;
    private Double direction;

    // Non-serialized members (transient)
    private transient String source;
    private transient Integer damage;

    public MicroProjectile(Point.Double position, Double direction, String source) {
        this.position = position;
        this.direction = direction;
        this.source = source;
        this.damage = 5;
    }

    public void Move(Double x, Double y) {
        position.x += x;
        position.y += y;
    }

    public Point.Double getPosition() {
        return position;
    }

    public Double getDirection() {
        return direction;
    }

    public String getSource() {
        return source;
    }

    public Integer getDamage() {
        return damage;
    }
}

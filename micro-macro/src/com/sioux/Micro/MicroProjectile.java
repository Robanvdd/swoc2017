package com.sioux.Micro;

import java.awt.*;

class MicroProjectile {
    private Point.Double position;
    private double direction;

    // Non-serialized members (transient)
    private transient int source;
    private transient int damage;
    private transient double speed;
    private transient int radius;

    public MicroProjectile(Point.Double position, Double direction, int source) {
        this.position = new Point.Double(position.x, position.y);
        this.direction = direction;
        this.source = source;
        this.damage = 5;
        this.speed = 15;
        this.radius = 5;
    }

    public void Move() {
        Point.Double newPos = Utils.PolarToCartesian(speed, direction);
        position.x += newPos.x;
        position.y += newPos.y;
    }

    public Point.Double getPosition() {
        return position;
    }

    public double getDirection() {
        return direction;
    }

    public int getSource() {
        return source;
    }

    public int getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }

    public int getRadius() {
        return radius;
    }
}

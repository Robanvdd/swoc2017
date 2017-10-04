package com.sioux.Micro;

import com.sioux.Micro.Configuration.Projectile;

import java.awt.*;

class MicroProjectile {
    private int id;
    private Point.Double position;
    private double direction;

    // Non-serialized members (transient)
    private static transient int staticCounter = 0;
    private transient int source;
    private transient int damage;
    private transient double speed;
    private transient int radius;

    public MicroProjectile(Point.Double position, Double direction, int source) {
        this.id = staticCounter++;
        this.position = new Point.Double(position.x, position.y);
        this.direction = direction;
        this.source = source;
        this.damage = Projectile.Damage;
        this.speed = Projectile.Speed;
        this.radius = Projectile.Radius;
    }

    public void Move() {
        Point.Double newPos = Utils.PolarToCartesian(speed, direction);
        position.x += newPos.x;
        position.y += newPos.y;
    }

    public int getId() {
        return id;
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

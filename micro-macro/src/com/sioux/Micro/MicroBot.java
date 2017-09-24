package com.sioux.Micro;

import com.sioux.Micro.Command.Move;
import com.sioux.Micro.Command.Shoot;

import java.awt.*;

class MicroBot {
    private String name;
    private int hitpoints;
    private Point.Double position;

    // Non-serialized members (transient)
    private transient int tickShoot;
    private transient int radius;

    public MicroBot(String name, Integer hp, Point.Double pos, Integer radius) {
        this.name = name;
        this.hitpoints = hp;
        this.position = pos;
        this.tickShoot = 0;
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public Point.Double getPosition() {
        return position;
    }

    public int getTickShoot() {
        return tickShoot;
    }

    public int getRadius() {
        return radius;
    }

    public void Move(Move cmd, MicroArena arena) {
        if (cmd == null || !this.isAlive()) return;
        Point.Double newPos = Utils.PolarToCartesian(cmd.getSpeed(), cmd.getDirection());
        newPos = Utils.ClampPoint(newPos, 0, arena.getHeight(), 0, arena.getWidth());
        position.x += newPos.x;
        position.y += newPos.y;

        if (!Utils.BotInsideArena(this, arena)) {
            hitpoints = 0;
        }
    }

    public MicroProjectile Shoot(Shoot cmd, String source, int tick) {
        if (cmd == null || !this.isAlive() || !this.canShoot(tick))  return null;
        else tickShoot = tick;
        return new MicroProjectile(this.position, cmd.getDirection(), source);
    }

    public Boolean Hit(MicroProjectile projectile) {
        if (projectile == null || !this.isAlive()) return false;

        Double projectileRadius = 5.0;
        Double dx = projectile.getPosition().getX() - this.position.getX();
        Double dy = projectile.getPosition().getY() - this.position.getY();
        Double radii = radius + projectileRadius;

        if (( dx * dx ) + ( dy * dy ) < radii * radii) {
            hitpoints -= projectile.getDamage();
            return true;
        }
        return false;
    }

    public Boolean isAlive() {
        return hitpoints > 0;
    }

    public Boolean canShoot(int tick) {
        return (tickShoot + 30) < tick;
    }
}

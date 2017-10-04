package com.swoc;

/**
 * Created by Michael on 04/10/2017.
 */
public class Shoot {
    private int id;
    private float direction;

    public Shoot(int id, float direction) {
        this.id = id;
        this.direction = direction;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }
}

package com.swoc;

/**
 * Created by Michael on 04/10/2017.
 */
public class Move {
    private int id;
    private float direction;
    private float speed;

    public Move(int id, float direction, float speed) {
        this.id = id;
        this.direction = direction;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}

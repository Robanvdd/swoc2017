package com.swoc;

/**
 * Created by Michael on 04/10/2017.
 */
public class MoveAt {
    private int id;
    private float position;
    private float speed;

    public MoveAt(int id, float position, float speed) {
        this.id = id;
        this.position = position;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}

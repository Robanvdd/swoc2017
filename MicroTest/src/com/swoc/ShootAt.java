package com.swoc;

/**
 * Created by Michael on 04/10/2017.
 */
public class ShootAt {
    private int id;
    private float position;

    public ShootAt(int id, float position) {
        this.id = id;
        this.position = position;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }
}

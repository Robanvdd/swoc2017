package com.swoc;

/**
 * Created by Michael on 04/10/2017.
 */
public class BotOutput {
   private int id;
   private Move move;
   private MoveAt moveAt;
   private Shoot shoot;
   private ShootAt shootAt;

    public BotOutput(int id, Move move, MoveAt moveAt, Shoot shoot, ShootAt shootAt) {
        this.id = id;
        this.move = move;
        this.moveAt = moveAt;
        this.shoot = shoot;
        this.shootAt = shootAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public MoveAt getMoveAt() {
        return moveAt;
    }

    public void setMoveAt(MoveAt moveAt) {
        this.moveAt = moveAt;
    }

    public Shoot getShoot() {
        return shoot;
    }

    public void setShoot(Shoot shoot) {
        this.shoot = shoot;
    }

    public ShootAt getShootAt() {
        return shootAt;
    }

    public void setShootAt(ShootAt shootAt) {
        this.shootAt = shootAt;
    }
}

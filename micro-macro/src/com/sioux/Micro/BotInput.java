package com.sioux.Micro;

import com.sioux.Micro.Command.Move;
import com.sioux.Micro.Command.Shoot;
import com.sioux.Micro.Command.ShootAt;

class BotInput {
    private String name;
    private Move move;
    private Shoot shoot;
    private ShootAt shootAt;

    public String getName() {
        return name;
    }

    public Move getMove() {
        return move;
    }

    public Shoot getShoot() {
        return shoot;
    }

    public ShootAt getShootAt() { return shootAt; }
}

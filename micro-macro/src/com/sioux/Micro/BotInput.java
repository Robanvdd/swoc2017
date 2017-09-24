package com.sioux.Micro;

import com.sioux.Micro.Command.Move;
import com.sioux.Micro.Command.Shoot;

class BotInput {
    private String name;
    private Move move;
    private Shoot shoot;

    public String getName() {
        return name;
    }

    public Move getMove() {
        return move;
    }

    public Shoot getShoot() {
        return shoot;
    }
}

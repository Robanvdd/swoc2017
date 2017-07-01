package com.sioux;

import com.sioux.game_objects.Game;

/**
 * Created by Michael on 01/07/2017.
 */
public class MicroBattle {
    private Game game;

    MicroBattle(Game g){
        this.game = g;
        game.setName("s1p2");//test by change an element;
    }

    public String GetMicroBattleGameName(){
        return game.getName();
    }
}

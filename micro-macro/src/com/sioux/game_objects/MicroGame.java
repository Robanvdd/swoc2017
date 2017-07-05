package com.sioux.game_objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 5-7-17.
 */
public class MicroGame {
    public Player p1;
    public List<Ufo> p1_ufos;
    public Player p2;
    public List<Ufo> p2_ufos;

    public MicroGame() {
        p1_ufos = new ArrayList<Ufo>();
        p2_ufos = new ArrayList<Ufo>();
        p1_ufos.add(new Ufo(0, UfoType.FIGHTER, false, new Point(0, 0)));
        p2_ufos.add(new Ufo(0, UfoType.FIGHTER, false, new Point(0, 0)));
        p1 = new Player("p1", 0, p1_ufos, 1);
        p2 = new Player("p2", 0, p2_ufos, 2);
    }
}

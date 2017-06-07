package com.sioux.game_objects;

import java.util.List;

/**
 * Created by Michael and Ferdinand on 07/06/2017.
 */
public class Battle {

    private int id;
    private List<Ufo> alliance;
    private List<Ufo> horde;
    private Planet battleArena;

    public Battle(int id, List<Ufo> alliance, List<Ufo> horde, Planet battleArena) {
        this.id = id;
        this.alliance = alliance;
        this.horde = horde;
        this.battleArena = battleArena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Ufo> getAlliance() {
        return alliance;
    }

    public void setAlliance(List<Ufo> alliance) {
        this.alliance = alliance;
    }

    public List<Ufo> getHorde() {
        return horde;
    }

    public void setHorde(List<Ufo> horde) {
        this.horde = horde;
    }

    public Planet getBattleArena() {
        return battleArena;
    }

    public void setBattleArena(Planet battleArena) {
        this.battleArena = battleArena;
    }
}
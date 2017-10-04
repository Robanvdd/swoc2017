package com.swoc;

import java.util.ArrayList;

/**
 * Created by Michael on 04/10/2017.
 */
public class MicroBattle {
    private Arena arena;
    private int playerId;
    private String playerName;
    private ArrayList<Player> players;
    private ArrayList<Projectile> projectiles;

    public MicroBattle(Arena arena, int player, String playerName, ArrayList<Player> players, ArrayList<Projectile> projectiles) {
        this.arena = arena;
        this.playerId = player;
        this.playerName = playerName;
        this.players = players;
        this.projectiles = projectiles;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }
}

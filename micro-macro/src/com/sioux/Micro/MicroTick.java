package com.sioux.Micro;

import java.util.ArrayList;
import java.util.List;

class MicroTick {
    private MicroArena arena;
    private String player;
    private List<MicroPlayer> players;
    private List<MicroProjectile> projectiles;

    public MicroTick(MicroArena arena) {
        this.arena = arena;
        this.player = new String();
        this.players = new ArrayList<>();
        this.projectiles = new ArrayList<>();
    }

    public MicroArena getArena() {
        return arena;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public List<MicroPlayer> getPlayers() {
        return players;
    }

    public List<MicroProjectile> getProjectiles() {
        return projectiles;
    }

    public void Add(MicroPlayer player) {
        players.add(player);
    }

    public void Add(MicroProjectile projectile) {
        projectiles.add(projectile);
    }
}

package com.sioux.Micro;

import java.util.ArrayList;
import java.util.List;

class MicroTick {
    private int tick;
    private int gameId;
    private MicroArena arena;
    private List<MicroPlayer> players;
    private List<MicroProjectile> projectiles;
    private List<Integer> hits;

    // Non-serialized members (transient)
    private transient String ticksFolder;

    public MicroTick(int gameId, String ticksFolder, MicroArena arena) {
        this.tick = 0;
        this.gameId = gameId;
        this.arena = arena;
        this.players = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.hits = new ArrayList<>();
        this.ticksFolder = ticksFolder;
    }

    public int getTick() {
        return tick;
    }

    public int getGameId() {
        return gameId;
    }

    public MicroArena getArena() {
        return arena;
    }

    public List<MicroPlayer> getPlayers() {
        return players;
    }

    public List<MicroProjectile> getProjectiles() {
        return projectiles;
    }

    public String getTicksFolder() {
        return ticksFolder;
    }

    public void Add(MicroPlayer player) {
        players.add(player);
    }

    public void Add(MicroProjectile projectile) {
        projectiles.add(projectile);
    }

    public void Remove(MicroProjectile projectile) {
        hits.add(projectile.getId());
        projectiles.remove(projectile);
    }

    public void IncreaseTick() {
        hits.clear();
        tick++;
    }
}

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

    // Quick hax to include current player's id and name in the tick.
    private int playerId;
    private String playerName;

    public MicroTick(int gameId, MicroArena arena) {
        this.tick = 0;
        this.gameId = gameId;
        this.arena = arena;
        this.players = new ArrayList<>();
        this.projectiles = new ArrayList<>();

        clearPlayer();
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

    public void setPlayer(int id, String name) {
        this.playerId = id;
        this.playerName = name;
    }

    public void clearPlayer() {
        this.playerId = 0;
        this.playerName = "";
    }

    public void Add(MicroPlayer player) {
        players.add(player);
    }

    public void Add(MicroProjectile projectile) {
        projectiles.add(projectile);
    }

    public void Remove(MicroProjectile projectile) {
        projectiles.remove(projectile);
        hits.add(projectile.getId());
    }

    public void IncreaseTick() {
        hits.clear();
        tick++;
    }
}

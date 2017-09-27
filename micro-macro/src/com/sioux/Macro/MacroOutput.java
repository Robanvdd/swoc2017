package com.sioux.Macro;

import java.util.List;

/**
 * Created by Ferdinand on 27-9-17.
 */
public class MacroOutput {
    private List<MacroPlayer> players;
    private int gameId;
    private int winner;

    public MacroOutput(List<MacroPlayer> players, int gameId, int winner) {
        this.players = players;
        this.gameId = gameId;
        this.winner = winner;
    }
}

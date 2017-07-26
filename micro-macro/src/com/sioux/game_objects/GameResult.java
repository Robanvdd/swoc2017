package com.sioux.game_objects;

/**
 * Created by Ferdinand on 19-7-17.
 */
public class GameResult {
    Game game;
    String winner;

    public GameResult(Game game, String winner) {
        this.game = game;
        this.winner = winner;
    }
}

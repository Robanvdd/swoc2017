package com.swoc;

import java.util.ArrayList;

/**
 * Created by Michael on 04/10/2017.
 */
public class Test_spamCommands implements Test {

    Test_spamCommands(){

    }

    @Override
    public void GameLoop() {
        IO input = new IO();
        while (true){
            MicroBattle battle = input.ReadInput();
            int playerID = battle.getPlayerId();

            ArrayList<BotOutput> outputs = new ArrayList<>();
            ArrayList<Integer> botIds = new ArrayList<>();
            for (Player player: battle.getPlayers()) {
                if(player.getId() == playerID){
                    for (int i = 0; i < 100000; i++){
                        outputs.add(new BotOutput(player.getId(),
                                new Move(player.getBots().get(0).getId(),10,10),null, null, null));
                    }
                }
            }
        }
    }
}

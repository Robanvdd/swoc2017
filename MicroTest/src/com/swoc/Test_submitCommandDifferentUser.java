package com.swoc;

import java.util.ArrayList;

/**
 * Created by Michael on 04/10/2017.
 */
public class Test_submitCommandDifferentUser implements Test{

    Test_submitCommandDifferentUser(){
    }

    @Override
    public void GameLoop(){
        IO input = new IO();

        while (true){
            MicroBattle battle = input.ReadInput();
            int playerID = battle.getPlayerId();
            Player otherPlayer;


            ArrayList<BotOutput> outputs = new ArrayList<>();
            for (Player player: battle.getPlayers()) {
                if(player.getId() != playerID){
                 otherPlayer = player;
                    for (Bot otherBot: otherPlayer.getBots()){
                        outputs.add(new BotOutput(otherPlayer.getId(),
                                                  new Move(otherBot.getId(),10,10),
                                                  null,
                                                  new Shoot(otherBot.getId(),10),
                                                  null));
                    }
                }
            }
            MicroBattle b = input.ReadInput();
            break;
        }
    }
}

package com.swoc;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Michael on 04/10/2017.
 */
public class Mock {

    Mock(){

    }


    public MicroBattle MockData(){
        Arena arena = new Arena(1000,1000);

        Bot b1 = new Bot(1,"ufo1",100, new Point2D.Float(10,10));
        Bot b2 = new Bot(2,"ufo2",100, new Point2D.Float(10,10));
        Bot b3 = new Bot(3,"ufo3",100, new Point2D.Float(10,10));
        ArrayList<Bot> playerOneBots = new ArrayList<>();
        playerOneBots.add(b1);
        playerOneBots.add(b2);
        playerOneBots.add(b3);
        Player playerOne = new Player(1,"player1",playerOneBots);

        Bot b10 = new Bot(1,"ufo1",100, new Point2D.Float(10,10));
        Bot b20 = new Bot(2,"ufo2",100, new Point2D.Float(10,10));
        Bot b30 = new Bot(3,"ufo3",100, new Point2D.Float(10,10));
        ArrayList<Bot> playerTwoBots = new ArrayList<>();
        playerOneBots.add(b10);
        playerOneBots.add(b20);
        playerOneBots.add(b30);
        Player playerTwo = new Player(1,"player2",playerTwoBots);

        ArrayList<Player> players = new ArrayList<>();
        players.add(playerOne);
        players.add(playerTwo);

        Projectile p = new Projectile(new Point2D.Float(10,10),1);
        Projectile p1 = new Projectile(new Point2D.Float(10,10),1);
        Projectile p2 = new Projectile(new Point2D.Float(10,10),1);
        Projectile p3 = new Projectile(new Point2D.Float(10,10),1);


        ArrayList<Projectile> projectiles = new ArrayList<>();
        projectiles.add(p);
        projectiles.add(p1);
        projectiles.add(p2);
        projectiles.add(p3);

        return new MicroBattle(arena,1,"player1", players, projectiles);
    }

    public ArrayList<BotOutput> MockBotOutputs(){
        BotOutput bop = new BotOutput(1,new Move(3,3,10),null,new Shoot(3,40),null);
        BotOutput bop1 = new BotOutput(1,new Move(3,3,10),null,new Shoot(3,40),null);
        BotOutput bop2 = new BotOutput(1,new Move(3,3,10),null,new Shoot(3,40),null);
        BotOutput bop3 = new BotOutput(1,new Move(3,3,10),null,new Shoot(3,40),null);

        ArrayList<BotOutput> items = new ArrayList<>();
        items.add(bop);
        items.add(bop1);
        items.add(bop2);
        items.add(bop3);

        return items;
    }
}

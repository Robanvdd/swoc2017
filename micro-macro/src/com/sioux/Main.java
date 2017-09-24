package com.sioux;

import com.sioux.Micro.MicroEngine;

public class Main {

    public static void main(String[] botArgs) {
//        BotShepherdThread bot = new BotShepherdThread(botArgs);//starts the bots
//        Thread botThread = new Thread(bot);
//        Engine e = new Engine(bot);
//        Thread engineThread = new Thread(e);
//
//        botThread.start();
//        engineThread.start();
//
//        //Sleep for testing
//        try { Thread.sleep(20000);} catch (InterruptedException e1) { e1.printStackTrace(); }
//
//        try {
//            botThread.interrupt();
//            engineThread.interrupt();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        MicroEngine micro = new MicroEngine();
        micro.Run(null);
    }
}
package com.sioux;

public class Main {

    public static void main(String[] args) {
        BotShepherdThread bot = new BotShepherdThread();//starts the bots
        Thread botThread = new Thread(bot);
        Engine e = new Engine(bot);
        Thread engineThread = new Thread(e);

        botThread.start();
        engineThread.start();

        //Sleep for testing
        try { Thread.sleep(20000);} catch (InterruptedException e1) { e1.printStackTrace(); }

        try {
            botThread.interrupt();
            engineThread.interrupt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

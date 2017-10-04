package com.swoc;

public class Main {


    public static void main(String[] args) {

        Test test = new Test_submitCommandDifferentUser();
        test.GameLoop();

        /*
        Test test1 = new Test_doNothing();
        test.GameLoop();

        Test test2 = new Test_spamCommands();
        test2.GameLoop();

        Test test3 = new Test_letProcessDie();
        test3.GameLoop();
        */
    }
}

package com.swoc;

/**
 * Created by Michael on 04/10/2017.
 */
public class Test_doNothing implements Test {

    Test_doNothing(){

    }

    @Override
    public void GameLoop(){

        while (true) {
            //busy waiting
        }
    }
}

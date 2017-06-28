package com.sioux.game_scheduler;

import com.sioux.game_engine.MacroEngine;

/**
 * Created by Michael on 21/06/2017.
 */
public class scheduler { //starts up the macroEngine, gives commands to the macro Engine parses user input to usable objects for macroEngine

    Client client; //Returns command for macroEngine
    MacroEngine macroEngine;//knows where planets are and starts battles(aka microgame)
    DataOffload datapoint;

    public void SetUpClientCommunication() {
        Client _client = new Client(1);
        _client.StartUpClient("pathToScript");
    }
}

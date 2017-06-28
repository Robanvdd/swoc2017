package com.sioux.game_scheduler;

import java.io.IOException;

/**
 * Created by Michael on 21/06/2017.
 */
public class IOScocket {

    private String inputCommandString;
    private String _pathToScript;
    Process clientProcess;

    public IOScocket(String pathToScript) {
        this._pathToScript = pathToScript;
    }

    public void Start() {
        ProcessBuilder builder = new ProcessBuilder(_pathToScript);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInputCommandString() {
        if(!inputCommandString.isEmpty())
            return inputCommandString;
        else return "";
    }

    public void SendString(String outPutCommandString) {

    }
}

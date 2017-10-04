package com.swoc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Michael on 04/10/2017.
 */
public class IO {

    public IO() {

    }

    public MicroBattle ReadInput(){
        Scanner stdin = new Scanner(System.in);

        String input = "";
        String in;

        while (stdin.hasNextLine() && !(in = stdin.nextLine()).equals("")) {
            input += in;
        }
        stdin.close();

        return ParseToObj(input);
    }

    public void WriteToDisk(MicroBattle battle){
        try (Writer writer = new FileWriter("./Output.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(battle, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Write(ArrayList<BotOutput> botCommands ){
        System.out.print(ParseToJson(botCommands));
    }

    private MicroBattle ParseToObj(String data){
        Gson gson = new  GsonBuilder().create();
        return gson.fromJson(data, MicroBattle.class);
    }

    private <T> String ParseToJson(T data){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(data);
    }
}

package com.sioux;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sioux.Macro.MacroInput;
import com.sioux.Macro.MacroOutput;
import com.sioux.Micro.MicroEngine;

import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static GsonBuilder gsonBuilder;
    private static Gson gson;

    public static void main(String[] botArgs) {
        scanner = new Scanner(System.in);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        try {
            String macroInputJson = scanner.nextLine();
            MacroInput macroInput = gson.fromJson(macroInputJson, MacroInput.class);

            MicroEngine micro = new MicroEngine();
            MacroOutput macroOutput = micro.Run(macroInput);

            String macroOutputJson = gson.toJson(macroOutput, MacroOutput.class);
            System.out.println(macroOutputJson);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}

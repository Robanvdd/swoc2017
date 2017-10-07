package com.sioux;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sioux.Macro.MacroInput;
import com.sioux.Macro.MacroOutput;
import com.sioux.Micro.Configuration.Dev;
import com.sioux.Micro.MicroEngine;

import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static GsonBuilder gsonBuilder;
    private static Gson gson;

    public static void main(String[] botArgs) {
        for (String arg : botArgs) {
            switch (arg) {
                case "--debug": // All debug modes relevant to the user
                    Dev.EnableDebugMode(Dev.DebugMode.Micro);
                    Dev.EnableDebugMode(Dev.DebugMode.Script);
                    break;
                case "--debug-dev":
                    Dev.EnableDebugMode(Dev.DebugMode.Dev);
                    break;
                case "--debug-micro":
                    Dev.EnableDebugMode(Dev.DebugMode.Micro);
                    break;
                case "--debug-script":
                    Dev.EnableDebugMode(Dev.DebugMode.Script);
                    break;
                default:
                    break;
            }
        }

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

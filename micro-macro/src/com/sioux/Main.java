package com.sioux;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sioux.Macro.MacroInput;
import com.sioux.Macro.MacroOutput;
import com.sioux.Micro.Configuration.Debug;
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
                    Debug.EnableDebugMode(Debug.DebugMode.Micro);
                    Debug.EnableDebugMode(Debug.DebugMode.Script);
                    break;
                case "--debug-dev":
                    Debug.EnableDebugMode(Debug.DebugMode.Dev);
                    break;
                case "--debug-micro":
                    Debug.EnableDebugMode(Debug.DebugMode.Micro);
                    break;
                case "--debug-script":
                    Debug.EnableDebugMode(Debug.DebugMode.Script);
                    break;
                default:
                    break;
            }
        }
        Debug.InitializeLogger();

        scanner = new Scanner(System.in);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        try {
            // Read input from Macro.
            String macroInputJson = scanner.nextLine();

            Debug.Print(Debug.DebugMode.Micro, "Micro Input: %s", macroInputJson);
            MacroInput macroInput = gson.fromJson(macroInputJson, MacroInput.class);

            try {
                MicroEngine micro = new MicroEngine();
                MacroOutput macroOutput = micro.Run(macroInput);

                try {
                    String macroOutputJson = gson.toJson(macroOutput, MacroOutput.class);
                    Debug.Print(Debug.DebugMode.Micro, "Micro Output: %s", macroOutputJson);

                    // Write output to Macro.
                    System.out.println(macroOutputJson);
                } catch (JsonSyntaxException e) {
                    Debug.Print(Debug.DebugMode.Micro, "Micro Input Error: %s", e.getCause().getMessage());
                    Debug.PrintStacktrace(Debug.DebugMode.Dev, "Micro Input Stacktrace:", e);
                }
            } catch (Exception e) {
                Debug.Print(Debug.DebugMode.Micro, "Micro Error: %s", e.getCause().getMessage());
                Debug.PrintStacktrace(Debug.DebugMode.Dev, "Micro Stacktrace:", e);
            }
        } catch (JsonSyntaxException e) {
            Debug.Print(Debug.DebugMode.Micro, "Micro Output Error: %s", e.getCause().getMessage());
            Debug.PrintStacktrace(Debug.DebugMode.Dev, "Micro Output Stacktrace:", e);
        }
    }
}

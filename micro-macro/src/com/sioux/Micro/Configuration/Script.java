package com.sioux.Micro.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Ferdinand on 4-10-17.
 */
public final class Script {
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String runCommandFile = "runCommand.txt";

    public static String GetScriptCommand(String dir) {
        String runCommand = new String();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dir + "\\" + runCommandFile));
            try {
                runCommand = bufferedReader.readLine();
            } catch (Exception e) {
                Debug.Print(Debug.DebugMode.Micro, "Failed to read command file: %s", e.getCause().getMessage());
                Debug.PrintStacktrace(Debug.DebugMode.Micro, "Micro Read Command File Stack Trace:", e);
            } finally {
                bufferedReader.close();
            }
        }
        catch (Exception e) {
            Debug.Print(Debug.DebugMode.Micro, "Failed to open command file: %s", e.getCause().getMessage());
            Debug.PrintStacktrace(Debug.DebugMode.Micro, "Micro Open Command File Stack Trace:", e);
        }

        return runCommand;
    }

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isUnix() {
        return OS.contains("linux") || OS.contains("nix");
    }
}

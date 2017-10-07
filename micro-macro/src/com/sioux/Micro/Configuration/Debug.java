package com.sioux.Micro.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ferdinand on 7-10-17.
 */
public final class Debug {
    public enum DebugMode { Dev, Micro, Script }
    private static boolean DebugDevEnabled = false;
    private static boolean DebugMicroEnabled = false;
    private static boolean DebugScriptEnabled = false;

    public static void EnableDebugMode(DebugMode mode) {
        switch (mode) {
            case Dev:
                DebugDevEnabled = true;
                break;
            case Micro:
                DebugMicroEnabled = true;
                break;
            case Script:
                DebugScriptEnabled = true;
                break;
            default:
                break;
        }
    }

    public static void Print(DebugMode mode, String format, Object... args) {
        if (DebugMode.Dev == mode && !DebugDevEnabled) return;
        else if (DebugMode.Micro == mode && !DebugMicroEnabled) return;
        else if (DebugMode.Script == mode && !DebugScriptEnabled) return;

        String timestamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        String message = String.format("[%s][%s] %s%n", timestamp, mode.name(), format);
        System.err.printf(message, args);
    }
}

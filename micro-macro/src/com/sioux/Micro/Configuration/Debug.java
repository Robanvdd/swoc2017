package com.sioux.Micro.Configuration;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * Created by Ferdinand on 7-10-17.
 */
public final class Debug {
    public enum DebugMode { Dev, Micro, Script }
    private static boolean DebugDevEnabled = false;
    private static boolean DebugMicroEnabled = false;
    private static boolean DebugScriptEnabled = false;

    private static String logfile = "micro-log.txt";
    private static Logger logger = Logger.getLogger(Debug.class.getName());

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

    public static void InitializeLogger() {
        try {
            FileHandler filehandler = new FileHandler(logfile, true);
            filehandler.setFormatter(new CustomFormatter());
            logger.addHandler(filehandler);
        } catch (IOException e) {
            Print(DebugMode.Micro, "Logger Exception: %s", e.getMessage());
        }
    }

    public static void Print(DebugMode mode, String format, Object... args) {
        if (DebugMode.Dev == mode && !DebugDevEnabled) return;
        else if (DebugMode.Micro == mode && !DebugMicroEnabled) return;
        else if (DebugMode.Script == mode && !DebugScriptEnabled) return;

        String message = String.format("[%s] %s%n", mode.name(), format);
        LogRecord logRecord = new LogRecord(Level.INFO, String.format(message, args));

        logger.log(logRecord);
    }

    public static void PrintStacktrace(DebugMode mode, String message, Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        Print(mode, message + "\n%s", stringWriter.toString());
    }

    static private class CustomFormatter extends Formatter {
        public String format(LogRecord record) {
            String timestamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
            return String.format("[%s]%s", timestamp, record.getMessage());
        }
    }
}

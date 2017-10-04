package com.sioux.Micro.Configuration;

/**
 * Created by Ferdinand on 4-10-17.
 */
public final class Script {
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String windowsCmd = "run.cmd";
    private static String unixCmd = "run.sh";

    public static String GetScriptCommand() throws Exception {
        if (isWindows()) {
            return windowsCmd;
        } else if (isUnix()) {
            return unixCmd;
        } else {
            throw new Exception("OS not supported");
        }
    }

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isUnix() {
        return OS.contains("linux") || OS.contains("nix");
    }
}

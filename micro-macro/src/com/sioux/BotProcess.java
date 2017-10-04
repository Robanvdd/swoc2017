package com.sioux;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 29/06/2017.
 */

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

public class BotProcess implements AutoCloseable
{
    private final Process child;

    private final OutputStreamWriter outputWriter;

    private final StreamGobbler inputReader;
    private final StreamGobbler errorReader;

    private final Thread inputThread;
    private final Thread errorThread;

    public BotProcess(String workingDir, String command) throws IOException
    {
        File parent = new File(workingDir);
        if (!parent.exists())
        {
            throw new IllegalArgumentException("Working directory does not exist");
        }

        System.err.println("Starting new bot process " + command + " in " + parent);

        child = Runtime.getRuntime().exec(workingDir + "\\" + command, null, parent);

        inputReader = new StreamGobbler(child.getInputStream());
        errorReader = new StreamGobbler(child.getErrorStream());
        outputWriter = new OutputStreamWriter(child.getOutputStream());

        inputThread = new Thread(inputReader);
        errorThread = new Thread(errorReader);
        inputThread.start();
        errorThread.start();
    }

    @Override
    public void close()
    {
        child.destroy();

        try
        {
            child.waitFor();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        try
        {
            outputWriter.close();
        }
        catch (IOException e)
        {
        }
    }

    public String readLine(long timeOut)
    {
        if (!isRunning())
        {
            return null;
        }

        return inputReader.readLine(timeOut, TimeUnit.MILLISECONDS);
    }

    public boolean writeLine(String line)
    {
        try
        {
            outputWriter.write(line.trim());
            outputWriter.write("\n");
            outputWriter.flush();
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    public boolean isRunning()
    {
        try
        {
            child.exitValue();
            return false;
        }
        catch(IllegalThreadStateException ex)
        {
            return true;
        }
    }

    public String getErrors()
    {
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = errorReader.readLine(0, TimeUnit.SECONDS)) != null)
        {
            builder.append(line);
            builder.append('\n');
        }

        return builder.toString();
    }
}


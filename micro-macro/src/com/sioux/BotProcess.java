package com.sioux;

import com.sioux.Micro.Configuration.Debug;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 29/06/2017.
 */

import java.io.File;
import java.io.OutputStreamWriter;

public class BotProcess implements AutoCloseable
{
    private final ProcessBuilder processBuilder;
    private final Process child;

    private final OutputStreamWriter outputWriter;

    private final StreamGobbler inputReader;
    private final StreamGobbler errorReader;

    private final Thread inputThread;
    private final Thread errorThread;

    public BotProcess(String command) throws IOException
    {
        Debug.Print(Debug.DebugMode.Dev, "Starting new bot process with command: %s", command);

        processBuilder = new ProcessBuilder(command);
        child = processBuilder.start();

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
            Debug.Print(Debug.DebugMode.Dev, "Waiting for bot process");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace(System.err);
        }

        try
        {
            outputWriter.close();
            Debug.Print(Debug.DebugMode.Dev, "Closing bot process");
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
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

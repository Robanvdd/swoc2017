package com.sioux;

import com.sioux.Micro.Configuration.Debug;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class StreamGobbler implements Runnable
{
    private final InputStream is;

    StreamGobbler(InputStream is)
    {
        this.is = is;
    }

    private final BlockingQueue<String> lines = new LinkedBlockingQueue<String>();

    public String readLine()
    {
        return readLine(2, TimeUnit.SECONDS);
    }

    public String readLine(long timeout, TimeUnit unit)
    {
        try
        {
            return lines.poll(timeout, unit);
        }
        catch (InterruptedException e)
        {
            Debug.Print(Debug.DebugMode.Dev, "StreamGobbler read line timed out");
            return null;
        }
    }

    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null)
            {
                lines.put(line);
            }
        }
        catch (IOException | InterruptedException ioe)
        {
            ioe.printStackTrace(System.err);
        }
    }
}

package gos.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class StreamGobbler implements Runnable
{
    private final InputStream is;
    private final String name;

    StreamGobbler(InputStream is, String name)
    {
        this.is = is;
        this.name = name;
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
            return null;
        }
    }
    
    public void run()
    {
        System.err.println("[gobbler " + name + "] running");
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null)
            {
                System.err.println("[gobbler " + name + "] " + line);
                lines.put(line);
            }
            System.err.println("[gobbler " + name + "] done");
        }
        catch (IOException | InterruptedException ioe)
        {
            System.err.println("[gobbler " + name + "] exception");
            ioe.printStackTrace();
        }
    }
}

package matchmaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
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
            System.err.println("Readline timed out.");
            return null;
        }
    }
    
    public List<String> readAllLines()
    {
        List<String> allLines = new LinkedList<String>();
        try
        {
            while (!lines.isEmpty())
            {
                allLines.add(lines.take());
            }
        }
        catch (InterruptedException e)
        {
            // ignore
        }
        return allLines;
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
            ioe.printStackTrace();
        }
    }
}

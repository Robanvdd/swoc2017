package gos.engine.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BotProcess implements AutoCloseable
{
	private final Process child;
	private final InputStreamReader inputReader;
	private final BufferedReader bufferedInputReader;
	private final OutputStreamWriter outputWriter;
	private final InputStreamReader errorReader;
	private final BufferedReader bufferedErrorReader;
	
	public BotProcess(String command) throws IOException
	{
		child = Runtime.getRuntime().exec(command);
		inputReader = new InputStreamReader(child.getInputStream());
        bufferedInputReader = new BufferedReader(inputReader);
        outputWriter = new OutputStreamWriter(child.getOutputStream());
        errorReader = new InputStreamReader(child.getErrorStream());
        bufferedErrorReader = new BufferedReader(errorReader);
        
        singleLineReader = new SingleLineReader(bufferedInputReader);
	}
	
	@Override
	public void close()
	{
        try { bufferedErrorReader.close(); } catch (IOException e) {}
	    try { errorReader.close(); } catch (IOException e) {}
	    try { outputWriter.close(); } catch (IOException e) {}
	    try { bufferedInputReader.close(); } catch (IOException e) {}
	    try { inputReader.close(); } catch (IOException e) {}
	    
		child.destroy();
		
		try
		{
			child.waitFor();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		executor.shutdown();
	}
	
	private class SingleLineReader implements Callable<String>
	{
	    private final BufferedReader reader;
	     
	    public SingleLineReader(BufferedReader reader)
	    {
	        this.reader = reader;
	    }
	    
	    @Override
	    public String call() throws IOException
	    {
	        return reader.readLine();
	    }
	}

	private final SingleLineReader singleLineReader;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
            
	public String readLine(long timeOut)
	{
		if (!isRunning())
		{
		    return null;
		}

		Future<String> readSingleLine = executor.submit(singleLineReader);
		try
        {
            return readSingleLine.get(timeOut, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e1)
        {
            return null;
        }
        catch (ExecutionException e1)
        {
            return null;
        }
        catch (TimeoutException e1)
        {
            readSingleLine.cancel(true);
            return null;
        }
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
	    try
        {
            while (bufferedErrorReader.ready())
            {
                builder.append(bufferedErrorReader.readLine());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	    return builder.toString();
	}
}

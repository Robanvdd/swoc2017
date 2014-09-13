package gos.engine;

import gos.engine.protocol.Player;

import java.io.IOException;

import com.google.gson.Gson;

public class Bot implements AutoCloseable
{
	private final BotProcess handler;
	private final StringBuilder botInputLog = new StringBuilder();
	private final StringBuilder botOutputLog = new StringBuilder();
	private final Gson gson;

	public Bot(String workingDir, String command, Player player, String id) throws IOException
	{
        Player = player;
        Id = id;

        handler = new BotProcess(workingDir, command);
		gson = new Gson();
	}
	
	public final Player Player;
	public final String Id;

	public void writeMessage(Object message)
	{
		// Serialize
		String messageStr = gson.toJson(message);

		// Write
		botInputLog.append(messageStr);
        botInputLog.append('\n');
		handler.writeLine(messageStr);
	}

	public <T> T readMessage(Class<T> classOfT)
	{
        return readMessage(classOfT, 2000);
	}

	public <T> T readMessage(Class<T> classOfT, long timeOut)
	{
		// Read
		String messageStr = handler.readLine(timeOut);
        if (messageStr == null)
        {
            botOutputLog.append("No response from bot");
            return null;
        }

        botOutputLog.append(messageStr);
        botOutputLog.append('\n');
		
		// Deserialize
		return gson.fromJson(messageStr, classOfT);
	}

	public <T> T writeAndReadMessage(Object message, Class<T> classOfT)
	{
		return writeAndReadMessage(message, classOfT, 2000);
	}
	
	public <T> T writeAndReadMessage(Object message, Class<T> classOfT, long timeOut)
	{
		writeMessage(message);
		return readMessage(classOfT, timeOut);
	}

	@Override
	public void close()
	{
		handler.close();
	}

	public String getErrors()
	{
	    return handler.getErrors();
	}
	
	public String GetInputLog()
	{
	    return botInputLog.toString();
	}
	
	public String GetOutputLog()
	{
        return botOutputLog.toString();
	}
}

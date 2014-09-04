package gos.engine.io;

import java.io.IOException;

import com.google.gson.Gson;

public class Bot implements AutoCloseable
{
	private final BotProcess handler;
	private final StringBuilder dump;
	private final Gson gson;

	public Bot(String command, int player) throws IOException
	{
		handler = new BotProcess(command);
		dump = new StringBuilder();
		gson = new Gson();
		Player = player;
	}
	
	public final int Player;

	public void writeMessage(Object message)
	{
		// Serialize
		String messageStr = gson.toJson(message);

		// Write
		dump.append(">" + messageStr + "\n");
		handler.writeLine(messageStr);
	}

	public <T> T readMessage(Class<T> classOfT)
	{
        return readMessage(classOfT, 2000);
	}

	public <T> T readMessage(Class<T> classOfT, long timeOut)
	{
		// Read
		String message = handler.readLine(timeOut);
		dump.append("<" + message + "\n");
        if (message == null)
        {
            return null;
        }
		
		// Deserialize
		return gson.fromJson(message, classOfT);
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

	public String getDump()
	{
		return dump.toString();
	}
	
	public String getErrors()
	{
	    return handler.getErrors();
	}
}

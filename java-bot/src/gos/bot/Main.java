package gos.bot;

import gos.bot.protocol.InitiateRequest;
import gos.bot.protocol.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class Main
{
    private static InputStreamReader inStreamReader;
    private static BufferedReader inReader;
    private static Gson gson;
    
    private static Player myColor;
    
    public static void main(String[] args)
    {
        inStreamReader = new InputStreamReader(System.in);
        inReader = new BufferedReader(inStreamReader);
        
        gson = new Gson();

        {
            InitiateRequest initRequest = readMessage(InitiateRequest.class);
            if (initRequest == null)
            {
                System.err.println("Unexpected message received. Expected InitiateRequest.");
                return;
            }
    
            myColor = initRequest.Color;
            assert(myColor != Player.None);
            
            System.out.println("My color is " + myColor);
        }
        
        try
        {
            inReader.close();
            inStreamReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private static <T> T readMessage(Class<T> classOfT)
    {
        String line = null;
        try
        {
            line = inReader.readLine();
        }
        catch (IOException e)
        {
            return null;
        }

        if (line == null || line.isEmpty())
        {
            return null;
        }

        return gson.fromJson(line, classOfT);
    }

    private static <T> void writeMessage(T message)
    {
        String messageStr = gson.toJson(message);
        System.out.println(messageStr);
    }
}

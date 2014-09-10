package gos.bot;

import gos.bot.protocol.InitiateRequest;
import gos.bot.protocol.Move;
import gos.bot.protocol.MoveRequest;
import gos.bot.protocol.Player;
import gos.bot.protocol.ProcessedMove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class Engine implements AutoCloseable
{
    private final IBot bot;
    
    private final InputStreamReader inStreamReader;
    private final BufferedReader inReader;
    private final Gson gson;
    
    private Player botColor;
    
    public Engine(IBot bot)
    {
        this.bot = bot;
        
        inStreamReader = new InputStreamReader(System.in);
        inReader = new BufferedReader(inStreamReader);
        gson = new Gson();
    }
    
    public void run()
    {
        try
        {
            DoInitiateRequest();

            Player winner = DoFirstRound();
            while (winner == Player.None)
            {
                winner = DoNormalRound();
            }
            System.err.println("bot " + botColor + " done. winner = " + winner);
        }
        catch (Exception ex)
        {
            System.err.println("Exception. Bailing out.");
            ex.printStackTrace(System.err);
        }
    }

    private void DoInitiateRequest() throws InvalidMessageException
    {
        InitiateRequest initRequest = readMessage(InitiateRequest.class);
        if (initRequest == null)
        {
            throw new InvalidMessageException("Unexpected message received. Expected InitiateRequest.");
        }
        
        botColor = initRequest.Color;

        bot.HandleInitiate(initRequest);
    }
    
    private Player DoFirstRound() throws InvalidMessageException
    {
        Player winner;
        if (botColor == Player.White)
        {
            System.err.println("bot white first round wait for move request");
            // Do the first move
            HandleMoveRequest();
            System.err.println("bot white first round wait processed move");
            // and wait for the engine to acknowledge
            winner = HandleProcessedMove();
            if (winner != Player.None)
            {
                return winner;
            }

            // Wait for first two moves of black
            System.err.println("bot white first round wait processed move of black");
            winner = HandleProcessedMove();
            if (winner != Player.None)
            {
                return winner;
            }
            System.err.println("bot white first round wait processed move of black");
            winner = HandleProcessedMove();
            System.err.println("bot white first round done, winner = " + winner);
        }
        else
        {
            System.err.println("bot black first round wait processed move of white");
            // Wait for first white move
            winner = HandleProcessedMove();
            System.err.println("bot black first round done, winner = " + winner);
        }
        return winner;
    }
    
    private Player DoNormalRound() throws InvalidMessageException
    {
        Player winner;
        
        System.err.println("bot " + botColor + " normal round wait for first move request");
        HandleMoveRequest();
        System.err.println("bot " + botColor + " normal round wait for first processed move");
        winner = HandleProcessedMove();
        if (winner != Player.None)
        {
            System.err.println("bot " + botColor + " normal round done. winner = " + winner);
            return winner;
        }

        System.err.println("bot " + botColor + " normal round wait for second move request");
        HandleMoveRequest();
        System.err.println("bot " + botColor + " normal round wait for second processed move");
        winner = HandleProcessedMove();
        if (winner != Player.None)
        {
            System.err.println("bot " + botColor + " normal round done. winner = " + winner);
            return winner;
        }

        System.err.println("bot " + botColor + " normal round wait for first other processed move");
        winner = HandleProcessedMove();
        if (winner != Player.None)
        {
            System.err.println("bot " + botColor + " normal round done. winner = " + winner);
            return winner;
        }

        System.err.println("bot " + botColor + " normal round wait for second other processed move");
        winner = HandleProcessedMove();
        
        System.err.println("bot " + botColor + " normal round done. winner = " + winner);
        return winner;
    }

    private void HandleMoveRequest() throws InvalidMessageException
    {
        // process first move
        MoveRequest moveRequest = readMessage(MoveRequest.class);
        if (moveRequest == null)
        {
            throw new InvalidMessageException("Unexpected message received. Expected MoveRequest.");
        }

        Move move = bot.HandleMove(moveRequest);
        writeMessage(move);
    }

    private Player HandleProcessedMove() throws InvalidMessageException
    {
        ProcessedMove processedMove = readMessage(ProcessedMove.class);
        if (processedMove == null)
        {
            throw new InvalidMessageException("Unexpected message received. Expected ProcessedMove.");
        }

        bot.HandleProcessedMove(processedMove);
        return processedMove.Winner;
    }

    @Override
    public void close() throws Exception
    {
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
    

    private <T> T readMessage(Class<T> classOfT)
    {
        String messageStr = null;
        try
        {
            messageStr = inReader.readLine();
            System.err.println("bot in>" + messageStr);
        }
        catch (IOException e)
        {
            return null;
        }

        if (messageStr == null || messageStr.isEmpty())
        {
            return null;
        }

        return gson.fromJson(messageStr, classOfT);
    }

    private <T> void writeMessage(T message)
    {
        String messageStr = gson.toJson(message);
        System.err.println("bot out>" + messageStr);
        System.out.println(messageStr);
    }
}

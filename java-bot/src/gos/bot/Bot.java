package gos.bot;

import gos.bot.protocol.InitiateRequest;
import gos.bot.protocol.Move;
import gos.bot.protocol.MoveRequest;
import gos.bot.protocol.Player;
import gos.bot.protocol.ProcessedMove;

public class Bot implements IBot
{
    private Player myColor;
    
    public Bot()
    {
    }

    @Override
    public void HandleInitiate(InitiateRequest request)
    {
        myColor = request.Color;
        assert(myColor != Player.None);
        
        System.out.println("My color is " + myColor);
    }

    @Override
    public Move HandleMove(MoveRequest request)
    {
        return null;
    }

    @Override
    public void HandleProcessedMove(ProcessedMove move)
    {
    }

}

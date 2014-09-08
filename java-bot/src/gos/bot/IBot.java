package gos.bot;

import gos.bot.protocol.InitiateRequest;
import gos.bot.protocol.Move;
import gos.bot.protocol.MoveRequest;
import gos.bot.protocol.ProcessedMove;

public interface IBot
{
    public void HandleInitiate(InitiateRequest request);
    public Move HandleMove(MoveRequest request);
    public void HandleProcessedMove(ProcessedMove move);
}

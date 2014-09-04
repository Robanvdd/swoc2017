package gos.engine;

public class ProcessedMove
{
    public ProcessedMove(int player, Move move, int winner)
    {
        Player = player;
        Move = move;
        Winner = winner;
    }
    
    public final int Player;
    public final Move Move;
    public final int Winner;
}

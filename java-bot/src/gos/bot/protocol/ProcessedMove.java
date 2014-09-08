package gos.bot.protocol;

public class ProcessedMove
{
    public ProcessedMove(Player player, Move move, Player winner)
    {
        Player = player;
        Move = move;
        Winner = winner;
    }
    
    public final Player Player;
    public final Move Move;
    public final Player Winner;
}

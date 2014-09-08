package gos.bot.protocol;

public class MoveRequest
{
    public MoveRequest(Board board, int[] allowedMoves)
    {
        Board = board;
        AllowedMoves = allowedMoves;
    }
    
    public final Board Board;
    public final int[] AllowedMoves;
}

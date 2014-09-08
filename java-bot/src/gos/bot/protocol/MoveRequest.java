package gos.bot.protocol;

public class MoveRequest
{
    public MoveRequest(Board board, MoveType[] allowedMoves)
    {
        Board = board;
        AllowedMoves = allowedMoves;
    }
    
    public final Board Board;
    public final MoveType[] AllowedMoves;
}

package gos.bot.protocol;

public class Move
{
    public Move(MoveType type, BoardLocation from, BoardLocation to)
    {
        Type = type;
        From = from;
        To = to;
    }
    
    public final MoveType Type;
    public final BoardLocation From;
    public final BoardLocation To;
}

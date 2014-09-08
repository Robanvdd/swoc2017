package gos.bot.protocol;

public class Move
{
    public static final int Pass = 0;
    public static final int Attack = 1;
    public static final int Strengthen = 2;
    
    public Move(int type, BoardLocation from, BoardLocation to)
    {
        Type = type;
        From = from;
        To = to;
    }
    
    public final int Type;
    public final BoardLocation From;
    public final BoardLocation To;
}

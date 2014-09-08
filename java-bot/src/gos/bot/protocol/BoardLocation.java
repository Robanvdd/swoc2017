package gos.bot.protocol;

public class BoardLocation
{
    public BoardLocation(int x, int y)
    {
        if (!IsLegal(x, y))
        {
            throw new IllegalArgumentException("not a legal board location");
        }

        X = x;
        Y = y;
    }

    public final int X;
    public final int Y;

    public static boolean IsLegal(int x, int y)
    {
        return x >= 0 && x < 9 && y >= 0 && y < 9 &&
                (x - y) < 5 && (y - x) < 5 && (x != 4 || y != 4);
    }

    public String ToLabel()
    {
        char x = (char)('A' + X);
        char y = (char)('1' + (Y - Math.max(X - 4, 0)));
        return new String(new char[] { x, y });
    }
}

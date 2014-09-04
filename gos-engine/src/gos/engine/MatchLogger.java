package gos.engine;

import java.util.ArrayList;
import java.util.List;

public class MatchLogger
{
    private final List<Move> moves = new ArrayList<Move>();

    public MatchLogger()
    {
    }

    public void AddMove(Move move)
    {
        moves.add(move);
    }

    public String GetLog()
    {
        StringBuilder sb = new StringBuilder();
        for (Move move : moves)
        {
            switch (move.Type)
            {
            case Move.Pass:
                sb.append("PASS");
                break;
            case Move.Attack:
            case Move.Strengthen:
                sb.append(BoardLocationToString(move.From));
                sb.append(BoardLocationToString(move.To));
                break;
            }
        }
        return sb.toString();
    }

    private static String BoardLocationToString(BoardLocation location)
    {
        char x = (char)('A' + location.X);
        char y = (char)('0' + location.Y);
        return new String(new char[] { x, y });
    }
}

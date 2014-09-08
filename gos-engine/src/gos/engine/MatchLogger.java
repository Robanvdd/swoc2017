package gos.engine;

import gos.engine.protocol.Move;

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
            case Pass:
                sb.append("PASS");
                break;
            case Attack:
            case Strengthen:
                sb.append(move.From.ToLabel());
                sb.append(move.To.ToLabel());
                break;
            }
        }
        return sb.toString();
    }
}

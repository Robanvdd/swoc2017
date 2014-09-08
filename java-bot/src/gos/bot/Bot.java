package gos.bot;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import gos.bot.protocol.Board;
import gos.bot.protocol.BoardLocation;
import gos.bot.protocol.InitiateRequest;
import gos.bot.protocol.Move;
import gos.bot.protocol.MoveRequest;
import gos.bot.protocol.MoveType;
import gos.bot.protocol.Player;
import gos.bot.protocol.ProcessedMove;

public class Bot implements IBot
{
    private Player myColor;
    
    public Bot()
    {
    }

    @Override
    public void HandleInitiate(InitiateRequest request)
    {
        myColor = request.Color;
    }

    @Override
    public Move HandleMove(MoveRequest request)
    {
        if ((request.AllowedMoves.length == 1) && request.AllowedMoves[0] == MoveType.Attack)
        {
            return GetRandomAttack(request.Board);   
        }
        else
        {
            return GetRandomMove(request.Board);
        }
    }

    @Override
    public void HandleProcessedMove(ProcessedMove move)
    {
        // Ignore what the other did
    }

    private static final Random random = new Random();

    private Move GetRandomMove(Board board)
    {
        List<BoardLocation> allLocations = AllLegalBoardLocations();
        List<BoardLocation> myLocations = new LinkedList<BoardLocation>();
        for (BoardLocation location : allLocations)
        {
            if (board.GetOwner(location) == myColor)
            {
                myLocations.add(location);
            }
        }

        BoardLocation fromLocation = myLocations.get(random.nextInt(myLocations.size()));

        List<BoardLocation> possibleToLocations = GetPossibleToLocations(board, fromLocation);

        BoardLocation toLocation = possibleToLocations.get(random.nextInt(possibleToLocations.size()));

        if (toLocation == fromLocation)
        {
            return new Move(MoveType.Pass, null, null);
        }
        else if (board.GetOwner(toLocation) != myColor)
        {
            return new Move(MoveType.Attack, fromLocation, toLocation);
        }
        else
        {
            return new Move(MoveType.Strengthen, fromLocation, toLocation);
        }
    }
    
    private Move GetRandomAttack(Board board)
    {
        Move move = GetRandomMove(board);
        while (move.Type != MoveType.Attack)
        {
            move = GetRandomMove(board);
        }
        return move;
    }

    private static List<BoardLocation> GetPossibleToLocations(Board board, BoardLocation fromLocation)
    {
        List<BoardLocation> possibleToLocations = new LinkedList<BoardLocation>();

        // always possible to pass
        possibleToLocations.add(fromLocation);

        BoardLocation north = GetFirstNonEmptyInDirection(board, fromLocation, 0, -1);
        if (north != null && IsValidMove(board, fromLocation, north)) possibleToLocations.add(north);

        BoardLocation south = GetFirstNonEmptyInDirection(board, fromLocation, 0, 1);
        if (south != null && IsValidMove(board, fromLocation, south)) possibleToLocations.add(south);

        BoardLocation east = GetFirstNonEmptyInDirection(board, fromLocation, 1, 0);
        if (east != null && IsValidMove(board, fromLocation, east)) possibleToLocations.add(east);

        BoardLocation west = GetFirstNonEmptyInDirection(board, fromLocation, -1, 0);
        if (west != null && IsValidMove(board, fromLocation, west)) possibleToLocations.add(west);

        BoardLocation northWest = GetFirstNonEmptyInDirection(board, fromLocation, -1, -1);
        if (northWest != null && IsValidMove(board, fromLocation, northWest)) possibleToLocations.add(northWest);

        BoardLocation southEast = GetFirstNonEmptyInDirection(board, fromLocation, 1, 1);
        if (southEast != null && IsValidMove(board, fromLocation, southEast)) possibleToLocations.add(southEast);
        return possibleToLocations;
    }

    private static BoardLocation GetFirstNonEmptyInDirection(Board board, BoardLocation location, int directionX, int directionY)
    {
        int x = location.X;
        int y = location.Y;

        do
        {
            x += directionX;
            y += directionY;
        } while (BoardLocation.IsLegal(x, y) && (board.GetOwner(new BoardLocation(x, y)) == Player.None));

        if (!BoardLocation.IsLegal(x, y))
        {
            return null;
        }

        BoardLocation newLocation = new BoardLocation(x, y);
        if (newLocation == location ||
            board.GetOwner(newLocation) == Player.None)
        {
            return null;
        }
        else
        {
            return newLocation;
        }
    }
    
    private static boolean IsValidMove(Board board, BoardLocation from, BoardLocation to)
    {
        Player fromOwner = board.GetOwner(from);
        Player toOwner = board.GetOwner(to);
        int fromHeight = board.GetHeight(from);
        int toHeight = board.GetHeight(to);
        return (fromOwner != Player.None) &&
            (toOwner != Player.None) &&
            (fromOwner == toOwner || fromHeight >= toHeight);
    }

    private static List<BoardLocation> AllLegalBoardLocations()
    {
        LinkedList<BoardLocation> locations = new LinkedList<BoardLocation>();
        for (int y = 0; y < 9; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                if (BoardLocation.IsLegal(x, y))
                {
                    locations.add(new BoardLocation(x, y));
                }
            }
        }
        return locations;
    }

}

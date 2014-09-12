using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SharpBot.Protocol;

namespace SharpBot
{
    class Bot : IBot
    {
        private Player myColor;

        public void HandleInitiate(Protocol.InitiateRequest request)
        {
            myColor = request.Color;
        }

        public Protocol.Move HandleMove(Protocol.MoveRequest request)
        {
            if ((request.AllowedMoves.Length == 1) && request.AllowedMoves[0] == MoveType.Attack)
            {
                return GetRandomAttack(request.Board);
            }
            else
            {
                return GetRandomMove(request.Board);
            }
        }

        public void HandleProcessedMove(Protocol.ProcessedMove move)
        {
            // Ignore what the other did
        }

        private static readonly Random random = new Random();

        private Move GetRandomMove(Board board)
        {
            var myLocations = AllLegalBoardLocations().Where(l => board.GetOwner(l) == myColor);

            var fromLocation = myLocations.ElementAt(random.Next(myLocations.Count()));

            var possibleToLocations = GetPossibleToLocations(board, fromLocation);

            var toLocation = possibleToLocations.ElementAt(random.Next(possibleToLocations.Count));

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
            List<BoardLocation> possibleToLocations = new List<BoardLocation>();

            // always possible to pass
            possibleToLocations.Add(fromLocation);

            BoardLocation north = GetFirstNonEmptyInDirection(board, fromLocation, 0, -1);
            if (north != null && IsValidMove(board, fromLocation, north)) possibleToLocations.Add(north);

            BoardLocation south = GetFirstNonEmptyInDirection(board, fromLocation, 0, 1);
            if (south != null && IsValidMove(board, fromLocation, south)) possibleToLocations.Add(south);

            BoardLocation east = GetFirstNonEmptyInDirection(board, fromLocation, 1, 0);
            if (east != null && IsValidMove(board, fromLocation, east)) possibleToLocations.Add(east);

            BoardLocation west = GetFirstNonEmptyInDirection(board, fromLocation, -1, 0);
            if (west != null && IsValidMove(board, fromLocation, west)) possibleToLocations.Add(west);

            BoardLocation northWest = GetFirstNonEmptyInDirection(board, fromLocation, -1, -1);
            if (northWest != null && IsValidMove(board, fromLocation, northWest)) possibleToLocations.Add(northWest);

            BoardLocation southEast = GetFirstNonEmptyInDirection(board, fromLocation, 1, 1);
            if (southEast != null && IsValidMove(board, fromLocation, southEast)) possibleToLocations.Add(southEast);
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

        private static bool IsValidMove(Board board, BoardLocation from, BoardLocation to)
        {
            Player fromOwner = board.GetOwner(from);
            Player toOwner = board.GetOwner(to);
            int fromHeight = board.GetHeight(from);
            int toHeight = board.GetHeight(to);
            return (fromOwner != Player.None) &&
                (toOwner != Player.None) &&
                (fromOwner == toOwner || fromHeight >= toHeight);
        }

        private static IEnumerable<BoardLocation> AllLegalBoardLocations()
        {
            for (int y = 0; y < 9; y++)
            {
                for (int x = 0; x < 9; x++)
                {
                    if (BoardLocation.IsLegal(x, y))
                    {
                        yield return new BoardLocation(x, y);
                    }
                }
            }
        }

    }
}

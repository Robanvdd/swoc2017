using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;
using System.Diagnostics;

namespace SharpBot
{
    class Program
    {
        private static Player myColor;

        static void Main(string[] args)
        {
            {
                InitiateRequest initRequest = ReadMessage<InitiateRequest>();

                myColor = initRequest.Color;
                Debug.Assert(myColor != Player.None);
            }

            if (myColor == Player.White)
            {
                MoveRequest moveRequest = ReadMessage<MoveRequest>();
                WriteMessage(GetRandomAttack(moveRequest.Board));

                ProcessedMove processedMove = ReadMessage<ProcessedMove>();
                Debug.WriteLine(processedMove.ToString());

                // Wait for first two moves of black
                ProcessedMove processedMoveB1 = ReadMessage<ProcessedMove>();
                Debug.WriteLine(processedMoveB1.ToString());
                ProcessedMove processedMoveB2 = ReadMessage<ProcessedMove>();
                Debug.WriteLine(processedMoveB1.ToString());
            }
            else
            {
                // Wait for first white move
                ProcessedMove processedMoveW = ReadMessage<ProcessedMove>();
                Debug.WriteLine(processedMoveW.ToString());
            }

            while (true)
            {
                MoveRequest firstMoveRequest = ReadMessage<MoveRequest>();
                WriteMessage(GetRandomAttack(firstMoveRequest.Board));
                ProcessedMove firstProcessedMove = ReadMessage<ProcessedMove>();
                Debug.WriteLine(firstProcessedMove.ToString());
                if (firstProcessedMove.Winner != Player.None)
                {
                    break;
                }

                MoveRequest secondMoveRequest = ReadMessage<MoveRequest>();
                WriteMessage(GetRandomMove(secondMoveRequest.Board));
                ProcessedMove secondProcessedMove = ReadMessage<ProcessedMove>();
                Debug.WriteLine(secondProcessedMove.ToString());
                if (secondProcessedMove.Winner != Player.None)
                {
                    break;
                }

                ProcessedMove moveOther1 = ReadMessage<ProcessedMove>();
                Debug.WriteLine(moveOther1.ToString());
                if (moveOther1.Winner != Player.None)
                {
                    break;
                }

                ProcessedMove moveOther2 = ReadMessage<ProcessedMove>();
                Debug.WriteLine(moveOther2.ToString());
                if (moveOther2.Winner != Player.None)
                {
                    break;
                }
            }
        }

        private static Random random = new Random();

        private static Move GetRandomMove(Board board)
        {
            // find all locations with my pieces
            var myLocations = AllLegalBoardLocations().Where(l => board.GetOwner(l) == myColor);

            var fromLocation = myLocations.ElementAt(random.Next(myLocations.Count()));

            var possibleToLocations = GetNeighbors(board, fromLocation);

            var toLocation = possibleToLocations.ElementAt(random.Next(possibleToLocations.Count));

            if (toLocation == null)
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

        private static Move GetRandomAttack(Board board)
        {
            Move move = GetRandomMove(board);
            while (move.Type != MoveType.Attack)
            {
                move = GetRandomMove(board);
            }
            return move;
        }

        private static List<BoardLocation> GetNeighbors(Board board, BoardLocation fromLocation)
        {
            List<BoardLocation> possibleToLocations = new List<BoardLocation>();
            var north = GetFirstNonEmptyInDirection(board, fromLocation, 0, -1);
            if (north != null) possibleToLocations.Add(north);

            var south = GetFirstNonEmptyInDirection(board, fromLocation, 0, 1);
            if (south != null) possibleToLocations.Add(south);

            var east = GetFirstNonEmptyInDirection(board, fromLocation, 1, 0);
            if (east != null) possibleToLocations.Add(east);

            var west = GetFirstNonEmptyInDirection(board, fromLocation, -1, 0);
            if (west != null) possibleToLocations.Add(west);

            var northWest = GetFirstNonEmptyInDirection(board, fromLocation, -1, -1);
            if (northWest != null) possibleToLocations.Add(northWest);

            var southEast = GetFirstNonEmptyInDirection(board, fromLocation, 1, 1);
            if (southEast != null) possibleToLocations.Add(southEast);
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

        private static T ReadMessage<T>()
        {
            string line = Console.In.ReadLine();
            if (string.IsNullOrEmpty(line))
            {
                return default(T);
            }

            return JsonConvert.DeserializeObject<T>(line);
        }

        private static void WriteMessage<T>(T message)
        {
            Console.Out.WriteLine(JsonConvert.SerializeObject(message));
        }
    }
}

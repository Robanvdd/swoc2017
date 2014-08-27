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
                StatusResponse status = new StatusResponse(true);
                WriteMessage(status);

                myColor = initRequest.Color;
                Debug.Assert(myColor != Player.None);
            }

            if (myColor == Player.White)
            {
                MoveRequest moveRequest = ReadMessage<MoveRequest>();
                Move move = new Move(MoveType.Attack, new BoardLocation(1, 0), new BoardLocation(0, 0));
                WriteMessage(move);

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
                Move firstMove = new Move(MoveType.Attack, new BoardLocation(0, 0), new BoardLocation(0, 2));
                WriteMessage(firstMove);
                ProcessedMove firstProcessedMove = ReadMessage<ProcessedMove>();
                Debug.WriteLine(firstProcessedMove.ToString());
                if (firstProcessedMove.Winner != Player.None)
                {
                    break;
                }

                MoveRequest secondMoveRequest = ReadMessage<MoveRequest>();
                Move secondMove = new Move(MoveType.Pass, null, null);
                WriteMessage(secondMove);
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

            

            return null;
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

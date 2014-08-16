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
        static void Main(string[] args)
        {
            //while (true)
            {
                {
                    InitiateRequest initRequest = ReadMessage<InitiateRequest>();
                    StatusResponse status = new StatusResponse(true);
                    WriteMessage(status);
                }

                {
                    MoveRequest moveRequest = ReadMessage<MoveRequest>();
                    Move move = new Move(MoveType.Attack, new BoardLocation(0, 0), new BoardLocation(1, 0));
                    WriteMessage(move);
                    StatusResponse status = ReadMessage<StatusResponse>();

                    Debug.WriteLine("Move was {0}", status.Ok ? "Ok" : "NOT Ok");
                }

                {
                    MoveRequest firstMoveRequest = ReadMessage<MoveRequest>();
                    Move firstMove = new Move(MoveType.Attack, new BoardLocation(1, 0), new BoardLocation(2, 0));
                    WriteMessage(firstMove);
                    StatusResponse firstStatus = ReadMessage<StatusResponse>();

                    Debug.WriteLine("First move was {0}", firstStatus.Ok ? "Ok" : "NOT Ok");

                    MoveRequest secondMoveRequest = ReadMessage<MoveRequest>();
                    Move secondMove = new Move(MoveType.Pass, null, null);
                    WriteMessage(secondMove);
                    StatusResponse secondStatus = ReadMessage<StatusResponse>();

                    Debug.WriteLine("Second move was {0}", secondStatus.Ok ? "Ok" : "NOT Ok");
                }

                //int[][] boardState = JsonConvert.DeserializeObject<int[][]>(line);

                //Board board = new Board(boardState);

                //var loc = new BoardLocation(2, 1);

                //Console.WriteLine("{0} is owned by {1} with stone {2} of {3} height", loc, board.GetOwner(loc), board.GetStone(loc), board.GetHeight(loc));
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

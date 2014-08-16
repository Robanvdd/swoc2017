using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;

namespace SharpBot
{
    class Program
    {
        static void Main(string[] args)
        {
            while (true)
            {
                string line = Console.In.ReadLine();
                if (string.IsNullOrEmpty(line))
                {
                    break;
                }

                int[][] boardState = JsonConvert.DeserializeObject<int[][]>(line);

                Board board = new Board(boardState);
                
                var loc = new BoardLocation(0, 0);

                Console.WriteLine("(0, 0) is owned by {0} with stone {1} of {2} height", board.GetOwner(loc), board.GetStone(loc), board.GetHeight(loc));
            }
        }
    }
}

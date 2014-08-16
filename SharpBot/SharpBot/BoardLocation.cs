using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SharpBot
{
    class BoardLocation
    {
        public BoardLocation(int x, int y)
        {
            if (x < 0 || x >= 9 || y < 0 || y >= 9)
            {
                throw new ArgumentOutOfRangeException("not a valid board location");
            }

            X = x;
            Y = y;
        }

        public readonly int X;
        public readonly int Y;
    }
}

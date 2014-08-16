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
                throw new ArgumentOutOfRangeException("location of the board");
            }

            if ((x - y) >= 5 || (y - x) >= 5)
            {
                throw new ArgumentException("location not legal");
            }

            X = x;
            Y = y;
        }

        public readonly int X;
        public readonly int Y;

        public override string ToString()
        {
            return "(" + X.ToString() + ", " + Y.ToString() + ")";
        }
    }
}

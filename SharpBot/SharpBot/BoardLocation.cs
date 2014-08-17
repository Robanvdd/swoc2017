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
            if (!IsLegal(x, y))
            {
                throw new ArgumentException("not a legal board location");
            }

            X = x;
            Y = y;
        }

        public readonly int X;
        public readonly int Y;

        public static bool IsLegal(int x, int y)
        {
            return x >= 0 && x < 9 && y >= 0 && y < 9 &&
                    (x - y) < 5 && (y - x) < 5 && (x != 4 || y != 4);
        }

        public override string ToString()
        {
            return "(" + X.ToString() + ", " + Y.ToString() + ")";
        }
    }
}

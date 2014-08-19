using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;

namespace SharpBot
{
    public enum Player : int
    {
        None = 0,
        White = 1,
        Black = -1,
    }
    public enum Stone : int
    {
        None = 0,
        A = 1,
        B = 2,
        C = 3,
    }
    public class Board
    {
        private readonly int[][] state;

        private static readonly int Empty = GetCode(Player.None, Stone.None, 0);

        private static readonly int BlackA = GetCode(Player.Black, Stone.A, 1);
        private static readonly int BlackB = GetCode(Player.Black, Stone.B, 1);
        private static readonly int BlackC = GetCode(Player.Black, Stone.C, 1);
        private static readonly int WhiteA = GetCode(Player.White, Stone.A, 1);
        private static readonly int WhiteB = GetCode(Player.White, Stone.B, 1);
        private static readonly int WhiteC = GetCode(Player.White, Stone.C, 1);

        public Board(int[][] initialState)
        {
            state = initialState;
        }

        public Player GetOwner(BoardLocation location)
        {
            return GetOwner(state[location.Y][location.X]);
        }

        public Stone GetStone(BoardLocation location)
        {
            return GetStone(state[location.Y][location.X]);
        }

        public int GetHeight(BoardLocation location)
        {
            return GetHeight(state[location.Y][location.X]);
        }

        private static int GetCode(Player owner, Stone stone, int height)
        {
            Debug.Assert(-1 <= (int)owner && (int)owner <= 1);
            Debug.Assert(0 <= (int)stone && (int)stone <= 3);
            Debug.Assert((owner != Player.None && stone != Stone.None && height > 0) ||
                (owner == Player.None && stone == Stone.None && height == 0));
            return (int)owner * (height * 4 + (int)stone);
        }

        private static Player GetOwner(int fieldCode)
        {
            if (fieldCode == 0)
            {
                return Player.None;
            }
            else if (fieldCode > 0)
            {
                return Player.White;
            }
            else
            {
                return Player.Black;
            }
        }

        private static Stone GetStone(int fieldCode)
        {
            return (Stone)(Math.Abs(fieldCode) % 4);
        }

        private static int GetHeight(int fieldCode)
        {
            return Math.Abs(fieldCode) / 4;
        }

        public void SetSpace(BoardLocation location, Player owner, Stone stone, int height)
        {
            if (owner == Player.None)
            {
                throw new ArgumentException("owner not specified");
            }
            if (stone == Stone.None)
            {
                throw new ArgumentException("stone not specified");
            }
            if (height <= 0)
            {
                throw new ArgumentException("height not specified");
            }

            state[location.Y][location.X] = GetCode(owner, stone, height);
        }

        public void ClearSpace(BoardLocation location)
        {
            state[location.Y][location.X] = Empty;
        }

        public int GetTotalCount(Player player, Stone stone)
        {
            int count = 0;

            for (int y = 0; y < 9; y++)
            {
                for (int x = 0; x < 9; x++)
                {
                    int code = state[y][x];
                    if (GetOwner(code) == player && GetStone(code) == stone)
                    {
                        count++;
                    }
                }
            }
            return count;
        }
    }
}

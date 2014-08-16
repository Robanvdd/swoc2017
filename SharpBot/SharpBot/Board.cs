using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;

namespace SharpBot
{
    enum Owner : int
    {
        None = 0,
        White = 1,
        Black = -1,
    }
    enum Stone : int
    {
        None = 0,
        A = 1,
        B = 2,
        C = 3,
    }
    class Board
    {
        public static readonly int Illegal = GetCode(Owner.None, Stone.None, 100);

        private readonly int[][] state;

        private static readonly int BlackA = GetCode(Owner.Black, Stone.A, 1);
        private static readonly int BlackB = GetCode(Owner.Black, Stone.B, 1);
        private static readonly int BlackC = GetCode(Owner.Black, Stone.C, 1);
        private static readonly int WhiteA = GetCode(Owner.White, Stone.A, 1);
        private static readonly int WhiteB = GetCode(Owner.White, Stone.B, 1);
        private static readonly int WhiteC = GetCode(Owner.White, Stone.C, 1);

        public Board(int[][] initialState)
        {
            state = initialState;
        }

        public bool IsIllegal(BoardLocation location)
        {
            return state[location.Y][location.X] == Illegal;
        }

        public Owner GetOwner(BoardLocation location)
        {
            if (IsIllegal(location))
            {
                return Owner.None;
            }
            return GetOwner(state[location.Y][location.X]);
        }

        public Stone GetStone(BoardLocation location)
        {
            if (IsIllegal(location))
            {
                return Stone.None;
            }
            return GetStone(state[location.Y][location.X]);
        }

        public int GetHeight(BoardLocation location)
        {
            if (IsIllegal(location))
            {
                return 0;
            }
            return GetHeight(state[location.Y][location.X]);
        }

        private static int GetCode(Owner owner, Stone stone, int height)
        {
            Debug.Assert(-1 <= (int)owner && (int)owner <= 1);
            Debug.Assert(0 <= (int)stone && (int)stone <= 3);
            Debug.Assert((owner != Owner.None && stone != Stone.None && height > 0) ||
                (owner == Owner.None && stone == Stone.None && (height == 0 || height == 100)));
            return (int)owner * (height * 4 + (int)stone);
        }

        private static Owner GetOwner(int fieldCode)
        {
            if (fieldCode == 0)
            {
                return Owner.None;
            }
            else if (fieldCode > 0)
            {
                return Owner.White;
            }
            else
            {
                return Owner.Black;
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

        public Board ChangeState(BoardLocation location, Owner owner, Stone stone, int height)
        {
            // TODO: check for legal locations

            int[][] newState = new int[9][];

            for (int y = 0; y < 9; y++)
            {
                newState[y] = new int[9];
                for (int x = 0; x < 9; x++)
                {
                    if (x == location.X && y == location.Y)
                    {
                        newState[y][x] = GetCode(owner, stone, height);
                    }
                    else
                    {
                        newState[y][x] = state[y][x];
                    }
                }
            }

            return new Board(newState);
        }

        public int GetTotalCount(Owner owner, Stone stone)
        {
            int count = 0;

            for (int y = 0; y < 9; y++)
            {
                for (int x = 0; x < 9; x++)
                {
                    int code = state[y][x];
                    if (GetOwner(code) == owner && GetStone(code) == stone)
                    {
                        count++;
                    }
                }
            }
            return count;
        }

    }
}

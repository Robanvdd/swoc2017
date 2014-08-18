using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SharpBot
{
    class MoveRequest
    {
        public MoveRequest(Board board, MoveType[] allowedMoves)
        {
            this.board = board;
            this.allowedMoves = allowedMoves;
        }

        private readonly Board board;
        public Board Board { get { return board; } }

        private readonly MoveType[] allowedMoves;
        public MoveType[] AllowedMoves { get { return allowedMoves; } }
    }
}

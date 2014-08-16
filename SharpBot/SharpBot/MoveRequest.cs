using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SharpBot
{
    class MoveRequest
    {
        public MoveRequest(Board board, MoveType[] allowedTypes)
        {
            this.board = board;
            this.allowedTypes = allowedTypes;
        }

        private readonly Board board;
        public Board Board { get { return board; } }

        private readonly MoveType[] allowedTypes;
        public MoveType[] AllowedTypes { get { return allowedTypes; } }
    }
}

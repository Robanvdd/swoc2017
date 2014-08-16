using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SharpBot
{
    enum MoveType : int
    {
        Pass = 0,
        Attack = 1,
        Strengthen = 2,
    };

    class Move
    {
        public Move(MoveType type, BoardLocation from, BoardLocation to)
        {
            this.type = type;
            this.from = from;
            this.to = to;
        }

        private readonly MoveType type;
        public MoveType Type { get { return type; } }

        private readonly BoardLocation from;
        public BoardLocation From { get { return from; } }

        private readonly BoardLocation to;
        public BoardLocation To { get { return to; } }
    }
}

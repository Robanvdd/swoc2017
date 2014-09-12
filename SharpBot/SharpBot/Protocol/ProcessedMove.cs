using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SharpBot.Protocol
{
    public class ProcessedMove
    {
        public ProcessedMove(Player player, Move move, Player winner)
        {
            this.player = player;
            this.move = move;
            this.winner = winner;
        }

        private readonly Player player;
        public Player Player { get { return player; } }

        private readonly Move move;
        public Move Move { get { return move; } }
        
        private readonly Player winner;
        public Player Winner { get { return winner; } }

        public override string ToString()
        {
            return string.Format("{0}: {1} from {2} to {3}, winner is {4}", Player, Move.Type, Move.From, Move.To, Winner);
        }
    }
}
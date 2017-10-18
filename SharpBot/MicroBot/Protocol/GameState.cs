using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MicroBot.Protocol
{
    public sealed class GameState
    {
        public Arena Arena { get; set; }

        public List<Player> Players { get; set; }

        public List<Projectile> Projectiles { get; set; }

        public int PlayerId { get; set; }

        public string PlayerName { get; set; }
    }
}

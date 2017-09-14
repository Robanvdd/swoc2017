using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MicroBot.Protocol
{
    public sealed class Player
    {
        public string Name { get; set; }
        public List<Bot> Bots { get; set; }

        public override string ToString()
        {
            return String.Format("Player name: {0}\n{1}", Name, String.Join("\n", Bots));
        }
    }
}

using Newtonsoft.Json;
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

        public override string ToString()
        {
            return String.Format("{0}\n{1}\n{2}\n", Arena, String.Join("\n", Players), String.Join("\n", Projectiles));
        }

        public string ToJson()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }
    }
}

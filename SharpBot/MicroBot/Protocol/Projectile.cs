using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;
using Bot.Protocol;

namespace MicroBot.Protocol
{
    public class Projectile
    { 
        [JsonConverter(typeof(PositionConverter))]
        public Position Position { get; set; }
        public float Direction { get; set; }

        public override string ToString()
        {
            return String.Format("Projectile: \nPosition: {0}\nDirection: {1}", Position, Direction);
        }
    }
}

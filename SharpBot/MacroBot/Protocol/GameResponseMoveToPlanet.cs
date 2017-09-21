using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MacroBot.Protocol
{
    public sealed class GameResponseMoveToPlanet
    {
        public string Command { get { return "moveToPlanet"; } }
        public List<int> Ufos { get; set; }
        public int PlanetId { get; set; }

        public string ToJson(Formatting formatting = Formatting.None)
        {
            return JsonConvert.SerializeObject(this, formatting);
        }
    }
}

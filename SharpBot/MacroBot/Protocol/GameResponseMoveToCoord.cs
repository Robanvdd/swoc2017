using Bot.Protocol;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MacroBot.Protocol
{
    public sealed class GameResponseMoveToCoord
    {
        public string Command { get { return "moveToCoord"; } }
        public List<int> Ufos { get; set; }
        public Position Coord { get; set; }

        public string ToJson(Formatting formatting = Formatting.None)
        {
            return JsonConvert.SerializeObject(this, formatting);
        }
    }
}

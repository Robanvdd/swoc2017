using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MicroBot.Protocol
{
    public sealed class GameResponse
    {
        public GameResponse()
        {
            Bots = new List<BotAction>();
        }

        public List<BotAction> Bots { get; set; }

        public string ToJson()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }
    }
}

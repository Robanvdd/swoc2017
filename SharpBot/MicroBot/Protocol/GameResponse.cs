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
            Commands = new List<BotAction>();
        }

        public List<BotAction> Commands { get; set; }
    }
}

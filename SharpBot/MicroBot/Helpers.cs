using MicroBot.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MicroBot
{
    public static class Helpers
    {
        public static Player GetPlayerByName(IEnumerable<Player> players, string name)
        {
            return players.Single(player => player.Name == name);
        }

        public static IEnumerable<Protocol.Bot> GetUfosWithHitPoints(IEnumerable<Protocol.Bot> ufos)
        {
            return ufos.Where(b => b.Hitpoints > 0);
        }

        public static IEnumerable<Protocol.Bot> GetOtherUfos(GameState gameState, string playerName)
        {
            return gameState.Players.Where(p => p.Name != playerName).SelectMany(p => p.Bots);
        }
    }
}

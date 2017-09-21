using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MacroBot.Protocol;

namespace MacroBot
{
    public sealed class MacroEngine : Engine
    {
        public override void Response(GameState gameState)
        {
            var mePlayer = gameState.Players.Single(player => player.Name == "dwight");
            var planet0 = gameState.SolarSystems.SelectMany(sys => sys.Planets).First();

            WriteMessage(new Protocol.GameResponseBuy
            {
                Amount = 1,
                PlanetId = planet0.Id,
            });

            var ufo = mePlayer.Ufos.First();
            WriteMessage(new Protocol.GameResponseMoveToPlanet
            {
                PlanetId = planet0.Id,
                Ufos = new List<int> { ufo.Id },
            });
        }
    }
}

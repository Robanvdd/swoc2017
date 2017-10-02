using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MacroBot.Protocol;

namespace MacroBot
{
    public sealed class MacroEngine : Engine<Protocol.GameState>
    {
        public override void Response(GameState gameState)
        {
            //var mePlayer = gameState.Players.Single(player => player.Name == "dwight");
            //var planet0 = gameState.SolarSystems.SelectMany(sys => sys.Planets).First();

            WriteMessage(new Protocol.GameResponseBuy
            {
                Amount =  2,
                PlanetId = 42,
            });

            //var ufo = mePlayer.Ufos.First();
            WriteMessage(new Protocol.GameResponseMoveToPlanet
            {
                PlanetId = 43,
                Ufos = new List<int> { 24 },
            });

            WriteMessage(new Protocol.GameResponseMoveToCoord
            {
                Coord = new Bot.Protocol.Position { X = 42, Y = 24 },
                Ufos = new List<int> { 12 },
            });
        }
    }
}

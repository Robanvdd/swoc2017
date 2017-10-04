using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MacroBot.Protocol;

namespace MacroBot
{
    public sealed class MacroEngine : Engine<Protocol.GameState>
    {
        //private const string playerId = "dwight";
        private const string playerId = "jesper";

        private bool movingToPlanet = false;
        private bool atPlanet = false;
        private bool conquering = false;

        public override void Response(List<GameState> gameStates)
        {
            var gameState = gameStates.Last();
            var mePlayer = gameState.Players.Single(player => player.Name == playerId);
            var solarSystem0 = gameState.SolarSystems.First();
            var planet0 = solarSystem0.Planets.First();

            var ufo = mePlayer.Ufos.First();

            if (!movingToPlanet)
            {
                WriteMessage(new Protocol.GameResponseMoveToPlanet
                {
                    PlanetId = planet0.Id,
                    Ufos = new List<int> { ufo.Id, },
                });

                movingToPlanet = true;
                return;
            }

            if (!atPlanet)
            {
                var planetCoord = PolarCoord.AsCartesianCoord(planet0.OrbitDistance, planet0.OrbitRotation, PolarCoord.AngleType.Degree);
                var ufoCc = new CartesianCoord(ufo.Coord.X, ufo.Coord.Y);
                var planetCc = new CartesianCoord(solarSystem0.Coords.X + planetCoord.X, solarSystem0.Coords.Y + planetCoord.Y);

                if ((planetCc - ufoCc).LengthSquared() < 16 * 16)
                    atPlanet = true;
            }

            if (atPlanet && !conquering)
            {
                WriteMessage(new Protocol.GameResponseConquer
                {
                    PlanetId = planet0.Id,
                });
                conquering = true;
            }
        }
    }
}

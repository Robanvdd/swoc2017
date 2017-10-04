using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MacroBot.Protocol;

namespace MacroBot
{
    public sealed class MacroEngine : Engine<Protocol.GameState>
    {
        private const string playerId = "dwight";
        //private const string playerId = "jesper";

        private bool movingToPlanet = false;
        private bool atPlanet = false;
        private bool conquering = false;

        public override void Response(List<GameState> gameStates)
        {
            var gameState = gameStates.Last();
            var mePlayer = gameState.Players.Single(player => player.Name == playerId);
            var solarSystem0 = gameState.SolarSystems[0];
            var solarSystem1 = gameState.SolarSystems[1];
            var solarSystem2 = gameState.SolarSystems[2];
            var planet0 = solarSystem0.Planets[5];
            var planet1 = solarSystem1.Planets[5];
            var planet2 = solarSystem2.Planets[5];

            var ufo0 = mePlayer.Ufos[0];
            var ufo1 = mePlayer.Ufos[1];
            var ufo2 = mePlayer.Ufos[2];

            if (!movingToPlanet)
            {
                WriteMessage(new Protocol.GameResponseMoveToPlanet
                {
                    PlanetId = planet0.Id,
                    Ufos = new List<int> { ufo0.Id, },
                });

                WriteMessage(new Protocol.GameResponseMoveToPlanet
                {
                    PlanetId = planet1.Id,
                    Ufos = new List<int> { ufo1.Id, },
                });

                WriteMessage(new Protocol.GameResponseMoveToPlanet
                {
                    PlanetId = planet2.Id,
                    Ufos = new List<int> { ufo2.Id, },
                });

                movingToPlanet = true;
                return;
            }

            if (!atPlanet)
            {
                var planetCoord = PolarCoord.AsCartesianCoord(planet0.OrbitDistance, planet0.OrbitRotation, PolarCoord.AngleType.Degree);
                var ufoCc = new CartesianCoord(ufo0.Coord.X, ufo0.Coord.Y);
                var planetCc = new CartesianCoord(solarSystem0.Coords.X + planetCoord.X, solarSystem0.Coords.Y + planetCoord.Y);

                if ((planetCc - ufoCc).LengthSquared() < 256 * 256)
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

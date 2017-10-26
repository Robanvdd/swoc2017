using System.Collections.Generic;
using MicroBot.Protocol;
using System.Linq;
using static System.Math;
using System;
using static MicroBot.Helpers;

namespace MicroBot
{
    public sealed class MicroEngine : Swoc.Engine<Protocol.GameState>
    {

        public override void Response(List<GameState> gameStates)
        {
            try
            {
                DoResponse(gameStates);
            }
            catch (Exception ex)
            {
                Console.Error.WriteLine("Response failure: " + ex.Message);
            }
        }

        private double time = 0;
        private void DoResponse(List<GameState> gameStates)
        {
            var gameState = gameStates.Last();
            var playerName = gameState.PlayerName;
            var mePlayer = GetPlayerByName(gameState.Players, playerName);
            time += 0.1;

            var ufos = mePlayer.Ufos;
            var targetUfo = GetUfosWithHitPoints(GetOtherUfos(gameState, playerName)).FirstOrDefault();

            if (targetUfo == default(Protocol.Ufo))
                return;

            foreach (var ufo in ufos)
            {
                WriteMessage(new GameResponse
                {
                    Commands = new List<UfoAction>
                    {
                        new UfoAction
                        {
                            Id = ufo.Id,
                            Move = new Move { Direction = Sin(time * 2) * 70, Speed = Sin(time * 0.2) * 4 },
                            ShootAt = new ShootAt { X = targetUfo.Position.X, Y = targetUfo.Position.Y },
                        }
                    },
                });
            }
        }


    }
}
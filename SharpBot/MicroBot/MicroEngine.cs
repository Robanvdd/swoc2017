using System.Collections.Generic;
using MicroBot.Protocol;

namespace MicroBot
{
    public sealed class MicroEngine : Swoc.Engine<Protocol.GameState>
    {
        public override void Response(List<GameState> gameStates)
        {
        }
    }
}
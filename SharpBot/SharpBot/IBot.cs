using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SharpBot.Protocol;

namespace SharpBot
{
    public interface IBot
    {
        void HandleInitiate(InitiateRequest request);
        Move HandleMove(MoveRequest request);
        void HandleProcessedMove(ProcessedMove move);
    }
}

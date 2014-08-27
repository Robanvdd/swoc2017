using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SharpBot
{
    class InitiateRequest
    {
        public InitiateRequest(Player color)
        {
            this.color = color;
        }

        private readonly Player color;
        public Player Color { get { return color; } }
    }
}

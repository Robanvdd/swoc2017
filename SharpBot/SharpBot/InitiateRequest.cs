using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SharpBot
{
    class InitiateRequest
    {
        public InitiateRequest(Owner color)
        {
            this.color = color;
        }

        private readonly Owner color;
        public Owner Color { get { return color; } }
    }
}

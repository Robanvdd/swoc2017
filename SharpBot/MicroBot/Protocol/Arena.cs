using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MicroBot.Protocol
{
    public sealed class Arena
    {
        public int Height { get; set; }
        public int Width { get; set; }

        public override string ToString()
        {
            return String.Format("Arena: {0}x{1} (width x height)", Width, Height);
        }
    }
}

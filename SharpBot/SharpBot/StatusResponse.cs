using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SharpBot
{
    class StatusResponse
    {
        public StatusResponse(bool ok)
        {
            this.ok = ok;
        }

        private readonly bool ok;
        public bool Ok { get { return ok; } }
    }
}

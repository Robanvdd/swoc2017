using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SharpBot
{
    public class InvalidMessageException : Exception
    {
        public InvalidMessageException(String message) :
            base(message)
        {
        }
    }
}

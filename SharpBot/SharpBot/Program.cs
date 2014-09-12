using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;

namespace SharpBot
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                Bot bot = new Bot();
                Engine engine = new Engine(bot);
                engine.run();
            }
            catch (Exception e)
            {
                Console.Error.WriteLine(e.StackTrace);
            }
        }
    }
}

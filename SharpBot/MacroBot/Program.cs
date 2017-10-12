using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MacroBot
{
    class Program
    {
        static void Main(string[] args)
        {
            //System.Windows.Forms.MessageBox.Show("Attach debugger");
            var engine = new MacroEngine();
            engine.Run();
        }
    }
}

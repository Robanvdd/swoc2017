using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MacroBot
{
    public abstract class Engine
    {
        public void Run()
        {
            mStop = false;
            while (!mStop)
            {
                var gameState = ReadMessage<Protocol.GameState>();
                Response(gameState);
            }
        }

        public abstract void Response(Protocol.GameState gameState);

        private bool mStop;
        public void Stop()
        {
            mStop = true;
        }

        public static T ReadMessage<T>()
        {
            string line = Console.In.ReadLine();
            if (String.IsNullOrEmpty(line))
                return default(T);

            return JsonConvert.DeserializeObject<T>(line);
        }

        public static void WriteMessage<T>(T message)
        {
            Console.Out.WriteLine(JsonConvert.SerializeObject(message));
        }
    }
}

using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace MacroBot
{
    public abstract class Engine<GameStateTemplate> where GameStateTemplate : class
    {
        private readonly Thread readThread;
        private readonly List<GameStateTemplate> gameStates = new List<GameStateTemplate>();

        public Engine()
        {
            readThread = new Thread(new ThreadStart(PollState));
        }

        public void Run()
        {
            mStop = false;
            readThread.Start();
            while (!mStop)
            {
                var states = GameStates();
                if (states.Count > 0)
                {
                    Response(states);
                }
            }
        }

        private void PollState()
        {
            while (!mStop)
            {
                var state = ReadMessage<GameStateTemplate>();
                lock (gameStates)
                {
                    gameStates.Add(state);
                }
            }
        }

        private List<GameStateTemplate> GameStates()
        {
            lock (gameStates)
            {
                var states = gameStates.ToList();
                gameStates.Clear();
                return states;
            }
        }

        public abstract void Response(List<GameStateTemplate> gameStates);

        private bool mStop;
        public void Stop()
        {
            mStop = true;
            readThread.Join();
        }

        private static T ReadMessage<T>()
        {
            string line = Console.ReadLine();
            if (String.IsNullOrEmpty(line))
                return default(T);

            try
            {
                return JsonConvert.DeserializeObject<T>(line);
            }
            catch (Exception)
            {
                return default(T);
            }
        }

        public static void WriteMessage<T>(T message)
        {
            Console.WriteLine(JsonConvert.SerializeObject(message, Formatting.None));
        }
    }
}

using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using static SwocIO.Pipeline;

namespace MacroBot
{
    public abstract class Engine<GameStateTemplate> where GameStateTemplate : class
    {
        public void Run()
        {
            mStop = false;
            while (!mStop)
            {
                GameStateTemplate gameState = ReadMessage<GameStateTemplate>();
                Response(gameState);
            }
        }

        public abstract void Response(GameStateTemplate gameState);

        private bool mStop;
        public void Stop()
        {
            mStop = true;
        }

        public static T ReadMessage<T>()
        {
            var line = ReadLine();
            if (String.IsNullOrEmpty(line))
            {
                System.Windows.Forms.MessageBox.Show("Null message read");
                return default(T);
            }

            try
            {
                return JsonConvert.DeserializeObject<T>(line);
            }
            catch (Exception ex)
            {
                System.Windows.Forms.MessageBox.Show("Failed to deserialize " + line);
                System.Windows.Forms.MessageBox.Show("Failed to deserialize " + ex.Message);
                return default(T);
            }
        }

        public static void WriteMessage<T>(T message)
        {
            WriteLine(JsonConvert.SerializeObject(message, Formatting.None));
        }
    }
}

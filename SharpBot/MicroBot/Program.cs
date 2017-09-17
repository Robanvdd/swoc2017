using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MicroBot
{
    class Program
    {
        static void Main(string[] args)
        {
            #region JSON input example
            string json = @"
{
    'arena': {
        'height': '1000',
        'width': '1000'
    },
    'players': [
        {
            'name': 'player0',
            'bots': [
                {
                    'name': 'bot0',
                    'hitpoints': '20',
                    'position': '25,25'
                },
                {
                    'name': 'bot1',
                    'hitpoints': '10',
                    'position': '25,50'
                }
            ]
        },
        {
            'name': 'player1',
            'bots': [
                {
                    'name': 'bot0',
                    'hitpoints': '15',
                    'position': '75,75'
                },
                {
                    'name': 'bot1',
                    'hitpoints': '15',
                    'position': '50,75'
                }
            ]
        }
    ],
    'projectiles': [
        {
            'position': '23,34',
            'direction': '32',
        },
        {
            'position':'15,17',
            'direction': '32'
        }
    ]
}";
            #endregion

            var gameState = JsonConvert.DeserializeObject<Protocol.GameState>(json);
            Console.WriteLine("GameState\n" + gameState.ToJson() + "\n");

            var gameReponse = new Protocol.GameResponse();
            gameReponse.Bots.Add(new Protocol.BotAction
            {
                Name = "bot1",
                Movement = new Protocol.Movement { Direction = 180, Speed = 1 },
                Shoot = new Protocol.Shoot { Direction = 200 },
            });
            gameReponse.Bots.Add(new Protocol.BotAction
            {
                Name = "bot2",
                Movement = new Protocol.Movement { Direction = 52.2f, Speed = 1.2f },
                Shoot = new Protocol.Shoot { Direction = 200f },
            });
            Console.WriteLine("GameResponse:\n" + gameReponse.ToJson(Formatting.Indented) + "\n");
            Console.WriteLine("GameResponse:\n" + gameReponse.ToJson());

            Console.ReadKey();
        }
    }
}

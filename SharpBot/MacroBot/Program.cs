﻿using Newtonsoft.Json;
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
            #region Json

            string json = @"
{
	'id': 1234,

    'name': 'Match 1',
	'tick': 231,
	'solarSystems': [
		{
			'id': 2344,
			'name': 'S1',
			'coords': {
				'x': 130,
				'y': 75

            },
			'planets': [
				{
					'id': 2554,
					'name': 'S1P1',
					'orbitDistance': 200,
					'orbitRotation': 21,
					'ownedBy': 1867

                },
				{
					'id': 2558,
					'name': 'S1P2',
					'orbitDistance': 100,
					'orbitRotation': 51,
					'ownedBy': -1
				}
			]
		},
		{
			'id': 2345,
			'name': 'S2',
			'coords': {
				'x': 400,
				'y': 300
			},
			'planets': [
				{
					'id': 2354,
					'name': 'S2P1',
					'orbitDistance': 300,
					'orbitRotation': 359,
					'ownedBy': -1
				}
			]
		}
	],
	'players': [
		{
			'id': 1867,
			'name': 'Jan',
			'credits': 252,
			'ufos': [
				{
					'id': 5364,
					'type': 'tank',
					'inFight': true,
					'coord': {
						'x': 180,
						'y': 130
					}
				},
				{
					'id': 5374,
					'type': 'support',
					'inFight': false,
					'coord': {
						'x': 280,
						'y': 130
					}
				}
			]
		},
		{
			'id': 1092,
			'name': 'Piet',
			'credits': 133,
			'ufos': [
				{
					'id': 9363,
					'type': 'fighter',
					'inFight': true,
					'coord': {
						'x': 180,
						'y': 400
					}
				},
				{
					'id': 9364,
					'type': 'fighter',
					'inFight': true,
					'coord': {
						'x': 1200,
						'y': 400
					}
				},
				{
					'id': 9365,
					'type': 'fighter',
					'inFight': true,
					'coord': {
						'x': 900,
						'y': 884
					}
				},
				{
					'id': 9366,
					'type': 'fighter',
					'inFight': true,
					'coord': {
						'x': 700,
						'y': 100
					}
				},
				{
					'id': 2374,
					'type': 'scout',
					'inFight': false,
					'coord': {
						'x': 280,
						'y': 700
					}
				}
			]
		}
	],
	'fights': [
		{
			'id': 1537,
			'player1Id': 1867,
			'player2Id': 1092,
			'planetId': 2554,
            'player1UfoIds': [5364, 200],
			'player2UfoIds': [9364]
		}
	]
}";
            #endregion

            var gameState = JsonConvert.DeserializeObject<Protocol.GameState>(json);

            var gameResponseMoveToPlanet = new Protocol.GameResponseMoveToPlanet
            {
                Ufos = new List<int> { 1, 2, 3 },
                PlanetId = 1324,
            };
            Console.WriteLine(gameResponseMoveToPlanet.ToJson(Formatting.Indented));
            Console.WriteLine(gameResponseMoveToPlanet.ToJson());

            var gameResponseMoveToCoord = new Protocol.GameResponseMoveToCoord
            {
                Ufos = new List<int> { 1, 2, 3 },
                Coord = new Bot.Protocol.Position { X = 2, Y = 3 },
            };
            Console.WriteLine(gameResponseMoveToCoord.ToJson(Formatting.Indented));
            Console.WriteLine(gameResponseMoveToCoord.ToJson());

            var gameResponseConquer = new Protocol.GameResponseConquer
            {
                PlanetId = 1234,
            };
            Console.WriteLine(gameResponseConquer.ToJson(Formatting.Indented));
            Console.WriteLine(gameResponseConquer.ToJson());

            var gameResponseBuy = new Protocol.GameResponseBuy
            {
                Amount = 1,
                PlanetId = 1324,
            };
            Console.WriteLine(gameResponseBuy.ToJson(Formatting.Indented));
            Console.WriteLine(gameResponseBuy.ToJson());

            Console.WriteLine("Starting macro engine");
            var engine = new MacroEngine();
            engine.Run();


            Console.ReadKey();
        }
    }
}

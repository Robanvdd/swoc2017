using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SharpBot.Protocol;
using Newtonsoft.Json;

namespace SharpBot
{
    public class Engine
    {
        private readonly IBot bot;

        private Player botColor;

        public Engine(IBot bot)
        {
            this.bot = bot;
        }

        public void run()
        {
            try
            {
                DoInitiateRequest();

                Player winner = DoFirstRound();
                while (winner == Player.None)
                {
                    winner = DoNormalRound();
                }
            }
            catch (Exception ex)
            {
                Console.Error.WriteLine("Exception. Bailing out.");
                Console.Error.WriteLine(ex.StackTrace);
            }
        }

        private void DoInitiateRequest()
        {
            InitiateRequest initRequest = readMessage<InitiateRequest>();
            if (initRequest == null)
            {
                throw new InvalidMessageException("Unexpected message received. Expected InitiateRequest.");
            }

            botColor = initRequest.Color;

            bot.HandleInitiate(initRequest);
        }

        private Player DoFirstRound()
        {
            Player winner;
            if (botColor == Player.White)
            {
                // Do the first move
                HandleMoveRequest();
                // and wait for the engine to acknowledge
                winner = HandleProcessedMove();
                if (winner != Player.None)
                {
                    return winner;
                }

                // Wait for first two moves of black
                winner = HandleProcessedMove();
                if (winner != Player.None)
                {
                    return winner;
                }
                winner = HandleProcessedMove();
            }
            else
            {
                // Wait for first white move
                winner = HandleProcessedMove();
            }
            return winner;
        }

        private Player DoNormalRound()
        {
            Player winner;

            HandleMoveRequest();
            winner = HandleProcessedMove();
            if (winner != Player.None)
            {
                return winner;
            }

            HandleMoveRequest();
            winner = HandleProcessedMove();
            if (winner != Player.None)
            {
                return winner;
            }

            winner = HandleProcessedMove();
            if (winner != Player.None)
            {
                return winner;
            }

            winner = HandleProcessedMove();

            return winner;
        }

        private void HandleMoveRequest()
        {
            // process first move
            MoveRequest moveRequest = readMessage<MoveRequest>();
            if (moveRequest == null)
            {
                throw new InvalidMessageException("Unexpected message received. Expected MoveRequest.");
            }

            Move move = bot.HandleMove(moveRequest);
            writeMessage(move);
        }

        private Player HandleProcessedMove()
        {
            ProcessedMove processedMove = readMessage<ProcessedMove>();
            if (processedMove == null)
            {
                throw new InvalidMessageException("Unexpected message received. Expected ProcessedMove.");
            }

            bot.HandleProcessedMove(processedMove);
            return processedMove.Winner;
        }

        private T readMessage<T>()
        {
            string line = Console.In.ReadLine();
            if (string.IsNullOrEmpty(line))
            {
                return default(T);
            }

            return JsonConvert.DeserializeObject<T>(line);
        }

        private void writeMessage<T>(T message)
        {
            Console.Out.WriteLine(JsonConvert.SerializeObject(message));
        }
    }
}
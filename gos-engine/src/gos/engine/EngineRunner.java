package gos.engine;

import gos.engine.protocol.Board;
import gos.engine.protocol.BoardLocation;
import gos.engine.protocol.InitiateRequest;
import gos.engine.protocol.Move;
import gos.engine.protocol.MoveRequest;
import gos.engine.protocol.MoveType;
import gos.engine.protocol.Player;
import gos.engine.protocol.ProcessedMove;
import gos.engine.protocol.Stone;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EngineRunner implements AutoCloseable
{
    private final Database database;
    private final MatchLogger logger;

    private final Bot botWhite;
    private final Bot botBlack;

    private final Board board;
    
    public EngineRunner(Database database, String idWhite, String idBlack) throws IOException
    {
        this.database = database;
        logger = new MatchLogger();

        String executableWhite = database.GetBotExecutable(idWhite);
        if (executableWhite == null)
        {
            throw new IllegalArgumentException("White bot could not be found");
        }

        botWhite = new Bot(executableWhite, Player.White, idWhite);

        String executableBlack = database.GetBotExecutable(idBlack);
        if (executableBlack == null)
        {
            throw new IllegalArgumentException("Black bot could not be found");
        }
        botBlack = new Bot(executableBlack, Player.Black, idBlack);

        board = new Board();
    }

    public void run()
    {
        try
        {
            System.out.println("Starting match between " + botWhite.Id + " and " + botBlack.Id);

            Player winner = Player.None;

            Date startDate = new Date();

            InitializeBots();

            winner = FirstRound();

            while (winner == Player.None)
            {
                winner = NormalRound();
            }

            Date endDate = new Date();

            long diff = endDate.getTime() - startDate.getTime();
            long matchTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            
            System.out.println("Match done in " + matchTimeInSeconds + " second(s)");
            System.out.println("Winner is " + ((winner == Player.Black) ? "Black" : "White"));

            String matchId = database.StoreMatch(logger.GetLog(), botWhite, botBlack, winner, startDate, endDate);
            
            System.out.println("Match id:");
            System.out.println(matchId);
        }
        catch (Exception ex)
        {
            System.err.println("Match failed: " + ex.toString());
            ex.printStackTrace();
        }
}

    @Override
    public void close() throws Exception
    {
        botWhite.close();
        botBlack.close();
    }

    private static final long FirstMoveTimeOut = 2000;
    private static final long NormalRoundTimeOut = 2000;

    private void InitializeBots()
    {
        InitiateRequest initReqW = new InitiateRequest(Player.White);
        botWhite.writeMessage(initReqW);

        InitiateRequest initReqB = new InitiateRequest(Player.Black);
        botBlack.writeMessage(initReqB);
    }

    private static final MoveType[] AttackOnly = { MoveType.Attack };
    private static final MoveType[] AllMoves = { MoveType.Pass, MoveType.Attack, MoveType.Strengthen };

    private Player FirstRound()
    {
        return DoOneMove(botWhite, AttackOnly, FirstMoveTimeOut);
    }

    private Player NormalRound()
    {
        Player winner;

        // First black then white, since white may start the game
        winner = DoOneMove(botBlack, AttackOnly, NormalRoundTimeOut);
        if (winner != Player.None)
        {
            return winner;
        }

        winner = DoOneMove(botBlack, AllMoves, NormalRoundTimeOut);
        if (winner != Player.None)
        {
            return winner;
        }

        winner = DoOneMove(botWhite, AttackOnly, NormalRoundTimeOut);
        if (winner != Player.None)
        {
            return winner;
        }

        winner = DoOneMove(botWhite, AllMoves, NormalRoundTimeOut);
        if (winner != Player.None)
        {
            return winner;
        }

        return Player.None;
    }

    private String MoveToString(Move move)
    {
        if (move == null)
        {
            return "null";
        }

        String moveType = (move.Type == MoveType.Pass) ? "Pass" : (move.Type == MoveType.Attack) ? "Attack" : (move.Type == MoveType.Strengthen) ? "Strengthen" : "Unknown";

        String dump = "move: type = " + moveType + ", from = ";
        if (move.From != null)
        {
            dump += move.From.X + ";" + move.From.Y;
        }
        else
        {
            dump += "null";
        }
        dump += ", to = ";
        if (move.To != null)
        {
            dump += move.To.X + ";" + move.To.Y;
        }
        else
        {
            dump += "null";
        }
        return dump;
    }

    private static Player GetOtherPlayer(Player player)
    {
        if (player == Player.Black)
        {
            return Player.White;
        }
        else if (player == Player.White)
        {
            return Player.Black;
        }
        else
        {
            return Player.None;
        }
    }

    private Player DoOneMove(Bot bot, MoveType[] allowedMoves, long timeOut)
    {
        MoveRequest request = new MoveRequest(board, allowedMoves);
        Move move = null;
        try
        {
            move = bot.writeAndReadMessage(request, Move.class, timeOut);
        }
        catch (Exception ex)
        {
            System.err.println("Exception occurred with move request.");
            ex.printStackTrace();
        }

        if (move == null)
        {
            System.err.println("No response received. Other bot wins.");
            return GetOtherPlayer(bot.Player);
        }

        if (!IsMoveInAllowedList(move, allowedMoves) || !IsMoveValid(bot, move))
        {
            System.err.println("Illegal move. Other bot wins.");
            return GetOtherPlayer(bot.Player);
        }

        ProcessValidatedMove(move);

        Player winner = GetCurrentWinner();

        SendMoveToAllBots(bot.Player, move, winner);

        return winner;
    }

    private void SendMoveToAllBots(Player player, Move move, Player winner)
    {
        ProcessedMove processedMove = new ProcessedMove(player, move, winner);
        botWhite.writeMessage(processedMove);
        botBlack.writeMessage(processedMove);
    }

    private boolean IsMoveInAllowedList(Move move, MoveType[] allowedMoves)
    {
        boolean allowedMove = false;
        for (int i = 0; i < allowedMoves.length; i++)
        {
            if (move.Type == allowedMoves[i])
            {
                allowedMove = true;
                break;
            }
        }

        return allowedMove;
    }

    private boolean IsMoveValid(Bot bot, Move move)
    {
        if (move.Type == MoveType.Pass)
        {
            return (move.From == null && move.To == null);
        }
        else if (move.From == null || move.To == null)
        {
            return false;
        }
        else
        {
            Player fromColor = board.GetOwner(move.From);
            Player toColor = board.GetOwner(move.To);

            int fromHeight = board.GetHeight(move.From);
            int toHeight = board.GetHeight(move.To);

            Player botColor = bot.Player;

            if (fromColor != botColor && // can only move from places owned by bot
                    toColor != Player.None) // can not move to an empty place
            {
                System.err.println("not owned by bot or moving to empty place");
                return false;
            }
            else if (move.Type == MoveType.Attack && fromColor != toColor && // can only attack the other color
                    fromHeight >= toHeight && // can only attack equal or lower stacks
                    IsValidPath(move.From, move.To))
            {
                return true;
            }
            else if (move.Type == MoveType.Strengthen && fromColor == toColor && // can only strengthen yourself
                    IsValidPath(move.From, move.To))
            {
                return true;
            }
            else
            {
                System.err.println("fromColor = " + fromColor + ", toColor = " + toColor + ", fromHeight = " + fromHeight + ", toHeight = " + toHeight);
                return false;
            }
        }
    }

    private boolean IsValidPath(BoardLocation from, BoardLocation to)
    {
        boolean movingEast = (from.X < to.X);
        boolean movingWest = (from.X > to.X);
        boolean movingSouth = (from.Y < to.Y);
        boolean movingNorth = (from.Y > to.Y);
        boolean movingEastOrWest = movingEast || movingWest;
        boolean movingNorthOrSouth = movingNorth || movingSouth;
        boolean movingSouthEast = movingEast && movingSouth;
        boolean movingNorthWest = movingWest && movingNorth;

        int minX = movingEast ? from.X : to.X;
        int minY = movingSouth ? from.Y : to.Y;
        int maxX = movingEast ? to.X : from.X;
        int maxY = movingSouth ? to.Y : from.Y;

        // only allowed to move, N, E, S, W, NW, SE

        boolean movingOnlyEastOrWest = movingEastOrWest && !movingNorthOrSouth;
        boolean movingOnlyNorthOrSouth = movingNorthOrSouth && !movingEastOrWest;
        boolean movingDiagonal = (movingSouthEast || movingNorthWest) && ((maxX - minX) == (maxY - minY));
        if (!movingOnlyEastOrWest && !movingOnlyNorthOrSouth && !movingDiagonal)
        {
            return false;
        }

        boolean allEmpty;
        if (movingOnlyEastOrWest)
        {
            allEmpty = true;
            for (int x = minX + 1; x < maxX; x++)
            {
                Player owner = board.GetOwner(new BoardLocation(x, from.Y));
                allEmpty &= (owner == Player.None);
            }
        }
        else if (movingOnlyNorthOrSouth)
        {
            allEmpty = true;
            for (int y = minY + 1; y < maxY; y++)
            {
                Player owner = board.GetOwner(new BoardLocation(from.X, y));
                allEmpty &= (owner == Player.None);
            }
        }
        else if (movingDiagonal)
        {
            allEmpty = true;
            for (int x = minX + 1, y = minY + 1; x < maxX && y < maxY; x++, y++)
            {
                Player owner = board.GetOwner(new BoardLocation(x, y));
                allEmpty &= (owner == Player.None);
            }
        }
        else
        {
            allEmpty = false;
        }

        return allEmpty;
    }

    private void ProcessValidatedMove(Move move)
    {
        if (move.Type != MoveType.Pass)
        {
            Player newOwner = board.GetOwner(move.From);
            Stone newStone = board.GetStone(move.From);
            int newCount = board.GetHeight(move.From);

            Player oldOwner = board.GetOwner(move.To);
            if (newOwner == oldOwner)
            {
                // strengthen
                int oldCount = board.GetHeight(move.To);
                newCount += oldCount;
            }
            else
            {
                // attack, just overwrite the "to" location
            }

            board.SetSpace(move.To, newOwner, newStone, newCount);
            board.ClearSpace(move.From);
        }
        logger.AddMove(move);
    }

    private Player GetCurrentWinner()
    {
        boolean blackAlive = IsAlive(Player.Black);
        boolean whiteAlive = IsAlive(Player.White);

        if (blackAlive && whiteAlive)
        {
            return Player.None;
        }
        else if (!blackAlive && whiteAlive)
        {
            return Player.White;
        }
        else if (blackAlive && !whiteAlive)
        {
            return Player.Black;
        }
        else // (!blackAlive && !whiteAlive)
        {
            return Player.None;
        }
    }

    private boolean IsAlive(Player player)
    {
        return board.GetTotalCount(player, Stone.A) > 0 && board.GetTotalCount(player, Stone.B) > 0 && board.GetTotalCount(player, Stone.C) > 0;
    }
}

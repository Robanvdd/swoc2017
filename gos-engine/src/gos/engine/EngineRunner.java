package gos.engine;

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

        botWhite = new Bot(executableWhite, Board.PlayerWhite, idWhite);

        String executableBlack = database.GetBotExecutable(idBlack);
        if (executableBlack == null)
        {
            throw new IllegalArgumentException("Black bot could not be found");
        }
        botBlack = new Bot(executableBlack, Board.PlayerBlack, idBlack);

        board = new Board();
    }

    public void run()
    {
        try
        {
            System.out.println("Starting match between " + botWhite.Id + " and " + botBlack.Id);

            int winner = Board.PlayerNone;

            Date startDate = new Date();

            InitializeBots();

            FirstRound();

            do
            {
                winner = NormalRound();
            }
            while (winner == Board.PlayerNone);

            Date endDate = new Date();

            long diff = endDate.getTime() - startDate.getTime();
            long matchTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            
            System.out.println("Match done in " + matchTimeInSeconds + " second(s)");
            System.out.println("Winner is " + ((winner == Board.PlayerBlack) ? "Black" : "White"));

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
        InitiateRequest initReqW = new InitiateRequest(Board.PlayerWhite);
        botWhite.writeMessage(initReqW);

        InitiateRequest initReqB = new InitiateRequest(Board.PlayerBlack);
        botBlack.writeMessage(initReqB);
    }

    private static final int[] AttackOnly = { Move.Attack };
    private static final int[] AllMoves = { Move.Pass, Move.Attack, Move.Strengthen };

    private void FirstRound()
    {
        DoOneMove(botWhite, AttackOnly, FirstMoveTimeOut);
    }

    private int NormalRound()
    {
        int winner;

        // First black then white, since white may start the game
        winner = DoOneMove(botBlack, AttackOnly, NormalRoundTimeOut);
        if (winner != Board.PlayerNone)
        {
            return winner;
        }

        winner = DoOneMove(botBlack, AllMoves, NormalRoundTimeOut);
        if (winner != Board.PlayerNone)
        {
            return winner;
        }

        winner = DoOneMove(botWhite, AttackOnly, NormalRoundTimeOut);
        if (winner != Board.PlayerNone)
        {
            return winner;
        }

        winner = DoOneMove(botWhite, AllMoves, NormalRoundTimeOut);
        if (winner != Board.PlayerNone)
        {
            return winner;
        }

        return Board.PlayerNone;
    }

    private String MoveToString(Move move)
    {
        if (move == null)
        {
            return "null";
        }

        String moveType = (move.Type == Move.Pass) ? "Pass" : (move.Type == Move.Attack) ? "Attack" : (move.Type == Move.Strengthen) ? "Strengthen" : "Unknown";

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

    private static int GetOtherPlayer(int player)
    {
        if (player == Board.PlayerBlack)
        {
            return Board.PlayerWhite;
        }
        else if (player == Board.PlayerWhite)
        {
            return Board.PlayerBlack;
        }
        else
        {
            return Board.PlayerNone;
        }
    }

    private int DoOneMove(Bot bot, int[] allowedMoves, long timeOut)
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

        int winner = GetCurrentWinner();

        SendMoveToAllBots(bot.Player, move, winner);

        return winner;
    }

    private void SendMoveToAllBots(int player, Move move, int winner)
    {
        ProcessedMove processedMove = new ProcessedMove(player, move, winner);
        botWhite.writeMessage(processedMove);
        botBlack.writeMessage(processedMove);
    }

    private boolean IsMoveInAllowedList(Move move, int[] allowedMoves)
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
        if (move.Type == Move.Pass)
        {
            return (move.From == null && move.To == null);
        }
        else if (move.From == null || move.To == null)
        {
            return false;
        }
        else
        {
            int fromColor = board.GetOwner(move.From);
            int toColor = board.GetOwner(move.To);

            int fromHeight = board.GetHeight(move.From);
            int toHeight = board.GetHeight(move.To);

            int botColor = bot.Player;

            if (fromColor != botColor && // can only move from places owned by bot
                    toColor != Board.PlayerNone) // can not move to an empty place
            {
                System.err.println("not owned by bot or moving to empty place");
                return false;
            }
            else if (move.Type == Move.Attack && fromColor != toColor && // can only attack the other color
                    fromHeight >= toHeight && // can only attack equal or lower stacks
                    IsValidPath(move.From, move.To))
            {
                return true;
            }
            else if (move.Type == Move.Strengthen && fromColor == toColor && // can only strengthen yourself
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
                int owner = board.GetOwner(new BoardLocation(x, from.Y));
                allEmpty &= (owner == Board.PlayerNone);
            }
        }
        else if (movingOnlyNorthOrSouth)
        {
            allEmpty = true;
            for (int y = minY + 1; y < maxY; y++)
            {
                int owner = board.GetOwner(new BoardLocation(from.X, y));
                allEmpty &= (owner == Board.PlayerNone);
            }
        }
        else if (movingDiagonal)
        {
            allEmpty = true;
            for (int x = minX + 1, y = minY + 1; x < maxX && y < maxY; x++, y++)
            {
                int owner = board.GetOwner(new BoardLocation(x, y));
                allEmpty &= (owner == Board.PlayerNone);
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
        if (move.Type != Move.Pass)
        {
            int newOwner = board.GetOwner(move.From);
            int newStone = board.GetStone(move.From);
            int newCount = board.GetHeight(move.From);

            int oldOwner = board.GetOwner(move.To);
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

    private int GetCurrentWinner()
    {
        boolean blackAlive = IsAlive(Board.PlayerBlack);
        boolean whiteAlive = IsAlive(Board.PlayerWhite);

        if (blackAlive && whiteAlive)
        {
            return Board.PlayerNone;
        }
        else if (!blackAlive && whiteAlive)
        {
            return Board.PlayerWhite;
        }
        else if (blackAlive && !whiteAlive)
        {
            return Board.PlayerBlack;
        }
        else // (!blackAlive && !whiteAlive)
        {
            return Board.PlayerNone;
        }
    }

    private boolean IsAlive(int player)
    {
        return board.GetTotalCount(player, Board.StoneA) > 0 && board.GetTotalCount(player, Board.StoneB) > 0 && board.GetTotalCount(player, Board.StoneC) > 0;
    }
}

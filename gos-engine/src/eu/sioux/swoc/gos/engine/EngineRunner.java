package eu.sioux.swoc.gos.engine;

import java.io.IOException;

import eu.sioux.swoc.gos.engine.io.IORobot;

public class EngineRunner implements AutoCloseable
{
	private final IORobot botWhite;
	private final IORobot botBlack;

	private final Board board;
	
	public EngineRunner(String executableWhite, String executableBlack) throws IOException
	{
		botWhite = new IORobot(executableWhite);
		botBlack = new IORobot(executableBlack);
		
		board = new Board();
	}

	public void run()
	{
		System.out.println("Game started");

		try
		{
			SetupBots();
			
			FirstRound();
			
			board.Dump();

			NormalRound();
			
			board.Dump();
		}
		catch (Exception ex)
		{
			System.out.println("Run failed: " + ex.getMessage());
		}

		System.out.println("---- START OF DUMP WHITE ----");
		System.out.println(botWhite.getDump());
		System.out.println("---- END OF DUMP WHITE ----");
		System.out.println("---- START OF DUMP BLACK ----");
		System.out.println(botBlack.getDump());
		System.out.println("---- END OF DUMP BLACK ----");
		System.out.println("Game ended");
	}

	@Override
	public void close() throws Exception
	{
		botWhite.close();
		Thread.sleep(200);

		botBlack.close();
		Thread.sleep(200);

		Thread.sleep(200);
	}

	private static final long SetupTimeOut = 2000;
	private static final long FirstMoveTimeOut = 2000;
	private static final long NormalRoundTimeOut = 2000;
	
	private void SetupBots()
	{
		InitiateRequest initReq = new InitiateRequest(Board.OwnerWhite);
		StatusResponse status = botWhite.writeAndReadMessage(initReq, StatusResponse.class, SetupTimeOut);
		
		System.out.println("SetupBots: status.Ok = " + status.Ok);
	}
	
	private static final int[] AttackOnly = { Move.Attack };
	private static final int[] AllMoves = { Move.Pass, Move.Attack, Move.Strengthen };
	
	private void FirstRound()
	{
		// TODO: If bot does not give a valid move, then let it loose immediately
		DoOneMove(botWhite, AttackOnly, FirstMoveTimeOut);
	}
	
	private void NormalRound()
	{
		// First black then white
//		DoOneMove(botBlack, AttackOnly, NormalRoundTimeOut); 
//		DoOneMove(botBlack, AllMoves, NormalRoundTimeOut); 

		DoOneMove(botWhite, AttackOnly, NormalRoundTimeOut); 
		DoOneMove(botWhite, AllMoves, NormalRoundTimeOut); 
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
	
	private boolean IsMoveValid(IORobot bot, Move move)
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
			
			int botColor = (bot == botWhite) ? Board.OwnerWhite : ((bot == botBlack) ? Board.OwnerBlack : Board.OwnerNone);

			// TODO: check direction
			// TODO: check if all places in between are empty
			

			if (fromColor != botColor &&             // can only move from places owned by bot
			        toColor != Board.OwnerNone       // can not move to an empty place
				)
			{
				return false;
			}
			else
			{
				if (move.Type == Move.Attack &&
				        fromColor != toColor &&       // can only attack the other color
						fromHeight >= toHeight        // can only attack equal or lower stacks
						)
				{
					return true;
				}
			    else if (move.Type == Move.Strengthen &&
			            fromColor == toColor         // can only strengthen yourself
						)
				{
					return true;
				}
			}

			return false;
		}
	}
	
	private boolean IsValidPath(BoardLocation from, BoardLocation to)
	{
	    boolean movingEast = (from.X < to.X);
	    boolean movingWest = (from.X > to.X);
	    boolean movingEastOrWest = movingEast || movingWest;
	    boolean movingNorth = (from.Y < to.Y);
	    boolean movingSouth = (from.Y > to.Y);
	    boolean movingNorthOrSouth = movingNorth || movingSouth;
	    
	    // only allowed to move, N, E, S, W, NW, SE

	    boolean directionOk = 
	            (movingEastOrWest && !movingNorthOrSouth) ||
	            (movingNorthOrSouth && !movingEastOrWest) ||
	            (movingEast && movingSouth) ||
	            (movingWest && movingNorth);
    
	    return directionOk;
	}
	
	private void DoOneMove(IORobot bot, int[] allowedMoves, long timeOut)
	{
		MoveRequest request = new MoveRequest(board, allowedMoves);
		Move move = bot.writeAndReadMessage(request, Move.class, timeOut);

		boolean moveProcessed = false;
		
		boolean moveAllowed = IsMoveInAllowedList(move, allowedMoves);
		if (moveAllowed)
		{
			boolean moveValid = IsMoveValid(bot, move);

			if (moveValid)
			{
				if (move.Type != Move.Pass)
				{
					DoMove(board, move.From, move.To);
				}
				moveProcessed = true;
			}
		}	

		StatusResponse status = new StatusResponse(moveProcessed);
		bot.writeMessage(status);
	}
	
	private static void DoMove(Board board, BoardLocation from, BoardLocation to)
	{
		int newOwner = board.GetOwner(from);
		int newStone = board.GetStone(from);
		int newCount = board.GetHeight(from);

		int oldOwner = board.GetOwner(to);
		if (newOwner == oldOwner)
		{
			// strengthen
			int oldCount = board.GetHeight(to);
			newCount += oldCount;
		}
		else
		{
			// TODO: check if count(to) <= count(from)
			// attack
		}
		
		board.SetSpace(to, newOwner, newStone, newCount);
		board.ClearSpace(from);
	}
}

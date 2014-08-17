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

	private void DoOneMove(IORobot bot, int[] allowedMoves, long timeOut)
	{
		MoveRequest request = new MoveRequest(board, allowedMoves);
		Move move = bot.writeAndReadMessage(request, Move.class, timeOut);
		
	
		// Check if move type is in allowed list
		boolean allowedMove = false;
		for (int i = 0; i < allowedMoves.length; i++)
		{
			if (move.Type == allowedMoves[i])
			{
				allowedMove = true;
				break;
			}
		}
	
		if (allowedMove)
		{
			if (move.Type != Move.Pass)
			{
				allowedMove = ProcessMove(move, bot);
			}
			else
			{
				// Pass, do nothing
			}
		}
		
		
		StatusResponse status = new StatusResponse(allowedMove);
		bot.writeMessage(status);
	}
	
	private boolean ProcessMove(Move move, IORobot bot)
	{
		assert (move.Type != Move.Pass);
		
		boolean accepted = true;
		
		// bot can only move from places owned by himself
		int fromOwner = board.GetOwner(move.From);
		accepted &= (bot == botWhite && fromOwner == Board.OwnerWhite) || (bot == botBlack && fromOwner == Board.OwnerBlack);
		
		// bot can only move to occupied places
		int toOwner = board.GetOwner(move.To);
		accepted &= (toOwner != Board.OwnerNone);

		// TODO: check if all places in between are empty
		
		if (accepted)
		{
			DoMove(board, move.From, move.To);
		}
		
		return accepted;
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

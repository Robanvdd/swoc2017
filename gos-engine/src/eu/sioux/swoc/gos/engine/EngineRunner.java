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
		
//		String serialized = board.Serialize();
//		System.out.println("Board state:");
//		System.out.println(serialized);
//
//		DumpOwners(board);
////		DumpStones(board);
////		DumpCount(board);
//		DumpTotals(board);
//		
//		Board b = DoMove(board, new BoardLocation(0, 0), new BoardLocation(0, 1));
//
//		DumpOwners(b);
////		DumpStones(b);
////		DumpCount(b);
//		DumpTotals(b);
//		
//		b = DoMove(b, new BoardLocation(0, 1), new BoardLocation(1, 0));
//
//		DumpOwners(b);
////		DumpStones(b);
////		DumpCount(b);
//		DumpTotals(b);
	}

	private static void DumpTotals(Board board)
	{
	}
	

	private static void DumpOwners(Board board)
	{
		System.out.print("-- owners --------  ");
		System.out.print("-- stones --------  ");
		System.out.println("-- heights ----------------");
		for (int y = 0; y < 9; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				char c;
				if ((x - y) < 5 && (y - x) < 5 && (x != 4 || y != 4))
				{
					int owner = board.GetOwner(new BoardLocation(x, y));
					c = (owner == Board.OwnerBlack) ? 'B' :
						(owner == Board.OwnerWhite) ? 'W' :
						'.';
				}
				else
				{
					c = ' ';
				}
				System.out.print(" " + c);
			}
			System.out.print("  ");
			for (int x = 0; x < 9; x++)
			{
				char c;
				if ((x - y) < 5 && (y - x) < 5 && (x != 4 || y != 4))
				{
					int stone = board.GetStone(new BoardLocation(x, y));
					c = (stone == Board.StoneA) ? 'a' :
						(stone == Board.StoneB) ? 'b' :
						(stone == Board.StoneC) ? 'c' :
						'.';
				}
				else
				{
					c = ' ';
				}
				System.out.print(" " + c);
			}
			System.out.print("  ");
			for (int x = 0; x < 9; x++)
			{
				String s;
				if ((x - y) < 5 && (y - x) < 5 && (x != 4 || y != 4))
				{
					int height = board.GetHeight(new BoardLocation(x, y));
					s = String.format("%2d", height);
				}
				else
				{
					s = "  ";
				}
				System.out.print(" " + s);
			}
			System.out.println();
		}
		System.out.print("------------------  ");
		System.out.print("------------------  ");
		System.out.println("---------------------------");
		System.out.print("White: "
				+ board.GetTotalCount(Board.OwnerWhite, Board.StoneA) + " a, "
				+ board.GetTotalCount(Board.OwnerWhite, Board.StoneB) + " b, "
				+ board.GetTotalCount(Board.OwnerWhite, Board.StoneC) + " c");
		System.out.println("  Black: "
				+ board.GetTotalCount(Board.OwnerBlack, Board.StoneA) + " a, "
				+ board.GetTotalCount(Board.OwnerBlack, Board.StoneB) + " b, "
				+ board.GetTotalCount(Board.OwnerBlack, Board.StoneC) + " c");
	}

	public void run()
	{
		System.out.println("Game started");

		try
		{
			SetupBots();
			
			FirstRound();
			
			DumpOwners(board);
			DumpTotals(board);

			NormalRound();
			
			DumpOwners(board);
			DumpTotals(board);
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

		if (move.Type != Move.Pass)
		{
			DoMove(board, move.From, move.To);
		}

		StatusResponse status = new StatusResponse(true);
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

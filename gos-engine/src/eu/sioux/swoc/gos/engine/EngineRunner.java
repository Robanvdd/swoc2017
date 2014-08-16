package eu.sioux.swoc.gos.engine;

import java.io.IOException;

import eu.sioux.swoc.gos.engine.io.IORobot;

public class EngineRunner implements AutoCloseable
{
	private final IORobot bot1;
	private final IORobot bot2;

	private final Board board;
	
	public EngineRunner(String executable1, String executable2) throws IOException
	{
		bot1 = new IORobot(executable1);
		bot2 = new IORobot(executable2);
		
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
		System.out.println("Total:");
		System.out.println("White: "
				+ board.GetTotalCount(Board.OwnerWhite, Board.StoneA) + ", "
				+ board.GetTotalCount(Board.OwnerWhite, Board.StoneB) + ", "
				+ board.GetTotalCount(Board.OwnerWhite, Board.StoneC));
		System.out.println("Black: "
				+ board.GetTotalCount(Board.OwnerBlack, Board.StoneA) + ", "
				+ board.GetTotalCount(Board.OwnerBlack, Board.StoneB) + ", "
				+ board.GetTotalCount(Board.OwnerBlack, Board.StoneC));
	}
	
	private static Board DoMove(Board board, BoardLocation from, BoardLocation to)
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
		return board.ChangeState(from, Board.OnwerNone, 0, 0).ChangeState(to, newOwner, newStone, newCount);
	}

	private static void DumpOwners(Board board)
	{
		System.out.println("---------------------------");
		for (int y = 0; y < 9; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				int owner = board.GetOwner(new BoardLocation(x, y));
				System.out.print(" " + String.format("%2d", owner));
			}
			System.out.println();
		}
		System.out.println("---------------------------");
	}

	private static void DumpStones(Board board)
	{
		System.out.println("---------------------------");
		for (int y = 0; y < 9; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				int stone = board.GetStone(new BoardLocation(x, y));
				System.out.print(" " + String.format("%2d", stone));
			}
			System.out.println();
		}
		System.out.println("---------------------------");
	}

	private static void DumpCount(Board board)
	{
		System.out.println("---------------------------");
		for (int y = 0; y < 9; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				int count = board.GetHeight(new BoardLocation(x, y));
				System.out.print(" " + String.format("%2d", count));
			}
			System.out.println();
		}
		System.out.println("---------------------------");
	}

	public void run()
	{
		System.out.println("Game started");
		
		bot1.doMove(board, 2000);
		bot2.doMove(board, 2000);

		System.out.println("---- START OF DUMP 1 ----");
		System.out.println(bot1.getDump());
		System.out.println("---- END OF DUMP 1 ----");
		System.out.println("---- START OF DUMP 2 ----");
		System.out.println(bot2.getDump());
		System.out.println("---- END OF DUMP 2 ----");
		System.out.println("Game ended");
	}

	@Override
	public void close() throws Exception
	{
		bot1.close();
		Thread.sleep(200);

		bot2.close();
		Thread.sleep(200);

		Thread.sleep(200);
	}
}

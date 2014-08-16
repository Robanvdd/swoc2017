package eu.sioux.swoc.gos.engine;

public class Board
{
	// owners
	public static final int OwnerNone = 0;
	public static final int OwnerWhite = 1;
	public static final int OwnerBlack = -1;
	
	// stone types
	public static final int StoneNone = 0;
	public static final int StoneA = 1;
	public static final int StoneB = 2;
	public static final int StoneC = 3;
	
	public static final int Illegal = GetCode(OwnerNone, StoneNone, 100); // must be (x mod 4 == 0)

	private final int[][] state;

	private static final int BlackA = GetCode(OwnerBlack, StoneA, 1);
	private static final int BlackB = GetCode(OwnerBlack, StoneB, 1);
	private static final int BlackC = GetCode(OwnerBlack, StoneC, 1);
	private static final int WhiteA = GetCode(OwnerWhite, StoneA, 1);
	private static final int WhiteB = GetCode(OwnerWhite, StoneB, 1);
	private static final int WhiteC = GetCode(OwnerWhite, StoneC, 1);
	
	private static final int[][] DefaultState = 
		{ 
			{ BlackA, WhiteA, WhiteA, WhiteA, WhiteA, Illegal, Illegal, Illegal, Illegal },
			{ BlackA, BlackB, WhiteB, WhiteB, WhiteB, BlackA, Illegal, Illegal, Illegal },
			{ BlackA, BlackB, BlackC, WhiteC, WhiteC, BlackB, BlackA, Illegal, Illegal },
			{ BlackA, BlackB, BlackC, BlackA, WhiteA, BlackC, BlackB, BlackA, Illegal },
			{ WhiteA, WhiteB, WhiteC, WhiteA, Illegal, BlackA, BlackC, BlackB, BlackA },
			{ Illegal, WhiteA, WhiteB, WhiteC, BlackA, WhiteA, WhiteC, WhiteB, WhiteA },
			{ Illegal, Illegal, WhiteA, WhiteB, BlackC, BlackC, WhiteC, WhiteB, WhiteA },
			{ Illegal, Illegal, Illegal, WhiteA, BlackB, BlackB, BlackB, WhiteB, WhiteA },
			{ Illegal, Illegal, Illegal, Illegal, BlackA, BlackA, BlackA, BlackA, WhiteA }, 
		};

	public Board()
	{
		state = DefaultState;
	}
	
	public Board(int[][] initialState)
	{
		state = initialState;
	}
	
	public boolean IsIllegal(BoardLocation location)
	{
		return state[location.Y][location.X] == Illegal;
	}

	public int GetOwner(BoardLocation location)
	{
		if (IsIllegal(location))
		{
			return OwnerNone;
		}
		return GetOwner(state[location.Y][location.X]);
	}

	public int GetStone(BoardLocation location)
	{
		if (IsIllegal(location))
		{
			return OwnerNone;
		}
		return GetStone(state[location.Y][location.X]);
	}

	public int GetHeight(BoardLocation location)
	{
		if (IsIllegal(location))
		{
			return 0;
		}
		return GetHeight(state[location.Y][location.X]);
	}
	
	private static int GetCode(int owner, int stone, int height)
	{
		assert (-1 <= owner && owner <= 1);
		assert (0 <= stone && stone <= 3);
		assert ((owner != 0 && stone != 0 && height > 0) ||
				(owner == 0 && stone == 0 && (height == 0 || height == 100)));
		return owner * (height * 4 + stone);
	}
	
	private static int GetOwner(int fieldCode)
	{
		if (fieldCode == 0)
		{
			return OwnerNone;
		}
		else if (fieldCode > 0)
		{
			return OwnerWhite;
		}
		else
		{
			return OwnerBlack;
		}
	}
	
	private static int GetStone(int fieldCode)
	{
		return Math.abs(fieldCode) % 4;
	}
	
	private static int GetHeight(int fieldCode)
	{
		return Math.abs(fieldCode) / 4;
	}
	
	public void Change(BoardLocation location, int owner, int stone, int height)
	{
		if (IsIllegal(location))
		{
			throw new IllegalArgumentException("location not legal");
		}

		state[location.Y][location.X] = GetCode(owner, stone, height);
	}
	
	public int GetTotalCount(int owner, int stone)
	{
		int count = 0;
		
		for (int y = 0; y < 9; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				int code = state[y][x];
				if (GetOwner(code) == owner && GetStone(code) == stone)
				{
					count++;
				}
			}
		}
		return count;
	}
}

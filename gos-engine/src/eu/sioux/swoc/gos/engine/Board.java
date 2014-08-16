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
	
	private final int[][] state;

	private static final int Empty = GetCode(OwnerNone, StoneNone, 0);

	private static final int BlackA = GetCode(OwnerBlack, StoneA, 1);
	private static final int BlackB = GetCode(OwnerBlack, StoneB, 1);
	private static final int BlackC = GetCode(OwnerBlack, StoneC, 1);
	private static final int WhiteA = GetCode(OwnerWhite, StoneA, 1);
	private static final int WhiteB = GetCode(OwnerWhite, StoneB, 1);
	private static final int WhiteC = GetCode(OwnerWhite, StoneC, 1);
	
	private static final int[][] DefaultState = 
		{ 
			{ BlackA, WhiteA, WhiteA, WhiteA, WhiteA,  Empty,  Empty,  Empty,  Empty },
			{ BlackA, BlackB, WhiteB, WhiteB, WhiteB, BlackA,  Empty,  Empty,  Empty },
			{ BlackA, BlackB, BlackC, WhiteC, WhiteC, BlackB, BlackA,  Empty,  Empty },
			{ BlackA, BlackB, BlackC, BlackA, WhiteA, BlackC, BlackB, BlackA,  Empty },
			{ WhiteA, WhiteB, WhiteC, WhiteA,  Empty, BlackA, BlackC, BlackB, BlackA },
			{  Empty, WhiteA, WhiteB, WhiteC, BlackA, WhiteA, WhiteC, WhiteB, WhiteA },
			{  Empty,  Empty, WhiteA, WhiteB, BlackC, BlackC, WhiteC, WhiteB, WhiteA },
			{  Empty,  Empty,  Empty, WhiteA, BlackB, BlackB, BlackB, WhiteB, WhiteA },
			{  Empty,  Empty,  Empty,  Empty, BlackA, BlackA, BlackA, BlackA, WhiteA }, 
		};

	public Board()
	{
		state = DefaultState;
	}
	
	public Board(int[][] initialState)
	{
		state = initialState;
	}
	
	public int GetOwner(BoardLocation location)
	{
		return GetOwner(state[location.Y][location.X]);
	}

	public int GetStone(BoardLocation location)
	{
		return GetStone(state[location.Y][location.X]);
	}

	public int GetHeight(BoardLocation location)
	{
		return GetHeight(state[location.Y][location.X]);
	}
	
	private static int GetCode(int owner, int stone, int height)
	{
		assert (-1 <= owner && owner <= 1);
		assert (0 <= stone && stone <= 3);
		assert ((owner != 0 && stone != 0 && height > 0) ||
				(owner == 0 && stone == 0 && height == 0));
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
	
	public void SetSpace(BoardLocation location, int owner, int stone, int height)
	{
		if (owner == OwnerNone)
		{
			throw new IllegalArgumentException("owner not specified");
		}
		if (stone == StoneNone)
		{
			throw new IllegalArgumentException("stone not specified");
		}
		if (height <= 0)
		{
			throw new IllegalArgumentException("height not specified");
		}

		state[location.Y][location.X] = GetCode(owner, stone, height);
	}
	
	public void ClearSpace(BoardLocation location)
	{
		state[location.Y][location.X] = Empty;
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

package eu.sioux.swoc.gos.engine;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Board
{
	// owners
	public static final int Empty = 0;
	public static final int White = 1;
	public static final int Black = -1;
	
	// stone types
	public static final int StoneA = 1;
	public static final int StoneB = 2;
	public static final int StoneC = 3;
	
	public static final int Illegal = 100; // must be (x mod 4 == 0)

	private final int[][] state;

	private static final int BlackA = GetCode(Black, StoneA, 1);
	private static final int BlackB = GetCode(Black, StoneB, 1);
	private static final int BlackC = GetCode(Black, StoneC, 1);
	private static final int WhiteA = GetCode(White, StoneA, 1);
	private static final int WhiteB = GetCode(White, StoneB, 1);
	private static final int WhiteC = GetCode(White, StoneC, 1);
	
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
			return Empty;
		}
		return GetOwner(state[location.Y][location.X]);
	}

	public int GetStone(BoardLocation location)
	{
		if (IsIllegal(location))
		{
			return Empty;
		}
		return GetStone(state[location.Y][location.X]);
	}

	public int GetCount(BoardLocation location)
	{
		if (IsIllegal(location))
		{
			return 0;
		}
		return GetCount(state[location.Y][location.X]);
	}
	
	private static int GetCode(int owner, int stone, int count)
	{
		assert (-1 <= owner && owner <= 1);
		assert ((owner != 0 && 1 <= stone && stone < 4 && count > 0) ||
				(owner == 0 && stone == 0 && count == 0));
		return owner * (count * 4 + stone);
	}
	
	private static int GetOwner(int fieldCode)
	{
		if (fieldCode == 0)
		{
			return Empty;
		}
		else if (fieldCode > 0)
		{
			return White;
		}
		else
		{
			return Black;
		}
	}
	
	private static int GetStone(int fieldCode)
	{
		return Math.abs(fieldCode) % 4;
	}
	
	private static int GetCount(int fieldCode)
	{
		return Math.abs(fieldCode) / 4;
	}
	
	public Board ChangeState(BoardLocation location, int owner, int stone, int count)
	{
		// TODO: check for legal locations
		
		int[][] newState = new int[9][9];
		
		for (int y = 0; y < 9; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				if (x == location.X && y == location.Y)
				{
					newState[y][x] = GetCode(owner, stone, count);
				}
				else
				{
					newState[y][x] = state[y][x];
				}
			}
		}
		
		return new Board(newState);
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

	public String Serialize()
	{
		JSONArray stateArray = new JSONArray();
		for (int y = 0; y < 9; y++)
		{
			JSONArray row = new JSONArray();
			for (int x = 0; x < 9; x++)
			{
				row.add(state[y][x]);
			}
			stateArray.add(row);
		}

		return stateArray.toJSONString();
	}
}

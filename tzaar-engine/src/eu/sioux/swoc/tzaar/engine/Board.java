package eu.sioux.swoc.tzaar.engine;

import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Board
{
	public final int InvalidOwner = 100;
	public final int NoOwner = 0;
	public final int WhiteA = 1;
	public final int WhiteB = 2;
	public final int WhiteC = 3;
	public final int BlackA = -1;
	public final int BlackB = -2;
	public final int BlackC = -3;
	
	private final int[][] owners =
		{
			{BlackA, WhiteA, WhiteA, WhiteA, WhiteA, InvalidOwner, InvalidOwner, InvalidOwner, InvalidOwner},
			{BlackA, BlackB, WhiteB, WhiteB, WhiteB, BlackA, InvalidOwner, InvalidOwner, InvalidOwner},
			{BlackA, BlackB, BlackC, WhiteC, WhiteC, BlackB, BlackA, InvalidOwner, InvalidOwner},
			{BlackA, BlackB, BlackC, BlackA, WhiteA, BlackC, BlackB, BlackA, InvalidOwner},
			{WhiteA, WhiteB, WhiteC, WhiteA, InvalidOwner, BlackA, BlackC, BlackB, BlackA},
			{InvalidOwner, WhiteA, WhiteB, WhiteC, BlackA, WhiteA, WhiteC, WhiteB, WhiteA},
			{InvalidOwner, InvalidOwner, WhiteA, WhiteB, BlackC, BlackC, WhiteC, WhiteB, WhiteA},
			{InvalidOwner, InvalidOwner, InvalidOwner, WhiteA, BlackB, BlackB, BlackB, WhiteB, WhiteA},
			{InvalidOwner, InvalidOwner, InvalidOwner, InvalidOwner, BlackA, BlackA, BlackA, BlackA, WhiteA},
		};

	private final int[][] stacks = new int[9][9];

	
	public Board()
	{
		for (int y = 0; y < 9; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				int owner = owners[y][x];
				if (owner == InvalidOwner)
				{
					stacks[y][x] = -1;
				}
				else if (owner == NoOwner)
				{
					stacks[y][x] = 0;
				}
				else
				{
					stacks[y][x] = 1;
				}
			}
		}
	}
	
	public int GetOwner(String id)
	{
		return NoOwner;
	}
	
	public JSONObject Serialize()
	{
		JSONArray ownersArray = new JSONArray();
		JSONArray stacksArray = new JSONArray();
		for (int y = 0; y < 9; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				ownersArray.add(owners[y][x]);
				stacksArray.add(stacks[y][x]);
			}
		}
			
		JSONObject object = new JSONObject();
		object.put("owners", ownersArray);
		object.put("stacks", stacksArray);
		return object;
	}
}

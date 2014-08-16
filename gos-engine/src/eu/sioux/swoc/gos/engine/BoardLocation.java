package eu.sioux.swoc.gos.engine;

public class BoardLocation
{
	public BoardLocation(int x, int y)
	{
		if (x < 0 || x >= 9 || y < 0 || y >= 9)
		{
			throw new IllegalArgumentException("not a valid board location");
		}
		
		X = x;
		Y = y;
	}
	
	public final int X;
	public final int Y;
}

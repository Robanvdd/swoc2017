package eu.sioux.swoc.gos.engine;

public class MoveRequest
{
	public MoveRequest(Board board, int[] allowedTypes)
	{
		Board = board;
		AllowedTypes = allowedTypes;
	}
	
	public final Board Board;
	public final int[] AllowedTypes;
}

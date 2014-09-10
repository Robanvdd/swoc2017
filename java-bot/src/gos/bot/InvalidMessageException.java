package gos.bot;

public class InvalidMessageException extends Exception
{
    public InvalidMessageException(String message)
    {
        super(message);
    }
}
package gos.engine.protocol;

public class InitiateRequest
{
    public InitiateRequest(Player color)
    {
        Color = color;
    }

    public final Player Color;
}

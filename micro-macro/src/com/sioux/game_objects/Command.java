package com.sioux.game_objects;

/**
 * Created by Michael on 30/06/2017.
 */
public class Command
{
    public Command(CommandType type, int ufoId, String to)
    {
        Type = type;
        UfoId = ufoId;
        To = to;
    }

    public final CommandType Type;
    public final int UfoId;
    public final String To;
}

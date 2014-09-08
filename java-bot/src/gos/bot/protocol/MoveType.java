package gos.bot.protocol;

import com.google.gson.annotations.SerializedName;

public enum MoveType
{
    @SerializedName("0")
    Pass(0),

    @SerializedName("1")
    Attack(1),
    
    @SerializedName("2")
    Strengthen(2);

    public final int value;

    private MoveType(int value)
    {
        this.value = value;
    }
    
    public static MoveType fromInt(int value)
    {
        for (MoveType t: MoveType.values())
        {
            if (t.value == value)
            {
                return t;
            }
        }
        throw new IllegalArgumentException();
    }
}

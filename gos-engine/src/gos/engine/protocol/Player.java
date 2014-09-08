package gos.engine.protocol;

import com.google.gson.annotations.SerializedName;

public enum Player
{
    @SerializedName("0")
    None(0),
    
    @SerializedName("1")
    White(1),
    
    @SerializedName("-1")
    Black(-1);

    public final int value;

    private Player(int value)
    {
        this.value = value;
    }
    
    public static Player fromInt(int value)
    {
        for (Player p: Player.values())
        {
            if (p.value == value)
            {
                return p;
            }
        }
        throw new IllegalArgumentException();
    }
}

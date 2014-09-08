package gos.bot.protocol;

import com.google.gson.annotations.SerializedName;

public enum Stone
{
    @SerializedName("0")
    None(0),
    
    @SerializedName("1")
    A(1),
    
    @SerializedName("2")
    B(2),
    
    @SerializedName("3")
    C(3);

    public final int value;

    private Stone(int value)
    {
        this.value = value;
    }
    
    public static Stone fromInt(int value)
    {
        for (Stone s: Stone.values())
        {
            if (s.value == value)
            {
                return s;
            }
        }
        throw new IllegalArgumentException();
    }
}

package gos.bot.protocol;

public class Board
{
    private final int[][] state;

    private static final int Empty = GetCode(Player.None, Stone.None, 0);

    private static final int BlackA = GetCode(Player.Black, Stone.A, 1);
    private static final int BlackB = GetCode(Player.Black, Stone.B, 1);
    private static final int BlackC = GetCode(Player.Black, Stone.C, 1);
    private static final int WhiteA = GetCode(Player.White, Stone.A, 1);
    private static final int WhiteB = GetCode(Player.White, Stone.B, 1);
    private static final int WhiteC = GetCode(Player.White, Stone.C, 1);

    private static final int[][] DefaultState =
        {
            { WhiteA, WhiteA, WhiteA, WhiteA, BlackA,  Empty,  Empty,  Empty,  Empty },
            { BlackA, WhiteB, WhiteB, WhiteB, BlackB, BlackA,  Empty,  Empty,  Empty },
            { BlackA, BlackB, WhiteC, WhiteC, BlackC, BlackB, BlackA,  Empty,  Empty },
            { BlackA, BlackB, BlackC, WhiteA, BlackA, BlackC, BlackB, BlackA,  Empty },
            { BlackA, BlackB, BlackC, BlackA,  Empty, WhiteA, WhiteC, WhiteB, WhiteA },
            {  Empty, WhiteA, WhiteB, WhiteC, WhiteA, BlackA, WhiteC, WhiteB, WhiteA },
            {  Empty,  Empty, WhiteA, WhiteB, WhiteC, BlackC, BlackC, WhiteB, WhiteA },
            {  Empty,  Empty,  Empty, WhiteA, WhiteB, BlackB, BlackB, BlackB, WhiteA },
            {  Empty,  Empty,  Empty,  Empty, WhiteA, BlackA, BlackA, BlackA, BlackA },
        };

    public Board()
    {
        state = DefaultState;
    }

    public Board(int[][] initialState)
    {
        state = initialState;
    }

    public Player GetOwner(BoardLocation location)
    {
        return GetOwner(state[location.Y][location.X]);
    }

    public Stone GetStone(BoardLocation location)
    {
        return GetStone(state[location.Y][location.X]);
    }

    public int GetHeight(BoardLocation location)
    {
        return GetHeight(state[location.Y][location.X]);
    }

    private static int GetCode(Player owner, Stone stone, int height)
    {
        assert((owner != Player.None && stone != Stone.None && height > 0) ||
            (owner == Player.None && stone == Stone.None && height == 0));
        return owner.value * (height * 4 + stone.value);
    }

    private static Player GetOwner(int fieldCode)
    {
        if (fieldCode == 0)
        {
            return Player.None;
        }
        else if (fieldCode > 0)
        {
            return Player.White;
        }
        else
        {
            return Player.Black;
        }
    }

    private static Stone GetStone(int fieldCode)
    {
        return Stone.fromInt(Math.abs(fieldCode) % 4);
    }

    private static int GetHeight(int fieldCode)
    {
        return Math.abs(fieldCode) / 4;
    }

    public void SetSpace(BoardLocation location, Player owner, Stone stone, int height)
    {
        if (owner == Player.None)
        {
            throw new IllegalArgumentException("owner not specified");
        }
        if (stone == Stone.None)
        {
            throw new IllegalArgumentException("stone not specified");
        }
        if (height <= 0)
        {
            throw new IllegalArgumentException("height not specified");
        }

        state[location.Y][location.X] = GetCode(owner, stone, height);
    }

    public void ClearSpace(BoardLocation location)
    {
        state[location.Y][location.X] = Empty;
    }

    public int GetTotalCount(Player player, Stone stone)
    {
        int count = 0;

        for (int y = 0; y < 9; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                int code = state[y][x];
                if (GetOwner(code) == player && GetStone(code) == stone)
                {
                    count++;
                }
            }
        }
        return count;
    }

    public void Dump()
    {
        System.out.print("-- owners --------  ");
        System.out.print("-- stones --------  ");
        System.out.println("-- heights ----------------");
        for (int y = 0; y < 9; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                char c;
                if (BoardLocation.IsLegal(x, y))
                {
                    Player owner = GetOwner(new BoardLocation(x, y));
                    c = (owner == Player.Black) ? 'B' :
                        (owner == Player.White) ? 'W' :
                        '.';
                }
                else
                {
                    c = ' ';
                }
                System.out.print(" " + c);
            }
            System.out.print("  ");
            for (int x = 0; x < 9; x++)
            {
                char c;
                if (BoardLocation.IsLegal(x, y))
                {
                    Stone stone = GetStone(new BoardLocation(x, y));
                    c = (stone == Stone.A) ? 'a' :
                        (stone == Stone.B) ? 'b' :
                        (stone == Stone.C) ? 'c' :
                        '.';
                }
                else
                {
                    c = ' ';
                }
                System.out.print(" " + c);
            }
            System.out.print("  ");
            for (int x = 0; x < 9; x++)
            {
                String s;
                if (BoardLocation.IsLegal(x, y))
                {
                    int height = GetHeight(new BoardLocation(x, y));
                    s = String.format("%2d", height);
                }
                else
                {
                    s = "  ";
                }
                System.out.print(" " + s);
            }
            System.out.println();
        }
        System.out.print("------------------  ");
        System.out.print("------------------  ");
        System.out.println("---------------------------");
        System.out.print("White: "
                + GetTotalCount(Player.White, Stone.A) + " a, "
                + GetTotalCount(Player.White, Stone.B) + " b, "
                + GetTotalCount(Player.White, Stone.C) + " c");
        System.out.println("  Black: "
                + GetTotalCount(Player.Black, Stone.A) + " a, "
                + GetTotalCount(Player.Black, Stone.B) + " b, "
                + GetTotalCount(Player.Black, Stone.C) + " c");
    }
}

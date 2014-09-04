package gos.engine;

public class Main
{
    public static void main(String[] args)
    {
        String bot1Executable = args[0];
        String bot2Executable = args[1];

        try (EngineRunner runner = new EngineRunner(bot1Executable, bot2Executable))
        {
            runner.run();
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }

}

package eu.sioux.swoc.tzaar.engine;

import java.io.IOException;

import eu.sioux.swoc.tzaar.engine.io.IORobot;

public class EngineRunner implements AutoCloseable
{
	private final IORobot bot1;
	private final IORobot bot2;

	public EngineRunner(String executable1, String executable2) throws IOException
	{
		bot1 = new IORobot(executable1);
		bot2 = new IORobot(executable2);
	}

	public void run()
	{
		System.out.println("Game started");
		bot1.doMove(2000);
		bot2.doMove(2000);

		System.out.println("---- START OF DUMP 1 ----");
		System.out.println(bot1.getDump());
		System.out.println("---- END OF DUMP 1 ----");
		System.out.println("---- START OF DUMP 2 ----");
		System.out.println(bot2.getDump());
		System.out.println("---- END OF DUMP 2 ----");
	}

	@Override
	public void close() throws Exception
	{
		bot1.close();
		Thread.sleep(200);

		bot2.close();
		Thread.sleep(200);

		Thread.sleep(200);
	}
}

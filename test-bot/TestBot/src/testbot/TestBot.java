/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbot;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import testbot.iohandling.IOHandler;

/**
 *
 * @author SvZ
 */
public class TestBot implements AutoCloseable {

    //IOHandler handler;
	StringBuilder dump;
	int errorCounter;
	final int maxErrors = 2;
    private GameField gameField = new GameField();
    static int myPlayer;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int player = Integer.parseInt(args[0]);
        myPlayer = player;
    }
    
    public TestBot(String command) throws IOException {
        //handler = new IOHandler(command);
		dump = new StringBuilder();
		errorCounter = 0;
    }
    
    public void readcommandline() {
        //String line = System.in.;
    }
    
    @SuppressWarnings("unchecked")
	public String doMove(GameField board, long timeOut)
	{
		/*JSONObject obj = new JSONObject();
		obj.put("message", "init");
		obj.put("boardState", board.Serialize());
		
		String output = obj.toString();
		handler.writeLine(output);
		
		String line = handler.readLine(timeOut);
		
		dump.append("output: " + output + "\n");
		dump.append("line: " + line + "\n");
		return line;*/
        return "";
	}
    
    public String pickAnyMove() {
        List<Move> moveList = gameField.getPossibleMoves(myPlayer);
        if (moveList.isEmpty())
            return "PASS";
        Random randomGen = new Random();
        int pick = randomGen.nextInt(moveList.size());
        
        Move move = moveList.get(pick);
        return move.getMoveAsString();
    }
    
    @Override
	public void close()
	{
		handler.close();
	}

	public String getStdin()
	{
		return handler.getStdin();
	}

	public String getStdout()
	{
		return handler.getStdout();
	}

	public String getStderr()
	{
		return handler.getStderr();
	}

	public String getDump()
	{
		return dump.toString();
	}
}

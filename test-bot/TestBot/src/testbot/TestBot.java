/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import testbot.iohandling.InStream;

/**
 *
 * @author SvZ
 */
public class TestBot {

    InStream instream = new InStream(System.in);
	private GameField gameField = new GameField();
    private static final String STOP_COMMAND = "STOP";
    private static int myPlayer;
    private int activePlayer;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int player = Integer.parseInt(args[0]);
        myPlayer = player;
        
        TestBot bot = new TestBot();
    }
    
    public TestBot() {
        runAgainstBot();
    }
    
    private void runSolo() {
        System.out.println(gameField.printField());
        System.out.flush();
        //System.out.println(gameField.serializeField().toJSONString());
        //System.out.flush();
        String move = pickAnyMove(true);
        System.out.println("Suggested move: " + move);
        String command = move;
		while (!command.equals(STOP_COMMAND)) {
            doMove(gameField, command);
            System.out.println(gameField.printField());
            System.out.flush();
            //System.out.println(gameField.serializeField().toJSONString());
            //System.out.flush();
            move = pickAnyMove(false);
            System.out.println("Suggested move: " + move);
            command = move;
            if (command.equals("PASS"))
                command = STOP_COMMAND;
        }
    }
    
    private void runAgainstBot() {
        System.out.println(gameField.printField());
        System.out.flush();
        System.out.println(gameField.serializeField().toJSONString());
        //System.out.flush();
        //System.out.println("Suggested move: " + pickAnyMove(true));
        String fieldState = readcommandline();
        String command;
		while (!fieldState.equals(STOP_COMMAND)) {
            gameField.initializeField(fieldState);
            command = pickAnyMove(true);
            doMove(gameField, command);
            System.out.println(command);
            System.out.flush();
            command = pickAnyMove(false);
            doMove(gameField, command);
            System.out.println(command);
            System.out.flush();
            System.out.println(gameField.serializeField().toJSONString());
            fieldState = readcommandline();
        }
    }
    
    private String readcommandline() {
        String value = null;
        InputStreamReader instream = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(instream);
        while (value == null)
        {
            try {
                String read = br.readLine();
                if (read != null)
                    value = read;
            } catch (IOException ex) {
                Logger.getLogger(TestBot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return value;
    }
    
    /**
     * 
     * Does a move, returns true if correctly
     * @param board
     * @param move
     * @return 
     */
	public boolean doMove(GameField board, String move) {
		Move givenMove = Move.parseString(move);
        if (givenMove == null) return false;
        if (givenMove.getType() == MoveType.PASS)
        {
            activePlayer = activePlayer * -1;
            return true;
        }
        if (givenMove.getType() == MoveType.ATTACK)
        {
            return board.moveAttack(givenMove.getFrom(), givenMove.getTo());
        }
        if (givenMove.getType() == MoveType.STRENGTHEN)
        {
            return board.moveStrengthen(givenMove.getFrom(), givenMove.getTo());
        }
        return false;
	}
    
    /**
     * 
     * @param mustAttack Must the returned move be an Attack move?
     * @return A Move as String
     */
    public String pickAnyMove(boolean mustAttack) {
        List<Move> moveList = gameField.getPossibleMoves(myPlayer, mustAttack);
        if (moveList.isEmpty())
            return "PASS";
        Random randomGen = new Random();
        int pick = randomGen.nextInt(moveList.size());
        
        Move move = moveList.get(pick);
        return move.getMoveAsString();
    }
}

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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
                
        TestBot bot = new TestBot();
    }
    
    public TestBot() {
        runWithEngine();
    }
    
    /*
     * Keep doing moves for one player till no longer possible
     * NOTE! Uses the Move-string convention, no JSON!
     */
    private void runSolo() {
        System.out.println(gameField.printField());
        System.out.flush();
        //System.out.println(gameField.serializeField().toJSONString());
        //System.out.flush();
        String move = pickAnyMove(true).getMoveAsString();
        System.out.println("Suggested move: " + move);
        String command = move;
		while (!command.equals(STOP_COMMAND)) {
            doMove(gameField, Move.parseString(command));
            System.out.println(gameField.printField());
            System.out.flush();
            //System.out.println(gameField.serializeField().toJSONString());
            //System.out.flush();
            move = pickAnyMove(false).getMoveAsString();
            System.out.println("Suggested move: " + move);
            command = move;
            if (command.equals("PASS"))
                command = STOP_COMMAND;
        }
    }
    
    /*
     * Given a valid JSON Board state, do two moves.
     * NOTE! Uses the Move-string convention, no JSON!
     */
    private void runAgainstBot() {
        System.out.println(gameField.printField());
        System.out.flush();
        //System.out.println("Suggested move: " + pickAnyMove(true));
        String fieldState = readcommandline();
        Move command;
		while (!fieldState.equals(STOP_COMMAND)) {
            gameField.initializeField(fieldState);
            command = pickAnyMove(true);
            doMove(gameField, command);
            System.out.println(command.getMoveAsString());
            System.out.flush();
            command = pickAnyMove(false);
            doMove(gameField, command);
            System.out.println(command.getMoveAsString());
            System.out.flush();
            fieldState = readcommandline();
        }
    }
    
    /*
     * Run against the Engine
     */
    private void runWithEngine() {
        
        String command = "";
        command = readcommandline();
        if (command.equals(STOP_COMMAND))
            return;
        JSONObject jsonObject = parseJsonString(command);
        if (jsonObject.containsKey("Color")) {
            Long color = (Long)jsonObject.get("Color"); 
            myPlayer = color.intValue();
        } else {
            throw new IllegalArgumentException("First message no Color message!");
        }
        while (true) {
            command = readcommandline();
            if (command.equals(STOP_COMMAND))
                break;
            jsonObject = parseJsonString(command);
            if (jsonObject.containsKey("Board")) {
                // Our move
                parseMoveRequest(jsonObject);
            }
            if (jsonObject.containsKey("Winner")) {
                // AI might want to use this information
                parseProcessedMove(jsonObject);
            }
        }
    }
    
    private JSONObject parseJsonString(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(jsonString);
            if (object instanceof JSONObject)
                return (JSONObject)object;
        } catch (ParseException ex) {
            Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("Illegal jsonString");
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
    
    private void parseMoveRequest(JSONObject jsonObject) {
        if (jsonObject.containsKey("Board")) {
            if (!gameField.deserializeField((JSONObject) jsonObject.get("Board"))) {
                throw new IllegalArgumentException("Board::state was not correct");
            }
        }
        if (jsonObject.containsKey("AllowedMoves")) {
            JSONArray moves = (JSONArray) jsonObject.get("AllowedMoves");
            if (moves.size() == 1) {
                // Assume attack is only move
                JSONObject move = Move.serializeMove(pickAnyMove(true));
                System.out.println(move.toJSONString());
            } else {
                // Assume we can do any move now
                JSONObject move = Move.serializeMove(pickAnyMove(false));
                System.out.println(move.toJSONString());
            }
        }
    }
    
    /*
     * Bot currently doesn't need this feedback
     * AI might want it, so we provide parser
     */
    private void parseProcessedMove(JSONObject jsonObject) {
        int playerOfThisMove;
        Move givenMove = null;
        int winner;
        if (jsonObject.containsKey("Player")) {
            Double d = (Double) jsonObject.get("Player");
            playerOfThisMove = d.intValue();
        }
        if (jsonObject.containsKey("Move")) {
            givenMove = Move.deserializeMove((JSONObject)jsonObject.get("Move"));
        }
        if (jsonObject.containsKey("Winner")) {
            Double d = (Double) jsonObject.get("Winner");
            winner = d.intValue();
        }
    }
    
    /**
     * 
     * Does a move, returns true if correctly
     * @param board
     * @param move
     * @return 
     */
	public boolean doMove(GameField board, Move givenMove) {
		if (givenMove == null) return false;
        if (givenMove.getType() == MoveType.PASS)
        {
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
    public Move pickAnyMove(boolean mustAttack) {
        List<Move> moveList = gameField.getPossibleMoves(myPlayer, mustAttack);
        if (moveList.isEmpty())
            return new Move();
        Random randomGen = new Random();
        int pick = randomGen.nextInt(moveList.size());
        
        Move move = moveList.get(pick);
        return move;
    }
}

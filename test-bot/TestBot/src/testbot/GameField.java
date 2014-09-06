/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbot;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * i/x is left to right (A to I)
 * j/y is top to bottom 
 * 
 *  Start Board:
 *   A   B   C   D   E   F   G   H   I
 *  -2   2   2   2   2   x   x   x   x
 *  -2  -3   3   3   3  -2   x   x   x
 *  -2  -3  -4   4   4  -3  -2   x   x
 *  -2  -3  -4  -2   2  -4  -3  -2   x
 *   2   3   4   2   x  -2  -4  -3  -2
 *   x   2   3   4  -2   2   4   3   2
 *   x   x   2   3  -4  -4   4   3   2
 *   x   x   x   2  -3  -3  -3   3   2
 *   x   x   x   x  -2  -2  -2  -2   2
 * 
 *  Sign gives the player owning the stone (BLACK is negative)
 *  Stone type can be LOW, MID or HIGH
 *  With Strengthen move we raise (or lower if BLACK) with 5
 *
 * @author SvZ
 */
public class GameField {
    static int EMPTY_FIELD = 0;
    static int PLAYER_WHITE = 1;
    static int PLAYER_BLACK = -1;
    static int STONE_HIGH = 3;
    static int STONE_MID = 2;
    static int STONE_LOW = 1;
    static int STRENGTH_VALUE = 4;
    private int[][] field = new int[9][9];
    
    public GameField() {
               
    }
    
    public void initializeField(JSONArray board) {
        if (board.size() != 9) {
            throw new IllegalArgumentException("Field is not 9x9!");
        }
        for (int j=0; j < 9; j++) {
            JSONArray line = (JSONArray) board.get(j);
            for (int i=0; i < 9; i++) {
                 Long value = (Long)line.get(i);
                 field[j][i] = value.intValue();
            }
        }
    }
    
    public boolean initializeField(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            Object jsonObject = parser.parse(jsonString);
            return deserializeField((JSONObject) jsonObject);
        } catch (ParseException ex) {
            Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public String printField() {
        StringBuilder sb = new StringBuilder();
        
        for (int j=0; j < 9; j++) {
            for (int i=0; i < 9; i++) {
                
                sb.append(field[j][i]);
                sb.append(' ');
            }
            sb.append('\n');
        }
        
        return sb.toString();
    }
    /* Old convention
    public JSONObject serializeField() {
        JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				jsonArray.add(field[i][j]);
			}
		}
        JSONObject object = new JSONObject();
		object.put("field", jsonArray);
        return object;
    }*/
    
    public boolean deserializeField(JSONObject boardObject) {
        if (!boardObject.containsKey("state"))   return false;
        
        Object stateObject = boardObject.get("state");
        if (stateObject instanceof JSONArray) {
            initializeField((JSONArray) stateObject);
        }
        
        return true;
    }
    
    public boolean isEmptyPosition(Location location) {
        return (field[location.y()][location.x()] == EMPTY_FIELD);
    }
    
    /**
     * 
     * @param location
     * @return How many stones are on top of each other?
     */
    public int getStoneStrength(Location location) {
        int strength = (field[location.y()][location.x()] / STRENGTH_VALUE);
        if (strength < 0 ) {
            strength = strength * -1; 
        } 
        return strength + 1;
    }
    
    /**
     * 
     * @param location
     * @return Get type of stone on this location (Is low/mid/high value)
     */
    public int getStoneType(Location location) {
        int type = (field[location.y()][location.x()] % STRENGTH_VALUE);
        if (type < 0 ) {
            return type * -1; 
        } else {            
            return type;
        }
    }
    
    public int isOfPlayer(Location location) {
        if (field[location.y()][location.x()] > 0)
            return PLAYER_WHITE;
        if (field[location.y()][location.x()] < 0)
            return PLAYER_BLACK;
        return 0;
    }
    
    /**
     * Gives the nr of stones of given type of given player
     * @param player
     * @param stoneType
     * @return 
     */
    public int nrOfStonesOfType(int stoneType, int player) {
        int count = 0;
        for (int i=0; i < 9; i++) {
            for (int j=0; j < 9; j++) {
                if ( (field[j][i] % STRENGTH_VALUE) == (player * stoneType) ) 
                    count ++;
            }
        }
        return count;
    }
    
    /**
     * 
     * @param location starting from
     * @param direction to move towards
     * @return new location after moving or null if not possible
     */
    public Location getLocationInDirection(Location location, Direction direction) {
        switch(direction) {
            case LEFT:
            {
                if (location.x() > 0) {
                    Location movedLocation = new Location(location.y(), location.x()-1);
                    return movedLocation;
                }
                return null;
            }
            case RIGHT:
            {
                if (location.x() < 8) {
                    Location movedLocation = new Location(location.y(), location.x()+1);
                    return movedLocation;
                }
                return null;
            }
            case UP:
            {
                if (location.y() > 0) {
                    Location movedLocation = new Location(location.y()-1, location.x());
                    return movedLocation;
                }
                return null;
            }
            case DOWN:
            {
                if (location.y() < 8) {
                    Location movedLocation = new Location(location.y()+1, location.x());
                    return movedLocation;
                }
                return null;
            }
            case DIAGONAL_UP:
            {
                if (location.x() > 0 && location.y() > 0) {
                    Location movedLocation = new Location(location.y()-1, location.x()-1);
                    return movedLocation;
                }
                return null;
            }
            case DIAGONAL_DOWN:
            {
                if (location.x() < 8 && location.y() < 8) {
                    Location movedLocation = new Location(location.y()+1, location.x()+1);
                    return movedLocation;
                }
                return null;
            }
            default:
                return null;
        }
    }
    
    /**
     * 
     * @param startLocation
     * @param direction
     * @return Location in given direction from startLocation which is not empty
     * returns null if not possible
     */
    public Location getNonEmptyLocationInDirection(Location startLocation, Direction direction) {
        if (isEmptyPosition(startLocation)) {
            return null;
        }
        
        Location movedLocation = getLocationInDirection(startLocation, direction);
        
        while (movedLocation != null && isEmptyPosition(movedLocation)) {
            movedLocation = getLocationInDirection(movedLocation, direction);
        }
        
        if (movedLocation != null && startLocation.equalsSimple(movedLocation)) {
            return null;
        }
        
        return movedLocation;
    }
    
    /*
     * Does some simple checks, but does not check if the move is legal
     * 
     * returns true if move has been processed
     */
    public boolean moveStrengthen(Location from, Location to) {
        if (isOfPlayer(from) != isOfPlayer(to))
            return false;
        if (from.equalsSimple(to))
            return false;
        
        int oldFromStrength = getStoneStrength(from);
        int oldToStrength = getStoneStrength(to);
        int stoneType = getStoneType(from);
        int player = isOfPlayer(to);
        
        field[from.y()][from.x()] = EMPTY_FIELD;
        field[to.y()][to.x()] = (stoneType + ((oldFromStrength + oldToStrength - 1)*STRENGTH_VALUE) ) * player;
        return true;
    }
    
        /*
     * Does some simple checks, but does not check if the move is legal
     * 
     * returns true if move has been processed
     */
    public boolean moveAttack(Location from, Location to) {
        if (isOfPlayer(from) == isOfPlayer(to))
            return false;
        
        int oldValue = field[from.y()][from.x()];
        
        field[from.y()][from.x()] = EMPTY_FIELD;
        field[to.y()][to.x()] = oldValue;
        return true;
    }
    
    /*
     * Returns all possible ATTACK and STRENGTHEN moves from given location
     * returns null if illegal given location
     */
    public List<Move> getPossibleMovesForLocation(Location from, boolean mustAttack) {
        if (isEmptyPosition(from)) {
            return null;
        }
        List<Move> moveList = new LinkedList<Move>();
        
        for (Direction d : Direction.values()) {
            Location towards = getNonEmptyLocationInDirection(from, d);
            
            if (towards == null)
                continue;
            
            // If we are positive after multiplication, the fields belonged to the same player
            boolean strengthen = field[from.y()][from.x()] * field[towards.y()][towards.x()] > 0;
            
            if (mustAttack && strengthen)
                continue;
            
            Move move = new Move(from, towards, strengthen ? MoveType.STRENGTHEN : MoveType.ATTACK);
            
            moveList.add(move);    
        }
        
        return moveList;
    }
    
    /*
     * Get all possible moves for player 
     * +1 = White
     * -1 = Black
     */
    public List<Move> getPossibleMoves(int player, boolean mustAttack) {
        List<Move> moveList = new LinkedList<Move>();
        
        for (int i=0; i < 9; i++) {
            for (int j=0; j < 9; j++) {
                Location location = new Location(j,i);
                
                if (isEmptyPosition(location)) 
                    continue;
                                
                if (player != isOfPlayer(location))
                    continue;
                
                moveList.addAll(getPossibleMovesForLocation(location, mustAttack));
            }
        }
        
        return moveList;
    }
    


    
}

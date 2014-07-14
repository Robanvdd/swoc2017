/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbot;

import java.util.LinkedList;
import java.util.List;

/**
 * i is left to right (A to I)
 * j is top to bottom 
 *
 * @author SvZ
 */
public class GameField {
    static int ILLEGAL_FIELD = 999;
    static int EMPTY_FIELD = 0;
    static int PLAYER_WHITE = 1;
    static int PLAYER_BLACK = -1;
    static int STONE_HIGH = 4;
    static int STONE_MID = 3;
    static int STONE_LOW = 2;
    static int STRENGTH_VALUE = 5;
    private int[][] field = new int[9][9];
    
    public GameField() {
        initializeField();
        
    }
        
    private void initializeField() {
        //Todo: Add player stones
        for (int i=0; i < 9; i++) {
            for (int j=0; j < 9; j++) {
                field[i][j] = 0;
                
                if (i-j >=5 || j-i >=5) {
                    field[i][j] = ILLEGAL_FIELD;
                }
            }
        }
        field[4][4] = ILLEGAL_FIELD;
    }
    
    // Assuming the format gained is same as we use
    public void initializeField(int[][] board) {
        if (board.length != 9 || board[0].length != 9) {
            throw new IllegalArgumentException("Field is not 9x9!");
        }
        for (int i=0; i < 9; i++) {
            for (int j=0; j < 9; j++) {
                field[i][j] = board[i][j];
            }
        }
    }
    
    public String printField() {
        StringBuilder sb = new StringBuilder();
        
        for (int i=0; i < 9; i++) {
            for (int j=0; j < 9; j++) {
                
                sb.append(field[i][j]);
                sb.append(' ');
            }
            sb.append('\n');
        }
        
        return sb.toString();
    }
    
    public boolean isIllegalPosition(Location location) {
        return (field[location.x()][location.y()] == ILLEGAL_FIELD);
    }
    
    public boolean isEmptyPosition(Location location) {
        return (field[location.x()][location.y()] == EMPTY_FIELD);
    }
    
    public int getStoneStrength(Location location) {
        return (field[location.x()][location.y()] / STRENGTH_VALUE) + 1;
    }
    
    public int getStoneType(Location location) {
        int type = (field[location.x()][location.y()] % STRENGTH_VALUE);
        if (type < 0 ) {
            return type * -1; 
        } else {            
            return type;
        }
    }
    
    public int isOfPlayer(Location location) {
        if (field[location.x()][location.y()] > 0)
            return PLAYER_WHITE;
        if (field[location.x()][location.y()] < 0)
            return PLAYER_BLACK;
        return 0;
    }
    
    public int nrOfStones(int player, int stone) {
        int count = 0;
        for (int i=0; i < 9; i++) {
            for (int j=0; j < 9; j++) {
                if ( (field[i][j] % STRENGTH_VALUE) == (player * stone) ) 
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
            case UP:
            {
                if (location.y() > 0) {
                    Location movedLocation = new Location(location.x(), location.y()-1);
                    if (!isIllegalPosition(movedLocation))
                        return movedLocation;
                }
                return null;
            }
            case DOWN:
            {
                if (location.y() < 9) {
                    Location movedLocation = new Location(location.x(), location.y()+1);
                    if (!isIllegalPosition(movedLocation))
                        return movedLocation;
                }
                return null;
            }
            case LEFT:
            {
                if (location.x() > 0) {
                    Location movedLocation = new Location(location.x()-1, location.y());
                    if (!isIllegalPosition(movedLocation))
                        return movedLocation;
                }
                return null;
            }
            case RIGHT:
            {
                if (location.x() < 9) {
                    Location movedLocation = new Location(location.x()+1, location.y());
                    if (!isIllegalPosition(movedLocation))
                        return movedLocation;
                }
                return null;
            }
            case DIAGONAL_UP:
            {
                if (location.y() > 0 && location.x() > 0) {
                    Location movedLocation = new Location(location.x()-1, location.y()-1);
                    if (!isIllegalPosition(movedLocation))
                        return movedLocation;
                }
                return null;
            }
            case DIAGONAL_DOWN:
            {
                if (location.y() < 9 && location.x() < 9) {
                    Location movedLocation = new Location(location.x()+1, location.y()+1);
                    if (!isIllegalPosition(movedLocation))
                        return movedLocation;
                }
                return null;
            }
            default:
                return null;
        }
    }
    
    public Location getNonEmptyLocationInDirection(Location location, Direction direction) {
        if (isIllegalPosition(location) || isEmptyPosition(location)) {
            return null;
        }
        
        Location movedLocation = location;
        
        while (movedLocation != null && isEmptyPosition(movedLocation)) {
            movedLocation = getLocationInDirection(movedLocation, direction);
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
        
        int oldFromStrength = getStoneStrength(from);
        int oldToStrength = getStoneStrength(to);
        int stoneType = getStoneType(from);
        int player = isOfPlayer(to);
        
        field[from.x()][from.y()] = EMPTY_FIELD;
        field[to.x()][to.y()] = (stoneType + ((oldFromStrength + oldToStrength - 1)*STRENGTH_VALUE) ) * player;
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
        
        int oldValue = field[from.x()][from.y()];
        
        field[from.x()][from.y()] = EMPTY_FIELD;
        field[to.x()][to.y()] = oldValue;
        return true;
    }
    
    /*
     * Returns all possible ATTACK and STRENGTHEN moves from given location
     * returns null if illegal given location
     */
    public List<Move> getPossibleMovesForLocation(Location from) {
        if (isIllegalPosition(from) || isEmptyPosition(from)) {
            return null;
        }
        List<Move> moveList = new LinkedList<Move>();
        
        for (Direction d : Direction.values()) {
            Location towards = getNonEmptyLocationInDirection(from, d);
            
            if (towards == null)
                continue;
            
            // If we are positive after multiplication, the fields belonged to the same player
            boolean strengthen = field[from.x()][from.y()] * field[towards.x()][towards.y()] > 0;
            
            Move move = new Move(from, towards, strengthen ? Move.TYPE.STRENGTHEN : Move.TYPE.ATTACK);
            
            moveList.add(move);    
        }
        
        return moveList;
    }
    
    /*
     * Get all possible moves for player 
     * +1 = White
     * -1 = Black
     */
    public List<Move> getPossibleMoves(int player) {
        List<Move> moveList = new LinkedList<Move>();
        
        for (int i=0; i < 9; i++) {
            for (int j=0; j < 9; j++) {
                Location location = new Location(i,j);
                
                if (isIllegalPosition(location) || isEmptyPosition(location)) 
                    continue;
                                
                if (player != isOfPlayer(location))
                    continue;
                
                moveList.addAll(getPossibleMovesForLocation(location));
            }
        }
        
        return moveList;
    }
    


    
}

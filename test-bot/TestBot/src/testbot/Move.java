/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbot;

/**
 *
 * @author SvZ
 */
public class Move {
    private Location _from;
    private Location _to;
    private MoveType _type = MoveType.PASS;
    
    public Move() {
        _from = new Location(4,4);
        _to = new Location(4,4);
    }
    
    public Move(Location from, Location to, MoveType type) {
        _from = from;
        _to = to;
        _type = type;
    }
    
    public MoveType getType() {
        return _type;
    }
    
    public Location getFrom() {
        return _from;
    }
    
    public Location getTo() {
        return _to;
    }
    
    public boolean equalsSimple(Move other) {
        if (other.getType() != _type) return false;
        if (!other.getFrom().equalsSimple(_from)) return false;
        if (!other.getTo().equalsSimple(_to)) return false;
        return true;
    }
    
    // TODO: Convert to actual move-convention 
    public String getMoveAsString() {
        switch (_type) {
            case PASS:
               return "PASS";
            case STRENGTHEN:
                return "S " + _from.toString() + " " + _to.toString();
            case ATTACK:
                return "A " + _from.toString() + " " + _to.toString();
        }
        return "";
    }
    
    public static Move parseString(String input) {
        if (input == null || input.trim().equals("")) return null;
        
        if (input.startsWith("PASS")) return new Move();
        
        if (input.startsWith("S "))
        {
            String[] stringArray = input.split(" ");
            // 0 = TYPE, 1 = Location from, 2 = Location to
            return new Move(new Location(stringArray[1]), new Location(stringArray[2]), MoveType.STRENGTHEN);
        }
        
        if (input.startsWith("A "))
        {
            String[] stringArray = input.split(" ");
            // 0 = TYPE, 1 = Location from, 2 = Location to
            return new Move(new Location(stringArray[1]), new Location(stringArray[2]), MoveType.ATTACK);
        }
        
        return null;
    }
}

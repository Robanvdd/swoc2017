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
    private TYPE _type = TYPE.PASS;
    
    public enum TYPE {
        ATTACK,
        STRENGTHEN,
        PASS
    }
    
    public Move() {
        _from = new Location(4,4);
        _to = new Location(4,4);
    }
    
    public Move(Location from, Location to, TYPE type) {
        _from = from;
        _to = to;
        _type = type;
    }
    
    // TODO: Convert to actual move-convention 
    public String getMoveAsString() {
        switch (_type) {
            case PASS:
               return "PASS";
            case STRENGTHEN:
                return "S " + _from.toString() + "->" + _to.toString();
            case ATTACK:
                return "A " + _from.toString() + "->" + _to.toString();
        }
        return "";
    }
}

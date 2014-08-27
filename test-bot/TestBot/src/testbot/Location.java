/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbot;

/**
 *
 * @author SvZ
 */
public class Location {
    
    private int _x;
    private int _y;
    
    public Location(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9) {
            throw new IllegalArgumentException("Creating Location object with illegal value");
        }
        _x = x;
        _y = y;
    }
    
    public Location(String inputString) {
        if (inputString == null || inputString.trim().equals("")) 
            throw new IllegalArgumentException("Creating Location object with illegal String value");
        
        String[] stringArray = inputString.split("-");
        int x = Integer.parseInt(stringArray[0]);
        int y = Integer.parseInt(stringArray[1]);
        _x = x;
        _y = y;
    }
    
    public int x() {
        return _x;
    }
    
    public int y() {
        return _y;
    }
    
    public boolean equalsSimple(Location other) {
        if (_x == other.x() && _y == other.y()) return true;
        return false;
    }
    
    @Override
    public String toString() {
        return _x + "-" + _y;
    }
    
}

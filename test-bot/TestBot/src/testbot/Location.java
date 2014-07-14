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
    
    public int x() {
        return _x;
    }
    
    public int y() {
        return _y;
    }
    
    @Override
    public String toString() {
        return _x + " " + _y;
    }
    
}

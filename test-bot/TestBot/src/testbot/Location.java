/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbot;

import org.json.simple.JSONObject;
/**
 *
 * @author SvZ
 */
public class Location {
    
    private int _x;
    private int _y;
    
    public Location(int y, int x) {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
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
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            throw new IllegalArgumentException("Creating Location object with illegal value");
        }
        _x = x;
        _y = y;
    }
    
    public Location(JSONObject jsonObject) {
        int x = -1; 
        int y = -1;
        if (jsonObject.containsKey("X")) {
            x = Integer.valueOf((String)jsonObject.get("X"));
        }
        if (jsonObject.containsKey("Y")) {
            y = Integer.valueOf((String)jsonObject.get("Y"));
        }
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
    
    public boolean equalsSimple(Location other) {
        if (_x == other.x() && _y == other.y()) return true;
        return false;
    }
    
    @Override
    public String toString() {
        return _x + "-" + _y;
    }
    
    public JSONObject asJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("X", x());
        jsonObject.put("Y", y());
        return jsonObject;
    }
    
}

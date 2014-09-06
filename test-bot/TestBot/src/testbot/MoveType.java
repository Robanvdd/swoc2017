/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbot;

/**
 *
 * @author SvZ
 */
    public enum MoveType {
        ATTACK(1),
        STRENGTHEN(2),
        PASS(0);
        
        private final int value;
        
        private MoveType(int v) {
            value = v;
        }
        
        public int value() {
            return value;
        }
    }

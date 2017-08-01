package com.sioux;

public class MacroGameLogic {
    //this class takes care of updating game state.
    private MacroGameLogic(){}

    private static class LazyHolder{
        static final MacroGameLogic INSTANCE = new MacroGameLogic();
    }

    public static MacroGameLogic getInstance(){
        return LazyHolder.INSTANCE;
    }

    public void Move(){
        System.out.print("[Game]::Doing a Move\n");
    }

    public void Conquer() {
        System.out.print("[Game]::Going to Conquer\n");
    }
}

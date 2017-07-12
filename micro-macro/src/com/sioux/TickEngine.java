package com.sioux;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//import java.util.Timer;


/**
 * Created by Michael on 05/07/2017.
 */
public class TickEngine {

    private int tickDuration = 5000;
    private ArrayList<TickListener> listners = new ArrayList<TickListener>();

    private static final TickEngine instance = new TickEngine();
    private  TickEngine() { }
    public static TickEngine GetInstance(){
        return instance;
    }

    public void AddListner(TickListener listner){
        listners.add(listner);
    }

    public void Start(){
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                HandleTick();
            }
        };
        new Timer(tickDuration, taskPerformer).start();
    }

    public void HandleTick(){
        for (TickListener t : listners){
            t.TickUpdate();
        }
    }
}

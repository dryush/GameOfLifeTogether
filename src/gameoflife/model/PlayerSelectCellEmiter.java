package gameoflife.model;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dryush
 */
public abstract class PlayerSelectCellEmiter {
    public void start(){
        
    }
    public void end(){
        
    }
    private ArrayList<IPlayerCellSelectListener> listeners;
    public void addListener(IPlayerCellSelectListener listener){
        this.listeners.add(listener);
    }
    
    protected void firePlayerSelectCellEvent(Cell cell){
        for ( IPlayerCellSelectListener l : listeners){
            l.onPlayerSelectCell(cell);
        }
    }
}

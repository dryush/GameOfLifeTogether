/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

import java.util.ArrayList;

/**
 *
 * @author dryush
 */
public class Cell {
    private Creature creature = null;
    private ArrayList<Cell> nearbyCells = new ArrayList<>();
    
    public Cell(){
    }
    
    public void setCreature(Creature creature){
        this.creature = creature;
    }
    
    public Creature getCreature(){
        return creature;
    }
    
    public void removeCreature(){
        creature = null;
    }
    
    public ArrayList<Cell> getNearbyCells(){
        return (ArrayList<Cell>) nearbyCells.clone();
    }
    
    public void setNearbyCells(ArrayList<Cell> cells){
        this.nearbyCells = cells;
    }
    
}

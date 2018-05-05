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
    
    ///Только для дебага
    public int x, y; 
    
    public Cell(){
    }
    
    public void setCreature(Creature creature){
        this.creature = creature;
        this.creature.addListener(creatureEventsResender);
        this.creatureEventsResender.fireCrearureSetEvent();
    }
    
    public Creature getCreature(){
        return creature;
    }
    
    public void removeCreature(){
        creature.removeListener(creatureEventsResender);
        creature = null;
    }
    
    public ArrayList<Cell> getNearbyCells(){
        return (ArrayList<Cell>) nearbyCells.clone();
    }
    
    public ArrayList<Creature> getNearbyCreatures(){
        ArrayList<Creature> creatures = new ArrayList<>();
        for (Cell nearbyCell : getNearbyCells()){
            Creature nearbyCellCreature = nearbyCell.getCreature();
            if ( nearbyCellCreature != null){
                creatures.add(nearbyCellCreature);
            }
        }
        return creatures;
    }
    
    public ArrayList<Cell> nearbyFreeCells(){
        ArrayList<Cell> freeCells = new ArrayList<>();
        for (Cell nearbyCell : getNearbyCells()){
            if ( nearbyCell.getCreature() == null){
                freeCells.add(nearbyCell);
            }
        }
        return freeCells;
    }
    
    public void setNearbyCells(ArrayList<Cell> cells){
        this.nearbyCells = cells;
    }
    
    private CreatureEventsResender creatureEventsResender = new CreatureEventsResender();
    public void addListener(ICellListener l){
        creatureEventsResender.addListener(l);
    }
    
    private class CreatureEventsResender implements ICreatureListener{

        private ArrayList<ICellListener> listeners = new ArrayList<>();
        public void addListener(ICellListener l){
            listeners.add(l);
        }
        
        private void fireCrearureSetEvent(){
            listeners.forEach((l) -> l.onCellCreatureSet(Cell.this));
        }
        
        private void fireCreatureStageChanged(){
            listeners.forEach(l -> l.onCellCreatureStageChanged(Cell.this));
        }
        
        private void fireDestroyEvent(){
            listeners.forEach((l) -> l.onCellCreatureDestroy(Cell.this));
        }
        
        @Override
        public void onLiveStageChanged(Creature creature) {
            if (creature.getLiveStage() != Creature.LiveStage.DESTROIED){
                fireCreatureStageChanged();
            } else if ( creature.getLiveStage() == Creature.LiveStage.DESTROIED){
                
                
                if (Cell.this.creature == creature){
                    Cell.this.removeCreature();
                }
                
                fireDestroyEvent();
                
            }
        }
        
    }
}

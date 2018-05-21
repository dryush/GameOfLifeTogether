/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author dryush
 */
public class Creature {
    
    private final Random rand = new Random();
    
    public static enum LiveStage{
        BIRTH,
        LIVE,
        DIE,
        DESTROIED,
    }

    private Cell cell;
    private LiveStage liveStage = LiveStage.BIRTH;
    
    void setLiveStage(LiveStage stage){
        liveStage = stage;
        fireStatusChanged();
    }
    
    public LiveStage getLiveStage(){
        return liveStage;
    }
    
    private Colony colony;
    public Colony getColony(){
        return colony;
    }

    public Creature(Cell cell, Colony colony){
        
        this.cell = cell;
        this.colony = colony;
        this.addListener(colony);
        setLiveStage(LiveStage.BIRTH);
    }
    
    public void liveEpoch(){
        
        if ( liveStage == LiveStage.LIVE){
            int nearbyCreturesCount = 0;
            for ( Cell nearbyCell : this.cell.getNearbyCells()){
                if ( nearbyCell.getCreature() == null){
                    initSpawning(nearbyCell);
                } else if (nearbyCell.getCreature().getLiveStage() == LiveStage.LIVE ||
                        nearbyCell.getCreature().getLiveStage() == LiveStage.DIE ){
                    nearbyCreturesCount ++;
                }
            }
            
            if ( nearbyCreturesCount > 3 || nearbyCreturesCount <= 1){
                setLiveStage(LiveStage.DIE);
            }
        }
    }
    
    public void endEpoch(){
        if ( liveStage == LiveStage.BIRTH){
            setLiveStage(LiveStage.LIVE);
        } else if (liveStage == LiveStage.DIE){
            this.destroy();
        }
    }
 
    
    private void destroy(){
        this.cell = null;
        setLiveStage(LiveStage.DESTROIED);
    }
    
    private void initSpawning(Cell cell){
        HashMap<Colony,ArrayList<Creature>> spawnedCreatures = new HashMap<>();
        HashMap<Colony, Integer> spawnedCreaturesCount = new HashMap<>();
        //Для каждой соседней клетки, отсноительно текущей
        //ArrayList<Cell> nearbyCells = cell.getNearbyCells();
        for (Creature  nearbyCreature : cell.getNearbyCreatures()){
            //Если в ней есть существо
            if ( nearbyCreature.isLive()){
                //оно пробует размножиться в текущую
                Creature spawnedCreature = nearbyCreature.trySpawnCreature(cell);
                if (spawnedCreature != null){
                        ArrayList curColonyCreature = spawnedCreatures.getOrDefault(spawnedCreature.colony, new ArrayList<>());
                        curColonyCreature.add(spawnedCreature);
                        spawnedCreatures.put(spawnedCreature.getColony(), curColonyCreature);
                }
            }
        }
        
        int maxOneColonyCount = 0;
        
        for ( ArrayList oneColonyCreatires : spawnedCreatures.values()){
            maxOneColonyCount = Math.max(maxOneColonyCount, oneColonyCreatires.size());
        }
        
        if( maxOneColonyCount > 0){
            ArrayList<ArrayList<Creature>> maxColonyCreatures = new ArrayList<>();

            for (Map.Entry<Colony, ArrayList<Creature>> colonySpawnedCreatures : spawnedCreatures.entrySet()){
                if ( colonySpawnedCreatures.getValue().size() == maxOneColonyCount ){
                    maxColonyCreatures.add(colonySpawnedCreatures.getValue());
                }
            }

            if (maxColonyCreatures.size() > 0)
                cell.setCreature( 
                    maxColonyCreatures.get( rand.nextInt(maxColonyCreatures.size())).
                            get(rand.nextInt(maxOneColonyCount)));
        }
    }
    
    private Creature trySpawnCreature(Cell cell){
        Creature creature = null;
        int neighborsCount = 0;
        ArrayList<Cell> nearbyCells = cell.getNearbyCells();
        //Считаю соседей клетки, (в том числе себя)
        for ( Cell c : nearbyCells ){
            if (c.getCreature() != null && ( c.getCreature().getLiveStage() == LiveStage.LIVE || 
                    c.getCreature().getLiveStage() == LiveStage.DIE ) ){
                neighborsCount++;
            }
        }
        
        
        
        //Должно родиться, если 3
        
        //if ( neighborsCount >= 3){
        if ( neighborsCount == 3){
            creature = new Creature(cell, colony);
        }
        return creature;
    }
    
    public boolean isLive(){
        return liveStage == LiveStage.LIVE;
    }
    
    private ArrayList<ICreatureListener> listeners = new ArrayList<>();
    
    public void addListener(ICreatureListener listener){
        listeners.add(listener);
    }
    public void removeListener(ICreatureListener listener){
        listeners.remove(listener);
    }
    
    private void fireStatusChanged(){
        ((ArrayList<ICreatureListener>)listeners.clone()).forEach((l) -> l.onLiveStageChanged(this));
    }
    
    /*
    public static class CreatureEvent {
        private Creature creature;
        private CreatureEvent ( Creature c){
            creature = c;
        }
        public Creature getCreature(){
            return creature;
        }
    }
    */
}

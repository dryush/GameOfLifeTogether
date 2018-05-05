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
public class Colony implements ICreatureListener{
    
    private ArrayList<Creature> creatures = new ArrayList<Creature>();

    public ArrayList<Creature> getCreatures(){
        return (ArrayList<Creature>) creatures.clone();
    }
    public int getCreaturesCount(){
        return creatures.size();
    }
    public boolean isFromThisColony(Creature creature){
        return creatures.contains(creature);
    }
    
    
    @Override
    public void onLiveStageChanged(Creature creature) {
        switch (creature.getLiveStage()){
            case BIRTH : creatures.add(creature); break;
            case LIVE: break;
            case DIE: break;
            case DESTROIED: creatures.remove(creature);
        }
    }
    
    
    public Colony clone(){
        Colony clon =  new Colony();
        clon.creatures = (ArrayList<Creature>) this.creatures.clone();
        return clon;
    }

}

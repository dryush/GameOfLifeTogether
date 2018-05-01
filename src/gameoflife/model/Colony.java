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
    
    @Override
        public void onBirth(Creature creature){
        }
    @Override
        public void onLive(Creature creature){
            creatures.add(creature);
        }
    @Override
        public void onDie(Creature creature){
            creatures.remove(creature);
        }
    @Override
        public void onDestroy(Creature creature){
        }
    
}

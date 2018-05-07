/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

/**
 *
 * @author dryush
 */
public class God {
    
    private Colony colony;
    public Colony getColony(){
        return colony;
    }
    private int actionsCount;
    public void setActionsCount(int count){
        actionsCount = count;
    }
    public int getActionsCount(){
        return actionsCount;
    }
    public boolean isHaveActions(){
        return actionsCount > 0;
    }
    private void incrementActions(){
        actionsCount++;
    }
    private void decrimentActions(){
        if ( actionsCount > 0){
            actionsCount--;
        }else {
            throw new RuntimeException("Попытка сделать действие, уводящее кол-во действий в минус");
        }
    }
    public God(){
        colony = new Colony();
    }
    
    private void createCreature(Cell c){      
        Creature cellCreature = c.getCreature();
        if ( cellCreature == null){
            decrimentActions();
            Creature newCreature = new Creature(c, colony);
            c.setCreature(newCreature);
            
        } else if ( cellCreature.getLiveStage() == Creature.LiveStage.DIE){
            incrementActions();
            cellCreature.setLiveStage(Creature.LiveStage.LIVE);
            
        } else {
            throw new RuntimeException("Нельзя поставить существо на место другого живого существа");
        }
        
    }
    
    private void killCreature(Cell c){
        Creature cellCreature = c.getCreature();
        if ( cellCreature == null){
            throw new RuntimeException("Нельзя убить существо в пустой клетке");
        } else if ( cellCreature.getLiveStage() == Creature.LiveStage.BIRTH){
            incrementActions();
            
            cellCreature.setLiveStage(Creature.LiveStage.DIE);
            cellCreature.endEpoch();
            
            
        } else if ( cellCreature.getLiveStage() == Creature.LiveStage.LIVE){
            decrimentActions();
            cellCreature.setLiveStage(Creature.LiveStage.DIE);
            
        } else if ( cellCreature.getLiveStage() == Creature.LiveStage.DIE){
            throw new RuntimeException("То, что мертво - умереть не может!");
        }
    }
    
    public void doAction(Cell c){
        Creature cellCreature = c.getCreature();
        if ( cellCreature != null && ( 
                cellCreature.getLiveStage() == Creature.LiveStage.LIVE ||
                cellCreature.getLiveStage() == Creature.LiveStage.BIRTH)){
            killCreature(c);
        } else if ( cellCreature == null ||
                cellCreature.getLiveStage() == Creature.LiveStage.DIE ){
            createCreature(c);
        }
    }
    
}

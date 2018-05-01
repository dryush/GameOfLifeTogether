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
        actionsCount--;
    }
    public God(){
        colony = new Colony();
    }
    
    private void createCreature(Cell c){      
        Creature cellCreature = c.getCreature();
        if ( cellCreature == null){
            Creature newCreature = new Creature(c, colony);
            c.setCreature(newCreature);
            decrimentActions();
        } else if ( cellCreature.getLiveStage() == Creature.LiveStage.DIE){
            cellCreature.setLiveStage(Creature.LiveStage.BIRTH);
            incrementActions();
        } else {
            throw new RuntimeException("Нельзя поставить существо на место другого живого существа");
        }
        
    }
    
    private void killCreature(Cell c){
        Creature cellCreature = c.getCreature();
        if ( cellCreature == null){
            throw new RuntimeException("Нельзя убить существо в пустой клетке");
        } else if ( cellCreature.getLiveStage() == Creature.LiveStage.BIRTH){
            cellCreature.setLiveStage(Creature.LiveStage.DIE);
            decrimentActions();
        } else if ( cellCreature.getLiveStage() == Creature.LiveStage.LIVE){
            cellCreature.setLiveStage(Creature.LiveStage.DIE);
            incrementActions();
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

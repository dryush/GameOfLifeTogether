/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View;

import gameoflife.model.Colony;
import gameoflife.model.Creature;
import gameoflife.model.God;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.awt.Color;
import java.util.Iterator;

/**
 *
 * @author dryush
 */
public class GameColors {
    
    
    private GameColors(){   
    }
    
    private static GameColors instance = new GameColors();
    
    public static GameColors getInstance(){
        return instance;
    }
    
    HashMap<God, Color> cellColors = new HashMap<>();
    public Color getCellColor(God god){
        return cellColors.getOrDefault(god, Color.BLUE);
    }
    public void setCellColor(God god, Color color){
        cellColors.put(god, color);
    }
    
    HashMap<Colony,HashMap<Creature.LiveStage, Color>> creatureColors = new HashMap<>() ;
    public void setCreatureColor(Colony colony, Color color){
        HashMap<Creature.LiveStage, Color> colors = new HashMap<>();
        colors.put(Creature.LiveStage.BIRTH, color.brighter().brighter());
        colors.put(Creature.LiveStage.LIVE, color.darker().darker());
        colors.put(Creature.LiveStage.DIE, color.darker().darker().darker().darker().darker());
        creatureColors.put(colony, colors);
    }
    
    public Color getBirthCreatureColor(Colony colony){
        return getCreatureColor(colony, Creature.LiveStage.BIRTH);
    }
    
    public Color getLiveCreatureColor(Colony colony){
        return getCreatureColor(colony, Creature.LiveStage.LIVE);
    }
    
    public Color getDieCreatureColor(Colony colony){
        return getCreatureColor(colony, Creature.LiveStage.DIE);
    }
    
    public Color getCreatureColor(Colony colony, Creature.LiveStage liveStage){
        Color creatureColor = null;
        try {
            creatureColor = creatureColors.get(colony).get(liveStage);
        } catch (Exception e) {
           creatureColor = Color.DARK_GRAY;
        } finally {
            return creatureColor;
        }
    }
    
    private Set<God> getGods(){
        return cellColors.keySet();
    }
    
    public Color getCreatureColor(Creature creature){
        return getCreatureColor(creature.getColony(), creature.getLiveStage());
    }
}

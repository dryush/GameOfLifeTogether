/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View;

import gameoflife.model.Cell;
import gameoflife.model.Colony;
import gameoflife.model.God;
import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author dryush
 */
public class Players {
    
    public Players(){
    }
    
    public static class PlayerParameters{
        private String name = "Игрок";
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }
        private Color color = Color.CYAN;
        public void setColor( Color color){
            this.color = color;
        }
        public Color getColor(){
            return color;
        }
    }
    
    private HashMap<God, PlayerParameters> godsParams = new HashMap<>() ;
    private HashMap<Colony, God> godByColony  = new HashMap<>();
    public PlayerParameters getPlayerParameters(God god){
        return godsParams.get(god);
    }
    public PlayerParameters getPlayerParameters(Colony colony){
        return godsParams.get( godByColony.get(colony));
    }
    
    public void putPlayerParametrs(God god,PlayerParameters params){
        this.godsParams.put(god, params);
        this.godByColony.put(god.getColony(), god);
    }
    
    public void putPlayerParametrs(Collection<? extends God> gods, Collection<? extends PlayerParameters> params){
        
        Iterator<God> iGod = (Iterator<God>) gods.iterator();
        Iterator<PlayerParameters> iParam = (Iterator<PlayerParameters>) params.iterator(); 
        while ( iGod.hasNext() && iParam.hasNext()){
            putPlayerParametrs(iGod.next(), iParam.next());
        }   
    }
    
    public Collection<God> getGods(){
        return godsParams.keySet();
    }
}

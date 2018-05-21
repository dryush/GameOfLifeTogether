/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View.creaturePainter;

import gameoflife.model.Creature;
import java.util.HashMap;

/**
 *
 * @author Dryush
 */
public class CreaturePaintersPool {
    private HashMap<Class, CreaturePainter> painters = new HashMap<>();
    
    private CreaturePaintersPool(){
        painters.put(Creature.class, new CreaturePainter());
    }
    private CreaturePainter get(Creature creature){
        return painters.get(creature.getClass());
    }
    
    private static CreaturePaintersPool instance = new CreaturePaintersPool();
    
    public static CreaturePaintersPool getInstance(){
        return instance;
    }
    public static CreaturePainter getCreaturePainter(Creature creature){
        return instance.get( creature);
    }
}

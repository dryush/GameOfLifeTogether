/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author admin
 */
public class GodController {
    Collection<IGodControllerListener> listeners = new ArrayList();
    public void addListener( IGodControllerListener l){
        listeners.add(l);
    }
    
    protected void fireGodSelectCell( Cell cell){
        for ( IGodControllerListener l : listeners){
            l.onGodSelectCell(cell);
        }
    }
    
    protected void fireGodEndMove(){
        for ( IGodControllerListener l : listeners){
            l.onGodEndMove();
        }
    }
}

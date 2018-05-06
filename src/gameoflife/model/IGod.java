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
public interface IGod {
    public Colony getColony();
    
    public void setActionsCount(int count);
    
    public int getActionsCount();
    
    public boolean isHaveActions();
    
    public void doAction(Cell c);
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Infrastructure;

/**
 *
 * @author dryush
 */
public class Size {
    private final int width;
    private final int height;
    
    public Size(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

import Infrastructure.Size;
import java.util.ArrayList;

/**
 *
 * @author dryush
 */
public class Field {
    private Cell cells[][] = new Cell[0][0];
    private Size size = new Size(0,0); 
    private ArrayList<God> acsess[][];
    private void allocateCells(ArrayList<God> gods){
        for ( God god : gods){
            for(int iX = 0; iX < (size.getWidth()+gods.size()-1) / gods.size(); iX++){
                for( int iY =0; iY < (size.getHeight()+gods.size()-1) / gods.size(); iY++){
                    acsess[iX][iY].add(god);
                }
            }
        }
    }
    
    public boolean[][] geCellAcsessForGod(God god){
        boolean isHaveAcsess [][] = new boolean[size.getWidth()][size.getHeight()];
        for(int iX = 0; iX < size.getWidth(); iX++){
            for( int iY = 0; iY < size.getHeight(); iY++){
                isHaveAcsess[iX][iY] = acsess[iX][iY].contains(god);
            }
        }
        return isHaveAcsess;
    }
    
    protected Field(Size size){
        cells = new Cell[size.getWidth()][size.getHeight()];
    }
    
    public static abstract class FieldShape{
        abstract protected void setNearbyCells(Field field);
    }
    
    public static class FlatSphere extends FieldShape{
        @Override
        protected void setNearbyCells(Field f) {
            Size size = f.size;
            Cell[][] field = f.cells;
            for ( int iCellX = 0; iCellX < size.getWidth(); iCellX++){
                for ( int iCellY = 0; iCellY < size.getHeight(); iCellY++ ){
                    ArrayList<Cell> nearbyCells = new ArrayList<Cell>();
                        
                    for ( int iNearbyX = -1; iNearbyX <= 1; iNearbyX++){
                        for (int iNearbyY = -1; iNearbyY <= 1; iNearbyY++){
                            if ( !(iNearbyX == 0 && iNearbyY == 0)){
                                int iNearbyCellX = (size.getWidth() + (iCellX + iNearbyX)) % size.getWidth() ;
                                int iNearbyCellY = (size.getHeight() + (iCellY + iNearbyY)) % size.getHeight() ;
                                Cell nearbyCell = field[iNearbyCellX][iNearbyCellY]; 
                                nearbyCells.add( nearbyCell );
                            }
                        }
                    }
                    field[iCellX][iCellY].setNearbyCells(nearbyCells);
                }
            }
        }
    }
    

    public static class FieldBuilder{
        private FieldShape shape = new FlatSphere();
        public FieldBuilder setShape(FieldShape shape){
            this.shape = shape;
            return this;
        }
        private Size size;
        public FieldBuilder setSize(Size size){
            this.size = size;
            return this;
        }
        private ArrayList<God> gods = new ArrayList<God>();
        public FieldBuilder setGods(ArrayList<God> gods ){
            this.gods = gods;
            return this;
        }
        public Field build(){
            Field f = new Field(size);
            shape.setNearbyCells(f);
            f.allocateCells(gods);
            return f;
        }
    }
}

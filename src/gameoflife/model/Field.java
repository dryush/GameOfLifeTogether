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
    public Cell[][] getCells(){
        return cells;
    }
    private Size size = new Size(0,0); 
    public Size getSize(){
        return size;
    }
    private ArrayList<God>[][] access = new ArrayList[0][0];
    
    public ArrayList<God>[][] getCellsAccesses (){
        ArrayList<God>[][] accesseCopy = new ArrayList[size.getHeight()][size.getWidth()];
        for (int iX = 0; iX < size.getWidth(); iX++){
            for( int iY = 0; iY < size.getHeight(); iY++){
                accesseCopy[iY][iX] = (ArrayList<God>) access[iY][iX].clone();
            }
        }
        return accesseCopy;
    }
    
    private void allocateCells(ArrayList<God> gods){
        access = new ArrayList[size.getHeight()][size.getWidth()];
        for (int iX = 0; iX < size.getWidth(); iX++){
            for( int iY = 0; iY < size.getHeight(); iY++){
                access[iY][iX] = new ArrayList<>();
            }
        }
        
        for ( int iGod = 0; iGod < gods.size(); iGod++){
            
            int partWidth = (size.getWidth()+gods.size()-1) / gods.size();
            int partHeight = size.getHeight();
            
            for(int iX = 0; iX < partWidth; iX++){
                 for( int iY =0; iY < partHeight; iY++){
                    access[iY][iX+iGod*(partWidth-size.getWidth()%gods.size())].add(gods.get(iGod));
                }
            }
        }
    }
    
    public boolean[][] geCellAcsessForGod(God god){
        boolean isHaveAcsess [][] = new boolean[size.getWidth()][size.getHeight()];
        for(int iX = 0; iX < size.getWidth(); iX++){
            for( int iY = 0; iY < size.getHeight(); iY++){
                isHaveAcsess[iY][iX] = access[iX][iY].contains(god);
            }
        }
        return isHaveAcsess;
    }
    
    protected Field(Size size){
        this.size = size;
        cells = new Cell[size.getHeight()][size.getWidth()];
        
        for ( int iY = 0 ; iY < size.getHeight(); iY++){
            for (int iX = 0; iX < size.getWidth(); iX++){
                cells[iY][iX] = new Cell();
                cells[iY][iX].x = iX;
                cells[iY][iX].y = iY;
            }
        }
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
                    
                    int iTopY = iCellY >= 1 ? iCellY - 1 : size.getHeight()-1;
                    int iBotY = iCellY < size.getHeight()-1 ? iCellY + 1 : 0;
                    int iLeftX = iCellX >= 1 ? iCellX - 1 : size.getWidth()-1;
                    int iRightX =iCellX < size.getWidth()-1 ? iCellX + 1 : 0;
                    
                    Cell leftTop =  field[iTopY][iLeftX];
                    Cell top =      field[iTopY][iCellX];
                    Cell rightTop = field[iTopY][iRightX];
                    Cell left =     field[iCellY][iLeftX];
                    Cell right =    field[iCellY][iRightX];
                    Cell leftBot =  field[iBotY][iLeftX];
                    Cell bot =      field[iBotY][iCellX];
                    Cell rightBot = field[iBotY][iRightX];
                    
                    nearbyCells.add(leftTop);
                    nearbyCells.add(top);
                    nearbyCells.add(rightTop);
                    nearbyCells.add(left);
                    nearbyCells.add(right);
                    nearbyCells.add(leftBot);
                    nearbyCells.add(bot);
                    nearbyCells.add(rightBot);
                    
                    field[iCellY][iCellX].setNearbyCells(nearbyCells);
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

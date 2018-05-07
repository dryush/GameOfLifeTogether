/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

import Infrastructure.Size;
import java.util.ArrayList;
import java.util.Collection;

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
    private ArrayList<IGod>[][] access = new ArrayList[0][0];
    
    public ArrayList<IGod>[][] getCellsAccesses (){
        ArrayList<IGod>[][] accesseCopy = new ArrayList[size.getHeight()][size.getWidth()];
        for (int iX = 0; iX < size.getWidth(); iX++){
            for( int iY = 0; iY < size.getHeight(); iY++){
                accesseCopy[iY][iX] = (ArrayList<IGod>) access[iY][iX].clone();
            }
        }
        return accesseCopy;
    }
    
    private void allocateCells(Collection<? extends IGod> gods){
        access = new ArrayList[size.getHeight()][size.getWidth()];
        for (int iX = 0; iX < size.getWidth(); iX++){
            for( int iY = 0; iY < size.getHeight(); iY++){
                access[iY][iX] = new ArrayList<>();
            }
        }
        
        int godNumber = 0;
        for ( IGod god : gods){
            
            int partWidth = (size.getWidth()+gods.size()-1) / gods.size();
            int partHeight = size.getHeight();
            
            for(int iX = 0; iX < partWidth; iX++){
                 for( int iY =0; iY < partHeight; iY++){
                    int iPartX = iX+godNumber*(partWidth-size.getWidth()%gods.size());
                    access[iY][iPartX].add(god);
                }
            }
            godNumber ++;
        }
    }
    
    public boolean[][] geCellAcsessForGod(IGod god){
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
        abstract protected void setCells(Field field);
        abstract protected void setNearbyCells(Field field);
        final public void set(Field field){
            setCells(field);
            setNearbyCells(field);
        }
    }
    
    public static class FlatSphere extends FieldShape{
        
        @Override
        protected void setCells(Field field) {
            Size size = field.size;
            for ( int iCellX = 0; iCellX < size.getWidth(); iCellX++){    
                for ( int iCellY = 0; iCellY < size.getHeight(); iCellY++ ){
                    field.cells[iCellY][iCellX] = new Cell();
                    field.cells[iCellY][iCellX].x = iCellX;
                    field.cells[iCellY][iCellX].y = iCellY;
                }
            }
            
        }
        
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
    
    public static class Romb extends FieldShape{

         @Override
        protected void setCells(Field field) {
            Size size = field.size;
            
            
                System.out.println("");
            for (int i =0; i < size.getWidth(); i++){
                for ( int j = 0; j < size.getHeight(); j++){
                    System.out.print( field.cells[j][i] == null ? "." : "8");
                }
                System.out.println("");
            }
            //Левый верхний треугольник
            {
                int ws = 0;
                int we = size.getWidth() / 2;
                int hs = 0;
                int he = size.getHeight() /2;
                for ( int iCellX = ws; iCellX < we; iCellX++){    
                    for ( int iCellY = hs; iCellY < he; iCellY++ ){

                        if ( we - iCellX - 1< (double)we/he*iCellY ){
                            field.cells[iCellY][iCellX] = new Cell();
                            field.cells[iCellY][iCellX].x = iCellX;
                            field.cells[iCellY][iCellX].y = iCellY;
                        }
                        else {
                            field.cells[iCellY][iCellX] = null;
                        }
                    }
                }
            }
            
            
                System.out.println("");
            for (int i =0; i < size.getWidth(); i++){
                for ( int j = 0; j < size.getHeight(); j++){
                    System.out.print( field.cells[j][i] == null ? "." : "8");
                }
                System.out.println("");
            }
            
            //Правый верхний треугольник
            {
                int ws = (size.getWidth()) / 2 ;
                int we = size.getWidth();
                int hs = 0;
                int he = (size.getHeight())/2;
                for ( int iCellX = ws; iCellX < we; iCellX++){    
                    for ( int iCellY = hs; iCellY < he; iCellY++ ){

                        if ( iCellX -ws  < (double)(we-ws)/(he-hs)*(iCellY-hs) ){
                            field.cells[iCellY][iCellX] = new Cell();
                            field.cells[iCellY][iCellX].x = iCellX;
                            field.cells[iCellY][iCellX].y = iCellY;
                        }
                        else {
                            field.cells[iCellY][iCellX] = null;
                        }
                    }
                }
            }
            
            
                System.out.println("");
            for (int i =0; i < size.getWidth(); i++){
                for ( int j = 0; j < size.getHeight(); j++){
                    System.out.print( field.cells[j][i] == null ? "." : "8");
                }
                System.out.println("");
            }
            
            //Левый нижний треугольник
            {
                int ws = 0;
                int we = size.getWidth() / 2;
                int hs = (size.getHeight()) / 2;
                int he = size.getHeight();
                for ( int iCellX = ws; iCellX < we; iCellX++){    
                    for ( int iCellY = hs; iCellY < he; iCellY++ ){

                        if ( iCellX - ws > (double)(we-ws)/(he-hs)*(iCellY-hs) ){
                            field.cells[iCellY][iCellX] = new Cell();
                            field.cells[iCellY][iCellX].x = iCellX;
                            field.cells[iCellY][iCellX].y = iCellY;
                        }
                        else {
                            field.cells[iCellY][iCellX] = null;
                        }
                    }
                }
            }
            
            
                System.out.println("");
            for (int i =0; i < size.getWidth(); i++){
                for ( int j = 0; j < size.getHeight(); j++){
                    System.out.print( field.cells[j][i] == null ? "." : "8");
                }
                System.out.println("");
            }
            
            //Правый нижний треугольник
            {
                int ws = size.getWidth()/ 2;
                int we = size.getWidth();
                int hs = (size.getHeight())/2;
                int he = size.getHeight();
                for ( int iCellX = ws; iCellX < we; iCellX++){    
                    for ( int iCellY = hs; iCellY < he; iCellY++ ){

                        if ( we - iCellX - 1 > (double)(we-ws)/(he-hs)*(iCellY-hs) ){
                            field.cells[iCellY][iCellX] = new Cell();
                            field.cells[iCellY][iCellX].x = iCellX;
                            field.cells[iCellY][iCellX].y = iCellY;
                        }
                        else {
                            field.cells[iCellY][iCellX] = null;
                        }
                    }
                }
            }
            
            
                System.out.println("");
            for (int i =0; i < size.getWidth(); i++){
                for ( int j = 0; j < size.getHeight(); j++){
                    System.out.print( field.cells[j][i] == null ? "." : "8");
                }
                System.out.println("");
            }
            
        }

        @Override
        protected void setNearbyCells(Field field) {
            
            Size size = field.getSize();
            
            for ( int iCellX = 0; iCellX < size.getWidth(); iCellX++){
                
                for ( int iCellY = 0; iCellY < size.getHeight(); iCellY++ ){
                    if ( field.cells[iCellY][iCellX] != null){
                        ArrayList<Cell> nearbyCells = new ArrayList<Cell>();
                        if ( iCellX > 0 && iCellY > 0){
                            Cell ncell = field.cells[iCellY-1][iCellX-1];
                            if (ncell != null){
                                nearbyCells.add(ncell);
                            }
                        }
                        if ( iCellX > 0 ){
                            Cell ncell = field.cells[iCellY][iCellX-1];
                            if (ncell != null){
                                nearbyCells.add(ncell);
                            }
                        }
                        if ( iCellX > 0 && iCellY + 1 < size.getHeight()){
                            Cell ncell = field.cells[iCellY+1][iCellX-1];
                            if (ncell != null){
                                nearbyCells.add(ncell);
                            }
                        }

                        if ( iCellX+1 < size.getWidth() && iCellY > 0){
                            Cell ncell = field.cells[iCellY-1][iCellX+1];
                            if (ncell != null){
                                nearbyCells.add(ncell);
                            }
                        }
                        if ( iCellX+1 < size.getWidth()){
                            Cell ncell = field.cells[iCellY][iCellX+1];
                            if (ncell != null){
                                nearbyCells.add(ncell);
                            }
                        }

                        if ( iCellX+1 < size.getWidth() && iCellY+1 < size.getHeight()){
                            Cell ncell = field.cells[iCellY+1][iCellX+1];
                            if (ncell != null){
                                nearbyCells.add(ncell);
                            }
                        }

                        if ( iCellY > 0){
                            Cell ncell = field.cells[iCellY-1][iCellX];
                            if (ncell != null){
                                nearbyCells.add(ncell);
                            }
                        }
                        if ( iCellY + 1 < size.getHeight()){
                            Cell ncell = field.cells[iCellY+1][iCellX];
                            if (ncell != null){
                                nearbyCells.add(ncell);
                            }
                        }

                        field.cells[iCellY][iCellX].setNearbyCells(nearbyCells);
                    }
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
        private Collection<? extends IGod> gods = new ArrayList<>();
        public FieldBuilder setGods(Collection<? extends IGod> gods ){
            this.gods = gods;
            return this;
        }
        public Field build(){
            Field f = new Field(size);
            shape.set(f);
            f.allocateCells(gods);
            
            return f;
        }
    }
}

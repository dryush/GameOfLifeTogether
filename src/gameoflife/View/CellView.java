/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View;

import gameoflife.model.Cell;
import gameoflife.model.Creature;
import gameoflife.model.Creature.LiveStage;
import gameoflife.model.God;
import gameoflife.model.ICellListener;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicBorders;

/**
 *
 * @author dryush
 */
public class CellView extends JButton {
    
    private class CellListener implements ICellListener{

        @Override
        public void onCellCreatureSet(Cell cell) {
            if ( cell == CellView.this.cell)
                CellView.this.painter.repaintWithCreature(cell.getCreature());
        }

        @Override
        public void onCellCreatureStageChanged(Cell cell) {
            if ( cell == CellView.this.cell)
                CellView.this.painter.repaintWithCreature(cell.getCreature());
        }

        @Override
        public void onCellCreatureDestroy(Cell cell) {
            if ( cell == CellView.this.cell)
                CellView.this.painter.repaintToClear(cell);
        }
/*
        @Override
        public void onCellCreatureBirth(Cell cell) {
            CellView.this.painter.repaintWithCreature(cell.getCreature());
            
            System.out.println("Клекта родилась в: x: " + cell.x + " y: " + cell.y);
        }

        @Override
        public void onCellCreatureLive(Cell cell) {
            CellView.this.painter.repaintWithCreature(cell.getCreature()); 
            
            
            System.out.println("Клетка живёт в: x: " + cell.x + " y: " + cell.y);
        }

        @Override
        public void onCellCreatureDie(Cell cell) {
            CellView.this.painter.repaintWithCreature(cell.getCreature()); //To change body of generated methods, choose Tools | Templates.
            
            System.out.println("Клетка Умерла в: x: " + cell.x + " y: " + cell.y);
        }

        @Override
        public void onCellCreatureDestroy(Cell cell) {
            CellView.this.painter.repaintToClear(cell); //To change body of generated methods, choose Tools | Templates.
                
            System.out.println("Клекта Уничтожена в: x: " + cell.x + " y: " + cell.y);
        }

        @Override
        public void onCellCreatureSet(Cell cell) {
            CellView.this.painter.repaintWithCreature(cell.getCreature());
            
            
            System.out.println("Клетка УСТАНОВЛЕНА в: x: " + cell.x + " y: " + cell.y);
        }
    */
    }
    
    Painter painter = new Painter();
    private class Painter{
        
        private GameColors colors = GameColors.getInstance();
        
        private void initPaint(){
            
            Dimension dim = new Dimension(20,20);
            CellView.this.setSize(dim);
            CellView.this.setPreferredSize(dim);
            
            //CellView.this.setBorder(new BasicBorders.ButtonBorder(Color.WHITE, Color.black, Color.black, Color.black));
        }
        
        private void repaintToClear(Cell cell){
            ArrayList<Color> godColors = new ArrayList<>();
            for ( God god : CellView.this.godsWithAccesse){
                godColors.add( colors.getCellColor(god));
            }
            
            int red = 0;
            int green = 0;
            int blue = 0;
            for ( Iterator<Color> iColor = godColors.iterator(); iColor.hasNext(); ){
                Color color = iColor.next();
                red += color.getRed();
                green += color.getGreen();
                blue += color.getBlue();
            }
            if ( godColors.size() > 0){
                red /= godColors.size();
                green /= godColors.size();
                blue /= godColors.size();
            }
            
            //CellView.this.setBackground(new Color(red, green, blue));
            CellView.this.setBackground(Color.GRAY);
            try {
                Thread.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(CellView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void repaintWithCreature(Creature creatue){
            
            
            
            CellView.this.setBackground(colors.getCreatureColor(creatue));
            
            try {
                Thread.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(CellView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private CellListener cellListener = new CellListener();
    private ArrayList<God> godsWithAccesse = new ArrayList<>();
    private Cell cell = null;
    private void setCell(Cell cell){
        this.cell = cell;
        this.cell.addListener(cellListener);
    }
    
    public Cell getCell(){
        return cell;
    }
    //Конструкторы!!!
    public CellView(Cell cell, AbstractAction a){
        super(a);
        setCell(cell);
        painter.initPaint();
    }
    
    public CellView(Cell cell){
        setCell(cell);
    }
    public void addAccesse(God god){
        godsWithAccesse.add(god);
    }
    public void addAccesse(Collection<God> gods){
        for (God god : gods){
            addAccesse(god);
        }
    }
    
    public void setDisabled(){
        this.setEnabled(false);
    }
    
    public void setEnabled(){
        this.setEnabled(true);
    }
    
    
    
    public void setEnabledStatusFor(God god){
        if (godsWithAccesse.contains(god)){
            setEnabled();
        } else {
            setDisabled();
        }
    }
}

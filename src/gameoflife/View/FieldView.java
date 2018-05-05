/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View;

import Infrastructure.Size;
import com.sun.java.swing.plaf.motif.MotifButtonListener;
import gameoflife.model.Cell;
import gameoflife.model.Field;
import gameoflife.model.God;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JPanel;

/**
 *
 * @author dryush
 */
public class FieldView extends JPanel {
    
    private Field field = null;

    CellView[][] cells = new CellView[0][0];
    CellSelectRefier cellSelectRefier = new CellSelectRefier();
    
    public FieldView(Field field) {
        this.field = field;
        Size size = field.getSize();
        cells = new CellView[size.getHeight()][size.getWidth()];
         
        Cell[][] modelCells = field.getCells();
        ArrayList<God>[][] cellAccesses = field.getCellsAccesses();
        
        GridLayout layout = new GridLayout(size.getHeight(), size.getWidth());
        this.setLayout(layout);
        
        
        
        for ( int iY = 0; iY < size.getHeight(); iY++){
            for ( int iX = 0; iX < size.getWidth(); iX++){

                Cell modelCell = modelCells[iY][iX];
                CellView viewCell = new CellView(modelCell, cellSelectRefier);
                viewCell.addAccesse( cellAccesses[iY][iX]);
                cells[iY][iX] = viewCell;
                viewCell.setText(""+iX+" "+iY);
                this.add(viewCell);
            }
        }
        
        
        this.setDoubleBuffered(true);   
    }
    
    public void setEnabledFor(God god){
        for( CellView[] cellLine : cells){
            for ( CellView cell : cellLine){
                cell.setEnabledStatusFor(god);
            }
        }
    }
    
    @Override
    public void setEnabled(boolean isEnabled){
        for ( CellView[] cellLine : cells){
            for ( CellView cell : cellLine){
            //    cell.setEnabled(isEnabled);
            }
            int stop = 2;
        }
    }
    
    
    public void addListener(IFieldViewListener l){
        cellSelectRefier.addListener(l);
    }
    private class CellSelectRefier extends AbstractAction{
        
        private List<IFieldViewListener> listeners = new ArrayList<>();
        public void addListener(IFieldViewListener l){
            listeners.add(l);
        }
        
        private void fireCellSelectEvent(Cell cell){
            listeners.forEach((l) -> l.onCellSelect(cell));
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            CellView source = (CellView) ae.getSource();
            Cell cell = source.getCell();
            System.out.println("Жмак по клетке: x: " + cell.x +" y: " + cell.y);
            /*
            for ( Cell nc : cell.getNearbyCells()){
                System.out.println("\t x:" + nc.x + " y: " + nc.y);
            }
            */
            fireCellSelectEvent(cell);
            
        }
    }
}

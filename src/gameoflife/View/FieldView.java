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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import java.util.Collection;
/**
 *
 * @author dryush
 */
public class FieldView extends JPanel {
    
    private Field field = null;

    CellView[][] cells = new CellView[0][0];
    CellSelectRefier cellSelectRefier = new CellSelectRefier();
    
    public FieldView(Field field, Players players) {
        this.field = field;
        Size size = field.getSize();
        cells = new CellView[size.getHeight()][size.getWidth()];
         
        Cell[][] modelCells = field.getCells();
        ArrayList<?>[][] cellAccesses = field.getCellsAccesses();
        
        GridLayout layout = new GridLayout(size.getHeight(), size.getWidth());
        this.setLayout(layout);
        Dimension sizes = new Dimension(300,300);
        this.setMinimumSize(sizes);
        this.setSize(sizes);
        
        for ( int iY = 0; iY < size.getHeight(); iY++){
            for ( int iX = 0; iX < size.getWidth(); iX++){

                Cell modelCell = modelCells[iY][iX];
                CellView viewCell = new CellView(modelCell, players);
                viewCell.addListener(cellSelectRefier);
                viewCell.addAccesse((Collection)cellAccesses[iY][iX]);
                cells[iY][iX] = viewCell;
                this.add(viewCell);
                viewCell.setVisible(true);
            }
        }
    }
    
    public void setEnabledFor(God god){
        for( CellView[] cellLine : cells){
            for ( CellView cell : cellLine){
                cell.setEnabledStatusFor(god);
            }
        }
    }
    
    public void addListener(IFieldViewListener l){
        cellSelectRefier.addListener(l);
    }
    
    private class CellSelectRefier implements ICellViewListener{
        
        private List<IFieldViewListener> listeners = new ArrayList<>();
        public void addListener(IFieldViewListener l){
            listeners.add(l);
        }
        
        private void fireCellSelectEvent(Cell cell){
            listeners.forEach((l) -> l.onCellSelect(cell));
        }
        

        @Override
        public void onCellSelected(Cell cell) {
            fireCellSelectEvent(cell);
        }
    }
}

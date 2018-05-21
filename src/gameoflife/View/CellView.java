/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View;

import gameoflife.model.Cell;
import gameoflife.model.Creature;
import gameoflife.model.God;
import gameoflife.model.ICellListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;

/**
 *
 * @author dryush
 */
public class CellView extends javax.swing.JPanel {

    /**
     * Creates new form CellView_
     */
    
    private CellView.CellListener cellListener = new CellView.CellListener();
    private ArrayList<God> godsWithAccesse = new ArrayList<>();
    private Cell cell = null;
    
    private void setCell(Cell cell){
        this.cell = cell;
        if (cell != null){
            this.cell.addListener(cellListener);
        } else {
            this.cellButton.setVisible(false);
            this.remove(cellButton);
        }
    }
    
    
    public Cell getCell(){
        return cell;
    }
    //Конструкторы!!!
    public CellView(Cell cell, Players players){
        super();
        initComponents();
        setCell(cell);
        painter.players = players;
        painter.initPaint();
    }
    
    public void addAccesse(God god){
        godsWithAccesse.add(god);
    }
    
    public void addAccesse(Collection< ? extends God > gods){
        for (God god : gods){
            addAccesse(god);
        }
    }
    
    public void setDisabled(){
        //Color backColor = this.getBackground();
        this.setEnabled(false);
        this.cellButton.setEnabled(false);
        this.setBackground(Color.BLACK);
    }
    
    public void setEnabled(){
        this.setEnabled(true);
        this.cellButton.setEnabled(true);
    }
    
    
    
    public void setEnabledStatusFor(God god){
        boolean isGod = god != null;
        if ( isGod){
        
            boolean isGodCell = godsWithAccesse.contains(god);
            boolean isCreature = cell!= null && cell.getCreature() != null;

            boolean isGodCreature = isCreature && cell.getCreature().getColony() == god.getColony();

            if (isGodCell && ((!isCreature) || (isGodCreature)) ){
                setEnabled();
            } else {
                setDisabled();
            }
        } else {
            setDisabled();
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cellButton = new javax.swing.JButton();

        cellButton.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        cellButton.setForeground(new java.awt.Color(0, 0, 0));
        cellButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        cellButton.setMaximumSize(new java.awt.Dimension(50, 50));
        cellButton.setMinimumSize(new java.awt.Dimension(20, 20));
        cellButton.setPreferredSize(new java.awt.Dimension(50, 50));
        cellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cellButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cellButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cellButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        cellButton.getAccessibleContext().setAccessibleName("CellButton");
    }// </editor-fold>//GEN-END:initComponents

    
    private Collection<ICellViewListener> listeners = new LinkedList();
    public void addListener(ICellViewListener listener){
        listeners.add(listener);
    }
    private void fireCellSelected(){
        for (ICellViewListener listener : listeners ){
            listener.onCellSelected(cell);
        }
    }
    
    private void cellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cellButtonActionPerformed
        fireCellSelected();
    }//GEN-LAST:event_cellButtonActionPerformed

    
    
    Painter painter = new Painter();
    private class Painter{
        
        private Players players;
        private Color deffaultColor =  Color.WHITE;
        
        
        
        private void initPaint(){
            
            if (CellView.this.cellButton != null){
                deffaultColor = CellView.this.cellButton.getBackground();
                CellView.this.cellButton.setMargin(new Insets(1, 1, 1, 1));
                CellView.this.cellButton.setAlignmentX(CENTER_ALIGNMENT);
                CellView.this.cellButton.setAlignmentY(CENTER_ALIGNMENT);
            }
            
            
            Dimension dim = new Dimension(30,30);
            CellView.this.setSize(dim);
            CellView.this.setPreferredSize(dim);
            CellView.this.setMinimumSize(dim);
            CellView.this.setMaximumSize(dim);
            CellView.this.setBackground(Color.BLACK);
            CellView.this.cellButton.setBackground(deffaultColor);
            
            //CellView.this.setBorder(new BasicBorders.ButtonBorder(Color.WHITE, Color.black, Color.black, Color.black));
        }
        
        private void repaintToClear(Cell cell){
            CellView.this.cellButton.setBackground(deffaultColor);
                CellView.this.cellButton.setText("");

        }
    
        private void repaintWithCreature(Creature creature){
            Players.PlayerParameters playerParam = players.getPlayerParameters(creature.getColony());
            if (null != creature.getLiveStage())switch (creature.getLiveStage()) {
                case BIRTH:
                    CellView.this.cellButton.setText("+");
                    CellView.this.cellButton.setBackground( playerParam.getColor().brighter());
                    break;
                case LIVE:
                    CellView.this.cellButton.setBackground( playerParam.getColor().darker());
                    CellView.this.cellButton.setText("");
                    break;
                case DIE:
                    CellView.this.cellButton.setBackground( playerParam.getColor().darker().darker().darker());
                    CellView.this.cellButton.setText("-");
                    break;
                default:
                    break;
            }
            
        }
    }
    
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
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cellButton;
    // End of variables declaration//GEN-END:variables
}

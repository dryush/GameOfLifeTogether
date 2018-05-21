/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View;

import gameoflife.model.Cell;
import gameoflife.model.Field;
import gameoflife.model.GameSession;
import gameoflife.model.God;
import gameoflife.model.GodController;
import gameoflife.model.IGameSessionListener;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dryush
 */
public class GameOfLifePanel extends javax.swing.JFrame {

    /**
     * Creates new form GameOfLIfePanel
     */
    public GameOfLifePanel() {
        super("Игра Жизнь v.1.2314-1a Амёба");
        initComponents();
        initComponents2();
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //gameMenuPanel.add(gameMenuBar);
        setJMenuBar(gameMenuBar);
        gameMenuBar.setVisible(true);
        
        this.setVisible(true);
    }
    
    private void initComponents2(){
        endMoveButton.setEnabled(false);
        gameInitDialog = new GameInitDialog(this);
    }
    
    private void initGameField(Field field, Players players){
        
        if ( fieldView != null){
            fieldPanel.remove(fieldView);
        }
        fieldView = new FieldView(field, players);
        
        fieldPanel.setLayout( new BorderLayout());
        fieldPanel.add(fieldView,BorderLayout.CENTER);
        fieldPanel.setVisible(true);
        fieldView.setVisible(true);
        pack();
        
        endMoveButton.setEnabled(true);
        
    }
    
    private void setEnableEndMoveBtn(boolean enable){
        endMoveButton.setEnabled(enable);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gameMenuBar = new javax.swing.JMenuBar();
        gameMenu = new javax.swing.JMenu();
        startNewGame_menuItem = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        playerNameLabel = new javax.swing.JLabel();
        playerColor = new javax.swing.JLabel();
        endMoveButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        actionsCount = new javax.swing.JLabel();
        fieldPanel = new javax.swing.JPanel();

        gameMenu.setText("Игра");
        gameMenu.setContentAreaFilled(false);

        startNewGame_menuItem.setText("Начать новую игру");
        startNewGame_menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startNewGame_menuItemActionPerformed(evt);
            }
        });
        gameMenu.add(startNewGame_menuItem);

        gameMenuBar.add(gameMenu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setMinimumSize(new java.awt.Dimension(300, 300));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Ходит:");

        playerNameLabel.setText("Игрок1");

        playerColor.setBackground(new java.awt.Color(255, 0, 0));
        playerColor.setForeground(new java.awt.Color(255, 0, 0));
        playerColor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        playerColor.setMaximumSize(new java.awt.Dimension(100, 100));
        playerColor.setMinimumSize(new java.awt.Dimension(25, 25));
        playerColor.setOpaque(true);
        playerColor.setPreferredSize(new java.awt.Dimension(25, 25));

        endMoveButton.setText("Завершитиь ход");
        endMoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endMoveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(playerNameLabel)
                .addGap(18, 18, 18)
                .addComponent(playerColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(endMoveButton)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(playerNameLabel)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(playerColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endMoveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel2.setText("Действий осталось:");

        actionsCount.setText("1");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(actionsCount)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(actionsCount))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        fieldPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout fieldPanelLayout = new javax.swing.GroupLayout(fieldPanel);
        fieldPanel.setLayout(fieldPanelLayout);
        fieldPanelLayout.setHorizontalGroup(
            fieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );
        fieldPanelLayout.setVerticalGroup(
            fieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(fieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(fieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startNewGame_menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startNewGame_menuItemActionPerformed
        gameInitDialog = new GameInitDialog(this);
        gameInitDialog.setVisible(true);
        SessionParams params = gameInitDialog.getSessionParams();
        if ( params != null){
            initGameSession(params);

       }
    }//GEN-LAST:event_startNewGame_menuItemActionPerformed

    private void endMoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endMoveButtonActionPerformed
        // TODO add your handling code here:
        godController.endGodMove();
    }//GEN-LAST:event_endMoveButtonActionPerformed

    private void setCurrentPlayerInfo(Players.PlayerParameters p){
        playerNameLabel.setText( p.getName());
        playerColor.setBackground(p.getColor());
    }
    private void setCurrentActionsCountInfo(int actionsCount){
        this.actionsCount.setText("" + actionsCount);
    }
    
    
    private ViewGodsController godController = new ViewGodsController();
    private class ViewGodsController extends GodController implements IFieldViewListener{
        private void endGodMove(){
            super.fireGodEndMove();
        }
        
        private void selectCell(Cell cell){
            super.fireGodSelectCell(cell);
        }

        @Override
        public void onCellSelect(Cell cell) {
            selectCell(cell);
        }
        
    }
    
    
    GameSessionHandler gameSessionHandler = null;
    private void initGameSession(SessionParams params){
        
        godController = new ViewGodsController();
        
        Field.FieldBuilder filedBuilder = new Field.FieldBuilder();
        params.configureFieldBuilder(filedBuilder);
        Field field = filedBuilder.build();
        
        Collection <God> gods = params.getGods();
        
        int epochCount = params.getEpochCount();
        
        initGameField(field, params.getPlayers());
        fieldView.addListener(godController);
        
        
        GameSession gameSession = new GameSession(field, gods, epochCount, godController);
        gameSessionHandler = new GameSessionHandler(params, gameSession);        
        gameSession.addListener(gameSessionHandler);
        gameSession.setPlayerMovesCount(params.getFirstMoveActionsCount());
        gameSession.start();
    }
    
    private class GameSessionHandler implements IGameSessionListener{

        private SessionParams params = null;
        private GameSession gameSession = null;
        private int curMoveNumber = 1;
        private GameSessionHandler(SessionParams sessionParams, GameSession session){
            this.params = sessionParams;
            this.gameSession = session;
        }
        
        private void showDrawMessage(){
            JOptionPane.showMessageDialog(GameOfLifePanel.this, "Победила дружба (✿◠‿◠)", "Игра окончена", JOptionPane.INFORMATION_MESSAGE);
        }
        private void showWinMessage(God god){
            String playerName = params.getPlayerParams(god).getName();
            JOptionPane.showMessageDialog(GameOfLifePanel.this, "Победил " + playerName + "(ノ°∀°)ノ⌒･*:.｡. .｡.:*･゜ﾟ･*☆", "Игра окончена", JOptionPane.INFORMATION_MESSAGE);
        }
        
        @Override
        public void onGameOver(GameSession gs) {
            
            if ( gs.getGameStatus() == GameSession.GameStatus.DRAW){
                showDrawMessage();
            } else if ( gs.getGameStatus() == GameSession.GameStatus.WIN){
                God winner = gs.getWinner();
                showWinMessage(winner);
            }
        }

        @Override
        public void onGodChanged(GameSession gs) {
            setCurrentPlayerInfo( params.getPlayerParams(gs.getCurrentGod()));
            fieldView.setEnabledFor(gs.getCurrentGod());
            onGodMovesCountChanged(gs);
        }

        @Override
        public void onGodMovesStageStarted(GameSession gs) {
            fieldView.setEnabledFor(gs.getCurrentGod());
            setEnableEndMoveBtn(true);
        }

        @Override
        public void onGodMovesStageEnded(GameSession gs) {
            fieldView.setEnabledFor(null);
            setEnableEndMoveBtn(false);
            curMoveNumber++;
        }

        @Override
        public void onGodMovesCountChanged(GameSession gs) {
            setCurrentActionsCountInfo(gs.getCurrentGod().getActionsCount());
        }

        @Override
        public void onBeforeGodNovesStageStart(GameSession gs) {
            if (curMoveNumber == 1){
                gs.setPlayerMovesCount(params.getFirstMoveActionsCount());
            } else {
                gs.setPlayerMovesCount(params.getMoveActionsCount());
            }
            
        }
    }
   
    
   
    private FieldView fieldView;
    private GameInitDialog gameInitDialog;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel actionsCount;
    private javax.swing.JButton endMoveButton;
    private javax.swing.JPanel fieldPanel;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JMenuBar gameMenuBar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel playerColor;
    private javax.swing.JLabel playerNameLabel;
    private javax.swing.JMenuItem startNewGame_menuItem;
    // End of variables declaration//GEN-END:variables
}

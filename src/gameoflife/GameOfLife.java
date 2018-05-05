/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;


import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.util.ArrayList;

import Infrastructure.*;
import gameoflife.View.FieldView;
import gameoflife.View.GameColors;
import gameoflife.View.IFieldViewListener;
import gameoflife.model.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javafx.event.EventType;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 *
 * @author dryush
 */
public class GameOfLife extends JApplet {
    
    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                }
                
                JFrame frame = new JFrame("JavaFX 2 in Swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                JApplet applet = new GameOfLife();
                applet.init();
                
                frame.setContentPane(applet.getContentPane());
                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                applet.start();                
            }
        });
    }
    
    
    FieldView fieldView = null;
    @Override
    public void init() {
        initGameSession();
        
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(500, 500));
        add(fxContainer, BorderLayout.CENTER);
        fxContainer.setLayout(new BorderLayout());
        
        JFXPanel topMenu = new JFXPanel();
        topMenu.setLayout(new FlowLayout());
        fxContainer.add(topMenu, BorderLayout.NORTH);
        // create JavaFX scene
        JButton endMoveBtn = new JButton(new AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                gameLoop.onMoveEnded();
            }
        });
        endMoveBtn.setText("Закончить ход");
        endMoveBtn.setPreferredSize(new Dimension(100, 50));
        
        JButton startGameBtn = new JButton(new AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                gameLoop.startPlayersStage();
            }
        });
        startGameBtn.setText("Начать игру");
        startGameBtn.setPreferredSize(new Dimension(100, 50));
        
        topMenu.add(startGameBtn);
        topMenu.add(endMoveBtn);
        fieldView = new FieldView(field);
        fieldView.setEnabledFor(null);
        fieldView.addListener(gameLoop);
        //fxContainer.add();
        fxContainer.add(fieldView, BorderLayout.CENTER);
        Platform.runLater(new Runnable() {
            
            @Override
            public void run() {
            
            }
        });
    }
    
    
    GameSession gameSession = null;
    ArrayList<God> gods = null;
    Field field = null;
    public void initGameSession(){
        Field.FieldBuilder fieldBuilder = new Field.FieldBuilder();
        gods = new ArrayList<>();
        gods.add(new God());
        gods.add(new God());
        
        field = fieldBuilder.setSize(new Size(20,20))
                .setGods(gods)
                .setShape(new Field.FlatSphere())
                .build();
        
        gameSession = new GameSession(field, gods, 1, 10);
        
        ArrayList<Color> colors =new ArrayList( Arrays.asList(
                new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA}));
        Random rand = new Random();
        for (God god : gods){
            GameColors gameColors = GameColors.getInstance();
            int iColor = rand.nextInt(colors.size());
            Color color = colors.get(iColor);
            colors.remove(iColor);
            gameColors.setCreatureColor(god.getColony(), color);
            gameColors.setCellColor(god, color);
        }
        
        gameLoop = new GameLoop();
        
    }

    GameLoop gameLoop = null;
    private class GameLoop implements IFieldViewListener{

        GameSession.GameStage_PlayerMoves playersStage = gameSession.getGameStage_PlayerMoves();
        GameSession.GameStage_Development developmentStage = gameSession.getGameStage_Development();
        GameSession.GameStage_GameOverCheck gameOverCheckStage = gameSession.getGameStage_GameOverCheck();
        God curGod = null;
        
        public void startPlayersStage(){
            curGod = playersStage.startStage();
            fieldView.setEnabledFor(curGod);
            
        }
        public void onMoveEnded(){
            fieldView.setEnabled(false);
            playersStage.endGodMove();
            if( !playersStage.isAllMoved() ){
                curGod = playersStage.startGodMove();
                fieldView.setEnabledFor(curGod);
            } else {
                developmentStage.start();
                
                GameSession.GameStatus gameStatus = gameOverCheckStage.checkGameStatus();
                if ( gameStatus == GameSession.GameStatus.DRAW){
                    JOptionPane.showMessageDialog(null, "Ничья", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
                } else if ( gameStatus == GameSession.GameStatus.WIN){
                    JOptionPane.showMessageDialog(null, "Кто-то выиграл -\\_o/-", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    startPlayersStage();
                }
            }
        }
        
        @Override
        public void onCellSelect(Cell cell) {
            gameSession.getGameStage_PlayerMoves().doPLayerAction(cell);
        }
        
        
    }
    
}


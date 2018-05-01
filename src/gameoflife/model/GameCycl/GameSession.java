/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model.GameCycl;

import gameoflife.model.Cell;
import gameoflife.model.Colony;
import gameoflife.model.Creature;
import gameoflife.model.Field;
import gameoflife.model.GameCycl.IGameStageListener;
import gameoflife.model.God;
import gameoflife.model.IPlayerCellSelectListener;
import gameoflife.model.PlayerSelectCellEmiter;
import java.util.ArrayList;

/**
 *
 * @author dryush
 */
public class GameSession {
    
    
    public int playerMovesCount = 0;
    
    private GameStage _firstPlayersMoveStage = null;
    private GameStage _playersMoveStage = null;
    private GameStage _developmentStage = null;
    private GameStage _checkGameOverStage = null;
    
    private void startGameLoop(){
        GameStage nextStage = _firstPlayersMoveStage;
        while( nextStage != null){
            nextStage = nextStage.start();
        }
    }

    private GameSession(Field f, ArrayList<God> gods, int epochCount, 
            int firstStageActionsCount, int actionCount, PlayerSelectCellEmiter e) {
        _firstPlayersMoveStage = new GameStage_PlayerMoves(e, gods, firstStageActionsCount);
        ArrayList<Colony> colonies = new ArrayList<>();
        for ( God god : gods){
            colonies.add(god.getColony());
        }
        _developmentStage = new GameStage_Development(epochCount, colonies);
        _playersMoveStage = new GameStage_PlayerMoves(e, gods, actionCount);
        _checkGameOverStage = new GameStage_GameOverCheck(gods);
    }
    
    
    
    
    
    private abstract class GameStage {
        
        
        abstract protected GameStage getNextStage();
        abstract protected void actions();
        
        private ArrayList<IGameStageListener> listeners;
        public void addListener( IGameStageListener listener){
            listeners.add(listener);
        }

        public GameStage start(){
            fireStageStartEvent();
            actions();
            end();
            return getNextStage();
        }

        
        private void fireStageStartEvent(){
            listeners.forEach((listener) -> {
                listener.onStageStart();
            });
        }
        
        private void end(){
            fireStageEndEvent();
        }
        private void fireStageEndEvent(){
            listeners.forEach((listener) -> {
                listener.onStageEnd();
            });
        }
        
    }
    
    private class GameStage_Development extends GameStage{

        private int epochCount;
        private ArrayList<Colony> colonies; 
        public GameStage_Development(int epochCount, ArrayList<Colony> colonies) {
            this.epochCount = epochCount;
            this.colonies = colonies;
        }

        
        @Override
        protected GameStage getNextStage() {
            return _checkGameOverStage; 
        }

        @Override
        protected void actions() {
            
            for ( int i = 0; i < epochCount; i++){
                
                
                for ( Colony colony : colonies){
                    for ( Creature creature : colony.getCreatures()){
                        creature.liveEpoch();
                    }
                }
                
                for ( Colony colony : colonies){
                    for ( Creature creature : colony.getCreatures()){
                        creature.endEpoch();
                    }
                }
                
            }
            
        }
        
    }
    
    private class GameStage_PlayerMoves extends GameStage{

        private PlayerSelectCellEmiter playerSelectCellEmiter;
        private ArrayList<God> gods;
        private int actionsCount = 0;
        
        GameStage_PlayerMoves(PlayerSelectCellEmiter e, ArrayList<God> gods, int actionsCount){
            playerSelectCellEmiter = e;
            this.gods = gods;
            this.actionsCount = actionsCount;
        }
        
        private int iCurrentGod;
        private int currentActionsCount;
        
        private class PlayerCellSelectListener implements IPlayerCellSelectListener{
            @Override
            public void onPlayerSelectCell(Cell cell) {
                if (currentActionsCount > 0){
                    gods.get(iCurrentGod).doAction(cell);
                    currentActionsCount--;
                }
                
                if (currentActionsCount == 0){
                    if ( iCurrentGod < gods.size()-1-1){
                        iCurrentGod ++;
                        currentActionsCount = actionsCount;
                    } else {
                        playerSelectCellEmiter.end();
                    }
                }
            }    
        }
        
        @Override
        protected GameStage getNextStage() {
            return _developmentStage;
        }

        @Override
        protected void actions() {
            this.playerSelectCellEmiter.start();
        }
    }
    
    private enum GameStatus {
        CONTINUE,
        WIN,
        DRAW
    }
    
    private class GameStage_GameOverCheck extends GameStage{

        private GameStatus gameStatus = GameStatus.CONTINUE;
        private God winner = null;
        
        private ArrayList<God> gods;
        public GameStage_GameOverCheck(ArrayList<God> gods){
            this.gods = gods;
        }
        
        @Override
        protected GameStage getNextStage() {
            if (gameStatus == GameStatus.CONTINUE){
                return _playersMoveStage;
            } else {
                return null;
            }
        }

        @Override
        protected void actions() {
            
            int noCreaturesGodsCount = 0;
            
            for (God god : gods){
                if (god.getColony().getCreaturesCount() == 0){
                    noCreaturesGodsCount++;
                }
            }
            if (noCreaturesGodsCount == gods.size()-1){
                for ( God god : gods){
                    if ( god.getColony().getCreaturesCount() > 0){
                        winner = god;
                        gameStatus = GameStatus.WIN;
                        break;
                    }
                }
            } else if ( noCreaturesGodsCount == gods.size() ){
                gameStatus = GameStatus.DRAW;
            } else {
                gameStatus = GameStatus.CONTINUE;
            }
        }
    }
}



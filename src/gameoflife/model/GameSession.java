/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

import java.util.ArrayList;

/**
 *
 * @author dryush
 */
public class GameSession {
    
    public int playerMovesCount = 0;
    
    private GameStage_PlayerMoves _playersMoveStage = null;
    private GameStage_Development _developmentStage = null;
    private GameStage_GameOverCheck _checkGameOverStage = null;
    
    public GameSession(Field f, ArrayList<God> gods, int epochCount, int actionCount) {
        
        ArrayList<Colony> colonies = new ArrayList<>();
        for ( God god : gods){
            colonies.add(god.getColony());
        }
        _developmentStage = new GameStage_Development(epochCount, colonies);
        _playersMoveStage = new GameStage_PlayerMoves( gods, actionCount);
        _checkGameOverStage = new GameStage_GameOverCheck(gods);
    }
       
    
    public GameStage_Development getGameStage_Development(){
        return _developmentStage;
    }
    public GameStage_PlayerMoves getGameStage_PlayerMoves(){
        return _playersMoveStage;
    }
    public GameStage_GameOverCheck getGameStage_GameOverCheck(){
        return _checkGameOverStage;
    }
    public class GameStage_Development {

        private int epochCount;
        private ArrayList<Colony> colonies; 
        public GameStage_Development(int epochCount, ArrayList<Colony> colonies) {
            this.epochCount = epochCount;
            this.colonies = colonies;
        }

        
        public void start() {
            
            for ( int i = 0; i < epochCount; i++){
                
                ArrayList<Colony> fixedColonies = new ArrayList<>();
                for ( Colony colony : colonies){
                    fixedColonies.add(colony.clone());
                }
                
                for ( Colony colony : fixedColonies){
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
    
    public class GameStage_PlayerMoves{

        private ArrayList<God> gods;
        private int actionsCount = 0;
        
        GameStage_PlayerMoves(ArrayList<God> gods, int actionsCount){;
            this.gods = gods;
            this.actionsCount = actionsCount;
        }
        public int getActionsCount(){
            return actionsCount;
        }
        
        private static final int BEFORE_FIRST = 0;
        private int iCurrentGod = BEFORE_FIRST;
        
        public God startStage(){
            iCurrentGod = 0;
            return startGodMove();
        }
        
        public God startGodMove(){
            God god = gods.get(iCurrentGod);
            god.setActionsCount(actionsCount);
            
            return god;
        }
        
        public void endGodMove(){
            gods.get(iCurrentGod).getColony().getCreatures().forEach((c) -> c.endEpoch());
            iCurrentGod++;
        } 
        
        public boolean isAllMoved(){
            return iCurrentGod+1 > gods.size();
        }
        
        public void  doPLayerAction(Cell cell){
            gods.get(iCurrentGod).doAction(cell);
        }
    }
    
    public enum GameStatus {
        CONTINUE,
        WIN,
        DRAW
    }
    
    public class GameStage_GameOverCheck {

        private God winner = null;
        
        private ArrayList<God> gods;
        public GameStage_GameOverCheck(ArrayList<God> gods){
            this.gods = gods;
        }
        
        
        public GameStatus checkGameStatus() {
            
            GameStatus gameStatus = GameStatus.CONTINUE;    
            
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
            return gameStatus;
        }
        
        public God getWinner(){
            return winner;
        }
    }
}



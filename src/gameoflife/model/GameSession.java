/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author dryush
 */
public class GameSession {
   
    private GameStage_GodsMoves _playersMoveStage = null;
    private GameStage_Development _developmentStage = null;
    private GameStage_GameOverCheck _checkGameOverStage = null;
    
    public GameSession(Field f, Collection<God> gods, int epochCount, GodController godController) {
        
        ArrayList<Colony> colonies = new ArrayList<>();
        for ( God god : gods){
            colonies.add(god.getColony());
        }
        _developmentStage = new GameStage_Development(epochCount, colonies);
        _playersMoveStage = new GameStage_GodsMoves( gods, godController);
        _checkGameOverStage = new GameStage_GameOverCheck(gods);
    }
       
    
    private Collection<IGameSessionListener> listeners = new ArrayList<>();
    public void addListener( IGameSessionListener listener){
        listeners.add(listener);
    }
    
    private God curGod = null;
    public God getCurrentGod(){
        return curGod;
    }
    
    private GameStage_Development getGameStage_Development(){
        return _developmentStage;
    }
    private GameStage_GodsMoves getGameStage_PlayerMoves(){
        return _playersMoveStage;
    }
    private GameStage_GameOverCheck getGameStage_GameOverCheck(){
        return _checkGameOverStage;
    }
    
    private interface GameStageListener{
        void onStageEnd(GameStage gs);
        void onStageStart(GameStage gs);
    }
    
    private GameLoop gameLoop = new GameLoop();
    public void start(){
        gameLoop.start();
    }
    private class GameLoop implements GameStageListener{

        public GameLoop(){
        }
        
        private void start(){
            getGameStage_Development().addListener(this);
            getGameStage_GameOverCheck().addListener(this);
            getGameStage_PlayerMoves().addListener(this);
            
            getGameStage_PlayerMoves().start();
        }
        @Override
        public void onStageEnd(GameStage gs) {
            GameStage next = gs.getNextStage();
            if ( next != null){
                next.start();
            }
        }

        @Override
        public void onStageStart(GameStage gs) {
            
        }
        
    }
    
    abstract private class GameStage{
        Collection<GameStageListener> listeners =  new ArrayList();
        public void addListener(GameStageListener listener){
            listeners.add(listener);
        }
        protected void fireStageEnd(){
            for ( GameStageListener l : listeners){
                l.onStageEnd(this);
            }
        }
        protected void fireStageStart(){
            for ( GameStageListener l : listeners){
                l.onStageStart(this);
            }
        }
        
        public void start(){
            fireStageStart();
        }
        
        protected void end(){
            fireStageEnd();
        }
        abstract public GameStage getNextStage();
    }
    
    private class GameStage_Development extends GameStage{

        private int epochCount;
        private ArrayList<Colony> colonies; 
        public GameStage_Development(int epochCount, ArrayList<Colony> colonies) {
            this.epochCount = epochCount;
            this.colonies = colonies;
        }

        
        public void start() {
            super.start();
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
            super.end();
        }

        @Override
        public GameStage getNextStage() {
            return GameSession.this.getGameStage_GameOverCheck();
        }
    }
    
    
    private void fireGodMovesStageStart(){
        listeners.forEach((listener) -> {
            listener.onGodMovesStageStarted(this);
        });
    }
    
    private void fireGodMovesStageEnd(){
        listeners.forEach((listener) -> {
            listener.onGodMovesStageEnded(this);
        });
    }
 
    private void fireGodMoveStart(){
        listeners.forEach((listener) -> {
            listener.onGodChanged(this);
        });
    }
    
    private void fireGodMovesCountChanged(){
        listeners.forEach((listener) -> {
            listener.onGodMovesCountChanged(this);
        });
    }
    
    private void fireBeforeGodsMovesStarted(){
        listeners.forEach( l -> l.onBeforeGodNovesStageStart(this));
    }
    
    
    public void setPlayerMovesCount(int count){
        getGameStage_PlayerMoves().setActionsCount(count);
    }
    
    private class GameStage_GodsMoves extends GameStage implements IGodControllerListener{

        private Collection<God> gods;
        private int actionsCount = 1;
        public void setActionsCount(int count){
            this.actionsCount = count;
        }
        private void setCurrentGod(God god){
            GameSession.this.curGod = god;
        }
        private God getCurrentGod(){
            return GameSession.this.getCurrentGod();
        }
        
        GameStage_GodsMoves(Collection <God> gods, GodController godController){
            this.gods = gods;
            godController.addListener(this);
        }
        
        private Iterator<God> iCurrentGod = null;
        private void startStage(){
            iCurrentGod = gods.iterator();
            setCurrentGod( iCurrentGod.next());
            startGodMove();
        }
        
        @Override
        public void start(){
            super.start();
            fireBeforeGodsMovesStarted();
            startStage();
            fireGodMovesStageStart();
        }
        
        private void startGodMove(){
            getCurrentGod().setActionsCount(actionsCount);
            fireGodMoveStart();
        }
        
        private void endGodMove(){
            getCurrentGod().getColony().getCreatures().forEach((c) -> c.endEpoch());
            if ( iCurrentGod.hasNext())
                setCurrentGod( iCurrentGod.next());
            else
                setCurrentGod( null);
        } 
        
        public boolean isAllMoved(){
            return getCurrentGod() == null;
        }
        
        public void  doPLayerAction(Cell cell){
            getCurrentGod().doAction(cell);
            fireGodMovesCountChanged();
        }
        
        @Override
        public GameStage getNextStage() {
            return GameSession.this.getGameStage_Development();
        }

        @Override
        public void onGodSelectCell(Cell cell) {
            doPLayerAction(cell);
        }

        @Override
        public void onGodEndMove() {
            endGodMove();
            if ( !isAllMoved()){
                startGodMove();
            } else {
                fireGodMovesStageEnd();
                super.end();
            }
        }
    }
    
    public enum GameStatus {
        CONTINUE,
        WIN,
        DRAW
    }
    
    
    private GameStatus gameStatus = GameStatus.CONTINUE;
    public GameStatus getGameStatus(){
        return this.gameStatus;
    }
    private void setGameStatus( GameStatus status){
        this.gameStatus = status;
    }  
    
    private boolean isSessionEnded(){
        return gameStatus != GameStatus.CONTINUE;
    }
    
    
    private God winner = null;
    public God getWinner(){
        return winner;
    }
    
        
    private void fireGameSessionOver(){
        listeners.forEach( (l) -> l.onGameOver(this));
    }
    
    
    private class GameStage_GameOverCheck extends GameStage{

        private Collection<God> gods;
        public GameStage_GameOverCheck(Collection <God> gods){
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

        @Override
        public void start(){
            super.start();
            setGameStatus(checkGameStatus());
            if (isSessionEnded()){
                fireGameSessionOver();
            }
            super.end();
        }
        
        @Override
        public GameStage getNextStage() {
            if ( GameSession.this.isSessionEnded()){
                return null;
            } else {
                return GameSession.this.getGameStage_PlayerMoves();
            }
        }
    }
}



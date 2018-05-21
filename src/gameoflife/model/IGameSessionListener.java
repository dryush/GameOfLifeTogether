/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

/**
 *
 * @author admin
 */
public interface IGameSessionListener {
    public void onGameOver(GameSession gs);
    public void onGodChanged(GameSession gs);
    public void onBeforeGodNovesStageStart(GameSession gs);
    public void onGodMovesStageStarted(GameSession gs);
    
    public void onGodMovesStageEnded(GameSession gs);
    public void onGodMovesCountChanged(GameSession gs);
}

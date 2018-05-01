/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.model;

/**
 *
 * @author dryush
 */
public interface ICreatureListener {
    void onBirth(Creature creature);
    void onLive(Creature creature);
    void onDie(Creature creature);
    void onDestroy(Creature creature);
}

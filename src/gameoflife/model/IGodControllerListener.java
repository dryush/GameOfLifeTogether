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
public interface IGodControllerListener {
    void onGodSelectCell(Cell cell);
    void onGodEndMove();
}

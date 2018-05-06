/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View;

import gameoflife.model.Cell;

/**
 *
 * @author dryush
 */
public interface ICellViewListener {
    void onCellSelected(Cell cell);
}

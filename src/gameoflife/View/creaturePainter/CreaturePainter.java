/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View.creaturePainter;

import gameoflife.View.CellView;
import gameoflife.model.Creature;
import java.awt.Color;

/**
 *
 * @author Dryush
 */
public class CreaturePainter {

    CreaturePainter() {
    }
    
    public void paint(CellView cellView, Creature creature, Color playerColor){
        if ( null != creature && null != creature.getLiveStage()) switch(creature.getLiveStage()){
              case BIRTH:
                    cellView.setText("+");
                    cellView.setCellBackground( playerColor.brighter());
                    break;
                case LIVE:
                    cellView.setCellBackground( playerColor.darker());
                    cellView.setText("");
                    break;
                case DIE:
                    cellView.setCellBackground( playerColor.darker().darker().darker());
                    cellView.setText("-");
                    break;
                default:
                    break;
        }
    }
}


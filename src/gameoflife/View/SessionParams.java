/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife.View;

import Infrastructure.Size;
import gameoflife.model.Colony;
import gameoflife.model.Field;
import gameoflife.model.God;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javafx.geometry.Side;

/**
 *
 * @author dryush
 */
public class SessionParams {
    public SessionParams(){
        
    }
    public static ArrayList<FieldShapeParams> getFieldShapeNames(){
       ArrayList<FieldShapeParams> shapes = new ArrayList<>();
       shapes.add(new FieldShapeParams("plane_sphere", "Плоская сфера" , new Field.FlatSphere()));
       shapes.add(new FieldShapeParams("romb", "Ромб" , new Field.FlatSphere()));
       return shapes;
    }
    
    public static class FieldShapeParams{
        private String name;
        private String ruName;
        private Field.FieldShape shape;
        private Field.FieldShape getShape(){
            return shape;
        }
        private FieldShapeParams(String name_, String ruName_, Field.FieldShape shape_){
            name = name_;
            ruName = ruName_;
            shape = shape_;
        }
        
        public String getName(){
            return name;
        }
        public String getRuName(){
            return ruName;
        }
        
        @Override
        public String toString(){
            return getRuName();
        }
    }
    
    private FieldShapeParams shapeParams = getFieldShapeNames().get(0);
    private Size fieldSize = new Size(20, 20);
    private Players players = new Players();
    private int epochCount = 1;
    private int firstMoveActionsCount = 1;
    private int moveActionsCount = 1;
    
    public void setEpochCount(int count){
        epochCount = count;
    }
    public int getEpochCount(){
        return epochCount;
    }
    public void setFirstMoveActionsCount(int count){
        firstMoveActionsCount = count;
    }
    public int getFirstMoveActionsCount(){
        return firstMoveActionsCount;
    }
    public void setMoveActionsCount( int count){
        this.moveActionsCount = count;
    }
    public int getMoveActionsCount(){
        return moveActionsCount;
    }
    
    public void setFieldShapeParams(FieldShapeParams params){
        this.shapeParams = params;
    }
    
    public void setFieldSize( Size size){
        this.fieldSize = size;
    }
    
    public void addPlayer(Players.PlayerParameters params){
        this.players.putPlayerParametrs(new God(), params);
    }
    
    public void addPlayer(God god, Players.PlayerParameters params){
        this.players.putPlayerParametrs(god, params);
    }
    
    public Players.PlayerParameters getPlayerParams(God god){
       return players.getPlayerParameters(god);
    }
    
    public Players getPlayers(){
        return players;
    }
    
    public Players.PlayerParameters getPlayerParams(Colony colony){
       return players.getPlayerParameters(colony);
    }
    public Collection<God> getGods(){
        return players.getGods();
    }
    
    public Field.FieldBuilder configureFieldBuilder(Field.FieldBuilder builder){
        return builder.setGods(players.getGods()).setShape( shapeParams.getShape()).setSize(fieldSize);
    }
}

package com.github.aites.localtest.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class BlockNode {
	private Label name;
	//private TinyBlock block;
	private BlockNode parent;
	private Rectangle node;
	
	private Locate leftPoint;
    private Locate rightPoint;
    private Locate parentPoint;
  
    private Locate downPoint;
    private Locate upPoint;
    
    
    private Line relation;
    
    private final StringProperty blockNameProperty;
    private StringProperty parentProperty;
    
    private Locate startPoint;
    private Locate endPoint;
    public BlockNode(String blockName, double x,double y){
    	node = new Rectangle(x,y,100,50);
        name = new Label();
         
        startPoint = new Locate(x,y);
        endPoint = new Locate(x+100,y+50);
    	name.setLayoutX(x+30);
        name.setLayoutY(y+17);
        name.setText(blockName);
        
        node.setFill(Color.TRANSPARENT);
        node.setStroke(Color.BLACK);
        node.setStrokeWidth(1);
       // this.block = block;
    	System.out.println(blockName);
    	//System.out.println(block.toString());
    	
    	leftPoint = new Locate(x,y+25);
    	rightPoint = new Locate(x+100,y+25);
    	
    	downPoint = new Locate(x+50,y);
    	upPoint = new Locate(x+50,y+50);
    	
    	blockNameProperty = new SimpleStringProperty(blockName);
        parentProperty = new SimpleStringProperty(" ");
    }
    public void hasRelation(BlockNode parent){
        this.parent = parent;
        parentPoint = parent.getLRightPoint();
        parentProperty = parent.getBlockNameProperty();
        relation = new Line(leftPoint.getX(),leftPoint.getY(),parentPoint.getX(), parentPoint.getY());
   }
    public void hasVerticalRelation(BlockNode parent){
        this.parent = parent;
        parentPoint = parent.getUpPoint();
        parentProperty = parent.getBlockNameProperty();
        relation = new Line(downPoint.getX(),downPoint.getY(),parentPoint.getX(), parentPoint.getY());
   }
    public void addToPane(AnchorPane pane){
        pane.getChildren().add(node);
        pane.getChildren().add(name);
        if(relation != null){
               pane.getChildren().add(relation);
        } 
    }
    public Locate getLeftPoint(){
        return leftPoint;
    }
    public Locate getLRightPoint(){
        return rightPoint;
    }
    public Locate getUpPoint(){
        return upPoint;
    }
    public Locate getLDownPoint(){
        return downPoint;
    }
    public StringProperty getBlockNameProperty(){
        return blockNameProperty;
    }
    public Locate getStartPoint(){
    	return startPoint;
    }
    public Locate getEndPoint(){
    	return endPoint;
    }
    
}

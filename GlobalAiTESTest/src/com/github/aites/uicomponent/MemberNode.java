package com.github.aites.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MemberNode {
	private Rectangle node;
    private Label name;
    private MemberNode parent;
    
    private Locate downPoint;
    private Locate topPoint;
    private Locate parentPoint;
    
    private final StringProperty nodeNameProperty;
    private final StringProperty stateProperty;
    private StringProperty parentProperty;
    
    private Line relation;
    
    public MemberNode(double x, double y, String nodeName){
        node = new Rectangle(x,y,200,50);
        name = new Label();
        name.setLayoutX(x+35);
        name.setLayoutY(y+17);
        name.setText(nodeName);
               
        node.setFill(Color.TRANSPARENT);
        node.setStroke(Color.BLACK);
        node.setStrokeWidth(1);
        
        downPoint = new Locate(x+100,y+50);
        topPoint = new Locate(x+100,y);
        
        nodeNameProperty = new SimpleStringProperty(nodeName);
        stateProperty = new SimpleStringProperty("connected");
        parentProperty = new SimpleStringProperty(" ");
        
    }
    public void hasRelation(MemberNode parent){
        this.parent = parent;
        parentPoint = parent.getDownPoint();
        parentProperty = parent.getNodeNameProperty();
        relation = new Line(topPoint.getX(),topPoint.getY(),parentPoint.getX(), parentPoint.getY());
   }
   public void addToPane(AnchorPane pane){
       pane.getChildren().add(node);
       pane.getChildren().add(name);
       if(relation != null){
              pane.getChildren().add(relation);
       } 
   }
   
   public Locate getDownPoint(){
       return downPoint;
   }
   
   public StringProperty getNodeNameProperty(){
       return nodeNameProperty;
   }
    public StringProperty getStateProperty(){
       return stateProperty;
   }
    public StringProperty getParentProperty(){
       return parentProperty;
   }
}

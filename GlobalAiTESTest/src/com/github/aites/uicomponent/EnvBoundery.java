package com.github.aites.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class EnvBoundery {
	private Rectangle node;
	private MemberNode parent;
	
	private Locate topPoint;
    private Locate parentPoint;
    private Line relation;
    
    private double memberXpoint;
    private double memberYpoint;
    
    private double x;
    private double y;
    public EnvBoundery(double x, double y){
        node = new Rectangle(x,y,650,350);
      
        node.setFill(Color.TRANSPARENT);
        node.setStroke(Color.BLACK);
        node.setStrokeWidth(1);
           
        topPoint = new Locate(x+175,y);
        memberXpoint= x+10;
        memberYpoint= y+10;
        this.x =x;
        this.y =y;
    
        
    }
    public void hasRelation(MemberNode parent){
        this.parent = parent;
        parentPoint = parent.getDownPoint();
      
        relation = new Line(topPoint.getX(),topPoint.getY(),parentPoint.getX(), parentPoint.getY());
   }
    public void addToPane(AnchorPane pane){
        pane.getChildren().add(node);
       
        if(relation != null){
               pane.getChildren().add(relation);
        } 
    }
    public MemberNode addDeviceMember(String name){
    	MemberNode member = new MemberNode(memberXpoint,memberYpoint,name);
    	memberXpoint += 210;
    	if(memberXpoint>x+600){
    		memberXpoint = memberXpoint - 630;
    		memberYpoint += 80;
    	}
    	return member;
    }
}

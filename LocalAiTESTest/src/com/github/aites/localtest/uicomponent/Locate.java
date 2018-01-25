/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aites.localtest.uicomponent;

/**
 *
 * @author srsok
 */
public class Locate {
    private double x;
    private double y;
    
    public Locate(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Locate(){
        x =0;
        y =0;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    
    public String toString(){
        return "x: "+x+",y: "+y;
    }
    public void setLocation(double x, double y){
        this.x =x;
        this.y =y;
    }
}

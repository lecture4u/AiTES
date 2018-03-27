package com.github.aites.framework.dbcomponent;

public class DBProperty {
	    private String name;
	    private String type;
	    private boolean isNullable;
	   
	    public DBProperty(String name, String type, boolean isNullable){
	        this.name = name;
	        this.type = type;
	        this.isNullable = isNullable;
	    }
	    
	    public void setName(String name){
	        this.name = name;
	    }
	    public void setType(String type){
	        this.type = type;
	        
	    }
	    public String getName(){
	        return name;
	    }
	    public String getType(){
	        return type;
	    }
	    public boolean getIsNullable(){
	        return isNullable;
	    }
}

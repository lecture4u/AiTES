package com.github.aites.framework.dbcomponent;

import java.util.ArrayList;

public class DBTable {
	private String tableName;
    private ArrayList<DBProperty> propertyList;
    private ArrayList<DBForeignKey> fkList;
    private String primaryKey;
    public DBTable(String tableName, String primaryKey){
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        propertyList = new ArrayList<DBProperty>();
        fkList = new ArrayList<DBForeignKey>();
        
    }
    public void addProperty(DBProperty property){
        propertyList.add(property);
    }
    public void addFK(DBForeignKey fk){
    	fkList.add(fk);
    }
    public void setTableName(String tableName){
        this.tableName = tableName;
    }
    public String getTableName(){
        return tableName;
    }
    public void setPrimaryKey(String primaryKey){
        this.primaryKey = primaryKey;
        
    }
    public ArrayList<DBForeignKey> getFKlist(){
    	return fkList;
    }
    public String getPrimaryKey(){
        return primaryKey;
    }
    public void setPropertys(ArrayList<DBProperty> propertyList){
        this.propertyList = propertyList;
    }
    public ArrayList<DBProperty> getPropertys(){
        return propertyList;
    }
    public boolean isHasFK(){
    	if(fkList.size() !=0){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    public void printDBTable(){
    	System.out.println("Table Name: "+tableName);
    	System.out.println("Primary Key: "+primaryKey);
    	System.out.println("-------properties--------");
    	for(int i=0; i<propertyList.size(); i++){
    		System.out.println(propertyList.get(i).getName());
    	}
    	if(fkList.size() !=0){
    		System.out.println("-------foreign keys---------");
    	
    		for(int i=0; i<fkList.size(); i++){
        		System.out.println(fkList.get(i).getFkTableName() + " to "+ fkList.get(i).getFkColumnName() );
        	}
    	
    	
    		System.out.println("----------------------------");
    	}
    	System.out.println("");
    }
}

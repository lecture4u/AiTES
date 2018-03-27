package com.github.aites.framework.ruleset;

import java.util.ArrayList;

import com.github.aites.framework.dbcomponent.DBForeignKey;
import com.github.aites.framework.dbcomponent.DBTable;

public class OWLClassGenerator {
	
	
	private ArrayList<DBTable > tableList;
	public OWLClassGenerator(ArrayList<DBTable > tableList){
		this.tableList = tableList;
	}
	public String classRelationGenerate(DBTable table){
		OWLPropertyGenerator owlpropg = new OWLPropertyGenerator();
		if(table.isHasFK()){
    		  System.out.println("Table "+table.getTableName() + " is have foreign key");
    		  ArrayList<DBForeignKey> fkList = table.getFKlist();
    		 for(int j=0; j<fkList.size(); j++){
    			 for(int i=0; i<tableList.size(); i++){
    				 DBTable tempDT = tableList.get(i);
       	      	     if(tempDT.getTableName().equals(fkList.get(j).getFkTableName())){
       	      	    	 System.out.println("Table "+table.getTableName() + " 's foreign table is "+tempDT.getTableName());
       	      	    	 if(table.getPrimaryKey().equals(tempDT.getPrimaryKey())){
       	      	    		 System.out.println("Table "+table.getTableName()+" and "+tempDT.getTableName()+" share Primary Key, Apply Class Hirearchy rule");
       	      	    		 String subClassBody = owlSubClassGener(stringFirstUpper(table.getTableName()),stringFirstUpper(tempDT.getTableName()));
       	      	    		 return subClassBody;
       	      	    	 }
       	      	    	 else{
       	      	    		 String objectPropertyBody = owlpropg.owlObjectPropertyGener(fkList.get(j).getFkColumnName());
       	      	    		 return objectPropertyBody;
       	      	    	 }
       	      	     }
    			 }
    		 }
    		  
    	}
		return "";
	}
	private String owlSubClassGener(String parentClass, String childClass){
		String subClassDeclaration = "SubClassOf(:"+childClass+" :"+parentClass+")\n";
		return subClassDeclaration;
	}
	public String owlClassGener(String tableName){
		
	    String generateClass = "Declaration(Class(:"+tableName+"))\n";
		return generateClass;
	}
	private String stringFirstUpper(String data){
		String transString = data.substring(0,1);
		transString = transString.toUpperCase();
		transString += data.substring(1);
		return transString;
	}
}

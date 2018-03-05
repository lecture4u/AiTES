package com.github.aites.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tables {
	private StringProperty tableName;

	
	public Tables(String tableName){
		this.tableName = new SimpleStringProperty(tableName);
		
	}
	
	public StringProperty tableNameProperty(){
		return tableName;
	}
}

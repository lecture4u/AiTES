package com.github.aites.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Attributes {
	private StringProperty attributeName;
	private StringProperty type;
	
	public Attributes(String attributeName, String type){
		this.attributeName = new SimpleStringProperty(attributeName);
		this.type = new SimpleStringProperty(type);
		
	}
	
	public StringProperty attributeNameProperty(){
		return attributeName;
	}
	public StringProperty typeNameProperty(){
		return type;
	}
}

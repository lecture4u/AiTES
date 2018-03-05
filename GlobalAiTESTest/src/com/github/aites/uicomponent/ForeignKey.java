package com.github.aites.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ForeignKey {
	private StringProperty attributeName;
	private StringProperty rt;
	
	public ForeignKey(String attributeName, String rt){
		this.attributeName = new SimpleStringProperty(attributeName);
		this.rt = new SimpleStringProperty(rt);
		
	}
	
	public StringProperty attributeNameProperty(){
		return attributeName;
	}
	public StringProperty rtProperty(){
		return rt;
	}
}

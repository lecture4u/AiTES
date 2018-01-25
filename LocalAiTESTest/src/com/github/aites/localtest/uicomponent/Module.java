package com.github.aites.localtest.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Module {
private final StringProperty moduleName;
	
	public Module(String moduleName){
		this.moduleName = new SimpleStringProperty(moduleName);
	}
	public String getModuleName(){
		return moduleName.get();
	}
	public StringProperty moduleNameProperty(){
		return moduleName;
	}
}

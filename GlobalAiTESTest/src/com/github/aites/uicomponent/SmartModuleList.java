package com.github.aites.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SmartModuleList {
	private StringProperty moduleName;
	private StringProperty variable;
	private StringProperty outputFile;
	
	public SmartModuleList(String moduleName, String variable, String outputFile){
		this.moduleName = new SimpleStringProperty(moduleName);
		this.variable = new SimpleStringProperty(variable);
		this.outputFile = new SimpleStringProperty(outputFile);
		
	}
	
	public StringProperty moduleNameProperty(){
		return moduleName;
	}
	public StringProperty variableProperty(){
		return variable;
	}
	public StringProperty outputFileProperty(){
		return outputFile;
	}
}

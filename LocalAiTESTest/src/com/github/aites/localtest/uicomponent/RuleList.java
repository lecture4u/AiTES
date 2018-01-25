package com.github.aites.localtest.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RuleList {
	private StringProperty ruleName;
	private StringProperty classAtom;
	private StringProperty propertyAtom;
	private StringProperty builtInAtom;
	public RuleList(String ruleName, String classAtom, String propertyAtom, String builtInAtom){
		this.ruleName = new SimpleStringProperty(ruleName);
		this.classAtom = new SimpleStringProperty(classAtom);
		this.propertyAtom = new SimpleStringProperty(propertyAtom);
		this.builtInAtom = new SimpleStringProperty(builtInAtom);
		
	}
	public StringProperty ruleNameProperty(){
		return ruleName;
	}
	public StringProperty classAtomProperty(){
		return classAtom;
	}
	public StringProperty propertyAtomProperty(){
		return propertyAtom;
	}
	public StringProperty builtInAtomProperty(){
		return builtInAtom;
	}
}

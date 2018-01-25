package com.github.aites.localtest.uicomponent;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StateReason {
	private StringProperty currentTime;
	private StringProperty individual;
	private StringProperty classAtom;
	private StringProperty propertyData;
	private StringProperty rule;
	private StringProperty result;
	
	public StateReason(ArrayList<String> reasonComponentList){
		this.currentTime = new SimpleStringProperty(reasonComponentList.get(0));
		this.individual = new SimpleStringProperty(reasonComponentList.get(1));
		this.classAtom = new SimpleStringProperty(reasonComponentList.get(2));
		this.propertyData = new SimpleStringProperty(reasonComponentList.get(3));
		this.rule = new SimpleStringProperty(reasonComponentList.get(4));
		this.result = new SimpleStringProperty(reasonComponentList.get(5));
		
	}
	public StringProperty currentTimeProperty(){
		return currentTime;
	}
	public StringProperty individualProperty(){
		return individual;
	}
	public StringProperty classAtomProperty(){
		return classAtom;
	}
	public StringProperty propertyDataProperty(){
		return propertyData;
	}
	public StringProperty ruleProperty(){
		return rule;
	}
	public StringProperty resultProperty(){
		return result;
	}
}

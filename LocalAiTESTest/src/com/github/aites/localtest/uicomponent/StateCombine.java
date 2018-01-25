package com.github.aites.localtest.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StateCombine {
	private StringProperty currentTime;
	private StringProperty state;
	private StringProperty state2;
	private StringProperty state3;
	public StateCombine(String currentTime, String state, String state2, String state3){
		this.currentTime = new SimpleStringProperty(currentTime);
		this.state = new SimpleStringProperty(state);
		this.state2 = new SimpleStringProperty(state2);
		this.state3 = new SimpleStringProperty(state3);
	}
	public StringProperty currentTimeProperty(){
		return currentTime;
	}
	public StringProperty stateProperty(){
		return state;
	}
	public StringProperty state2Property(){
		return state2;
	}
	public StringProperty state3Property(){
		return state3;
	}
}

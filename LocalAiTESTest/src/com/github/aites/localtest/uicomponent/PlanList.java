package com.github.aites.localtest.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlanList {
	private StringProperty planName;
	private StringProperty totalSpendTime;
	private StringProperty perpose;
	
	public PlanList(String planName, String totalSpendTime, String perpose){
		this.planName = new SimpleStringProperty(planName);
		this.totalSpendTime = new SimpleStringProperty(totalSpendTime);
		this.perpose = new SimpleStringProperty(perpose);
		
	}
	public StringProperty planNameProperty(){
		return planName;
	}
	public StringProperty totalSpendTimeProperty(){
		return totalSpendTime;
	}
	public StringProperty perposeProperty(){
		return perpose;
	}

}

package com.github.aites.localtest.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlanActionList {
	private StringProperty actionName;
	private StringProperty module;
	private StringProperty target;
	private StringProperty spendTime;
	private StringProperty processState;
	public PlanActionList(String actionName, String module, String target, String spendTime, String processState){
		this.actionName = new SimpleStringProperty(actionName);
		this.module = new SimpleStringProperty(module);
		this.target = new SimpleStringProperty(target);
		this.spendTime = new SimpleStringProperty(spendTime);
		this.processState = new SimpleStringProperty(processState);
	}
	public StringProperty actionNameProperty(){
		return actionName;
	}
	public StringProperty moduleProperty(){
		return module;
	}
	public StringProperty targetProperty(){
		return target;
	}
	public StringProperty spendTimeProperty(){
		return spendTime;
	}
	public StringProperty processStateProperty(){
		return processState;
	}
}

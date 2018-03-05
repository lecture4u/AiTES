package com.github.aites.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MemberList {
	private StringProperty memberName;
	private StringProperty affilience;
	
	public MemberList(String memberName, String affilience){
		this.memberName = new SimpleStringProperty(memberName);
		this.affilience = new SimpleStringProperty(affilience);
	}
	
	public StringProperty memberNameProperty(){
		return memberName;
	}
	
	public StringProperty affilienceProperty(){
		return affilience;
	}
}

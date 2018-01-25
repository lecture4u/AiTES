package com.github.aites.localtest.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SubTopic {
	private final StringProperty topicName;
	
	public SubTopic(String subTopic){
		this.topicName = new SimpleStringProperty(subTopic);
	}
	public String getTopicName(){
		return topicName.get();
	}
	public StringProperty topicNameProperty(){
		return topicName;
	}
}

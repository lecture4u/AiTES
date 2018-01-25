package com.github.aites.localtest.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PubTopic {
	private final StringProperty topicName;
	
	public PubTopic(String pubTopic){
		this.topicName = new SimpleStringProperty(pubTopic);
	}
	public String getTopicName(){
		return topicName.get();
	}
	public StringProperty topicNameProperty(){
		return topicName;
	}
}

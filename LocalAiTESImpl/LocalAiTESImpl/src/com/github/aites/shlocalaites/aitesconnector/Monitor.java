package com.github.aites.shlocalaites.aitesconnector;

import com.github.aites.framework.aitesconnector.Factory;
import com.github.aites.framework.aitesmanager.Manager;
import com.github.aites.shlocalaites.aitesmanager.MonitorManager;



public class Monitor implements Factory{
	private String mqttMessage;
	private String clientID;
	private String ruleSetURL;
	public Monitor(String mqttMessage, String clientID, String ruleSetURL){
		this.mqttMessage = mqttMessage;
		this.clientID = clientID;
		this.ruleSetURL = ruleSetURL;
	}
	@Override
	public Manager createManager() {
		
		return new MonitorManager(mqttMessage,clientID,ruleSetURL);
	}

}

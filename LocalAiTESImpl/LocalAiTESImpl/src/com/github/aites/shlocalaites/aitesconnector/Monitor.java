package com.github.aites.shlocalaites.aitesconnector;

import com.github.aites.shlocalaites.aitesmanager.MonitorManager;

import AiTESConnector.Factory;
import AiTESManager.Manager;

public class Monitor implements Factory{
	private String mqttMessage;
	private String deviceName;
	private String clientID;
	public Monitor(String mqttMessage, String deviceName, String clientID){
		this.mqttMessage = mqttMessage;
		this.deviceName = deviceName;
		this.clientID = clientID;
	}
	@Override
	public Manager createManager() {
		
		return new MonitorManager(mqttMessage,deviceName,clientID);
	}

}

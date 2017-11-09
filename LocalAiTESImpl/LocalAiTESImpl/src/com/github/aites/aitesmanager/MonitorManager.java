package com.github.aites.aitesmanager;

import com.github.aites.monitor.DataPreProcessor;

import AiTESManager.Manager;
import Monitor.PreProcessor;

public class MonitorManager extends Manager{
	private String mqttMessage;
	private String deviceName;
	private String clientID;
	public MonitorManager(String mqttMessage, String deviceName, String clientID){
		this.mqttMessage = mqttMessage;
		this.deviceName = deviceName;
		this.clientID = clientID;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("----------Monitoring base data----------");
		System.out.println("Topic:"+clientID);
		System.out.println("Message:"+mqttMessage);
		System.out.println("deviceName:"+deviceName);
		System.out.println("-------------------------------------------------------------");
		
		PreProcessor pr = new DataPreProcessor();
		pr.processData(mqttMessage);
	}

}

package com.github.aites.shlocalaites.aitesmanager;

import com.github.aites.framework.aitesmanager.Manager;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.monitor.EnvData;
import com.github.aites.framework.monitor.PreProcessor;

import com.github.aites.shlocalaites.gkconnect.MonitorEnvDataWriter;

import com.github.aites.shlocalaites.monitor.DataPreProcessor;

import com.github.aites.shlocalaites.monitor.MonitorHRAlgorithm;



public class MonitorManager extends Manager{
	private String mqttMessage;
	private String deviceName;
	private String clientID;
	
	private EnvData envdata;
	LogWritter log = LogWritter.getInstance();
	public MonitorManager(String mqttMessage, String deviceName, String clientID){
		this.mqttMessage = mqttMessage;
		this.deviceName = deviceName;
		this.clientID = clientID;
	}
	@Override
	public void run() {
		log.logInput("---------------MonitorManager: Monitoring IoT env data from"+deviceName+"---------------");
		log.logInput("ClientID"+clientID);
		log.logInput("Message:"+mqttMessage);
		log.logInput("deviceName:"+deviceName);
		
		
		
		PreProcessor pr = new DataPreProcessor();
		pr.processData(mqttMessage);
		
		envdata = (EnvData)pr.getProcessedData();
		
		MonitorHRAlgorithm mh = new MonitorHRAlgorithm(envdata);
		
		String mReult = mh.envDataHRAlgorithm();
		String position = ((DataPreProcessor)pr).getPosition();
		String temperture = ((DataPreProcessor)pr).getTemperture();
		
		DBConnector dc = new MonitorEnvDataWriter(envdata.getCollectDate(),clientID,mh.getAllEnvData(),mReult,position,temperture);
		dc.dbConnect();
		
		log.logInput("----------------------------------------------------------------");
	}
	
}

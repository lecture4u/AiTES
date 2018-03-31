package com.github.aites.shlocalaites.aitesmanager;

import com.github.aites.shlocalaites.aitesconnector.Analyzer;
import com.github.aites.shlocalaites.aitesconnector.Monitor;
import com.github.aites.shlocalaites.gkconnect.MonitorEnvDataReader;
import com.github.aites.shlocalaites.gkconnect.MonitorEnvDataWriter;
import com.github.aites.shlocalaites.log.LogWritter;
import com.github.aites.shlocalaites.monitor.DataPreProcessor;
import com.github.aites.shlocalaites.monitor.EnvData;
import com.github.aites.shlocalaites.monitor.MonitorHRAlgorithm;

import AiTESConnector.ManagerAF;
import AiTESManager.Manager;
import LocalPropertyConnect.DBConnector;
import Monitor.PreProcessor;

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

package com.github.aites.aitesmanager;

import com.github.aites.gkconnect.MonitorEnvDataWriter;
import com.github.aites.monitor.DataPreProcessor;
import com.github.aites.monitor.EnvData;

import AiTESManager.Manager;
import LocalPropertyConnect.DBConnector;
import Monitor.PreProcessor;

public class MonitorManager extends Manager{
	private String mqttMessage;
	private String deviceName;
	private String clientID;
	
	private EnvData envdata;
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
		
		envdata = (EnvData)pr.getProcessedData();
		
		for(int di=0; di<envdata.getDeviceData().size(); di++){
			DBConnector dc = new MonitorEnvDataWriter(envdata.getCollectDate(),clientID,envdata.getDeviceNmae().get(di),envdata.getDeviceData().get(di));
			dc.dbConnect();
		}
	}

}

package com.github.aites.aitesmanager;

import com.github.aites.gkconnect.MonitorEnvDataReader;
import com.github.aites.gkconnect.MonitorEnvDataWriter;
import com.github.aites.monitor.DataPreProcessor;
import com.github.aites.monitor.EnvData;
import com.github.aites.monitor.MonitorHRAlgorithm;

import AiTESManager.Manager;
import LocalPropertyConnect.DBConnector;
import Monitor.PreProcessor;

public class MonitorManager extends Manager{
	private String mqttMessage;
	private String deviceName;
	private String clientID;
	
	private EnvData envdata;
	private String psReult;
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
		
		
		PreProcessor pr = new DataPreProcessor();
		pr.processData(mqttMessage);
		
		envdata = (EnvData)pr.getProcessedData();
		
		MonitorHRAlgorithm mh = new MonitorHRAlgorithm(envdata);
		String mReult = mh.envDataHRAlgorithm();
		
		DBConnector dc = new MonitorEnvDataWriter(envdata.getCollectDate(),clientID,mh.getAllEnvData(),mReult);
		dc.dbConnect();
		
		if(mReult.equals("under") ){
			psReult = "under:";
			System.out.println("Occur env situation, Call analyzer");
		}
		else if(mReult.equals("over")){
			psReult = "over:";
		}
		else{
			psReult = "normal:";
		}
		
		
		
		System.out.println("-------------------------------------------------------------");
	}
	public String getEnvPSresult(){
		return psReult;
	}
}

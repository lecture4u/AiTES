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
	private String clientID;
	private String ruleSetURL;
	
	private EnvData envdata;
	LogWritter log = LogWritter.getInstance();
	public MonitorManager(String mqttMessage, String ruleSetURL, String clientID){
		this.mqttMessage = mqttMessage;
		this.clientID = clientID;
		this.ruleSetURL = ruleSetURL;
	}
	@Override
	public void run() {
		log.logInput("---------------MonitorManager: Monitoring IoT env data and reasoning monitoring factor exceed the threshold.---------------");
		log.logInput("ClientID"+clientID);
		log.logInput("Message:"+mqttMessage);
		log.logInput("RuleSetURL:"+ruleSetURL);
		DataPreProcessor dpp = new DataPreProcessor();
		dpp.processData(mqttMessage);
		
        envdata = (EnvData)dpp.getProcessedData();
		
		MonitorHRAlgorithm mh = new MonitorHRAlgorithm(envdata, ruleSetURL);
	
		String ps = mh.envDataHRAlgorithm();
		String position = ((DataPreProcessor)dpp).getPosition();
		String temperture = ((DataPreProcessor)dpp).getTemperture();
		log.logInput("#####Rasoning result - Smart Home's entire Power consumtion state is:"+ps+"#####");
		
		DBConnector dc = new MonitorEnvDataWriter(envdata.getCollectDate(),clientID,mh.getAllEnvData(),ps,position,temperture);
		log.logInput("&&&&&Write monitoring process to global knowledge&&&&&");
		log.logInput("monitoring date:"+envdata.getCollectDate());
		log.logInput("wirte local collaborative ID:"+clientID);
		log.logInput("Device sensor data:"+mh.getAllEnvData());
		log.logInput("Power consumtion state:"+ps);
		log.logInput("Position Data:"+position);
		log.logInput("Temperture Data:"+temperture);
		dc.dbConnect();
	}
	
}

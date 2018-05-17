package com.github.aites.shlocalaites.aitesmanager;

import java.lang.reflect.Method;

import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import com.github.aites.framework.aitesmanager.Manager;
import com.github.aites.framework.executor.Scheduler;
import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.shlocalaites.gkconnect.ExecutorActionWriter;



public class ExecutorManager extends Manager{
	Timer timer = Timer.getInstance();
	Scheduler scheduler = Scheduler.getInstance();
	LogWritter log = LogWritter.getInstance();
	private String clientID;
	private String gatewayID;
	private String dmFolderName;
	private String pubTopic;
	public ExecutorManager(String clientID, String gatewayID, String dmFolderName){
		this.clientID = clientID;
		this.gatewayID = gatewayID;
		this.dmFolderName = dmFolderName;
		combineExecuteTopic();
	}
	public ExecutorManager(){
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.logInput("---------------ExecutorManager: Execute Plan about This System Time:"+timer.getWholeTime()+".---------------"); 
		log.logInput("clientID:"+clientID);
		log.logInput("gatewayID:"+gatewayID);
		log.logInput("dmFolderName:"+dmFolderName);
		log.logInput("pubTopic:"+pubTopic);
		ExecutorManager exd = new ExecutorManager();
    	Class cl = exd.getClass();
		scheduler.setExecutuInfo(cl, pubTopic, dmFolderName);
		scheduler.execute(timer.getWholeTime());
		scheduler.printAllPlan();
	}
	public void writeExecuteResult(String fPubTopic, String fSendModuleName){
		DBConnector dc = new ExecutorActionWriter(timer.getWholeTime(),fPubTopic,fSendModuleName);
		
		
		log.logInput("&&&&&Write executring process to global knowledge ");
		log.logInput("Execute Time"+timer.getWholeTime());
		log.logInput("final execute pub Topic:"+fPubTopic);
		log.logInput("final execute module file name:"+fSendModuleName);
		dc.dbConnect();
		
		
	}
    private void combineExecuteTopic(){
    	String category = "IoTgateway";
    	pubTopic = category+"/"+clientID+"/"+gatewayID;
    }
}

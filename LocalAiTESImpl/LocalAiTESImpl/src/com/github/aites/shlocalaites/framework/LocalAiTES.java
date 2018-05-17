package com.github.aites.shlocalaites.framework;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.aites.framework.aitesconnector.ManagerAF;
import com.github.aites.framework.aitesmanager.Manager;
import com.github.aites.framework.communicate.DataTransfer;
import com.github.aites.framework.framework.LocalAiTESManager;
import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.globalknowledge.ConnectionStarter;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.orchestration.Device;
import com.github.aites.framework.orchestration.Participants;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.ruleset.RuleSetManager;
import com.github.aites.shlocalaites.aitesconnector.Analyzer;
import com.github.aites.shlocalaites.aitesconnector.Executor;
import com.github.aites.shlocalaites.aitesconnector.Monitor;
import com.github.aites.shlocalaites.aitesconnector.Planner;
import com.github.aites.shlocalaites.gkconnect.AnalyzerStateSetReader;
import com.github.aites.shlocalaites.gkconnect.MonitorEnvDataReader;



public class LocalAiTES extends LocalAiTESManager{
	DataTransfer df = DataTransfer.getInstance();
    Timer timer = Timer.getInstance();
	LogWritter log = LogWritter.getInstance();
	Participants participants = Participants.getInstance();
	DBConnector dc;
	
	
	public void runAiTES(){
		// Set initialize variables.
		timer.setSystemTime("2017.7.17 0:00");
		gkURL = "jdbc:mysql://220.149.235.85:3306/globalknowledge";
		adaptiveActionList = new ArrayList<String>(){{
		    add("powersaving");
		    add("ready");
		    add("stanby");
		    add("activity");	
		}};
		ruleSetURL = "smarthome.xml";
		dymoduleFolder = "modules";
		participants.setSoftwareActionList(adaptiveActionList);
		
		//Connect global knowledge server.
    	try{
    		DBConnector db = new ConnectionStarter(gkURL,"root","1234");
    		db.dbConnect();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	//Connect collaborative communication server.
    	brokerURL = "tcp://127.0.0.1:1883";
    	clientID = "Global1/Local1";
    	
    	LocalAiTES smd = new LocalAiTES();
    	Class cl = smd.getClass();
    	
    	try{
    		df.setMQTTConnection(brokerURL, cl, clientID);
    		df.setAfficationMode("Local");
    		df.runClient();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	log.logInput("~~~~~~~~Smart Home Local AiTES init Setting~~~~~~~~");
    	log.logInput("Init System time: "+timer.getWholeTime());
    	log.logInput("Connected Global Knowledge URL: " +gkURL);
    	log.logInput("Collaborative Communication Server URL: "+brokerURL);
    	String actionList="[";
    	for(int i=0; i<adaptiveActionList.size(); i++){
    		 if(i == adaptiveActionList.size()-1){
    			 actionList = actionList+"action"+i+":"+adaptiveActionList.get(i);
        	 }
        	 else{
        		 actionList = actionList+"action"+i+":"+adaptiveActionList.get(i)+", ";
        	 }	
    	}
    	actionList = actionList+"]";
    	log.logInput("SmartHome Environment Action List: "+actionList);
    	log.logInput("Local AiTES RuleSet URL:"+ruleSetURL);
    	log.logInput("Local AiTES dynamic module URL:"+dymoduleFolder);
    	log.logInput("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	log.logFileCreate();
    	
    	File file = new File(dymoduleFolder+"/powersaving.jar");
    	System.out.println(file.getAbsolutePath());
    	System.out.println(file.getName());

    }
	@Override
	public void manageAiTES(String mqttMessage, String topic, String deviceName) throws Exception {
        String[] topicSpliter = topic.split("/");
		
		if(topicSpliter[4].equals("envData")){
			processFeedBackLoops(mqttMessage, topic, deviceName);
		}
		else if(topicSpliter[4].equals("connection")){
			manageConnection(mqttMessage, topic, "connect");
		}
		else if(topicSpliter[4].equals("disconnection")){
			manageConnection(mqttMessage, topic, "disconnect");
		}
		System.out.println("Meessage Processing Complete");
		log.logFileCreate();
	}
	private void processFeedBackLoops(String mqttMessage, String topic, String deviceName){
		// Receive probe data.
		log.logInput("----------Get IoT environment Data from IoT gateway----------");
		log.logInput("Topic:"+topic);
		log.logInput("Message:"+mqttMessage);
		log.logInput("deviceName:"+deviceName);
		log.logInput("Current System Time:"+timer.getWholeTime());
		log.logInput("Current connected IoT Env participants:");
		log.logInput(participants.printParticipantsList());
		
		// Rule configuration before process feedback loops, assert individual;
		String feedBackInd  ="SHEdata"+timer.getAbbTime();
		log.logInput("#####Assertion individual to ruleSet:"+feedBackInd+"#####");
		RuleSetManager ruleSetManager = new RuleSetManager("smartHome.xml");
	    ruleSetManager.assertInd(feedBackInd, "SHEdata");
		ruleSetManager.saveRuleSet();
		
		//Monitor: preprocessing, reasoning call analyzer
		Manager af = ManagerAF.getManager(new Monitor(mqttMessage,ruleSetURL,clientID));
		af.run();
		
		//Analyzer:reasoing entire environment when monitoring factor is over limited.
		dc = new MonitorEnvDataReader();
		dc.dbConnect();
        ArrayList<String> monitorInfo = ((MonitorEnvDataReader)dc).getMonitorInfo(); // 0:collectDate, 1:mresult, 2:position, 3:temperture
	    
	    String monitorTime = timer.parseTime(monitorInfo.get(0));
		if(timer.getCurrentTime().equals(monitorTime) && (monitorInfo.get(1).equals("under")||monitorInfo.get(1).equals("over"))){
            log.logInput("Monitoring factor is over limited, call analyzer.");
			
			af = ManagerAF.getManager(new Analyzer(monitorInfo, clientID,ruleSetURL));
			af.run();
			
			dc = new AnalyzerStateSetReader();
			dc.dbConnect();
			
			//Plaing: organizing plan for solution about situation
			String analyedDate = ((AnalyzerStateSetReader)dc).getCollectDate();
			String stateSet = ((AnalyzerStateSetReader)dc).getStateSet();
			String pneed = ((AnalyzerStateSetReader)dc).getPneed();

			if(pneed.equals("yes")){
				log.logInput("Analyzed State Set need plaining, call planner");
				af = ManagerAF.getManager(new Planner(stateSet,analyedDate));
				af.run();
			}
		}
		//Executor: Execute Current Time plans
		af = ManagerAF.getManager(new Executor(clientID,deviceName,dymoduleFolder));
		af.run();
		// Rule configuration after process feedback loops, delete individual assertion;
		log.logInput("#####Delete individual to ruleSet:"+feedBackInd+"#####");
		ruleSetManager = new RuleSetManager("smartHome.xml");
	    ruleSetManager.deleteInd(feedBackInd);
		ruleSetManager.saveRuleSet();
		
		timer.processedTime();
		log.logInput("-------------------------------------------------------------");
	}
	private void manageConnection(String mqttMessage, String topic, String connectionFlag){
		if(connectionFlag.equals("connect")){
			log.logInput("++++++++Get Connection Signal from IoT gateway++++++++");
			log.logInput("Topic:"+topic);
			log.logInput("Message:"+mqttMessage);
			log.logInput("*****Parse connected device data*****");
			int dnStartIndex = mqttMessage.indexOf("{") +2;
			int dnEndIndex = mqttMessage.indexOf("[")-2;
			String deviceName =mqttMessage.substring(dnStartIndex, dnEndIndex);
			try{
				JSONParser parser = new JSONParser();
				Object jsonObj = parser.parse(mqttMessage);
				JSONObject jsonObject = (JSONObject)jsonObj; 
				
			    JSONArray deviceInfo = (JSONArray)jsonObject.get(deviceName);
				
				Iterator iter = deviceInfo.iterator();
				while(iter.hasNext()){
	               
					JSONObject innerObj = (JSONObject) iter.next();
					
					log.logInput("address:"+innerObj.get("address")+", modelCode:"+innerObj.get("modelCode")+", status:"+innerObj.get("status"));
					Device newDevice = new Device(deviceName ,innerObj.get("status").toString(), innerObj.get("address").toString(), innerObj.get("modelCode").toString());
					participants.addDevice(newDevice);			
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			log.logInput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		else if(connectionFlag.equals("disconnect")){
			log.logInput("++++++++Get Disconnection Signal from IoT gateway++++++++");
			log.logInput("Topic:"+topic);
			log.logInput("Message:"+mqttMessage);
			log.logInput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
	
	}
	

}

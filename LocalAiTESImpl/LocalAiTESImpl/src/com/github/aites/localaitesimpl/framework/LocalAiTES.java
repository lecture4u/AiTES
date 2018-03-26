package com.github.aites.localaitesimpl.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.github.aites.aitesconnector.Analyzer;
import com.github.aites.aitesconnector.Executor;
import com.github.aites.aitesconnector.Monitor;
import com.github.aites.aitesconnector.Planner;
import com.github.aites.gkconnect.AnalyzerStateSetReader;
import com.github.aites.gkconnect.AnalyzerStateSetWriter;
import com.github.aites.gkconnect.MonitorEnvDataReader;
import com.github.aites.gkconnect.PlannerPlanReader;
import com.github.aites.log.LogWritter;
import com.github.aites.palnner.Plan;
import com.github.aitest.executor.Timer;

import AiTESConnector.ManagerAF;
import AiTESManager.Manager;
import Communicate.DataTransfer;
import Framework.LocalAiTESManager;
import LocalPropertyConnect.ConnectionStarter;
import LocalPropertyConnect.DBConnector;
import Rule.RuleSetLoader;

public class LocalAiTES extends LocalAiTESManager{
	DataTransfer df = DataTransfer.getInstance();
	Timer timer = Timer.getInstance();
	LogWritter log = LogWritter.getInstance();
	DBConnector dc;
	public LocalAiTES(){
		
	}
	public void runAiTES(){
		timer.setSystemTime("2017.7.17 0:00");
		
		try{
			DBConnector db = new ConnectionStarter("jdbc:mysql://220.149.235.85:3306/globalknowledge","root","1234");
			db.dbConnect();
		}catch(Exception e){
			e.printStackTrace();
		}
		
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
		df.subscription("Effector/#");
		
		//ruleloader.resonOntologyFromFile();
		/*RuleSetLoader ruleloader = new RuleSetLoader("testRuleSet.xml");
		try {
			ruleloader.resonOntologyFromFile();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	@Override
	public void changeRule(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void manageAiTES(String mqttMessage, String topic, String deviceName) throws Exception {
		
		if(topic.equals("Global1")){
			System.out.println("----------Get RuleSet from Global AiTES----------");
			System.out.println("Topic:"+topic);
			System.out.println("Message:"+mqttMessage);
			
			
			String[] topicSplit = topic.split("/");
			System.out.println(topicSplit[3]);
			try {
			      
			      BufferedWriter out = new BufferedWriter(new FileWriter(topicSplit[3]));
				      
			      out.write(mqttMessage); out.newLine();			 			      
			      out.close();			    			   			     
			    } catch (IOException e) {
			        System.err.println(e);
			        System.exit(1);
			    }
			RuleSetLoader ruleloader = new RuleSetLoader(topicSplit[3]);
			try {
				ruleloader.resonOntologyFromFile();
			} catch (OWLOntologyCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			
			log.logInput("----------Get IoT environment Data from IoT gateway----------");
			log.logInput("Topic:"+topic);
			log.logInput("Message:"+mqttMessage);
			log.logInput("deviceName:"+deviceName);
			log.logInput("-------------------------------------------------------------");
		}
		
		
		Manager af = ManagerAF.getManager(new Monitor(mqttMessage,deviceName,clientID));
		af.run();
		
		dc = new MonitorEnvDataReader();
		dc.dbConnect();
		
		ArrayList<String> monitorInfo = ((MonitorEnvDataReader)dc).getMonitorInfo(); // 0:collectDate, 1:mresult, 2:position, 3:temperture
	    
	    String monitorTime = timer.parseTime(monitorInfo.get(0));
	    
		if(timer.getCurrentTime().equals(monitorTime) && (monitorInfo.get(1).equals("under")||monitorInfo.get(1).equals("over"))){
			log.logInput("Situation Occured");
			
			af = ManagerAF.getManager(new Analyzer(monitorInfo, clientID));
			af.run();
			
			dc = new AnalyzerStateSetReader();
			dc.dbConnect();
			String analyedDate = ((AnalyzerStateSetReader)dc).getCollectDate();
			String stateSet = ((AnalyzerStateSetReader)dc).getStateSet();
			String pneed = ((AnalyzerStateSetReader)dc).getPneed();

			if(pneed.equals("yes")){
				log.logInput("Situation need plaining");
				af = ManagerAF.getManager(new Planner(stateSet,analyedDate));
				af.run();
			}
		}
		
	
		
		dc = new PlannerPlanReader(timer.getWholeTime());
		dc.dbConnect();
		ArrayList<Plan> planList =  ((PlannerPlanReader)dc).getPlanList();
		
		af = ManagerAF.getManager(new Executor(planList,timer));
		af.run();
		timer.processedTime();
		log.logFileCreate();
	}

}

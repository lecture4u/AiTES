package com.github.aites.localaitesimpl.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.github.aites.aitesconnector.Monitor;

import AiTESConnector.ManagerAF;
import AiTESManager.Manager;
import Communicate.DataTransfer;
import Framework.LocalAiTESManager;
import LocalPropertyConnect.ConnectionStarter;
import LocalPropertyConnect.DBConnector;
import Rule.RuleSetLoader;

public class LocalAiTES extends LocalAiTESManager{
	DataTransfer df = DataTransfer.getInstance();
	public LocalAiTES(){
		
	}
	public void runAiTES(){
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
			System.out.println("----------Get IoT environment Data from IoT gateway----------");
			System.out.println("Topic:"+topic);
			System.out.println("Message:"+mqttMessage);
			System.out.println("deviceName:"+deviceName);
			System.out.println("-------------------------------------------------------------");
		}
		
		Manager af = ManagerAF.getManager(new Monitor(mqttMessage,deviceName,clientID));
		af.run();
	}

}

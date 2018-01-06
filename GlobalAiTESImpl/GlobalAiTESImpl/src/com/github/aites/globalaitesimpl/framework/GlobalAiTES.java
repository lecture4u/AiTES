package com.github.aites.globalaitesimpl.framework;



import java.io.File;

import Communicate.DataTransfer;
import Framework.LocalAiTESManager;


public class GlobalAiTES extends LocalAiTESManager{
	DataTransfer df = DataTransfer.getInstance();
	private File ruleSet;
	public void setRuleSet(File ruleSet){
		this.ruleSet  = ruleSet;
	}
	public void runAiTES(){
		
		
		brokerURL = "tcp://127.0.0.1:1883";
		clientID = "Global1";
		
		GlobalAiTES smd = new GlobalAiTES();
		Class cl = smd.getClass();
		
		try{
			df.setMQTTConnection(brokerURL, cl, clientID);
			df.setAfficationMode("Global");
			df.runClient();
			df.publish(ruleSet, "Global1/Local1");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void changeRule(String arg0) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void manageAiTES(String arg0, String arg1, String arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

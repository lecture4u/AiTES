package com.github.aites.localtest.utility;

import java.io.FileInputStream;
import java.util.Properties;

public class PropReader {
	private String clientID;
	private String brokerIP;
	
	private String globalknowledgeURL;
	private String connectionID;
	private String connectionPassword;
	
	private String ruleSetURL;
	private String logURL;
	private String moduleURL;
	public PropReader(){
		propReader();
	}
	private void propReader(){
		try{
			String propFile = "./config.properties";
			Properties props = new Properties();
			
			FileInputStream fis = new FileInputStream(propFile);
			props.load(new java.io.BufferedInputStream(fis));
			
			clientID = props.getProperty("clientID");
			brokerIP = props.getProperty("brokerIP");
			
			globalknowledgeURL=props.getProperty("globalknowledgeURL");
			connectionID = props.getProperty("connectionID");
			connectionPassword = props.getProperty("connectionPassword");
			
			ruleSetURL = props.getProperty("ruleSetURL");
			logURL = props.getProperty("logURL");
			moduleURL = props.getProperty("moduleURL");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getCilentID(){
		return clientID;
	}
	public String getBrokerIP(){
		return brokerIP;
	}
	
	public String getGlobalKnowledgeURL(){
		return globalknowledgeURL;
	}
	public String getConnectionID(){
		return connectionID;
	}
	public String getConnectionPassword(){
		return connectionPassword;
	}
	public String getRuleSetURL(){
		return ruleSetURL;
	}
	public String getLogURL(){
		return logURL;
	}
	public String getModuleURL(){
		return moduleURL;
	}
	
}

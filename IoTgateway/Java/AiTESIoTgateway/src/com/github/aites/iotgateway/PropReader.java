package com.github.aites.iotgateway;

import java.io.FileInputStream;
import java.util.Properties;

public class PropReader {
	private String clientID;
	private String brokerIP;
	private String moduleURL;
	
	public void propReader(){
		try{
			String propFile = "./config.properties";
			Properties props = new Properties();
			
			FileInputStream fis = new FileInputStream(propFile);
			props.load(new java.io.BufferedInputStream(fis));
			
			clientID = props.getProperty("clientID");
			brokerIP = props.getProperty("brokerIP");
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
	public String getModuleURL(){
		return moduleURL;
	}
}

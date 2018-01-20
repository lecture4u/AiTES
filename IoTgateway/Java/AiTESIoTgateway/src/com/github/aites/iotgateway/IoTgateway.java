package com.github.aites.iotgateway;

import com.github.aites.mqttclient.BlockChainClient;
import com.github.aites.mqttclient.MQTTClient;

public class IoTgateway {

	public static void main(String[] args) {
		
		
		
		PropReader prop = new PropReader();
		
		// TODO Auto-generated method stub
	
		prop.propReader();
		
		String clientID = prop.getCilentID();
		String brokerIP = prop.getBrokerIP();
		String moduleURL = prop.getModuleURL();
		
		System.out.println(clientID);
		System.out.println(brokerIP);
		System.out.println(moduleURL);
		
		MQTTClient mt = new MQTTClient(clientID, brokerIP, moduleURL);
		mt.runClient();
		
		if(args[0].equals("-t")){
			System.out.println("Test mode run");
			mt.publishTestData();
		}
		else if(args[0].equals("-r")){
			System.out.println("Regular mode run");
		}
		
		//BlockChainClient bclient = new BlockChainClient();
		//bclient.inputTestData();
		
	}

}

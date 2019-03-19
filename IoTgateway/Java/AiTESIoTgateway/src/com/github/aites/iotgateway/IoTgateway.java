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
		String csvURL = prop.getCsvURL();
		System.out.println(clientID);
		System.out.println(brokerIP);
		System.out.println(moduleURL);
		
		
		
		if(args[0].equals("-t")){
			System.out.println("Test mode run");
			MQTTClient mt = new MQTTClient(clientID, brokerIP, moduleURL,csvURL);
			mt.runClient();
			mt.publishInitDeviceInfo();
			mt.makeScheduleFromData();
			//mt.publishTestData();
		}
		else if(args[0].equals("-r")){
			System.out.println("Regular mode run");
		}
		else{
			System.out.println("Please input IoTgateway running mode(t:test, r:regular connection)");
		}
		//BlockChainClient bclient = new BlockChainClient();
		//bclient.inputTestData();
		
	}

}

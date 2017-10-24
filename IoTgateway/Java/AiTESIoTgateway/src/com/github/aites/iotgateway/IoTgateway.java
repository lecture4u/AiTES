package com.github.aites.iotgateway;

public class IoTgateway {

	public static void main(String[] args) {
		
		
		if(args[0].equals("-t")){
			System.out.println("Test mode run");
		}
		else if(args[0].equals("-r")){
			System.out.println("Regular mode run");
		}
		PropReader prop = new PropReader();
		
		// TODO Auto-generated method stub
	
		prop.propReader();
		
		String clientID = prop.getCilentID();
		String brokerIP = prop.getBrokerIP();
		String moduleURL = prop.getModuleURL();
		
		System.out.println(clientID);
		System.out.println(brokerIP);
		System.out.println(moduleURL);
		
	}

}

package com.github.aites.mqttclient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.paho.client.mqttv3.*;

import com.github.aites.device.Device;
import com.github.aites.iotgateway.DataProcessor;
import com.opencsv.CSVReader;
public class MQTTClient implements MqttCallback{
	private String clientID;
	private static String brokerIP;	
	private static String moduleURL;
	
	private String subDevice;
	private String subFeedBack;
	
	private String pubDevice;
	private String pubLocal;
	
	private MqttClient myClient;
	private MqttConnectOptions connOpt;
	private final static int subQoS = 0;
	private final static int pubQoS = 0;
	
	private String affiliateLocalName;
	private String affiliateGlobalName;
	private String affiliateName;
	
	private ArrayList<Device> deviceList;
	public MQTTClient(String clientID, String brokerIP, String moduleURL){
		this.clientID = clientID;
		this.brokerIP = brokerIP;
		this.moduleURL = moduleURL;
		deviceList = new ArrayList<Device>();
		idenfyAffiliate();
	}
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		String mqttMessage = new String(message.getPayload());
		System.out.println("---------------------Arrived Mqtt Message-----------------");
		System.out.println("Topic:"+topic);
		System.out.println("Message:"+mqttMessage);
		System.out.println("----------------------------------------------------------");
		
		String[] deviceSplit = new String[4];
		String deviceName;
		if(topic.equals("$SYS/broker/log/N")){
			if(mqttMessage.matches(".*connected.*")){
				int deviceNameStart = mqttMessage.indexOf("as");
				String deviceMiddleName = mqttMessage.substring(deviceNameStart);
				
				int deviceNameEnd = deviceMiddleName.indexOf("(");
				deviceName = deviceMiddleName.substring(3,deviceNameEnd-1);
			
			    
			    deviceSplit =  deviceName.split("/");
			    
			    if(deviceSplit[1].equals(affiliateLocalName)){
			    	deviceList.add(new Device(deviceSplit[2]));
			    	for(int di=0; di<deviceList.size(); di++){
			    		deviceList.get(di).printDevice();
			    	}
			    }
			}
			else if(mqttMessage.matches(".*disconnecting.*")){
				int deviceNameStart = mqttMessage.indexOf("nt");
				String deviceMiddleName = mqttMessage.substring(deviceNameStart);
		
				int deviceNameEnd = deviceMiddleName.indexOf(",");
				deviceName = deviceMiddleName.substring(3,deviceNameEnd);
				
			    deviceSplit =  deviceName.split("/");
			    
			    if(deviceSplit[1].equals(affiliateLocalName)){
			    	
			    	for(Iterator<Device> it = deviceList.iterator(); it.hasNext();){
			    		Device device = it.next();
			    		if(device.getDeviceName().equals(deviceSplit[2])){
			    			it.remove();
			    		}
			    	}
			    	for(int di=0; di<deviceList.size(); di++){
			    		deviceList.get(di).printDevice();
			    	}
			    }
			}
		}
		
	}
	
	
	public void runClient(){
		subDevice = "Gateway/"+affiliateLocalName+"/"+affiliateName+"/#";
		System.out.println("affiliate:"+subDevice);
		String logTopic= "$SYS/broker/log/N";
		connOpt = new MqttConnectOptions();
		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		
		
		try{
			myClient = new MqttClient(brokerIP,clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		}catch(MqttException e){
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("----------Connected MQTT Broker Server:"+brokerIP+"----------");
		System.out.println("to clientID:"+clientID);
		System.out.println("to DynamicModulePath:"+moduleURL);
		System.out.println("---------------------------------------------------------------------");
		pubLocal = "Local/"+affiliateGlobalName+"/"+affiliateLocalName+"/envData";
		try{
			myClient.subscribe(subDevice, subQoS);
			myClient.subscribe(logTopic, subQoS);
		}catch(MqttException e){
			e.printStackTrace();
			System.exit(-1);
		}
	
		
	}
	
	public void publishTestData(){
		System.out.println("publish excel data");
		try {
			
			 CSVReader reader = new CSVReader(new FileReader("./smartHome_data.csv"));
			 String[] testData;
			 String preData="";
			 String postData;
			 boolean headerC = false;
			 DataProcessor dp = new DataProcessor();
			 
			 MqttTopic pubLocal_Topic = myClient.getTopic(pubLocal);
			 while ((testData = reader.readNext()) != null) {
	            	for(int i=0; i<testData.length; i++){
	            		if(i == testData.length-1){
	            			preData = preData+testData[i];
	            		}
	            		else{
	            			preData = preData+testData[i]+",";
	            		}
	            			
	            	}
	            	if(!headerC){
	            		headerC = true;
	            		dp.setHeader(preData);
	            	}
	            	else{
	            		postData = dp.getProcessedTestData(preData);
	            		
	            		MqttMessage message = setMqttMessage(postData);
	            		message.setQos(pubQoS);
		    			message.setRetained(false);
		    			MqttDeliveryToken token = null;
		    			
		    			System.out.println("---------Send Publishing Message--------");
		    			System.out.println("Topic:" +pubLocal);
		    			System.out.println("Data:"+postData);
		    			System.out.println("----------------------------------------");
		    			token = pubLocal_Topic.publish(message);
	            	}
	            	preData ="";
	            	
	            	
	    			
	    			
	    		
	    		
	    		    
	    		    Thread.sleep(3000);
	    				
	            }
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	private void idenfyAffiliate(){
		String affiliate[] = clientID.split("/");
		this.affiliateGlobalName = affiliate[0];
		this.affiliateLocalName = affiliate[1];
		this.affiliateName = affiliate[2];
		System.out.println("------------------------affiliate information------------------------");
		System.out.println(affiliateGlobalName);
		System.out.println(affiliateLocalName); 
		System.out.println(affiliateName);
		System.out.println("---------------------------------------------------------------------");
	}
	private MqttMessage setMqttMessage(String message){
		MqttMessage mqMessage = new MqttMessage(message.getBytes());
		mqMessage.setQos(pubQoS);
		mqMessage.setRetained(false);
		
		return mqMessage;
	}
}

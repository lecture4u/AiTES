package com.github.aites.mqttclient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Method;






import org.eclipse.paho.client.mqttv3.*;

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
	public MQTTClient(String clientID, String brokerIP, String moduleURL){
		this.clientID = clientID;
		this.brokerIP = brokerIP;
		this.moduleURL = moduleURL;
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
		if(topic.equals("$SYS/broker/log/N")){
			if(mqttMessage.matches(".*connected.*")){
				
			}
			else if(mqttMessage.matches(".*disconnecting.*")){
				
			}
		}
		
	}
	
	public void runClient(){
		subDevice = "Gateway/Local1/Gateway1/#";
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
	            		
	            		MqttMessage message = new MqttMessage(postData.getBytes());
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
		this.affiliateGlobalName = affiliate[2];
		System.out.println("------------------------affiliate information------------------------");
		System.out.println(affiliate[0]);
		System.out.println(affiliate[1]); 
		System.out.println(affiliate[2]);
		System.out.println("---------------------------------------------------------------------");
	}
}

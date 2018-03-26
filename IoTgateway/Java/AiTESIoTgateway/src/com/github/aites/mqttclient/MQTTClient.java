package com.github.aites.mqttclient;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.paho.client.mqttv3.*;

import com.github.aites.device.Device;
import com.github.aites.iotgateway.DeviceDataProcessor;
import com.github.aites.iotgateway.EnvDataProcessor;
import com.opencsv.CSVReader;
public class MQTTClient implements MqttCallback{
	private String clientID;
	private static String brokerIP;	
	private static String moduleURL;
	
	private String subDevice;
	private String subFeedBack;
	
	private String pubDevice;
	private String pubLocal;
	private String pubConnection;
	
	private MqttClient myClient;
	private MqttConnectOptions connOpt;
	private final static int subQoS = 0;
	private final static int pubQoS = 0;
	
	private String affiliateLocalName;
	private String affiliateGlobalName;
	private String affiliateName;
	private static int deviceCounter = 0;
	private ArrayList<Device> deviceList;
	
	private ArrayList<String> effectorlog;	
	
	public MQTTClient(String clientID, String brokerIP, String moduleURL){
		this.clientID = clientID;
		this.brokerIP = brokerIP;
		this.moduleURL = moduleURL;
		deviceList = new ArrayList<Device>();
		idenfyAffiliate();
		initpartnerDevice();
		
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
		
		String[] deviceSplit = new String[5];
		String deviceName;
	
		
		if(topic.equals("$SYS/broker/log/N")){
			if(mqttMessage.matches(".*connected.*")){
				int deviceNameStart = mqttMessage.indexOf("as");
				String deviceMiddleName = mqttMessage.substring(deviceNameStart);
				
				int deviceNameEnd = deviceMiddleName.indexOf("(");
				deviceName = deviceMiddleName.substring(3,deviceNameEnd-1);
			
			    
			    deviceSplit =  deviceName.split("/");
			    
			    if(deviceSplit[1].equals(affiliateLocalName)){
			    	//deviceList.add(new Device(deviceSplit[2]));
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
			else{
				
				System.out.println("get message");
				deviceSplit = mqttMessage.split(",");
				
				
				deviceCounter +=1;
			}
			
			if(deviceCounter == deviceList.size()){
				deviceCounter = 0;
				
				
				
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
		System.out.println("run");
		
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
		setPubTopicFromAffiliate();
		
		try{
			myClient.subscribe(subDevice, subQoS);
			myClient.subscribe(logTopic, subQoS);
		}catch(MqttException e){
			e.printStackTrace();
			System.exit(-1);
		}
	
		
	}
	public void publishDeviceData(String iotEnvData){
		System.out.println("publish iot environment data");
		
	}
	public void publishInitDeviceInfo(){
		System.out.println("publish init IoT env participant device Information");
		 
		MqttTopic pubConnection_Topic = myClient.getTopic(pubConnection);
		try{
			for(Device d : deviceList){
				DeviceDataProcessor dp = new DeviceDataProcessor();
				dp.setHeader(d.getDeviceName());
				dp.processData(d);
				String processedDevice = dp.getProcessedData();
	
				
        		MqttMessage message = setMqttMessage(processedDevice);
        		message.setQos(pubQoS);
    			message.setRetained(false);
    			MqttDeliveryToken token = null;
    			
    			System.out.println("---------Send Publishing Message--------");
    			System.out.println("Topic:" +pubConnection);
    			System.out.println("Data:"+processedDevice);
    			//System.out.println(effectorlog.get(logcount));
    			System.out.println("----------------------------------------");
    			token = pubConnection_Topic.publish(message);
				 
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void publishTestData(){
		System.out.println("publish excel data");
		int logcount=0;
		try {
			 String csvFileName = "exresult2.csv";
			 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFileName),"MS949"));
			 CSVReader reader = new CSVReader(new FileReader("./smartHome_data_spec.csv"));
			 String[] testData;
			 String preData="";
			 String postData;
			 long start = System.currentTimeMillis();
			 boolean headerC = false;
			 EnvDataProcessor dp = new EnvDataProcessor();
			 
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
	            	System.out.println(preData);
	            	if(!headerC){
	            		headerC = true;
	            		dp.setHeader(preData);
	            	}
	            	else{
	            		dp.processData(preData);
	            		postData = dp.getProcessedData();
	            		
	            		MqttMessage message = setMqttMessage(postData);
	            		message.setQos(pubQoS);
		    			message.setRetained(false);
		    			MqttDeliveryToken token = null;
		    			
		    			System.out.println("---------Send Publishing Message--------");
		    			System.out.println("Topic:" +pubLocal);
		    			System.out.println("Data:"+postData);
		    			//System.out.println(effectorlog.get(logcount));
		    			System.out.println("----------------------------------------");
		    			token = pubLocal_Topic.publish(message);
	            	}
	               long end = System.currentTimeMillis();
	  	           double time = (end - start)/(double)1000;
	  	           System.out.println(time);
	  	           writer.write(time+"\r\n");
	            	preData ="";
	            	Thread.sleep(3000);
	            			
	    		
	    			    
	    			logcount +=1;	
	            }
			   writer.close(); 
		}catch(ArrayIndexOutOfBoundsException are){
			System.out.println("Data publish end");
		}
		catch (Exception e) {
			
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
	private void initpartnerDevice(){ //1.Device Name,Status, Address, ModelCode
		deviceList.add(new Device("Air_Conditioner","ready","192.168.0.14","FGRC084451"));
		deviceList.add(new Device("TV","ready","192.168.0.23","UN75J6350AFXK"));
		deviceList.add(new Device("Lamp","ready","192.168.0.11","XMCTD01YL"));
		deviceList.add(new Device("Robotic_Vaccum_Cleaner","ready","192.168.0.46","VR6480VMNC"));
		deviceList.add(new Device("FlowerPot","ready","192.168.0.23","GA3A00417A14"));
		deviceList.add(new Device("Washing_Machine","ready","192.168.0.11","F17WPSW"));
		deviceList.add(new Device("Lamp2","ready","192.168.0.36","BE1915VWSP8"));
		deviceList.add(new Device("Smart_Cook_Pot","ready","192.168.0.45","SCCPWM600-V2"));
		deviceList.add(new Device("CCTV","ready","192.168.0.22","722823017"));
		deviceList.add(new Device("Mixer","ready","192.168.0.45","B06XRKC719"));
		deviceList.add(new Device("AI_Speaker","ready","192.168.0.61","GA3A00417A14"));
		deviceList.add(new Device("Oven","ready","192.168.0.55","JC00A121601"));
	}
	private void setPubTopicFromAffiliate(){
		pubLocal = "Local/"+affiliateGlobalName+"/"+affiliateLocalName+"/"+affiliateName+"/envData";
		pubConnection = "Local/"+affiliateGlobalName+"/"+affiliateLocalName+"/"+affiliateName+"/connection";
	}
}

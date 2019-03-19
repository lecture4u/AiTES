package com.github.aites.mqttclient;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.paho.client.mqttv3.*;

import com.github.aites.device.Device;
import com.github.aites.device.Timer;
import com.github.aites.iotgateway.DeviceDataProcessor;
import com.github.aites.iotgateway.EnvDataProcessor;
import com.github.aites.iotgateway.Scheduler;
import com.opencsv.CSVReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class MQTTClient implements MqttCallback{
	private String clientID;
	private static String brokerIP;	
	private static String moduleURL;
	private String csvURL;
	
	
	private String subDevice;
	private String subFeedBack;
	
	private String pubDevice;
	private String pubLocal;
	private String pubConnection;
	private String pubDisConnection;
	
	private MqttClient myClient;
	private MqttConnectOptions connOpt;
	private final int subQoS = 0;
	private final int pubQoS = 0;
	
	private String affiliateLocalName;
	private String affiliateGlobalName;
	private String affiliateName;
	private static int deviceCounter = 0;
	private ArrayList<Device> deviceList;
	private HashMap<String,Device> deviceHashList = new HashMap();
	private ArrayList<String> effectorlog;	
	private ArrayList<Scheduler> schedulerList = new ArrayList<Scheduler>();
	
	//private String currentSystemTime = "2017.7.17 0:00";
	Timer timer = Timer.getInstance();
	private boolean processedFlag = true;
	public MQTTClient(String clientID, String brokerIP, String moduleURL){
		this.clientID = clientID;
		this.brokerIP = brokerIP;
		this.moduleURL = moduleURL;
		deviceList = new ArrayList<Device>();
		idenfyAffiliate();
		//initpartnerDevice();
		
	}
	public MQTTClient(String clientID, String brokerIP, String moduleURL, String csvURL){
		this.clientID = clientID;
		this.brokerIP = brokerIP;
		this.moduleURL = moduleURL;
		this.csvURL = csvURL;
		deviceList = new ArrayList<Device>();
		idenfyAffiliate();
		timer.setSystemTime("2017.7.17 0:00");
		//initpartnerDevice();
		
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
		if(processedFlag) {
			timer.processedTime();
			processedFlag = false;
		}
		boolean colserFlag = false;
		String mqttMessage = new String(message.getPayload());

		System.out.println("---------------------Arrived Mqtt Message-----------------");
		System.out.println("Topic:"+topic);
		System.out.println("Message:"+mqttMessage);
		System.out.println("----------------------------------------------------------");
		String[] topicSplicer = topic.split("/");
		if(topicSplicer[4].equals("Closer")) {
			colserFlag = true;
		}
		if(timer.getWholeTime().equals("2017.7.17 11:00")) {
			System.exit(1);
		}
		Device currentDivce = new Device();
	    for(int i=0; i<deviceList.size(); i++) {
	    	if(topicSplicer[4].equals(deviceList.get(i).getDeviceName())) {
	    		currentDivce = deviceList.get(i);
	    		currentDivce.setActionLevel(topicSplicer[5]);
	    		currentDivce.printDeviceInfo();
	    	}
	    }
	    
	   for(int i=0; i<schedulerList.size(); i++) {
	    	if(topicSplicer[4].equals(schedulerList.get(i).getSensorName()) && schedulerList.get(i).getArrivalTime().equals(timer.getWholeTime())) {
	    		System.out.println("Modify Scheduler Sensor:"+schedulerList.get(i).getSensorName() + " and time: "+schedulerList.get(i).getArrivalTime());
	    		schedulerList.get(i).setCurrentModule(topicSplicer[5]);
	    		if(topicSplicer[5].equals("activity")) {
	    			schedulerList.get(i).setSensorData(currentDivce.getActivity());
				}
				else if(topicSplicer[5].equals("ready")) {
					schedulerList.get(i).setSensorData(currentDivce.getReady());
				}
				else if(topicSplicer[5].equals("stanby")) {
					schedulerList.get(i).setSensorData(currentDivce.getStanby());
				}
				else if(topicSplicer[5].equals("powersaving")) {
					schedulerList.get(i).setSensorData(currentDivce.getPowersaving());
				}
	    		
	    	}
	    }
	   if(colserFlag) {
		  
		   makeSchedulerToJSON();
		   processedFlag = true;
	    }
	}

	public void runClient(){
		subDevice = "IoTgateway/"+affiliateGlobalName+"/"+affiliateLocalName+"/"+affiliateName+"/#";
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
		setPubTopicFromAffiliate();
		System.out.println("----------Connected MQTT Broker Server:"+brokerIP+"----------");
		System.out.println("to clientID:"+clientID);
		System.out.println("to DynamicModulePath:"+moduleURL);
		System.out.println("Subscribe Local AiTES Topic:"+subDevice);
		System.out.println("Publish Local AiTES Topics:"+pubLocal);
		System.out.println("Publish Local AiTES Topics:"+pubConnection);
		System.out.println("Publish Local AiTES Topics:"+pubDisConnection);
		System.out.println("---------------------------------------------------------------------");
	
		
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
		
		 try {
			CSVReader reader = new CSVReader(new FileReader("./smartHome_AiTES_TestDeviceInfo.csv"));
			 boolean headerC = false;
			 String[] testData;
			 String preData="";
			// String collectDate = "2017.7.17 0:00";
			 //String postData;
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
					 System.out.println("This is DataSet Header");					 
					 System.out.println(preData);
					 headerC = true;
				 }
				 else{
					System.out.println("This is DataSet Body");
					System.out.println(preData);
					deviceList.add(new Device(testData[0],testData[1],testData[2],testData[3],testData[4],testData[5],testData[6],testData[7]));
				 }
				 preData = "";
			 }
			 for(int i=0; i<deviceList.size(); i++) {
				 Device device = deviceList.get(i);
				 device.printDeviceInfo();
			 }
					 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 /*
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
		}*/
	}
	public void publishTestData(){
		System.out.println("------------publish Smart Home Test Data-------------");
		int logcount=0;
		try {
			// String csvFileName = "exresult2.csv";
			 //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFileName),"MS949")); Code for write Experiment result.

			 CSVReader reader = new CSVReader(new FileReader("./smatHome_AiTES_TestData.csv"));
			 String[] testData;
			 String preData="";
			 String collectDate = "2017.7.17 0:00";
			 String postData;
			// long start = System.currentTimeMillis();
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
				 if(!headerC){
					 System.out.println("This is DataSet Header");					 
					 System.out.println(preData);
					 dp.setHeader(preData);
					 headerC = true;
				 }
				 else{
					 if(!collectDate.equals(testData[0])){
						 System.out.println("This step end:"+collectDate+" publish Data");
						 collectDate = testData[0];
						 postData = dp.getProcessedData();
						

		            	 MqttMessage message = setMqttMessage(postData);
		            	 message.setQos(pubQoS);
			    		 message.setRetained(false);
			    		 MqttDeliveryToken token = null;
			    			
			    		 System.out.println("---------Send Publishing Message--------");
			    		 System.out.println("Topic:" +pubLocal);
			    		 System.out.println("Data:"+postData);		    			
			    		 System.out.println("----------------------------------------");
			    		 token = pubLocal_Topic.publish(message);			    		 			    		
			    		 Thread.sleep(3000);
					 }
					 Device currentDevice = deviceHashList.get(testData[1]);
					 System.out.println(preData+" CollectDate:"+collectDate);
				
					 currentDevice.setActionLevel(testData[3]);
					 
					 dp.processData(preData);
					 
				 }
				 
				 System.out.println("");
				 preData = "";
			 }
			 publishDisconnectDevice();			 			 			  
		}catch(ArrayIndexOutOfBoundsException are){
			System.out.println("Data publish end");
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	public void makeScheduleFromData() {
		 try { 
		   CSVReader reader = new CSVReader(new FileReader("./smartHome_AiTES_TestSchedule.csv"));
		   String[] testData;
		   String preData="";
		   String collectDate = "2017.7.17 0:00";
		   String postData;
		   int deviceIndexer=0;
		   // long start = System.currentTimeMillis();
		   boolean headerC = false;
		
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
					 //System.out.println("This is DataSet Header");					 
					// System.out.println(preData);
					 headerC = true;
				 }
				 else{
					//System.out.println("This is DataSet Body");
					//System.out.println(preData);
					String powerConsumtion = "";
					if(testData[1].equals("activity")) {
						powerConsumtion = deviceList.get(deviceIndexer).getActivity();
						deviceList.get(deviceIndexer).setActionLevel("activity");
					}
					else if(testData[1].equals("ready")) {
						powerConsumtion = deviceList.get(deviceIndexer).getReady();
						deviceList.get(deviceIndexer).setActionLevel("ready");
					}
					else if(testData[1].equals("stanby")) {
						powerConsumtion = deviceList.get(deviceIndexer).getStanby();
						deviceList.get(deviceIndexer).setActionLevel("stanby");
					}
					else if(testData[1].equals("powersaving")) {
						powerConsumtion = deviceList.get(deviceIndexer).getPowersaving();
						deviceList.get(deviceIndexer).setActionLevel("powersaving");
					}
					else {
						powerConsumtion = testData[1];
				        
					}
					if(deviceIndexer == 13) {
						schedulerList.add(new Scheduler(testData[0], deviceList.get(0).getDeviceName(), powerConsumtion, deviceList.get(0).getActionLevel(), testData[2]));
					}
					else {
						if(deviceList.get(deviceIndexer).getActionLevel() == null) {
							schedulerList.add(new Scheduler(testData[0], deviceList.get(deviceIndexer).getDeviceName(), powerConsumtion, "activity", testData[2]));
						}
						else {
							schedulerList.add(new Scheduler(testData[0], deviceList.get(deviceIndexer).getDeviceName(), powerConsumtion, deviceList.get(deviceIndexer).getActionLevel(), testData[2]));
						}
						
					}
					/*
					for(int i =0; i<schedulerList.size(); i++) {
						Scheduler scheduler = schedulerList.get(i);
						scheduler.printSchedule();
					}*/
					deviceIndexer++;
					if(deviceIndexer > 13) {
						deviceIndexer = 0;
					}
				 }
				 preData = "";
				
			 }
			 makeSchedulerToJSON();
			 
			 /*Test code
			 File file = new File("./abcd.txt");

			 byte[] testMessageByte = getBytesFromFile(file);
		
		
		  	 MqttMessage message = new MqttMessage(testMessageByte);
		  	 messageArrived("IoTgateway/Global1/Local1/Gateway1/RVC1/activity", message);
		  	 messageArrived("IoTgateway/Global1/Local1/Gateway1/WM1/ready", message);
		  	 messageArrived("IoTgateway/Global1/Local1/Gateway1/Closer", message);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void publishDisconnectDevice(){

		System.out.println("publish disconnecting IoT env device");
		 
		MqttTopic pubDisConnection_Topic = myClient.getTopic(pubDisConnection);
		try{
			for(Device d : deviceList){
			
	
				
        		MqttMessage message = setMqttMessage(d.getDeviceName());
        		message.setQos(pubQoS);
    			message.setRetained(false);
    			MqttDeliveryToken token = null;
    			
    			System.out.println("---------Send Publishing Message--------");
    			System.out.println("Topic:" +pubDisConnection);
    			System.out.println("Data:"+d.getDeviceName());

    			System.out.println("----------------------------------------");
    			token = pubDisConnection_Topic.publish(message);
				 
			}
			deviceList.clear();
			
		}catch(Exception e){
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
	private void setPubTopicFromAffiliate(){
		pubLocal = "Local/"+affiliateGlobalName+"/"+affiliateLocalName+"/"+affiliateName+"/envData";
		pubConnection = "Local/"+affiliateGlobalName+"/"+affiliateLocalName+"/"+affiliateName+"/connection";
		pubDisConnection = "Local/"+affiliateGlobalName+"/"+affiliateLocalName+"/"+affiliateName+"/disconnection";
	}
	private void makeSchedulerToJSON() {
		JsonObject jsonObject = new JsonObject();
		
		JsonArray jsonArray = new JsonArray();
		 MqttTopic pubLocal_Topic = myClient.getTopic(pubLocal); 
		for(int i=0; i<schedulerList.size(); i++) {
			Scheduler scheduler = schedulerList.get(i);
			if(scheduler.getArrivalTime().equals(timer.getWholeTime())) {
				JsonObject dataObject = new JsonObject();
				
				JsonPrimitive sensorNamePrimitive = new JsonPrimitive(scheduler.getSensorName());
				JsonPrimitive sensorDataPrimitive = new JsonPrimitive(scheduler.getSensorData());
				JsonPrimitive currentModulePrimitive = new JsonPrimitive(scheduler.getCurrentModule());
				JsonPrimitive dataTypePrimitive = new JsonPrimitive(scheduler.getDataType());
				
				dataObject.add("sensorName", sensorNamePrimitive);
				dataObject.add("sensorData", sensorDataPrimitive);
				dataObject.add("currentModule", currentModulePrimitive);
				dataObject.add("dataType", dataTypePrimitive);
				
				jsonArray.add(dataObject);
			}
		}
		jsonObject.add(timer.getWholeTime(), jsonArray);
		
		System.out.println(jsonObject);
		String jsonObjectString = jsonObject.toString();
		 MqttMessage message = setMqttMessage(jsonObjectString);
    	 message.setQos(pubQoS);
		 message.setRetained(false);
		 MqttDeliveryToken token = null;
			
		 System.out.println("---------Send Publishing Message--------");
		 System.out.println("Topic:" +pubLocal);
		 System.out.println("Data:"+jsonObject.toString());		    			
		 System.out.println("----------------------------------------");
		 try {
			token = pubLocal_Topic.publish(message);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	private byte[] getBytesFromFile(File file) throws IOException{
		InputStream is = new FileInputStream(file);
		long length = file.length();
		
		if(length > Integer.MAX_VALUE) {
			
		}
		
		byte[] bytes = new byte[(int)length];
		
		int offset =0;
		int numRead = 0;
		while(offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset))>=0) {
			offset += numRead;
		}
		
		if(offset < bytes.length) {
			throw new IOException("Could not completely read file "+file.getName());
		}
		is.close();
		return bytes;
	}
}


/*
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
	}*/
	

package com.github.aites.framework.communicate;
import org.eclipse.paho.client.mqttv3.*;



import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
public class DataTransfer implements MqttCallback{
	MqttClient myClient;
	MqttClient myClientPub;
	MqttConnectOptions connOpt;
	
	private final int pubQos=0;
	private final int subQos=0;
	private static String clientID;
	private static String MQTT_BROKER_URL = "tcp://broker_server_ip:port";
	
	private static final String logTopic= "$SYS/broker/log/N";
	private static MqttTopic pubtopic;
	private static String basicsubTopic;
	private Class mainClass;
   
	private String affiliateLocalName;
	private String affiliateGlobalName;
	
	private String deviceName;
	private String affilientmode;
	
	/**Set Mqtt Broker Server, ClientID, Own Class for using method invoke*/
 	public void setMQTTConnection(String brokerURL, Class mainClass, String clientID){
		MQTT_BROKER_URL = brokerURL;
		this.mainClass = mainClass;
		this.clientID = clientID;
	}
	private static class MQTTSingleton{
		private static final DataTransfer instance = new DataTransfer();
	}
	
	public static DataTransfer getInstance(){

		return MQTTSingleton.instance;
	}
	
	@Override
	public void connectionLost(Throwable t){
		System.out.println("Connection Lost");
	}
	
	public void subscription(String subURL){
	 
		try {
			myClient.subscribe(subURL, subQos);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		System.out.println("Message Publish Complete");
	}
	/** publish message to broker server*/
	public void publish(File sendModule, String pubTopic)throws IOException{ 
	    String fileName = sendModule.getName();
	
		MqttTopic new_Topic = myClient.getTopic(pubTopic);
		
		System.out.println("publish message "+fileName + " to Topic: "+pubTopic);
		byte[] fileByte = getBytesFromFile(sendModule);	
		int pubQoS = 0; 
		
		MqttMessage message = new MqttMessage(fileByte);
		
		
		message.setQos(pubQoS);
		message.setRetained(false);
		
		MqttDeliveryToken token = null;
	
		try{
			token = new_Topic.publish(message); 
			
		}catch(Exception e){
			e.printStackTrace();
		}
	

	}
	public void publish(String message, String topic){
		MqttTopic new_Topic = myClient.getTopic(topic);
		byte[] fileByte = message.getBytes();
				
		int pubQoS = 0; 
		
		MqttMessage mqttMessage = new MqttMessage(fileByte);
		
		mqttMessage.setQos(pubQoS);//QoS ����
		mqttMessage.setRetained(false);
		
		MqttDeliveryToken token = null;
		
		try{
			token = new_Topic.publish(mqttMessage); // ��ū���� �޼����� ���Ŀ ������ �����ϰ� �˴ϴ�.
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		//Loccal, Global mode Setting
	    String mqttMessage = message.toString();
	    Class[] methodParamClass = new Class[] {String.class, String.class, String.class};	 	 
		
		Method method = mainClass.getMethod("manageAiTES",methodParamClass);     
		Object clsInstance = mainClass.newInstance();
		MqttDeliveryToken token = null;
		MqttTopic pubTopic;
		
		String[] clientIDSplit = clientID.split("/");
		String[] deviceSplit = new String[4];
		
		try{
			String[] topicSplit = topic.split("/");
	
			deviceName =topicSplit[3];
			method.invoke(clsInstance, mqttMessage, topic, deviceName);
			}catch(Exception e){
					e.printStackTrace();
			}
	}
	
	private MqttMessage setMqttMessage(String message){
		MqttMessage mqMessage = new MqttMessage(message.getBytes());
		mqMessage.setQos(pubQos);
		mqMessage.setRetained(false);
		
		return mqMessage;
	}
	/**Running MQTT Client*/
	public void runClient(){
	
		
		connOpt = new MqttConnectOptions();
		
		connOpt.setCleanSession(true);  
		connOpt.setKeepAliveInterval(30); 
		
		
		try{
			myClient = new MqttClient(MQTT_BROKER_URL,clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
			
		
		    System.out.println("Connected to "+ MQTT_BROKER_URL+" with "+clientID);
	    
	        idenfyAffiliate();
	        if(affilientmode.equals("Local")){
		         basicsubTopic = "Local/"+affiliateGlobalName+"/"+affiliateLocalName+"/#";
	         }
	         else if(affilientmode.equalsIgnoreCase("Global")){
		         basicsubTopic = "Global/"+affiliateGlobalName+"/#";
	         }
	         System.out.println(basicsubTopic);
	         myClient.subscribe(basicsubTopic);
		}catch(MqttException e){
			 e.printStackTrace();
			 System.exit(-1);
	    }
	}
	public void setAfficationMode(String affilientmode){
		this.affilientmode = affilientmode;
	}
	private void idenfyAffiliate(){
		String affiliate[] = clientID.split("/");
		if(affilientmode.equals("Local")){
			this.affiliateGlobalName = affiliate[0];
			this.affiliateLocalName = affiliate[1];
			System.out.println("------------------------affiliate information------------------------");
			System.out.println(affiliateGlobalName);
			System.out.println(affiliateLocalName); 
		}
		else if(affilientmode.equals("Global")){
			this.affiliateGlobalName = affiliate[0];
			System.out.println("------------------------affiliate information------------------------");
			System.out.println(affiliateGlobalName);
		}
		else{
			System.out.println("please choose affilientmode first!");
			System.exit(0);
		}
		System.out.println("---------------------------------------------------------------------");
	}
	public static byte[] getBytesFromFile(File file) throws IOException {
	     InputStream is = new FileInputStream(file);
	     long length = file.length();
	     // You cannot create an array using a long type.
	     // It needs to be an int type.
	     // Before converting to an int type, check
	     // to ensure that file is not larger than Integer.MAX_VALUE.
	     if (length > Integer.MAX_VALUE) {
	         // File is too large
	     }
	     // Create the byte array to hold the data
	     byte[] bytes = new byte[(int)length];
	     // Read in the bytes
	     int offset = 0;
	     int numRead = 0;
	     while (offset < bytes.length
	            && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	         offset += numRead;
	     }
	     // Ensure all the bytes have been read in
	     if (offset < bytes.length) {
	         throw new IOException("Could not completely read file "+file.getName());
	     }
	     // Close the input stream and return bytes
	     is.close();
	     return bytes;
	 }
}



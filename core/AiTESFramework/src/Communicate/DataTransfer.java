package Communicate;
import org.eclipse.paho.client.mqttv3.*;



import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
public class DataTransfer implements MqttCallback{
	MqttClient myClient;
	MqttClient myClientPub;
	MqttConnectOptions connOpt;
	static String clientID;
	static String MQTT_BROKER_URL = "tcp://broker_server_ip:port";
	private Class mainClass;
	private static String logTopic= "$SYS/broker/log/N";
	
	private static MqttTopic pubtopic;
	private String deviceName;
	
	private static ArrayList deviceList = new ArrayList();
	
	private final int pubQos=0;
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
		int subQoS = 0; 
		try {
			myClient.subscribe(subURL, subQoS);
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
	public void publish(File sendModule, String device)throws IOException{ // 장치로 데이터를 피드백 하는 메소드입니다.
	    String fileName = sendModule.getName();
		String deviceTopic = "Effector/"+device+"/"+fileName;
		MqttTopic new_Topic = myClient.getTopic(deviceTopic);
		
		System.out.println("publish message "+fileName + " to Topic: "+deviceTopic);
		byte[] fileByte = getBytesFromFile(sendModule);	
		int pubQoS = 0; 
		
		MqttMessage message = new MqttMessage(fileByte);
		
		
		message.setQos(pubQoS);//QoS 설정
		message.setRetained(false);
		
		MqttDeliveryToken token = null;
	
		try{
			token = new_Topic.publish(message); // 토큰으로 메세지를 브로커 서버에 전달하게 됩니다.
			
		}catch(Exception e){
			e.printStackTrace();
		}
	

	}
	public void publish(String message, String topic){
		MqttTopic new_Topic = myClient.getTopic(topic);
		byte[] fileByte = message.getBytes();
				
		int pubQoS = 0; 
		
		MqttMessage mqttMessage = new MqttMessage(fileByte);
		
		mqttMessage.setQos(pubQoS);//QoS 설정
		mqttMessage.setRetained(false);
		
		MqttDeliveryToken token = null;
		
		try{
			token = new_Topic.publish(mqttMessage); // 토큰으로 메세지를 브로커 서버에 전달하게 됩니다.
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
				
	    String mqttMessage = message.toString();
	    Class[] methodParamClass = new Class[] {String.class, String.class, String.class};	 	 
		
		Method method = mainClass.getMethod("manageAiTES",methodParamClass);     
		Object clsInstance = mainClass.newInstance();
		MqttDeliveryToken token = null;
		MqttTopic pubTopic;
		
		String[] clientIDSplit = clientID.split("/");
		/*생성자의 매개변수로 넘겨받은 클래스의 메소드를 invoke해서 MQTTMessage와 Topic을 넘겨줍니다.*/
		System.out.println(topic);
		if(topic.equals("$SYS/broker/log/N")){
			if(mqttMessage.matches(".*connected.*")){
				int deviceNameStart = mqttMessage.indexOf("as");
				String deviceMiddleName = mqttMessage.substring(deviceNameStart);
				
				int deviceNameEnd = deviceMiddleName.indexOf("(");
				deviceName = deviceMiddleName.substring(3,deviceNameEnd-1);
				
			    String[] deviceSplit = new String[4];
			    deviceSplit =  deviceName.split("/");
			   
			    if(deviceSplit[2].matches(".*Device.*")){
			    	System.out.println("connected Participant: Device");
			    	topic = "Local/"+deviceSplit[0]+"/"+deviceSplit[1]+"/"+deviceSplit[2]+"/Connection";
			    	mqttMessage = "echoConnected";
			    	MqttMessage mqMessage = setMqttMessage(mqttMessage);
			    	pubTopic = myClient.getTopic(topic);
			    	try{
			    		token = pubTopic.publish(mqMessage); 
			    	}catch(Exception e){
			    		e.printStackTrace();
			    	}
			    	
			 
			    //	method.invoke(clsInstance, mqttMessage, topic, deviceName);
			    }
			    else if(deviceSplit[2].matches(".*Local.*")){
			    	System.out.println("connected Participant: Local");
			    	mqttMessage = "echoConnected";
			    
			    	topic = "Global/"+deviceSplit[0]+"/"+deviceSplit[0]+"/"+deviceSplit[2]+"/Connection";
			    	mqttMessage = "echoConnected";
			    	MqttMessage mqMessage = setMqttMessage(mqttMessage);
			    	pubTopic = myClient.getTopic(topic);
			    	try{
			    		token = pubTopic.publish(mqMessage); 
			    	}catch(Exception e){
			    		e.printStackTrace();
			    	}
			    	//method.invoke(clsInstance, mqttMessage, topic, deviceName);
			    }
			 			   			    
				deviceList.add(deviceName);
				
				printDeviceList();
				
			}			
			else if(mqttMessage.matches(".*disconnecting.*")){
				int deviceNameStart = mqttMessage.indexOf("nt");
				String deviceMiddleName = mqttMessage.substring(deviceNameStart);
		
				int deviceNameEnd = deviceMiddleName.indexOf(",");
				deviceName = deviceMiddleName.substring(3,deviceNameEnd);
				System.out.println("deviceName:"+deviceName);
				String[] deviceSplit = new String[4];
			    deviceSplit =  deviceName.split("/");
			    
			    if(deviceSplit[2].matches(".*Device.*")){
			    	System.out.println("disConnected Participant: Device");
			    	mqttMessage = "DisConnected";
			    	
			    	topic = "Local/"+deviceSplit[0]+"/"+deviceSplit[1]+"/"+deviceSplit[2]+"/Connection";
			    	;
			    	mqttMessage = "echoDisConnected";
			    	MqttMessage mqMessage = setMqttMessage(mqttMessage);
			    	pubTopic = myClient.getTopic(topic);
			    	try{
			    		token = pubTopic.publish(mqMessage); 
			    	}catch(Exception e){
			    		e.printStackTrace();
			    	}
			   
			    	//method.invoke(clsInstance, mqttMessage, topic, deviceName);
			    }
			    else if(deviceSplit[2].matches(".*Local.*")){
			    	System.out.println("disConnected Participant: Local");
			    	mqttMessage = "echoDisConnected";
			    

			    	topic = "Global/"+deviceSplit[0]+"/"+deviceSplit[0]+"/"+deviceSplit[2]+"/Connection";
			    	mqttMessage = "echoConnected";
			    	MqttMessage mqMessage = setMqttMessage(mqttMessage);
			    	pubTopic = myClient.getTopic(topic);
			    	try{
			    		token = pubTopic.publish(mqMessage); 
			    	}catch(Exception e){
			    		e.printStackTrace();
			    	}
			    	//method.invoke(clsInstance, mqttMessage, topic, deviceName);
			    }
			
				deviceList.remove(deviceName);
			
				printDeviceList();
			}		
		}
		
		else if(topic.matches(".*Check.*")){
			System.out.println("Check");
			String mqMessage = new String(message.getPayload());
			String[] messageSplit = mqMessage.split("/");
			deviceName =messageSplit[2];
			System.out.printf("get running Device Name: "+deviceName +" add List");
			deviceList.add(deviceName);
			printDeviceList();
		}
		
		else{
			
			try{
			String[] topicSplit = topic.split("/");
				
			
				
				deviceName =topicSplit[3];
				method.invoke(clsInstance, mqttMessage, topic, deviceName);
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}
	
	private void printDeviceList(){
		System.out.println("Connected Device List: "+deviceList);
	}
	private MqttMessage setMqttMessage(String message){
		MqttMessage mqMessage = new MqttMessage(message.getBytes());
		mqMessage.setQos(pubQos);
		mqMessage.setRetained(false);
		
		return mqMessage;
	}
	/**Get All Connected Device List*/
	public ArrayList getDeviceList(){
		return deviceList;
	}
	
	public String getDevice(String device){
		if(deviceList.contains(device)){
			return device;
		}
		else{
			return "That Device not in a List";
		}
	}
	
	/**Running MQTT Client*/
	public void runClient(){
		/*Broker에 Connection시 옵션을 조절하는 부분입니다.*/
		
		connOpt = new MqttConnectOptions();
		
		connOpt.setCleanSession(true);  
		connOpt.setKeepAliveInterval(30); 
		
		/*서버에 접속할 이 클라이언트의 정보를 셋팅하는 부분입니다.*/
		try{
			myClient = new MqttClient(MQTT_BROKER_URL,clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
			
		}catch(MqttException e){
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Connected to "+ MQTT_BROKER_URL+" with "+clientID);
	   String topic = "";
	  
	   String message = "Check";
	   String[] clientIDSplit = clientID.split("/");
	    if(clientIDSplit[2].matches(".*Local.*")){
	    	topic = "Device/"+clientIDSplit[2]+"/Check";
	    }
	    else if(clientIDSplit[2].matches(".*Global.*")){
	    	topic = "Local/"+clientIDSplit[2]+"/Check";
	    }
	    System.out.println("CheckTopic1 :"+topic);
		MqttMessage mqMessage = setMqttMessage(message);	
	
	
	
		try{
			int subQoS=0;		
			MqttTopic pubTopic = myClient.getTopic(topic);
		 	MqttDeliveryToken token = pubTopic.publish(mqMessage);
			
			myClient.subscribe(logTopic, subQoS);		
	
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
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



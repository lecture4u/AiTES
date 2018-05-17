package com.github.aites.framework.framework;
import java.lang.reflect.Method;
import java.util.ArrayList;
/**
 * Class run main local AiTES and managed feedback loops
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public abstract class LocalAiTESManager {
	public static String brokerURL;
	public static Class mainClass;
	public Method module;
	public Object clsInstance;
	public String ruleName;
    public static String clientID;
    public String gkURL;
    public ArrayList<String> adaptiveActionList;
    public static String ruleSetURL;
    public static String dymoduleFolder;
    
   
    /**
	 * Method for Managing AITES Module, String MQTTMessage, String Topic, String deviceName
	 * Called from the DataTransfer class. 
	 * @param mqttMessage, topic, deviceName
	 * @return none
	 * @exception ClassNotFoundException
	 *     failed class load from DataTransfer.
	 */
	public abstract void manageAiTES(String mqttMessage, String topic, String deviceName) throws Exception;
	
}

package Framework;
import java.lang.reflect.Method;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import AiTESConnector.*;
import LocalPropertyConnect.*;

public abstract class LocalAiTESManager {
	public static String brokerURL;
	public static Class mainClass;
	public Method module;
	public Object clsInstance;
	public String ruleName;
    public static String clientID;
    public static Class invokeCls;
    /**Managing AITES Module, String MQTTMessage, String Topic, String deviceName*/
	public abstract void manageAiTES(String mqttMessage, String topic, String deviceName) throws Exception;
	
	
	/**set Analyzer, Planer's Rule Module Method name and class instance */
	public void setModule(String ModuleName){
		try{
			module = mainClass.getMethod(ModuleName);
			clsInstance = mainClass.newInstance();
		}catch(Exception e){
			System.out.println("Module Setting Failed");
		
		}
	};
	/**Change Rule to RuleName*/
	public abstract void changeRule(String ruleName);
}

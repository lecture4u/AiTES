package com.github.aites.framework.executor;

import java.io.File;
import java.io.IOException;

import com.github.aites.framework.communicate.DataTransfer;
import com.github.aites.framework.log.LogWritter;
/**
 * Class for effect taget environment
 * find module file, generate publish topic, effect.s
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public class Effector {
	DataTransfer df = DataTransfer.getInstance();
	String pubTopic = "IoTgateway/Local1/Gateway1/Air_conditioner/turnOn";
	private String moduleFolder;
	private String fileName;
	private String afillPubtopic;
	LogWritter log = LogWritter.getInstance();
	public Effector(String moduleFolder, String pubTopic){
		//ModuleForder명
		this.moduleFolder = moduleFolder;
		afillPubtopic = pubTopic;
	}
	 /**
		 * Method for effect moduleFile
		 * findModuleAboutAction, makeTopic;
		 * publish to MQTT broker server pubTopic.
		 * @param target, action
		 * @return File
		 */
    public void effectIoTgateway(String target, String action){
		log.logInput("****Effect Dynamic Module to IoT gateway");
		log.logInput("target:"+target);
		log.logInput("action:"+action);
		
		File module = findModuleAboutAction(action);
		String pubTopic = makeTopic(target, action);
		
		try {
			df.publish(module, this.pubTopic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    public void effectIoTgatewayCloser() {
    	log.logInput("****Effect Closer Message to IoT gateway");
    	String pubTopic = makeCloser();
       df.publish("closerMessage", pubTopic);
    }
	
    /**
	 * Method for find jar file
	 * using action name
	 * jar file name is FileURL/action.jar
	 * @param action
	 * @return File
	 */
	private File findModuleAboutAction(String action){
		log.logInput("******Find Jar File About action:"+action+"*****");
		String moduleURL =moduleFolder+"/"+action+".jar";
		File module = new File(moduleURL);
		fileName = module.getName();
		return module;
	}
	 /**
		 * Method for combine pubtopic IoTgateway		
		 * Later, it needs to be developed for global use.
		 * @param target, action
		 * @return String
		 */
	private String makeTopic(String target, String action){
		pubTopic = afillPubtopic+"/"+target+"/"+action;
		
		return pubTopic;
	}
	private String makeCloser() {
		pubTopic =afillPubtopic+"/Closer";
		return pubTopic;
	}
	public String getTopic(){
		return pubTopic;
	}
	public String getFileName(){
		return fileName;
	}
}

package com.github.aites.framework.executor;

import com.github.aites.framework.communicate.DataTransfer;
import com.github.aites.framework.log.LogWritter;

public class Effector {
	DataTransfer df = DataTransfer.getInstance();
	String pubTopic = "IoTgateway/Local1/Gateway1/Air_conditioner/turnOn";
	LogWritter log = LogWritter.getInstance();
	public Effector(){
		//ModuleForder명
	}
	public String findModuleAboutAction(String action){
		log.logInput("******Find Jar File About action:"+action+"*****");
		String moduleName = action+".jar";
		return moduleName;
	}
	public void effectIoTgateway(){
		
		
	}
	public String getTopic(){
		return pubTopic;
	}
}

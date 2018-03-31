package com.github.aitest.shlocalaites.executor;

import com.github.aites.shlocalaites.log.LogWritter;

import Communicate.DataTransfer;

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

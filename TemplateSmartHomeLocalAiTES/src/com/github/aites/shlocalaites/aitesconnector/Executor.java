package com.github.aites.shlocalaites.aitesconnector;

import com.github.aites.framework.aitesconnector.Factory;
import com.github.aites.framework.aitesmanager.Manager;
import com.github.aites.shlocalaites.aitesmanager.ExecutorManager;

public class Executor implements Factory{
    private String clientID;
    private String iotGatewayID;
    private String dyModuleFolder;
    public Executor(String clientID,String iotGatewayID,String dyModuleFolder){
    	this.clientID = clientID;
    	this.iotGatewayID = iotGatewayID;
    	this.dyModuleFolder = dyModuleFolder;
    }
	@Override
	public Manager createManager() {
		// TODO Auto-generated method stub
		return new ExecutorManager(clientID, iotGatewayID, dyModuleFolder);
	}

}

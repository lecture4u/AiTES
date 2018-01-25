package com.github.aites.localtest.framework;

import com.github.aites.localtest.application.AppUIController;
import com.github.aites.localtest.utility.PropReader;

public class LocalAiTES {
	private static AppUIController controller;
	public LocalAiTES(AppUIController controller){
		this.controller = controller;
	}
	public void runAiTES(){
		PropReader propReader = new PropReader();
		propSetting(propReader);
		
	}
	
	private void propSetting(PropReader propReader){
		controller.setConfigSetting(propReader);
	}
}

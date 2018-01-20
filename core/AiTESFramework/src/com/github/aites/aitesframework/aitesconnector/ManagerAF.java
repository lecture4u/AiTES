package com.github.aites.aitesframework.aitesconnector;

import com.github.aites.aitesframework.aitesmanager.Manager;


public class ManagerAF {
	/**Get MAPE-K  Manager Class*/
	public static Manager getManager(Factory factory){
		return factory.createManager();
	}

}

package com.github.aites.framework.aitesconnector;

import com.github.aites.framework.aitesmanager.Manager;


public class ManagerAF {
	/**Get MAPE-K  Manager Class*/
	public static Manager getManager(Factory factory){
		return factory.createManager();
	}

}

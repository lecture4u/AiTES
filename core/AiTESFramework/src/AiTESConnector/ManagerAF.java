package AiTESConnector;

import AiTESManager.Manager;


public class ManagerAF {
	/**Get MAPE-K  Manager Class*/
	public static Manager getManager(Factory factory){
		return factory.createManager();
	}

}

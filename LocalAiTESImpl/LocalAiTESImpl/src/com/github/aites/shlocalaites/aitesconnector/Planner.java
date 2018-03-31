package com.github.aites.shlocalaites.aitesconnector;



import com.github.aites.shlocalaites.aitesmanager.PlannerManager;

import AiTESConnector.Factory;
import AiTESManager.Manager;

public class Planner implements Factory{
	String stateSet;
	String collectDate;

	public Planner(String stateSet, String collectDate){
		this.stateSet = stateSet;
		this.collectDate = collectDate;
	
	}
	@Override
	public Manager createManager() {
		// TODO Auto-generated method stub
		return new PlannerManager(stateSet,collectDate);
	}

}

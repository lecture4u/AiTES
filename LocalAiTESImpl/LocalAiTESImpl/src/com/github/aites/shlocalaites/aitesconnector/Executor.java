package com.github.aites.shlocalaites.aitesconnector;

import java.util.ArrayList;

import com.github.aites.framework.aitesconnector.Factory;
import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.planner.Plan;
import com.github.aites.shlocalaites.aitesmanager.ExecutorManager;







public class Executor implements Factory{
	ArrayList<Plan> planList;
	Timer systemTime;

	public Executor(ArrayList<Plan> planList, Timer systemTime){
		this.planList = planList;
		this.systemTime = systemTime;
	}
	
	@Override
	public ExecutorManager createManager() {
		// TODO Auto-generated method stub
		return new ExecutorManager(planList,systemTime);
	}

}

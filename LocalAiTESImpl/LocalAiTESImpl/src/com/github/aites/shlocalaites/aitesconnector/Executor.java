package com.github.aites.shlocalaites.aitesconnector;

import java.util.ArrayList;

import com.github.aites.shlocalaites.aitesmanager.ExecutorManager;
import com.github.aites.shlocalaites.log.LogWritter;
import com.github.aites.shlocalaites.palnner.Plan;
import com.github.aitest.shlocalaites.executor.Timer;

import AiTESConnector.Factory;


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

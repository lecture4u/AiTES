package com.github.aites.shlocalaites.aitesmanager;

import java.util.ArrayList;

import com.github.aites.shlocalaites.log.LogWritter;
import com.github.aites.shlocalaites.palnner.Plan;
import com.github.aitest.shlocalaites.executor.Scheduler;
import com.github.aitest.shlocalaites.executor.Timer;

import AiTESManager.Manager;

public class ExecutorManager extends Manager{
	ArrayList<Plan> planList;
	private Scheduler scheduler;
	Timer systemTime;
	LogWritter log = LogWritter.getInstance();
	public ExecutorManager(ArrayList<Plan> planList, Timer systemTime){
		this.planList = planList;
		this.systemTime = systemTime;
	}
	@Override
	public void run() {
		log.logInput("---------------run executor for execute module---------------");
		log.logInput("system time:"+systemTime.getWholeTime());
		scheduler = new Scheduler();
		for(Plan p:planList){
			scheduler.inputPlan(p);
		}
		scheduler.execute(systemTime.getWholeTime());
	}

}

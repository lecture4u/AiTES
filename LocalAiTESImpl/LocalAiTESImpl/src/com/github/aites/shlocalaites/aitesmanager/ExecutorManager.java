package com.github.aites.shlocalaites.aitesmanager;

import java.util.ArrayList;

import com.github.aites.framework.aitesmanager.Manager;
import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.planner.Plan;
import com.github.aitest.shlocalaites.executor.SHScheduler;




public class ExecutorManager extends Manager{
	ArrayList<Plan> planList;
	private SHScheduler scheduler;
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
		String moduleFolderName ="";
		scheduler = new SHScheduler(moduleFolderName);
		for(Plan p:planList){
			scheduler.inputPlan(p);
		}
		scheduler.execute(systemTime.getWholeTime());
	}

}

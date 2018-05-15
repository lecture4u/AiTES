package com.github.aites.framework.planner;

import java.util.ArrayList;

import com.github.aites.framework.executor.Scheduler;
import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.globalknowledge.DBConnector;
import com.github.aites.framework.globalknowledge.LoadScheduleFromStateSet;
import com.github.aites.framework.orchestration.Participants;


public class PlanManager {
	Timer timer = Timer.getInstance();
	Participants partners = Participants.getInstance();
	private String stateSet;
	ArrayList<String> allOccurStateSet;
	DBConnector db;
	ArrayList<Plan> tempSchedule = new ArrayList<Plan>();
	ArrayList<Plan> planList = new ArrayList<Plan>();
	String prefix;
	Scheduler scheduler = Scheduler.getInstance();
	public PlanManager(String stateSet){
		this.stateSet = stateSet;
	}
	public void setSchedulePrefix(String prefix){
		this.prefix = prefix;
	}
	public void planing(){
		StateCombinationCalculator scc = new StateCombinationCalculator(stateSet);
		for(int i=1; i<=scc.getStateSetLength(); i++){
			scc.CalstateCombinateion(scc.getStateSetLength(), i, 0);
		}
		allOccurStateSet = scc.getAllOccurStateSet();
		
		for(int i=0; i<allOccurStateSet.size(); i++){
			
			db = new LoadScheduleFromStateSet(allOccurStateSet.get(i),timer,prefix);
			db.dbConnect();
			tempSchedule = ((LoadScheduleFromStateSet)db).getSchedule();
			for(int j=0; j<tempSchedule.size(); j++){
				planList.add(tempSchedule.get(j));
				scheduler.inputPlan(tempSchedule.get(j));
			}
		}
	}
	public ArrayList<Plan> getSchedule(){
		return planList;
	}
}

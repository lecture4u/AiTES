package com.github.aites.framework.planner;

import java.io.File;

/**
 * Class defined plan in AiTES.
 * have plan execute time, target, plan action, moduleName.
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public class Plan {
	private String planTime;
	private String target;
	private String action;
	private String moduleName;
	public Plan(String planTime,String target, String action){
		this.planTime = planTime;
		this.target = target;
		this.action = action;
	}
	/**
     * Method print Plan information
	 * @param  none
	 * @return none
	 */
	public void printPlan(){
		System.out.println("planTime:"+planTime+",target:"+target+",action:"+action);
	}
	public String getPlanTime(){
		return planTime;
	}
	public String getTarget(){
		return target;
	}
	public String getAction(){
		return action;
	}
}

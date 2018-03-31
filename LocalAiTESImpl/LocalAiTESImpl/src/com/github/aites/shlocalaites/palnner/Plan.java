package com.github.aites.shlocalaites.palnner;

import java.io.File;

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
	public File findModule(){
		File file = new File("");
		return file;
	}
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

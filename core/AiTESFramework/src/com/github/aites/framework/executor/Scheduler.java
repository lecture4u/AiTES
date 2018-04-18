package com.github.aites.framework.executor;

import java.io.File;
import java.util.ArrayList;

import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.planner.Plan;


/**
 * Class for manage feedback loop schedule.
 * input plan, and execute.
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public abstract class Scheduler{
	static ArrayList<Plan> planList = new ArrayList<Plan>();
	
	Effector effector;
	LogWritter log = LogWritter.getInstance();
    private String moduleFolder;
    private File currentFile;
    public Scheduler(String moduleFolder){
    	effector = new Effector(moduleFolder);
    }
    /**
	 * Method for execute schedule
	 * plan loaded global knowledge
	 * @param  plan
	 * @return none
	 */
	public void inputPlan(Plan plan){
		log.logInput("Input plan to scheduler");
		planList.add(plan);
	}
	/**
	 * Method for execute schedule
	 * plan execute every time step.
	 * @param systemTime
	 * @return none
	 */
	public void execute(String systemTime){

		log.logInput("**********execute scheduled plan***************");
		
		for(int i=0; i<planList.size(); i++){
			Plan p = planList.get(i);
			
			if(p.getPlanTime().equals(systemTime)){
				log.logInput("Execute plannTarget:"+p.getTarget()+"and action:"+p.getAction());
				
				
				writeGlobalKnowledge();
			
			}
		}

	}
	public void printAllPlan(){
		int i=0;
		for(Plan p : planList){
			log.logInput("index:"+i+",plantime:"+p.getPlanTime()+",planTarget:"+p.getTarget()+",planAction:"+p.getAction());
			i++;
		}
	}
	public abstract void writeGlobalKnowledge();
		
		
	
}

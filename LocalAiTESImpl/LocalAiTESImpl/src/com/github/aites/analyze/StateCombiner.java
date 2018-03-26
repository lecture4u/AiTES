package com.github.aites.analyze;

import com.github.aites.log.LogWritter;

public class StateCombiner {
	private String tempertureState;
	private String positionState;
	private String psState;
	
	private String stateSet;
	private String needPlan;
	LogWritter log = LogWritter.getInstance();
	public StateCombiner(String tempertureState, String positionState, String psState){
		this.tempertureState = tempertureState;
		this.positionState = positionState;
		this.psState = psState;
	}
	public String combinestateSetReasoing(){
		log.logInput("*****state combiner start*****");
		if(psState.equals("under")&&tempertureState.equals("normal") && positionState.equals("outHome")){
			log.logInput("StateSet Don't Need plan:"+psState+","+tempertureState+","+positionState);
			needPlan = "no";
			stateCombine();
		}
		else if(psState.equals("over")&&tempertureState.equals("over") && positionState.equals("inHome")){
			log.logInput("StateSet Don't Need plan:"+psState+","+tempertureState+","+positionState);
			needPlan = "no";
			stateCombine();
		}
		else if(psState.equals("over")&&tempertureState.equals("normal") && positionState.equals("outHome")){
			log.logInput("StateSet Need plan:"+psState+","+tempertureState+","+positionState);
			needPlan = "yes";
			stateCombine();
		}
		else if(psState.equals("under")&&tempertureState.equals("cold") && positionState.equals("inHome")){
			log.logInput("StateSet Need plan:"+psState+","+tempertureState+","+positionState);
			needPlan = "yes";
			stateCombine();
		}
		else if(psState.equals("over")&&tempertureState.equals("normal") && positionState.equals("inHome")){
			log.logInput("StateSet Need plan:"+psState+","+tempertureState+","+positionState);
			needPlan = "yes";
			stateCombine();
		}
		else{
			needPlan = "no";
			stateCombine();
		}
		
		return stateSet;
	}
	public String getNeedPlan(){
		return needPlan;
	}
	private void stateCombine(){
		stateSet = psState + "," + positionState +"," + tempertureState;
	}
}

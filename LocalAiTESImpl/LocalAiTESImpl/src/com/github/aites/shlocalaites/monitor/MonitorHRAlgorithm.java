package com.github.aites.shlocalaites.monitor;

import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.monitor.EnvData;
import com.github.aites.framework.monitor.HRAlgorithm;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.ruleset.RuleSetManager;



public class MonitorHRAlgorithm implements HRAlgorithm{
	EnvData envData;
	String ruleSetURL;
	Timer timer = Timer.getInstance();
	LogWritter log = LogWritter.getInstance();
	public MonitorHRAlgorithm(EnvData envData, String ruleSetURL){
		this.envData = envData;
		this.ruleSetURL = ruleSetURL;
	}
	public String getAllEnvData(){
		String envResult = "";
		for(int di=0; di<envData.getDeviceData().size(); di++){
			if(di==0){
				envResult = envResult+envData.getDeviceData().get(di);
			}
			else{
				envResult = envResult+","+envData.getDeviceData().get(di);
			}
			
		}
		
		return envResult;
	}
	@Override
	public String envDataHRAlgorithm(){
		log.logInput("*****Caculate Entire Smart Home PS and reasoning context need call analyzer*****");
	
		Double hresult =0.0;
		for(int di=0; di<envData.getDeviceData().size(); di++){
			envData.getDeviceNmae().get(di);
			hresult += Double.parseDouble(envData.getDeviceData().get(di));
			
		}
		log.logInput("Power consumtion:"+hresult);
		
		String dpValue = hresult.toString();
		String feedBackInd = "SHEdata"+timer.getAbbTime();
		log.logInput("#####Reasoing rule about individual:"+feedBackInd+" and dataProperty:"+dpValue+"#####");
		RuleSetManager ruleSetManager = new RuleSetManager(ruleSetURL);
	    ruleSetManager.assertDataProperty("hasPS", feedBackInd, dpValue, "double");
		ruleSetManager.saveRuleSet();
		
		RuleManager ruleManager = new RuleManager(ruleSetURL);
		ruleManager.loadOntology(); 
		boolean isOverPS = ruleManager.reasoningRule(feedBackInd, "PSoverRule");
		boolean isUnderPS = ruleManager.reasoningRule(feedBackInd, "PSunderRule");
		if(isUnderPS){
			return "under";
		}
		else if(isOverPS){
			return "over";
		}
		return "normal";
		
	}
}




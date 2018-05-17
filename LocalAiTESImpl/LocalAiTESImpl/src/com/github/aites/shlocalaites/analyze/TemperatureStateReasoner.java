package com.github.aites.shlocalaites.analyze;

import com.github.aites.framework.analyzer.StateReasoner;
import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.ruleset.RuleSetManager;



public class TemperatureStateReasoner implements StateReasoner{
	String tempertureState;
	private String ruleSetURL;
	LogWritter log = LogWritter.getInstance();
	Timer timer = Timer.getInstance();
	public TemperatureStateReasoner(String ruleSetURL){
		this.ruleSetURL = ruleSetURL;
	}
	@Override
	public String stateResoning(String temperture, String collectDate){
		log.logInput("*****Start Home Temperture State Reasoning*****");
		log.logInput(temperture);
		
		String feedBackInd = "SHEdata"+timer.getAbbTime();
		log.logInput("#####Reasoing rule about individual:"+feedBackInd+" and dataProperty:"+temperture+"#####");
		RuleSetManager ruleSetManager = new RuleSetManager(ruleSetURL);
	    ruleSetManager.assertDataProperty("hasTemperture", feedBackInd, temperture, "double");
		ruleSetManager.saveRuleSet();
		
		RuleManager ruleManager = new RuleManager(ruleSetURL);
		ruleManager.loadOntology(); 
		boolean isHotTem= ruleManager.reasoningRule(feedBackInd, "TempertureHotRule");
		boolean isColdTem = ruleManager.reasoningRule(feedBackInd, "TempertureColdRule");
		if(isHotTem){
			tempertureState = "hot";
		}
		else if(isColdTem){
			tempertureState = "cold";
		}
		else{
			tempertureState = "warm";
		}
		
		return tempertureState;
	}
	
}

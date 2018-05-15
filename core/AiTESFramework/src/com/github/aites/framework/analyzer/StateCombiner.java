package com.github.aites.framework.analyzer;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.ruleset.RuleSetManager;

public class StateCombiner {
	private LogWritter log = LogWritter.getInstance();
	private ArrayList<String> stateList = new ArrayList<String>();
	private String needPlan = "no";
	private String stateSet = "";
	
	private String ruleSetURL;
	Timer timer = Timer.getInstance();
	private HashMap<Integer, String> setOrderMap = new HashMap<Integer, String>();
	public StateCombiner(ArrayList<String> stateList, HashMap<Integer, String> setOrderMap, String ruleSetURL){
		this.setOrderMap = setOrderMap;
		this.stateList = stateList;
		this.ruleSetURL = ruleSetURL;
	}
	public String reasoningStateSetNeedPlan(){
		log.logInput("****Start state combine and resoning this set need plan*****");
		RuleSetManager ruleSetManager = new RuleSetManager(ruleSetURL);
		String feedBackInd = "SHEdata"+timer.getAbbTime();
		
		for(int i=0; i<setOrderMap.size(); i++){
			log.logInput("Reasoning state set Component:"+setOrderMap.get(i)+" and value:"+stateList.get(i));
			ruleSetManager.assertDataProperty(setOrderMap.get(i)+"State", feedBackInd, stateList.get(i), "string");
		}
	
		ruleSetManager.saveRuleSet();
		
		RuleManager ruleManager = new RuleManager(ruleSetURL);
		ruleManager.loadOntology();
		for(int i=1; i<=3; i++){
			if( ruleManager.reasoningRule(feedBackInd, "ISneedPlanRule"+i)){
				log.logInput("#####Reasoning This state set is need planing? is yes to individual:"+feedBackInd+" and ruleName: ISneedPlanRule"+i);
				needPlan = "yes";
			}
		}
		
		for(int i=0; i<stateList.size(); i++){
			if(i == stateList.size()-1){
				stateSet = stateSet + stateList.get(i);
			}
			else{
				stateSet = stateSet + stateList.get(i)+",";
			}
		}
		
	    return needPlan;	
	}
	public String getStateSet(){
		return stateSet;
	}
	
}

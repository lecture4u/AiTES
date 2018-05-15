package com.github.aites.shlocalaites.analyze;

import java.util.ArrayList;
import java.util.Collections;

import com.github.aites.framework.analyzer.StateReasoner;
import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.rule.RuleManager;
import com.github.aites.framework.ruleset.RuleSetManager;


public class PositionStateReasoner implements StateReasoner{
	ArrayList<String> latitudeList = new ArrayList<String>();
	ArrayList<String> rongitudeList = new ArrayList<String>();
	LogWritter log = LogWritter.getInstance();
	String positionState;
	String collectDate;
	Timer timer = Timer.getInstance();
 	private void processedPosition(String position){
 		
		String[] positionParser = position.split(":");
		String delims = "[ .,?!]+";
		String[] latitudeParser = positionParser[0].split(delims);
		String[] rongitudeParser = positionParser[1].split(delims);
		
		Collections.addAll(latitudeList, latitudeParser);
		Collections.addAll(rongitudeList, rongitudeParser);

		String feedBackInd = "SHEdata"+timer.getAbbTime();
		RuleSetManager ruleSetManager = new RuleSetManager("smartHome.xml");
		
		String otla = latitudeParser[0]+"."+latitudeParser[1];
		String otro = rongitudeParser[0]+"."+rongitudeParser[1];
		String thridla = latitudeParser[2];
		String thridro = rongitudeParser[2];
		String forthla = latitudeParser[2];
		String forthro = rongitudeParser[2];
		log.logInput("#####Reasoing rule about individual:"+feedBackInd+" and dataProperty:"+otla+","+otro+","+thridla+","+thridro+","+forthla+","+forthro+"#####");
	    ruleSetManager.assertDataProperty("otLa", feedBackInd, otla, "string");
	    ruleSetManager.assertDataProperty("otRo", feedBackInd, otro, "string");
	    ruleSetManager.assertDataProperty("thridLa", feedBackInd, thridla, "integer");
	    ruleSetManager.assertDataProperty("thridRo", feedBackInd, thridro, "integer");
	    ruleSetManager.assertDataProperty("forthLa", feedBackInd, forthla, "integer");
	    ruleSetManager.assertDataProperty("forthRo", feedBackInd, forthro, "integer");
		ruleSetManager.saveRuleSet();
		

		
		RuleManager ruleManager = new RuleManager("smartHome.xml");
		ruleManager.loadOntology(); 
		boolean isNearHome= ruleManager.reasoningRule(feedBackInd, "PositionNearRule");
		boolean isInHome = ruleManager.reasoningRule(feedBackInd, "PositionInRule");
		
		if(isInHome){
			positionState = "inHome";
		}
		else if(isNearHome){
			positionState = "nearHome";
		}
		else{
			positionState = "outHome";
		}
		
	}
 	@Override
	public String stateResoning(String position, String collectDate){
		this.collectDate = collectDate;
		log.logInput("*****Reasoning Home User Position State  d*****");
		processedPosition(position);
		log.logInput("resoning user position state:"+positionState);
		return positionState;
	}
}

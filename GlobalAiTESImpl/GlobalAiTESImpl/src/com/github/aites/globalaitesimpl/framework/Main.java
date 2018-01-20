package com.github.aites.globalaitesimpl.framework;

import com.github.aites.globalaitesimple.ruleset.Rule;
import com.github.aites.globalaitesimple.ruleset.RuleSetParser;

public class Main {
	public static void main(String[] args) {
		
	          RuleSetParser rulesetP = new RuleSetParser();
	          rulesetP.ruleSetParsing("testRuleSet.xml");
	          //GlobalAiTES globalaites = new GlobalAiTES();
	         // globalaites.setRuleSet(ruleSet);
	         // globalaites.runAiTES();
	          
	          Rule findRule = rulesetP.findRule("sdvdsvdsv");
	          if(findRule != null){
	        	  System.out.println("findRule :" +findRule.getRuleName());
	          }
	          else{
	        	  System.out.println("Can't find Rule");
	          }
	          rulesetP.removeRule("UnderPower");
	}
}

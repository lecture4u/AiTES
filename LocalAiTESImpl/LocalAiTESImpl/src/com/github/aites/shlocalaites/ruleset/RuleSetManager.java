package com.github.aites.shlocalaites.ruleset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RuleSetManager {
	private String ruleSetName;
	private String ruleSet;
	RuleSetParser ruleSetLoader = new RuleSetParser();
	
	
	private String ruleSetHead;
	private String ruleBody;
	private final String ruleSetTail = ")";
	
	public RuleSetManager(String ruleSetName){
		this.ruleSetName = ruleSetName;
		ruleSetLoader.loadRuleSet(ruleSetName);
		
		ruleSetHead = ruleSetLoader.getRuleSetHeader();
		ruleBody = ruleSetLoader.getRuleSetBody();
	}
	public void assertInd(String indName, String className){
		String indAxiom = "    Declaration(NamedIndividual(:" + indName + "))\r\n" + 
    			            "    ClassAssertion(:"+className+" :" + indName + ")\r\n";
		
		ruleBody = ruleBody+indAxiom;
	}
	public void assertDataProperty(String dpName, String indName, String dpValue, String dpType){
		String dpAxiom = "    DataPropertyAssertion(:"+dpName+" :" + indName + " \"" + dpValue + "\"^^xsd:"+dpType+")\r\n";
		ruleBody = ruleBody+dpAxiom;
	}
	
	public void saveRuleSet(){
		ruleSet = ruleSetHead+ ruleBody + ruleSetTail;
		System.out.println(ruleSet);
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(ruleSetName)));
			bw.write(ruleSet);
			bw.flush();
	    	bw.close();
		} catch (IOException e) {
	
			e.printStackTrace();
	    }
	}

	public void printRuleSetHeader(){
		System.out.println(ruleSetHead);
	}
	public void printRuleSetBody(){
		System.out.println(ruleBody);
	}
}

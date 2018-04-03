package com.github.aites.shlocalaites.ruleset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RuleSetManager {
	private String ruleSetName;
	private String ruleSet;
	RuleSetParser ruleSetLoader = new RuleSetParser();
	private RuleSetBody ruleSetBody;
	
	private String ruleSetHead;
	
	private final String ruleSetTail = ")";
	
	public RuleSetManager(String ruleSetName){
		this.ruleSetName = ruleSetName;
		ruleSetLoader.loadRuleSet(ruleSetName);
		
		ruleSetHead = ruleSetLoader.getRuleSetHeader();
		ruleSetBody = ruleSetLoader.getRuleSetBody();
		
	}
	public void assertInd(String indName, String className){
		String indAxiom = "    Declaration(NamedIndividual(:" + indName + "))\r\n";
		ruleSetBody.addIndDeclaration(indAxiom);
		String classAxiom = "    ClassAssertion(:"+className+" :" + indName + ")\r\n";
	    ruleSetBody.addAssertion(classAxiom);        
		
	}
	public void assertDataProperty(String dpName, String indName, String dpValue, String dpType){
		String dpAxiom = "    DataPropertyAssertion(:"+dpName+" :" + indName + " \"" + dpValue + "\"^^xsd:"+dpType+")\r\n";
		ruleSetBody.addAssertion(dpAxiom);
	}
	public void assertObjectProperty(String opName, String ind1Name, String ind2Name){
		String opAxiom = "    ObjectPropertyAssertion(:"+opName+" :" + ind1Name + ":"+ind2Name+")\r\n";
		ruleSetBody.addAssertion(opAxiom);
	}
	public void saveRuleSet(){
		String ruleBodyValue = ruleSetBody.getRuleSetBody();
		ruleSet = ruleSetHead+ ruleBodyValue + ruleSetTail;
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
	
}

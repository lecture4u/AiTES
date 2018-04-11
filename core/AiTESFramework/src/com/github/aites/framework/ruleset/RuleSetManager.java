package com.github.aites.framework.ruleset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Class for manage ontology rule set
 * parsing rule set File, update rule, delete rule.
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
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
	/**
	 * Method for declaration NamedIndividual axiom
	 * declarted to rulest form Declaration(NamedIndividual(:individualname))
	 * and asserted to rulest form ClassAssertion(:className :individualName)
	 * @param indName, className
	 * @return none
	 */
	public void assertInd(String indName, String className){
		String indAxiom = "    Declaration(NamedIndividual(:" + indName + "))\r\n";
		ruleSetBody.addIndDeclaration(indAxiom);
		String classAxiom = "    ClassAssertion(:"+className+" :" + indName + ")\r\n";
	    ruleSetBody.addAssertion(classAxiom);        
		
	}
	/**
	 * Method for assertion data property axiom
	 * asserted to rulest form DataPropertyAssertion(:dpname :individual "dpvaule"^^dptype)
	 * @param dpName,indName,dpValue, dpType
	 * @return none
	 */
	public void assertDataProperty(String dpName, String indName, String dpValue, String dpType){
		String dpAxiom = "    DataPropertyAssertion(:"+dpName+" :" + indName + " \"" + dpValue + "\"^^xsd:"+dpType+")\r\n";
		ruleSetBody.addAssertion(dpAxiom);
	}
	/**
	 * Method for assertion object property axiom
	 * asserted to rulest form ObjectPropertyAssertion(:objectName :object :subject)
	 * @param opName,ind1Name,ind2Name
	 * @return none
	 */
	public void assertObjectProperty(String opName, String ind1Name, String ind2Name){
		String opAxiom = "    ObjectPropertyAssertion(:"+opName+" :" + ind1Name + ":"+ind2Name+")\r\n";
		ruleSetBody.addAssertion(opAxiom);
	}
	/**
	 * Method for apply ontology axiom change.
	 * save rule set File to file system where program located.
	 * @param none
	 * @return none
	 * @exception IOException
	 *     rule set save failed
	 */
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
package hyperledger.fabric.Rulemanager;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import hyperledger.fabric.Rulemanager.SWRLrule;
/**
 * Class for manage ontology rule set
 * parsing rule set File, update rule, delete rule.
 * @author JungHyun An
 * @version 3.0.2
 * 
 */
public class RuleSetManager {
	private String ruleSetName;
	private String ruleSet;
	RuleSetParser ruleSetParser = new RuleSetParser();
	private RuleSetBody ruleSetBody;
	
	private String ruleSetHead;
	
	private final String ruleSetTail = ")";
	
	public RuleSetManager(String ruleSetName){
		this.ruleSetName = ruleSetName;
		ruleSetParser.loadRuleSet(ruleSetName);
		
		ruleSetHead = ruleSetParser.getRuleSetHeader();
		ruleSetBody = ruleSetParser.getRuleSetBody();
		
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
	public void deleteInd(String indName){
		ruleSetBody.deleteIndDeclaration(indName);
		ruleSetBody.deleteAssertion(indName);
	}
	
	public void assertClass(String className) {
		String classAxiom = "    Declaration(Class(:" + className + "))\r\n";
		ruleSetBody.addClassDeclaration(classAxiom);
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
	public void assertSWRLrule(String Rulename,SWRLrule rule){		
		assertClass(Rulename);
		ruleSetBody.addSWRLRule(rule);
	}
	public void updateSWRLBulitInRule(String ruleName, int bulitInIndex, String newValue){
		ruleSetBody.updateSWRLRule_BuiltInAtom(ruleName, bulitInIndex, newValue);
	}
	public void updateSWRLDataRangRule(String ruleName, int dataRangeIndex, String newDataType, String newMinValue, String newMaxValue, String newVariable) {
		ruleSetBody.updateSWRLRule_DataRangeAtom(ruleName, dataRangeIndex, newDataType, newMinValue, newMaxValue, newVariable);
	}
	public void saveRuleSet(){
		String ruleBodyValue = ruleSetBody.getRuleSetBody();
		ruleSet = ruleSetHead+ ruleBodyValue + ruleSetTail;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(ruleSetName)));
			bw.write(ruleSet);
			bw.flush();
	    	bw.close();
		} catch (IOException e) {
	
			e.printStackTrace();
	    }
	}

	 public void printRuleSetInformation(){
	    	System.out.println("#####Print RuleSetName:"+this.ruleSetName+"'s information#####");
	    	System.out.println("*****RuleSet Header*****");
	    	System.out.println(ruleSetHead);
	    	System.out.println("*****RuleSet Body*****");
	    	ruleSetBody.printRuleSetBodyInformation();
	    }
	
}

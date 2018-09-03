package hyperledger.fabric.Rulemanager;

import java.util.ArrayList;
import java.util.Iterator;

import hyperledger.fabric.Rulemanager.SWRLrule;
/**
 * Class for define and make object about ontology rule set body.
 * 
 * @author JungHyun An
 * @version 3.0.2
 * 
 */
public class RuleSetBody {
	private String ruleSetBody = "";
	
	private String declaration = "";
	private String assertion = "";
	private String exAxiom = "";
	private String allSWRLrule ="";
	
	private ArrayList<String> classDeclarations;
	private ArrayList<String> dpDeclarations;
	private ArrayList<String> opDclarations;
	private ArrayList<String> indDclarations;
	private ArrayList<String>  assertions;
	private ArrayList<String>  exAxioms;
	
	private ArrayList<SWRLrule> swrlRuleList = new ArrayList<SWRLrule>();
	
	public RuleSetBody(){
		
		assertions = new ArrayList<String>();
		exAxioms = new ArrayList<String>();
		
		classDeclarations = new ArrayList<String>();
		dpDeclarations = new ArrayList<String>();
		opDclarations = new ArrayList<String>();
		indDclarations = new ArrayList<String>();
	}
	
	public void addClassDeclaration(String classDeclaration){
		classDeclarations.add(classDeclaration);
	}
	public void addDpDeclaration(String dpDeclaration){
		dpDeclarations.add(dpDeclaration);
	}
	public void addOpDeclaration(String opDeclaration){
		opDclarations.add(opDeclaration);
	}
	public void addIndDeclaration(String indDeclaration){
		indDclarations.add(indDeclaration);
	}
	public void deleteIndDeclaration(String indDeclaration){	
		for(Iterator<String> it = indDclarations.iterator() ; it.hasNext() ; )
		{
			String value = it.next();
			if(value.contains(indDeclaration))
			{
				it.remove();
			}
		}
	}

	public void addAssertion(String assertion){
		assertions.add(assertion);
	}
	public void deleteAssertion(String axiomKeyword){	
		for(Iterator<String> it = assertions.iterator() ; it.hasNext() ; )
		{
			String value = it.next();
			
			if(value.contains(axiomKeyword))
			{
				it.remove();
			}
		}
	}
	public void addExAxiom(String exAxiom){
		exAxioms.add(exAxiom);
	}
    
    public void addSWRLRule(SWRLrule rule){
    	swrlRuleList.add(rule);
    }
    public void updateSWRLRule_BuiltInAtom(String ruleName, int bulitInIndex, String newValue){
        for(int i=0; i<swrlRuleList.size(); i++){
        	if(swrlRuleList.get(i).getHeadClassAtom().getClassAtom().equals(ruleName)){
    			System.out.println("find rule, update axiom.");
    			SWRLrule updaterule = swrlRuleList.get(i);
    			updaterule.updateBulitInAtom(bulitInIndex, newValue);
    			
    			swrlRuleList.set(i, updaterule);
    		}
        }   	
    	
    }
    public void updateSWRLRule_DataRangeAtom(String ruleName, int dataRangeIndex, String newDataType, String newMinValue, String newMaxValue, String newVariable) {
        for(int i=0; i<swrlRuleList.size(); i++){
        	if(swrlRuleList.get(i).getHeadClassAtom().getClassAtom().equals(ruleName)){
    			System.out.println("find rule, update axiom.");
    			SWRLrule updaterule = swrlRuleList.get(i);
    			updaterule.updateDataRangeAtom(dataRangeIndex, newDataType, newMinValue, newMaxValue, newVariable);
    			
    			swrlRuleList.set(i, updaterule);
    		}
        }   
    }
	private void combineDeclartion(){
		for(String c : classDeclarations){
			declaration = declaration +c+"\r\n";
		}
		for(String d : dpDeclarations){
			declaration = declaration +d+"\r\n";
		}
		for(String o : opDclarations){
			declaration = declaration +o+"\r\n";
		}
		for(String i : indDclarations){
			declaration = declaration +i+"\r\n";
		}
	}
	private void combineAssertion(){
		for(String a : assertions){
			assertion = assertion +a+"\r\n";
		}
	}
	private void combineExAxiom(){
		for(int i=0; i<exAxioms.size(); i++){
			exAxiom = exAxiom +exAxioms.get(i)+"\r\n";
		}
	}
	private void combineSWRLRules(){
		
		for(SWRLrule rule :swrlRuleList ){
			allSWRLrule = allSWRLrule+rule.getSWRLrule();
		}
		
	}
	public String getRuleSetBody(){
		combineDeclartion();
		combineAssertion();
		combineExAxiom();
		combineSWRLRules();
		ruleSetBody = declaration+assertion+exAxiom+allSWRLrule;
		
		return ruleSetBody;
	}
	public void printRuleSetBodyInformation(){
		System.out.println("*****Print RuleSet Body Information*****");
		System.out.println("Class Declaration Atoms:"+classDeclarations.size());
		for(int i=0; i<classDeclarations.size(); i++){
			System.out.println(classDeclarations.get(i));
		}
		System.out.println("Data Property Atoms:"+dpDeclarations.size());
		for(int i=0; i<dpDeclarations.size(); i++){
			System.out.println(dpDeclarations.get(i));
		}
		System.out.println("Object Property Atoms:"+opDclarations.size());
		for(int i=0; i<opDclarations.size(); i++){
			System.out.println(opDclarations.get(i));
		}
		System.out.println("Individual Atoms:"+indDclarations.size());
		for(int i=0; i<indDclarations.size(); i++){
			System.out.println(indDclarations.get(i));
		}
		System.out.println("Assertion Atoms:"+assertions.size());
		for(int i=0; i<assertions.size(); i++){
			System.out.println(assertions.get(i));
		}
		System.out.println("Extra Atoms:"+exAxioms.size());
		for(int i=0; i<exAxioms.size(); i++){
			System.out.println(exAxioms.get(i));
		}
		System.out.println("SWRL Rules:"+swrlRuleList.size());
		for(int i=0; i<swrlRuleList.size(); i++){
			swrlRuleList.get(i).printSWRLruleInfo();
		}
	}
}


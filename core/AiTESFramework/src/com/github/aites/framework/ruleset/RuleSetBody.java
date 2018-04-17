package com.github.aites.framework.ruleset;

import java.util.ArrayList;
import java.util.Iterator;

import com.github.aites.framework.rule.SWRLrule;
/**
 * Class for define and make object about ontology rule set body.
 * 
 * @author JungHyun An
 * @version 3.0.1
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
    public void updateSWRLRule(String ruleName, int bulitInIndex, String newValue){
        for(int i=0; i<swrlRuleList.size(); i++){
        	if(swrlRuleList.get(i).getHeadClassAtom().getClassAtom().equals(ruleName)){
    			System.out.println("find rule, update axiom.");
    			SWRLrule updaterule = swrlRuleList.get(i);
    			updaterule.updateBulitInAtom(bulitInIndex, newValue);
    			
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
		for(int i=0; i<exAxioms.size() -1; i++){
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
}


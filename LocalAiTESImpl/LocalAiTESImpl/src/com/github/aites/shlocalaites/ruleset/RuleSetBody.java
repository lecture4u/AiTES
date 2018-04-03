package com.github.aites.shlocalaites.ruleset;

import java.util.ArrayList;

public class RuleSetBody {
	private String ruleSetBody = "";
	
	private String declaration = "";
	private String assertion = "";
	private String exAxiom = "";
	
	
	private ArrayList<String> classDeclarations;
	private ArrayList<String> dpDeclarations;
	private ArrayList<String> opDclarations;
	private ArrayList<String> indDclarations;
	private ArrayList<String>  assertions;
	private ArrayList<String>  exAxioms;
	
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
	
	
	public void addAssertion(String assertion){
		assertions.add(assertion);
	}
	public void addExAxiom(String exAxiom){
		exAxioms.add(exAxiom);
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
	
	public String getRuleSetBody(){
		combineDeclartion();
		combineAssertion();
		combineExAxiom();
		ruleSetBody = declaration+assertion+exAxiom;
		
		return ruleSetBody;
	}
}


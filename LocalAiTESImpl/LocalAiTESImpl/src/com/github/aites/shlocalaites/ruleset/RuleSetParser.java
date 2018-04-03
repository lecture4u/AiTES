package com.github.aites.shlocalaites.ruleset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class RuleSetParser {
	private String ruleset = "";
	private String ruleSetHeader ="";
	
	
	private RuleSetBody ruleSetBody = new RuleSetBody();
	
	private boolean headTrigger = false;
	private boolean declartionTrigger = false;
	private boolean assertionTrigger = false;
	public void loadRuleSet(String rulesetName){
		String ruleString = "";
	
		try{
        	BufferedReader br = new BufferedReader(new FileReader(new File(rulesetName)));

			while((ruleString= br.readLine()) != null) {
				if(ruleString.contains("Ontology")){
					ruleSetHeader = ruleset = ruleset+ ruleString + "\r\n";
					headTrigger = true;
				}
				
				
				else if(headTrigger && ruleString.contains("Assertion")){
					ruleSetBody.addAssertion(ruleString);
					declartionTrigger = true;
				}
				else if(declartionTrigger && ruleString.contains("Sub")){
					ruleSetBody.addExAxiom(ruleString);
					assertionTrigger = true;
				}
				else if(assertionTrigger){
					ruleSetBody.addExAxiom(ruleString);
				}
				else if(headTrigger){
					if(ruleString.contains("Class")){
						ruleSetBody.addClassDeclaration(ruleString);
					}
					else if(ruleString.contains("DataProperty")){
						ruleSetBody.addDpDeclaration(ruleString);
					}
					else if(ruleString.contains("ObjectProperty")){
						ruleSetBody.addOpDeclaration(ruleString);
					}
					else if(ruleString.contains("NamedIndividual")){
						ruleSetBody.addIndDeclaration(ruleString);
					}
					
				}
				ruleset = ruleset+ ruleString + "\r\n";						
			}
        	br.close();
        
		}catch(FileNotFoundException e){
        	e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}

	public String getRuleSetHeader(){
		return ruleSetHeader;
	}
	
	public RuleSetBody getRuleSetBody(){
		return ruleSetBody;
	}
}

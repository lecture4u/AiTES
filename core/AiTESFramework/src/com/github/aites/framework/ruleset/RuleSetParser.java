package com.github.aites.framework.ruleset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.github.aites.framework.rule.SWRLrule;

/**
 * Class for parse and store ruleBody to OWL ruleset file.
 * @author JungHyun An
 * @version 3.0.1
 * @see com.github.aites.framework.ruleset.RuleSetBody
 */

public class RuleSetParser {
	private String ruleset = "";
	private String ruleSetHeader ="";
	
	
	private RuleSetBody ruleSetBody = new RuleSetBody();
	
	private Stack ruleParsingStack = new Stack();
	private String swrlRule="";
	private ArrayList<String>ruleList = new ArrayList<String>();
	
	private boolean headTrigger = false;
	private boolean declartionTrigger = false;
	private boolean assertionTrigger = false;
	private boolean dlsafeRuleTrigger = false;
	
	/**
	 * Method for load ruleset and parsing
	 * Ruleset ontology is functional syntax OWL-DL.
	 * @param Ruleset name(ex:smartHomeOWL.xml)
	 * @return none
	 * @exception FileNotFoundException
	 *     ruleset file can't find.
	 * @exception IOException
	 *     ruleset file load failed
	 */
	public void loadRuleSet(String rulesetName){
		String ruleString = "";
	
		try{
        	BufferedReader br = new BufferedReader(new FileReader(new File(rulesetName)));
        	
			while((ruleString= br.readLine()) != null) { 
				if(ruleString.contains("Ontology")){ // head parsing
					ruleSetHeader = ruleset = ruleset+ ruleString + "\r\n";
					headTrigger = true;
				}
				else if(headTrigger && ruleString.contains("Assertion")){ // ruleset body ontology Assertion part parsing
					ruleSetBody.addAssertion(ruleString);
					declartionTrigger = true;
				}
				else if(declartionTrigger && ruleString.contains("Sub")){ // ruleset body ontology Extra Aixom part parsing
					ruleSetBody.addExAxiom(ruleString);
					assertionTrigger = true;
				}
				else if(assertionTrigger){ // ruleset body ontology DL-Safe Ruleset part parsing
					ruleSetBody.addExAxiom(ruleString);
					if(ruleString.contains("DLSafeRule")){ // DL-sate rule first syntax	 				
						swrlRule = swrlRule+ruleString+"\r\n";
						showpush(ruleParsingStack);
						dlsafeRuleTrigger = true;
					}
					else if(dlsafeRuleTrigger){ // DL-sate rule component parsing when: DL-safe rule axiom end. 
						swrlRule = swrlRule+ruleString+"\r\n";
						for(int i=0; i<ruleString.length(); i++){
							if(ruleString.charAt(i) == '('){
								showpush(ruleParsingStack);
								
							}
							else if(ruleString.charAt(i) == ')'){
								try{
									showpop(ruleParsingStack);
									if(ruleParsingStack.isEmpty()){										
										System.out.println(swrlRule);
										ruleList.add(swrlRule);
										
										SWRLrule rule = new SWRLrule(swrlRule);
										ruleSetBody.addSWRLRule(rule);
										swrlRule = "";
										
									}
								}catch(EmptyStackException e){									
									for(String r: ruleList){
										SWRLrule swrlRule = new SWRLrule(r);
										swrlRule.parsedRule();
									}
								}	
							}
						}
					}
					
				}
				else if(headTrigger){ // declaration parsing
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
			e.printStackTrace();
		}    
	}

	public String getRuleSetHeader(){
		return ruleSetHeader;
	}
	
	public RuleSetBody getRuleSetBody(){
		return ruleSetBody;
	}
	private void showpush(Stack st){
		st.push(new String("("));
	}
	private void showpop(Stack st){
		st.pop();		
	
	}
}

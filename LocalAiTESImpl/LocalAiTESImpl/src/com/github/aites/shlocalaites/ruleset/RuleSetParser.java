package com.github.aites.shlocalaites.ruleset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


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
					if(ruleString.contains("DLSafeRule")){
						System.out.println("#####DLSafeRule Start#####");
						swrlRule = swrlRule+ruleString+"\r\n";
						showpush(ruleParsingStack);
						dlsafeRuleTrigger = true;
					}
					else if(dlsafeRuleTrigger){
						swrlRule = swrlRule+ruleString+"\r\n";
						for(int i=0; i<ruleString.length(); i++){
							if(ruleString.charAt(i) == '('){
								showpush(ruleParsingStack);
								
							}
							else if(ruleString.charAt(i) == ')'){
								try{
									showpop(ruleParsingStack);
									if(ruleParsingStack.isEmpty()){
										System.out.println("#####DLSafeRule end#####");
										System.out.println(swrlRule);
										ruleList.add(swrlRule);
										swrlRule = "";
										
									}
								}catch(EmptyStackException e){
									System.out.println("#####End Rule parsing#####");
									for(String r: ruleList){
										SWRLrule swrlRule = new SWRLrule(r);
										swrlRule.parsedRule();
									}
								}	
							}
						}
					}
					
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
	private void showpush(Stack st){
		st.push(new String("("));
		System.out.println("push");
		System.out.println("stack:"+st);
	}
	private void showpop(Stack st){
		st.pop();
		System.out.println("pop");
		System.out.println("stack:"+st);
	
	}
}

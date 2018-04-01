package com.github.aites.shlocalaites.ruleset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class RuleSetParser {
	private String ruleset = "";
	private String ruleSetHeader ="";
	private String ruleSetBody = "";
	
	private boolean headTrigger = false;
	public void loadRuleSet(String rulesetName){
		String ruleString = "";
		try{
        	BufferedReader br = new BufferedReader(new FileReader(new File(rulesetName)));

			while((ruleString= br.readLine()) != null) {
				if(ruleString.contains("Ontology")){
					ruleSetHeader = ruleset = ruleset+ ruleString + "\r\n";
					headTrigger = true;
				}
				else if(headTrigger){
					ruleSetBody =  ruleSetBody+ ruleString + "\r\n";
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
	public String getRuleSetBody(){
		ruleSetBody = ruleSetBody.substring(0, ruleSetBody.length()-3); // - ")/r/n"
		return ruleSetBody;
	}
}

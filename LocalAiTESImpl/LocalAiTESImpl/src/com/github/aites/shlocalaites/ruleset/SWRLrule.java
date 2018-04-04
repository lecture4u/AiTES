package com.github.aites.shlocalaites.ruleset;

import java.util.ArrayList;

public class SWRLrule {
	private String swrlRule;
    private ArrayList<String> bodyAtoms = new ArrayList<String>();
    private String headAtom;
    private String annotation;
    
    public SWRLrule(String swrlRule){
    	this.swrlRule = swrlRule;
    	ruleParsing();
    }
	private void ruleParsing(){
		String[] parsedRule = swrlRule.split("\r\n");
		for(int i=0; i<parsedRule.length; i++){
			if(parsedRule[i].contains("Body")){
				//System.out.println(parsedRule[i] +" ["+i+"] - BodyAtoms");
				for(int j=i+1; i<parsedRule.length; j++){
					if(parsedRule[j].equals("        )")){
						break;
					}
					bodyAtoms.add(parsedRule[j]);
				}
			}
			else if(parsedRule[i].contains("Head")){
				//System.out.println(parsedRule[i] +" ["+i+"] - HeadAtoms");
				headAtom = parsedRule[i+1];
			}
			else if(parsedRule[i].contains("Annotation")){
				//System.out.println(parsedRule[i] +" ["+i+"] - Annotation");
				annotation = parsedRule[i];
			}
			else{
				//System.out.println(parsedRule[i] +" ["+i+"]");
			}
			
		}
	}
	public void parsedRule(){
		System.out.println("####Parsed SWRL rule#####");
		System.out.println("Rule annotation:"+annotation);
		System.out.println("Rule headAtom:"+headAtom);
		for(String b : bodyAtoms){
			System.out.println("Rule bodyAtom:"+b);
		}
	}
}

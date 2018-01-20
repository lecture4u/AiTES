package Rule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class RuleSetParser {
	String ruleSet = "";
	Rule rule;
	String ruleName;
	ArrayList<Rule> ruleList = new ArrayList<Rule>();
	
	private final String body = "ClassAtom";
	private final String dataProperty = "DataPropertyAtom";
	private final String builtInAtom = "BuiltInAtom";
	private final String head = "Head";
	public void ruleSetParsing(String fileName){
		try{
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String s;
			while((s = in.readLine())!=null){
				ruleSet = ruleSet+s+"\n";
				if(s.contains("DLSafeRule")){					
					
					int headIndex = s.indexOf(head);
					int bodyIndex = s.indexOf(body);
					String atomBody = s.substring(bodyIndex, headIndex);
					String atomHead = s.substring(headIndex,s.length()-1);
					headParser(atomHead);
				
					
					
					if(atomBody.contains(dataProperty)){
						headIndex = s.indexOf(dataProperty);
						String classAtomBody = s.substring(bodyIndex,headIndex);
						int dataPropertyIndex = atomBody.indexOf(dataProperty);
						int builtInAtomIndex = atomBody.indexOf(builtInAtom); 
						String dataPropertyBody = atomBody.substring(dataPropertyIndex, builtInAtomIndex);
						String builtInAtomBody = atomBody.substring(builtInAtomIndex, atomBody.length()-1);
						
						
						classAtomParser(classAtomBody);
						propertyAtomParser(dataPropertyBody);
						builtInAtomParser(builtInAtomBody);
						
					}
					else
					{
						headIndex = s.indexOf(builtInAtom);
						String classAtomBody = s.substring(bodyIndex,headIndex);
						int builtInAtomIndex = atomBody.indexOf(builtInAtom); 
					    String builtInAtomBody = atomBody.substring(builtInAtomIndex, atomBody.length()-1);
				
						
						classAtomParser(classAtomBody);
						builtInAtomParser(builtInAtomBody);
						
					}
					rule.makeRuleBody();
					rule.printRule();
					ruleList.add(rule);
					
				}
				
			}
			in.close();
		}catch(IOException e){
			System.out.println(e);
		}
		catch(StringIndexOutOfBoundsException e){
			System.out.println("haven't dataProperty");
			
		}
		System.out.println(ruleSet);
	}
	private void headParser(String atomHead){
		String[] parser = atomHead.split("\\s"); 
		String ruleName = parser[0].substring(16);
		this.ruleName = ruleName;
	}
	private void classAtomParser(String classAtomBody){
		String[] parser = classAtomBody.split("\\s");
		String classAtom = parser[0].substring(11);
		
		String variable = parser[1].substring(13, parser[1].length()-2);
		rule = new Rule(ruleName,classAtom,variable);
	}
	private void propertyAtomParser(String propertyAtom){
		String[] parser = propertyAtom.split("\\s");
		String[] variables = new String[2];
		int typeIndex = propertyAtom.indexOf("(");
		String type = parser[0].substring(0, typeIndex);
		
		String property = parser[0].substring(typeIndex+2);
		
		for(int i=1; i<parser.length; i++){
			if(i == parser.length-1){
				String variable = parser[i].substring(13, parser[i].length()-2);
				
				variables[i-1] = variable;
			}
			else{
				String variable = parser[i].substring(13, parser[i].length()-1);
			
				variables[i-1] = variable;
			}
			
		}
		rule.addPropertyAtom(type, property, variables);
	}
	private void builtInAtomParser(String builtInAtom){
		String[] parser = builtInAtom.split("\\s");
		String[] variables = new String[1];
		int typeIndex = builtInAtom.indexOf("(");
		String type = parser[0].substring(0, typeIndex);
		
		String property = parser[0].substring(typeIndex+7);
		
		
		String variable = parser[1].substring(13, parser[1].length()-1);
		
		variables[0] = variable;
		String atom = parser[2].substring(0, parser[2].length()-1);
		rule.addBuiltInAtom(property, variables, atom);
	}
	public Rule findRule(String ruleName){
		Rule tempRule = new Rule();
		for(int i=0; i<ruleList.size(); i++){
			tempRule = ruleList.get(i);
			System.out.println(tempRule.getRuleName());
			if(tempRule.getRuleName().equals(ruleName)){
				return tempRule;
			}
		}
		return null;
	}
	public void removeRule(String ruleName){
		
		for(int i=0; i<ruleList.size(); i++){
			
			if(ruleList.get(i).getRuleName().equals(ruleName)){
				ruleList.remove(i);
			}
		}
		
		for(int i=0; i<ruleList.size(); i++){
			System.out.println(ruleList.get(i).getRuleName());
		}
	}
}
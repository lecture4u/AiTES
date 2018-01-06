package Rule;

public class RuleGenerator {
	public String ruleGenerate(String ruleName, String condition, String comment,String variable){
		String dlHead = "DLSafeRule(";
		String annotation = "Annotation(rdfs:comment "+comment+")";
		String body ="Body("+condition+")";
		String head = "Head(ClassAtom(:"+ruleName+" Variable(var:"+variable+")))";
		String dlTail = ")";
		//Rule rule = new Rule(); 
		
		String dlRule = dlHead+annotation+body+head+dlTail;
		return dlRule;
		
	}
	public void rulemodify(){
		
	}
	public void ruledelete(){
		
	}
}

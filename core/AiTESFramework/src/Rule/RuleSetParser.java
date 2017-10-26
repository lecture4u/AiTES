package Rule;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RuleSetParser {
private String jarFileName;
	
	public void readXMLFile(String ruleName,String ruleSetURL){
		try{
			File fXmlFile = new File(ruleSetURL);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		
			doc.getDocumentElement().normalize();
		
		
		
			NodeList nList = doc.getElementsByTagName("rule");
		
			System.out.println("------------------------------");
		
			for(int temp = 0; temp<nList.getLength(); temp++){
				Node nNode = 
						nList.item(temp);
		
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) nNode;
									
					if(eElement.getAttribute("name").equals(ruleName)){
						jarFileName = eElement.getElementsByTagName("jarFile").item(0).getTextContent().toString();
						
					}
				}
			}
			System.out.println(jarFileName);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getJarFileName(){
		return jarFileName;
	}
}

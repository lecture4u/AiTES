package Rule;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public  class RuleSetManager {

	public static String ruleSetURL;
	public String rulename;
	
	
	public String jarFileName;
	public String jarFIleURL;
	
	public Class mainClass;
	public RuleSetManager(String ruleSetURL , String jarFIleURL){
		this.ruleSetURL = ruleSetURL;
		this.jarFIleURL = jarFIleURL;
		
	
	}
	public void setRule(String rulename){
		this.rulename = rulename;
	}
	public Class changeRule() {
		try {
			RuleSetParser rp = new RuleSetParser();
			rp.readXMLFile(rulename,ruleSetURL);		
		
			jarFileName = rp.getJarFileName();
			System.out.println("jarFile:"+jarFileName);
		
			ClassLoadFromJarFile jar = new ClassLoadFromJarFile();
		
		
			mainClass = jar.getClassFromJarFile(jarFileName,jarFIleURL);
		
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mainClass;
	}

	
	
}

package com.github.aites.framework.rule;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoadFromJarFile {
	
	public Class getClassFromJarFile(String jarFileName, String jarFileURL)throws Exception{
		String FilePath = jarFileURL +jarFileName;
		String[] ClassParser;
		ClassParser = jarFileName.split("\\.");
		String ClassName = ClassParser[0];
		
		try{
			File file = new File(FilePath);
			URL url = file.toURL();
			
			URL[] urls = new URL[]{url};
			
			ClassLoader cl = new URLClassLoader(urls);
			Class cls = cl.loadClass("Rule."+ClassName);
			return cls;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}
	
	}
 
}

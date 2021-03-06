package com.github.aites.framework.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * Class for write log
 * log file created program folder
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public class LogWritter {
	private static String log = "";
	private String filename = "./log.txt";
	private static int logcounter =0;
	private static int lognamecounter =0;
	private static class LogWritterSingleton{
		private static final LogWritter instance = new LogWritter();
	}
	
	public static LogWritter getInstance(){

		return LogWritterSingleton.instance;
	}
	/**
	 * Method input log
	 * log also writed application console
	 * @param logText
	 * @return none
	 */
	public void logInput(String logText){
		log = log + logText+"\n";
	}
	/**
	 * Method created log File
	 * log filename is log.txt
	 * @param none
	 * @return none
	 */
	public void logFileCreate(){
		try{
			filename = "./log"+lognamecounter+".txt";
			File file = new File(filename);
			FileWriter fw = new FileWriter(file, false);
			fw.write(log);
			fw.flush();
			fw.close();
			logcounter++;
			if(logcounter == 10){
				logcounter = 0;
				lognamecounter++;
				log = "";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

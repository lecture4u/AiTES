package com.github.aites.shlocalaites.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class LogWritter {
	private static String log = "";
	private String filename = "./log.txt";
	
	private static class LogWritterSingleton{
		private static final LogWritter instance = new LogWritter();
	}
	
	public static LogWritter getInstance(){

		return LogWritterSingleton.instance;
	}
	public void logInput(String logText){
	    System.out.println(logText);
		log = log + logText+"\n";
	}
	public void logFileCreate(){
		try{
			File file = new File(filename);
			FileWriter fw = new FileWriter(file, false);
			fw.write(log);
			fw.flush();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

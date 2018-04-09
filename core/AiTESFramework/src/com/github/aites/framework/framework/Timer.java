package com.github.aites.framework.framework;



public class Timer {
	private static String systemTime = "";
	
	private String year;
	private String month;
	private String day;
	private static String time;
	private static class TimerSingleton{
		private static final Timer instance = new Timer();
	}
	
	public static Timer getInstance(){

		return TimerSingleton.instance;
	}
	
	public Timer(){
		
	}
	public void setSystemTime(String systemTime){
		this.systemTime = systemTime;
		time = parseTime(systemTime);
		
	}
	public boolean checkTime(){
		return true;
	}
	public String parseTime(String whloeTime){
		String[] parsedTime = whloeTime.split(" ");
		String[] parsedYMD = parsedTime[0].split("[ .,?!]+");
		year = parsedYMD[0];
	    month = parsedYMD[1];
		day = parsedYMD[2];
		String[] paserdMinit = parsedTime[1].split(":");
		String time = paserdMinit[0];
	
		return time;
	}

	private void printTime(){
		System.out.println("processed Time:"+time);
		System.out.println("processed System Time:"+systemTime);
	}
	public String getCurrentTime(){
		return time;
	}
	public void processedTime(){
		System.out.println("*******processed System time*******");
		int timeData = Integer.parseInt(time);
		int dayData = Integer.parseInt(day);
		timeData = timeData +1;
		if(timeData > 23){
			timeData = 0;
			dayData +=1;
		}
		time = timeData+"";
		systemTime = year+"."+month+"."+day+" "+time+":00"; 

		printTime();
	}
	public String getWholeTime(){
		return systemTime;
	}
	public String getAbbTime(){
		String abbTime= systemTime.replaceAll("[.: ]","");
		return abbTime;
	}
}

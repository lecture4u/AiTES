package com.github.aites.framework.framework;

import java.util.Calendar;

/**
 * Class for control AiTES Time step.
 * singleton pattern.
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public class Timer {
	private static String systemTime = "";
	
	private String year;
	private String month;
	private String day;
	private static String time;
	
	private String intervalType = Calendar.HOUR_OF_DAY+"";
	private int intervalValue =1;
    Calendar cal = Calendar.getInstance();
	private static class TimerSingleton{
		private static final Timer instance = new Timer();
	}
	
	public static Timer getInstance(){

		return TimerSingleton.instance;
	}
	
	public Timer(){
		
	}
	public void setProcessInterval(String type, int value){
		if(type.equals("Month")){
			intervalType = Calendar.MONTH+"";
		}
		else if(type.equals("Day")){
			intervalType = Calendar.DATE+"";
		}
		else if(type.equals("Hour")){
			intervalType = Calendar.HOUR_OF_DAY+"";
        }
		intervalValue = value;
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
	
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time));
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		return time;
	}

	private void printTime(){
		System.out.println("processed Time:"+time);
		System.out.println("processed System Time:"+systemTime);
	}
	public String getCurrentTime(){
		return time;
	}

/**
 * Method for processed time step 
 * steped by 1 hour, need method for judged time step
 * @param none
 * @return none
 */
	
	public void processedTime(){
		System.out.println("*******processed System time Using Calendar*******");
		int timeData = cal.get(Calendar.HOUR_OF_DAY);
		if(Integer.parseInt(intervalType) == Calendar.HOUR_OF_DAY){
			cal.add(Calendar.HOUR_OF_DAY, +intervalValue);
		}
		else if(Integer.parseInt(intervalType) == Calendar.MONTH){
			cal.add(Calendar.MONTH, +intervalValue);
		}
		else if(Integer.parseInt(intervalType) == Calendar.DATE){
			cal.add(Calendar.DATE, +intervalValue);
		}
		time = cal.get(Calendar.HOUR_OF_DAY)+"";
		year= cal.get(Calendar.YEAR)+"";
		month= cal.get(Calendar.MONTH)+1+"";
		day= cal.get(Calendar.DATE)+"";
		systemTime = year+"."+month+"."+day+" "+time+":00"; 
	}
	public String getWholeTime(){
		return systemTime;
	}
	public String getAbbTime(){
		String abbTime= systemTime.replaceAll("[.: ]","");
		return abbTime;
	}
	public String makeCalendarAddTime(String time){
		int currentTime = cal.get(Calendar.HOUR_OF_DAY);
		currentTime = currentTime + Integer.parseInt(time);
		if(currentTime > 23){
			currentTime = currentTime - 24;
		}
		
		String calendarAddedTime = year+"."+month+"."+day+" "+currentTime+":00";
		return calendarAddedTime;
	}
}

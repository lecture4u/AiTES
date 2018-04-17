package com.github.aites.framework.monitor;

import java.util.ArrayList;
/**
 * Class for intancelized iot environment data.
 * include collect json data, date, device name;
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public class EnvData {
	private String collectDate;
	private ArrayList<String> deviceData;
	private ArrayList<String> deviceName;
	public EnvData(String collectData){
		this.collectDate = collectData;
		deviceData = new ArrayList<String>();
		deviceName = new ArrayList<String>();
	}
	public void addDeviceData(String data){
		deviceData.add(data);
	}
	public void addDeviceName(String name){
		deviceName.add(name);
	}
	
	public ArrayList<String> getDeviceData(){
		return deviceData;
	}
	public ArrayList<String> getDeviceNmae(){
		return deviceName;
	}
	public String getCollectDate(){
		return collectDate;
	}
	
}

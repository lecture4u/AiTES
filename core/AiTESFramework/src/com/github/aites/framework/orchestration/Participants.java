package com.github.aites.framework.orchestration;

import java.util.ArrayList;
import java.util.Iterator;

public class Participants {
	private static ArrayList<Device> deviceList = new ArrayList<Device>();
	private ArrayList<String> deviceSoftwareActionList;
	
	private static class ParticipantsSingleton{
		private static final Participants instance = new Participants();
	}
	
	public static Participants getInstance(){
		return ParticipantsSingleton.instance;
	}
	
	public void addDevice(Device device){
		deviceList.add(device);
	}
	public void setSoftwareActionList(ArrayList<String> deviceSoftwareActionList){
		this.deviceSoftwareActionList = deviceSoftwareActionList;
	}
	public ArrayList<String> getSoftwareActionList(){
		return deviceSoftwareActionList;
	}
	public void setDeviceActionLevel(String deviceName, int actionLevel){
		for(Iterator<Device> it = deviceList.iterator(); it.hasNext();)
		{
			Device value = it.next();
			
			if(value.getDeviceName().equals(deviceName))
			{
				value.setSoftwareAction(deviceSoftwareActionList.get(actionLevel));
			}
		}
		
	}
	public String getDeviceAction(String deviceName){
		for(Iterator<Device> it = deviceList.iterator(); it.hasNext();)
		{
			Device value = it.next();
			
			if(value.getDeviceName().equals(deviceName))
			{
				return value.getSoftwareAction();
			}
		}
		return null;
	}
	public int getDeviceActionLevel(String deviceName){
		for(Iterator<Device> it = deviceList.iterator(); it.hasNext();)
		{
			Device value = it.next();
			
			if(value.getDeviceName().equals(deviceName))
			{
				return deviceSoftwareActionList.indexOf(value.getSoftwareAction());
			}
		}
		return 99;
	}
	public void deleteDevice(String deviceName){
		for(Iterator<Device> it = deviceList.iterator(); it.hasNext();)
		{
			Device value = it.next();
			
			if(value.getDeviceName().equals(deviceName))
			{
				it.remove();
			}
		}
	}
	public String printParticipantsList(){
		String deviceInfo="";
		for(Device d:deviceList){
			deviceInfo = deviceInfo + d.printDevice()+"\n";
		}
		return deviceInfo;
	}
	public Device getDeviceFromName(String deviceName){
		for(Iterator<Device> it = deviceList.iterator(); it.hasNext();)
		{
			Device value = it.next();
			
			if(value.getDeviceName().equals(deviceName))
			{
				return value;
			}
		}
		return null;
	}
	public Device getDeviceFromStatus(String status){
		for(Iterator<Device> it = deviceList.iterator(); it.hasNext();)
		{
			Device value = it.next();
			
			if(value.getDeviceName().equals(status))
			{
				return value;
			}
		}
		return null;
	}
}

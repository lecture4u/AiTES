package com.github.aites.framework.orchestration;

import java.util.ArrayList;
import java.util.Iterator;

public class Participants {
	public static ArrayList<Device> deviceList = new ArrayList<Device>();
	
	
	private static class ParticipantsSingleton{
		private static final Participants instance = new Participants();
	}
	
	public static Participants getInstance(){
		return ParticipantsSingleton.instance;
	}
	
	public void addDevice(Device device){
		deviceList.add(device);
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
	public void printParticipantsList(){
		for(Device d:deviceList){
			d.printDevice();
		}
	}
	public Device getDeiceFromName(String deviceName){
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
	public Device getDeiceFromStatus(String status){
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

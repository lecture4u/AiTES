package com.github.aites.device;

public class Device {
	private String deviceName;
	public Device(String deviceName){
		this.deviceName = deviceName;
	}
	public void printDevice(){
		System.out.println(deviceName);
	}
	public String getDeviceName(){
		return deviceName;
	}
}

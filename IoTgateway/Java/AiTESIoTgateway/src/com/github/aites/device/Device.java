package com.github.aites.device;

public class Device {
	private String deviceName;
	private String status;
	private String address;
	private String modelCode;
	private String actionLevel;
	public Device(String deviceName, String status, String address, String modelCode){
		this.deviceName = deviceName;
		this.status = status;
		this.address = address;
		this.modelCode = modelCode;
	}
	public void printDevice(){
		System.out.println("Device:"+deviceName+","+status+","+address+","+modelCode);
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getDeviceName(){
		return deviceName;
	}
	public String getStatus(){
		return status;
	}
	public String getAddress(){
		return address;
	}
	public String getModelCode(){
		return modelCode;
	}
	public void setActionLevel(String actionLevel){
		this.actionLevel = actionLevel;
	}
	public String getActionLevel(){
		return actionLevel;
	}
}

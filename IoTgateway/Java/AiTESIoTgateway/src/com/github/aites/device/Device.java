package com.github.aites.device;

public class Device {
	private String deviceName;
	private String status;
	private String address;
	private String modelCode;
	private String actionLevel;
	private String activity;
	private String stanby;
	private String ready;
	private String powersaving;
	public Device(String deviceName, String status, String address, String modelCode, String activity, String stanby, String ready, String powersaving){
		this.deviceName = deviceName;
		this.status = status;
		this.address = address;
		this.modelCode = modelCode;
		this.activity = activity;
		this.stanby = stanby;
		this.ready = ready;
		this.powersaving = powersaving;
	}
	public Device() {
		
	}
	public void printDeviceInfo(){
	    System.out.println("Print Device Information");
	    System.out.println("Device Name: "+deviceName);
	    System.out.println("Connection Status: "+status);
	    System.out.println("Current Action Mode: "+actionLevel);
	    System.out.println("Device IP Address:" +address);
	    System.out.println("Model Number: "+modelCode);
	    System.out.println("Activity Power Consumtion: "+activity);
	    System.out.println("Stanby Power Consumtion: "+stanby);
	    System.out.println("Ready Power Consumtion: "+ready);
	    System.out.println("Powersaving Power Consumtion: "+powersaving);
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
	public void setStanby(String stanby){
		this.stanby = stanby;
	}
	public String getStanby(){
		return stanby;
	}
	
	public void setActivity(String activity){
		this.activity = activity;
	}
	public String getActivity(){
		return activity;
	}
	
	public void setReady(String ready){
		this.ready = ready;
	}
	public String getReady(){
		return ready;
	}
	
	public void setPowersaving(String powersaving){
		this.powersaving = powersaving;
	}
	public String getPowersaving(){
		return powersaving;
	}
}

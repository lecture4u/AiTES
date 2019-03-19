package com.github.aites.iotgateway;

public class Scheduler {
    private String arrivalTime;
    private String sensorName;
    private String sensorData;
    private String currentModule;
    private String dataType;
    
    public Scheduler(String arrivalTime, String sensorName, String sensorData, String currentModule, String dataType) {
    	this.arrivalTime = arrivalTime;
    	this.sensorName = sensorName;
    	this.sensorData = sensorData;
    	this.currentModule = currentModule;
    	this.dataType = dataType;
    }
    public void printSchedule() {
    	System.out.println("Schedule Time: "+arrivalTime+" SensorName:"+sensorName+" sensorData: "+sensorData+" currentModule: "+currentModule+" dataType:"+dataType);
    }
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public String getSensorData() {
		return sensorData;
	}
	public void setSensorData(String sensorData) {
		this.sensorData = sensorData;
	}
	public String getCurrentModule() {
		return currentModule;
	}
	public void setCurrentModule(String currentModule) {
		this.currentModule = currentModule;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}

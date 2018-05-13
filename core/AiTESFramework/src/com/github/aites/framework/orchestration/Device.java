package com.github.aites.framework.orchestration;
/**
 * Class for define device information
 * Device name, status, address, model code
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public class Device {
	private String deviceName;
	private String status;
	private String address;
	private String modelCode;
	private String softwareAction="";
	public Device(String deviceName, String status, String address, String modelCode){
		this.deviceName = deviceName;
		this.status = status;
		this.address = address;
		this.modelCode = modelCode;
		
	}  /**
	 * Method for print device information
	 * @param  none
	 * @return none
	 */
	public String printDevice(){
		String printDeviceInfo = "Name:"+deviceName+", Status:"+status+", Address:"+address+", modelCode:"+modelCode+", SWaction:"+softwareAction;
		return printDeviceInfo;
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
	public String getSoftwareAction(){
		return softwareAction;
	}
	public void setSoftwareAction(String softwareAction){
		this.softwareAction = softwareAction;
	}
}

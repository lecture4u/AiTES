package com.github.aites.localtest.uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DeviceList {
	private StringProperty deviceName;
	private StringProperty powerConsumtion;
	private StringProperty address;
	private StringProperty currentMode;
	private StringProperty type;
	private StringProperty affilience;
	public DeviceList(String deviceName, String powerConsumtion, String address, String currentMode, String type, String affilience){
		this.deviceName = new SimpleStringProperty(deviceName);
		this.powerConsumtion = new SimpleStringProperty(powerConsumtion);
		this.address = new SimpleStringProperty(address);
		this.currentMode = new SimpleStringProperty(currentMode);
		this.type = new SimpleStringProperty(type);
		this.affilience = new SimpleStringProperty(affilience);
		
	}
	public StringProperty deviceNameProperty(){
		return deviceName;
	}
	public StringProperty powerConsumtionTimeProperty(){
		return powerConsumtion;
	}
	public StringProperty addressTimeProperty(){
		return address;
	}
	public StringProperty currentModeTimeProperty(){
		return currentMode;
	}
	public StringProperty typeProperty(){
		return type;
	}
	public StringProperty affilienceProperty(){
		return affilience;
	}
}

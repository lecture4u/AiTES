package com.github.aites.localtest.uicomponent;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IoTEnvData {
	private StringProperty arrival_Time;
	
	private DoubleProperty air_conditioner;
	private DoubleProperty tv;
	private DoubleProperty lamp;
	
	private DoubleProperty rvc;
	private DoubleProperty flowerpot;
	private DoubleProperty wm;
	
	private DoubleProperty lamp2;
	private DoubleProperty smartcook;
	private DoubleProperty cctv;
	
	private DoubleProperty mixcer;
	private DoubleProperty aiSpeaker;
	private DoubleProperty oven;
	
	public IoTEnvData(String time, ArrayList<Double> iotEnvDataList){
		arrival_Time = new SimpleStringProperty(time);
		
		this.air_conditioner = new SimpleDoubleProperty(iotEnvDataList.get(0));
		this.tv = new SimpleDoubleProperty(iotEnvDataList.get(1));
		this.lamp = new SimpleDoubleProperty(iotEnvDataList.get(2));
		
		this.rvc = new SimpleDoubleProperty(iotEnvDataList.get(3));
		this.flowerpot = new SimpleDoubleProperty(iotEnvDataList.get(4));
		this.wm = new SimpleDoubleProperty(iotEnvDataList.get(5));
		
		this.lamp2 = new SimpleDoubleProperty(iotEnvDataList.get(6));
		this.smartcook = new SimpleDoubleProperty(iotEnvDataList.get(7));
		this.cctv = new SimpleDoubleProperty(iotEnvDataList.get(8));
		
		this.mixcer = new SimpleDoubleProperty(iotEnvDataList.get(9));
		this.aiSpeaker = new SimpleDoubleProperty(iotEnvDataList.get(10));
		this.oven = new SimpleDoubleProperty(iotEnvDataList.get(11));
	}
	public String getArrivate_Time(){
		return arrival_Time.get();
	}
	public StringProperty arrivalTimeProperty(){
		return arrival_Time;
	}
	
	public DoubleProperty airconditionerProperty() {
		return air_conditioner;
	}
	public DoubleProperty tvProperty() {
		return tv;
	}
	public DoubleProperty lampProperty() {
		return lamp;
	}
	
	public DoubleProperty rvcProperty() {
		return rvc;
	}
	public DoubleProperty flowerpotProperty() {
		return flowerpot;
	}
	public DoubleProperty wmProperty() {
		return wm;
	}
	
	public DoubleProperty lamp2Property() {
		return lamp2;
	}
	public DoubleProperty smartcookProperty() {
		return smartcook;
	}
	public DoubleProperty cctvProperty() {
		return cctv;
	}
	
	public DoubleProperty mixcerProperty() {
		return mixcer;
	}
	public DoubleProperty aiSpeakerProperty() {
		return aiSpeaker;
	}
	public DoubleProperty ovenProperty() {
		return oven;
	}
}

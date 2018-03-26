package com.github.aites.iotgateway;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.aites.device.Device;
public class DeviceDataProcessor implements DataProcessor{

	JSONObject deviceObject = new JSONObject();
	JSONArray deviceArray =  new JSONArray();
	String deviceHader;
	@Override
	public void setHeader(String dataHeader){
		deviceHader = dataHeader;
	}
	@Override
	public void processData(Object data){ //1.Device Name,Status, Address, ModelCode
	 
		
		JSONObject dataInfo = new JSONObject();
		
		dataInfo.put("status", ((Device)data).getStatus());
		dataInfo.put("address", ((Device)data).getAddress());
		dataInfo.put("modelCode", ((Device)data).getModelCode());
	
		deviceArray.add(dataInfo);
	
	}
	@Override
	public String getProcessedData(){
		deviceObject.put(deviceHader, deviceArray);
		String jsonInfo = deviceObject.toJSONString();
		
		return jsonInfo;
	}
}

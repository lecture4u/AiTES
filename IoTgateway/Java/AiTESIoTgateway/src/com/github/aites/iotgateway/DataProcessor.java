package com.github.aites.iotgateway;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class DataProcessor {
	String header[];
	
   
	JSONObject deviceObject;
	JSONArray deviceArray;
	String deviceHader;
	public DataProcessor(){
		
	}
	public DataProcessor(String jsonHeader){
		 deviceObject = new JSONObject();
		 deviceArray = new JSONArray();
		 
	}
	public void setHeader(String dataHeader){
		header = dataHeader.split(",");
	}
	public void setDeviceHader(String dataHeader){
		deviceHader = dataHeader;
	}
	public String getProcessedTestData(String testData){
		JSONObject jsonObject = new JSONObject();
		JSONArray dataArray = new JSONArray();
		String processData[] = testData.split(",");
		
		for(int i=1; i<processData.length; i++){
			JSONObject dataInfo = new JSONObject();
			dataInfo.put("name", header[i]);
			dataInfo.put("data", processData[i]);
			dataArray.add(dataInfo);
		}
		
		jsonObject.put(processData[0],dataArray);
		
		String jsonInfo = jsonObject.toJSONString();
		
		return jsonInfo;
	}
	public void processDeviceData(String deviceName,String deviceData, String deviceState){
		JSONObject dataInfo = new JSONObject();
		dataInfo.put("name", deviceName);
		dataInfo.put("data", deviceData);
		dataInfo.put("state", deviceState);
	
		deviceArray.add(dataInfo);
	
	}
	public String getProcessedDeviceData(){
		deviceObject.put(deviceHader, deviceArray);
		String jsonInfo = deviceObject.toJSONString();
		
		return jsonInfo;
	}
}

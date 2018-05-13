package com.github.aites.iotgateway;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class EnvDataProcessor implements DataProcessor{
	
	JSONObject envObject = new JSONObject();
	JSONArray envArray = new JSONArray();
	String envHeader[];
	public EnvDataProcessor(){
		
	}
	
	@Override
	public void setHeader(String dataHeader) {
		// TODO Auto-generated method stub
		envHeader = dataHeader.split(",");
	}

	@Override
	public void processData(Object data) {
		 
		envObject = new JSONObject();
		String processData[] = ((String) data).split(",");
		JSONObject dataInfo = new JSONObject();
		for(int i=1; i<processData.length; i++){
			
	    	dataInfo.put(envHeader[i], processData[i]);
	    	
		}
		envArray.add(dataInfo);
		envObject.put(processData[0],envArray);			
	}

	@Override
	public String getProcessedData() {
		String jsonString = envObject.toJSONString();
		
		envArray = new JSONArray();
		return jsonString;
	}

}

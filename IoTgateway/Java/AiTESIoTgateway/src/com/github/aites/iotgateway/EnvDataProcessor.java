package com.github.aites.iotgateway;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class EnvDataProcessor implements DataProcessor{
	
	JSONObject envObject;
	JSONArray envArray;
	String envHader[];
	public EnvDataProcessor(){
		
	}
	
	@Override
	public void setHeader(String dataHeader) {
		// TODO Auto-generated method stub
		envHader = dataHeader.split(",");
	}

	@Override
	public void processData(Object data) {
		envObject = new JSONObject();
		envArray = new JSONArray();
		
		String processData[] = ((String) data).split(",");
		
		for(int i=1; i<processData.length; i++){
			JSONObject dataInfo = new JSONObject();
			dataInfo.put("name", envHader[i]);
			dataInfo.put("data", processData[i]);
			envArray.add(dataInfo);
		}
		
		envObject.put(processData[0],envArray);

	}

	@Override
	public String getProcessedData() {
		String jsonString = envObject.toJSONString();
		return jsonString;
	}

}

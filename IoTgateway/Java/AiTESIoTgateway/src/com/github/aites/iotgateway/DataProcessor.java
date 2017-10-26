package com.github.aites.iotgateway;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class DataProcessor {
	String header[];
	
   
	public DataProcessor(){
		
	}
	public void setHeader(String dataHeader){
		header = dataHeader.split(",");
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
}

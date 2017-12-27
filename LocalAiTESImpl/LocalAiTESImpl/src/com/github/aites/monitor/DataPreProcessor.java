package com.github.aites.monitor;

import Monitor.PreProcessor;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class DataPreProcessor implements PreProcessor{
	private EnvData envData;
	@Override
	public void dataPreprocess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getProcessedData() {
		// TODO Auto-generated method stub
		return envData;
	}

	@Override
	public void processData(Object mqttMessage) {
		String message = mqttMessage.toString();
		String date = message.substring(2, 16);
		
		System.out.println(date);
		envData = new EnvData(date);
		try{
			JSONParser parser = new JSONParser();
			Object jsonObj = parser.parse(message);
			JSONObject jsonObject = (JSONObject)jsonObj; 
			
			System.out.println("------------Parsed Json Data---------------");
		    JSONArray iotenv = (JSONArray)jsonObject.get(date);
			/*for(int i=0; i<iotenv.size(); i++){
			    System.out.println("The " + i + " element of the array: "+iotenv.get(i));
		
			}*/
			Iterator iter = iotenv.iterator();
			while(iter.hasNext()){
				JSONObject innerObj = (JSONObject) iter.next();
				System.out.println("data:"+innerObj.get("data"));
				envData.addDeviceData(innerObj.get("data").toString());
				System.out.println("name:"+innerObj.get("name"));
				envData.addDeviceName(innerObj.get("name").toString());

			}

			
			System.out.println("-------------------------------------------");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}

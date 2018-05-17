package com.github.aites.shlocalaites.monitor;



import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.monitor.EnvData;
import com.github.aites.framework.monitor.PreProcessor;
import com.github.aites.framework.orchestration.Participants;

public class DataPreProcessor implements PreProcessor{
	private EnvData envData;
	private static int dayCounter = 0;
    private String position;
    private String temperture;
    private String date;
    
	LogWritter log = LogWritter.getInstance();
	Participants participants = Participants.getInstance();
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
		
		 date =""; 
	    
	    if(dayCounter<=9){
	    	date = message.substring(2, 16);
	    }
	    else{
	    	date = message.substring(2, 17);
	    }
	    log.logInput("*****PreProcessing: Parsed Json Data about collected:"+date+"*****");
		envData = new EnvData(date);
		
		try{
			JSONParser parser = new JSONParser();
			Object jsonObj = parser.parse(message);
			JSONObject jsonObject = (JSONObject)jsonObj; 
			
			
			
		    JSONArray iotenv = (JSONArray)jsonObject.get(date);
			
			Iterator iter = iotenv.iterator();
			ArrayList<String> actionLevel = participants.getSoftwareActionList();
			while(iter.hasNext()){
               
				JSONObject innerObj = (JSONObject) iter.next();
				
				log.logInput("SensorName:"+innerObj.get("sensorName")+", SensorData:"+innerObj.get("sensorData")+", CurrentModule:"+innerObj.get("currentModule")+", DataType:"+innerObj.get("dataType"));
				if(innerObj.get("dataType").toString().equals("GPS")){
					position = innerObj.get("sensorData").toString();
				}
				else if(innerObj.get("dataType").toString().equals("Tem")){
					temperture = innerObj.get("sensorData").toString();
				}
				else{
					envData.addDeviceData(innerObj.get("sensorData").toString());
					envData.addDeviceName(innerObj.get("sensorName").toString());
					participants.setDeviceActionLevel(innerObj.get("sensorName").toString(), actionLevel.indexOf(innerObj.get("currentModule").toString()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		dayCounter+=1;
		if(dayCounter > 23){
			dayCounter = 0;
		}
	}
	public String getPosition(){
		 return position;
		
	}
	public String getTemperture(){
	     return temperture;
	}

	
	

}

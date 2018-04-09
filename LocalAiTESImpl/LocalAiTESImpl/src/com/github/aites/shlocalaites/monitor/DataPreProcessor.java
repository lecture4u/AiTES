package com.github.aites.shlocalaites.monitor;



import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.aites.framework.log.LogWritter;
import com.github.aites.framework.monitor.EnvData;
import com.github.aites.framework.monitor.PreProcessor;

public class DataPreProcessor implements PreProcessor{
	private EnvData envData;
	private static int dayCounter = 0;
    private String position;
    private String temperture;
    private String date;
    
	LogWritter log = LogWritter.getInstance();
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
		System.out.println(date);
		envData = new EnvData(date);
		try{
			JSONParser parser = new JSONParser();
			Object jsonObj = parser.parse(message);
			JSONObject jsonObject = (JSONObject)jsonObj; 
			
			
			log.logInput("*****PreProcessing: Parsed Json Data*****");
		    JSONArray iotenv = (JSONArray)jsonObject.get(date);
			
			Iterator iter = iotenv.iterator();
			while(iter.hasNext()){
               
				JSONObject innerObj = (JSONObject) iter.next();
				
				log.logInput("data:"+innerObj.get("data")+", name:"+innerObj.get("name"));
				if(innerObj.get("name").toString().equals("Position")){
					position = innerObj.get("data").toString();
				}
				else if(innerObj.get("name").toString().equals("Temperture")){
					temperture = innerObj.get("data").toString();
				}
				else{
					envData.addDeviceData(innerObj.get("data").toString());
					envData.addDeviceName(innerObj.get("name").toString());
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

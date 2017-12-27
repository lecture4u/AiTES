package com.github.aites.mqttclient;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import com.github.aites.iotgateway.DataProcessor;
import com.opencsv.CSVReader;

import rest.JavaRestTemplate;

public class BlockChainClient {
	public BlockChainClient(){
		
	}
	public void inputTestData(){
		JavaRestTemplate rest = new JavaRestTemplate();
		String arrive_time = "";
		rest.enrollId();
		
		//init
		rest.deploy();
		
		//Data Input
		int restIndex= 0;
	
		
		
		System.out.println("publish excel data");
		boolean headerC = false;
		
		
		try {
			 CSVReader reader = new CSVReader(new FileReader("./smartHome_data.csv"));
			 String[] testData;
			 String preData="";
			
			 String csvFileName = "exresult.csv";
			 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFileName),"MS949"));
			 long start = System.currentTimeMillis();
			 while ((testData = reader.readNext()) != null) {
		    	 if(!headerC){
					 headerC=true;
				 }
		    	 else{
		    		 arrive_time = testData[0];
		    		 System.out.println(arrive_time);

		    		  for(int i=0; i<testData.length; i++){
	            		  preData = preData+testData[i]+",";
	            		  
	             }
		       preData = preData.substring(0,preData.length()-1);
		      
	           rest.put(arrive_time, preData);
	           long end = System.currentTimeMillis();
	           double time = (end - start)/(double)1000;
	           System.out.println(time);
	           writer.write(time+"\r\n");
	           preData ="";
	           
	    	
	    	   restIndex++;	  
	    	   
			   }
		    	 }
		        writer.close(); 				
			 
	   } catch (Exception e) {
				
			e.printStackTrace();
	  }
			
			
	}
}

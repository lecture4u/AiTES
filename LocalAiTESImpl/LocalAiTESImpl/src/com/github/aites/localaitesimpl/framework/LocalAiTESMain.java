package com.github.aites.localaitesimpl.framework;

import com.github.aites.rest.JavaRestTemplate;

public class LocalAiTESMain {

	public static void main(String[] args) {
		
		
	/*	JavaRestTemplate rest = new JavaRestTemplate();
		String arrive_time = "";
		rest.enrollId();
		int key =0;
		//init
		rest.deploy();
		for(int i=0; i<=23; i++){
			String keyvalue = key+":00";
			keyvalue = "2017.7.23 "+keyvalue;
			String query1 = rest.query(keyvalue);
			System.out.println(query1);
			key++;
		}*/
		
		LocalAiTES localAiTES = new LocalAiTES();
		localAiTES.runAiTES();

	}

}

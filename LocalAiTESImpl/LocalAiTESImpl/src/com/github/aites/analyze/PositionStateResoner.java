package com.github.aites.analyze;

import java.util.ArrayList;
import java.util.Collections;

import com.github.aites.log.LogWritter;

public class PositionStateResoner {
	ArrayList<String> latitudeList = new ArrayList<String>();
	ArrayList<String> rongitudeList = new ArrayList<String>();
	LogWritter log = LogWritter.getInstance();
	String positionState;
 	private void processedPosition(String position){
		String[] positionParser = position.split(":");
		String delims = "[ .,?!]+";
		String[] latitudeParser = positionParser[0].split(delims);
		String[] rongitudeParser = positionParser[1].split(delims);
		
		Collections.addAll(latitudeList, latitudeParser);
		Collections.addAll(rongitudeList, rongitudeParser);
		
		
		
		double thirdLa = Double.parseDouble(latitudeList.get(2));
		double thirdRo = Double.parseDouble(rongitudeList.get(2));
		
		System.out.println(thirdLa + "," +thirdRo);
		//여기 부분이 온톨로지 리조닝이여야함.
		if((thirdLa >= 24.0 && thirdLa<=26) && (thirdRo>=42 && thirdRo <=44)){
			positionState = "inHome";
		}
		else if(((thirdLa >=22.0 && thirdLa <24.0) || (thirdLa>26 && thirdLa<=28)) && ((thirdRo>=40 && thirdRo<42) || (thirdRo>46 && thirdRo <44))){
			positionState = "nearHome";
		}
		else{
			positionState = "outHome";
		}
		
	}
	
	public String stateResoning(String position){
		log.logInput("*****Start Position State Reasoning*****");
		processedPosition(position);
		log.logInput("resoning user position state:"+positionState);
		return positionState;
	}
}

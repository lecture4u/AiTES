package com.github.aites.monitor;

public class MonitorHRAlgorithm {
	EnvData envData;
	public MonitorHRAlgorithm(EnvData envData){
		this.envData = envData;
	}
	public String getAllEnvData(){
		String envResult = "";
		for(int di=0; di<envData.getDeviceData().size(); di++){
			if(di==0){
				envResult = envResult+envData.getDeviceData().get(di);
			}
			else{
				envResult = envResult+","+envData.getDeviceData().get(di);
			}
			
		}
		
		return envResult;
	}
	public String envDataHRAlgorithm(){
		System.out.println("Smart Home Environment Data Heuristic funtion");
		Double hresult =0.0;
		for(int di=0; di<envData.getDeviceData().size(); di++){
			envData.getDeviceNmae().get(di);
			hresult += Double.parseDouble(envData.getDeviceData().get(di));
			
		}
		System.out.println("Power consumtion:"+hresult);
		/*-------온톨로지 추론으로 수정해야함.-----*/
		if(hresult < 600){
			return "under";
		}
		else if(hresult>2000){
			return "over";
		}
		return "normal";
	}
}

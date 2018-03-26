package com.github.aites.iotgateway;


public interface DataProcessor {
	public void setHeader(String dataHeader);
	public void processData(Object data);
	public String getProcessedData();
}

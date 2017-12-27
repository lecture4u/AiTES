package com.github.aitest.model;

import java.util.HashMap;
import java.util.Map;

//쿼리 결과 파싱하기(json data -> class data)
public class Data {

	private String jsonrpc;
	private Map<String, String> result = new HashMap<String, String>();
	private int id;
	public Data(String jsonrpc, int id){
		this.id = id;
	
		this.jsonrpc = jsonrpc;
	}
}
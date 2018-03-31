package com.github.aites.shlocalaites.rest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JavaRestTemplate {
	
	private static final String address = "http://220.149.235.74:7050";
	private static final String Username = "admin";
	private static final String Chaincodename="ChaincodeLogSmartContract";

	//Rest Get method
	public String Get(URL obj) {
		BufferedReader in = null;
		String line = "";
		String result = "";
		
		try {
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				result = line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	//Rest Post method
	public String Post(URL obj, String json) {
		
		InputStream is = null;
		String result = "";
		
		try{
			HttpURLConnection con = (HttpURLConnection)obj.openConnection();
			
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-type", "application/json");
			
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			
			java.io.OutputStream os = con.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.flush();
			
			try{
				is = con.getInputStream();
				
				if(is != null)
					result = getStringFromInputStream(is);
				else
					result = "Did`t work!";
				
			} catch(IOException e){
				e.printStackTrace();
			} finally{
				con.disconnect();
			}
			System.out.println(result);
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return result;

	}
	
	//Convert InputStream --> String 
	private static String getStringFromInputStream(InputStream is) {
		
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
	
	
	 ////////////////////
    ////[GET Method]////
   ////////////////////
	
	//Get Chain Height, Current/Previous chainHash
	public String getChainData(){
		URL uri = null;
		try {
			uri = new URL(address + "/chain");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String result = Get(uri);
		return result;
	}
	
	//Get Chain Information(block num)
	public String getBlockData(String blockNum){
		URL uri = null;
		try {
			uri = new URL(address + "/chain/blocks/" + blockNum);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String result = Get(uri);
		return result;
	}
	
	//Transaction Information(in chainInformation)
	public String getTranData(String txID){
		URL uri = null;
		try {
			uri = new URL(address + "/transactions/" + txID);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String result = Get(uri);
		return result;
	}
	
	//Peer Information
	public String getPeerData(){
		URL uri = null;
		try {
			uri = new URL(address + "/network/peers");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String result = Get(uri);
		return result;
	}
	
	
    /////////////////////
   ////[POST Method]////
  /////////////////////	
	
	//EnrollID
	public void enrollId(){
		URL uri = null;
		try {
			uri = new URL(address + "/registrar");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String jsonData1 = ("{\n" +
	            "    \"enrollId\": \"admin\",\n" +
	            "    \"enrollSecret\" : \"Xurw3yU9zI0l\"\n" +
	            "} ");
		
		String result = Post(uri, jsonData1);
	}
	
	//Deploy
	public void deploy(){
		URL uri = null;
		try {
			uri = new URL(address + "/chaincode");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String jsonData1 = ("{\n" +
	            "    \"jsonrpc\": \"2.0\",\n" +
	            "		\"method\": \"deploy\",\n" +
	            "		\"params\": {\n" +
	            "       	\"type\": 1,\n" +
	            "			\"chaincodeID\":{\n" +
	            "           	\"name\": \"" + Chaincodename +"\"\n" +
	            "			},\n"+		
	            "           \"CtorMsg\": {\n" +
	            "           	\"args\": [\"init\"]\n" +
	            "           },\n" +
	            "           \"secureContext\": \"" + Username +"\"\n" +
	            "       },\n" +
	            "    \"id\": 1\n"+
	            "}");
		
		String result = Post(uri, jsonData1);
	}
	
	//put
	public void put(String key, String value){
		URL uri = null;
		try {
			uri = new URL(address + "/chaincode");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String jsonData1 = ("{\n" +
	            "    \"jsonrpc\": \"2.0\",\n" +
	            "		\"method\": \"invoke\",\n" +
	            "		\"params\": {\n" +
	            "       	\"type\": 1,\n" +
	            "			\"chaincodeID\":{\n" +
	            "           	\"name\": \"" + Chaincodename +"\"\n" +
	            "			},\n"+		
	            "           \"CtorMsg\": {\n" +
	            "           	\"args\": [\"put\",\""+ key +"\", \""+ value +"\"]\n" +
	            "           },\n" +
	            "           \"secureContext\": \"" + Username +"\"\n" +
	            "       },\n" +
	            "    \"id\": 3\n"+
	            "}");
		String result = Post(uri, jsonData1);
	}
	
	//query
	public String query(String key){
		URL uri = null;
		try {
			uri = new URL(address + "/chaincode");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String jsonData1 = ("{\n" +
	            "    \"jsonrpc\": \"2.0\",\n" +
	            "		\"method\": \"query\",\n" +
	            "		\"params\": {\n" +
	            "       	\"type\": 1,\n" +
	            "			\"chaincodeID\":{\n" +
	            "           	\"name\": \"" + Chaincodename +"\"\n" +
	            "			},\n"+		
	            "           \"CtorMsg\": {\n" +
	            "           	\"args\": [\"query\", \""+ key +"\"]\n" +
	            "           },\n" +  
	            "            \"secureContext\": \"" + Username +"\"\n" +
	            "       },\n" +
	            "    \"id\": 5\n"+
	            "}");
		String result = Post(uri, jsonData1);
		
		return result;
	}
	


}

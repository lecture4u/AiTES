package com.github.aitest.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BlockData {
	public static class Transaction{
		public String type;
		public String chaincodeID;
		public String payload;
		public String txid;
		public Map<String, String> timestamp = new HashMap<String, String>();
	}
	
	public static class nonhashData{
		
		public Map<String, String> localLedgerCommitTimestamp = new HashMap<String, String>();
		//chaincodeEvents를 사용하지 않는다면 이 변수 사용하지 말기..
		//public List<String> chaincodeEvents = new ArrayList<String>();
	}
	
	//배열 형태의 json인데, 배열 안의 인자들이 List형식일 때 이렇게 사용한다.
	public List<Transaction> transactions = new ArrayList<Transaction>();
	
	public String stateHash;
	
	public String previousBlockHash;
	
	public nonhashData nonHashData = new nonhashData();
	
	//localLedgerCommitTimestamp 출력해보기
	public void searchNonHashData(){
		Set<Entry<String, String>>set = nonHashData.localLedgerCommitTimestamp.entrySet();
		Iterator<Entry<String, String>> it = set.iterator();
		
		while(it.hasNext()){
			Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
			System.out.println("key : " + e.getKey() + " value : " + e.getValue());
		}
	}
	
	//Transaction 정보 출력해보기
	public void searchTransaction(){
		System.out.println(transactions.get(0).type);
		System.out.println(transactions.get(0).chaincodeID);
		System.out.println(transactions.get(0).payload);
		System.out.println(transactions.get(0).txid);
	}
}

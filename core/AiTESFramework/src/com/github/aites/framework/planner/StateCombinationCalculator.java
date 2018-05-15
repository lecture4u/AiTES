package com.github.aites.framework.planner;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class for calculate passible state set combination
 * using stack, reatable.
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public class StateCombinationCalculator {
	private String[] stateSet;
	private Stack<String> combinatStack;
	ArrayList<String> allOccurStateSet = new ArrayList<String>();
	public StateCombinationCalculator(String stateSet){
		this.stateSet = stateSet.split(",");
		combinatStack = new Stack<String>();
		for(int i=0; i<this.stateSet.length; i++){
			System.out.println("stateSet's "+i+":"+this.stateSet[i]);
		}
		
	}
	private void showStack(){
		String totalSet ="";
		for(int i=0; i<combinatStack.size(); i++){
			
			if(i == combinatStack.size()-1){
				totalSet = totalSet + combinatStack.get(i);
			}
			else{
				totalSet = totalSet + combinatStack.get(i)+",";
			}
		}
		System.out.println("ts:"+totalSet);
		allOccurStateSet.add(totalSet);
	}
	public int getStateSetLength(){
		int stateSetLength = stateSet.length;
		
		return stateSetLength;
	}
	public void CalstateCombinateion(int n, int r, int index){
		//n: total state
		//r: combine
	   //index, forcused array
		if(r==0){
			showStack();
			return;
		}
		else if(n==r){
			for(int i=0; i<n; i++) combinatStack.add(stateSet[index+i]);
			showStack();
			for(int i=0; i<n; i++)combinatStack.pop();
		}
		else{
			combinatStack.add(stateSet[index]);
			CalstateCombinateion(n-1,r-1,index+1);
			
			combinatStack.pop();
			CalstateCombinateion(n-1, r, index+1);
		}
	}
	public ArrayList<String> getAllOccurStateSet(){
		return allOccurStateSet;
	}
}

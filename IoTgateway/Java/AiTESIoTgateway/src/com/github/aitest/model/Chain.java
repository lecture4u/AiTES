package com.github.aitest.model;

public class Chain {
	private String height;
	private String currentBlockHash;
	private String previousBlockHash;
	public Chain(String height, String currentBlockHash, String previousBlockHash){
		this.height = height;
		this.currentBlockHash = currentBlockHash;
		this.previousBlockHash = previousBlockHash;
	}
}

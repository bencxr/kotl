package com.example.lovelights;

public class VeraSwitch {
	
	private boolean value = false;
	private String name = "Switch";
	
	public VeraSwitch(String name, int value) {
		
		this.value = (value >= 1);
		this.name = name;
	}
	
	public VeraSwitch(String name, boolean value) {
		
		this.value = value;
		this.name = name;
	}

	public boolean getValue() {
		
		return this.value;
	}

	public String getName() {

		return this.name + Math.random();
	}
}

package com.example.lovelights;

public class VeraSwitch {
	
	private int id;
	private String name = "Switch";
	private String room;
	private int state;
	
	public VeraSwitch(String name, int state) {
		
		this.state = state;
		this.name = name;
	}
	
	public int getState() {
		
		return this.state;
	}

	public String getName() {

		return this.name;
	}
	
	public String toString() {
		
		return "VeraSwitch id: " + this.id + ", name: " + this.name + ", state: " + this.state; 
	}
}

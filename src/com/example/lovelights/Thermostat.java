package com.example.lovelights;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.widget.Switch;

public class Thermostat {
	
	public static final String BASE_URL = "http://home.isidorechan.com/nests";

	protected int id;
	protected int minTemp;
	protected int maxTemp;
	protected int currentTemperature;
	protected String name = "Switch";
	protected String room;
	protected int state;
	
	private boolean stateLocked = false;
	
	public Thermostat(String name, int state) {
		
		this.state = state;
		this.name = name;
	}
	
	public int getId() {
		
		return this.id;
	}
	
	public int getState() {
		
		return this.state;
	}

	public String getName() {

		return this.name;
	}

	public String getRoom() {

		return this.room;
	}
	
	public int getMaxTemp() {
		
		return this.maxTemp;
	}
	
	public int getMinTemp() {
		
		return this.minTemp;
	}
	
	public int getCurrentTemperature() {
		
		return this.currentTemperature;
	}
	
	public boolean isLocked() {
		
		return this.stateLocked;
	}
	
	public String toString() {
		
		return "Thermostat id: " + this.id + ", name: " + this.name + ", state: " + this.state + 
				" temp: " + this.currentTemperature + " (" + this.minTemp + " - " + this.maxTemp + ")"; 
	}
}

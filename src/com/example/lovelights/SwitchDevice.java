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

public class SwitchDevice {
	
	public static final String BASE_SWITCH_URL = "http://home.isidorechan.com/lights";

	private int id;
	private String name = "Switch";
	private String room;
	protected int state;
	
	private boolean stateLocked = false;
	
	public SwitchDevice(String name, int state) {
		
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
	
	public void setRoom(String room) {
		
		this.room = room;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public String toString() {
		
		return "VeraSwitch id: " + this.id + ", name: " + this.name + ", state: " + this.state; 
	}
	
	public void setState(int state) {
		
		if (!stateLocked) {
			this.state = state;
		}
	}
	
	public void setState(final Switch switchButton, final int requestedState) {
		
		if (stateLocked) {
			
			return;
		} else {

			this.state = requestedState;
		}
		
		new Thread(new Runnable() {
			public void run() {

				stateLocked = true;
				MainActivity.getInstance().runOnUiThread(new Runnable() {
		    	     @Override
		    	     public void run() {

						switchButton.setEnabled(false);
						switchButton.setFocusable(false);
		    	    	switchButton.setClickable(false);
		    	    }
		    	});
				
					try {
						for (int i=0; i<3; i++) {
							String lightURL = BASE_SWITCH_URL + "/" + id;
							HttpClient client = new DefaultHttpClient();
							HttpPut put = new HttpPut(lightURL);
							put.addHeader("Content-Type", "application/json");
							put.addHeader("Accept", "application/json");
							put.setEntity(new StringEntity("{ \"state\": " + Integer.toString(requestedState) +" }"));
							HttpResponse response = client.execute(put);
							
						    // 200 type response.
						    if (response.getStatusLine().getStatusCode() >= HttpStatus.SC_OK &&
						    response.getStatusLine().getStatusCode() < HttpStatus.SC_MULTIPLE_CHOICES)
						    {
						    	InputStream content = response.getEntity().getContent();
						    	Reader reader = new InputStreamReader(content);
						    	
						    	Map<String, String> map = 
						    			new Gson().fromJson(reader, new TypeToken<HashMap<String, String>>() {}.getType());
						    	if (map.containsKey("result") && map.get("result").equals("ok") && 
						    			map.containsKey("state")) {
						    		state = Integer.parseInt(map.get("state"));
						    		break;
						    	} else {
						    		System.out.println("error response" + map.toString());
						    	}
						    }
						    
						    if (i==2) {
						    	state = (requestedState + 1) % 2;
						    }
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						
						stateLocked = false;
						MainActivity.getInstance().runOnUiThread(new Runnable() {
				    	     @Override
				    	     public void run() {
	
				    	    	 switchButton.setFocusable(true);
				    	    	 switchButton.setClickable(true);
				    	    	 switchButton.setEnabled(true);
				    	    }
				    	});
					}
			}}).start();
	}
}

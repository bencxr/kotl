package com.example.lovelights;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ThermostatManager extends Thread {

	MainActivity activity;
	TextView setTemperatureLabel;
	RangeSeekBar<Integer> seekBar;
	Integer minTemp = 68;
	Integer maxTemp = 82;
	
	public ThermostatManager(final MainActivity activity) {
		
		this.activity = activity;

        TextView minTempTextView = (TextView)activity.findViewById(R.id.minTempTextView);
        minTempTextView.setText(minTemp.toString() + "°c");
        
        TextView maxTempTextView = (TextView)activity.findViewById(R.id.maxTempTextView);
        maxTempTextView.setText(maxTemp.toString() + "°c");
        
        setTemperatureLabel = (TextView)activity.findViewById(R.id.setTemperatureLabel);
        
        // create RangeSeekBar as Integer range between 20 and 75
        seekBar = new RangeSeekBar<Integer>(minTemp, maxTemp, activity);
        
        // add RangeSeekBar to pre-defined layout
        ViewGroup rangeSeekLayout = (ViewGroup)activity.findViewById(R.id.rangeSeekLayout);
        rangeSeekLayout.removeView(activity.findViewById(R.id.seekBar));
        rangeSeekLayout.addView(seekBar);

        // seekBar.setNotifyWhileDragging(true);
        seekBar.setSelectedCurrentValue(76);
	}

	@Override
	public void run() {
		
		while (true) {
			try {
				if (activity.isInForeground()) {
					getThermostat();
					Thread.sleep(500);
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void getThermostat() {

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(Thermostat.BASE_URL);
			get.addHeader("Content-Type", "application/json");
			get.addHeader("Accept", "application/json");
			HttpResponse response = client.execute(get);
			
		    // 200 type response
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		    {
		    	InputStream content = response.getEntity().getContent();
		    	Reader reader = new InputStreamReader(content);

		    	Map<String, Thermostat> map = 
		    			new Gson().fromJson(reader, new TypeToken<HashMap<String, Thermostat>>() {}.getType());
		    	
		    	if (map.size() != 1) {
		    		return;
		    	}
		    	final Thermostat nest = (Thermostat) map.values().toArray()[0];
		    	
		    	activity.runOnUiThread(new Runnable() {
		    		
		    	     @Override
		    	     public void run() {

		    	    	 if (stateLocked==0) {
		    	    		 setTemperatureLabel.setText(nest.getCurrentTemperature() + "°c (" + nest.getMinTemp() + "°c - " + nest.getMaxTemp() + "°c)");
		    	    		 seekBar.setSelectedMinValue(nest.getMinTemp());
		    	    		 seekBar.setSelectedMaxValue(nest.getMaxTemp());
		    	    		 seekBar.setSelectedCurrentValue(nest.getCurrentTemperature());
		    	    	 }
		    	    	 seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
		                        @Override
		                        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
		                        	// handle changed range values
		                        	System.out.println("cahgnged!!!");
				    	    		seekBar.setSelectedMinValue(minValue);
				    	    		seekBar.setSelectedMaxValue(maxValue);
		                        	putThermostat(nest, minValue, maxValue);
				    	    		setTemperatureLabel.setText(nest.getCurrentTemperature() 
				    	    				+ "°c (" + minValue + "°c - " + maxValue + "°c)");
		                        }
		    	    	 });
		    	    }
		    	});
		    } else {
				throw new UnsupportedOperationException("Received non-200 http response - HTTP " + response.getStatusLine());
		    }
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return;
	}
	
	int stateLocked = 0;
	public void putThermostat(final Thermostat nest, final int minTemp, final int maxTemp) {
		
		/*
		if (stateLocked) {
			
			return;
		} else {

			stateLocked = true;
			this.minTemp = minTemp;
			this.maxTemp = maxTemp;
		}*/

		stateLocked++;
		new Thread(new Runnable() {
			public void run() {

			try {
				for (int i=0; i<1; i++) {
					String thermostatURL = Thermostat.BASE_URL + "/" + nest.getId();
					HttpClient client = new DefaultHttpClient();
					HttpPut put = new HttpPut(thermostatURL);
					put.addHeader("Content-Type", "application/json");
					put.addHeader("Accept", "application/json");
					put.setEntity(new StringEntity("{ \"minTemp\": " + Integer.toString(minTemp) + ", " 
							+ " \"maxTemp\": " + Integer.toString(maxTemp) + ", "
							+ " \"password\": \"iamal0ck\"" 
							+ " } "));
					HttpResponse response = client.execute(put);
					
				    // 200 type response.
				    if (response.getStatusLine().getStatusCode() >= HttpStatus.SC_OK &&
				    response.getStatusLine().getStatusCode() < HttpStatus.SC_MULTIPLE_CHOICES)
				    {
				    	InputStream content = response.getEntity().getContent();
				    	Reader reader = new InputStreamReader(content);
				    	
				    	Map<String, String> map = 
				    			new Gson().fromJson(reader, new TypeToken<HashMap<String, String>>() {}.getType());
				    	if (map.containsKey("result") && map.get("result").toLowerCase().equals("ok")) {
				    		System.out.println("ok setting thermostat: " + map.toString());
				    		break;
				    	} else {
				    		System.out.println("error response while setting thermostat: " + map.toString());
				    	}
				    } else {
				    	System.out.println("unexpected status code while setting thermostat: " 
				    		+ response.getStatusLine().getStatusCode());
				    	ResponseHandler<String> handler = new BasicResponseHandler();
				    	String body = handler.handleResponse(response);
				    	System.out.println(body);
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
				stateLocked--;
			}
			}}).start();
	}
}

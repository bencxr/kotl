package com.example.lovelights;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.*;

import android.os.AsyncTask;

public class ApiRequest extends AsyncTask<URL, Integer, Long>  {

	private static final int CONNECT_TIMEOUT = 10;
	public URL url;
	int id = Integer.MAX_VALUE;
	
	public ApiRequest() {
	}

	public Long doInBackground(URL... urls)  {

    	URL url = urls[0];
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPut put = new HttpPut(url.toString());
			put.addHeader("Content-Type", "application/json");
			put.addHeader("Accept", "application/json");
			put.setEntity(new StringEntity("{ \"state\": 0 }"));
			HttpResponse response = client.execute(put);
			
		    // 200 type response.
		    if (response.getStatusLine().getStatusCode() >= HttpStatus.SC_OK &&
		    response.getStatusLine().getStatusCode() < HttpStatus.SC_MULTIPLE_CHOICES)
		    {
		      // Handle OK response etc........
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (long)0;
	}
}

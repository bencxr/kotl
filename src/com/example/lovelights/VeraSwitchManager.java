package com.example.lovelights;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class VeraSwitchManager extends Thread implements List<VeraSwitch> {
	
	private static final String BASE_LIGHT_URL = "http://home.isidorechan.com/lights";
	List<VeraSwitch> list;
	MainActivity activity;
	
	public VeraSwitchManager(MainActivity activity) {
		
		this.list = new ArrayList<VeraSwitch>();
		this.activity = activity;
	}

	@Override
	public boolean add(VeraSwitch object) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int location, VeraSwitch object) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends VeraSwitch> arg0) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends VeraSwitch> arg1) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {

		this.list.clear();
	}

	@Override
	public boolean contains(Object object) {

		return this.list.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {

		return this.list.containsAll(arg0);
	}

	@Override
	public VeraSwitch get(int location) {

		return this.list.get(location);
	}

	@Override
	public int indexOf(Object object) {

		return 0;
	}

	@Override
	public boolean isEmpty() {

		return this.list.isEmpty();
	}

	@Override
	public Iterator<VeraSwitch> iterator() {

		return this.list.iterator();
	}

	@Override
	public int lastIndexOf(Object object) {
		
		return this.list.lastIndexOf(object);
	}

	@Override
	public ListIterator<VeraSwitch> listIterator() {
		
		return this.list.listIterator();
	}

	@Override
	public ListIterator<VeraSwitch> listIterator(int location) {
		
		return this.list.listIterator(location);
	}

	@Override
	public VeraSwitch remove(int location) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object object) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public VeraSwitch set(int location, VeraSwitch object) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {

		return this.list.size();
	}

	@Override
	public List<VeraSwitch> subList(int start, int end) {
		
		return this.list.subList(start, end);
	}

	@Override
	public Object[] toArray() {

		return this.list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		
		return (T[]) this.list.toArray();
	}

	@Override
	public void run() {
		
		while (true) {
			getLights();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	private List<VeraSwitch> getLights() { 
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(BASE_LIGHT_URL);
			get.addHeader("Content-Type", "application/json");
			get.addHeader("Accept", "application/json");
			HttpResponse response = client.execute(get);
			
		    // 200 type response.
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		    {
		    	InputStream content = response.getEntity().getContent();
		    	Reader reader = new InputStreamReader(content);
		    	
		    	GsonBuilder builder = new GsonBuilder();
		    	builder.registerTypeAdapter(VeraSwitch[].class, new VeraSwitchDeserializer());
		    	Gson gson = builder.setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		    			.create();
		    	VeraSwitch[] veraSwitches = gson.fromJson(reader, VeraSwitch[].class);
		    	
		    	this.list = new ArrayList<VeraSwitch>();
		    	for (VeraSwitch veraSwitch: veraSwitches) {
		    		this.list.add(veraSwitch);
		    	}
		    	activity.runOnUiThread(new Runnable() {
		    	     @Override
		    	     public void run() {
		    	    	 
		    	    	 activity.adapter.notifyDataSetChanged();
		    	    }
		    	});
		    } else {
				throw new UnsupportedOperationException("Received non-200 http response - HTTP " + response.getStatusLine());
		    }
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}

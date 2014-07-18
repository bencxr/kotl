package com.example.lovelights;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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

public class SwitchDeviceManager extends Thread implements List<SwitchDevice> {
	
	List<SwitchDevice> list;
	MainActivity activity;
	
	public SwitchDeviceManager(MainActivity activity) {
		
		this.list = new ArrayList<SwitchDevice>();
		this.activity = activity;
	}

	@Override
	public boolean add(SwitchDevice object) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int location, SwitchDevice object) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends SwitchDevice> arg0) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends SwitchDevice> arg1) {

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
	public SwitchDevice get(int location) {

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
	public Iterator<SwitchDevice> iterator() {

		return this.list.iterator();
	}

	@Override
	public int lastIndexOf(Object object) {
		
		return this.list.lastIndexOf(object);
	}

	@Override
	public ListIterator<SwitchDevice> listIterator() {
		
		return this.list.listIterator();
	}

	@Override
	public ListIterator<SwitchDevice> listIterator(int location) {
		
		return this.list.listIterator(location);
	}

	@Override
	public SwitchDevice remove(int location) {
		
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
	public SwitchDevice set(int location, SwitchDevice object) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {

		return this.list.size();
	}

	@Override
	public List<SwitchDevice> subList(int start, int end) {
		
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
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	private List<SwitchDevice> getLights() { 
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(SwitchDevice.BASE_SWITCH_URL);
			get.addHeader("Content-Type", "application/json");
			get.addHeader("Accept", "application/json");
			HttpResponse response = client.execute(get);
			
		    // 200 type response
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		    {
		    	InputStream content = response.getEntity().getContent();
		    	Reader reader = new InputStreamReader(content);
		    	
		    	GsonBuilder builder = new GsonBuilder();
		    	builder.registerTypeAdapter(SwitchDevice[].class, new SwitchDeviceDeserializer());
		    	Gson gson = builder.setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		    			.create();
		    	SwitchDevice[] veraSwitches = gson.fromJson(reader, SwitchDevice[].class);
		    	
		    	Map<Integer, SwitchDevice> switchMap = new HashMap<Integer, SwitchDevice>();
		    	for (SwitchDevice veraSwitch: veraSwitches) {
		    		switchMap.put(veraSwitch.getId(), veraSwitch);
		    	}
		    	
		    	for (SwitchDevice veraSwitch: this.list){
		    		
		    		veraSwitch.setState(switchMap.get(veraSwitch.getId()).getState());
		    		veraSwitch.setName(switchMap.get(veraSwitch.getId()).getName());
		    		veraSwitch.setRoom(switchMap.get(veraSwitch.getId()).getRoom());
		    		switchMap.remove(veraSwitch.getId());
		    	}
		    	
		    	this.list.addAll(switchMap.values());
		    	
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

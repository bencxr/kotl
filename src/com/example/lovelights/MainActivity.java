package com.example.lovelights;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static final String BASE_URL = "http://home.isidorechan.com";
	
	SwitchListAdapter adapter;
	
	private static MainActivity singleton;

	private boolean mIsInForegroundMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singleton = this;
        
        setContentView(R.layout.activity_main);
        
        ThermostatManager themostatManager = new ThermostatManager(this);
        themostatManager.start();
        
        // Switch manager to populate switch list view
        SwitchDeviceManager veraSwitchManager = new SwitchDeviceManager(this);
        veraSwitchManager.start();
        
        // set up list view
        adapter = new SwitchListAdapter(this, R.layout.switch_list_item, veraSwitchManager);
        ListView switchListView = (ListView)findViewById(R.id.switchListView);
        switchListView.setAdapter(adapter);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mIsInForegroundMode = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsInForegroundMode = true;
    }
    
    // Returns the application instance 
    public static MainActivity getInstance() {
        return singleton;
    }
    
    // Some function.
    public boolean isInForeground() {
        return mIsInForegroundMode;
    }
    
    public void saveStateHandler(View view) {

    	RadioGroup radioState = (RadioGroup) findViewById(R.id.radioState);
    	RadioButton r = (RadioButton) findViewById(radioState.getCheckedRadioButtonId());
    	if (r == null) { return; }
    	
    	final String selectedState = (String) r.getText();
    	
    	if (selectedState != null) {
			new AlertDialog.Builder(this)
		    .setTitle("Confirm State Override")
		    .setMessage("Are you sure you want to save state for " + selectedState + "?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	new Thread(new Runnable() {
						public void run() {
							try {
								HttpClient client = new DefaultHttpClient();
								HttpPut put = new HttpPut(BASE_URL + "/states/" + selectedState);
								put.addHeader("Content-Type", "application/json");
								put.addHeader("Accept", "application/json");
								put.setEntity(new StringEntity("{ \"password\": \"iamal0ck\" }"));
								HttpResponse response = client.execute(put);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}}).start();
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		    .show();
    	}
    }

    public void loadStateHandler(View view) {

    	RadioGroup radioState = (RadioGroup) findViewById(R.id.radioState);
    	RadioButton r = (RadioButton) findViewById(radioState.getCheckedRadioButtonId());
    	if (r == null) { return; }
    	
    	final String selectedState = (String) r.getText();
    	
    	if (selectedState != null) {
			new AlertDialog.Builder(this)
		    .setTitle("Confirm house-wide state Load")
		    .setMessage("Are you sure you want to load state " + selectedState + "?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	new Thread(new Runnable() {
						public void run() {
							
							try {
								HttpClient client = new DefaultHttpClient();
								HttpPut put = new HttpPut(BASE_URL + "/states/load/" + selectedState);
								put.addHeader("Content-Type", "application/json");
								put.addHeader("Accept", "application/json");
								put.setEntity(new StringEntity("{ \"password\": \"iamal0ck\" }"));
								HttpResponse response = client.execute(put);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}}).start();
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		    .show();
    	}
    }
}

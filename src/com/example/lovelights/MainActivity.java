package com.example.lovelights;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    
    public void clickHandler(View view) {
    	
    	if (view.getId() == R.id.radioButton2) {
    		adapter.notifyDataSetChanged();
    	} else {
                
			new AlertDialog.Builder(this)
		    .setTitle("Delete entry")
		    .setMessage("It will go dark. ")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	try {
						new ApiRequest().execute(new URL("http://home.isidorechan.com:5000/lights/11"));
						RadioButton r = (RadioButton)(MainActivity.getInstance().findViewById(R.id.radioButton1));
						r.setText("abc");
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

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
	
	Integer minTemp = 68;
	Integer maxTemp = 82;
	SwitchListAdapter adapter;
	
	Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        
        TextView minTempTextView = (TextView)findViewById(R.id.minTempTextView);
        minTempTextView.setText(minTemp.toString());
        
        TextView maxTempTextView = (TextView)findViewById(R.id.maxTempTextView);
        maxTempTextView.setText(maxTemp.toString());
        
        // create RangeSeekBar as Integer range between 20 and 75
        RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(minTemp, maxTemp, getApplicationContext());
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                	// handle changed range values
                	TextView setTemperatureLabel = (TextView)findViewById(R.id.setTemperatureLabel);
                	setTemperatureLabel.setText(minValue + "°c - " + maxValue + "°c");
                }
        });
        seekBar.setNotifyWhileDragging(true);
        seekBar.setSelectedCurrentValue(76);
        
        // add RangeSeekBar to pre-defined layout
        ViewGroup rangeSeekLayout = (ViewGroup) findViewById(R.id.rangeSeekLayout);
        rangeSeekLayout.removeView(findViewById(R.id.seekBar1));
        rangeSeekLayout.addView(seekBar);
        
        VeraSwitchManager veraSwitchManager = new VeraSwitchManager(this);
        veraSwitchManager.start();
        
        // set up list view
        adapter = new SwitchListAdapter(this, R.layout.switch_list_item, veraSwitchManager);
        ListView switchListView = (ListView)findViewById(R.id.switchListView);
        switchListView.setAdapter(adapter);
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
						RadioButton r = (RadioButton)(mActivity.findViewById(R.id.radioButton1));
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

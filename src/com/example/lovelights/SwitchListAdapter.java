package com.example.lovelights;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class SwitchListAdapter extends ArrayAdapter<SwitchDevice> {
	
	private List<SwitchDevice> items;
	private int layoutResourceId;
	private Context context;

	public SwitchListAdapter(Context context, int layoutResourceId, SwitchDeviceManager veraSwitchManager) {
		
		super(context, layoutResourceId, veraSwitchManager);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = veraSwitchManager;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		final SwitchHolder holder = new SwitchHolder();
		holder.veraSwitch = items.get(position);
		holder.switchButton = (Switch)row.findViewById(R.id.switch_button);
		holder.switchButton.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	int requestedState = (holder.veraSwitch.getState() + 1) % 2;
	        	holder.veraSwitch.setState(holder.switchButton, requestedState);	        
	        }
	    });

		row.setTag(holder);

		setupItem(holder);
		return row;
	}

	private void setupItem(SwitchHolder holder) {
		
		holder.switchButton.setText(holder.veraSwitch.getName());
		holder.switchButton.setChecked(holder.veraSwitch.getState() >= 1);
	}

	public static class SwitchHolder {
		
		SwitchDevice veraSwitch;
		Switch switchButton;
	}
}

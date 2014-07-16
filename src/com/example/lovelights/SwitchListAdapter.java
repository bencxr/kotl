package com.example.lovelights;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class SwitchListAdapter extends ArrayAdapter<VeraSwitch> {
	
	private List<VeraSwitch> items;
	private int layoutResourceId;
	private Context context;

	public SwitchListAdapter(Context context, int layoutResourceId, List<VeraSwitch> items) {
		
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		SwitchHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new SwitchHolder();
		holder.veraSwitch = items.get(position);
		holder.switchButton = (Switch)row.findViewById(R.id.switch_button);

		row.setTag(holder);

		setupItem(holder);
		return row;
	}

	private void setupItem(SwitchHolder holder) {
		
		holder.switchButton.setText(holder.veraSwitch.getName());
		holder.switchButton.setChecked(holder.veraSwitch.getValue());
	}

	public static class SwitchHolder {
		VeraSwitch veraSwitch;
		Switch switchButton;
	}
}

package com.infotop.tianbaorebengmis.main;

import java.util.ArrayList;

import com.example.tianbaorebengmis.R;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceListAdapter extends ArrayAdapter<String> {

	private ArrayList<String> devName;
	private ArrayList<String> useName;
	private final Activity context;
		
	public DeviceListAdapter(Activity context, ArrayList<String> devName,
			ArrayList<String> useName) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.list_item,devName);
		this.context = context;
		this.devName = devName;
		this.useName = useName;
		
	}


	@Override
	public View getView(int position, View view, ViewGroup parent) {
		View rowView = view;
		final ViewHolder holder;
		if (rowView == null) {
			rowView = context.getLayoutInflater().inflate(
					R.layout.list_item, parent, false);
			holder = new ViewHolder();
			holder.dName = (TextView) rowView.findViewById(R.id.devicename);
			holder.uName = (TextView) rowView.findViewById(R.id.uname);
			
			
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		
		holder.dName.setText(devName.get(position));
		holder.uName.setText(useName.get(position));
		
		return rowView;

	}
	
	
	private class ViewHolder {
		public TextView dName;
		public TextView uName;
		
		
	}

}

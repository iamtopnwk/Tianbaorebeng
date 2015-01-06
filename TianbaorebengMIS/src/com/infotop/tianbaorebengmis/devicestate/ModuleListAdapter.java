package com.infotop.tianbaorebengmis.devicestate;

import java.util.ArrayList;

import com.example.tianbaorebengmis.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ModuleListAdapter extends BaseAdapter {

	private ArrayList<String> name;
	private ArrayList<String> address;
	private ArrayList<String> mstate;
	private final Activity context;

	public ModuleListAdapter(Activity context, ArrayList<String> name,
			ArrayList<String> address, ArrayList<String> mstate) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.name = name;
		this.address = address;
		this.mstate = mstate;

	}

	private class ViewHolder {
		public TextView dName;
		public TextView uaddress;
		public TextView mstate;
		public Button btnUse;
		public Button btnBan;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return name.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View rowView, ViewGroup parent) {
		ViewHolder holder = null;
		if (rowView == null) {
			holder = new ViewHolder();
			rowView = context.getLayoutInflater().inflate(
					R.layout.module_list_item, null);
			holder.dName = (TextView) rowView.findViewById(R.id.name_tb_module);
			holder.uaddress = (TextView) rowView
					.findViewById(R.id.address_tb_module);
			holder.mstate = (TextView) rowView
					.findViewById(R.id.mastate_tb_module);
			holder.btnUse = (Button) rowView.findViewById(R.id.useStateBtn);
			holder.btnBan = (Button) rowView.findViewById(R.id.banStateBtn);

			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.dName.setText(name.get(position));
		holder.uaddress.setText(address.get(position));
		holder.mstate.setText(mstate.get(position));
		if (mstate.get(position).equals("0")) {
			holder.btnUse.setEnabled(false);
			holder.btnBan.setEnabled(true);
		} else if (mstate.get(position).equals("1")) {
			holder.btnUse.setEnabled(true);
			holder.btnBan.setEnabled(false);
		}
		return rowView;
	}

}

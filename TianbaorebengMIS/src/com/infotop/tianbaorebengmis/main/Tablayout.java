package com.infotop.tianbaorebengmis.main;

import com.example.tianbaorebengmis.R;
import com.example.tianbaorebengmis.R.layout;
import com.infotop.tianbaorebengmis.command.CommandActivity;
import com.infotop.tianbaorebengmis.device.DeviceActivity;
import com.infotop.tianbaorebengmis.devicestate.DeviceStateActivity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class Tablayout extends TabActivity {
	private String deviceId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tablayout);
		deviceId = getIntent().getExtras().getString("dId");
		TabHost tabHost = getTabHost();

		// Tab for Photos
		TabSpec tab = tabHost.newTabSpec("DeviceState");
		Intent i = new Intent(Tablayout.this, DeviceStateActivity.class);
		i.putExtra("dId", deviceId);
		tab.setIndicator("DeviceState");
		tab.setContent(i);

		// Tab for Songs
		TabSpec tab1 = tabHost.newTabSpec("Command");
		Intent i1 = new Intent(Tablayout.this, CommandActivity.class);
		i1.putExtra("dId", deviceId);
		tab1.setIndicator("Command");
		tab1.setContent(i1);

		// Tab for Videos
		TabSpec tab2 = tabHost.newTabSpec("Device");
		Intent i2 = new Intent(Tablayout.this, DeviceActivity.class);
		i2.putExtra("dId", deviceId);
		tab2.setIndicator("Device");
		tab2.setContent(i2);

		// Adding all TabSpec to TabHost
		tabHost.addTab(tab); // Adding photos tab
		tabHost.addTab(tab1); // Adding songs tab
		tabHost.addTab(tab2); // Adding videos tab
	}
}

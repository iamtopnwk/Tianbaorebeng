package com.infotop.tianbaorebengmis.devicestate;

import com.example.tianbaorebengmis.R;
import com.example.tianbaorebengmis.R.id;
import com.example.tianbaorebengmis.R.layout;
import com.example.tianbaorebengmis.R.menu;
import com.infotop.tianbaorebengmis.command.CommandActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DeviceStateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_state);
	}

	
	 public void next1(View view){
		 Intent i=new Intent(this,CommandActivity.class);
		 startActivity(i);
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.device_state, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

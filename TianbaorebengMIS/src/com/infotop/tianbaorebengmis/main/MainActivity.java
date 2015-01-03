package com.infotop.tianbaorebengmis.main;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.tianbaorebengmis.R;
import com.infotop.tianbaorebengmis.devicestate.DeviceStateActivity;
import com.infotop.tianbaorebengmis.httpservice.HttpServiceHandler;
import com.infotop.tianbaorebengmis.httpservice.HttpUrl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG_ID = "id";
	private static final String TAG_USERNAME = "userName";
	private static final String TAG_DEVICENAME = "deviceName";
	private static final String TAG_UUID = "uuid";
	
	
	String userName;
	String deviceName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String serverURL = new HttpUrl().getUrl()+":8080/Tianbaorebeng/rest/devicesList";
		
		

		// Use AsyncTask execute Method To Prevent ANR Problem
		new LongOperation().execute(serverURL);
	}
	private class LongOperation extends AsyncTask<String, Void, Void> {
		private ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			dialog.setMessage("Please wait..");
			dialog.show();

		}
		@Override
		protected Void doInBackground(String... urls) {
		    String pcontent;
			// Send data
			try {
				
				HttpServiceHandler hs = new HttpServiceHandler();
				pcontent = hs.httpContent(urls[0]);
				System.out.println("*********Content*******");	
				System.out.println(pcontent);
				 JSONArray jsonArray;
					jsonArray = new JSONArray(pcontent);
					
					int length=jsonArray.length();
					for(int i=0;i<length;i++){
						JSONObject pc=jsonArray.getJSONObject(0);
						
						
						deviceName=pc.getString(TAG_DEVICENAME);
						userName=pc.getString(TAG_USERNAME);
						
						
						
					}
					
			} catch (Exception ex) {
				System.out.println("Exception e:" + ex.getMessage());
			}
			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			dialog.dismiss();
			
TextView txt1=(TextView)findViewById(R.id.deviceNameHome);
TextView txt2=(TextView)findViewById(R.id.userNameHome);

txt1.setText(deviceName);
txt2.setText(userName);

			
			
			
			
		}
	}
	
	public void next(View view){
		Intent i=new Intent(this,DeviceStateActivity.class);
		startActivity(i);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

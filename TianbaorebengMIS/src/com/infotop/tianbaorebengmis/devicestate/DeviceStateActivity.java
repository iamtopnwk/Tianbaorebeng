package com.infotop.tianbaorebengmis.devicestate;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianbaorebengmis.R;
import com.infotop.tianbaorebengmis.command.CommandActivity;
import com.infotop.tianbaorebengmis.httpservice.HttpServiceHandler;
import com.infotop.tianbaorebengmis.httpservice.HttpUrl;

public class DeviceStateActivity extends Activity {
	private String command;
	private String deviceId;
	private static final String TAG_ID = "id";
	private static final String TAG_UUID = "uuid";

	private static final String TAG_DEVICE_NAME = "deviceName";
	private static final String TAG_USER_NAME = "userName";

	// private static final String TAG_UUID = "uuid";

	/* tb_DeviceState constants */
	private static final String TAG_DEVICE_UNIT_STATE = "deviceUnitState";
	private static final String TAG_MODULE_USE = "moduleUse";
	private static final String TAG_RUN_MODE = "runMode";
	private static final String TAG_MODULE_NUM = "moduleNum";
	private static final String TAG_ENVIRONMENT_CELSIUS = "environmentCelsius";
	private static final String TAG_HEATING_CELSIUS = "heatingCelsius";
	private static final String TAG_COOLING_CELSIUS = "coolingCelsius";
	private static final String TAG_TOTAL_RUN_DATE = "totalRunDate";
	private static final String TAG_EFFLUENT_CELSIUS = "effluentCelsius";
	private static final String TAG_REWATER_CELSIUS = "rewaterCelsius";

	private static final String TAG_NAME = "name";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_MSTATE = "mstate";
	/* tb_Device constants */

	private static final String TAG_BEGIN_DATE = "beginDate";
	private static final String TAG_MODULE_NUMBER = "moduleNumber";
	private static final String TAG_USER_IPHONE = "userIphone";
	private static final String TAG_USER_MAIL = "userMail";
	private static final String TAG_USER_ADDRESS = "userAddress";
	private static final String TAG_REMARK = "remark";

	String id, uuid, deviceName, userName, deviceUnitState, moduleUse, runMode,
			moduleNum, environmentCelsius, heatingCelsius, coolingCelsius,
			totalRunDate, effluentCelsius, rewaterCelcius, name, address,
			mstate, moduleNumber, beginDate, userIphone, userMail, userAddress,
			remark;
	Switch switchBtn;
	String serverURL1;
	Button useBtn, banBtn;

	ListView list1;
	private ArrayList<String> modName = new ArrayList<String>();
	private ArrayList<String> modAddress = new ArrayList<String>();
	private ArrayList<String> modMstate = new ArrayList<String>();
	String[] dId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_state);

		list1 = (ListView) findViewById(R.id.module_list);

		useBtn = (Button) findViewById(R.id.useStateBtn);
		banBtn = (Button) findViewById(R.id.banStateBtn);
		deviceId = getIntent().getExtras().getString("dId");
		String serverURL = new HttpUrl().getUrl()
				+ ":8080/Tianbaorebeng/rest/deviceList/" + deviceId;
		serverURL1 = new HttpUrl().getUrl()
				+ ":8080/Tianbaorebeng/rest/statusUpdate";
		// serverPostURL=new HttpUrl().getUrl()+
		// ":8080/Tianbaorebeng/rest/statusUpdate";

		// Use AsyncTask execute Method To Prevent ANR Problem
		new LongOperation().execute(serverURL);
	}

	/*
	 * public void useOperation(View view){ command="01 03 23 20 FF FF 01"; new
	 * LongOperation().execute(serverPostURL); } public void banOperation(View
	 * view){ command="01 06 23 20 FF FF 01"; new
	 * LongOperation().execute(serverPostURL); }
	 */
	private class LongOperation extends AsyncTask<String, Void, Void> {
		private ProgressDialog dialog = new ProgressDialog(
				DeviceStateActivity.this);
		ModuleListAdapter moduleAdapter;

		

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

				//int length = jsonArray.length();

				// for (int i = 0; i < length; i++) {
				
				JSONObject pc = null,pc2 = null;
				JSONArray pc1 = null;
				//int length = jsonArray.length();

				// for (int i = 0; i < length; i++) {
				try{
				 pc = jsonArray.getJSONObject(0);
				   deviceUnitState = pc.getString(TAG_DEVICE_UNIT_STATE);
					moduleUse = pc.getString(TAG_MODULE_USE);
					runMode = pc.getString(TAG_RUN_MODE);
					moduleNum = pc.getString(TAG_MODULE_NUM);
					environmentCelsius = pc.getString(TAG_ENVIRONMENT_CELSIUS);
					heatingCelsius = pc.getString(TAG_HEATING_CELSIUS);
					coolingCelsius = pc.getString(TAG_COOLING_CELSIUS);
					totalRunDate = pc.getString(TAG_TOTAL_RUN_DATE);
					effluentCelsius = pc.getString(TAG_EFFLUENT_CELSIUS);
					rewaterCelcius = pc.getString(TAG_REWATER_CELSIUS);
				}catch(Exception e){
					System.out.println("Exception e:" + e.getMessage());
				}
				try{
				 pc1 = jsonArray.getJSONArray(1);
				 int length1 = pc1.length();
					for (int j = 0; j < length1; j++) {
						JSONObject op1 = pc1.getJSONObject(j);
						System.out.println("op1:" + op1);
						modName.add(op1.getString(TAG_NAME));
						modAddress.add(op1.getString(TAG_ADDRESS));
						modMstate.add(op1.getString(TAG_MSTATE));

					}
				}catch(Exception e){
					System.out.println("Exception e:" + e.getMessage());
				}
				try{
				 pc2 = jsonArray.getJSONObject(2);
				}catch(Exception e){
					System.out.println("Exception e:" + e.getMessage());
				}
			} catch (Exception ex) {
				System.out.println("Exception e:" + ex.getMessage());
			}
			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			dialog.dismiss();

			/* calling Textview ids */
			TextView txt1 = (TextView) findViewById(R.id.deviceUnitState);
			TextView txt2 = (TextView) findViewById(R.id.moduleUse);
			TextView txt3 = (TextView) findViewById(R.id.runMode);
			TextView txt4 = (TextView) findViewById(R.id.moduleNumber);
			TextView txt5 = (TextView) findViewById(R.id.environmentCelsius);
			TextView txt6 = (TextView) findViewById(R.id.heatingCelsius);
			TextView txt7 = (TextView) findViewById(R.id.coolingCelsius);
			TextView txt8 = (TextView) findViewById(R.id.totalRunDate);
			TextView txt9 = (TextView) findViewById(R.id.effluentCelsius);
			TextView txt10 = (TextView) findViewById(R.id.rewaterCelsius);


			txt1.setText(deviceUnitState);
			txt2.setText(moduleUse);
			txt3.setText(runMode);
			txt4.setText(moduleNum);
			txt5.setText(environmentCelsius);
			txt6.setText(heatingCelsius);
			txt7.setText(coolingCelsius);
			txt8.setText(totalRunDate);
			txt9.setText(effluentCelsius);
			txt10.setText(rewaterCelcius);

			
			moduleAdapter = new ModuleListAdapter(DeviceStateActivity.this,
					modName, modAddress, modMstate);
			for (String ff : modName) {
				Log.v("test", ff);
			}
			list1.setAdapter(moduleAdapter);
		}
	}

	public void getUseButton(View view) {
		command = "01 03 23 20 FF FF " + modAddress.get((Integer)(view.getTag()));
		new PostOperation().execute(serverURL1);

	}

	public void getBanButton(View view) {
		command = "01 06 23 20 00 00 " + modAddress.get((Integer)(view.getTag()));
		new PostOperation().execute(serverURL1);

	}

	private class PostOperation extends AsyncTask<String, Void, Void> {
		private String pcontent;
		private ProgressDialog dialog = new ProgressDialog(
				DeviceStateActivity.this);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			dialog.setMessage("Please wait");
			dialog.show();

		}

		@Override
		protected Void doInBackground(String... urls) {
			String jsonData = "";
			// Send data
			try {
				HttpServiceHandler hs = new HttpServiceHandler();
				JSONObject json = new JSONObject();
				json.accumulate("command", command);
				json.accumulate("id", deviceId);
				jsonData = json.toString();
				System.out.println("Json Data for posting:" + jsonData);
				pcontent = hs.httpPost(urls[0], jsonData);
				System.out.println("Executed data:" + pcontent);
				dialog.dismiss();
				// textView.setText("wrong credentials");

			} catch (Exception ex) {
				System.out.println("Exception e:" + ex.getMessage());
			}
			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {

			if (pcontent.equalsIgnoreCase("Success")) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.CommandisUpdated),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.Connectionerror),
						Toast.LENGTH_SHORT).show();
			}
			// Close progress dialog
		}

	}

	public void next1(View view) {
		Intent i = new Intent(this, CommandActivity.class);
		i.putExtra("dId", deviceId);
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

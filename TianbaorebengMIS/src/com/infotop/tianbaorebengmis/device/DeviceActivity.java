package com.infotop.tianbaorebengmis.device;

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
import com.infotop.tianbaorebengmis.devicestate.DeviceStateActivity;
import com.infotop.tianbaorebengmis.devicestate.ModuleListAdapter;

import com.infotop.tianbaorebengmis.httpservice.HttpServiceHandler;
import com.infotop.tianbaorebengmis.httpservice.HttpUrl;

public class DeviceActivity extends Activity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device);

		deviceId = getIntent().getExtras().getString("dId");
		String serverURL = new HttpUrl().getUrl()
				+ "/rest/deviceList/" + deviceId;

		// Use AsyncTask execute Method To Prevent ANR Problem
		new LongOperation().execute(serverURL);
	}

	private class LongOperation extends AsyncTask<String, Void, Void> {
		private ProgressDialog dialog = new ProgressDialog(DeviceActivity.this);

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
				try {
					JSONObject pc2 = jsonArray.getJSONObject(2);
					System.out.println("pc2:" + pc2);
					deviceName = pc2.getString(TAG_DEVICE_NAME);
					moduleNumber = pc2.getString(TAG_MODULE_NUMBER);
					beginDate = pc2.getString(TAG_BEGIN_DATE);
					userName = pc2.getString(TAG_USER_NAME);
					userIphone = pc2.getString(TAG_USER_IPHONE);
					userMail = pc2.getString(TAG_USER_MAIL);
					userAddress = pc2.getString(TAG_USER_ADDRESS);
					remark = pc2.getString(TAG_REMARK);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				// }

			} catch (Exception ex) {
				System.out.println("Exception e:" + ex.getMessage());
			}
			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			dialog.dismiss();

			TextView txt14 = (TextView) findViewById(R.id.deviceName);
			TextView txt15 = (TextView) findViewById(R.id.moduleNumberTbDevice);
			TextView txt16 = (TextView) findViewById(R.id.beginDate);
			TextView txt17 = (TextView) findViewById(R.id.userName);
			TextView txt18 = (TextView) findViewById(R.id.userIphone);
			TextView txt19 = (TextView) findViewById(R.id.userMail);
			TextView txt20 = (TextView) findViewById(R.id.userAddress);
			TextView txt21 = (TextView) findViewById(R.id.remark);

			txt14.setText(deviceName);
			txt15.setText(moduleNumber);
			txt16.setText(beginDate);
			txt17.setText(userName);
			txt18.setText(userIphone);
			txt19.setText(userMail);
			txt20.setText(userAddress);
			txt21.setText(remark);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.device, menu);
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

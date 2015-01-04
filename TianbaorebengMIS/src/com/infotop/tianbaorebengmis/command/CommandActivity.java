package com.infotop.tianbaorebengmis.command;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.tianbaorebengmis.R;
import com.example.tianbaorebengmis.R.id;
import com.example.tianbaorebengmis.R.layout;
import com.example.tianbaorebengmis.R.menu;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CommandActivity extends Activity {

	private String serverURL;
	private String command;
	private String deviceId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_command);
		deviceId="1";
		serverURL = new HttpUrl().getUrl()
				+ ":8080/Tianbaorebeng/rest/statusUpdate";
	}

	public void getRunModeButton(View view) {
		Spinner runmodeData = (Spinner) findViewById(R.id.spinner1);
		if (runmodeData.getSelectedItem().toString().trim().equals("Choice")) {
			//String s=getResources().getString(R.string.pleaseselectchoice);
			Toast.makeText(getApplicationContext(),  getResources().getString(R.string.pleaseselectchoice),
					Toast.LENGTH_SHORT).show();
		} else if (runmodeData.getSelectedItem().toString().trim()
				.equals(getResources().getString(R.string.HeatingMode))) {
			command = "01 06 23 00 00 01";
			new LongOperation().execute(serverURL);

		} else if (runmodeData.getSelectedItem().toString().trim()
				.equals(getResources().getString(R.string.CoolingMode))) {
			command = "01 06 23 00 00 02";
			new LongOperation().execute(serverURL);
		} else if (runmodeData.getSelectedItem().toString().trim()
				.equals(getResources().getString(R.string.AutoMode))) {
			command = "01 06 23 00 00 03";
			new LongOperation().execute(serverURL);

		}

	}

	public void getCoolingCelsius(View view) {
		EditText coolingData = ((EditText) findViewById(R.id.editText21));
		if (coolingData.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.CoolingCelsiusshouldnotempty),
					Toast.LENGTH_SHORT).show();
			/*coolingData.setError("CoolingCelsius is required!");*/
		} else {
			
			command = "01 06 23 01 00 " + coolingData.getText().toString();
			coolingData.setText("");
			new LongOperation().execute(serverURL);
			
		}
	}

	public void getHeatingCelsius(View view) {
		EditText heatingData = ((EditText) findViewById(R.id.editText20));
		if (heatingData.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.HeatingCelsiusshouldnotempty),
					Toast.LENGTH_SHORT).show();
			/*heatingData.setError("HeatingCelsius is required!");*/
		} else {
			command = "01 06 23 02 00 " + heatingData.getText().toString();
			heatingData.setText("");
			new LongOperation().execute(serverURL);
		}
	}

	public void postDeviceState(View view) {
		command = "01 05 00 00 FF 00 8C 3A";
		new LongOperation().execute(serverURL);
	}

	public void postDeviceStop(View view) {
		command = "01 05 00 01 FF 00 DD FA";
		new LongOperation().execute(serverURL);
	}

	public void postDeviceReset(View view) {
		command = "01 05 00 08 FF 00 0D F8";
		new LongOperation().execute(serverURL);
	}

	public void postDeviceDefrost(View view) {
		command = "01 05 00 10 FF 00 8D FF";
		new LongOperation().execute(serverURL);
	}

	private class LongOperation extends AsyncTask<String, Void, Void> {
		private String pcontent;
		private ProgressDialog dialog = new ProgressDialog(CommandActivity.this);

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
						getResources().getString(R.string.CommandisUpdated), Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.Connectionerror),
						Toast.LENGTH_SHORT).show();
			}
			// Close progress dialog
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.command, menu);
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

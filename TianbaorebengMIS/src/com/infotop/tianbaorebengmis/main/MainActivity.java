package com.infotop.tianbaorebengmis.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.tianbaorebengmis.R;
import com.infotop.tianbaorebengmis.devicestate.DeviceStateActivity;
import com.infotop.tianbaorebengmis.httpservice.HttpServiceHandler;
import com.infotop.tianbaorebengmis.httpservice.HttpUrl;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG_ID = "id";
	private static final String TAG_USERNAME = "userName";
	private static final String TAG_DEVICENAME = "deviceName";
	private static final String TAG_UUID = "uuid";
	//private List<Device> listDe = new ArrayList<Device>();
	private  Context context;
	String[] dId;
	/*String[] userName;
	String[] deviceName;*/
	
	ListView list;
	private TableLayout tl;
	 ArrayList<HashMap<String, String>> deviceList = new ArrayList<HashMap<String, String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);
		String serverURL = new HttpUrl().getUrl()+":8080/Tianbaorebeng/rest/devicesList";
		context=this;
		//tl=(TableLayout)findViewById(R.id.TableLayoutmain3);
		list = (ListView) findViewById(R.id.list);
		// Use AsyncTask execute Method To Prevent ANR Problem
		new LongOperation().execute(serverURL);
	}
	private class LongOperation extends AsyncTask<String, Void, Void> {
		
		DeviceListAdapter listAdapter;
		private ArrayList<String> devName=new ArrayList<String>();
		private ArrayList<String> useName=new ArrayList<String>();
		
        
		private ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);
		 @Override
		protected void onPreExecute() {
			 super.onPreExecute();
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
				System.out.println("==========pppppppppp"+pcontent);
				 JSONArray jsonArray;
					jsonArray = new JSONArray(pcontent);
					
					int length=jsonArray.length();
					System.out.println("================"+length);
					dId = new String[length];
					for(int i=0;i<length;i++){
						JSONObject pc=jsonArray.getJSONObject(i);
						System.out.println("Device Name:"+pc.getString(TAG_DEVICENAME));
						devName.add(pc.getString(TAG_DEVICENAME));
						useName.add(pc.getString(TAG_USERNAME));
						dId[i] = pc.getString(TAG_ID);
						System.out.println("jjjjjjjjjjjjj"+dId[i]);
						System.out.println("Device Name:"+pc.getString(TAG_DEVICENAME));
						/*deviceName[i]=pc.getString(TAG_DEVICENAME);
						userName[i]=pc.getString(TAG_USERNAME);
						System.out.println("oooooooooooooooooooooo"+deviceName[i]);*/
						
						
						
						//Device devic = new Device();
						//devic.setId(pc.getString(TAG_ID).toString());
						//devic.setDeviceName(pc.getString(TAG_DEVICENAME).toString());
						//devic.setUserName(pc.getString(TAG_USERNAME).toString());
						
						//listDe.add(devic);
						
					}
					/*for (Device deviceVal : listDe) {
						//sout 
						TableRow tr= new TableRow(context);
						TextView tv=new TextView(context);
						TextView tvdN=new TextView(context);
						TextView tvUN=new TextView(context);
						
							tv.setText(deviceVal.getId());
							tvdN.setText(deviceVal.getDeviceName());
							tvUN.setText(deviceVal.getUserName());
							tr.addView(tv);
							tr.addView(tvdN);
							tr.addView(tvUN);
						tl.addView(tr);
					}
					*/
					
			} catch (Exception ex) {
				System.out.println("Exception e:" + ex.getMessage());
			}
			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			dialog.dismiss();
			listAdapter = new DeviceListAdapter(MainActivity.this,
					devName, useName);
			list.setAdapter(listAdapter);
			//list.setTextFilterEnabled(true);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Intent i = new Intent(MainActivity.this,
							DeviceStateActivity.class);
					//i.putStringArrayListExtra("productData", productData);
					i.putExtra("dId",dId[position]);
					System.out.println("kkkkkkkkkkkkkkk"+dId[position]);
					startActivity(i);
				}
			});
	
		}}
	
	public void getDeviceButton(View view){
		System.out.println("Position id is"+(Integer)view.getTag());
		Intent i = new Intent(MainActivity.this,
				Tablayout.class);
		//i.putStringArrayListExtra("productData", productData);
		i.putExtra("dId",dId[(Integer)view.getTag()]);
		System.out.println("kkkkkkkkkkkkkkk"+dId[(Integer)view.getTag()]);
		startActivity(i);
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

package com.infotop.tianbaorebengmis.login;

import com.example.tianbaorebengmis.R;
import com.infotop.tianbaorebengmis.devicestate.DeviceStateActivity;
import com.infotop.tianbaorebengmis.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private String userName;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
	
		
	}

	public void getSigninPage(View view) {
		userName = ((EditText) findViewById(R.id.editTextLoginUser)).getText().toString();
		password = ((EditText) findViewById(R.id.editTextLoginPwd)).getText().toString();
		System.out.println(userName);
		System.out.println(password);
		if (userName.equals("admin") && password.equals("123456")) {
			System.out.println("Logged Successfully");
			Intent i = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(i);
		} else {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginerror),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

package com.example.nfc;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
//import android.widget.Toast;
import android.widget.Toast;

public class MainActivity extends Activity {
	NfcAdapter mNfcAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		// Check for available NFC Adapter
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
//            finish();
            return true;
        } else{
            Toast.makeText(this, "NFC is available", Toast.LENGTH_LONG).show();
        }
		
		
		return true;
	}
	
	/** Called when the user touches the button */
	public void sendWriteMessage(View view) {
	    // Do something in response to button click
		Intent myIntent = new Intent(this, WriteActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
	}
	/** Called when the user touches the button */
	public void sendReadMessage(View view) {
	    // Do something in response to button click
		Intent myIntent = new Intent(this, ReadActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
	}
	/** Called when the user touches the button */
	public void sendLastTenMessage(View view) {
		// Do something in response to button click
		Intent myIntent = new Intent(this, TopTenActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
	}

}

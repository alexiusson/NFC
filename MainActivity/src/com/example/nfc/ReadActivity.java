package com.example.nfc;

import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ReadActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read, menu);
		return true;
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		String str = "";
	    NdefMessage[] msgs;
	    Intent intent = getIntent();
	    NdefMessage test = null;
	    byte[] ndef;
	    long time = 0;
	    long endtime = 0;
	    double size, sendtime;
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			Toast.makeText(this, "Tag discovered", Toast.LENGTH_LONG).show();
			
			Intent myIntent = new Intent(this, ReadResultsActivity.class);
//	    	time = System.currentTimeMillis();
//			myIntent.putExtra("startTime", time);
			//startActivity(myIntent);
			//finish();
			
//            //Toast.makeText(this, "Tag discovered", Toast.LENGTH_LONG).show();
//	        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//	        if (rawMsgs != null) {
//		        test = (NdefMessage) rawMsgs[0];
//	        }
	        
	    }
	}

	/** Called when the user touches the button */
	public void sendWriteMessage(View view) {
	    // Do something in response to button click
		Intent myIntent = new Intent(this, ReadResultsActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
		finish();
	}
	/** Called when the user touches the button */
	public void sendReadMessage(View view) {
	    // Do something in response to button click
		Toast.makeText(getApplicationContext(), "Already in Read", Toast.LENGTH_LONG).show();
	}
	/** Called when the user touches the button */
	public void sendLastTenMessage(View view) {
		// Do something in response to button click
		Intent myIntent = new Intent(this, TopTenActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
		finish();
	}

}

package com.example.nfc;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	NfcAdapter mNfcAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (!mNfcAdapter.isEnabled()) {
			EnableNFCDialog dialog = new EnableNFCDialog();
			dialog.show(getFragmentManager(), "NFC");
        } else if(mNfcAdapter == null){
        	Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_LONG).show();
        }		
	}
	
	/*No settings
	 * @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	public void onResume() {
	    super.onResume();	    
	}
	
	/** Called when the user touches the button */
	public void sendWriteMessage(View view) {
	    // Do something in response to button click
		Intent myIntent = new Intent(this, WriteResultActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
	}
	/** Called when the user touches the button */
	public void sendReadMessage(View view) {
	    // Do something in response to button click
		Intent myIntent = new Intent(this, ReadResultsActivity.class);
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

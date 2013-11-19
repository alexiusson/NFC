package com.example.nfc;

//import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
//import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
//import android.view.Menu;
import android.view.View;
//import android.widget.Toast;
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
		

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
            //Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
//            finish();
            
        } else;
            //Toast.makeText(this, "NFC is available", Toast.LENGTH_LONG).show();
	}



	public void onResume() {
	    super.onResume();
	    
	    /*mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
//            finish();
            
        } else
            Toast.makeText(this, "NFC is available", Toast.LENGTH_LONG).show();*/
		
		/*String str = "";
	    NdefMessage[] msgs;
	    Intent intent = getIntent();
	    NdefMessage test = null;
	    byte[] ndef;
	    long time = 0;
	    long endtime = 0;
	    double size, sendtime;
	    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
	    	time = System.currentTimeMillis();
            //Toast.makeText(this, "Tag discovered", Toast.LENGTH_LONG).show();
	        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	        test = (NdefMessage) rawMsgs[0];
	        if (rawMsgs != null) {
	            msgs = new NdefMessage[rawMsgs.length];
	            for (int i = 0; i < rawMsgs.length; i++) {
	                msgs[i] = (NdefMessage) rawMsgs[i];
	                str += msgs[i];
	                
	            }
	        }
	    }*/
	    //process the msgs array
	    /*if(test != null){
	    	endtime = System.currentTimeMillis();
	    	ndef = test.toByteArray();
	    	byte[] payload = test.getRecords()[0].getPayload();
	    	System.out.println(payload.length);
	    	endtime -= time;
	    	size = ((double)ndef.length)/endtime;
	    	size = Math.round(size*10000);
	    	size = size/10000;
	    	sendtime = ((double)endtime/1000)/((double)size*endtime);
	    	sendtime = Math.round(size*10000);
	    	sendtime = size/10000;
	    	Toast.makeText(this, String.valueOf(size)+"kb/s", Toast.LENGTH_LONG).show();
	    	
	    	try{
	    		//Get the Text Encoding
		        String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";

		        //Get the Language Code
		        int languageCodeLength = payload[0] & 0077;
		        String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

		        //Get the Text
		        String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
		        
		        //Toast.makeText(this, text, Toast.LENGTH_LONG).show();

	    	} catch(Exception e){
	    		throw new RuntimeException("Record Parsing Failure!!");
	    	}
	    	
	    	str = new String(test.getRecords()[0].getPayload());
	    }*/
	    
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

package com.example.nfc;

import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ReadResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_results);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_results, menu);
		return true;
	}
	long startTime; 
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		startTime = System.currentTimeMillis();
	}

//	String str = "";
//    
//    NdefMessage test = null;
//    byte[] ndef;
//    long time = 0;
//    long endtime = 0;
//    double size, sendtime;
//    TextView tv = (TextView) findViewById(R.id.t1);
//    
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		Intent intent = getIntent();
//		
//        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//        if (rawMsgs != null) {
//	        test = (NdefMessage) rawMsgs[0];
//        }
//	    
//	    //process the msgs array
//	    if(test != null){
//	    	endtime = System.currentTimeMillis();
//	    	ndef = test.toByteArray();
//	    	byte[] payload = test.getRecords()[0].getPayload();
//	    	System.out.println(payload.length);
//	    	endtime -= time;
//	    	size = ((double)ndef.length)/endtime;
//	    	size = Math.round(size*10000);
//	    	size = size/10000;
//	    	sendtime = ((double)endtime/1000)/((double)size*endtime);
//	    	sendtime = Math.round(size*10000);
//	    	sendtime = size/10000;
////	    	Toast.makeText(this, String.valueOf(size)+"kb/s", Toast.LENGTH_LONG).show();
//	    	tv.setText(String.valueOf(size)+"kb/s");
//	    	
//	    	try{
//	    		//Get the Text Encoding
//		        String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
//
//		        //Get the Language Code
//		        int languageCodeLength = payload[0] & 0077;
//		        String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
//
//		        //Get the Text
//		        String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
//		        
//		        //Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//
//	    	} catch(Exception e){
//	    		throw new RuntimeException("Record Parsing Failure!!");
//	    	}
//	    	
//	    	str = new String(test.getRecords()[0].getPayload());
//	    }
//	    
//	}
	

	
	
	

}

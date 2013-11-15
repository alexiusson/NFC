package com.example.nfc;

import java.io.IOException;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReadResultsActivity extends Activity {

	private NfcAdapter mAdapter;
	private Button readTagButton;
	private boolean inWriteMode;
	private TextView readResultTV;
	private EditText enterMessageField;
	private long startTime;
	private long endTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_results);
		mAdapter = NfcAdapter.getDefaultAdapter(this);
		// Read button
		//readTagButton = (Button) findViewById(R.id.writeSendButton);
		

		// Här skrivs write speed ut
		//readResultTV = (TextView) findViewById(R.id.writeResult);
		// Här matas input till tag
		//enterMessageField = (EditText) findViewById(R.id.writeEnterMessage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_results, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		disableRead();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		enableRead();
	}

	@Override
	public void onNewIntent(Intent intent) {

		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		readFromTag(tag);

	}

	private void enableRead() {
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, getClass())
						.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(
				NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter[] filters = new IntentFilter[] { tagDetected };

		mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}

	private void disableRead() {
		mAdapter.disableForegroundDispatch(this);
	}

	private void readFromTag(Tag tag) {
		TextView  tv= (TextView) findViewById(R.id.writeResult);
		String str = "";
	    Intent intent = getIntent();
	    long startTime = 0;
	    long endTime = 0;
	    double bytesPerMilliSecond;
			//Toast.makeText(this, "Tag discovered", Toast.LENGTH_LONG).show();
			
	        //Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	    
		NdefMessage message;
		String type;
		Ndef ndef =Ndef.get(tag);
		type = ndef.getType();
		try {
	    	startTime = System.currentTimeMillis();
			ndef.connect();
			message =ndef.getNdefMessage();
	        endTime = System.currentTimeMillis();
			ndef.close();
	        if (message != null) {
		        //message = (NdefMessage) rawMsgs[0];
	        	byte[] payload = message.getRecords()[0].getPayload();
				bytesPerMilliSecond = (endTime - startTime)/ (double) payload.length;
		        Toast.makeText(this, String.valueOf(bytesPerMilliSecond), Toast.LENGTH_LONG).show();
		        
		        tv.setText("NFC  Type "+type + "detected"+"\n"+"\n" +"ReadSpeed: " + String.valueOf(bytesPerMilliSecond) + "kb/s");

			
				
				
				try{
		    		//Get the Text Encoding
			        String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";

			        //Get the Language Code
			        int languageCodeLength = payload[0] & 0077;
			        String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

			        //Get the Text
			        String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
			        
			        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
			    
				
				

		    	} catch(Exception e){
			        Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();

		    		throw new RuntimeException("Record Parsing Failure!!");
		    	}
		    	
		    	
		    	
		    	//str = new String(message.getRecords()[0].getPayload());
	        
	        
	        }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	

	
	}
}

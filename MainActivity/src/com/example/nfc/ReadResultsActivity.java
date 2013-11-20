package com.example.nfc;

import java.io.IOException;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ReadResultsActivity extends Activity {

	private NfcAdapter mAdapter;
//	private Button readTagButton;
//	private boolean inWriteMode;
//	private TextView readResultTV;
//	private EditText enterMessageField;
//	private long startTime;
//	private long endTime;
	private TextView tv;
	private ProgressBar pb;
	private DatabaseHelper dbh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_results);
		mAdapter = NfcAdapter.getDefaultAdapter(this);
		tv = (TextView) findViewById(R.id.readResult);
		tv.setText("Waiting for tag detection...");
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		// Read button
		//readTagButton = (Button) findViewById(R.id.writeSendButton);
		dbh = new DatabaseHelper(getApplicationContext());

		

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
		pb.setVisibility(View.GONE);
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
	    long startTime = 0;
	    long endTime = 0;
	    double bytesPerMilliSecond;
	    
		NdefMessage message;
		String type;
		Ndef ndef = Ndef.get(tag);
		type = ndef.getType();
		
		
		
		if(type.equals(Ndef.MIFARE_CLASSIC)){
			type = "MiFare Classic";
		}else if(type.equals(Ndef.NFC_FORUM_TYPE_1)){
			type = "Type 1";
		}else if(type.equals(Ndef.NFC_FORUM_TYPE_2)){
			type = "Type 2";
		}else if(type.equals(Ndef.NFC_FORUM_TYPE_3)){
			type = "Type 3";
		}else if(type.equals(Ndef.NFC_FORUM_TYPE_4)){
			type = "Type 4";
		}else{
			type = "Unknown";
		}
		
		try {
	    	startTime = System.currentTimeMillis();
	    	ndef.connect();
			message =ndef.getNdefMessage();
			ndef.close();
	        endTime = System.currentTimeMillis();
	        if (message != null) {
	        	byte[] payload = message.getRecords()[0].getPayload();
				bytesPerMilliSecond = payload.length / (double)(endTime - startTime);
//				bytesPerMilliSecond = Math.round(bytesPerMilliSecond*100);
//				bytesPerMilliSecond = bytesPerMilliSecond/100;
				
	    		//Get the Text Encoding
		        String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";

		        //Get the Language Code
		        int languageCodeLength = payload[0] & 0077;
		        String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

		        //Get the Text
		        String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
		        tv.setText("NFC Type "+type + " detected!"+"\n"+"\n" +"Read speed: " + String.valueOf(bytesPerMilliSecond) + " kb/s\n\n"
		        			+ "Content: " + text);
		        dbh.addRead(type, bytesPerMilliSecond);
	        
	        }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch(Exception e){
	        Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();

    		throw new RuntimeException("Record Parsing Failure!!");
	    }
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
		//Toast.makeText(getApplicationContext(), "Already in Read", Toast.LENGTH_LONG).show();

	}
	/** Called when the user touches the button */
	public void sendLastTenMessage(View view) {
		// Do something in response to button click
		Intent myIntent = new Intent(this, TopTenActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
	}
}

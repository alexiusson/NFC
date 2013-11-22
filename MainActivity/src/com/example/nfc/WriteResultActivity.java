package com.example.nfc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.app.PendingIntent;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteResultActivity extends Activity implements OnClickListener {
	private NfcAdapter mAdapter;
	private Button writeTagButton;
	private boolean inWriteMode;
	private TextView writeResultTV;
	private EditText enterMessageField;
	private long startTime;
	private long endTime;
	private DatabaseHelper dbh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_result);
		mAdapter = NfcAdapter.getDefaultAdapter(this);
		// Write button
		writeTagButton = (Button) findViewById(R.id.writeSendButton);
		writeTagButton.setOnClickListener((android.view.View.OnClickListener) this);

		// Här skrivs write speed ut
		writeResultTV = (TextView) findViewById(R.id.writeResult);
		// Här matas input till tag
		enterMessageField = (EditText) findViewById(R.id.writeEnterMessage);
		dbh = new DatabaseHelper(getApplicationContext());

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		disableWrite();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		enableWrite();
	}

	@Override
	public void onNewIntent(Intent intent) {
		if(inWriteMode) {
			inWriteMode = false;
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			writeToTag(tag);
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.writeSendButton) {
			inWriteMode = true;
			writeResultTV.setText("Hold tag against phone to write.");
			
			InputMethodManager imn = (InputMethodManager) getSystemService(
					Context.INPUT_METHOD_SERVICE);
			imn.hideSoftInputFromWindow(enterMessageField.getWindowToken(), 0);
		}
	}
	
	private void enableWrite() {
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
	            new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] filters = new IntentFilter[] { tagDetected };
        
		mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}
	
	private void disableWrite() {
		mAdapter.disableForegroundDispatch(this);
	}

	
	private boolean writeToTag(Tag tag) {
		NdefMessage message = editTextToNdefMsg(enterMessageField);
        
		try {
			// see if tag is already NDEF formatted
			Ndef ndef = Ndef.get(tag);
			int maxSize = ndef.getMaxSize();
			String type = ndef.getType();
			if (ndef != null) {
				ndef.connect();
				
				if (!ndef.isWritable()) {
					Toast.makeText(this, "Read-only tag.", Toast.LENGTH_LONG).show();
					return false;
				}
				
				// work out how much space we need for the data
				int size = message.toByteArray().length;
				if (ndef.getMaxSize()< size) {
					Toast.makeText(this, "Tag doesn't have enough free space.", Toast.LENGTH_LONG).show();
					return false;
				}
				startTime=System.currentTimeMillis();
				ndef.writeNdefMessage(message);
				endTime=System.currentTimeMillis();
				computeTime(startTime, endTime, message, type, maxSize);
				Toast.makeText(this, "Tag written successfully.", Toast.LENGTH_LONG).show();
				return true;
			} else {
				// attempt to format tag
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						startTime=System.currentTimeMillis();
						format.format(message);
						endTime=System.currentTimeMillis();
						Toast.makeText(this, "Tag written successfully!", Toast.LENGTH_LONG).show();
						computeTime(startTime, endTime, message, type, maxSize);
						return true;
					} catch (IOException e) {
						Toast.makeText(this, "Unable to format tag to NDEF.", Toast.LENGTH_LONG).show();
						return false;
					}
				} else {
					Toast.makeText(this, "Tag doesn't appear to support NDEF format.", Toast.LENGTH_LONG).show();
					return false;
				}
			}
		} catch (Exception e) {
			Toast.makeText(this, "Failed to write tag.", Toast.LENGTH_LONG).show();
		}

        return false;
    }
	
	private void computeTime(long sTime, long eTime, NdefMessage nMessage, String type, int maxSize){
		int bytes = nMessage.getByteArrayLength();
		double bytesPermilSec= bytes/(double)(eTime-sTime);
		bytesPermilSec = Math.round(bytesPermilSec*100);
		bytesPermilSec = bytesPermilSec/100;
		
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
		
		writeResultTV.setText(	"NFC Type: " + type + " detected!\n\n" + 
								"Write speed: " + String.valueOf(bytesPermilSec) + " kb/s");
		dbh.addWrite(type, bytesPermilSec);
//		Toast.makeText(this, "Writespeed: " + String.valueOf(bytesPermilSec) + " kb/s", Toast.LENGTH_LONG).show();
	}
	
	private NdefMessage editTextToNdefMsg(EditText et) {
		String msg = et.getText().toString();
	    byte[] languageCode;
	    byte[] msgBytes;
	    try {
	        languageCode = "en".getBytes("US-ASCII");
	        msgBytes = msg.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e) {
	        return null;
	    }

	    byte[] messagePayload = new byte[1 + languageCode.length
	            + msgBytes.length];
	    messagePayload[0] = (byte) 0x02; // status byte: UTF-8 encoding and
	                                        // length of language code is 2
	    System.arraycopy(languageCode, 0, messagePayload, 1,
	            languageCode.length);
	    System.arraycopy(msgBytes, 0, messagePayload, 1 + languageCode.length,
	            msgBytes.length);

	    NdefMessage message;
	    NdefRecord[] records = new NdefRecord[1];
	    NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
	            NdefRecord.RTD_TEXT, new byte[]{}, messagePayload);
	    records[0] = textRecord;
	    message = new NdefMessage(records);
	    
	    return message;
	}
	
	/** Called when the user touches the button */
	public void sendWriteMessage(View view) {
	    // Do something in response to button click
		//Intent myIntent = new Intent(this, WriteResultActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		//this.startActivity(myIntent);
		//finish();
		//Toast.makeText(this, "Already in write", Toast.LENGTH_LONG).show();
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

package com.example.nfc;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TopTenActivity extends Activity {

	private DatabaseHelper dbh;
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10,
			tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top_ten);
		
		dbh = new DatabaseHelper(getApplicationContext());
		//Linking the textviews
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		tv4 = (TextView) findViewById(R.id.textView4);
		tv5 = (TextView) findViewById(R.id.textView5);
		tv6 = (TextView) findViewById(R.id.textView6);
		tv7 = (TextView) findViewById(R.id.textView7);
		tv8 = (TextView) findViewById(R.id.textView8);
		tv9 = (TextView) findViewById(R.id.textView9);
		tv10 = (TextView) findViewById(R.id.textView10);
		tv11 = (TextView) findViewById(R.id.textView11);
		tv12 = (TextView) findViewById(R.id.textView12);
		tv13 = (TextView) findViewById(R.id.textView13);
		tv14 = (TextView) findViewById(R.id.textView14);
		tv15 = (TextView) findViewById(R.id.textView15);
		tv16 = (TextView) findViewById(R.id.textView16);
		tv17 = (TextView) findViewById(R.id.textView17);
		tv18 = (TextView) findViewById(R.id.textView18);
		tv19 = (TextView) findViewById(R.id.textView19);
		tv20 = (TextView) findViewById(R.id.textView20);
		print();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_ten, menu);
		return true;
	}
	
	/** Called when the user touches the write button */
	public void sendWriteMessage(View view) {
	    // Do something in response to button click
		Intent myIntent = new Intent(this, WriteResultActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
		
	}
	/** Called when the user touches the read button */
	public void sendReadMessage(View view) {
	    // Do something in response to button click
		Intent myIntent = new Intent(this, ReadResultsActivity.class);
//		myIntent.putExtra("key", value); //Optional parameters
		this.startActivity(myIntent);
	}
	/** Called when the user touches the last ten button */
	public void sendLastTenMessage(View view) {
		// Do something in response to button click
//		Toast.makeText(getApplicationContext(), "Already in Last 10", Toast.LENGTH_LONG).show();
		print();
	}

	/**
	 * Prints the database to the lists.
	 */
	private void print(){
		ArrayList<String> readList = dbh.showResults(dbh.results("read"));
		ArrayList<String> writeList = dbh.showResults(dbh.results("write"));
				
		for(int i=readList.size(); i<10; i++) {
			readList.add(i, "N/A");
		}
		
		for(int k=writeList.size(); k<10; k++) {
			writeList.add(k, "N/A");
		}
		
		// read list
		tv1.setText(readList.get(0));
		tv2.setText(readList.get(1));
		tv3.setText(readList.get(2));
		tv4.setText(readList.get(3));
		tv5.setText(readList.get(4));
		tv6.setText(readList.get(5));
		tv7.setText(readList.get(6));
		tv8.setText(readList.get(7));
		tv9.setText(readList.get(8));
		tv10.setText(readList.get(9));
		// write list
		tv11.setText(writeList.get(0));
		tv12.setText(writeList.get(1));
		tv13.setText(writeList.get(2));
		tv14.setText(writeList.get(3));
		tv15.setText(writeList.get(4));
		tv16.setText(writeList.get(5));
		tv17.setText(writeList.get(6));
		tv18.setText(writeList.get(7));
		tv19.setText(writeList.get(8));
		tv20.setText(writeList.get(9));
	}

}

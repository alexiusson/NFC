package com.example.nfc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String DB_NAME = "nfcDB";
	static final String DB_TABLE_READ = "Read";
	static final String DB_TABLE_WRITE = "Write";
	static final String DB_COL_READ = "Read";
	static final String DB_COL_WRITE = "Write";

	static final int DB_VERSION = 1;
	static final String SQL_CREATE_READ_TABLE = ("CREATE TABLE "
			+ DB_TABLE_READ + " (" + DB_COL_READ + " text not null");
	static final String SQL_CREATE_WRITE_TABLE = ("CREATE TABLE "
			+ DB_TABLE_WRITE + " (" + DB_COL_WRITE + " text not null");

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION); // Context, databaseName,
													// CursorFactory, Version
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_READ_TABLE);
		db.execSQL(SQL_CREATE_WRITE_TABLE);
		// InsertDepts(db);
		// DatabaseHelper db3;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + DB_TABLE_READ);
		db.execSQL("drop table if exists " + DB_TABLE_WRITE);
		onCreate(db);
	}

	// HŠmtar 10 senaste resultaten
	// select * from (select * from Results order by sortfield ASC limit 10)
	// order by sortfield DESC;

}

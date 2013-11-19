package com.example.nfc;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String DB_NAME = "nfcDB";
	static final String DB_TABLE_READ = "Read";
	static final String DB_TABLE_WRITE = "Write";
	static final String DB_COL_SPEED = "Speed";
	static final String DB_COL_TYPE = "Type";

	static final int DB_VERSION = 1;
	static final String SQL_CREATE_READ_TABLE = ("CREATE TABLE " + DB_TABLE_READ + " (" +
																"ID INTEGER primary key AUTOINCREMENT," + 
																DB_COL_SPEED + " TEXT not null," + 
																DB_COL_TYPE + " TEXT not null");
	static final String SQL_CREATE_WRITE_TABLE = ("CREATE TABLE " + DB_TABLE_WRITE + " (" + 
																"ID INTEGER primary key AUTOINCREMENT," + 
																DB_COL_SPEED + " TEXT not null," + 
																DB_COL_TYPE + " TEXT not null");
	
	
	//private SQLiteDatabase db;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION); // Context, databaseName,
													// CursorFactory, Version
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_READ_TABLE);
		db.execSQL(SQL_CREATE_WRITE_TABLE);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + DB_TABLE_READ);
		db.execSQL("drop table if exists " + DB_TABLE_WRITE);
		onCreate(db);
	}
	
	public boolean addRead(String type, double sp){
		
        SQLiteDatabase db = this.getReadableDatabase();
		String speed = String.valueOf(sp);
		String sql = "INSERT INTO " + DB_TABLE_READ + " (Speed, Type) VALUES(" + speed + ", " + type + ") "  ;
		try{
			db.execSQL(sql);
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean addWrite(String type, double sp){
        SQLiteDatabase db = this.getReadableDatabase();
		String speed = String.valueOf(sp);
		String sql = "INSERT INTO " + DB_TABLE_WRITE + " (Speed, Type) VALUES(" + speed + ", " + type + ") "  ;
		try{
			db.execSQL(sql);
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	
	public Cursor results (String table) {
        SQLiteDatabase db = this.getWritableDatabase();
		String getTable = "";
		if(table.equals("read")){
			getTable = DB_TABLE_READ;
		}else if(table.equals("write")){
			getTable = DB_TABLE_WRITE;
		}
		
		String sql = "SELECT * from " + getTable + " order by id desc limit 10";
		Cursor c = db.rawQuery(sql, null);
		
		return c;
	}

	// HŠmtar 10 senaste resultaten
	// select * from (select * from Results order by sortfield ASC limit 10)
	// order by sortfield DESC;
	
//	public class DBResult{
//		public String type, speed;
//		public DBResult(){
//			type = "";
//			speed = "";
//		}
//	}

}

package com.bally;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "sales";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COL1 = "barcode";
	public static final String COLUMN_COL2 = "price";
	
	public static final String TABLE_NAME_1 = "stock";
	public static final String COLUMN_ID_1 = "_id";
	public static final String COLUMN_COL1_1 = "shopid";
	public static final String COLUMN_COL2_1 = "barcode";
	
	private static final String DATABASE_NAME = "/sdcard/barcode.db";//to store on sdcard /sdcard/barcode.db
	private static final int DATABASE_VERSION = 2;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_COL1
			+ " text UNIQUE not null," + COLUMN_COL2
			+ " text not null);";

	private static final String DATABASE_CREATE_1 = "create table "
			+ TABLE_NAME_1 + "( " + COLUMN_ID_1
			+ " integer primary key autoincrement, " + COLUMN_COL1_1
			+ " text not null," + COLUMN_COL2_1
			+ " text UNIQUE not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE);
		database.execSQL(DATABASE_CREATE_1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
		onCreate(db);
	}

}

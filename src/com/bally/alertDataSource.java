package com.bally;

import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class alertDataSource {
	private SQLiteDatabase db;
	private MySQLiteHelper dbh;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_COL1,MySQLiteHelper.COLUMN_COL2};
	
	private String[] stock_allColumns = { MySQLiteHelper.COLUMN_ID_1,
			MySQLiteHelper.COLUMN_COL1_1,MySQLiteHelper.COLUMN_COL2_1};

	public alertDataSource(Context cnt) {
		// TODO Auto-generated constructor stub
		dbh=new MySQLiteHelper(cnt);
	}
	public void open() throws SQLException
	{
		db=dbh.getWritableDatabase();
	}
	public void close()
	{
		dbh.close();
	}
	public void insertValue(String str,String i) throws Throwable 
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_COL1, str);
		values.put(MySQLiteHelper.COLUMN_COL2, i);
		long insertId = db.insert(MySQLiteHelper.TABLE_NAME, null,
				values);
		if(insertId==-1)
			throw new Throwable("Duplicate Barcode Entry");
	}
	
	public void stock_insertValue(String str,String i) throws Throwable 
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_COL1_1, str);
		values.put(MySQLiteHelper.COLUMN_COL2_1, i);
		long insertId = db.insert(MySQLiteHelper.TABLE_NAME_1, null,
				values);
		if(insertId==-1)
			throw new Throwable("Duplicate Barcode Entry");
	}

	public void deleteValue(String str) 
	{
		long insertId = db.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID + " = " + str, null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}

	public void stock_deleteValue(String str) 
	{
		long insertId = db.delete(MySQLiteHelper.TABLE_NAME_1, MySQLiteHelper.COLUMN_ID_1 + " = " + str, null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}

	public void update(String str1,String str2,String str3) 
	{
		ContentValues cv=new ContentValues();
		cv.put(MySQLiteHelper.COLUMN_COL1, str2);
		cv.put(MySQLiteHelper.COLUMN_COL2, str3);
		
		long insertId = db.update(MySQLiteHelper.TABLE_NAME, cv, MySQLiteHelper.COLUMN_ID + " = " + str1, null);
				//delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID + " = " + str, null);//.insert(MySQLiteHelper.TABLE_NAME, null,
				//values);
	}

	public String showValue(Context cnt)
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		cursor.moveToLast();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		return cursor.getInt(0) + ">  " + cursor.getString(1);// + "/" + cursor.getString(2) + "/" ;
//		return cursor.getInt(0) + ">  " + cursor.getString(1) + "/" + cursor.getString(2) + "/" ;
	}

	public String stock_showValue(Context cnt)
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				stock_allColumns, null, null, null, null, null);
		cursor.moveToLast();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		return cursor.getInt(0) + ">  " + cursor.getString(2);// + "/" + cursor.getString(2) + "/" ;
//		return cursor.getInt(0) + ">  " + cursor.getString(1) + "/" + cursor.getString(2) + "/" ;
	}

	public alertRecord getRecord(Context cnt)
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		if(cursor.getCount()>0)
		{
		alertRecord r1=new alertRecord();
		r1.setId(cursor.getInt(0));
		r1.setBarcode(cursor.getString(1));
		r1.setPrice(cursor.getString(2));
		return r1;
		}
		else
			return null;
		
	}

	public stockRecord stock_getRecord(Context cnt)
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				stock_allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		if(cursor.getCount()>0)
		{
			stockRecord r1=new stockRecord();
		r1.setId(cursor.getInt(0));
		r1.setShopno(cursor.getString(1));
		r1.setBarcode(cursor.getString(2));
		return r1;
		}
		else
			return null;
		
	}

	
	/*	public alertRecord stock_getRecord(Context cnt)
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				stock_allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		if(cursor.getCount()>0)
		{
		alertRecord r1=new alertRecord();
		r1.setId(cursor.getInt(0));
		r1.setBarcode(cursor.getString(1));
		r1.setPrice(cursor.getString(2));
		return r1;
		}
		else
			return null;
		
	}
*/	
	public int showCount()
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		return cursor.getCount();
	}
	
	public int stock_showCount()
	{
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				stock_allColumns, null, null, null, null, null);
		return cursor.getCount();
	}

	public List<String> showAllValue(Context cnt)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + ">  " + cursor.getString(1));// + "/" + cursor.getString(2) + "/");
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		
			//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/";
	}

	public List<String> stock_showAllValue(Context cnt,Integer shopno)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				stock_allColumns, MySQLiteHelper.COLUMN_COL1_1 + " = " + shopno.toString() , null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			lst.add(cursor.getInt(0) + ">  " + cursor.getString(2));// + "/" + cursor.getString(2) + "/");
			cursor.moveToNext();
		}
		cursor.close();
		return lst;
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		
			//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/";
	}

	public Cursor showAllRec(Context cnt)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		return cursor;
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		
			//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/";
	}
	
	public Cursor stock_showAllRec(Context cnt)
	{
		List<String> lst = new ArrayList<String>();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME_1,
				stock_allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		return cursor;
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		
			//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/";
	}

	
	public Cursor showJobDet(String str)
	{
		Cursor cursor = db.rawQuery("select * from sales where _id = ?", new String[] { str });//.query(MySQLiteHelper.TABLE_NAME_1,allColumns, MySQLiteHelper.COLUMN_COL1_1 + " = " + str, null, null, null, null);
		cursor.moveToFirst();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/" + cursor.getString(3) + "/" + cursor.getInt(4) + "/";
		//return "01/abc/efg/1";
		return cursor;
	}

	public Cursor stock_showJobDet(String str)
	{
		Cursor cursor = db.rawQuery("select * from stock where _id = ?", new String[] { str });//.query(MySQLiteHelper.TABLE_NAME_1,allColumns, MySQLiteHelper.COLUMN_COL1_1 + " = " + str, null, null, null, null);
		cursor.moveToFirst();
		//Toast.makeText(cnt,cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/", 3000).show();
		//return cursor.getInt(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2) + "/" + cursor.getString(3) + "/" + cursor.getInt(4) + "/";
		//return "01/abc/efg/1";
		return cursor;
	}

}

package com.example.live_safe;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class service_database extends SQLiteOpenHelper{
	   public static final String DATABASE_NAME = "MyDDName.db";
	   public static final String driverdetails_TABLE_NAME = "driverdetails";
	   public static final String driverdetails_COLUMN_ID = "id";
	   public static final String driverdetails_COLUMN_NAME = "name";
	   public static final String driverdetails_COLUMN_VEHICALNO = "vehno";
	   public static final String CONTACTS_COLUMN_PHONE = "phone";
	   private HashMap hp;

	   public service_database(Context context)
	   {
	      super(context, DATABASE_NAME , null, 1);
	   }
	   
	   @Override
	   public void onCreate(SQLiteDatabase db) {
	      // TODO Auto-generated method stub
	      db.execSQL(
	      "create table driverdetails " +
	      "(id integer primary key, name text,phone text,vehno text)"
	      );
	   }

	   @Override
	   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	      // TODO Auto-generated method stub
	      db.execSQL("DROP TABLE IF EXISTS driverdetails");
	      onCreate(db);
	   }

	   public boolean insertDD  (String name, String phone, String vehno)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put("name", name);
	      contentValues.put("phone", phone);
	      contentValues.put("vehno", vehno);	
	      db.insert("driverdetails", null, contentValues);
	      return true;
	   }
	   
	   public Cursor getData(int id){
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from driverdetails where id="+id+"", null );
	      return res;
	   }
	   
	   public int numberOfRows(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, driverdetails_TABLE_NAME);
	      return numRows;
	   }
	   
	   public boolean updateDD (Integer id, String name, String phone, String vehno)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put("name", name);
	      contentValues.put("phone", phone);
	      contentValues.put("vehno", vehno);
	      
	      db.update("driverdetails", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
	      return true;
	   }

	   public Integer deleteDD (Integer id)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      return db.delete("driverdetails", 
	      "id = ? ", 
	      new String[] { Integer.toString(id) });
	   }
	   
	   public int datainsert()
	   {
	      
	    	  insertDD("Kumara", "9035550910", "KA-03 J-6055");
	    	  insertDD("Ravi", "9035550910", "KA-41 6927");
	    	  insertDD("Ramesh", "9035550910", "KA-03 EX-6921");
	      
	      int rownumber = numberOfRows();
	      return rownumber;
	   }
	   
	   public ArrayList<String> getAllCotacts()
	   {
	      ArrayList<String> array_list = new ArrayList<String>();
	      
	      //hp = new HashMap();
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from driverdetails", null );
	      res.moveToFirst();
	      
	      while(res.isAfterLast() == false){
	         array_list.add(res.getString(res.getColumnIndex(driverdetails_COLUMN_NAME)));
	         res.moveToNext();
	      }
	   return array_list;
	   }
}

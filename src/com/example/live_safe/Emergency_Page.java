package com.example.live_safe;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Emergency_Page extends Activity {
	
	Button btnhelp,btnreportpolice,btncancel;
	GPSTracker gps;
	DBHelper mydb;
	service_database servdb;
	Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
	StringBuilder strReturnedAddress = new StringBuilder("");
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_page);
        
       btnhelp 			= (Button)findViewById(R.id.btnhelp);
       btnreportpolice  = (Button)findViewById(R.id.btnreportpolice);
       btncancel 		= (Button)findViewById(R.id.btncancel);
       mydb = new DBHelper(this);
       
       btnhelp.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               gps = new GPSTracker(Emergency_Page.this);
               
               int Value =1;
               Cursor rs = mydb.getData(Value);
               
               rs.moveToFirst();
               String[]  phonedata = new String[3];
               
               phonedata[0] = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY1));
               phonedata[1] = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY2));
               phonedata[2] = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY3));
               
               if(gps.canGetLocation()) {
                   double latitude = gps.getLatitude();
                   double longitude = gps.getLongitude();
                   strReturnedAddress = getAddress(latitude, longitude);         
                   for(int i=0; i<phonedata.length;i++)
                   sendSMSMessage(phonedata[i],latitude,longitude,strReturnedAddress);
                   strReturnedAddress = new StringBuilder("");
                   
               } else {
            	   
                   gps.showSettingsAlert();
               }
           }
       });
       btnreportpolice.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               gps = new GPSTracker(Emergency_Page.this);
               
               int Value =1;
               Cursor rs = mydb.getData(Value);
               
               rs.moveToFirst();
               String[]  phonedata = new String[3];
               String name = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
               String phone = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
               phonedata[0] = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY1));
               phonedata[1] = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY2));
               phonedata[2] = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY3));
               
               if(gps.canGetLocation()) {
                   double latitude = gps.getLatitude();
                   double longitude = gps.getLongitude();
                   strReturnedAddress = getAddress(latitude, longitude); 
                   for(int i=0; i<phonedata.length;i++)
                   sendPoliceSMSMessage(phonedata[i],name,phone,latitude,longitude,strReturnedAddress);
               } else {
            	   
                   gps.showSettingsAlert();
               }
           }
       });
       btncancel.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
        	   int Value=1;
        	   Bundle dataBundle = new Bundle();
               dataBundle.putInt("id", Value);
               
               Intent intent = new Intent(getApplicationContext(),Main_Page.class);
               intent.putExtras(dataBundle);
               
               startActivity(intent);
           }
        });
	}
	protected void sendSMSMessage(String phone,double latitude,double longitude,StringBuilder address) {
	      Log.i("Send SMS", "");
	      
	      try {
	         SmsManager smsManager = SmsManager.getDefault();
	         String message = "I Am in trouble Please Help Me\n LOC: "+address+"\nLAT- "+latitude+"\nLNG- "+longitude;
	         smsManager.sendTextMessage(phone, null,message, null, null);
	         Toast.makeText(getApplicationContext(), "SMS Sent", Toast.LENGTH_LONG).show();
	      } 
	      
	      catch (Exception e) {
	         Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
	         e.printStackTrace();
	      }
	   }
	protected void sendPoliceSMSMessage(String phone,String name,String phoneno,double latitude,double longitude,StringBuilder address) {
	      Log.i("Send SMS", "");
	      
	      try {
	    	  String message = "Reporting Sexual Harrassment/Mugging Please Help!!\n Details: "+ name +",\n"+phoneno;
	    	  String message1= "\nLOC: "+address+"\nLAT- "+latitude+"\nLNG- "+longitude;
	    	  SmsManager smsManager = SmsManager.getDefault();
	          smsManager.sendTextMessage(phone, null,message, null, null);
	         smsManager.sendTextMessage(phone, null,message1, null, null);
	         Toast.makeText(getApplicationContext(), "SMS Sent", Toast.LENGTH_LONG).show();
	      } 
	      
	      catch (Exception e) {
	         Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
	         e.printStackTrace();
	      }
	   }
	   protected StringBuilder getAddress(double latitude,double longitude)
	   {
		   try {
               List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

               if(addresses != null) {
                   Address returnedAddress = addresses.get(0);
                   
                   for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                       strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                   }
                   Toast.makeText(getApplicationContext(), strReturnedAddress, Toast.LENGTH_LONG).show();
                   
               }
               else{
            	   Toast.makeText(getApplicationContext(), "Address Not Found", Toast.LENGTH_LONG).show();
               }
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
              
           }
		   return strReturnedAddress;
	   }
}

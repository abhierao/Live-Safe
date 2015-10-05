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

public class Uber_Page extends Activity{
	
	Button emg,safe;
	GPSTracker gps;
	DBHelper mydb;
	Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
	StringBuilder strReturnedAddress = new StringBuilder("");
	Main_Page main = new Main_Page();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uber_page);
        
        emg   = (Button)findViewById(R.id.Emerg);
        safe  = (Button)findViewById(R.id.Safe);
        
        mydb = new DBHelper(this);
        
        emg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	for(int j=0;j<1;j++)
            	{
            		gps = new GPSTracker(Uber_Page.this);
                    
                    int Value =1;
                    Cursor rs = mydb.getData(Value);
                    
                    rs.moveToFirst();
                    String[]  phonedata = new String[3];
                    String name = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                    String phone = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                    phonedata[0] = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY1));
                    phonedata[1] = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY2));
                    phonedata[2] = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY3));
                    if (!rs.isClosed()) 
                    {
                       rs.close();
                    }
                    Value = 2;
                    rs = mydb.getData(Value);
                    
                    rs.moveToFirst();
                    String dname = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                    String dphone = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                    String dvehno = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_VEHICALNO));
                    if (!rs.isClosed()) 
                    {
                       rs.close();
                    }
                    if(gps.canGetLocation()) {
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        strReturnedAddress = getAddress(latitude, longitude);
                        for(int i=0; i<phonedata.length;i++)
                        sendPoliceSMSMessage(phonedata[i],name,phone,latitude,longitude,dname,dphone,dvehno,strReturnedAddress);
                       if(j==0)
                        main.RecordSound();
                       else
                       {
                    	   try
                    	   {

                        	   Thread.sleep(60000);   
                    	   }
                    	   catch(Exception e)
                    	   {
                    		   
                    	   }
                       }
                    	   
                    } else {
                 	   
                        gps.showSettingsAlert();
                    }
           
            	}
            }
            });
        safe.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {

	            
	        	 Intent myIntent = new Intent(v.getContext(), Main_Page.class);
	                startActivityForResult(myIntent, 0);
	        }
	    });

	}
	protected void sendPoliceSMSMessage(String phone,String name,String phoneno,double latitude,double longitude,String dname,String dphone,String dvehno,StringBuilder address) {
	      Log.i("Send SMS", "");
	      
	      try {
	         SmsManager smsManager = SmsManager.getDefault();
	         String message = "Reporting Cab CRIME:\nUser Details: "+ name +",\n"+phoneno+"\nDriver Details: \n"+dname+",\n"+dphone+",\n"+dvehno;
	         String message1 ="LOC: "+address+"\nLAT- "+latitude+"\nLNG- "+longitude;
	         smsManager.sendTextMessage(phone, null,message, null, null);
	         smsManager.sendTextMessage(phone, null,message1, null, null);
	         Toast.makeText(getApplicationContext(), "Sent Message", Toast.LENGTH_LONG).show();
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

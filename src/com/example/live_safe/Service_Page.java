package com.example.live_safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Service_Page extends Activity{
	Button uber,ola,pt;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_service);
        
        uber = (Button)findViewById(R.id.Uber);
        ola = (Button)findViewById(R.id.Ola);
        pt = (Button)findViewById(R.id.PT);
        
        uber.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {

	        	Intent intent = new Intent(getApplicationContext(),Uber_Page.class);
                
                startActivity(intent);
	        }
	    });
        
        ola.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {

	            
	        	 Intent myIntent = new Intent(v.getContext(), Ola_Page.class);
	                startActivityForResult(myIntent, 0);
	        }
	    });
        
        pt.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {

	            
	        	 Intent myIntent = new Intent(v.getContext(), PT_Page.class);
	                startActivityForResult(myIntent, 0);
	        }
	    });
	}  

}

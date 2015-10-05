package com.example.live_safe;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registration_Page extends Activity{
	
	Button reg;
	DBHelper mydb;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        
        reg = (Button)findViewById(R.id.reg);
        mydb = new DBHelper(this);
        
        int Value =1;
        try
        {
        	Cursor rs = mydb.getData(Value);
            
            rs.moveToFirst();
            
            String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
            
            if (!rs.isClosed()) 
            {
               rs.close();
            }
            
            if(nam != null)
            {
            	Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", Value);
                
                Intent intent = new Intent(getApplicationContext(),Main_Page.class);
                intent.putExtras(dataBundle);
                
                startActivity(intent);
            }

        }
        catch(Exception e)
        {
        	
        }
                reg.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	
	        	Bundle dataBundle = new Bundle();
	            dataBundle.putInt("id", 0);
	            
	            Intent intent = new Intent(getApplicationContext(),LoginPage.class);
	            intent.putExtras(dataBundle);
	            
	            startActivity(intent);
	        	
	        }
	    }); 
	}
	public void onBackPressed() {
	    moveTaskToBack(true);
	}

}

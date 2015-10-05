package com.example.live_safe;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends Activity {
	int from_Where_I_Am_Coming = 0;
	private DBHelper mydb ;
	EditText name,phoneno,aadhar,emg1,emg2,emg3;
	Button Register;
	int id_To_Update = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        
        name 	= (EditText)findViewById(R.id.editText1);
        phoneno = (EditText)findViewById(R.id.editText2);
        aadhar 	= (EditText)findViewById(R.id.editText3);
        emg1	= (EditText)findViewById(R.id.editText4);
        emg2 	= (EditText)findViewById(R.id.editText5);
        emg3 	= (EditText)findViewById(R.id.editText6);
        Register = (Button)findViewById(R.id.button1);
        
        mydb = new DBHelper(this);
        
	      Bundle extras = getIntent().getExtras(); 
	      if(extras !=null)
	      {
	         int Value = extras.getInt("id");
	         
	         if(Value>0){
	            //means this is the view part not the add contact part.
	            Cursor rs = mydb.getData(Value);
	            id_To_Update = Value;
	            rs.moveToFirst();
	            
	            String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
	            String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
	            String aad = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_AADHAR));
	            String emger1 = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY1));
	            String emger2 = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY2));
	            String emger3 = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMERGENCY3));
	            
	            if (!rs.isClosed()) 
	            {
	               rs.close();
	            }
	            
	            Register.setVisibility(View.INVISIBLE);

	            name.setText((CharSequence)nam);
	            name.setFocusable(false);
	            name.setClickable(false);

	            phoneno.setText((CharSequence)phon);
	            phoneno.setFocusable(false); 
	            phoneno.setClickable(false);

	            aadhar.setText((CharSequence)aad);
	            aadhar.setFocusable(false);
	            aadhar.setClickable(false);

	            emg1.setText((CharSequence)emger1);
	            emg1.setFocusable(false); 
	            emg1.setClickable(false);

	            emg2.setText((CharSequence)emger2);
	            emg2.setFocusable(false);
	            emg2.setClickable(false);
	            
	            emg3.setText((CharSequence)emger3);
	            emg3.setFocusable(false);
	            emg3.setClickable(false);
	         }
	      }

        Register.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {

	        	 run(v);
	        	 mydb.insertContact("Ravi","9035550910","","","","","KA-03 J-8767");
	        	 mydb.insertContact("Kumar","9035550910","","","","","KA-41 H-654367");
	        	 mydb.insertContact("Ramesh","9035550910","","","","","KA-05 EX-9867");
	        	
	        }
	    });
    }

    @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      Bundle extras = getIntent().getExtras(); 
	      
	      if(extras !=null)
	      {
	         int Value = extras.getInt("id");
	         if(Value>0){
	            getMenuInflater().inflate(R.menu.display_contact, menu);
	         }
	         
	      }
	      return true;
	   }

	   public boolean onOptionsItemSelected(MenuItem item) 
	   { 
	      super.onOptionsItemSelected(item); 
	      switch(item.getItemId()) 
	   { 
	      case R.id.Edit_Contact: 
	      Button b = (Button)findViewById(R.id.button1);
	      b.setVisibility(View.VISIBLE);
	      
	      name.setEnabled(true);
	      name.setFocusableInTouchMode(true);
	      name.setClickable(true);

	      phoneno.setEnabled(true);
	      phoneno.setFocusableInTouchMode(true);
	      phoneno.setClickable(true);

	      aadhar.setEnabled(true);
	      aadhar.setFocusableInTouchMode(true);
	      aadhar.setClickable(true);

	      emg1.setEnabled(true);
	      emg1.setFocusableInTouchMode(true);
	      emg1.setClickable(true);

	      emg2.setEnabled(true);
	      emg2.setFocusableInTouchMode(true);
	      emg2.setClickable(true);
	      
	      emg3.setEnabled(true);
	      emg3.setFocusableInTouchMode(true);
	      emg3.setClickable(true);

	      return true; 
	      case R.id.Delete_Contact:

	      AlertDialog.Builder builder = new AlertDialog.Builder(this);
	      builder.setMessage(R.string.deleteContact)
	      .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int id) {
	            mydb.deleteContact(id_To_Update);
	            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();  
	            Intent intent = new Intent(getApplicationContext(),Registration_Page.class);
	            startActivity(intent);
	         }
	      })
	      .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int id) {
	            // User cancelled the dialog
	         }
	      });
	      AlertDialog d = builder.create();
	      d.setTitle("Are you sure");
	      d.show();

	      return true;
	      default: 
	      return super.onOptionsItemSelected(item); 

	      } 
	   } 

	   public void run(View view)
	   {	
		  
	      Bundle extras = getIntent().getExtras();
	      if(extras !=null)
	      {
	         int Value = extras.getInt("id");
	         
	         if(Value>0){
	            if(mydb.updateContact(id_To_Update,name.getText().toString(), phoneno.getText().toString(), aadhar.getText().toString(), emg1.getText().toString(), emg2.getText().toString(), emg3.getText().toString(),"")){
	               Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();	
	               Intent intent = new Intent(getApplicationContext(),Main_Page.class);
	               startActivity(intent);
	            }		
	            else{
	               Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();	
	            }
	         }
	         else{
	            if(mydb.insertContact(name.getText().toString(), phoneno.getText().toString(), aadhar.getText().toString(), emg1.getText().toString(), emg2.getText().toString(),emg3.getText().toString(),"")){
	               Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();	
	            }		
	            
	            else{
	               Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();	
	            }
	            Intent intent = new Intent(getApplicationContext(),Main_Page.class);
	            startActivity(intent);
	         }
	      }
	   }

	   public void onBackPressed() {
		    moveTaskToBack(true);
		}

}

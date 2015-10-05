package com.example.live_safe;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class Main_Page extends Activity {
	public final static String EXTRA_MESSAGE = "MESSAGE";
	   private ListView obj;
	   DBHelper mydb;
	Button safe,btnemerg;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        
        safe = (Button)findViewById(R.id.safe);
        btnemerg = (Button)findViewById(R.id.emerge);
        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllCotacts();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        
        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
                
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
               // TODO Auto-generated method stub
               int id_To_Search = arg2 + 1;
               
               Bundle dataBundle = new Bundle();
               dataBundle.putInt("id", id_To_Search);
               
               Intent intent = new Intent(getApplicationContext(),LoginPage.class);
               
               intent.putExtras(dataBundle);
               startActivity(intent);
            }
         });
        btnemerg.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	
	        	 Intent myIntent = new Intent(v.getContext(), Emergency_Page.class);
	                startActivityForResult(myIntent, 0);
	        }
	    });
        
        safe.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            
	        	 Intent myIntent = new Intent(v.getContext(), Service_Page.class);
	                startActivityForResult(myIntent, 0);
	        }
	    });
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
       super.onOptionsItemSelected(item);
       
       switch(item.getItemId())
       {
          case R.id.item1:Bundle dataBundle = new Bundle();
          dataBundle.putInt("id", 0);
          
          Intent intent = new Intent(getApplicationContext(),LoginPage.class);
          intent.putExtras(dataBundle);
          
          startActivity(intent);
          return true;
          default:
          return super.onOptionsItemSelected(item);
       }
    }
	public void onBackPressed() {
	    moveTaskToBack(true);
	}
	protected void RecordSound()
	{
		String outputFile = null;
		MediaRecorder myAudioRecorder;
		outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";;

	    myAudioRecorder=new MediaRecorder();
	    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
	    myAudioRecorder.setOutputFile(outputFile);
	    try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
            
        }

        catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    try
	    {
	    	Thread.sleep(50000);
	    }
	    catch(Exception e)
	    {
	    	
	    }
	    myAudioRecorder.stop();
        myAudioRecorder.release();
	}
}

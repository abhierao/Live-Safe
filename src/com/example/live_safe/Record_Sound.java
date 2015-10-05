package com.example.live_safe;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class Record_Sound extends Activity{
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 

			
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
            Toast.makeText(getApplicationContext(), "started", Toast.LENGTH_LONG).show();
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
	    	Thread.sleep(10000);
	    }
	    catch(Exception e)
	    {
	    	
	    }
	    MediaPlayer m = new MediaPlayer();

        try {
            m.setDataSource(outputFile);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            m.prepare();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
	}
}

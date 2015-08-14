package com.example.alarmstresstest;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AlarmActivity extends Activity {
	
	public Button startButton;
	public Button stopButton;
	public TextView countText;
	public static Boolean status;
	private DelayThread delayThreadRunnbale = new DelayThread();
	private Thread delayThread;
	public static SharedPreferences prefs;
	public int testCountNum;
	public final int  MESSAGE_START = 1;
	private Handler mHandler = new Handler(){
			
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MESSAGE_START:
					impleMethod();
					break;
				}
			};
		};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        startButton = (Button) this.findViewById(R.id.startButton);
        stopButton = (Button) this.findViewById(R.id.stopButton);
        countText = (TextView) this.findViewById(R.id.countText);
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        testCountNum = prefs.getInt("testCount", 0);
        status = prefs.getBoolean("status", false);
        countText.setText("Total Count : " + String.valueOf(testCountNum));        
        Button.OnClickListener listen =  new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buttonOnClick(v);
			}
        	
        };
        
        startButton.setOnClickListener(listen);
        stopButton.setOnClickListener(listen);
       
	    //载入音频流，返回在池中的id  
	    //
        delayThread = new Thread(delayThreadRunnbale);
        delayThread.start();
    }
    
    
    protected void impleMethod(){
    	if(status){
    		testCountNum++;
    	}else{
    		testCountNum = 0;
    	}
    	SharedPreferences.Editor editor = prefs.edit();
    	editor.putInt("testCount", testCountNum);
    	editor.putBoolean("status", status);
    	editor.commit();
    	if(status){
    		startTest();
    		Toast.makeText(this, "begin test", Toast.LENGTH_LONG).show();
    		shutDown();
    	}
    	
    }

    public void buttonOnClick(View v){
    	
    	if(v.getId() == R.id.startButton){
    		status = true;
    	}else{
    		status = false;
    	}
    	
    }
    
    public void startTest(){
    		AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTimeInMillis(System.currentTimeMillis());
    		calendar.add(Calendar.MINUTE, 5);
    		Intent recIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
    		PendingIntent pendIntent = PendingIntent.getBroadcast(getApplicationContext(),
    				0, recIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    		
    		alarmMgr.set(4,calendar.getTimeInMillis(), pendIntent);
    		//alarmMgr.setRepeating(AlarmManager.POWER_OFF_WAKEUP,calendar.getTimeInMillis(), 2, pendIntent);
    }
    
    public void stopTest(){
    	finish();
    }
    
   public void shutDown(){
    	Intent shutdownIntent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
		shutdownIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		shutdownIntent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
		startActivity(shutdownIntent);
    }
  

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    
    class DelayThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			 try {
                 Thread.sleep(20*1000);
             } catch (InterruptedException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
				mHandler.sendEmptyMessage(MESSAGE_START);
		}
    	
    }

}

package com.example.alarmstresstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.preference.PreferenceManager;

public class BootBroadcastReceiver extends BroadcastReceiver{

	static final String action_boot = "android.intent.action.BOOT_COMPLETED";
	private SoundPool pool;
	private  int sourceid ; 

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals(action_boot)){
			Intent startMytest = new Intent(context, AlarmActivity.class);
			startMytest.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startMytest);
		}
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean status = prefs.getBoolean("status", false);
        if(status){
        	pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);  
        	//播放音频，第二个参数为左声道音量;第三个参数为右声道音量;第四个参数为优先级；
        	//第五个参数为循环次数，0不循环，-1循环;第六个参数为速率，速率    最低0.5最高为2，1代表正常速度
        	sourceid = pool.load(context, R.raw.alarm_ring, 0);
        	pool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
        		
        		public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        			// TODO Auto-generated method stub
        			soundPool.play(sourceid, 2, 2, 0, 1, 1);
        		}
        	});
        }
        
	}
	
}

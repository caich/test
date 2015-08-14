package com.example.alarmstresstest;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{

	private SoundPool pool;
	private  int sourceid ; 
	private MediaPlayer mp;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		/* pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);  
	  //播放音频，第二个参数为左声道音量;第三个参数为右声道音量;第四个参数为优先级；
	 //第五个参数为循环次数，0不循环，-1循环;第六个参数为速率，速率    最低0.5最高为2，1代表正常速度
		sourceid = pool.load(context, R.raw.alarm_ring, 0);
 		pool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
             
 	           public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
 	                    // TODO Auto-generated method stub
 	                    soundPool.play(sourceid, 2, 2, 0, 0, 1);
 	                   }
 	     });*/
		Intent startMytest = new Intent(context, AlarmActivity.class);
		startMytest.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(startMytest);
		
		 try {
			 //Uri uri = Uri.parse("android.resource://com.example.alarmstresstest/raw/alarm_ring");
			 if(mp != null){
				 mp.stop();
			 }
			 mp = MediaPlayer.create(context, R.raw.alarm_ring);
             mp.start();
          } catch (IllegalArgumentException e) {
             e.printStackTrace();
          } catch (IllegalStateException e) {
             e.printStackTrace();
          } catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          mp.setOnCompletionListener(new OnCompletionListener(){
             @Override
             public void onCompletion(MediaPlayer mp) {
            	 mp.reset();
                 mp.release();
             }
          });
        Log.v("AlarmStress", "begin to paly ring");  
	}

}
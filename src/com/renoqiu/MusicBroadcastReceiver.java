package com.renoqiu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MusicBroadcastReceiver extends BroadcastReceiver {
	private static final Object SMSRECEIVED = "com.android.music.metachanged";
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(SMSRECEIVED)){
			String trackName=intent.getStringExtra("track");
			String artist=intent.getStringExtra("artist");

			Intent pushStatusIntent = new Intent();
			pushStatusIntent.setAction("com.renoqiu.pushstatus");
    		Bundle myBundle = new Bundle();
    		myBundle.putString("trackName", trackName);
    		myBundle.putString("artist", artist);
    		pushStatusIntent.putExtras(myBundle);
			context.startService(pushStatusIntent);
		}
	}
}
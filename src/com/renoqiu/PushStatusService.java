package com.renoqiu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class PushStatusService extends Service {
	private Handler handler;
	private com.renoqiu.LooperThread thread;
	private SharedPreferences sharedPreferences;
	private boolean syncFlag;
	private ConnectivityManager cm;
	private NetworkInfo ni;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	private boolean checkNet() {
		cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
	    }
		ni = cm.getActiveNetworkInfo();
		if (ni == null || !ni.isAvailable()) {     
			return false;     
		}     
		return true;     
	}    
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		syncFlag = sharedPreferences.getBoolean("syncFlag",false);
		if(syncFlag && checkNet()){
			if(intent != null){
				Bundle myBundle = intent.getExtras();
				String trackName = myBundle.getString("trackName");
				String artist = myBundle.getString("artist");
				String accessToken = sharedPreferences.getString("accessToken","");
				
				if(accessToken != null && !accessToken.equals("")){
					String requestMethod = "status.set";
					//接口名称  
					String url = StatusPublishHelper.API_URL;
					String secretKey = StatusPublishHelper.SECRET_KEY;
					if(artist == null || artist.equals("")){
						artist = "xxx";
					}
					if(trackName == null || trackName.equals("")){
						trackName = "xxx";
					}
					String message = "我在听" + artist + "的《" + trackName + "》。\r\n 通过我在听发布！";
					thread = new LooperThread(handler, requestMethod, "1.0", url, accessToken, message, secretKey);
					thread.start(); /* 启动线程 */
				}else{
					Toast.makeText(PushStatusService.this, "请登陆！", Toast.LENGTH_SHORT).show();
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putBoolean("syncFlag", false);
					editor.commit();
				}
			}
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 0:
						if((Integer)msg.obj != 1){
							Toast.makeText(PushStatusService.this, "同步失败！请检查网络是否打开...", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(PushStatusService.this, "同步成功！", Toast.LENGTH_SHORT).show();
						}
						break;
				}
			}
		};
	}
}

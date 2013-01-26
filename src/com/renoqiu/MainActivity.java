package com.renoqiu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	private ToggleButton syncToggleButton, syncToggleButtonForWeibo;
	private Button loginBtn, loginBtnForWeibo;
	private SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		sharedPreferences = (SharedPreferences)getSharedPreferences("shared", MODE_PRIVATE);
		//For Renren
		syncToggleButton = (ToggleButton)findViewById(R.id.syncToggleButton);
		boolean syncFlag = sharedPreferences.getBoolean("syncFlag",false);
		syncToggleButton.setChecked(syncFlag);
		syncToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked == false){
					SharedPreferences.Editor editor = sharedPreferences.edit();
	                editor.putBoolean("syncFlag", isChecked);
	                editor.commit();
				}else{
					String accessToken = sharedPreferences.getString("accessToken","");
					if(accessToken != null && !accessToken.equals("") ){
						SharedPreferences.Editor editor = sharedPreferences.edit();
		                editor.putBoolean("syncFlag", isChecked);
		                editor.commit();
					}else{
						syncToggleButton.setChecked(false);
						Toast.makeText(MainActivity.this, "请先登陆！", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
		});
		//For Weibo
		syncToggleButtonForWeibo  = (ToggleButton)findViewById(R.id.syncToggleButtonForWeibo);
		boolean syncFlagForWeibo = sharedPreferences.getBoolean("syncFlagForWeibo",false);
		syncToggleButtonForWeibo.setChecked(syncFlagForWeibo);
		syncToggleButtonForWeibo.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked == false){
					SharedPreferences.Editor editor = sharedPreferences.edit();
	                editor.putBoolean("syncFlagForWeibo", isChecked);
	                editor.commit();
				}else{
					String accessTokenForWeibo = sharedPreferences.getString("accessTokenForWeibo","");
					if(accessTokenForWeibo != null && !accessTokenForWeibo.equals("") ){
						SharedPreferences.Editor editor = sharedPreferences.edit();
		                editor.putBoolean("syncFlagForWeibo", isChecked);
		                editor.commit();
					}else{
						syncToggleButtonForWeibo.setChecked(false);
						Toast.makeText(MainActivity.this, "请先登陆！", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
		});
		//For Renren
		loginBtn = (Button)findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, IamListenActivity.class);
				Bundle myBundle = new Bundle();
				myBundle.putString("snsName", RenrenPublishHelper.MY_NAME);
				intent.putExtras(myBundle);
				startActivity(intent);
			}});
		
		//For Weibo
		loginBtnForWeibo = (Button)findViewById(R.id.loginBtnForWeibo);
		loginBtnForWeibo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, WeiboActivity.class);
				Bundle myBundle = new Bundle();
				myBundle.putString("snsName", WeiboPublishHelper.MY_NAME);
				intent.putExtras(myBundle);
				startActivity(intent);
			}});
	}
}

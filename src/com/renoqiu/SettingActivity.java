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

public class SettingActivity extends Activity {
	private ToggleButton syncToggleButton;
	private Button loginBtn;
	private SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		syncToggleButton = (ToggleButton)findViewById(R.id.syncToggleButton);
		sharedPreferences = (SharedPreferences)getSharedPreferences("shared", MODE_PRIVATE);
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
						Toast.makeText(SettingActivity.this, "请先登陆！", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
		});
		loginBtn = (Button)findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SettingActivity.this, IamListenActivity.class);
				startActivity(intent);
			}});
	}
}

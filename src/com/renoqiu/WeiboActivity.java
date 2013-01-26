package com.renoqiu;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.net.RequestListener;

public class WeiboActivity extends Activity {
	private WebView webView;
    private Weibo mWeibo;
    //rivate Button authBtn, apiBtn, ssoBtn, cancelBtn;
    //private TextView mText;
    public static Oauth2AccessToken accessToken;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mWeibo = Weibo.getInstance(WeiboPublishHelper.CONSUMER_KEY, WeiboPublishHelper.REDIRECT_URL);
		mWeibo.authorize(WeiboActivity.this, new AuthDialogListener());
		webView = (WebView) findViewById(R.id.web);
	}
	
	class AuthDialogListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            WeiboActivity.accessToken = new Oauth2AccessToken(token, expires_in);
            if (WeiboActivity.accessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                        .format(new java.util.Date(WeiboActivity.accessToken
                                .getExpiresTime()));
                Toast.makeText(WeiboActivity.this, "认证成功: \r\n access_token: " + token + "\r\n"
                        + "expires_in: " + expires_in + "\r\n有效期：" + date, Toast.LENGTH_SHORT).show();
            }
            SharedPreferences settings = (SharedPreferences)getSharedPreferences("shared", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            Log.v("com.renoqiu",WeiboActivity.accessToken.toString());
            editor.putString("accessTokenForWeibo", token);
    		editor.putString("expiresTimeForWeibo", expires_in);
    		editor.putBoolean("syncFlagForWeibo", false);
            editor.commit();
            finish();
        }

        @Override
        public void onError(WeiboDialogError e) {
            Toast.makeText(getApplicationContext(),
                    "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Auth cancel",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getApplicationContext(),
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }
}
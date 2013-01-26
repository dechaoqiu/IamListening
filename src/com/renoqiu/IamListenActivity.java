package com.renoqiu;

import java.net.URLDecoder;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class IamListenActivity extends Activity {
	private WebView webView;
    private String accessToken = null;  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		webView = (WebView) findViewById(R.id.web);
		WebSettings settings = webView.getSettings();  
        settings.setJavaScriptEnabled(true);  
        settings.setSupportZoom(true);  
        settings.setBuiltInZoomControls(true);  
        webView.loadUrl(RenrenPublishHelper.AUTHURL);
        webView.requestFocusFromTouch();  
        WebViewClient wvc = new WebViewClient() {  
            @Override  
            public void onPageFinished(WebView view, String url) {  
                super.onPageFinished(view, url);  
                //人人网用户名和密码验证通过后，刷新页面时即可返回accessToken  
                String reUrl = webView.getUrl();  
                if (reUrl != null && reUrl.indexOf("access_token") != -1) {  
                    //截取url中的accessToken  
                    int startPos = reUrl.indexOf("token=") + 6;  
                    int endPos = reUrl.indexOf("&expires_in");  
                    accessToken = URLDecoder.decode(reUrl.substring(startPos, endPos));
                    //保存获取到的accessToken  
                    //share.saveRenrenToken(accessToken);  
                    Toast.makeText(IamListenActivity.this, "验证成功，设置同步后。\n听歌时就能自动传状态哦。:)", Toast.LENGTH_SHORT).show();
                    SharedPreferences settings = (SharedPreferences)getSharedPreferences("shared", MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("accessToken", accessToken);
                    editor.putBoolean("syncFlag", false);
                    editor.commit();
                    finish();
                }
            }  
        };
        webView.setWebViewClient(wvc);
	}
}
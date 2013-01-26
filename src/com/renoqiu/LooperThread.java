package com.renoqiu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.renoqiu.util.MD5Helper;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LooperThread extends Thread{
	private String requestMethod;
	private String v;
	private String url;
	private String accessToken;
	private String status;
	private String secretKey;
	private Handler fatherHandler;
	public LooperThread(Handler fatherHandler, String requestMethod, String v, String url, String accessToken, String message, String secretKey) {
		this.requestMethod = requestMethod;
		this.v = v;
		this.url = url;
		this.accessToken = accessToken;
		this.status = message; 
		this.secretKey = secretKey;
		this.fatherHandler = fatherHandler;
	}

	public void run() {
		Message msg = new Message();
		msg.obj = updateStatus(status);
		msg.what = 0;
		fatherHandler.sendMessage(msg);
	}
	public  int updateStatus(String status) {  
		int success = 0;
        //生成签名 字典序排列
        StringBuilder sb = new StringBuilder();  
        sb.append("access_token=").append(accessToken)  
            .append("format=").append("JSON")  
            .append("method=").append(requestMethod)  
            .append("status=").append(status)  
            .append("v=").append(v)
            .append(secretKey);
        String sig = MD5Helper.getMD5(sb.toString());  

        HttpPost httpRequest = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("access_token", accessToken));
		params.add(new BasicNameValuePair("method", requestMethod)); 
		params.add(new BasicNameValuePair("v", v)); 
		params.add(new BasicNameValuePair("status", status)); 
		params.add(new BasicNameValuePair("format", "JSON"));
		params.add(new BasicNameValuePair("sig", sig));
		 try { 
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200){
				String result = EntityUtils.toString(httpResponse .getEntity());
				 JSONObject json = new JSONObject(result);
				 success = (Integer)json.get("result");
				 Log.v("org.reno", result);
			}
        }catch (Exception e){
			return success;
		} 
  
        return success;  
    }
}

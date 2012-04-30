package com.renoqiu;

import java.security.MessageDigest;

public class StatusPublishHelper {
	// 你的应用ID
	public static final String APP_ID = "";
	// 应用的API Key
	public static final String API_KEY = "";
	// 应用的Secret Key
	public static final String SECRET_KEY = "";
	
	public static final String API_URL = "http://api.renren.com/restserver.do";
	public static final String AUTHURL = "https://graph.renren.com/oauth/authorize?client_id="  
	        + API_KEY +"&response_type=token"  
	        + "&redirect_uri=http://www.renoqiu.com/iamlisten.html&display=touch"  
	        + "&scope=status_update";  
	public static String getMD5(String s) {  
        try {  
            MessageDigest md5 = MessageDigest.getInstance("MD5");  
  
            byte[] byteArray = s.getBytes("UTF-8");  
            byte[] md5Bytes = md5.digest(byteArray);  
  
            StringBuffer hexValue = new StringBuffer();  
  
            for (int i = 0; i < md5Bytes.length; i++) {  
                int val = ((int) md5Bytes[i]) & 0xff;  
                if (val < 16)  
                    hexValue.append("0");  
                hexValue.append(Integer.toHexString(val));  
            }  
  
            return hexValue.toString();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }
    }
}

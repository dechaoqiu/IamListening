package com.renoqiu;

public class RenrenPublishHelper implements IPublishHelper {
	public static final String MY_NAME = "RENREN";
	// 你的应用ID
	public static final String APP_ID = "191350";
	// 应用的API Key
	public static final String API_KEY = "d4e354717850425ca09161ca455bd497";
	// 应用的Secret Key
	public static final String SECRET_KEY = "c2ba979f994b43bbbb0c9cc9c4ac6dbd";
	
	public static final String API_URL = "http://api.renren.com/restserver.do";
	public static final String AUTHURL = "https://graph.renren.com/oauth/authorize?client_id="  
	        + API_KEY +"&response_type=token"  
	        + "&redirect_uri=http://www.renoqiu.com/iamlisten.html&display=touch"  
	        + "&scope=status_update";  
	
}

/**
 * 发送短信
 * @author tcmld
 * @create time 2014/2/8
 */
package net.htwater.Util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.miao.framework.endec.CNEncoder;
import cn.miao.framework.util.StringUtil;


	public class SendMessage {
		/**
		 * 发送短信消息
		 * @param phonenum 手机号码
		 * @param message 信息
		 *
		 */

		final static PropertiesHelper propHelper = new PropertiesHelper("/htpms.properties");

		final static String apiUrl = propHelper.getPropertyValue("htpms.url");
		final static String key = propHelper.getPropertyValue("htpms.key");
		final static String secret = propHelper.getPropertyValue("htpms.secret");
		
		/**
		 * 服务地址
		 */
		//final static String APIURL = "http://www.htwater.net:8181/htpms/";
		
		//下面为fanghaojie
		//final static String APIKEY = "549ba0c6-2be2-4cb9-9a34-5b6c5cb3c1e1"; 
		//final static String APISECRET = "1CB9A2C1BD4EB9A6A17F46B14F4BC105";
		public void YSendMessage2(String PhoneNum,String Message){
		Message = StringUtil.urlEncode(Message);
		String str=apiUrl+"sendSMS!public?phonenos="+PhoneNum+"&content="+Message+"&password=tongtech";
		 try {
			 URL url = new URL(str);
	/*		 System.out.println(str);*/
			  HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			  conn.connect();
			  InputStream is = conn.getInputStream();
			  BufferedReader br = new BufferedReader(new InputStreamReader(is,"gb2312"));
			  String line="";
			  while((line = br.readLine())!=null){
			    
			  }
			  br.close();
			  is.close();
			  conn.disconnect();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 短信发送Demo
	 * 
	 * @return void
	 */
	public String YSendMessage(String PhoneNum,String Message) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("phonenos", PhoneNum);
		paramsMap.put("content", CNEncoder.encode(Message));
		String result = cn.miao.framework.util.HttpUtil.htOauthRequest(apiUrl, "sendSMS", paramsMap,
				key, secret);
		System.out.println(result);
		return result;
		
	}
	/**
	 * 邮件demo
	 * 
	 * @return void
	 */
	public static void emailTest() {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("emailAddrs", "nealnb@qq.com");
		paramsMap.put("title", CNEncoder.encode("测试邮件"));
		paramsMap.put("content", CNEncoder.encode("测试邮件发给nealnb@qq.com，请勿回复。"));
		String result = cn.miao.framework.util.HttpUtil.htOauthRequest(apiUrl, "sendEmails",
				paramsMap, key, secret);
		System.out.println(result);
	}

	}

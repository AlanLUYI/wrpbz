/*
 * version date author 
 * 鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢鈹�攢 
 * 1.0  2013-3-5 Neal Miao 
 * 
 * Copyright(c) 2013, by htwater.net. All Rights Reserved.
 */
package net.htwater.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import com.sun.jndi.toolkit.url.UrlUtil;

import cn.miao.framework.endec.CNEncoder;
import cn.miao.framework.endec.MD5;
import cn.miao.framework.util.HttpUtil;

/**
 * 
 * 
 * @author Neal Miao
 * @version
 * @Date 2013年12月13日 上午10:16:58
 * 
 * @see
 */
public class TestService {
	final static String apiUrl = "http://192.168.100.4:8181/htpms/";
	final static String key = "fcb1c7ce-6594-4d62-85e6-f1480d0599e4";
	final static String secret = "53EAC0D8DE662B49ACD3FAB8B4598A2C";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
		//String urlString="http://域名:8080/zhsl/QueryResSupport!SYQ?cache=true";
		//String returnstr=HttpUtil.getResponseFromWebByGET(urlString);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("tm1", "2016-1-13%2008:00");
		params.put("tm2", "2016-1-16%2008:00");
		params.put("tm", "2016-3-15%2017:05");
		params.put("path", "nb");
		params.put("district","xs");
		String result=HttpUtil.htOauthRequest(apiUrl,
				"getSTRiverInfo", params, key, secret);
		System.out.println(result);
		
//		Map<String, String> p1 = new HashMap<String, String>();
//		p1.put("tm1", "2015-5-10");
//		p1.put("tm2", "2015-5-13");
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"queryISO",p1, key, secret);
//		System.out.println("data is:"+resultstr);
		
		//System.out.println(UrlUtil.encode("2015 05 :", "UTF-8"));
		//System.out.println(URLEncoder.encode("2015 05 :","UTF-8"));
		
//		String result = HttpUtil.getResponseByApacheGET("http://localhost:8080/shanhong/ModulePageAuth?"
//				+"appcode=A0C35D26-7596-4442-94A1-D52280AE7948"
//				+"&module=dotRain"
//				+"&user=fxtadmin"
//				+"&region=yz");
//		System.out.println(result);
		
//		String url = getModuleLink(
//				"http://域名:8080/shanhong/ModulePageAuth",
//				"D466EB13-0AC7-4515-8C13-346BD93B0D6B",
//				"fx_zbap",//模块编码
//				"shanhong_duty_user",//您的系统的当前登陆用户
//				"nbslj");//您的系统的当前登陆用户所属区县
//		System.out.println(url);
	}
	/**
	 * @param host 验证服务器的地址
	 * @param key 对第三方平台授权的密钥（请向管理员申请）
	 * @param module 要访问的模块编码（请向管理员申请）
	 * @param user 第三方平台中当前登陆的用户
	 * @param region 第三方平台当前登陆用户所属区县（nbslj宁波市，hs海曙区，yz鄞州区， jb江北区，jd江东区...）
	 * @return 您要访问的模块网址（内涵验证码）
	 */
	public static String getModuleLink(String host,String key,String module,String user,String region) {
		String url=host;
		String param = 
				"appcode="+key
				+"&module="+module
				+"&user="+user
				+"&region="+region;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    } 
}

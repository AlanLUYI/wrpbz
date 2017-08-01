package net.htwater.hos.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.Util.PropertiesHelper;
import net.htwater.hos.helper.impl.HtOauthServiceImpl;
import net.htwater.hos.service.FxywService;
import net.htwater.hos.service.SlgcService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.HttpUtil;

public class PublicAction extends DoAction {
	PropertiesHelper propHelper = new PropertiesHelper("/htpms.properties");

	String apiUrl = propHelper.getPropertyValue("htpms.url");
	String key = propHelper.getPropertyValue("htpms.key");
	String secret = propHelper.getPropertyValue("htpms.secret");
	
	HtOauthServiceImpl htOSImpl = new HtOauthServiceImpl();
	/**
	 * 多条件检索水库工程
	 * @return
	 */
	public Responser searchSLGC(){
		String where = "where " + params.getParam("where");
		String type = params.getParam("type");
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		if(type.equals("res")){
			list = service.searchRes(where);
		}else if(type.equals("gate")){
			list = service.searchGate(where);
		}else if(type.equals("pump")){
			list = service.searchPump(where);
		}else if(type.equals("dike")){
			list = service.searchDike(where);
		}else if(type.equals("seawall")){
			list = service.searchSeawall(where);
		}else if(type.equals("gen")){
			list = service.searchGen(where);
		}else if(type.equals("station")){
			list = service.searchStation(where);
		}else if(type.equals("pool")){
			list = service.searchPool(where);
		}else if(type.equals("mriver")){
			list = service.searchMRiver(where);
		}else if(type.equals("priver")){
			list = service.searchPRiver(where);
		}else if(type.equals("valley")){
			list = service.searchValley(where);
		}
		
		responser.setRtType(JSON); 
		responser.setRtString(parseJSON(list)); 
		return responser;
	}
	
	/**
	 * 以工程名称检索水利工程
	 * @return
	 */
	public Responser searchSLGCByName(){
		String name = params.getParam("name");
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		Map<String,Object> map = service.searchAllByName(name);
		
		responser.setRtType(JSON); 
		responser.setRtString(parseJSON(map)); 
		return responser;
	}

	//获取值班情况
		public Responser queryFORduty() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			responser.setRtString(parseJSON(service.queryFORduty(
								
			)));
			return responser;
		}
		public Responser queryFORdutybyid() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			responser.setRtString(parseJSON(service.queryFORdutybyid(
					params.getParam("id")		
			)));
			return responser;
		}
		public Responser queryFORXML() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			responser.setRtString(service.queryFORXML(	
					params.getParam("id")
			));
			return responser;
		}
		//交接班
		/*public Responser updateFORdutychange() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.updateFORdutychange(
					params.getParam("id"),
					params.getParam("name"),
					params.getParam("password"),
					params.getParam("duty")
			);
			String str1="{\"ok\":true,\"message\":\"登陆成功\"}";
			String str2="{\"ok\":false,\"message\":\"密码错误\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}*/
		//传真状态信息
		public Responser queryFORfaxresult() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			responser.setRtString(service.queryFORfaxresult(	
					params.getParam("id")
			));
			return responser;
		}	
		//获取未处理日志
		public Responser queryFORlog() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			responser.setRtString(parseJSON(service.queryFORlog(
								
			)));
			return responser;
		}
		public Responser queryforlogbytime() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			responser.setRtString(parseJSON(service.queryforlogbytime(
					params.getParam("time1"),
					params.getParam("time2")
			)));
			return responser;
		}
		/*public Responser updateFORdutyin() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.updateFORdutyin(
					params.getParam("id"),
					params.getParam("type"),
					params.getParam("password"),
					params.getParam("man_name"),
					params.getParam("man_id")
			);
			String str1="{\"ok\":true,\"message\":\"登陆成功\"}";
			String str2="{\"ok\":false,\"message\":\"密码错误\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}*/
		/*public Responser updateFORdutyout() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.updateFORdutyout(
					params.getParam("id"),
					params.getParam("password"),
					params.getParam("man_name"),
					params.getParam("man_id")
			);
			String str1="{\"ok\":true,\"message\":\"登出成功\"}";
			String str2="{\"ok\":false,\"message\":\"密码错误\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}*/
		public Responser updateFORlog() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.updateFORlog(
					params.getParam("type"),
					params.getParam("shijian"),
					params.getParam("radio_d"),
					params.getParam("radio_n"),
					params.getParam("yijian"),
					params.getParam("time")
			);
			String str1="{\"ok\":true,\"message\":\"提交成功\"}";
			String str2="{\"ok\":false,\"message\":\"提交失败，请联系管理员\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}
		public Responser updateFORlogdo() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.updateFORlogdo(
					params.getParam("id")
			);
			String str1="{\"ok\":true,\"message\":\"提交成功\"}";
			String str2="{\"ok\":false,\"message\":\"提交失败，请联系管理员\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}
		public Responser queryFORchange() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.queryFORchange(
					params.getParam("id")
			);
			String str1="{\"ok\":true,\"message\":\"提交成功\"}";
			String str2="{\"ok\":false,\"message\":\"未到换班时间\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}
		public Responser queryFORzbbcx() {
			responser.setRtType(JSON);
			String url = getModuleLink(
					"http://www.htwater.net:8080/shanhong/ModulePageAuth",
					"D466EB13-0AC7-4515-8C13-346BD93B0D6B",
					"modules/fxyw/modules/fx_zbap.html",//模块编码
					"shanhong_duty_user",//您的系统的当前登陆用户
					"nbslj");//您的系统的当前登陆用户所属区县
			System.out.println(url);		
			String str="{\"url\":\""+url+"\"}";
			responser.setRtString(str);
			return responser;
		}
		public Responser queryFORtongxunlu() {
			responser.setRtType(JSON);
			String url = getModuleLink(
					"http://www.htwater.net:8080/shanhong/ModulePageAuth",
					"D466EB13-0AC7-4515-8C13-346BD93B0D6B",
					"modules/yw_phone/index.html",//模块编码
					"shanhong_duty_user",//您的系统的当前登陆用户
					"nbslj");//您的系统的当前登陆用户所属区县
			System.out.println(url);		
			String str="{\"url\":\""+url+"\"}";
			responser.setRtString(str);
			return responser;
		}
		/**
		 * @param host 验证服务器的地址
		 * @param key 对第三方平台授权的密钥（请向管理员申请）
		 * @param module 要访问的模块编码（请向管理员申请）
		 * @param user 第三方平台中当前登陆的用户
		 * @param region 第三方平台当前登陆用户所属区县（nbslj宁波市，hs海曙区，yz鄞州区， jb江

北区，jd江东区...）
		 * @return 您要访问的模块网址（内涵验证码）
		 */
		public static String getModuleLink(String host,String key,String module,String 

user,String region) {
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

		public Responser updateFAXresult() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.updateFAXresult(
					params.getParam("result"),
					params.getParam("id")
			);
			String str1="{\"ok\":true,\"message\":\"提交成功\"}";
			String str2="{\"ok\":false,\"message\":\"提交失败\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}
	
	//报警概况
	public Responser getAlertInfo() {
		Map<String,Object> result= new HashMap<String,Object>();
//		String resultRain = HttpUtil.htOauthRequest(apiUrl, "getDistrictStatics", key,
//				secret);
		String resultRain = parseJSON(htOSImpl.getDistrictStatics());
		JSONArray jsonRain = JSONArray.fromObject(resultRain);
		
//		String resultWater=HttpUtil.htOauthRequest(apiUrl,
//				"getAlertRiverInfo",key, secret);
		String resultWater = parseJSON(htOSImpl.getWarnRivers());
		JSONArray jsonWater = JSONArray.fromObject(resultWater);
		
		Map<String, List<Map<String, Object>>> rtMap = 
				new HashMap<String, List<Map<String, Object>>>();
		rtMap.put("BM", htOSImpl.getWarnBigRsvrs());
		rtMap.put("S1", htOSImpl.getWarnSmallRsvrs("s1"));
		rtMap.put("S2",htOSImpl.getWarnSmallRsvrs("s2"));
//		String resultRsvr=HttpUtil.htOauthRequest(apiUrl,
//				"getAlertRsvrInfo",key, secret);
		String resultRsvr = parseJSON(rtMap);
		JSONObject jsonRsvr = JSONObject.fromObject(resultRsvr);
		
		double rain1=0,rain3=0,rain7=0;
		for(int i=0;i<jsonRain.size();i++){
			rain1+=Double.parseDouble(((JSONObject)jsonRain.get(i)).get("todayRain").toString());
			rain3+=Double.parseDouble(((JSONObject)jsonRain.get(i)).get("totalRain").toString());
			rain7+=Double.parseDouble(((JSONObject)jsonRain.get(i)).get("sum7").toString());
		}
		rain1=rain1/jsonRain.size();
		rain3=rain3/jsonRain.size();
		rain7=rain7/jsonRain.size();
		
		DecimalFormat df = new DecimalFormat("0.0");
		result.put("rain1", df.format(rain1));
		result.put("rain3", df.format(rain3));
		result.put("rain7", df.format(rain7));
		result.put("water",Integer.toString(jsonWater.size()) );
		result.put("rsvr", Integer.toString(((JSONArray)jsonRsvr.get("BM")).size()) );
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	}

	//获取水利工程经纬度
	public Responser getLocation(){
		String ennmcd = params.getParam("ennmcd");
		String type = params.getParam("type");
		
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		Map<String,Object> resultMap= service.getLocation(ennmcd, type);
		
		String rtString=parseJSON(resultMap);
		
		responser.setRtType(JSON);
		responser.setRtString(rtString);
		return responser;
	}
}

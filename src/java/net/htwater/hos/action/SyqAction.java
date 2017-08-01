/**
 * 水雨情服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.hos.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import net.htwater.Util.HTMLFormatter;
import net.htwater.Util.PropertiesHelper;
import net.htwater.hos.helper.HtOauthHelper;
import net.htwater.hos.helper.impl.HtOauthServiceImpl;
import net.htwater.hos.helper.impl.RainWaterServiceImpl;
import net.htwater.hos.service.SyqService;
import net.htwater.hos.service.UserService;
import net.sf.json.JSONArray;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.cache.CacheUtil;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.DateUtil;
import cn.miao.framework.util.HttpUtil;
import cn.miao.framework.util.PinyinUtil;


public class SyqAction extends DoAction {
	PropertiesHelper propHelper = new PropertiesHelper("/htpms.properties");

	String apiUrl = propHelper.getPropertyValue("htpms.url");
	String key = propHelper.getPropertyValue("htpms.key");
	String secret = propHelper.getPropertyValue("htpms.secret");
	
	HtOauthServiceImpl hOSImpl = new HtOauthServiceImpl();
	/**
	 * 风向风速
	 * POWER风力，VALUE风速，DIR风向
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Querywind() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		List<Map<String,Object>> result = service.Querywind(params.getParam("TM"));
		
		//deal focus
		UserService service_user = (UserService) ServiceFactory.getService("user");
		service_user.dealFocusField(result, "STCD", 
				session.getAttribute("token").toString(), "wind");
		for(Map<String,Object> m : result){
			m.put("_s", m.get("STNM").toString()+PinyinUtil.converterToPinYin(m.get("STNM").toString()));
		}
		
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 流量过程
	 * z水位/潮位,q流量
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryll_line() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);
		
		
		
		responser.setRtString(parseJSON(service.Queryll_line(params.getParam("TM1"),params.getParam("TM2"),params.getParam("stcd")))); 
		return responser;
	}
	
	/**
	 * 流量流速
	 * z水位/潮位,q流量,xsavv流速,xsa断面面积
	 * @Date 2014-02-17
	 * @since v 1.0
	 */
	public Responser Queryll() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);
		
		List<Map<String,Object>> result = service.Queryll(params.getParam("TM"));
		//deal focus
		UserService service_user = (UserService) ServiceFactory.getService("user");
		service_user.dealFocusField(result, "stcd", 
				session.getAttribute("token").toString(), "flow");
		responser.setRtString(parseJSON(result)); 
		
		//responser.setRtString(parseJSON(service.Queryll(params.getParam("TM")))); 
		return responser;
	}
	
	/**
	 * 水库河道水位/潮位过程
	 * 参数说明：sttp站点类型，RS水库,RV河道,TT潮位
	 * z水位/潮位
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryswcw_line() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryswcw_line(params.getParam("TM1"),params.getParam("TM2"),params.getParam("stcd"),params.getParam("sttp")))); 
		return responser;
	}
	
	/**
	 * 水库实时水位
	 * Z水位，ZKR库容，Z8八点水位，Z8SK8点库容，CTZ控制水位，KZKR控制水位，RATIO实时库容占控制库容比例，TYPE ‘B大型M中型S1小一S2小二，ORDNO排序’
	 * fhgsw防洪高水位，sjsw设计水位，zcsw正常水位，jhsw校核水位
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryreswater() {  
//		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
//		List<Map<String,Object>> result = service.Queryreswater(params.getParam("TM"));
		/*
		String region=session.getAttribute("region").toString();
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		Map<String, String> p1 = new HashMap<String, String>();
		p1.put("tm", params.getParam("TM").toString());
		if(!region.equals("nbslj")) p1.put("district", region);
		String mathod = "getSTBigMidRsvrInfoByTm";
		String realtime = params.getParam("realtime");
		if(realtime != null && realtime.equals("true")){
			mathod = "getSTBigMidRsvrInfo";
			//大中型
			//String resultstr=HttpUtil.htOauthRequest(apiUrl,
			//		mathod,p1, key, secret);
			JSONArray jsonArray = JSONArray.fromObject(resultstr);
			//小一型
			mathod = "getSTSmall1RsvrInfoByTm";
			if(realtime != null && realtime.equals("true")) mathod = "getSTSmall1RsvrInfo";
			//resultstr=HttpUtil.htOauthRequest(apiUrl,
			//		mathod,p1, key, secret);
			JSONArray jsonArray_S1 = JSONArray.fromObject(resultstr);
			jsonArray.addAll(jsonArray_S1);
			//小二型
			mathod = "getSTSmall2RsvrInfoByTm";
			if(realtime != null && realtime.equals("true")) mathod = "getSTSmall2RsvrInfo";
			//resultstr=HttpUtil.htOauthRequest(apiUrl,
			//		mathod,p1, key, secret);
			JSONArray jsonArray_S2 = JSONArray.fromObject(resultstr);
			jsonArray.addAll(jsonArray_S2);
			//翻译成我自己的格式
			for(Object obj : jsonArray.toArray()){
				Map<String,Object> map=(Map<String,Object>)obj;
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("STCD", map.get("stcd").toString().split("-")[0].trim());
				String stnm = HTMLFormatter.removeHtmlTag(map.get("stnm").toString().trim());
				m.put("STNM", stnm);
				m.put("CITY", map.get("district"));
				m.put("Z", Double.valueOf(map.get("stsw").toString()));
				m.put("ZKR", Double.valueOf(map.get("stkr").toString()));
				m.put("Z8", Double.valueOf(map.get("m8sw").toString()));
				m.put("Z8KR", Double.valueOf(map.get("m8kr").toString()));
				m.put("CTZ", map.get("kzsw")==null||map.get("kzsw").toString().equals("")?null:Double.valueOf(map.get("kzsw").toString()));
				m.put("KZKR", map.get("kzkr")==null||map.get("kzkr").toString().equals("")?null:Double.valueOf(map.get("kzkr").toString()));
				String ratioString = map.get("ratio").toString();ratioString = HTMLFormatter.removeHtmlTag(ratioString);
				m.put("RATIO", map.get("ratio")==null||map.get("ratio").toString().equals("")?null:Double.valueOf(ratioString.replaceAll("%", ""))/100);
				m.put("TYPE", map.get("rsv_type"));
				m.put("LGTD", map.get("longitude"));
				m.put("LATD", map.get("latitude"));
				m.put("fhgsw", map.get("floodhigh_level")==null||map.get("floodhigh_level").toString().equals("")||map.get("floodhigh_level").toString().equals("-1")?null:Double.valueOf(map.get("floodhigh_level").toString()));
				m.put("zcsw", map.get("nor_level")==null||map.get("nor_level").toString().equals("")||map.get("nor_level").toString().equals("-1")?null:Double.valueOf(map.get("nor_level").toString()));
				m.put("_s", map.get("code"));
				result.add(m);
			}
		}else{
			SyqService service = (SyqService) ServiceFactory.getService("SyqService");
			result = service.Queryreswater(params.getParam("TM"));
		}
		
		
		//deal focus
		UserService service_user = (UserService) ServiceFactory.getService("user");
		service_user.dealFocusField(result, "STCD", 
				session.getAttribute("token").toString(), "water_run_res");
		responser.setRtString(parseJSON(result)); 
		
		responser.setRtType(JSON); 
		return responser;
		*/
		
		//方式2：get result
		String region=session.getAttribute("region").toString();
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		Map<String, String> p1 = new HashMap<String, String>();
		String tm = params.getParam("TM").toString();
		p1.put("tm", tm);
		if(!region.equals("nbslj")) p1.put("district", region);
		String mathod = "getSTBigMidRsvrInfoByTm";
		String realtime = params.getParam("realtime");
		JSONArray jsonArray = new JSONArray();
		if(realtime != null && realtime.equals("true")){ //mathod = "getSTBigMidRsvrInfo";
			tm = DateUtil.date2Str(new Date(),	"yyyy-MM-dd HH:mm:ss");
		}
			//大中型
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				mathod,p1, key, secret);
		String resultstr = parseJSON(hOSImpl.getSTBigMidRsvrInfo(tm));
		jsonArray = JSONArray.fromObject(resultstr);
		//小一型
		//mathod = "getSTSmall1RsvrInfoByTm";
		//if(realtime != null && realtime.equals("true")) mathod = "getSTSmall1RsvrInfo";
//		resultstr = HttpUtil.htOauthRequest(apiUrl,
//				mathod,p1, key, secret);
		resultstr = parseJSON(hOSImpl.getSTSmallRsvrInfo("HT_RSVRS1_B",tm));
		JSONArray jsonArray_S1 = JSONArray.fromObject(resultstr);
		jsonArray.addAll(jsonArray_S1);
		//小二型
		//mathod = "getSTSmall2RsvrInfoByTm";
		//if(realtime != null && realtime.equals("true")) mathod = "getSTSmall2RsvrInfo";
		//resultstr=HttpUtil.htOauthRequest(apiUrl,
		//		mathod,p1, key, secret);
		resultstr = parseJSON(hOSImpl.getSTSmallRsvrInfo("HT_RSVRS2_B",tm));
		JSONArray jsonArray_S2 = JSONArray.fromObject(resultstr);
		jsonArray.addAll(jsonArray_S2);
		
		//翻译成我自己的格式
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> map=(Map<String,Object>)obj;
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("STCD", map.get("stcd").toString().split("-")[0].trim());
			String stnm = HTMLFormatter.removeHtmlTag(map.get("stnm").toString().trim());
			m.put("STNM", stnm);
			m.put("CITY", map.get("district"));
			m.put("Z", Double.valueOf(map.get("stsw").toString()));
			m.put("ZKR", Double.valueOf(map.get("stkr").toString()));
			m.put("Z8", Double.valueOf(map.get("m8sw").toString()));
			m.put("Z8KR", Double.valueOf(map.get("m8kr").toString()));
			m.put("CTZ", map.get("kzsw")==null||map.get("kzsw").toString().equals("")?null:Double.valueOf(map.get("kzsw").toString()));
			m.put("KZKR", map.get("kzkr")==null||map.get("kzkr").toString().equals("")?null:Double.valueOf(map.get("kzkr").toString()));
			String ratioString = map.get("ratio").toString();ratioString = HTMLFormatter.removeHtmlTag(ratioString);
			m.put("RATIO", map.get("ratio")==null||map.get("ratio").toString().equals("")?null:Double.valueOf(ratioString.replaceAll("%", ""))/100);
			m.put("TYPE", map.get("rsv_type"));
			m.put("LGTD", map.get("longitude"));
			m.put("LATD", map.get("latitude"));
			m.put("fhgsw", map.get("floodhigh_level")==null||map.get("floodhigh_level").toString().equals("")||map.get("floodhigh_level").toString().equals("-1")?null:Double.valueOf(map.get("floodhigh_level").toString()));
			m.put("zcsw", map.get("nor_level")==null||map.get("nor_level").toString().equals("")||map.get("nor_level").toString().equals("-1")?null:Double.valueOf(map.get("nor_level").toString()));
			m.put("_s", map.get("code"));
			result.add(m);
		}
		
		//deal focus
		UserService service_user = (UserService) ServiceFactory.getService("user");
		service_user.dealFocusField(result, "STCD", 
				session.getAttribute("token").toString(), "water_run_res");
		responser.setRtString(parseJSON(result)); 
		
		responser.setRtType(JSON); 
		return responser;
		
	}
	/**
	 * 大中型水库水情
	 * @return
	 */
	public Responser QueryreswaterBM() {
		
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		Map<String, String> p1 = new HashMap<String, String>();
		String tm =  params.getParam("TM").toString();
		p1.put("tm", tm);
		
		String mathod = "getSTBigMidRsvrInfoByTm";
		String realtime = params.getParam("realtime");
		if(realtime != null && realtime.equals("true")){// mathod = "getSTBigMidRsvrInfo";
			tm =  DateUtil.date2Str(new Date(),	"yyyy-MM-dd HH:mm:ss");
		}//大中型
		//String resultstr=HttpUtil.htOauthRequest(apiUrl,
		//		mathod,p1, key, secret);
		String resultstr = parseJSON(hOSImpl.getSTBigMidRsvrInfo(tm));
		JSONArray jsonArray = JSONArray.fromObject(resultstr);
		
		//翻译成我自己的格式
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> map=(Map<String,Object>)obj;
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("STCD", map.get("stcd").toString().split("-")[0].trim());
			String stnm = HTMLFormatter.removeHtmlTag(map.get("stnm").toString().trim());
			m.put("STNM", stnm);
			m.put("CITY", map.get("district"));
			m.put("Z", Double.valueOf(map.get("stsw").toString()));
			m.put("ZKR", Double.valueOf(map.get("stkr").toString()));
			m.put("Z8", Double.valueOf(map.get("m8sw").toString()));
			m.put("Z8KR", Double.valueOf(map.get("m8kr").toString()));
			m.put("CTZ", map.get("kzsw")==null||map.get("kzsw").toString().equals("")?null:Double.valueOf(map.get("kzsw").toString()));
			m.put("KZKR", map.get("kzkr")==null||map.get("kzkr").toString().equals("")?null:Double.valueOf(map.get("kzkr").toString()));
			String ratioString = map.get("ratio").toString();ratioString = HTMLFormatter.removeHtmlTag(ratioString);
			m.put("RATIO", map.get("ratio")==null||map.get("ratio").toString().equals("")?null:Double.valueOf(ratioString.replaceAll("%", ""))/100);
			m.put("TYPE", map.get("rsv_type"));
			m.put("LGTD", map.get("longitude"));
			m.put("LATD", map.get("latitude"));
			m.put("fhgsw", map.get("floodhigh_level")==null||map.get("floodhigh_level").toString().equals("")||map.get("floodhigh_level").toString().equals("-1")?null:Double.valueOf(map.get("floodhigh_level").toString()));
			m.put("zcsw", map.get("nor_level")==null||map.get("nor_level").toString().equals("")||map.get("nor_level").toString().equals("-1")?null:Double.valueOf(map.get("nor_level").toString()));
			m.put("_s", map.get("code"));
			result.add(m);
		}
		
		responser.setRtString(parseJSON(result)); 
		
		responser.setRtType(JSON); 
		return responser;
	}
	
	/**
	 * 三江干流水位
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return  Z水位，WRZ警戒水位，GRZ保证水位，TREND涨落趋势
	 */
	public Responser QueryMRvWater() {  
		//方式1：get result
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);
		
		List<Map<String,Object>> result = service.QueryMRvWater(params.getParam("TM"));
		//deal focus
		UserService service_user = (UserService) ServiceFactory.getService("user");
		service_user.dealFocusField(result, "STCD", 
				session.getAttribute("token").toString(), "water_run_main");
		responser.setRtString(parseJSON(result)); 
		
		//responser.setRtString(parseJSON(service.QueryMRvWater(params.getParam("TM")))); 
		return responser;
	}
	
	/**
	 * 平原水位
	 * Z水位，WRZ警戒水位，GRZ保证水位，TREND涨落趋势，ISRCONTROL是否控制站
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 * zzj 2014.6.25 修改为从数据中心读取
	 */
	public Responser QueryPRvWater() {  
		//方式1：get result
		//SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		//List<Map<String,Object>> result = service.QueryPRvWater(params.getParam("TM"));
		/*
		String region=session.getAttribute("region").toString();
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		Map<String, String> p1 = new HashMap<String, String>();
		p1.put("tm", params.getParam("TM").toString());
		if(!region.equals("nbslj")) p1.put("district", region);
		String mathod = "getSTRiverInfoByTm";
		String realtime = params.getParam("realtime");
		if(realtime != null && realtime.equals("true")){
			mathod = "getSTRiverInfo";
			//String resultstr=HttpUtil.htOauthRequest(apiUrl,
			//		mathod,p1, key, secret);
			JSONArray jsonArray = JSONArray.fromObject(resultstr);
			for(Object obj : jsonArray.toArray()){
				Map<String,Object> map=(Map<String,Object>)obj;
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("STCD", map.get("stcd").toString().split("-")[0]);
				m.put("STNM", map.get("stnm"));
				m.put("CITY", map.get("district"));
				m.put("Z", Double.valueOf(map.get("sw85").toString()));
				m.put("WRZ", map.get("guard")==null||map.get("guard").toString().equals("")?null:Double.valueOf(map.get("guard").toString()));
				m.put("GRZ", map.get("ensure")==null||map.get("ensure").toString().equals("")?null:Double.valueOf(map.get("ensure").toString()));
				m.put("ISRCONTROL", map.get("isRControl")==null||map.get("isRControl").toString().equals("")?false:map.get("isRControl"));
				m.put("TM", map.get("tm"));
				m.put("TREND", map.get("trend"));
				m.put("LGTD", map.get("longitude"));
				m.put("LATD", map.get("latitude"));
				m.put("river", map.get("river"));
				m.put("_s", map.get("stnm").toString()+PinyinUtil.converterToPinYin(map.get("stnm").toString()));
				result.add(m);
			}
		}else{
			SyqService service = (SyqService) ServiceFactory.getService("SyqService");
			result = service.QueryPRvWater(params.getParam("TM"));
		}
			
		
		
		//deal focus
		UserService service_user = (UserService) ServiceFactory.getService("user");
		service_user.dealFocusField(result, "STCD", 
			session.getAttribute("token").toString(), "water_run_plain");
		
		//set return
		responser.setRtString(parseJSON(result));
		responser.setRtType(JSON);
		return responser;
		*/
		
		//方式2：get result
		String region=session.getAttribute("region").toString();
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		Map<String, String> p1 = new HashMap<String, String>();
		String tm = params.getParam("TM").toString();
		p1.put("tm", tm);
		if(!region.equals("nbslj")) p1.put("district", region);
		String mathod = "getSTRiverInfoByTm";
		String realtime = params.getParam("realtime");
		String resultstr = "";
		if(realtime != null && realtime.equals("true")){ //mathod = "getSTRiverInfo";
			resultstr = parseJSON(hOSImpl.getSTRiverInfo());
		}else{
			resultstr = parseJSON(hOSImpl.getSTRiverInfoByTm(tm));
		}
			
		//String resultstr=HttpUtil.htOauthRequest(apiUrl,
		//		mathod,p1, key, secret);
		
		JSONArray jsonArray = JSONArray.fromObject(resultstr);
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> map=(Map<String,Object>)obj;
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("STCD", map.get("stcd").toString().split("-")[0]);
			m.put("STNM", map.get("stnm"));
			m.put("CITY", map.get("district"));
			m.put("Z", Double.valueOf(map.get("sw85").toString()));
			m.put("WRZ", map.get("guard")==null||map.get("guard").toString().equals("")?null:Double.valueOf(map.get("guard").toString()));
			m.put("GRZ", map.get("ensure")==null||map.get("ensure").toString().equals("")?null:Double.valueOf(map.get("ensure").toString()));
			m.put("ISRCONTROL", map.get("isRControl")==null||map.get("isRControl").toString().equals("")?false:map.get("isRControl"));
			m.put("TM", map.get("tm"));
			m.put("TREND", map.get("trend"));
			m.put("LGTD", map.get("longitude"));
			m.put("LATD", map.get("latitude"));
			m.put("river", map.get("river"));
			m.put("_s", map.get("stnm").toString()+PinyinUtil.converterToPinYin(map.get("stnm").toString()));
			result.add(m);
		}
		
		//deal focus
		UserService service_user = (UserService) ServiceFactory.getService("user");
		service_user.dealFocusField(result, "STCD", 
			session.getAttribute("token").toString(), "water_run_plain");
		
		//set return
		responser.setRtString(parseJSON(result));
		responser.setRtType(JSON);
		return responser;
		
	}
	
	/**
	 * 平原水位-实时
	 * zzj 2014.6.25
	 * @return
	 */
	public Responser realtimePRvWater(){
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getSTRiverInfo", key, secret);
		String resultstr = parseJSON(hOSImpl.getSTRiverInfo());
		JSONArray jsonArray = JSONArray.fromObject(resultstr);
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> map=(Map<String,Object>)obj;
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("STCD", map.get("stcd").toString().split("-")[0]);
			m.put("STNM", map.get("stnm"));
			m.put("CITY", map.get("district"));
			m.put("Z", map.get("sw85"));
			m.put("WRZ", map.get("guard"));
			m.put("GRZ", map.get("ensure"));
			m.put("ISRCONTROL", map.get("isrcontrol"));
			m.put("TM", map.get("tm"));
			m.put("TREND", map.get("trend"));
			m.put("LGTD", map.get("longitude"));
			m.put("LATD", map.get("latitude"));
			m.put("_s", map.get("stnm").toString()+PinyinUtil.converterToPinYin(map.get("stnm").toString()));
			result.add(m);
		}
		responser.setRtType(JSON); 
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 潮位
	 * tdz潮位，guard警戒潮位，ensure保证潮位，poltp塘顶高，ordno排序号
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Querytidewater() {  
//		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
//		responser.setRtType(JSON);  
//		responser.setRtString(parseJSON(service.Querytidewater(params.getParam("TM")))); 
//		return responser;
		//以下赵子建修改 2014.6.25
		String region=session.getAttribute("region").toString();
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		Map<String, String> p1 = new HashMap<String, String>();
		p1.put("tm", params.getParam("TM").toString());
		if(!region.equals("nbslj")) p1.put("district", region);
		String mathod = "getSTTideInfoByTm";
		String realtime = params.getParam("realtime");
		String resultstr = "";
		if(realtime != null && realtime.equals("true")){// mathod = "getSTTideInfo";
			resultstr = parseJSON(hOSImpl.getSTTideInfo());
		}else{
			resultstr = parseJSON(hOSImpl.getSTTideInfoByTm(params.getParam("TM").toString()));
		}
		
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				mathod,p1, key, secret);
		
		JSONArray jsonArray = JSONArray.fromObject(resultstr);
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> map=(Map<String,Object>)obj;
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("stcd", map.get("stcd").toString().split("-")[0]);
			m.put("stnm", map.get("stnm"));
			m.put("guard", map.get("guard"));
			m.put("ensure", map.get("ensure"));
			m.put("city", map.get("district"));
			m.put("poltp", map.get("polTop"));
			m.put("polnm", map.get("polName"));
			m.put("ordno", 0);
			m.put("enabled", 1);
			m.put("tm", map.get("tm"));
			m.put("tdz", Double.parseDouble(map.get("sw85").toString()));
			m.put("LGTD", map.get("longitude"));
			m.put("LATD", map.get("latitude"));
			m.put("_s", map.get("stnm").toString()+PinyinUtil.converterToPinYin(map.get("stnm").toString()));
			result.add(m);
		}
		
		//deal focus
		UserService service_user = (UserService) ServiceFactory.getService("user");
		service_user.dealFocusField(result, "stcd", 
						session.getAttribute("token").toString(), "tide2");
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * zzj 2014.6.25
	 * 潮位
	 * tdz潮位，guard警戒潮位，ensure保证潮位，poltp塘顶高，ordno排序号
	 * 
	 * @since v 1.0
	 * @return
	 */
	public Responser realtimeTideWater() {
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		//String resultstr=HttpUtil.htOauthRequest(apiUrl,
		//		"getSTTideInfo", key, secret);
		String resultstr = parseJSON(hOSImpl.getSTTideInfo());
		JSONArray jsonArray = JSONArray.fromObject(resultstr);
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> map=(Map<String,Object>)obj;
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("stcd", map.get("stcd").toString().split("-")[0]);
			m.put("stnm", map.get("stnm"));
			m.put("guard", map.get("guard"));
			m.put("ensure", map.get("ensure"));
			m.put("city", map.get("district"));
			m.put("poltp", map.get("polTop"));
			m.put("polnm", map.get("polName"));
			m.put("ordno", 0);
			m.put("enabled", 1);
			m.put("tm", map.get("tm"));
			m.put("tdz", Double.parseDouble(map.get("sw85").toString()));
			m.put("LGTD", map.get("longitude"));
			m.put("LATD", map.get("latitude"));
			m.put("_s", map.get("stnm").toString()+PinyinUtil.converterToPinYin(map.get("stnm").toString()));
			result.add(m);
		}
		responser.setRtType(JSON); 
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 雨量站时段雨量过程 
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量
	 */
	public Responser Queryrian_hour_line() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		
				
		responser.setRtString(parseJSON(service.Queryrian_hour_line(params.getParam("TM1"),params.getParam("TM2"),params.getParam("stcd")))); 
		return responser;
	}
	
	/**
	 * 雨量站时段雨量
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量，CITY城市
	 */
	public Responser Queryrian_hour() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);
		
		List<Map<String,Object>> result = service.Queryrian_hour(params.getParam("TM1"),params.getParam("TM2"));
		
		//deal focus
				UserService service_user = (UserService) ServiceFactory.getService("user");
				service_user.dealFocusField(result, "STCD", 
						session.getAttribute("token").toString(), "dotRain");
				responser.setRtString(parseJSON(result));
		//responser.setRtString(parseJSON(service.Queryrian_hour(params.getParam("TM1"),params.getParam("TM2")))); 
		return responser;
	}
	
	/**
	 * 雨量站日雨量过程
	 * 如果要求02-02至02-05的雨量过程，TM1取02-02 08:00，TM2取02-05 08:00
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量
	 */
	public Responser Queryrian_day_line() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		
		

		responser.setRtString(parseJSON(service.Queryrian_day_line(params.getParam("TM1"),params.getParam("TM2"),params.getParam("stcd")))); 
		return responser;
	}
	
	/**
	 * 雨量站日雨量
	 * 参数TM2的时间为当前时间的晚八点时间，例如现在时间是2013-12-09 16:25,求一日雨量，TM2='2013-12-10 08:00',TM1='2013-12-09 08:00'
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryrian_day() { 
		//方式1：
		//SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		//List<Map<String,Object>> result = service.Queryrian_day(params.getParam("TM1"),params.getParam("TM2"));
		
		//方式2：get result
		String region=session.getAttribute("region").toString();
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		Map<String, String> p1 = new HashMap<String, String>();
		p1.put("tm1", params.getParam("TM1").toString());
		p1.put("tm2", params.getParam("TM2").toString());
		if(!region.equals("nbslj")) p1.put("district", region);
		String mathod = "getSTRainInfoByTm";
		String realtime = params.getParam("realtime");
		String day = params.getParam("day");//实时获取时要用到此参数，是想获取最近1、3或7天数据
		String resultstr="";
		if(realtime != null && realtime.equals("true")){ //mathod = "getSTRainInfo";
			resultstr = parseJSON(hOSImpl.getSTRainInfo());
		}else{
			resultstr = parseJSON(hOSImpl.getSTRainInfoByTm(params.getParam("TM1").toString(),
					params.getParam("TM2").toString()));
		}
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				mathod,p1, key, secret);
		JSONArray jsonArray = JSONArray.fromObject(resultstr);
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> map=(Map<String,Object>)obj;
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("STCD", map.get("stcd").toString().split("-")[0]);
			String stnm = HTMLFormatter.removeHtmlTag(map.get("name").toString());
			m.put("STNM", stnm);
			m.put("CITY", map.get("district"));
			m.put("ISSTATE", map.get("ISSTATE"));
			m.put("LGTD", map.get("longitude"));
			m.put("LATD", map.get("latitude"));
			m.put("_s", map.get("code"));
			//设置雨量值
			if(realtime.equals("true")){//如果是获取实时数据
				if(day.equals("1")){
					m.put("VALUE", Double.valueOf(map.get("todayRain").toString()));
				}else if(day.equals("3")){
					m.put("VALUE", Double.valueOf(map.get("totalRain").toString()));
				}else if(day.equals("7")){
					m.put("VALUE", Double.valueOf(map.get("sum7").toString()));
				}
			}else{//如果是动态查询的
				m.put("VALUE", Double.valueOf(map.get("todayRain").toString()));
			}
			
			result.add(m);
		}
		
		//deal focus
		UserService service_user = (UserService) ServiceFactory.getService("user");
		service_user.dealFocusField(result, "STCD", 
				session.getAttribute("token").toString(), "dotRain");
		
		responser.setRtString(parseJSON(result)); 
		responser.setRtType(JSON); 
		return responser;
	}
	
	/**
	 * 当有1，3,7的实时站点雨量
	 * @return
	 */
	public Responser getSTRainInfo(){
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getSTRainInfo", key, secret);
		String resultstr = parseJSON(hOSImpl.getSTRainInfo());
		
		responser.setRtType(JSON); 
		responser.setRtString(resultstr);
		return responser;
	}
	
	/**
	 * 区县面时段雨量过程
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量
	 */
	public Responser Queryarearian_hour_line() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryarearian_hour_line(params.getParam("TM1"),params.getParam("TM2"),params.getParam("region")))); 
		return responser;
	}
	
	/**
	 * 区县面时段雨量
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量，COUNT_POINT站点数
	 */
	public Responser Queryarearian_hour() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryarearian_hour(params.getParam("TM1"),params.getParam("TM2")))); 
		return responser;
	}
	
	
	/**
	 * 区县面日雨量过程线
	 * 如果要求02-02至02-05的雨量过程，TM1取02-02 08:00，TM2取02-05 08:00
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量
	 */
	public Responser Queryarearian_day_line() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryarearian_day_line(params.getParam("TM1"),params.getParam("TM2"),params.getParam("region")))); 
		return responser;
	}
	
	/**
	 * 区县面日雨量
	 * 参数TM2的时间为当前时间的晚八点时间，例如现在时间是2013-12-09 16:25,求一日雨量，TM2='2013-12-10 08:00',TM1='2013-12-09 08:00'
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryarearian_day() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryarearian_day(params.getParam("TM1"),params.getParam("TM2")))); 
		return responser;
	}
	
	/**
	 * 大型水库面雨量过程
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return  VALUE雨量
	 */
	public Responser Queryresrian_line() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryresrian_line(params.getParam("TM1"),params.getParam("TM2"),params.getParam("rsnm")))); 
		return responser;
	}
	
	
	/**
	 * 大型水库面雨量
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return RSNM水库 drp雨量
	 */
	public Responser Queryresrian() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryresrian(params.getParam("TM1"),params.getParam("TM2")))); 
		return responser;
	}
	
	/**
	 * 水库承洪
	 * TM1是当前时间，TM2是对比时间
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return sw1,kr1当前水位、库容,  sw2,kr2,tm2cha对比水位、库容、库容差，xxsw,xxkr,xxyl汛限水位、库容、承载雨量，
	 * zcsw,zckr,zcyl正常水位、库容、承载雨量，jhsw,jhkr,jhyl校核水位、库容、承载雨量，sjsw,sjkr,sjyl设计水位、库容、承载雨量
	 * fhgsw,fhgkr,fhgcha,fhgyl防洪高水位、库容、库容差、承载雨量
	 */
	public Responser QueryResSupport() {  
		responser.setRtType(JSON);  
		List<Map<String,Object>> result;
		if(params.getParam("cache").equals("true")){
			String urlString="http://www.htwater.net:8080/zhsl/QueryResSupport!SYQ?cache=true";
			String returnstr=HttpUtil.getResponseFromWebByGET(urlString);
			JSONArray jsonArray = JSONArray.fromObject(returnstr);
			result=jsonArray;
			//responser.setRtString(parseJSON(CacheUtil.getValue("CACHE_RESSUPPORT",
					//"res-support")));
		}else{
			SyqService service = (SyqService) ServiceFactory.getService("SyqService");
			result=service.QueryResSupport(params.getParam("TM1"),params.getParam("TM2"));
		}
		
		//deal focus
				UserService service_user = (UserService) ServiceFactory.getService("user");
				service_user.dealFocusField(result, "stcd", 
						session.getAttribute("token").toString(), "ch_res");
		for(Map<String,Object> m : result){
			m.put("_s", m.get("stnm").toString()+PinyinUtil.converterToPinYin(m.get("stnm").toString()));
		}
		
		responser.setRtString(parseJSON(result));	
		return responser;
	}
	
	/**
	 * 库容曲线
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return Xvalue水位，Yvalue库容
	 */
	public Responser QueryResCapacity() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryResCapacity(params.getParam("stnm")))); 
		return responser;
	}
	
	/**
	 * 平原承洪
	 * @Date 2014-02-20
	 * @since v 1.0
	 * @return Capacity承洪能力(万方)，Current当前水位(米),CurrentCapacity当前容量(万方)
	 * Control控制水位(米),ControlCapacity控制容量(万方),Rain可承受降雨量(毫米)
	 */
	public Responser QueryPlainCapacity() {  
		SyqService service = (SyqService) ServiceFactory.getService("SyqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryPlainCapacity(params.getParam("TM")))); 
		return responser;
	}
	
	/**
	 * 报警预警-河道水位报警
	 * zzj 2014.8.18
	 * @return
	 */
	public Responser getAlertRiverInfo(){
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getAlertRiverInfo", key, secret);
		String resultstr = parseJSON(hOSImpl.getAlertRiverInfo(null));
		responser.setRtType(JSON); 
		responser.setRtString(resultstr);
		return responser;
	}
	/**
	 * 报警预警-水库水位报警
	 * zzj 2014.8.18
	 * @return
	 */
	public Responser getAlertRsvrInfo(){
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getAlertRsvrInfo", key, secret);
		String resultstr = parseJSON(hOSImpl.getAlertRsvrInfo(null));
		responser.setRtType(JSON); 
		responser.setRtString(resultstr);
		return responser;
	}
	/**
	 * 报警预警-潮位报警
	 * zzj 2014.8.18
	 * @return
	 */
	public Responser getAlertTideInfo(){
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getAlertTideInfo", key, secret);
		String resultstr = parseJSON(hOSImpl.getWarnTides());
		responser.setRtType(JSON); 
		responser.setRtString(resultstr);
		return responser;
	}
	/**
	 * 报警预警-潮位报警
	 * zzj 2014.8.18
	 * @return
	 */
	public Responser getAlertRainInfo(){
		/*Map<String, String> p1 = new HashMap<String, String>();
		p1.put("hourorday", params.getParam("hourorday"));
		p1.put("mm", params.getParam("mm"));
		p1.put("type", params.getParam("type"));
		//String resultstr=HttpUtil.htOauthRequest(apiUrl,
		//		"getAlertRainInfo",p1, key, secret);
		
		responser.setRtType(JSON); 
		responser.setRtString(resultstr);
		return responser;*/
		responser.setRtType(JSON);
		int hourOrDay = null == params.getParam("hourorday") ? 
				1 : Integer.parseInt(params.getParam("hourorday"));
		if (null != params.getParam("hour")) {
		hourOrDay = Integer.parseInt(params.getParam("hour"));
		}
		
		int mm = null == params.getParam("mm") ? 
				30 : Integer.parseInt(params.getParam("mm"));
		String type = null == params.getParam("type") ?
				"hour" : params.getParam("type");
		String from = null == params.getParam("from") ? 
				"regular" : params.getParam("from");
		List<Map<String, Object>> list = null;
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		Date nowDate = DateUtil.str2Date(DateUtil.getToday("yyyy-MM-dd HH:00:00"), 
				"yyyy-MM-dd HH:mm:ss");
		cal.setTime(nowDate);
		
		if ("hour".equals(type) && 1 == hourOrDay) {
			list = (List<Map<String, Object>>) 
					CacheUtil.getValue("CACHE_WARN","alert-rain1h");
			if( null==list || list.isEmpty() )
				list = hOSImpl.getRainsByHour(cal.getTime(), 1);
		} else if ("hour".equals(type) && 3 == hourOrDay) {
			list = (List<Map<String, Object>>) 
					CacheUtil.getValue("CACHE_WARN","alert-rain3h");
			if( null==list || list.isEmpty() )
				list = hOSImpl.getRainsByHour(cal.getTime(), 3);
		} else if ("day".equals(type) && 1 == hourOrDay) {
			list = (List<Map<String, Object>>) 
					CacheUtil.getValue("CACHE_WARN","alert-rain1d");
			if( null==list || list.isEmpty() )
				list = hOSImpl.getRainsByDay(cal.getTime(), 1);
		}

		if (null == list) {
			if ("regular".equals(from)) {
				list = hOSImpl.getWarnRains(nowDate, hourOrDay, mm, type);
			} else {
				list = hOSImpl.getWarnRains(new Date(), hourOrDay, mm, type);
			}
		} 

		List<Map<String, Object>> tmpList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			double stationRain = (Double) map.get("drp");
			if (stationRain >= mm) {
				tmpList.add(map);
			}
		}
		list.clear();
		list.addAll(tmpList);
		
		String district = params.getParam("district");
		String rsvr = params.getParam("rsvr");
		responser.setRtString(parseJSON(HtOauthHelper.filterDistrict(list,
				district, rsvr)));
		return responser;
	}
	/**
	 * 实时查询等值面
	 * @return
	 */
	public Responser queryISO(){
//		Map<String, String> p1 = new HashMap<String, String>();
//		p1.put("tm1", params.getParam("tm1").replaceAll(" ", "%20"));
//		p1.put("tm2", params.getParam("tm2").replaceAll(" ", "%20"));
//		p1.put("iscontry", null==params.getParam("iscontry")?"true":params.getParam("iscontry"));
////		String resultstr=HttpUtil.htOauthRequest(apiUrl,
////				"queryISO",p1, key, secret);
//		responser.setRtType(JSON); 
//		responser.setRtString(resultstr);
//		return responser;
		
		responser.setRtType(JSON);
		String result = "";
		Properties properties = new Properties();
		InputStream in = SyqAction.class.getResourceAsStream("/htpms.properties");
		try {
			properties.load(in);

		String host = properties.getProperty("htpms.isonet_server");
		String url = host + "api.ashx?tm1="
				+ params.getParam("tm1").replaceAll(" ", "%20")
				+ "&tm2="+params.getParam("tm2").replaceAll(" ", "%20")
				+ "&iscontry="+
				(null==params.getParam("iscontry")?"true":params.getParam("iscontry"));
		result = HttpUtil.getResponseByApacheGET(url.replaceAll(" ", "%20"));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responser.setRtString(result);
		return responser;
	}
	
	/**
	 * 获取简报html内容
	 * @return
	 */
	public Responser getReport(){
		String url = HttpUtil.getResponseFromWeb("http://www.htwater.net:8080/htpublish/getReport!public");
		String html = getURLContent("http://www.htwater.net:8080/htpublish_files/"+url.split(",")[0]);

		responser.setRtType(TEXT); 
		responser.setRtString(html);
		

		return responser;
	}
	public static String getURLContent(String urlStr) {               
	       /** 网络的url地址 */        
	    URL url = null;              
	       /** http连接 */    
	    HttpURLConnection httpConn = null;            
	        /**//** 输入流 */   
	    BufferedReader in = null;   
	    StringBuffer sb = new StringBuffer();   
	    try{     
	     url = new URL(urlStr);     
	     in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") );   
	     String str = null;    
	     while((str = in.readLine()) != null) {    
	      sb.append( str );     
	            }     
	        } catch (Exception ex) {   
	            
	        } finally{    
	         try{             
	          if(in!=null) {  
	           in.close();     
	                }     
	            }catch(IOException ex) {      
	            }     
	        }     
	        String result =sb.toString();     
	        System.out.println(result);     
	        return result;    
	}
	
	/**
	 * 获取最近24小时逐时雨量等值线面
	 * @return
	 */
	public Responser getISO24zhushi(){
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getISO24zhushi", key, secret);
//		
//		responser.setRtType(JSON); 
//		responser.setRtString(resultstr);
//		return responser;
		List<Map<String,Object>> result = new RainWaterServiceImpl().getISO24zhushi();
		Properties properties = new Properties();
		InputStream in = SyqAction.class.getResourceAsStream("/htpms.properties");
		try {
			properties.load(in);	
			String host = properties.getProperty("htpms.iso24zhushi_host");
			for(Map<String,Object> map : result){
				if(map.get("path") != null){
					map.put("path", host+map.get("path").toString());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	} 
	/**
	 * 获取最近24小时累积雨量等值线面
	 * @return
	 */
	public Responser getISO24leiji(){
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getISO24leiji", key, secret);
//		
//		responser.setRtType(JSON); 
//		responser.setRtString(resultstr);
//		return responser;
		List<Map<String,Object>> result =  new RainWaterServiceImpl().getISO24leiji();
		Properties properties = new Properties();
		InputStream in = SyqAction.class.getResourceAsStream("/htpms.properties");
		try {
			properties.load(in);
			String host = properties.getProperty("htpms.iso24leiji_host");
			for(Map<String,Object> map : result){
				if(map.get("path") != null){
					map.put("path", host+map.get("path").toString());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	} 
}

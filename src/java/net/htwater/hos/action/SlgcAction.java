/**
 * 水利工程管理服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.hos.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.Util.PropertiesHelper;
import net.htwater.hos.helper.impl.BaseDataServiceImpl;
import net.htwater.hos.service.SlgcService;
import net.htwater.hos.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.HttpUtil;
import cn.miao.framework.util.PinyinUtil;

public class SlgcAction extends DoAction {
	PropertiesHelper propHelper = new PropertiesHelper("/htpms.properties");

	String apiUrl = propHelper.getPropertyValue("htpms.url");
	String key = propHelper.getPropertyValue("htpms.key");
	String secret = propHelper.getPropertyValue("htpms.secret");
	
	BaseDataServiceImpl bDSImpl = new BaseDataServiceImpl();
	/**
	 * 水库
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryres() {  
//		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
//		responser.setRtType(JSON);  
//		responser.setRtString(parseJSON(service.Queryres())); 
//		return responser;
		
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getRsvrList", key, secret);
		String resultstr = parseJSON(bDSImpl.getRsvrList());
		JSONArray jsonArray = JSONArray.fromObject(resultstr);
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> m=(Map<String,Object>)obj;
			m.put("_s", m.get("ENNM").toString()+PinyinUtil.converterToPinYin(m.get("ENNM").toString()));
			result.add(m);
		}
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	}
	/**
	 * 水库查询
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser QueryFORres() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryFORres(params.getParam("ENNM"),params.getParam("CITY"),params.getParam("TOTAL_s")))); 
		return responser;
	}
	
	/**
	 * 河道
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryriver() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryriver())); 
		return responser;
	}
	/**
	 * 河道查询
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return        
	 */
	public Responser QueryFORriver() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryFORriver(params.getParam("STNM"),params.getParam("classify")))); 
		return responser;
	}
	
	/**
	 * 水闸
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Querygate() {
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getGateList", key, secret);
		String resultstr = parseJSON(bDSImpl.getGateList());
		JSONArray jsonArray = JSONArray.fromObject(resultstr);
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> m=(Map<String,Object>)obj;
			m.put("_s", m.get("ENNM").toString()+PinyinUtil.converterToPinYin(m.get("ENNM").toString()));
			result.add(m);
		}
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	}
	/**
	 * 水闸查询
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser QueryFORgate() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryFORgate(params.getParam("ENNM"),params.getParam("TP"),params.getParam("CITY")))); 
		return responser;
	}
	
	/**
	 * 泵站
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Querypumb() {  
//		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
//		responser.setRtType(JSON);  
//		responser.setRtString(parseJSON(service.Querypumb())); 
//		return responser;
		List<Map<String, Object>> result =new ArrayList<Map<String, Object>>();
		
//		String resultstr=HttpUtil.htOauthRequest(apiUrl,
//				"getPumpList", key, secret);
		String resultstr = parseJSON(bDSImpl.getPumpList());
		JSONArray jsonArray = JSONArray.fromObject(resultstr);
		for(Object obj : jsonArray.toArray()){
			Map<String,Object> m=(Map<String,Object>)obj;
			m.put("_s", m.get("ENNM").toString()+PinyinUtil.converterToPinYin(m.get("ENNM").toString()));
			result.add(m);
		}
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	}
	/**
	 * 泵站查询
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return                  
	 */
	public Responser QueryFORpumb() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryFORpumb(params.getParam("ENNM"),params.getParam("CITY"),params.getParam("TP")))); 
		return responser;
	}
	
	/**
	 * 堤防
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Querydike() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querydike())); 
		return responser;
	}
	/**
	 * 堤防查询
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser QueryFORdike() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryFORdike(params.getParam("ENNM"),params.getParam("FLOOD_STAN")))); 
		return responser;
	}
	
	
	public Responser Queryhaitang() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryhaitang())); 
		return responser;
	}
	
	
	/**
	 * 水站查询
	 * @Date 2014-02-27
	 * @since v 1.0
	 * @return
	 */
	public Responser Querywaterstation() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querywaterstation())); 
		return responser;
	}
	
	/**
	 * 获取水站不同的区县和乡镇数据
	 * @Date 2014-03-18
	 * @since v 1.0
	 * @return
	 */
	public Responser Querydistinctqx() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querydistinctqx())); 
		return responser;
	}
	
	/**
	 * 水电站查询
	 * @Date 2014-02-27
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryhydropowerstation() {  
		SlgcService service = (SlgcService) ServiceFactory.getService("SlgcService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryhydropowerstation())); 
		return responser;
	}
	
}

package net.htwater.hos.action;

import java.util.List;
import java.util.Map;

import net.htwater.hos.service.FloodService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.PinyinUtil;

/**
 * @author liuxiaojing
 * @description 获取山洪灾害预警相关记录、应急响应记录等
 * @date 2015-06-09
 */
public class FloodAction extends DoAction {
	
	/**
	 * 获取测站预警列表
	 * @return
	 */
	public Responser getSTWarn() {
		FloodService service = (FloodService) ServiceFactory.getService("Flood");
		responser.setRtType(JSON);
		List<Map<String,Object>> result=service.getSTWarn(
				params.getParam("TM1"),
				params.getParam("TM2")
		);
		for(Map<String,Object> map : result){
			map.put("_s", map.get("STWarnNM").toString()+PinyinUtil.converterToPinYin(map.get("STWarnNM").toString()));
		}
		
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 获取某时段行政区预警记录
	 * @return
	 */
	public Responser getWarnList() {
		FloodService service = (FloodService) ServiceFactory.getService("Flood");
		responser.setRtType(JSON);
		List<Map<String,Object>> result=service.getWarnList(
				params.getParam("tm1"),
				params.getParam("tm2")
		);		
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 根据行政区预警ID获取影响该行政区预警的测站预警记录
	 * @return
	 */
	public Responser getSTWarnList() {
		FloodService service = (FloodService) ServiceFactory.getService("Flood");
		responser.setRtType(JSON);
		List<Map<String,Object>> result=service.getSTWarnList(
				params.getParam("warnid"),
				params.getParam("radcd")
		);		
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 根据行政区预警ID获取该预警的变化过程记录
	 * @return
	 */
	public Responser getWarnChangeList() {
		FloodService service = (FloodService) ServiceFactory.getService("Flood");
		responser.setRtType(JSON);
		List<Map<String,Object>> result=service.getWarnChangeList(
				params.getParam("warnid"),
				params.getParam("radcd")
		);		
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 获取某时间段应急响应记录
	 * @return
	 */
	public Responser getResponseList() {
		FloodService service = (FloodService) ServiceFactory.getService("Flood");
		responser.setRtType(JSON);
		List<Map<String,Object>> result=service.getResponseList(
				params.getParam("tm1"),
				params.getParam("tm2")
		);		
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 根据应急响应ID获取应急响应措施记录
	 * @return
	 */
	public Responser getMeasuresList() {
		FloodService service = (FloodService) ServiceFactory.getService("Flood");
		responser.setRtType(JSON);
		List<Map<String,Object>> result=service.getMeasuresList(
				params.getParam("responseid"),
				params.getParam("radcd")
		);		
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 获取区县级山洪系统地址(含用户验证的token)
	 * @return
	 */
	public Responser getDistrictApp() {
		FloodService service = (FloodService) ServiceFactory.getService("Flood");
		responser.setRtType(JSON);
		List<Map<String,Object>> result=service.getDistrictApp(session.getAttribute("token").toString());	
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 获取某时间段灾害记录
	 * @return
	 */
	public Responser getDisasterSta() {
		FloodService service = (FloodService) ServiceFactory.getService("Flood");
		responser.setRtType(JSON);
		List<Map<String,Object>> result=service.getDisasterSta(
				params.getParam("tm1"),
				params.getParam("tm2")
		);		
		responser.setRtString(parseJSON(result));
		return responser;
	}
}

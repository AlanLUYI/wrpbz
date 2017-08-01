/**
 * 防汛管理服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import net.htwater.mydemo.service.XsswService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class XsswAction extends DoAction {

	 
	public Responser Queryzdxx() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryzdxx())); 
		return responser;
	}
	
	public Responser QueryHisRain_year() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryHisRain_year(
				params.getParam("tm1"),
				params.getParam("tm2")))); 
		return responser;
	}
	
	public Responser QueryHisRain_month() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryHisRain_month(
				params.getParam("tm1"),
				params.getParam("tm2")))); 
		return responser;
	}
	
	public Responser QueryHisRain_day() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryHisRain_day(
				params.getParam("tm1"),
				params.getParam("tm2")))); 
		return responser;
	}
	
	public Responser QueryHisWe_year() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryHisWe_year(
				params.getParam("tm1"),
				params.getParam("tm2")))); 
		return responser;
	}
	
	public Responser QueryHisWe_month() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryHisWe_month(
				params.getParam("tm1"),
				params.getParam("tm2")))); 
		return responser;
	}
	
	public Responser QueryHisWe_day() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryHisWe_day(
				params.getParam("tm1"),
				params.getParam("tm2")))); 
		return responser;
	}
	
	public Responser Querydmxp() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querydmxp(
				params.getParam("tm1"),
				params.getParam("tm2"),
				params.getParam("stcds")))); 
		return responser;
	}
	
	public Responser Queryhmxp() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryhmxp(
				params.getParam("tm1"),
				params.getParam("tm2"),
				params.getParam("stcds")))); 
		return responser;
	}
	
	public Responser Querymmxp() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querymmxp(
				params.getParam("tm1"),
				params.getParam("tm2"),
				params.getParam("stcds")))); 
		return responser;
	}
	
	public Responser QueryYRWE() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryYRWE(
				params.getParam("tm1"),
				params.getParam("tm2"),
				params.getParam("stcds")))); 
		return responser;
	}
	public Responser QueryMTHWE() {  
		XsswService service = (XsswService) ServiceFactory.getService("XsswService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryMTHWE(
				params.getParam("tm1"),
				params.getParam("tm2"),
				params.getParam("stcds")))); 
		return responser;
	}
}

/**
 * 防汛管理服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import net.htwater.mydemo.service.TJBBService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class TJBBAction extends DoAction {

	public Responser Queryrealstnm() {  
		TJBBService service = (TJBBService) ServiceFactory.getService("TJBBService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryrealstnm(
				params.getParam("tm")))); 
		return responser;
	}
	public Responser Querymultidata() {  
		TJBBService service = (TJBBService) ServiceFactory.getService("TJBBService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querymultidata(
				params.getParam("year"),
				params.getParam("month")))); 
		return responser;
	}
	public Responser Querysingledata() {  
		TJBBService service = (TJBBService) ServiceFactory.getService("TJBBService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querysingledata(
				params.getParam("year"),
				params.getParam("stcd")))); 
		return responser;
	}
	
}

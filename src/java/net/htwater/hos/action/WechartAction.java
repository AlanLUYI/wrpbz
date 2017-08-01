/**
 * wechart  微信数据服务
 * @author fhj
 * 2015-02-10
 */
package net.htwater.hos.action;

import net.htwater.hos.service.WechartService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class WechartAction extends DoAction {
	//微信数据服务，雨量数据
	public Responser queryFORrain() {
		WechartService service = (WechartService) ServiceFactory.getService("Wechart");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORrain(

		)));
		return responser;
	}
	//微信数据服务，水位数据
	public Responser queryFORwater() {
		WechartService service = (WechartService) ServiceFactory.getService("Wechart");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORwater(
				
		)));
		return responser;
	}
	//微信数据服务，潮位数据
	public Responser queryFORtide() {
		WechartService service = (WechartService) ServiceFactory.getService("Wechart");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORtide(
			
		)));
		return responser;
	}
	//微信数据服务，台风消息
   public Responser queryFORtyph() {
	   WechartService service = (WechartService) ServiceFactory.getService("Wechart");
	   responser.setRtType(JSON);
	   responser.setRtString(service.queryFORtyph(
			 
	   ));	   
	   return responser;
   }
   public Responser insertOPENID() {
	   WechartService service = (WechartService) ServiceFactory.getService("Wechart");
	   responser.setRtType(JSON);
	   responser.setRtString(service.insertOPENID(
			 params.getParam("openid")
	   ));	   
	   return responser;
   }
}

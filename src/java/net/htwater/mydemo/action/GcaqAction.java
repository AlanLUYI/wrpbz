/**
 * 防汛管理服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import net.htwater.mydemo.service.GcaqService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class GcaqAction extends DoAction {

	/**
	 * 水库工程安全信息
	 * @Date 2014-03-05
	 * @since v 1.0
	 * @return
	 */
	public Responser Querygcaq_res() {  
		GcaqService service = (GcaqService) ServiceFactory.getService("GcaqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querygcaq_res())); 
		return responser;
	}
	
	/**
	 * 水闸安全信息
	 * @Date 2014-03-05
	 * @since v 1.0
	 * @return
	 */
	public Responser getAllGateSafeData() {  
		GcaqService service = (GcaqService) ServiceFactory.getService("GcaqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getAllGateSafeData())); 
		return responser;
	}
	/**
	 * 堤防安全信息
	 * @Date 2014-03-05
	 * @since v 1.0
	 * @return
	 */
	public Responser getAllDikeSafeData() {  
		GcaqService service = (GcaqService) ServiceFactory.getService("GcaqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getAllDikeSafeData())); 
		return responser;
	}
}

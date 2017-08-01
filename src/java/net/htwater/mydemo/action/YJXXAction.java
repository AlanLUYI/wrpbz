/**
 * 预警信息
 * @author RYX
 * @Date 2014-06-17
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import net.htwater.mydemo.service.YJXXService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class YJXXAction extends DoAction {

	/**
	 * 预警信息列表 
	 * @Date 2014-06-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryyjxxlist() {  
		YJXXService service = (YJXXService) ServiceFactory.getService("YJXXService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryyjxxlist(params.getParam("TM1"),params.getParam("TM2")))); 
		return responser;
	}
	
	/**
	 * 根据ID获取单条预警信息 
	 * @Date 2014-06-17
	 * @since v 1.0
	 * @return
	 */
	public Responser QueryyjxxbyID() {  
		YJXXService service = (YJXXService) ServiceFactory.getService("YJXXService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryyjxxbyID(params.getParam("ID")))); 
		return responser;
	}
	
	/**
	 * 实时预警状态 
	 * @Date 2014-06-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryrealyjxx() {  
		YJXXService service = (YJXXService) ServiceFactory.getService("YJXXService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryrealyjxx())); 
		return responser;
	}
	
	/**
	 * 增加预警信息 
	 * TM时间，GRADE预警级别，TITLE标题，Source来源，CONTENT内容
	 * @Date 2014-06-17
	 * @since v 1.0
	 * @return
	 */
	public Responser initupdateLY() {
		YJXXService service = (YJXXService) ServiceFactory.getService("YJXXService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.addyjxx( 
				params.getParam("TM"),
				params.getParam("GRADE"),
				params.getParam("TITLE"),
				params.getParam("Source"),
				params.getParam("CONTENT") 
				)));
		return responser;
	}
}

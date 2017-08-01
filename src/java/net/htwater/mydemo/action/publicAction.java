/**
 * 公用服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import net.htwater.mydemo.service.publicService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class publicAction extends DoAction {


	/**
	 * 天气服务 
	 * @author RYX
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser tqService() {  
		publicService service = (publicService) ServiceFactory.getService("publicService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.gettq())); 
		return responser;
	}
}

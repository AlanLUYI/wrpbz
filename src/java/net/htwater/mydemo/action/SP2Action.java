/**
 * 获取视频监视列表
 * @author yangshengfei
 * @Date 2014-06-27
 * @since v 1.0
 */

package net.htwater.mydemo.action;

import net.htwater.mydemo.service.SP2Service;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.HttpUtil;

public class SP2Action extends DoAction {
	/**
	 * 获取视频监视列表All
	 * @Date 2014-07-30
	 * @since v 1.0
	 * @return
	 */
	public Responser GetTreeAll(){
		SP2Service SPService = (SP2Service) ServiceFactory.getService("SP2Service");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(SPService.QueryShiPinAll()));
		return responser;
	}
	
}

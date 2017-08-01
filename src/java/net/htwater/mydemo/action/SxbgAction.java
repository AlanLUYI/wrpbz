/**
 * 失信曝光服务
 * @author chenchu
 * @Date 2014-03-24
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import net.htwater.mydemo.service.SxbgService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class SxbgAction extends DoAction {
	
	/**
	 * 查询失信曝光信息
	 * @Date 2014-03-24
	 * @since v 1.0
	 * @return
	 */
	public Responser QuerySxbgInfo(){
		SxbgService sxbgService = (SxbgService) ServiceFactory.getService("SxbgService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(sxbgService.QuerySxbgInfo(params.getParam("type"))));
		return responser;
	}
}

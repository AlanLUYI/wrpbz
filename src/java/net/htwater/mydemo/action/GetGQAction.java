/**
 * 工情信息
 * @author YSF
 * @Date 2014-07-7
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import net.htwater.mydemo.service.GetGQService;

public class GetGQAction extends DoAction {

	/**
	 * 工情系数
	 * @Date 2014-07-08
	 * @since v 1.0
	 * @return
	 */
	public Responser QueryGQ() {  
		GetGQService service = (GetGQService) ServiceFactory.getService("GetGQService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryGQ())); 
		return responser;
	}
	
	/**
	 * 修改工情配置表
	 * @Date 2014-07-09
	 * @since v 1.0
	 * @return
	 */
	public Responser updateGQ(){
		GetGQService service=(GetGQService)ServiceFactory.getService("GetGQService");
		String resultde="";
		int updateresult=service.updateGQ(params.getParam("STCD"), params.getParam("RTUCD"), params.getParam("RTUNM"), params.getParam("RTUCD1"), params.getParam("RTUNM1"), params.getParam("RTUCD2"), params.getParam("RTUNM2"),params.getParam("CZXS"),params.getParam("XZXS"));
		if(updateresult==1)
		{
			resultde="{\"result\":true,\"message\":\"更新成功\"}";
		}
		else 
		{
			resultde="{\"result\":false,\"message\":\"更新失败\"}";
		}
		responser.setRtType(JSON);
		responser.setRtString(resultde);
		return responser;
	}
	/**
	 * 水库承洪配置
	 * @Date 2014-09-25
	 * @since v 1.0
	 * @return
	 */
	public Responser getSKCHtab(){
		GetGQService service=(GetGQService)ServiceFactory.getService("GetGQService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getSKCHtab()));
		return responser;
	}
	/**
	 * 修改水库承洪配置表
	 * @Date 2014-09-25
	 * @since v 1.0
	 * @return
	 */
	public Responser updateSKCHtab(){
		GetGQService service=(GetGQService)ServiceFactory.getService("GetGQService");
		String resultde="";
		int updateresult=service.updateSKCHtab(params.getParam("resID"), params.getParam("rateV"));

		if(updateresult==1)
		{
			resultde="{\"result\":true,\"message\":\"更新成功\"}";
		}
		else 
		{
			resultde="{\"result\":false,\"message\":\"更新失败\"}";
		}
		responser.setRtType(JSON);
		responser.setRtString(resultde);
		return responser;
	}
	/**
	 * 功能菜单列表
	 * @Date 2014-09-25
	 * @since v 1.0
	 * @return
	 */
	public Responser getMenuList(){
		GetGQService service=(GetGQService)ServiceFactory.getService("GetGQService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getMenuList()));
		return responser;
	}
}

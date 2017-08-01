/**
 * @author chenchu
 * @date 2014/3/27
 * @since v1.0
 * @info 水质监测服务
 */

package net.htwater.mydemo.action;

import net.htwater.mydemo.service.WaterQuaService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class WaterQuaAction extends DoAction {

	/**
	 * 查询有效的时间
	 * @return
	 */
	public Responser QueryValidTime(){
		WaterQuaService waterQuaService = (WaterQuaService) ServiceFactory.getService("WaterQuaService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(waterQuaService.QueryValidTime()));
		return responser;
	}
	
	/**
	 * 查询水源地信息
	 * @return
	 */
	public Responser QuerySydInfo(){
		WaterQuaService waterQuaService = (WaterQuaService) ServiceFactory.getService("WaterQuaService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(waterQuaService.QuerySydInfo(params.getParam("year"), params.getParam("month"))));
		return responser;
	}
	
	/**
	 * 查询主要江河信息
	 * @return
	 */
	public Responser QueryRivInfo(){
		WaterQuaService waterQuaService = (WaterQuaService) ServiceFactory.getService("WaterQuaService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(waterQuaService.QueryRivInfo(params.getParam("year"), params.getParam("month"))));
		return responser;
	}
	
	/**
	 * 查询平原河网信息
	 * @return
	 */
	public Responser QueryPlainRivInfo(){
		WaterQuaService waterQuaService = (WaterQuaService) ServiceFactory.getService("WaterQuaService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(waterQuaService.QueryPlainRivInfo(params.getParam("year"), params.getParam("month"))));
		return responser;
	}
}

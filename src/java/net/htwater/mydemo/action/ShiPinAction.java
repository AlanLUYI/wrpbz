/**
 * 获取视频监视列表
 * @author yangshengfei
 * @Date 2014-06-27
 * @since v 1.0
 */

package net.htwater.mydemo.action;

import net.htwater.mydemo.service.ShiPinService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.HttpUtil;

public class ShiPinAction extends DoAction {
	
	String apiUrl = "http://192.168.100.4:8181/htpms/";
	String key = "14e43d5e-97c3-427c-adcc-684ec5df1bbb";
	String secret = "E44DE5C5F966735F2873525866DF7108";
	/**
	 * 获取视频监视列表
	 * @Date 2014-06-27
	 * @since v 1.0
	 * @return
	 */
	public Responser GetTree(){
		ShiPinService SPService = (ShiPinService) ServiceFactory.getService("ShiPinService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(SPService.QueryShiPin()));
		return responser;
	}
	
	/**
	 * 获取视频监视列表All
	 * @Date 2014-07-30
	 * @since v 1.0
	 * @return
	 */
	public Responser GetTreeAll(){
		ShiPinService SPService = (ShiPinService) ServiceFactory.getService("ShiPinService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(SPService.QueryShiPinAll()));
		return responser;
	}
	/**
	 * 获取视频XML
	 * @Date 2014-07-31
	 * @since v 1.0
	 * @return
	 */
	public Responser GetCameraXML(){
		ShiPinService SPService = (ShiPinService) ServiceFactory.getService("ShiPinService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(SPService.QueryCameraXML()));
		return responser;
	}
	
	public Responser GetALLCameraXML(){
		String rtString=HttpUtil.htOauthRequest(apiUrl,
				"getVideoXML", key, secret);
		responser.setRtType(JSON);
		//esponser.setRtString(HttpUtil.getResponseByApacheGET("http://61.153.21.221:8081/htos/GetAllProjectXMLListService!base"));
		responser.setRtString(rtString);
		return responser;
	}
	
}

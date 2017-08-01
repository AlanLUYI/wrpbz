/**
 * 工情服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.hos.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.Util.PropertiesHelper;
import net.htwater.hos.helper.impl.GqSImpl;
import net.htwater.hos.helper.impl.HtOauthServiceImpl;
import net.htwater.hos.helper.impl.ReservoirServiceImpl;
import net.htwater.hos.service.GqService;
import net.htwater.hos.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.DateUtil;
import cn.miao.framework.util.HttpUtil;

public class GqAction extends DoAction {
	/*PropertiesHelper propHelper = new PropertiesHelper("/htpms.properties");

	String apiUrl = propHelper.getPropertyValue("htpms.url");
	String key = propHelper.getPropertyValue("htpms.key");
	String secret = propHelper.getPropertyValue("htpms.secret");
	
	HtOauthServiceImpl htOSImpl = new HtOauthServiceImpl();
	ReservoirServiceImpl rSImpl = new ReservoirServiceImpl();*/
	
	
	public Responser getsplist() {  
		GqService service = (GqService) ServiceFactory.getService("GqService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getsplist())); 
		return responser;
	}
}

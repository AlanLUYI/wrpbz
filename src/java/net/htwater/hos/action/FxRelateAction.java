package net.htwater.hos.action;

import java.util.List;
import java.util.Map;

import net.htwater.hos.service.FxRelateService;
import net.sf.json.JSONArray;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.PinyinUtil;

public class FxRelateAction extends DoAction{

	public Responser queryMaterial()
	{
		FxRelateService service = (FxRelateService) ServiceFactory.getService("FxRelate");
		
		responser.setRtString(parseJSON(service.queryMaterial()));
		responser.setRtType(JSON);
		return responser;
	}
	
	public Responser queryResTeam()
	{
		FxRelateService service = (FxRelateService) ServiceFactory.getService("FxRelate");
		List<Map<String,Object>> result = service.queryResTeam();
		for(Map<String,Object> m : result){
			m.put("_s", m.get("RESTEAMNAME").toString()+PinyinUtil.converterToPinYin(m.get("RESTEAMNAME").toString()));
		}
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	}
	
	/**
	 * 获取山洪灾害基础信息
	 * @return
	 */
	public Responser getBaseInfo()
	{
		FxRelateService service = (FxRelateService) ServiceFactory.getService("FxRelate");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getBaseInfo(params.getParam("type"))));
		return responser;
	}
	
	/**
	 * 按新上报数据，获取山洪灾害基础信息
	 * @return
	 */
	public Responser getSHBaseInfo(){
		FxRelateService service = (FxRelateService) ServiceFactory.getService("FxRelate");
		responser.setRtType(JSON);
		List<Map<String,Object>> result = service.getSHBaseInfo(params.getParam("type"));
		for(Map<String,Object> m : result){
			if(m.get("NM")!=null)
				m.put("_s", m.get("NM").toString()+PinyinUtil.converterToPinYin(m.get("NM").toString()));
			else
				m.put("_s", "");
		}
		responser.setRtString(parseJSON(result));
		return responser;
	}
}

package net.htwater.mydemo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.GcaqService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
//import net.htwater.cms.util.HttpUtil;

public class GcaqServiceImpl implements GcaqService {
	BaseDao daoQGJ = DaoFactory.getDao(QGJ_SMP);

	public List<Map<String, Object>> Querygcaq_res() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		rtList = daoQGJ.callProcedure("{call proczhsl_工程安全水库()}");
		return rtList; 
	}
	
	public List<Map<String, Object>> getAllGateSafeData() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " select ENNM_CD,ST_ID,ENNM,ORDBY,LAT,LONG,ICON,FSTCD,BSTCD,CNT from  base_gate where ISHasSafe=1  "; 
		rtList = daoQGJ.executeQuery(sql);
		for (Map<String, Object> map : rtList) {
			map.put("problemCount", "1");
			map.put("tm", "2014-02-12 08:00");
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> getAllDikeSafeData() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " select  SECTION_ID,ENNM,ENNMCD,SECTION_NAME,LAT,LONG from base_dike_section order by ENNMCD "; 
		rtList = daoQGJ.executeQuery(sql);
		for (Map<String, Object> map : rtList) {
			if (map.get("SECTION_ID").toString().equals("8")) {
				map.put("problemCount", "1");
				map.put("tm", "2014-02-19 08:00");
			} else {
				map.put("problemCount", "0");
				map.put("tm", "2014-02-19 08:00");
			}
		}
		return rtList; 
	}
}

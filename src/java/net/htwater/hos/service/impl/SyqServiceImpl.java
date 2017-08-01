package net.htwater.hos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.htwater.hos.service.SyqService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
//import net.htwater.cms.util.HttpUtil;
import cn.miao.framework.util.PinyinUtil;

public class SyqServiceImpl implements SyqService {
	BaseDao HTSQ = DaoFactory.getDao(HT_NBSQDB);

	public List<Map<String, Object>> Querywind(String TM) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM};
		rtList = HTSQ.callProcedure("{call proczhsl_风向风速(?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryll_line(String TM1,String TM2,String stcd) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2,stcd};
		rtList = HTSQ.callProcedure("{call proczhsl_流量过程(?,?,?)}",args);
		return rtList; 
	}
	 
	public List<Map<String, Object>> Queryll(String TM) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM};
		rtList = HTSQ.callProcedure("{call proczhsl_流量监控(?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryswcw_line(String TM1,String TM2,String stcd,String sttp) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2,stcd,sttp};
		rtList = HTSQ.callProcedure("{call proczhsl_水库河道潮位过程(?,?,?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryreswater(String TM) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM};
		rtList = HTSQ.callProcedure("{call proczhsl_水库实时水位(?)}",args);
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String cname = map.get("STNM").toString(); 
				map.put("_s", cname + PinyinUtil.converterToPinYin(cname));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> QueryMRvWater(String TM) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM};
		rtList = HTSQ.callProcedure("{call proczhsl_三江干流水位(?)}",args);
		//rtList = HTSQ.callProcedure("{call proc_MRvWater_QueryByTM2(?)}",args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String cname = map.get("STNM").toString(); 
				map.put("_s", cname + PinyinUtil.converterToPinYin(cname));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> QueryPRvWater(String TM) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM};
		rtList = HTSQ.callProcedure("{call proczhsl_平原水位(?)}",args);
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				if(map.get("ISRCONTROL")==null) 
					map.put("ISRCONTROL", false);
				String cname = map.get("STNM").toString(); 
				map.put("_s", cname + PinyinUtil.converterToPinYin(cname));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> Querytidewater(String TM) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM};
		rtList = HTSQ.callProcedure("{call proczhsl_潮位(?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryrian_hour_line(String TM1,String TM2,String stcd) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2,stcd};
		rtList = HTSQ.callProcedure("{call proczhsl_雨量站时段雨量_过程(?,?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryrian_hour(String TM1,String TM2) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2};
		rtList = HTSQ.callProcedure("{call proczhsl_雨量站时段雨量(?,?)}",args);
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String stnm = map.get("STNM").toString(); 
				map.put("_s", stnm + PinyinUtil.converterToPinYin(stnm));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryrian_day_line(String TM1,String TM2,String stcd) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2,stcd};
		rtList = HTSQ.callProcedure("{call proczhsl_雨量站日雨量_过程(?,?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryrian_day(String TM1,String TM2) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2};
		rtList = HTSQ.callProcedure("{call proczhsl_雨量站日雨量(?,?)}",args);
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String stnm = map.get("STNM").toString(); 
				map.put("_s", stnm + PinyinUtil.converterToPinYin(stnm));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryarearian_hour_line(String TM1,String TM2,String region) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2,region};
		rtList = HTSQ.callProcedure("{call proczhsl_区县面时段雨量_过程(?,?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryarearian_hour(String TM1,String TM2) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2};
		rtList = HTSQ.callProcedure("{call proczhsl_区县面时段雨量(?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryarearian_day_line(String TM1,String TM2,String region) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2,region};
		rtList = HTSQ.callProcedure("{call proczhsl_区县面日雨量_过程(?,?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryarearian_day(String TM1,String TM2) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2};
		rtList = HTSQ.callProcedure("{call proczhsl_区县面日雨量(?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryresrian_line(String TM1,String TM2,String rsnm) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2,rsnm};
		rtList = HTSQ.callProcedure("{call proczhsl_大型水库面雨量_过程(?,?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryresrian(String TM1,String TM2) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2};
		rtList = HTSQ.callProcedure("{call proczhsl_大型水库面雨量(?,?)}",args);
		return rtList; 
	}
	
	
	public List<Map<String, Object>> QueryResSupport(String TM1,String TM2) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2};
		rtList = HTSQ.callProcedure("{call proczhsl_水库承洪(?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> QueryResCapacity(String stnm) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {stnm};
		rtList = HTSQ.callProcedure("{call proczhsl_库容曲线(?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> QueryPlainCapacity(String TM) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM};
		rtList = HTSQ.callProcedure("{call proczhsl_平原承洪(?)}",args);
		return rtList; 
	}
}

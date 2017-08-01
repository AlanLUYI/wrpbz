/**
 * 水利工程服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.hos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import net.htwater.hos.service.SlgcService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.PinyinUtil;
//import net.htwater.cms.util.HttpUtil;

public class SlgcServiceImpl implements SlgcService {
	BaseDao HTSQ = DaoFactory.getDao(SLPC);
	BaseDao daoZHSL = DaoFactory.getDao(DB_ZHSL);
	 
	public List<Map<String, Object>> Queryres() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		rtList = HTSQ.callProcedure("{call proczhsl_水库()}");
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("ENNM").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	/**
	 * 水库查询
	 * @Date 2014-02-19
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> QueryFORres(String ENNM,String CITY,String TOTAL_s) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		rtList = HTSQ.callProcedure("{call proczhsl_水库()}");
		String a[] = TOTAL_s.split("-");
		int small = Integer.parseInt(a[0]);
		int big = Integer.parseInt(a[1]);			
		   if(CITY.equals("全市")){
			    for(Map<String,Object> map : rtList){
				float total = Float.parseFloat(map.get("TOTAL_S").toString());
				  if(map.get("ENNM").toString().contains(ENNM)&&total>=small&&total<big){
					  result.add(map);
				  }
				}
			}
			else {
				for(Map<String,Object> map : rtList){
				float total = Float.parseFloat(map.get("TOTAL_S").toString());
				  if(map.get("ENNM").toString().contains(ENNM)&&total>=small&&total<big&&map.get("CITY").equals(CITY)){
					result.add(map);
				  }
				}
			}
		
		return result;
	}
	public List<Map<String, Object>> Queryriver() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		rtList = HTSQ.callProcedure("{call proczhsl_河道()}");
		return rtList; 
	}
	/**
	 * 河道查询
	 * @Date 2014-02-19
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> QueryFORriver(String STNM,String classify) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		rtList = HTSQ.callProcedure("{call proczhsl_河道()}");
		if(classify.equals("不限")){
			 for(Map<String,Object> map : rtList){
				 if(map.get("STNM").toString().contains(STNM)){
					 result.add(map);
				 }
			 }
		}else{
			for(Map<String,Object> map : rtList){
				 if(map.get("STNM").toString().contains(STNM)&&map.get("classify").toString().equals(classify)){
					 result.add(map);
				 }
			 }
		}		
		return result; 
	}
	public List<Map<String, Object>> Querygate() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		rtList = HTSQ.callProcedure("{call proczhsl_水闸()}");
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("ENNM").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}	
	/**
	 * 水闸查询
	 * @Date 2014-02-19
	 * @since v 1.0
	 * @return   
	 */
	public List<Map<String, Object>> QueryFORgate(String ENNM,String TP,String CITY) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		rtList = HTSQ.callProcedure("{call proczhsl_水闸()}");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(TP.equals("不限")){
			if(CITY.equals("全市")){
				for(Map<String,Object> map : rtList){
					if(map.get("ENNM").toString().contains(ENNM)){
						result.add(map);
					}
				}
			}else{
				for(Map<String,Object> map : rtList){
					if(map.get("ENNM").toString().contains(ENNM)&&map.get("CITY").equals(CITY)){
						result.add(map);
					}
				}
			}
		}else{
			if(CITY.equals("全市")){
				for(Map<String,Object> map : rtList){
					if(map.get("ENNM").toString().contains(ENNM)&&map.get("TP").equals(TP)){
						result.add(map);
					}
				}
			}else{
				for(Map<String,Object> map : rtList){
					if(map.get("ENNM").toString().contains(ENNM)&&map.get("TP").equals(TP)&&map.get("CITY").equals(CITY)){
						result.add(map);
					}
				}
			}

		}
		return result; 
	}	
	public List<Map<String, Object>> Querypumb() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		rtList = HTSQ.callProcedure("{call proczhsl_泵站()}");
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("ENNM").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	/**
	 * 泵站查询
	 * @Date 2014-02-19
	 * @since v 1.0
	 * @return   
	 */
	public List<Map<String, Object>> QueryFORpumb(String ENNM,String CITY,String TP) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		rtList = HTSQ.callProcedure("{call proczhsl_泵站()}");
		if(TP.equals("不限")){
			if(CITY.equals("全市")){
				for(Map<String,Object> map : rtList){
					if(map.get("ENNM").toString().contains(ENNM)){
						result.add(map);
					}
				}
			}else{
				for(Map<String,Object> map : rtList){
					if(map.get("ENNM").toString().contains(ENNM)&&map.get("CITY").equals(CITY)){
						result.add(map);
					}
				}
			}
		}else{
			if(CITY.equals("全市")){
				for(Map<String,Object> map : rtList){
					if(map.get("ENNM").toString().contains(ENNM)&&map.get("TP").equals(TP)){
						result.add(map);
					}
				}
			}else{
				for(Map<String,Object> map : rtList){
					if(map.get("ENNM").toString().contains(ENNM)&&map.get("TP").equals(TP)&&map.get("CITY").equals(CITY)){
						result.add(map);
					}
				}
			}

		}
		return result; 
	}
	public List<Map<String, Object>> Querydike() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		rtList = HTSQ.callProcedure("{call proczhsl_堤防()}");
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("ENNM").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	/**
	 * 堤防查询
	 * @Date 2014-02-19
	 * @since v 1.0
	 * @return   
	 */
	public List<Map<String, Object>> QueryFORdike(String ENNM,String FLOOD_STAN) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		rtList = HTSQ.callProcedure("{call proczhsl_堤防()}");
		if(FLOOD_STAN.equals("不限")){
			for(Map<String,Object> map : rtList){
				if(map.get("ENNM").toString().contains(ENNM)){
					result.add(map);
				}
			}
		}else{
			for(Map<String,Object> map : rtList){
				if(map.get("ENNM").toString().contains(ENNM)&&map.get("FLOOD_STAN").toString().equals(FLOOD_STAN)){
					result.add(map);
				}				
			}
		}
		return result; 
	}
	
	public List<Map<String, Object>> Queryhaitang() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		rtList = HTSQ.callProcedure("{call proczhsl_海塘()}");
		return rtList; 
	}
	
	public List<Map<String, Object>> Querywaterstation() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		rtList = daoZHSL.callProcedure("{call proczhsl_水站()}");
		for(Map<String,Object> map:rtList){
			String str=map.get("水站名称").toString();
			map.put("_s", str+PinyinUtil.converterToPinYin(str));
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> Querydistinctqx() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql = " SELECT distinct [乡镇] ,[县市区] FROM [dbo].[水站信息]  "; 
		rtList = daoZHSL.executeQuery(sql); 
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryhydropowerstation() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		rtList = daoZHSL.callProcedure("{call proczhsl_水电站()}");
		return rtList; 
	}
	@Override
	public Map<String, Object> searchAllByName(String name) {
		List<Map<String,Object>> mapRes = searchRes("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapGate = searchGate("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapPump = searchPump("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapDike = searchDike("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapSeawall = searchSeawall("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapGen = searchGen("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapStation = searchStation("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapPool = searchStation("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapMRiver = searchStation("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapPRiver = searchStation("where ENNM like '%"+name+"%'");
		List<Map<String,Object>> mapValley = searchStation("where ENNM like '%"+name+"%'");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("res", mapRes);
		map.put("gate", mapGate);
		map.put("pump", mapPump);
		map.put("dike", mapDike);
		map.put("seawall", mapSeawall);
		map.put("gen", mapGen);
		map.put("station", mapStation);
		map.put("shantang", mapPool);
		map.put("shandiheliu", mapMRiver);
		map.put("pingyuanheliu", mapPRiver);
		map.put("xiaoliuyu", mapValley);
		return map;
	}
	@Override
	public List<Map<String, Object>> searchRes(String where) {
		String sql="select * from view_RES ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchGate(String where) {
		String sql="select * from view_GATE ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchPump(String where) {
		String sql="select * from view_PUMP ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchGen(String where) {
		String sql="select * from view_GEN ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchPool(String where) {
		String sql="select * from view_POOL ";
		if(where.trim().length()>5)
			sql += where;
		List<Map<String,Object>> list =  HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchDike(String where) {
		String sql="select * from view_DIKE ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchSeawall(String where) {
		String sql="select * from view_SEAWALL ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchIntake(String where) {
		
		return null;
	}
	@Override
	public List<Map<String, Object>> searchSource(String where) {
		
		return null;
	}
	@Override
	public List<Map<String, Object>> searchOutfall(String where) {
		
		return null;
	}
	@Override
	public List<Map<String, Object>> searchWell(String where) {
		
		return null;
	}
	@Override
	public List<Map<String, Object>> searchStation(String where) {
		String sql="select * from view_STATION ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchMRiver(String where) {
		String sql="select * from view_MRIVER ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchPRiver(String where) {
		String sql="select * from view_PRIVER ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> searchValley(String where) {
		String sql="select * from view_VALLEY ";
		if(where.trim().length()>5)
			sql=sql + where;
		List<Map<String,Object>> list = HTSQ.executeQuery(sql);
		return list;
	}
	@Override
	public Map<String, Object> getLocation(String ennmcd, String type) {
		String viewName="";
		if(type.equals("res"))
			viewName="view_RES";
		else if(type.equals("gate"))
			viewName ="view_GATE";
		else if(type.equals("dike"))
			viewName ="view_DIKE";
		else if(type.equals("pool"))
			viewName ="view_POOL";
		else if(type.equals("pump"))
			viewName ="view_PUMP";
		else if(type.equals("valley"))
			viewName ="view_VALLEY";
		else if(type.equals("mriver"))
			viewName ="view_MRIVER";
		String sql="select top 1 * from "+viewName+" where ennmcd=?";
		Object[] args = {ennmcd};
		Map<String,Object> resultMap = HTSQ.executeQueryObject(sql,args);
		return resultMap;
	}
}

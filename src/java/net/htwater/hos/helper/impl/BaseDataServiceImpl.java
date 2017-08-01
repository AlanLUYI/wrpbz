/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  2013年12月17日 zzj 
 * 
 * Copyright(c) 2013, by htwater.net. All Rights Reserved.
 */
package net.htwater.hos.helper.impl;

import java.util.List;
import java.util.Map;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.PinyinUtil;

public class BaseDataServiceImpl  {
	BaseDao daoSLPC = DaoFactory.getDao("SLPC");
	BaseDao daoHTPMS = DaoFactory.getDao("HOS");

	
	public List<Map<String, Object>> getRsvrList() {
		// TODO Auto-generated method stub
		String sql = "SELECT [NO],[CITY],[TOWN],[CITYCD],[ENNM],[ENNMCD],[RVNM],[RVCD],(LONG_D+LONG_M/60+LONG_S/3600) as LGTD,(LAT_D+LAT_M/60+LAT_S/3600) as LATD,(case when TOTAL_S>=10000 then 'B' when TOTAL_S>=1000 then 'M' when TOTAL_S>=100 then 'S1' when TOTAL_S>=10 then 'S2' else 'P' end) as TYPE,[RSVTP],[DAM_VALLEY_AREA],[DAM_RUN_OFF],[PROJ_GRAD],[DAM_GRAD],[DAM_HIGHT],[DAM_LEN],[MAX_FLOOD_Q],[ELEV_SYS],[DAM_ELEV],[DESIGN_FLOOD_STAN],[CHECK_FLOOD_STAN],[CHECK_FLOOD_Z],[DESIGN_FLOOD_Z],[FLOOD_TOP_Z],[FLOOD_NORMAL_Z],[FLOOD_LIMIT_Z],[DEAD_Z],[TOTAL_S],[REGU_S],[FLOOD_S],[UTIL_S],[DEAD_S],[NORMAL_Z_AREA],[DESIGN_SUPPLY],[INTAKE_CNT],[SUPPLY_TARGET],[DESIGN_IRRI_AREA],[IRRI_TARGET] FROM [SLPC].[dbo].[P201_RSV] where ID_INDEX<422 ORDER BY TOTAL_S DESC";
		List<Map<String,Object>> list=daoSLPC.executeQuery(sql);
		for(int i=0;i<6;i++){
			Map<String,Object> rsvr=list.get(i);
			if(rsvr.get("ENNM").equals("白溪水库")){
				rsvr.put("LGTD", 121.250372222222);rsvr.put("LATD", 29.2007972222222);
			}else if(rsvr.get("ENNM").equals("亭下水库")){
				rsvr.put("LGTD", 121.221052777778);rsvr.put("LATD", 29.6556555555556);
			}else if(rsvr.get("ENNM").equals("四明湖水库")){
				rsvr.put("LGTD", 121.051588888889);rsvr.put("LATD", 29.9523805555556);
			}else if(rsvr.get("ENNM").equals("皎口水库")){
				rsvr.put("LGTD", 121.270855555556);rsvr.put("LATD", 29.8376722222222);
			}else if(rsvr.get("ENNM").equals("周公宅水库")){
				rsvr.put("LGTD", 121.205983333333);rsvr.put("LATD", 29.7954916666667);
			}else if(rsvr.get("ENNM").equals("横山水库")){
				rsvr.put("LGTD", 121.357294444444);rsvr.put("LATD", 29.5753472222222);
			}
		}
		return list;
	}

	
	public List<Map<String, Object>> getGateList() {
		String sql="select a.*,(case when a.GATE_Q>=1000 then 'B' when a.GATE_Q>=100 then 'M' when a.GATE_Q>=20 then 'S1' when a.GATE_Q>0 then 'S2' else 'N' end) as [TYPE] from (SELECT [NO],[CITY],[TOWN],[VILLAGE],[CITYCD],[ENNM],[ENNMCD],(LONG_D+LONG_M/60+LONG_S/3600) as LGTD,(LAT_D+LAT_M/60+LAT_S/3600) as LATD,[RVNM],[RVCD],[ISGATE],[IS_GATES],[STATUS],[GRADE],[BUILD_GRADE],[HOLE_CNT],[HOLE_WIDTH],[GATETP],(case GATETP when '挡潮闸' then TIDAL_GATE_Q when '引(进)水闸' then IN_GATE_Q when '排(退)水闸' then OUT_GATE_Q when '节制闸' then CTRL_GATE_Q when '分(泄)洪闸' then FLOOD_GATE_Q else 0 end) as GATE_Q,(case GATETP when '挡潮闸' then TIDAL_DESIGN_STAN when '引(进)水闸' then IN_DESIGN_Q when '排(退)水闸' then OUT_DESIGN_STAN when '节制闸' then CTRL_DESIGN_STAN when '分(泄)洪闸' then FLOOD_DESIGN_STAN else 0 end) as GATE_STAN FROM [SLPC].[dbo].[P203_GATE])as a order by gate_q desc";
		return daoSLPC.executeQuery(sql);
	}
	
	
	public List<Map<String, Object>> getPumpList() {
		String sql="SELECT [NO],[CITY],[TOWN],[VILLAGE],[CITYCD],[ENNM],[ENNMCD],(LONG_D+LONG_M/60+LONG_S/3600) as LGTD,(LAT_D+LAT_M/60+LAT_S/3600) as LATD,[RVNM],[RVCD],[IRRNM],[IRRCD],[PUMPTP],[ISGATE],[ISLEAD],[STATUS],[TASK],[GRADE],[BUILD_GRADE],[Q],[KW],[DESIGN_LIFT],[CNT],TP=(case when isnull(Q,0) <2 then 'S2' when isnull(Q,0) <10 and isnull(Q,0) >=2 then 'S1' when isnull(Q,0) <50 and isnull(Q,0) >=10 then 'M' when isnull(Q,0) <200 and isnull(Q,0) >=50 then 'B2' when isnull(Q,0) >=200 then 'B1' end) FROM [SLPC].[dbo].[P204_PUMP] ORDER BY Q DESC";
		return daoSLPC.executeQuery(sql);
	}

	
	public List<Map<String, Object>> getContactTypes() {
		String sql = "SELECT [id],[dept],[typeid] FROM [tb_contact_dept] where typeid=0";
		return daoHTPMS.executeQuery(sql);
	}

	
	public List<Map<String, Object>> getContactByType(String type) {
		String sql = "SELECT a.[id],[name],[phone],[title],[mobile],[smobile],a.[dept],a.[area],[ord] FROM [tb_contacts] as a left join tb_contact_dept as b on a.area=b.dept where b.id="
				+ type + "";
		List<Map<String, Object>> list = daoHTPMS.executeQuery(sql);
		for (Map<String, Object> map : list) {
			map.put("searchkey", PinyinUtil.converterToPinYin(map.get("name").toString()));
		}
		return list;
	}
}

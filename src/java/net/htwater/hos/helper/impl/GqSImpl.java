/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  2014年6月1日 zzj 
 * 工情数据服务，包括水闸和翻水站
 * Copyright(c) 2013, by htwater.net. All Rights Reserved.
 */
package net.htwater.hos.helper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

public class GqSImpl {
	BaseDao daoMssql = DaoFactory.getDao("DSDB");
	BaseDao daoSLPC = DaoFactory.getDao("SLPC");
	
	public List<Map<String, Object>> realtimeGate() {
		String sql="declare @mytm datetime set @mytm=DATEADD(MI,-20,GETDATE()) select a.ZMNM,a.ZMID,a.REGION,a.OUT,a.CITY,a.ORDBY,a.CNT,a.SPID, b.ZQSW,b.ZHSW,b.LL,b.LLs,b.KDs from DS_ZM as a left join (select ZMID,sum(LL) LL,ZQSW,ZHSW, (select convert(varchar(20),case when(TM<@mytm) then 0 else LL end)+',' from DS_ZM_Unit where ZMID=t.ZMID for xml path('')) as LLs,(select convert(varchar(20),case when(TM<@mytm) then 0 else KD end)+',' from DS_ZM_Unit where ZMID=t.ZMID for xml path('')) as KDs from DS_ZM_Unit t where t.TM>@mytm group by ZMID,ZQSW,ZHSW) as b on a.ZMID=b.ZMID where a.STATUS=1 and ZQSW is not null and ZHSW is not null";
		List<Map<String,Object>> result= daoMssql.executeQuery(sql);
		
		checkBaseGate(result);
		
		return dealGate(result);
	}
	
	public List<Map<String, Object>> realtimePump() {
		String sql="select a.BZNM,LL=(select SUM(ll) from DS_BZ_Unit as b where a.bzid=b.BZID),a.REGION,a.PRJID,OPENCNT=(select COUNT(ll) from DS_BZ_Unit as b where a.bzid=b.BZID and b.LL>0),a.CNT from DS_BZ as a";
		return daoMssql.executeQuery(sql);
	}
	
	private static List<Map<String,Object>> configGate;//存储了闸门配置信息
	/**
	 * 检查内存里是否已有闸门配置信息
	 * @param list
	 */
	private void checkBaseGate(List<Map<String,Object>> list){
		if( null==list || list.size()==0)
			return ;
		if(configGate==null || configGate.size()<1){
			configGate=new ArrayList<Map<String,Object>>();
			
			String sql_config="select STNM,RTUCD,RTUNM,RTUCD1,RTUNM1,RTUCD2,RTUNM2 from SWINF";
			List<Map<String,Object>> configlist=daoMssql.executeQuery(sql_config);
			
			String where ="";
			
			for(Map<String,Object> item : list){
				Map<String,Object> c=new HashMap<String,Object>();
				c.put("ZMNM", item.get("ZMNM").toString());
				c.put("ZMID", item.get("ZMID"));
				c.put("SPID", item.get("SPID"));
				for(Map<String,Object> map : configlist){
					if(c.get("ZMNM").toString().equals(map.get("STNM"))){
						c.put("RTUCD", map.get("RTUCD"));
						c.put("RTUNM", map.get("RTUNM"));
						c.put("RTUCD1", map.get("RTUCD1"));
						c.put("RTUNM1", map.get("RTUNM1"));
						c.put("RTUCD2", map.get("RTUCD2"));
						c.put("RTUNM2", map.get("RTUNM2"));
					}
				}
				configGate.add(c);
				
				where=where + ","+item.get("SPID");
			}
			
			where=" where ENNMCD in ("+where.substring(1)+")";
			
			String sql_base="SELECT "
					+"      [ENNMCD] "
					+"      ,(LONG_D+LONG_M/60+LONG_S/3600) as LGTD "
					+"	  ,(LAT_D+LAT_M/60+LAT_S/3600) as LATD "
					+"       "
					+"      ,(case GATETP when '挡潮闸' then TIDAL_GATE_Q when '引(进)水闸' then IN_GATE_Q when '排(退)水闸' then OUT_GATE_Q when '节制闸' then CTRL_GATE_Q when '分(泄)洪闸' then FLOOD_GATE_Q else 0 end) "
					+"       as SJLL "
					+"  FROM [SLPC].[dbo].[P203_GATE] "+where;
			List<Map<String,Object>> baseGate=daoSLPC.executeQuery(sql_base);
			for(Map<String,Object> c : configGate){
				for(Map<String,Object> base : baseGate){
					if(c.get("SPID").equals(base.get("ENNMCD"))){
						c.put("SJLL", base.get("SJLL").toString());
						c.put("LGTD", base.get("LGTD"));
						c.put("LATD", base.get("LATD"));
					}
				}
			}
		}
	}
	/**
	 * 可以为闸门工情数据加上其他的配置信息
	 * @param list
	 * @return
	 */
	private List<Map<String,Object>> dealGate(List<Map<String,Object>> list){
		for(Map<String,Object> map : list){
			for(Map<String,Object> c : configGate){
				if(map.get("SPID").equals(c.get("SPID"))){
					map.put("SJLL", c.get("SJLL").toString());
					map.put("LGTD", c.get("LGTD"));
					map.put("LATD", c.get("LATD"));
					map.put("RTUCD", c.get("RTUCD"));
					map.put("RTUNM", c.get("RTUNM"));
					map.put("RTUCD1", c.get("RTUCD1"));
					map.put("RTUNM1", c.get("RTUNM1"));
					map.put("RTUCD2", c.get("RTUCD2"));
					map.put("RTUNM2", c.get("RTUNM2"));
				}
			}
		}
		return list;
	} 
	
	
	public List<Map<String, Object>> queryGate(String tm,boolean recache) {
		if(recache) 
			configGate=null;
		String sql="SET NOCOUNT ON declare @ptm datetime set @ptm=? declare @tb table(ZMID int,UnitCD varchar(50),UnitName varchar(50), UnitNo int,LL float,KD float,ZQSW FLOAT,ZHSW FLOAT, TM DATETIME) insert @tb "
				+"select dt_rt.ZMID,dt_max.UnitCD,dt_rt.UnitName,dt_rt.UnitNO,dt_rt.LL,dt_rt.KD,dt_rt.ZQSW,dt_rt.ZHSW,dt_max.tm "
				+"from (select UnitCD,tm=(select top 1 tm from DS_ZM_Unit_His where DS_ZM_Unit_His.UnitCD=DS_ZM_Unit.UnitCD and TM < @ptm order by DS_ZM_Unit_His.TM desc) from DS_ZM_Unit "
				+") as dt_max left join DS_ZM_Unit_His dt_rt on dt_max.UnitCD=dt_rt.UnitCD and dt_max.tm=dt_rt.TM "
				+" "
				+"declare @mytm datetime set @mytm=DATEADD(MI,-20,@ptm) select a.ZMNM,a.ZMID,a.REGION,a.OUT,a.CITY,a.ORDBY,a.CNT,a.SPID, b.ZQSW,b.ZHSW,b.LL,b.LLs,b.KDs from DS_ZM as a left join (select ZMID,sum(LL) LL,ZQSW,ZHSW, (select convert(varchar(20),case when(TM<@mytm) then 0 else LL end)+',' from @tb where ZMID=t.ZMID for xml path('')) as LLs,(select convert(varchar(20),case when(TM<@mytm) then 0 else KD end)+',' from @tb where ZMID=t.ZMID for xml path('')) as KDs from @tb t where t.TM>@mytm group by ZMID,ZQSW,ZHSW) as b on a.ZMID=b.ZMID where a.STATUS=1 and ZQSW is not null and ZHSW is not null "
				;
		//String sql="declare @mytm datetime set @mytm=DATEADD(MI,-20,GETDATE()) select a.ZMNM,a.ORDBY,a.CNT,a.SPID, b.ZQSW,b.ZHSW,b.LL,b.LLs,b.KDs from DS_ZM as a left join (select ZMID,sum(LL) LL,ZQSW,ZHSW, (select convert(varchar(20),case when(TM<@mytm) then 0 else LL end)+',' from DS_ZM_Unit where ZMID=t.ZMID for xml path('')) as LLs,(select convert(varchar(20),case when(TM<@mytm) then 0 else KD end)+',' from DS_ZM_Unit where ZMID=t.ZMID for xml path('')) as KDs from DS_ZM_Unit t where t.TM>@mytm group by ZMID,ZQSW,ZHSW) as b on a.ZMID=b.ZMID where a.STATUS=1 and ZQSW is not null and ZHSW is not null";
		List<Map<String,Object>> result=daoMssql.executeQuery(sql,new Object[]{tm});
		
		checkBaseGate(result);
		
		return dealGate(result);
	}
	
	
	public List<Map<String, Object>> queryPump(String tm) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 实时水闸工情
	 */
	
	public List<Map<String,Object>> queryRTGate_(){
		return daoMssql.queryAll("ds_zm_unit_");
	}
	
	/**
	 * 某时刻水闸工情,精确到分钟
	 */
	
	public List<Map<String,Object>> queryGateByTm(String tm){
		if( null==tm || tm.equals("") )
			return null;
		String sql = "select * from dbo.ds_zm_unit_his_ a "
				+ "where a.TM=(CONVERT(CHAR(16),cast(? as datetime),120)+':00') and a.LLS is not null";
		return daoMssql.executeQuery(sql, new Object[]{tm});
	}
	
	/**
	 * 某时刻某水闸工情,精确到分钟
	 */
	
	public List<Map<String,Object>> queryGateByIDAndTm(String zmid,String tm){
		if( null==zmid || zmid.equals("") ||
			null==tm || tm.equals("") )
			return null;
		String sql = "select * from dbo.ds_zm_unit_his_ a where a.zmid=? "
				+ "and a.TM=(CONVERT(CHAR(16),cast(? as datetime),120)+':00') and a.LLS is not null";
		return daoMssql.executeQuery(sql, new Object[]{zmid,tm});
	}
	
	/**
	 * 某水闸流量过程,整5分钟数据
	 */
	
	public List<Map<String,Object>> queryGateLL(String zmid,String stm,String etm){
		if( null==zmid || zmid.equals("") ||
			null==stm || stm.equals("") ||
			null==etm || etm.equals(""))
				return null;
		String sql = "declare @stcd varchar(10),@stm datetime,@etm datetime;set @stcd=?;set @stm=?;set @etm=?;"
				+ " set @stm=cast((CONVERT(CHAR(16),@stm,120)+':00') as datetime);"
				+ " set @etm=cast((CONVERT(CHAR(16),@etm,120)+':00') as datetime);"
				+ " select * from DS_ZM_UNIT_HIS_ where zmid=@stcd and tm>=@stm and tm<=@etm and"
				+ " datediff(minute,@stm,tm) %5 = 0";
		return daoMssql.executeQuery(sql, new Object[]{zmid,stm,etm});
	}
	
	/**
	 * 某水闸过水量过程
	 */
	
	public List<Map<String,Object>> queryGateGS(String zmid,String stm,String etm){
		if( null==zmid || zmid.equals("") ||
			null==stm || stm.equals("") ||
			null==etm || etm.equals(""))
					return null;
		String sql = "declare @stcd varchar(10),@stm datetime,@etm datetime;set @stcd=?;set @stm=?;set @etm=?;"
				+"";
		List<Map<String, Object>> rtlist = new ArrayList<Map<String,Object>>();
		return rtlist;
	}
	
	/**
	 * 某水闸过水总量
	 */
	
	public List<Map<String,Object>> queryGateGSSum(String zmid,String stm,String etm){
		List<Map<String, Object>> rtlist = new ArrayList<Map<String,Object>>();
		return rtlist;
	}
	
	/**
	 * 某些水闸过水总量
	 */
	
	public List<Map<String,Object>> queryGateGSSumByCondition(String zmid,String stm,String etm){
		List<Map<String, Object>> rtlist = new ArrayList<Map<String,Object>>();
		return rtlist;
	}
	
	/**
	 * 行政区水闸过水过程
	 */
	
	public List<Map<String,Object>> queryGateGSSumByCondition_(String zmid,String stm,String etm){
		List<Map<String, Object>> rtlist = new ArrayList<Map<String,Object>>();
		return rtlist;
	}
}

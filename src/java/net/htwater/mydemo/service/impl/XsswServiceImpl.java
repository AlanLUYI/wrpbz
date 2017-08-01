package net.htwater.mydemo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.XsswService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
//import net.htwater.cms.util.HttpUtil;
import cn.miao.framework.util.PinyinUtil;

public class XsswServiceImpl implements XsswService {
	BaseDao daoZHSL = DaoFactory.getDao(DB_ZHSL);

	public List<Map<String, Object>> Queryzdxx() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " SELECT  RTRIM([STCD])[STCD]"
				   +"   ,RTRIM([STNM])[STNM]"
				   +"   ,RTRIM([基值信息])[基值信息]"
				   +"   ,[LGTD]"
				   +"   ,[LTTD]"
				   +"   ,RTRIM([STLC])[STLC]"
				   +"   ,[ISWE]"
                   +"   FROM [dbo].[HY_STSC_A] "; 
		rtList = daoZHSL.executeQuery(sql); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("STNM").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	} 
	
	
	
	public List<Map<String, Object>> QueryHisRain_year(String TM1, String TM2 ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" declare @sql varchar(8000) "
				+"  set @sql = 'select  YR TM' "
				+"  select @sql = @sql + ', max(case [STNM] when ''' + STNM + ''' then P else NULL end) ['+ STNM + ']'"
				+"  from (select distinct STNM from [dbo].[View_HY_YRP_F]) as a "
				+"  set @sql = @sql + ' from [dbo].[View_HY_YRP_F] where YR>=''"+TM1+"'' and YR<=''"+TM2+"'' group by YR order by YR'" 
				+"  exec(@sql)"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	
	public List<Map<String, Object>> QueryHisRain_month(String TM1, String TM2 ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" declare @sql varchar(8000) "
				+"	  set @sql = 'select  TM' "
				+"	  select @sql = @sql + ', max(case [STNM] when ''' + STNM + ''' then P else NULL end) ['+ STNM + ']'"
				+"	  from (select distinct STNM from [dbo].[View_HY_MTP_E]) as a "
				+"	  set @sql = @sql + ' from [dbo].[View_HY_MTP_E] where TM>=''"+TM1+"'' and TM<=''"+TM2+"'' group by TM order by TM' "
				+"	  exec(@sql)"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	
	public List<Map<String, Object>> QueryHisRain_day(String TM1, String TM2 ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" declare @sql varchar(8000) "
				+"	  set @sql = 'select  CONVERT(varchar(100),DT, 23) TM' "
				+"	  select @sql = @sql + ', max(case [STNM] when ''' + STNM + ''' then P else NULL end) ['+ STNM + ']'"
				+"	  from (select distinct STNM from [dbo].[View_HY_DP_C]) as a "
				+"	  set @sql = @sql + ' from [dbo].[View_HY_DP_C] where DT>=''"+TM1+"'' and DT<=''"+TM2+"'' group by DT order by DT'" 
				+"	  exec(@sql)"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	//蒸发历史资料
	public List<Map<String, Object>> QueryHisWe_year(String TM1, String TM2 ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" declare @sql varchar(8000) "
				+"  set @sql = 'select  YR TM' "
				+"  select @sql = @sql + ', max(case [STNM] when ''' + STNM + ''' then WSFE else NULL end) ['+ STNM + ']'"
				+"  from (select distinct STNM from [dbo].[View_HY_YRWE_F]) as a "
				+"  set @sql = @sql + ' from [dbo].[View_HY_YRWE_F] where YR>=''"+TM1+"'' and YR<=''"+TM2+"'' group by YR order by YR'" 
				+"  exec(@sql)"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	
	public List<Map<String, Object>> QueryHisWe_month(String TM1, String TM2 ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" declare @sql varchar(8000) "
				+"	  set @sql = 'select  TM' "
				+"	  select @sql = @sql + ', max(case [STNM] when ''' + STNM + ''' then WSFE else NULL end) ['+ STNM + ']'"
				+"	  from (select distinct STNM from [dbo].[View_HY_MTWE_E]) as a "
				+"	  set @sql = @sql + ' from [dbo].[View_HY_MTWE_E] where TM>=''"+TM1+"'' and TM<=''"+TM2+"'' group by TM order by TM' "
				+"	  exec(@sql)"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	
	public List<Map<String, Object>> QueryHisWe_day(String TM1, String TM2 ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" declare @sql varchar(8000) "
				+"	  set @sql = 'select  CONVERT(varchar(100),DT, 23) TM' "
				+"	  select @sql = @sql + ', max(case [STNM] when ''' + STNM + ''' then WSFE else NULL end) ['+ STNM + ']'"
				+"	  from (select distinct STNM from [dbo].[View_HY_DWE_C]) as a "
				+"	  set @sql = @sql + ' from [dbo].[View_HY_DWE_C] where DT>=''"+TM1+"'' and DT<=''"+TM2+"'' group by DT order by DT'" 
				+"	  exec(@sql)"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	//日最大降雨
	public List<Map<String, Object>> Querydmxp(String TM1, String TM2,String stcds ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" select b.STNM,a.[stcd]"
				+"	      ,[yr]"
				+"	      ,[dmxp1] "
				+"	      ,LEFT('0'+bt1d,2)+'-'+RIGHT(bt1d,2)  [bt1d]"
				+"	      ,[dmxp3] "
				+"	      ,LEFT('0'+[bt3d],2)+'-'+RIGHT([bt3d],2)  [bt3d]"
				+"	      ,[dmxp7] "
				+"	      ,LEFT('0'+[bt7d],2)+'-'+RIGHT([bt7d],2)  [bt7d]"
				+"	      ,[dmxp15] "
				+"	      ,LEFT('0'+[bt15d],2)+'-'+RIGHT([bt15d],2)  [bt15d]"
				+"	      ,[dmxp30] "
				+"	      ,LEFT('0'+[bt30d],2)+'-'+RIGHT([bt30d],2)  [bt30d]"
				+"	  from [dbo].[ditmxphj] a,HY_STSC_A b"
				+"	  where a.stcd=b.STCD and b.STLC='雨量站' and a.stcd in ("+stcds+")"
				+"	  and a.yr>='"+TM1+"' and a.yr<='"+TM2+"'"
				+"	  order by  stcd,yr"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	//小时最大降雨
	public List<Map<String, Object>> Queryhmxp(String TM1, String TM2,String stcds ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql="  select b.STNM,a.[stcd]"
				+"      ,[yr]  "
				+"      ,[hmxp1]"
				+"      ,LEFT('0'+[bt1h],2)+'-'+RIGHT([bt1h],2) [bt1h]"
				+"      ,[hmxp2]"
				+"      ,LEFT('0'+[bt2h],2)+'-'+RIGHT([bt2h],2) [bt2h]"
				+"      ,[hmxp3]"
				+"      ,LEFT('0'+[bt3h],2)+'-'+RIGHT([bt3h],2) [bt3h]"
				+"      ,[hmxp6]"
				+"      ,LEFT('0'+[bt6h],2)+'-'+RIGHT([bt6h],2) [bt6h]"
				+"      ,[hmxp12]"
				+"      ,LEFT('0'+[bt12h],2)+'-'+RIGHT([bt12h],2) [bt12h]"
				+"      ,[hmxp24]"
				+"      ,LEFT('0'+[bt24h],2)+'-'+RIGHT([bt24h],2) [bt24h]"
				+"      ,[hmxp48]"
				+"      ,LEFT('0'+[bt48h],2)+'-'+RIGHT([bt48h],2) [bt48h]"
				+"      ,[hmxp72]"
				+"      ,LEFT('0'+[bt72h],2)+'-'+RIGHT([bt72h],2) [bt72h]"
				+"  from [dbo].[hitmxphj] a,HY_STSC_A b"
				+"	  where a.stcd=b.STCD and b.STLC='雨量站' and a.stcd in ("+stcds+")"
				+"	  and a.yr>='"+TM1+"' and a.yr<='"+TM2+"'"
				+"	  order by  stcd,yr"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	//分钟最大降雨
	public List<Map<String, Object>> Querymmxp(String TM1, String TM2,String stcds ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" select b.STNM,a.[stcd]"
				+"      ,[yr]  "
				+"      ,[rcd]"
				+"      ,[mmp5]"
				+"      ,LEFT('0'+[bt5m],2)+'-'+RIGHT([bt5m],2) [bt5m]"
				+"      ,[mmp10]"
				+"      ,LEFT('0'+[bt10m],2)+'-'+RIGHT([bt10m],2) [bt10m]"
				+"      ,[mmp15]"
				+"      ,LEFT('0'+[bt15m],2)+'-'+RIGHT([bt15m],2)[bt15m]"
				+"      ,[mmp20]"
				+"      ,LEFT('0'+[bt20m],2)+'-'+RIGHT([bt20m],2)[bt20m]"
				+"      ,[mmp30]"
				+"      ,LEFT('0'+[bt30m],2)+'-'+RIGHT([bt30m],2)[bt30m]"
				+"      ,[mmp45]"
				+"      ,LEFT('0'+[bt45m],2)+'-'+RIGHT([bt45m],2)[bt45m]"
				+"      ,[mmp60]"
				+"      ,LEFT('0'+[bt60m],2)+'-'+RIGHT([bt60m],2)[bt60m]"
				+"      ,[mmp90]"
				+"      ,LEFT('0'+[bt90m],2)+'-'+RIGHT([bt90m],2)[bt90m]"
				+"      ,[mmp120]"
				+"      ,LEFT('0'+[bt120m],2)+'-'+RIGHT([bt120m],2)[bt120m]"
				+"      ,[mmp150]"
				+"      ,LEFT('0'+[bt150m],2)+'-'+RIGHT([bt150m],2)[bt150m]"
				+"      ,[mmp180]"
				+"      ,LEFT('0'+[bt180m],2)+'-'+RIGHT([bt180m],2)[bt180m]"
				+"      ,[mmp240]"
				+"      ,LEFT('0'+[bt240m],2)+'-'+RIGHT([bt240m],2)[bt240m]"
				+"      ,[mmp360]"
				+"      ,LEFT('0'+[bt360m],2)+'-'+RIGHT([bt360m],2)[bt360m]"
				+"      ,[mmp540]"
				+"      ,LEFT('0'+[bt540m],2)+'-'+RIGHT([bt540m],2)[bt540m]"
				+"      ,[mmp720]"
				+"      ,LEFT('0'+[bt720m],2)+'-'+RIGHT([bt720m],2)[bt720m]"
				+"      ,[mmp1440]"
				+"      ,LEFT('0'+[bt1440m],2)+'-'+RIGHT([bt1440m],2)[bt1440m]"
				+"  from [dbo].[mitmxphj] a,HY_STSC_A b"
				+"	  where a.stcd=b.STCD and b.STLC='雨量站' and a.stcd in ("+stcds+")"
				+"	  and a.yr>='"+TM1+"' and a.yr<='"+TM2+"' and rcd in (0,1)" 
				+"	  order by  stcd,yr"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	//蒸发年特征值
	public List<Map<String, Object>> QueryYRWE(String TM1, String TM2,String stcds ) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" SELECT b.STNM,a.[STCD],[EETP],[YR],[WSFE],[WSFERCD],[MXDYE],[MXDYERCD],CONVERT(varchar(100),[MXDYEODT],23) as [MXDYEODT] ,[MNDYE],[MNDYERCD],CONVERT(varchar(100),[MNDYEODT],23) as [MNDYEODT],[IDSDT],[ICAPD],[ESLCCH] ,[NT]"
				+"	  FROM [dbo].[HY_YRWE_F]  a ,dbo.HY_STSC_A b"
				+"	  where YR>='"+TM1+"' and YR<='"+TM2+"' and a.STCD in ("+stcds+") "
				+"	  and a.STCD=b.STCD and b.STLC='雨量站' and b.ISWE=1"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	//蒸发月特征值
		public List<Map<String, Object>> QueryMTHWE(String TM1, String TM2,String stcds ) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
			String sql="  select a.[STNM],[TM],b.[STCD],[EETP],[YR],[MTH],[WSFE],[WSFERCD] ,[MXDYE] ,[MXDYERCD],[MNDYE],[MNDYERCD] "
					+ "from [dbo].[View_HY_MTWE_E] a,dbo.HY_STSC_A b "
					+ "where a.STCD=b.STCD and b.STLC='雨量站' and b.ISWE=1 and a.STCD in ("+stcds+")"
                    +" and TM>='"+TM1+"' and TM<='"+TM2+"'  order by TM";
			rtList=daoZHSL.executeQuery(sql, null);
			return rtList;
		}
}

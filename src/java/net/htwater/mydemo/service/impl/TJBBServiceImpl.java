package net.htwater.mydemo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.TJBBService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
//import net.htwater.cms.util.HttpUtil;
import cn.miao.framework.util.PinyinUtil;

public class TJBBServiceImpl implements TJBBService {
	BaseDao daoZHSL = DaoFactory.getDao(DB_ZHSL);

	public List<Map<String, Object>> Queryrealstnm(String TM) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" SELECT *"
				+"  FROM [dbo].[View_HY_MTP_E] "
				+"  where TM='"+TM+"' order by STNM"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	
	public List<Map<String, Object>> Querymultidata(String year,String month) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql=" declare @sql varchar(8000) "
				+"	set @sql = 'select  CONVERT(varchar(100),DT, 23) TM' "
				+"	select @sql = @sql + ', max(case [STNM] when ''' + STNM + ''' then P else NULL end) ['+ STNM + ']'"
				+"	from (select distinct STNM from [dbo].[View_HY_DP_C]) as a "
				+"	set @sql = @sql + ' from [dbo].[View_HY_DP_C] where YEAR(DT)="+year+" and MONTH(DT)="+month+" group by DT order by DT'"
				+"	exec(@sql)"; 
		rtList=daoZHSL.executeQuery(sql, null);
		return rtList;
	}
	public Map<String, Object> Querysingledata(String year,String stcd) {
		String sql="";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> rtList1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> rtList2 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> rtList3 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> rtList4 = new ArrayList<Map<String, Object>>();
		
		sql="  SELECT *,DATEPART(MONTH,DT) month, "
     +" DATEPART(DAY,DT) day FROM [dbo].[View_HY_DP_C] where YEAR(DT)="+year+" and STCD='"+stcd+"' "; 
		rtList1=daoZHSL.executeQuery(sql, null);
		sql="SELECT * FROM [dbo].[View_HY_MTP_E] where YR="+year+" and STCD='"+stcd+"' "; 
		rtList2=daoZHSL.executeQuery(sql, null);
		sql="SELECT * FROM [dbo].[View_HY_YRP_F] where YR="+year+" and STCD='"+stcd+"' "; 
		rtList3=daoZHSL.executeQuery(sql, null);
		sql="SELECT [STCD],[YR],CONVERT(varchar(100),[BGDT],23)  as [BGDT],[MXPDR],[MXP],[MXPRC] FROM [dbo].[HY_DMXP_F] where YR="+year+" and STCD='"+stcd+"' "; 
		rtList4=daoZHSL.executeQuery(sql, null);
		
		map.put("day_singledata", rtList1);
		map.put("month_singledata", rtList2);
		map.put("year_singledata", rtList3);
		map.put("time_singledata", rtList4);
		return map;
	}
	
}

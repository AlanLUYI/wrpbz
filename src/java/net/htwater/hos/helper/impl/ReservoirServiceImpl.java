/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  Mar 3, 2014 Neal Miao 
 * 
 * Copyright(c) 2014, by htwater.net. All Rights Reserved.
 */
package net.htwater.hos.helper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.hos.helper.RainWaterHelper;
import net.htwater.hos.helper.impl.ReservoirServiceImpl;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

/**
 * 
 * @author Neal Miao
 * @version
 * @Date Mar 3, 2014 2:11:59 PM
 * 
 * @see
 */
public class ReservoirServiceImpl {

	BaseDao dao = DaoFactory.getDao("resos");
	BaseDao daoMssql = DaoFactory.getDao("HT_NBSQDB");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.htwater.htpms.service.ReservoirService#getRsvrWorkInfo(java.util.Map)
	 */

	
	public Map<String, Object> getRsvrWorkInfo(Map<String, String> condition) {
			String stid = condition.get("stid");
			String sql = "SELECT * FROM SKLL WHERE stid = %s and "
					+ "tm =(select max(tm) from SKLL where stid = %s)";
			List<Map<String, Object>> list = dao.executeQuery(String.format(sql,
					stid, stid));
			Map<String, Object> rtMap = new HashMap<>();
			if (list.isEmpty()) {
				return rtMap;
			}
			Map<String, Object> tmpMap = list.get(0);
			rtMap.put("STID", tmpMap.get("STID"));
			rtMap.put("STNM", tmpMap.get("STNM"));
			rtMap.put("SW", tmpMap.get("SW"));
			
			rtMap.put("RAIN", getRsvrRainInfo(tmpMap.get("STNM").toString()));
			List<Map<String, Object>> zmList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				zmList.add(setWorkInfoValue(list.get(i)));
			}
			rtMap.put("ZMS", zmList);
			return rtMap;
	}
	 
	private Map<String, Object> setWorkInfoValue(Map<String, Object> map) {
		Map<String, Object> emptyMap = new HashMap<>();
		emptyMap.putAll(map);
		emptyMap.remove("STNM");
		emptyMap.remove("STID");
		emptyMap.remove("SW");
		emptyMap.remove("SWTM");
		emptyMap.remove("DataID");
		emptyMap.remove("TM");
		return emptyMap;
	}

	public String getRsvrRainInfo(String stnm) {
		stnm = stnm.replaceAll("水库", "");
		String sql = "declare @stnm varchar(10),@stm datetime,@etm datetime,@coef float;"
				+ " set @stnm=?;set @stm=?;set @etm=?;"
				+ " SELECT @coef=SUM(coef) FROM HT_RAIN_B WHERE RSNM=@stnm AND ENABLED=1;"
				+ " SELECT round(SUM(SR.drp*RB.COEF/@coef),2) AS ADRN FROM HT_PPTN_R AS SR "
				+ " LEFT JOIN HT_RAIN_B AS RB ON SR.STCD=RB.STCD "
				+ " LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.[ID] "
				+ " WHERE LTRIM(RTRIM(RB.RSNM))=@stnm AND RB.ENABLED=1 AND SR.TM>@stm AND SR.TM<=@etm";
		
		Map<String, Object> rtMap = daoMssql.executeQueryObject(
				sql,new Object[]{stnm,RainWaterHelper.getWaterToday(),
				RainWaterHelper.getWaterTommorow()});
		return (null == rtMap.get("ADRN"))?"0":rtMap.get("ADRN").toString();
	}

	
	public List<Map<String, Object>> cacheAllRsvrWorkInfo() {
		/*List<Map<String, Object>> idList = dao
				.executeQuery("select distinct stcd stid from [HT_DATA].[dbo].[SKINF]");
		List<Map<String, Object>> rtList = new ArrayList<>();
		for (Map<String, Object> idMap : idList) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("stid", idMap.get("stid").toString());
			Map<String, Object> rsvrWIMap = getRsvrWorkInfo(param);
			if (!rsvrWIMap.isEmpty()) {
				rtList.add(rsvrWIMap);
			}
		}
		return rtList;*/
		
		List<Map<String, Object>> rtList = new ArrayList<>();
		String sql = "select distinct stcd stid,stnm,(select top 1 SW "
				+ " from SKLL b where a.STCD=b.stid order by TM desc)sw from SKINF a;";
		List<Map<String, Object>> idList = dao.executeQuery(sql);
		String stidstr="";
		String stnmstr ="";
		for(Map<String, Object> idMap : idList){
			stidstr += idMap.get("stid").toString()+",";
			stnmstr += idMap.get("stnm").toString().replace("水库", "")+",";
		}
		if( !"".equals(stidstr) ){
			stidstr = stidstr.substring(0,stidstr.length()-1);
			stnmstr = stnmstr.substring(0,stnmstr.length()-1);
		}
		sql = "declare @stnmstr varchar(200),@stm datetime,@etm datetime;"
			+ " set @stnmstr=?;set @stm=?;set @etm=?;"
			+ " select b.line stnm,(select round(sum(sr.drp*rb.coef)/(select sum(coef) "
			+ " from ht_rain_b where rsnm=b.line and enabled=1),2) as adrn from ht_pptn_r as sr "
			+ " left join ht_rain_b as rb on sr.stcd=rb.stcd where rsnm=b.line "
			+ " and rb.enabled=1 and sr.tm>@stm and sr.tm<=@etm)adrn from dbo.split(@stnmstr,',')b;";
		List<Map<String, Object>> yllist = daoMssql.executeQuery(sql,
				new Object[]{stnmstr,RainWaterHelper.getWaterToday(),
						RainWaterHelper.getWaterTommorow()});
		
		sql = " declare @stidstr varchar(200);set @stidstr=?;"
			+ " select a.line stid,ZMNM,ZMTM,LL,ZMKD,ZMID from dbo.split(@stidstr,',')a "
			+ " left join skll b on b.stid=a.line and "
			+ " tm=(select max(tm) from skll where stid = a.line)";
		List<Map<String, Object>> zmslist = dao.executeQuery(sql,new Object[]{stidstr});
		
		for(Map<String, Object> idMap : idList){
			Map<String, Object> rsvrWIMap = new HashMap<>();
			rsvrWIMap.put("STID", idMap.get("stid"));
			rsvrWIMap.put("STNM", idMap.get("stnm"));
			rsvrWIMap.put("SW", idMap.get("sw"));

			Object rainobj = new Object();
			for( Map<String, Object> ylMap : yllist ){
				if( idMap.get("stnm").toString().contains(ylMap.get("stnm").toString())){
					rainobj = (null==ylMap.get("adrn"))?0:ylMap.get("adrn");
					break;
				}
			}
			rsvrWIMap.put("RAIN", rainobj);
			
			List<Map<String, Object>> tmplist= new ArrayList<>();
			for( Map<String, Object> zmMap : zmslist ){
				if( idMap.get("stid").toString().contains(zmMap.get("stid").toString())){
					tmplist.add(zmMap);
				}
			}
			rsvrWIMap.put("ZMS", tmplist);
			
			rtList.add(rsvrWIMap);
		}
		
		return rtList;
	}
	
	/**
	 * 水库闸门工况
	 * @param tm
	 * @return {dataid,LL,ZMKD,TM,KQTM}
	 * @remark tm为null(字符串) 则返回最新数据，tm为某值，则返回某历史时间的工况，LL>0时KQTM(开启时间)才有意义
	 */
	private List<Map<String,Object>> queryAllZmGq(String tm){
		String sql="declare @tm datetime declare @tb table(dataid varchar(20),tm datetime,zmkd float,ll float) set @tm=? if @tm is null begin insert @tb select DataID,TM,ZMKD,LL from SKLLSSB end else begin insert @tb select c.dataid,c.tm,d.ZMKD,d.LL from (select dataid,tm=(select top 1 tm from SKLL as b where b.DataID=a.DataID and TM<=@tm order by tm desc) from SKLLSSB as a) as c left join SKLL as d on c.DataID=d.DataID and c.tm=d.TM end  select dataid,TM,ZMKD,LL,KQTM=(case when a.ll>0 then (select top 1 tm from skll where tm >(select top 1 tm from SKLL as b where b.DataID=a.DataID and b.tm<a.TM and b.LL=0 order by b.tm desc) order by TM asc) else null end) from @tb as a";
		Object[] args={tm};
		return dao.executeQuery(sql, args);
	}
	
	
	public List<Map<String, Object>> getAllBigRsvrWorkInfo(){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		String sql = "SELECT * FROM SKLL a WHERE EXISTS(SELECT DISTINCT STCD FROM SKINF b where a.stid=b.stcd) "
				+ "and tm =(SELECT max(tm) from SKLL where stid=30) ";
		
		List<Map<String, Object>> alllist = dao.executeQuery(sql);
		List<Map<String, Object>> biglist = dao.executeQuery("SELECT DISTINCT STCD FROM SKINF"); 
		
		List<Map<String, Object>> tmplist = new ArrayList<Map<String,Object>>();
		for (int i=0 ;i<biglist.size(); i++){
			tmplist.clear();
			Map<String, Object> tmpSkMap = biglist.get(i);
			String stcd = tmpSkMap.get("STCD").toString();
			for(int j=0 ;j<alllist.size(); j++){
				Map<String, Object> tmpMap = alllist.get(j);
				if (stcd.equals(tmpMap.get("STID").toString()))
					tmplist.add(tmpMap);
			}
			result.add(GateGq(tmplist));			
		}

		return result;
	}
	
	private Map<String,Object> GateGq(Object obj){		
		List<Map<String, Object>> list = (List<Map<String, Object>>)obj;
		double LL=0;
		double ll =0;
		int open_cnt = 0;
		
		Map<String, Object> tmpMap30 = list.get(0);
		Map<String, Object> rtMap = new HashMap<>();
		rtMap.put("STID", tmpMap30.get("STID"));
		rtMap.put("STNM", tmpMap30.get("STNM"));
		rtMap.put("SW", tmpMap30.get("SW"));
		// rtMap.put("SWTM", tmpMap.get("SWTM"));
		rtMap.put("RAIN", getRsvrRainInfo(tmpMap30.get("STNM").toString()));
		List<Map<String, Object>> zmList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			zmList.add(setWorkInfoValue(list.get(i)));
			Map<String, Object> gate = list.get(i);
			ll = Double.valueOf(gate.get("LL").toString());
			LL = LL+ll;
			if (ll > 0)
				open_cnt += 1;
		}	
		rtMap.put("LL", LL);
		rtMap.put("OPEN_CNT", open_cnt);
		rtMap.put("SUM_CNT", list.size());
		rtMap.put("ZMS", zmList);
		return rtMap;
	}

}

package net.htwater.hos.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import net.htwater.hos.service.FloodService;

public class FloodServiceImpl implements FloodService {
	BaseDao dao = DaoFactory.getDao(DB_SHANHONG);
	@Override
	public List<Map<String, Object>> getSTWarn(String TM1, String TM2) {
		String sql="select b.STNM,a.*,b.LGTD,B.LTTD as LATD,d.regionnm as CITY from ZT_STWarnRecord_R as a left join ZT_ST_station_B as b on a.STCD=b.STCD left join ZT_ST_station_B as c on a.STCD=c.STCD left join sys_region as d on substring(c.ADCD,1,6)=d.adcd where a.STWarnETM > ? and a.STWarnSTM <= ?";
		List<Map<String,Object>> result=dao.executeQuery(sql, new Object[]{TM1,TM2});
		return result;
	}
	
	@Override
	public List<Map<String,Object>> getWarnList(String tm1,String tm2){
		String sql="declare @tm1 datetime;declare @tm2 datetime;set @tm1=?;set @tm2=?;select a.ADCD,a.WarnID,a.WarnNM,a.WarnTypeID,a.WarnGradeID,a.WarnStatusID,a.WarnDesc ,a.WarnSTM,a.WarnETM,a.RADCD,(select COUNT(b.WarnID) from dbo.ZT_Warn_StatusRecord_R  b where a.WarnID=b.WarnID) as ChangeNum,"+
				    "(select count(c.STWarnID) from dbo.ZT_STWarnRecord_R c where exists(select stcd from dbo.ZT_Warn_STCD_R  d where a.WarnID=d.WarnID and c.STCD=d.STCD) and ((c.STWarnSTM>@tm1 and c.STWarnETM<@tm2) or (c.STWarnSTM<@tm1 and c.STWarnETM>@tm1) or(c.STWarnSTM<@tm2 and c.STWarnETM>@tm2))) "+ 
				    "as STWarnNum,e.regionnm as ADNM from dbo.[ZT_WarnRecord_R] a left join sys_region as e on substring(a.ADCD,1,6)=e.adcd;";
		return dao.executeQuery(sql,new Object[]{tm1,tm2});
	}
	
	@Override
	public List<Map<String,Object>> getSTWarnList(String warnid,String radcd){	
		String sql = "select b.STWarnID,b.STWarnNM,b.STCD,c.STNM,b.WarnTypeID,b.WarnGradeID,b.STWarnDESC,b.STWarnSTM,b.STWarnETM,b.RADCD from dbo.ZT_STWarnRecord_R b left join dbo.ZT_ST_station_B c on b.STCD=c.STCD where  exists(select stcd from dbo.ZT_Warn_STCD_R  d where d.WarnID=? and d.STCD=?)";
		return dao.callProcedure(sql, new Object[]{warnid,radcd});
	}
	
	@Override
	public List<Map<String,Object>> getWarnChangeList(String warnid,String radcd){
		String sql = "select a.WarnID,a.Time,a.WarnGradeID,a.WarnStatusID,a.remark,a.RADCD from dbo.ZT_Warn_StatusRecord_R a where warnid=? and radcd=?";
		return dao.executeQuery(sql,new Object[]{warnid,radcd});
	}
	
	@Override
	public List<Map<String,Object>> getResponseList(String tm1,String tm2){
		String sql = "declare @tm1 datetime;set @tm1=?;declare @tm2 datetime;set @tm2=?;SELECT a.RespID,a.ADCD,a.RespGradeID,a.RspCmder,a.RespSTime,a.RespETime,a.WarnIDs,a.remark,a.RADCD,e.regionnm as ADNM,"+
		  " (select COUNT(*) from dbo.ZT_RespMeasures_R b where a.RespID = b.RespID group by b.RespID) as MeasuresNum FROM dbo.ZT_Response_R a left join sys_region as e on substring(a.ADCD,1,6)=e.adcd where ((a.RespSTime>@tm1 and a.RespETime<@tm2) or (a.RespSTime<@tm1 and a.RespETime>@tm1) or(a.RespSTime<@tm2 and a.RespETime>@tm2))";
		List<Map<String,Object>> result=dao.executeQuery(sql, new Object[]{tm1,tm2});
		return result;
	}
	
	@Override
	public List<Map<String,Object>> getMeasuresList(String responseid,String radcd){
		String sql="select a.RespID,a.DeptCD,a.DeptNM,a.Measures ,a.RADCD from dbo.ZT_RespMeasures_R a where a.RespID=? and a.RADCD=?";
		List<Map<String,Object>> result=dao.executeQuery(sql, new Object[]{responseid,radcd});
		return result;
	}

	@Override
	public List<Map<String, Object>> getDistrictApp(String userid) {
		String uuid=UUID.randomUUID().toString();
		String sql="insert into sys_auth_districtapp_log(userid,token,tm) values(?,?,getdate())";
		dao.executeSQL(sql, new Object[]{userid,uuid});
		sql="select * from sys_auth_districtapp order by ordby";
		List<Map<String,Object>> list = dao.executeQuery(sql);
		for(Map<String,Object> map : list){
			if(map.get("appurl") != null){
				String appurl = map.get("appurl").toString();
				if(appurl.indexOf("?") > -1){
					map.put("appurl", appurl.toString()+"&nbfb_token="+uuid);
				}
				else{
					map.put("appurl", appurl.toString()+"?nbfb_token="+uuid);
				}
			}
		}
		return list;
	}

	@Override
	public Map<String,Object> checkDistricApp(String token) {
		//找出当前用户两个小时之内的第一条token
		String sql="select top 1 a.*,b.name from sys_auth_districtapp_log as a left join sys_user as b on a.userid=b.loginname where a.token=? and a.tm>=DATEADD(hour,-2,getdate())";
		Map<String,Object> map = dao.executeQueryObject(sql, new Object[]{token});
		return map;
	}
	
	@Override
	public List<Map<String,Object>> getDisasterSta(String tm1,String tm2){
		String sql="select a.regionnm as ADNM,b.ADCD,b.DistDescrip,b.Drmp,b.Missp,b.Collapsehouse from dbo.ZT_Disaster_Statistics_R b left join sys_region as a on substring(b.ADCD,1,6)=a.adcd where time>? and time<?";
		List<Map<String,Object>> result=dao.executeQuery(sql, new Object[]{tm1,tm2});
		return result;		
	}

}

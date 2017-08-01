/**
 * 防汛水情雨情统计和资料上报服务
 * @author wangjinlong
 * @Date 2015-10-13
 * @since v 1.0
 */ 

package net.htwater.mydemo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.wjlService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
//import net.htwater.cms.util.HttpUtil;
import cn.miao.framework.util.PinyinUtil;

public class wjlServiceImpl implements wjlService {
	BaseDao HTSQ = DaoFactory.getDao(HT_NBSQDB);
	BaseDao daoZHSL = DaoFactory.getDao(DB_ZHSL);
	BaseDao daogq = DaoFactory.getDao(DB_gq);
	BaseDao daoshanhong = DaoFactory.getDao(DB_SHANHONG);
	BaseDao daojwjgq = DaoFactory.getDao(DB_jwjgq);
	BaseDao daozhcggps = DaoFactory.getDao(DB_zhcggps);
	@Override
	public List<Map<String, Object>> QueryYqInfo() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql = " SELECT a.STCD,b.STNM from "
				+" [dbo].[HT_RAIN_B] a,dbo.HT_STBPRP_B b "
				+" where a.ST_ID=b.ID and b.STNM in ('骆驼桥','荪湖水库','黄古林','慈城','集仕港','大西坝','姚江大闸')"; 
		rtList = HTSQ.executeQuery(sql); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String cname = map.get("STNM").toString(); 
				map.put("_s", cname + PinyinUtil.converterToPinYin(cname));
			}
		}
		return rtList; 
	}
	@Override
	public List<Map<String, Object>> QueryRainInfo(String TM1, String TM2,String queryType) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2};
		String sql=" use [ht_nbsqdb]"
					+"  SET NOCOUNT ON;"
					+"  declare @nowtm datetime"
					+"  set @nowtm=GETDATE()"
					  
					+"  insert [zhcg].[dbo].[MIDTB_FORYLTJ]"
					  
					+"   select  c.tm,c.drp,RTRIM( b.STNM) ,@nowtm"
					+"  from [dbo].[HT_RAIN_B] a,dbo.HT_STBPRP_B b ,dbo.ST_PPTN_R c"
					+"  where a.ST_ID=b.ID and a.STCD=c.stcd and TM>= ? and TM<= ? and DATEPART(MI,TM)=0 ";
					
				if(queryType.equals("DAY")){
					sql+=" and DATEPART(HOUR,TM)=8 ";
				}
					sql+="  and a.STCD in ('1106','1109','1819','1906','5133','5150','7915')"
					  
				    +"  declare @sql varchar(8000) "
				    +"  set @sql = 'select  TM' "
				    +"  select @sql = @sql + ', max(case [STNM] when ''' + STNM + ''' then drp else NULL end) ['+ STNM + ']'"
				    +"  from (select distinct STNM from [zhcg].[dbo].[MIDTB_FORYLTJ]) as a "
				    +"  set @sql = @sql + ' from [zhcg].[dbo].[MIDTB_FORYLTJ] group by tm order by tm' "
				    +"  exec(@sql) "
					  
					+"  delete [zhcg].[dbo].[MIDTB_FORYLTJ] where [INSERTTM]=@nowtm";
		
	  rtList=HTSQ.executeQuery(sql, args);
	  return rtList;
	}
	
	@Override
	public List<Map<String, Object>> QuerySqInfo() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql = " SELECT a.STCD,b.STNM from "
				+" [dbo].[HT_RIVER_B] a,dbo.HT_STBPRP_B b "
				+" where a.ST_ID=b.ID and b.STNM in ('姚江大闸','北斗河','杨木碶闸上','江东内河','江北内河','澄浪堰闸上','印洪碶上','梅墟闸上站','大西坝')"; 
		rtList = HTSQ.executeQuery(sql); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String cname = map.get("STNM").toString(); 
				map.put("_s", cname + PinyinUtil.converterToPinYin(cname));
			}
		}
		return rtList; 
	}
	public List<Map<String, Object>> QuerySwInfo(String TM1,String TM2,String queryType){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2};
		String sql = " use [ht_nbsqdb]"
					+ " SET NOCOUNT ON;"
					+ " declare @nowtm datetime"
					+ " set @nowtm=GETDATE()"
				  
				  	+ " insert [zhcg].[dbo].[MIDTB_FORSWTJ]"
				  	+ " select  c.tm,c.z,b.STNM ,@nowtm"
				  	+ " from [dbo].[HT_RIVER_B] a,dbo.HT_STBPRP_B b ,dbo.ST_RIVER_R c"
				  	+ " where a.ST_ID=b.ID and a.STCD=c.stcd and TM>=? and  TM<=? and DATEPART(MI,TM)=0 ";
				  	
		if(queryType.equals("DAY")){
			sql=sql+" and DATEPART(HOUR,TM)=8 ";
		}		  	
				  	
		sql=sql+ " and a.STCD in ('5886','1871','1878','8008','1875','1904','1872','1876','5150','6073') "
				  
				  	+ " declare @sql varchar(8000) "
				  	+ " set @sql = 'select  TM' "
				  	+ " select @sql = @sql + ', max(case [STNM] when ''' + STNM + ''' then Z else NULL end) ['+ STNM + ']'"
				  	+ " from (select distinct STNM from [zhcg].[dbo].[MIDTB_FORSWTJ]) as a "
				  	+ " set @sql = @sql + ' from [zhcg].[dbo].[MIDTB_FORSWTJ] group by tm order by tm' "
				  	+ " exec(@sql) "
				  
				 	+ " delete [zhcg].[dbo].[MIDTB_FORSWTJ] where [INSERTTM]=@nowtm";
		
		list = HTSQ.executeQuery(sql, args);
		return list;
	}
	
	public List<Map<String, Object>> AdminQueryFxry(String selectyr) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr};
		String sql = " SELECT * FROM [dbo].[防汛人员] where DATEPART(YEAR,uploadTime)=? and isUpload = 1  "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("username").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	public List<Map<String, Object>> CommonQueryFxry(String selectyr,String unit) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,unit};
		String sql = " SELECT * FROM [dbo].[防汛人员] where DATEPART(YEAR,uploadTime)=? and unit = ? "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("username").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	public List<Map<String, Object>> AdminQueryFxwz(String selectyr) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr};
		String sql = " SELECT * FROM [dbo].[防汛物资上报] where DATEPART(YEAR,time)=? and isUpload = 1  "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("qx").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	public List<Map<String, Object>> CommonQueryFxwz(String selectyr,String unit) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,unit};
		String sql = " SELECT * FROM [dbo].[防汛物资上报] where DATEPART(YEAR,time)=? and unit = ? "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("qx").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	@Override
	public List<Map<String, Object>> AdminQueryFxwzTotal(String selectyr) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr};
		String sql = " SELECT [ID],[time],[unit],[qx],[ckmc],[ckwz],qpc+ydbc+psb as qpsb,zl+yscl as yssb,[bl],[dl],[aqm]+[yy]+[st]+[kz]+[sk]+[yx] as cdsb, "
				+ "[yjd],[sd],[jsqc],[ssl],[ts],[isUpload],[isCheck],[rjtReason] FROM [dbo].[防汛物资上报] where DATEPART(YEAR,time)=? and isUpload = 1  "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("qx").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	@Override
	public List<Map<String, Object>> CommonQueryFxwzTotal(String selectyr,String unit) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,unit};
		String sql = " SELECT [ID],[time],[unit],[qx],[ckmc],[ckwz],qpc+ydbc+psb as qpsb,zl+yscl as yssb,[bl],[dl],[aqm]+[yy]+[st]+[kz]+[sk]+[yx] as cdsb, "
				+ "[yjd],[sd],[jsqc],[ssl],[ts],[isUpload],[isCheck],[rjtReason] FROM [dbo].[防汛物资上报] where DATEPART(YEAR,time)=? and unit = ? "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("qx").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList;
	}
//	public List<Map<String, Object>> QueryFxwz() {
//		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
//		String sql = " SELECT * FROM [dbo].[防汛物资上报] "; 
//		rtList = daoZHSL.executeQuery(sql); 
//		if (rtList != null) {
//			for (Map<String, Object> map : rtList) {
//				String ennm = map.get("qx").toString(); 
//				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
//			}
//		}
//		return rtList; 
//	}
	public List<Map<String, Object>> AdminqueryDwStaInfo(String selectyr) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr};
		String sql = " SELECT * FROM [dbo].[防汛队伍上报] where DATEPART(YEAR,time)=? and isUpload = 1  "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("aera").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	public List<Map<String, Object>> CommonqueryDwStaInfo(String selectyr,String unit) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,unit};
		String sql = " SELECT * FROM [dbo].[防汛队伍上报] where DATEPART(YEAR,time)=? and unit = ? "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("aera").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
//	public List<Map<String, Object>> queryDwStaInfo() {
//		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
//		String sql = " SELECT * from [dbo].[防汛队伍上报] ";
//		rtList = daoZHSL.executeQuery(sql); 
//		if (rtList != null) {
//			for (Map<String, Object> map : rtList) {
//				String cname = map.get("aera").toString(); 
//				map.put("_s", cname + PinyinUtil.converterToPinYin(cname));
//			}
//		}
//		return rtList; 
//	}
	public List<Map<String, Object>> AdminqueryPlStaInfo(String selectyr) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr};
		String sql = " SELECT * FROM [dbo].[排涝能力统计] where DATEPART(YEAR,time)=? and isUpload = 1  "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("aera").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	public List<Map<String, Object>> CommonqueryPlStaInfo(String selectyr,String unit) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,unit};
		String sql = " SELECT * FROM [dbo].[排涝能力统计] where DATEPART(YEAR,time)=? and unit = ? "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("aera").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
//	public List<Map<String, Object>> queryPlStaInfo() {
//		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
//		String sql = " SELECT * from [dbo].[排涝能力统计] ";
//		rtList = daoZHSL.executeQuery(sql); 
//		if (rtList != null) {
//			for (Map<String, Object> map : rtList) {
//				String cname = map.get("aera").toString(); 
//				map.put("_s", cname + PinyinUtil.converterToPinYin(cname));
//			}
//		}
//		return rtList; 
//	}
	
	public List<Map<String, Object>> AdminQueryZbb(String selectyr,String typhoon) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,typhoon};
		String sql = " SELECT * FROM [dbo].[防汛值班表] where DATEPART(YEAR,time)=? and typhoon=? and isUpload = 1  "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("leader").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	public List<Map<String, Object>> CommonQueryZbb(String selectyr,String unit,String typhoon) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,unit,typhoon};
		String sql = " SELECT * FROM [dbo].[防汛值班表] where DATEPART(YEAR,time)=? and unit = ? and typhoon=? "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("leader").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
//	@Override
//	public List<Map<String, Object>> QueryZhibanbiao() {
//		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
//		String sql = " SELECT * FROM [zhcg].[dbo].[防汛值班表] ";
//		rtList = HTSQ.executeQuery(sql); 
//		if (rtList != null) {
//			for (Map<String, Object> map : rtList) {
//				String ennm = map.get("leader").toString(); 
//				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
//			}
//		}
//		return rtList; 
//	}
	
	@Override
	public List<Map<String, Object>> AdminQueryBsfxInfo(String selectyr,String typhoon) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,typhoon};
		String sql = " SELECT * FROM [dbo].[城区防台防汛情况表] where DATEPART(YEAR,time)=? and typhoon=? and isUpload = 1  "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("aera").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList;
	}
	@Override
	public List<Map<String, Object>> CommonQueryBsfxInfo(String selectyr,String unit,String typhoon) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,unit,typhoon};
		String sql = " SELECT * FROM [dbo].[城区防台防汛情况表] where DATEPART(YEAR,time)=? and unit = ? and typhoon=?"; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("aera").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
//	@Override
//	public List<Map<String, Object>> QueryBsfxInfo() {
//		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
//		String sql = " SELECT * FROM [zhcg].[dbo].[城区防台防汛情况表] ";
//		rtList = HTSQ.executeQuery(sql); 
//		if (rtList != null) {
//			for (Map<String, Object> map : rtList) {
//				String ennm = map.get("aera").toString(); 
//				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
//			}
//		}
//		return rtList; 
//	}
	
	@Override
	public List<Map<String, Object>> AdminQueryNlSta(String selectyr,String typhoon) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,typhoon};
		String sql = " SELECT * FROM [dbo].[内涝统计上报] where DATEPART(YEAR,time)=? and typhoon=? and isUpload = 1  "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("jsdType").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	@Override
	public List<Map<String, Object>> CommonQueryNlSta(String selectyr,String unit,String typhoon) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,unit,typhoon};
		String sql = " SELECT * FROM [dbo].[内涝统计上报] where DATEPART(YEAR,time)=? and unit = ? and typhoon=?"; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("jsdType").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	
	
	
	
	//汛后报告
	@Override
	public List<Map<String, Object>> AdminQueryXhReport(String selectyr) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr};
		String sql = " SELECT * FROM [dbo].[汛后总结报告] where DATEPART(YEAR,time)=? and isUpload = 1  "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("title").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	@Override
	public List<Map<String, Object>> CommonQueryXhReport(String selectyr,String unit) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr,unit};
		String sql = " SELECT * FROM [dbo].[汛后总结报告] where DATEPART(YEAR,time)=? and unit = ? "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("title").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
//	public List<Map<String, Object>> queryXhReport() {
//		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
//		String sql = " SELECT * from [dbo].[汛后总结报告] ";
//		rtList = daoZHSL.executeQuery(sql); 
//		if (rtList != null) {
//			for (Map<String, Object> map : rtList) {
//				String cname = map.get("title").toString(); 
//				map.put("_s", cname + PinyinUtil.converterToPinYin(cname));
//			}
//		}
//		return rtList; 
//	}
	
	
	//汛前服务
	public Map<String, Object> addContactPerson(String uploadTime,String unit, String username,String post,
			String officephone, String telephone, String demo,String isUpload,String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[防汛人员](uploadTime,unit,username,post,officephone,telephone,demo,isUpload,isCheck) values(?,?,?,?,?,?,?,?,?)";
		if(daoZHSL.executeSQL(sql,new Object[]{uploadTime,unit,username,post,officephone,telephone,demo,isUpload,isCheck})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	public Map<String, Object> updateContactPerson(String id,String uploadTime,String unit, String username,
			String post,String officephone, String telephone, String demo){
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "update [dbo].[防汛人员] set uploadTime=?, unit=?,username=?,post=?,officephone=?,telephone=?,demo=? where id= ?";
			if(daoZHSL.executeSQL(sql, new Object[]{uploadTime,unit,username,post,officephone,telephone,demo,id})){
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
	public Map<String, Object> delContactPerson(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		String sql ="delete [dbo].[防汛人员] where id=?";
		if (daoZHSL.executeSQL(sql,new Object[]{id})) {
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	
	public Map<String, Object> addFXWZ(String time,String unit,String qx,String ckmc,String ckwz,String qpc,String ydbc,String psb,String zl,
			String yscl,String bl,String dl,String yjd,String aqm,String yy,String sd,String jsqc,String ssl,String ts,String st,String kz,String sk,String yx,String X,String Y,String isUpload,String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[防汛物资上报](time,unit,qx,ckmc,ckwz,qpc,ydbc,psb,zl,yscl,bl,dl,yjd,aqm,yy,sd,jsqc,ssl,ts,st,kz,sk,yx,X,Y,isUpload,isCheck) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if(daoZHSL.executeSQL(sql,new Object[]{time,unit,qx,ckmc,ckwz,qpc,ydbc,psb,zl,yscl,bl,dl,yjd,aqm,yy,sd,jsqc,ssl,ts,st,kz,sk,yx,X,Y,isUpload,isCheck})){
			map.put("msg","success");          
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	public Map<String, Object> updateFXWZ(String id,String time,String unit,String qx,String ckmc,String ckwz,String qpc,String ydbc,String psb,String zl,
			String yscl,String bl,String dl,String yjd,String aqm,String yy,String sd,String jsqc,String ssl,String ts,String st,String kz,String sk,String yx,String X,String Y){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "update [dbo].[防汛物资上报] set time=?,unit=?,qx=?,ckmc=?,ckwz=?,qpc=?,ydbc=?,psb=?,zl=?,yscl=?,bl=?,dl=?,yjd=?,aqm=?,yy=?,sd=?,jsqc=?,ssl=?,ts=?,st=?,kz=?,sk=?,yx=?,X=?,Y=? where id= ?";
			if(daoZHSL.executeSQL(sql, new Object[]{time,unit,qx,ckmc,ckwz,qpc,ydbc,psb,zl,yscl,bl,dl,yjd,aqm,yy,sd,jsqc,ssl,ts,st,kz,sk,yx,X,Y,id})){
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
	public Map<String, Object> delFXWZ(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		String sql ="delete [dbo].[防汛物资上报] where id=?";
		if (daoZHSL.executeSQL(sql,new Object[]{id})) {
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	public Map<String, Object> addDwInfo(String time,String unit,String aera,String teanName, String count,String place,
			String contacter, String phone,String X,String Y,String isUpload,String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[防汛队伍上报](time,unit,aera,teanName,count,place,contacter,phone,X,Y,isUpload,isCheck) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		if(daoZHSL.executeSQL(sql,new Object[]{time,unit,aera,teanName,count,place,contacter,phone,X,Y,isUpload,isCheck})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	public Map<String, Object> updateDwInfo(String id,String time,String unit,String aera,String teanName, String count,String place,
			String contacter, String phone,String X,String Y){
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "update [dbo].[防汛队伍上报] set time=?,unit=?,aera=?, teanName=?,count=?,place=?,contacter=?,phone=?,X=?,Y=? where id= ?";
			if(daoZHSL.executeSQL(sql, new Object[]{time,unit,aera,teanName,count,place,contacter,phone,X,Y,id})){
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
	}
	
	public Map<String, Object> delDwInfo(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		String sql ="delete [dbo].[防汛队伍上报] where id=?";
		if (daoZHSL.executeSQL(sql,new Object[]{id})) {
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	
	@Override
	public Map<String, Object> addPlCapStaInfo(String time,String unit,String aera, String totalPlCap,String qpcCnt, String qpcCap, String ydbcCnt, String ydbeCap,
			String psbcCnt, String psbcCap, String memo, String isUpload,String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[排涝能力统计](time,unit,aera,totalPlCap,qpcCnt,qpcCap,ydbcCnt,ydbeCap,psbcCnt,psbcCap,memo,isUpload,isCheck) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if(daoZHSL.executeSQL(sql,new Object[]{time,unit,aera,totalPlCap,qpcCnt,qpcCap,ydbcCnt,ydbeCap,psbcCnt,psbcCap,memo,isUpload,isCheck})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updatePlCapStaInfo(String id,String time,String unit,String aera,String totalPlCap, String qpcCnt, String qpcCap, String ydbcCnt,
			String ydbeCap, String psbcCnt, String psbcCap, String memo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[排涝能力统计] set time=?,unit=?,aera=?, totalPlCap=?,qpcCnt=?,qpcCap=?,ydbcCnt=?,ydbeCap=?,psbcCnt=?,psbcCap=?,memo=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{time,unit,aera,totalPlCap,qpcCnt,qpcCap,ydbcCnt,ydbeCap,psbcCnt,psbcCap,memo,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> delPlCapStaInfo(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		String sql ="delete [dbo].[排涝能力统计] where id=?";
		if (daoZHSL.executeSQL(sql,new Object[]{id})) {
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	
	//汛期服务
	public Map<String, Object> addZbPerson(String unit,String zbtime,String time,String leader, String leaderPhone,String zbPerson,
			String zbPersonPhone, String zbPhone, String fax,String typhoon,String isUpload,String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[防汛值班表](unit,zbtime,time,leader,leaderPhone,zbPerson,zbPersonPhone,zbPhone,fax,typhoon,isUpload,isCheck) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		if(daoZHSL.executeSQL(sql,new Object[]{unit,zbtime,time,leader,leaderPhone,zbPerson,zbPersonPhone,zbPhone,fax,typhoon,isUpload,isCheck})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	public Map<String, Object> updateZbPerson(String id,String unit,String time,String zbtime,String leader, String leaderPhone,String zbPerson,
			String zbPersonPhone, String zbPhone, String fax){
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "update [dbo].[防汛值班表] set unit=?, time=?, zbtime=?, leader=?,leaderPhone=?,zbPerson=?,zbPersonPhone=?,zbPhone=?,fax=? where id= ?";
			if(daoZHSL.executeSQL(sql, new Object[]{unit,time,zbtime,leader,leaderPhone,zbPerson,zbPersonPhone,zbPhone,fax,id})){
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
	}
	public Map<String, Object> delZbPerson(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		String sql ="delete [dbo].[防汛值班表] where id=?";
		if (daoZHSL.executeSQL(sql,new Object[]{id})) {
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	
	public Map<String, Object> addBsfxInfo(String time,String unit,String aera, String bqry,String cdry,String bqcl, String cdcl, String jsdd, String zyry,
			String smdf,String smql, String ldsh,String ldxf,String qlcz, String yjstgd, String slrx,
			String qxdlss,String hwggdt, String hwggql,String ryss,String rysw, String fwdt, String pcjjxq,
			String other,String xqdeal,String typhoon,String isUpload,String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[城区防台防汛情况表](time,unit,aera,bqry,cdry,bqcl,cdcl,jsdd,zyry,smdf,smql,ldsh,ldxf,qlcz,yjstgd,slrx,qxdlss,hwggdt,hwggql,ryss,rysw,fwdt,pcjjxq,other,xqdeal,typhoon,isUpload,isCheck) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if(daoZHSL.executeSQL(sql,new Object[]{time,unit,aera,bqry,cdry,bqcl,cdcl,jsdd,zyry,smdf,smql,ldsh,ldxf,qlcz,yjstgd,slrx,qxdlss,hwggdt,hwggql,ryss,rysw,fwdt,pcjjxq,other,xqdeal,typhoon,isUpload,isCheck})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	public Map<String, Object> updateBsfxInfo(String id,String time,String unit,String aera, String bqry,String cdry,String bqcl, String cdcl, String jsdd, String zyry,
			String smdf,String smql, String ldsh,String ldxf,String qlcz, String yjstgd, String slrx,
			String qxdlss,String hwggdt, String hwggql,String ryss,String rysw, String fwdt, String pcjjxq,
			String other,String xqdeal){
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "update [dbo].[城区防台防汛情况表] set time=?,unit=?, aera=?,bqry=?,cdry=?,bqcl=?,cdcl=?,jsdd=?,zyry=?, "
					+ "smdf=?,smql=?,ldsh=?,ldxf=?,qlcz=?,yjstgd=?,slrx=?, qxdlss=?,hwggdt=?,hwggql=?,ryss=?,rysw=?,fwdt=?,pcjjxq=?, other=?,xqdeal=? where id= ?";
			if(daoZHSL.executeSQL(sql, new Object[]{time,unit,aera,bqry,cdry,bqcl,cdcl,jsdd,zyry,smdf,smql,ldsh,ldxf,qlcz,yjstgd,slrx,qxdlss,hwggdt,hwggql,ryss,rysw,fwdt,pcjjxq,other,xqdeal,id})){
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
	}
	public Map<String, Object> delBsfxInfo(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		String sql ="delete [dbo].[城区防台防汛情况表] where id=?";
		if (daoZHSL.executeSQL(sql,new Object[]{id})) {
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	
	@Override
	public Map<String, Object> addNlSta(String time,String unit, String jsdType, String jsdLoc, String waterDep, String jsAera,
			String jsReason, String dealMethod, String respUnit,String typhoon,String isUpload, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[内涝统计上报](unit,time,jsdType,jsdLoc,waterDep,jsAera,jsReason,dealMethod,respUnit,typhoon,isUpload,isCheck) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		if(daoZHSL.executeSQL(sql,new Object[]{unit,time,jsdType,jsdLoc,waterDep,jsAera,jsReason,dealMethod,respUnit,typhoon,isUpload,isCheck})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateNlSta(String id,String time, String unit, String jsdType, String jsdLoc, String waterDep, String jsAera,
			String jsReason, String dealMethod, String respUnit) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[内涝统计上报] set unit=?, time=?,jsdType=?,jsdLoc=?,waterDep=?,jsAera=?,jsReason=?,dealMethod=?,respUnit=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{unit,time,jsdType,jsdLoc,waterDep,jsAera,jsReason,dealMethod,respUnit,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> delNlSta(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		String sql ="delete [dbo].[内涝统计上报] where id=?";
		if (daoZHSL.executeSQL(sql,new Object[]{id})) {
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	
	@Override
	public Map<String, Object> addXhReport(String title, String unit,
			String time, String filepath, String isUpload, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[汛后总结报告](title,unit,time,filepath,isUpload,isCheck) values(?,?,?,?,?,?)";
		if(daoZHSL.executeSQL(sql,new Object[]{title,unit,time,filepath,isUpload,isCheck})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> delXhReport(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		String sql ="delete [dbo].[汛后总结报告] where id=?";
		if (daoZHSL.executeSQL(sql,new Object[]{id})) {
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	
	
	
	
	public Map<String, Object> updateRyUploadState(String id, String isUpload) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛人员] set isUpload=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isUpload,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	public Map<String, Object> updateRyCheckState(String id, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛人员] set isCheck=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isCheck,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	public Map<String, Object> updateWzUploadState(String id, String isUpload) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛物资上报] set isUpload=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isUpload,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	public Map<String, Object> updateWzCheckState(String id, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛物资上报] set isCheck=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isCheck,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	public Map<String, Object> updateDwUploadState(String id, String isUpload) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛队伍上报] set isUpload=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isUpload,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	public Map<String, Object> updateDwCheckState(String id, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛队伍上报] set isCheck=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isCheck,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updatePlStaUploadState(String id, String isUpload) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[排涝能力统计] set isUpload=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isUpload,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updatePlStaCheckState(String id, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[排涝能力统计] set isCheck=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isCheck,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	
	
	@Override
	public Map<String, Object> updateZbUploadState(String id, String isUpload) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛值班表] set isUpload=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isUpload,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateZbCheckState(String id, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛值班表] set isCheck=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isCheck,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateBsfxUploadState(String id, String isUpload) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[城区防台防汛情况表] set isUpload=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isUpload,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateBsfxCheckState(String id, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[城区防台防汛情况表] set isCheck=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isCheck,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateNlStaUploadState(String id, String isUpload) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[内涝统计上报] set isUpload=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isUpload,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateNlStaCheckState(String id, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[内涝统计上报] set isCheck=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isCheck,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateRepUploadState(String id, String isUpload) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[汛后总结报告] set isUpload=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isUpload,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateRepCheckState(String id, String isCheck) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[汛后总结报告] set isCheck=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{isCheck,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public List<Map<String, Object>> QueryTyphoon(String selectyr) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		Object[] args = {selectyr};
		String sql = " SELECT TITLE,MAX(TM) TM FROM [dbo].[NEW_FXYJ] where DATEPART(YEAR,TM)= ? group by TITLE order by TM desc "; 
		rtList = daoZHSL.executeQuery(sql,args); 
		return rtList; 
	}
	////退回理由入库
	@Override
	public Map<String, Object> updateRyRjtRsn2Db(String id, String rjtReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛人员] set rjtReason=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{rjtReason,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateZbRjtRsn2Db(String id, String rjtReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛值班表] set rjtReason=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{rjtReason,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateBsfxRjtRsn2Db(String id, String rjtReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[城区防台防汛情况表] set rjtReason=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{rjtReason,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateNlStaRjtRsn2Db(String id, String rjtReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[内涝统计上报] set rjtReason=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{rjtReason,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateRepRjtRsn2Db(String id, String rjtReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[汛后总结报告] set rjtReason=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{rjtReason,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateWzRjtRsn2Db(String id, String rjtReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛物资上报] set rjtReason=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{rjtReason,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updatePlStaRjtRsn2Db(String id, String rjtReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[排涝能力统计] set rjtReason=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{rjtReason,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	@Override
	public Map<String, Object> updateDwRjtRsn2Db(String id, String rjtReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update [dbo].[防汛队伍上报] set rjtReason=? where id= ?";
		if(daoZHSL.executeSQL(sql, new Object[]{rjtReason,id})){
			map.put("msg", "success");
		}else {
			map.put("msg", "fail");
		}
		return map;
	}
	
	public List<Map<String, Object>> queryGPSPos(String tm1,String tm2,String carNum) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql =  " SELECT a.carID,a.[PositioningDate],a.[X],a.[Y],b.[CarName]"
				+ " FROM [zhcg_gps].[dbo].[车辆历史定位表] a,[zhcg_gps].[dbo].[车辆信息表] b"
				+ " where a.carID=b.carID "
				+ " and [PositioningDate] between cast(? as datetime) and cast(? as datetime)"
				+ " and datediff(minute,cast(? as datetime),[PositioningDate]) % 2 = 0 "
				+ " and datediff(SECOND,cast(? as datetime),[PositioningDate]) % 10 = 0 "
				+ " and CarName=?"
				+ " GROUP BY a.carID,a.[PositioningDate],a.[X],a.[Y],b.[CarName]" ;
		rtList = daozhcggps.executeQuery(sql,new Object[]{tm1,tm2,tm1,tm1,carNum}); 
		return rtList; 
	}
	
	public List<Map<String, Object>> queryLqInfo() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql =  " SELECT * FROM [zhcg].[dbo].[lqxc]" ;
		rtList = daozhcggps.executeQuery(sql,new Object[]{}); 
		return rtList; 
	}
	
	public List<Map<String, Object>> queryLqUser() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql =  "SELECT distinct [picuser] FROM [zhcg].[dbo].[lqxc]" ;
		rtList = daozhcggps.executeQuery(sql,new Object[]{}); 
		return rtList; 
	}
	
	public synchronized Map<String, Object> updateLqxcPic(String pictm,String picuser, String title,
			String picname,String memo, String tel){
			Map<String, Object> map = new HashMap<String, Object>();
			String [] picNames=picname.split(",");
			for (int i = 0; i < picNames.length; i++) {
				String sql = "insert into [dbo].[lqxc] (pictm,picuser,title,picname,memo,tel) values(?,?,?,?,?,?)";
				if(daoZHSL.executeSQL(sql, new Object[]{pictm,picuser,title,picNames[i],memo,tel})){
					map.put("msg", "success");
				}else {
					map.put("msg", "fail");
				}
			}
			
			return map;
		}
	
	
}

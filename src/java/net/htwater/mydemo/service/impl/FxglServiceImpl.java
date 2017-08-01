package net.htwater.mydemo.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.axis2.databinding.types.soapencoding.String;



import com.hp.hpl.sparta.xpath.TrueExpr;

import net.htwater.mydemo.service.FxglService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
//import net.htwater.cms.util.HttpUtil;
import cn.miao.framework.util.PinyinUtil;

public class FxglServiceImpl implements FxglService {
	BaseDao daoQGJ = DaoFactory.getDao(QGJ_SMP);

	
	public List<Map<String, Object>> Queryszxx() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " SELECT * FROM [dbo].[水质] "; 
		rtList = daoQGJ.executeQuery(sql); 
		return rtList; 
	}
	public List<Map<String, Object>> Querybzxx() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " SELECT * FROM [dbo].[泵站] "; 
		rtList = daoQGJ.executeQuery(sql); 
		return rtList; 
	}
	public List<Map<String, Object>> Querydmll() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " SELECT * FROM [dbo].[海塘工程] order by [LGTD]"; 
		rtList = daoQGJ.executeQuery(sql); 
		return rtList; 
	}
	public List<Map<String, Object>> Query360() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " SELECT * FROM [dbo].[全景点] order by [LGTD] "; 
		rtList = daoQGJ.executeQuery(sql); 
		return rtList; 
	}
	public List<Map<String, Object>> Queryspd() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " SELECT * FROM [dbo].[视频点] "; 
		rtList = daoQGJ.executeQuery(sql); 
		return rtList; 
	}
	public List<Map<String, Object>> Queryqjd() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " SELECT * FROM [dbo].[全景点] "; 
		rtList = daoQGJ.executeQuery(sql); 
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryqxdw() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " SELECT * FROM [dbo].[防汛队伍统计] "; 
		rtList = daoQGJ.executeQuery(sql); 
		return rtList; 
	}
	 
	public List<Map<String, Object>> Querywz() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = "select a.*,b.LGTD,b.LATD from  [dbo].防汛物资统计 a, [dbo].防汛物资仓库 b"
				+ " where a.ID=b.ID";
		rtList = daoQGJ.executeQuery(sql); 
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryfxya_lj() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql = " SELECT * FROM [dbo].[立交下穿道路]  "; 
		rtList = daoQGJ.executeQuery(sql); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("立交下穿道路").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryfhmb() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql = " SELECT [ID],[类型],[名称],[全景],[强排车],[强排人员],[应急泵站],[强排人员物资配备],[预案],[LGTD],[LATD],[视频],[SYT]"
				+"	FROM [dbo].[重点防护目标]  "; 
		rtList = daoQGJ.executeQuery(sql); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("名称").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryfxya_yj() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql = " SELECT * FROM [dbo].[易积水点]  "; 
		rtList = daoQGJ.executeQuery(sql); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("ID").toString(); 
				map.put("_t", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryfxwz2() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql = " SELECT * FROM [dbo].[防汛物资统计] "; 
		rtList = daoQGJ.executeQuery(sql); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("区县").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	
	
	public List<Map<String, Object>> QueryEquip() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql = " SELECT * FROM [dbo].[设备管理] "; 
		rtList = daoQGJ.executeQuery(sql); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String ennm = map.get("equipNM").toString(); 
				map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryjb() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
		String sql = " SELECT * FROM [dbo].[简报] order by [名称] desc "; 
		rtList = daoQGJ.executeQuery(sql); 
		return rtList; 
	}

	public Map<String, Object> addEquipment(String equipNM,String equipLoc,String  manger,String telephone,String equipWell,String memo,String regionid) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[设备管理](equipNM, equipLoc, manger, telephone, equipWell, memo, regionid) values(?,?,?,?,?,?,?)";
		if(daoQGJ.executeSQL(sql,new Object[]{equipNM, equipLoc, manger, telephone, equipWell, memo, regionid})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	
		public Map<String, Object> updateEquipment(String id,String equipNM,String equipLoc,String  manger,String telephone,String equipWell,String memo){
				
				Map<String, Object> map = new HashMap<String, Object>();
				String sql = "update [dbo].[设备管理] set equipNM=?,equipLoc=?,manger=?,telephone=?,equipWell=?,memo=? where id= ?";
				if(daoQGJ.executeSQL(sql, new Object[]{equipNM, equipLoc, manger, telephone, equipWell, memo,id})){
					map.put("msg", "success");
				}else {
					map.put("msg", "fail");
				}
				return map;
			}
		
		public Map<String, Object> delEquipment(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[设备管理] where id=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		public Map<String, Object> delEquipCheck(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[设备检查] where id=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		public Map<String, Object> delEquipMaintain(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[设备维修] where id=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		
		public Map<String, Object> updateContactPersonorderid(String id,String type) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql =" "
						+"  declare @id int ,@orderid int ,@id2 int,@orderid2 int,@type varchar(50)"
						+"  set @type=?"
						+"  set @id=? "
						+"  select @orderid=[ORDERID]  from [dbo].[设备管理] where ID=@id"
						+"  if(@type='up')"
						+"  begin"
						+"	set @id2=(select top 1 [ID] from [dbo].[设备管理] where [ORDERID]<@orderid order by [ORDERID] desc)"
						+"	set @orderid2=(select top 1 [ORDERID] from [dbo].[设备管理] where [ORDERID]<@orderid order by [ORDERID] desc)"
						+"  end"
						+"  else"
						+"  begin"
						+"	set @id2=(select top 1 [ID] from [dbo].[设备管理] where [ORDERID]>@orderid order by [ORDERID] )"
						+"	set @orderid2=(select top 1 [ORDERID] from [dbo].[设备管理] where [ORDERID]>@orderid order by [ORDERID] )"
						+"  end "
						+"  if(@id2 is not null)"
						+"	begin"
						+"	 update [dbo].[设备管理] set [ORDERID]=@orderid2 where ID=@id"
						+"	 update [dbo].[设备管理] set [ORDERID]=@orderid where ID=@id2"
						+"	end";
			if (daoQGJ.executeSQL(sql,new Object[]{type,id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		@Override
		public List<Map<String, Object>> queryLatestWXRepTM(String regionid,
				String repType) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT max(repTM) TM FROM [dbo].[月报表] WHERE regionid = '"+regionid+"' and repType = '"+repType+"' "; 
			rtList = daoQGJ.executeQuery(sql); 
			return rtList;
		}
		@Override
		public List<Map<String, Object>> QueryMonthReport(String TM,String repType) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[月报表] WHERE CONVERT(varchar(7), repTM, 120) = '"+TM+"' and repType = '"+repType+"' ORDER BY repTM"; 
			rtList = daoQGJ.executeQuery(sql); 
			return rtList; 
		}
		@Override
		public List<Map<String, Object>> QueryMonthReport4Manage(String regionid,String repType) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[月报表] WHERE regionid = '"+regionid+"' and repType = '"+repType+"' ORDER BY repTM desc"; 
			rtList = daoQGJ.executeQuery(sql); 
			return rtList; 
		}
		@Override
		public List<Map<String, Object>> queryLatestYHRepTM(String regionid,
				String repType) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT max(repTM) TM FROM [dbo].[月报表] WHERE regionid = '"+regionid+"' and repType = '"+repType+"' "; 
			rtList = daoQGJ.executeQuery(sql); 
			return rtList;
		}
		@Override
		public List<Map<String, Object>> QueryReport4WXYH(String regionid,String repType) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[养护考核] WHERE regionid = '"+regionid+"' and repType = '"+repType+"' ORDER BY repTM desc"; 
			rtList = daoQGJ.executeQuery(sql); 
			return rtList; 
		}
		@Override
		public Map<String, Object> addMonthReport(String repNM,String orgname,String repType, String repTM,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[月报表](repNM,orgname,repType,repTM,regionid) values(?,?,?,?,?)";
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM" );
			try {
				Date TM = sdf.parse(repTM);
				if(daoQGJ.executeSQL(sql,new Object[]{repNM,orgname,repType,TM,regionid})){
					map.put("msg","success");
				}else {
					map.put("msg","fail");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;
			
		}
		@Override
		public Map<String, Object> addObserveReport(String regionid,String year,String name,String repNM,String orgname,String filetype) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[观测报告](regionid,year,name,filename,orgname,filetype) values(?,?,?,?,?,?)";
			if(daoQGJ.executeSQL(sql,new Object[]{regionid,year,name,repNM,orgname,filetype})){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
			
		}
		@Override
		public Map<String, Object> addWeiXiuRep(String repNM,String orgname, String repType,
				String repTM ,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[养护考核](repNM,orgname,repType,repTM,regionid) values(?,?,?,?,?)";
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			try {
				Date TM = sdf.parse(repTM);
				if(daoQGJ.executeSQL(sql,new Object[]{repNM,orgname,repType,TM,regionid})){
					map.put("msg","success");
				}else {
					map.put("msg","fail");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;
		}
		
		@Override
		public List<Map<String, Object>> QueryWeiXiuRep(String TM) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			Object[] args = {TM};
			String sql = " SELECT * FROM [dbo].[养护考核] WHERE CONVERT(varchar(7), repTM, 120) = ? and repType='monthRep' ORDER BY repTM"; 
			rtList = daoQGJ.executeQuery(sql,args); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("repType").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		@Override
		public List<Map<String, Object>> QueryStaticRep(String TM) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			Object[] args = {TM};
			String sql = " SELECT * FROM [dbo].[养护考核] WHERE CONVERT(varchar(7), repTM, 120) = ? and repType='staticRep' ORDER BY repTM"; 
			rtList = daoQGJ.executeQuery(sql,args); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("repType").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		@Override
		public List<Map<String, Object>> QueryWeixiuStaticRep(String TM,String repType) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			Object[] args = {TM,repType};
			String sql = " SELECT * FROM [dbo].[月报表] WHERE CONVERT(varchar(7), repTM, 120) = ? and repType=? ORDER BY repTM"; 
			rtList = daoQGJ.executeQuery(sql,args); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("repType").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		public List<Map<String, Object>> getChecklist(){
			String sql = "select * from [dbo].设备检查";
			return daoQGJ.executeQuery(sql);
		}
		public List<Map<String, Object>> getMaintainForm(){
			String sql = "select * from [dbo].设备维修";
			return daoQGJ.executeQuery(sql);
		}
		
		public List<Map<String, Object>> queryObserReport(String TM1,String TM2,String filetype){
			Object[] args = {TM1,TM2,filetype};
			String sql = "select * FROM [dbo].[观测报告] where CONVERT(varchar(4), year, 120) >= ? and CONVERT(varchar(4), year, 120) <= ? and filetype = ? order by year desc";
			return daoQGJ.executeQuery(sql,args);
		}
		
		public List<Map<String, Object>> QuerySubDanger() {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[隐患处理] order by tm desc "; 
			rtList = daoQGJ.executeQuery(sql); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("name").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		
		public List<Map<String, Object>> QueryDanger() {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[险情上报] order by tm desc "; 
			rtList = daoQGJ.executeQuery(sql); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("name").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		
		@Override
		public Map<String, Object> addSubDanger(String name, String location,
				String description, String tm, String howFind, String pic,
				String dealMethod, String dealTm, String dealPic,String dealMemo,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[隐患处理](name, location, description, tm, howFind, pic, dealMethod, dealTm, dealPic, dealMemo, regionid) values(?,?,?,?,?,?,?,?,?,?,?)";
			if(daoQGJ.executeSQL(sql,new Object[]{ name, location, description, tm, howFind, pic, dealMethod, dealTm, dealPic, dealMemo, regionid})){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
		}
		public Map<String, Object> delSubDanger(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[隐患处理] where ID=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		@Override
		public Map<String, Object> updateSubDanger(String id, String name,
				String location, String description, String tm, String howFind,
				String pic, String dealMethod, String dealTm, String dealPic,
				String dealMemo) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "update [dbo].[隐患处理] set name=?,location=?,description=?,tm=?,howFind=?,pic=?,dealMethod=?,dealTm=?,dealPic=?,dealMemo=? where id= ?";
			if(daoQGJ.executeSQL(sql, new Object[]{name, location, description, tm, howFind, pic, dealMethod, dealTm, dealPic, dealMemo,id})){
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		@Override
		public Map<String, Object> addDanger(String unit, String writetm,
				String name, String location, String description, String tm,
				String pic,String orgPic, String qxState, String xqImprove, String problem,
				String report,String orgReport, String writeperson, String phone,
				String respperson,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[险情上报](unit, writetm, name, location, description, tm, pic,orgPic, qxState, xqImprove, problem, report,orgReport, writeperson, phone, respperson, regionid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			if(daoQGJ.executeSQL(sql,new Object[]{unit, writetm, name, location, description, tm, pic,orgPic,
					qxState, xqImprove, problem, report,orgReport, writeperson, phone, respperson, regionid})){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
		}
		@Override
		public Map<String, Object> updateDanger(String id, String unit,
				String writetm, String name, String location,
				String description, String tm, String pic,String orgPic, String qxState,
				String xqImprove, String problem, String report,String orgReport,
				String writeperson, String phone, String respperson) {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "update [dbo].[险情上报] set unit=?,writetm=?,name=?,location=?,description=?,tm=?,pic=?,orgPic=?,qxState=?,xqImprove=?,"
					+ "problem=?,report=?,orgReport=?,writeperson=?,phone=?,respperson=? where id= ?";
			if(daoQGJ.executeSQL(sql, new Object[]{unit, writetm, name, location, description, tm, pic,orgPic,
					qxState, xqImprove, problem, report,orgReport, writeperson, phone, respperson,id})){
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		public Map<String, Object> delDanger(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[险情上报] where ID=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		
		@Override
		public Map<String, Object> IsFileExist(String filepath) {
			Map<String, Object> map=new HashMap<String, Object>();
			File file=new File(filepath);
			if(!file.exists()){
				map.put("isExist", true);
			}
			return map;
		}
		
		public List<Map<String, Object>> QueryFXJC() {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[防汛检查] order by tm desc "; 
			rtList = daoQGJ.executeQuery(sql); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("name").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		public List<Map<String, Object>> QuerySpecJC() {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[嘉兴定期检查表] order by ID desc "; 
			rtList = daoQGJ.executeQuery(sql); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("htnm").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		public List<Map<String, Object>> QuerySubQK() {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[嘉兴险工隐患表] order by ID desc "; 
			rtList = daoQGJ.executeQuery(sql); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("htnm").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		
		@Override
		public Map<String, Object> addFXJC(String tm, String  name, String hdtdSitu,String htgkSitu,String fcssSitu,String sswzSitu,
				String jcPerson,String jlPerson,String memo,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[防汛检查]( tm, name, hdtdSitu, htgkSitu, fcssSitu, sswzSitu, jcPerson, jlPerson, memo, regionid) values(?,?,?,?,?,?,?,?,?,?)";
			if(daoQGJ.executeSQL(sql,new Object[]{ tm, name, hdtdSitu, htgkSitu, fcssSitu, sswzSitu, jcPerson, jlPerson, memo, regionid})){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
		}
		
		@Override
		public Map<String, Object> addSpeCheck(String htnm,String jctm,
				  String wt1,String wt2,String wt3,String wt4,String wt5,String wt6,
				  String wt7,String wt8,String wt9,String wt10,String wt11,String wt12,
				  String wt13,String wt14,String wt15,String wt16,String wt17,String wt18,
				  String jl1,String jl2,String jl3,String jl4,String jl5,String jl6,
				  String jl7,String jl8,String jl9,String jl10,String jl11,String jl12,
				  String jl13,String jl14,String jl15,String jl16,String jl17,String jl18,
				  String checkPerson,String chargePerson) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[嘉兴定期检查表](  htnm, jctm, wt1, wt2, wt3, wt4, wt5, wt6,wt7, wt8, wt9, wt10, wt11, wt12,"
					+ " wt13, wt14, wt15, wt16, wt17, wt18,jl1, jl2, jl3, jl4, jl5, jl6,jl7, jl8, jl9, jl10, jl11, jl12,"
					+ " jl13, jl14, jl15, jl16, jl17, jl18,checkPerson, chargePerson) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			if(daoQGJ.executeSQL(sql,new Object[]{ htnm, jctm, wt1, wt2, wt3, wt4, wt5, wt6,wt7, wt8, wt9, wt10, wt11, wt12,
					 wt13, wt14, wt15, wt16, wt17, wt18,jl1, jl2, jl3, jl4, jl5, jl6,jl7, jl8, jl9, jl10, jl11, jl12,
					 jl13, jl14, jl15, jl16, jl17, jl18,checkPerson, chargePerson})){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
		}
		
		@Override
		public Map<String, Object> addSubdangerQK(String htnm,String subDangerNM,
				String problem,String suggestion,String pics) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[嘉兴险工隐患表](htnm,subDangerNM,problem,suggestion,pics) values(?,?,?,?,?)";
			if(daoQGJ.executeSQL(sql,new Object[]{ htnm,subDangerNM,problem,suggestion,pics})){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
		}
		public Map<String, Object> delSpecJC(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[嘉兴定期检查表] where ID=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		public Map<String, Object> delSubQK(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[嘉兴险工隐患表] where ID=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		

		public Map<String, Object> delFXJC(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[防汛检查] where ID=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		@Override
		public Map<String, Object> delSame(String tm,String regionid,String repType) {
			Map<String, Object> map=new HashMap<String, Object>();
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM 养护考核 WHERE CONVERT(varchar(10), repTM, 120) = '"+tm+"' and regionid = '"+regionid+"' and repType = '"+repType+"' "; 
			rtList = daoQGJ.executeQuery(sql); 
			if (rtList.size() > 0) {
				String sql1 ="DELETE FROM 养护考核 WHERE CONVERT(varchar(10), repTM, 120) = ? and regionid = ? and repType = '"+repType+"' ";
				if (daoQGJ.executeSQL(sql1,new Object[]{tm,regionid})) {
					map.put("msg", "ok");
				}else {
					map.put("msg", "no");
				}
			}else {
				map.put("msg", "ok");
			}
			return map;
		}
		@Override
		public Map<String, Object> delSame1(String tm,String regionid,String repType) {
			Map<String, Object> map=new HashMap<String, Object>();
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM 月报表 WHERE CONVERT(varchar(7), repTM, 120) = '"+tm+"' and regionid = '"+regionid+"' and repType = '"+repType+"' "; 
			rtList = daoQGJ.executeQuery(sql); 
			if (rtList.size() > 0) {
				String sql1 ="DELETE FROM 月报表 WHERE CONVERT(varchar(7), repTM, 120) = ? and regionid = ? and repType = '"+repType+"' ";
				if (daoQGJ.executeSQL(sql1,new Object[]{tm,regionid})) {
					map.put("msg", "ok");
				}else {
					map.put("msg", "no");
				}
			}else {
				map.put("msg", "ok");
			}
			return map;
		}
		@Override
		public Map<String, Object> addQRCode(String name,String location,String type,String html,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[二维码](name,location,type,html,tm,regionid) values('"+name+"','"+location+"','"+type+"','"+html+"',getdate(),'"+regionid+"' )";
			if(daoQGJ.executeSQL(sql)){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
		}
		public List<Map<String, Object>> QueryQRCode() {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[二维码] order by tm desc "; 
			rtList = daoQGJ.executeQuery(sql); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("name").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		public Map<String, Object> delQRCode(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[二维码] where ID=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		public List<Map<String, Object>> QueryTZbyID(String id) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT [html] FROM [dbo].[二维码] where ID= "+id; 
			rtList = daoQGJ.executeQuery(sql); 
			return rtList; 
		}
		@Override
		public Map<String, Object> updateQRCode(String id,String name,String location,String type,String html,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "update [dbo].[二维码] set name=?,location=?,type=?,html=?,tm=?,regionid=? where id= ?";
			Date date=new Date();
			if(daoQGJ.executeSQL(sql, new Object[]{name,location,type,html,date,regionid,id})){
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		@Override
		public Map<String, Object> addEquipCheck(String elementnm,
				String departmentnm, String responsiblenm, String sdt,
				String state, String description, String filename,String orgname,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[设备检查]( elementnm, departmentnm, responsiblenm, sdt, state, description, filename, orgname,regionid) values(?,?,?,?,?,?,?,?,?)";
			if(daoQGJ.executeSQL(sql,new Object[]{ elementnm, departmentnm, responsiblenm, sdt, state, description, filename, orgname,regionid})){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
		}
		@Override
		public Map<String, Object> addEquipMaintain(String elementnm,
				String departmentnm, String responsiblenm, String sdt,
				String state, String description, String filename,String orgname,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[设备维修]( elementnm, departmentnm, responsiblenm, sdt, state, description, filename, orgname,regionid) values(?,?,?,?,?,?,?,?,?)";
			if(daoQGJ.executeSQL(sql,new Object[]{ elementnm, departmentnm, responsiblenm, sdt, state, description, filename, orgname,regionid})){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
		}
	
		public List<Map<String, Object>> QueryWarnTide() {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT 海塘代码编号 AS enmcd, 工程名称 AS ennm, 参照潮位站名称 AS stnm_tide, 参照潮位站_蓝色警戒潮位 AS warn_blue, "
					+ " 参照潮位站_黄色警戒潮位 AS warn_yellow, 参照潮位站_橙色警戒潮位 AS warn_orange, 参照潮位站_红色警戒潮位 AS warn_red"
					+ " FROM [dbo].杭州段12段海塘基本信息 "; 
			rtList = daoQGJ.executeQuery(sql); 
			return rtList; 
		}
		
		public List<Map<String, Object>> QueryDoc(String type) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].档案检索 WHERE type = '"+type+"'"; 
			rtList = daoQGJ.executeQuery(sql); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String id=map.get("ID").toString();
					//System.out.println(id);
					String ennm = map.get("案卷题名").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		@Override
		public Map<String, Object> delSingleRep(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[观测报告] where ID=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		@Override
		public Map<String, Object> delSingleRep4WXBB(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[月报表] where id=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		@Override
		public Map<String, Object> delSingleRep4WXYH(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[养护考核] where id=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		
		@Override
		public Map<String, Object> addYHXCJL(String bh, String  xctm, String xcdd,String xcry,String xcqk,String clyj,
				String cljg,String hzr,String ksfzr,String bz,String regionid) {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql="insert into [dbo].[养护巡查汇总表](  bh,xctm,xcdd,xcry,xcqk,clyj,cljg,hzr,ksfzr,bz,regionid) values(?,?,?,?,?,?,?,?,?,?,?)";
			if(daoQGJ.executeSQL(sql,new Object[]{ bh,xctm,xcdd,xcry,xcqk,clyj,cljg,hzr,ksfzr,bz,regionid})){
				map.put("msg","success");
			}else {
				map.put("msg","fail");
			}
			return map;
		}
		
		public List<Map<String, Object>> QueryHisYhjc(String regionid) {
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
			String sql = " SELECT * FROM [dbo].[养护巡查汇总表] where regionid = ? order by xctm desc "; 
			rtList = daoQGJ.executeQuery(sql,new Object[]{regionid}); 
			if (rtList != null) {
				for (Map<String, Object> map : rtList) {
					String ennm = map.get("bh").toString(); 
					map.put("_s", ennm + PinyinUtil.converterToPinYin(ennm));
				}
			}
			return rtList; 
		}
		
		public Map<String, Object> delHisYhxc(String id) {
			Map<String, Object> map=new HashMap<String, Object>();
			String sql ="delete [dbo].[养护巡查汇总表] where ID=?";
			if (daoQGJ.executeSQL(sql,new Object[]{id})) {
				map.put("msg", "success");
			}else {
				map.put("msg", "fail");
			}
			return map;
		}
		
		
		
		
}

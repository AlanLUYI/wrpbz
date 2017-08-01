package net.htwater.mydemo.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.DateUtil;
import net.htwater.mydemo.service.OfficeService;
/**
 * 
 * 
 * @author yj
 * @version
 * @since v 1.0
 * @Date   2013-02-01 3:16:42 PM
 * 
 * @see
 */
public class OfficeServiceImpl implements OfficeService {
	
	BaseDao dao = DaoFactory.getDao(QGJ_SMP);
	
	public boolean addUserZhaoPian(int id, String pathString, String sheetnameString) {
		String sqlString = "update o_user set path=?,sheetname=?"
				+ " where id=?";
		return dao.executeSQL(sqlString, new Object[] {pathString, sheetnameString,  id });
	}
	
	
	public boolean addDegreeSheet(int id, String pathString, String sheetnameString) {
		String sqlString = "update office_StudyInfo set path=?,sheetname=?"
				+ " where ID=?";
		return dao.executeSQL(sqlString, new Object[] {pathString, sheetnameString,  id });
	}
	
	
	public boolean addPicSheet(int id, String Rescd, String pathString, String sheetnameString) {
		String sqlString = "update o_user set path=?,sheetname=?"
				+ " where id=? and rescd=?";
		return dao.executeSQL(sqlString, new Object[] {pathString, sheetnameString,  id, Rescd.trim() });
	}
	
	public boolean addWorkInfoSheet(int id, String pathString, String sheetnameString) {
		String sqlString = "update office_WorkInfo set path=?,sheetname=?"
				+ " where ID=?";
		return dao.executeSQL(sqlString, new Object[] {pathString, sheetnameString,  id });
	}

	
	
	/**
	 * 人事档案中人员列表的人员信息获取功能
	 * @return  Map<String, Object> 返回某水库的所有人员的重要信息
	 * @param ResCD 水库编码	
	 * @since v1.0
	 */
	public Map<String, Object> getPersonInfo(String ResCD) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> newMap = new HashMap<String, Object>();
		//return dao.callProcedure("{call proc_personInfo(?)}", new Object[]{"yj"});
		//list=dao.callProcedure("{call proc_personInfo(?)}", new Object[]{"yj"});
        //String sql = "select a.userid as id,c.name as name,d.selection as selection from o_userLink a left join tb_member as b on a.loginnm=b.username left join o_user as c on a.userid=c.id";
		String sql = "select a.id as id,a.name as name,b.DName as selection,a.onguard,a.mobile,a.phone,a.politics,a.rank,a.islocked,a.sex,a.birthday,a.workday,a.workdaynew,a.departmentcd from o_user a left join SYS_Department as b on a.departmentcd = b.DepartmentCD where b.RESCD='" + ResCD.trim() +"'";
		list= dao.executeQuery(sql);
		for (Map<String, Object> map : list) {
			if((map.get("selection")==null)||(map.get("selection").toString().trim().contains("null")))
			{
				map.remove("selection");
				map.put("selection", "");
			}
			
			if((map.get("politics")==null)||(map.get("politics").toString().trim().contains("null")))
			{
				map.remove("politics");
				map.put("politics", "");
			}
			
			if((map.get("rank")==null)||(map.get("rank").toString().trim().contains("null")))
			{
				map.remove("rank");
				map.put("rank", "");
			}
			
		}
		newMap.put("rows", list);
		return newMap;
	}
	public Map<String, Object> getPersonInfo1(String ResCD) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> newMap = new HashMap<String, Object>();
		//return dao.callProcedure("{call proc_personInfo(?)}", new Object[]{"yj"});
		//list=dao.callProcedure("{call proc_personInfo(?)}", new Object[]{"yj"});
        //String sql = "select a.userid as id,c.name as name,d.selection as selection from o_userLink a left join tb_member as b on a.loginnm=b.username left join o_user as c on a.userid=c.id";
		String sql = "select a.id as id,a.name as name,b.DName as selection,a.onguard,a.mobile,a.phone,a.politics,a.rank,a.islocked,a.sex,a.birthday,a.workday,a.workdaynew from o_user a left join SYS_Department as b on a.departmentcd = b.DepartmentCD where a.onguard='在职' and b.RESCD='" + ResCD.trim() +"'";
		list= dao.executeQuery(sql);
		for (Map<String, Object> map : list) {
			if((map.get("selection")==null)||(map.get("selection").toString().trim().contains("null")))
			{
				map.remove("selection");
				map.put("selection", "");
			}
			
			if((map.get("politics")==null)||(map.get("politics").toString().trim().contains("null")))
			{
				map.remove("politics");
				map.put("politics", "");
			}
			
			if((map.get("rank")==null)||(map.get("rank").toString().trim().contains("null")))
			{
				map.remove("rank");
				map.put("rank", "");
			}
			
		}
		newMap.put("rows", list);
		return newMap;
	}
	
	
	/**
	 * 人事档案中人员列表的组合条件人员查询功能
	 * @return  Map<String, Object> 返回经组合条件人员查询后的人员列表
	 * @param ResCD 水库编码
	 * @param name 姓名
	 * @param sex 性别
	 * @param politics 政治面貌
	 * @param rank 技术职称
	 * @since v1.0
	 */
	public Map<String, Object> PersonFactorsQuery(String ResCD,String name,String sex,String politics,String rank) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> newMap = new HashMap<String, Object>();		
		String sql = "select a.id as id,a.name as name,b.DName as selection,a.politics,a.rank,a.islocked from o_user a left join SYS_Department as b on a.departmentcd = b.DepartmentCD where b.RESCD='" + ResCD.trim() +"'";
		list= dao.executeQuery(sql);
		for (Map<String, Object> map : list) {
			if((map.get("selection")==null)||(map.get("selection").toString().trim().contains("null")))
			{
				map.remove("selection");
				map.put("selection", "");
			}
			
			if((map.get("politics")==null)||(map.get("politics").toString().trim().contains("null")))
			{
				map.remove("politics");
				map.put("politics", "");
			}
			
			if((map.get("rank")==null)||(map.get("rank").toString().trim().contains("null")))
			{
				map.remove("rank");
				map.put("rank", "");
			}
			
		}
		newMap.put("rows", list);
		return newMap;
	}
	
	
	
	public List<Map<String, Object>>  queryDutyInfo(String Rescd) {		

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();		
		String sql = "select * from SYS_Duty where RESCD='"+ Rescd.trim() +"' order by orderby asc";
		list= dao.executeQuery(sql);
		return list;
		
	}
	
	
	public List<Map<String, Object>>  queryAdminDutyInfo(String Rescd) {
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();		
		String sql = "select * from SYS_AdminDuty where RESCD='"+ Rescd.trim() +"' order by orderby asc";
		list= dao.executeQuery(sql);
		return list;
		
		
		
	}
	
	
	public List<Map<String, Object>>  queryTechDutyInfo(String Rescd) {
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();		
		String sql = "select * from SYS_TechDuty where RESCD='"+ Rescd.trim() +"' order by orderby asc";
		list= dao.executeQuery(sql);
		return list;		
		
	}
	
	
	public List<Map<String, Object>>  queryTechPostInfo(String Rescd) {
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();		
		String sql = "select * from SYS_TechPost where RESCD='"+ Rescd.trim() +"' order by orderby asc";
		list= dao.executeQuery(sql);
		return list;		
		
	}
	
	public List<Map<String, Object>>  getZiDuanOption(int ZiDuan,String Rescd) {
		
		String TableNM ="";
		if(ZiDuan!=0)
		{
		  switch(ZiDuan)
		  {
		    case 1:
		    	TableNM = "SYS_Department";
		    	break;
		    	
		    case 2:
		    	TableNM = "SYS_Post";
		    	break;		    	
			 
		    case 3:
		    	TableNM = "SYS_Duty";
		    	break;	
		    	
		    case 4:
		    	TableNM = "SYS_AdminDuty";
		    	break;	
		    	
		    case 5:
		    	TableNM = "SYS_TechDuty";
		    	break;	
		    	
		    case 6:
		    	TableNM = "SYS_TechPost";
		    	break;	
		
		
		  }
		  
		  
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			
			String sql = "select * from " + TableNM + " where RESCD='" + Rescd.trim() +"' order by orderby asc";
			list= dao.executeQuery(sql);
			return list;
		  
		}		
	
		return null;		
		
	}
	/**
	 * 人士档案管理字段管理
	 * @author fhj
	 * @param  oper,id,rescd,ZiDuan,xuanxiang,orderby,optioncd
	 * @return Map<String, Object>
	 */
	public Map<String, Object> UpdateAddOptionService(String oper,String id,String rescd,String ZiDuan,String xuanxiang,String orderby,String optioncd) {
        if(oper.equals("add")){
        	Map<String, String> stream = new HashMap<String, String>();
        	stream.put("rescd", rescd);
        	stream.put("ZiDuan", ZiDuan);
        	stream.put("optioncd","");
        	stream.put("newname", xuanxiang);
        	stream.put("orderby", orderby);
        	this.AddOption(stream);
        }
        if(oper.equals("edit")){
        	Map<String, String> stream = new HashMap<String, String>();
        	stream.put("rescd", rescd);
        	stream.put("ZiDuan", ZiDuan);
        	stream.put("optioncd",optioncd);
        	stream.put("newname", xuanxiang);
        	stream.put("orderby", orderby);
        	this.updateOption(stream);
        }
		return null;
	}
	public Map<String, Object> updateOption(Map<String, String> stream) {
		Map<String, Object> result = new HashMap<String, Object>();
		String rescd = stream.get("rescd").toString();
		int ZiDuan =Integer.parseInt(stream.get("ZiDuan").toString());
		String optioncd = stream.get("optioncd").toString();
		String typename = stream.get("newname").toString();
		String orderby = stream.get("orderby").toString();
		//重名问题
		String sql2 ="";
		switch(ZiDuan)
		{
		  case 1:		  
		     sql2 = "select DName from SYS_Department where RESCD = '" +rescd + "' and DName = '" + typename + "'";
		     break;
		  case 2:		  
			 sql2 = "select postname from SYS_Post where RESCD = '" +rescd + "' and postname = '" + typename + "'";
			 break;		   
		  case 3:		  
			 sql2 = "select dutyname from SYS_Duty where RESCD = '" +rescd + "' and dutyname = '" + typename + "' and orderby='"+orderby+"'";
			 break;
		  case 4:		  
		     sql2 = "select AdminDutyName from SYS_AdminDuty where RESCD = '" +rescd + "' and AdminDutyName = '" + typename + "'and orderby='"+orderby+"'";
			 break;	
		  case 5:		  
			 sql2 = "select TechDutyName from SYS_TechDuty where RESCD = '" +rescd + "' and TechDutyName = '" + typename + "'and orderby='"+orderby+"'";
			 break;
		  case 6:		  
		     sql2 = "select TechPostName from SYS_TechPost where RESCD = '" +rescd + "' and TechPostName = '" + typename + "'and orderby='"+orderby+"'";
		     break;					 
		
		}
		  
		Map<String, Object> t = dao.executeQueryObject(sql2);
		
		if(t!=null)
		{
			result.put("msg", "保存失败！错误原因：该字段已存在“"+typename+"”选项名，请另取选项名");
			return result;
		}
		else   //未重名 
		{
			
			//开始修改
			String sql ="";		
			
			switch(ZiDuan)
			{
			  case 1:		  
			     sql = "update SYS_Department set DName ='" + typename  + "' where RESCD = '" +rescd + "' and DepartmentCD = '" + optioncd + "'";
			     break;
			  case 2:		  
				 sql = "update SYS_Post set postname = '" + typename + "' where RESCD = '" +rescd + "' and postcd = '" + optioncd + "'";
				 break;		   
			  case 3:		  
				 sql = "update SYS_Duty set dutyname = '" + typename + "',orderby='" + orderby + "' where RESCD = '" +rescd + "' and dutycd = '" + optioncd + "'";
				 break;
			  case 4:		  
			     sql = "update SYS_AdminDuty set AdminDutyName = '" + typename + "',orderby='" + orderby + "' where RESCD = '" +rescd + "' and AdminDutyCd = '" + optioncd + "'";
				 break;	
			  case 5:		  
				 sql = "update SYS_TechDuty set TechDutyName = '" + typename + "',orderby='" + orderby + "' where RESCD = '" +rescd + "' and TechDutyCd = '" + optioncd + "'";
				 break;
			  case 6:		  
			     sql = "update SYS_TechPost set TechPostName = '" + typename + "',orderby='" + orderby + "' where RESCD = '" +rescd + "' and TechPostCd = '" + optioncd + "'";
			     break;					 
			
			}		
			
			
			if(dao.executeSQL(sql))
			{
				result.put("msg", "success");
			}
			else
			{
				result.put("msg", "保存失败！");
			}
			return result;			
			
			
		}
		
		
		
//		//系统保留类型问题
//		String sql3 = "select typename, readonly from ele_type where rescd = '" +rescd + "' and departmentcd = '" + departmentcd + "' and typecd = '" + typecd + "'";
//		Map<String, Object> f = dao.executeQueryObject(sql3);
//		if(f.get("readonly").toString().equals("true"))
//		{
//			result.put("msg", "保存失败！错误原因：该部门已存在“"+f.get("typename").toString()+"”类型为系统保留类型，不可修改");
//			return result;
//		}
		
		

	}
	
	
	/*public Map<String, String> GetMaxCode(String rescd, int ZiDuan)
	{
		
		String sql ="";
		Map<String, Object> mac  = new HashMap<String, Object>();
		Map<String, String> stream = new HashMap<String, String>();	
		
		
		switch(ZiDuan)
		{
		  case 1:		  
			  sql = "select top 1 * from SYS_Department where RESCD ='"+ rescd + "' order by DepartmentCD desc";
			  mac = dao.executeQueryObject(sql);				
			  stream.put("optionCD", Integer.parseInt(mac.get("DepartmentCD").toString()) + 1 +"");
			  stream.put("orderby", Integer.parseInt(mac.get("orderby").toString())+ 1 +"");		     
			  
			  break;
		  case 2:		  
			  sql = "select top 1 * from SYS_Post where RESCD = '" +rescd + "' order by postcd desc";
			  mac = dao.executeQueryObject(sql);				
			  stream.put("optionCD", Integer.parseInt(mac.get("postcd").toString()) + 1 +"");
			  stream.put("orderby", Integer.parseInt(mac.get("orderby").toString())+ 1 +"");	
			  break;		   
		  case 3:		  
			  sql = "select top 1 * from SYS_Duty where RESCD = '" +rescd + "' order by dutycd desc";
			  mac = dao.executeQueryObject(sql);				
			  stream.put("optionCD", Integer.parseInt(mac.get("dutycd").toString()) + 1 +"");
			  stream.put("orderby", Integer.parseInt(mac.get("orderby").toString())+ 1 +"");				 
			  break;
		  case 4:		  
			  sql = "select top 1 * from SYS_AdminDuty where RESCD = '" +rescd + "' order by AdminDutyCd desc";
			  mac = dao.executeQueryObject(sql);				
			  stream.put("optionCD", Integer.parseInt(mac.get("AdminDutyCd").toString()) + 1 +"");
			  stream.put("orderby", Integer.parseInt(mac.get("orderby").toString())+ 1 +"");	
			  break;	
		  case 5:		  
			  sql = "select top 1 * from SYS_TechDuty where RESCD = '" +rescd + "' order by TechDutyCd desc";
			  mac = dao.executeQueryObject(sql);				
			  stream.put("optionCD", Integer.parseInt(mac.get("TechDutyCd").toString()) + 1 +"");
			  stream.put("orderby", Integer.parseInt(mac.get("orderby").toString())+ 1 +"");	
			  break;
		  case 6:		  
			  sql = "select top 1 * from SYS_TechPost where RESCD = '" +rescd + "' order by TechPostCd desc";
			  mac = dao.executeQueryObject(sql);				
			  stream.put("optionCD", Integer.parseInt(mac.get("TechPostCd").toString()) + 1 +"");
			  stream.put("orderby", Integer.parseInt(mac.get("orderby").toString())+ 1 +"");	
			  break;					 
		
		}	
		
		return stream;
	}*/
	
	
	
	public Map<String, Object> AddOption(Map<String, String> stream) {
        System.out.println(stream);
		Map<String, Object> result = new HashMap<String, Object>();
		String rescd = stream.get("rescd").toString();
		int ZiDuan =Integer.parseInt(stream.get("ZiDuan").toString());
		String typename = stream.get("newname").toString();	
		
		Map<String, String> mac =new HashMap<String, String>();
/*		mac = this.GetMaxCode(rescd, ZiDuan);
		String maxoptioncd = mac.get("optionCD");*/
		UUID uuid  =  UUID.randomUUID(); 
		String maxoptioncd = UUID.randomUUID().toString(); 
		String maxorderby = stream.get("orderby");
		
		
		//重名问题	
		String sql2 ="";
		switch(ZiDuan)
		{
		  case 1:		  
		     sql2 = "select DName from SYS_Department where RESCD = '" +rescd + "' and DName = '" + typename + "'";
		     break;
		  case 2:		  
			 sql2 = "select postname from SYS_Post where RESCD = '" +rescd + "' and postname = '" + typename + "'";
			 break;		   
		  case 3:		  
			 sql2 = "select dutyname from SYS_Duty where RESCD = '" +rescd + "' and dutyname = '" + typename + "'";
			 break;
		  case 4:		  
		     sql2 = "select AdminDutyName from SYS_AdminDuty where RESCD = '" +rescd + "' and AdminDutyName = '" + typename + "'";
			 break;	
		  case 5:		  
			 sql2 = "select TechDutyName from SYS_TechDuty where RESCD = '" +rescd + "' and TechDutyName = '" + typename + "'";
			 break;
		  case 6:		  
		     sql2 = "select TechPostName from SYS_TechPost where RESCD = '" +rescd + "' and TechPostName = '" + typename + "'";
		     break;					 
		
		}		
		  
		Map<String, Object> t = dao.executeQueryObject(sql2);
		
		if(t!=null)
		{
			result.put("msg", "保存失败！错误原因：该字段已存在“"+typename+"”选项名，请另取选项名");
			return result;
		}
		else   //未重名 
		{
		
			//开始新增
			switch(ZiDuan)
			{
			  case 1:
				 stream.remove("rescd");
				 stream.put("RESCD",rescd);
				    
			     stream.remove("optioncd");
				 stream.put("DepartmentCD",maxoptioncd);
					
				 stream.remove("newname");
				 stream.put("DName",typename);				 
								
				 stream.put("orderby", maxorderby);
				 
				 stream.remove("ZiDuan");
				 
				 
				 if(dao.saveMap("SYS_Department", stream))
				 {
					result.put("msg", "success");
				 }
				 else
				 {
					result.put("msg", "保存失败！");
				 }
				  
				 return result;
				  
        	     
        	     
			  case 2:		  
					 stream.remove("rescd");
					 stream.put("RESCD",rescd);
					    
				     stream.remove("optioncd");
					 stream.put("postcd",maxoptioncd);
						
					 stream.remove("newname");
					 stream.put("postname",typename);				 
									
					 stream.put("orderby", maxorderby);
					 
					 stream.remove("ZiDuan");
					 
					 
					 if(dao.saveMap("SYS_Post", stream))
					 {
						result.put("msg", "success");
					 }
					 else
					 {
						result.put("msg", "保存失败！");
					 }
					  
					 return result;
					 
			  case 3:		  
					 stream.remove("rescd");
					 stream.put("RESCD",rescd);
					    
				     stream.remove("optioncd");
					 stream.put("dutycd",maxoptioncd);
						
					 stream.remove("newname");
					 stream.put("dutyname",typename);				 
									
					 stream.put("orderby", maxorderby);
					 
					 stream.remove("ZiDuan");
					 
					 
					 if(dao.saveMap("SYS_Duty", stream))
					 {
						result.put("msg", "success");
					 }
					 else
					 {
						result.put("msg", "保存失败！");
					 }
					  
					 return result;
					 
			  case 4:		  
					 stream.remove("rescd");
					 stream.put("RESCD",rescd);
					    
				     stream.remove("optioncd");
					 stream.put("AdminDutyCd",maxoptioncd);
						
					 stream.remove("newname");
					 stream.put("AdminDutyName",typename);				 
									
					 stream.put("orderby", maxorderby);
					 
					 stream.remove("ZiDuan");
					 
					 
					 if(dao.saveMap("SYS_AdminDuty", stream))
					 {
						result.put("msg", "success");
					 }
					 else
					 {
						result.put("msg", "保存失败！");
					 }
					  
					 return result;
					 
			  case 5:		  
					 stream.remove("rescd");
					 stream.put("RESCD",rescd);
					    
				     stream.remove("optioncd");
					 stream.put("TechDutyCd",maxoptioncd);
						
					 stream.remove("newname");
					 stream.put("TechDutyName",typename);				 
									
					 stream.put("orderby", maxorderby);
					 
					 stream.remove("ZiDuan");
					 
					 
					 if(dao.saveMap("SYS_TechDuty", stream))
					 {
						result.put("msg", "success");
					 }
					 else
					 {
						result.put("msg", "保存失败！");
					 }
					  
					 return result;
					 
			  case 6:		  
					 stream.remove("rescd");
					 stream.put("RESCD",rescd);
					    
				     stream.remove("optioncd");
					 stream.put("TechPostCd",maxoptioncd);
						
					 stream.remove("newname");
					 stream.put("TechPostName",typename);				 
									
					 stream.put("orderby", maxorderby);
					 
					 stream.remove("ZiDuan");
					 
					 
					 if(dao.saveMap("SYS_TechPost", stream))
					 {
						result.put("msg", "success");
					 }
					 else
					 {
						result.put("msg", "保存失败！");
					 }
					  
					 return result;
					 
			
			}		
			
			

			result.put("msg", "保存失败！");
			
			return result;	
			
			
		
		}
		
		
	}
	

	public Map<String, Object> DeleteOption(Map<String, String> stream) {

		Map<String, Object> result = new HashMap<String, Object>();
		String rescd = stream.get("rescd").toString();
		int ZiDuan =Integer.parseInt(stream.get("ZiDuan").toString());
		String optioncd = stream.get("optioncd").toString();
		String typename = stream.get("newname").toString();	
		
		
		//开始删除
		String sql2 ="";
		switch(ZiDuan)
		{
		  case 1:		  
		     sql2 = "delete from SYS_Department where RESCD = '" +rescd + "' and DepartmentCD = '" + optioncd + "'";
		     break;
		  case 2:		  
			 sql2 = "delete from SYS_Post where RESCD = '" +rescd + "' and postcd = '" + optioncd + "'";
			 break;		   
		  case 3:		  
			 sql2 = "delete from SYS_Duty where RESCD = '" +rescd + "' and dutycd = '" + optioncd + "'";
			 break;
		  case 4:		  
		     sql2 = "delete from SYS_AdminDuty where RESCD = '" +rescd + "' and AdminDutyCd = '" + optioncd + "'";
			 break;	
		  case 5:		  
			 sql2 = "delete from SYS_TechDuty where RESCD = '" +rescd + "' and TechDutyCd = '" + optioncd + "'";
			 break;
		  case 6:		  
		     sql2 = "delete from SYS_TechPost where RESCD = '" +rescd + "' and TechPostCd = '" + optioncd + "'";
		     break;					 
		
		}
		
		
		if(dao.executeSQL(sql2))
		{
			result.put("msg", "success");
		}
		else
		{
			result.put("msg", "删除失败！");
		}
		return result;		
		
		
	}	
	
	
	
	public Map<String, Object>  GetWorkInfo(int id) {		
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> newMap = new HashMap<String, Object>();		
		String sql = "select * from office_WorkInfo where UserId =" + id + "order by StartTime asc";
		list= dao.executeQuery(sql);
		newMap.put("rows", list);
		return newMap;		
		
	}
	
	public Map<String, Object>  GetPicInfo(int id,String Rescd) {		
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> newMap = new HashMap<String, Object>();		
		String sql = "select * from o_user where id =" + id + " and rescd ='" + Rescd.trim() +"'";
		list= dao.executeQuery(sql);
		newMap.put("rows", list);
		return newMap; 
		
		
	}
	
	
    public Map<String, Object>  GetStudyInfo(int id) {
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> newMap = new HashMap<String, Object>();
	    String sql = "select * from office_StudyInfo where UserId =" + id + "order by StartTime asc";
		list= dao.executeQuery(sql);
		newMap.put("rows", list);
		return newMap;
		
		
	}
	//14-3-31 更改原存储过程求sql，原功能未变化byfhj
	public List<Map<String, Object>>  queryDepartmentInfo(String ResCD) {
		String sql = "select * from SYS_Department where isadministration =1 and RESCD ="+ResCD+" order by orderby asc";
		return dao.callProcedure(sql);
		
	}
	
	
	public List<Map<String, Object>>  queryPostInfo(String Rescd) {
		
		return dao.callProcedure("{call proc_queryPostInfo(?)}", new Object[]{Rescd.trim()});
		
	}	
	
	
	public List<Map<String, Object>>  getPerson(String Rescd) {		

	return dao.callProcedure("{call proc_personInfo(?)}", new Object[]{Rescd});
	
	}
	
	public List<Map<String, Object>>  queryPersonInfoById(int id,String Rescd) {
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		String sql = "select top 1 a.*, b.DName as selection  from o_user a left join SYS_Department b on a.departmentcd =b.DepartmentCD where a.id="+ id +" and a.rescd ='" + Rescd.trim() + "'";
		list= dao.executeQuery(sql);
		
		for (Map<String, Object> map : list) {
			if((map.get("name")==null)||(map.get("name").toString().trim().contains("null")))
			{
				map.remove("name");
				map.put("name", "");
			}
			
			if((map.get("nation")==null)||(map.get("nation").toString().trim().contains("null")))
			{
				map.remove("nation");
				map.put("nation", "");
			}
			
			if((map.get("native")==null)||(map.get("native").toString().trim().contains("null")))
			{
				map.remove("native");
				map.put("native", "");
			}
			
			if((map.get("card")==null)||(map.get("card").toString().trim().contains("null")))
			{
				map.remove("card");
				map.put("card", "");
			}
			
			if((map.get("phone")==null)||(map.get("phone").toString().trim().contains("null")))
			{
				map.remove("phone");
				map.put("phone", "");
			}
			
			if((map.get("HomePhone")==null)||(map.get("HomePhone").toString().trim().contains("null")))
			{
				map.remove("HomePhone");
				map.put("HomePhone", "");
			}
			
			if((map.get("mobile")==null)||(map.get("mobile").toString().trim().contains("null")))
			{
				map.remove("mobile");
				map.put("mobile", "");
			}
			
			if((map.get("email")==null)||(map.get("email").toString().trim().contains("null")))
			{
				map.remove("email");
				map.put("email", "");
			}
			
			if((map.get("otherContacts")==null)||(map.get("otherContacts").toString().trim().contains("null")))
			{
				map.remove("otherContacts");
				map.put("otherContacts", "");
			}		
			
		}
		
		return list;
		
	}
	
	public List<Map<String, Object>> queryDepartmentInfoByStringDepartment(String departmentcd) {

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();		
		String sql = "select a.id as id,a.name as name,b.DName as selection from o_user a left join SYS_Department as b on a.departmentcd = b.DepartmentCD where a.departmentcd ='" + departmentcd +"'";
		list= dao.executeQuery(sql);		
		return list;
		
	}
	
	public List<Map<String, Object>> queryALLuser(String rescd) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();		
		String sql = "select a.*,b.DName as selection from o_user as a left join SYS_Department as b on a.departmentcd = b.DepartmentCD where a.rescd = '"+rescd+"' order by orderby";
		list= dao.executeQuery(sql);		
		return list;	
	}
	public List<Map<String, Object>> queryALLuserLOCK(String rescd) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();		
		String sql = "select a.*,b.DName as selection from o_user as a left join SYS_Department as b on a.departmentcd = b.DepartmentCD where a.rescd = '"+rescd+"' and a.islocked = 1 order by orderby";
		list= dao.executeQuery(sql);		
		return list;	
	}
	public List<Map<String, Object>> queryALLdepartment(String rescd) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();		
		String sql = "select * from SYS_Department where isadministration = 1 and rescd = '"+rescd+"' order by orderby";
		list= dao.executeQuery(sql);		
		return list;		
	}
	public void AddPersonInfo(Map<String, String>  MapDate) {		
		dao.saveMap("o_user",MapDate);		
	}
	
	
	public void ModifyPersonInfo(Map<String, String> ids,Map<String, String>  MapDate) {

		dao.updateMap("o_user", ids, MapDate); 		
		
	}
	
	
	public void deletePersonInfoById(Map<String, String> ids) {
		
		dao.deleteById("o_user", ids); 
		String userIdString = ids.get("id");
		Map<String, String> WorkInfoOrStudyInfoIds = new HashMap<String, String>();
		WorkInfoOrStudyInfoIds.put("UserId", userIdString);
		dao.deleteById("office_WorkInfo",WorkInfoOrStudyInfoIds);
		dao.deleteById("office_StudyInfo",WorkInfoOrStudyInfoIds);
		
		
	}
	
    public int GetFirstPersonId(String Rescd) {
		
		
    	String sql = "select top 1 *, b.DName as selection from o_user a left join SYS_Department b on a.departmentcd =b.DepartmentCD where a.rescd='" + Rescd.trim() + "' order by id asc";
    		
		return Integer.parseInt(dao.executeQueryObject(sql).get("id").toString());
		
		
	}
    
    public Map<String,Object> QueryLockStatusById(String Rescd,String id) {
		
    	
    	String sql = "select top 1 *, b.DName as selection from o_user a left join SYS_Department b on a.departmentcd =b.DepartmentCD where a.rescd='" + Rescd.trim() + "' and a.id=" + id;
/*    	if((dao.executeQueryObject(sql).get("islocked")==null)||dao.executeQueryObject(sql).get("islocked").toString().trim().contains("null")||(dao.executeQueryObject(sql).get("islocked").toString().trim().equals("0")))
		  return 0;
    	else*/
    	  return  dao.executeQueryObject(sql);
		
	}
    
    public int SetLockStatusById(String Rescd,int id) {
		
    	int setValue=99999;
    	String sql = "select top 1 *, b.DName as selection from o_user a left join SYS_Department b on a.departmentcd =b.DepartmentCD where a.rescd='" + Rescd.trim() + "' and a.id=" + id;
    	if((dao.executeQueryObject(sql).get("islocked")==null)||dao.executeQueryObject(sql).get("islocked").toString().trim().contains("null")||(dao.executeQueryObject(sql).get("islocked").toString().trim().equals("0")))
    		setValue =1;
    	else if(dao.executeQueryObject(sql).get("islocked").toString().trim().equals("1"))
    		setValue =0;
    	
	    String sql1 = "update o_user set islocked = '" + setValue + "' where rescd ='" +Rescd.trim() + "' and id =" + id;
	    if(dao.executeSQL(sql1))
	      return setValue;
	    else 
	      return 99999;		
		
	}
    
    
   public int QueryTheLastIdOfPersonInfo() {		
		
    	String sql = "select top 1 * from o_user order by id desc";    		
		return Integer.parseInt(dao.executeQueryObject(sql).get("id").toString());		
		
	}
   
   
   public void addWorkInfo(Map<String, Object>  MapDate) {
	    
		dao.saveMap("office_WorkInfo",MapDate);		
		
	}
   
   
   public void updateWorkInfo(Map<String, String> ids,Map<String, Object>  MapDate) {
	    
	   dao.updateMap("office_WorkInfo", ids, MapDate);		
	}
   
	public void deleteWorkInfoById(Map<String, String> ids) {		
		
		dao.deleteById("office_WorkInfo", ids); 		
	}
	
	
	  public void addStudyInfo(Map<String, Object>  MapDate) {
		    
			dao.saveMap("office_StudyInfo",MapDate);	
			
		}
	   
	   
	   public void updateStudyInfo(Map<String, String> ids,Map<String, Object>  MapDate) {
		    
		   dao.updateMap("office_StudyInfo", ids, MapDate); 
			
			
		}
	   
		public void deleteStudyInfoById(Map<String, String> ids) {			
			
			dao.deleteById("office_StudyInfo", ids); 
			
			
		}
		
		
		
		//--------------------档案管理---------------------
		
		public Map<String, Object> GetFilesInfo(String department,String year,String type) {
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String, Object> newMap = new HashMap<String, Object>();		
			String sql = "";			
			sql ="select * from off_FilesInfo ";			
			list= dao.executeQuery(sql);
			newMap.put("rows", list);
			return newMap;
		}
		
		
		
		public Map<String, Object> getFilesdetail(String rescd, String selected,String StartTime,String EndTime) {
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String, Object> newMap = new HashMap<String, Object>(); 
			
			String sql = "";
			
			if((StartTime =="")||(StartTime ==null)||(StartTime =="undefined"))
			{
			  //sql ="select * from off_FilesInfo where type in (" + selected +")";
			  sql ="select * from off_FilesInfo where dutyDepartment in (" + selected + "'') and type in (" + selected +"'')";
			
			}
			else 
			{				
			  sql ="select * from off_FilesInfo where dutyDepartment in (" + selected + "'') and type in (" + selected +"'') and filesDate >='" + StartTime + "00:00:00" + "' and filesDate <='" + EndTime + "00:00:00" + "'";
				
			}			
			
			list= dao.executeQuery(sql);
			newMap.put("rows", list);
			return newMap;
		}
		
		
		public Map<String, Object> GetSingleFileInfo(String FilesNo) {
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String, Object> newMap = new HashMap<String, Object>();
			
			String sql = "select * from off_SingleFileInfo where FilesNum ='" + FilesNo.trim() + "'";
			list= dao.executeQuery(sql);
			newMap.put("rows", list);
			return newMap;
		}
		
		
		public String getSingleFileDetail(String SingleFileId) {
			String sql = "select top 1 * from off_SingleFileInfo where id =" + SingleFileId.trim();
			return dao.executeQueryObject(sql).get("codename").toString();
		}
		
		
		
		
		public void addFilesInfo(Map<String, Object>  MapDate) {	
				dao.saveMap("off_FilesInfo",MapDate);				
		}
		
		
		public void updateFilesInfo(Map<String, String> ids,Map<String, Object>  MapDate) {	
    
			   dao.updateMap("off_FilesInfo", ids, MapDate); 			
				
		}
		   
		public void deleteFilesInfoById(Map<String, String> ids) {				
				
				dao.deleteById("off_FilesInfo", ids); 				
				
		}
		
		
		public void addSingleFileInfo(Map<String, Object>  MapDate) {
		    
			dao.saveMap("off_SingleFileInfo",MapDate);			
			
	}
	
	
	public void updateSingleFileInfo(Map<String, String> ids,Map<String, Object>  MapDate) {
		    
		   dao.updateMap("off_SingleFileInfo", ids, MapDate);			
			
	}
	   
	public void deleteSingleFileInfoById(Map<String, String> ids) {			
			
			dao.deleteById("off_SingleFileInfo", ids);			
			
	}
	
	
	public String QueryFilesInfoById(String id){		
		
		String sql = "select top 1 * from off_FilesInfo where id =" + id.trim();		
		return dao.executeQueryObject(sql).get("FilesNum").toString();
		
	}
	
	
	public String GetLastFilesInfo(){		
		
        String sql = "select top 1 * from off_FilesInfo order by generateDate desc";
        Map<String, Object> newMap =dao.executeQueryObject(sql);        
		return newMap.get("FilesNum").toString().trim() + "," + newMap.get("FilesName").toString().trim();
	}
	
	
	
	public List<Map<String, Object>> getPromenuTree(String rescd) {
		String sql = "SELECT DepartmentCD,DName from SYS_Department where isadministration = 1 and RESCD='"+rescd+"'order by DepartmentCD asc";
		List<Map<String, Object>> data=dao.executeQuery(sql);
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> depList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> yearList=new ArrayList<Map<String,Object>>();
		Map<String, Object> deps=new HashMap<String, Object>();
		Map<String, Object> years=new HashMap<String, Object>();
		deps.put("name", "按科室");
		deps.put("code", "all");
		deps.put("open", true);
		deps.put("iconOpen", "../css/zTreeStyle/img/diy/1_open.png");
		deps.put("iconClose", "../css/zTreeStyle/img/diy/1_close.png");		

		if (data.size()>0) {
			for (Map<String, Object> map : data) {
				Map<String, Object> dep=new HashMap<String, Object>();
				dep.put("name", map.get("DName"));
				dep.put("code", map.get("DepartmentCD"));
				dep.put("icon","../css/zTreeStyle/img/node_door-open.png");
				depList.add(dep);
			}
			deps.put("children", depList);
		}
		
		String sql1 = "SELECT ArchivesTypeCD,ArchivesTypeName from off_ArchivesType where RESCD='"+rescd+"'order by ArchivesTypeCD asc";
		List<Map<String, Object>> data1=dao.executeQuery(sql1);
		
		List<Map<String, Object>> TypeList=new ArrayList<Map<String,Object>>();		
		Map<String, Object> types=new HashMap<String, Object>();	
		types.put("name", "按档案类型");
		types.put("code", "all");
		types.put("open", true);
		types.put("iconOpen", "../css/zTreeStyle/img/diy/1_open.png");
		types.put("iconClose", "../css/zTreeStyle/img/diy/1_close.png");	
		if (data.size()>0) {
			for (Map<String, Object> map : data1) {
				Map<String, Object> type=new HashMap<String, Object>();
				type.put("name", map.get("ArchivesTypeName"));
				type.put("code", map.get("ArchivesTypeCD"));
				type.put("icon","../css/zTreeStyle/img/folder.png");
				TypeList.add(type);
			}
			types.put("children", TypeList);
		}
		
		
		dataList.add(types);
		dataList.add(deps);		
		return dataList;
	}
	
	
	
	
	
	
	//----------------薪资管理---------------------
	public Map<String, Object> getPersonSalaryByYear(String UserId,String year,String rescd) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> newMap = new HashMap<String, Object>();
		
		String sql = "";		
		sql ="select * from off_Salary where UserId ='" + UserId.trim() + "' and rescd ='" + rescd.trim()  + "' and DATEPART(YEAR,Time) ='" + year + "' order by Time desc";
		list= dao.executeQuery(sql);		
		if (list.size() > 0) {
			for (Map<String, Object> map : list) 
			{
				String dateString = DateUtil.date2Str((Date) map.get("Time"),"yyyy-MM");				
				map.put("Time", dateString);
			
			}
		}		
		
		newMap.put("rows", list);
		return newMap;
	}
	
	
	
	
	
	   public void addPersonSalary(Map<String, Object>  MapDate) {
		    
			dao.saveMap("off_Salary",MapDate);			
			
		}
	   
	   
	   public void updatePersonSalary(Map<String, String> ids,Map<String, Object>  MapDate) {	
		    
		   dao.updateMap("off_Salary", ids, MapDate); 			
			
		}
	   
		public void deletePersonSalaryById(Map<String, String> ids) {
			
			dao.deleteById("off_Salary", ids); 			
			
		}	
	
	
		public List <Map<String, Object>> getStaticSalaryForTable(String rescd,String year,String department,String project)
		{
			
			String sql1="select id as UserId,name,departmentcd from o_user where rescd='" + rescd + "'";
			if (!department.contains("所有")) {
				sql1+="and departmentcd in (select top 1 DepartmentCD from SYS_Department where RESCD='"+ rescd +"' and DName ='"+ department + "')";
			}
			List <Map<String, Object>> people=dao.executeQuery(sql1);
			for (Map<String, Object> map : people) {
				String nameString=map.get("name").toString();
				String UserIdString=map.get("UserId").toString();
				//int Jan=0;int Feb=0;int Mar=0;int Apr=0;int May=0;int Jun=0;int Jul=0;int Aug=0;int Sep=0;int Oct=0;int Nov=0;int Des=0;
				String Jan=null;String Feb=null;String Mar=null;String Apr=null;String May=null;String Jun=null;String Jul=null;String Aug=null;String Sep=null;String Oct=null;String Nov=null;String Des=null;
				
				String sql="select * from off_Salary where UserId='"+UserIdString+"' and UserName='"+nameString+"' and " +
						year+" in(DATEPART(YEAR,Time)) order by Time asc";
				List<Map<String, Object>> SalaryList=dao.executeQuery(sql);
				for (Map<String, Object> map2 : SalaryList) {
					//Date sDate= map2.get("YFGZ");
					Date eDate=(Date)map2.get("Time");
					Calendar sCal=Calendar.getInstance();
					sCal.setTime(eDate);						
					Calendar cal=sCal;				   
					if (cal.get(Calendar.YEAR)==Integer.parseInt(year)) {
							switch (cal.get(Calendar.MONTH)) {
								case 0:
									Jan = map2.get(project).toString();
									break;
								case 1:
									Feb = map2.get(project).toString();
									break;
								case 2:
									Mar = map2.get(project).toString();
									break;
								case 3:
									Apr = map2.get(project).toString();
									break;
								case 4:
									May = map2.get(project).toString();
									break;
								case 5:
									Jun = map2.get(project).toString();
									break;
								case 6:
									Jul = map2.get(project).toString();
									break;
								case 7:
									Aug = map2.get(project).toString();
									break;
								case 8:
									Sep = map2.get(project).toString();
									break;
								case 9:
									Oct = map2.get(project).toString();
									break;
								case 10:
									Nov = map2.get(project).toString();
									break;
								case 11:
									Des = map2.get(project).toString();
									break;
								default:
									break;
								}	
						
					}
				}
			map.put("year", year);
			map.put("Jan", Jan);
			map.put("Feb", Feb);
			map.put("Mar", Mar);
			map.put("Apr", Apr);
			map.put("May", May);
			map.put("Jun", Jun);
			map.put("Jul", Jul);
			map.put("Aug", Aug);
			map.put("Sep", Sep);
			map.put("Oct", Oct);
			map.put("Nov", Nov);
			map.put("Des", Des);
			}
		
			return people;
		}

		public List<Map<String, Object>> getStaticSalaryForChart(String rescd,
				String dep, String year,String project) {

			String sql1="select id as UserId,name,departmentcd from o_user where rescd='" + rescd + "'";
			if (!dep.contains("所有")) {
				sql1+="and departmentcd in (select top 1 DepartmentCD from SYS_Department where RESCD='"+ rescd +"' and DName ='"+ dep + "')";
			}
			List <Map<String, Object>> people=dao.executeQuery(sql1);
			for (Map<String, Object> map : people) {
				String nameString=map.get("name").toString();
				String UserIdString=map.get("UserId").toString();
				int Jan=0;int Feb=0;int Mar=0;int Apr=0;int May=0;int Jun=0;int Jul=0;int Aug=0;int Sep=0;int Oct=0;int Nov=0;int Des=0;
				//String Jan=null;String Feb=null;String Mar=null;String Apr=null;String May=null;String Jun=null;String Jul=null;String Aug=null;String Sep=null;String Oct=null;String Nov=null;String Des=null;
				
				String sql="select * from off_Salary where UserId='"+UserIdString+"' and UserName='"+nameString+"' and " +
						year+" in(DATEPART(YEAR,Time)) order by Time asc";
				List<Map<String, Object>> SalaryList=dao.executeQuery(sql);
				for (Map<String, Object> map2 : SalaryList) {
					//Date sDate= map2.get("YFGZ");
					Date eDate=(Date)map2.get("Time");

					Calendar sCal=Calendar.getInstance();
					sCal.setTime(eDate);						
					Calendar cal=sCal;				    
					if (cal.get(Calendar.YEAR)==Integer.parseInt(year)) {
							switch (cal.get(Calendar.MONTH)) {
								case 0:
									Jan = Integer.parseInt(map2.get(project).toString());
									break;
								case 1:
									Feb = Integer.parseInt(map2.get(project).toString());
									break;
								case 2:
									Mar = Integer.parseInt(map2.get(project).toString());
									break;
								case 3:
									Apr = Integer.parseInt(map2.get(project).toString());
									break;
								case 4:
									May = Integer.parseInt(map2.get(project).toString());
									break;
								case 5:
									Jun = Integer.parseInt(map2.get(project).toString());
									break;
								case 6:
									Jul = Integer.parseInt(map2.get(project).toString());
									break;
								case 7:
									Aug = Integer.parseInt(map2.get(project).toString());
									break;
								case 8:
									Sep = Integer.parseInt(map2.get(project).toString());
									break;
								case 9:
									Oct = Integer.parseInt(map2.get(project).toString());
									break;
								case 10:
									Nov = Integer.parseInt(map2.get(project).toString());
									break;
								case 11:
									Des = Integer.parseInt(map2.get(project).toString());
									break;
								default:
									break;
								}	
						
					}
				}
				
		    int total =	Jan + Feb+ Mar+Apr+May+Jun+Jul+Aug+Sep+Oct+Nov+Des;
			map.put("year", year);
			map.put("total", total);
			map.put("Jan", Jan);
			map.put("Feb", Feb);
			map.put("Mar", Mar);
			map.put("Apr", Apr);
			map.put("May", May);
			map.put("Jun", Jun);
			map.put("Jul", Jul);
			map.put("Aug", Aug);
			map.put("Sep", Sep);
			map.put("Oct", Oct);
			map.put("Nov", Nov);
			map.put("Des", Des);
			}
		
			return people;

		}

		public List<Map<String, Object>> queryManageList(){
			String sql = "select * from T_Manage order by orderby";
			return dao.executeQuery(sql);
		}

}

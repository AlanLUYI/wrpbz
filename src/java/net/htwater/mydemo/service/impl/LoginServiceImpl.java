package net.htwater.mydemo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.LoginService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
//import net.htwater.cms.util.HttpUtil;

public class LoginServiceImpl implements LoginService {
	BaseDao daoZHSL = DaoFactory.getDao(DB_ZHSL);

	
	public Map<String, Object> getpad_config() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = " SELECT [id],[vercode],[serverip],[apkurl],[config]  FROM [dbo].[pad_config] "; 
		rtList = daoZHSL.executeQuery(sql, null); 
		return rtList.get(0); 
	}
	
	public List<Map<String, Object>> login(String loginname, String password) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = { loginname,password };
		rtList = daoZHSL.callProcedure("{call proc_login(?,?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> ssologin(String loginname) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = { loginname };
		rtList = daoZHSL.callProcedure("{call proc_ssologin(?)}",args);
		return rtList; 
	}
	
	
	public List<Map<String, Object>> Gettreedata(String user) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(); 
//		String sql = " 	  SELECT  [id],[name],[code] ,[icon],[action],[ENABLED],[orderid],[newPID] [pId]"
//					+"  FROM [dbo].[sys_Menu]"
//					+"  where ENABLED=1 and pId !=0  "; 
//		if(user.equals("qxj")){
//			sql=sql		+ "  and pId not in (5,6,4.1,5.1)  "; 
//		}			
//			sql=sql		+ "order by orderid,pId ,id  "; 
		String sql = " 	 select a.[id],a.[name],a.[code] ,a.[icon],a.[action],a.[ENABLED],a.[orderid],a.[newPID] [pId]"
					+"	,b.showENABLED,b.editpower"
					+"	from [dbo].[sys_Menu] a,dbo.sys_GroupMenu b ,dbo.sys_User c"
					+"	where a.ENABLED=1  and c.UserID='"+user+"' and c.GroupID=b.groupid and b.menuid=a.id"
					+"	order by a.[newPID] ,a.id  "; 
		rtList = daoZHSL.executeQuery(sql, null); 
		return rtList; 
	}
	
	
	
	public Map<String, Object> saveSkin(String UserID,String bg,String op,String cl){
		Map<String, Object> result = new HashMap<String, Object>(); 
		String sql = " delete FROM sys_Skin where UserID=? " 
				+" insert into sys_Skin (UserID,skin_bg,skin_op,skin_cl) values(?,?,?,?)";
		if(daoZHSL.executeSQL(sql, new Object[]{UserID,UserID,bg,op,cl}))
			result.put("status","success");
		else
			result.put("status", "保存失败");			
		 
		return result;
	}
	
	
	public Map<String, Object> saveAids(String UserID,String AIDs){
		Map<String, Object> result = new HashMap<String, Object>(); 
		String sql = " delete FROM sys_UserPower where Userid=? " 
				+" insert into sys_UserPower (Userid,AIDs) values(?,?)";
		if(daoZHSL.executeSQL(sql, new Object[]{UserID,UserID,AIDs}))
			result.put("status","success");
		else
			result.put("status", "保存失败");			
		 
		return result;
	}
	
	
	public List<Map<String, Object>> getmyapps(String GroupID) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = { GroupID };
		String sql = " SELECT  a.[GroupID],[app],[bz],[url]  FROM [dbo].[sys_GroupApp] a,dbo.sys_Group b  where a.GroupID=b.GroupID and b.GroupName=? "; 
		rtList = daoZHSL.executeQuery(sql, args); 
		return rtList; 
	}
	
	public List<Map<String, Object>> geturls(String UserID) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = { UserID };
		String sql = " SELECT  [ID],[Userid],[url],[title]  FROM [dbo].[P_URL] where  Userid=? "; 
		rtList = daoZHSL.executeQuery(sql, args); 
		return rtList; 
	}
	
	
	public Map<String, Object> addurl(String UserID,String url,String title){
		Map<String, Object> result = new HashMap<String, Object>(); 
		String sql =" insert into P_URL ([Userid],[url],[title]) values(?,?,?)";
		if(daoZHSL.executeSQL(sql, new Object[]{UserID,url,title})){
//			result.put("status","success");
			String sqlselect="SELECT  top 1 ID as status   FROM [dbo].[P_URL] where  Userid=? and url=? and title=? order by ID desc";
			result=daoZHSL.executeQueryObject(sqlselect, new Object[]{UserID,url,title});
		} 
		else
			result.put("status", "添加失败");	 
		return result;
	}
	
	public Map<String, Object> updateurl(String ID,String UserID,String url,String title){
		Map<String, Object> result = new HashMap<String, Object>(); 
		String sql =" update [dbo].[P_URL] set url=?,title=? where [ID]=? and Userid=? ";
		if(daoZHSL.executeSQL(sql, new Object[]{url,title,ID,UserID}))
			result.put("status","success");
		else
			result.put("status", "修改失败");			
		 
		return result;
	}
	
	
	public Map<String, Object> deleteurl(String ID,String UserID){
		Map<String, Object> result = new HashMap<String, Object>(); 
		String sql =" delete FROM [dbo].[P_URL]  where [ID]=? and Userid=? ";
		if(daoZHSL.executeSQL(sql, new Object[]{ID,UserID}))
			result.put("status","success");
		else
			result.put("status", "删除失败");			
		 
		return result;
	}
	
	
	
	
	
	
	public Map<String, Object> CheckphoneNumber(String phoneNumber ){
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = { phoneNumber };
		String sql = " SELECT *  FROM [zhcg].[dbo].[verify_user] where [telephone]=? "; 
		rtList = daoZHSL.executeQuery(sql, args);  
		Map<String, Object> result = new HashMap<String, Object>(); 
		if(rtList.size()>0){
			result.put("msg",true);
		}else{
			result.put("msg",false);
		}
		return result;
	}
	
	public Map<String, Object> CheckIMSI(String IMSI ){
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = { IMSI };
		String sql = " SELECT *  FROM [zhcg].[dbo].[verify_user] where [IMSI]=? "; 
		rtList = daoZHSL.executeQuery(sql, args);  
		Map<String, Object> result = new HashMap<String, Object>(); 
		if(rtList.size()>0){
			result.put("msg",true);
		}else{
			result.put("msg",false);
		}
		return result;
	}
	
	public Map<String, Object> SaveIMSI(String phoneNumber,String IMSI){
		Map<String, Object> result = new HashMap<String, Object>(); 
		String sql =" update [dbo].[verify_user] set IMSI=? where [telephone]=?  ";
		if(daoZHSL.executeSQL(sql, new Object[]{IMSI,phoneNumber}))
			result.put("msg",true);
		else
			result.put("msg",false);			
		 
		return result;
	}
}

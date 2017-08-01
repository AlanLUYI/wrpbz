package net.htwater.hos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.miao.framework.cache.CacheUtil;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import net.htwater.hos.service.UserService;

public class UserServiceImpl implements UserService {
	public static String DB_HOS="HOS";
	
//	static {
//		for( Map<String,Object> map:CacheUtil.){
//			
//		}
//		
//		
//	}
	BaseDao dao = DaoFactory.getDao(DB_HOS);
	@Override
	public Boolean login(String loginname, String password) {
		
		String sql = "select loginname from sys_user where loginname=? and password=?";
		Object[] args={loginname,password};
		List<Map<String,Object>> result=dao.executeQuery(sql,args);
		
		dao.closeDB();
		return result.size()>0?true:false;
	}
	@Override
	public Boolean freeLogin(String uid) {
		if (uid != "") {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public List<Map<String, Object>> getMenutree(String loginname) {
		//String sql = "select id,pid,name,action,icon,ord,enable,type from view_user_menu where loginname=? order by ord asc";
		String sql = "select id,pid,name,action,icon,ord,enable,type from sys_menu  order by ord asc";
		Object[] args={};
		List<Map<String,Object>> result=dao.executeQuery(sql,args);
		return result;
	}
	@Override
	public Map<String, Object> getUserInfo(String loginname) {
		// TODO Auto-generated method stub
		String sql = "select loginname userid,name realname,role_code role,regionid region,regionName=(select top 1 regionnm from sys_region b where a.regionid=b.regionid) from sys_user as a where loginname=?";
		Object[] args={loginname};
		Map<String,Object> result = dao.executeQueryObject(sql,args);
		return result;
	}
	@Override
	public List<Map<String, Object>> getDataReportProc(String region) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = dao.executeQuery("{call DataReportProcQuery()}");
		if(region.equals("nbslj")){
			return list;
		}
		List<Map<String, Object>> results=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : list){
			if(map.get("counties").toString().equals(region)){
				results.add(map);
			}
		}
		return results;
	}
	@Override
	public Boolean delDataReportProc(Integer id, String loginname) {
		// TODO Auto-generated method stub
		Boolean result =  dao.executeSQL("{call DataReportProcDel(?,?)}", new Object[]{id,loginname});
		return !result;
	}
	@Override
	public Boolean modifyPassword(String userid, String oldp, String newp) {
		// TODO Auto-generated method stub
		Object[] params=new Object[]{newp,userid,oldp};
		String sql="update sys_user set password=? where loginname=? and password=?";
		Boolean result = dao.executeSQL(sql, params);
		return result;
	}
	@Override
	public List<Map<String, Object>> dealFocusField(
			List<Map<String, Object>> list, String field, String user,String module) {
		String sql="select (','+focus+',') as focus from sys_user_focus where loginname=? and module=?";
		Map<String,Object> map=dao.executeQueryObject(sql,new Object[]{user,module});
		Boolean not_need_deal=true;
		if(map != null){
			Object focus = map.get("focus");
			if(focus != null && !focus.toString().equals(",,"))
			{
				not_need_deal=false;
				String str_focus=focus.toString();
				for(Map<String,Object> obj : list){
					obj.put("isfocus", str_focus.indexOf(","+obj.get(field).toString().trim()+",") > -1);
				}
			}
		}
		if(not_need_deal){
			for(Map<String,Object> obj : list){
				obj.put("isfocus", false);
			}
		}
		return list;
	}
	@Override
	public Boolean setFocus(String user, String module, String focus) {
		focus=focus.replaceAll(" ", "");
		String sql="if exists(select top 1 0 from sys_user_focus where loginname=? and module=?) begin update sys_user_focus set focus=? where loginname=? and module=? end else begin insert into sys_user_focus(loginname,module,focus) values(?,?,?) end";
		Boolean b=dao.executeSQL(sql,new Object[]{user,module,focus,user,module,user,module,focus});
		return b;
	}
	@Override
	public Boolean addFocus(String user, String module, String focus) {
		focus=focus.replaceAll(" ", "");
		String sql="if exists(select top 1 0 from sys_user_focus where loginname=? and module=?) begin update sys_user_focus set focus=replace((focus+','+?),',,',',') where loginname=? and module=? end else begin insert into sys_user_focus(loginname,module,focus) values(?,?,?) end";
		Boolean b=dao.executeSQL(sql,new Object[]{user,module,focus,user,module,user,module,focus});
		return b;
	}
	@Override
	public Boolean delFocus(String user, String module, String focus) {
		focus=focus.replaceAll(" ", "");
		Boolean b=true;
		String sql_get="select focus from sys_user_focus where loginname=? and module=?";
		Map<String,Object> map_focus=dao.executeQueryObject(sql_get, new Object[]{user,module});
		if(map_focus != null){
			focus=map_focus.get("focus").toString().replaceAll(focus, "").replaceAll(",,", ",");
		}
		String sql="update sys_user_focus set focus=? where loginname=? and module=?";
		b=dao.executeSQL(sql,new Object[]{focus,user,module});
		return b;
	}
	@Override
	public Boolean checkModuleAuth(String appcode, String module) {
		// TODO Auto-generated method stub
		Boolean b=false;
		String sql = "select * from sys_auth_modules where appcode=? and enable=1";
		Map<String,Object> map = dao.executeQueryObject(sql, new Object[]{appcode});
		if(map != null){
			if(map.get("modules").toString().indexOf(","+module+",")>-1){
				b=true;
			}
		}
		return b;
	}
	@Override
	public String createModuleToken(String appcode,String module, String session_user,
			String session_region) {
		String sql = "insert into sys_auth_modules_log(token,tm,ischeck,session_userid,session_region,module,appcode) values(?,getdate(),0,?,?,?,?)";
		String uuid=UUID.randomUUID().toString();
		dao.executeSQL(sql, new Object[]{uuid,session_user,session_region,module,appcode});
		return uuid;
	}
	@Override
	public Map<String,Object> checkModuleToken(String token) {
		String sql="select * from sys_auth_modules_log where token=? and ischeck=0";
		Map<String,Object> map = dao.executeQueryObject(sql, new Object[]{token});
		sql="update sys_auth_modules_log set ischeck=1 where token=? and ischeck=0";
		dao.executeSQL(sql, new Object[]{token});
		return map;
	}
	@Override
	public void log(String loginname,String DeptId,String ou, String type, String ip, String city,
			String logstr) {
		// TODO Auto-generated method stub
		String sql="insert into sys_log(loginname,DeptId,ou,logtype,ip,city,logstr,tm) values(?,?,?,?,?,?,?,getdate())";
		dao.executeSQL(sql, new Object[]{loginname,DeptId,ou,type,ip,city,logstr});
	}
	@Override
	public List<Map<String, Object>> getSysLogs(String tm1,String tm2) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> logList = dao.executeQuery("{call QuerySysLog(?,?)}", new Object[]{tm1, tm2});
		return logList;
	}
	@Override
	public List<Map<String, Object>> getSysUsers() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> userList = dao.executeQuery("{call QuerySysUser()}");
		return userList;
	}
	@Override
	public List<Map<String, Object>> getSysRoles() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> roleList = dao.executeQuery("{call QuerySysRole()}");
		return roleList;
	}
	@Override
	public boolean updateUserInfo(String id, String loginName, String userName,
			String roleCode, String regionId, String enable) {
		// TODO Auto-generated method stub
		boolean result = dao.executeSQL("{call UpdateUserInfo(?,?,?,?,?,?)}", new Object[]{id, loginName, userName, roleCode, regionId, enable});
		return !result;
	}
	@Override
	public String insertUserInfo(String loginName, String password,
			String userName, String roleCode, String regionId, String enable) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = dao.executeQuery("{call InsertSysUser(?,?,?,?,?,?)}", new Object[]{loginName, password, userName, roleCode, regionId, enable});
		if(result != null){
			//System.out.println(result.get(0));
			return result.get(0).get("id").toString();
		}
		return null;
	}
	@Override
	public boolean removeSysUser(String id) {
		// TODO Auto-generated method stub
		boolean result = dao.executeSQL("{call RemoveSysUser(?)}", new Object[]{id});
		return !result;
	}
	@Override
	public List<Map<String, Object>> getRoleMenu(String roleCode) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = dao.executeQuery("{call QueryRoleMenu(?)}", new Object[]{roleCode});
		return result;
	}
	@Override
	public List<Map<String, Object>> getAllMenu() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = dao.executeQuery("{call QueryAllMenu()}");
		return result;
	}
	@Override
	public boolean updateRoleMenu(String roleCode, String menuIds) {
		// TODO Auto-generated method stub
		boolean result = dao.executeSQL("{call UpdateRoleMenu(?,?)}", new Object[]{roleCode, menuIds});
		return !result;
	}
}

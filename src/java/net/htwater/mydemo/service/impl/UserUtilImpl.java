package net.htwater.mydemo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import net.htwater.mydemo.service.LogHelper;
import net.htwater.mydemo.service.UserUtil;

public class UserUtilImpl implements UserUtil {

	BaseDao dao = DaoFactory.getDao(QGJ_SMP);

	public Map<String, Object> getKV(String k,String rescd) {
		Map<String, Object> result = new HashMap<String, Object>();
		int v = 1;
		String sql = "select k, v from SYS_kvsequence where k='" + k + "' and rescd = '" + rescd + "'";
		List<Map<String, Object>> r = dao.executeQuery(sql);
		if (r.size() > 0) {
			int _v = Integer.parseInt(r.get(0).get("v").toString()) + 1;
			sql = "update SYS_kvsequence set v=? where k=? and rescd = ?";
			dao.executeSQL(sql, new Object[] { _v, k, rescd});
			v = _v;
		} else {
			sql = "insert into SYS_kvsequence (k,v,rescd) values (?,?,?)";
			dao.executeSQL(sql, new Object[] { k, 1, rescd});
		}
		result.put("v", v);
		return result;
	}	
	public Map<String, Object> getuuid(){
		Map<String, Object> result = dao.executeQueryObject("select newid() as v");		
		return result;
	}
	public String getUserName(int userID,String rescd) {
		String sql = "select name from o_user where id = " + userID + " and rescd = '" + rescd + "'";
		Map<String,Object> map =  dao.executeQueryObject(sql);
		if(map!=null){
			return map.get("name").toString();
		}else{
			return "";
		}
	}

	public String getUserName2(String userID,String rescd) {
		String sql = "select name from o_user where id = " + userID + " and rescd = '" + rescd + "'";
		Map<String,Object> map =  dao.executeQueryObject(sql);
		if(map!=null){
			return map.get("name").toString();
		}else{
			return "";
		}
	}
		
	public List<Map<String, Object>> getUsers(String rescd) {
		// 获取人员
		String sql = "select convert(varchar(50),a.id) as id, a.name,('d_' + a.departmentcd) as pId,"
				+ " '../css/images/people.png' as icon, 'false' as [nocheck] "
				+ " from o_user a where a.rescd = '" + rescd + "' order by orderby";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		
//		for(Map<String, Object> map : list){
//			if(map.get("departmentcd").toString().isEmpty()){
//				map.put("pId","none-dep");
//			}
//		}
		// 获取部门
		sql = "select ('d_' + convert(varchar(50),DepartmentCD)) as id, DName as name, "
				+ " '../css/images/department.png' as icon,'true' as [open], 'false' as [nocheck],'class' as type "
				+ " from SYS_Department where RESCD = '" + rescd + "' and isadministration = 'true' order by orderby";
		List<Map<String, Object>> list2 = dao.executeQuery(sql);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "d_");
		map.put("name", "未设置科室人员");
		map.put("icon", "../css/images/department.png");
		map.put("open", "true");
		map.put("nocheck", "false");
		map.put("type", "class");
		list2.add(map);
		list.addAll(list2);
		return list;
	}
	

	public List<Map<String, Object>> getUsersContact(String rescd){
		// 获取人员
		String sql = "select convert(varchar(50),a.id) as id, a.name,('d_' + a.departmentcd) as pId,mobile,"
				+ " '../css/images/people.png' as icon, 'false' as [nocheck], 'd' as grouptype "
				+ " from o_user a "
				+ " where a.rescd = '" + rescd + "' order by orderby";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		
		// 获取部门
		sql = "select ('d_' + convert(varchar(50),DepartmentCD)) as id, DName as name, "
				+ " '../css/images/department.png' as icon,'true' as [open], 'false' as [nocheck],'class' as type, 'd' as grouptype  "
				+ " from SYS_Department "
				+ " where RESCD = '" + rescd + "' and isadministration = 'true' order by orderby";
		List<Map<String, Object>> list2 = dao.executeQuery(sql);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "d_");
		map.put("name", "未设置科室人员");
		map.put("icon", "../css/images/department.png");
		map.put("open", "true");
		map.put("nocheck", "false");
		map.put("type", "class");
		list2.add(map);
		list.addAll(list2);
		
		sql = "select convert(varchar(50),a.id) as id, a.name,('d_' + a.groupcd) as pId, mobile,"
				+ " '../css/images/people.png' as icon, 'false' as [nocheck], 'c' as grouptype "
				+ " from off_pcontacts a "
				+ " where a.rescd = '" + rescd + "' order by a.id";
		List<Map<String, Object>> list3 = dao.executeQuery(sql);
		list.addAll(list3);
		
		sql = "select ('d_' + convert(varchar(50),gcd)) as id, gname as name, "
				+ " '../css/images/department.png' as icon,'true' as [open], 'false' as [nocheck],'class' as type, 'c' as grouptype "
				+ " from off_pgroup "
				+ " where RESCD = '" + rescd + "'  order by id";
		List<Map<String, Object>> list4 = dao.executeQuery(sql);
		list.addAll(list4);
		
		return list;
	}
	
	public Map<String, Object> getContactById(String rescd,String userid,String grouptype){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "";
		if(grouptype.equals("d")){
			sql = "select id,name,mobile from o_user where rescd='"+rescd+"' and id = '"+userid+"'";
			map = dao.executeQueryObject(sql);
		}else if(grouptype.equals("c")){
			sql = "select id,name,mobile from off_pcontacts where rescd='"+rescd+"' and id = '"+userid+"'";
			map = dao.executeQueryObject(sql);
		}
		return map;
	}
	
	
	public List<Map<String, Object>> getUsersPostTree(String rescd) {
		List<Map<String, Object>> copy = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> userslist = new ArrayList<Map<String,Object>>();
		// 获取人员
		String sql = "select convert(varchar(50),a.id) as id,name,"
				+ " postcd, otherpostcd "
				+ " from o_user a where a.rescd = '" + rescd + "' order by orderby";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		for(Map<String, Object> map : list){
			String postcd = (map.get("postcd")!=null)?map.get("postcd").toString():"";
			String otherpostcd = (map.get("otherpostcd")!=null)?map.get("otherpostcd").toString():"";
			otherpostcd = otherpostcd.replace(")(", ",").replace("(", "")
					.replace(")", "");
			String[] otherpostcdArray = otherpostcd.toString().split("\\,");
			if(!postcd.equals("")){
				Map<String, Object> u = new HashMap<String, Object>();
				u.put("id", "p_"+postcd+"_"+map.get("id").toString());
				u.put("name", map.get("name").toString());
				u.put("pId", "p_"+postcd);
				u.put("icon", "../css/images/people.png");
				u.put("false", "[nocheck]");
				userslist.add(u);
			}
			for (int i = 0; i < otherpostcdArray.length; i++) {
				if (!otherpostcdArray[i].trim().equals("")) {
					Map<String, Object> u = new HashMap<String, Object>();
					u.put("id", "p_"+otherpostcdArray[i]+"_"+map.get("id").toString());
					u.put("name", map.get("name").toString());
					u.put("pId", "p_"+otherpostcdArray[i]);
					u.put("icon", "../css/images/people.png");
					u.put("false", "[nocheck]");
					userslist.add(u);
				}
			}
		}
		// 获取角色
		sql = "select ('p_' + convert(varchar(50),postcd)) as id, postname as name, "
				+ " '../css/images/department.png' as icon,'false' as [open], 'false' as [nocheck],'class' as type,'top1' as pId "
				+ " from SYS_Post where RESCD = '" + rescd + "' and isadministration = 'true' order by orderby";
		List<Map<String, Object>> list2 = dao.executeQuery(sql);
		result.addAll(list2);
		copy.addAll(list2);
//		sql = "select ('p_' + convert(varchar(50),postcd)) as id, postname as name, "
//				+ " '../css/images/department.png' as icon,'false' as [open], 'false' as [nocheck],'class' as type,'top2' as pId  "
//				+ " from SYS_Post where RESCD = '" + rescd + "' and isadministration = 'false' order by orderby";
//		List<Map<String, Object>> list3 = dao.executeQuery(sql);
//		result.addAll(list3);
//		copy.addAll(list3);
//		Map<String, Object> u1 = new HashMap<String, Object>();
//		u1.put("id", "top1");
//		u1.put("name", "行政岗位角色");
//		u1.put("pId", "0");
//		u1.put("type", "class");
//		u1.put("icon", "../css/images/department.png");
//		u1.put("false", "[nocheck]");
//		result.add(u1);
//		Map<String, Object> u2 = new HashMap<String, Object>();
//		u2.put("id", "top2");
//		u2.put("name", "权限岗位角色");
//		u2.put("pId", "0");
//		u2.put("type", "class");
//		u2.put("icon", "../css/images/department.png");
//		u2.put("false", "[nocheck]");
//		result.add(u2);
		for(Map<String,Object> map : copy){
			for(Map<String, Object> user : userslist){
				if(map.get("id").toString().equals(user.get("pId").toString())){
					result.add(user);
				}
			}
		}
		return result;
	}

	public List<Map<String, Object>> getPeople(String rescd) {
		// 获取人员
		String sql = "select convert(varchar(50),a.id) as id, a.name,('d_' + a.departmentcd) as pId,"
				+ " '../css/images/people.png' as icon, 'false' as [nocheck] "
				+ " from o_user a where a.rescd = '" + rescd + "' order by orderby";
		return dao.executeQuery(sql);
	}

	public List<Map<String, Object>> getUsersByDepartment(String rescd,	String departmentcd) {
		// 获取人员
		String sql = "select convert(varchar(50),a.id) as id, a.name,('d_' + a.departmentcd) as pId,"
				+ " '../css/images/people.png' as icon, 'false' as [nocheck],'people' as type "
				+ " from o_user a where a.rescd = '" + rescd + "' and a.departmentcd != '' and a.departmentcd != 'NULL'";
		if (!rescd.equals(departmentcd)) {
			sql += " and departmentcd = '" + departmentcd + "'";
		}
		sql += " order by orderby ";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		// 获取部门
		sql = "select ('d_' + convert(varchar(50),DepartmentCD)) as id, DName as name, "
				+ " '../css/images/department.png' as icon,'true' as [open], 'true' as [nocheck],'department' as type  "
				+ " from SYS_Department where RESCD = '" + rescd + "' and isadministration = 'true' ";
		if (!rescd.equals(departmentcd)) {
			sql += " and departmentcd = '" + departmentcd + "'";
		}
		sql += "order by orderby";
		List<Map<String, Object>> list2 = dao.executeQuery(sql);
		list.addAll(list2);
		return list;
	}

	public Map<String,Object>setloginname(String currentName,String actualName,String rescd, String userid,String loginname, String password){
		//admin不可用
		Map<String,Object> result = new HashMap<String,Object>();
		if(loginname.trim().equals("admin")){
			result.put("msg", "admin用户名不可用");
			return result;
		}
		
		//userid是否已创建过用户
		String sql = "select * from tb_member where userid = " + userid + " and rescd = '" + rescd + "' and username!='admin'";
		if(dao.executeQuery(sql).size()>0){
			result.put("msg", "该人员已创建过账户");
			return result;
		}

		//loginname是否为空
		if(loginname.trim().equals("")){
			result.put("msg", "登录用户名不能为空");
			return result;
		}
		
		//loginname是否已被使用
		sql = "select * from tb_member where username = '"+loginname+"' or otherusername = '" +loginname+"'";
		if(dao.executeQuery(sql).size()>0){
			result.put("msg", "该用户名已经存在");
			return result;
		}
		sql = " insert into tb_member (username,password,userid,dock,desk1,desk2,desk3,desk4,desk5,regdt,otherusername,rescd) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		if (dao.executeSQL(sql, new Object[] {
				loginname, password,userid, "", "", "", "", "", "",getToday("yyyy-MM-dd HH:mm:ss"),"",rescd
			})) {
			result.put("msg", "保存成功");
			new LogHelper().remark_auth(rescd, currentName, actualName,"设置登录用户名"+" loginname:"+loginname+" userid:"+userid,"用户ip","用户所在地","操作成功");
		} else {
			result.put("msg", "保存失败");
			new LogHelper().remark_auth(rescd, currentName, actualName,"设置登录用户名"+" loginname:"+loginname+" userid:"+userid,"用户ip","用户所在地","操作失败");
		}
		return result;
	}

	public Map<String,Object>getotherloginname(String rescd, String userid){
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select otherusername from tb_member where userid = " + userid+" and rescd = '" + rescd+"'";
		result = dao.executeQueryObject(sql);
		return result;
	}

	public Map<String,Object>setotherloginname(String currentName,String actualName,String rescd, String userid,String loginname){
		Map<String,Object> result = new HashMap<String,Object>();
		//admin不可用
		if(loginname.trim().equals("admin")){
			result.put("msg", "admin用户名不可用");
			return result;
		}

		//loginname是否为空
		if(loginname.trim().equals("")){
			result.put("msg", "登录用户名不能为空");
			return result;
		}
		
		//loginname是否已被使用
		String sql = "select * from tb_member where username = '"+loginname+"' or otherusername = '" +loginname+"'";
		if(dao.executeQuery(sql).size()>0){
			result.put("msg", "该用户名已经存在");
			return result;
		}
		sql = " update tb_member set otherusername = ? where userid = ? and rescd = ?";
		if (dao.executeSQL(sql, new Object[] {
				loginname, userid,rescd
			})) {
			result.put("msg", "保存成功");
			new LogHelper().remark_auth(rescd, currentName, actualName,"设置个性登录用户名"+"loginname:"+loginname+"userid:"+userid,"用户ip","用户所在地","操作成功");
		} else {
			result.put("msg", "保存失败");
			new LogHelper().remark_auth(rescd, currentName, actualName,"设置个性登录用户名"+"loginname:"+loginname+"userid:"+userid,"用户ip","用户所在地","操作失败");
		}
		return result;
	}

	public Map<String, Object> deleteUser(String loginName,String actualName,String rescd, String userid) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "delete tb_member where userid = ? and rescd = ?"
				+ "	delete o_user where id = ? and rescd = ?";
		if (dao.executeSQL(sql, new Object[] { userid, rescd, userid, rescd })) {
			result.put("msg", "删除成功");
			new LogHelper().remark_auth(rescd, loginName, actualName,"删除用户"+" userid:"+userid,"用户ip","用户所在地","操作成功");
		} else {
			result.put("msg", "失败");
			new LogHelper().remark_auth(rescd, loginName, actualName,"删除用户"+" userid:"+userid,"用户ip","用户所在地","操作失败");
		}
		return result;
	}

	public Map<String, Object> getLoginfoByUserid(String uid, String rescd) {
		String sqlString="select username,password from tb_member where userid="+uid+" and rescd ='"+rescd+"'" ;
		return dao.executeQueryObject(sqlString);
	}
	
	public boolean updatePassword(String loginName,String actualName,String uid, String pass,String rescd) {
		String sqlString="update tb_member set password=? where userid=? and rescd = ?";
		new LogHelper().remark_auth(rescd, loginName, actualName,"修改密码"+" userid:"+uid,"用户ip","用户所在地","操作成功");
		return dao.executeSQL(sqlString, new Object[]{pass,uid,rescd});
	}                                    
	
	
	public List<Map<String,Object>>getDeptUsers(String rescd){
		//(1)先获部门信息
		String sql = "select a.DepartmentCD as departmentcd, a.DName as departmentnm, a.isadministration,a.orderby " +
				" from SYS_Department a" +
				" where a.RESCD = '" + rescd + "' order by a.isadministration asc,a.orderby asc, a.departmentcd asc ";
		List<Map<String,Object>> postlist = dao.executeQuery(sql);
		//(2)再获人员全部信息
		sql = "select id as userid, name as username,departmentcd,otherdepartmentcd from o_user where rescd = '" + rescd +"' order by orderby";
		List<Map<String,Object>> totaluserlist = dao.executeQuery(sql);

		//(3)将人员信息整合到岗位信息里
		for(Map<String,Object> post : postlist){
			String users = "";
			for(Map<String,Object> u : totaluserlist){
				if(post.get("departmentcd")!=null && u.get("departmentcd")!=null){
					if(post.get("departmentcd").toString().equals(u.get("departmentcd").toString())
							|| u.get("otherdepartmentcd").toString().contains("("+post.get("departmentcd").toString()+")")){
						users += ("<div class='deptuser'>"+u.get("username")+
								"<img class='deldeptuser'src='css/images/del.png' departmentcd='"+post.get("departmentcd")+"'departmentnm='"+post.get("departmentnm")+"'"+
								"userid='"+u.get("userid")+"'></div>");
					}
				}
			}
			post.put("users", users);
			post.put("orderby", post.get("orderby"));
			if(post.get("isadministration").toString().equals("true")){
				post.put("type", "行政机构部门");
			}else{
				post.put("type", "其他机构部门");
			}
		}
		return postlist;
	}
	
	public List<Map<String,Object>>getPostUsers(String rescd){
		//(1)先获岗位角色信息
		String sql = "select b.postcd, b.postname,b.isadministration,b.orderby " +
				" from SYS_Post b " +
				" where b.RESCD = '" + rescd + "' order by b.orderby ASC, b.postcd asc ";
		List<Map<String,Object>> postlist = dao.executeQuery(sql);
		//(2)再获人员全部信息
		sql = "select id as userid, name as username,postcd, otherpostcd,orderby as userorderby from o_user where rescd = '" + rescd +"' order by orderby ";
		List<Map<String,Object>> totaluserlist = dao.executeQuery(sql);
		
		//(3)将人员信息整合到岗位信息里
		for(Map<String,Object> post : postlist){
			String users = "";
			for(Map<String,Object> u : totaluserlist){
				if(post.get("postcd")!=null && u.get("postcd")!=null){
					if(post.get("postcd").toString().equals(u.get("postcd").toString())
						|| u.get("otherpostcd").toString().contains("("+post.get("postcd").toString()+")")){
						users += ("<div class='postuser'>"+u.get("username")+
								"<img class='delpostuser'src='css/images/del.png' postcd='"+post.get("postcd")+"'postnm='"+post.get("postname")+
								"'userid='"+u.get("userid")+"'></div>");
					}
				}
			}
			post.put("users", users);
			post.put("orderby", post.get("orderby"));
			if(post.get("isadministration").toString().equals("true")){
				post.put("type", "行政岗位角色");
			}else{
				post.put("type", "权限岗位角色");
			}
		}
		return postlist;
	}

	public  List<Map<String,Object>>getonguardUsers(String rescd){
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		//(1)先获取rescd所有人员表
		String sql = "select rescd, id as userid, name as username " +
				" from o_user " +
				" where rescd = '" + rescd + "' and OnGuard ='在职' order by orderby";
		list = dao.executeQuery(sql);
		//(2)在获取rescd多有登录用户表（因为不是所有人员都有登录账户）
		sql = "select userid, username as loginname from tb_member where rescd = '" + rescd + "'";
		List<Map<String,Object>> lst = dao.executeQuery(sql);
		//(3)将补充人员的登录账号名
		for(Map<String,Object> user : list){
			for(Map<String,Object> u : lst){
				if(user.get("userid").toString().equals(u.get("userid").toString())
						&&
				!u.get("loginname").toString().equals("admin")){
					user.put("loginname", u.get("loginname"));continue;
				}
			}
		}
		return list;
	}
	
	public  List<Map<String,Object>>getallUsers(String rescd){
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		String sql = "select id, id as userid, name as username,OnGuard,orderby from o_user  " +
				" where rescd = '" + rescd + "' order by orderby";
		list = dao.executeQuery(sql);
		return list;
	}
	
	public  Map<String,Object>setOnguard(String loginName,String actualName,String rescd,String userid){
		Map<String,Object> result =new HashMap<String,Object>();
		String sql = "update o_user set OnGuard = '在职' where rescd ='"+ rescd + "' and id = " + userid;
		if(dao.executeSQL(sql)){
			result.put("msg", "保存成功");
			new LogHelper().remark_auth(rescd, loginName, actualName,"设置在职用户"+" userid:"+userid,"用户ip","用户所在地","操作成功");
		}else{
			result.put("msg", "保存失败");
			new LogHelper().remark_auth(rescd, loginName, actualName,"设置在职用户"+" userid:"+userid,"用户ip","用户所在地","操作失败");
		}
		return result;
	}

	public  Map<String,Object>leaveOnguard(String loginName,String actualName,String rescd,String userid){
		Map<String,Object> result =new HashMap<String,Object>();
		String sql = "update o_user set "
				+ " departmentcd = '', postcd='', "
				+ " otherdepartmentcd='',otherdepartmentnm='', "
				+ " otherpostcd= '', otherpostnm='', "
				+ " OnGuard = '离职' where rescd ='"+ rescd + "' and id = " + userid;
		if(dao.executeSQL(sql)){
			result.put("msg", "保存成功");
			new LogHelper().remark_auth(rescd, loginName, actualName,"设置离职用户"+" userid:"+userid,"用户ip","用户所在地","操作成功");
		}else{
			result.put("msg", "保存失败");
			new LogHelper().remark_auth(rescd, loginName, actualName,"设置离职用户"+" userid:"+userid,"用户ip","用户所在地","操作失败");
		}
		return result;
	}
	
	public  Map<String,Object>setuserdept(String loginName,String actualName,String rescd,String userid,String departmentcd,String departmentnm){
		Map<String,Object> result =new HashMap<String,Object>();
		String sql = "select isadministration from SYS_Department where DepartmentCD = '"+ departmentcd + "' and RESCD = '" +rescd +"'";
		if(dao.executeQueryObject(sql).get("isadministration").toString().equals("true")){
			//行政机构部门
			sql = "update o_user set departmentcd = ? where id = ? and rescd = ?";
			if(dao.executeSQL(sql,new Object[]{departmentcd,userid,rescd})){
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置行政机构"+" userid:"+userid+" DepartmentCD:"+departmentcd+" departmentnm:"+departmentnm,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置行政机构"+" userid:"+userid+" DepartmentCD:"+departmentcd+" departmentnm:"+departmentnm,"用户ip","用户所在地","操作失败");
			}
		}else{
			//其他机构部门
			sql = "select otherdepartmentcd,otherdepartmentnm from o_user where id = " + userid + " and rescd ='" + rescd+"'";
			Map<String,Object> user = dao.executeQueryObject(sql);
			String otherdepartmentcd = this.add22(user.get("otherdepartmentcd").toString(),departmentcd,user.get("otherdepartmentcd").toString(),departmentcd);
			String otherdepartmentnm = this.add11(user.get("otherdepartmentcd").toString(),departmentcd,user.get("otherdepartmentnm").toString(),departmentnm);
			sql = "update o_user set otherdepartmentcd = ?, otherdepartmentnm=? where id = ? and rescd=?";
			if(dao.executeSQL(sql,new Object[]{otherdepartmentcd,otherdepartmentnm,userid,rescd})){
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置其他机构"+" userid:"+userid+" DepartmentCD:"+departmentcd+" departmentnm:"+departmentnm,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置其他机构"+" userid:"+userid+" DepartmentCD:"+departmentcd+" departmentnm:"+departmentnm,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}

	public  Map<String,Object>removeuserdept(String loginName,String actualName,String rescd,String userid,String departmentcd,String departmentnm){
		Map<String,Object> result =new HashMap<String,Object>();
		String sql = "select isadministration from SYS_Department where DepartmentCD = '"+ departmentcd + "' and RESCD = '" +rescd +"'";
		if(dao.executeQueryObject(sql).get("isadministration").toString().equals("true")){
			//行政机构部门
			sql = "update o_user set departmentcd = ? where id = ? and rescd =?";
			if(dao.executeSQL(sql,new Object[]{"",userid,rescd})){
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(rescd, loginName, actualName,"移动人员行政机构"+" userid:"+userid+" DepartmentCD:"+departmentcd+" departmentnm:"+departmentnm,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"移动人员行政机构"+" userid:"+userid+" DepartmentCD:"+departmentcd+" departmentnm:"+departmentnm,"用户ip","用户所在地","操作失败");
			}
		}else{
			//其他机构部门
			sql = "select otherdepartmentcd,otherdepartmentnm from o_user where id = " + userid +" and rescd ='"+ rescd+"'";
			Map<String,Object> user = dao.executeQueryObject(sql);
			String otherdepartmentcd = this.remove22(user.get("otherdepartmentcd").toString(),departmentcd,user.get("otherdepartmentcd").toString());
			String otherdepartmentnm = this.remove11(user.get("otherdepartmentcd").toString(),departmentcd,user.get("otherdepartmentnm").toString());
			
			sql = "update o_user set otherdepartmentcd = ?, otherdepartmentnm=? where id = ? and rescd =?";
			if(dao.executeSQL(sql,new Object[]{otherdepartmentcd,otherdepartmentnm,userid,rescd})){
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(rescd, loginName, actualName,"移动人员其他机构"+" userid:"+userid+" DepartmentCD:"+departmentcd+" departmentnm:"+departmentnm,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"移动人员其他机构"+" userid:"+userid+" DepartmentCD:"+departmentcd+" departmentnm:"+departmentnm,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}

	public  Map<String,Object>setuserpost(String loginName,String actualName,String rescd,String userid,String postcd, String postnm){
		Map<String,Object> result =new HashMap<String,Object>();
		String sql = "select isadministration from SYS_Post where postcd = '"+ postcd + "' and RESCD = '" +rescd +"'";
		if(dao.executeQueryObject(sql).get("isadministration").toString().equals("true")){
			//行政 岗位角色
			sql = "update o_user set postcd = ? where id = ? and rescd = ?";
			if(dao.executeSQL(sql,new Object[]{postcd,userid,rescd})){
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置行政角色"+" userid:"+userid+" postcd:"+postcd+" postnm:"+postnm,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置行政角色"+" userid:"+userid+" postcd:"+postcd+" postnm:"+postnm,"用户ip","用户所在地","操作失败");
			}
		}else{
			//其他 岗位角色
			sql = "select otherpostcd,otherpostnm from o_user where id = " + userid +" and rescd ='"+rescd +"'";
			Map<String,Object> user = dao.executeQueryObject(sql);
			String otherpostcd = this.add22(user.get("otherpostcd").toString(),postcd,user.get("otherpostcd").toString(),postcd);
			String otherpostnm = this.add11(user.get("otherpostcd").toString(),postcd,user.get("otherpostnm").toString(),postnm);
			sql = "update o_user set otherpostcd = ?, otherpostnm=? where id = ? and rescd = ?";
			if(dao.executeSQL(sql,new Object[]{otherpostcd,otherpostnm,userid,rescd})){
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置其他角色"+" userid:"+userid+" otherpostcd:"+otherpostcd+" otherpostnm:"+otherpostnm,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置其他角色"+" userid:"+userid+" otherpostcd:"+otherpostcd+" otherpostnm:"+otherpostnm,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}

	public  Map<String,Object>removeuserpost(String loginName,String actualName,String rescd,String userid,String postcd, String postnm){
		Map<String,Object> result =new HashMap<String,Object>();
		String sql = "select isadministration from SYS_Post where postcd = '"+ postcd + "' and RESCD = '" +rescd +"'";
		if(dao.executeQueryObject(sql).get("isadministration").toString().equals("true")){
			//行政岗位角色
			sql = "update o_user set postcd = ? where id = ? and rescd = ?";
			if(dao.executeSQL(sql,new Object[]{"",userid,rescd})){
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(rescd, loginName, actualName,"移动行政角色"+"userid:"+userid+"postcd:"+postcd,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"移动行政角色"+"userid:"+userid+"postcd:"+postcd,"用户ip","用户所在地","操作失败");	
			}
		}else{
			//其他岗位角色
			sql = "select otherpostcd,otherpostnm from o_user where id = " + userid +" and rescd = '"+rescd+"'";
			Map<String,Object> user = dao.executeQueryObject(sql);
			String otherpostcd = this.remove22(user.get("otherpostcd").toString(),postcd,user.get("otherpostcd").toString());
			String otherpostnm = this.remove11(user.get("otherpostcd").toString(),postcd,user.get("otherpostnm").toString());
			
			sql = "update o_user set otherpostcd = ?, otherpostnm=? where id = ? and rescd =?";
			if(dao.executeSQL(sql,new Object[]{otherpostcd,otherpostnm,userid,rescd})){
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(rescd, loginName, actualName,"移动其他角色"+"userid:"+userid+"otherpostcd:"+otherpostcd+"otherpostnm"+otherpostnm,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"移动其他角色"+"userid:"+userid+"otherpostcd:"+otherpostcd+"otherpostnm"+otherpostnm,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}

	public Map<String,Object>edituser(String loginName,String actualName,String rescd, String userid,String username, String opr, String orderby){
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "";
		if(opr.equals("add")){
			//新增用户
			userid = this.getKV("userid",rescd).get("v").toString();
			sql = " insert into o_user (id,name,rescd,departmentcd,postcd,otherdepartmentcd,otherdepartmentnm,otherpostcd,otherpostnm,orderby) values (?,?,?,?,?,?,?,?,?,?)";
			if (dao.executeSQL(sql, new Object[] { userid, username, rescd,
					"", "", "","","","",orderby 
				})) {
				result.put("msg", "保存成功");
				result.put("userid", userid);
				new LogHelper().remark_auth(rescd, loginName, actualName,"增加新用户"+" username:"+username+" userid:"+userid,"用户ip","用户所在地","操作成功");
			} else {
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"增加新用户"+" username:"+username+" userid:"+userid,"用户ip","用户所在地","操作失败");
			}
			return result;
		}
		if(opr.equals("edit")){
			//修改用户
			sql = "update o_user set name = '" + username + "', orderby = '"+orderby+"' where id='" + userid + "' and rescd ='"
					+ rescd + "'";
			if (dao.executeSQL(sql)) {
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(rescd, loginName, actualName,"编辑用户"+" username:"+username+" userid:"+userid,"用户ip","用户所在地","操作成功");
			} else {
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"编辑用户"+" username:"+username+" userid:"+userid,"用户ip","用户所在地","操作失败");
			}
		}
		if(opr.equals("del")){
			result = this.deleteUser(loginName,actualName,rescd, userid);
		}
		return result;
	}
    
	
	
	
	public List<Map<String, Object>> getPostTree(String rescd) {
		//行政岗位
		String sql = "select postcd as id, postname as name, 'xingzheng' as pId,"//('d_' + convert(varchar,departmentcd)) as pId, "
				+ " '../css/images/post.png' as icon, 'false' as [nocheck], 'true' as [open]  "
				+ " from SYS_Post where isadministration = 'true' and RESCD = '"
				+ rescd
				+ "' order by orderby";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		
		//其他岗位
		sql = "select postcd as id, postname as name, 'other' as pId, "//('d_' + convert(varchar,departmentcd)) as pId, "
				+ " '../css/images/post.png' as icon, 'false' as [nocheck], 'true' as [open]  "
				+ " from SYS_Post where isadministration = 'false' and RESCD = '"
				+ rescd
				+ "' order by orderby";
		list.addAll(dao.executeQuery(sql));
				
		Map<String, Object> xz = new HashMap<String, Object>();
		Map<String, Object> other = new HashMap<String, Object>();
		xz.put("id", "xingzheng");
		xz.put("name", "行政岗位角色");
		xz.put("icon", "");
		xz.put("open", "true");
		xz.put("type", "class");
		xz.put("isleaf", "false");
		xz.put("icon", "../css/images/jigou.png");
		other.put("id", "other");
		other.put("name", "权限岗位角色");
		other.put("icon", "");
		other.put("open", "true");
		other.put("type", "class");
		other.put("isleaf", "false");
		other.put("icon", "../css/images/jigou.png");
		list.add(other);
		list.add(xz);		
		return list;
	}
	
	public List<Map<String, Object>> getXZPostTree(String rescd) {
		String sql = "select postcd as id, postname as name, 'xingzheng' as pId,"//('d_' + convert(varchar,departmentcd)) as pId, "
				+ " '../css/images/post.png' as icon, 'false' as [nocheck], 'xingzheng' as pId,'true' as [open]  "
				+ " from SYS_Post where isadministration = 'true' and RESCD = '"
				+ rescd
				+ "' order by orderby";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		return list;
	}

	public List<Map<String, Object>> getOtherPostTree(String rescd) {
		String sql = "select postcd as id, postname as name, 'other' as pId,"//('d_' + convert(varchar,departmentcd)) as pId, "
				+ " '../css/images/post.png' as icon, 'false' as [nocheck], 'other' as pId,'true' as [open]  "
				+ " from SYS_Post where isadministration = 'false' and RESCD = '"
				+ rescd
				+ "' order by orderby";
		List<Map<String, Object>> list = dao.executeQuery(sql);		
		return list;
	}

	public Map<String, Object> getPostBycd(String postcd, String rescd) {
		String sql = "select a.*, b.DepartmentCD as departmentcd, b.DName as departmentnm from SYS_Post a "
				+ " left join SYS_Department b on a.departmentcd = b.DepartmentCD "
				+ " where a.rescd = '"
				+ rescd
				+ "' and a.postcd = '"
				+ postcd
				+ "'";
		return dao.executeQueryObject(sql);
	}

	public Map<String, Object> savepost( String LoginName,String actualName, String rescd, String postcd,
			String postname, String isadministration, String counts,
			String orderby, String duty,String departmentcd) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(postname.trim().equals("系统管理员")){
			if(!LoginName.equals("admin")){
				result.put("msg", "保存失败，“系统管理员”为系统保留命名");
				return result;
			}
		}
		
		String sql = "";
		if (this.chechexit2("SYS_Post", "RESCD", rescd, "postcd", postcd)) {
			sql = "update SYS_Post set postname=?, isadministration=?,counts=?,orderby=?,duty=?,departmentcd = ? "
					+ " where RESCD = ? and postcd= ?";
		} else {
			sql = "insert into SYS_Post (postname, isadministration,counts,orderby,duty,departmentcd,RESCD, postcd)"
					+ "values (?,?,?,?,?,?,?,?)";
		}

		if (dao.executeSQL(sql, new Object[] { postname, isadministration, counts,
				orderby, duty,departmentcd, rescd, postcd })) {
			result.put("msg", "保存成功");
			new LogHelper().remark_auth(rescd, LoginName, actualName,"新增/修改岗位角色"+" postcd:"+postcd+" postname:"+postname+" isadministration:"+isadministration+
					" counts:"+counts+" orderby:"+orderby+" duty:"+duty+" departmentcd:"+departmentcd,"用户ip","用户所在地","操作成功");
		} else {
			result.put("msg", "保存失败");
			new LogHelper().remark_auth(rescd, LoginName, actualName,"新增/修改岗位角色"+" postcd:"+postcd+" postname:"+postname+" isadministration:"+isadministration+
					" counts:"+counts+" orderby:"+orderby+" duty:"+duty+" departmentcd:"+departmentcd,"用户ip","用户所在地","操作失败");
		
		}
		return result;
	}

	public Map<String, Object> deletepost(String loginName,String actualName, String rescd, String postcd) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select * from SYS_Post where postcd = ? and RESCD =?";
		Map<String, Object> m = dao.executeQueryObject(sql, new Object[] { postcd, rescd });
		if(null != m.get("postname")){
			if(m.get("postname").toString().equals("系统管理员")){
				if(!loginName.equals("admin")){
					result.put("msg", "删除失败，“系统管理员”为系统保留命名");
					return result;
				}
			}
		}
		
		
		sql = "delete SYS_Post where postcd = ? and RESCD =?";
		if (dao.executeSQL(sql, new Object[] { postcd, rescd })) {
			//2013-11-13 
			//同时要删除o_user中拥有该角色的人的对应角色postcd+otherpostcd
			sql = " UPDATE o_user SET postcd = REPLACE(postcd,'"+postcd+"','')  where rescd = '"+rescd+"'";
			sql += " UPDATE o_user SET otherpostcd = REPLACE(otherpostcd,'("+postcd+")','')  where rescd = '"+rescd+"'";
			dao.executeSQL(sql);
			
			result.put("msg", "删除成功");
			new LogHelper().remark_auth(rescd, loginName, actualName,"删除岗位角色"+" postcd:"+postcd,"用户ip","用户所在地","操作成功");
			
			
		} else {
			result.put("msg", "失败");
		}
		return result;
	}	
	
	public List<Map<String, Object>> getDepartment(String rescd) {
		//(1)获取所有人员列表
		//List<Map<String, Object>> userlist = this.getallUsers(rescd);
		//(2)行政机构
		String sql = "select a.isleader, a.RESCD as rescd, a.DepartmentCD as departmentcd, a.DepartmentCD as id, "
				+ " '../css/images/department.png' as icon,a.DName as name, a.DName as departmentnm, a.leader_k, a.leader_f,  a.orderby,"
				+ " b.name as leader_knm, c.name as leader_fnm,"
				+ " d.Inchargeid, " 
				+ " e.name as Incharge, " 
				+ "'xingzheng' as pId,'true' as [open] "
				+ " from SYS_Department a "
				+ " left join o_user b on a.leader_k = b.id and a.RESCD = b.rescd "
				+ " left join o_user c on a.leader_f = c.id and a.RESCD = c.rescd "
				+ " left join SYS_Reservoir d on a.RESCD = d.rescd "
				+ " left join o_user e on d.Inchargeid = e.id and a.RESCD = e.rescd"
				+ " where a.RESCD = '" + rescd + "' "
				+ " and d.rescd = '" + rescd + "' "
				+ " and a.isadministration = 'true' order by a.orderby";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		//(3)其他机构 
		sql = "select a.isleader, a.RESCD as rescd, a.DepartmentCD as departmentcd, a.DepartmentCD as id, "
				+ " '../css/images/department.png' as icon,a.DName as name, a.DName as departmentnm, a.leader_k, a.leader_f,  a.orderby,"
				+ " b.name as leader_knm, c.name as leader_fnm, "
				+ " d.Inchargeid, " 
				+ " e.name as Incharge, " 
				+ "'other' as pId,'true' as [open] "
				+ " from SYS_Department a "
				+ " left join o_user b on a.leader_k = b.id  and a.RESCD = b.rescd "
				+ " left join o_user c on a.leader_f = c.id and a.RESCD = c.rescd "
				+ " left join SYS_Reservoir d on a.RESCD = d.rescd "
				+ " left join o_user e on d.Inchargeid = e.id  and a.RESCD = e.rescd"
				+ " where a.RESCD = '" + rescd + "' " 
				+ " and d.rescd = '" + rescd + "' "
				+ " and a.isadministration = 'false' order by a.orderby";
		list.addAll(dao.executeQuery(sql)); 
//		//(4)科室负责人、分管领导、单位负责人
//		for(Map<String, Object> l :list){
//			l.put("leader_knm", "");l.put("leader_fnm", "");l.put("Incharge", "");
//			if(l.get("leader_k")==null){l.remove("leader_k");l.put("leader_k","");}
//			if(l.get("leader_f")==null){l.remove("leader_f");l.put("leader_f","");}
//			if(l.get("Inchargeid")==null){l.remove("Inchargeid");l.put("Inchargeid","");}
//			for(Map<String, Object> u :userlist){
//				if(l.get("leader_k").toString().equals(u.get("userid").toString())){
//					l.remove("leader_knm");l.put("leader_knm", u.get("username"));
//				}
//				if(l.get("leader_f").toString().equals(u.get("userid").toString())){
//					l.remove("leader_fnm");l.put("leader_fnm", u.get("username"));
//				}
//				if(l.get("Inchargeid").toString().equals(u.get("userid").toString())){
//					l.remove("Incharge");l.put("Incharge", u.get("username"));
//				}
//			}
//		}
		//(5)
		Map<String, Object> xz = new HashMap<String, Object>();
		Map<String, Object> other = new HashMap<String, Object>();
		xz.put("id", "xingzheng");
		xz.put("name", "行政机构");
		xz.put("icon", "");
		xz.put("open", "true");
		xz.put("type", "class");
		xz.put("icon", "../css/images/jigou.png");
		other.put("id", "other");
		other.put("name", "局属单位");
		other.put("icon", "");
		other.put("open", "true");
		other.put("type", "class");
		other.put("icon", "../css/images/jigou.png");
		list.add(xz);
		list.add(other);		
		return list;
	}

	public List<Map<String, Object>> getXZDepartment(String rescd) {
		//(1)获取所有人员列表
		//List<Map<String, Object>> userlist = this.getallUsers(rescd);
		//(2)行政机构
		String sql = "select a.isleader, a.RESCD as rescd, a.DepartmentCD as departmentcd, a.DepartmentCD as id, "
				+ " '../css/images/department.png' as icon,a.DName as name, a.DName as departmentnm, " 
				+ " a.leader_k, a.leader_f,  a.orderby,"
				+ " b.name as leader_knm, c.name as leader_fnm,"
				+ " d.Inchargeid, " 
				+ " e.name as Incharge, " 
				+ " 'xingzheng' as pId,'true' as [open] "
				+ " from SYS_Department a "
				+ " left join o_user b on a.leader_k = b.id and a.RESCD = b.rescd "
				+ " left join o_user c on a.leader_f = c.id and a.RESCD = c.rescd "
				+ " left join SYS_Reservoir d on a.RESCD = d.rescd "
				+ " left join o_user e on d.Inchargeid = e.id and d.rescd = e.rescd "
				+ " where a.RESCD = '" + rescd + "' " + " and a.isadministration = 'true' order by a.orderby";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		return list;
	}
	
	public List<Map<String, Object>> getOtherDepartment(String rescd) {
		//(1)获取所有人员列表
//		List<Map<String, Object>> userlist = this.getallUsers(rescd);
		//(2)其他机构
		String sql = "select a.isleader, a.RESCD as rescd, a.DepartmentCD as departmentcd, a.DepartmentCD as id, "
				+ " '../css/images/department.png' as icon,a.DName as name, a.DName as departmentnm, a.leader_k, a.leader_f,  a.orderby,"
				+ " b.name as leader_knm, c.name as leader_fnm,"
				+ " d.Inchargeid, " 
				+ " e.name as Incharge, " 
				+ " 'other' as pId,'true' as [open] "
				+ " from SYS_Department a "
				+ " left join o_user b on a.leader_k = b.id and a.RESCD = b.rescd "
				+ " left join o_user c on a.leader_f = c.id and a.RESCD = c.rescd "
				+ " left join SYS_Reservoir d on a.RESCD = d.rescd "
				+ " left join o_user e on d.Inchargeid = e.id and a.RESCD = e.rescd "
				+ " where a.RESCD = '" + rescd + "' " + " and a.isadministration = 'false' order by a.orderby";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		return list;
	}
	
	public Map<String, Object> getDep(String rescd, String departmentcd) {
		String sql = "select a.*, a.DepartmentCD as id, a.DName as name,  " 
				+ " b.name as leader_knm, c.name as leader_fnm,  "
				+ " '../css/images/department.png' as icon "
				+ " from SYS_Department a "
				+ " left join o_user b on a.leader_k = b.id and a.RESCD = b.rescd"
				+ " left join o_user c on a.leader_f = c.id and a.RESCD = c.rescd"
				+ " where a.RESCD = '"
				+ rescd
				+ "' and a.DepartmentCD = '"
				+ departmentcd + "'";
		Map<String, Object> obj = dao.executeQueryObject(sql);
		return obj;
	}
	
	public Map<String, Object> saveDep(String loginName,String actualName,Map<String, String> stream) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select * from SYS_Department where RESCD='"
				+ stream.get("rescd") + "' and DepartmentCD='"
				+ stream.get("DepartmentCD") + "'";
		if (dao.executeQuery(sql).size() > 0) {
			//修改部门
			if(stream.get("isadministration").toString().equals("true")){
				//行政机构
				if(stream.get("isleader").toString().equals("true")){
					//检查该部门是否是局领导
					this.setunit(stream.get("rescd"), stream.get("DepartmentCD"));
				}
				sql = "update SYS_Department set DName=?, Phone=?,Fax=?,Duty=?, " +
						" orderby=?,leader_k=?,leader_f=?,isleader=?,isadministration='true'" +
						" where RESCD = ? and DepartmentCD = ?";
				if(dao.executeSQL(sql, new Object[]{stream.get("DName"),stream.get("Phone"),
						stream.get("Fax"),stream.get("Duty"),stream.get("orderby"),
						stream.get("leader_k"),stream.get("leader_f"),stream.get("isleader"),
						stream.get("rescd"),stream.get("DepartmentCD")})){
						result.put("msg", "保存成功");
						new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"修改行政机构"+" DName:"+stream.get("DName")+
								" Phone:"+stream.get("Phone")+" Fax:"+stream.get("Fax")+" Duty:"+stream.get("Duty")+" orderby:"+stream.get("orderby")+" leader_k:"+stream.get("leader_k")+
								" leader_f:"+stream.get("leader_f")+" isleader:"+stream.get("isleader")+" DepartmentCD:"+stream.get("DepartmentCD"),"用户ip","用户所在地","操作成功");
				}else {
					result.put("msg", "保存失败");
					new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"修改行政机构"+" DName:"+stream.get("DName")+
							" Phone:"+stream.get("Phone")+" Fax:"+stream.get("Fax")+" Duty:"+stream.get("Duty")+" orderby:"+stream.get("orderby")+" leader_k:"+stream.get("leader_k")+
							" leader_f:"+stream.get("leader_f")+" isleader:"+stream.get("isleader")+" DepartmentCD:"+stream.get("DepartmentCD"),"用户ip","用户所在地","操作失败");	
				}
			}else{
				//其他机构
				sql = "update SYS_Department set DName=?, Phone=?,Fax=?,Duty=?, " +
						" orderby=?,leader_k='0',leader_f='0',isleader='0',isadministration='false' " +
						" where RESCD = ? and DepartmentCD = ?";
				if(dao.executeSQL(sql, new Object[]{stream.get("DName").toString(),
						stream.get("Phone").toString(),stream.get("Fax").toString(),
						stream.get("Duty").toString(),stream.get("orderby").toString(),
						stream.get("rescd").toString(),stream.get("DepartmentCD").toString()})){
						result.put("msg", "保存成功");
						new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"修改其他机构"+" DName:"+stream.get("DName")+
								" Phone:"+stream.get("Phone")+" Fax:"+stream.get("Fax")+" Duty:"+stream.get("Duty")+" orderby:"+stream.get("orderby")+" leader_k:"+stream.get("leader_k")+
								" leader_f:"+stream.get("leader_f")+" isleader:"+stream.get("isleader")+" DepartmentCD:"+stream.get("DepartmentCD"),"用户ip","用户所在地","操作成功");	
				}else {
					result.put("msg", "保存失败");
					new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"修改其他机构"+" DName:"+stream.get("DName")+
							" Phone:"+stream.get("Phone")+" Fax:"+stream.get("Fax")+" Duty:"+stream.get("Duty")+" orderby:"+stream.get("orderby")+" leader_k:"+stream.get("leader_k")+
							" leader_f:"+stream.get("leader_f")+" isleader:"+stream.get("isleader")+" DepartmentCD:"+stream.get("DepartmentCD"),"用户ip","用户所在地","操作失败");
				}
			}	
		} else {
			//保存新建部门			
			if (dao.saveMap("SYS_Department", stream)) {
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"新建机构"+" DName:"+stream.get("DName")+
						" Phone:"+stream.get("Phone")+" Fax:"+stream.get("Fax")+" Duty:"+stream.get("Duty")+" orderby:"+stream.get("orderby")+" leader_k:"+stream.get("leader_k")+
						" leader_f:"+stream.get("leader_f")+" isleader:"+stream.get("isleader")+" DepartmentCD:"+stream.get("DepartmentCD"),"用户ip","用户所在地","操作成功");		
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"新建机构"+" DName:"+stream.get("DName")+
						" Phone:"+stream.get("Phone")+" Fax:"+stream.get("Fax")+" Duty:"+stream.get("Duty")+" orderby:"+stream.get("orderby")+" leader_k:"+stream.get("leader_k")+
						" leader_f:"+stream.get("leader_f")+" isleader:"+stream.get("isleader")+" DepartmentCD:"+stream.get("DepartmentCD"),"用户ip","用户所在地","操作失败");
			}

			if(stream.get("isadministration").toString().equals("true"))
				//行政机构
				if(stream.get("isleader").toString().equals("true"))
					//检查该部门是否是局领导
					this.setunit(stream.get("rescd"), stream.get("DepartmentCD"));
		}
		return result;
	}

	public Map<String, Object> deleteDep(String loginName,String actualName,String rescd, String departmentcd) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "delete SYS_Department where DepartmentCD = ? and RESCD =?";
		if (dao.executeSQL(sql, new Object[] { departmentcd, rescd })) {
			//2013-11-13 
			//同时要删除o_user中拥有该角色的人的对应角色departmentcd+otherdepartmentcd
			sql = " UPDATE o_user SET departmentcd = REPLACE(departmentcd,'"+departmentcd+"','')  where rescd = '"+rescd+"'";
			sql += " UPDATE o_user SET otherdepartmentcd = REPLACE(otherdepartmentcd,'("+departmentcd+")','')  where rescd = '"+rescd+"'";
			dao.executeSQL(sql);
			
			result.put("msg", "删除成功");
			new LogHelper().remark_auth(rescd, loginName, actualName,"删除机构"+" departmentcd:"+departmentcd,"用户ip","用户所在地","操作成功");
		} else {
			result.put("msg", "删除失败");
			new LogHelper().remark_auth(rescd, loginName, actualName,"删除机构"+" departmentcd:"+departmentcd,"用户ip","用户所在地","操作失败");
		}
		return result;
	}

	
		
	

	public Map<String, Object> getUnit(String rescd) {
		String sql = "select * from [单位信息] where RESCD = '"
				+ rescd + "'";
		return dao.executeQueryObject(sql);
	}

	public Map<String, Object> saveUnit(String loginName,String actualName,Map<String, String> stream) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select * from SYS_Reservoir where RESCD='"
				+ stream.get("rescd") + "'";
		if (dao.executeQuery(sql).size() > 0) {
			Map<String, Object> ids = new HashMap<String, Object>();
			ids.put("RESCD", stream.get("rescd"));
			Map<String, ?> d = dao.updateMap("SYS_Reservoir", ids, stream);
			if (d != null) {
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"修改/保存单位信息","用户ip","用户所在地","操作成功");
			} else {
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"修改/保存单位信息","用户ip","用户所在地","操作失败");
			}
		} else {
			if (dao.saveMap("SYS_Reservoir", stream)) {
				result.put("msg", "保存成功");
				new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"修改/保存单位信息","用户ip","用户所在地","操作成功");
			} else {
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(stream.get("rescd"), loginName, actualName,"修改/保存单位信息","用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}

	public Map<String, Object> getunitleader(String rescd) {
		String sql = "select a.*,b.id as userid, a.RESCD as rescd,  b.name as username from SYS_Reservoir a "
				+ " left join o_user b on a.Inchargeid = b.id and a.RESCD = b.rescd "
				+ " where a.RESCD = '" + rescd + "'";
		return dao.executeQueryObject(sql);
	}

	public Map<String, Object> checkunitleader2(String rescd, String userid) {
		Map<String, Object> result = new HashMap<String,Object>();
		String sql = "select * from SYS_Reservoir where RESCD= '" + rescd
				+ "' and Inchargeid = " + userid;
		List<Map<String, Object>> ok = dao.executeQuery(sql);
		if (ok.size() > 0) {
			result.put("msg", true);
		} else {
			result.put("msg", false);
		}
		return result;
	}

	

	public List<Map<String, String>> getAppTypes() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "6");
		map.put("name", "水库工程");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("id", "1");
		map.put("name", "安全运行");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("id", "3");
		map.put("name", "应急调度");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("id", "2");
		map.put("name", "行政办公");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("id", "4");
		map.put("name", "上下级联动");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("id", "5");
		map.put("name", "系统");
		list.add(map);
		// JSONArray array = JSONArray.fromObject(list);
		return list;
	}

	public List<Map<String, Object>> getAppList() {
		String sql = "select tbid, name, icon, kindid, appcode,apptype from tb_app";
		return dao.executeQuery(sql);
	}
	
	public List<Map<String,Object>> getAppList2(String rescd){
		//2013-10-30 过滤未开放的模块 filter
		String apps = "";
		String sqlapp = "select * from tb_app_open where rescd = '"+rescd+"' and show = 'true'";
		List<Map<String,Object>> lst1 = dao.executeQuery(sqlapp);
		for(Map<String,Object> l :lst1){
			apps += (apps.equals(""))?"'"+l.get("tbid").toString()+"'":(",'"+l.get("tbid").toString()+"'");
		}
		String filter = apps.equals("")?"":"a.tbid in ("+apps+") ";	
		//tb_app,读取模块列表，并结合SYS_appshareall，是否可以全体可看的属性
		String sql = "select a.tbid,a.name as appname, ('<img src=\"../../'+a.icon + '\" class=\"appimage\"/><span class=\"appname\">'+a.name + '</span>') as name," +
				" a.apptype,a.needchecker,b.kindname,c.shareall, a.appcode " +
				" from tb_app a " +
				" left join tb_kind b on a.kindid = b.kindid " +
				" left join SYS_appshareall c on a.tbid = c.tbid and c.rescd ='" + rescd + "' ";
		if(filter.isEmpty()){
			sql +=  " order by b.orderby,a.orderby";
		}else{
			sql += " where " + filter+
					 //*******************************//
					" order by b.orderby,a.orderby";
		}
		List<Map<String,Object>> applist = dao.executeQuery(sql);

		//SYS_authority_more,模块是否有更详细的权限配置
		sql = "select * from SYS_authority_more ";
		List<Map<String,Object>> authority_more_list = dao.executeQuery(sql);
		for(Map<String,Object> app : applist){
			app.put("authority_more", false);
			for(Map<String,Object> a_m : authority_more_list){
				if(a_m.get("appcode").equals(app.get("appcode"))){
					app.put("authority_more", true);	
				}
			}
		}
		//SYS_authority,读取有查看/编辑权限的角色
		sql = "select a.*, b.postname " +
				" from SYS_authority a " +
				" left join SYS_Post b on a.postcd = b.postcd and a.rescd = b.RESCD " +
				" left join SYS_Department c on b.departmentcd = c.DepartmentCD and b.RESCD = c.RESCD " +
				" where a.rescd='" + rescd+"' order by c.orderby,b.orderby";
		List<Map<String,Object>> postlist = dao.executeQuery(sql);
		//SYS_appchecker,读取有审核权限的角色
		sql = "select a.*, b.postname as checkerpost " +
				" from SYS_appchecker a " +
				" left join SYS_Post b on a.postcd = b.postcd and a.rescd = b.RESCD " +
				" left join SYS_Department c on b.departmentcd = c.DepartmentCD  and b.RESCD = c.RESCD " +
				" where a.rescd='" + rescd+"' order by c.orderby,b.orderby";
		List<Map<String,Object>> checkerlist = dao.executeQuery(sql);

		//将查看/编辑权限的角色、审核权限的角色组合到模块列表中
		for(Map<String,Object> app : applist){
			if(app.get("shareall")==null){
				app.remove("shareall");
				app.put("shareall", false);
			}
			//查看、编辑权限的角色
			String sharepost = "<img class='apppostimg' src='css/images/post.png' title='需设置'>";
			for(Map<String,Object> post : postlist){
				
				if(post.get("tbid").toString().equals(app.get("tbid").toString())){
					sharepost += ("<div class='apppost'>"+post.get("postname")+
							"<img class='delapppost'src='css/images/del.png' postcd='"+post.get("postcd")+
							"'tbid='"+app.get("tbid")+"'></div>");
				}
			}
			app.put("sharepost", sharepost);
			//审核权限
			String checkerpost = "";
			if(app.get("needchecker").toString().equals("true")){checkerpost="<img class='appcheckerimg' src='css/images/checker.png' title='需设置'>";}
			for(Map<String,Object> checker : checkerlist){
				if(checker.get("tbid").toString().equals(app.get("tbid").toString())){
					checkerpost += ("<div class='appchecker'>"+checker.get("checkerpost")+
							"<img class='delappchecker'src='css/images/del.png' postcd='"+checker.get("postcd")+
							"'tbid='"+app.get("tbid")+"'></div>");
				}
			}
			if(checkerpost.isEmpty()){
				checkerpost = ("<div class='blankdata'></div>");
			}
			app.put("checkerpost", checkerpost);
		}
		return applist;
	}

	public List<Map<String,Object>> getAppList3(String rescd){
		String sql = "select a.tbid, ('<img src=\"../../'+a.icon + '\" class=\"appimage\"/><span class=\"appname\">'+a.name + '</span>') as name,"
				+ " a.apptype,a.needchecker, "
//				+ " b.rescd,b.show, "
				+ " c.kindname  "
				+ " from tb_app a "
//				+ " left join tb_app_open b on a.tbid = b.tbid "
				+ " left join tb_kind c on a.kindid = c.kindid " 
//				+ " where b.rescd = '" + rescd + "' "
				+ " order by c.orderby,a.orderby";
		List<Map<String,Object>> allAPP = dao.executeQuery(sql);
		sql = "select * from tb_app_open where rescd = '"+rescd+"' and show = 'true'" ;
		List<Map<String,Object>> showAPP = dao.executeQuery(sql);
		for(Map<String,Object> all : allAPP){
			all.put("show", false);
			for(Map<String,Object> show : showAPP){
				if(all.get("tbid").toString().equals(show.get("tbid").toString())){
					all.remove("show");
					all.put("show", true);
				}
			} 
		} 
		return allAPP;
	}

	public List<Map<String, Object>> getAppByPostcd(String rescd, String postcd) {
		String sql = "select * from SYS_authority where rescd= ? and postcd=?";
		return dao.executeQuery(sql, new Object[] { rescd, postcd });
	}
	
	public Map<String,Object> setShareall(String loginName,String actualName,String rescd, String tbid,String shareall){
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select * from tb_app where tbid = " + tbid ;
		if(dao.executeQuery(sql).size()>0){
//			sql = "update tb_app set shareall ='"+ shareall+"' where tbid = " +tbid;
//			if(dao.executeSQL(sql)){
//				result.put("msg", "success");
//			}else{
//				result.put("msg", "保存失败");
//			}
			if(shareall.equals("true")){
				sql = "select * from  SYS_appshareall where tbid = " + tbid + " and rescd ='"+rescd+"'";
				if(dao.executeQuery(sql).size()>0){
					sql = "update SYS_appshareall set shareall ='true' where tbid = " + tbid + " and rescd ='"+rescd+"'";
					if(dao.executeSQL(sql)){
						result.put("msg", "success");
						new LogHelper().remark_auth(rescd, loginName, actualName,"设置模块是否全体可用"+" tbid:"+tbid+" shareall:"+shareall,"用户ip","用户所在地","操作成功");
					}else{
						result.put("msg", "保存失败");
						new LogHelper().remark_auth(rescd, loginName, actualName,"设置模块是否全体可用"+" tbid:"+tbid+" shareall:"+shareall,"用户ip","用户所在地","操作失败");
					}
				}else{
					sql = "insert into SYS_appshareall (rescd,tbid,shareall)values(?,?,?)";
					if(dao.executeSQL(sql,new Object[]{rescd,tbid,true})){
						result.put("msg", "success");
						new LogHelper().remark_auth(rescd, loginName, actualName,"设置模块是否全体可用"+" tbid:"+tbid+" shareall:"+shareall,"用户ip","用户所在地","操作成功");
					}else{
						result.put("msg", "保存失败");
						new LogHelper().remark_auth(rescd, loginName, actualName,"设置模块是否全体可用"+" tbid:"+tbid+" shareall:"+shareall,"用户ip","用户所在地","操作失败");
					}
				}
				
				sql = "delete SYS_authority where tbid = " + tbid + " and rescd ='" + rescd + "'";
				dao.executeSQL(sql);
			}else{
				sql = "select * from  SYS_appshareall where tbid = " + tbid + " and rescd ='"+rescd+"'";
				if(dao.executeQuery(sql).size()>0){
					sql = "delete SYS_appshareall where tbid = " + tbid + " and rescd ='"+rescd+"'";
					if(dao.executeSQL(sql)){
						result.put("msg", "success");
					}else{
						result.put("msg", "保存失败");
					}
				}
			}
		}
		return result;
	}
	
	public Map<String,Object> setOpenAPP(String loginName,String actualName,String rescd, String tbid,String show){
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select * from tb_app_open where rescd = '"+rescd+"' and  tbid = " + tbid ;
		if(dao.executeQuery(sql).size()>0){
			sql = "delete  tb_app_open where rescd = '"+rescd+"' and  tbid = " + tbid ;
			if(!dao.executeSQL(sql)){
				result.put("msg", "设置失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置模块是否开放"+" tbid:"+tbid+" show:"+show,"用户ip","用户所在地","操作失败");
				return result;
			}
		}
		sql = "select name from tb_app where tbid = " + tbid;
		Map<String,Object> map = dao.executeQueryObject(sql);
		sql = "insert tb_app_open (rescd,tbid,name,show) values (?,?,?,?)";
		if(dao.executeSQL(sql, new Object[]{rescd,tbid,map.get("name").toString(),show})){
			result.put("msg", "success");
			new LogHelper().remark_auth(rescd, loginName, actualName,"设置模块是否开放"+" tbid:"+tbid+" show:"+show,"用户ip","用户所在地","操作成功");
		}else{
			result.put("msg", "设置失败");
			new LogHelper().remark_auth(rescd, loginName, actualName,"设置模块是否开放"+" tbid:"+tbid+" show:"+show,"用户ip","用户所在地","操作失败");
		}
		return result;
	}
	public Map<String,Object> addAppPost(String loginName,String actualName,String rescd,String tbid,String postcd){
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select * from SYS_authority where rescd = '" + rescd + "' and postcd= '" + postcd + "' and tbid ="+tbid;
		if(dao.executeQuery(sql).size()>0){
			result.put("msg", "已存在");
		}else{
			sql ="insert SYS_authority (rescd,postcd,tbid)values(?,?,?)";
			if(dao.executeSQL(sql,new Object[]{rescd,postcd,tbid})){
				result.put("msg", "success");
				new LogHelper().remark_auth(rescd, loginName, actualName,"新增权限范围"+" tbid:"+tbid+" postcd:"+postcd,"用户ip","用户所在地","操作成功");
				//sql = "update tb_app set shareall ='false' where tbid = " +tbid;
				sql = "delete SYS_appshareall where tbid = " +tbid + " and rescd = '"+ rescd+"'";
				dao.executeSQL(sql);
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"新增权限范围"+" tbid:"+tbid+" postcd:"+postcd,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}

	public Map<String,Object> delAppPost(String loginName,String actualName,String rescd,String tbid,String postcd){
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select * from SYS_authority where rescd = '" + rescd + "' and postcd= '" + postcd + "' and tbid ="+tbid;
		if(dao.executeQuery(sql).size()>0){
			sql ="delete SYS_authority where rescd =? and postcd = ? and tbid = ?";
			if(dao.executeSQL(sql,new Object[]{rescd,postcd,tbid})){
				result.put("msg", "success");
				new LogHelper().remark_auth(rescd, loginName, actualName,"删除权限范围"+" tbid:"+tbid+" postcd:"+postcd,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "删除失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"删除权限范围"+" tbid:"+tbid+" postcd:"+postcd,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}
	
	public Map<String,Object> addAppChecker(String loginName,String actualName,String rescd,String tbid,String checkerpost){
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select * from SYS_appchecker where rescd = '" + rescd + "' and postcd= '" + checkerpost + "' and tbid ="+tbid;
		if(dao.executeQuery(sql).size()>0){
			result.put("msg", "已存在");
		}else{
			sql = "select * from tb_app where tbid = " + tbid;
			Map<String,Object> app = dao.executeQueryObject(sql);
			if(!app.get("needchecker").toString().equals("true")){
				result.put("msg", "该模块无需设置审核员");return result;
			}
			
			sql ="insert SYS_appchecker (rescd,postcd,tbid)values(?,?,?)";
			if(dao.executeSQL(sql,new Object[]{rescd,checkerpost,tbid})){
				result.put("msg", "success");
				new LogHelper().remark_auth(rescd, loginName, actualName,"新增应用模块审核员"+" tbid:"+tbid+" checkerpost:"+checkerpost,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"新增应用模块审核员"+" tbid:"+tbid+" checkerpost:"+checkerpost,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}

	public Map<String,Object> delAppChecker(String loginName,String actualName,String rescd,String tbid,String checkerpost){
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select * from SYS_appchecker where rescd = '" + rescd + "' and postcd= '" + checkerpost + "' and tbid ="+tbid;
		if(dao.executeQuery(sql).size()>0){
			sql = "select * from tb_app where tbid = " + tbid;
			Map<String,Object> app = dao.executeQueryObject(sql);
			if(!app.get("apptype").toString().contains("应用")){
				result.put("msg", "不是应用模块，无需设置审核员");return result;
			}
			
			sql ="delete SYS_appchecker where rescd =? and postcd = ? and tbid = ?";
			if(dao.executeSQL(sql,new Object[]{rescd,checkerpost,tbid})){
				result.put("msg", "success");
				new LogHelper().remark_auth(rescd, loginName, actualName,"删除应用模块审核员"+" tbid:"+tbid+" checkerpost:"+checkerpost,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "删除失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"删除应用模块审核员"+" tbid:"+tbid+" checkerpost:"+checkerpost,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}

	

	@Override
	public boolean isChecker(String userid, String tbidString,String rescd) {
		List<String> postlist = this.getPostCD(userid,rescd);
		String strpost = "";
		for(String post:postlist){
			strpost+=(post+",");
		}
		strpost = ("("+strpost+"'')");
		String sql = "select * from SYS_appchecker where rescd = '" + rescd + "' and postcd in " + strpost + " and tbid ="+tbidString;
		List<Map<String,Object>> list = dao.executeQuery(sql);
		if (list.size()>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isOnline(String userid, String rescd){
		boolean flag = false;
//		List<Map<String,Object>> onlineuser = new OnLineUser().getOnLineUsers();
//		for(Map<String,Object> on : onlineuser){
//			//在线用户和所有用户比较，
//			if(on.get("rescd").toString().equals(rescd) 
//					&& on.get("userid").toString().equals(userid) ){
//				flag = true;
//			}
//		}
		return flag;
	}
	/** ↓↓↓↓↓↓↓↓↓  服务端调用函数分割线，客户端不调用  **************************************************************************************************************************************************/
	
	/**
	 * 减少一条推送消息
	 * @param rescd
	 * @param appcode
	 * @param typeid
	 * @param userid
	 * @param postcd
	 * @param departmentcd
	 */
	public void minus(String rescd, String appcode, String typeid,
			String userid, String postcd, String departmentcd) {
		int _typeid = Integer.parseInt(typeid);
		String sql = "";
		switch (_typeid) {
		case 1:
			sql = "select * from oa_message where rescd = ? and appcode = ? and userid = ? and typeid = 1";
			if (dao.executeQuery(sql, new Object[] { rescd, appcode, userid })
					.size() > 0) {
				sql = "update oa_message set counts = counts - 1 where rescd = ? and appcode = ? and userid = ? and typeid = 1";
				dao.executeSQL(sql, new Object[] { rescd, appcode, userid });
			} else {

			}
			break;
		case 2:
			sql = "select * from oa_message where rescd = ? and appcode = ? and postcd = ? and typeid = 2";
			if (dao.executeQuery(sql, new Object[] { rescd, appcode, postcd })
					.size() > 0) {
				sql = "update oa_message set counts = counts - 1 where rescd = ? and appcode = ? and postcd = ? and typeid = 2";
				dao.executeSQL(sql, new Object[] { rescd, appcode, postcd });
			} else {

			}
			break;
		case 3:
			sql = "select * from oa_message where rescd = ? and appcode = ? and departmentcd = ? and typeid = 3";
			if (dao.executeQuery(sql,
					new Object[] { rescd, appcode, departmentcd }).size() > 0) {
				sql = "update oa_message set counts = counts - 1 where rescd = ? and appcode = ? and departmentcd = ? and typeid = 3";
				dao.executeSQL(sql,
						new Object[] { rescd, appcode, departmentcd });
			} else {

			}
			break;
		case 4:
			sql = "select * from oa_message where rescd = ? and appcode = ? and typeid = 4";
			if (dao.executeQuery(sql, new Object[] { rescd, appcode }).size() > 0) {
				sql = "update oa_message set counts = counts - 1 where rescd = ? and appcode = ? and typeid= 4";
				dao.executeSQL(sql, new Object[] { rescd, appcode });
			} else {

			}
			break;
		case 5:
			sql = "select * from oa_message where rescd = ? and appcode = ? and departmentcd = ? and typeid = 5";
			if (dao.executeQuery(sql,
					new Object[] { rescd, appcode, departmentcd }).size() > 0) {
				sql = "update oa_message set counts = counts - 1 where rescd = ? and appcode = ? and departmentcd = ?  and typeid= 5";
				dao.executeSQL(sql,
						new Object[] { rescd, appcode, departmentcd });
			} else {

			}
			break;
		case 6:
			sql = "select * from oa_message where rescd = ? and appcode = ? and departmentcd = ? and typeid = 6";
			if (dao.executeQuery(sql,
					new Object[] { rescd, appcode, departmentcd }).size() > 0) {
				sql = "update oa_message set counts = counts - 1 where rescd = ? and appcode = ? and departmentcd = ?  and typeid= 6";
				dao.executeSQL(sql,
						new Object[] { rescd, appcode, departmentcd });
			} else {

			}
			break;
		default:
			break;
		}
	}
	/**
	 * 增加一条推送消息
	 * @param rescd
	 * @param appcode
	 * @param typeid
	 * @param userid
	 * @param postcd
	 * @param departmentcd
	 */
	public void plus(String rescd, String appcode, String typeid,
			String userid, String postcd, String departmentcd) {
		int _typeid = Integer.parseInt(typeid);
		String sql = "";
		switch (_typeid) {
		case 1://人
			sql = "select * from oa_message where rescd = ? and appcode = ? and userid = ? and typeid = 1";
			if (dao.executeQuery(sql, new Object[] { rescd, appcode, userid })
					.size() > 0) {
				sql = "update oa_message set counts = counts + 1 where rescd = ? and appcode = ? and userid = ? and typeid = 1";
				dao.executeSQL(sql, new Object[] { rescd, appcode, userid });
			} else {
				sql = "insert oa_message (rescd,appcode,userid,counts,typeid) values (?,?,?,1,1)";
				dao.executeSQL(sql, new Object[] { rescd, appcode, userid });
			}
			break;
		case 2://岗位
			sql = "select * from oa_message where rescd = ? and appcode = ? and postcd = ? and typeid = 2";
			if (dao.executeQuery(sql, new Object[] { rescd, appcode, postcd })
					.size() > 0) {
				sql = "update oa_message set counts = counts + 1 where rescd = ? and appcode = ? and postcd = ? and typeid = 2";
				dao.executeSQL(sql, new Object[] { rescd, appcode, postcd });
			} else {
				sql = "insert oa_message (rescd,appcode,postcd,counts,typeid) values (?,?,?,1,2)";
				dao.executeSQL(sql, new Object[] { rescd, appcode, postcd });
			}
			break;
		case 3://部门
			sql = "select * from oa_message where rescd = ? and appcode = ? and departmentcd = ? and typeid = 3";
			if (dao.executeQuery(sql,
					new Object[] { rescd, appcode, departmentcd }).size() > 0) {
				sql = "update oa_message set counts = counts + 1 where rescd = ? and appcode = ? and departmentcd = ? and typeid = 3";
				dao.executeSQL(sql,
						new Object[] { rescd, appcode, departmentcd });
			} else {
				sql = "insert oa_message (rescd,appcode,departmentcd,counts,typeid) values (?,?,?,1,3)";
				dao.executeSQL(sql,
						new Object[] { rescd, appcode, departmentcd });
			}
			break;
		case 4://单位负责人
			sql = "select * from oa_message where rescd = ? and appcode = ? and typeid = 4";
			if (dao.executeQuery(sql, new Object[] { rescd, appcode }).size() > 0) {
				sql = "update oa_message set counts = counts + 1 where rescd = ? and appcode = ? and typeid = 4";
				dao.executeSQL(sql, new Object[] { rescd, appcode });
			} else {
				sql = "insert oa_message (rescd,appcode,counts,typeid) values (?,?,1,4)";
				dao.executeSQL(sql, new Object[] { rescd, appcode });
			}
			break;
		case 5://科室分管领导
			sql = "select * from oa_message where rescd = ? and appcode = ? and departmentcd = ? and typeid = 5";
			if (dao.executeQuery(sql,
					new Object[] { rescd, appcode, departmentcd }).size() > 0) {
				sql = "update oa_message set counts = counts + 1 where rescd = ? and appcode = ? and departmentcd = ? and typeid = 5";
				dao.executeSQL(sql,
						new Object[] { rescd, appcode, departmentcd });
			} else {
				sql = "insert oa_message (rescd,appcode,departmentcd,counts,typeid) values (?,?,?,1,5)";
				dao.executeSQL(sql,
						new Object[] { rescd, appcode, departmentcd });
			}
			break;
		case 6://科室负责人
			sql = "select * from oa_message where rescd = ? and appcode = ? and departmentcd = ? and typeid = 6";
			if (dao.executeQuery(sql,
					new Object[] { rescd, appcode, departmentcd }).size() > 0) {
				sql = "update oa_message set counts = counts + 1 where rescd = ? and appcode = ? and departmentcd = ? and typeid = 6";
				dao.executeSQL(sql,
						new Object[] { rescd, appcode, departmentcd });
			} else {
				sql = "insert oa_message (rescd,appcode,departmentcd,counts,typeid) values (?,?,?,1,6)";
				dao.executeSQL(sql,
						new Object[] { rescd, appcode, departmentcd });
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 设置局班子单位
	 * 
	 * @param rescd
	 * @param departmentcd
	 */
	public void setunit(String rescd, String departmentcd) {
		String sql = "select * from SYS_Department where RESCD='" + rescd
				+ "' and DepartmentCD='" + departmentcd + "'";
		if (dao.executeQuery(sql).size() > 0) {
			sql = "update SYS_Department set isleader = 'false' where RESCD = '"
					+ rescd
					+ "' "
					+ " update SYS_Department set isleader = 'true' where RESCD = '"
					+ rescd + "' and DepartmentCD = '" + departmentcd + "'";
			dao.executeSQL(sql);
		}
	}

	/**
	 * 检查是否是单位负责人
	 * 
	 * @param rescd
	 * @param userid
	 * @return
	 */
	public boolean checkunitleader(String rescd, String userid) {
		String sql = "select * from SYS_Reservoir where RESCD= '" + rescd
				+ "' and Inchargeid = " + userid;
		List<Map<String, Object>> ok = dao.executeQuery(sql);
		if (ok.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 获取自己是科室负责人的科室列表
	 * 
	 * @param rescd
	 * @param userid
	 * @return
	 */
	public List<Map<String, Object>> getdepk(String rescd, String userid) {
		String sql = "select *,DepartmentCD as departmentcd, DName as departmentnm, RESCD as rescd from SYS_Department where RESCD ='" + rescd
				+ "'  and leader_k = " + userid;
		return dao.executeQuery(sql);
	}
	
	public Map<String, Object> getdepk2(String rescd, String departmentcd) {
		String sql = "select b.id as userid, b.name as username  from SYS_Department a left join o_user b on a.RESCD = b.rescd and a.leader_k = b.id " +
				 " where a.RESCD = '" + rescd + "' and a.DepartmentCD = '" + departmentcd + "'";
		return dao.executeQueryObject(sql);
	}

	/**
	 * 获取自己是科室分管领导的科室列表
	 * 
	 * @param rescd
	 * @param userid
	 * @return
	 */
	public List<Map<String, Object>> getdepf(String rescd, String userid) {
		String sql = "select *, DepartmentCD as departmentcd, DName as departmentnm, RESCD as rescd from SYS_Department where RESCD ='" + rescd
				+ "'  and leader_f = " + userid;
		return dao.executeQuery(sql);
	}
	
	public Map<String, Object> getdepf2(String rescd, String departmentcd) {
		String sql = "select b.id as userid, b.name as username  from SYS_Department a left join o_user b on a.RESCD = b.rescd and a.leader_f = b.id " +
				 " where a.RESCD = '" + rescd + "' and a.DepartmentCD = '" + departmentcd + "'";
		return dao.executeQueryObject(sql);
	}

	public boolean chechexit(String tbnm, String keyword, String value) {
		String sql = "select * from " + tbnm + " where " + keyword + " = ?";
		if (dao.executeQuery(sql, new Object[] { value }).size() > 0)
			return true;
		else
			return false;
	}

	public boolean chechexit2(String tbnm, String keyword1, String value1,
			String keyword2, String value2) {
		String sql = "select * from " + tbnm + " where " + keyword1
				+ " = ? and " + keyword2 + " = ?";
		if (dao.executeQuery(sql, new Object[] { value1, value2 }).size() > 0)
			return true;
		else
			return false;
	}
	public List<Map<String, Object>> getUsersByDepartmentCD2(String rescd) {
		String sql = "select name,departmentcd,postcd,otherpostcd,otherdepartmentcd from o_user where rescd= '"+rescd+"'";
		return dao.executeQuery(sql);
	}
	/**
	 * 获取departmentcd中所有的人员，例如：departmentcd="(001)(002)"，返回result="张三,李四,王五,"
	 * @param departmentcd 
	 * @return
	 */
	public String getUsersByDepartmentCD(String departmentcd,String rescd) {
		String result = "";
		if (departmentcd.length() >= 2) {
			String sql = "";
			String _dep = departmentcd.replace(")(", ",").replace("(", "")
					.replace(")", "");
			String[] depsArray = _dep.toString().split("\\,");
			List<Map<String, Object>> list =  new ArrayList<Map<String,Object>>();
			for (int i = 0; i < depsArray.length; i++) {
				if (!depsArray[i].trim().equals("")) {
					sql = "select name from o_user where (departmentcd ='" + depsArray[i] + 
							"' or otherdepartmentcd like '%("+depsArray[i]+")%') " +
							" and rescd= '"+rescd+"'";
					list.addAll(dao.executeQuery(sql));
				}
			}
			for (Map<String, Object> l : list) {
				result += l.get("name") + ",";
			}
		}
		return result;
	}
	/**
	 * 获取postcd所有的人员，例如：postcd="(001)(002)(003)(004)"，返回result="张三,李四,王五,"
	 * @param postcd
	 * @return
	 */
	public String getUsersByPostcd(String postcd,String rescd) {
		String result = "";
		if (postcd.length() >= 2) {
			String sql = "";
			String _post = postcd.replace(")(", ",").replace("(", "")
					.replace(")", "");
			String[] depsArray = _post.toString().split("\\,");
			List<Map<String, Object>> list =  new ArrayList<Map<String,Object>>();
			for (int i = 0; i < depsArray.length; i++) {
				if (!depsArray[i].trim().equals("")) {
					sql = "select name from o_user where postcd = '" + depsArray[i] + "' or otherpostcd like '%("+depsArray[i]+")%'";
					list.addAll(dao.executeQuery(sql));
				}
			}
			for (Map<String, Object> l : list) {
				result += l.get("name") + ",";
			}
		}
		return result;
	}
	/**
	 * 根据userid获取他的登录mid
	 * @param userid
	 * @return
	 */
	public String getMidByUserid(String userid,String rescd) {
		String sql = "select tbid from tb_member where userid = '" + userid + "' and rescd='"+rescd+"'";
		Map<String, Object> user = dao.executeQueryObject(sql);
		if (user != null) {
			return user.get("tbid").toString();
		} else {
			return "";
		}
	}
	
	public void delMyApp(String id, String type, String mid) {
		if ("app".equals(type) || "widget".equals(type)) {
			String sql = "select dock,desk1,desk2,desk3,desk4,desk5 from tb_member where tbid=?";
			Map<String, Object> map = dao.executeQueryObject(sql,
					new Object[] { mid });
			for (String key : map.keySet()) {
				updateTbMember(key,
						delIcon(map.get(key).toString(), type + "_" + id), mid);
			}
			String sql2 = "select content,tbid from tb_folder where content<>'' and member_id='"
					+ mid + "'";
			List<Map<String, Object>> list = dao.executeQuery(sql2);
			for (Map<String, Object> fMap : list) {
				updateTbFolder(
						"content",
						delIcon(fMap.get("content").toString(), type + "_" + id),
						fMap.get("tbid").toString(), mid);
			}
			String sql3 = "update tb_app set usecount=usecount-1 where tbid = '"
					+ id + "'";
			dao.executeSQL(sql3);
		} else if ("folder".equals(type)) {
			// 删除文件夹内的
			String sql = "select content from tb_folder where tbid='" + id
					+ "' and member_id='" + mid + "'";
			Map<String, Object> map = dao.executeQueryObject(sql);
			String ficons = map.get("content").toString();
			if (ficons.length() > 0) {
				String[] ficonArr = ficons.split(",");
				for (String ficon : ficonArr) {
					dao.executeSQL("update tb_app set usecount=usecount-1 where tbid = '"
							+ ficon.split("_")[1] + "'");
				}
			}
			// 删除文件夹
			String sql2 = "select dock,desk1,desk2,desk3,desk4,desk5 from tb_member where tbid=?";
			Map<String, Object> map2 = dao.executeQueryObject(sql2,
					new Object[] { mid });
			for (String key : map2.keySet()) {
				updateTbMember(key,
						delIcon(map2.get(key).toString(), type + "_" + id), mid);
			}
			String sql3 = "delete from tb_folder where tbid=? and member_id=?";
			dao.executeSQL(sql3, new Object[] { id, mid });
		} else { // papp pwidget
			String sql = "select dock,desk1,desk2,desk3,desk4,desk5 from tb_member where tbid=?";
			Map<String, Object> map = dao.executeQueryObject(sql,
					new Object[] { mid });
			for (String key : map.keySet()) {
				updateTbMember(key,
						delIcon(map.get(key).toString(), type + "_" + id), mid);
			}
			String sql2 = "select content,tbid from tb_folder where content<>'' and member_id='"
					+ mid + "'";
			List<Map<String, Object>> list = dao.executeQuery(sql2);
			for (Map<String, Object> fMap : list) {
				updateTbFolder(
						"content",
						delIcon(fMap.get("content").toString(), type + "_" + id),
						fMap.get("tbid").toString(), mid);
			}
			String sql3 = "delete from tb_papp where tbid = '" + id
					+ "' and member_id='" + mid + "'";
			dao.executeSQL(sql3);
		}
	}

	/**
	 * 更新字段
	 * 
	 * @param colum
	 * @param value
	 * @return void
	 * @since v 1.0
	 */
	private void updateTbMember(String colum, String value, String mid) {
		String sql = "update tb_member set " + colum + "='" + value
				+ "' where tbid='" + mid + "'";
		dao.executeSQL(sql);
	}

	/**
	 * 更新字段
	 * 
	 * @param colum
	 * @param value
	 * @return void
	 * @since v 1.0
	 */
	private void updateTbFolder(String colum, String value, String fid,
			String mid) {
		String sql = "update tb_folder set " + colum + "='" + value
				+ "' where member_id='" + mid + "' and tbid='" + fid + "'";
		dao.executeSQL(sql);
	}

	/**
	 * 字符串中删除应用
	 * 
	 * @param icons
	 * @param icon
	 * @return String 返回空，则没有改应用，无需update
	 * @since v 1.0
	 */
	private String delIcon(String icons, String icon) {
		if (icons.length() > 0) {
			String[] iconArr = icons.split(",");
			String rtStr = "";
			for (String myIcon : iconArr) {
				if (myIcon.equals(icon))
					continue;
				rtStr += "," + myIcon;
			}
			if (icons.length() == rtStr.length())
				return "";
			else {
				if (rtStr.length() > 0)
					return rtStr.substring(1);
				else
					return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * 获取userid所有部门编码，返回List<String>
	 * @param userid
	 * @return
	 */
	public List<String> getDepartmentCD(String userid,String rescd){
		List<String> result = new ArrayList<String>();
		//获取部门信息=departmentcd+otherdepartmentcd
		String sqluser = "select departmentcd, otherdepartmentcd from o_user where id = " + userid +" and rescd = '" +rescd+"'";
		Map<String, Object> userinfo = dao.executeQueryObject(sqluser);
		if(userinfo!=null){
			if(userinfo.get("departmentcd")!=null){
				result.add(userinfo.get("departmentcd").toString());
			}
			if(userinfo.get("otherdepartmentcd")!=null){
				if(userinfo.get("otherdepartmentcd").toString().length()>0){
					String _s = userinfo.get("otherdepartmentcd").toString().replace(")(", ",").replace("(", "").replace(")", "");
					String[] otherdepartmentcdArr = _s.split(",");
					for(int i=0;i<otherdepartmentcdArr.length;i++){
						result.add(otherdepartmentcdArr[i]);
					}
				}
			}
		}
		return result;
	}
	/**
	 * 获取userid所有岗位编码，返回List<String>
	 * @param userid
	 * @return
	 */
	public List<String> getPostCD(String userid,String rescd){
		List<String> result = new ArrayList<String>();
		//获取部门信息=postcd+otherpostcd
		String sqluser = "select postcd, otherpostcd from o_user where id = " + userid +" and rescd = '" +rescd+"'";
		Map<String, Object> userinfo = dao.executeQueryObject(sqluser);
		if(userinfo!=null){
			if(userinfo.get("postcd")!=null){
				result.add(userinfo.get("postcd").toString());
			}
			if(userinfo.get("otherpostcd")!=null){
				if(userinfo.get("otherpostcd").toString().length()>0){
					String _s = userinfo.get("otherpostcd").toString().replace(")(", ",").replace("(", "").replace(")", "");
					String[] otherotherpostcdArr = _s.split(",");
					for(int i=0;i<otherotherpostcdArr.length;i++){
						result.add(otherotherpostcdArr[i]);
					}
				}
			}
		}
		return result;
	}
	public String getPostCDStr(String userid,String rescd){
		String r = "";
		List<String> result = new ArrayList<String>();
		//获取部门信息=postcd+otherpostcd
		String sqluser = "select postcd, otherpostcd from o_user where id = " + userid +" and rescd = '" +rescd+"'";
		Map<String, Object> userinfo = dao.executeQueryObject(sqluser);
		if(userinfo!=null){
			if(userinfo.get("postcd")!=null){
				r += "("+userinfo.get("postcd").toString()+")";
			}
			if(userinfo.get("otherpostcd")!=null){
				r += "("+userinfo.get("otherpostcd").toString()+")";
			}
		}
		return r;
	}
	
	/**
	 * 增加名字
	 * @param all
	 * @param addone
	 * @return
	 */
	public String add11(String othercds, String othercd, String all, String addone){
		String result = all;
		if(!othercds.contains("("+othercd+"")){
			if(all.length()>0)
				result += (","+addone);
			else
				result += addone;
		}
		return result;
	}
	/**
	 * 增加编码
	 * @param all
	 * @param addone
	 * @return
	 */
	public String add22(String othercds, String othercd, String all, String addone){
		String result = all;
		if(!othercds.contains("("+othercd+"")){
			result += ("("+addone+")");
		}
		return result;
	}
	
	/**
	 * 移除名字
	 * @param othercds
	 * @param othercd
	 * @param names
	 * @return
	 */
	public String remove11(String othercds, String othercd, String names){
		String result = names;
		if(othercds.length()>0){
			result = "";
			String _otherpostcds = othercds.replace(")(",",").replace("(", "").replace(")", "");
			String[] array = _otherpostcds.toString().split("\\,");//
			int position = -1;
			for (int i = 0; i < array.length; i++) {
				if(array[i].equals(othercd)){
					position = i;break;
				}
			}
			if(position!=-1){
				String[] array2 = names.split("\\,");
				for (int i = 0; i < array2.length; i++) {
					if(i!=position){
						result += (array2[i]+",");
					}
				}
				if (result.length() > 0 ) result = result.substring(0, result.length()-1);
			}
		}
		return result;
	}
	/**
	 * 移除编码
	 * @param othercds
	 * @param othercd
	 * @param cds
	 * @return
	 */
	public String remove22(String othercds, String othercd, String cds){
		String result = cds;
		if(othercds.length()>0){
			result = "";
			String _otherpostcds = othercds.replace(")(",",").replace("(", "").replace(")", "");
			String[] array = _otherpostcds.toString().split("\\,");//
			int position = -1;
			for (int i = 0; i < array.length; i++) {
				if(array[i].equals(othercd)){
					position = i;break;
				}
			}
			if(position!=-1){
				String _cds = cds.replace(")(",",").replace("(", "").replace(")", "");
				String[] array2 = _cds.split("\\,");
				for (int i = 0; i < array2.length; i++) {
					if(i!=position){
						result += ("("+array2[i]+")");
					}
				}
			}
		}
		return result;
	}
	

	public static String getToday(String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**↑↑↑↑↑↑↑ 服务端调用函数分割线，客户端不调用*************************************************************/
	
	

	//**↓↓↓↓↓↓↓暂时作废代码***********************************************************//

	public Map<String,Object> saveDDP(String rescd, String userid,  String username, 
			String departmentcd, String postcd, String otherdepartmentcd, String otherdepartmentnm,
			String otherpostcd,String otherpostnm, 
			String option) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "";
		if (option.equals("add")) {
			userid = this.getKV("userid",rescd).get("v").toString();
			sql = " insert into o_user (id,name,rescd,departmentcd,postcd,otherdepartmentcd,otherdepartmentnm,otherpostcd,otherpostnm) values (?,?,?,?,?,?,?,?,?)";
			if (dao.executeSQL(sql, new Object[] { userid, username, rescd,
					departmentcd, postcd, otherdepartmentcd,otherdepartmentnm,otherpostcd,otherpostnm//, 
				})) {
				result.put("msg", "保存成功");
				result.put("userid", userid);
			} else {
				result.put("msg", "保存失败");
			}
			return result;
		} else if (option.equals("edit")) {
			//修改用户
			sql = "update o_user set name = '" + username + "', departmentcd='"
					+ departmentcd + "', postcd='" + postcd + "',otherdepartmentcd='"
					+ otherdepartmentcd + "',otherdepartmentnm='"
					+ otherdepartmentnm + "',otherpostcd='"
					+ otherpostcd + "',otherpostnm='"
					+ otherpostnm + "' where id='" + userid + "' and rescd ='"
					+ rescd + "'";
			if (dao.executeSQL(sql)) {
				result.put("msg", "保存成功");
				result.put("userid", userid);
			} else {
				result.put("msg", "保存失败");
			}
		}
		return result;
	}
	
	public Map<String, Object> savePostApp(String loginName,String actualName,String rescd, String postcd,
			String tbids) {
		// 先修改[SYS_authority]表
		String sql1 = " delete SYS_authority where rescd = '" + rescd
				+ "' and postcd ='" + postcd + "'";
		String[] tbidArray = tbids.toString().split("\\,");// 新的有权限模块
		String tbidall="";
		for (int i = 0; i < tbidArray.length; i++) {
			if (!tbidArray[i].trim().equals("")) {
				sql1 += " insert into SYS_authority (rescd,postcd,tbid) values('"
						+ rescd + "','" + postcd + "','" + tbidArray[i] + "') ";
				tbidall+=tbidArray[i]+" ";
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if (dao.executeSQL(sql1)) {
			result.put("msg", "保存成功");
			new LogHelper().remark_auth(rescd, loginName, actualName,"保存岗位有权限的模块"+" postcd:"+postcd+" tbid:"+tbidall,"用户ip","用户所在地","操作成功");
		} else {
			result.put("msg", "保存失败");
			new LogHelper().remark_auth(rescd, loginName, actualName,"保存岗位有权限的模块"+" postcd:"+postcd+" tbid:"+tbidall,"用户ip","用户所在地","操作失败");
		}

		// 再找到postcd岗位对应的人的列表
		String sql2 = "select b.tbid from tb_member b " +
				" left join o_user a on a.id = b.userid and a.rescd = b.rescd" +
				" where a.postcd ='"
				+ postcd + "'";
		List<Map<String, Object>> userlist = dao.executeQuery(sql2);
		List<Map<String, Object>> delList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> user : userlist) {
			// 通过遍历，修改[tb_member]这张表：dock，desk1，desk2，desk3，desk4，desk5六个字段
			String strsql = "select dock,desk1,desk2,desk3,desk4,desk5 from tb_member where tbid = "
					+ user.get("tbid").toString();
			Map<String, Object> app = dao.executeQueryObject(strsql);
			// 原有的 有权限模块
			List<Map<String, Object>> applist = new ArrayList<Map<String, Object>>();
			applist.addAll(this.getdeskapp(app.get("dock").toString()));
			applist.addAll(this.getdeskapp(app.get("desk1").toString()));
			applist.addAll(this.getdeskapp(app.get("desk2").toString()));
			applist.addAll(this.getdeskapp(app.get("desk3").toString()));
			applist.addAll(this.getdeskapp(app.get("desk4").toString()));
			applist.addAll(this.getdeskapp(app.get("desk5").toString()));
			applist.addAll(this.getfolderapp(user.get("tbid").toString()));
			// 对比新的和原有的 有权限模块，
			for (Map<String, Object> ap : applist) {// 原来有的权限
				boolean flag = true;
				for (int i = 0; i < tbidArray.length; i++) {// 新的权限
					if (ap.get("tbid").toString().equals(tbidArray[i])) {
						flag = false;
						break;
					}
				}
				if (flag) {
					ap.put("mid", user.get("tbid"));
					delList.add(ap);
				}
			}
		}
		// 开始删除 撤销的权限模块
		for (Map<String, Object> ap : delList) {
			this.delMyApp(ap.get("tbid").toString(), ap.get("type").toString(),
					ap.get("mid").toString());
		}
		return result;
	}
	
	/**
	 * 将桌面应用字符串组合成list返回
	 * @param strapp: 如"app_55,app_39,app_53,app_47,app_95,app_96,app_91"
	 * @return 如："[{tbid=67, type=app}, {tbid=68, type=app}, {tbid=69, type=app}]"
	 */
	public List<Map<String, Object>> getdeskapp(String strapp) {
		if (strapp.length() == 0) {
			return new ArrayList<Map<String,Object>>();
		}
		List<Map<String, Object>> applist = new ArrayList<Map<String, Object>>();
		String[] appArray = strapp.split("\\,");
		for (int i = 0; i < appArray.length; i++) {
			String[] cobapp = appArray[i].split("\\_");
			Map<String, Object> app = new HashMap<String, Object>();
			if(!cobapp[0].equals("folder")){
				app.put("type", cobapp[0]);
				app.put("tbid", cobapp[1]);
				applist.add(app);
			}
		}
		return applist;
	}
	
	public List<Map<String, Object>> getfolderapp(String mid) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		String sql = "select content from tb_folder where member_id = '" + mid + "'";
		List<Map<String, Object>> folderlist = dao.executeQuery(sql);
		for(Map<String, Object> folder : folderlist){
			if(folder.get("content")!=null){
				result.addAll(this.getdeskapp(folder.get("content").toString()));
			}
		}
		return result;
	}

	public Map<String, Object> addPostApp(String loginName,String actualName,String rescd, String postcds,
			String tbids) {
		String sql = " ";
		String tbidall="";
		String postcdall="";
		String[] tbidArray = tbids.toString().split("\\,");
		String[] postcdArray = postcds.toString().split("\\,");
		for (int i = 0; i < tbidArray.length; i++) {
			for (int j = 0; j < postcdArray.length; j++) {
				if (!tbidArray[i].trim().equals("")
						&& !postcdArray[j].trim().equals("")) {
					sql += " insert into SYS_authority (rescd,postcd,tbid) values('"
							+ rescd
							+ "','"
							+ postcdArray[j]
							+ "','"
							+ tbidArray[i] + "') ";
					tbidall +=tbidArray[i];
					postcdall+=postcdArray[j];
				}
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if (dao.executeSQL(sql)) {
			result.put("msg", "保存成功");
			new LogHelper().remark_auth(rescd, loginName, actualName,"批量增加权限"+" postcds:"+postcdall+" tbids:"+tbidall,"用户ip","用户所在地","操作成功");
		} else {
			result.put("msg", "保存失败");
			new LogHelper().remark_auth(rescd, loginName, actualName,"批量增加权限"+" postcds:"+postcdall+" tbids:"+tbidall,"用户ip","用户所在地","操作失败");
		}
		return result;
	}

	public Map<String, Object> delPostApp(String loginName,String actualName,String rescd, String postcds,
			String tbids) {
		String sql = " ";
		String[] tbidArray = tbids.toString().split("\\,");
		String[] postcdArray = postcds.toString().split("\\,");
		String tbidall = "";
		String postcdall = "";
		for (int i = 0; i < tbidArray.length; i++) {
			for (int j = 0; j < postcdArray.length; j++) {
				if (!tbidArray[i].trim().equals("")
						&& !postcdArray[j].trim().equals("")) {
					sql += " delete SYS_authority where rescd = '" + rescd
							+ "' and postcd = '" + postcdArray[j]
							+ "' and tbid = " + tbidArray[i];
					tbidall = tbidArray[i];
					postcdall = postcdArray[j];
				}
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if (dao.executeSQL(sql)) {
			result.put("msg", "保存成功");
			new LogHelper().remark_auth(rescd, loginName, actualName,"批量删除权限"+" postcds:"+postcdall+" tbids:"+tbidall,"用户ip","用户所在地","操作成功");
		} else {
			result.put("msg", "保存失败");
			new LogHelper().remark_auth(rescd, loginName, actualName,"批量删除权限"+" postcds:"+postcdall+" tbids:"+tbidall,"用户ip","用户所在地","操作失败");
		}
		return result;
	}

	public Map<String, Object> getUserByUserID(String userid, String rescd) {
		String sql = "select a.id as userid, a.name as username,a.*," +
				" b.DName as departmentnm,  "
				+ " c.postname, " 
				+ " e.tbid, e.username as loginname "
				+ " from o_user a "
				+ " left join SYS_Department b on a.DepartmentCD = b.departmentcd and a.rescd = b.RESCD "
				+ " left join SYS_Post c on a.postcd = c.postcd  and a.rescd=c.RESCD "
				+ " left join tb_member e on a.id = e.userid   and a.rescd=e.rescd "
				+ " where a.id = "
				+ userid
				+ " and a.rescd = '"+ rescd + "'";
		return dao.executeQueryObject(sql);
	}	

	public List<Map<String, Object>> getUsersByUserIDs(String userids, String rescd) {
		String sql = "select id, name from o_user where rescd = '"
				+ rescd + "' and id in (" + userids + ")";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		return list;
	}	
	public Map<String, Object> getDepartment2(String rescd) {
		List<Map<String, Object>> list = this.getDepartment(rescd);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		return result;
	}
	public Map<String, Object> setleaderdep(String loginName,String actualName,String departmentcd, String rescd) {
		String sql = "update SYS_Department set isleader = 'false' where RESCD = '"
				+ rescd + "'";
		sql += " update SYS_Department set isleader = 'true' where RESCD = '"
				+ rescd + "' and DepartmentCD = '" + departmentcd + "'";
		dao.executeSQL(sql);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "设置成功");
		new LogHelper().remark_auth(rescd, loginName, actualName,"设置哪个机构为局班子"+" departmentcd:"+departmentcd,"用户ip","用户所在地","操作成功");
		return result;
	}

	public String checkauthority(String tbid,String userid,String rescd){
		Map<String,Object> user = this.getUserByUserID(userid, rescd);
		if(user!=null){
			String postcd = user.get("postcd").toString();
			String otherpostcd = user.get("otherpostcd").toString()+"("+postcd+")";
			String sql = "select * from SYS_appchecker where rescd = '" +rescd + "' and tbid = '"+tbid+"'";
			List<Map<String,Object>> lst = dao.executeQuery(sql);
			for(Map<String,Object> l : lst ){
				String p = "("+l.get("postcd").toString()+")";
				if(otherpostcd.contains(p)){
					return "true";
				}
			}
		}else{
			return "false";
		}
		return "false";
	}
	
	public String viewauthority(String tbid,String userid,String rescd){
		Map<String,Object> user = this.getUserByUserID(userid, rescd);
		if(user!=null){
			String postcd = user.get("postcd").toString();
			String otherpostcd = user.get("otherpostcd").toString()+"("+postcd+")";
			String sql = "select * from SYS_authority where rescd = '" +rescd + "' and tbid = '"+tbid+"'";
			List<Map<String,Object>> lst = dao.executeQuery(sql);
			for(Map<String,Object> l : lst ){
				String p = "("+l.get("postcd").toString()+")";
				if(otherpostcd.contains(p)){
					return "true";
				}
			}
		}else{
			return "false";
		}
		return "false";
	}

	public String editauthority(String tbid,String userid,String rescd){
		Map<String,Object> user = this.getUserByUserID(userid, rescd);
		if(user!=null){
			String postcd = user.get("postcd").toString();
			String otherpostcd = user.get("otherpostcd").toString()+"("+postcd+")";
			String sql = "select * from SYS_authority where rescd = '" +rescd + "' and tbid = '"+tbid+"'";
			List<Map<String,Object>> lst = dao.executeQuery(sql);
			for(Map<String,Object> l : lst ){
				String p = "("+l.get("postcd").toString()+")";
				if(otherpostcd.contains(p)){
					return "true";
				}
			}
		}else{
			return "false";
		}
		return "false";
	}

	public String checkauthority_appcode(String appcode,String userid,String rescd){
		String sql = "select tbid from tb_app where appcode = ?";
		Map<String, Object> map = dao.executeQueryObject(sql, new Object[]{appcode});
		if(map==null){
			return "false";
		}
		String tbid = map.get("tbid").toString();
		
		Map<String,Object> user = this.getUserByUserID(userid, rescd);
		if(user!=null){
			String postcd = user.get("postcd").toString();
			String otherpostcd = user.get("otherpostcd").toString()+"("+postcd+")";
			sql = "select * from SYS_appchecker where rescd = '" +rescd + "' and tbid = '"+tbid+"'";
			List<Map<String,Object>> lst = dao.executeQuery(sql);
			for(Map<String,Object> l : lst ){
				String p = "("+l.get("postcd").toString()+")";
				if(otherpostcd.contains(p)){
					return "true";
				}
			}
		}else{
			return "false";
		}
		return "false";
	}
	
	public String viewauthority_appcode(String appcode,String userid,String rescd){
		String sql = "select tbid from tb_app where appcode = ?";
		Map<String, Object> map = dao.executeQueryObject(sql, new Object[]{appcode});
		if(map==null){
			return "false";
		}
		String tbid = map.get("tbid").toString();
		
		Map<String,Object> user = this.getUserByUserID(userid, rescd);
		if(user!=null){
			String postcd = user.get("postcd").toString();
			String otherpostcd = user.get("otherpostcd").toString()+"("+postcd+")";
			sql = "select * from SYS_authority where rescd = '" +rescd + "' and tbid = '"+tbid+"'";
			List<Map<String,Object>> lst = dao.executeQuery(sql);
			for(Map<String,Object> l : lst ){
				String p = "("+l.get("postcd").toString()+")";
				if(otherpostcd.contains(p)){
					return "true";
				}
			}
		}else{
			return "false";
		}
		return "false";
	}

	public String editauthority_appcode(String appcode,String userid,String rescd){
		String sql = "select tbid from tb_app where appcode = ?";
		Map<String, Object> map = dao.executeQueryObject(sql, new Object[]{appcode});
		if(map==null){
			return "false";
		}
		String tbid = map.get("tbid").toString();
		
		Map<String,Object> user = this.getUserByUserID(userid, rescd);
		if(user!=null){
			String postcd = user.get("postcd").toString();
			String otherpostcd = user.get("otherpostcd").toString()+"("+postcd+")";
			sql = "select * from SYS_authority where rescd = '" +rescd + "' and tbid = '"+tbid+"'";
			List<Map<String,Object>> lst = dao.executeQuery(sql);
			for(Map<String,Object> l : lst ){
				String p = "("+l.get("postcd").toString()+")";
				if(otherpostcd.contains(p)){
					return "true";
				}
			}
		}else{
			return "false";
		}
		return "false";
	}
	
	public void importPeople(List<Map<String,Object>> list){
		String sql = "insert into o_user ("
				+ " id, rescd, name, sex, birthday, "
				+ " nation, native, politics, partyday, card, "
				+ " phone, HomePhone, mobile, email, otherContacts, "
				+ " departmentcd, postcd, dutycd, AdminDutyCd, TechDutyCd, "
				+ " TechPostCd, rank, OnGuard, workday, workdaynew, "
				+ " path, sheetname, otherdepartmentcd, otherdepartmentnm, otherpostcd, "
				+ " otherpostnm, islocked, orderby) "
				+ " values ("
				+ " ?,?,?,?,?,"
				+ " ?,?,?,?,?,"
				+ " ?,?,?,?,?,"
				+ " ?,?,?,?,?,"
				+ " ?,?,?,?,?,"
				+ " ?,?,?,?,?,"
				+ " ?,?,?)";
		for (Map<String, Object> map : list) {
			String userid = this.getKV("userid", map.get("rescd").toString()).get("v").toString();
			if(dao.executeSQL(sql, new Object[] {
					userid, map.get("rescd"), map.get("name"), map.get("sex"), map.get("birthday"), 
					map.get("nation"), map.get("native"), map.get("politics"), map.get("partyday"), map.get("card"), 
					map.get("phone"), map.get("HomePhone"), map.get("mobile"), map.get("email"), map.get("otherContacts"),
					map.get("departmentcd"), map.get("postcd"), map.get("dutycd"), map.get("AdminDutyCd"), map.get("TechDutyCd"), 
					map.get("TechPostCd"), map.get("rank"), map.get("OnGuard"), map.get("workday"), map.get("workdaynew"), 
					map.get("path"), map.get("sheetname"), map.get("otherdepartmentcd"), map.get("otherdepartmentnm"), map.get("otherpostcd"),
					map.get("otherpostnm"), map.get("islocked"), map.get("orderby")
				})){
				String sql2 = "insert into office_StudyInfo("
						+ " UserId, Starttime, EndTime, School, Department, Degree "
						+ ") values ("
						+ "?,?,?,?,?,?"
						+ ")";
				dao.executeSQL(sql2, new Object[] {
						userid, map.get("StartTime"), map.get("EndTime"), map.get("School"), map.get("Department") , map.get("Degree")
					});
			}
		}
	}
	
	//根据行政部门的名称获取行政部门的编码，若不存在，则自动创建，返回行政部门编码
	public String getdepCDbyNM1(String rescd, String depnm1){
		if(depnm1.isEmpty()){
			return "";
		}
		String sql = "select * from SYS_Department where RESCD = ? and DName = ? and isadministration = ?";
		Map<String,Object> map = dao.executeQueryObject(sql, new Object[]{rescd, depnm1, "true"});
		if(map!=null){
			return map.get("DepartmentCD").toString();
		}else{
			 UUID uuid = UUID.randomUUID();
			 String DepartmentCD = uuid.toString();
			 sql = "insert into SYS_Department ("
			 		+ "RESCD,DepartmentCD,DName,isleader,isadministration,orderby"
			 		+ ") values("
			 		+ "?,?,?,?,?,?)";
			 dao.executeSQL(sql,new Object[]{rescd, DepartmentCD, depnm1, "false","true","999"});
			 return DepartmentCD;
		}
	}
	//根据其他权限部门的名称获取其他权限部门的编码，若不存在，则自动创建，返回其他权限部门编码
	public String getdepCDbyNM2(String rescd, String depnm2){
		if(depnm2.isEmpty()){
			return "";
		}
		String sql = "";
		String result = "";
		String[] depsArray = depnm2.toString().split("\\,");
		List<Map<String, Object>> list =  new ArrayList<Map<String,Object>>();
		for (int i = 0; i < depsArray.length; i++) {
			sql = "select * from SYS_Department where RESCD = ? and DName = ? and isadministration = ?";
			Map<String,Object> map = dao.executeQueryObject(sql, new Object[]{rescd, depsArray[i].trim(), "false"});
			if(map!=null){
				result += ("("+map.get("DepartmentCD").toString()+")");
			}else{
				 UUID uuid = UUID.randomUUID();
				 String DepartmentCD = uuid.toString();
				 sql = "insert into SYS_Department ("
				 		+ "RESCD,DepartmentCD,DName,isleader,isadministration,orderby"
				 		+ ") values("
				 		+ "?,?,?,?,?,?)";
				 dao.executeSQL(sql,new Object[]{rescd, DepartmentCD, depsArray[i].trim(), "false","false","999"});
				result += ("("+DepartmentCD+")");
			}
		}
		return result;
	}
	//根据行政部门的名称获取行政岗位角色的编码，若不存在，则自动创建，返回行政岗位角色编码
	public String getpostCDbyNM1(String rescd, String postnm1){
		if(postnm1.isEmpty()){
			return "";
		}
		String sql = "select * from SYS_Post where RESCD = ? and postname = ? and isadministration = ?";
		Map<String,Object> map = dao.executeQueryObject(sql, new Object[]{rescd, postnm1, "true"});
		if(map!=null){
			return map.get("postcd").toString();
		}else{
			 UUID uuid = UUID.randomUUID();
			 String DepartmentCD = uuid.toString();
			 sql = "insert into SYS_Post ("
			 		+ "RESCD,postcd,postname,isadministration,orderby"
			 		+ ") values("
			 		+ "?,?,?,?,?)";
			 dao.executeSQL(sql,new Object[]{rescd, DepartmentCD, postnm1, "true","999"});
			 return DepartmentCD;
		}
	}
	//根据其他权限部门的名称获取其他权限岗位角色的编码，若不存在，则自动创建，返回其他权限岗位角色编码
	public String getpostCDbyNM2(String rescd, String postnm2){
		if(postnm2.isEmpty()){
			return "";
		}
		String sql = "";
		String result = "";
		String[] depsArray = postnm2.toString().split("\\,");
		List<Map<String, Object>> list =  new ArrayList<Map<String,Object>>();
		for (int i = 0; i < depsArray.length; i++) {
			sql = "select * from SYS_Post where RESCD = ? and postname = ? and isadministration = ?";
			Map<String,Object> map = dao.executeQueryObject(sql, new Object[]{rescd, depsArray[i].trim(), "false"});
			if(map!=null){
				result += ("("+map.get("postcd").toString()+")");
			}else{
				 UUID uuid = UUID.randomUUID();
				 String postcd = uuid.toString();
				 sql = "insert into SYS_Post ("
				 		+ "RESCD,postcd,postname,isadministration,orderby"
				 		+ ") values("
				 		+ "?,?,?,?,?)";
				 dao.executeSQL(sql,new Object[]{rescd, postcd, depsArray[i].trim(), "false","999"});
				result += ("("+postcd+")");
			}
		}
		return result;
	}
	
	public String checkTechPost(String rescd, String TechPostName){
		if(TechPostName.isEmpty()){
			return "";
		}
		String sql = "select * from SYS_TechPost where RESCD = ? and TechPostName = ?";
		Map<String,Object> map = dao.executeQueryObject(sql, new Object[]{rescd, TechPostName});
		if(map!=null){
			return map.get("TechPostCd").toString();
		}else{
			 UUID uuid = UUID.randomUUID();
			 String TechPostCd = uuid.toString();
			 sql = "insert into SYS_TechPost ("
			 		+ "RESCD,TechPostCd,TechPostName"
			 		+ ") values("
			 		+ "?,?,?)";
			 dao.executeSQL(sql,new Object[]{rescd, TechPostCd, TechPostName});
			 return TechPostCd;
		}
	}
	
	/**↑↑↑↑↑↑↑作废分割线*************************************************************/
	
	
	
	/**↓↓↓↓↓↓↓与该包无关系部分，但有用，改日迁移*************************************************************/
	
	public Map<String, Object> getresosurl(){
		String sql = "select * from os_url";
		return dao.executeQueryObject(sql);
	}
	
	public Map<String, Object> setresosurl(String loginName,String actualName,String url){
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "delete os_url";
		dao.executeSQL(sql);
		sql = "insert into os_url (url) values (?)";
		if(dao.executeSQL(sql, new Object[]{url})){
			result.put("msg", "success");
			new LogHelper().remark_auth("rescd", loginName, actualName,"设置联动网址"+" os_url:"+url,"用户ip","用户所在地","操作成功");
		}else{
			result.put("msg", "保存失败");
			new LogHelper().remark_auth("rescd", loginName, actualName,"设置联动网址"+" os_url:"+url,"用户ip","用户所在地","操作失败");
		}
		return result;
	}
	
	public Map<String, Object> getsllurl(String rescd){
		String sql = "select * from [360_url] where rescd = '"+rescd+"'";
		return dao.executeQueryObject(sql);
	}
	
	public Map<String, Object> setsllurl(String loginName,String actualName,String rescd, String url){
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select * from  [360_url] where rescd = '"+rescd+"'";
		if(dao.executeQueryObject(sql)==null){
			sql = "insert into [360_url] (rescd,url) values (?,?)";
			if(dao.executeSQL(sql, new Object[]{rescd, url})){
				result.put("msg", "success");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置360网址"+" url:"+url,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置360网址"+" url:"+url,"用户ip","用户所在地","操作失败");
			}
		}else{
			sql = "update [360_url] set url = ? where rescd = ?";
			if(dao.executeSQL(sql, new Object[]{url, rescd})){
				result.put("msg", "success");
				new LogHelper().remark_auth(rescd, loginName, actualName,"更新360网址"+" url:"+url,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"更新360网址"+" url:"+url,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}
	
	public Map<String, Object> getfhddurl(String rescd){
		String sql = "select * from DD_FHDD_CONFIG where rescd = '"+rescd+"'";
		return dao.executeQueryObject(sql);
	}
	
	public Map<String, Object> setfhddurl(String loginName,String actualName,String rescd, String resnm, String url){
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select * from  DD_FHDD_CONFIG where rescd = '"+rescd+"'";
		if(dao.executeQueryObject(sql)==null){
			sql = "insert into DD_FHDD_CONFIG (rescd,resnm,connect_url) values (?,?,?)";
			if(dao.executeSQL(sql, new Object[]{rescd, resnm, url})){
				result.put("msg", "success");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置防汛调度网址"+" url:"+url,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"设置防汛调度网址"+" url:"+url,"用户ip","用户所在地","操作失败");
			}
		}else{
			sql = "update DD_FHDD_CONFIG set connect_url = ? where rescd = ?";
			if(dao.executeSQL(sql, new Object[]{url, rescd})){
				result.put("msg", "success");
				new LogHelper().remark_auth(rescd, loginName, actualName,"更新防汛调度网址"+" url:"+url,"用户ip","用户所在地","操作成功");
			}else{
				result.put("msg", "保存失败");
				new LogHelper().remark_auth(rescd, loginName, actualName,"更新防汛调度网址"+" url:"+url,"用户ip","用户所在地","操作失败");
			}
		}
		return result;
	}
	
	public List<Map<String, Object>> queryForBDEVICE(String RESCD){
		String sql = "select * from B_Device where RESCD ='"+RESCD+"'";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		return list;
	}
	
	public List<Map<String, Object>> queryForBDEVICEcd(String RESCD,String devicecd){
		String sql = "select * from B_Device where RESCD ='"+RESCD+"' and devicecd = '"+devicecd+"'";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		return list;
	}
	
	public List<Map<String, Object>> queryForBDEVICETYPE(String rescd){
		String sql = "select * from B_Device_TYPE where RESCD ='"+rescd+"'";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		return list;
	}
	public List<Map<String, Object>> queryForBDEVICETYPEcd(String rescd,String typecd){
		String sql = "select * from B_Device_TYPE where rescd ='"+rescd+"'and typecd = '"+typecd+"'";
		List<Map<String, Object>> list = dao.executeQuery(sql);
		return list;
	}
	public boolean updateBDEVICE(String RESCD,String devicecd,String devicenm,String deviceid,String device_type,
		String maxv,String submaxv,String subminv,String minv,String x,String y,String state,String math,String p1,
		String p2,String p3,String p4,String p5,String p6,String p7,String p8,String apply) {
		String sqlString="update B_Device set devicenm = '"+devicenm+"',deviceid = '"+deviceid+"',device_type = '"+device_type+"',maxv = '"+maxv+"',submaxv = '"+submaxv+"',subminv = '"+subminv+"',"+
			             "minv = '"+minv+"',x ='"+x+"',y = '"+y+"',state = '"+state+"',math = '"+math+"',p1 = '"+p1+"',p2 ='"+p2+"',p3 ='"+p3+"',p4 = '"+p4+"',p5 = '"+p5+"',p6 ='"+p6+"',p7 = '"+p7+"',p8 = '"+p8+"',apply = '"+apply+"' "+
				         "where RESCD = '"+RESCD+"' and devicecd = '"+devicecd+"'";
		return dao.executeSQL(sqlString);
	}
	public boolean insertBDEVICE(String RESCD,String devicecd,String devicenm,String deviceid,String device_type,
			String maxv,String submaxv,String subminv,String minv,String x,String y,String state,String math,String p1,
			String p2,String p3,String p4,String p5,String p6,String p7,String p8,String apply) {
			String sqlString="INSERT INTO B_Device (RESCD, devicecd,devicenm,deviceid,device_type,maxv,submaxv,subminv,minv,x,y,state,math,p1,p2,p3,p4,p5,p6,p7,p8,apply) "+
			"VALUES ('"+RESCD+"','"+devicecd+"','"+devicenm+"','"+deviceid+"','"+device_type+"','"+maxv+"','"+submaxv+"','"+subminv+"','"+minv+"','"+x+"','"+y+"','"+state+"','"+math+"','"+p1+"','"+p2+"','"+p3+"','"+p4+"','"+p5+"','"+p6+"','"+p7+"','"+p8+"','"+apply+"')";
			return dao.executeSQL(sqlString);
	}
	public boolean updateBDEVICETYPE(String rescd,String typecd,String typenm,String icon,String bgimg,String highlevel,String able,String title,String unit) {
			String sqlString="update B_Device_Type set typenm = '"+typenm+"',icon = '"+icon+"',bgimg = '"+bgimg+"',highlevel = '"+highlevel+"',able = '"+able+"',title = '"+title+"',"+
				             "unit = '"+unit+"'"+
					         "where rescd = '"+rescd+"' and typecd = '"+typecd+"'";
			return dao.executeSQL(sqlString);
	}
	public boolean insertBDEVICETYPE(String rescd,String typecd,String typenm,String icon,String bgimg,String highlevel,String able,String title,String unit) {
		String sqlString="INSERT INTO B_Device_Type (rescd,typecd,typenm,icon,bgimg,highlevel,able,title,unit)"+
	                     "VALUES('"+rescd+"','"+typecd+"','"+typenm+"','"+icon+"','"+bgimg+"','"+highlevel+"','"+able+"','"+title+"','"+unit+"')";
		return dao.executeSQL(sqlString);
	}
	
	public Map<String, Object> getservlet_xx(){
		String sql = "select * from servlet_xx";
		return dao.executeQueryObject(sql);
	}

}

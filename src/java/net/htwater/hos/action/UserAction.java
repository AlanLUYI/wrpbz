package net.htwater.hos.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.Util.IPHelper;
import net.htwater.entity.ResultObject;
import net.htwater.hos.service.UserService;
import net.sf.json.JSONObject;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class UserAction extends DoAction {
	public Responser getUserInfo() {
		Map<String,Object> result=new HashMap<String,Object>();
		UserService service = (UserService) ServiceFactory.getService("user");
		String token=session.getAttribute("token").toString();
		if(token.indexOf("THIRD-")==0){
			result.put("realname", "已授权的第三方平台");
		}else{
			result = service.getUserInfo(token);
		}
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	}
	public Responser getMenuTree() {
		UserService service = (UserService) ServiceFactory.getService("user");
		List<Map<String,Object>> result=service.getMenutree(session.getAttribute("token").toString());
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(result));
		return responser;
	}
	public Responser getDataReportProc(){
		UserService service = (UserService)ServiceFactory.getService("user");
		List<Map<String, Object>> results = service.getDataReportProc(session.getAttribute("region").toString());
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(results));
		return responser;
	}
	public Responser delDataReportProc(){
		UserService service = (UserService)ServiceFactory.getService("user");
		Boolean result = service.delDataReportProc(new Integer(params.getParam("id")),session.getAttribute("token").toString());
		JSONObject json = new JSONObject();
		json.put("result", result);
		responser.setRtType(JSON);
		responser.setRtString(json.toString());
		return responser;
	}
	
	public Responser modifyPassword(){
		UserService service = (UserService)ServiceFactory.getService("user");
		Boolean result = service.modifyPassword(session.getAttribute("token").toString(), params.getParam("oldp"), params.getParam("newp"));
		ResultObject ro = new ResultObject();
		if(result){
			ro.setMessage("密码修改成功！");
			session.removeAttribute("token");
			session.removeAttribute("region");
		}else{
			ro.setMessage("旧密码输入错误！");
		}
		ro.setSuccess(result);
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(ro));
		return responser;
	}
	
	/**
	 * 设置关注
	 * @return
	 */
	public Responser setFocus(){
		UserService service = (UserService)ServiceFactory.getService("user");
		Boolean result = service.setFocus(session.getAttribute("token").toString(),  params.getParam("module"), params.getParam("focus"));
		ResultObject ro = new ResultObject();
		if(result){
			ro.setMessage("更新关注成功");
			ro.setSuccess(true);
		}else{
			ro.setMessage("更新关注失败");
			ro.setSuccess(false);
		}
		ro.setSuccess(result);
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(ro));
		return responser;
	}

	/**
	 * 添加关注
	 * @return
	 */
	public Responser addFocus(){
		UserService service = (UserService)ServiceFactory.getService("user");
		Boolean result = service.addFocus(session.getAttribute("token").toString(),  params.getParam("module"), params.getParam("focus"));
		ResultObject ro = new ResultObject();
		if(result){
			ro.setMessage("添加关注成功");
			ro.setSuccess(true);
		}else{
			ro.setMessage("添加关注失败");
			ro.setSuccess(false);
		}
		ro.setSuccess(result);
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(ro));
		return responser;
	}
	/**
	 * 删除关注
	 * @return
	 */
	public Responser delFocus(){
		UserService service = (UserService)ServiceFactory.getService("user");
		Boolean result = service.delFocus(session.getAttribute("token").toString(),  params.getParam("module"), params.getParam("focus"));
		ResultObject ro = new ResultObject();
		if(result){
			ro.setMessage("删除关注成功");
			ro.setSuccess(true);
		}else{
			ro.setMessage("删除关注失败");
			ro.setSuccess(false);
		}
		ro.setSuccess(result);
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(ro));
		return responser;
	}
	
	/**
	 * 模块访问日志
	 * @return
	 */
	public Responser moduleLog(){
		UserService service = (UserService)ServiceFactory.getService("user");
		String ip = getRequestIp();
		//service.log(session.getAttribute("loginname").toString(),session.getAttribute("DeptId").toString(),session.getAttribute("ou").toString(), "模块", ip, IPHelper.getLocation(ip), params.getParam("module"));
		service.log(session.getAttribute("token").toString(),"","", "模块", ip, IPHelper.getLocation(ip), params.getParam("module"));
		responser.setRtType(NONE);
		return responser;
	}
	
	/**
	 * 获取ip
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public String getRequestIp() {
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		return ip;
	}
	
	/**
	 * 获取日志
	 * 
	 * @return List
	 * @since v 1.0
	 */
	public Responser getSysLogs() {
		UserService service = (UserService)ServiceFactory.getService("user");
		List<Map<String, Object>> logList = service.getSysLogs(params.getParam("tm1"), params.getParam("tm2"));
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(logList));
		return responser;
	}
	
	/**
	 * 获取用户
	 * 
	 * @return List
	 * @since v 1.0
	 */
	public Responser getSysUsers(){
		UserService service = (UserService)ServiceFactory.getService("user");
		List<Map<String, Object>> userList = service.getSysUsers();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(userList));
		return responser;
	}
	
	/**
	 * 获取角色
	 * 
	 * @return List
	 * @since v 1.0
	 */
	public Responser getSysRoles(){
		UserService service = (UserService)ServiceFactory.getService("user");
		List<Map<String, Object>> roleList = service.getSysRoles();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(roleList));
		return responser;
	}
	/**
	 * 更新用户信息
	 * @author chenchu
	 * @since v 1.0
	 */
	public Responser updateUserInfo(){
		UserService service = (UserService)ServiceFactory.getService("user");
		boolean result = service.updateUserInfo(params.getParam("id"), params.getParam("loginName"), params.getParam("userName"), params.getParam("roleCode"), params.getParam("regionId"), params.getParam("enable"));
		JSONObject json = new JSONObject();
		json.put("result", result);
		if(result){
			json.put("message", "更新用户信息成功");
		}else {
			json.put("message", "更新用户信息失败");
		}
		responser.setRtType(JSON);
		responser.setRtString(json.toString());
		return responser;
	}
	/**
	 * 添加所有菜单
	 * @author chenchu
	 * @since v 1.0
	 */
	public Responser insertUserInfo(){
		UserService service = (UserService)ServiceFactory.getService("user");
		String id = service.insertUserInfo(params.getParam("loginName"), params.getParam("password"), params.getParam("userName"), params.getParam("roleCode"), params.getParam("regionId"), params.getParam("enable"));
		JSONObject json = new JSONObject();
		if (id != null) {
			json.put("result", true);
			json.put("message", "添加用户成功");
			json.put("id", id);
		}else {
			json.put("result", false);
			json.put("message", "添加用户失败");
		}
		responser.setRtType(JSON);
		responser.setRtString(json.toString());
		return responser;
	}
	/**
	 * 删除用户
	 * @author chenchu
	 * @since v 1.0
	 */
	public Responser removeSysUser(){
		UserService service = (UserService)ServiceFactory.getService("user");
		boolean result = service.removeSysUser(params.getParam("id"));
		JSONObject json = new JSONObject();
		json.put("result", result);
		json.put("message", "删除用户成功");
		responser.setRtType(JSON);
		responser.setRtString(json.toString());
		return responser;
	}
	/**
	 * 验证原始密码
	 * @author chenchu
	 * @since v 1.0
	 */
	public Responser validatePassword(){
		UserService service = (UserService)ServiceFactory.getService("user");
		boolean result = service.login(params.getParam("loginName"), params.getParam("password"));
		JSONObject json = new JSONObject();
		json.put("valid", result);
		responser.setRtType(JSON);
		responser.setRtString(json.toString());
		return responser;
	}
	/**
	 * 修改用户密码
	 * @author chenchu
	 * @since v 1.0
	 */
	public Responser modifyPassword2(){
		UserService service = (UserService)ServiceFactory.getService("user");
		boolean result = service.modifyPassword(params.getParam("loginName"), params.getParam("password"), params.getParam("password2"));
		JSONObject json = new JSONObject();
		json.put("result", result);
		if(result){
			json.put("message", "修改密码成功");
		}else{
			json.put("message", "修改密码失败");
		}
		responser.setRtType(JSON);
		responser.setRtString(json.toString());
		return responser;
	}
	/**
	 * 获取角色菜单
	 * @author chenchu
	 * @since v 1.0
	 */
	public Responser getRoleMenu(){
		UserService service = (UserService)ServiceFactory.getService("user");
		List<Map<String, Object>> menuList = service.getRoleMenu(params.getParam("roleCode"));
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(menuList));
		return responser;
	}
	/**
	 * 获取所有菜单
	 * @author chenchu
	 * @since v 1.0
	 */
	public Responser getAllMenu(){
		UserService service = (UserService)ServiceFactory.getService("user");
		List<Map<String, Object>> menuList = service.getAllMenu();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(menuList));
		return responser;
	}
	
	/**
	 * 更新角色菜单
	 * @author chenchu
	 * @since v 1.0
	 */
	public Responser updateRoleMenu(){
		UserService service = (UserService)ServiceFactory.getService("user");
		String roleCode = params.getParam("roleCode");
		String menuIds = params.getParam("menuIds");
		boolean result = service.updateRoleMenu(roleCode, menuIds);
		JSONObject json = new JSONObject();
		json.put("result", result);
		if(result){
			json.put("message", "更新角色成功");
		}else {
			json.put("message", "更新角色失败");
		}
		responser.setRtType(JSON);
		responser.setRtString(json.toString());
		return responser;
	}
}

package net.htwater.hos.service;

import java.util.List;
import java.util.Map;

public interface UserService {
	public Boolean login(String loginname,String password);
	public Boolean freeLogin(String uid);
	public List<Map<String,Object>> getMenutree(String loginname);
	public Map<String,Object> getUserInfo(String loginname);
	public List<Map<String, Object>> getDataReportProc(String region);
	public Boolean delDataReportProc(Integer id,String loginname);
	public Boolean modifyPassword(String userid,String oldp,String newp);
	/**
	 * 对结果列表中的某字段处理：是否是当前用户关注的记录
	 * @param list
	 * @param field
	 * @param user
	 * @return
	 */
	public List<Map<String,Object>> dealFocusField(List<Map<String,Object>> list,String field,String user,String module);
	
	/**
	 * 设置关注:之前的会被覆盖
	 * @param user
	 * @param module
	 * @param focus
	 * @return
	 */
	public Boolean setFocus(String user,String module,String focus);
	
	/**
	 * 添加关注：在之前基础上再添加1个关注
	 * @param user
	 * @param module
	 * @param focus
	 * @return
	 */
	public Boolean addFocus(String user,String module,String focus);
	
	/**
	 * 删除关注：在之前基础上删除1个关注
	 * @param user
	 * @param module
	 * @param focus
	 * @return
	 */
	public Boolean delFocus(String user,String module,String focus);
	
	/**
	 * 检查被外网调用的模块是否已授权
	 * @param appcode
	 * @param module
	 * @return
	 */
	public Boolean checkModuleAuth(String appcode,String module);
	
	/**
	 * 为外网即将调用的模块创建令牌
	 * @param appcode
	 * @param session_user
	 * @param session_region
	 * @return 令牌
	 */
	public String createModuleToken(String appcode,String module,String session_user,String session_region);
	
	/**
	 * 验证外网调用模块的令牌
	 * @param token
	 * @return
	 */
	public Map<String,Object> checkModuleToken(String token);
	
	/**
	 * 记录日志
	 * @param loginname
	 * @param type
	 * @param ip
	 * @param city
	 * @param logstr
	 */
	public void log(String loginname,String DeptId,String ou,String type,String ip,String city,String logstr);
	
	/**
	 * 查询某个时间段内的日志
	 * @param tm1
	 * @param tm2
	 */
	public List<Map<String, Object>> getSysLogs(String tm1, String tm2);
	
	public List<Map<String, Object>> getSysUsers();
	
	public List<Map<String, Object>> getSysRoles();
	
	public boolean updateUserInfo(String id, String loginName, String userName, String roleCode, String regionId, String enable);
	
	public String insertUserInfo(String loginName, String password, String userName, String roleCode, String regionId, String enable);
	
	public boolean removeSysUser(String id);
	
	public List<Map<String, Object>> getRoleMenu(String roleCode);
	
	public List<Map<String, Object>> getAllMenu();
	
	public boolean updateRoleMenu(String roleCode, String menuIds);
	
 }

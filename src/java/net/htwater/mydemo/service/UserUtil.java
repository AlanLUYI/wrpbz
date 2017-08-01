package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;

public interface UserUtil {

	static final String QGJ_SMP = "HOS";

	/**
	 * 获取编码
	 * @param k
	 * @return
	 */
	public Map<String, Object> getKV(String k,String rescd);
	/**
	 * 获取编码
	 * @return
	 */
	public Map<String, Object> getuuid();
	/**
	 * 获取userID的人名
	 * 
	 * @param userID
	 * @return
	 */
	public String getUserName(int userID, String rescd);

	/**
	 * 获取单位人员树，（树包括行政机构名称）
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getUsers(String rescd);
	
	/**
	 * 获取通讯录
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getUsersContact(String rescd);
	
	/**
	 * 获取通讯录信息
	 * @param rescd
	 * @param userid
	 * @param grouptype
	 * @return
	 */
	public Map<String, Object> getContactById(String rescd,String userid,String grouptype);
	/**
	 * 获取单位人员树，（树包括行政机构名称）
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getUsersPostTree(String rescd);

	/**
	 * 获取单位人员树，（树不包括机构名称）
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getPeople(String rescd);
	
	/**
	 * 获取科室的人员树（树包括行政机构名称，若departmentcd=rescd，则返回单位人员树，同getUsers）
	 * 
	 * @param rescd
	 * @param departmentcd
	 * @return
	 */
	public List<Map<String, Object>> getUsersByDepartment(String rescd,	String departmentcd);

	/**
	 * 设置登录用户名
	 * 
	 * @param userid
	 * @param loginname
	 * @param password
	 * @return
	 */
	public Map<String, Object> setloginname(String currentName,String actualName,String rescd, String userid, String loginname,String password);

	/**
	 * 获取个性登录用户名
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getotherloginname(String rescd, String userid);

	/**
	 * 设置个性登录用户名
	 * 
	 * @param userid
	 * @param loginname
	 * @return
	 */
	public Map<String, Object> setotherloginname(String currentName,String actualName,String rescd, String userid, String loginname);

	/**
	 * 删除人的账户和信息
	 * 
	 * @param rescd
	 * @param userid
	 * @return
	 */
	public Map<String, Object> deleteUser(String loginName,String actualName,String rescd, String userid);
	
	/**
	 * 密码修改
	 * @author heliang
	 */
	public Map<String, Object> getLoginfoByUserid(String uid, String rescd);

	/**
	 * 保存密码
	 * @author heliang
	 */
	public boolean updatePassword(String loginName,String actualName,String uid, String pass,String rescd);

	/**
	 * 设置人员基本机构属性
	 * 
	 * @param rescd
	 * @param userid
	 * @param departmentcd
	 * @param dutycd
	 * @param postcd
	 * @return
	 */
	public Map<String, Object> saveDDP(String rescd, String userid,
			String username, String departmentcd, String postcd,
			String otherdepartmentcd, String otherdepartmentnm,
			String otherpostcd, String otherpostnm, String option);

	/**
	 * 获取部门人员（拖动配置）
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getDeptUsers(String rescd);

	/**
	 * 获取岗位人员（拖动配置）
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getPostUsers(String rescd);

	/**
	 * 获取在职人员，userid+username+loginname
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getonguardUsers(String rescd);

	/**
	 * 获取所有人员，userid+username
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getallUsers(String rescd);

	/**
	 * 设置在职
	 * 
	 * @param rescd
	 * @param userid
	 * @return
	 */
	public Map<String, Object> setOnguard(String loginName,String actualName,String rescd, String userid);

	/**
	 * 设置离职
	 * 
	 * @param rescd
	 * @param userid
	 * @return
	 */
	public Map<String, Object> leaveOnguard(String loginName,String actualName,String rescd, String userid);

	/**
	 * 设置人的机构部门属性（用于拖动设置）
	 * @param rescd
	 * @param userid
	 * @param departmentcd
	 * @param departmentnm
	 * @return
	 */
	public Map<String, Object> setuserdept(String loginName,String actualName,String rescd, String userid,
			String departmentcd, String departmentnm);

	/**
	 *  设置人的机构部门属性（用于拖动设置）
	 * @param rescd
	 * @param userid
	 * @param departmentcd
	 * @param departmentnm
	 * @return
	 */
	public Map<String, Object> removeuserdept(String loginName,String actualName,String rescd, String userid,
			String departmentcd, String departmentnm);

	/**
	 *  设置人的岗位角色属性（用于拖动设置）
	 * @param rescd
	 * @param userid
	 * @param postcd
	 * @param postnm
	 * @return
	 */
	public Map<String, Object> setuserpost(String loginName,String actualName,String rescd, String userid,
			String postcd, String postnm);

	/**
	 *  设置人的岗位角色属性（用于拖动设置）
	 * @param rescd
	 * @param userid
	 * @param postcd
	 * @param postnm
	 * @return
	 */
	public Map<String, Object> removeuserpost(String loginName,String actualName,String rescd, String userid,
			String postcd, String postnm);

	/**
	 * 编辑人员信息
	 * @param rescd
	 * @param userid
	 * @param username
	 * @param opr
	 * @return
	 */
	public Map<String, Object> edituser(String loginName,String acturlName,String rescd, String userid,
			String username, String opr, String orderby);
		
	
	
	
	


	/**
	 * 获取单位的岗位树（包括行政岗位、其他岗位）
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getPostTree(String rescd);

	/**
	 * 获取单位的岗位树（包括行政岗位）
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getXZPostTree(String rescd);

	/**
	 * 获取单位的岗位树（包括其他岗位）
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getOtherPostTree(String rescd);

	/**
	 * 获取岗位信息
	 * 
	 * @param postcd
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> getPostBycd(String postcd, String rescd);
	
	/**
	 * 保存岗位信息
	 * @param rescd
	 * @param postcd
	 * @param postname
	 * @param isadministration
	 * @param counts
	 * @param orderby
	 * @param duty
	 * @param departmentcd
	 * @return
	 */
	public Map<String, Object> savepost( String username,String actualName, String rescd, String postcd,
			String postname, String isadministration, String counts,
			String orderby, String duty, String departmentcd);

	/**
	 * 删除岗位信息
	 * 
	 * @param rescd
	 * @param postcd
	 * @return
	 */
	public Map<String, Object> deletepost(String loginName,String actualName,String rescd, String postcd);

	
	
	

	
	
	
	
	
	/**
	 * 获取单位的部门树（包括行政机构部门、其他机构部门）
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getDepartment(String rescd);

	/**
	 * 获取单位的部门树（包括行政机构部门）
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getXZDepartment(String rescd);

	/**
	 * 获取单位的部门树（包括其他机构部门）
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getOtherDepartment(String rescd);



	/**
	 * 获取部门信息
	 * 
	 * @param rescd
	 * @param departmentcd
	 * @return
	 */
	public Map<String, Object> getDep(String rescd, String departmentcd);

	/**
	 * 保存部门信息
	 * 
	 * @param stream
	 * @return
	 */
	public Map<String, Object> saveDep(String loginName,String actualName,Map<String, String> stream);
	
	/**
	 * 删除部门信息
	 * 
	 * @param rescd
	 * @param departmentcd
	 * @return
	 */
	public Map<String, Object> deleteDep(String loginName,String actualName,String rescd, String departmentcd);

	
	
	
	
	
	/**
	 * 获取单位信息
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> getUnit(String rescd);

	/**
	 * 保存单位信息
	 * 
	 * @param stream
	 * @return
	 */
	public Map<String, Object> saveUnit(String loginName,String actualName,Map<String, String> stream);

	/**
	 * 获取单位负责人
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> getunitleader(String rescd);
	
	
	
	
	
	

	


	

	

	/**
	 * 获取应用的类型
	 * 
	 * @return
	 */
	public List<Map<String, String>> getAppTypes();

	/**
	 * 获取应用模块
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getAppList();
	
	/**
	 * 获取模块列表（用于拖动设置模块权限）
	 * 
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getAppList2(String rescd);
	/**
	 * 获取模块列表（用于初始化配置）
	 * @param rescd
	 * @return
	 */
	public List<Map<String,Object>> getAppList3(String rescd);
	/**
	 * 获取某岗位拥有的模块权限
	 * 
	 * @param postcd
	 * @return
	 */
	public List<Map<String, Object>> getAppByPostcd(String rescd, String postcd);
	

	/**
	 * 设置模块是否全体可用
	 * @param tbid
	 * @param shareall
	 * @return
	 */
	public Map<String, Object> setShareall(String loginName,String actualName,String rescd, String tbid, String shareall);

	/**
	 * 设置模块是否开放
	 * @param rescd
	 * @param tbid
	 * @param show
	 * @return
	 */
	public Map<String,Object> setOpenAPP(String loginName,String actualName,String rescd, String tbid,String show);
	/**
	 * 新增权限范围
	 * 
	 * @param rescd
	 * @param tbid
	 * @param postcd
	 * @return
	 */
	public Map<String, Object> addAppPost(String loginName,String actualName,String rescd, String tbid,
			String postcd);

	/**
	 * 删除权限范围
	 * 
	 * @param rescd
	 * @param tbid
	 * @param postcd
	 * @return
	 */
	public Map<String, Object> delAppPost(String loginName,String actualName,String rescd, String tbid,
			String postcd);

	/**
	 * 新增应用模块的审核员
	 * 
	 * @param rescd
	 * @param tbid
	 * @param checkerpost
	 * @return
	 */
	public Map<String, Object> addAppChecker(String loginName,String actualName,String rescd, String tbid,
			String checkerpost);

	/**
	 * 删除应用模块的审核员
	 * 
	 * @param rescd
	 * @param tbid
	 * @param checkerpost
	 * @return
	 */
	public Map<String, Object> delAppChecker(String loginName,String actualName,String rescd, String tbid,
			String checkerpost);

	/**
	 * 检查是否是单位负责人
	 * 
	 * @param rescd
	 * @param userid
	 * @return
	 */
	public Map<String, Object> checkunitleader2(String rescd, String userid);
		
	

	/**
	 * 判断用户是否有模块审核权限
	 * 
	 * @param userid
	 * @param tbidString
	 * @return
	 */
	public boolean isChecker(String userid, String tbidString, String rescd);
	
//*暂时作废代码*******************************************************************************************//
	
	/**
	 * 保存岗位有权限的模块
	 * @param rescd
	 * @param postcd
	 * @param tbid
	 * @return
	 */
	public Map<String, Object> savePostApp(String loginName,String actualName,String rescd, String postcd,	String tbids);

	/**
	 * 批量增加权限
	 * 
	 * @param rescd
	 * @param postcd
	 * @param tbids
	 * @return
	 */
	public Map<String, Object> addPostApp(String loginName,String actualName,String rescd, String postcd,
			String tbids);

	/**
	 * 批量删除权限
	 * 
	 * @param rescd
	 * @param postcd
	 * @param tbids
	 * @return
	 */
	public Map<String, Object> delPostApp(String loginName,String actualName,String rescd, String postcd,	String tbids);

	/**
	 * 获取userid的人员信息
	 * 
	 * @param userid
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> getUserByUserID(String userid, String rescd);

	/**
	 * 根据用户名id集合，查询用户
	 * 
	 * @param userids
	 *            1,2,3,4,5....
	 * @param rescd
	 *            水库编码
	 * @return
	 */
	public List<Map<String, Object>> getUsersByUserIDs(String userids, String rescd);
	
	/**
	 * 获取单位的部门列表（包括行政机构部门、其他机构部门，同getDepartment）
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> getDepartment2(String rescd);

	/**
	 * 设置哪个科室为局班子
	 * 
	 * @param departmentcd
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> setleaderdep(String loginName,String actualName,String departmentcd, String rescd);
	
	/**
	 * 检查是否有审查权限
	 * @param mid
	 * @param tbid
	 * @return
	 */
	public String checkauthority(String tbid,String userid,String rescd);
	/**
	 * 检查是否有查看权限
	 * @param mid
	 * @param tbid
	 * @return
	 */
	public String viewauthority(String tbid,String userid,String rescd);
	/**
	 * 检查是否有编辑权限
	 * @param mid
	 * @param tbid
	 * @return
	 */
	public String editauthority(String tbid,String userid,String rescd);
	
	/**
	 * 检查是否有审查权限
	 * @param mid
	 * @param tbid
	 * @return
	 */
	public String checkauthority_appcode(String appcode,String userid,String rescd);
	/**
	 * 检查是否有查看权限
	 * @param mid
	 * @param tbid
	 * @return
	 */
	public String viewauthority_appcode(String appcode,String userid,String rescd);
	/**
	 * 检查是否有编辑权限
	 * @param mid
	 * @param tbid
	 * @return
	 */
	public String editauthority_appcode(String appcode,String userid,String rescd);
	
	/**
	 * 获取科室负责人
	 * @param rescd
	 * @param departmentcd
	 * @return
	 */
	public Map<String, Object> getdepk2(String rescd, String departmentcd);
	/**
	 * 获取科室分管领导
	 * @param rescd
	 * @param departmentcd
	 * @return
	 */
	public Map<String, Object> getdepf2(String rescd, String departmentcd);
	
	
	
	/**↓↓↓↓↓↓↓与该包无关系部分，改日迁移*************************************************************/
	
	/**
	 * 获取联动网址
	 * @return
	 */
	public Map<String, Object> getresosurl();
	/**
	 * 设置联动网址
	 * @param url
	 * @return
	 */
	public Map<String, Object> setresosurl(String loginName,String actualName,String url);
	/**
	 * 获取360网址
	 * @return
	 */
	public Map<String, Object> getsllurl(String rescd);
	/**
	 * 设置360网址
	 * @param url
	 * @return
	 */
	public Map<String, Object> setsllurl(String loginName,String actualName,String rescd, String url);
	/**
	 * 获取防洪调度网址
	 * @return
	 */
	public Map<String, Object> getfhddurl(String rescd);
	/**
	 * 设置防洪调度网址
	 * @param url
	 * @return
	 */
	public Map<String, Object> setfhddurl(String loginName,String actualName,String rescd,String resnm, String url);
	/**
	 * 大坝安全配置表  B_Device
	 * @param url
	 * @return
	 */
	public List<Map<String, Object>> queryForBDEVICE(String RESCD);
	public List<Map<String, Object>> queryForBDEVICEcd(String RESCD,String devicecd);
	/**
	 * 大坝安全配置表  B_Device_TYPE
	 * @param url
	 * @return
	 */
	public List<Map<String, Object>> queryForBDEVICETYPE(String rescd);
	public List<Map<String, Object>> queryForBDEVICETYPEcd(String rescd,String typecd);
	/**
	 * 更新大坝安全配置表  B_Device
	 * @param url
	 * @return
	 */
	public boolean updateBDEVICE(String RESCD,String devicecd,String devicenm,String deviceid,String device_type,
			String maxv,String submaxv,String subminv,String minv,String x,String y,String state,String math,String p1,
			String p2,String p3,String p4,String p5,String p6,String p7,String p8,String apply);
	/**
	 * 更新大坝安全配置表  B_Device_Type
	 * @param url
	 * @return
	 */
	public boolean updateBDEVICETYPE(String rescd,String typecd,String typenm,String icon,String bgimg,String highlevel,String able,String title,String unit);	
	/**
	 * 新增大坝安全配置表  B_Device
	 * @param url
	 * @return
	 */
	public boolean insertBDEVICE(String RESCD,String devicecd,String devicenm,String deviceid,String device_type,
			String maxv,String submaxv,String subminv,String minv,String x,String y,String state,String math,String p1,
			String p2,String p3,String p4,String p5,String p6,String p7,String p8,String apply);
	public boolean insertBDEVICETYPE(String rescd,String typecd,String typenm,String icon,String bgimg,String highlevel,String able,String title,String unit);
	
}
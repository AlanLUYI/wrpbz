/**
 * 水库巡查
 * @author fhj
 * 2015-02-10
 */
package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;



public interface XunchaService {
//	static final String HT_YUNRUN = "yurun"; 
	static final String HT_BLZHSL = "HOS";
//	static final String HT_SHANHONG = "shanhong";
//	static final String HT_SLPC = "slpc";

	public Boolean queryFORUserid(String userid);
	public Boolean refashFORsecret(String id);
	public Boolean addMemberRes(String id,String res);
	public Boolean updateFORmember(String id,String Name,String UserID,String Telephone);
	public Boolean updateFORLengthRes(String id,String pinlv,String isOPEN);
	public Boolean insertFORXunchaUser(String Name, String UserID,String Password, String Type, String TypeValue, String Telephone);
	public Boolean deleteFORmember(String id);
	
	public List<Map<String, Object>> queryFORALLmember();
	public List<Map<String, Object>> queryFORALLres();
	public List<Map<String, Object>> queryMemberRes(String id);
	public Boolean deleteMemberRes(String id, String res);
	public Boolean updateFORLengthP(String days, String sel);
	public Map<String, Object> updateTaskState(String task_id,String mem_id);
	public List<Map<String, Object>> queryFORNewTask();
	public Boolean insertTask(Map<String, Object> map);
	public Map<String, Object> Login(String UserID, String Password);
	public Map<String, Object> queryForTasklist(String mem_id);
	public Map<String, Object> queryForCaselist(String mem_id,String pid);
	public Map<String, Object>queryFORXunchaPath(String task_id);
	
	public Boolean islogin(String id);
	//用户移动端判断
	public Map<String, Object> isLogin(String id);
	public Boolean updateFORFile(String param, String param2, String param3);
	public Map<String, Object> queryState();
	public Map<String, Object> updateFORsecret(String mem_id, String old_password, String new_password);
	public Map<String, Object> insertResult(String result,String mem_id);
	public Map<String, Object> queryChildResult(String task_id,String case_pid,String mem_id);
	public Map<String, Object> updateTaskResult(String task_id,String mem_id,String place);
	public Map<String, Object> insertTaskFile(String task_id, String file_name,String file_type, String case_pid);
	public Map<String, Object> queryChildFile(String task_id,String case_pid,String mem_id);
	public List<Map<String, Object>> queryFORAllRes();
	
	public List<Map<String, Object>> queryFOROverTask(String TM1,String TM2);
	public Map<String, Object> selectFORXunchaResultList(String task_id,String PageNum,String type);
	
	public List<Map<String, Object>> queryFORresultByid(String id);
	public List<Map<String, Object>> queryFORfileByid(String id);
	public List<Map<String, Object>> queryFORUNOverTask(String TM1,String TM2);
	public Map<String, Object> insertTaskPath(String task_id,String mem_id, String lon,String lat);
	public Map<String, Object> queryFORTaskStat(String TM1,String TM2);
	public Map<String, Object> queryWeiHuTaskStat(String TM);
	public Map<String, Object> addWxyhProgram(String task_id,String regionid,String task_name,String time
			,String money,String Name);
	public Map<String, Object> addWxyhFeeUse(String task_id,String regionid,String yr,String mth,String finished,String payed);
	public Map<String, Object> updateForhead(String mem_id, String file);
	public Boolean insertLoginlog(String mem_id,String mobile_id, String sys_version, String mobile_type,String mobile_brand, String mobile_system, String client_version, String client_version_int);
	public Boolean insertFORjmxc(String res_id, String mem_id, String creat_time,String limit_time);
	public Map<String, Object> queryResList(String mem_id);
	public Map<String, Object> insertFORenTask(String res_id, String mem_id);
	public Boolean updateFORisread(String id, String type);
	public List<Map<String, Object>> queryTaskMoney(String TM1,String TM2);
	public Map<String, Object> addDocs(String docNO,String docNM,String type,String docType
			,String pages,String bzTM,String bzUnit,String deadline,String secLvl,String refer);
	
}

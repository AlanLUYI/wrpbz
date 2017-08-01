package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * @author yj
 * @version
 * @since v 1.0
 * @Date  2013-02-01 3:14:02 PM
 * 
 * @see
 */
public interface OfficeService {
	
	static final String QGJ_SMP = "HOS";
	
	public boolean addUserZhaoPian(int id,String pathString,String sheetnameString);
	
	public boolean addDegreeSheet(int id,String pathString,String sheetnameString);
	
	public boolean addPicSheet(int id,String Rescd,String pathString,String sheetnameString);
	
	public boolean addWorkInfoSheet(int id,String pathString,String sheetnameString);
	
	public Map<String, Object> getPersonInfo(String ResCD); 
	public Map<String, Object> getPersonInfo1(String ResCD); 
	public Map<String, Object> PersonFactorsQuery(String ResCD,String name,String sex,String politics,String rank); 
	
	public List<Map<String, Object>> queryDutyInfo(String Rescd);
	
	public List<Map<String, Object>> queryAdminDutyInfo(String Rescd);
	
	public List<Map<String, Object>> queryTechDutyInfo(String Rescd);	
	
	public List<Map<String, Object>> queryTechPostInfo(String Rescd);	
	
	public List<Map<String, Object>> getZiDuanOption(int ZiDuan,String Rescd);
	
	public Map<String, Object> updateOption(Map<String, String> stream);
	/**
	 * 人士档案管理字段管理
	 * @author fhj
	 * @param  oper,id,rescd,ZiDuan,xuanxiang,orderby,optioncd
	 * @return Map<String, Object>
	 */
	public Map<String, Object> UpdateAddOptionService(String oper,String id,String rescd,String ZiDuan,String xuanxiang,String orderby,String optioncd);
	
	public Map<String, Object> AddOption(Map<String, String> stream);
	
	public Map<String, Object> DeleteOption(Map<String, String> stream);
	
	public Map<String, Object> GetWorkInfo(int id);
	
	public Map<String, Object> GetPicInfo(int id,String Rescd); 
	
	public Map<String, Object> GetStudyInfo(int id);
	
	public List<Map<String, Object>> queryDepartmentInfo(String Rescd);
	
	public List<Map<String, Object>> queryPostInfo(String Rescd);
	
	public List<Map<String, Object>>  getPerson(String Rescd);
	
	public List<Map<String, Object>>  queryPersonInfoById(int id,String Rescd);
	
	public List<Map<String, Object>>  queryDepartmentInfoByStringDepartment(String departmentcd);
	
	public void AddPersonInfo(Map<String, String> MapDate);
	
	public void ModifyPersonInfo(Map<String, String> ids,Map<String, String>  MapDate);
	
	public void deletePersonInfoById(Map<String, String> ids);
	
	public int GetFirstPersonId(String Rescd);	
	
	public Map<String, Object> QueryLockStatusById(String Rescd,String id);	
	
	public int SetLockStatusById(String Rescd,int id);	
	
	public int QueryTheLastIdOfPersonInfo();	
	
	public void addWorkInfo(Map<String, Object> MapDate);
	
	public void updateWorkInfo(Map<String, String> ids,Map<String, Object>  MapDate);
	
	public void deleteWorkInfoById(Map<String, String> ids);
	
	
	public void addStudyInfo(Map<String, Object> MapDate);
	
	public void updateStudyInfo(Map<String, String> ids,Map<String, Object>  MapDate);
	
	public void deleteStudyInfoById(Map<String, String> ids);
	
	
	//--------------------档案管理-----------------
	
	public Map<String, Object> GetFilesInfo(String department,String year,String type);
	
	public Map<String, Object> getFilesdetail(String rescd, String selected,String StartTime,String EndTime);
	
	public Map<String, Object> GetSingleFileInfo(String FilesNo);
	
	public String getSingleFileDetail(String SingleFileId);
	
	public void addFilesInfo(Map<String, Object> MapDate);
	
	public void updateFilesInfo(Map<String, String> ids,Map<String, Object>  MapDate);
	
	public void deleteFilesInfoById(Map<String, String> ids);
	
	public void addSingleFileInfo(Map<String, Object> MapDate);
	
	public void updateSingleFileInfo(Map<String, String> ids,Map<String, Object>  MapDate);
	
	public void deleteSingleFileInfoById(Map<String, String> ids);
	
	public String QueryFilesInfoById(String id);
	
	public String GetLastFilesInfo();	
	
	
	/**
	 * 获取项目管理菜单树
	 * @param rescd
	 * @return
	 */
	public List<Map<String, Object>> getPromenuTree(String rescd);
	
	
	//--------------薪资管理-----------
	
	public Map<String, Object> getPersonSalaryByYear(String UserId,String year,String rescd);
	
	public void addPersonSalary(Map<String, Object> MapDate);
	
	public void updatePersonSalary(Map<String, String> ids,Map<String, Object>  MapDate);
	
	public void deletePersonSalaryById(Map<String, String> ids);
	
	public List <Map<String, Object>>  getStaticSalaryForTable(String rescd,String year,String department,String project);
	public List<Map<String, Object>> queryALLuser(String rescd);
	public List<Map<String, Object>> queryALLuserLOCK(String rescd);
	public List<Map<String, Object>> queryALLdepartment(String rescd);
	public List<Map<String, Object>> getStaticSalaryForChart(String rescd,String dep,String year,String project);

	public List<Map<String, Object>> queryManageList();
}

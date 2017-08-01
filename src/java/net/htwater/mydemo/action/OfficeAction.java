package net.htwater.mydemo.action;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.attribute.standard.DateTimeAtCompleted;
//import org.apache.log4j.helpers.DateTimeDateFormat;
import net.htwater.mydemo.service.OfficeService;
import net.sf.json.JSONObject;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
/**
 * 
 * 
 * @author yj
 * @version
 * @since v 1.0
 * @Date  2013-02-01 4:46:37 PM
 * 
 * @see
 */
public class OfficeAction extends DoAction {
	
	public Responser addUserZhaoPianService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		String idString=params.getParam("id");
		String pathString=params.getParam("path");
		String filenameString=params.getParam("filename");
		responser.setRtType(JSON);
		int id=Integer.parseInt(idString);
		boolean result=service.addUserZhaoPian(id, pathString, filenameString);
		if (result) {
			responser.setRtString(parseJSON("{\"result\":\"success\"}"));
		}else {
			responser.setRtString(parseJSON("{\"result\":\"error\"}"));
		}
		return responser;
	}	
	
	public Responser addDegreeSheetService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		String idString=params.getParam("id");
		String pathString=params.getParam("path");
		String filenameString=params.getParam("filename");
		responser.setRtType(JSON);
		int id=Integer.parseInt(idString);
		boolean result=service.addDegreeSheet(id, pathString, filenameString);
		if (result) {
			responser.setRtString(parseJSON("{\"result\":\"success\"}"));
		}else {
			responser.setRtString(parseJSON("{\"result\":\"error\"}"));
		}
		return responser;
	}
	
	public Responser addPicSheetService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		String idString=params.getParam("id");
		String Rescd=params.getParam("Rescd");
		String pathString=params.getParam("path");
		String filenameString=params.getParam("filename");
		responser.setRtType(JSON);
		int id=Integer.parseInt(idString);
		boolean result=service.addPicSheet(id, Rescd, pathString, filenameString);
		if (result) {
			responser.setRtString(parseJSON("{\"result\":\"success\"}"));
		}else {
			responser.setRtString(parseJSON("{\"result\":\"error\"}"));
		}
		return responser;
	}
	
	
	public Responser addWorkInfoSheetService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		String idString=params.getParam("id");
		String pathString=params.getParam("path");
		String filenameString=params.getParam("filename");
		responser.setRtType(JSON);
		int id=Integer.parseInt(idString);
		boolean result=service.addWorkInfoSheet(id, pathString, filenameString);
		if (result) {
			responser.setRtString(parseJSON("{\"result\":\"success\"}"));
		}else {
			responser.setRtString(parseJSON("{\"result\":\"error\"}"));
		}
		return responser;
	}
	
	public Responser personInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getPersonInfo(params.getParam("Rescd"))));
		return responser;
		
			
	}
	public Responser personInfoService1() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getPersonInfo1(params.getParam("Rescd"))));
		return responser;
		
			
	}
	
	public Responser PersonFactorsQueryService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.PersonFactorsQuery(params.getParam("Rescd"),params.getParam("name"),params.getParam("sex"),params.getParam("politics"),params.getParam("rank"))));
		return responser;
		
			
	}	
	
	public Responser queryALLuser() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryALLuser(params.getParam("rescd"))));
		return responser;				
	}
	public Responser queryALLuserLOCK() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryALLuserLOCK(params.getParam("rescd"))));
		return responser;				
	}
	public Responser queryALLdepartment() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryALLdepartment(params.getParam("rescd"))));
		return responser;				
	}	
	public Responser OperationWorkInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		String operString=params.getParam("oper");
		String IDString=params.getParam("id");
		//String UserIdString=params.getParam("UserId");
		
		String StartTimeString=params.getParam("StartTime");
		String EndTimeString=params.getParam("EndTime");
		String UnitString=params.getParam("Unit");
		String DepartmentString=params.getParam("Department");
		String DutyString=params.getParam("Duty");		
		String result="success";
		boolean flag=false;
		responser.setRtType(NONE);
		int AddOrModifyForWorkInfoAndStudyInfo =Integer.parseInt(params.getParam("AddOrModifyForWorkInfoAndStudyInfo"));
//		int ModifyTbale = Integer.parseInt(params.getParam("ModifyTbale"));
		
		if (operString!=null&&operString.equals("add")) {
			//增加
			if(AddOrModifyForWorkInfoAndStudyInfo ==2)  //新增人员信息时，新增工作经历
			{
			    int TheLastUserIdOfPersonInfo = service.QueryTheLastIdOfPersonInfo();	
			    
			    Map<String, Object> newMap = new HashMap<String, Object>();		    
				newMap.put("UserId", TheLastUserIdOfPersonInfo);
				newMap.put("StartTime", StartTimeString);
				newMap.put("EndTime", EndTimeString);
				newMap.put("Unit", UnitString);
				newMap.put("Department", DepartmentString);
				newMap.put("Duty", DutyString);				
				//flag= service.addWorkInfo(newMap); 
				service.addWorkInfo(newMap); 
				flag= true;
			
			}
			else if(AddOrModifyForWorkInfoAndStudyInfo ==3)   //修改人员信息时，新增工作经历
			{				
				int userId = Integer.parseInt(params.getParam("userId"));				
				Map<String, Object> newMap = new HashMap<String, Object>();				
				newMap.put("UserId", userId);
				newMap.put("StartTime", StartTimeString);
				newMap.put("EndTime", EndTimeString);
				newMap.put("Unit", UnitString);
				newMap.put("Department", DepartmentString);
				newMap.put("Duty", DutyString);				
				//flag= service.addWorkInfo(newMap); 
				service.addWorkInfo(newMap); 
				flag= true;
				
			}		
		
		}
		else if (operString!=null&&operString.equals("edit")&&IDString!=null) {
			//修改
			int id=Integer.parseInt(IDString);		
			Map<String, Object> WorkInfoMap = new HashMap<String, Object>();			
			WorkInfoMap.put("StartTime", StartTimeString);
			WorkInfoMap.put("EndTime", EndTimeString);
			WorkInfoMap.put("Unit", UnitString);
			WorkInfoMap.put("Department", DepartmentString);
			WorkInfoMap.put("Duty", DutyString);
			
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("ID", id +"");			
			//flag= service.addWorkInfo(newMap); 
			service.updateWorkInfo(ids,WorkInfoMap); 
			flag= true;
		
		
		}
		else if (operString!=null&&operString.equals("del")&&IDString!=null) {
			//删除
			int id=Integer.parseInt(IDString);
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("ID", id +"");	
			service.deleteWorkInfoById(ids);
			flag= true;
			
		}
		if (!flag) {
			result="error";
			responser.setRtType("JSON");
		}
		JSONObject resultjson = new JSONObject(); 
		resultjson.put("status", result); //返回状态
		responser.setRtString((resultjson.toString()));
		return responser;
	}
	
	
	
	public Responser OperationStudyInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		String operString=params.getParam("oper");
		String IDString=params.getParam("id");
		//String UserIdString=params.getParam("UserId");
		
		String StartTimeString=params.getParam("StartTime");
		String EndTimeString=params.getParam("EndTime");
		String SchoolString=params.getParam("School");
		String DepartmentString=params.getParam("Department");
		String FormString=params.getParam("Form");
		String DegreeString=params.getParam("Degree");		
		String result="success";
		boolean flag=false;
		responser.setRtType(NONE);
		int AddOrModifyForWorkInfoAndStudyInfo =Integer.parseInt(params.getParam("AddOrModifyForWorkInfoAndStudyInfo"));

		if (operString!=null&&operString.equals("add")) {
			//增加
			if(AddOrModifyForWorkInfoAndStudyInfo ==2)  //新增人员信息时，新增学习经历
			{
			   int TheLastUserIdOfPersonInfo = service.QueryTheLastIdOfPersonInfo();			
			
			   Map<String, Object> newMap = new HashMap<String, Object>();		    
			   newMap.put("UserId", TheLastUserIdOfPersonInfo);
			   newMap.put("StartTime", StartTimeString);
			   newMap.put("EndTime", EndTimeString);
			   newMap.put("School", SchoolString);
			   newMap.put("Department", DepartmentString);
			   newMap.put("Form", FormString);
			   newMap.put("Degree", DegreeString);
			
			   //flag= service.addWorkInfo(newMap); 
			   service.addStudyInfo(newMap); 
			   flag= true;
			}
			else if(AddOrModifyForWorkInfoAndStudyInfo ==3)   //修改人员信息时，新增学习经历
			{
				
	           int userId = Integer.parseInt(params.getParam("userId"));
				
				Map<String, Object> newMap = new HashMap<String, Object>();
				
				newMap.put("UserId", userId);
				newMap.put("StartTime", StartTimeString);
				newMap.put("EndTime", EndTimeString);
				newMap.put("School", SchoolString);
				newMap.put("Department", DepartmentString);
				newMap.put("Form", FormString);
				newMap.put("Degree", DegreeString);
				
				//flag= service.addWorkInfo(newMap); 
				service.addStudyInfo(newMap); 
				flag= true;				
			
			}
		
		
		
		}
		else if (operString!=null&&operString.equals("edit")&&IDString!=null) {
			//修改
			int id=Integer.parseInt(IDString);		
			Map<String, Object> StudyInfoMap = new HashMap<String, Object>();  
			
			StudyInfoMap.put("StartTime", StartTimeString);
			StudyInfoMap.put("EndTime", EndTimeString);
			StudyInfoMap.put("School", SchoolString);
			StudyInfoMap.put("Department", DepartmentString);
			StudyInfoMap.put("Form", FormString);
			StudyInfoMap.put("Degree", DegreeString);			
			
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("ID", id +"");		
			
			//flag= service.addWorkInfo(newMap); 
			service.updateStudyInfo(ids,StudyInfoMap); 
			flag= true;
		
		
		}
		else if (operString!=null&&operString.equals("del")&&IDString!=null) {
			//删除
			int id=Integer.parseInt(IDString);
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("ID", id +"");	
			service.deleteStudyInfoById(ids);
			flag= true;
			
		}
		if (!flag) {
			result="error";
			responser.setRtType("JSON");
		}
		JSONObject resultjson = new JSONObject(); 
		resultjson.put("status", result); //返回状态
		responser.setRtString((resultjson.toString()));
		return responser;
	}
	
	
	
	
	public Responser queryDutyInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryDutyInfo(params.getParam("Rescd"))));
		return responser;
	}
	
	public Responser queryAdminDutyInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryAdminDutyInfo(params.getParam("Rescd"))));
		return responser;
	}
	
	public Responser queryTechDutyInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryTechDutyInfo(params.getParam("Rescd"))));
		return responser;
	}
	
	
	public Responser queryTechPostInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryTechPostInfo(params.getParam("Rescd"))));
		return responser;
	}
	
	public Responser getZiDuanOptionService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getZiDuanOption(Integer.parseInt(params.getParam("ZiDuan")),params.getParam("rescd"))));
		return responser;
	}
	
	public Responser UpdateOptionService()
	{
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.updateOption(params.getParams())));
		return responser;
	}
	/**
	 * 人士档案管理字段管理
	 * @author fhj
	 * @param  oper,id,rescd,ZiDuan,xuanxiang,orderby,optioncd
	 * @return Map<String, Object>
	 */
	public Responser UpdateAddOptionService()
	{
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtType(NONE);
		responser.setRtString(parseJSON(service.UpdateAddOptionService(
				params.getParams().get("oper"),
				params.getParams().get("id"),
				params.getParams().get("rescd"),
				params.getParams().get("ZiDuan"),
				params.getParams().get("xuanxiang"),
				params.getParams().get("orderby"),
				params.getParams().get("optioncd")
				)));
		return responser;
	}
	
	public Responser AddOptionService()
	{
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AddOption(params.getParams())));
		return responser;
	}
	
	
	public Responser DeleteOptionService()
	{
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.DeleteOption(params.getParams())));
		return responser;
	}
	
	
	public Responser GetWorkInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.GetWorkInfo(Integer.parseInt(params.getParam("id")))));
		return responser;
	}
	
	public Responser GetPicInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);		
		responser.setRtString(parseJSON(service.GetPicInfo(Integer.parseInt(params.getParam("id")),params.getParam("Rescd"))));
		return responser;
	}
	
	
	
	public Responser GetStudyInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		int id=Integer.parseInt(params.getParam("id"));
		responser.setRtString(parseJSON(service.GetStudyInfo(Integer.parseInt(params.getParam("id")))));
		return responser;
	}
	
	
	
	public Responser queryDepartmentInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryDepartmentInfo(params.getParam("Rescd"))));
		return responser;
	}
	
	
	public Responser queryPostInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryPostInfo(params.getParam("Rescd"))));
		return responser;
	}
	
	
	 
	public Responser personService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getPerson(params.getParam("Rescd"))));
		return responser;
	}
	
	public Responser queryPersonInfoByIdService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryPersonInfoById(Integer.parseInt(params.getParam("id")),params.getParam("Rescd"))));
		return responser;
	}
	
	
	public void AddPersonInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		service.AddPersonInfo(params.getParams());  
	}
	
	public void ModifyPersonInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		Map<String, String> PersonInfoMap = params.getParams();
		Map<String, String> ids =new HashMap<String, String>();
		ids.put("id", PersonInfoMap.get("id"));
		ids.put("Rescd", PersonInfoMap.get("Rescd"));
		PersonInfoMap.remove("id");
		PersonInfoMap.remove("Rescd");
		if(PersonInfoMap.get("native123") != null){
			PersonInfoMap.put("native", PersonInfoMap.get("native123"));
			PersonInfoMap.remove("native123");
		}
		
		service.ModifyPersonInfo(ids,PersonInfoMap);  
	}
	
	
	
	public void deletePersonInfoByIdService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		Map<String, String> ids  = params.getParams();		
		service.deletePersonInfoById(ids);  
	}
	
	
	public Responser queryDepartmentInfoByStringDepartmentService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryDepartmentInfoByStringDepartment(params.getParam("departmentcd"))));
		return responser;
	}	
	
	public Responser GetFirstPersonIdService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(TEXT);
		responser.setRtString(service.GetFirstPersonId(params.getParam("Rescd"))+"");
		return responser;
	}
	
	public Responser QueryLockStatusByIdService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.QueryLockStatusById(params.getParam("Rescd"),params.getParam("id"))));
		return responser;
	}	
	
	
	public Responser SetLockStatusByIdService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(TEXT);
		responser.setRtString(service.SetLockStatusById(params.getParam("Rescd"),Integer.parseInt(params.getParam("id")))+"");
		return responser;
	}		
	
	//----------------档案管理---------------
	public Responser GetFilesInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.GetFilesInfo(params.getParam("department"),params.getParam("year"),params.getParam("type"))));
		return responser;		
		
	}
	
	public Responser getFilesdetailService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getFilesdetail(params.getParam("rescd"),params.getParam("selected"),params.getParam("StartTime"),params.getParam("EndTime"))));
		return responser;		
		
	}	
	
	public Responser GetSingleFileInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.GetSingleFileInfo(params.getParam("FilesNo"))));
		return responser;		
		
	}	
	
	public Responser getSingleFileDetailService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		responser.setRtType(TEXT);
		responser.setRtString(service.getSingleFileDetail(params.getParam("SingleFileId")));
		return responser;		
		
	}
	
	
	public Responser OperationFilesInfoService() {
		
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		String operString=params.getParam("oper");
		String IDString=params.getParam("id");
		String FilesNumString=params.getParam("FilesNum");		
		String FilesNameString=params.getParam("FilesName");
		String maintainDayString=params.getParam("maintainDay");
		String typeString=params.getParam("type");
		String filesDateString=params.getParam("filesDate");
		String pageNumString=params.getParam("pageNum");
		String copyNumString=params.getParam("copyNum");
		String dutyUnitString=params.getParam("dutyUnit");
		String dutyDepartmentString=params.getParam("dutyDepartment");
		String generatePersonString=params.getParam("generatePerson");
		String generateDateString=params.getParam("generateDate");		
		
		String result="success";
		boolean flag=false;
		responser.setRtType(NONE);
		
		if (operString!=null&&operString.equals("add")) {
			//增加		
			    
			    Map<String, Object> newMap = new HashMap<String, Object>();		    
				newMap.put("FilesNum", FilesNumString);
				newMap.put("FilesName", FilesNameString);
				newMap.put("maintainDay", maintainDayString);
				newMap.put("type", typeString);
				newMap.put("filesDate", filesDateString);
				newMap.put("pageNum", pageNumString);			
				newMap.put("copyNum", copyNumString);
				newMap.put("dutyUnit", dutyUnitString);
				newMap.put("dutyDepartment", dutyDepartmentString);
				newMap.put("generatePerson", generatePersonString);
				newMap.put("generateDate", generateDateString);				
				
				//flag= service.addWorkInfo(newMap); 
				service.addFilesInfo(newMap); 
				flag= true;		
		
		
		}
		else if (operString!=null&&operString.equals("edit")) {
			//修改
			
			Map<String, Object> newMap = new HashMap<String, Object>();			
			newMap.put("FilesName", FilesNameString);
			newMap.put("maintainDay", maintainDayString);
			newMap.put("type", typeString);
			newMap.put("filesDate", filesDateString);
			newMap.put("pageNum", pageNumString);			
			newMap.put("copyNum", copyNumString);
			newMap.put("dutyUnit", dutyUnitString);
			newMap.put("dutyDepartment", dutyDepartmentString);
			newMap.put("generatePerson", generatePersonString);
			newMap.put("generateDate", generateDateString);		
			
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("FilesNum", FilesNumString);		
			
			//flag= service.addWorkInfo(newMap); 
			service.updateFilesInfo(ids,newMap); 
			flag= true;
		
		
		}
		else if (operString!=null&&operString.equals("del")&&IDString !=null) {
			//删除
		
			//删除该案卷
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("id", IDString);			
			
			//先同步删除该案卷内的所有文件
			String StringFilesNum =service.QueryFilesInfoById(IDString);	//用该案卷的id查找该案卷的案卷号		
			Map<String, String> ids1 =new HashMap<String, String>();
			ids1.put("FilesNum", StringFilesNum);	
			service.deleteSingleFileInfoById(ids1);			
			
			//再删除该案卷的信息
			service.deleteFilesInfoById(ids);			
			
			flag= true;
			
		}
		if (!flag) {
			result="error";
			responser.setRtType("JSON");
		}
		JSONObject resultjson = new JSONObject(); 
		resultjson.put("status", result); //返回状态
		responser.setRtString((resultjson.toString()));
		return responser;
	}
	
	
    public Responser OperationSingleFileInfoService() {
		
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		String operString=params.getParam("oper");
		String IDString=params.getParam("id");
		String NumString=params.getParam("Num");		
		String FileNameString=params.getParam("FileName");
		String FilesNumString=params.getParam("FilesNum");
		String maintainDayString=params.getParam("maintainDay");
		String MuLuNumString=params.getParam("MuLuNum");
		String pageNumString=params.getParam("pageNum");
		String dutyUnitString=params.getParam("dutyUnit");
		String copyNumString=params.getParam("copyNum");
		
		String pathString=params.getParam("path");
		
		String result="success";
		boolean flag=false;
		responser.setRtType(NONE);

		
		if (operString!=null&&operString.equals("add")) {
			//增加		
			    String codenameString=params.getParam("codename");
			    
			    
			    Map<String, Object> newMap = new HashMap<String, Object>();		    
				newMap.put("Num", NumString);
				newMap.put("FileName", FileNameString);
				newMap.put("FilesNum", FilesNumString);
				newMap.put("maintainDay", maintainDayString);
				newMap.put("MuLuNum", MuLuNumString);
				newMap.put("pageNum", pageNumString);			
				newMap.put("copyNum", copyNumString);
				newMap.put("dutyUnit", dutyUnitString);
				newMap.put("path", pathString);
				newMap.put("codename", codenameString);					
				
				//flag= service.addWorkInfo(newMap); 
				service.addSingleFileInfo(newMap); 
				flag= true;		
		
		
		}
		else if (operString!=null&&operString.equals("edit")&&IDString !=null) {
			//修改
			
			Map<String, Object> newMap = new HashMap<String, Object>();  
			
			newMap.put("Num", NumString);
			newMap.put("FileName", FileNameString);
			newMap.put("FilesNum", FilesNumString);
			newMap.put("maintainDay", maintainDayString);
			newMap.put("MuLuNum", MuLuNumString);
			newMap.put("pageNum", pageNumString);			
			newMap.put("copyNum", copyNumString);
			newMap.put("dutyUnit", dutyUnitString);	
			
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("id", IDString);		
			
			//flag= service.addWorkInfo(newMap); 
			service.updateSingleFileInfo(ids,newMap); 
			flag= true;
		
		
		}
		else if (operString!=null&&operString.equals("del")&&IDString !=null) {
			//删除
		
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("id", IDString);	
			service.deleteSingleFileInfoById(ids);
			flag= true;
			
		}
		if (!flag) {
			result="error";
			responser.setRtType("JSON");
		}
		JSONObject resultjson = new JSONObject(); 
		resultjson.put("status", result); //返回状态
		responser.setRtString((resultjson.toString()));
		return responser;
	}
    
    
    
    
	public Responser GetLastFilesInfoService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(TEXT);
		responser.setRtString((service.GetLastFilesInfo()).trim());
		return responser;
	}
	
	
	//初始化菜单树
	public Responser getMenuTree() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		responser.setRtType(JSON);
		String rescdString=params.getParam("rescd");
		//String result=service.getPromenuTree(rescdString).toString();
		responser.setRtString(parseJSON(service.getPromenuTree(rescdString)));
		return responser;
	}
	
	
	
	
    //-----------------------------------------------	
	//--------------------薪资管理-------------------

	public Responser getPersonSalaryByYearService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getPersonSalaryByYear(params.getParam("UserId"),params.getParam("year"),params.getParam("rescd"))));
		return responser;
		
	}
	
	
    public Responser modifyPersonSalaryService() {
		
		OfficeService service = (OfficeService) ServiceFactory.getService("office");		
		String operString=params.getParam("oper");
		String IDString=params.getParam("id");
		String UserIdString=params.getParam("UserId");
		String UserNameString=params.getParam("UserName");	
		String rescdString=params.getParam("rescd");		
		String TimeString=params.getParam("Time");	    
		String JBGZString=params.getParam("JBGZ");
		String NGGZString=params.getParam("NGGZ");
		String JXGZString=params.getParam("JXGZ");
		String TXBTString=params.getParam("TXBT");	
		String JTBTString=params.getParam("JTBT");
		String ZFBTString=params.getParam("ZFBT");
		String WCBTString=params.getParam("WCBT");	
		String YFGZString=params.getParam("YFGZ");
		String YLBXString=params.getParam("YLBX");
		String HealthBXString=params.getParam("HealthBX");	
		String SYBXString=params.getParam("SYBX");
		String GJJString=params.getParam("GJJ");
		String GHFString=params.getParam("GHF");	
		String CommerceBXString=params.getParam("CommerceBX");
		String SpareString=params.getParam("Spare");
		String NSGZString=params.getParam("NSGZ");	
		String GSSString=params.getParam("GSS");
		String SFGZString=params.getParam("SFGZ");
	
		String result="success";
		boolean flag=false;
		responser.setRtType(NONE);		

		if (operString!=null&&operString.equals("add")) {
			//增加						
			
			   Map<String, Object> newMap = new HashMap<String, Object>();		    
			   newMap.put("UserId", UserIdString);
			   newMap.put("UserName", UserNameString);
			   newMap.put("rescd", rescdString);
			   newMap.put("Time", TimeString);
			   newMap.put("JBGZ", JBGZString);
			   newMap.put("NGGZ", NGGZString);
			   newMap.put("JXGZ", JXGZString);
			   newMap.put("TXBT", TXBTString);
			   newMap.put("JTBT", JTBTString);
			   newMap.put("ZFBT", ZFBTString);
			   newMap.put("WCBT", WCBTString);
			   newMap.put("YFGZ", YFGZString);
			   newMap.put("YLBX", YLBXString);
			   newMap.put("HealthBX", HealthBXString);
			   newMap.put("SYBX", SYBXString);
			   newMap.put("GJJ", GJJString);
			   newMap.put("GHF", GHFString);
			   newMap.put("CommerceBX", CommerceBXString);
			   newMap.put("Spare", SpareString);
			   newMap.put("NSGZ", NSGZString);
			   newMap.put("GSS", GSSString);
			   newMap.put("SFGZ", SFGZString);			
			   
			   //flag= service.addWorkInfo(newMap); 
			   service.addPersonSalary(newMap); 
			   flag= true;		
		
		
		
		}
		else if (operString!=null&&operString.equals("edit")&&IDString!=null) {
			//修改
			int id=Integer.parseInt(IDString);		
			Map<String, Object> newMap = new HashMap<String, Object>();  
			
			   newMap.put("Time", TimeString +"-01");
			   newMap.put("JBGZ", JBGZString);
			   newMap.put("NGGZ", NGGZString);
			   newMap.put("JXGZ", JXGZString);
			   newMap.put("TXBT", TXBTString);
			   newMap.put("JTBT", JTBTString);
			   newMap.put("ZFBT", ZFBTString);
			   newMap.put("WCBT", WCBTString);
			   newMap.put("YFGZ", YFGZString);
			   newMap.put("YLBX", YLBXString);
			   newMap.put("HealthBX", HealthBXString);
			   newMap.put("SYBX", SYBXString);
			   newMap.put("GJJ", GJJString);
			   newMap.put("GHF", GHFString);
			   newMap.put("CommerceBX", CommerceBXString);
			   newMap.put("Spare", SpareString);
			   newMap.put("NSGZ", NSGZString);
			   newMap.put("GSS", GSSString);
			   newMap.put("SFGZ", SFGZString);		
			
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("id", id +"");		
			
			//flag= service.addWorkInfo(newMap); 
			service.updatePersonSalary(ids,newMap); 
			flag= true;
		
		
		}
		else if (operString!=null&&operString.equals("del")&&IDString!=null) {
			//删除
			int id=Integer.parseInt(IDString);
			Map<String, String> ids =new HashMap<String, String>();
			ids.put("id", id +"");	
			service.deletePersonSalaryById(ids);
			flag= true;
			
		}
		if (!flag) {
			result="error";
			responser.setRtType("JSON");
		}
		JSONObject resultjson = new JSONObject(); 
		resultjson.put("status", result); //返回状态
		responser.setRtString((resultjson.toString()));
		return responser;
	}
    
    
    
	public Responser getStaticSalaryForTableService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(list2AAData(service.getStaticSalaryForTable(params.getParam("rescd"),params.getParam("year"),params.getParam("department"),params.getParam("project")))));
		return responser;
		
		
	}
	
	public Responser getStaticSalaryForChartService() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		String rescdString=params.getParam("rescd");
		String depString=params.getParam("department");
		String yearString=params.getParam("year");
		String projectString=params.getParam("project");
		responser.setRtType(JSON);
		List<Map<String, Object>> result=service.getStaticSalaryForChart(rescdString, depString, yearString,projectString);
		responser.setRtString(parseJSON(list2AAData(result)));
		return responser;
	}
	
	
	public Responser queryManageList() {
		OfficeService service = (OfficeService) ServiceFactory.getService("office");
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryManageList()));
		return responser;
		
		
	}
	/**
	 * 将list转成json对象
	 * 
	 * @param list
	 * @return
	 */
	public static String list2AAData(List<Map<String, Object>> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	

	
	
}

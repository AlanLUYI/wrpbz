package net.htwater.mydemo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.json.simple.JSONObject;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.Cache;
import net.htwater.hrpf.stainterface.service.serviceImpl.StaInterfaceServiceImpl;
import net.htwater.hrpf.stainterface.util.File2String;
import net.htwater.hrpf.stainterface.util.XmlUtil;






import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 大中型水库，标准化上报接口
 * 
 * @author mcg 2016-10-05
 */
public class AReportGateServicImpl {
	
	static final String SQLSERVER1 = "HOS";
	static final String xmlsNamespace = "ProjectSupervise";
	static final String xmlsxsi = "http://www.w3.org/2001/XMLSchema-instance";

	BaseDao dao = DaoFactory.getDao(SQLSERVER1);
	StaInterfaceServiceImpl staImpl = new StaInterfaceServiceImpl();

	
	public String Sluice(String rescd, String username, String userid) {
		String strCommand = "Sluice";
		String strContent = "";
		Map<String, Object> Project_Base_Infos = this
				.getProject_Base_Infos(rescd);
		Map<String, Object> Sluice_Base_Infos = this
				.getSluice_Base_Infos(rescd);
		Map<String, Object> Sluice_Supplement_Infors = this
				.getSluice_Supplement_Infors(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Base_Infos", Project_Base_Infos);
		root.put("Sluice_Base_Infos", Sluice_Base_Infos);
		root.put("Sluice_Supplement_Infors", Sluice_Supplement_Infors);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return "";
		return this.myReceiv(rescd, username, userid, strCommand, strContent,"");
	}
	private Map<String, Object> getProject_Base_Infos(String rescd){
		
		String sql = "select '"
				+ getstarescdCD(rescd).get("Project_Code").toString()
				+ "' as Project_Code, "
				+ "'"
				+ getstarescdCD(rescd).get("Project_Name").toString()
				+ "' as Project_Name, "
				+ "'"
				+ getstarescdCD(rescd).get("Type_Code").toString()
				+ "'  as Type_Code,"
				+ "'"
				+ getstarescdCD(rescd).get("City_Code").toString()
				+ "'  as City_Code,"
				+ "'"
				+ getstarescdCD(rescd).get("County_Code").toString()
				+ "'  as County_Code,"
				+ "'"
				+ getstarescdCD(rescd).get("Town_Code").toString()
				+ "'  as Town_Code,"
				+ " LAT_D + LAT_M / 60 + LONG_S / 3600 AS Latitude,"
				+ " LONG_D + LONG_M / 60 + LONG_S / 3600 AS Longitude from P203_GATE where GCTYPE_ENNMCD='"
				+ rescd + "'";
		/*StringBuilder strSql=new StringBuilder();
		strSql.append("SELECT  CITY AS Project_Code , ");
		strSql.append("        ENNM AS Project_Name , ");
		strSql.append("        Type_Code AS Type_Code , ");
		strSql.append("        CITY AS City_Code , ");
		strSql.append("        CITY AS County_Code , ");
		strSql.append("        CITY AS Town_Code , ");
		strSql.append("        LAT_D + LAT_M / 60 + LONG_S / 3600 AS Latitude , ");
		strSql.append("        LONG_D + LONG_M / 60 + LONG_S / 3600 AS Longitude ");
		strSql.append("FROM    [dbo].[P203_GATE] ");
		strSql.append("WHERE   ENNMCD = '330283000164' ");
		strSql.append("        AND ENNM LIKE '%陡门闸%'; ");*/
		
		Map<String, Object> map=new HashMap<>();
		map=dao.executeQueryObject(sql);
		return map;
	}
	private Map<String, Object> getSluice_Base_Infos(String rescd){
		StringBuilder strSql=new StringBuilder();
		strSql.append("SELECT  VILLAGE AS Village_Name , ");
		strSql.append("        Is_River_Sluice AS Is_River_Sluice , ");
		strSql.append("        RVNM River_Name , ");
		strSql.append("        RVCD AS River_Code , ");
		strSql.append("        CONVERT(VARCHAR(7), CAST(Completion_Time AS DATETIME), 111) AS Completion_Time , ");
		strSql.append("        CONVERT(VARCHAR(7), CAST(Finish_Time AS DATETIME), 111) AS Finish_Time , ");
		strSql.append("        Is_Completion_Acceptance AS Is_Completion_Acceptance , ");
		strSql.append("        Sluice_Type AS Sluice_Type , ");
		strSql.append("        Is_Gate_Station_Project AS Is_Gate_Station_Project , ");
		strSql.append("        IS_GATES AS Is_Set_Brake_Engineering , ");
		strSql.append("        Is_Traffic AS Is_Traffic , ");
		strSql.append("        Reference_WL_Station_Name AS Reference_WL_Station_Name , ");
		strSql.append("        Reference_WL_Station_Code AS Reference_WL_Station_Code , ");
		strSql.append("        Reference_WL_Station_Warning_Level AS Reference_WL_Station_Warning_Level , ");
		strSql.append("        Reference_WL_Station_Critical_Level AS Reference_WL_Station_Critical_Level , ");
		strSql.append("        Engineering_Level_dic AS Engineering_Level , ");
		strSql.append("        Engineering_Scale_dic AS Engineering_Scale , ");
		strSql.append("        Elevation_System_dic AS Elevation_System , ");
		strSql.append("        HOLE_WIDTH AS Sluice_Hole_Total_Width , ");
		strSql.append("        HOLE_CNT AS Sluice_Hole_Number , ");
		strSql.append("        Gate_Type AS Gate_Type , ");
		strSql.append("        Gate_Size AS Gate_Size , ");
		strSql.append("        Hoist_Type AS Hoist_Type , ");
		strSql.append("        Max_Flow AS Max_Flow , ");
		strSql.append("        Sluice_Floor_Elevation AS Sluice_Floor_Elevation , ");
		strSql.append("        Design_Sluice_Upstream_Level AS Design_Sluice_Upstream_Level , ");
		strSql.append("        Check_Sluice_Upstream_Level AS Check_Sluice_Upstream_Level , ");
		strSql.append("        Biggest_Water_Level_Difference AS Biggest_Water_Level_Difference , ");
		strSql.append("        Normal_Impoundage_Level AS Normal_Impoundage_Level , ");
		strSql.append("        Meiyuflood_Period_Water_Level AS Meiyuflood_Period_Water_Level , ");
		strSql.append("        Typhoon_Period_Water_Level AS Typhoon_Period_Water_Level ");
		strSql.append("FROM    [dbo].[P203_GATE] ");
		strSql.append("WHERE   GCTYPE_ENNMCD = '"+rescd+"' ");
		//strSql.append("        AND ENNM LIKE '%陡门闸%' ");

		Map<String, Object> map=new HashMap<>();
		map=dao.executeQueryObject(strSql.toString());
		return map;
	}

	private Map<String, Object> getSluice_Supplement_Infors(String rescd){
		StringBuilder strSql=new StringBuilder();
		strSql.append("SELECT   ");
		strSql.append("       [RName] as Manage_Unit_Name ");
		strSql.append("      ,Manage_Unit_Property_dic as Manage_Unit_Property ");
		strSql.append("      ,[Adress] as  Manage_Unit_Address ");
		strSql.append("      ,[Phone] as  Manage_Unit_Phone ");
		strSql.append("      ,[Fax] as Manage_Unit_Fax ");
		strSql.append("      ,[PhoneDuty] as  Manage_Unit_Duty_Phone ");
		strSql.append("      ,[totalman] as Manage_Unit_Staff_Count ");
		strSql.append("      ,[TotalmanOnduty] as Manage_Unit_Work_Staff_Count ");
		strSql.append("      ,[Incharge] as Manage_Unit_Charge_Person_Name ");
		strSql.append("      ,[InchargePhone] as   Manage_Unit_Charge_Person_Phone ");
		strSql.append("      ,[InchargeMobile] as Manage_Unit_Charge_Person_Mobile ");
		strSql.append("      ,[supunit] as  Uplevel_Water_Gov_Department,Department_In_Charge as Department_In_Charge");
		strSql.append("  FROM [abzh].[dbo].[SYS_Reservoir]  ");
		strSql.append("  where  RESCD='"+rescd+"' ");
		
		
		Map<String, Object> map=new HashMap<>();
		map=dao.executeQueryObject(strSql.toString());
		return map;
	}
	
	public String UploadDailyPatrol(String rescd, String username,
			String userid, String formid) {
		String strCommand = "UploadDailyPatrol";
		String strContent = "";
		Map<String, Object> DailyPatrol = this
				.DailyPatrol(rescd, formid);
		//Map<String, Object> Project_Observe_Water_Level = this.Project_Observe_Water_Level(rescd, formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Patrol_Statistics_Days", DailyPatrol);
		//root.put("Project_Observe_Water_Level", Project_Observe_Water_Level);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				formid);
	}
	/**
	 * 上报当天水位信息
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	private Map<String, Object> Project_Observe_Water_Level(String rescd,String formid)
	{
		String sql="select Z as Water_Level from inspect2_form where rescd='"+rescd+"' and formid='"+formid+"'";
		
		return dao.executeQueryObject(sql);
	}
	
	/**
	 * 上报日常巡查信息
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	private Map<String, Object> DailyPatrol(String rescd,String formid)
	{
		String sql="select inspectdt as Date,'"
				+ getstarescdCD(rescd).get("Project_Code").toString()
				+ "' as Project_Code, "
				+ "updatetime as Start_Time,"
				+ "endtime as End_Time "
				+ " from inspect2_form where rescd='"+rescd+"' and formid='"+formid+"'";
		
		return dao.executeQueryObject(sql);
	}

	public String UploadDanger(String rescd, String username, String userid,
			String formid) throws IOException {
		String strCommand = "UploadDanger";
		String strContent = "";
		Map<String, Object> Danger_Records = this.Danger_Records_before(rescd,
				formid);
		List<Map<String, Object>> Danger_File = this.Danger_File_before(rescd,
				formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Danger_Records", Danger_Records);
		root.put("File", Danger_File);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			 System.out.println(strContent);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				formid);
	}
	/**
	 * 隐患内容（发现上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	
	private Map<String, Object> Danger_Records_before(String rescd, String formid) {
		String sql = "select '"
				+ getstarescdCD(rescd).get("Project_Code").toString()
				+ "' as Project_Code," + " s_content as Description,"
				+ " elementnm as Danger_Name,"
				+ " regionnm as Danger_Position,"
				+ " s_wayid as Discover_Type,"
				+ " s_dt as Discover_Time from el_handleform where rescd='"
				+ rescd + "' and formid='" + formid + "'";
		return dao.executeQueryObject(sql);
	}

	/**
	 * 隐患文件（发现上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 * @throws IOException
	 */
	
	private List<Map<String, Object>> Danger_File_before(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String path = path1 + "element/" + rescd + "/handleformatta/";
		String pathpic = Cache.uploadRoot + "element/" + rescd
				+ "/handleformpic/";
		String sql = "SELECT filename,orgname FROM dbo.el_handleform_atta WHERE type='atta' and rescd='"
				+ rescd + "' and formid='" + formid + "'";
		List<Map<String, Object>> data = dao.executeQuery(sql);
		String sqlpic = "SELECT filename,orgname FROM dbo.el_handleform_atta WHERE type='pic' and rescd='"
				+ rescd + "' and formid='" + formid + "'";
		List<Map<String, Object>> datapic = dao.executeQuery(sqlpic);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", re.get("orgname").toString());
				map.put("Data",
						File2String.getImageStrBase64(path
								+ re.get("filename").toString()));
				result.add(map);
			}
		} 
		if (datapic.size() > 0) {
			for (Map<String, Object> re : datapic) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", re.get("orgname").toString());
				map.put("Data",
						File2String.getImageStrBase64(pathpic
								+ re.get("filename").toString()));
				result.add(map);
			}
		} 
		return result;
	}

	public String UploadDangerDealSituation(String rescd, String username,
			String userid, String formid) throws IOException {
		String strCommand = "UploadDangerDealSituation";
		String strContent = "";
		Map<String, Object> Danger_Records = this.Danger_Records_after(rescd,
				formid);
		List<Map<String, Object>> Danger_File = this.Danger_File_after(rescd,
				formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Danger_Records", Danger_Records);
		root.put("File", Danger_File);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				formid);
	}
	/**
	 * 隐患内容（处理上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	
	private Map<String, Object> Danger_Records_after(String rescd, String formid) {
		String sql = "select (SELECT TOP 1  stakey FROM dbo.stalog WHERE formid='"+formid+"' AND [strCommand]='UploadDanger' ORDER BY stakey desc) as Danger_ID, "
				+ "responsiblenm as Treatment_Charge_Person,"
				+ "(SELECT phone FROM dbo.o_user WHERE o_user.id=responsible) as Treatment_Charge_Person_Phone,"
				+ "actual_finishtime as Actual_Completion_Time,"
				+ "Deal_Type_Code as Deal_Type,"
				+ "s_lastdt as Plan_Complete_Time from el_handleform where rescd='"
				+ rescd + "' and formid='" + formid + "'";
		return dao.executeQueryObject(sql);
	}

	/**
	 * 隐患文件（处理上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 * @throws IOException
	 */
	private String path1="";
	private List<Map<String, Object>> Danger_File_after(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String path = path1 + "element/" + rescd
				+ "/handleformpic_Com/";
		String sql = "SELECT filename,orgname FROM dbo.el_handleform_atta WHERE type='c_pic' and rescd='"
				+ rescd + "' and formid='" + formid + "'";

		List<Map<String, Object>> data = dao.executeQuery(sql);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", re.get("orgname").toString());
				map.put("Data",
						File2String.getImageStrBase64(path
								+ re.get("filename").toString()));
				result.add(map);
			}
		} 
		return result;
	}
	
	/**
	 * 工程保护范围线
	 * 
	 * @param rescd
	 * @return
	 */
	public String UploadGeographicCoordinates(String rescd,String lineType, String username,
			String userid) {
		String strCommand = "UploadGeographicCoordinates";
		String strContent = "";
		Map<String, Object> Project_Geographic_Coordinates = this
				.Project_Geographic_Coordinates(rescd,lineType);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Geographic_Coordinates",
				Project_Geographic_Coordinates);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return "";
		return this.myReceiv(rescd, username, userid, strCommand, strContent,"");
	}
	/**
	 * 工程保护范围线
	 * 
	 * @param rescd
	 * @return
	 */
	private Map<String, Object> Project_Geographic_Coordinates(String rescd,String lineType) {
		String sql = "select SmX,SmY,Category_Code,'"+getstarescdCD(rescd).get("Project_Code").toString()+"' as Project_Code "
				+ " from HJQQ where type='"+lineType+"' and  GCTYPE_ENNMCD='"+rescd+"'";
		List<Map<String, Object>> result = dao.executeQuery(sql);
		Map<String, Object> map = new HashMap<String, Object>();
		if(result.size()>0)
		{
			map.put("Category_Code", result.get(0).get("Category_Code"));
			map.put("Project_Code", result.get(0).get("Project_Code"));
			//Map<String, Object> map_zb = new HashMap<String, Object>();
			List<Object> map_zb = new ArrayList<Object>();
			for (Map<String, Object> re : result) {
				List<Double> a = new ArrayList<Double>();
				a.add(Double.parseDouble(re.get("SmY").toString()));
				a.add(Double.parseDouble(re.get("SmX").toString()));
				map_zb.add(a);
			}
			Map<String, Object> b = new HashMap<String, Object>();
			Map<String, Object> c = new HashMap<String, Object>();
			b.put("paths", map_zb);
			c.put("wkid", result.size());
			b.put("spatialReference",c) ;
			map.put("Coordinates", JSONObject.toJSONString(b));
		}
		else
		{
			/*map.put("Category_Code", "3461BDD9-E803-47A1-B8B1-9F785602995F");
			map.put("Project_Code", getstarescdCD(rescd).get("Project_Code").toString());
			map.put("Coordinates", null);*/
		}
		
		return map;
	}
	
	public String UploadProjectMaintenanceItem(String rescd, String username,
			String userid, String formid) throws IOException {
		String strCommand = "UploadProjectMaintenanceItem";
		String strContent = "";
		Map<String, Object> Project_Year_Maintenance_Items = this
				.Project_Year_Maintenance_Items(rescd, formid);
		List<Map<String, Object>> Before_File = this.Maintenance_File_before(
				rescd, formid);
		List<Map<String, Object>> After_File = this.Maintenance_File_after(
				rescd, formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Year_Maintenance_Items",
				Project_Year_Maintenance_Items);
		root.put("Before_File", Before_File);
		root.put("After_File", After_File);
		
		System.out.println(root);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				formid);
	}
	/**
	 * 维修养护事项
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	private Map<String, Object> Project_Year_Maintenance_Items(String rescd,
			String formid) {
		String sql = "select prjname as Item_Name,'"+getstarescdCD(rescd).get("Project_Code").toString()+"' as Project_Code,"
				+ "YEAR([sdt]) AS Year,sdt as  Item_Plan_Start_Time,edt as Item_Plan_Complete_Time from  wxyh_prj where rescd='"+rescd+"' and prjid='"+formid+"'  ";
		return dao.executeQueryObject(sql);
	}
	/**
	 * 维修养护事项(前 文件)
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 * @throws IOException 
	 */
	private List<Map<String, Object>> Maintenance_File_before(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String path = Cache.uploadRoot  + rescd
				+ "/wxyh_prj/doc/";
		String sql = " SELECT a.[filename],orgname FROM wxyh_prj_atta AS a LEFT JOIN dbo.wxyh_prj_doc ON docid=formid WHERE filetypeid='beforePic' and a.rescd='"
				+ rescd + "' and a.prjid ='" + formid + "'";

		List<Map<String, Object>> data = dao.executeQuery(sql);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", re.get("orgname").toString());
				
				map.put("Data",
						File2String.getImageStrBase64(path
								+ re.get("filename").toString()));
				result.add(map);
			}
		} 
		System.out.println(result);
		/*else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Name", null);
			map.put("Data", null);
			result.add(map);
		}*/

		return result;
	}

	/**
	 * 维修养护事项(后 文件)
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 * @throws IOException 
	 */
	private List<Map<String, Object>> Maintenance_File_after(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String path = Cache.uploadRoot  + rescd
				+ "/wxyh_prj/doc/";
		String sql = " SELECT a.[filename],orgname FROM wxyh_prj_atta AS a LEFT JOIN dbo.wxyh_prj_doc ON docid=formid WHERE filetypeid='afterPic' and a.rescd='"
				+ rescd + "' and a.prjid ='" + formid + "'";

		List<Map<String, Object>> data = dao.executeQuery(sql);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", re.get("orgname").toString());
				map.put("Data",
						File2String.getImageStrBase64(path
								+ re.get("filename").toString()));
				result.add(map);
			}
		} 
		
		return result;
	}
	public String UploadProjectYearUsedFee(String rescd, String username,
			String userid, String formid) {
		String strCommand = "UploadProjectYearUsedFee";
		String strContent = "";
		Map<String, Object> Project_Year_Maintenance_Fee = this
				.Project_Year_Maintenance_Fee(rescd, formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Year_Maintenance_Fee", Project_Year_Maintenance_Fee);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				formid);
	}
	public String UploadDanger_other(String rescd, String username, String userid,
			String formid) throws IOException {
		String strCommand = "UploadDanger";
		String strContent = "";
		Map<String, Object> Danger_Records = this.Danger_Records_before_other(rescd,
				formid);
		List<Map<String, Object>> Danger_File = this.Danger_File_before_other(rescd,
				formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Danger_Records", Danger_Records);
		root.put("File", Danger_File);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			 System.out.println(strContent);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				formid);
	}
	/**
	* 其他隐患内容（发现上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Danger_Records_before_other(String rescd, String formid) {
		String sql = "select '"
				+ getstarescdCD(rescd).get("Project_Code").toString()
				+ "' as Project_Code," + " s_content as Description,"
				+ " elementnm as Danger_Name,"
				+ " regionnm as Danger_Position,"
				+ " s_wayid as Discover_Type,"
				+ " s_dt as Discover_Time from el_handleform_other where rescd='"
				+ rescd + "' and formid='" + formid + "'";
		return dao.executeQueryObject(sql);
	}
	/**
	 * 隐患文件（发现上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 * @throws IOException
	 */
	public List<Map<String, Object>> Danger_File_before_other(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String path = Cache.uploadRoot + "element/" + rescd + "/handleformatta/";
		String pathpic = Cache.uploadRoot + "element/" + rescd
				+ "/handleformpic/";
		String sql = "SELECT filename,orgname FROM dbo.el_handleform_atta_other WHERE type='atta' and rescd='"
				+ rescd + "' and formid='" + formid + "'";
		List<Map<String, Object>> data = dao.executeQuery(sql);
		String sqlpic = "SELECT filename,orgname FROM dbo.el_handleform_atta_other WHERE type='pic' and rescd='"
				+ rescd + "' and formid='" + formid + "'";
		List<Map<String, Object>> datapic = dao.executeQuery(sqlpic);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", re.get("orgname").toString());
				map.put("Data",
						File2String.getImageStrBase64(path
								+ re.get("filename").toString()));
				result.add(map);
			}
		} 
		if (datapic.size() > 0) {
			for (Map<String, Object> re : datapic) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", re.get("orgname").toString());
				map.put("Data",
						File2String.getImageStrBase64(pathpic
								+ re.get("filename").toString()));
				result.add(map);
			}
		} 
//		if(data.size()==0&&datapic.size()==0){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("Name", null);
//			map.put("Data", null);
//			result.add(map);
//		}
		return result;
	}
	public String UploadDangerDealSituation_other(String rescd, String username,
			String userid, String formid) throws IOException {
		String strCommand = "UploadDangerDealSituation";
		String strContent = "";
		Map<String, Object> Danger_Records = this.Danger_Records_after_other(rescd,
				formid);
		List<Map<String, Object>> Danger_File = this.Danger_File_after_other(rescd,
				formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Danger_Records", Danger_Records);
		root.put("File", Danger_File);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				formid);
	}
	/**
	 * 其他隐患内容（处理上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Danger_Records_after_other(String rescd, String formid) {
		String sql = "select (SELECT TOP 1  stakey FROM dbo.stalog WHERE formid='"+formid+"' AND [strCommand]='UploadDanger' ORDER BY stakey desc) as Danger_ID, "
				+ "h_personnm as Treatment_Charge_Person,"
				+ "(SELECT phone FROM dbo.o_user WHERE o_user.id=responsible) as Treatment_Charge_Person_Phone,"
				+ "actual_finishtime as Actual_Completion_Time,"
				+ "Deal_Type_Code as Deal_Type,"
				+ "s_lastdt as Plan_Complete_Time from el_handleform_other where rescd='"
				+ rescd + "' and formid='" + formid + "'";
		return dao.executeQueryObject(sql);
	}
	/**
	 * 隐患文件（处理上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 * @throws IOException
	 */
	public List<Map<String, Object>> Danger_File_after_other(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String path = Cache.uploadRoot + "element/" + rescd
				+ "/handleformpic_Com/";
		String sql = "SELECT filename,orgname FROM dbo.el_handleform_atta_other WHERE type='c_pic' and rescd='"
				+ rescd + "' and formid='" + formid + "'";
		List<Map<String, Object>> data = dao.executeQuery(sql);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", re.get("orgname").toString());
				map.put("Data",
						File2String.getImageStrBase64(path
								+ re.get("filename").toString()));
				result.add(map);
			}
		} 
		/*else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Name", null);
			map.put("Data", null);
			result.add(map);
		}*/
		return result;
	}
	/**
	 * 年度已使用维修养护资金
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	private Map<String, Object> Project_Year_Maintenance_Fee(String rescd,
			String formid) {
		String sql = "select '"+getstarescdCD(rescd).get("Project_Code").toString()+"' as Project_Code,"
				+ "YEAR([year]) AS Year,Distributed_Fund,Raised_Fund,"
				+ "Implement_Fee,Paid_Fee from funds_file where rescd='"+rescd+"' and id='"+formid+"'  ";
		return dao.executeQueryObject(sql);
	}

	/**
	 * 获取ADCD
	 * 
	 * @param rescd
	 * @return
	 */
 	public String getADCD(String rescd) {
		String sql = "select * from starescdCD where rescd = '" + rescd + "'";
		Map<String, Object> map = dao.executeQueryObject(sql);
		if (map.get("ADCD") != null)
			return map.get("ADCD").toString();
		else {
			System.out.println(rescd + "未在starescdCD中配置ADCD");
			return "";
		}
	}

	/**
	 * 获取水库基本信息
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> getstarescdCD(String rescd) {
		String sql = "select * from starescdCD where rescd = '" + rescd + "'";
		Map<String, Object> map = dao.executeQueryObject(sql);
		return map;
	}

	/**
	 * 当前时间
	 * 
	 * @return
	 */
	public static String gettime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNowStr = sdf.format(d);
		return dateNowStr;
	}

	/**
	 * 上报
	 * 
	 * @param rescd
	 * @param username
	 * @param userid
	 * @param strCommand
	 * @param strContent
	 * @param formid
	 * @return
	 */
	public String myReceiv(String rescd, String username, String userid,
			String strCommand, String strContent, String formid) {
		String result = "";
		String _xml = staImpl.Receive(rescd, userid, strCommand, strContent);
		 System.out.println(strContent);
		Map amapMap = null;
		try {
			amapMap = XmlUtil.xml2mapWithAttr(_xml, true);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> d = (Map<String, Object>) amapMap.get("root");
		result = d.get("result").toString();
		String _key = d.get("key").toString();
		String _time = d.get("time").toString();
		staImpl.stalog(rescd, username, userid, strCommand, strContent, result,
				_key, _time, formid);
		return result;
	}

	public static void main(String[] args) throws ParseException {
		String rescd="gc_dzxsz-330283000164";
		String username="fhslj";
		String userid="53AE7EF2-484E-4A2A-9105-44765510603B";
		
		//String lineType="GLFW";
		new AReportGateServicImpl().Sluice(rescd, username, userid);
		//new AReportGateServicImpl().UploadGeographicCoordinates(rescd, lineType, username, userid);
	}
}

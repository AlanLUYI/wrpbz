package net.htwater.mydemo.service.impl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.htwater.Util.uploadDoc;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.json.simple.JSONObject;

import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.sun.org.apache.xpath.internal.operations.And;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.Cache;
import net.htwater.hrpf.stainterface.service.serviceImpl.StaInterfaceServiceImpl;
import net.htwater.hrpf.stainterface.util.File2String;
import net.htwater.hrpf.stainterface.util.XmlUtil;
import net.htwater.mydemo.service.BusinessServic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *海塘，标准化上报接口
 * 
 * @author
 */
public class BusinessServicImpl implements BusinessServic {

	BaseDao dao = DaoFactory.getDao(SQLSERVER1);
	static StaInterfaceServiceImpl staImpl = new StaInterfaceServiceImpl();
	String path="D:/Java/tomcat/apache-tomcat-7.0.42/webapps/ROOT/qgjFiles/";

	@Override
	public List<Map<String, Object>> getHistory(String rescd, String userid,
			String business) {
		
		
		String sql = "select *, strContent as ostrContent from stalog where strCommand ='"
				+ business + "' order by time desc ";
		List<Map<String, Object>> lst = dao.executeQuery(sql);
		for (Map<String, Object> map : lst) {
			if (map.get("strContent") != null) {
				try {
					map.put("strContent",
							XmlUtil.formatXml(map.get("strContent").toString()));
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lst;
	}

	@Override
	public String getReservoirLM(String rescd, String username, String userid) {
		String ret = "";
		Map<String, Object> Project_Base_Infos = this
				.get_Project_Base_Infos(rescd);
		Map<String, Object> Reservoir_LM_Base_Infos = this
				.get_Seawall_Base_Infos(rescd);
		Map<String, Object> Reservoir_LM_Supplement_Infos = this
				.get_Seawall_Supplement_infos(rescd);
		Map<String, Object> Project_Reinforcements = this
				.get_Project_Patrollers(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Base_Infos", Project_Base_Infos);
		root.put("Reservoir_LM_Base_Infos", Reservoir_LM_Base_Infos);
		root.put("Reservoir_LM_Supplement_Infos", Reservoir_LM_Supplement_Infos);
		root.put("Project_Reinforcements", Project_Reinforcements);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			ret = XmlUtil.formatXml(doc.asXML().toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public String getDike(String rescd, String username, String userid) {
		String ret = "";
		Map<String, Object> Project_Base_Infos = this
				.Project_Base_Infos(rescd);
		Map<String, Object> Dike_Base_Infos = this
				.Dike_Base_Infos(rescd);
		Map<String, Object> Dike_Supplement_Infos = this
				.Dike_Supplement_Infos(rescd);
		Map<String, Object> Project_Patrollers = this
				.Project_Patrollers(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Base_Infos", Project_Base_Infos);
		root.put("Dike_Base_Infos", Dike_Base_Infos);
		root.put("Dike_Supplement_Infos", Dike_Supplement_Infos);
		root.put("Project_Patrollers", Project_Patrollers);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			ret = XmlUtil.formatXml(doc.asXML().toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public String Seawall(String rescd, String username, String userid) {
		String strCommand = "Seawall";
		String strContent = "";
		Map<String, Object> Project_Base_Infos = this
				.get_Project_Base_Infos(rescd);
		Map<String, Object> Seawall_Base_Infos = this
				.get_Seawall_Base_Infos(rescd);
		Map<String, Object> Seawall_Supplement_infos = this
				.get_Seawall_Supplement_infos(rescd);
		Map<String, Object> Project_Patrollers = this
				.get_Project_Patrollers(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Base_Infos", Project_Base_Infos);
		root.put("Seawall_Base_Infos", Seawall_Base_Infos);
		root.put("Seawall_Supplement_infos", Seawall_Supplement_infos);
		root.put("Project_Patrollers", Project_Patrollers);
		
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,"");
	}

	@Override
	public String getUploadFacilityCount(String rescd, String username,
			String userid) {
		String ret = "";
		Map<String, Object> Project_Facility_Count = this
				.Project_Facility_Count(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Facility_Count", Project_Facility_Count);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			ret = XmlUtil.formatXml(doc.asXML().toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public String UploadFacilityCount(String rescd, String username,
			String userid) {
		String strCommand = "UploadFacilityCount";
		String strContent = "";
		Map<String, Object> Project_Facility_Count = this
				.Project_Facility_Count(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Facility_Count", Project_Facility_Count);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				"");
	}

	@Override
	public String UploadDispatchSituation(String rescd, String username,
			String userid, String formid) throws IOException {
		String strCommand = "UploadDispatchSituation";
		String strContent = "";
		Map<String, Object> Dispatch_Situation = this.Dispatch_Situation(rescd,
				formid);
		List<Map<String, Object>> Dispatch_Situation_File = this
				.Dispatch_Situation_File(rescd, formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Dispatch_Situation", Dispatch_Situation);
		root.put("File", Dispatch_Situation_File);
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

	@Override
	public String UploadDanger(String rescd, String username, String userid,
			String formid) throws IOException {
		String strCommand = "UploadDanger";
		String strContent = "";
		Map<String, Object> Danger_Records = this.Danger_Records_before(rescd,formid);
		List<Map<String, Object>> Danger_File = this.Danger_File_before(rescd,formid);
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
			System.out.println(strContent);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				formid);
	}

	@Override
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
	
	@Override
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
	
	@Override
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

	@Override
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

	@Override
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

	@Override
	public String UploadDike(String rescd, String username,
			String userid, String formid) {
		String strCommand = "Dike";
		String strContent = "";
		Map<String, Object> Project_Base_Infos = this
				.Project_Base_Infos(rescd);
		Map<String, Object> Dike_Base_Infos = this
				.Dike_Base_Infos(rescd);
		Map<String, Object> Dike_Supplement_Infos = this 
				.Dike_Supplement_Infos(rescd);
		Map<String, Object> Project_Patrollers = this
				.Project_Patrollers(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Base_Infos", Project_Base_Infos);
		root.put("Dike_Base_Infos", Dike_Base_Infos);
		root.put("Dike_Supplement_Infos", Dike_Supplement_Infos);
		root.put("Project_Patrollers", Project_Patrollers);
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
	
	@Override
	public String UploadDailyPatrol(String rescd, String username,
			String userid, String formid) {
		String strCommand = "UploadDailyPatrol";
		String strContent = "";
		Map<String, Object> DailyPatrol = this
				.DailyPatrol(rescd, formid);
		Map<String, Object> Project_Observe_Water_Level = this
				.Project_Observe_Water_Level(rescd, formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Patrol_Statistics_Days", DailyPatrol);
		root.put("Project_Observe_Water_Level", Project_Observe_Water_Level);
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
	
	@Override
	public String UploadRecentReinforcements(String rescd, String username,
			String userid, String formid) {
		String strCommand = "UploadRecentReinforcements";
		String strContent = "";
		Map<String, Object> Project_Reinforcements = this
				.Project_Reinforcements_only(rescd, formid);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Reinforcements", Project_Reinforcements);
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
	
	
	@Override
	public String getGeographicCoordinates1(String rescd, String username, String userid) {
		String ret = "";
		Map<String, Object> Project_Geographic_Coordinates = this
				.Project_Geographic_Coordinates1(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Geographic_Coordinates", Project_Geographic_Coordinates);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			ret = XmlUtil.formatXml(doc.asXML().toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public String UploadGeographicCoordinates0(String rescd, String username,
			String userid) {
		String strCommand = "UploadGeographicCoordinates";
		String strContent = "";
		Map<String, Object> Project_Geographic_Coordinates = this
				.Project_Geographic_Coordinates0(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Geographic_Coordinates",
				JSONObject.toJSONString(Project_Geographic_Coordinates));
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				"");
	}

	@Override
	public String UploadGeographicCoordinates1(String rescd, String username,
			String userid) {
		String strCommand = "UploadGeographicCoordinates";
		String strContent = "";
		Map<String, Object> Project_Geographic_Coordinates = this
				.Project_Geographic_Coordinates1(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Geographic_Coordinates",
				JSONObject.toJSONString(Project_Geographic_Coordinates));
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			strContent = doc.asXML().toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				"");
	}

	@Override
	public String getGeographicCoordinates2(String rescd, String username, String userid) {
		String ret = "";
		Map<String, Object> Project_Geographic_Coordinates = this
				.Project_Geographic_Coordinates2(rescd);
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("@ADCD", this.getADCD(rescd));
		root.put("@time", gettime());
		root.put("@xsi:schemaLocation", "");
		root.put("@xmlns:xsi", xmlsxsi);
		root.put("Project_Geographic_Coordinates", Project_Geographic_Coordinates);
		try {
			Document doc = XmlUtil.map2xml(root, "root", xmlsNamespace);
			ret = XmlUtil.formatXml(doc.asXML().toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public String UploadGeographicCoordinates2(String rescd, String username,
			String userid) {
		String strCommand = "UploadGeographicCoordinates";
		String strContent = "";
		Map<String, Object> Project_Geographic_Coordinates = this
				.Project_Geographic_Coordinates2(rescd);
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
		return this.myReceiv(rescd, username, userid, strCommand, strContent,
				"");
	}

	/**
	 * 基本信息1
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> get_Project_Base_Infos(String rescd) {
		Map<String, Object> baseMap=new LinkedHashMap<String, Object>();
		baseMap.put("Project_Code", getstarescdCD(rescd).get("Project_Code").toString());
		baseMap.put("Project_Name", getstarescdCD(rescd).get("Project_Name").toString());
		baseMap.put("Type_Code", getstarescdCD(rescd).get("Type_Code").toString());
		baseMap.put("City_Code", getstarescdCD(rescd).get("City_Code").toString());
		baseMap.put("County_Code", getstarescdCD(rescd).get("County_Code").toString());
		
		String sql = "select Seawall_Start_Latitude Latitude,Seawall_Start_Longitude Longitude from [海塘工程信息]  where rescd = '"+rescd+"' ";
		Map<String, Object> map_coor=new LinkedHashMap<String, Object>();
		map_coor=dao.executeQueryObject(sql);
		
		baseMap.put("Latitude", map_coor.get("Latitude").toString());
		baseMap.put("Longitude", map_coor.get("Longitude").toString());
		return baseMap;
	}

	/**
	 * 基本信息2
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> get_Seawall_Base_Infos(String rescd) {
		String sql = "SELECT Seawall_Type, Seawall_Level, Tide_Station_Code, Tide_Station_Name, Tide_Station_Blue_Level, "
                     +" Tide_Station_Yellow_Level, Tide_Station_Orange_Level, Tide_Station_Red_Level, Project_Completion, Start_Date, Completion_Date, Finish_Time, Seawall_Length, "
                     +"  Reach_Standard_Length, Start_Location, Start_Pile_No, Seawall_Start_Latitude, Seawall_Start_Longitude, Final_Location, Final_Pile_No, Seawall_Final_Latitude, "
                     +"  Seawall_Final_Longitude, Start_Crest_Altitude, Final_Crest_Altitude, Design_Tide_Level, Design_Tide_Standard, Prevent_Tide_Standard, Sluice_Count, "
                     +"  Protection_Population, Protection_Key_Facility"
				+ " FROM dbo.[海塘工程信息] where rescd='"+rescd+"'";
		return dao.executeQueryObject(sql);
	}

	/**
	 * 基本信息3
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> get_Seawall_Supplement_infos(String rescd) {
		String unitString="";
		if(Integer.parseInt(rescd) <= 6 ){ //杭州管理处包括001-006海塘
			unitString="hzglc";
		}else if(Integer.parseInt(rescd) > 6 && Integer.parseInt(rescd) <= 8){
			unitString="jxglc";
		}else if(Integer.parseInt(rescd) > 8 && Integer.parseInt(rescd) <= 13){
			unitString="nsglc";
		}
		String sql = "select RName as Manage_Unit_Name, "
				+ "'C9CEDD65-D5BC-4D0F-A967-63E0E8BAB135' as Manage_Unit_Property,"
				+ "Adress as Manage_Unit_Address,"
				+ "Phone as Manage_Unit_Phone,"
				+ "Fax as Manage_Unit_Fax,"
				+ "zbPhone as Manage_Unit_Duty_Phone,"
				+ "totalstaff as Manage_Unit_Staff_Count,"
				+ "onlinestaff as Manage_Unit_Work_Staff_Count,"
				+ "Incharge as Manage_Unit_Charge_Person_Name,"
				+ "InchargeTel as Manage_Unit_Charge_Person_Mobile,"
				+ "InchargePhone as Manage_Unit_Charge_Person_Phone,zgUnit as Department_In_Charge,"
				+ "sjzgUnit as Uplevel_Water_Gov_Department,"
			
				+ "flood_chargePerson as Flood_Control_Charge_Person,"
				+ "flood_chargePost as Flood_Control_Charge_Person_Position"

				+ " from 单位信息 where rescd='"+unitString+"'";
		return dao.executeQueryObject(sql);
	}

	/**
	 * 基本信息4
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> get_Project_Patrollers(String rescd) {
		String sql = "select xcyName as Patroller_Name,cxyPhone as Patroller_Mobile from 海塘安全责任人"
				+ " where rescd = '"+rescd+"'";
		Map<String, Object> map = dao.executeQueryObject(sql);
		return map;
	}

	/**
	 * 隐患内容（发现上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Danger_Records_before(String rescd, String formid) {
		String sql = "select '"
				+ getstarescdCD(rescd).get("Project_Code").toString()
				+ "' as Project_Code," + " description as Description,"
				+ " name as Danger_Name,"
				+ " location as Danger_Position,"
				+ " 'B48A9BE2-EDCE-45BA-9F57-0C6CD7651B90' as Discover_Type,"
				+ " tm as Discover_Time from 隐患处理 where htcd='"
				+ rescd +"'";
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
	public List<Map<String, Object>> Danger_File_before(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		String sql = "SELECT pic FROM dbo.隐患处理 WHERE htcd='" + rescd +"'";
		List<Map<String, Object>> data = dao.executeQuery(sql);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				if(re.get("pic") != null){
					map.put("Name", re.get("pic").toString());
					map.put("Data", File2String.getImageStrBase64(path + re.get("pic").toString()));
				}
				result.add(map);
			}
		} 

		return result;
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
	/**
	 * 隐患内容（处理上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Danger_Records_after(String rescd, String formid) {
		String sql = "select (SELECT TOP 1  stakey FROM dbo.stalog WHERE [strCommand]='UploadDanger' ORDER BY stakey desc) as Danger_ID, "
				+ " chargePerson as Treatment_Charge_Person,"
				+ " chargePhone as Treatment_Charge_Person_Phone,"
				+ " realTm as Actual_Completion_Time,"
				+ " '9107AFAF-FCEC-4888-86A1-5C51737DCD58' as Deal_Type,"
				+ " dealTm as Plan_Complete_Time from  隐患处理  where htcd='"
				+ rescd + "'";
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
	public List<Map<String, Object>> Danger_File_after(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		String sql = "SELECT dealPic FROM dbo.隐患处理 WHERE htcd='"
				+ rescd + "'";

		List<Map<String, Object>> data = dao.executeQuery(sql);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				if(re.get("dealPic") != null){
					map.put("Name", re.get("dealPic").toString());
					map.put("Data", File2String.getImageStrBase64(path + re.get("dealPic").toString()));
				}
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
	 * 其他隐患内容（处理上报）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Danger_Records_after_other(String rescd, String formid) {
		String sql = "select (SELECT TOP 1  stakey FROM dbo.stalog WHERE formid='"+formid+"' AND [strCommand]='UploadDanger' ORDER BY stakey desc) as Danger_ID, "
				+ "h_personnm as Treatment_Charge_Person,"
				+ "h_phone as Treatment_Charge_Person_Phone,"
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
	 * 调度运行情况
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Dispatch_Situation(String rescd, String formid) {
		String sql = "select '"
				+ getstarescdCD(rescd).get("Project_Code").toString()
				+ "' as Project_Code,"
				+ " Sluice_Gate_Name,Sluice_Gate_Code,"
				+ "name as Dispatch_Order_ID,"
				+ "formtm as  Dispatch_Order_Time,"
				+ "sdt as Dispatch_Order_Open_Time,"
				+ "edt as Dispatch_Order_Close_Time,"
				+ "Q as Dispatch_Order_Discharge_Flow,"
				+ "(select sdt from ddOperation where zltzid=zhiling.id) as  Dispatch_Actual_Open_Time,"
				+ "(select Q from ddOperation where zltzid=zhiling.id) as  Dispatch_Actual_Discharge_Flow,"
				+ "(select edt from ddOperation where zltzid=zhiling.id) as  Dispatch_Actual_Close_Time "
				+ "from zhiling where rescd='" + rescd + "' and id='" + formid
				+ "'";
		return dao.executeQueryObject(sql);
	}

	/**
	 * 调度运行情况（文件）
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 * @throws IOException 
	 */
	public List<Map<String, Object>> Dispatch_Situation_File(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String path = Cache.uploadRoot + rescd + "/zhiling/";

		String sql = "SELECT filename,"
				+ "orgname,"
				+ "(SELECT classificationid FROM dbo.zhiling WHERE id=zhiling_atta.formid ) AS classificationid "
				+ " FROM dbo.zhiling_atta where formid='"+formid+"'";

		List<Map<String, Object>> data = dao.executeQuery(sql);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", re.get("orgname").toString());
				map.put("Data",
						File2String.getImageStrBase64(path+re.get("classificationid").toString()+"/"
								+ re.get("filename").toString()));
				result.add(map);
			}
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Name", null);
			map.put("Data", null);
			result.add(map);
		}
		return result;
	}

	/**
	 * 监测设备数量
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> Project_Facility_Count(String rescd) {
		String sql = "select  (select COUNT(*) from B_Device AS a where a.apply='启用' and  a.RESCD='"+rescd+"') as Facility_Count,"
				+ "(select COUNT(*) from B_Device AS a where a.apply='启用' and  a.RESCD='"+rescd+"' and state =1) as Normal_Facility_Count,"
						+ "'"
				+ getstarescdCD(rescd).get("Project_Code").toString()
				+ "' as Project_Code from B_Device";
		return dao.executeQueryObject(sql);
	}

	/**
	 *海塘塘线
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> Project_Geographic_Coordinates0(String rescd) {
		String sql = "select SmX,SmY, '832637F2-F6BE-4A3B-8C5F-22928636526E' as Category_Code,'"+getstarescdCD(rescd).get("Project_Code").toString()+"' as Project_Code "
				+ " from 海塘线坐标 where htcd='"+rescd+"' and type = '堤轴线' order by ID ";
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
				a.add(Double.parseDouble(re.get("SmX").toString()));
				a.add(Double.parseDouble(re.get("SmY").toString()));
				map_zb.add(a);
			}
			Map<String, Object> b = new HashMap<String, Object>();
			Map<String, Object> c = new HashMap<String, Object>();
			List<Object> map_b = new ArrayList<Object>();
			map_b.add(map_zb);
			b.put("paths", map_b);
			c.put("wkid", "4326");
			b.put("spatialReference", c);
			map.put("Coordinates", JSONObject.toJSONString(b));
		}
		
		return map;
	}
	
	/**
	 * 工程管理范围线
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> Project_Geographic_Coordinates1(String rescd) {
		String sql = "select SmX,SmY, '5A4441D5-6A4D-4569-A9D0-A9EB0A2026DC' as Category_Code,'"+getstarescdCD(rescd).get("Project_Code").toString()+"' as Project_Code "
				+ " from 海塘线坐标 where htcd='"+rescd+"' and type = '管理范围线' order by ID ";
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
				a.add(Double.parseDouble(re.get("SmX").toString()));
				a.add(Double.parseDouble(re.get("SmY").toString()));
				map_zb.add(a);
			}
			Map<String, Object> b = new HashMap<String, Object>();
			Map<String, Object> c = new HashMap<String, Object>();
			List<Object> map_b = new ArrayList<Object>();
			map_b.add(map_zb);
			b.put("paths", map_b);
			c.put("wkid", "4326");
			b.put("spatialReference", c);
			map.put("Coordinates", JSONObject.toJSONString(b));
		}
		
		return map;
	}

	/**
	 * 工程保护范围线
	 * 
	 * @param rescd
	 * @return
	 */
	public Map<String, Object> Project_Geographic_Coordinates2(String rescd) {
		String sql = "select SmX,SmY, '3461BDD9-E803-47A1-B8B1-9F785602995F' as Category_Code,'"+getstarescdCD(rescd).get("Project_Code").toString()+"' as Project_Code "
				+ " from 海塘线坐标  where htcd='"+rescd+"' and type = '保护范围线' order by ID ";
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
				a.add(Double.parseDouble(re.get("SmX").toString()));
				a.add(Double.parseDouble(re.get("SmY").toString()));
				map_zb.add(a);
			}
			Map<String, Object> b = new HashMap<String, Object>();
			Map<String, Object> c = new HashMap<String, Object>();
			List<Object> map_b = new ArrayList<Object>();
			map_b.add(map_zb);
			b.put("paths", map_b);
			c.put("wkid", "4326");
			b.put("spatialReference", c);
			map.put("Coordinates", JSONObject.toJSONString(b));
		}
		
		
		return map;
	}

	/**
	 * 维修养护事项
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Project_Year_Maintenance_Items(String rescd,
			String formid) {
		String sql = "select proNM as Item_Name,'"+getstarescdCD(rescd).get("Project_Code").toString()+"' as Project_Code,"
				+ "YEAR([plan_startTM]) AS Year,[plan_startTM] as Item_Plan_Start_Time,[plan_finishTM] as Item_Plan_Complete_Time from  维修养护项目 where htcd='"+rescd+"'";
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
	public List<Map<String, Object>> Maintenance_File_before(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		String sql = " SELECT [pic] FROM dbo.维修养护项目  WHERE htcd='"
				+ rescd + "'";

		List<Map<String, Object>> data = dao.executeQuery(sql);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (re.get("pic") != null) {
					map.put("Name", re.get("pic").toString());
					map.put("Data",File2String.getImageStrBase64(path
									+ re.get("pic").toString()));
				}
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
	public List<Map<String, Object>> Maintenance_File_after(String rescd,
			String formid) throws IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sql = " SELECT [dealPic] FROM dbo.维修养护项目  WHERE htcd='"
				+ rescd + "'";

		List<Map<String, Object>> data = dao.executeQuery(sql);
		if (data.size() > 0) {
			for (Map<String, Object> re : data) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (re.get("dealPic") != null) {
					map.put("Name", re.get("dealPic").toString());
					map.put("Data",File2String.getImageStrBase64(path
									+ re.get("dealPic").toString()));
				}
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
	public Map<String, Object> Project_Year_Maintenance_Fee(String rescd,
			String formid) {
		String sql = "select '"+getstarescdCD(rescd).get("Project_Code").toString()+"' as Project_Code,"
				+ "YEAR([updateTM]) AS Year,downFee as Distributed_Fund,selfFee as Raised_Fund,"
				+ "finishFee as Implement_Fee,payedFee as Paid_Fee from 维养计划费用 where htcd='"+rescd+"'";
		return dao.executeQueryObject(sql);
	}

	/**
	 * 最近一次除险加固信息
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Project_Reinforcements_only(String rescd,
			String formid) {
		String sql = "select '"+getstarescdCD(rescd).get("Project_Code").toString()+"' as Project_Code,"
				+ "(SELECT TOP 1 edt FROM dbo.reinforcement ORDER BY  createtime DESC) as  Complete_Time,"
		+ "(SELECT TOP 1 Complete_Time FROM dbo.reinforcement ORDER BY  createtime DESC) as  Finish_Time from reinforcement  where rescd='"+rescd+"' and id='"+formid+"'  ";
		
		
		return dao.executeQueryObject(sql);
	}

	/**
	 * 上报大坝巡查信息
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Dike_Base_Infos(String rescd)
	{
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
				+ "'  as County_Code";
		
		return dao.executeQueryObject(sql);
	}
	
	/**
	 * 上报大坝巡查信息--项目信息
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Project_Base_Infos(String rescd)
	{
		String sql="";
		
		return dao.executeQueryObject(sql);
	}
	
	/**
	 * 上报大坝巡查信息--管理单位信息
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Dike_Supplement_Infos(String rescd)
	{
		String sql = "select RName as Manage_Unit_Name, "
				+ "UnitNatureid as Manage_Unit_Property,"
				+ "Phone as Manage_Unit_Phone,"
				+ "Fax as Manage_Unit_Fax,"
				+ "FloodPhone as Manage_Unit_Duty_Phone,"
				+ "Adress as Manage_Unit_Address,"
				+ "StaffNumber as Manage_Unit_Staff_Count,"
				+ "StaffNumberOnJob as Manage_Unit_Work_Staff_Count,"
				+ "Incharge as Manage_Unit_Charge_Person_Name,"
				+ "ThoseResponsiblePho as Manage_Unit_Charge_Person_Mobile,"
				+ "ThoseResponsibleMob as Manage_Unit_Charge_Person_Phone,Department_In_Charge,"
				+ "supunit as Uplevel_Water_Gov_Department,Flood_Control_Charge_Person,Flood_Control_Charge_Person_Position"
				+ " from SYS_Reservoir where rescd='"+rescd+"'";
		
		return dao.executeQueryObject(sql);
	}
	
	/**
	 * 上报大坝巡查信息--巡查人员
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Project_Patrollers(String rescd)
	{
		String sql="select username as Patroller_Name,(select mobile from o_user where id=userid) as Patroller_Mobile from inspect2_members ";
		return dao.executeQueryObject(sql);
	}
	

	/**
	 * 上报日常巡查信息
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> DailyPatrol(String rescd,String formid)
	{
		String sql="select inspectdt as Date,'"
				+ getstarescdCD(rescd).get("Project_Code").toString()
				+ "' as Project_Code, "
				+ "updatetime as Start_Time,"
				+ "endtime as End_Time "
				+ " from inspect2_form where rescd='"+rescd+"' and formid='"+formid+"'";
		
		return dao.executeQueryObject(sql);
	}
	
	/**
	 * 上报当天水位信息
	 * 
	 * @param rescd
	 * @param formid
	 * @return
	 */
	public Map<String, Object> Project_Observe_Water_Level(String rescd,String formid)
	{
		String sql="select Z as Water_Level from inspect2_form where rescd='"+rescd+"' and formid='"+formid+"'";
		
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
		//System.out.println(strContent);
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
		System.out.println(result);
		return result;
	}

	public static void main(String[] args) throws ParseException, IOException {
		String strProject_Type="D95B7D3B-DF72-4A35-B4A9-C19D0B38DAC7";
		staImpl.GetAdminDivCode("001", "0");
		staImpl.GetProjectCode("001", "0", "331202", strProject_Type);
		staImpl.GetProjectCode("001", "0", "331203", strProject_Type);
		staImpl.GetProjectCode("001", "0", "331204", strProject_Type);
		//new BusinessServicImpl().UploadDanger(rescd, username, userid, formid);
	}

	
	
}

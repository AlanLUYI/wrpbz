package net.htwater.mydemo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 * 海塘，标准化上报接口
 * 
 * @author 
 * 2016-10-05
 */
public interface BusinessServic {

	static final String SQLSERVER1 = "HOS";
	static final String SQLSERVER2 = "QGJ_HTProject";
	static final String xmlsNamespace = "ProjectSupervise";
	static final String xmlsxsi = "http://www.w3.org/2001/XMLSchema-instance";

	/**
	 * 历史上报信息
	 * @param rescd
	 * @param userid
	 * @param business
	 * @return
	 */
	public List<Map<String, Object>>  getHistory(String rescd, String userid, String business);
	
	/**
	 * 获取大中型水库基本信息
	 * @return
	 */
	public String getReservoirLM(String rescd, String username, String userid);
	/**
	 * 获取大坝巡查基本信息
	 * @return
	 */
	public String getDike(String rescd, String username, String userid);
	/**
	 * 上报大中型水库基本信息
	 * @return
	 */
	public String Seawall(String rescd, String username, String userid);
	/**
	 * 获取监测设备数量
	 * @return
	 */
	public String getUploadFacilityCount(String rescd, String username, String userid);
	/**
	 * 上报监测设备数量
	 * @return
	 */
	public String UploadFacilityCount(String rescd, String username, String userid);
	/**
	 * 上报调度运行情况 
	 * @return
	 */
	public String UploadDispatchSituation(String rescd, String username, String userid, String formid)throws IOException;
	/**
	 * 上报隐患情况
	 * @return
	 */
	public String UploadDanger(String rescd, String username, String userid, String formid) throws IOException;
	/**
	 * 上报隐患处理情况 
	 * @return
	 */
	public String UploadDangerDealSituation(String rescd, String username, String userid, String formid)throws IOException;
	/**
	 * 上报年度已使用维修养护资金
	 * @return
	 */
	public String UploadProjectYearUsedFee(String rescd, String username,  String userid, String formid);
	/**
	 * 上报维修养护项目
	 * @return
	 */
	public String UploadProjectMaintenanceItem(String rescd, String username, String userid, String formid)throws IOException;
	/**
	 * 上报最近一次除险加固信息
	 * @return
	 */
	public String UploadRecentReinforcements(String rescd, String username, String userid, String formid);
	
	
	/**
	 * 上报地理坐标   海塘塘线
	 * @return
	 */
	public String UploadGeographicCoordinates0(String rescd, String username, String userid);
	/**
	 * 获取上报地理坐标   工程管理范围线信息
	 * @return
	 */
	public String getGeographicCoordinates1(String rescd, String username, String userid);
	/**
	 * 上报地理坐标   工程管理范围线
	 * @return
	 */
	public String UploadGeographicCoordinates1(String rescd, String username, String userid);
	

	/**
	 * 获取上报地理坐标   工程保护范围线信息
	 * @return
	 */
	public String getGeographicCoordinates2(String rescd, String username, String userid);
	/**
	 * 上报地理坐标  工程保护范围线
	 * @return
	 */
	public String UploadGeographicCoordinates2(String rescd, String username, String userid);
	
	/**
	 * 上报大坝巡查信息
	 * @return
	 */
	public String UploadDike(String rescd, String username, String userid,String formid);
	
	/**
	 * 上报日常巡查信息
	 * @return
	 */
	public String UploadDailyPatrol(String rescd, String username, String userid,String formid);

	/**
	 * 上报其他隐患情况
	 * @return
	 */
	public String UploadDanger_other(String rescd, String username, String userid, String formid) throws IOException;
	/**
	 * 上报其他隐患处理情况 
	 * @return
	 */
	public String UploadDangerDealSituation_other(String rescd, String username, String userid, String formid)throws IOException;

	
}

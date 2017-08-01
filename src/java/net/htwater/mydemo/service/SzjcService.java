/**
 * 水政相关服务接口
 * @author chenyong
 * @Date 2014-03-21
 * @since v 1.0
 */
package net.htwater.mydemo.service;

import java.util.Map;

public interface SzjcService {

	static final String DB_SZJC = "qgj_smp";//

	/**
	 * 获取涉水监管统计信息
	 * 
	 * @param projectType
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSSJGStaticInfo(String projectType);

	/**
	 * 获取涉水监管列表信息
	 * 
	 * @param area
	 *            ,type,status,year
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSSJGListInfo(String area, String type,
			String status, String year);

	/**
	 * 搜素涉水监管列表信息
	 * 
	 * @param search
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSSJGListInfo(String search);

	/**
	 * 根据项目id，获取涉水监管项目详细信息
	 * 
	 * @param projectid
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSSJGDetailInfo(String projectid);

	/**
	 * 获取水政巡查记录统计信息
	 * 
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSZXCStaticInfo();

	/**
	 * 获取水政巡查记录列表信息
	 * 
	 * @param area
	 *            ,status,year
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSZXCListInfo(String area, String status,
			String year);

	/**
	 * 查找水政巡查记录
	 * 
	 * @param search
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSZXCListInfo(String search);

	/**
	 * 根据水政巡查id，获取水政巡查详细信息
	 * 
	 * @param id
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSZXCDetailInfo(String id);
	
	
	/**
	 * 根据水政巡查项目Check_ID，获取水政巡查编号信息
	 * 
	 * @param projectid
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSZXCDetailInfoByProjectid(String projectid);

	/**
	 * 获取水政巡查记录中的附件信息
	 * 
	 * @param projectid
	 *            ,timeid,cid
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getSZXCAttachmentInfo(String projectid,
			String timeid, String cid);
	
	/**
	 *  获取水库水位流量雨量信息
	 * 
	 * @param rescd
	 *            ,start,end
	 * @return map:{error:0,data:[{}]}
	 */
	public Map<String, Object> getResInfo(String rescd,
			String start, String end);

}

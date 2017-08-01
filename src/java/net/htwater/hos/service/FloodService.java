package net.htwater.hos.service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxiaojing
 * @description 获取山洪灾害预警相关记录、应急响应记录等
 * @date 2015-06-09
 */
public interface FloodService {
	static final String DB_SHANHONG="HOS";
	/**
	 * 获取测站预警列表
	 * @param TM1
	 * @param TM2
	 * @return
	 */
	public List<Map<String,Object>> getSTWarn(String TM1,String TM2);
	
	/**
	 * 获取某时段行政区预警记录
	 * @param TM1
	 * @param TM2
	 * @return
	 */
	public List<Map<String,Object>> getWarnList(String tm1,String tm2);
	
	/**
	 * 根据行政区预警ID获取影响该行政区预警的测站预警记录
	 * @param WARNID
	 * @param RADCD
	 * @return
	 */
	public List<Map<String,Object>> getSTWarnList(String warnid,String radcd);
	
	/**
	 * 根据行政区预警ID获取该预警的变化过程记录
	 * @param WARNID
	 * @param RADCD
	 * @return
	 */
	public List<Map<String,Object>> getWarnChangeList(String warnid,String radcd);
	
	/**
	 * 获取某时间段应急响应记录
	 * @param TM1
	 * @param TM2
	 * @return
	 */
	public List<Map<String,Object>> getResponseList(String tm1,String tm2);
	
	/**
	 * 根据应急响应ID获取应急响应措施记录
	 * @param RESPONSEID 
	 * @return
	 */
	public List<Map<String,Object>> getMeasuresList(String responseid,String radcd);
	
	/**
	 * 获取区县级山洪系统地址(含用户验证的token)
	 * @return
	 */
	public List<Map<String,Object>> getDistrictApp(String userid);
	
	/**
	 * 验证区县级山洪系统的token
	 * @param token
	 * @return
	 */
	public Map<String,Object> checkDistricApp(String token);
	
	/**
	 * 获取某时间段灾害记录
	 * @param tm1,tm2
	 * @return
	 */
	public List<Map<String,Object>> getDisasterSta(String tm1,String tm2);
}

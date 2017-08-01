/**
 * @author:cc
 * @date:2014/3/26
 * @since:v1.0
 * @info:水质监测
 */

package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;

public interface WaterQuaService {
	static final String DB_WATERQUA = "DSDB";
	
	/**
	 * 查询有效的年月信息
	 * @return
	 */
	public List<Map<String, Object>> QueryValidTime();
	
	/**
	 * 查询水源地信息
	 * @param year
	 * @param months
	 * @return
	 */
	public List<Map<String, Object>> QuerySydInfo(String year,String month);
	
	/**
	 * 查询主要江河信息
	 * @param year
	 * @param months
	 * @return
	 */
	public List<Map<String, Object>> QueryRivInfo(String year,String month);
	
	/**
	 * 查询平原河网信息
	 * @param year
	 * @param months
	 * @return
	 */
	public List<Map<String, Object>> QueryPlainRivInfo(String year,String month);
}

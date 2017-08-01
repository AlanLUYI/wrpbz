/**
 * 防汛管理服务接口
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;


public interface TJBBService {
	static final String DB_ZHSL = "qgj_smp"; 
	public List<Map<String, Object>> Queryrealstnm(String TM);
	public List<Map<String, Object>> Querymultidata(String year,String month);
	public Map<String, Object> Querysingledata(String year,String stcd);
	
	
}

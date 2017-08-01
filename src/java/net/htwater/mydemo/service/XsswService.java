/**
 * 防汛管理服务接口
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;


public interface XsswService {
	static final String DB_ZHSL = "qgj_smp"; 
	
	public List<Map<String, Object>> Queryzdxx();
	
	public List<Map<String, Object>> QueryHisRain_year(String TM1, String TM2 );
	public List<Map<String, Object>> QueryHisRain_month(String TM1, String TM2 );
	public List<Map<String, Object>> QueryHisRain_day(String TM1, String TM2 );
	public List<Map<String, Object>> QueryHisWe_year(String TM1, String TM2 );
	public List<Map<String, Object>> QueryHisWe_month(String TM1, String TM2 );
	public List<Map<String, Object>> QueryHisWe_day(String TM1, String TM2 );
	
	public List<Map<String, Object>> Querydmxp(String TM1, String TM2,String stcds );
	public List<Map<String, Object>> Queryhmxp(String TM1, String TM2,String stcds );
	public List<Map<String, Object>> Querymmxp(String TM1, String TM2,String stcds );
	public List<Map<String, Object>> QueryYRWE(String TM1, String TM2,String stcds );
	public List<Map<String, Object>> QueryMTHWE(String TM1, String TM2,String stcds );
}

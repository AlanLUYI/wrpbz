/**
 * 水利工程服务接口
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.hos.service;

import java.util.List;
import java.util.Map;


public interface SlgcService {
	static final String SLPC = "SLPC"; 
	static final String DB_ZHSL = "db_zhsl"; 
	/**
	 * 水库
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryres();
	/**
	 * 水库查询
	 * @Date 2014-02-19
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> QueryFORres(String ENNM,String CITY,String TOTAL_s);
	/**
	 * 河道
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryriver() ;
	/**
	 * 河道查询
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return           
	 */
	public List<Map<String, Object>> QueryFORriver(String STNM,String classify) ;
	/**
	 * 水闸
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Querygate();
	/**
	 * 水闸查询
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> QueryFORgate(String ENNM,String TP,String CITY);
	/**
	 * 泵站
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Querypumb();
	/**
	 * 泵站查询
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return     ENNM:ENNM,CITY:CITY,TP:TP
	 */
	public List<Map<String, Object>> QueryFORpumb(String ENNM,String CITY,String TP);
	/**
	 * 堤防
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Querydike();
	/**
	 * 堤防查询
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> QueryFORdike(String ENNM,String FLOOD_STAN);
	
	
	public List<Map<String, Object>> Queryhaitang();
	/**
	 * 水站查询
	 * @Date 2014-02-27
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Querywaterstation();
	/**
	 * 获取水站不同的区县和乡镇数据
	 * @Date 2014-03-18
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Querydistinctqx();
	
	/**
	 * 水电站查询
	 * @Date 2014-02-27
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryhydropowerstation();
	
	/**
	 * 在全部水利工程表里模糊搜索工程名称
	 * @param name
	 * @return
	 */
	public Map<String,Object> searchAllByName(String name);
	
	/**
	 * 多条件搜索水库工程
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchRes(String where);
	
	/**
	 * 多条件搜索水闸工程
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchGate(String where);
	
	/**
	 * 多条件搜索泵站工程
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchPump(String where);
	
	/**
	 * 多条件搜索水电站
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchGen(String where);
	
	/**
	 * 多条件搜索山塘
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchPool(String where);
	
	/**
	 * 多条件搜索堤防
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchDike(String where);
	
	/**
	 * 多条件搜索海塘
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchSeawall(String where);
	
	/**
	 * 多条件搜索取水口
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchIntake(String where);
	
	/**
	 * 多条件搜索地表水水源地
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchSource(String where);
	
	/**
	 * 多条件搜索排污口
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchOutfall(String where);
	
	/**
	 * 多条件搜索机电井
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchWell(String where);
	
	/**
	 * 多条件搜索水文站点
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchStation(String where);
	
	/**
	 * 多条件搜索山地河流
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchMRiver(String where);
	
	/**
	 * 多条件搜索平原河网
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchPRiver(String where);
	
	/**
	 * 多条件搜索小流域
	 * @param where
	 * @return
	 */
	public List<Map<String,Object>> searchValley(String where);
	
	/**
	 * 获取工程经纬度
	 * @param ennmcd
	 * @param type
	 * @return
	 */
	public Map<String,Object> getLocation(String ennmcd,String type);
}

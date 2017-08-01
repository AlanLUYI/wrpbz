/**
 * wechart  微信数据服务
 * @author fhj
 * 2015-02-10
 */
package net.htwater.hos.service;

import java.util.List;
import java.util.Map;

public interface WechartService {
	static final String DB_SHPUB="SHPUB";
	static final String DB_NBSQDB="HT_NBSQDB";
	static final String DB_Server2="SqlServer2";
	static final String DB_HOS="HOS";
	//微信数据服务，雨量数据
	public List<Map<String, Object>> queryFORrain();
	//微信数据服务，水位数据
	public List<Map<String, Object>> queryFORwater();
	//微信数据服务，潮位数据
	public List<Map<String, Object>> queryFORtide();
	//微信数据服务，台风消息
	public String queryFORtyph();
	//记录微信openid
	public String  insertOPENID(String openid);
}

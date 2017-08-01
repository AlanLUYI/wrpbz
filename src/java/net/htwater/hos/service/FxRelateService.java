package net.htwater.hos.service;
import java.util.List;
import java.util.Map;

public interface FxRelateService {
	static final String DB_SHANHONG="HOS";
	
	public List<Map<String,Object>> queryMaterial();
				
	public List<Map<String,Object>> queryResTeam();
	
	/**
	 * 获取山洪灾害基础信息
	 * @param type 表名的类别
	 * @return list
	 * @author Clay
	 */
	public List<Map<String,Object>> getBaseInfo(String type);
	
	/**
	 * 按重新上报的数据，获取山洪基础信息
	 * @param type
	 * @return
	 */
	public List<Map<String,Object>> getSHBaseInfo(String type);
	
}

/**
 * 工情服务接口
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.hos.service;

import java.util.List;
import java.util.Map;


public interface GqService {
	static final String DB_SHANHONG="HOS";
	
	public List<Map<String, Object>> getsplist();
}

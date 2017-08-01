/**
 * 预警信息接口
 * @author RYX
 * @Date 2014-06-17
 * @since v 1.0
 */ 
package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;



public interface YJXXService {
	static final String DB_ZHSL = "qgj_smp"; 
	/**
	 * 预警信息列表 
	 * @Date 2014-06-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryyjxxlist(String TM1,String TM2);
	
	/**
	 * 根据ID获取单条预警信息 
	 * @Date 2014-06-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> QueryyjxxbyID(String ID);
	
	/**
	 * 实时预警状态 
	 * @Date 2014-06-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryrealyjxx();
	
	
	/**
	 * 增加预警信息 
	 * @Date 2014-06-17
	 * @since v 1.0
	 * @return
	 */
	public Map<String, Object> addyjxx(String TM,String GRADE,String TITLE,String Source,String CONTENT);
	
}

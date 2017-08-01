/**
 * 防汛管理服务接口
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;


public interface GcaqService {
	static final String QGJ_SMP = "qgj_smp"; 
	 
	/**
	 * 水库工程安全信息
	 * @Date 2014-03-05
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Querygcaq_res() ;
	
	/**
	 * 水闸安全信息
	 * @Date 2014-03-05
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> getAllGateSafeData();
	/**
	 * 堤防安全信息
	 * @Date 2014-03-05
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> getAllDikeSafeData() ;
}

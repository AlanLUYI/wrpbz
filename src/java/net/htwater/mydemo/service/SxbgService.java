/**
 * 失信曝光服务
 * @author chenchu
 * @Date 2014-03-24
 * @since v 1.0
 */

package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;

public interface SxbgService {
	
	static final String DB_ZHSL = "qgj_smp";//
	
	/**
	 * 查询失信曝光信息
	 * @Date 2014-03-24
	 * @since v 1.0
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> QuerySxbgInfo(String type);

}

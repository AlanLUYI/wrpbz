/**
 * 获取工情信息服务接口
 * @author ysf
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;


public interface GetGQService {
	static final String DB_ZHSL = "GQ"; 
	static final String HT_NBSQDB = "ht_nbsqdb";
	static final String QGJ_SMP = "qgj_smp";
	/**
	 * 
	 * @Date 2014-05-17
	 * @since v 1.0
	 * @return
	 * @info 获取工情信息数据
	 */
	public List<Map<String, Object>> QueryGQ();
	/**
	 * 
	 * @Date 2014-05-17
	 * @since v 1.0
	 * @return
	 * @info 更新工情信息数据
	 */
	public int updateGQ(String STCD,String RTUCD,String RTUNM,String RTUCD1,String RTUNM1,String RTUCD2,String RTUNM2,String CZXS,String XZXS);
	
	/**
	 * 
	 * @Date 2014-09-27
	 * @since v 1.0
	 * @return
	 * @info 获取水库承洪信息数据
	 */
	public List<Map<String, Object>> getSKCHtab();
	/**
	 * 
	 * @Date 2014-09-27
	 * @since v 1.0
	 * @return
	 * @info 修改水库承洪信息数据
	 */
	public int updateSKCHtab(String resID,String rateV);
	/**
	 * 
	 * @Date 2014-09-27
	 * @since v 1.0
	 * @return
	 * @info 获取智慧水利系统配置功能菜单列表数据
	 */
	public List<Map<String, Object>> getMenuList();
}

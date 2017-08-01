/**
 * login服务接口
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;


public interface LoginService {
	static final String DB_ZHSL = "qgj_smp"; 
	
	
	public Map<String, Object> getpad_config();
	/**
	   * 密码验证
	   * @author RYX
	   * @Date 2014-02-17
	   * @since v 1.0
	   * @return
	   */
	public List<Map<String, Object>> login(String loginname, String password);
	
	/**
	 * zzj 2014.3.18 单点登录
	 * @param loginname
	 * @return
	 */
	public List<Map<String, Object>> ssologin(String loginname);
	/**
	   * 菜单
	   * @author RYX
	   * @Date 2014-02-26
	   * @since v 1.0
	   * @return
	   */
	public List<Map<String, Object>> Gettreedata(String user);
	
	/**
	 * 修改背景
     * @author RYX
     * @Date 2014-06-10
     * @since v 1.0
	 * @return
	 */
	public Map<String, Object> saveSkin(String UserID,String bg,String op,String cl);
	/**
	 * 修改模块AIDs
     * @author RYX
     * @Date 2014-06-10
     * @since v 1.0
	 * @return
	 */
	public Map<String, Object> saveAids(String UserID,String AIDs);
	
	/**
	   * 我的应用
	   * @author RYX
	   * @Date 2014-08-12
	   * @since v 1.0
	   * @return
	   */
	public List<Map<String, Object>> getmyapps(String GroupID);
	
	/**
	   * 网站列表
	   * @author RYX
	   * @Date 2014-06-17
	   * @since v 1.0
	   * @return
	   */
	public List<Map<String, Object>> geturls(String UserID);
	/**
	   * 增加网站
	   * @author RYX
	   * @Date 2014-06-17
	   * @since v 1.0
	   * @return
	   */
	public Map<String, Object> addurl(String UserID,String url,String title);
	/**
	   * 修改网站
	   * @author RYX
	   * @Date 2014-06-17
	   * @since v 1.0
	   * @return
	   */
	public Map<String, Object> updateurl(String ID,String UserID,String url,String title);
	/**
	   * 删除网站
	   * @author RYX
	   * @Date 2014-06-17
	   * @since v 1.0
	   * @return
	   */
	public Map<String, Object> deleteurl(String ID,String UserID);
	
	public Map<String, Object> CheckphoneNumber(String phoneNumber );
	public Map<String, Object> CheckIMSI(String IMSI );
	public Map<String, Object> SaveIMSI(String phoneNumber,String IMSI);
}

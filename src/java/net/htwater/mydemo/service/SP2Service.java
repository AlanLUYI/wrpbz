/**
 * 获取视频监视列表
 * @author yangshengfei
 * @Date 2014-06-27
 * @since v 1.0
 */


package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;

public interface SP2Service {
	static final String DB_ZHSL = "qgj_smp";
	/**
	 * 获取视频监视列表
	 * @return
	 */
	public List<Map<String, Object>> QueryShiPinAll();

}

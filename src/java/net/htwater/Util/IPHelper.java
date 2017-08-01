package net.htwater.Util;

import cn.miao.framework.util.HttpUtil;
import net.sf.json.JSONObject;

public class IPHelper {
	/**
	 * 根据IP获取所在地
	 * @param ip
	 * @return String
	 * @since v 1.0
	 */
	public static String getLocation(String ip) {
		if (ip.indexOf(".1.1.1") > -1
				|| ip.indexOf(":0") > -1
				|| ip.indexOf("127.0.0.1") > -1) {
			return "";
		}
		String urlString = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="+ip;
		String result = HttpUtil.getResponseFromWeb(urlString);
		JSONObject object = JSONObject.fromObject(result);
		if (1 == object.getInt("ret")) {
			return object.getString("province")+object.getString("city");
		} else {
			return "";
		}
	}
}

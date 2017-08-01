package net.htwater.hos.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.miao.framework.util.DateUtil;
import cn.miao.framework.util.PinyinUtil;
import net.htwater.hos.helper.entity.MyCaches;

public class HtOauthHelper {
	/**
	 * 根据开始结束日期，判断是否台汛
	 *
	 * @param stDateStr
	 * @param endDateStr
	 * @return
	 */
	public static boolean isTX(String stDateStr, String endDateStr) {
		if (null == stDateStr) {
			stDateStr = "07-15";
		}
		if (null == endDateStr) {
			endDateStr = "10-16";
		}
		Date stDate = DateUtil.str2Date(DateUtil.getToday("yyyy-") + stDateStr,
				"yyyy-MM-dd");
		Date endDate = DateUtil.str2Date(DateUtil.getToday("yyyy-")
				+ endDateStr, "yyyy-MM-dd");
		Date now = new Date();
		return now.getTime() >= stDate.getTime()
				&& now.getTime() < endDate.getTime();
	}
	
	public static String getTimeByHourOffset(Date today, int hours,
			String pattern) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(today);
		calendar.add(Calendar.HOUR, hours);
		if (null == pattern)
			pattern = "yyyy-MM-dd";
		return DateUtil.date2Str(calendar.getTime(), pattern);
	}
	
	public static String getTimeByDayOffset(Date today, int day, String pattern) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(today);
		calendar.add(Calendar.DATE, day);
		if (null == pattern)
			pattern = "yyyy-MM-dd";
		return DateUtil.date2Str(calendar.getTime(), pattern);
	}
	
	/**
	 * 雨量站过滤县市区、水库
	 *
	 * @param list
	 * @param district
	 * @param rsvr
	 * @return
	 */
	public static List<Map<String, Object>> filterDistrict(
			List<Map<String, Object>> list, String district, String rsvr) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		if (null == district && null == rsvr) {
			return list;
		} else if (null != district && null == rsvr) {
			for (Map<String, Object> map : list) {
				if (MyCaches.locationMap.get(district).contains(
						map.get("CITY").toString())) {
					rtList.add(map);
				}
			}
			return rtList;
		} else if (null == district && null != rsvr) {
			for (Map<String, Object> map : list) {
				if (PinyinUtil.converterToPinYin(map.get("RSNM").toString())
						.contains(rsvr)) {
					rtList.add(map);
				}
			}
			return rtList;
		} else if (null != district && null != rsvr) {
			for (Map<String, Object> map : list) {
				if (MyCaches.locationMap.get(district).contains(
						map.get("CITY").toString())
						&& PinyinUtil.converterToPinYin(
								map.get("RSNM").toString()).contains(rsvr)) {
					rtList.add(map);
				}
			}
			return rtList;
		} else {
			return rtList;
		}
	}
}

/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  Mar 3, 2014 Neal Miao 
 * 
 * Copyright(c) 2014, by htwater.net. All Rights Reserved.
 */
package net.htwater.hos.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htwater.hos.helper.other.HTMLFormatter;
import net.htwater.hos.helper.other.MaxRainStationSorter;
import net.htwater.hos.helper.entity.RiverInfo;
import net.htwater.hos.helper.entity.STRain;
import net.htwater.hos.helper.entity.STWater;
import net.htwater.hos.helper.entity.TideInfo;
import net.htwater.hos.helper.impl.HtOauthServiceImpl;
import cn.miao.framework.util.DateUtil;
import cn.miao.framework.util.StringUtil;

/**
 * 
 * @author Neal Miao
 * @version
 * @Date Mar 3, 2014 2:58:44 PM
 * 
 * @see
 */
public class RainWaterHelper {
	/**
	 * 获取昨天8点
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public static String getYestoday8() {
		return getBDayByDay(-1);
	}

	/**
	 * 获取明天8点
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public static String getTomorrow8() {
		return getBDayByDay(1);
	}

	/**
	 * 获取今天8点
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public static String getToday8() {
		Date today = new Date();
		return DateUtil.date2Str(today, "yyyy-MM-dd 08:00:00");
	}

	/**
	 * 判断是不是水利上的今天
	 * 
	 * @return boolean true 需要去昨天八点
	 * @since v 1.0
	 */
	public static boolean isNotWaterToday() {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		Calendar c2 = Calendar.getInstance();
		c2.setTime(DateUtil.str2Date(getToday8(), "yyyy-MM-dd HH:mm:ss"));
		switch (c1.compareTo(c2)) {
		case -1:
			return true;
		case 1:
			return false;
		default:
			return false;
		}
	}

	/**
	 * 获取水利上的昨天8点
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public static String getWaterYestorday() {
		if (isNotWaterToday()) {
			return getBYestoday8();
		} else {
			return getYestoday8();
		}
	}

	/**
	 * 日期加减
	 * 
	 * @param days
	 * @return String 返回8时时间
	 * @since v 1.0
	 */
	public static String getBDayByDay(int days) {
		Date today = new Date();
		String todayStr = DateUtil.date2Str(today, "yyyy-MM-dd 08:00:00");
		today = DateUtil.str2Date(todayStr, "yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, days);
		return DateUtil.date2Str(calendar.getTime(), "yyyy-MM-dd 08:00:00");
	}

	/**
	 * 获取前天8点
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public static String getBYestoday8() {
		return getBDayByDay(-2);
	}

	public static String getB3day8() {
		return getBDayByDay(-3);
	}

	public static String getB4day8() {
		return getBDayByDay(-4);
	}

	public static String getB5day8() {
		return getBDayByDay(-5);
	}

	public static String getB6day8() {
		return getBDayByDay(-6);
	}

	/**
	 * 获取水利上的今天8点
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public static String getWaterToday() {
		if (isNotWaterToday()) {
			return getYestoday8();
		} else {
			return getToday8();
		}
	}

	/**
	 * 获取水利上的明天8点
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public static String getWaterTommorow() {
		if (isNotWaterToday()) {
			return getToday8();
		} else {
			return getTomorrow8();
		}
	}

	/**
	 * 获取水利上的前天8点
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public static String getWaterBYestorday() {
		if (isNotWaterToday()) {
			return getB3day8();
		} else {
			return getBYestoday8();
		}
	}
	
	/**
	 * 获取水利上的整点
	 * interval ：当前整点往前推 interval个整点，e.g. interval=1 表示前一小时，interval=3,表示前3小时
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public static String getWaterWholePoint(Date tm,int interval) {
		Calendar Cal= Calendar.getInstance();
		Cal.setTime(tm);
		@SuppressWarnings("static-access")
		int hour = Cal.HOUR_OF_DAY - interval;
		String tmStr = "";
		if(hour>=10)
			tmStr = "yyyy-MM-dd " + Integer.toString(hour) + ":00:00";
		else {
			tmStr = "yyyy-MM-dd 0" + Integer.toString(hour) + ":00:00";
		}
		
		String result = DateUtil.date2Str(tm, tmStr);
		return result;		
	}
	
	/**
	 * 格式化流量流速Map数据
	 * 
	 * @param map
	 * @return Map<String,Object>
	 */
	public static void formatFlowMap(Map<String, Object> map, String key) {
		if (null == map.get(key)) {
			map.put(key, "-");
		}
	}

	/**
	 * 查询map
	 * 
	 * @param map
	 * @param key
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> queryStationBySTCD(
			List<Map<String, Object>> list, String stcd) {
		if (null == list) {
			return null;
		}
		for (Map<String, Object> map : list) {
			if (null == map.get("STCD")) {
				continue;
			}
			if (null == map.get("RIVER")) {
				map.put("RIVER", "");
			}
			String tbStcd = map.get("STCD").toString().trim();
			if (tbStcd.contains(stcd.trim())) {
				return map;
			}
		}
		return null;
	}
	
	/**
	 * 查询map
	 * 
	 * @param map
	 * @param key
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> queryStationBySTNM(
			List<Map<String, Object>> list, String stnm) {
		if (null == list) {
			return null;
		}
		for (Map<String, Object> map : list) {
			if (null == map.get("STNM")) {
				continue;
			}
			if (null == map.get("RIVER")) {
				map.put("RIVER", "");
			}
			String tbStnm = map.get("STNM").toString().trim();
			if (tbStnm.contains(stnm.trim())) {
				return map;
			}
		}
		return null;
	}
	
	/**
	 * 水位站点，过滤地区、超警戒查询
	 * 
	 * @param district
	 * @param compareKey
	 * @param waterList
	 * @param alert
	 * @return List<STWater>
	 */
	public static List<Object> filterAlertStations(String district,
			List<Object> waterList, String alert, String stationType) {
		List<Object> rtList = new ArrayList<>();
		// 过滤区县
		if (null == district || "All".equals(district)) {
			rtList = waterList;
		} else {
			rtList.clear();
			// STWater RiverInfo TideInfo可以重新抽象一下
			for (int i = 0; i < waterList.size(); i++) {
				if ("rsvr".equals(stationType)) {
					STWater obj = (STWater) waterList.get(i);
					// 过滤县市区
					if (district.equals(obj.getDistrict())) {
						rtList.add(waterList.get(i));
					}
				} else if ("river".equals(stationType)) {
					RiverInfo obj = (RiverInfo) waterList.get(i);
					if (district.equals(obj.getDistrict())) {
						rtList.add(waterList.get(i));
					}
				} else {
					TideInfo obj = (TideInfo) waterList.get(i);
					if (district.equals(obj.getDistrict())) {
						rtList.add(waterList.get(i));
					}
				}
			}
		}
		// 过滤超警戒
		if ("1".equals(alert)) {
			List<Object> newList = new ArrayList<>();
			for (int i = 0; i < rtList.size(); i++) {
				if ("rsvr".equals(stationType)) {
					STWater obj = (STWater) rtList.get(i);
					if (isAlertStation(obj.getStsw(), obj.getKzsw())) {
						newList.add(obj);
					}
				} else if ("river".equals(stationType)) {
					RiverInfo obj = (RiverInfo) rtList.get(i);
					if (isAlertStation(obj.getSw85(), obj.getGuard())) {
						newList.add(obj);
					}
				} else {
					TideInfo obj = (TideInfo) rtList.get(i);
					if (isAlertStation(obj.getSw85(), obj.getGuard())) {
						newList.add(obj);
					}
				}
			}
			return newList;
		} else {
			return rtList;
		}
	}
	
	/**
	 * 水位站点，过滤地区、超警戒查询
	 * 
	 * @param district
	 * @param compareKey
	 * @param waterList
	 * @param alert
	 * @return List<STWater>
	 */
	public static List<STWater> filterAlertRsvr(String district,
			List<STWater> waterList, String alert) {
		List<STWater> rtList = new ArrayList<>();
		// 过滤区县
		if (null == district || "All".equals(district)) {
			rtList = waterList;
		} else {
			rtList.clear();
			// STWater RiverInfo TideInfo可以重新抽象一下
			for (int i = 0; i < waterList.size(); i++) {
				STWater obj = (STWater) waterList.get(i);
				// 过滤县市区
				if (district.equals(obj.getDistrict())) {
					rtList.add(waterList.get(i));
				}
			}
		}
		// 过滤超警戒
		if ("1".equals(alert)) {
			List<STWater> newList = new ArrayList<>();
			for (int i = 0; i < rtList.size(); i++) {
				STWater obj = (STWater) rtList.get(i);
				if (isAlertStation(obj.getStsw(), obj.getKzsw())) {
					newList.add(obj);
				}
			}
			return newList;
		} else {
			return rtList;
		}
	}
	
	/**
	 * 水位站点，过滤地区、超警戒查询
	 * 
	 * @param district
	 * @param compareKey
	 * @param waterList
	 * @param alert
	 * @return List<STWater>
	 */
	public static List<RiverInfo> filterAlertRiver(String district,
			List<RiverInfo> waterList, String alert) {
		List<RiverInfo> rtList = new ArrayList<>();
		// 过滤区县
		if (null == district || "All".equals(district)) {
			rtList = waterList;
		} else {
			rtList.clear();
			// STWater RiverInfo TideInfo可以重新抽象一下
			for (int i = 0; i < waterList.size(); i++) {
				RiverInfo obj = (RiverInfo) waterList.get(i);
				if (district.equals(obj.getDistrict())) {
					rtList.add(waterList.get(i));
				}
			}
		}
		// 过滤超警戒
		if ("1".equals(alert)) {
			List<RiverInfo> newList = new ArrayList<>();
			for (int i = 0; i < rtList.size(); i++) {
				RiverInfo obj = (RiverInfo) rtList.get(i);
				if (isAlertStation(obj.getSw85(), obj.getGuard())) {
					newList.add(obj);
				}
			}
			return newList;
		} else {
			return rtList;
		}

	}
	
	/**
	 * 水位站点，过滤地区、超警戒查询
	 * 
	 * @param district
	 * @param compareKey
	 * @param waterList
	 * @param alert
	 * @return List<STWater>
	 */
	public static List<TideInfo> filterAlertTide(String district,
			List<TideInfo> waterList, String alert) {
		List<TideInfo> rtList = new ArrayList<>();
		// 过滤区县
		if (null == district || "All".equals(district)) {
			rtList = waterList;
		} else {
			rtList.clear();
			// STWater RiverInfo TideInfo可以重新抽象一下
			for (int i = 0; i < waterList.size(); i++) {
				TideInfo obj = (TideInfo) waterList.get(i);
				if (district.equals(obj.getDistrict())) {
					rtList.add(waterList.get(i));
				}
			}
		}

		// 过滤超警戒
		if ("1".equals(alert)) {
			List<TideInfo> newList = new ArrayList<>();
			for (int i = 0; i < rtList.size(); i++) {
				TideInfo obj = (TideInfo) rtList.get(i);
				if (isAlertStation(obj.getSw85(), obj.getGuard())) {
					newList.add(obj);
				}
			}
			return newList;
		} else {
			return rtList;
		}
	}

	/**
	 * 判断是否超警戒
	 * 
	 * @param stsw
	 * @param kzsw
	 * @return boolean
	 */
	private static boolean isAlertStation(String stsw, String kzsw) {
		if (null != stsw && null != kzsw && stsw.length() > 0
				&& kzsw.length() > 0) {
			try {
				if (StringUtil.compareString(stsw, kzsw) >= 0) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 过滤县市区
	 * 
	 * @param datas
	 * @param district
	 * @return List<STRain>
	 */
	public static List<STRain> filterRainByDistrict(List<STRain> datas,
			String district) {
		// 过滤区县
		if (null == district || "All".equals(district)) {
			return datas;
		}
		List<STRain> rtList = new ArrayList<>();
		for (STRain rain : datas) {
			if (rain.getDistrict().trim().contains(district.trim())) {
				rtList.add(rain);
			}
		}
		return rtList;
	}

	/**
	 * 过滤雨量站点
	 * 
	 * @param datas
	 * @param col
	 * @param line
	 * @return String
	 */
	public static List<STRain> filterRain(List<STRain> datas, String col,
			String line) {
		if (null == col || null == line || 0 == line.length()
				|| 0 == col.length()) {
			return datas;
		}
		int colInt = Integer.parseInt(col); // 行
		int lineInt = Integer.parseInt(line);
		List<STRain> rtVal = null;
		switch (lineInt) {
		case 0: // >=200
			rtVal = filterRain(datas, colInt, 200, 10000);
			break;
		case 1: // 100~200
			rtVal = filterRain(datas, colInt, 100, 200);
			break;
		case 2: // 50~100
			rtVal = filterRain(datas, colInt, 50, 100);
			break;
		case 3: // 30~50
			rtVal = filterRain(datas, colInt, 30, 50);
			break;
		case 4: // 0~30
			rtVal = filterRain(datas, colInt, 0, 30);
			break;
		default:
			break;
		}
		return rtVal;
	}

	/**
	 * 根据雨量段和哪一列过滤
	 * 
	 * @param datas
	 * @param col
	 * @param min
	 * @param max
	 * @return String
	 * @since v 1.0
	 */
	public static List<STRain> filterRain(List<STRain> datas, int col, int min,
			int max) {
		List<STRain> rtList = new ArrayList<>();
		for (STRain rain : datas) {
			String compareVal = "";
			switch (col) {
			case 0: // 今天
				compareVal = rain.getTodayRain();
				break;
			case 1: // 昨天
				compareVal = rain.getYestodayRain();
				break;
			case 2: // 前天
				compareVal = rain.getByestodayRain();
				break;
			case 3: // 三天
				compareVal = rain.getTotalRain();
				break;
			case 4: // 七天
				compareVal = rain.getSum7();
				break;
			}

			if (Double.valueOf(compareVal) <= max
					&& Double.valueOf(compareVal) > min) {
				rtList.add(rain);
			}
		}
		return rtList;
	}



	/*
	 * 获取最大降雨点信息
	 */
	private static List<Map<String, Object>> getMaxJYDinfo(List<STRain> districtList,String area){
		List<Map<String, Object>> stations = new ArrayList<Map<String,Object>>();
		if(area.equals("宁波市")){
			for (STRain rain : districtList) {
				//if (rain.getName().indexOf(area.substring(0, 2)) > -1) {// 用于过滤区县
				if (rain.getOtherInfo().length() > 0) {
					if (rain.getOtherInfo().indexOf("、") > -1) { // 有两个最大降雨点
						String[] tmpStationArr = rain.getOtherInfo().split("、");
						for (int i = 0; i < tmpStationArr.length; i++) {
							double maxRain = getbracketValue(HTMLFormatter
									.removeHtmlTag(tmpStationArr[i]));
							Map<String, Object> obj = new HashMap<>();
							obj.put("val", maxRain);
							String[] infoArr = HTMLFormatter.removeHtmlTag(
									tmpStationArr[i]).split("-");
							obj.put("text", rain.getName() + "的"
									+ formatShowDetailHtml(infoArr));
							stations.add(obj);
						}
					} else {
						double maxRain = getbracketValue(HTMLFormatter
								.removeHtmlTag(rain.getOtherInfo()));
						Map<String, Object> obj = new HashMap<>();
						obj.put("val", maxRain);
						String[] infoArr = HTMLFormatter.removeHtmlTag(
								rain.getOtherInfo()).split("-");
						obj.put("text", rain.getName() + "的"
								+ formatShowDetailHtml(infoArr));
						stations.add(obj);
					}
				}
			}
		}
		else {
			for (STRain rain : districtList) {
				if (rain.getName().indexOf(area.substring(0, 2)) > -1) {// 用于过滤区县
					if (rain.getOtherInfo().length() > 0) {
						if (rain.getOtherInfo().indexOf("、") > -1) { // 有两个最大降雨点
							String[] tmpStationArr = rain.getOtherInfo().split("、");
							for (int i = 0; i < tmpStationArr.length; i++) {
								double maxRain = getbracketValue(HTMLFormatter
										.removeHtmlTag(tmpStationArr[i]));
								Map<String, Object> obj = new HashMap<>();
								obj.put("val", maxRain);
								String[] infoArr = HTMLFormatter.removeHtmlTag(
										tmpStationArr[i]).split("-");
								obj.put("text", rain.getName() + "的"
										+ formatShowDetailHtml(infoArr));
								stations.add(obj);
							}
						} else {
							double maxRain = getbracketValue(HTMLFormatter
									.removeHtmlTag(rain.getOtherInfo()));
							Map<String, Object> obj = new HashMap<>();
							obj.put("val", maxRain);
							String[] infoArr = HTMLFormatter.removeHtmlTag(
									rain.getOtherInfo()).split("-");
							obj.put("text", rain.getName() + "的"
									+ formatShowDetailHtml(infoArr));
							stations.add(obj);
						}
					}
				}
			}			
		}
		if (stations.size() > 0) {
			Collections.sort(stations, new MaxRainStationSorter());
			if (stations.size() > 3) {
				stations = stations.subList(0,3);
			}			
		}
		
		return stations;
	}

	/*
	 * 获取最大降雨点信息
	 */
	private static List<Map<String, Object>> getMaxJYDinfo2(List<STRain> districtList,String area){
		List<Map<String, Object>> stations = new ArrayList<Map<String,Object>>();
		if(area.equals("宁波市")){
			for (STRain rain : districtList) {
				//if (rain.getName().indexOf(area.substring(0, 2)) > -1) {// 用于过滤区县
				if (rain.getOtherInfo().length() > 0) {
					if (rain.getOtherInfo().indexOf("、") > -1) { // 有两个最大降雨点
						String[] tmpStationArr = rain.getOtherInfo().split("、");
						for (int i = 0; i < tmpStationArr.length; i++) {
							double maxRain = getbracketValue(HTMLFormatter
									.removeHtmlTag(tmpStationArr[i]));
							Map<String, Object> obj = new HashMap<>();
							obj.put("val", maxRain);
							String[] infoArr = HTMLFormatter.removeHtmlTag(
									tmpStationArr[i]).split("-");
							obj.put("text", rain.getName() + "的"
									+ formatShowDetailHtml(rain.getName(), infoArr));
							stations.add(obj);
						}
					} else {
						double maxRain = getbracketValue(HTMLFormatter
								.removeHtmlTag(rain.getOtherInfo()));
						Map<String, Object> obj = new HashMap<>();
						obj.put("val", maxRain);
						String[] infoArr = HTMLFormatter.removeHtmlTag(
								rain.getOtherInfo()).split("-");
						obj.put("text", rain.getName() + "的"
								+ formatShowDetailHtml(rain.getName(), infoArr));
						stations.add(obj);
					}
				}
			}
		}
		else {
			for (STRain rain : districtList) {
				if (rain.getName().indexOf(area.substring(0, 2)) > -1) {// 用于过滤区县
					if (rain.getOtherInfo().length() > 0) {
						if (rain.getOtherInfo().indexOf("、") > -1) { // 有两个最大降雨点
							String[] tmpStationArr = rain.getOtherInfo().split("、");
							for (int i = 0; i < tmpStationArr.length; i++) {
								double maxRain = getbracketValue(HTMLFormatter
										.removeHtmlTag(tmpStationArr[i]));
								Map<String, Object> obj = new HashMap<>();
								obj.put("val", maxRain);
								String[] infoArr = HTMLFormatter.removeHtmlTag(
										tmpStationArr[i]).split("-");
								obj.put("text", rain.getName() + "的"
										+ formatShowDetailHtml(rain.getName(), infoArr));
								stations.add(obj);
							}
						} else {
							double maxRain = getbracketValue(HTMLFormatter
									.removeHtmlTag(rain.getOtherInfo()));
							Map<String, Object> obj = new HashMap<>();
							obj.put("val", maxRain);
							String[] infoArr = HTMLFormatter.removeHtmlTag(
									rain.getOtherInfo()).split("-");
							obj.put("text", rain.getName() + "的"
									+ formatShowDetailHtml(rain.getName(), infoArr));
							stations.add(obj);
						}
					}
				}
			}			
		}
		if (stations.size() > 0) {
			Collections.sort(stations, new MaxRainStationSorter());
			if (stations.size() > 3) {
				stations = stations.subList(0,3);
			}			
		}
		
		return stations;
	}

	public static String formatShowDetailHtml(String station, String[] infoArr) {
		String stnm = infoArr[1].replace(")", "毫米)");
		return "<a href='javascript:alert(\"" + station + "-" + stnm.split("\\(")[0] + "-" + infoArr[0] + "\")'>"
				+ stnm + "</a>";
	}
	
	public static String formatShowDetailHtml(String[] infoArr) {
		return "<a href='javascript:showStation(" + infoArr[0] + ")'>"
				+ infoArr[1].replace(")", "毫米)") + "</a>";
	}

	/**
	 * 获取文本中的数字
	 * 
	 * @param str
	 * @return double
	 */
	public static double getbracketValue(String str) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return Double.valueOf(m.replaceAll("").trim());
	}

	/**
	 * 统计 计数
	 * 
	 * @param rainVal
	 * @param summary
	 * @param col
	 * @return void
	 */
	private static void getRainSummary(Double rainVal, int[][] summary, int col) {
		if (rainVal > 200) {
			summary[0][col]++;
		} else if (rainVal > 100) {
			summary[1][col]++;
		} else if (rainVal > 50) {
			summary[2][col]++;
		} else if (rainVal > 30) {
			summary[3][col]++;
		} else if (rainVal > 0) {
			summary[4][col]++;
		}
	}

	/**
	 * 获取水情统计信息
	 * 
	 * @param district
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getWaterStatics(String district) {
		if ("All".equals(district)) {
			district = null;
		}
		String alert = "1";
		List<STWater> bigMidList = new HtOauthServiceImpl().getSTBigMidRsvrInfo(DateUtil.date2Str(new Date(),
					"yyyy-MM-dd HH:mm:ss"));
		int bigMidTotal = bigMidList.size();
		bigMidList = RainWaterHelper.filterAlertRsvr(district, bigMidList,
				alert);
		int bigMidSize = bigMidList.size();

		List<STWater> small1List = new HtOauthServiceImpl().getSTSmallRsvrInfo("HT_RSVRS1_B",
				DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		int small1Total = small1List.size();
		small1List = RainWaterHelper.filterAlertRsvr(district, small1List,
				alert);
		int small1Size = small1List.size();

		List<STWater> small2List = new HtOauthServiceImpl().getSTSmallRsvrInfo("HT_RSVRS2_B",
				DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		int small2Total = small2List.size();
		small2List = RainWaterHelper.filterAlertRsvr(district, small2List,
				alert);
		int small2Size = small2List.size();

		List<RiverInfo> riverList = new HtOauthServiceImpl().getSTRiverInfo();
		int riverTotal = riverList.size();
		riverList = RainWaterHelper.filterAlertRiver(district, riverList,
				alert);
		int riverSize = riverList.size();

		List<TideInfo> tideList = new HtOauthServiceImpl().getSTTideInfo();
		int tideTotal = tideList.size();
		tideList = RainWaterHelper.filterAlertTide(district, tideList,
				alert);
		int tideSize = tideList.size();

		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("bigmid", bigMidSize);
		rtMap.put("s1", small1Size);
		rtMap.put("s2", small2Size);
		rtMap.put("tide", tideSize);
		rtMap.put("river", riverSize);
		rtMap.put("bigmid2", new int[] { bigMidSize, bigMidTotal });
		rtMap.put("s12", new int[] { small1Size, small1Total });
		rtMap.put("s22", new int[] { small2Size, small2Total });
		rtMap.put("tide2", new int[] { tideSize, tideTotal });
		rtMap.put("river2", new int[] { riverSize, riverTotal });


		String summary = "";
		summary += alertTextRiver("河道", "RIVER", riverList)
				+ alertTextRsvr("大中型水库", "BM", bigMidList)
				+ alertTextRsvr("小一型水库", "M1", small1List)
				+ alertTextRsvr("小二型水库", "M2", small2List)
				+ alertTextTide("潮位", "TIDE", tideList);
		rtMap.put("summary", summary);
		String summary2 = "";
		summary2 += alertText2("河道", "RIVER", riverList, "river") + alertText2("大中型水库", "BM", bigMidList, "rsvr")
				+ alertText2("小一型水库", "M1", small1List, "rsvr") + alertText2("小二型水库", "M2", small2List, "rsvr")
				+ alertText2("潮位", "TIDE", tideList, "tide");
		rtMap.put("summary2", summary2);
		return rtMap;
	}
	
	private static String alertText2(String title, String type, List<?> array, String stationType) {
		if (array.size() > 0) {
			String rtStr = title + "超警站点：";
			String alertStr = "";

			for (int i = 0; i < array.size(); i++) {
				String stnm = "";
				String district = "";
				String stcd = "";
				if ("rsvr".equals(stationType)) {
					STWater obj = (STWater) array.get(i);
					district = obj.getDistrict();
					stnm = obj.getStnm();
					stcd = obj.getStcd();
				} else if ("river".equals(stationType)) {
					RiverInfo obj = (RiverInfo) array.get(i);
					district = obj.getDistrict();
					stnm = obj.getStnm();
					stcd = obj.getStcd();
				} else {
					TideInfo obj = (TideInfo) array.get(i);
					district = obj.getDistrict();
					stnm = obj.getStnm();
					stcd = obj.getStcd();
				}
				alertStr += ", " + district + "的" + "<a href='javascript:alert(\"" + district + "-"
						+ HTMLFormatter.removeHtmlTag(stnm) + "-" + stcd + "-" + type + "\")'>"
						+ HTMLFormatter.removeHtmlTag(stnm) + "</a>";
			}
			return rtStr + alertStr.substring(1) + "</p>";
		} else {
			return "";
		}
	}

	private static String alertTextRiver(String title, String type,
			List<RiverInfo> array) {
		if (array.size() > 0) {
			String rtStr = title + "超警站点：";
			String alertStr = "";

			for (int i = 0; i < array.size(); i++) {
				String stnm = "";
				String district = "";
				String stcd = "";
				RiverInfo obj = (RiverInfo) array.get(i);
				district = obj.getDistrict();
				stnm = obj.getStnm();
				stcd = obj.getStcd();
				alertStr += ", " + district + "的"
						+ "<a href='javascript:showStation(" + stcd + ", \""
						+ type + "\")'>" + HTMLFormatter.removeHtmlTag(stnm)
						+ "</a>";
			}
			return rtStr + alertStr.substring(1) + "</p>";
		} else {
			return "";
		}
	}
	
	private static String alertTextRsvr(String title, String type,
			List<STWater> array) {
		if (array.size() > 0) {
			String rtStr = title + "超警站点：";
			String alertStr = "";

			for (int i = 0; i < array.size(); i++) {
				String stnm = "";
				String district = "";
				String stcd = "";

				STWater obj = (STWater) array.get(i);
				district = obj.getDistrict();
				stnm = obj.getStnm();
				stcd = obj.getStcd();
				alertStr += ", " + district + "的"
						+ "<a href='javascript:showStation(" + stcd + ", \""
						+ type + "\")'>" + HTMLFormatter.removeHtmlTag(stnm)
						+ "</a>";
			}
			return rtStr + alertStr.substring(1) + "</p>";
		} else {
			return "";
		}
	}
	
	private static String alertTextTide(String title, String type,
			List<TideInfo> array) {
		if (array.size() > 0) {
			String rtStr = title + "超警站点：";
			String alertStr = "";

			for (int i = 0; i < array.size(); i++) {
				String stnm = "";
				String district = "";
				String stcd = "";
				TideInfo obj = (TideInfo) array.get(i);
				district = obj.getDistrict();
				stnm = obj.getStnm();
				stcd = obj.getStcd();
				alertStr += ", " + district + "的"
						+ "<a href='javascript:showStation(" + stcd + ", \""
						+ type + "\")'>" + HTMLFormatter.removeHtmlTag(stnm)
						+ "</a>";
			}
			return rtStr + alertStr.substring(1) + "</p>";
		} else {
			return "";
		}
	}
	
	/**
	 * 判断是不是水利上的今天
	 * 
	 * @return boolean false 需要去昨天八点 true 需要+1天
	 * @since v 1.0
	 */
	public static boolean isWaterToday() {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		Calendar c2 = Calendar.getInstance();
		c2.setTime(DateUtil.str2Date(getToday8(), "yyyy-MM-dd HH:mm:ss"));
		switch (c1.compareTo(c2)) {
		case -1:
			return false;
		case 1:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 字符串日期计算
	 *
	 * @param dateStr
	 * @param days
	 * @param pattern
	 * @return
	 */
	public static String getDayByOffset(String dateStr, int days, String pattern) {
		if (null == pattern) 
			pattern = "yyyy-MM-dd HH:mm:ss";
		Date today = DateUtil.str2Date(dateStr, pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, days);
		return DateUtil.date2Str(calendar.getTime(), pattern);
	}
}

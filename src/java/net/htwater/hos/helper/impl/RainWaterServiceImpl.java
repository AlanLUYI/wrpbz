/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  2013年12月17日 Neal Miao 
 * 
 * Copyright(c) 2013, by htwater.net. All Rights Reserved.
 */
package net.htwater.hos.helper.impl;

import java.io.RandomAccessFile;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.htwater.hos.helper.entity.MyCaches;
import net.htwater.hos.helper.entity.STRain;
import net.htwater.hos.helper.entity.WindInfo;
import net.htwater.hos.helper.other.HTMLFormatter;
import net.htwater.hos.helper.RainWaterHelper;
import org.apache.commons.lang.StringUtils;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.DateUtil;
import cn.miao.framework.util.PinyinUtil;
import cn.miao.framework.util.StringUtil;

/**
 * 
 * @author Neal Miao
 * @version
 * @Date 2013年12月17日 下午3:36:04
 * 
 * @see
 */
public class RainWaterServiceImpl  {
	
	//BaseDao daoHtnbsqdb = DaoFactory.getDao(Constant.DBSWZ);
	BaseDao daoHtnbsqdb = DaoFactory.getDao("HT_NBSQDB");
	RainWaterHelper rwhelper = new RainWaterHelper();

	// ===============================移植部分======================================

	/**
	 * 获取区域平均雨量 时间是早上8点来计算的
	 * 
	 * @param startTime
	 * @param endTime
	 * @return String
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getDistrictAvgRain(String startTime,
			String endTime) {
		String sql = "select c.CITY, CONVERT(decimal(12, 1),sum(drn)/COUNT(distinct(stnm))) as 'VALUE' ,COUNT(distinct(stnm))as 'COUNT_POINT'"
				+ "from HT_RAIN_B a "
				+ "left join HT_STBPRP_B  c on a.ST_ID=c.ID "
				+ "left join rainday b on a.stcd =b.stcd "
				+ "where b.tm>? and b.tm<=? and enabled=1 and isstate=1 "
				+ "group by c.city";
		return daoHtnbsqdb.executeQuery(sql, new Object[] { startTime, endTime });
	}

	/**
	 * 根据地区查最大两个站点
	 * 
	 * @param district
	 * @return List<Map<String,Object>>
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getMax2StationByDistrict(String district) {
		String sql = "SELECT TOP 2 SUM(SR.drp) AS ADRN,SR.STCD AS ST,HSB.STNM AS NM "
				+ "FROM HT_PPTN_R AS SR LEFT JOIN HT_RAIN_B AS HRB ON SR.STCD=HRB.STCD LEFT JOIN HT_STBPRP_B AS HSB ON HRB.ST_ID=HSB.[ID] "
				+ "WHERE LTRIM(RTRIM(HSB.CITY))='"
				+ district
				+ "' AND HRB.ENABLED=1 "
				+ " AND SR.TM>'"
				+ RainWaterHelper.getWaterToday()
				+ "' AND SR.TM<='"
				+ RainWaterHelper.getWaterTommorow()
				+ "' GROUP BY SR.STCD,HSB.STNM ORDER BY ADRN DESC ";// AND
		// SR.drn>0
		return daoHtnbsqdb.executeQuery(sql);
	}

	/**
	 * 根据水库查最大两个站点
	 * 
	 * @param district
	 * @return List<Map<String,Object>>
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getMax2StationByRSVR(String rsvr) {
		String sql = "SELECT TOP 2 SUM(SR.drp) AS ADRN,SR.STCD AS ST,HSB.STNM AS NM "
				+ "FROM HT_PPTN_R AS SR LEFT JOIN HT_RAIN_B AS HRB ON SR.STCD=HRB.STCD LEFT JOIN HT_STBPRP_B AS HSB ON HRB.ST_ID=HSB.[ID] "
				+ "WHERE LTRIM(RTRIM(HRB.RSNM))='"
				+ rsvr
				+ "' AND HRB.ENABLED=1 "
				+ " AND SR.TM>'"
				// + RainWaterHelper.getWaterToday()
				+ RainWaterHelper.getWaterToday()
				+ "' AND SR.TM<='"
				// + RainWaterHelper.getWaterTommorow()
				+ RainWaterHelper.getWaterTommorow()
				+ "' GROUP BY SR.STCD,HSB.STNM ORDER BY ADRN DESC ";// AND
		// SR.drn>0
		return daoHtnbsqdb.executeQuery(sql);
	}

	/**
	 * 格式化最大两个站点显示
	 * 
	 * @param list
	 * @return String
	 * @since v 1.0
	 */
	private String formatStation(List<Map<String, Object>> list) {
		String rtString = "";
		for (Map<String, Object> map : list) {
			rtString += "、" + map.get("ST") + "-" + map.get("NM") + "("
					+ map.get("ADRN") + ")";
		}
		return rtString.length() < 2 ? "" : "<span style='font-size:13px'>"
				+ rtString.substring(1) + "</span>";
	}

	public List<STRain> getDistricStatics() {
		List<STRain> rtList = new ArrayList<STRain>();
		STRain stDayRain = null;
		List<Map<String, Object>> byestoday = getDistrictAvgRain(
				RainWaterHelper.getWaterBYestorday(),
				RainWaterHelper.getWaterYestorday());
		List<Map<String, Object>> yestodayList = getDistrictAvgRain(
				RainWaterHelper.getWaterYestorday(),
				RainWaterHelper.getWaterToday());
		List<Map<String, Object>> todayList = getDistrictAvgRain(
				RainWaterHelper.getWaterToday(),
				RainWaterHelper.getWaterTommorow());
		List<Map<String, Object>> sevenDayList = null;
		if (RainWaterHelper.isWaterToday()) {
			sevenDayList = getDistrictAvgRain(RainWaterHelper.getBDayByDay(-6),
					RainWaterHelper.getWaterTommorow());
		} else {
			sevenDayList = getDistrictAvgRain(RainWaterHelper.getBDayByDay(-7),
					RainWaterHelper.getWaterTommorow());
		}
		// @20121024 today是空的,则手动置其中数据为空
		if ((null==todayList || todayList.size() < 3) && null!=byestoday) { // 数据不全，不缓存
			for(Map<String , Object> map:byestoday){
				Map<String , Object> tmpmap = new HashMap<String, Object>();
				tmpmap.put("CITY", map.get("CITY"));
				tmpmap.put("VALUE", "0");
				tmpmap.put("COUNT_POINT", "0");
				todayList.add(tmpmap);
			}
		}
		for (int i = 0; i < todayList.size(); i++) {
			stDayRain = new STRain();
			String district = todayList.get(i).get("CITY").toString();
			stDayRain.setName(district);
			stDayRain.setHtmlName(HTMLFormatter.formatTypeHTML(district,
					"district"));
			stDayRain.setTodayRain(todayList.get(i).get("VALUE").toString());
			stDayRain.setYestodayRain(yestodayList.get(i).get("VALUE")
					.toString());
			stDayRain
					.setByestodayRain(byestoday.get(i).get("VALUE").toString());
			stDayRain.setSum7(sevenDayList.get(i).get("VALUE").toString());
			String otherInfo = formatStation(getMax2StationByDistrict(district));
			stDayRain.setOtherInfo(otherInfo);
			if (stDayRain.getName().contains("市区")) {
				stDayRain.setOrder(1);
			} else if (stDayRain.getName().contains("镇海")) {
				stDayRain.setOrder(2);
			} else if (stDayRain.getName().contains("北仑")) {
				stDayRain.setOrder(3);
			} else if (stDayRain.getName().contains("鄞州")) {
				stDayRain.setOrder(4);
			} else if (stDayRain.getName().contains("余姚")) {
				stDayRain.setOrder(5);
			} else if (stDayRain.getName().contains("慈溪")) {
				stDayRain.setOrder(6);
			} else if (stDayRain.getName().contains("宁海")) {
				stDayRain.setOrder(7);
			} else if (stDayRain.getName().contains("奉化")) {
				stDayRain.setOrder(8);
			} else if (stDayRain.getName().contains("象山")) {
				stDayRain.setOrder(9);
			} else {
				stDayRain.setOrder(10);
			}
			rtList.add(stDayRain);
		}
		Collections.sort(rtList, new Comparator<STRain>() {
			
			public int compare(STRain o1, STRain o2) {
				if (o1.getOrder() > o2.getOrder()) {
					return 1;
				} else if (o1.getOrder() == o2.getOrder()) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		
		return rtList;
	}
	
	public List<STRain> getReservoirStatics() {
		List<STRain> rtList = new ArrayList<STRain>();
		STRain stDayRain = null;
		for (String stnm : MyCaches.bigRsvrs) {
			Map<String, Object> map1 = getRsvr(stnm,
					RainWaterHelper.getWaterBYestorday(),
					RainWaterHelper.getWaterYestorday());
			Map<String, Object> map2 = getRsvr(stnm,
					RainWaterHelper.getWaterYestorday(),
					RainWaterHelper.getWaterToday());
			Map<String, Object> map3 = getRsvr(stnm,
					RainWaterHelper.getWaterToday(),
					RainWaterHelper.getWaterTommorow());
			Map<String, Object> map4 = null;
			if (RainWaterHelper.isWaterToday()) {
				map4 = getRsvr(stnm, RainWaterHelper.getBDayByDay(-6),
						RainWaterHelper.getWaterTommorow());
			} else {
				map4 = getRsvr(stnm, RainWaterHelper.getBDayByDay(-7),
						RainWaterHelper.getWaterTommorow());
			}
			stDayRain = new STRain();
			stDayRain.setName(stnm);
			stDayRain.setHtmlName(HTMLFormatter.formatTypeHTML(
					stnm.replaceAll("水库", ""), "rsvr"));
			stDayRain.setTodayRain(map3.get("ADRN").toString());
			stDayRain.setYestodayRain(map2.get("ADRN").toString());
			stDayRain.setByestodayRain(map1.get("ADRN").toString());
			stDayRain.setSum7(map4.get("ADRN").toString());
			String otherInfo = formatStation(getMax2StationByRSVR(stnm
					.replaceAll("水库", "")));
			stDayRain.setOtherInfo(otherInfo);
			rtList.add(stDayRain);
		}
		if (rtList.size() < 3) { // 数据不全，不缓存
			return new ArrayList<STRain>();
		}
		return rtList;
	}

	/**
	 * 获取水库雨量
	 * 
	 * @param stnm
	 * @param startTime
	 * @param endTime
	 * @return Map<String,Object>
	 * @since v 1.0
	 */
	private Map<String, Object> getRsvr(String stnm, String startTime,
			String endTime) {
		stnm = stnm.replaceAll("水库", "");
		String sqlCoef = "SELECT SUM(coef) AS CCOT FROM HT_RAIN_B WHERE RSNM='"
				+ stnm + "' AND ENABLED=1";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sqlCoef);
		String coef = map.get("CCOT").toString();
		String sql = "SELECT SUM(SR.drp*RB.COEF/"
				+ coef
				+ ") AS ADRN,COUNT(DISTINCT SR.STCD) AS CCOT "
				+ " FROM HT_PPTN_R AS SR LEFT JOIN HT_RAIN_B AS RB ON SR.STCD=RB.STCD LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.[ID] "
				+ " WHERE LTRIM(RTRIM(RB.RSNM))=? AND RB.ENABLED=1  "
				+ " AND SR.TM>? AND SR.TM<=?";
		Map<String, Object> rtMap = daoHtnbsqdb.executeQueryObject(sql,
				new Object[] { stnm, startTime, endTime });
		if (null == rtMap.get("ADRN")) {
			rtMap.put("ADRN", 0);
		}
		return rtMap;
	}




	
	

	/**
	 * 获取入库时间
	 * 
	 * @param stcd
	 * @return String
	 * @since v 1.0
	 */
	private Map<String, Object> getWaterMaxTime(String stcd,String tm) {
		String sql = "SELECT  top 1 rz as Z, tm as TM FROM ST_RSVR_R WHERE stcd=? and tm <= ? order by tm desc";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql, new Object[] { stcd,tm });
		if (null == map) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	/**
	 * 获取库容
	 * 
	 * @param stcd
	 * @param sw
	 *            实时水位
	 * @return double
	 * @since v 1.0
	 */
	private double getResKR(String stcd, double sw) {
		// 由于站码被修改，这里做些处理
		if (stcd.indexOf("-") > -1) {
			stcd = stcd.split("-")[0];
		}
		String sqlKR1 = "select top 1 RZ,W from  HT_ZVARL_B where stcd=? and RZ>=? order by RZ";
		String sqlKR2 = "select top 1 RZ,W from  HT_ZVARL_B where stcd=? and RZ<=? order by RZ desc";
		Map<String, Object> map1 = daoHtnbsqdb.executeQueryObject(sqlKR1,
				new Object[] { stcd, sw });
		Map<String, Object> map2 = daoHtnbsqdb.executeQueryObject(sqlKR2,
				new Object[] { stcd, sw });
		double result = 0;

		if (null != map1 && null != map2) {
			double maxKur = Double.parseDouble(map1.get("W").toString());
			double minKur = Double.parseDouble(map2.get("W").toString());
			if (minKur == maxKur)
				return minKur;
			else {
				double maxShui = Double.parseDouble(map1.get("RZ").toString());
				double minShui = Double.parseDouble(map2.get("RZ").toString());
				result = StringUtil.double2String(minKur
						+ ((maxKur - minKur) / (maxShui - minShui))
						* (sw - minShui), 2);
				return result;
			}
		} else {
			if (null != map1) {
				result = StringUtil.double2String(
						Double.parseDouble(map1.get("W").toString()), 2);
				return result;
			} else if (null != map2) {
				result = StringUtil.double2String(
						Double.parseDouble(map2.get("W").toString()), 2);
				return result;
			} else
				return 0;
		}
	}

	private Map<String, Object> getMaxM8Time(String stcd, String MaxM8) {
		String sql = "SELECT  top 1 CONVERT(decimal(12, 2),rz) as Z8, tm as TM FROM ST_RSVR_R with (nolock) WHERE stcd=? and rz<>'-99' and tm='"
				+ MaxM8 + "'";
		return daoHtnbsqdb.executeQueryObject(sql, new Object[] { stcd });
	}
	


	/**
	 * 获取河道水位趋势
	 * 
	 * @param stcd
	 * @return String
	 * @since v 1.0
	 */
	private String getRiverTrendHtml(String stcd) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -20);
		String sql = "SELECT TOP 2 z FROM ST_RIVER_R WHERE stcd=? AND z<>-99 AND tm<? AND tm>? ORDER BY tm DESC";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(
				sql,
				new Object[] {
						stcd,
						DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss"),
						DateUtil.date2Str(calendar.getTime(),
								"yyyy-MM-dd HH:mm:ss") });
		return getTrendHtml(list);
	}

	/**
	 * 获取河道水位趋势-ByTm zzj 2014.6.25 根据站码和时间获取某站某时刻涨落趋势
	 * 
	 * @param stcd
	 * @param tm
	 * @return String
	 * @since v 1.0
	 */
	private String getRiverTrendHtmlByStcdAndTm(String stcd, String tm) {
		String sql = "SELECT TOP 2 z FROM ST_RIVER_R WHERE stcd=? AND z<>-99 AND tm<=? ORDER BY tm DESC";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sql,
				new Object[] { stcd, tm });
		return getTrendHtml(list);
	}

	/**
	 * 获取趋势
	 * 
	 * @param list
	 * @return String
	 * @since v 1.0
	 */
	private String getTrendHtml(List<Map<String, Object>> list) {
		if (list.size() != 2) {
			return "";
		}
		double z1 = Double.parseDouble(list.get(0).get("z").toString());
		double z2 = Double.parseDouble(list.get(1).get("z").toString());
		if (z1 > z2) {
			return "<font color=\"#ff0000\">↑</font>";
		} else if (z1 == z2) {
			return "<font color=\"#666666\">-</font>";
		} else {
			return "<font color=\"#3399ff\">↓</font>";
		}
	}

	/**
	 * 获取河道85水位
	 * 
	 * @param stcd
	 * @return String
	 * @since v 1.0
	 */
	private Map<String, Object> getRiverSWByStcd(String stcd) {
		String sql = "SELECT TOP 1 z, tm FROM ST_RIVER_R"
				+ " WHERE stcd=? AND z<>-99 ORDER BY TM DESC";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql,
				new Object[] { stcd });
		if (null == map) {
			map = new HashMap<String, Object>();
			map.put("z", "0");
			map.put("tm", ". .");
		}
		return map;
	}

	/**
	 * 获取河道85水位-ByTm zzj 2014.6.25 添加根据时间获取某站点水位
	 * 
	 * @param stcd
	 * @param tm
	 * @return Map<String, Object>
	 * @since v 1.0
	 */
	private Map<String, Object> getRiverSWByStcdAndTm(String stcd, String tm) {
		String sql = "SELECT TOP 1 z, tm FROM ST_RIVER_R"
				+ " WHERE stcd=? AND z<>-99 AND tm<=? ORDER BY TM DESC";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql,
				new Object[] { stcd, tm });
		if (null == map) {
			map = new HashMap<String, Object>();
			map.put("z", "0");
			map.put("tm", ". .");
		}
		return map;
	}
	


	/**
	 * 获取潮位85水位
	 *
	 * @param stcd
	 * @return String
	 * @since v 1.0
	 */
	private Map<String, Object> getTideSWByStcd(String stcd) {
		String sql = "SELECT TOP 1 tdz AS z, tm FROM ST_TIDE_R"
				+ " WHERE stcd=? AND tdz<>-99 ORDER BY TM DESC";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql,
				new Object[] { stcd });
		if (null == map) {
			map = new HashMap<String, Object>();
			map.put("z", "0");
			map.put("tm", ". .");
		}
		return map;
	}

	/**
	 * 获取潮位85水位
	 * 
	 * @author zzj
	 * @param stcd
	 * @return String
	 * @since v 1.0
	 */
	private Map<String, Object> getTideSWByStcdAndTm(String stcd, String tm) {
		String sql = "SELECT TOP 1 tdz AS z, tm FROM ST_TIDE_R"
				+ " WHERE stcd=? AND tdz<>-99 and tm<=? ORDER BY TM DESC";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql,
				new Object[] { stcd, tm });
		if (null == map) {
			map = new HashMap<String, Object>();
			map.put("z", "0");
			map.put("tm", ". .");
		}
		return map;
	}

	/**
	 * 获取潮位水位趋势
	 * 
	 * @param stcd
	 * @return String
	 * @since v 1.0
	 */
	private String getTideTrendHtml(String stcd) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -20);
		String sql = "SELECT TOP 2 tdz AS z FROM ST_TIDE_R WHERE stcd=? AND tdz<>-99 AND tm<? AND tm>? ORDER BY tm DESC";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(
				sql,
				new Object[] {
						stcd,
						DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss"),
						DateUtil.date2Str(calendar.getTime(),
								"yyyy-MM-dd HH:mm:ss") });
		return getTrendHtml(list);
	}

	/**
	 * 获取潮位水位趋势
	 * 
	 * @author zzj
	 * @param stcd
	 * @return String
	 * @since v 1.0
	 */
	private String getTideTrendHtmlByStcdAndTm(String stcd, String tm) {
		// Date date = new Date();
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(date);
		// calendar.add(Calendar.MINUTE, -20);
		String sql = "SELECT TOP 2 tdz AS z FROM ST_TIDE_R WHERE stcd=? AND tdz<>-99 AND tm<=? ORDER BY tm DESC";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sql,
				new Object[] { stcd, tm });
		return getTrendHtml(list);
	}

	
	public List<WindInfo> getWindInfo(int days) {
		WindInfo w =null;
		String sql = "declare @tm1 datetime,@tm2 datetime,@tm3 datetime;"
				+ " set @tm1=GETDATE();set @tm2=DATEADD(MINUTE,-10,@tm1);"
				+ " set @tm3=DATEADD(DAY,-1*?,@tm1);"
				+ " select sb.stnm, rtrim(rb.stcd) as stcd,"
				+ " (SELECT  max(tm) FROM ST_WDWV_R WHERE stcd=rb.stcd)tm,"
				+ " (select top 1 wndpwr from st_wdwv_r where stcd=rb.stcd and tm>@tm2 and tm<@tm1 order by tm desc)wndpwr,"
				+ " (select top 1 wndv from st_wdwv_r where stcd=rb.stcd and tm>@tm2 and tm<@tm1 order by tm desc)wndv,"
				+ " (select top 1 rtrim(wnddir) from st_wdwv_r where stcd=rb.stcd and tm>@tm2 and tm<@tm1 order by tm desc)wnddir,"
				+ " (select max(wndv) from st_wdwv_r where stcd=rb.stcd and tm>@tm3 and tm<@tm1)wndpwrMax"
				+ " from ht_wind_b as rb left join ht_stbprp_b as sb on rb.st_id=sb.id "
				+ " where enabled=1 order by ordno";
		List<WindInfo> rtList = new ArrayList<WindInfo>();
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(
				sql, new Object[]{days});
		for (Map<String, Object> map : list) {
			w = new WindInfo();
			String tm = (null==map.get("tm"))?"":map.get("tm").toString();
			String stnm = (null==map.get("stnm"))?"":map.get("stnm").toString();
			String wndpwrMax = (null==map.get("wndpwrMax"))
					?"":map.get("wndpwrMax").toString();
			String wndpwr = (null==map.get("wndpwr"))
					?"":map.get("wndpwr").toString();
			String wndv = (null==map.get("wndv"))?"":map.get("wndv").toString();
			String wnddir = (null==map.get("wnddir"))
					?"":map.get("wnddir").toString();
			
			w.setTm(tm);
			w.setWndName(stnm);
			w.setMaxValue(wndpwrMax);
			w.setWndPwr("".equals(wndpwr)?"-":wndpwr);
			w.setWndDirection("".equals(wnddir)?"-":wnddir);
			w.setWndSpeed("".equals(wndv)?"-":wndv);
			rtList.add(w);
		}

		return rtList;
	}

	private String getMaxWindTm(String stcd) {
		String sql = "SELECT  max(tm) as tm FROM ST_WDWV_R WHERE stcd=?";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql,
				new Object[] { stcd });
		return map.get("tm").toString();
	}

	private void setWindDetail(String stcd, WindInfo w) {
		String wndpwr = "-";
		String wndv = "-";
		String wnddir = "-";
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -10);
		String strSql = "SELECT TOP 1 wndpwr,wndv,rtrim(wnddir) as wnddir FROM ST_WDWV_R WHERE stcd=? AND tm>? AND tm<? ORDER BY TM DESC";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(
				strSql,
				new Object[] {
						stcd,
						DateUtil.date2Str(calendar.getTime(),
								"yyyy-MM-dd HH:mm:ss"),
						DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss") });
		if (null != map) {
			wndpwr = map.get("wndpwr").toString();
			wndv = map.get("wndv").toString();
			wnddir = map.get("wnddir").toString();
		}
		w.setWndPwr(wndpwr);
		w.setWndDirection(wnddir);
		w.setWndSpeed(wndv);
	}

	/**
	 * 获取最大值
	 * 
	 * @param stcd
	 * @param days
	 * @return String
	 */
	private String getMaxWinValue(String stcd, int days) {
		String wndpwrMax = "-";
		Date date = new Date();
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(date);
		endTime.add(Calendar.DATE, -1 * days);
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(date);
		startTime.add(Calendar.MINUTE, 10);
		String strSql = "SELECT MAX(wndv) AS WV FROM ST_WDWV_R WHERE stcd=? AND tm<? AND tm>=?";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(
				strSql,
				new Object[] {
						stcd,
						DateUtil.date2Str(startTime.getTime(),
								"yyyy-MM-dd HH:mm:ss"),
						DateUtil.date2Str(endTime.getTime(),
								"yyyy-MM-dd HH:mm:ss") });
		if (null == map.get("WV")) {
			wndpwrMax = "-";
		} else {
			wndpwrMax = map.get("WV").toString();
		}
		return wndpwrMax;
	}
	
	
	public List<Map<String, Object>> getFlowInfo() {
		String sql = "select hf.*,(select top 1 z from st_river_r "
				+ " where stcd=hf.stcd and z<>-99 order by tm desc)z,"
				+ "(select top 1 q from st_river_r where stcd=hf.stcd and z<>-99 order by tm desc)q,"
				+ "(select top 1 xsa from st_river_r where stcd=hf.stcd and z<>-99 order by tm desc)xsa,"
				+ "(select top 1 xsavv from st_river_r where stcd=hf.stcd and z<>-99 order by tm desc)xsavv,"
				+ "(select top 1 tm from st_river_r where stcd=hf.stcd and z<>-99 order by tm desc)tm "
				+ "from HT_FLOW hf where ENABLED=1;";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sql);
		for (Map<String, Object> map : list) {
			map.put("STNMHTML", HTMLFormatter.formatStationHTML(map.get("STNM")
					.toString(), map.get("STCD").toString()));
			map.put("z", null==map.get("z")?"-":map.get("z"));
			map.put("q", null==map.get("q")?"-":map.get("q"));
			map.put("tm", null==map.get("tm")?"-":map.get("tm"));
			map.put("xsa", null==map.get("xsa")?"-":map.get("xsa"));
			map.put("xsavv", null==map.get("xsavv")?"-":map.get("xsavv"));
		}
		return list;
	}

	/**
	 * 根据站点获取流量流速
	 * 
	 * @param stcd
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getFlowByStcd(String stcd) {
		String sql = "SELECT TOP 1 z AS z,q,xsa,xsavv,tm FROM ST_RIVER_R"
				+ " WHERE stcd='" + stcd + "' AND z<>-99 ORDER BY TM DESC";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql);
		if (map != null) {
			RainWaterHelper.formatFlowMap(map, "z");
			RainWaterHelper.formatFlowMap(map, "q");
			RainWaterHelper.formatFlowMap(map, "tm");
			RainWaterHelper.formatFlowMap(map, "xsa");
			RainWaterHelper.formatFlowMap(map, "xsavv");
		} else {
			map = new HashMap<String, Object>();
			map.put("z", "-");
			map.put("q", "-");
			map.put("tm", "-");
			map.put("xsa", "-");
			map.put("xsavv", "-");
		}
		return map;
	}

	/**
	 * 获取入库时间
	 * 
	 * @param stcd
	 * @return String
	 * @since v 1.0
	 */
	private String getRainMaxTime(String stcd) {
		String sql = "SELECT  max(tm) as tm FROM ST_PPTN_R WHERE stcd=?";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql,
				new Object[] { stcd });
		return null == map.get("tm") ? "00-00 00:00" : map.get("tm").toString();
	}

	/**
	 * 根据站点获取时段雨量
	 * 
	 * @param stcd
	 * @param startTime
	 * @param endTime
	 * @return String
	 * @since v 1.0
	 */
	private String getStataionDayRain(String stcd, String startTime,
			String endTime) {
		String sql = "SELECT SUM(drn) AS ADRN FROM rainday WHERE stcd=? AND tm>? AND tm<=?";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql,
				new Object[] { stcd, startTime, endTime });
		if (null == map.get("ADRN")) {
			return "0";
		} else {
			return map.get("ADRN").toString();
		}
	}

	/**
	 * 根据站点获取时段雨量
	 * 
	 * @param stcd
	 * @param startTime
	 * @param endTime
	 * @return String
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getRainInfoByDay(String stcd,
			String startTime, String endTime) {
		startTime = startTime + " 08:00:00";
		endTime = endTime + " 08:00:00";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Date sDate = new Date();
		Date eDate = new Date();
		try {
			sDate = df.parse(startTime);
			eDate = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		if(eDate.getTime()-sDate.getTime()<=24*60*60*1000)
		{
			return daoHtnbsqdb.executeQuery("{call proc_rain_station_hour(?,?,?)}",new Object[]{stcd,startTime,endTime});
		}
		else{
			if (RainWaterHelper.isWaterToday()) {
				startTime = RainWaterHelper.getDayByOffset(startTime, 1, null);
				endTime = RainWaterHelper.getDayByOffset(endTime, 1, null);
			}
			String sql = "SELECT drn AS sum, tm as time FROM RAINDAY WHERE stcd=? AND tm>=? AND tm<=?";
			return daoHtnbsqdb.executeQuery(sql, new Object[] { stcd, startTime,
					endTime });
		}
	}

	/**
	 * 根据站点获取时段雨量
	 * 
	 * @param stcd
	 * @param startTime
	 * @param endTime
	 * @return String
	 * @since v 1.0
	 */
	private String getStataionRain(String stcd, String startTime, String endTime) {
		String sql = "SELECT SUM(drp) AS ADRN FROM HT_PPTN_R WHERE stcd=? AND drp>0 AND tm>? AND tm<=?";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql,
				new Object[] { stcd, startTime, endTime });
		if (null == map.get("ADRN")) {
			return "0";
		} else {
			return map.get("ADRN").toString();
		}
	}
	

	
	
	public List<STRain> getSTRsvrRainInfo() {
		String sqlAll = "declare @curtm datetime,@pTM8 datetime;set @curtm = ?;"
				+ " set @pTM8 = dbo.getWater8Point(@curtm,2); "
				+ " select rtrim(rb.stcd) as stcd,sb.stnm,rb.valley,rb.rsnm "
				+ " ,(select top 1 tm from ST_PPTN_r order by tm desc)tm,"
				+ " (select sum(drn) as adrn from rainday  where stcd=rb.stcd "
				+ " and tm>@pTM8 and tm<=DATEADD(D,1,@pTM8))tdrain,"
				+ " (select sum(drn) as adrn from rainday where stcd=rb.stcd  "
				+ " and tm>DATEADD(D,-1,@pTM8) and tm<=@pTM8)ydrain, "
				+ " (select sum(drn) as adrn from rainday where stcd=rb.stcd  "
				+ " and tm>DATEADD(D,-2,@pTM8) and tm<=DATEADD(D,-1,@pTM8))bydrain, "
				+ " (select sum(drn) as adrn from rainday where stcd=rb.stcd  "
				+ " and tm>DATEADD(D,-7,@pTM8) and tm<=@pTM8)sum7rain"
				+ " FROM HT_RAIN_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID"
				+ " WHERE ISRSVR=1 AND ENABLED=1 ORDER BY RB.ID";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sqlAll,
				new Object[]{DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")});
		// List<STRain> rtList = new ArrayList<STRain>();
		List<STRain> allList = new ArrayList<STRain>();
		STRain stDayRain = null;
		// Map<String, List<STRain>> cacheMap = new HashMap<String,
		// List<STRain>>();
		for (Map<String, Object> map : list) {
			stDayRain = new STRain();
			String stcd = null==map.get("stcd")?"":map.get("stcd").toString();
			String stnm = null==map.get("stnm")?"":map.get("stnm").toString();
			String rsvr = null==map.get("rsnm")?"":map.get("rsnm").toString();
			String TM = null==map.get("tm")?"":map.get("tm").toString();
			String valley = null==map.get("valley")?"":map.get("valley").toString();
			double tdrain = (null==map.get("tdrain") || "".equals(map.get("tdrain")))
					?0.0:Double.parseDouble(map.get("tdrain").toString());
			double ydrain = (null==map.get("ydrain") || "".equals(map.get("ydrain")))
					?0.0:Double.parseDouble(map.get("ydrain").toString());
			double bydrain = (null==map.get("bydrain") || "".equals(map.get("bydrain")))
					?0.0:Double.parseDouble(map.get("bydrain").toString());
			double sum7rain = (null==map.get("sum7rain") || "".equals(map.get("sum7rain")))
					?0.0:Double.parseDouble(map.get("sum7rain").toString());
			
			stDayRain.setTm(TM);
			stDayRain.setCode(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			stDayRain.setStcd(stcd);
			stDayRain.setName(stnm);
			stDayRain.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			stDayRain.setTodayRain(""+tdrain);
			stDayRain.setYestodayRain(""+ydrain);
			stDayRain.setByestodayRain(""+bydrain);
			stDayRain.setSum7(""+sum7rain);

			stDayRain.setOtherInfo(valley);
			stDayRain.setDistrict(rsvr+"水库");
			allList.add(stDayRain);
		}
		return allList;
	}

	/**
	 * 获取标题信息
	 * 
	 * @param stcd
	 * @return Map<String,Object>
	 * @since v 1.0
	 */
	private Map<String, Object> getTitleInfo(String stcd) {
		String titleSql = "select top 1 STNM, CITY FROM HT_STBPRP_B where GSTCD=? or SSTCD =?";
		Map<String, Object> titleMap = daoHtnbsqdb.executeQueryObject(titleSql,
				new Object[] { stcd, stcd });
		return titleMap;
	}

	/**
	 * 8时以前，倒退1天
	 * 
	 * @param dateStr
	 * @return String
	 * @since v 1.0
	 */
	private String convertSTDate(String dateStr) {
		if (dateStr.equals(DateUtil.getToday("yyyy-MM-dd"))) {
			if (RainWaterHelper.isNotWaterToday()) {
				return DateUtil.getDayByOffset(-1);
			} else {
				return dateStr;
			}
		} else {
			return dateStr;
		}
	}

	/**
	 * 查询站点指定日期的降雨情况
	 * 
	 * @param stcd
	 * @param dateStr
	 * @return List<Map<String,Object>>
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getRainInfoByDay(String stcd,
			String dateStr) {
		Date stDate = DateUtil.str2Date(convertSTDate(dateStr) + " 08:00:00",
				"yyyy-MM-dd HH:00:00");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(stDate);
		Map<String, Object> rtMap = null;
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 24; i++) {
			String stTime = DateUtil.date2Str(calendar.getTime(),
					"yyyy-MM-dd HH:00:00");
			calendar.add(Calendar.HOUR, 1);

			/*
			 * if (endCalendar.getTime().before(calendar.getTime())) { break; }
			 */
			String endTime = DateUtil.date2Str(calendar.getTime(),
					"yyyy-MM-dd HH:00:00");
			String sql = "SELECT SUM(drp) AS S ,COUNT(drp) AS CO FROM ST_PPTN_R WHERE stcd=?"
					+ "  AND tm>? AND tm<=?";
			// AND drp>0
			Map<String, Object> map = daoHtnbsqdb.executeQueryObject(sql,
					new Object[] { stcd, stTime, endTime });
			rtMap = new HashMap<String, Object>();
			rtMap.put("time", endTime);
			int cnt = Integer.parseInt(map.get("CO").toString());
			if (cnt > 0) {
				double sum = Double.parseDouble(map.get("S").toString());
				sum = Math.floor(sum * 100 + 0.5) / 100;
				rtMap.put("sum", sum);
			} else {
				rtMap.put("sum", "");
				// rtMap.put("sum", 0.0);
			}
			rtList.add(rtMap);
		}
		return rtList;
	}

	/**
	 * 查询站点指定日期的降雨情况
	 * 
	 * @param stcd
	 * @param dateSt
	 * @param dateEd
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> getRainInfoByDayOld(String stcd,
			String dateSt, String dateEd) {
		Date stDate = DateUtil.str2Date(dateSt + " 08:00:00",
				"yyyy-MM-dd HH:00:00");

		Calendar stCalendar = Calendar.getInstance();
		stCalendar.setTime(stDate);
		stCalendar.add(Calendar.DATE, 0);
		Date edDate = DateUtil.str2Date(dateEd + " 08:00:00",
				"yyyy-MM-dd HH:00:00");
		Calendar edCalendar = Calendar.getInstance();
		edCalendar.setTime(edDate);
		edCalendar.add(Calendar.DATE, 0);
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(stDate);
		Map<String, Object> rtMap = null;
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String stTime = "";
		String edTime = "";
		while (stCalendar.getTime().before(edCalendar.getTime())) {

			rtMap = new HashMap<String, Object>();
			rtMap.put("time", DateUtil.date2Str(stCalendar.getTime(),
					"yyyy-MM-dd HH:00:00"));

			// stCalendar.add(Calendar.DATE, 1);
			stTime = DateUtil.date2Str(stCalendar.getTime(),
					"yyyy-MM-dd HH:00:00");
			stCalendar.add(Calendar.DATE, +1);
			edTime = DateUtil.date2Str(stCalendar.getTime(),
					"yyyy-MM-dd HH:00:00");
			rtMap.put("sum", getStataionRain(stcd, stTime, edTime));
			rtList.add(rtMap);
		}
		return rtList;
	}

	
	public Map<String, Object> getRainDetail(String stcd, String dateStr) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("title", getTitleInfo(stcd));
		rtMap.put("datas", getRainInfoByDay(stcd, dateStr));
		return rtMap;
	}

	
	public Map<String, Object> getRainDetail(String stcd, String dateSt,
			String dateEd) {
		if (null == dateEd) {
			return getRainDetail(stcd, dateSt);
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("title", getTitleInfo(stcd));
		rtMap.put("datas", getRainInfoByDay(stcd, dateSt, dateEd));
		return rtMap;
	}

	/**
	 * 获取警戒水位
	 * 
	 * @param stcd
	 * @param type
	 * @return double
	 * @since v 1.0
	 */
	private Map<String, Object> getRsvrInfo(String stcd, String type) {
		String sqlStr = "";
		boolean bTaiXun = false;
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.get(Calendar.YEAR);
		String nowTime = DateUtil.date2Str(now, "yyyy-MM-dd HH:mm:ss");
		String txStTime = calendar.get(Calendar.YEAR) + "-7-15 00:00:00";
		String txEndTime = calendar.get(Calendar.YEAR) + "-10-16 00:00:00";
		if (DateUtil.compareStrDate(nowTime, txStTime) >= 0
				&& DateUtil.compareStrDate(nowTime, txEndTime) <= 0)
			bTaiXun = true;
		if ("M1".equals(type)) {// 小一型
			if (bTaiXun)
				sqlStr = "SELECT CITY,STNM,TXSW AS MXSW FROM HT_RSVRS1_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
						+ stcd + "' ";
			else
				sqlStr = "SELECT CITY,STNM,MXSW FROM HT_RSVRS1_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
						+ stcd + "' ";
		} else if ("M2".equals(type)) {// 小二型
			if (bTaiXun)
				sqlStr = "SELECT CITY,STNM, TXSW AS MXSW FROM HT_RSVRS2_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
						+ stcd + "' ";
			else
				sqlStr = "SELECT CITY,STNM, MXSW FROM HT_RSVRS2_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
						+ stcd + "' ";
		} else if ("81".equals(type)) {// 81

			if (bTaiXun)
				sqlStr = "SELECT CITY,STNM,TXSW AS MXSW FROM HT_RS8RV1_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
						+ stcd + "' ";
			else
				sqlStr = "SELECT CITY,STNM,MXSW FROM HT_RS8RV1_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
						+ stcd + "' ";
		} else if ("BN".equals(type)) { // 在建
			if (bTaiXun)
				sqlStr = "SELECT CITY,STNM,TXSW AS MXSW FROM HT_RSVRBN_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
						+ stcd + "' ";
			else
				sqlStr = "SELECT CITY,STNM,MXSW FROM HT_RSVRBN_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
						+ stcd + "' ";
		} else { // "BM" 大中
			// if (bTaiXun)
			// sqlStr =
			// "SELECT CITY,STNM,TXSW AS MXSW FROM HT_RSVRBM_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
			// + stcd + "' ";
			// else
			// sqlStr =
			// "SELECT CITY,STNM,MXSW FROM HT_RSVRBM_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
			// + stcd + "' ";
			sqlStr = "SELECT CITY,STNM,MXSW=case when getdate()>=convert(varchar(5),getdate(),120)+RB.MXEndTime and getdate()<=convert(varchar(5),getdate(),120)+'10-16' then RB.TXSW when getdate()<convert(varchar(5),getdate(),120)+RB.MXEndTime or getdate()>convert(varchar(5),getdate(),120)+'10-16' then RB.MXSW end FROM HT_RSVRBM_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.STCD='"
					+ stcd + "' ";
		}
		return daoHtnbsqdb.executeQueryObject(sqlStr);
	}

	/**
	 * 查询水库站点指定日期的水位情况
	 * 
	 * @param stcd
	 * @param dateStr
	 * @return List<Map<String,Object>>
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getRsvrInfoByDay(String stcd,
			String dateStr) {
		String stTime = convertSTDate(dateStr) + " 08:00:00";
		Date stDate = DateUtil.str2Date(stTime, "yyyy-MM-dd HH:00:00");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(stDate);
		calendar.add(Calendar.DATE, 1);
		String endTime = DateUtil.date2Str(calendar.getTime(),
				"yyyy-MM-dd HH:00:00");
		String sqlStr = "SELECT rz, tm FROM ST_RSVR_R  WHERE stcd='" + stcd
				+ "' AND rz<>-99  AND tm>='" + stTime + "'  AND tm<'" + endTime
				+ "' ORDER BY tm";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sqlStr);
		for (Map<String, Object> map : list) {
			map.put("tm", DateUtil.date2Str((Date) map.get("tm"),
					"yyyy-MM-dd HH:mm:ss"));
		}
		return list;
	}

	
	public Map<String, Object> getRsvrDetail(String stcd, String dateStr,
			String type) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("rsvrInfo", getRsvrInfo(stcd, type));
		rtMap.put("datas", getRsvrInfoByDay(stcd, dateStr));
		return rtMap;
	}

	/**
	 * 查询水库站点指定日期段的水位情况
	 * 
	 * @param stcd
	 * @param dateSt
	 * @param dateEd
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> getRsvrInfoByDay(String stcd,
			String dateSt, String dateEd) {

		Date stDate = DateUtil.str2Date(dateSt + " 08:00:00",
				"yyyy-MM-dd HH:00:00");

		Calendar stCalendar = Calendar.getInstance();
		stCalendar.setTime(stDate);
		// stCalendar.add(Calendar.DATE, 1);
		Date edDate = DateUtil.str2Date(dateEd + " 08:00:00",
				"yyyy-MM-dd HH:00:00");
		Calendar edCalendar = Calendar.getInstance();
		edCalendar.setTime(edDate);
		edCalendar.add(Calendar.DATE, 1);

		String stTime = DateUtil.date2Str(stCalendar.getTime(),
				"yyyy-MM-dd HH:00:00");
		stCalendar.add(Calendar.DATE, +1);
		String endTime = DateUtil.date2Str(edCalendar.getTime(),
				"yyyy-MM-dd HH:00:00");

		String sqlStr = "SELECT rz, tm FROM ST_RSVR_R  WHERE stcd='" + stcd
				+ "' AND rz<>-99  AND tm>='" + stTime + "'  AND tm<'" + endTime
				+ "' ORDER BY tm";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sqlStr);
		for (Map<String, Object> map : list) {
			map.put("tm", DateUtil.date2Str((Date) map.get("tm"),
					"yyyy-MM-dd HH:mm:ss"));
		}
		return list;
	}

	
	public Map<String, Object> getRsvrDetail(String stcd, String stDate,
			String edDate, String type) {
		if (null == edDate) {
			return getRsvrDetail(stcd, stDate, type);
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("rsvrInfo", getRsvrInfo(stcd, type));
		rtMap.put("datas", getRsvrInfoByDay(stcd, stDate, edDate));
		return rtMap;
	}

	/**
	 * 河道信息
	 * 
	 * @param stcd
	 * @return Map<String,Object>
	 * @since v 1.0
	 */
	private Map<String, Object> getRiverInfo(String stcd) {
		String sqlStr = "SELECT GUARD,STNM,CITY FROM HT_RIVER_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.stcd='"
				+ stcd + "'";
		return daoHtnbsqdb.executeQueryObject(sqlStr);
	}

	/**
	 * 查询河道站点指定日期的水位情况
	 * 
	 * @param stcd
	 * @param dateSt
	 * @param dateEd
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> getRiverInfoByDay(String stcd,
			String dateSt, String dateEd) {
		Date stDate = DateUtil.str2Date(dateSt + " 08:00:00",
				"yyyy-MM-dd HH:00:00");

		Calendar stCalendar = Calendar.getInstance();
		stCalendar.setTime(stDate);
		// stCalendar.add(Calendar.DATE, 1);
		Date edDate = DateUtil.str2Date(dateEd + " 08:00:00",
				"yyyy-MM-dd HH:00:00");
		Calendar edCalendar = Calendar.getInstance();
		edCalendar.setTime(edDate);
		edCalendar.add(Calendar.DATE, 1);

		String stTime = DateUtil.date2Str(stCalendar.getTime(),
				"yyyy-MM-dd HH:00:00");
		stCalendar.add(Calendar.DATE, +1);
		String endTime = DateUtil.date2Str(edCalendar.getTime(),
				"yyyy-MM-dd HH:00:00");
		String sqlStr = "SELECT z, tm FROM ST_RIVER_R  WHERE stcd='" + stcd
				+ "' AND z<>-99  AND tm>='" + stTime + "'  AND tm<'" + endTime
				+ "' ORDER BY tm";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sqlStr);
		for (Map<String, Object> map : list) {
			map.put("tm", DateUtil.date2Str((Date) map.get("tm"),
					"yyyy-MM-dd HH:mm:ss"));
		}
		return list;
	}

	/**
	 * 查询河道站点指定日期的水位情况
	 * 
	 * @param stcd
	 * @param dateStr
	 * @return List<Map<String,Object>>
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getRiverInfoByDay(String stcd,
			String dateStr) {
		String stTime = convertSTDate(dateStr) + " 08:00:00";
		Date stDate = DateUtil.str2Date(stTime, "yyyy-MM-dd HH:00:00");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(stDate);
		calendar.add(Calendar.DATE, 1);
		String endTime = DateUtil.date2Str(calendar.getTime(),
				"yyyy-MM-dd HH:00:00");
		String sqlStr = "SELECT z, tm FROM ST_RIVER_R  WHERE stcd='" + stcd
				+ "' AND z<>-99  AND tm>='" + stTime + "'  AND tm<'" + endTime
				+ "' ORDER BY tm";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sqlStr);
		for (Map<String, Object> map : list) {
			map.put("tm", DateUtil.date2Str((Date) map.get("tm"),
					"yyyy-MM-dd HH:mm:ss"));
		}
		return list;
	}

	
	public Map<String, Object> getRiverDetail(String stcd, String dateStr) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("riverInfo", getRiverInfo(stcd));
		rtMap.put("datas", getRiverInfoByDay(stcd, dateStr));
		return rtMap;
	}

	
	public Map<String, Object> getRiverDetail(String stcd, String stDate,
			String edDate) {
		if (null == edDate) {
			return getRiverDetail(stcd, stDate);
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("riverInfo", getRiverInfo(stcd));
		rtMap.put("datas", getRiverInfoByDay(stcd, stDate, edDate));
		return rtMap;
	}

	/**
	 * 河道信息
	 * 
	 * @param stcd
	 * @return Map<String,Object>
	 * @since v 1.0
	 */
	private Map<String, Object> getTideInfo(String stcd) {
		String sqlStr = "SELECT GUARD,STNM,CITY FROM HT_TIDE_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE RB.stcd='"
				+ stcd + "'";
		return daoHtnbsqdb.executeQueryObject(sqlStr);
	}

	/**
	 * 查询河道站点指定日期的水位情况
	 * 
	 * @param stcd
	 * @param dateStr
	 * @return List<Map<String,Object>>
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getTideInfoByDay(String stcd,
			String dateStr) {
		String stTime = convertSTDate(dateStr) + " 08:00:00";
		Date stDate = DateUtil.str2Date(stTime, "yyyy-MM-dd HH:00:00");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(stDate);
		calendar.add(Calendar.DATE, 1);
		String endTime = DateUtil.date2Str(calendar.getTime(),
				"yyyy-MM-dd HH:00:00");
		String sqlStr = "SELECT tdz, tm FROM ST_TIDE_R  WHERE stcd='" + stcd
				+ "' AND tdz<>-99  AND tm>='" + stTime + "'  AND tm<'"
				+ endTime + "' ORDER BY tm";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sqlStr);
		for (Map<String, Object> map : list) {
			map.put("tm", DateUtil.date2Str((Date) map.get("tm"),
					"yyyy-MM-dd HH:mm:ss"));
			String tdz = StringUtil.float2String(map.get("tdz").toString(), 2);
			map.put("tdz", Double.parseDouble(tdz));
		}
		return list;
	}

	/**
	 * 查询河道站点指定日期的水位情况
	 * 
	 * @param stcd
	 * @param dateSt
	 * @param dateEd
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> getTideInfoByDay(String stcd,
			String dateSt, String dateEd) {

		Date stDate = DateUtil.str2Date(dateSt + " 08:00:00",
				"yyyy-MM-dd HH:00:00");

		Calendar stCalendar = Calendar.getInstance();
		stCalendar.setTime(stDate);
		// stCalendar.add(Calendar.DATE, 1);
		Date edDate = DateUtil.str2Date(dateEd + " 08:00:00",
				"yyyy-MM-dd HH:00:00");
		Calendar edCalendar = Calendar.getInstance();
		edCalendar.setTime(edDate);
		edCalendar.add(Calendar.DATE, 1);

		String stTime = DateUtil.date2Str(stCalendar.getTime(),
				"yyyy-MM-dd HH:00:00");
		stCalendar.add(Calendar.DATE, +1);
		String endTime = DateUtil.date2Str(edCalendar.getTime(),
				"yyyy-MM-dd HH:00:00");

		String sqlStr = "SELECT tdz, tm FROM ST_TIDE_R  WHERE stcd='" + stcd
				+ "' AND tdz<>-99  AND tm>='" + stTime + "'  AND tm<'"
				+ endTime + "' ORDER BY tm";
		List<Map<String, Object>> list = daoHtnbsqdb.executeQuery(sqlStr);
		for (Map<String, Object> map : list) {
			map.put("tm", DateUtil.date2Str((Date) map.get("tm"),
					"yyyy-MM-dd HH:mm:ss"));
			String tdz = StringUtil.float2String(map.get("tdz").toString(), 2);
			map.put("tdz", Double.parseDouble(tdz));
		}
		return list;
	}

	
	public Map<String, Object> getTideDetail(String stcd, String dateStr) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("tideInfo", getTideInfo(stcd));
		rtMap.put("datas", getTideInfoByDay(stcd, dateStr));
		return rtMap;
	}

	
	public Map<String, Object> getTideDetail(String stcd, String stDate,
			String edDate) {
		if (null == edDate) {
			return getTideDetail(stcd, stDate);
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("tideInfo", getTideInfo(stcd));
		rtMap.put("datas", getTideInfoByDay(stcd, stDate, edDate));
		return rtMap;
	}

	
	public int getPathIsExit(String path) {
		String strSql = "select ID,State from [ce].[dbo].[数据接口表] where DatPath=?";
		Map<String, Object> map = daoHtnbsqdb.executeQueryObject(strSql,
				new Object[] { path });
		if (map != null) {
			// 存在
			if (map.get("State").toString().equals("2")) {
				// 已经成功创建了的
				return 2; // 成功创建
			} else {
				return 1; // 正在生成中
			}
		}
		return -1; // 不存在
	}

	
	public int getRainDatetoTxt(String pathHeader, String path,
			String tm_begin, String tm_end, String city, String clipLayer,
			String step, String Coordinate, String Stationtype) {
		String strSql;
		List<Map<String, Object>> lst;
		int res = 1;
		try {
			if (city.equals("all")) {
				strSql = "use ht_nbsqdb;select a.STNM,VALUE,CITY,Address,STX,STY from "
						+ "( "
						+ "select stnm as STNM,convert(decimal(12,1),sum(drp)) as VALUE, c.stcd as STCD ,city as CITY, b.ISSTATE from HT_RAIN_B "
						+ "a left join HT_STBPRP_B b on  a.ST_ID=b.ID left join ST_PPTN_R c on a.stcd=c.stcd  "
						+ "where enabled=1 and c.tm > ? and c.tm <=?  "
						+ "group by c.stcd,stnm,city,ordno,ISSTATE  "
						+ ") as a,t_STCoordinate as b "
						+ "where a.STNM=b.STNM and b.ISGJZ>=?"; // 国家站 3 正常的 2 0
																// 表示全部
				lst = daoHtnbsqdb.executeQuery(strSql, new Object[] { tm_begin,
						tm_end, Stationtype });
			} else {
				strSql = "use ht_nbsqdb;select a.STNM,VALUE,CITY,Address,STX,STY from "
						+ "( "
						+ "select stnm as STNM,convert(decimal(12,1),sum(drp)) as VALUE, c.stcd as STCD ,city as CITY, b.ISSTATE from HT_RAIN_B "
						+ "a left join HT_STBPRP_B b on  a.ST_ID=b.ID left join ST_PPTN_R c on a.stcd=c.stcd and city=? "
						+ "where enabled=1 and c.tm > ? and c.tm <=?  "
						+ "group by c.stcd,stnm,city,ordno,ISSTATE  "
						+ ") as a,t_STCoordinate as b "
						+ "where a.STNM=b.STNM and b.ISGJZ>=?";
				lst = daoHtnbsqdb.executeQuery(strSql, new Object[] { city,
						tm_begin, tm_end, Stationtype });
			}

			// 生成TXT 没事就找找那个大茅岙水库
			String strTxt = "";
			double maxValue = 0, dtemp = 0;
			if (lst != null) {
				for (Map<String, Object> map : lst) {
					if (map == null)
						continue;
					// 更新最大值
					dtemp = Double.parseDouble(map.get("VALUE").toString());
					if (dtemp > maxValue)
						maxValue = dtemp;

					if (map.get("STNM").toString().equals("大茅岙水库")) {
						if (map.get("Address").toString()
								.indexOf(map.get("CITY").toString()) >= 0) {
							if (strTxt.equals("")) {
								strTxt += map.get("STX").toString().trim()
										+ " "
										+ map.get("STY").toString().trim()
										+ " "
										+ map.get("VALUE").toString().trim();
							} else {
								strTxt += "\r\n"
										+ map.get("STX").toString().trim()
										+ " "
										+ map.get("STY").toString().trim()
										+ " "
										+ map.get("VALUE").toString().trim();
							}
						}
					} else {
						if (strTxt.equals("")) {
							strTxt += map.get("STX").toString().trim() + " "
									+ map.get("STY").toString().trim() + " "
									+ map.get("VALUE").toString().trim();
						} else {
							strTxt += "\r\n" + map.get("STX").toString().trim()
									+ " " + map.get("STY").toString().trim()
									+ " " + map.get("VALUE").toString().trim();
						}
					}
				}

				// 输出 //转成TXT
				RandomAccessFile mm = null;
				mm = new RandomAccessFile(pathHeader + path + ".dat", "rw");
				mm.writeBytes(strTxt);
				mm.close();

				if (maxValue < 0.00001) {
					// 这个是没有雨量
					res = 0;
				} else {
					// 插入一条数据
					strSql = "insert into [ce].[dbo].[数据接口表](CreateTM,DatPath,Coordinate,Layer,ContainStation,State,Step,DatFile,MaxRain) "
							+ "values(GETDATE(),?,?,?,1,0,?,?,?)";
					boolean rb = daoHtnbsqdb.executeSQL(strSql, new Object[] {
							path, Coordinate, clipLayer, step, pathHeader,
							maxValue });
					if (!rb) {
						res = -1;
					}
				}
			} else {
				// 没有数据 雨量为0
				res = 0;
			}

		} catch (Exception e) {
			// TODO: handle exception
			res = -1;
		}
		return res;
	}

	
	public List<String> getSJGLStations() {
		List<Map<String, Object>> list1 = daoHtnbsqdb
				.executeQuery("SELECT STCD FROM HT_TIDE_B2 where ENABLED =1");
		List<Map<String, Object>> list2 = daoHtnbsqdb
				.executeQuery("SELECT STCD FROM HT_RIVER_B2 where ENABLED =1");
		List<String> list = new ArrayList<String>();
		list.addAll(getStrings(list1));
		list.addAll(getStrings(list2));
		boolean has7290 = false;
		for (String stcd : list) {
			if (stcd.contains("7290")) {
				has7290 = true;
			}
		}
		if (!has7290) {
			list.add("7290");
		}
		return list;
	}

	private List<String> getStrings(List<Map<String, Object>> list) {
		List<String> rtList = new ArrayList<String>();
		for (Map<String, Object> map : list) {
			rtList.add(map.get("STCD").toString());
		}
		return rtList;
	}
	
	
	public List<Map<String, Object>> getDetailFlowInfo(String stcd,String stm,String etm){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(stcd==null)
			result = null;
		else{
			if(stm==null || etm==null){
				Calendar cal = Calendar.getInstance(Locale.CHINA);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				etm = df.format(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, -1);
				stm = df.format(cal.getTime());
			}
			result = daoHtnbsqdb.executeQuery("{call proczhsl_流量过程(?,?,?)}", new Object[]{stm,etm,stcd});
		}

		return result;
	}

	
	public List<Map<String, Object>> getISO24zhushi() {
		String sql="";
		String[] sqlArray={
				"SET NOCOUNT ON; declare @tb table(tm datetime)"
				," declare @i int"
				," set @i=0"
				," declare @now datetime"
				," set @now=convert(varchar(13),GETDATE(),120)+':00'"
				," while @i<24"
				," begin"
				," insert into @tb(tm) values(dateadd(HH,-@i,@now))"
				," set @i=@i+1"
				," end"
				," select a.*,b.hourname,path=(case when b.maxrain<5 or maxrain=5 then 'imgbg.png' else b.path+'.png' end),b.maxrain from @tb as a left join iso24zhushi as b on a.tm=b.createtm order by tm asc"
		};
		sql = StringUtils.join(sqlArray);
		
		return daoHtnbsqdb.executeQuery(sql);
	}

	
	public List<Map<String, Object>> getISO24leiji() {
		String sql="";
		String[] sqlArray={
				"SET NOCOUNT ON; declare @tb table(tm datetime,leijiname int)"
				," declare @i int"
				," set @i=0"
				," declare @now datetime"
				," set @now=convert(varchar(13),GETDATE(),120)+':00'"
				," while @i<24"
				," begin"
				," insert into @tb(tm,leijiname) values(@now,@i+1)"
				," set @i=@i+1"
				," end"
				," select a.*,path=(case when b.maxrain<5 or maxrain=5 then 'imgbg.png' else b.path+'.png' end),b.maxrain from @tb as a left join iso24leiji as b on a.tm=b.createtm and a.leijiname=b.leijiname"
		};
		sql = StringUtils.join(sqlArray);
		
		return daoHtnbsqdb.executeQuery(sql);
	}
}

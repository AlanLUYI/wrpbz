/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  2012-4-1 Neal Miao 
 * 
 * Copyright(c) 2012, by HTWater. All Rights Reserved.
 */
package net.htwater.hos.helper.entity;

import java.io.Serializable;

import cn.miao.framework.util.DateUtil;
import cn.miao.framework.util.StringUtil;

/**
 * 
 * 
 * @author Neal Miao
 * @version
 * @since v 1.0
 * @Date 2012-4-1 上午09:40:06
 * 
 * @see
 */
public class STRain implements Serializable {

	private static final long serialVersionUID = -8592265171363439130L;
	private String code;
	private String name;
	private String htmlName;
	private String todayRain;
	private String yestodayRain;
	private String byestodayRain;
	private String sum7;
	// private String totalRain;
	private String otherInfo; // 最大站点，小流域等等
	private String stcd;
	private String tm; // 入库时间
	private double latitude;
	private double longitude;
	private String district;

	private String ennmcd;	
	private Boolean ISSTATE;//是否是国家站@20150729

	private String river; // 流域信息 @20150625 

	
	private int order;
	
	public String getRiver() {
		return river;
	}

	public void setRiver(String river) {
		this.river = river;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getHtmlName() {
		return htmlName;
	}

	public void setHtmlName(String htmlName) {
		this.htmlName = htmlName;
	}

	public String getTm() {
		if (null != tm) {
			String pattern0 = "yyyy-MM-dd hh:mm:ss";
			String pattern1 = "MM-dd HH:mm";
			return DateUtil.date2Str(DateUtil.str2Date(tm, pattern0), pattern1);
		} else {
			return "";
		}
	}

	public void setTm(String tm) {
		this.tm = tm;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTodayRain() {
		return StringUtil.float2String(todayRain, 1);
	}

	public void setTodayRain(String todayRain) {
		this.todayRain = todayRain;
	}

	public String getYestodayRain() {
		return StringUtil.float2String(yestodayRain, 1);
	}

	public void setYestodayRain(String yestodayRain) {
		this.yestodayRain = yestodayRain;
	}

	public String getByestodayRain() {
		return StringUtil.float2String(byestodayRain, 1);
	}

	public void setByestodayRain(String byestodayRain) {
		this.byestodayRain = byestodayRain;
	}
	
	public String getSum7() {
		if (null == sum7) {
			return "0";
		} else {
			return StringUtil.float2String(sum7, 1);
		}
	}

	public void setSum7(String sum7) {
		this.sum7 = sum7;
	}

	public String getTotalRain() {
		Double value = Double.parseDouble(todayRain)
				+ Double.parseDouble(yestodayRain)
				+ Double.parseDouble(byestodayRain);
		return StringUtil.float2String("" + value, 1);
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getStcd() {
		return stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	public String getennmcd() {
		return ennmcd;
	}

	public void setennmcd(String ennmcd) {
		this.ennmcd = ennmcd;
	}
	public void setISSTATE(Boolean isstate){
		this.ISSTATE = isstate;
	}
	public Boolean getISSTATE(){
		return ISSTATE;
	}
}

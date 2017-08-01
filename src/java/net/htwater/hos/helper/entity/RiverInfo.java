/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  2012-4-6 Neal Miao 
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
 * @Date 2012-4-6 下午04:36:27
 * 
 * @see
 */
public class RiverInfo implements Serializable {
	
	private static final long serialVersionUID = 4965227755408895345L;
	private String stcd;
	private String stnm;
	private String district;
	private String guard;
	private String ensure;
	private String sw85;
	private String swws;
	private String tm;
	private String htmlName;
	private double latitude;
	private double longitude;
	private boolean isrcontrol;
	private String type;

	private String ennmcd;

	private String river; // 流域信息  @20150625
	
	public boolean isIsrcontrol() {
		return isrcontrol;
	}

	public void setIsrcontrol(boolean isrcontrol) {
		this.isrcontrol = isrcontrol;
	}

	public String getRiver() {
		return river;
	}

	public void setRiver(String river) {
		this.river = river;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean getIsRControl() {
		return isrcontrol;
	}

	public void setIsRControl(boolean isrcontrol) {
		this.isrcontrol = isrcontrol;
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
		if (tm.length() > 5) {
			String pattern0 = "yyyy-MM-dd hh:mm:ss";
			String pattern1 = "MM-dd HH:mm";
			return DateUtil.date2Str(DateUtil.str2Date(tm, pattern0), pattern1);
		} else {
			return tm;
		}
	}
	public void setTm(String tm) {
		this.tm = tm;
	}
	public String getSw85() {
		return StringUtil.float2String(sw85, 2);
	}
	public void setSw85(String sw85) {
		this.sw85 = sw85;
	}
	public String getSwws() {
		return StringUtil.float2String(swws, 2);
	}
	public void setSwws(String swws) {
		this.swws = swws;
	}
	private String trend; // 趋势
	public String getStcd() {
		return stcd;
	}
	public void setStcd(String stcd) {
		this.stcd = stcd;
	}
	public String getStnm() {
		if (sw85.length() > 0 && guard.length()>0 
				&& Double.parseDouble(sw85) >= Double.parseDouble(guard)) {
			return "<span style='color:red'>" + stnm + "</span>";
		} else {
			return stnm;
		}
	}
	public void setStnm(String stnm) {
		this.stnm = stnm;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getGuard() {
		return guard;
	}
	public void setGuard(String guard) {
		this.guard = guard;
	}
	public String getEnsure() {
		return ensure;
	}
	public void setEnsure(String ensure) {
		this.ensure = ensure;
	}
	public String getTrend() {
		return trend;
	}
	public void setTrend(String trend) {
		this.trend = trend;
	}

	public String getennmcd() {
		return ennmcd;
	}

	public void setennmcd(String ennmcd) {
		this.ennmcd = ennmcd;
	}
}

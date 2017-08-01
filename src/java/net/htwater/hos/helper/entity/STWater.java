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
 * 实时水位
 * 
 * @author Neal Miao
 * @version
 * @since v 1.0
 * @Date 2012-4-6 上午10:11:56
 * 
 * @see
 */
public class STWater implements Serializable {

	private static final long serialVersionUID = 7737631895589718587L;
	private String stcd;
	private String code;
	private String stnm;
	private String district;
	private String m8sw;
	private String m8kr;
	private String stsw;
	private String stkr;
	private String kzsw;
	private String kzkr;
	private String ratio;
	private String tm;
	private String htmlName;
	private double latitude;
	private double longitude;
	private String type;
	private String river; // 流域信息 @20150625
	
	private String ennmcd;
	private String nor_level;
	private String floodhigh_level;
	private String rsv_type;
	
	public String getennmcd() {
		return ennmcd;
	}

	public void setennmcd(String ennmcd) {
		this.ennmcd = ennmcd;
	}	
	
	public String getnor_level() {
		return nor_level;
	}

	public void setnor_level(String nor_level) {
		this.nor_level = nor_level;
	}
	
	public String getfloodhigh_level() {
		return floodhigh_level;
	}

	public void setfloodhigh_level(String floodhigh_level) {
		this.floodhigh_level = floodhigh_level;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	public String getStcd() {
		return stcd;
	}
	public void setStcd(String stcd) {
		this.stcd = stcd;
	}
	public String getStnm() {
		double r = Double.parseDouble(ratio);
		if (r >= 1) {
			return "<span style='color:red'>" + stnm + "</span>";
		} else {
			return stnm;
		}
	}
	public void setStnm(String stnm) {
		this.stnm = stnm;
	}
	public String getM8sw() {
		return m8sw;
	}
	public void setM8sw(String m8sw) {
		this.m8sw = m8sw;
	}
	public String getM8kr() {
		if (null == m8kr) {
			return "0";
		} else {
			return StringUtil.float2String(m8kr, 0);
		}
	}
	public void setM8kr(String m8kr) {
		this.m8kr = m8kr;
	}
	public String getStsw() {
		return  StringUtil.float2String(stsw, 2);
	}
	public void setStsw(String stsw) {
		this.stsw = stsw;
	}
	public String getStkr() {
		if (null == stkr) {
			return "0";
		} else {
			return StringUtil.float2String(stkr, 0);
		}
	}
	public void setStkr(String stkr) {
		this.stkr = stkr;
	}
	public String getKzsw() {
		try{
			return StringUtil.float2String(kzsw, 2);
		}catch(Exception e){
			return "9999";
		}
	}
	public void setKzsw(String kzsw) {
		this.kzsw = kzsw;
	}
	public String getKzkr() {
		return StringUtil.float2String(kzkr, 0);
	}
	public void setKzkr(String kzkr) {
		this.kzkr = kzkr;
	}
	public String getRatio() {
		double r = Double.parseDouble(ratio);
		r *= 100;
		String rarioPer = StringUtil.float2String(""+r, 1) + "%";
		if (r > 100) {
			return "<span style='color:red'>" + rarioPer + "</span>";
		} else {
			return rarioPer;
		}
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

	public String getrsv_type() {
		return rsv_type;
	}

	public void setrsv_type(String rsv_type) {
		this.rsv_type = rsv_type;
	}
}

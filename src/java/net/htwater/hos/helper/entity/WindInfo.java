/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  2012-4-9 Neal Miao 
 * 
 * Copyright(c) 2012, by HTWater. All Rights Reserved.
 */
package net.htwater.hos.helper.entity;

import java.io.Serializable;

import cn.miao.framework.util.DateUtil;

/**
 * 
 * 
 * @author Neal Miao
 * @version
 * @since v 1.0
 * @Date 2012-4-9 下午03:00:34
 * 
 * @see
 */
public class WindInfo implements Serializable {

	private static final long serialVersionUID = 3618895372784412756L;
	private String wndName;
	private String wndPwr;  // 风力
	private String wndSpeed;    // 风速
	private String wndDirection;  // 风向
	private String maxValue; 
	private String tm;
	
	public String getTm() {
		String pattern0 = "yyyy-MM-dd hh:mm:ss";
		String pattern1 = "MM-dd HH:mm";
		return DateUtil.date2Str(DateUtil.str2Date(tm, pattern0), pattern1);
	}
	public void setTm(String tm) {
		this.tm = tm;
	}
	public String getWndName() {
		return wndName;
	}
	public void setWndName(String wndName) {
		this.wndName = wndName;
	}
	public String getWndPwr() {
		return wndPwr;
	}
	public void setWndPwr(String wndPwr) {
		this.wndPwr = wndPwr;
	}
	public String getWndSpeed() {
		return wndSpeed;
	}
	public void setWndSpeed(String wndSpeed) {
		this.wndSpeed = wndSpeed;
	}
	public String getWndDirection() {
		return wndDirection;
	}
	public void setWndDirection(String wndDirection) {
		this.wndDirection = wndDirection;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
}

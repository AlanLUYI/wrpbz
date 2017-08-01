/*
 * version date author 
 * ────────────────────────────────── 
 * 1.0  2012-4-9 Neal Miao 
 * 
 * Copyright(c) 2012, by HTWater. All Rights Reserved.
 */
package net.htwater.hos.helper.entity;

import java.io.Serializable;

/**
 * 
 * 
 * @author Neal Miao
 * @version
 * @since v 1.0
 * @Date 2012-4-9 上午10:09:42
 * 
 * @see
 */
public class TideInfo extends RiverInfo implements Serializable {

	private static final long serialVersionUID = -5716648641086645742L;
	private String polName;
	private String polTop;
	
	public String getPolName() {
		return polName;
	}
	public void setPolName(String polName) {
		this.polName = polName;
	}
	public String getPolTop() {
		return 0 == polTop.length() ? "-" : polTop;
	}
	public void setPolTop(String polTop) {
		this.polTop = polTop;
	}
}

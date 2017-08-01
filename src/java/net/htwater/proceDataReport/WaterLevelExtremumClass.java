/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月4日
 * @updateTime 2015年2月4日
 * @描述:水位测站极值表
 */
public class WaterLevelExtremumClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3877884900134090814L;
	
	private String stcd;
	private String ihz;
	private String ihztime;
	private String thz;
	private String thztime;
	
	//
	public String getthztime() {
		return thztime;
	}
	public void setthztime(String _thztime) {
		this.thztime=_thztime;
	}
	//
	public String getthz() {
		return thz;
	}
	public void setthz(String _thz) {
		this.thz=_thz;
	}
	//
	public String getihztime() {
		return ihztime;
	}
	public void setihztime(String _ihztime) {
		this.ihztime=_ihztime;
	}
	//
	public String getihz() {
		return ihz;
	}
	public void setihz(String _ihz) {
		this.ihz=_ihz;
	}
	//
	public String getstcd() {
		return stcd;
	}
	public void setstcd(String _stcd) {
		this.stcd=_stcd;
	}
}

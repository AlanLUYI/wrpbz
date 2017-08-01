/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月4日
 * @updateTime 2015年2月4日
 * @描述:雨量测站极值表
 */
public class RainStaExtremumClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1849449980363505835L;
	
	private String stcd;
	private String maxDrp1h;
	private String maxDrp3h;
	private String maxDrp6h;
	private String maxDrp12h;
	private String maxDrp24h;
	private String tm1h;
	private String tm3h;
	private String tm6h;
	private String tm12h;
	private String tm24h;
	
	//
	public String gettm24h() {
		return tm24h;
	}
	public void settm24h(String _tm24h) {
		this.tm24h=_tm24h;
	}
	//
	public String gettm12h() {
		return tm12h;
	}
	public void settm12h(String _tm12h) {
		this.tm12h=_tm12h;
	}
	//
	public String gettm6h() {
		return tm6h;
	}
	public void settm6h(String _tm6h) {
		this.tm6h=_tm6h;
	}
	//
	public String gettm3h() {
		return tm3h;
	}
	public void settm3h(String _tm3h) {
		this.tm3h=_tm3h;
	}
	//
	public String gettm1h() {
		return tm1h;
	}
	public void settm1h(String _tm1h) {
		this.tm1h=_tm1h;
	}
	//
	public String getmaxDrp24h() {
		return maxDrp24h;
	}
	public void setmaxDrp24h(String _maxDrp24h) {
		this.maxDrp24h=_maxDrp24h;
	}
	//
	public String getmaxDrp12h() {
		return maxDrp12h;
	}
	public void setmaxDrp12h(String _maxDrp12h) {
		this.maxDrp12h=_maxDrp12h;
	}
	//
	public String getmaxDrp6h() {
		return maxDrp6h;
	}
	public void setmaxDrp6h(String _maxDrp6h) {
		this.maxDrp6h=_maxDrp6h;
	}
	//
	public String getmaxDrp3h() {
		return maxDrp3h;
	}
	public void setmaxDrp3h(String _maxDrp3h) {
		this.maxDrp3h=_maxDrp3h;
	}
	//
	public String getmaxDrp1h() {
		return maxDrp1h;
	}
	public void setmaxDrp1h(String _maxDrp1h) {
		this.maxDrp1h=_maxDrp1h;
	}
	//
	public String getstcd() {
		return stcd;
	}
	public void setstcd(String _stcd) {
		this.stcd=_stcd;
	}
}

/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月4日
 * @updateTime 2015年2月4日
 * @描述:历史山洪灾害情况表
 */
public class HistoryDisasterClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4717842546589329447L;
	
	private String adcd;
	private String hzrdtm;
	private String hzrddesc;
	
	//
	public String gethzrddesc() {
		return hzrddesc;
	}
	public void sethzrddesc(String _hzrddesc) {
		this.hzrddesc=_hzrddesc;
	}
	//
	public String gethzrdtm() {
		return hzrdtm;
	}
	public void sethzrdtm(String _hzrdtm) {
		this.hzrdtm=_hzrdtm;
	}
	//
	public String getadcd() {
		return adcd;
	}
	public void setadcd(String _adcd) {
		this.adcd=_adcd;
	}
}

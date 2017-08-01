/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月4日
 * @updateTime 2015年2月4日
 * @描述:山洪灾害预警指标
 */
public class DisasterWarnIndicaClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1683292629785948147L;
	
	private String struleid;
	private String stcd;
	private String stindex;
	private String warngradeid;
	private String warntypeid;
	private String stthreshold;
	private String stindexunit;
	private String stdt;
	private String strulevalidtime;
	private String stindexnm;
	
	//
	public String getstindexnm() {
		return stindexnm;
	}
	public void setstindexnm(String _stindexnm) {
		this.stindexnm=_stindexnm;
	}
	//
	public String getstrulevalidtime() {
		return strulevalidtime;
	}
	public void setstrulevalidtime(String _strulevalidtime) {
		this.strulevalidtime=_strulevalidtime;
	}
	//
	public String getstdt() {
		return stdt;
	}
	public void setstdt(String _stdt) {
		this.stdt=_stdt;
	}
	//
	public String getstindexunit() {
		return stindexunit;
	}
	public void setstindexunit(String _stindexunit) {
		this.stindexunit=_stindexunit;
	}
	//
	public String getstthreshold() {
		return stthreshold;
	}
	public void setstthreshold(String _stthreshold) {
		this.stthreshold=_stthreshold;
	}
	//
	public String getwarntypeid() {
		return warntypeid;
	}
	public void setwarntypeid(String _warntypeid) {
		this.warntypeid=_warntypeid;
	}
	//
	public String getwarngradeid() {
		return warngradeid;
	}
	public void setwarngradeid(String _warngradeid) {
		this.warngradeid=_warngradeid;
	}
	//
	public String getstindex() {
		return stindex;
	}
	public void setstindex(String _stindex) {
		this.stindex=_stindex;
	}
	//
	public String getstcd() {
		return stcd;
	}
	public void setstcd(String _stcd) {
		this.stcd=_stcd;
	}
	//
	public String getstruleid() {
		return struleid;
	}
	public void setstruleid(String _struleid) {
		this.struleid=_struleid;
	}
}

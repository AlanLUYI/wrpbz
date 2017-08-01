/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月4日
 * @updateTime 2015年2月4日
 * @描述:测站基本信息表
 */
public class StationBaseInfoClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1237127352926596341L;
	
	private String stcd;
	private String stnm;
	private String rvnm;
	private String hnnm;
	private String bsnm;
	private String lgtd;
	private String lttd;
	private String stlc;
	private String sttp;
	private String phcd;
	private String cacd;
	private String adcd;
	
	//
	public String getadcd() {
		return adcd;
	}
	public void setadcd(String _adcd) {
		this.adcd=_adcd;
	}
	//
	public String getcacd() {
		return cacd;
	}
	public void setcacd(String _cacd) {
		this.cacd=_cacd;
	}
	//
	public String getphcd() {
		return phcd;
	}
	public void setphcd(String _phcd) {
		this.phcd=_phcd;
	}
	//
	public String getsttp() {
		return sttp;
	}
	public void setsttp(String _sttp) {
		this.sttp=_sttp;
	}
	//
	public String getstlc() {
		return stlc;
	}
	public void setstlc(String _stlc) {
		this.stlc=_stlc;
	}
	//
	public String getlttd() {
		return lttd;
	}
	public void setlttd(String _lttd) {
		this.lttd=_lttd;
	}
	//
	public String getlgtd() {
		return lgtd;
	}
	public void setlgtd(String _lgtd) {
		this.lgtd=_lgtd;
	}
	//
	public String getbsnm() {
		return bsnm;
	}
	public void setbsnm(String _bsnm) {
		this.bsnm=_bsnm;
	}
	//
	public String gethnnm() {
		return hnnm;
	}
	public void sethnnm(String _hnnm) {
		this.hnnm=_hnnm;
	}
	//
	public String getrvnm() {
		return rvnm;
	}
	public void setrvnm(String _rvnm) {
		this.rvnm=_rvnm;
	}
	//
	public String getstnm() {
		return stnm;
	}
	public void setstnm(String _stnm) {
		this.stnm=_stnm;
	}
	//
	public String getstcd() {
		return stcd;
	}
	public void setstcd(String _stcd) {
		this.stcd=_stcd;
	}
}

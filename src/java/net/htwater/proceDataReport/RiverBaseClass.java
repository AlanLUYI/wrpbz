/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月5日
 * @updateTime 2015年2月5日
 * @描述:河流基本情况表
 */
public class RiverBaseClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1050174880063267360L;
	
	private String rvcd;
	private String rvnm;
	private String rvpl;
	private String mnstln;
	private String ttdrbsar;
	private String drbspp;
	private String mxsfds;
	private String avtm;
	
	//
	public String getavtm() {
		return avtm;
	}
	public void setavtm(String _avtm) {
		this.avtm=_avtm;
	}
	//
	public String getmxsfds() {
		return mxsfds;
	}
	public void setmxsfds(String _mxsfds) {
		this.mxsfds=_mxsfds;
	}
	//
	public String getdrbspp() {
		return drbspp;
	}
	public void setdrbspp(String _drbspp) {
		this.drbspp=_drbspp;
	}
	//
	public String getttdrbsar() {
		return ttdrbsar;
	}
	public void setttdrbsar(String _ttdrbsar) {
		this.ttdrbsar=_ttdrbsar;
	}
	//
	public String getmnstln() {
		return mnstln;
	}
	public void setmnstln(String _mnstln) {
		this.mnstln=_mnstln;
	}
	//
	public String getrvpl() {
		return rvpl;
	}
	public void setrvpl(String _rvpl) {
		this.rvpl=_rvpl;
	}
	//
	public String getrvnm() {
		return rvnm;
	}
	public void setrvnm(String _rvnm) {
		this.rvnm=_rvnm;
	}
	//
	public String getrvcd() {
		return rvcd;
	}
	public void setrvcd(String _rvcd) {
		this.rvcd=_rvcd;
	}
}

/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月5日
 * @updateTime 2015年2月5日
 * @描述:堤防基本情况表
 */
public class DikeBaseClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3111818980485728736L;
	
	private String dkcd;
	private String dknm;
	private String bnscbgpl;
	private String bnscenpl;
	private String bnsctp;
	private String bnscln;
	private String bntpel;
	private String crdkhg;
	private String dsfllv;
	private String gnwtlv;
	private String alwtlv;
	private String dsfl;
	private String alfl;
	private String prinar;
	private String ptpp;
	
	//
	public String getptpp() {
		return ptpp;
	}
	public void setptpp(String _ptpp) {
		this.ptpp=_ptpp;
	}
	//
	public String getprinar() {
		return prinar;
	}
	public void setprinar(String _prinar) {
		this.prinar=_prinar;
	}
	//
	public String getalfl() {
		return alfl;
	}
	public void setalfl(String _alfl) {
		this.alfl=_alfl;
	}
	//
	public String getdsfl() {
		return dsfl;
	}
	public void setdsfl(String _dsfl) {
		this.dsfl=_dsfl;
	}
	//
	public String getalwtlv() {
		return alwtlv;
	}
	public void setalwtlv(String _alwtlv) {
		this.alwtlv=_alwtlv;
	}
	//
	public String getgnwtlv() {
		return gnwtlv;
	}
	public void setgnwtlv(String _gnwtlv) {
		this.gnwtlv=_gnwtlv;
	}
	//
	public String getdsfllv() {
		return dsfllv;
	}
	public void setdsfllv(String _dsfllv) {
		this.dsfllv=_dsfllv;
	}
	//
	public String getcrdkhg() {
		return crdkhg;
	}
	public void setcrdkhg(String _crdkhg) {
		this.crdkhg=_crdkhg;
	}
	//
	public String getbntpel() {
		return bntpel;
	}
	public void setbntpel(String _bntpel) {
		this.bntpel=_bntpel;
	}
	//
	public String getbnscln() {
		return bnscln;
	}
	public void setbnscln(String _bnscln) {
		this.bnscln=_bnscln;
	}
	//
	public String getbnsctp() {
		return bnsctp;
	}
	public void setbnsctp(String _bnsctp) {
		this.bnsctp=_bnsctp;
	}
	//
	public String getbnscenpl() {
		return bnscenpl;
	}
	public void setbnscenpl(String _bnscenpl) {
		this.bnscenpl=_bnscenpl;
	}
	//
	public String getbnscbgpl() {
		return bnscbgpl;
	}
	public void setbnscbgpl(String _bnscbgpl) {
		this.bnscbgpl=_bnscbgpl;
	}
	//
	public String getdknm() {
		return dknm;
	}
	public void setdknm(String _dknm) {
		this.dknm=_dknm;
	}
	//
	public String getdkcd() {
		return dkcd;
	}
	public void setdkcd(String _dkcd) {
		this.dkcd=_dkcd;
	}
}

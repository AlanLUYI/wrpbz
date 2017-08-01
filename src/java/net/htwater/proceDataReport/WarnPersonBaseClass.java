/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月5日
 * @updateTime 2015年2月5日
 * @描述:预警人员基本情况表
 */
public class WarnPersonBaseClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3370617489942476855L;
	
	private String personCD;
	private String adcd;
	private String name;
	private String sex;
	private String company;
	private String position;
	private String mobile;
	private String officeTel;
	
	//
	public String getofficeTel() {
		return officeTel;
	}
	public void setofficeTel(String _officeTel) {
		this.officeTel=_officeTel;
	}
	//
	public String getmobile() {
		return mobile;
	}
	public void setmobile(String _mobile) {
		this.mobile=_mobile;
	}
	//
	public String getposition() {
		return position;
	}
	public void setposition(String _position) {
		this.position=_position;
	}
	//
	public String getcompany() {
		return company;
	}
	public void setcompany(String _company) {
		this.company=_company;
	}
	//
	public String getsex() {
		return sex;
	}
	public void setsex(String _sex) {
		this.sex=_sex;
	}
	//
	public String getname() {
		return name;
	}
	public void setname(String _name) {
		this.name=_name;
	}
	//
	public String getadcd() {
		return adcd;
	}
	public void setadcd(String _adcd) {
		this.adcd=_adcd;
	}
	//
	public String getpersonCD() {
		return personCD;
	}
	public void setpersonCD(String _personCD) {
		this.personCD=_personCD;
	}
	
}

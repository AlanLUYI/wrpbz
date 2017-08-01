/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月6日
 * @updateTime 2015年2月6日
 * @描述:各类责任人名单
 */
public class DutyPersonClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2894252784796202841L;
	
	private String name;
	private String unit;
	private String position;
	private String officeTell;
	private String country;
	private String mobile;
	private String type;
	private String restype;
	private String remark;
	
	//
	public String getremark() {
		return remark;
	}
	public void setremark(String _remark) {
		this.remark=_remark;
	}
	//
	public String gettype() {
		return type;
	}
	public void settype(String _type) {
		this.type=_type;
	}
	//
	public String getmobile() {
		return mobile;
	}
	public void setmobile(String _mobile) {
		this.mobile=_mobile;
	}
	//
	public String getofficeTell() {
		return officeTell;
	}
	public void setofficeTell(String _officeTell) {
		this.officeTell=_officeTell;
	}
	//
	public String getposition() {
		return position;
	}
	public void setposition(String _position) {
		this.position=_position;
	}
	//
	public String getunit() {
		return unit;
	}
	public void setunit(String _unit) {
		this.unit=_unit;
	}
	//
	public String getname() {
		return name;
	}
	public void setname(String _name) {
		this.name=_name;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the resType
	 */
	public String getrestype() {
		return restype;
	}
	/**
	 * @param resType the resType to set
	 */
	public void setrestype(String restype) {
		this.restype = restype;
	}
}

/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月5日
 * @updateTime 2015年2月5日
 * @描述:预警部门情况
 */
public class WarnDepartmentClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2841837615776497409L;
	
	private String adcd;
	private String warnGradeID;
	private String warnTypeID;
	private String warnStatusID;
	private String deptCD;
	private String warnRange;
	
	//
	public String getwarnRange() {
		return warnRange;
	}
	public void setwarnRange(String _warnRange) {
		this.warnRange=_warnRange;
	}
	//
	public String getdeptCD() {
		return deptCD;
	}
	public void setdeptCD(String _deptCD) {
		this.deptCD=_deptCD;
	}
	//
	public String getwarnStatusID() {
		return warnStatusID;
	}
	public void setwarnStatusID(String _warnStatusID) {
		this.warnStatusID=_warnStatusID;
	}
	//
	public String getwarnTypeID() {
		return warnTypeID;
	}
	public void setwarnTypeID(String _warnTypeID) {
		this.warnTypeID=_warnTypeID;
	}
	//
	public String getwarnGradeID() {
		return warnGradeID;
	}
	public void setwarnGradeID(String _warnGradeID) {
		this.warnGradeID=_warnGradeID;
	}
	//
	public String getadcd() {
		return adcd;
	}
	public void setadcd(String _adcd) {
		this.adcd=_adcd;
	}
}

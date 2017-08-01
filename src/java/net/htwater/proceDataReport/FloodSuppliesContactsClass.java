/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月9日
 * @updateTime 2015年4月3日
 * @描述:防汛抗旱物资分管负责人及物资联系人通讯录
 */
public class FloodSuppliesContactsClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3950840748753225548L;
	
	private String counties;
	private String areaCode;
	private String dutyPhone;
	private String fax;
	private String perInCharge;
	private String fzrofficePhone;
	private String fzrmobilePhone;
	private String attn;
	private String jbrofficePhone;
	private String jbrmobilePhone;
	/**
	 * @return the counties
	 */
	public String getCounties() {
		return counties;
	}
	/**
	 * @param counties the counties to set
	 */
	public void setCounties(String counties) {
		this.counties = counties;
	}
	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return the dutyPhone
	 */
	public String getDutyPhone() {
		return dutyPhone;
	}
	/**
	 * @param dutyPhone the dutyPhone to set
	 */
	public void setDutyPhone(String dutyPhone) {
		this.dutyPhone = dutyPhone;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return the perInCharge
	 */
	public String getPerInCharge() {
		return perInCharge;
	}
	/**
	 * @param perInCharge the perInCharge to set
	 */
	public void setPerInCharge(String perInCharge) {
		this.perInCharge = perInCharge;
	}
	/**
	 * @return the attn
	 */
	public String getAttn() {
		return attn;
	}
	/**
	 * @param attn the attn to set
	 */
	public void setAttn(String attn) {
		this.attn = attn;
	}
	/**
	 * @return the fzrofficePhone
	 */
	public String getFzrofficePhone() {
		return fzrofficePhone;
	}
	/**
	 * @param fzrofficePhone the fzrofficePhone to set
	 */
	public void setFzrofficePhone(String fzrofficePhone) {
		this.fzrofficePhone = fzrofficePhone;
	}
	/**
	 * @return the fzrmobilePhone
	 */
	public String getFzrmobilePhone() {
		return fzrmobilePhone;
	}
	/**
	 * @param fzrmobilePhone the fzrmobilePhone to set
	 */
	public void setFzrmobilePhone(String fzrmobilePhone) {
		this.fzrmobilePhone = fzrmobilePhone;
	}
	/**
	 * @return the jbrofficePhone
	 */
	public String getJbrofficePhone() {
		return jbrofficePhone;
	}
	/**
	 * @param jbrofficePhone the jbrofficePhone to set
	 */
	public void setJbrofficePhone(String jbrofficePhone) {
		this.jbrofficePhone = jbrofficePhone;
	}
	/**
	 * @return the jbrmobilePhone
	 */
	public String getJbrmobilePhone() {
		return jbrmobilePhone;
	}
	/**
	 * @param jbrmobilePhone the jbrmobilePhone to set
	 */
	public void setJbrmobilePhone(String jbrmobilePhone) {
		this.jbrmobilePhone = jbrmobilePhone;
	}
	
}

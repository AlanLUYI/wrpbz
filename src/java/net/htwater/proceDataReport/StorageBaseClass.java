/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月9日
 * @updateTime 2015年2月9日
 * @描述:防汛抗旱物资储备仓库基本情况调查表
 */
public class StorageBaseClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8618806932591729989L;
	
	private String unit;
	private String reserveName;
	private String detailAddress;
	private String storageArea;
	private String buildYear;
	private String storageConditions;
	private String storageStruct;
	private String trafficConditions;
	private String lotd;
	private String latd;
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the reserveName
	 */
	public String getReserveName() {
		return reserveName;
	}
	/**
	 * @param reserveName the reserveName to set
	 */
	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}
	/**
	 * @return the detailAddress
	 */
	public String getDetailAddress() {
		return detailAddress;
	}
	/**
	 * @param detailAddress the detailAddress to set
	 */
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	/**
	 * @return the storageStruct
	 */
	public String getStorageStruct() {
		return storageStruct;
	}
	/**
	 * @param storageStruct the storageStruct to set
	 */
	public void setStorageStruct(String storageStruct) {
		this.storageStruct = storageStruct;
	}
	/**
	 * @return the storageArea
	 */
	public String getStorageArea() {
		return storageArea;
	}
	/**
	 * @param storageArea the storageArea to set
	 */
	public void setStorageArea(String storageArea) {
		this.storageArea = storageArea;
	}
	/**
	 * @return the storageConditions
	 */
	public String getStorageConditions() {
		return storageConditions;
	}
	/**
	 * @param storageConditions the storageConditions to set
	 */
	public void setStorageConditions(String storageConditions) {
		this.storageConditions = storageConditions;
	}
	/**
	 * @return the buildYear
	 */
	public String getBuildYear() {
		return buildYear;
	}
	/**
	 * @param buildYear the buildYear to set
	 */
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}
	/**
	 * @return the trafficConditions
	 */
	public String getTrafficConditions() {
		return trafficConditions;
	}
	/**
	 * @param trafficConditions the trafficConditions to set
	 */
	public void setTrafficConditions(String trafficConditions) {
		this.trafficConditions = trafficConditions;
	}
	/**
	 * @return the lotd
	 */
	public String getLotd() {
		return lotd;
	}
	/**
	 * @param lotd the lotd to set
	 */
	public void setLotd(String lotd) {
		this.lotd = lotd;
	}
	/**
	 * @return the latd
	 */
	public String getLatd() {
		return latd;
	}
	/**
	 * @param latd the latd to set
	 */
	public void setLatd(String latd) {
		this.latd = latd;
	}

}

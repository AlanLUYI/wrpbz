/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月5日
 * @updateTime 2015年4月2日
 * @描述:预警设施
 */
public class WarnDevicesClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6451947761067450672L;
	
	private String deviceName;
	private String deviceType;
	private String nativeCountry;
	private String lon;
	private String lat;
	private String adminName;
	private String phone;
	private String mobile;

	/**
	 * @return the deviceName
	 */
	public String getdeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setdeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the nativeCountry
	 */
	public String getNativeCountry() {
		return nativeCountry;
	}

	/**
	 * @param nativeCountry the nativeCountry to set
	 */
	public void setNativeCountry(String nativeCountry) {
		this.nativeCountry = nativeCountry;
	}

	/**
	 * @return the lon
	 */
	public String getLon() {
		return lon;
	}

	/**
	 * @param lon the lon to set
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}

	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return the adminName
	 */
	public String getAdminName() {
		return adminName;
	}

	/**
	 * @param adminName the adminName to set
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}

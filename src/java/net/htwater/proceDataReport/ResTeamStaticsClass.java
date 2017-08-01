/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月9日
 * @updateTime 2015年2月9日
 * @描述:抢险队基本情况统计表
 */
public class ResTeamStaticsClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6183526191654829643L;
	
	private String resTeamType;
	private String resTeamName;
	private String resTeamPlace;
	private String professionalRes;
	private String unitNature;
	private String numberStaff;
	private String nowPerson;
	private String machine;
	private String earthMachine;
	private String liftingDevice;
	private String transportCar;
	private String divingDevice;
	private String drainDevice;
	private String pump;
	private String assault;
	private String powerBoat;
	private String boat;
	private String powerDevice;
	private String totalAsset;
	private String deviceWorth;
	private String contact;
	private String contactWay;
	private String lotd;
	private String latd;
	/**
	 * @return the resTeamName
	 */
	public String getResTeamName() {
		return resTeamName;
	}
	/**
	 * @param resTeamName the resTeamName to set
	 */
	public void setResTeamName(String resTeamName) {
		this.resTeamName = resTeamName;
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
	 * @return the contactWay
	 */
	public String getContactWay() {
		return contactWay;
	}
	/**
	 * @param contactWay the contactWay to set
	 */
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the deviceWorth
	 */
	public String getDeviceWorth() {
		return deviceWorth;
	}
	/**
	 * @param deviceWorth the deviceWorth to set
	 */
	public void setDeviceWorth(String deviceWorth) {
		this.deviceWorth = deviceWorth;
	}
	/**
	 * @return the totalAsset
	 */
	public String getTotalAsset() {
		return totalAsset;
	}
	/**
	 * @param totalAsset the totalAsset to set
	 */
	public void setTotalAsset(String totalAsset) {
		this.totalAsset = totalAsset;
	}
	/**
	 * @return the powerDevice
	 */
	public String getPowerDevice() {
		return powerDevice;
	}
	/**
	 * @param powerDevice the powerDevice to set
	 */
	public void setPowerDevice(String powerDevice) {
		this.powerDevice = powerDevice;
	}
	/**
	 * @return the boat
	 */
	public String getBoat() {
		return boat;
	}
	/**
	 * @param boat the boat to set
	 */
	public void setBoat(String boat) {
		this.boat = boat;
	}
	/**
	 * @return the powerBoat
	 */
	public String getPowerBoat() {
		return powerBoat;
	}
	/**
	 * @param powerBoat the powerBoat to set
	 */
	public void setPowerBoat(String powerBoat) {
		this.powerBoat = powerBoat;
	}
	/**
	 * @return the assault
	 */
	public String getAssault() {
		return assault;
	}
	/**
	 * @param assault the assault to set
	 */
	public void setAssault(String assault) {
		this.assault = assault;
	}
	/**
	 * @return the pump
	 */
	public String getPump() {
		return pump;
	}
	/**
	 * @param pump the pump to set
	 */
	public void setPump(String pump) {
		this.pump = pump;
	}
	/**
	 * @return the drainDevice
	 */
	public String getDrainDevice() {
		return drainDevice;
	}
	/**
	 * @param drainDevice the drainDevice to set
	 */
	public void setDrainDevice(String drainDevice) {
		this.drainDevice = drainDevice;
	}
	/**
	 * @return the divingDevice
	 */
	public String getDivingDevice() {
		return divingDevice;
	}
	/**
	 * @param divingDevice the divingDevice to set
	 */
	public void setDivingDevice(String divingDevice) {
		this.divingDevice = divingDevice;
	}
	/**
	 * @return the transportCar
	 */
	public String getTransportCar() {
		return transportCar;
	}
	/**
	 * @param transportCar the transportCar to set
	 */
	public void setTransportCar(String transportCar) {
		this.transportCar = transportCar;
	}
	/**
	 * @return the liftingDevice
	 */
	public String getLiftingDevice() {
		return liftingDevice;
	}
	/**
	 * @param liftingDevice the liftingDevice to set
	 */
	public void setLiftingDevice(String liftingDevice) {
		this.liftingDevice = liftingDevice;
	}
	/**
	 * @return the earthMachine
	 */
	public String getEarthMachine() {
		return earthMachine;
	}
	/**
	 * @param earthMachine the earthMachine to set
	 */
	public void setEarthMachine(String earthMachine) {
		this.earthMachine = earthMachine;
	}
	/**
	 * @return the machine
	 */
	public String getMachine() {
		return machine;
	}
	/**
	 * @param machine the machine to set
	 */
	public void setMachine(String machine) {
		this.machine = machine;
	}
	/**
	 * @return the nowPerson
	 */
	public String getNowPerson() {
		return nowPerson;
	}
	/**
	 * @param nowPerson the nowPerson to set
	 */
	public void setNowPerson(String nowPerson) {
		this.nowPerson = nowPerson;
	}
	/**
	 * @return the numberStaff
	 */
	public String getNumberStaff() {
		return numberStaff;
	}
	/**
	 * @param numberStaff the numberStaff to set
	 */
	public void setNumberStaff(String numberStaff) {
		this.numberStaff = numberStaff;
	}
	/**
	 * @return the unitNature
	 */
	public String getUnitNature() {
		return unitNature;
	}
	/**
	 * @param unitNature the unitNature to set
	 */
	public void setUnitNature(String unitNature) {
		this.unitNature = unitNature;
	}
	/**
	 * @return the professionalRes
	 */
	public String getProfessionalRes() {
		return professionalRes;
	}
	/**
	 * @param professionalRes the professionalRes to set
	 */
	public void setProfessionalRes(String professionalRes) {
		this.professionalRes = professionalRes;
	}
	/**
	 * @return the resTeamPlace
	 */
	public String getResTeamPlace() {
		return resTeamPlace;
	}
	/**
	 * @param resTeamPlace the resTeamPlace to set
	 */
	public void setResTeamPlace(String resTeamPlace) {
		this.resTeamPlace = resTeamPlace;
	}
	/**
	 * @return the resTeamType
	 */
	public String getResTeamType() {
		return resTeamType;
	}
	/**
	 * @param resTeamType the resTeamType to set
	 */
	public void setResTeamType(String resTeamType) {
		this.resTeamType = resTeamType;
	}
	
}

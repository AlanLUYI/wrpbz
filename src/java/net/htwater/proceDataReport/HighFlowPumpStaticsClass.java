/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月9日
 * @updateTime 2015年2月9日
 * @描述:大流量移动水泵统计表
 */
public class HighFlowPumpStaticsClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2963510702708611444L;
	
	private String name;
	private String count;
	private String storagePlace;
	private String storageUnit;
	private String flow;
	private String head;
	private String power;
	private String isCar;
	private String isWeight;
	private String isPower;
	private String inlet;
	private String outlet;
	private String cable;
	private String isControlBox;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}
	/**
	 * @return the storagePlace
	 */
	public String getStoragePlace() {
		return storagePlace;
	}
	/**
	 * @param storagePlace the storagePlace to set
	 */
	public void setStoragePlace(String storagePlace) {
		this.storagePlace = storagePlace;
	}
	/**
	 * @return the storageUnit
	 */
	public String getStorageUnit() {
		return storageUnit;
	}
	/**
	 * @param storageUnit the storageUnit to set
	 */
	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
	}
	/**
	 * @return the flow
	 */
	public String getFlow() {
		return flow;
	}
	/**
	 * @param flow the flow to set
	 */
	public void setFlow(String flow) {
		this.flow = flow;
	}
	/**
	 * @return the head
	 */
	public String getHead() {
		return head;
	}
	/**
	 * @param head the head to set
	 */
	public void setHead(String head) {
		this.head = head;
	}
	/**
	 * @return the power
	 */
	public String getPower() {
		return power;
	}
	/**
	 * @param power the power to set
	 */
	public void setPower(String power) {
		this.power = power;
	}
	/**
	 * @return the isCar
	 */
	public String getIsCar() {
		return isCar;
	}
	/**
	 * @param isCar the isCar to set
	 */
	public void setIsCar(String isCar) {
		this.isCar = isCar;
	}
	/**
	 * @return the isWeight
	 */
	public String getIsWeight() {
		return isWeight;
	}
	/**
	 * @param isWeight the isWeight to set
	 */
	public void setIsWeight(String isWeight) {
		this.isWeight = isWeight;
	}
	/**
	 * @return the isPower
	 */
	public String getIsPower() {
		return isPower;
	}
	/**
	 * @param isPower the isPower to set
	 */
	public void setIsPower(String isPower) {
		this.isPower = isPower;
	}
	/**
	 * @return the inlet
	 */
	public String getInlet() {
		return inlet;
	}
	/**
	 * @param inlet the inlet to set
	 */
	public void setInlet(String inlet) {
		this.inlet = inlet;
	}
	/**
	 * @return the outlet
	 */
	public String getOutlet() {
		return outlet;
	}
	/**
	 * @param outlet the outlet to set
	 */
	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}
	/**
	 * @return the cable
	 */
	public String getCable() {
		return cable;
	}
	/**
	 * @param cable the cable to set
	 */
	public void setCable(String cable) {
		this.cable = cable;
	}
	/**
	 * @return the isControlBox
	 */
	public String getIsControlBox() {
		return isControlBox;
	}
	/**
	 * @param isControlBox the isControlBox to set
	 */
	public void setIsControlBox(String isControlBox) {
		this.isControlBox = isControlBox;
	}
}

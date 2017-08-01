/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月9日
 * @updateTime 2015年2月9日
 * @描述:社会防汛抢险应急资源调查统计表
 */
public class SocialResourcesStaticsClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4402905517214615411L;
	
	private String name;
	private String bread;
	private String manufacturer;
	private String address;
	private String companySize;
	private String dailyInventory;
	private String productionCapa;
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
	 * @return the bread
	 */
	public String getBread() {
		return bread;
	}
	/**
	 * @param bread the bread to set
	 */
	public void setBread(String bread) {
		this.bread = bread;
	}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the companySize
	 */
	public String getCompanySize() {
		return companySize;
	}
	/**
	 * @param companySize the companySize to set
	 */
	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}
	/**
	 * @return the dailyInventory
	 */
	public String getDailyInventory() {
		return dailyInventory;
	}
	/**
	 * @param dailyInventory the dailyInventory to set
	 */
	public void setDailyInventory(String dailyInventory) {
		this.dailyInventory = dailyInventory;
	}
	/**
	 * @return the productionCapa
	 */
	public String getProductionCapa() {
		return productionCapa;
	}
	/**
	 * @param productionCapa the productionCapa to set
	 */
	public void setProductionCapa(String productionCapa) {
		this.productionCapa = productionCapa;
	}
	
}

/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月4日
 * @updateTime 2015年2月6日
 * @描述:危险区基本情况表
 */
public class DangerZoneBaseClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6049691883719619866L;
	
	private String dacd;
	private String adcd;
	private String name;
	private String address;
	private String population;
	private String house;
	
	//
	public void setaddress(String _address) {
		this.address=_address;
	}
	public String getaddress() {
		return address;
	}
	//
	public void setname(String _name) {
		this.name=_name;
	}
	public String getname() {
		return name;
	}
	//
	public void setdacd(String _dacd) {
		this.dacd=_dacd;
	}
	public String getdacd() {
		return dacd;
	}
	//
	public void setadcd(String _adcd) {
		this.adcd=_adcd;
	}
	public String getadcd() {
		return adcd;
	}
	//
	public void setpopulation(String _population) {
		this.population=_population;
	}
	public String getpopulation() {
		return population;
	}
	//
	public void sethouse(String _house) {
		this.house=_house;
	}
	public String gethouse() {
		return house;
	}
}

package net.htwater.proceDataReport;

public class AdminDiviClass implements java.io.Serializable{

	/**
	 * @author yangshengfei
	 * @createTime 2015年1月27日
	 * @updateTime 2015年1月27日
	 * @描述:危险区基本情况表
	 */
	private static final long serialVersionUID = 1L;
	
	private String adcd;
	private String adnm;
	private String landArea;
	private String cultiArea;
	private String population;
	private String household;
	private String house;
	private String cutoffDate;

	//
	public String getadcd() {
		return adcd;
	}
	public void setadcd(String adcd) {
		this.adcd = adcd;
	}
	//
	public String getlandArea() {
		return landArea;
	}
	public void setlandArea(String landArea) {
		this.landArea = landArea;
	}
	//
	public String getcultiArea() {
		return cultiArea;
	}
	public void setcultiArea(String cultiArea) {
		this.cultiArea = cultiArea;
	}
	//
	public String getpopulation() {
		return population;
	}
	public void setpopulation(String population) {
		this.population = population;
	}
	//
	public String gethousehold() {
		return household;
	}
	public void sethousehold(String household) {
		this.household = household;
	}
	//
	public String gethouse() {
		return house;
	}
	public void sethouse(String house) {
		this.house = house;
	}
	//
	public String getcutoffDate() {
		return cutoffDate;
	}
	public void setcutoffDate(String cutoffDate) {
		this.cutoffDate=cutoffDate;
	}
	/**
	 * @return the adnm
	 */
	public String getadnm() {
		return adnm;
	}
	/**
	 * @param adnm the adnm to set
	 */
	public void setadnm(String adnm) {
		this.adnm = adnm;
	}
	
}

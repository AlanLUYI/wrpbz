package net.htwater.proceDataReport;

public class FloodDisClass implements java.io.Serializable{

	/**
	 * @author yangshengfei
	 * @createTime 2015年1月27日
	 * @updateTime 2015年1月27日
	 * @描述:山洪灾害影响情况表类
	 * @see 注意:该类应符合javaBean类的命名规范：
		一般JavaBean属性以小写字母开头，驼峰命名格式，相应的 getter/setter 方法是 get/set 接上首字母大写的属性名。例如：属性名为userName，其对应的getter/setter 方法是 getUserName/setUserName。
		但是，还有一些特殊情况：
		1、如果属性名的第二个字母大写，那么该属性名直接用作 getter/setter 方法中 get/set 的后部分，就是说大小写不变。例如属性名为uName，方法是getuName/setuName。
		2、如果前两个字母是大写（一般的专有名词和缩略词都会大写），也是属性名直接用作 getter/setter 方法中 get/set 的后部分。例如属性名为URL，方法是getURL/setURL。
		3、如果首字母大写，也是属性名直接用作 getter/setter 方法中 get/set 的后部分。例如属性名为Name，方法是getName/setName，这种是最糟糕的情况，会找不到属性出错，因为默认的属性名是name。
	 */
	private static final long serialVersionUID = 1L;
	
	private String adcd;
	private String hulPopulation;
	private String hulHousehold;
	private String hulHouse;
	private String hulFarmland;
	private String holPopulation;
	private String holHousehold;
	private String holSoiHouse;
	private String cutoffDate;

	//
	public String getadcd() {
		return adcd;
	}
	public void setadcd(String adcd) {
		this.adcd = adcd;
	}
	//
	public String gethulPopulation() {
		return hulPopulation;
	}
	public void sethulPopulation(String hulPopulation) {
		this.hulPopulation = hulPopulation;
	}
	//
	public String gethulHousehold() {
		return hulHousehold;
	}
	public void sethulHousehold(String hulHousehold) {
		this.hulHousehold = hulHousehold;
	}
	//
	public String gethulHouse() {
		return hulHouse;
	}
	public void sethulHouse(String hulHouse) {
		this.hulHouse = hulHouse;
	}
	//
	public String gethulFarmland() {
		return hulFarmland;
	}
	public void sethulFarmland(String hulFarmland) {
		this.hulFarmland = hulFarmland;
	}
	//
	public String getholPopulation() {
		return holPopulation;
	}
	public void setholPopulation(String holPopulation) {
		this.holPopulation = holPopulation;
	}
	//
	public String getholHousehold() {
		return holHousehold;
	}
	public void setholHousehold(String holHousehold) {
		this.holHousehold = holHousehold;
	}
	//
	public String getholSoiHouse() {
		return holSoiHouse;
	}
	public void setholSoiHouse(String holSoiHouse) {
		this.holSoiHouse = holSoiHouse;
	}
	//
	public String getcutoffDate() {
		return cutoffDate;
	}
	public void setcutoffDate(String cutoffDate) {
		this.cutoffDate=cutoffDate;
	}
	
}

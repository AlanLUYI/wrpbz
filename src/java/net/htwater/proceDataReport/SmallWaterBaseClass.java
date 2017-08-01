package net.htwater.proceDataReport;

public class SmallWaterBaseClass implements java.io.Serializable{

	/**
	 * @author yangshengfei
	 * @createTime 2015年2月3日
	 * @updateTime 2015年2月3日
	 * @描述:小流域基本情况表
	 * @see 注意:该类应符合javaBean类的命名规范：
		一般JavaBean属性以小写字母开头，驼峰命名格式，相应的 getter/setter 方法是 get/set 接上首字母大写的属性名。例如：属性名为userName，其对应的getter/setter 方法是 getUserName/setUserName。
		但是，还有一些特殊情况：
		1、如果属性名的第二个字母大写，那么该属性名直接用作 getter/setter 方法中 get/set 的后部分，就是说大小写不变。例如属性名为uName，方法是getuName/setuName。
		2、如果前两个字母是大写（一般的专有名词和缩略词都会大写），也是属性名直接用作 getter/setter 方法中 get/set 的后部分。例如属性名为URL，方法是getURL/setURL。
		3、如果首字母大写，也是属性名直接用作 getter/setter 方法中 get/set 的后部分。例如属性名为Name，方法是getName/setName，这种是最糟糕的情况，会找不到属性出错，因为默认的属性名是name。
	 */
	private static final long serialVersionUID = 1L;
	
	private String cacd;
	private String canm;
	private String caarea;
	private String river;
	private String rvlen;
	private String gradient;
	private String rvhead;
	private String rvmouth;

	//
	public String getcacd() {
		return cacd;
	}
	public void setcacd(String cacd) {
		this.cacd = cacd;
	}
	//
	public String getcanm() {
		return canm;
	}
	public void setcanm(String canm) {
		this.canm = canm;
	}
	//
	public String getcaarea() {
		return caarea;
	}
	public void setcaarea(String caarea) {
		this.caarea = caarea;
	}
	//
	public String getriver() {
		return river;
	}
	public void setriver(String river) {
		this.river = river;
	}
	//
	public String getrvlen() {
		return rvlen;
	}
	public void setrvlen(String rvlen) {
		this.rvlen = rvlen;
	}
	//
	public String getgradient() {
		return gradient;
	}
	public void setgradient(String gradient) {
		this.gradient = gradient;
	}
	//
	public String getrvhead() {
		return rvhead;
	}
	public void setrvhead(String rvhead) {
		this.rvhead = rvhead;
	}
	//
	public String getrvmouth() {
		return rvmouth;
	}
	public void setrvmouth(String rvmouth) {
		this.rvmouth = rvmouth;
	}
	
}

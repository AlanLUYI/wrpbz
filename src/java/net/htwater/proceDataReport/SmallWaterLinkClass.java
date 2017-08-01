/**
 * 
 */
package net.htwater.proceDataReport;

import java.io.Serializable;

/**
 * @author yangshengfei
 * @createTime 2015年2月3日
 * @updateTime 2015年2月3日
 * @描述:
 */
public class SmallWaterLinkClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7834608236218455810L;
	
	private String cacd;
	private String adcd;
	
	//
	public String getcacd() {
		return cacd;
	}
	public void setcacd(String _cacd) {
		this.cacd=_cacd;
	}
	
	//
	public String getadcd() {
		return adcd;
	}
	public void setadcd(String _adcd) {
		this.adcd=_adcd;
	}
}

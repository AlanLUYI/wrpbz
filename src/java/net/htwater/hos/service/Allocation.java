/**
 * 水资源配置子系统  
 * @author mcg
 * 2014-11-05
 */
package net.htwater.hos.service;

import java.util.Map;

public interface Allocation {

	static final String DB_HOS="HOS";
	/**
	 * 生活参数数据-图
	 * @param ids
	 * @return
	 */
	public Map<String, Object> getlifechartdata(String ids);
	/**
	 * 工业参数数据-图
	 * @param ids
	 * @return
	 */
	public Map<String, Object> getindustrychartdata(String ids);

	/**
	 * 农业参数数据-图
	 * @param ids
	 * @return
	 */
	public Map<String, Object> getagriculturechartdata(String ids);
	/**
	 * 农业参数数据-图
	 * @param ids
	 * @return
	 */
	public Map<String, Object> getagriculturechartdata50(String ids);
	/**
	 * 农业参数数据-图
	 * @param ids
	 * @return
	 */
	public Map<String, Object> getagriculturechartdata75(String ids);
	/**
	 * 农业参数数据-图
	 * @param ids
	 * @return
	 */
	public Map<String, Object> getagriculturechartdata90(String ids);
	/**
	 * 农业参数数据-图
	 * @param ids
	 * @return
	 */
	public Map<String, Object> getagriculturechartdata95(String ids);
	/**
	 * 生态
	 * @param ids
	 * @return
	 */
	public Map<String, Object> getecologicalchartdata(String ids);
	/**
	 * 水资源基本信息
	 * @param cd
	 * @return
	 */
	public Map<String, Object> getbaseinfo(String cd);
}

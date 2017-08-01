/**
 * 水资源配置子系统  
 * @author mcg
 * 2014-11-05
 */
package net.htwater.hos.action;

import net.htwater.hos.service.Allocation;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class AllocationAction extends DoAction {
	/**
	 * 生活参数数据-图
	 * @return
	 */
	public Responser getlifechartdata() {
		Allocation service = (Allocation) ServiceFactory.getService("allocation");
		String ids = params.getParam("ids");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getlifechartdata(ids)));
		return responser;
	}
	/**
	 * 工业参数数据-图
	 * @return
	 */
	public Responser getindustrychartchartdata() {
		Allocation service = (Allocation) ServiceFactory.getService("allocation");
		String ids = params.getParam("ids");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getindustrychartdata(ids)));
		return responser;
	}
	/**
	 * 农业参数数据-图
	 * @return
	 */
	public Responser getagriculturechartdata() {
		Allocation service = (Allocation) ServiceFactory.getService("allocation");
		String ids = params.getParam("ids");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getagriculturechartdata(ids)));
		return responser;
	}
	/**
	 * 农业参数数据-图 50%
	 * @return
	 */
	public Responser getagriculturechartdata50() {
		Allocation service = (Allocation) ServiceFactory.getService("allocation");
		String ids = params.getParam("ids");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getagriculturechartdata50(ids)));
		return responser;
	}
	/**
	 * 农业参数数据-图 75%
	 * @return
	 */
	public Responser getagriculturechartdata75() {
		Allocation service = (Allocation) ServiceFactory.getService("allocation");
		String ids = params.getParam("ids");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getagriculturechartdata75(ids)));
		return responser;
	}
	/**
	 * 农业参数数据-图90%
	 * @return
	 */
	public Responser getagriculturechartdata90() {
		Allocation service = (Allocation) ServiceFactory.getService("allocation");
		String ids = params.getParam("ids");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getagriculturechartdata90(ids)));
		return responser;
	}
	/**
	 * 农业参数数据-图 95%
	 * @return
	 */
	public Responser getagriculturechartdata95() {
		Allocation service = (Allocation) ServiceFactory.getService("allocation");
		String ids = params.getParam("ids");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getagriculturechartdata95(ids)));
		return responser;
	}
	/**
	 * 生态
	 * @return
	 */
	public Responser getecologicalchartdata() {
		Allocation service = (Allocation) ServiceFactory.getService("allocation");
		String ids = params.getParam("ids");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getecologicalchartdata(ids)));
		return responser;
	}
	/**
	 *  水资源基本信息
	 * @return
	 */
	public Responser getbaseinfo() {
		Allocation service = (Allocation) ServiceFactory.getService("allocation");
		String cd = params.getParam("cd");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getbaseinfo(cd)));
		return responser;
	}
}

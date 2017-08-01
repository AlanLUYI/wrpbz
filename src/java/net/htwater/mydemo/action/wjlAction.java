/**
 * 防汛水情雨情统计和资料上报服务
 * @author wangjinlong
 * @Date 2015-9-13
 * @since v 1.0
 */

package net.htwater.mydemo.action;

import net.htwater.mydemo.service.wjlService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;


public class wjlAction extends DoAction {
	
	/**
	 * 雨情信息 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser QueryYqInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryYqInfo())); 
		return responser;
	}
	
	/**
	 * 雨量信息 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser QueryRainInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryRainInfo(params.getParam("TM1"),params.getParam("TM2"),params.getParam("queryType")))); 
		return responser;
	}
	
	/**
	 * 水情信息 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser QuerySqInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QuerySqInfo())); 
		return responser;
	}
	/**
	 * 水位信息 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser QuerySwInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QuerySwInfo(params.getParam("TM1"),params.getParam("TM2"),params.getParam("queryType")))); 
		return responser;
	}
	
	/**
	 * 防汛人员分管理员和普通账户查询 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser AdminQueryFxry() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AdminQueryFxry(params.getParam("selectyr")))); 
		return responser;
	}
	public Responser CommonQueryFxry() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.CommonQueryFxry(params.getParam("selectyr"),params.getParam("unit")))); 
		return responser;
	}
	
	/**
	 * 防汛物资分管理员和普通账户查询 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser AdminQueryFxwz() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AdminQueryFxwz(params.getParam("selectyr")))); 
		return responser;
	}
	public Responser CommonQueryFxwz() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.CommonQueryFxwz(params.getParam("selectyr"),params.getParam("unit")))); 
		return responser;
	}
	public Responser AdminQueryFxwzTotal() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AdminQueryFxwzTotal(params.getParam("selectyr")))); 
		return responser;
	}
	public Responser CommonQueryFxwzTotal() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.CommonQueryFxwzTotal(params.getParam("selectyr"),params.getParam("unit")))); 
		return responser;
	}
	
	/**
	 * 队伍统计 分管理员和普通账户查询 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser AdminqueryDwStaInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AdminqueryDwStaInfo(params.getParam("selectyr")))); 
		return responser;
	}
	public Responser CommonqueryDwStaInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.CommonqueryDwStaInfo(params.getParam("selectyr"),params.getParam("unit")))); 
		return responser;
	}
//	public Responser queryDwStaInfo() {  
//		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
//		responser.setRtType(JSON);  
//		responser.setRtString(parseJSON(service.queryDwStaInfo())); 
//		return responser;
//	}
	
	/**
	 * 排涝统计分管理员和普通账户查询 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser AdminqueryPlStaInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AdminqueryPlStaInfo(params.getParam("selectyr")))); 
		return responser;
	}
	public Responser CommonqueryPlStaInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.CommonqueryPlStaInfo(params.getParam("selectyr"),params.getParam("unit")))); 
		return responser;
	}
//	public Responser queryPlStaInfo() {  
//		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
//		responser.setRtType(JSON);  
//		responser.setRtString(parseJSON(service.queryPlStaInfo())); 
//		return responser;
//	}
	
	/**
	 * 值班表分管理员和普通账户查询  
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser AdminQueryZbb() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AdminQueryZbb(params.getParam("selectyr"),params.getParam("typhoon")))); 
		return responser;
	}
	public Responser CommonQueryZbb() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.CommonQueryZbb(params.getParam("selectyr"),params.getParam("unit"),params.getParam("typhoon")))); 
		return responser;
	}
//	public Responser QueryZhibanbiao() {  
//		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
//		responser.setRtType(JSON);  
//		responser.setRtString(parseJSON(service.QueryZhibanbiao())); 
//		return responser;
//	}
	
	/**
	 * 报送防汛信息分管理员和普通账户查询 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser AdminQueryBsfxInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AdminQueryBsfxInfo(params.getParam("selectyr"),params.getParam("typhoon")))); 
		return responser;
	}
	public Responser CommonQueryBsfxInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.CommonQueryBsfxInfo(params.getParam("selectyr"),params.getParam("unit"),params.getParam("typhoon")))); 
		return responser;
	}
//	public Responser QueryBsfxInfo() {  
//		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
//		responser.setRtType(JSON);  
//		responser.setRtString(parseJSON(service.QueryBsfxInfo())); 
//		return responser;
//	}
	
	/**
	 * 内涝统计信息分管理员和普通账户查询 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser AdminQueryNlSta() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AdminQueryNlSta(params.getParam("selectyr"),params.getParam("typhoon")))); 
		return responser;
	}
	public Responser CommonQueryNlSta() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.CommonQueryNlSta(params.getParam("selectyr"),params.getParam("unit"),params.getParam("typhoon")))); 
		return responser;
	}
	
	/**
	 * 汛后报告分管理员和普通账户查询 
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	//汛后报告
	public Responser AdminQueryXhReport() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.AdminQueryXhReport(params.getParam("selectyr")))); 
		return responser;
	}
	public Responser CommonQueryXhReport() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.CommonQueryXhReport(params.getParam("selectyr"),params.getParam("unit")))); 
		return responser;
	}
//	public Responser queryXhReport() {  
//		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
//		responser.setRtType(JSON);  
//		responser.setRtString(parseJSON(service.queryXhReport())); 
//		return responser;
//	}
	
	//汛前服务
	/**
	 * 增加、删除和更新联系人
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser addContactPerson(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String uploadTime = params.getParam("uploadTime");
		String unit = params.getParam("unit");
		String username = params.getParam("username");
		String post = params.getParam("post");
		String officephone = params.getParam("officephone");
		String telephone = params.getParam("telephone");
		String demo = params.getParam("demo");
		String isUpload= params.getParam("isUpload");
		String isCheck= params.getParam("isCheck");
		responser.setRtString(parseJSON(service.addContactPerson(uploadTime,unit,username,post,officephone,telephone,demo,isUpload,isCheck)));
		return responser;
	}
	public Responser updateContactPerson(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String uploadTime = params.getParam("uploadTime");
		String unit = params.getParam("unit");
		String username = params.getParam("username");
		String post = params.getParam("post");
		String officephone = params.getParam("officephone");
		String telephone = params.getParam("telephone");
		String demo = params.getParam("demo");
		responser.setRtString(parseJSON(service.updateContactPerson(id,uploadTime,unit,username,post,officephone,telephone,demo)));
		return responser;
	}
	public Responser delContactPerson(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delContactPerson(id)));
		return responser;
	}
	
	
	/**
	 * 增加、删除和更新防汛物资
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser addFXWZ(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String time = params.getParam("time");
		String unit = params.getParam("unit");
		String qx = params.getParam("qx");
		String ckmc = params.getParam("ckmc");
		String ckwz = params.getParam("ckwz");
		String qpc = params.getParam("qpc");
		String ydbc = params.getParam("ydbc");
		String psb = params.getParam("psb");
		String zl = params.getParam("zl");
		String yscl = params.getParam("yscl");
		String bl = params.getParam("bl");
		String dl = params.getParam("dl");
		String yjd = params.getParam("yjd");
		String aqm = params.getParam("aqm");
		String yy = params.getParam("yy");
		String sd = params.getParam("sd");
		String jsqc = params.getParam("jsqc");
		String ssl = params.getParam("ssl");
		String ts = params.getParam("ts");
		String st = params.getParam("st");
		String kz = params.getParam("kz");
		String sk = params.getParam("sk");
		String yx = params.getParam("yx");
		String X = params.getParam("X");
		String Y = params.getParam("Y");
		String isUpload= params.getParam("isUpload");
		String isCheck= params.getParam("isCheck");
		responser.setRtString(parseJSON(service.addFXWZ(time,unit,qx,ckmc,ckwz,qpc,ydbc,psb,zl,yscl,bl,dl,yjd,aqm,yy,sd,jsqc,ssl,ts,st,kz,sk,yx,X,Y,isUpload,isCheck)));
		return responser;
	}
	public Responser updateFXWZ(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String time = params.getParam("time");
		String unit = params.getParam("unit");
		String qx = params.getParam("qx");
		String ckmc = params.getParam("ckmc");
		String ckwz = params.getParam("ckwz");
		String qpc = params.getParam("qpc");
		String ydbc = params.getParam("ydbc");
		String psb = params.getParam("psb");
		String zl = params.getParam("zl");
		String yscl = params.getParam("yscl");
		String bl = params.getParam("bl");
		String dl = params.getParam("dl");
		String yjd = params.getParam("yjd");
		String aqm = params.getParam("aqm");
		String yy = params.getParam("yy");
		String sd = params.getParam("sd");
		String jsqc = params.getParam("jsqc");
		String ssl = params.getParam("ssl");
		String ts = params.getParam("ts");
		String st = params.getParam("st");
		String kz = params.getParam("kz");
		String sk = params.getParam("sk");
		String yx = params.getParam("yx");
		String X = params.getParam("X");
		String Y = params.getParam("Y");
		responser.setRtString(parseJSON(service.updateFXWZ(id,time,unit,qx,ckmc,ckwz,qpc,ydbc,psb,zl,yscl,bl,dl,yjd,aqm,yy,sd,jsqc,ssl,ts,st,kz,sk,yx,X,Y)));
		return responser;
	}
	public Responser delFXWZ(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delFXWZ(id)));
		return responser;
	}
	
	/**
	 * 增加、删除和更新防汛队伍
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser addDwInfo(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String time = params.getParam("time");
		String unit = params.getParam("unit");
		String aera = params.getParam("aera");
		String teanName = params.getParam("teanName");
		String count = params.getParam("count");
		String place = params.getParam("place");
		String contacter = params.getParam("contacter");
		String phone = params.getParam("phone");
		String X = params.getParam("X");
		String Y = params.getParam("Y");
		String isUpload= params.getParam("isUpload");
		String isCheck= params.getParam("isCheck");
		responser.setRtString(parseJSON(service.addDwInfo(time,unit,aera,teanName,count,place,contacter,phone,X,Y,isUpload,isCheck)));
		return responser;
	}
	public Responser updateDwInfo(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String time = params.getParam("time");
		String unit = params.getParam("unit");
		String aera = params.getParam("aera");
		String teanName = params.getParam("teanName");
		String count = params.getParam("count");
		String place = params.getParam("place");
		String contacter = params.getParam("contacter");
		String phone = params.getParam("phone");
		String X = params.getParam("X");
		String Y = params.getParam("Y");
		responser.setRtString(parseJSON(service.updateDwInfo(id,time,unit,aera,teanName,count,place,contacter,phone,X,Y)));
		return responser;
	}
	public Responser delDwInfo(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delDwInfo(id)));
		return responser;
	}
	
	/**
	 * 增加、删除和更新排涝能力统计
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser addPlCapStaInfo(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String time = params.getParam("time");
		String unit = params.getParam("unit");
		String aera = params.getParam("aera");
		String totalPlCap = params.getParam("totalPlCap");
		String qpcCnt = params.getParam("qpcCnt");
		String qpcCap = params.getParam("qpcCap");
		String ydbcCnt = params.getParam("ydbcCnt");
		String ydbeCap = params.getParam("ydbeCap");
		String psbcCnt = params.getParam("psbcCnt");
		String psbcCap = params.getParam("psbcCap");
		String memo = params.getParam("memo");
		String isUpload= params.getParam("isUpload");
		String isCheck= params.getParam("isCheck");
		responser.setRtString(parseJSON(service.addPlCapStaInfo(time,unit,aera,totalPlCap,qpcCnt,qpcCap,ydbcCnt,ydbeCap,psbcCnt,psbcCap,memo,isUpload,isCheck)));
		return responser;
	}
	public Responser updatePlCapStaInfo(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String time = params.getParam("time");
		String unit = params.getParam("unit");
		String aera = params.getParam("aera");
		String totalPlCap = params.getParam("totalPlCap");
		String qpcCnt = params.getParam("qpcCnt");
		String qpcCap = params.getParam("qpcCap");
		String ydbcCnt = params.getParam("ydbcCnt");
		String ydbeCap = params.getParam("ydbeCap");
		String psbcCnt = params.getParam("psbcCnt");
		String psbcCap = params.getParam("psbcCap");
		String memo = params.getParam("memo");
		responser.setRtString(parseJSON(service.updatePlCapStaInfo(id,time,unit,aera,totalPlCap,qpcCnt,qpcCap,ydbcCnt,ydbeCap,psbcCnt,psbcCap,memo)));
		return responser;
	}
	public Responser delPlCapStaInfo(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delPlCapStaInfo(id)));
		return responser;
	}
	
	/**
	 * 增加、删除和更新值班表
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	//汛期服务
	public Responser addZbPerson(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String unit = params.getParam("unit");
		String zbtime = params.getParam("zbtime");
		String time = params.getParam("time");
		String leader = params.getParam("leader");
		String leaderPhone = params.getParam("leaderPhone");
		String zbPerson = params.getParam("zbPerson");
		String zbPersonPhone = params.getParam("zbPersonPhone");
		String zbPhone = params.getParam("zbPhone");
		String fax = params.getParam("fax");
		String typhoon = params.getParam("typhoon");
		String isUpload= params.getParam("isUpload");
		String isCheck= params.getParam("isCheck");
		responser.setRtString(parseJSON(service.addZbPerson(unit,zbtime,time,leader,leaderPhone,zbPerson,zbPersonPhone,zbPhone,fax,typhoon,isUpload,isCheck)));
		return responser;
	}
	public Responser updateZbPerson(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String unit = params.getParam("unit");
		String time = params.getParam("time");
		String zbtime = params.getParam("zbtime");
		String leader = params.getParam("leader");
		String leaderPhone = params.getParam("leaderPhone");
		String zbPerson = params.getParam("zbPerson");
		String zbPersonPhone = params.getParam("zbPersonPhone");
		String zbPhone = params.getParam("zbPhone");
		String fax = params.getParam("fax");
		responser.setRtString(parseJSON(service.updateZbPerson(id,unit,time,zbtime,leader,leaderPhone,zbPerson,zbPersonPhone,zbPhone,fax)));
		return responser;
	}
	public Responser delZbPerson(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delZbPerson(id)));
		return responser;
	}
	
	
	/**
	 * 增加、删除和更新防汛信息
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser addBsfxInfo(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String time = params.getParam("time");
		String unit = params.getParam("unit");
		String aera = params.getParam("aera");
		String bqry = params.getParam("bqry");
		String cdry = params.getParam("cdry");
		String bqcl = params.getParam("bqcl");
		String cdcl = params.getParam("cdcl");
		String jsdd = params.getParam("jsdd");
		String zyry = params.getParam("zyry");
		String smdf = params.getParam("smdf");
		String smql = params.getParam("smql");
		String ldsh = params.getParam("ldsh");
		String ldxf = params.getParam("ldxf");
		String qlcz = params.getParam("qlcz");
		String yjstgd = params.getParam("yjstgd");
		String slrx = params.getParam("slrx");
		String qxdlss = params.getParam("qxdlss");
		String hwggdt = params.getParam("qxdlss");
		String hwggql = params.getParam("hwggql");
		String ryss = params.getParam("ryss");
		String rysw = params.getParam("rysw");
		String fwdt = params.getParam("fwdt");
		String pcjjxq = params.getParam("pcjjxq");
		String other = params.getParam("other");
		String xqdeal = params.getParam("other");
		String typhoon = params.getParam("typhoon");
		String isUpload= params.getParam("isUpload");
		String isCheck= params.getParam("isCheck");
		responser.setRtString(parseJSON(service.addBsfxInfo(time,unit,aera,bqry,cdry,bqcl,cdcl,jsdd,zyry,smdf,smql,ldsh,ldxf,qlcz,yjstgd,slrx,qxdlss,hwggdt,hwggql,ryss,rysw,fwdt,pcjjxq,other,xqdeal,typhoon,isUpload,isCheck)));
		return responser;
	}
	public Responser updateBsfxInfo(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String time = params.getParam("time");
		String unit = params.getParam("unit");
		String aera = params.getParam("aera");
		String bqry = params.getParam("bqry");
		String cdry = params.getParam("cdry");
		String bqcl = params.getParam("bqcl");
		String cdcl = params.getParam("cdcl");
		String jsdd = params.getParam("jsdd");
		String zyry = params.getParam("zyry");
		String smdf = params.getParam("smdf");
		String smql = params.getParam("smql");
		String ldsh = params.getParam("ldsh");
		String ldxf = params.getParam("ldxf");
		String qlcz = params.getParam("qlcz");
		String yjstgd = params.getParam("yjstgd");
		String slrx = params.getParam("slrx");
		String qxdlss = params.getParam("qxdlss");
		String hwggdt = params.getParam("qxdlss");
		String hwggql = params.getParam("hwggql");
		String ryss = params.getParam("ryss");
		String rysw = params.getParam("rysw");
		String fwdt = params.getParam("fwdt");
		String pcjjxq = params.getParam("pcjjxq");
		String other = params.getParam("other");
		String xqdeal = params.getParam("other");
		responser.setRtString(parseJSON(service.updateBsfxInfo(id,time,unit,aera,bqry,cdry,bqcl,cdcl,jsdd,zyry,smdf,smql,ldsh,ldxf,qlcz,yjstgd,slrx,qxdlss,hwggdt,hwggql,ryss,rysw,fwdt,pcjjxq,other,xqdeal)));
		return responser;
	}
	public Responser delBsfxInfo(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delBsfxInfo(id)));
		return responser;
	}
	
	
	/**
	 * 增加、删除和更新内涝统计
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	public Responser addNlSta(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String unit = params.getParam("unit");
		String time = params.getParam("time");
		String jsdType = params.getParam("jsdType");
		String jsdLoc = params.getParam("jsdLoc");
		String waterDep = params.getParam("waterDep");
		String jsAera = params.getParam("jsAera");
		String jsReason = params.getParam("jsReason");
		String dealMethod = params.getParam("dealMethod");
		String respUnit = params.getParam("respUnit");
		String typhoon = params.getParam("typhoon");
		String isUpload= params.getParam("isUpload");
		String isCheck= params.getParam("isCheck");
		responser.setRtString(parseJSON(service.addNlSta(time,unit,jsdType,jsdLoc,waterDep,jsAera,jsReason,dealMethod,respUnit,typhoon,isUpload,isCheck)));
		return responser;
	}
	public Responser updateNlSta(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String unit = params.getParam("unit");
		String time = params.getParam("time");
		String jsdType = params.getParam("jsdType");
		String jsdLoc = params.getParam("jsdLoc");
		String waterDep = params.getParam("waterDep");
		String jsAera = params.getParam("jsAera");
		String jsReason = params.getParam("jsReason");
		String dealMethod = params.getParam("dealMethod");
		String respUnit = params.getParam("respUnit");
		responser.setRtString(parseJSON(service.updateNlSta(id,time,unit,jsdType,jsdLoc,waterDep,jsAera,jsReason,dealMethod,respUnit)));
		return responser;
	}
	public Responser delNlSta(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delNlSta(id)));
		return responser;
	}
	
	public Responser addXhReport(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String title = params.getParam("title");
		String unit = params.getParam("unit");
		String time = params.getParam("time");
		String filepath = params.getParam("filepath");
		String isUpload= params.getParam("isUpload");
		String isCheck= params.getParam("isCheck");
		responser.setRtString(parseJSON(service.addXhReport(title,unit,time,filepath,isUpload,isCheck)));
		return responser;
	}
	public Responser delXhReport(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delXhReport(id)));
		return responser;
	}
	
	
	/**
	 * 上报审批服务
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	//上报审批服务
	public Responser updateRyUploadState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isUpload = params.getParam("isUpload");
		responser.setRtString(parseJSON(service.updateRyUploadState(id,isUpload)));
		return responser;
	}
	public Responser updateRyCheckState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isCheck = params.getParam("isCheck");
		responser.setRtString(parseJSON(service.updateRyCheckState(id,isCheck)));
		return responser;
	}
	public Responser updateWzUploadState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isUpload = params.getParam("isUpload");
		responser.setRtString(parseJSON(service.updateWzUploadState(id,isUpload)));
		return responser;
	}
	public Responser updateWzCheckState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isCheck = params.getParam("isCheck");
		responser.setRtString(parseJSON(service.updateWzCheckState(id,isCheck)));
		return responser;
	}
	public Responser updateDwUploadState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isUpload = params.getParam("isUpload");
		responser.setRtString(parseJSON(service.updateDwUploadState(id,isUpload)));
		return responser;
	}
	public Responser updateDwCheckState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isCheck = params.getParam("isCheck");
		responser.setRtString(parseJSON(service.updateDwCheckState(id,isCheck)));
		return responser;
	}
	public Responser updatePlStaUploadState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isUpload = params.getParam("isUpload");
		responser.setRtString(parseJSON(service.updatePlStaUploadState(id,isUpload)));
		return responser;
	}
	public Responser updatePlStaCheckState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isCheck = params.getParam("isCheck");
		responser.setRtString(parseJSON(service.updatePlStaCheckState(id,isCheck)));
		return responser;
	}
	
	
	public Responser updateZbUploadState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isUpload = params.getParam("isUpload");
		responser.setRtString(parseJSON(service.updateZbUploadState(id,isUpload)));
		return responser;
	}
	public Responser updateZbCheckState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isCheck = params.getParam("isCheck");
		responser.setRtString(parseJSON(service.updateZbCheckState(id,isCheck)));
		return responser;
	}
	public Responser updateBsfxUploadState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isUpload = params.getParam("isUpload");
		responser.setRtString(parseJSON(service.updateBsfxUploadState(id,isUpload)));
		return responser;
	}
	public Responser updateBsfxCheckState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isCheck = params.getParam("isCheck");
		responser.setRtString(parseJSON(service.updateBsfxCheckState(id,isCheck)));
		return responser;
	}
	public Responser updateNlStaUploadState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isUpload = params.getParam("isUpload");
		responser.setRtString(parseJSON(service.updateNlStaUploadState(id,isUpload)));
		return responser;
	}
	public Responser updateNlStaCheckState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isCheck = params.getParam("isCheck");
		responser.setRtString(parseJSON(service.updateNlStaCheckState(id,isCheck)));
		return responser;
	}
	public Responser updateRepUploadState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isUpload = params.getParam("isUpload");
		responser.setRtString(parseJSON(service.updateRepUploadState(id,isUpload)));
		return responser;
	}
	public Responser updateRepCheckState(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String isCheck = params.getParam("isCheck");
		responser.setRtString(parseJSON(service.updateRepCheckState(id,isCheck)));
		return responser;
	}
	
	public Responser QueryTyphoon() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.QueryTyphoon(params.getParam("selectyr")))); 
		return responser;
	}
	
	
	/**
	 * 退回理由入库
	 * @Date 2015-09-17
	 * @since v 1.0
	 * @return
	 */
	//退回理由入库
	public Responser updateRyRjtRsn2Db(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String rjtReason = params.getParam("rjtReason");
		responser.setRtString(parseJSON(service.updateRyRjtRsn2Db(id,rjtReason)));
		return responser;
	}
	public Responser updateZbRjtRsn2Db(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String rjtReason = params.getParam("rjtReason");
		responser.setRtString(parseJSON(service.updateZbRjtRsn2Db(id,rjtReason)));
		return responser;
	}
	public Responser updateBsfxRjtRsn2Db(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String rjtReason = params.getParam("rjtReason");
		responser.setRtString(parseJSON(service.updateBsfxRjtRsn2Db(id,rjtReason)));
		return responser;
	}
	public Responser updateNlStaRjtRsn2Db(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String rjtReason = params.getParam("rjtReason");
		responser.setRtString(parseJSON(service.updateNlStaRjtRsn2Db(id,rjtReason)));
		return responser;
	}
	public Responser updateRepRjtRsn2Db(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String rjtReason = params.getParam("rjtReason");
		responser.setRtString(parseJSON(service.updateRepRjtRsn2Db(id,rjtReason)));
		return responser;
	}
	public Responser updateDwRjtRsn2Db(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String rjtReason = params.getParam("rjtReason");
		responser.setRtString(parseJSON(service.updateDwRjtRsn2Db(id,rjtReason)));
		return responser;
	}
	public Responser updateWzRjtRsn2Db(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String rjtReason = params.getParam("rjtReason");
		responser.setRtString(parseJSON(service.updateWzRjtRsn2Db(id,rjtReason)));
		return responser;
	}
	public Responser updatePlStaRjtRsn2Db(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String rjtReason = params.getParam("rjtReason");
		responser.setRtString(parseJSON(service.updatePlStaRjtRsn2Db(id,rjtReason)));
		return responser;
	}
	
	
	
	public Responser queryGPSPos() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);  
		String tm1 = params.getParam("tm1");
		String tm2 = params.getParam("tm2");
		String carNum = params.getParam("carNum");
		responser.setRtString(parseJSON(service.queryGPSPos(tm1,tm2,carNum))); 
		return responser;
	}
	
	public Responser queryLqInfo() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);  
//		String tm1 = params.getParam("tm1");
//		String tm2 = params.getParam("tm2");
//		String carNum = params.getParam("carNum");
		responser.setRtString(parseJSON(service.queryLqInfo())); 
		return responser;
	}
	
	public Responser queryLqUser() {  
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);  
//		String tm1 = params.getParam("tm1");
//		String tm2 = params.getParam("tm2");
//		String carNum = params.getParam("carNum");
		responser.setRtString(parseJSON(service.queryLqUser())); 
		return responser;
	}
	
	public Responser updateLqxcPic(){
		wjlService service = (wjlService) ServiceFactory.getService("wjlService");
		responser.setRtType(JSON);
		
		String pictm = params.getParam("pictm");
		String picuser = params.getParam("picuser");
		String title = params.getParam("title");
		String picname = params.getParam("picname");//1.jpg,2.jpg,3.jpg
		String memo = params.getParam("memo");
		String tel = params.getParam("tel");
		
		responser.setRtString(parseJSON(service.updateLqxcPic(pictm,picuser,title,picname,memo,tel)));
		return responser;
	}
	      	
}

/**
 * 防汛管理服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.FxglService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class FxglAction extends DoAction {

	public Responser Queryszxx() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryszxx())); 
		return responser;
	}
	public Responser Querybzxx() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querybzxx())); 
		return responser;
	}
	public Responser Querydmll() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querydmll())); 
		return responser;
	}
	public Responser Query360() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Query360())); 
		return responser;
	}
	public Responser Queryspd() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryspd())); 
		return responser;
	}
	public Responser Queryqjd() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryqjd())); 
		return responser;
	}
	/**
	 * 抢险队伍
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Queryqxdw() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryqxdw())); 
		return responser;
	}
	
	public Responser Queryfhmb() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryfhmb())); 
		return responser;
	}
	
	/**
	 * 物资 
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public Responser Querywz() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Querywz())); 
		return responser;
	}
	
	public Responser Queryfxya_lj() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryfxya_lj())); 
		return responser;
	}
	
	public Responser Queryfxya_yj() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		
		responser.setRtString(parseJSON(service.Queryfxya_yj())); 
		return responser;
	}
	
	public Responser Queryfxwz2() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryfxwz2())); 
		return responser;
	}
	
	public Responser QueryEquip() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryEquip())); 
		return responser;
	}
	public Responser addEquipment(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String equipNM = params.getParam("equipNM");
		String equipLoc = params.getParam("equipLoc");
		String manger = params.getParam("manger");
		String telephone = params.getParam("telephone");
		String equipWell = params.getParam("equipWell");
		String memo = params.getParam("memo");
		String regionid = params.getParam("regionid");
		responser.setRtString(parseJSON(service.addEquipment(equipNM, equipLoc, manger, telephone, equipWell, memo, regionid)));
		return responser;
	}
	public Responser addEquipCheck(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String elementnm = params.getParam("elementnm");
		String departmentnm = params.getParam("departmentnm");
		String responsiblenm = params.getParam("responsiblenm");
		String sdt = params.getParam("sdt");
		String state = params.getParam("state");
		String description = params.getParam("description");
		String filename = params.getParam("filename");
		String orgname = params.getParam("orgname");
		String regionid = params.getParam("regionid");
		responser.setRtString(parseJSON(service.addEquipCheck(elementnm, departmentnm, responsiblenm, sdt, state, description, filename, orgname,regionid)));
		return responser;
	}
	public Responser addEquipMaintain(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String elementnm = params.getParam("elementnm");
		String departmentnm = params.getParam("departmentnm");
		String responsiblenm = params.getParam("responsiblenm");
		String sdt = params.getParam("sdt");
		String state = params.getParam("state");
		String description = params.getParam("description");
		String filename = params.getParam("filename");
		String orgname = params.getParam("orgname");
		String regionid = params.getParam("regionid");
		responser.setRtString(parseJSON(service.addEquipMaintain(elementnm, departmentnm, responsiblenm, sdt, state, description, filename, orgname,regionid)));
		return responser;
	}
	public Responser updateEquipment(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String equipNM = params.getParam("equipNM");
		String equipLoc = params.getParam("equipLoc");
		String manger = params.getParam("manger");
		String telephone = params.getParam("telephone");
		String equipWell = params.getParam("equipWell");
		String memo = params.getParam("memo");
		responser.setRtString(parseJSON(service.updateEquipment(id,equipNM, equipLoc, manger, telephone, equipWell, memo)));
		return responser;
	}
	
	public Responser updateContactPersonorderid(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String type = params.getParam("type"); 
		responser.setRtString(parseJSON(service.updateContactPersonorderid(id,type)));
		return responser;
	}
	public Responser delEquipment(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delEquipment(id)));
		return responser;
	}
	public Responser delEquipCheck(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delEquipCheck(id)));
		return responser;
	}
	public Responser delEquipMaintain(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delEquipMaintain(id)));
		return responser;
	}
	
	public Responser Queryjb() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Queryjb())); 
		return responser;
	}
	
	public Responser queryLatestWXRepTM() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		String regionid = params.getParam("regionid");
		String repType = params.getParam("repType");
		responser.setRtString(parseJSON(service.queryLatestWXRepTM(regionid,repType))); 
		return responser;
	}
	
	public Responser QueryMonthReport() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		String TM = params.getParam("TM");
		String repType = params.getParam("repType");
		responser.setRtString(parseJSON(service.QueryMonthReport(TM,repType))); 
		return responser;
	}
	
	public Responser QueryMonthReport4Manage() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		String regionid = params.getParam("regionid");
		String repType = params.getParam("repType");
		responser.setRtString(parseJSON(service.QueryMonthReport4Manage(regionid,repType))); 
		return responser;
	}
	
	public Responser queryLatestYHRepTM() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		String regionid = params.getParam("regionid");
		String repType = params.getParam("repType");
		responser.setRtString(parseJSON(service.queryLatestYHRepTM(regionid,repType))); 
		return responser;
	}
	
	public Responser QueryReport4WXYH() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		String regionid = params.getParam("regionid");
		String repType = params.getParam("repType");
		responser.setRtString(parseJSON(service.QueryReport4WXYH(regionid,repType))); 
		return responser;
	}
	
	public Responser addMonthReport(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String repNM = params.getParam("repNM");
		String orgname = params.getParam("orgname");
		String repType = params.getParam("repType");
		String repTM = params.getParam("repTM");
		String regionid = params.getParam("regionid");
		responser.setRtString(parseJSON(service.addMonthReport(repNM,orgname,repType,repTM,regionid)));
		return responser;
	}
	public Responser addObserveReport(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String regionid = params.getParam("regionid");
		String year = params.getParam("year");
		String name = params.getParam("name");
		String repNM = params.getParam("repNM");
		String orgname = params.getParam("orgname");
		String filetype = params.getParam("filetype");
		responser.setRtString(parseJSON(service.addObserveReport(regionid,year,name,repNM,orgname,filetype)));
		return responser;
	}
	public Responser QueryWeiXiuRep() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryWeiXiuRep(params.getParam("TM")))); 
		return responser;
	}
	public Responser QueryStaticRep() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryStaticRep(params.getParam("TM")))); 
		return responser;
	}
	public Responser QueryWeixiuStaticRep() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryWeixiuStaticRep(params.getParam("TM"),params.getParam("repType")))); 
		return responser;
	}
	public Responser addWeiXiuRep(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String repNM = params.getParam("repNM");
		String orgname = params.getParam("orgname");
		String repType = params.getParam("repType");
		String repTM = params.getParam("repTM");
		String regionid = params.getParam("regionid");
		responser.setRtString(parseJSON(service.addWeiXiuRep(repNM,orgname,repType,repTM,regionid)));
		return responser;
	}
	public Responser getChecklist() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getChecklist())); 
		return responser;
	}
	public Responser getMaintainForm() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getMaintainForm())); 
		return responser;
	}
	public Responser queryObserReport() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.queryObserReport(params.getParam("TM1"),params.getParam("TM2"),params.getParam("filetype")))); 
		return responser;
	}
	
	public Responser QuerySubDanger() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QuerySubDanger())); 
		return responser;
	}
	
	public Responser QueryDanger() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryDanger())); 
		return responser;
	}
	
	public Responser addSubDanger(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String name = params.getParam("name");
		String location = params.getParam("location");
		String description = params.getParam("description");
		String tm = params.getParam("tm");
		String howFind = params.getParam("howFind");
		String pic = params.getParam("pic");
		String dealMethod = params.getParam("dealMethod");
		String dealTm = params.getParam("dealTm");
		String dealPic = params.getParam("dealPic");
		String dealMemo = params.getParam("dealMemo");
		String regionid = params.getParam("regionid");
		responser.setRtString(parseJSON(service.addSubDanger(name, location, description, tm, howFind, pic, dealMethod, dealTm, dealPic, dealMemo, regionid)));
		return responser;
	}
	
	public Responser delSubDanger(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delSubDanger(id)));
		return responser;
	}
	public Responser updateSubDanger(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String name = params.getParam("name");
		String location = params.getParam("location");
		String description = params.getParam("description");
		String tm = params.getParam("tm");
		String howFind = params.getParam("howFind");
		String pic = params.getParam("pic");
		String dealMethod = params.getParam("dealMethod");
		String dealTm = params.getParam("dealTm");
		String dealPic = params.getParam("dealPic");
		String dealMemo = params.getParam("dealMemo");
		responser.setRtString(parseJSON(service.updateSubDanger(id,name, location, description, tm, howFind, pic, dealMethod, dealTm, dealPic, dealMemo)));
		return responser;
	}
	
	public Responser addDanger(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String unit = params.getParam("unit");
		String writetm = params.getParam("writetm");
		String name = params.getParam("name");
		String location = params.getParam("location");
		String description = params.getParam("description");
		String tm = params.getParam("tm");
		String pic = params.getParam("pic");
		String orgPic = params.getParam("orgPic");
		String qxState = params.getParam("qxState");
		String xqImprove = params.getParam("xqImprove");
		String problem = params.getParam("problem");
		String report = params.getParam("report");
		String orgReport = params.getParam("orgReport");
		String writeperson = params.getParam("writeperson");
		String phone = params.getParam("phone");
		String respperson = params.getParam("respperson");
		String regionid = params.getParam("regionid");
		
		responser.setRtString(parseJSON(service.addDanger( unit, writetm, name, location, description, tm, pic, orgPic, qxState, xqImprove, problem, report, orgReport, writeperson, phone, respperson, regionid)));
		return responser;
	}
	
	public Responser updateDanger(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String unit = params.getParam("unit");
		String writetm = params.getParam("writetm");
		String name = params.getParam("name");
		String location = params.getParam("location");
		String description = params.getParam("description");
		String tm = params.getParam("tm");
		String pic = params.getParam("pic");
		String orgPic = params.getParam("orgPic");
		String qxState = params.getParam("qxState");
		String xqImprove = params.getParam("xqImprove");
		String problem = params.getParam("problem");
		String report = params.getParam("report");
		String orgReport = params.getParam("orgReport");
		String writeperson = params.getParam("writeperson");
		String phone = params.getParam("phone");
		String respperson = params.getParam("respperson");
		responser.setRtString(parseJSON(service.updateDanger(id,unit, writetm, name, location, description, tm, pic,orgPic,
				qxState, xqImprove, problem, report,orgReport, writeperson, phone, respperson)));
		return responser;
	}
	public Responser delDanger(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delDanger(id)));
		return responser;
	}
	
	public Responser IsFileExist(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.IsFileExist(params.getParam("filepath"))));
		return responser;
	}
	
	public Responser QueryFXJC() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryFXJC())); 
		return responser;
	}
	public Responser QuerySpecJC() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QuerySpecJC())); 
		return responser;
	}
	public Responser QuerySubQK() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QuerySubQK())); 
		return responser;
	}
	
	
	public Responser addFXJC(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String tm = params.getParam("tm");
		String name = params.getParam("name");
		String hdtdSitu = params.getParam("hdtdSitu");
		String htgkSitu = params.getParam("htgkSitu");
		String fcssSitu = params.getParam("fcssSitu");
		String sswzSitu = params.getParam("sswzSitu");
		String jcPerson = params.getParam("jcPerson");
		String jlPerson = params.getParam("jlPerson");
		String memo = params.getParam("memo");
		String regionid = params.getParam("regionid");
		
		responser.setRtString(parseJSON(service.addFXJC( tm, name, hdtdSitu, htgkSitu, fcssSitu, sswzSitu, jcPerson, jlPerson, memo, regionid)));
		return responser;
	}
	
	public Responser addSpeCheck(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String htnm = params.getParam("htnm");
		String jctm = params.getParam("jctm");
		String wt1 = params.getParam("wt1");
		String wt2 = params.getParam("wt2");
		String wt3 = params.getParam("wt3");
		String wt4 = params.getParam("wt4");String wt9 = params.getParam("wt9");String wt14 = params.getParam("wt14");
		String wt5 = params.getParam("wt5");String wt10 = params.getParam("wt10");String wt15 = params.getParam("wt15");
		String wt6 = params.getParam("wt6");String wt11 = params.getParam("wt11");String wt16 = params.getParam("wt16");
		String wt7 = params.getParam("wt7");String wt12= params.getParam("wt12");String wt17 = params.getParam("wt17");
		String wt8 = params.getParam("wt8");String wt13 = params.getParam("wt13");String wt18 = params.getParam("wt18");
		
		String jl1 = params.getParam("jl1");
		String jl2 = params.getParam("jl2");
		String jl3 = params.getParam("jl3");
		String jl4 = params.getParam("jl4");String jl9 = params.getParam("jl9");String jl14 = params.getParam("jl14");
		String jl5 = params.getParam("jl5");String jl10 = params.getParam("jl10");String jl15 = params.getParam("jl15");
		String jl6 = params.getParam("jl6");String jl11 = params.getParam("jl11");String jl16 = params.getParam("jl16");
		String jl7 = params.getParam("jl7");String jl12= params.getParam("jl12");String jl17 = params.getParam("jl17");
		String jl8 = params.getParam("jl8");String jl13 = params.getParam("jl13");String jl18 = params.getParam("jl18");
		
		String checkPerson = params.getParam("checkPerson");
		String chargePerson = params.getParam("chargePerson");
		
		responser.setRtString(parseJSON(service.addSpeCheck( htnm, jctm, wt1, wt2, wt3, wt4, wt5, wt6,wt7, wt8, wt9, wt10, wt11, wt12,
				 wt13, wt14, wt15, wt16, wt17, wt18,jl1, jl2, jl3, jl4, jl5, jl6,jl7, jl8, jl9, jl10, jl11, jl12,
				 jl13, jl14, jl15, jl16, jl17, jl18,checkPerson, chargePerson)));
		return responser;
	}
	
	public Responser addSubdangerQK(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String htnm = params.getParam("htnm");
		String subDangerNM = params.getParam("subDangerNM");
		String problem = params.getParam("problem");
		String suggestion = params.getParam("suggestion");
		String pics = params.getParam("pics");
		
		responser.setRtString(parseJSON(service.addSubdangerQK( htnm,subDangerNM,problem,suggestion,pics)));
		return responser;
	}
	public Responser delSpecJC(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delSpecJC(id)));
		return responser;
	}
	public Responser delSubQK(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delSubQK(id)));
		return responser;
	}
	
	public Responser delFXJC(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delFXJC(id)));
		return responser;
	}
	
	public Responser delSame(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String tm = params.getParam("repTM");
		String regionid = params.getParam("regionid");
		String repType = params.getParam("repType");
		responser.setRtString(parseJSON(service.delSame(tm,regionid,repType)));
		return responser;
	}
	public Responser delSame1(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String tm = params.getParam("repTM");
		String regionid = params.getParam("regionid");
		String repType = params.getParam("repType");
		responser.setRtString(parseJSON(service.delSame1(tm,regionid,repType)));
		return responser;
	}
	public Responser addQRCode(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String name = params.getParam("name");
		String location = params.getParam("location");
		String type = params.getParam("type");
		String html = params.getParam("html");
		String regionid = params.getParam("regionid");
		responser.setRtString(parseJSON(service.addQRCode(name,location,type,html,regionid)));
		return responser;
	}
	public Responser QueryQRCode() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryQRCode())); 
		return responser;
	}
	public Responser delQRCode(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delQRCode(id)));
		return responser;
	}
	public Responser QueryTZbyID() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.QueryTZbyID(id))); 
		return responser;
	}
	public Responser updateQRCode(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		String name = params.getParam("name");
		String location = params.getParam("location");
		String type = params.getParam("type");
		String html = params.getParam("html");
		String regionid = params.getParam("regionid");
		responser.setRtString(parseJSON(service.updateQRCode(id,name,location,type,html,regionid)));
		return responser;
	}
	
	public Responser QueryWarnTide() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryWarnTide())); 
		return responser;
	}
	
	public Responser QueryDoc() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);  
		String type = params.getParam("type");
		responser.setRtString(parseJSON(service.QueryDoc(type))); 
		return responser;
	}
	
	public Responser delSingleRep(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delSingleRep(id)));
		return responser;
	}
	
	public Responser delSingleRep4WXBB(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delSingleRep4WXBB(id)));
		return responser;
	}
	
	public Responser delSingleRep4WXYH(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delSingleRep4WXYH(id)));
		return responser;
	}
	
	public Responser addYHXCJL(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String bh = params.getParam("bh");
		String xctm = params.getParam("xctm");
		String xcdd = params.getParam("xcdd");
		String xcry = params.getParam("xcry");
		String xcqk = params.getParam("xcqk");
		String clyj = params.getParam("clyj");
		String cljg = params.getParam("cljg");
		String hzr = params.getParam("hzr");
		String ksfzr = params.getParam("ksfzr");
		String bz = params.getParam("bz");
		String regionid = params.getParam("regionid");
		
		responser.setRtString(parseJSON(service.addYHXCJL( bh,xctm,xcdd,xcry,xcqk,clyj,cljg,hzr,ksfzr,bz,regionid)));
		return responser;
	}
	
	public Responser QueryHisYhjc() {  
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		String regionid=params.getParam("regionid");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.QueryHisYhjc(regionid))); 
		return responser;
	}
	public Responser delHisYhxc(){
		FxglService service = (FxglService) ServiceFactory.getService("FxglService");
		responser.setRtType(JSON);
		String id = params.getParam("id");
		responser.setRtString(parseJSON(service.delHisYhxc(id)));
		return responser;
	}
	
	
	
}

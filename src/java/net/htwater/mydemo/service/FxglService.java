/**
 * 防汛管理服务接口
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;

//import org.apache.axis2.databinding.types.soapencoding.String;


public interface FxglService {
	static final String QGJ_SMP = "HOS"; 
	
	public List<Map<String, Object>> Queryszxx();
	public List<Map<String, Object>> Querybzxx();
	public List<Map<String, Object>> Querydmll();
	public List<Map<String, Object>> Query360();
	public List<Map<String, Object>> Queryspd();
	public List<Map<String, Object>> Queryqjd();
	/**
	 * 抢险队伍
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryqxdw();
	/**
	 * 物资
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return 
	 */
	public List<Map<String, Object>> Querywz();
	
	public List<Map<String, Object>> Queryfxya_lj();
	public List<Map<String, Object>> Queryfxya_yj(); 
	
	public List<Map<String, Object>> Queryfxwz2();
	public List<Map<String, Object>> Queryfhmb();
	
	//设备管理、检查、维修
	public List<Map<String, Object>> QueryEquip();
	public Map<String, Object> addEquipment(String equipNM,String equipLoc,String  manger,String telephone,String equipWell,String memo,String regionid);
	public Map<String, Object> updateEquipment(String id,String equipNM,String equipLoc,String  manger,String telephone,String equipWell,String memo);
	public Map<String, Object> delEquipment(String id);
	public Map<String, Object> addEquipCheck(String elementnm,String departmentnm,String responsiblenm,String sdt,String state,String description,String filename,String orgname,String regionid);
	public Map<String, Object> addEquipMaintain(String elementnm,String departmentnm,String responsiblenm,String sdt,String state,String description,String filename,String orgname,String regionid);
	public Map<String, Object> updateContactPersonorderid(String id,String type);
	public Map<String, Object> delEquipCheck(String id);
	public Map<String, Object> delEquipMaintain(String id);
	
	public List<Map<String, Object>> Queryjb();
	public List<Map<String, Object>> queryLatestWXRepTM(String regionid,String repType);
	public List<Map<String, Object>> QueryMonthReport(String TM,String repType);
	public List<Map<String, Object>> QueryMonthReport4Manage(String regionid,String repType);
	public List<Map<String, Object>> queryLatestYHRepTM(String regionid,String repType);
	public List<Map<String, Object>> QueryReport4WXYH(String regionid,String repType);
	public Map<String, Object> addMonthReport(String repNM,String orgname,String repType,String repTM,String regionid);
	public Map<String, Object> addObserveReport(String regionid,String year,String name,String repNM,String orgname,String filetype);
	public List<Map<String, Object>> QueryWeiXiuRep(String TM);
	public List<Map<String, Object>> QueryStaticRep(String TM);
	public List<Map<String, Object>> QueryWeixiuStaticRep(String TM,String repType);
	public Map<String, Object> addWeiXiuRep(String repNM,String orgname,String repType,String repTM,String regionid);
	public List<Map<String, Object>> getChecklist();
	public List<Map<String, Object>> getMaintainForm();
	//观测报告
	public List<Map<String, Object>> queryObserReport(String TM1,String TM2,String filetype);
	
	//隐患处置
	public List<Map<String, Object>> QuerySubDanger();
	public Map<String, Object> addSubDanger(String name,String location,String  description,String tm,String howFind,
			String pic,String dealMethod,String dealTm,String dealPic,String dealMemo,String regionid);
	public Map<String, Object> delSubDanger(String id);
	public Map<String, Object> updateSubDanger(String id,String name,String location,String  description,String tm,String howFind,
			String pic,String dealMethod,String dealTm,String dealPic,String dealMemo);
	//险情上报
	public List<Map<String, Object>> QueryDanger();
	public Map<String, Object> addDanger(String unit, String  writetm, String name,String location,String  description,String tm,
			String pic,String orgPic,String qxState,String xqImprove,String problem,String report,String orgReport,String writeperson,String phone,String respperson,String regionid);
	public Map<String, Object> updateDanger(String id,String unit, String  writetm, String name,String location,String  description,String tm,
			String pic,String orgPic,String qxState,String xqImprove,String problem,String report,String orgReport,String writeperson,String phone,String respperson);
	public Map<String, Object> delDanger(String id);
	
	public Map<String, Object> IsFileExist(String filepath);
	
	//防汛检查
	public List<Map<String, Object>> QueryFXJC();
	public List<Map<String, Object>> QuerySpecJC();
	public List<Map<String, Object>> QuerySubQK();
	public Map<String, Object> addFXJC(String tm, String  name, String hdtdSitu,String htgkSitu,String fcssSitu,String sswzSitu,
			String jcPerson,String jlPerson,String memo,String regionid);
	public Map<String, Object> addSpeCheck(String htnm,String jctm,
			  String wt1,String wt2,String wt3,String wt4,String wt5,String wt6,
			  String wt7,String wt8,String wt9,String wt10,String wt11,String wt12,
			  String wt13,String wt14,String wt15,String wt16,String wt17,String wt18,
			  String jl1,String jl2,String jl3,String jl4,String jl5,String jl6,
			  String jl7,String jl8,String jl9,String jl10,String jl11,String jl12,
			  String jl13,String jl14,String jl15,String jl16,String jl17,String jl18,
			  String checkPerson,String chargePerson);
	public Map<String, Object> addSubdangerQK(String htnm,String subDangerNM,
			String problem,String suggestion,String pics);
	public Map<String, Object> delSpecJC(String id);
	public Map<String, Object> delSubQK(String id);
	
	
	public Map<String, Object> delFXJC(String id);
	
	public Map<String, Object> delSame(String tm,String regionid,String repType);
	public Map<String, Object> delSame1(String tm,String regionid,String repType);
	public Map<String, Object> addQRCode(String name,String location,String type,String html,String regionid);
	public List<Map<String, Object>> QueryQRCode();
	public Map<String, Object> delQRCode(String id);
	//根据ID查找二维码页面
	public List<Map<String, Object>> QueryTZbyID(String id);
	public Map<String, Object> updateQRCode(String id,String name,String location,String type,String html,String regionid);
	//	12段工程的警戒潮位
	public List<Map<String, Object>> QueryWarnTide();
    //档案检索
	public List<Map<String, Object>> QueryDoc(String type);
	
	public Map<String, Object> delSingleRep(String id);
	public Map<String, Object> delSingleRep4WXBB(String id);
	public Map<String, Object> delSingleRep4WXYH(String id);
	public Map<String, Object> addYHXCJL(String bh, String  xctm, String xcdd,String xcry,String xcqk,String clyj,
			String cljg,String hzr,String ksfzr,String bz,String regionid);
	
	public List<Map<String, Object>> QueryHisYhjc(String regionid);
	public Map<String, Object> delHisYhxc(String id);

}

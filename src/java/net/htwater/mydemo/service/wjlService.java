/**
 * 防汛水情雨情统计和资料上报服务接口
 * @author wangjinlong
 * @Date 2015-10-13
 * @since v 1.0
 */

package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;




public interface wjlService {
	static final String HT_NBSQDB = "ht_nbsqdb"; 
	static final String DB_ZHSL = "qgj_smp"; 
	static final String DB_gq = "GQ"; 
	static final String DB_SHANHONG = "qgj_smp";//
	static final String DB_jwjgq = "JWJGQ";
	static final String DB_zhcggps = "zhcggps";
	
	public List<Map<String, Object>> QueryYqInfo();
	public List<Map<String, Object>> QueryRainInfo(String TM1,String TM2,String queryType);
	
	public List<Map<String, Object>> QuerySqInfo();
	public List<Map<String, Object>> QuerySwInfo(String TM1,String TM2,String queryType);
	
	
	public List<Map<String, Object>> AdminQueryFxry(String selectyr);
	public List<Map<String, Object>> CommonQueryFxry(String selectyr,String unit);
	public List<Map<String, Object>> AdminQueryFxwz(String selectyr);
	public List<Map<String, Object>> CommonQueryFxwz(String selectyr,String unit);
	public List<Map<String, Object>> AdminQueryFxwzTotal(String selectyr);
	public List<Map<String, Object>> CommonQueryFxwzTotal(String selectyr,String unit);
	public List<Map<String, Object>> AdminqueryDwStaInfo(String selectyr);
	public List<Map<String, Object>> CommonqueryDwStaInfo(String selectyr,String unit);
	public List<Map<String, Object>> AdminqueryPlStaInfo(String selectyr);
	public List<Map<String, Object>> CommonqueryPlStaInfo(String selectyr,String unit);
	public List<Map<String, Object>> AdminQueryZbb(String selectyr,String typhoon);
	public List<Map<String, Object>> CommonQueryZbb(String selectyr,String unit,String typhoon);
//	public List<Map<String, Object>> QueryZhibanbiao();
	public List<Map<String, Object>> AdminQueryBsfxInfo(String selectyr,String typhoon);
	public List<Map<String, Object>> CommonQueryBsfxInfo(String selectyr,String unit,String typhoon);
//	public List<Map<String, Object>> QueryBsfxInfo();
	public List<Map<String, Object>> AdminQueryNlSta(String selectyr,String typhoon);
	public List<Map<String, Object>> CommonQueryNlSta(String selectyr,String unit,String typhoon);
//	public List<Map<String, Object>> queryDwStaInfo();
//	public List<Map<String, Object>> queryPlStaInfo();
	//汛后报告
	public List<Map<String, Object>> AdminQueryXhReport(String selectyr);
	public List<Map<String, Object>> CommonQueryXhReport(String selectyr,String unit);
//	public List<Map<String, Object>> queryXhReport();
	
	
	//汛前服务
	public Map<String, Object> addContactPerson(String uploadTime,String unit, String username,String post,
			String officephone, String telephone, String demo,String isUpload,String isCheck);
	public Map<String, Object> updateContactPerson(String id,String uploadTime,String unit, String username,
			String post,String officephone, String telephone, String demo);
	public Map<String, Object> delContactPerson(String id);
	
	public Map<String, Object> addFXWZ(String time,String unit,String qx,String ckmc, String ckwz,String qpc,
			String ydbc, String psb, String zl,String yscl,String bl,String dl,
			String yjd,String aqm,String yy,String sd,String jsqc,String ssl,String ts,
			String st,String kz,String sk,String yx,String X,String Y,String isUpload,String isCheck);
	public Map<String, Object> updateFXWZ(String id,String time,String unit,String qx,String ckmc, String ckwz,String qpc,
			String ydbc, String psb, String zl,String yscl,String bl,String dl,
			String yjd,String aqm,String yy,String sd,String jsqc,String ssl,String ts,
			String st,String kz,String sk,String yx,String X,String Y);
	public Map<String, Object> delFXWZ(String id);
	
	public Map<String, Object> addDwInfo(String time,String unit,String aera,String teanName, String count,String place,
			String contacter, String phone,String X,String Y,String isUpload,String isCheck);
	public Map<String, Object> updateDwInfo(String id,String time,String unit,String aera,String teanName, String count,String place,
			String contacter, String phone,String X,String Y);
	public Map<String, Object> delDwInfo(String id);
	
	public Map<String, Object> addPlCapStaInfo(String time,String unit,String aera,String totalPlCap, String qpcCnt,String qpcCap,
			String ydbcCnt, String ydbeCap, String psbcCnt, String psbcCap,String memo,String isUpload,String isCheck);
	public Map<String, Object> updatePlCapStaInfo(String id,String time,String unit,String aera,String totalPlCap, String qpcCnt,String qpcCap,
			String ydbcCnt, String ydbeCap, String psbcCnt, String psbcCap,String memo);
	public Map<String, Object> delPlCapStaInfo(String id);
	//汛期服务
	public Map<String, Object> addZbPerson(String unit,String zbtime,String time,String leader, String leaderPhone,String zbPerson,
			String zbPersonPhone, String zbPhone, String fax,String typhoon,String isUpload,String isCheck);
	public Map<String, Object> updateZbPerson(String id,String unit,String time,String zbtime,String leader, String leaderPhone,String zbPerson,
			String zbPersonPhone, String zbPhone, String fax);
	public Map<String, Object> delZbPerson(String id);
	
	public Map<String, Object> addBsfxInfo(String time,String unit,String aera, String bqry,String cdry,String bqcl, String cdcl, String jsdd, String zyry,
			String smdf,String smql, String ldsh,String ldxf,String qlcz, String yjstgd, String slrx,
			String qxdlss,String hwggdt, String hwggql,String ryss,String rysw, String fwdt, String pcjjxq,
			String other,String xqdeal,String typhoon,String isUpload,String isCheck);
	public Map<String, Object> updateBsfxInfo(String id,String time,String unit,String aera, String bqry,String cdry,String bqcl, String cdcl, String jsdd, String zyry,
			String smdf,String smql, String ldsh,String ldxf,String qlcz, String yjstgd, String slrx,String qxdlss,String hwggdt, 
			String hwggql,String ryss,String rysw, String fwdt, String pcjjxq,String other,String xqdeal);
	public Map<String, Object> delBsfxInfo(String id);
	
	public Map<String, Object> addNlSta(String time,String unit,String jsdType, String jsdLoc,String waterDep,
			String jsAera, String jsReason,String dealMethod, String respUnit,String typhoon,String isUpload,String isCheck);
	public Map<String, Object> updateNlSta(String id,String time,String unit,String jsdType, String jsdLoc,String waterDep,
			String jsAera, String jsReason,String dealMethod, String respUnit);
	public Map<String, Object> delNlSta(String id);

	public Map<String, Object> addXhReport(String title,String unit,String time, String filepath,String isUpload,String isCheck);
	public Map<String, Object> delXhReport(String id);
	
	
	public Map<String, Object> updateRyUploadState(String id,String isUpload);
	public Map<String, Object> updateRyCheckState(String id,String isCheck);
	public Map<String, Object> updateWzUploadState(String id,String isUpload);
	public Map<String, Object> updateWzCheckState(String id,String isCheck);
	public Map<String, Object> updateDwUploadState(String id,String isUpload);
	public Map<String, Object> updateDwCheckState(String id,String isCheck);
	public Map<String, Object> updatePlStaUploadState(String id,String isUpload);
	public Map<String, Object> updatePlStaCheckState(String id,String isCheck);
	
	public Map<String, Object> updateZbUploadState(String id,String isUpload);
	public Map<String, Object> updateZbCheckState(String id,String isCheck);
	public Map<String, Object> updateBsfxUploadState(String id,String isUpload);
	public Map<String, Object> updateBsfxCheckState(String id,String isCheck);
	public Map<String, Object> updateNlStaUploadState(String id,String isUpload);
	public Map<String, Object> updateNlStaCheckState(String id,String isCheck);
	public Map<String, Object> updateRepUploadState(String id,String isUpload);
	public Map<String, Object> updateRepCheckState(String id,String isCheck);
	
	public List<Map<String, Object>> QueryTyphoon(String selectyr);
	
	//退回理由入库
	public Map<String, Object> updateRyRjtRsn2Db(String id,String rjtReason);
	public Map<String, Object> updateZbRjtRsn2Db(String id,String rjtReason);
	public Map<String, Object> updateBsfxRjtRsn2Db(String id,String rjtReason);
	public Map<String, Object> updateNlStaRjtRsn2Db(String id,String rjtReason);
	public Map<String, Object> updateRepRjtRsn2Db(String id,String rjtReason);
	public Map<String, Object> updateDwRjtRsn2Db(String id,String rjtReason);
	public Map<String, Object> updateWzRjtRsn2Db(String id,String rjtReason);
	public Map<String, Object> updatePlStaRjtRsn2Db(String id,String rjtReason);
	
	public List<Map<String, Object>> queryGPSPos(String tm1,String tm2,String carNum);
	
	public List<Map<String, Object>> queryLqInfo();
	
	public List<Map<String, Object>> queryLqUser();
	
	public Map<String, Object> updateLqxcPic(String pictm,String picuser, String title,
			String picname,String memo, String tel);
	
}

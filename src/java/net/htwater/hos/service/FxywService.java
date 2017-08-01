/**
 * fxyw  防汛业务数据服务
 * @author fhj
 * 2015-02-10
 */
package net.htwater.hos.service;

import java.util.List;
import java.util.Map;

import com.sun.xml.internal.ws.message.StringHeader;

public interface FxywService {
	static final String DB_HOS="HOS";
	static final String DB_NBSQDB="HT_NBSQDB";
	static final String DB_SLPC="SLPC";
	static final String DB_USERS="USERS";
	public List<Map<String, Object>> queryFORyearmonth(String year ,String month);
	//获取全部值班人员
	public List<Map<String, Object>> queryFORmember();
	//更新或插入值班信息
	public boolean updateFORduty( Map<String, String> map);
	//获取指定人员id的信息
	public List<Map<String, Object>> queryFORperson(String id);
	//获取抢险队伍数据
	public List<Map<String, Object>> queryFORteam(String region);
	//获取防汛物资
	public List<Map<String, Object>> queryFORmaterial(String region);
	//删除某日值班安排
	public boolean deteteFORzbap( String id );
	//发送短息
	void sendmessage(String PhoneNum, String Message);
	//获取某一天的安排
	public Map<String, Object>queryFORzbapBYid(String id);
	//获取通讯录树结构
	public List<Map<String, Object>> query_for_address();
	//编辑模式下获取通讯录
	public List<Map<String, Object>> query_for_address_edit(String region);	
	//获取某个单位通讯录的信息
	public List<Map<String, Object>> queryFORaddmes(String id);
	//获取县市区
	public List<Map<String, Object>> queryFORcity(String region,String type);
	//防汛责任人表格类型
	public List<Map<String, Object>> queryFORhead();
	//通知模版列表
	public List<Map<String, Object>> queryFORnoticeTlist(String type);
	//单张模板信息
	public List<Map<String, Object>> queryFORnoticeT(String temp_id);
	//防汛通知模版插入
	public boolean insertFORnoticT(String title,String message,String html,String type);
	//插入新通知
	public boolean insertFORnotic_list(String title,String author,String html,String time,String type,String grade);
	//更新模版
	public boolean updateFORnoticeT(String temp_id,String temp_title,String temp_mes,String temp_content,String type);
	//防汛业务短信发送
	public int sendQUNmessage(String name, String phone,String content,String id);
	//获取快报当前是第几期
	public int queryFORquickNUM();
	//新增预排预泄-闸门
	//public boolean insertFORdrainageG(String ENNM,String start,String end,String num,String jobid);
	//新增预排预泄-水库
	public boolean insertFORdrainageR(String ENNM,String start,String end,String num,String jobid);
	//获取预排预泄数据
	public List<Map<String, Object>> queryFORdrainage(String type,String year,String region);
	//获取山洪快报
	public Map<String, Object>queryFORreportbyid(String id);
	//获取快报信息
	public List<Map<String, Object>> queryFORquickreport(String start,String end);
	//插入短信内容
	public boolean savemessage(String PhoneNum, String Message,String result);
	//删除预排预泄
	public boolean deleteFORdrainage(String id);
	public boolean deleteFORdrainagejob(String id);
	//修改抢险队伍
	public boolean updateFORteam(Map<String, String> data);
	//根据权限获取闸门
	public List<Map<String, Object>> getALLgateBYregion(String region);
	//水库
	public List<Map<String, Object>> getALLresBYregion(String region);
	//修改防汛物资
	public boolean updateFORmaterial(Map<String, String> data);
	//新增抢险队伍
	public boolean insertFORteam(Map<String, String> data);
	//新增防汛物资
	public boolean insertFORmaterial(Map<String, String> data);
	//获取通知列表
	public List<Map<String, Object>> queryFORNotice_List(String time1,String time2,String key);
	//获取具体通知列表的信息
	public List<Map<String, Object>> queryFORNotice(String id);
	//更新通知
	public boolean updateFORnotic(String title,String author,String html,String id);
	//更新扫描件
	public boolean updateFORfile(String orgname,String filename,String id,String count,String fnString);
	//获取历史短信内容
	public List<Map<String, Object>> queryFORmesBYlistid(String id);
	//删除扫描件
	public boolean deleteFORfilename(String id);
	//删除抢险队伍
	public boolean deleteFORteam(String id);
	//删除防汛物资
	public boolean deleteFORmaterial(String id);
	//删除模版信息
	public boolean deleteFORnoticeT(String temp_id);
	//新建快报
	public boolean insertFORquickreport(String report);
	//发送微信消息
	public boolean updateFORisweixin(String id,String first,String warnno,String warn_level,String warn_area,String time,String remark);
	//发送传真
	public boolean  sendQUNfax(String id,String name,String phone,String id_list);
	//删除防汛通知
	public boolean deleteFORnoticelist(String id);
	//获取xml
	public String queryFORXML(String id);
	//获取一点时间内的雨量数据
	//public Map<String, Object> queryFORrain(String start,String end);
	//获取转移人员的数据
	public List<Map<String, Object>> getFORtransfer(String year,String type,String region);
	//更新传真结果
	public boolean updateFAXresult(String result,String id);
	//新增人口转移
	public boolean insertFORtransfer(String year);
	//获取发送状态信息
	public String queryFORfaxresult(String id);
	//转移人口阅读
	public boolean updateFORtransferREAD(String id,String token);
	//人口转移阅读
	public boolean updateFORdrainageREAD(String id,String token);
	//获取防汛责任人信息
	public List<Map<String, Object>> queryFORheadmes();
	//更新防汛责任人
	public boolean updateFORhead(String id,String Name,String Unit,String Position,String OfficeTell,String Mobile,String ResType,String Remark,String ordby);
	//删除防汛责任人
	public boolean deleteFORhead(String id);
	//新增责任人
	public boolean addFORhead(String city,String type,String Name,String Unit,String Position,String OfficeTell,String Mobile,String ResType,String Remark,String ordby);
	//更新通讯录
	public boolean updateFORaddress(String type,String id,String command,String mem_name,String position,String office,String phone,String home_phone,String email,String _order,String virtual,String _function,String _function_,String _duty);
	//更新通讯录列表
	public boolean updateFORaddress_menu(String type,String id,String unit_name,String postcode,String address,String tel,String email,String fax,String zf_address,String zf_postcode,String zf_phone,String zf_fax,String other);
	//插入通讯录
	public boolean insertADDRESS(String id);
	//删除通讯录
	public boolean deleteADDRESS(String id);
	//预排预泄前台展示
	public List<Map<String, Object>> queryFORdrainageRead(String start,String end,String token);
	//预排预泄编辑
	//public boolean updateFORdrainage(String type,String id,String ENNM,String start,String end,String num);
	//新增防汛预案
	public boolean insertFORfxya(String title,String regionnm,String filename);
	//更新防汛预案
	public boolean updateFORpingjia(String city,String filename);
	//查询防汛预案
	public List<Map<String, Object>> queryFORfxya(String type,String region);
	//删除人口转移
	public boolean deleteFORtransfer(String id);
	//编辑转移人员信息
	public boolean updateFORtransfer(String id,String htw,String htn,String cxht,String wf,String cz,String sd,String sk,String qt,String hgbf,String zgbf);
	//更新文档名称
	public boolean deleteFORfxya(String id);
	//调查评价
	public List<Map<String, Object>> queryFORpingjia(String region,String type);
	//获取人口转移关于某一条台风的转移人口数据
	public List<Map<String, Object>> getFORtrandferbyid(String id);
	//更新台风消息
	public boolean updateFORtyphoon(String id,String year,String num,String name,String ordby);
	//删除台风消息
	public boolean deleteFORtyphoon(String id);
	//获取区县转移人口上报数据
	public List<Map<String, Object>> getFORper_transfer(String id,String region);
	//新建预排预泄任务
	public boolean insertFORdrainage(String year);
	//修改预排预泄任务
	public boolean updateFORdrainagejob(String id,String name,String year,String res,String gate,String start,String ordby);
	//获取所有预排预泄的闸门数据
	public List<Map<String, Object>> queryFORdrainageGate();
	//更新水闸所属流域
	public boolean updateFORdrainageG(String list);
	//更新水闸排往地
	public boolean updateFORdrainageG_f(String list);
	//获取某一条预排预泄的数据
	public List<Map<String, Object>> getFORdrainferbyid(String id);
	//获取这个时间点的值班人员
	public List<Map<String, Object>> queryFORduty();
	public List<Map<String, Object>> queryFORdutybyid(String id);
	//未处理日志
	public List<Map<String, Object>> queryFORlog();
	public List<Map<String, Object>> queryforlogbytime(String time1,String time2);
	//交接班
	//public boolean updateFORdutyin(String id,String type,String password,String man_name,String man_id);
	//交接班退出
	//public boolean updateFORdutyout(String id,String password,String man_name,String man_id);
	//提交日志
	public boolean updateFORlog(String type,String shijian,String radio_d,String radio_n,String yijian,String time);
	public boolean updateFORlogdo(String id);
	public boolean queryFORchange(String id);
	//public boolean updateFORdutychange(String id,String name,String password,String duty);
	//将通知公告同步到公众网
	public boolean updateFORpublic(String title,String id,String isCommend,String newsType,String also,String grade,String content);
	//绑定文件
	public boolean updateListFax(String id,String fax_name);
	//获取历史公众网发布消息
	public List<Map<String, Object>> queryFORpubliclist(String id);
	/**
	 * 获取避灾场所
	 * @return
	 */
	public List<Map<String,Object>> getBizai();
	
	/**
	 * 发布应急响应
	 * @return
	 */
	public Map<String, Object> publishYJXY(String TM,String GRADE,String TITLE,String Source,String CONTENT);
	
	/**
	 * 获取当前的应急响应状态
	 * @return
	 */
	public Map<String,Object> getCurrentResponse();
}

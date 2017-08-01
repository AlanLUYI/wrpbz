/**
 * fxyw  防汛业务
 * @author fhj
 * 2015-03-19
 */
package net.htwater.hos.action;

import java.util.List;
import java.util.Map;

import net.htwater.hos.service.FxywService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.PinyinUtil;

public class FxywAction extends DoAction {
	//按照年，月获取值班信息
	public Responser queryFORyearmonth() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORyearmonth(
				params.getParam("year"),
				params.getParam("month")
		)));
		return responser;
	}
	//获取全部值班人员
	public Responser queryFORmember() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORmember(
		
		)));
		return responser;
	}
	//更新或插入值班信息
	public Responser updateFORduty() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORduty(
				params.getParams()
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//获取指定人员id的信息
	public Responser queryFORperson() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORperson(
		       params.getParam("id")
		)));
		return responser;
	}
	//获取抢险队伍
	public Responser queryFORteam() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORteam(
				session.getAttribute("region").toString()
		)));
		return responser;
	}
	//获取抢险队伍
	public Responser queryFORmaterial() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORmaterial(
				session.getAttribute("region").toString()
		)));
		return responser;
	}
	//根据权限获取闸门
	public Responser getALLgateBYregion() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getALLgateBYregion(
				session.getAttribute("region").toString()
		)));
		return responser;
	}
	//水库
	public Responser getALLresBYregion() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getALLresBYregion(
				session.getAttribute("region").toString()
		)));
		return responser;
	}
	//获取预排预泄数据
	public Responser queryFORdrainage() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORdrainage(
				params.getParam("type"),
				params.getParam("year"),
				session.getAttribute("region").toString()
		)));
		return responser;
	}
	public Responser getFORdrainferbyid() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getFORdrainferbyid(
				params.getParam("id")
				
		)));
		return responser;
	}
	public Responser queryFORpubliclist() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORpubliclist(
				params.getParam("id")
				
		)));
		return responser;
	}
	//获取值班情况
	public Responser queryFORduty() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORduty(
							
		)));
		return responser;
	}
	//删除某日值班安排
	public Responser deteteFORzbap() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deteteFORzbap(
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	public Responser updateFORdrainagejob() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORdrainagejob(
				params.getParam("id"),
				params.getParam("name"),
				params.getParam("year"),
				params.getParam("res"),
				params.getParam("gate"),
				params.getParam("start"),
				params.getParam("ordby")
				
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}

	//删除预排预泄
	public Responser deleteFORdrainage() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deleteFORdrainage(
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	public Responser deleteFORdrainagejob() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deleteFORdrainagejob(
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//新增预排预泄--闸门
	/*public Responser insertFORdrainageG() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORdrainageG(
				params.getParam("ENNM"),
				params.getParam("start"),
				params.getParam("end"),
				params.getParam("num"),
				params.getParam("jobid")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}*/
	//编辑预排预泄
	/*public Responser updateFORdrainage() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORdrainage(
				params.getParam("type"),
				params.getParam("id"),
				params.getParam("ENNM"),
				params.getParam("start"),
				params.getParam("end"),
				params.getParam("num")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}*/
	//新增预排预泄--水库
	public Responser insertFORdrainageR() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORdrainageR(
				params.getParam("ENNM"),
				params.getParam("start"),
				params.getParam("end"),
				params.getParam("num"),
				params.getParam("jobid")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//新建快报
	public Responser insertFORquickreport() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORquickreport(
				params.getParam("report")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//新建上报任务
	public Responser insertFORdrainage() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORdrainage(
				params.getParam("year")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//删除防汛通知
	public Responser deleteFORnoticelist() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deleteFORnoticelist(
				params.getParam("id")
		);
		String str1="{\"message\":\"提交成功\"}";
		String str2="{\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//通讯录获取树结构
	public Responser query_for_address() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.query_for_address(

		)));
		return responser;
	}
	/* public Responser queryFORrain() {
		 FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		   responser.setRtType(JSON);
		   responser.setRtString(parseJSON(service.queryFORrain(
				   params.getParam("start"),
				   params.getParam("end")
			)));	   
		   return responser;
	   }*/
	public Responser query_for_address_edit() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.query_for_address_edit(
				session.getAttribute("region").toString()
		)));
		return responser;
	}
	//获取转移人员数据
	public Responser getFORtransfer() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getFORtransfer(
				params.getParam("year"),
				params.getParam("type"),
				session.getAttribute("region").toString()
		)));
		return responser;
	}
	//预排预泄前台展示
	public Responser queryFORdrainageRead() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORdrainageRead(
				params.getParam("start"),
				params.getParam("end"),
				session.getAttribute("token").toString()
		)));
		return responser;
	}
	public Responser getFORtrandferbyid() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getFORtrandferbyid(
				params.getParam("id")
		)));
		return responser;
	}
	//通讯录信息
	public Responser queryFORaddmes() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORaddmes(
		       params.getParam("id")
		)));
		return responser;
	}
	//调查评价
	public Responser queryFORpingjia() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORpingjia(
		       session.getAttribute("region").toString(),
		       params.getParam("type")
		)));
		return responser;
	}
	
	//防汛责任人
	public Responser queryFORcity() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORcity(
				session.getAttribute("region").toString(),
				params.getParam("type")
		)));
		return responser;
	}
	//防汛责任人表格类型
	public Responser queryFORhead() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORhead(	       
		)));
		return responser;
	}
	//通知模版列表
	public Responser queryFORnoticeTlist() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORnoticeTlist(	
				params.getParam("type")
		)));
		return responser;
	}
	//单张模版信息
	public Responser queryFORnoticeT() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORnoticeT(	
				params.getParam("temp_id")
		)));
		return responser;
	}
	//查询防汛预案
	public Responser queryFORfxya() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORfxya(	
				params.getParam("type"),
				session.getAttribute("region").toString()
		)));
		return responser;
	}
	//防汛通知模版插入
	public Responser insertFORnoticT() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORnoticT(
				params.getParam("title"),
				params.getParam("message"),
				params.getParam("html"),
				params.getParam("type")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//编辑台风消息
	public Responser updateFORtyphoon() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORtyphoon(
				params.getParam("id"),
				params.getParam("year"),
				params.getParam("num"),
				params.getParam("name"),
				params.getParam("ordby")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//删除台风消息
	public Responser deleteFORtyphoon() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deleteFORtyphoon(
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//编辑转移人口
	public Responser updateFORtransferREAD() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORtransferREAD(
				params.getParam("id"),
				session.getAttribute("token").toString()
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//编辑转移人口
	public Responser updateFORdrainageREAD() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORdrainageREAD(
				params.getParam("id"),
				session.getAttribute("token").toString()
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//新增人口转移
	public Responser insertFORtransfer() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORtransfer(
				params.getParam("year")			
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//编辑人口转移
		public Responser updateFORtransfer() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.updateFORtransfer(					
					params.getParam("id"),
					params.getParam("htw"),
					params.getParam("htn"),
					params.getParam("cxht"),
					params.getParam("wf"),
					params.getParam("cz"),
					params.getParam("sd"),
					params.getParam("sk"),
					params.getParam("qt"),
					params.getParam("hgbf"),
					params.getParam("zgbf")
			);
			String str1="{\"ok\":true,\"message\":\"提交成功\"}";
			String str2="{\"ok\":false,\"message\":\"提交失败\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}
	//删除防汛通知
	public Responser deleteFORnoticeT() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deleteFORnoticeT(
				params.getParam("temp_id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//按照权限获取转移人口上报
	public Responser getFORper_transfer() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getFORper_transfer(	
				params.getParam("id"),
				session.getAttribute("region").toString()
		)));	
		return responser;
	}
	//删除防汛预案
	public Responser deleteFORfxya() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deleteFORfxya(
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//删除人口转移
	public Responser deleteFORtransfer() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deleteFORtransfer(
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//插入新通知
	public Responser insertFORnotic_list() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORnotic_list(
				params.getParam("title"),
				params.getParam("author"),
				params.getParam("html"),
				params.getParam("time"),
				params.getParam("type"),
				params.getParam("grade")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//新增防汛预案
	public Responser insertFORfxya() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORfxya(
				params.getParam("title"),
				params.getParam("regionnm"),
				params.getParam("filename")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//更新防汛预案
	public Responser updateFORpingjia() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORpingjia(
				params.getParam("city"),
				params.getParam("filename")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//更新模版
	public Responser updateFORnoticeT() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORnoticeT(
				params.getParam("temp_id"),
				params.getParam("temp_title"),
				params.getParam("temp_mes"),
				params.getParam("temp_content"),
				params.getParam("type")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//更新通知
	public Responser updateFORnotic() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORnotic(
				params.getParam("title"),
				params.getParam("author"),
				params.getParam("html"),
				params.getParam("id")
		);
		String str1="提交成功";
		String str2="提交失败";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//更新扫描件
	public Responser updateFORfile() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORfile(
				params.getParam("orgname"),
				params.getParam("filename"),
				params.getParam("id"),
				params.getParam("count"),
				params.getParam("fnString")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}	
	//发送传真
	public Responser sendQUNfax() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.sendQUNfax(
				params.getParam("id"),
				params.getParam("name"),
				params.getParam("phone"),
				params.getParam("id_list")
		);	
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}	
	//群发微信
	public Responser updateFORisweixin() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORisweixin(
				params.getParam("id"),
				params.getParam("first"),
				params.getParam("warnno"),
				params.getParam("warn_level"),
				params.getParam("warn_area"),
				params.getParam("time"),
				params.getParam("remark")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}	
	//更新防汛责任人
	public Responser updateFORhead() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORhead(
				params.getParam("id"),
				params.getParam("Name"),
				params.getParam("Unit"),
				params.getParam("Position"),
				params.getParam("OfficeTell"),
				params.getParam("Mobile"),
				params.getParam("ResType"),
				params.getParam("Remark"),
				params.getParam("ordby")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//更新通讯录
	public Responser updateFORaddress() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORaddress(
				params.getParam("type"),
				params.getParam("id"),
				params.getParam("command"),
				params.getParam("mem_name"),
				params.getParam("position"),
				params.getParam("office"),
				params.getParam("phone"),
				params.getParam("home_phone"),
				params.getParam("email"),
				params.getParam("_order"),
				params.getParam("virtual"),
				params.getParam("_function"),
				params.getParam("_function_"),
				params.getParam("_duty")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	public Responser insertADDRESS() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertADDRESS(
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//删除通讯录
	public Responser deleteADDRESS() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deleteADDRESS(
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//修改抢险队伍
	public Responser updateFORteam() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORteam(
				params.getParams()
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//修改防汛物资
	public Responser updateFORmaterial() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORmaterial(
				params.getParams()
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//新增抢险队伍
	public Responser insertFORteam() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORteam(
				params.getParams()
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//新增防汛物资
	public Responser insertFORmaterial() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.insertFORmaterial(
				params.getParams()
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//更新通讯录目录
	public Responser updateFORaddress_menu() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORaddress_menu(
				params.getParam("type"),
				params.getParam("id"),
				params.getParam("unit_name"),
				params.getParam("postcode"),
				params.getParam("address"),
				params.getParam("tel"),
				params.getParam("email"),
				params.getParam("fax"),
				params.getParam("zf_address"),
				params.getParam("zf_postcode"),
				params.getParam("zf_phone"),
				params.getParam("zf_fax"),
				params.getParam("other")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//新增责任人
	public Responser addFORhead() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.addFORhead(
				params.getParam("city"),
				params.getParam("type"),
				params.getParam("Name"),
				params.getParam("Unit"),
				params.getParam("Position"),
				params.getParam("OfficeTell"),
				params.getParam("Mobile"),
				params.getParam("ResType"),
				params.getParam("Remark"),
				params.getParam("ordby")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}	
	
	//删除防汛责任人 deleteFORhead
	public Responser deleteFORhead() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.deleteFORhead(
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	//更新服务器
	public Responser updateFAXresult() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFAXresult(
				params.getParam("result"),
				params.getParam("id")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	public Responser updateFORdrainageG() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORdrainageG(
				params.getParam("list")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	public Responser updateFORdrainageG_f() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		Boolean result = service.updateFORdrainageG_f(
				params.getParam("list")
		);
		String str1="{\"ok\":true,\"message\":\"提交成功\"}";
		String str2="{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result?str1:str2);
		return responser;
	}
	public Responser sendQUNmessage() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		int result = service.sendQUNmessage(
				params.getParam("name"),
				params.getParam("phone"),
				params.getParam("content"),
				params.getParam("id")
		);
		if(result == 1){
			responser.setRtString("{\"ok\":true,\"message\":\"短信发送成功\"}");	
		}else if(result ==2){
			responser.setRtString("{\"ok\":true,\"message\":\"短信发送成功，数据库插入失败\"}");	
		}else{
			responser.setRtString("{\"ok\":true,\"message\":\"短信发送失败\"}");	
		}
		return responser;
	}
	public Responser queryFORquickNUM() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		int result = service.queryFORquickNUM(
				
		);
		responser.setRtString(String.valueOf(result));	
		return responser;
	}
	//获取通知列表
	public Responser queryFORNotice_List() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORNotice_List(	
				params.getParam("time1"),
				params.getParam("time2"),
				params.getParam("key")
		)));
		return responser;
	}
	//获取具体通知列表的信息
	public Responser queryFORNotice() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORNotice(	
				params.getParam("id")
		)));
		return responser;
	}
	//获取快报信息
	public Responser queryFORreportbyid() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORreportbyid(	
				params.getParam("id")
		)));
		return responser;
	}
	//获取快报信息
	public Responser queryFORquickreport() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORquickreport(	
				params.getParam("start"),
				params.getParam("end")
		)));
		return responser;
	}
	//根据id获取历史短信信息
	public Responser queryFORmesBYlistid() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORmesBYlistid(	
				params.getParam("id")
		)));
		return responser;
	}
	//传真状态信息
	public Responser queryFORfaxresult() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(service.queryFORfaxresult(	
				params.getParam("id")
		));
		return responser;
	}		
	//删除扫描件
	public Responser deleteFORfilename() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.deleteFORfilename(	
				params.getParam("id")
		)));
		return responser;
	}
	//删除抢险队伍
	public Responser deleteFORteam() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.deleteFORteam(	
				params.getParam("id")
		)));
		return responser;
	}
	//删除防汛物资
	public Responser deleteFORmaterial() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.deleteFORmaterial(	
				params.getParam("id")
		)));
		return responser;
	}
	//防汛责任人信息
	public Responser queryFORheadmes() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORheadmes(
				
		)));
		return responser;
	}
	public Responser queryFORdrainageGate() {
		FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORdrainageGate(
				
		)));
		return responser;
	}
	//获取xml
		public Responser queryFORXML() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			responser.setRtString(service.queryFORXML(	
					params.getParam("id")
			));
			return responser;
		}
		public Responser updateFORpublic() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.updateFORpublic(
					params.getParam("title"),
					params.getParam("id"),
					params.getParam("isCommend"),
					params.getParam("newsType"),
					params.getParam("also"),
					params.getParam("grade"),
					params.getParam("content")
			);
			String str1="{\"ok\":true,\"message\":\"提交成功\"}";
			String str2="{\"ok\":false,\"message\":\"提交失败\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}
		//绑定传真文件
		public Responser updateListFax() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			Boolean result = service.updateListFax(
					params.getParam("id"),
					params.getParam("tif_name")
			);
			String str1="{\"ok\":true,\"message\":\"绑定成功\"}";
			String str2="{\"ok\":false,\"message\":\"绑定失败\"}";
			responser.setRtString(result?str1:str2);
			return responser;
		}
		/**
		 * 获取避灾场所
		 * @return
		 */
		public Responser getBizai()
		{
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			List<Map<String,Object>> result = service.getBizai();
			for(Map<String,Object> m : result){
				m.put("_s", m.get("name").toString()+PinyinUtil.converterToPinYin(m.get("name").toString()));
			}
			responser.setRtType(JSON);
			responser.setRtString(parseJSON(result));
			return responser;
		} 
		
		/**
		 * 增加预警信息 
		 * TM时间，GRADE预警级别，TITLE标题，Source来源，CONTENT内容
		 * @Date 2014-06-17
		 * @since v 1.0
		 * @return
		 */
		public Responser publishYJXY() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			responser.setRtString(parseJSON(service.publishYJXY( 
					params.getParam("TM"),
					params.getParam("GRADE"),
					params.getParam("TITLE"),
					params.getParam("Source"),
					params.getParam("CONTENT") 
					)));
			return responser;
		}
		
		/**
		 * 获取当前应急响应状态
		 * @return
		 */
		public Responser getCurrentResponse() {
			FxywService service = (FxywService) ServiceFactory.getService("Fxyw");
			responser.setRtType(JSON);
			responser.setRtString(parseJSON(service.getCurrentResponse()));
			return responser;
		}
}

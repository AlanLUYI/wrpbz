/**
 * 水库巡查
 * @author fhj
 * 2015-03-19
 */
package net.htwater.mydemo.action;

import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.FxglService;
import net.htwater.mydemo.service.XunchaService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.PinyinUtil;

public class XunchaAction extends DoAction {
	//判断巡查账户是否已经存在
	public Responser queryFORUserid() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.queryFORUserid(params.getParam("userid"));
		String str1 = "{\"ok\":true,\"message\":\"帐号重复\"}";
		String str2 = "{\"ok\":false,\"message\":\"帐号唯一\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	//新增巡查账户
	public Responser insertFORXunchaUser() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.insertFORXunchaUser(
				params.getParam("Name"),
				params.getParam("UserID"),
				params.getParam("Password"),
				params.getParam("Type"),
				params.getParam("TypeValue"),
				params.getParam("Telephone")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	
	public Responser insertFORjmxc() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.insertFORjmxc(
				params.getParam("res_id"),
				params.getParam("mem_id"),
				params.getParam("creat_time"),
				params.getParam("limit_time")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	
	
	public Responser updateFORisread() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.updateFORisread(
				params.getParam("id"),
				params.getParam("type")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	
	//获取所有巡查人员账号信息
	public Responser queryFORALLmember() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORALLmember(
				
		)));
		return responser;
	}
	//获取单个巡查人员绑定的水库
	public Responser queryMemberRes() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryMemberRes(
				params.getParam("id")
		)));
		return responser;
	}
	
	public Responser queryFORUNOverTask() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORUNOverTask(
				params.getParam("TM1"),
				params.getParam("TM2")
		)));
		return responser;
	}
	
	public Responser updateForhead() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.updateForhead(
				params.getParam("mem_id"),
				params.getParam("file")
		)));
		return responser;
	}
	
	public Responser insertTaskPath() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.insertTaskPath(
				params.getParam("task_id"),
				params.getParam("mem_id"),
				params.getParam("lon"),
				params.getParam("lat")
		)));
		return responser;
	}
	
	public Responser insertFORenTask() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.insertFORenTask(
				params.getParam("res_id"),
				params.getParam("mem_id")
		)));
		return responser;
	}
	
	public Responser queryFORfileByid() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORfileByid(
				params.getParam("id")
		)));
		return responser;
	}
	public Responser queryFORTaskStat() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORTaskStat(
				params.getParam("TM1"),
				params.getParam("TM2")
		)));
		return responser;
	}
	public Responser queryWeiHuTaskStat() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryWeiHuTaskStat(
				params.getParam("TM")
		)));
		return responser;
	}
	
	public Responser addWxyhProgram(){
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		String task_id = params.getParam("task_id");
		String regionid = params.getParam("regionid");
		String task_name = params.getParam("task_name");
		String time = params.getParam("time");
		String money = params.getParam("money");
		String Name = params.getParam("Name");
		
		responser.setRtString(parseJSON(service.addWxyhProgram(task_id,regionid,task_name,time,money,Name)));
		return responser;
	}
	
	public Responser addWxyhFeeUse(){
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		String task_id = params.getParam("task_id");
		String regionid = params.getParam("regionid");
		String yr = params.getParam("yr");
		String mth = params.getParam("mth");
		String finished = params.getParam("finished");
		String payed = params.getParam("payed");
		
		responser.setRtString(parseJSON(service.addWxyhFeeUse(task_id,regionid,yr,mth,finished,payed)));
		return responser;
	}
	
	public Responser queryFORresultByid() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORresultByid(
				params.getParam("id")
		)));
		return responser;
	}
	
	public Responser queryFOROverTask() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFOROverTask(
				params.getParam("TM1"),
				params.getParam("TM2")
		)));
		return responser;
	}
	public Responser selectFORXunchaResultList() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.selectFORXunchaResultList(
				params.getParam("task_id"),
				params.getParam("PageNum"),
				params.getParam("type")
		)));
		return responser;
	}
	
	public Responser queryResList() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryResList(
				params.getParam("mem_id")
		)));
		return responser;
	}

	
	public Responser queryFORXunchaPath() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORXunchaPath(
				params.getParam("task_id")
		)));
		return responser;
	}
	public Responser queryFORAllRes() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORAllRes(
				
		)));
		return responser;
	}
	//巡查人员绑定水库
	public Responser addMemberRes() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.addMemberRes(
				params.getParam("id"),
				params.getParam("res")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	//巡查人员解除绑定水库
	public Responser deleteMemberRes() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.deleteMemberRes(
				params.getParam("id"),
				params.getParam("res")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	//获取全部水库
	public Responser queryFORALLres() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryFORALLres(
				
		)));
		return responser;
	}
	//重置密码
	public Responser refashFORsecret() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.refashFORsecret(
				params.getParam("id")
		);
		String str1 = "{\"ok\":true,\"message\":\"重置成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"重置失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	//修改账户
	public Responser updateFORmember() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.updateFORmember(
				params.getParam("id"),
				params.getParam("Name"),
				params.getParam("UserID"),
				params.getParam("Telephone")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败，账号已存在\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	//修改单个水库巡查频率
	public Responser updateFORLengthRes() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.updateFORLengthRes(
				params.getParam("id"),
				params.getParam("pinlv"),
				params.getParam("isOPEN")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	//删除账号
	public Responser deleteFORmember() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.deleteFORmember(
				params.getParam("id")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	//批量修改
	public Responser updateFORLengthP() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.updateFORLengthP(
				params.getParam("days"),
				params.getParam("sel")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	//巡查用户登录
	public Responser Login() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.Login(
				params.getParam("UserID"),
				params.getParam("Password")
		)));
		return responser;
	}
	//巡查用户是否登录
	public Responser isLogin() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.isLogin(
					params.getParam("mem_id")
		)));
		return responser;
	}
	public Responser updateTaskState() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.updateTaskState(
				params.getParam("task_id"),
					params.getParam("mem_id")
		)));
		return responser;
	}
	//巡查任务获取
	public Responser queryForTasklist() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryForTasklist(
				params.getParam("mem_id")
		)));
		return responser;
	}
	//获取巡查情况
	public Responser queryForCaselist() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryForCaselist(
				params.getParam("mem_id"),
				params.getParam("pid")
		)));
		return responser;
	}
	//文件提交后，将返回结果保存都数据库，图片，录音，录像类
	public Responser updateFORFile() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		Boolean result = service.updateFORFile(
				params.getParam("task_id"),
				params.getParam("file_name"),
				params.getParam("file_type")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	//获取巡查情况
	public Responser queryState() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryState(
				
		)));
		return responser;
	}
	
	//用户修改密码
	public Responser updateFORsecret() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.updateFORsecret(
				params.getParam("mem_id"),
				params.getParam("old_password"),
				params.getParam("new_password")
		)));
		return responser;
	}
	
	public Responser insertResult() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.insertResult(
				params.getParam("result"),
				params.getParam("mem_id")
		)));
		return responser;
	}
	
	public Responser insertLoginlog() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		/*responser.setRtString(parseJSON(service.insertLoginlog(
				params.getParam("mobile_id"),
				params.getParam("sys_version"),
				params.getParam("mobile_type"),
				params.getParam("mobile_brand"),
				params.getParam("mobile_system"),
				params.getParam("client_version"),
				params.getParam("client_version_int")
		)));*/
		Boolean result = service.insertLoginlog(
				params.getParam("mem_id"),
				params.getParam("mobile_id"),
				params.getParam("sys_version"),
				params.getParam("mobile_type"),
				params.getParam("mobile_brand"),
				params.getParam("mobile_system"),
				params.getParam("client_version"),
				params.getParam("client_version_int")
		);
		String str1 = "{\"ok\":true,\"message\":\"提交成功\"}";
		String str2 = "{\"ok\":false,\"message\":\"提交失败\"}";
		responser.setRtString(result ? str1 : str2);
		return responser;
	}
	
	public Responser queryChildResult() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryChildResult(
				params.getParam("task_id"),
				params.getParam("case_pid"),
				params.getParam("mem_id")
		)));
		return responser;
	}
	
	public Responser updateTaskResult() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.updateTaskResult(
				params.getParam("task_id"),
				params.getParam("mem_id"),
				params.getParam("place")
		)));
		return responser;
	}
	
	public Responser insertTaskFile() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.insertTaskFile(
				params.getParam("task_id"),
				params.getParam("file_name"),
				params.getParam("file_type"),
				params.getParam("case_pid")
		)));
		return responser;
	}
	
	public Responser queryChildFile() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryChildFile(
				params.getParam("task_id"),
				params.getParam("case_pid"),
				params.getParam("mem_id")
		)));
		return responser;
	}
	public Responser queryTaskMoney() {
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryTaskMoney(
				params.getParam("TM1"),
				params.getParam("TM2")
		)));
		return responser;
	}
	
	public Responser addDocs(){
		XunchaService service = (XunchaService) ServiceFactory.getService("Xuncha");
		responser.setRtType(JSON);
		String docNO = params.getParam("docNO");
		String docNM = params.getParam("docNM");
		String type = params.getParam("type");
		String docType = params.getParam("docType");
		String pages = params.getParam("pages");
		String bzTM = params.getParam("bzTM");
		String bzUnit = params.getParam("bzUnit");
		String deadline = params.getParam("deadline");
		String secLvl = params.getParam("secLvl");
		String refer = params.getParam("refer");
		
		responser.setRtString(parseJSON(service.addDocs(docNO, docNM, type,docType, pages,bzTM, bzUnit,deadline, secLvl, refer)));
		return responser;
	}
	
}

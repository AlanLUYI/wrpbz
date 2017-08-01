package net.htwater.mydemo.action;

import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import net.htwater.mydemo.service.UserUtil;

public class UserUtilAction extends DoAction {
	
	public Responser getUsers() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getUsers("3302001")));
		return responser;
	}
	public Responser getUsersContact() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getUsersContact("3302001")));
		return responser;
	}
	public Responser getUsersPostTree() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getUsersPostTree("3302001")));
		return responser;
	}
	public Responser getPeople() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getPeople("3302001")));
		return responser;
	}
	public Responser getUsersByDepartment() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getUsersByDepartment(
				"3302001",
				params.getParams().get("departmentcd")				
			)));
		return responser;
	}
	public Responser getUserByUserID() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getUserByUserID(
				params.getParams().get("userid"),
				"3302001"
			)));
		return responser;
	}
	
	public Responser getDepartment() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getDepartment("3302001")));
		return responser;
	}
	public Responser getXZDepartment() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getXZDepartment(
				"3302001"
			)));
		return responser;
	}
	public Responser getOtherDepartment() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getOtherDepartment(
				"3302001"
			)));
		return responser;
	}
	public Responser getDepartment2() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getDepartment2("3302001")));
		return responser;
	}
	public Responser getPostTree() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getPostTree(
				"3302001"
			)));
		return responser;
	}
	public Responser getXZPostTree() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getXZPostTree(
				"3302001"
			)));
		return responser;
	}
	public Responser getOtherPostTree() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getOtherPostTree(
				"3302001"
			)));
		return responser;
	}
	public Responser getPostBycd() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getPostBycd(
				params.getParams().get("postcd"),
				"3302001"
			)));
		return responser;
	}
	public Responser savepost() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.savepost(
				loginName,
				actualName,
				"3302001",
				params.getParams().get("postcd"),
				params.getParams().get("postname"),
				params.getParams().get("isadministration"),
				params.getParams().get("counts"),
				params.getParams().get("orderby"),
				params.getParams().get("duty"),
				params.getParams().get("departmentcd")
			)));
		return responser;
	}
	public Responser deletepost() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.deletepost(
				loginName,
				actualName,
				"3302001",
				params.getParams().get("postcd")
			)));
		return responser;
	}
	
	
	public Responser saveDDP() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.saveDDP(
				"3302001",
				params.getParams().get("userid"),
				params.getParams().get("username"),
				params.getParams().get("departmentcd"),
				params.getParams().get("postcd"),
				params.getParams().get("otherdepartmentcd"),
				params.getParams().get("otherdepartmentnm"),
				params.getParams().get("otherpostcd"),
				params.getParams().get("otherpostnm"),
				params.getParams().get("option")
			)));
		return responser;
	}
	public Responser deleteUser() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.deleteUser(
				loginName,
				actualName,
				"3302001",
				params.getParams().get("userid")
			)));
		return responser;
	}
	public Responser getunitleader() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getunitleader(
				"3302001"				
			)));
		return responser;
	}	
	
	public Responser getUnit() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getUnit(params.getParam("rescd"))));
		return responser;
	}
	public Responser saveUnit() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.saveUnit(
				loginName,
				actualName,
				params.getParams()
			)));
		return responser;
	}
	
	public Responser getKV() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getKV(
				params.getParams().get("k"),
				"3302001"
			)));
		return responser;
	}

	public Responser getuuid() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getuuid()));
		return responser;
	}
		
	public Responser getDep() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getDep(
				"3302001",
				params.getParams().get("departmentcd")
			)));
		return responser;
	}
	public Responser saveDep() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.saveDep(
				loginName,
				actualName,
				params.getParams()
			)));
		return responser;
	}
	public Responser deleteDep() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.deleteDep(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("departmentcd")
			)));
		return responser;
	}
	public Responser setleaderdep() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setleaderdep(
				loginName,
				actualName,
				params.getParams().get("departmentcd"),
				params.getParams().get("rescd")
			)));
		return responser;
	}
	public Responser getAppTypes() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getAppTypes(
			)));
		return responser;
	}
	public Responser getAppList() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getAppList(
			)));
		return responser;
	}
	
	public Responser getAppByPostcd() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getAppByPostcd(
				params.getParams().get("rescd"),
				params.getParams().get("postcd")
			)));
		return responser;
	}
	public Responser savePostApp() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.savePostApp(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("postcd"),
				params.getParams().get("tbids")
			)));
		return responser;
	}
	public Responser addPostApp() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.addPostApp(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("postcd"),
				params.getParams().get("tbids")
			)));
		return responser;
	}
	public Responser delPostApp() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.delPostApp(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("postcd"),
				params.getParams().get("tbids")
			)));
		return responser;
	}

	public Responser setloginname() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String currentName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setloginname(
				currentName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("userid"),
				params.getParams().get("loginname"),
				params.getParams().get("password")
			)));
		return responser;
	}

	public Responser getotherloginname() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getotherloginname(
				params.getParams().get("rescd"),
				params.getParams().get("userid")
			)));
		return responser;
	}
	public Responser setotherloginname() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String currentName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setotherloginname(
				currentName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("userid"),
				params.getParams().get("loginname")
			)));
		return responser;
	}

	public Responser getDeptUsers() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getDeptUsers(
				"3302001"
			)));
		return responser;
	}
	public Responser getPostUsers() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getPostUsers(
				"3302001"
			)));
		return responser;
	}
	public Responser getonguardUsers() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getonguardUsers(
				"3302001"
			)));
		return responser;
	}
	public Responser getallUsers() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getallUsers(
				"3302001"
			)));
		return responser;
	}
	public Responser setOnguard() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setOnguard(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("userid")
			)));
		return responser;
	}
	public Responser leaveOnguard() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.leaveOnguard(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("userid")
			)));
		return responser;
	}

	public Responser setuserdept() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setuserdept(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("userid"),
				params.getParams().get("departmentcd"),
				params.getParams().get("departmentnm")
			)));
		return responser;
	}
	public Responser removeuserdept() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.removeuserdept(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("userid"),
				params.getParams().get("departmentcd"),
				params.getParams().get("departmentnm")
			)));
		return responser;
	}
	public Responser setuserpost() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setuserpost(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("userid"),
				params.getParams().get("postcd"),
				params.getParams().get("postname")
			)));
		return responser;
	}
	public Responser removeuserpost() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.removeuserpost(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("userid"),
				params.getParams().get("postcd"),
				params.getParams().get("postname")
			)));
		return responser;
	}
	public Responser edituser() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.edituser(
				loginName,
				actualName,
			params.getParams().get("rescd"),
			params.getParams().get("userid"),
			params.getParams().get("username"),
			params.getParam("oper"),
			params.getParam("orderby")
		)));
		return responser;
	}

	public Responser getAppList2() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getAppList2(
			params.getParams().get("rescd")
		)));
		return responser;
	}
	public Responser getAppList3() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getAppList3(
				session.getAttribute("rescd").toString()
		)));
		return responser;
	}
	public Responser setShareall() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setShareall(
				loginName,
				actualName,
			params.getParams().get("rescd"),
			params.getParams().get("tbid"),
			params.getParams().get("shareall")
		)));
		return responser;
	}
	public Responser setOpenAPP() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setOpenAPP(
				loginName,
				actualName,
			session.getAttribute("rescd").toString(),
			params.getParams().get("tbid"),
			params.getParams().get("show")
		)));
		return responser;
	}
	public Responser addAppPost() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.addAppPost(
				loginName,
				actualName,
			params.getParams().get("rescd"),
			params.getParams().get("tbid"),
			params.getParams().get("postcd")
		)));
		return responser;
	}
	public Responser delAppPost() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.delAppPost(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("tbid"),
				params.getParams().get("postcd")
		)));
		return responser;
	}
	public Responser addAppChecker() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.addAppChecker(
				loginName,
				actualName,
			params.getParams().get("rescd"),
			params.getParams().get("tbid"),
			params.getParams().get("postcd")
		)));
		return responser;
	}
	public Responser delAppChecker() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.delAppChecker(
				loginName,
				actualName,
				params.getParams().get("rescd"),
				params.getParams().get("tbid"),
				params.getParams().get("postcd")
		)));
		return responser;
	}

	public Responser checkunitleader2() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.checkunitleader2(
				params.getParams().get("rescd"),
				params.getParams().get("userid")
		)));
		return responser;
	}
	
	/*******************************heliang***********************************?
	 *账户安全
	 */
	public Responser getLoginfoByUserid() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String rescdString=params.getParams().get("rescd");
		String useridString=params.getParams().get("userid");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getLoginfoByUserid(useridString,rescdString)));
		return responser;
	}

	public Responser updatePassword() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String passString=params.getParams().get("password");
		String useridString=params.getParams().get("userid");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		String rescd=params.getParams().get("rescd");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON("{'status':"+service.updatePassword(
				loginName,actualName,useridString,passString,rescd)+"}"));
		return responser;
	}

	public Responser checkauthority() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(service.checkauthority(
				params.getParams().get("tbid"),
				params.getParams().get("userid"),
				params.getParams().get("rescd")
			));
		return responser;
	}
	public Responser viewauthority() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(service.viewauthority(
				params.getParams().get("tbid"),
				params.getParams().get("userid"),
				params.getParams().get("rescd")
			));
		return responser;
	}
	public Responser editauthority() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(service.editauthority(
				params.getParams().get("tbid"),
				params.getParams().get("userid"),
				params.getParams().get("rescd")
			));
		return responser;
	}

	public Responser checkauthority_appcode() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(service.checkauthority_appcode(
				params.getParams().get("appcode"),
				params.getParams().get("userid"),
				params.getParams().get("rescd")
			));
		return responser;
	}
	public Responser viewauthority_appcode() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(service.viewauthority_appcode(
				params.getParams().get("appcode"),
				params.getParams().get("userid"),
				params.getParams().get("rescd")
			));
		return responser;
	}
	public Responser editauthority_appcode() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(service.editauthority_appcode(
				params.getParams().get("appcode"),
				params.getParams().get("userid"),
				params.getParams().get("rescd")
			));
		return responser;
	}

	public Responser getdepk2() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getdepk2(
				params.getParams().get("rescd"),
				params.getParams().get("departmentcd")
			)));
		return responser;
	}

	public Responser getdepf2() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getdepf2(
				params.getParams().get("rescd"),
				params.getParams().get("departmentcd")
			)));
		return responser;
	}
	public Responser getresosurl() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getresosurl(
			)));
		return responser;
	}
	public Responser setresosurl() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setresosurl(
				loginName,
				actualName,
				params.getParams().get("url")
			)));
		return responser;
	}
	public Responser getsllurl() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getsllurl(
				session.getAttribute("rescd").toString()
			)));
		return responser;
	}
	public Responser setsllurl() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setsllurl(
				loginName,
				actualName,
				session.getAttribute("rescd").toString(),
				params.getParams().get("url")
			)));
		return responser;
	}
	public Responser getfhddurl() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");

		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getfhddurl(
				session.getAttribute("rescd").toString()
			)));
		return responser;
	}
	public Responser setfhddurl() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		String loginName = session.getAttribute("username").toString();
		String actualName = session.getAttribute("name").toString();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.setfhddurl(
				loginName,
				actualName,
				session.getAttribute("rescd").toString(),
				session.getAttribute("resnm").toString(),
				params.getParams().get("url")
			)));
		return responser;
	}
	public Responser queryForBDEVICE() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryForBDEVICE(
				params.getParams().get("RESCD")
				)));
		return responser;
	}
	public Responser queryForBDEVICEcd() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryForBDEVICEcd(
				params.getParams().get("RESCD"),
				params.getParams().get("devicecd")
				)));
		return responser;
	}
	public Responser queryForBDEVICETYPE() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryForBDEVICETYPE(
				params.getParams().get("rescd")
				)));
		return responser;
	}
	public Responser queryForBDEVICETYPEcd() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.queryForBDEVICETYPEcd(
				params.getParams().get("rescd"),
				params.getParams().get("typecd")
				)));
		return responser;
	}
	public Responser updateBDEVICE() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.updateBDEVICE(
				params.getParams().get("RESCD"),
				params.getParams().get("devicecd"),
				params.getParams().get("devicenm"),
				params.getParams().get("deviceid"),
				params.getParams().get("device_type"),
				params.getParams().get("maxv"),
				params.getParams().get("submaxv"),
				params.getParams().get("subminv"),
				params.getParams().get("minv"),
				params.getParams().get("x"),
				params.getParams().get("y"),
				params.getParams().get("state"),
				params.getParams().get("math"),
				params.getParams().get("p1"),
				params.getParams().get("p2"),
				params.getParams().get("p3"),
				params.getParams().get("p4"),
				params.getParams().get("p5"),
				params.getParams().get("p6"),
				params.getParams().get("p7"),
				params.getParams().get("p8"),
				params.getParams().get("apply")			
				)));
		return responser;
	}
	public Responser insertBDEVICE() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.insertBDEVICE(
				params.getParams().get("RESCD"),
				params.getParams().get("devicecd"),
				params.getParams().get("devicenm"),
				params.getParams().get("deviceid"),
				params.getParams().get("device_type"),
				params.getParams().get("maxv"),
				params.getParams().get("submaxv"),
				params.getParams().get("subminv"),
				params.getParams().get("minv"),
				params.getParams().get("x"),
				params.getParams().get("y"),
				params.getParams().get("state"),
				params.getParams().get("math"),
				params.getParams().get("p1"),
				params.getParams().get("p2"),
				params.getParams().get("p3"),
				params.getParams().get("p4"),
				params.getParams().get("p5"),
				params.getParams().get("p6"),
				params.getParams().get("p7"),
				params.getParams().get("p8"),
				params.getParams().get("apply")			
				)));
		return responser;
	}
	public Responser updateBDEVICETYPE() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.updateBDEVICETYPE(
				params.getParams().get("rescd"),
				params.getParams().get("typecd"),
				params.getParams().get("typenm"),
				params.getParams().get("icon"),
				params.getParams().get("bgimg"),
				params.getParams().get("highlevel"),
				params.getParams().get("able"),
				params.getParams().get("title"),
				params.getParams().get("unit")
				)));
		return responser;
	}
	public Responser insertBDEVICETYPE() {
		UserUtil service = (UserUtil) ServiceFactory
				.getService("userutil");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.insertBDEVICETYPE(
				params.getParams().get("rescd"),
				params.getParams().get("typecd"),
				params.getParams().get("typenm"),
				params.getParams().get("icon"),
				params.getParams().get("bgimg"),
				params.getParams().get("highlevel"),
				params.getParams().get("able"),
				params.getParams().get("title"),
				params.getParams().get("unit")
				)));
		return responser;
	}
}


/**
 * 用户管理服务
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;

public class UserAction extends DoAction {

  
	
	
	/**
	 * 获取用户Name、部门GroupName和模块AIDs
	 * @return
	 */
	public Responser Getapps(){
		String result=""; 
		String Name="";String GroupName="";String AIDs="";
		if(session.getAttribute("Name")!=null){Name='"'+session.getAttribute("Name").toString()+'"';}
		if(session.getAttribute("GroupName")!=null){GroupName='"'+session.getAttribute("GroupName").toString()+'"';}
		if(session.getAttribute("AIDs")!=null){AIDs='"'+session.getAttribute("AIDs").toString()+'"';}
		result="{\"Name\":"+Name+",\"GroupName\":"+GroupName+",\"AIDs\":"+AIDs+"}";
		responser.setRtType(JSON);
		responser.setRtString(result);
		return responser;
	} 
	
	/**
	 * zzj 2014.5.8 add
	 * 注销登陆
	 * @return
	 */
	public Responser logOut(){
		session.removeAttribute("token");
		responser.setRtType(JSON);
		responser.setRtString("{\"result\":true,\"message\":\"注销成功\"}");
		return responser;
	}
}

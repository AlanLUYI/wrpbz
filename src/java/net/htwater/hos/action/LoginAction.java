package net.htwater.hos.action;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import net.htwater.Util.IPHelper;
import net.htwater.entity.ResultObject;
import net.htwater.hos.service.UserService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class LoginAction extends DoAction {
	/**
	 * 获取ip
	 * 
	 * @return String
	 * @since v 1.0
	 */
	public String getRequestIp() {
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		return ip;
	}
	private String getIpAddr() {   
	     String ipAddress = null;   
	     //ipAddress = this.getRequest().getRemoteAddr();   
	     ipAddress = this.getRequest().getHeader("x-forwarded-for");   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = this.getRequest().getHeader("Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	         ipAddress = this.getRequest().getHeader("WL-Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = this.getRequest().getRemoteAddr();   
	      if(ipAddress.equals("127.0.0.1")){   
	       //根据网卡取本机配置的IP   
	       InetAddress inet=null;   
	    try {   
	     inet = InetAddress.getLocalHost();   
	    } catch (UnknownHostException e) {   
	     e.printStackTrace();   
	    }   
	    ipAddress= inet.getHostAddress();   
	      }   
	            
	     }   
	  
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	     }   
	     return ipAddress;    
	  }
	
	public Responser getip(){
		responser.setRtType(TEXT);
		responser.setRtString(getIpAddr());
		return responser;
	}
	public Responser getip_old(){
		responser.setRtType(TEXT);
		responser.setRtString(getRequestIp());
		return responser;
	}
	public Responser login() {
		String ip = getRequestIp();
		
		ResultObject ro = new ResultObject();
		String loginname=params.getParam("loginname");
		String password=params.getParam("password");
		UserService service = (UserService) ServiceFactory.getService("user");
		Boolean result=service.login(loginname, password);
		ro.setResult(result);
		ro.setSuccess(result);
		if(result){
			ro.setMessage("验证成功");	
			session.setAttribute("token", loginname);
			Map<String,Object> user=service.getUserInfo(loginname);
			session.setAttribute("region", user.get("region").toString());
			session.setAttribute("realname", user.get("realname").toString());
			//service.log(loginname, "登陆", ip, IPHelper.getLocation(ip), "登陆成功");
			service.log(loginname,"","", "登陆", ip, "", "登陆成功");
		}else{
			ro.setMessage("用户名或者密码错误");
			
			//service.log(loginname, "登陆", ip, IPHelper.getLocation(ip), "登陆失败");
			service.log(loginname,"","", "登陆", ip, "", "登陆失败");
		}
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(ro));
		return responser;
	}
	public Responser freeLogin() {
		String ip = getRequestIp();
		ResultObject ro = new ResultObject();
		Map<String,String> profile = params.getParams(); 
		String loginname="",DeptId="",ou="";
		if (profile.get("uid") != null) {
			loginname=profile.get("uid").toString();
			DeptId=profile.get("DeptId").toString();
			ou=profile.get("ou").toString();
		}
		UserService service = (UserService) ServiceFactory.getService("user");
		Boolean result=service.freeLogin(loginname);
		ro.setResult(result);
		ro.setSuccess(result);
		if(result){
			ro.setMessage("验证成功");
			session.setAttribute("token", "admin");
			session.setAttribute("loginname", loginname);
			session.setAttribute("name", profile.get("name").toString());
			session.setAttribute("DeptId", DeptId);
			session.setAttribute("ou", ou);
			//Map<String,Object> user=service.getUserInfo(loginname);
			session.setAttribute("region", profile.get("DeptId").toString());
			
			service.log(loginname,DeptId,ou, "登陆", ip, IPHelper.getLocation(ip), "登陆成功");
			//service.log(loginname, "登陆", ip, "", "登陆成功");
		}else{
			ro.setMessage("用户名或者密码错误");
			//service.log(loginname, "登陆", ip, IPHelper.getLocation(ip), "登陆失败");
			service.log(loginname,DeptId,ou, "登陆", ip, "", "登陆失败");
		}
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(ro));
		return responser;
	}
	
	public Responser logout(){
		ResultObject ro = new ResultObject();
		
		if(session.getAttribute("token")==null){
			ro.setMessage("您已处于注销状态，将回到登陆页面！");
			ro.setResult(false);
			ro.setResult(false);
		}else{
			String loginname=session.getAttribute("token").toString();
			session.removeAttribute("token");
			session.removeAttribute("region");
			session.removeAttribute("realname");
			ro.setMessage("帐户注销成功，将回到登陆页面！");
			ro.setResult(true);
			ro.setResult(true);
			
			UserService service = (UserService) ServiceFactory.getService("user");
			String ip=getRequestIp();
			service.log(loginname,"","", "登出", ip, IPHelper.getLocation(ip), "注销登陆");
		}
		
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(ro));
		return responser;
	}
}

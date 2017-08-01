/**
 * login
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */
package net.htwater.mydemo.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.LoginService;
import net.htwater.mydemo.service.YJXXService;
import cn.miao.framework.action.DoAction;
import cn.miao.framework.endec.Base64;
import cn.miao.framework.endec.RSA;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;

public class LoginAction extends DoAction {

	
	public Responser getpad_config(){
		Map<String,Object> map=new HashMap<String,Object>();
		LoginService service = (LoginService)ServiceFactory.getService("LoginService");
		map=service.getpad_config();
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(map));
		return responser;
	}
	
	public Responser padlogin(){
		Map<String,Object> map=new HashMap<String,Object>();
		LoginService service = (LoginService)ServiceFactory.getService("LoginService");
		List<Map<String, Object>> loginresult=service.login(params.getParam("loginname"), params.getParam("password"));
		if(loginresult.size()>0){
			map.put("result", true);
			map.put("message", "已登录");
			map.put("userid", loginresult.get(0).get("UserID"));
			map.put("username", loginresult.get(0).get("Name"));
			map.put("groupname", loginresult.get(0).get("GroupName"));
		}else{
			map.put("result", false);
			map.put("message", "未登录");
		} 
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(map));
		return responser;
	}
	
  /**
   * login页面验证服务
   * @author RYX
   * @Date 2014-02-17
   * @since v 1.0
   * @return
   */
	public Responser login(){
		LoginService service = (LoginService)ServiceFactory.getService("LoginService");
		String result="";
		List<Map<String, Object>> loginresult=service.login(params.getParam("loginname"), params.getParam("password"));
		if(loginresult.size()>0){
			result="{\"result\":true,\"message\":\"登陆成功\"}";
			session.setAttribute("ID", loginresult.get(0).get("ID"));
			session.setAttribute("token", loginresult.get(0).get("UserID"));		
			session.setAttribute("Name", loginresult.get(0).get("Name"));
			session.setAttribute("AIDs", loginresult.get(0).get("AIDs")); 
			session.setAttribute("GroupName", loginresult.get(0).get("GroupName")); 
			session.setAttribute("skin_bg", loginresult.get(0).get("skin_bg")); 
			session.setAttribute("skin_op", loginresult.get(0).get("skin_op")); 
			session.setAttribute("skin_cl", loginresult.get(0).get("skin_cl")); 
		}else{
			result="{\"result\":false,\"message\":\"用户名密码错误\"}";
		} 
		responser.setRtType(JSON);
		responser.setRtString(result);
		return responser;
	}
	
	/**
	 * 2014.3.18 zzj 添加单点登录功能
	 * @return
	 */
	public Responser ssologin(){
		String timestamp = params.getParam("timestamp");
		long intime = 0;
		timestamp = RSA.decrypt(timestamp);
		
		if (timestamp.length() > 0) {
			intime = Long.parseLong(timestamp);
		}
		
		//long diff = new Date().getTime() - intime;
		String token = request.getParameter("token");
		token = Base64.base64Decode(token);
		try {
			token = URLDecoder.decode(token, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 再次验证，这不很重要,在有效时间内登录本系统，设置session
		// 超时处理--这里处理有用么？，需要再去服务端验证
		// 封装一个包出来
//		System.out.println("时间差:" + diff + "ms");
//		if (diff < 10 * 1000) { // 1min内没登录就需要从新去系统认证下
//			HttpSession session = request.getSession();
//			session.setAttribute("token", token);
//			map.put("result", "ok");
//		} else {
//			map.put("result", "timeout");
//		}
		LoginService service = (LoginService)ServiceFactory.getService("LoginService");
		String result="";
		String userid=token.substring(token.lastIndexOf("-")+1);
		List<Map<String, Object>> loginresult=service.ssologin(userid);
		if(loginresult.size()>0){
			result="{\"result\":true,\"message\":\"登陆成功\"}";
			session.setAttribute("ID", loginresult.get(0).get("ID"));
			session.setAttribute("token", loginresult.get(0).get("UserID"));
			session.setAttribute("Name", loginresult.get(0).get("Name"));
			session.setAttribute("AIDs", loginresult.get(0).get("AIDs")); 
			session.setAttribute("GroupName", loginresult.get(0).get("GroupName")); 
			session.setAttribute("skin_bg", loginresult.get(0).get("skin_bg")); 
			session.setAttribute("skin_op", loginresult.get(0).get("skin_op")); 
			session.setAttribute("skin_cl", loginresult.get(0).get("skin_cl")); 
		}else{
			result="{\"result\":false,\"message\":\"用户名密码错误\"}";
		} 
		responser.setRtType(JSON);
		responser.setRtString(result);
		return responser;
	}

	/**
	   * 主页面获取登入账号信息
	   * @author RYX
	   * @Date 2014-02-17
	   * @since v 1.0
	   * @return
	   */
	public Responser checkLogin(){
		Map<String,Object> map=new HashMap<String,Object>();
		if(null == session.getAttribute("token")){
			map.put("result", false);
			map.put("message", "未登录");
		}else{
			map.put("result", true);
			map.put("message", "已登录");
			map.put("userid", session.getAttribute("token"));
			map.put("username", session.getAttribute("Name"));
			map.put("groupname", session.getAttribute("GroupName"));
			map.put("skin_bg", session.getAttribute("skin_bg"));
			map.put("skin_op", session.getAttribute("skin_op"));
			map.put("skin_cl", session.getAttribute("skin_cl"));
		}
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(map));
		return responser;
	}
	
	/**
	 * 修改背景
     * @author RYX
     * @Date 2014-06-10
     * @since v 1.0
	 * @return
	 */
	public Responser saveSkin()
	{
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		Map<String,Object> map=new HashMap<String,Object>();
		
		responser.setRtType(JSON);
		if(null == session.getAttribute("token")){
			map.put("result", false);
			map.put("message", "未登录");
			responser.setRtString(parseJSON(map));
		}else{ 
			map=service.saveSkin( 
					session.getAttribute("ID").toString(),
					params.getParams().get("bg"),
					params.getParams().get("op"),
					params.getParams().get("cl")
					);
			responser.setRtString(parseJSON(map));
			if(map.get("status")=="success")
			{
				session.setAttribute("skin_bg", params.getParams().get("bg")); 
				session.setAttribute("skin_op", params.getParams().get("op")); 
				session.setAttribute("skin_cl", params.getParams().get("cl")); 
			}
		}  
		
		return responser;
	}
	
	/**
	 * 修改模块AIDs
     * @author RYX
     * @Date 2014-06-10
     * @since v 1.0
	 * @return
	 */
	public Responser saveAids()
	{
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		Map<String,Object> map=new HashMap<String,Object>();
		
		responser.setRtType(JSON);
		if(null == session.getAttribute("token")){
			map.put("result", false);
			map.put("message", "未登录");
			responser.setRtString(parseJSON(map));
		}else{ 
			map=service.saveAids( 
					session.getAttribute("token").toString(),
					params.getParams().get("aids")
					);
			responser.setRtString(parseJSON(map));
			if(map.get("status")=="success")
			{
				session.setAttribute("AIDs", params.getParams().get("aids"));  
			}
		}  
		
		return responser;
	}
	
	/**
	 * 获取用户Name、部门GroupName和模块AIDs
     * @author RYX
     * @Date 2014-02-17
     * @since v 1.0
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
	 * 登出
     * @author RYX
     * @Date 2014-02-17
     * @since v 1.0
	 * @return
	 */
	public Responser logout(){
		session.invalidate();
		responser.setRtType(NONE);
		return responser;
	}
	
	/**
	   * 菜单
	   * @author RYX
	   * @Date 2014-02-26
	   * @since v 1.0
	   * @return
	   */
	public Responser Gettreedata() {  
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.Gettreedata(session.getAttribute("token").toString()))); 
		return responser;
	}
	
	
	
	/**
	   * 我的应用
	   * @author RYX
	   * @Date 2014-08-12
	   * @since v 1.0
	   * @return
	   */
	public Responser getmyapps() {  
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getmyapps(session.getAttribute("GroupName").toString()))); 
		return responser;
	}
	
	/**
	   * 网站列表
	   * @author RYX
	   * @Date 2014-06-17
	   * @since v 1.0
	   * @return
	   */
	public Responser geturls() {  
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.geturls(session.getAttribute("token").toString()))); 
		return responser;
	}
	
	/**
	   * 增加网站
	   * @author RYX
	   * @Date 2014-06-17
	   * @since v 1.0
	   * @return
	   */
	public Responser addurl()
	{
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		Map<String,Object> map=new HashMap<String,Object>(); 
		responser.setRtType(JSON);
		if(null == session.getAttribute("token")){
			map.put("result", false);
			map.put("message", "未登录");
			responser.setRtString(parseJSON(map));
		}else{ 
			map=service.addurl( 
					session.getAttribute("token").toString(),
					params.getParams().get("url"),
					params.getParams().get("title")
					);
			responser.setRtString(parseJSON(map)); 
		}  
		return responser;
	}
	/**
	   * 修改网站
	   * @author RYX
	   * @Date 2014-06-17
	   * @since v 1.0
	   * @return
	   */
	public Responser updateurl()
	{
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		Map<String,Object> map=new HashMap<String,Object>(); 
		responser.setRtType(JSON);
		if(null == session.getAttribute("token")){
			map.put("result", false);
			map.put("message", "未登录");
			responser.setRtString(parseJSON(map));
		}else{ 
			map=service.updateurl( 
					params.getParams().get("ID"),
					session.getAttribute("token").toString(),
					params.getParams().get("url"),
					params.getParams().get("title")
					);
			responser.setRtString(parseJSON(map)); 
		}  
		return responser;
	}
	/**
	   * 删除网站
	   * @author RYX
	   * @Date 2014-06-17
	   * @since v 1.0
	   * @return
	   */
	public Responser deleteurl()
	{
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		Map<String,Object> map=new HashMap<String,Object>(); 
		responser.setRtType(JSON);
		if(null == session.getAttribute("token")){
			map.put("result", false);
			map.put("message", "未登录");
			responser.setRtString(parseJSON(map));
		}else{ 
			map=service.deleteurl(  
					params.getParams().get("ID"),
					session.getAttribute("token").toString()
					);
			responser.setRtString(parseJSON(map)); 
		}  
		return responser;
	}
	
	
	
	public Responser CheckphoneNumber()
	{
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		Map<String,Object> map=new HashMap<String,Object>(); 
		responser.setRtType(JSON);
		map=service.CheckphoneNumber( 
				params.getParams().get("phoneNumber") 
				);
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	public Responser CheckIMSI()
	{
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		Map<String,Object> map=new HashMap<String,Object>(); 
		responser.setRtType(JSON);
		map=service.CheckIMSI( 
				params.getParams().get("IMSI") 
				);
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	public Responser SaveIMSI()
	{
		LoginService service = (LoginService) ServiceFactory.getService("LoginService");
		Map<String,Object> map=new HashMap<String,Object>(); 
		responser.setRtType(JSON);
		map=service.SaveIMSI( 
				params.getParams().get("phoneNumber"),
				params.getParams().get("IMSI")
				);
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
}

/*zzj 2015.2 桌面图标系统相关*/
package net.htwater.webos;

import java.util.HashMap;
import java.util.Map;

import cn.miao.framework.action.DoAction;
import cn.miao.framework.entity.Responser;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.HttpUtil;

public class WebosAction extends DoAction {
	int getUserid(){
		return Integer.parseInt(session.getAttribute("token").toString());
	}

	/**
	 * 获取桌面图标
	 * @return
	 */
	public Responser getApps(){
		WebosService service = (WebosService)ServiceFactory.getService("webos");
		responser.setRtType(JSON);
		responser.setRtString(parseJSON(service.getApps()));
		return responser;
	}
	
	/**
	 * 获取用户的桌面信息，包括桌面里的所有项
	 * @param userid
	 * @return
	 */
	public Responser getDesks(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getDesks(session.getAttribute("token").toString()))); 
		return responser;
	}
	
	/**
	 * 创建桌面：按用户所属角色默认桌面配置创建
	 * @return
	 */
	public Responser createDeskByDefault(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		responser.setRtType(JSON); 
		boolean b=service.createDeskByDefault(session.getAttribute("token").toString());
		Map<String,Object> map=new HashMap<String,Object>();
		if (b) {
			map.put("result", true);
			map.put("message", "桌面创建成功");
		}else{
			map.put("result", false);
			map.put("message", "桌面创建失败，请尝试手动创建或联系管理员");
		}
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	
	/**
	 * 删除文件夹
	 * @return
	 */
	public Responser deleteFolder(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		responser.setRtType(JSON); 
		boolean b=service.deleteFolder(params.getParam("folderid"));
		Map<String,Object> map=new HashMap<String,Object>();
		if (b) {
			map.put("result", true);
			map.put("message", "文件夹删除成功");
		}else{
			map.put("result", false);
			map.put("message", "文件夹删除失败");
		}
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	/**
	 * 更新文件夹
	 * @return
	 */
	public Responser updateFolder(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		responser.setRtType(JSON); 
		boolean b=service.updateFolder(params.getParam("folderid")
				,params.getParam("foldername")
				,params.getParam("folder_items"));
		Map<String,Object> map=new HashMap<String,Object>();
		if (b) {
			map.put("result", true);
			map.put("message", "文件夹更新成功");
		}else{
			map.put("result", false);
			map.put("message", "文件夹更新失败");
		}
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	
	/**
	 * 根据文件夹ID获取文件夹对象，包括里面的itmes
	 * @param folderid
	 * @return
	 */
	public Responser getFolder(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getFolder(params.getParam("folderid")))); 
		return responser;
	}
	/**
	 * 更新桌面上的图标
	 * @return
	 */
	public Responser updateDeskItems(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		responser.setRtType(JSON); 
		boolean b=service.updateDeskItems(params.getParam("deskid"),params.getParam("items"));
		Map<String,Object> map=new HashMap<String,Object>();
		if (b) {
			map.put("result", true);
			map.put("message", "桌面更新成功");
		}else{
			map.put("result", false);
			map.put("message", "桌面更新失败");
		}
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	/**
	 * 新建桌面
	 * @param userid
	 * @return
	 */
	public Responser addDesk(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.addDesk(session.getAttribute("token").toString()))); 
		return responser;
	}
	/**
	 * 更新桌面名称
	 * @return
	 */
	public Responser updateDeskName(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		boolean b=service.updateDeskName(params.getParam("deskid"), params.getParam("desk_name"));
		Map<String,Object> map=new HashMap<String,Object>();
		if (b) {
			map.put("result", true);
			map.put("message", "桌面更新名称成功");
		}else{
			map.put("result", false);
			map.put("message", "桌面更新名称失败");
		}
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	/**
	 * 更新桌面顺序
	 * @return
	 */
	public Responser updateDeskOrd(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		boolean b=service.updateDeskOrd(params.getParam("deskid"),params.getParam("ord"));
		Map<String,Object> map=new HashMap<String,Object>();
		if (b) {
			map.put("result", true);
			map.put("message", "更新桌面顺序成功");
		}else{
			map.put("result", false);
			map.put("message", "更新桌面顺序失败，再次出现请联系管理员");
		}
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	/**
	 * 删除桌面
	 * @return
	 */
	public Responser delDesk(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		boolean b=service.delDesk(params.getParam("deskid"));
		Map<String,Object> map=new HashMap<String,Object>();
		if (b) {
			map.put("result", true);
			map.put("message", "删除桌面成功");
		}else{
			map.put("result", false);
			map.put("message", "删除桌面失败");
		}
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	
	/**
	 * 删除桌面
	 * @return
	 */
	public Responser createFolder(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		String folderid=service.createFolder(params.getParam("folder_items"), session.getAttribute("token").toString());
		Map<String,Object> map=new HashMap<String,Object>();
		if (folderid != null && !folderid.equals("")) {
			map.put("result", true);
			map.put("message", "新建文件夹成功");
			map.put("data", service.getFolder(folderid));
		}else{
			map.put("result", false);
			map.put("message", "新建文件夹失败");
		}
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(map)); 
		return responser;
	}
	
	/**
	 * 获取应用分类
	 * @return
	 */
	public Responser getAppClass(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getAppClass())); 
		return responser;
	}
	/**
	 * 根据当前用户所属角色，获取其可用的所有应用
	 * @return
	 */
	public Responser getAllApp(){
		WebosService service = (WebosService) ServiceFactory.getService("webos");
		responser.setRtType(JSON);  
		responser.setRtString(parseJSON(service.getAllApp(session.getAttribute("token").toString()))); 
		return responser;
	}
}

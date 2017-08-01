/*zzj 2015.2 桌面图标系统相关*/
package net.htwater.webos;

import java.util.List;
import java.util.Map;

public interface WebosService {
	static final String DB_WEBOS = "HOS";
	
	/**
	 * 获取桌面图标
	 * @return
	 */
	public List<Map<String,Object>> getApps();

	/**
	 * 获取用户的桌面信息，包括桌面里的所有项
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> getDesks(String userid);
	
	/**
	 * 创建桌面：根据用户所在角色的默认桌面配置
	 * @param userid
	 * @return
	 */
	public boolean createDeskByDefault(String userid);
	
	
	/**
	 * 删除文件夹
	 * @param folderid
	 * @return
	 */
	public boolean deleteFolder(String folderid);
	
	/**
	 * 跟新文件夹
	 * @param folderid
	 * @param foldername
	 * @param folder_items
	 */
	public boolean updateFolder(String folderid,String foldername,String folder_items);
	
	/**
	 * 根据文件夹ID获取文件夹对象，包括里面的itmes
	 * @param folderid
	 * @return
	 */
	public Map<String,Object> getFolder(String folderid);
	
	/**
	 * 新建文件夹
	 * @param foleder_items
	 * @return the new folderid
	 */
	public String createFolder(String folder_items,String userid);

	/**
	 * 更新桌面
	 * @param userid
	 * @return
	 */
	public boolean updateDeskItems(String deskid,String desk_items);
	/**
	 * 新建桌面
	 * @return
	 */
	public Map<String,Object> addDesk(String userid);
	/**
	 * 修改桌面名称
	 * @param deskid
	 * @param desk_name
	 * @return
	 */
	public boolean updateDeskName(String deskid,String desk_name);
	/**
	 * 修改桌面排序
	 * @param deskid
	 * @param desk_ord
	 * @return
	 */
	public boolean updateDeskOrd(String deskid,String desk_ord);
	/**
	 * 删除桌面
	 * @param deskid
	 * @return
	 */
	public boolean delDesk(String deskid);
	
	/**
	 * 获取app分类
	 * @return
	 */
	public List<Map<String,Object>> getAppClass();
	
	/**
	 * 获取当前用户所属角色下的所有可用app
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> getAllApp(String userid);
}

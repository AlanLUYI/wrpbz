/*zzj 2015.2 桌面图标系统相关*/
package net.htwater.webos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

public class WebosServiceImpl implements WebosService {
	BaseDao daoWEBOS = DaoFactory.getDao(DB_WEBOS);
	
	/**
	 * 获取桌面图标
	 * @return
	 */
	public List<Map<String,Object>> getApps(){
		String sql="select * from os_app where enable=1 order by ordby";
		
		return daoWEBOS.executeQuery(sql);
	}
	
	/**
	 * 获取用户的桌面信息，包括桌面里的所有项
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> getDesks(String userid){
		List<Map<String, Object>> listDesk = new ArrayList<Map<String, Object>>();
		String sqlDesk="select * from sys_desk_items where userid=? order by ordby";
		listDesk=daoWEBOS.executeQuery(sqlDesk,new Object[]{userid});
		
		String sqlApp="select * from sys_app";
		List<Map<String, Object>> listApp=daoWEBOS.executeQuery(sqlApp);
		
		String sqlFolder="select * from sys_folder where userid=?";
		List<Map<String, Object>> listFolder=daoWEBOS.executeQuery(sqlFolder,new Object[]{userid});
		
		for(Map<String,Object> map:listDesk){
			if(map.get("items")==null){
				continue;
			}
			String itemstr=map.get("items").toString();
			List<Map<String,Object>> itemsList = new ArrayList<Map<String,Object>>();
			map.put("items", itemsList);
			for(String item:itemstr.split(",")){
				String[] arr=item.split("_");
				if(arr[0].equals("app")){
					Map<String,Object> _m=searchFromList(listApp,"id",arr[1]);
					_m.put("type", "app");
					itemsList.add(_m);
				}else if(arr[0].equals("folder")){
					Map<String,Object> _m=searchFromList(listFolder,"id",arr[1]);
					_m.put("type", "folder");
					itemsList.add(_m);
					
					String itemstr2=_m.get("items").toString();
					List<Map<String,Object>> itemsList2 = new ArrayList<Map<String,Object>>();
					_m.put("items", itemsList2);
					for(String item2:itemstr2.split(",")){
						String[] arr2=item2.split("_");
						if(arr2[0].equals("app")){
							Map<String,Object> _m2=searchFromList(listApp,"id",arr2[1]);
							_m2.put("type", "app");
							itemsList2.add(_m2);
						}
					}
				}
			}
		}
		return listDesk;
	}
	
	private Map<String,Object> searchFromList(List<Map<String,Object>> list,String key,String value){
		Map<String,Object> map=new HashMap<String,Object>();
		for(Map<String,Object> m:list){
			if(m.get(key).toString().equals(value)){
				map=m;
				break;
			}
		}
		return map;
	}

	
	
	private String createFolderByDefault(String folderid,String user){
		String sql="SET NOCOUNT ON insert into sys_folder (name,items,userid) select name,items,? from sys_folder where id=? SELECT @@IDENTITY as folderid";
		Map<String,Object> map=daoWEBOS.executeQueryObject(sql, new Object[]{user,folderid});
		return "folder_"+map.get("folderid");
	}
	private String arrJoin(String[] arr,String joinwith){
		String result="";
		for(int i=0;i<arr.length;i++){
			result+=joinwith+arr[i];
		}
		if(result.length()>0){
			result=result.substring(1);
		}
		return result;
	}

	

	@Override
	public boolean deleteFolder(String folderid) {
		String sql="delete sys_folder where id=?";
		boolean b=daoWEBOS.executeSQL(sql,new Object[]{folderid});
		return b;
	}

	@Override
	public boolean updateFolder(String folderid, String foldername,
			String folder_items) {
		String sql="update sys_folder set items=?,name=? where id=?";
		boolean b=daoWEBOS.executeSQL(sql,new Object[]{folder_items,foldername,folderid});
		return b;
	}

	@Override
	public Map<String, Object> getFolder(String folderid) {
		String sql_folder="select * from sys_folder where id=?";
		Map<String,Object> map_folder=daoWEBOS.executeQueryObject(sql_folder, new Object[]{folderid});
		map_folder.put("type", "folder");
		
		String sql_apps="select * from sys_app";
		List<Map<String,Object>> list_app=daoWEBOS.executeQuery(sql_apps);
		
		String itemstr2=map_folder.get("items").toString();
		List<Map<String,Object>> itemsList2 = new ArrayList<Map<String,Object>>();
		map_folder.put("items", itemsList2);
		for(String item2:itemstr2.split(",")){
			String[] arr2=item2.split("_");
			if(arr2[0].equals("app")){
				Map<String,Object> _m2=searchFromList(list_app,"id",arr2[1]);
				_m2.put("type", "app");
				itemsList2.add(_m2);
			}
		}
		return map_folder;
	}

	@Override
	public Map<String, Object> addDesk(String userid) {
		String sql_max="select MAX(ordby) max from sys_desk_items where userid=?";
		Map<String,Object> map_max=daoWEBOS.executeQueryObject(sql_max, new Object[]{userid});
		Double max=1.0;
		if(map_max!=null && map_max.get("max") != null){
			max=Double.parseDouble(map_max.get("max").toString()) +1;
		}
		
		String sql="SET NOCOUNT ON insert into sys_desk_items (name,userid,ordby) values('新建桌面',?,?) select * from sys_desk_items where id=@@IDENTITY";
		Map<String,Object> map_desk=daoWEBOS.executeQueryObject(sql, new Object[]{userid,max});
		return map_desk;
	}
	
	@Override
	public boolean updateDeskItems(String deskid,String desk_items) {
		// TODO Auto-generated method stub
		String sql="update sys_desk_items set items=? where id=?";
		boolean b=daoWEBOS.executeSQL(sql,new Object[]{desk_items,deskid});
		return b;
	}
	
	@Override
	public boolean createDeskByDefault(String user) {
		boolean b=false;
		
		try{
			String sql_get_role="select role_code from sys_user where loginname=?";
			Map<String,Object> map_role = daoWEBOS.executeQueryObject(sql_get_role,new Object[]{user});
			String role=map_role.get("role_code").toString();
			
			String sql="SET NOCOUNT ON insert into sys_desk_items(name,items,userid,ordby) select name,items,?,ordby from sys_desk_items where userid=? select * from sys_desk_items where userid=?";
			List<Map<String,Object>> list_get_desk
				=daoWEBOS.executeQuery(sql,new Object[]{user,"default-"+role,user});
			
			for(int i=0;i<list_get_desk.size();i++){
				Map<String,Object> desk=list_get_desk.get(i);
				String desk_items=desk.get("items").toString();
				String[] arr = desk_items.split(",");
				for(int j=0;j<arr.length;j++){
					String item=arr[j];
					if(item.indexOf("folder")>-1){
						arr[j]= createFolderByDefault(arr[j].split("_")[1], user);
					}
				}
				desk_items=arrJoin(arr,",");
				updateDeskItems(desk.get("id").toString(),desk_items);
			}
			
			b=true;
		}catch(Exception e){
			b=false;
		}
		return b;
	}

	@Override
	public boolean updateDeskName(String deskid, String desk_name) {
		String sql="update sys_desk_items set name=? where id=?";
		boolean b=daoWEBOS.executeSQL(sql, new Object[]{desk_name,deskid});
		return b;
	}

	@Override
	public boolean updateDeskOrd(String deskid, String desk_ord) {
		String sql="update sys_desk_items set ordby=? where id=?";
		boolean b=daoWEBOS.executeSQL(sql, new Object[]{desk_ord,deskid});
		return b;
	}

	@Override
	public boolean delDesk(String deskid) {
		String sql_items="select items from sys_desk_items where id=?";
		Map<String,Object> map_items=daoWEBOS.executeQueryObject(sql_items, new Object[]{deskid});
		if(map_items.get("items")!=null){
			String str_items=map_items.get("items").toString();
			if(str_items.length()>0){
				String[] arr_items=str_items.split(",");
				for(int i=0;i<arr_items.length;i++){
					if(arr_items[i].indexOf("folder_")>-1){
						deleteFolder(arr_items[i].split("_")[1]);
					}
				}
			}
		}
		
		String sql="delete sys_desk_items where id=?";
		boolean b=daoWEBOS.executeSQL(sql, new Object[]{deskid});
		return b;
	}

	@Override
	public String createFolder(String folder_items,String userid) {
		// TODO Auto-generated method stub
		String sql="SET NOCOUNT ON insert into sys_folder(name,items,userid) values('新建文件夹',?,?) select @@IDENTITY as folderid";
		Map<String,Object> map=daoWEBOS.executeQueryObject(sql, new Object[]{folder_items,userid});
		return map.get("folderid").toString();
	}

	@Override
	public List<Map<String, Object>> getAppClass() {
		String sql="select * from sys_app_class order by ord";
		List<Map<String,Object>> list = daoWEBOS.executeQuery(sql);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> getAllApp(String userid) {
		String sql="select a.* from sys_app as a where menuid in (select menu_id from sys_role_menu where role_code = (select role_code from sys_user where loginname=?)) order by ordby";
		List<Map<String,Object>> list = daoWEBOS.executeQuery(sql, new Object[]{userid});
		return list;
	}
}

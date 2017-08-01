/**
 * wechart  微信数据服务
 * @author fhj
 * 2015-02-10
 */
package net.htwater.hos.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import net.htwater.hos.service.WechartService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

public class WechartImpl implements WechartService {
	BaseDao dao = DaoFactory.getDao(DB_HOS);//DB_SHPUB
	BaseDao dao_nbsqdb = DaoFactory.getDao(DB_NBSQDB);
	BaseDao dao_Server2 = DaoFactory.getDao(DB_HOS);//DB_Server2
	BaseDao dao_sh = DaoFactory.getDao(DB_HOS);
	//微信数据服务，雨量数据
	public List<Map<String, Object>> queryFORrain() {
		String sqlString = "";
		List<Map<String, Object>> result = null;
	    sqlString = "select * from STATION where type='R' order by orderby";
		result = dao.executeQuery(sqlString);
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");     		
		Date ydate=new Date();//取时间 
		
		Calendar   calendar   =   new   GregorianCalendar(); 
		calendar.setTime(ydate); 
		calendar.add(calendar.DATE,1);
		String time1 = sDateFormat.format(calendar.getTime());
	
		Calendar   calendar2   =   new   GregorianCalendar(); 
		calendar2.setTime(ydate); 
		calendar2.add(calendar2.DATE,2);
		String time2 = sDateFormat.format(calendar2.getTime());
		
		for(Map<String,Object> map : result){
			String stcd = map.get("STCD").toString();
			String sql = "select SUM(drn) as drn from [RAINDAY] where tm > ? and tm<? and stcd = ?";
			Object[] args={time1,time2,stcd};
		    Map<String,Object> drn = dao_nbsqdb.executeQueryObject(sql, args);
			map.put("drn", drn.get("drn"));
		}		 
		return result;
	}
	
	public static void main(String[] args) {
		new WechartImpl().queryFORrain();
	}
	
	//微信数据服务，水位数据
	public List<Map<String, Object>> queryFORwater() {
		String sqlString = "";
		List<Map<String, Object>> result = null;
		sqlString = "select * from STATION where type='W' order by orderby";
		result = dao.executeQuery(sqlString);
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");     
		String   time   =   sDateFormat.format(new Date()); 	
		for(Map<String,Object> map : result){
			String stcd = map.get("STCD").toString();
			String sql = "select top 2 * from ST_RIVER_R where stcd = ? and tm <? ";
			Object[] args={stcd,time};
			List<Map<String, Object>> z = dao_nbsqdb.callProcedure(sql, args);
			String z1 = z.get(0).get("z").toString();
			String z2 = z.get(1).get("z").toString();	
			//Float.parseFloat
			if(Float.parseFloat(z1)>Float.parseFloat(z2)){
				map.put("zl", "↑");		
			}else if(Float.parseFloat(z1)==Float.parseFloat(z2)){
				map.put("zl", "--");		
			}else{
				map.put("zl", "↓");	
			}
			map.put("z", z.get(0).get("z"));		
		}			
		return result;
	}
	
	//微信数据服务，潮位数据
	public List<Map<String, Object>> queryFORtide() {
		String sqlString = "";
		List<Map<String, Object>> result = null;
		sqlString = "select * from STATION where type='T' order by orderby";
		result = dao.executeQuery(sqlString);
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");     
		String   time   =   sDateFormat.format(new Date()); 
		for(Map<String,Object> map : result){
			String stcd = map.get("STCD").toString();
			String sql = "select top 2 * from ST_TIDE_R where stcd = ? and tm <? ";
			Object[] args={stcd,time};
			List<Map<String, Object>> tdz = dao_nbsqdb.callProcedure(sql, args);
			String z1 = tdz.get(0).get("tdz").toString();
			String z2 = tdz.get(1).get("tdz").toString();	
			//Float.parseFloat
			if(Float.parseFloat(z1)>Float.parseFloat(z2)){
				map.put("zl", "↑");		
			}else if(Float.parseFloat(z1)==Float.parseFloat(z2)){
				map.put("zl", "--");		
			}else{
				map.put("zl", "↓");	
			}
			map.put("tdz", tdz.get(0).get("tdz"));		
		}			
		return result;
	}
	
	//微信数据服务，台风消息
    public String queryFORtyph(){
		List<Map<String, Object>> result = null;
		String message = "";
		//取出多个台风数据，循环
		String sqlString = "SELECT TOP 10 [id],[typhoonname],[typhoonyear] ,[typhooncode],[starttime],[ename] FROM tb_zj_typhoonlist order by starttime desc";
		//Map<String, Object> map= dao_Server2.executeQueryObject(sqlString);
		result = dao_Server2.executeQuery(sqlString);
		int count = 0;
		for (Map<String, Object> map : result) {	
		    String typhooncode = map.get("typhooncode").toString();//台风id
			//判断台风是否是现在		    
		    String sql = "select TOP 1 * from  tb_zj_typhoonpath where typhooncode =" + typhooncode +" order by wind_time desc";
	        Map<String, Object> map1 = dao_Server2.executeQueryObject(sql);
	        String wind_time = map1.get("wind_time").toString();
	        //判断台风是否进行中
	        SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
	        try
	        {
	            Date d1 = sDateFormat.parse(wind_time);
	            Date d2 = new Date();
	            long diff = d2.getTime() - d1.getTime();
	            long days = diff / (1000 * 60 * 60 * 24);
	            if(days<=3){
	            	count ++;
		            Calendar   calendar   =   new   GregorianCalendar(); 
		    		calendar.setTime(d1);  	        		
		    		String typh_time = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日" + calendar.get(Calendar.HOUR_OF_DAY) + "时";    		              
	         	    double latitude = Double.parseDouble(map1.get("latitude").toString());
	         	    double longitude = Double.parseDouble(map1.get("longitude").toString()); 
	         	    //宁波经纬度29.87801,121.53887
	         	    double julin = GetDistance(latitude, longitude, 29.87801, 121.53887);
	         	    message += typh_time + "消息：" + map1.get("dottype") +"\""+ map1.get("typhoonname").toString() +"\"," + "距离宁波：" +julin+ "千米，中心气压：" + map1.get("air_pressure") +"百帕，"+"最大风速："+map1.get("wind_speed")+"米/秒，"+
	         	    "移动速度："+map1.get("move_speed")+"公里/小时，" + "移动方向："+map1.get("move_direction")+"。\n";  	    
	            }else{
	            	         
	            }    	      	             	           		    
	        }
	        
	        catch (Exception e)
	        {
	        	
	        }
		}
		if( count > 0 ){
			message = "当前共有"+count+"台风：\n"+message;
		}else{
			message = "台风消息：当前无台风！";
		}
		return message;
	}
    
    public String  insertOPENID(String openid){
    	String select = "select * from weixin_openid where weixin_openid = '"+ openid +"'";
    	List<Map<String, Object>> result = null;
    	result = dao_sh.callProcedure(select);
    	if(result.size() == 0){
    		String sql = "insert into weixin_openid ( weixin_openid ) values ( '"+openid+"' )";
        	dao_sh.executeSQL(sql);
    	}   	
    	return "";
    }
    
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
       double EARTH_RADIUS = 6378.137;
       double radLat1 = rad(lat1);
       double radLat2 = rad(lat2);
       double a = radLat1 - radLat2;
       double b = rad(lng1) - rad(lng2);

       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
       Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
       s = s * EARTH_RADIUS;
       s = Math.round(s * 10000) / 10000;
       return s;
    }
    private static double rad(double d)
    {
       return d * Math.PI / 180.0;
    }

}

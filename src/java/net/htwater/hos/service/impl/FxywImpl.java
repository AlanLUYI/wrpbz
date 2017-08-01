/**
 * fxyw  防汛业务数据服务
 * @author fhj
 * 2015-03-19
 */
package net.htwater.hos.service.impl;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Map.Entry;
import java.net.URLConnection;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.Session;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import org.apache.poi.hssf.record.formula.functions.Replace;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.xml.bind.v2.runtime.Name;

import sun.misc.BASE64Encoder;
import net.htwater.Util.SendMessage;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.DateUtil;
import net.htwater.hos.service.FxywService;

public class FxywImpl implements FxywService {
	BaseDao dao = DaoFactory.getDao(DB_HOS);
	//BaseDao dao_nbsqdb = DaoFactory.getDao(DB_NBSQDB);
	//BaseDao dao_slpc = DaoFactory.getDao(DB_SLPC);
	//BaseDao dao_USERS = DaoFactory.getDao(DB_HOS);//DB_USERS
	private String uploadPath = "D://shanhong_fxyw/file/";
	//按照年，月获取值班信息
	public List<Map<String, Object>> queryFORyearmonth(String year,String month) {
		String strM = month.length()>1?"":"0";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);//Java月份才0开始算
		int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		for(int i=1 ;i<= dateOfMonth ;i++){
			String strI = i<10?"0":"";
			String dateStr = year+"-"+strM+month+"-"+strI+i;
			Map< String , Object> map = new java.util.HashMap<String, Object>();
			map.put("date", dateStr);
			try {
				//以距离2015-01-01的日期作为id
				java.util.Date d2 = sdf.parse("2015-01-01");
				java.util.Date date = sdf.parse( dateStr );	 			
			    long diff = date.getTime() - d2.getTime();    
			    long days = diff / (1000 * 60 * 60 * 24);
			    map.put("id", days);
				map.put("week", getWeekOfDate(date) );
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(map);		
		}	
        String start = list.get(0).get("id").toString();
        String end = list.get(list.size()-1).get("id").toString();
		String sql = "select * from FXYW_duty where id <= ? and id >= ?";
		Object[] args = { end,start };
		List<Map<String, Object>> duty = dao.executeQuery(sql, args);
		//增加值班内容
			if(duty.size()>0){
				for(int i=0;i<list.size();i++){
					String id = list.get(i).get("id").toString();
					for (int j=0;j<duty.size();j++){
						if(duty.get(j).get("id").toString().equals(id)){
							//匹配到同一条，增加内容
							list.get(i).put("leader", duty.get(j).get("leader"));
							list.get(i).put("leader_name", duty.get(j).get("leader_name"));
							list.get(i).put("master", duty.get(j).get("master"));
							list.get(i).put("master_name", duty.get(j).get("master_name"));
							list.get(i).put("member", duty.get(j).get("member"));
							list.get(i).put("member_name", duty.get(j).get("member_name"));
							list.get(i).put("dirver", duty.get(j).get("dirver"));
							list.get(i).put("dirver_name", duty.get(j).get("dirver_name"));
							list.get(i).put("hurry", duty.get(j).get("hurry"));
							list.get(i).put("hurry_name", duty.get(j).get("hurry_name"));
							break;
						}else{
							list.get(i).put("leader", "");
							list.get(i).put("leader_name", "");
							list.get(i).put("master", "");
							list.get(i).put("master_name", "");
							list.get(i).put("member", "");
							list.get(i).put("member_name", "");
							list.get(i).put("dirver", "");
							list.get(i).put("dirver_name", "");
							list.get(i).put("hurry", "");
							list.get(i).put("hurry_name", "");
						}
					}
				}
			}else{
				for(int i=0;i<list.size();i++){
					list.get(i).put("leader", "");
					list.get(i).put("leader_name", "");
					list.get(i).put("master", "");
					list.get(i).put("master_name", "");
					list.get(i).put("member", "");
					list.get(i).put("member_name", "");
					list.get(i).put("dirver", "");
					list.get(i).put("dirver_name", "");
					list.get(i).put("hurry", "");
					list.get(i).put("hurry_name", "");
				}
			}
			
		return list;
	}
	//获取日期的星期
	public static String getWeekOfDate(Date dt) {
	        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	        if (w < 0)
	            w = 0;
	        return weekDays[w];
	 }
	//获取全部值班人员
	public List<Map<String, Object>> queryFORmember() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
		String sql = "select * from FXYW_men ";
		list = dao.executeQuery(sql);
		return list;
	}
	//更新或插入值班信息
	public boolean updateFORduty( Map<String, String> map){
		String id = map.get("APP[first]").toString();
		//查询数据库，看是否有这条数据
		Map< String , Object> result = new java.util.HashMap<String, Object>();
		String sql = "select top 1 * from FXYW_duty where id = '"+id+"'";
		result = dao.executeQueryObject(sql);
		String sqlstr = "";
		if(result==null){
			sqlstr = "insert into FXYW_duty (id,leader,master,member,dirver,hurry,leader_name,master_name,member_name,dirver_name,hurry_name) "
					+ "values ('"+map.get("APP[first]")+"','"+map.get("APP[leader]")+"','"+map.get("APP[master]")+"','"+map.get("APP[member]")+"','"+map.get("APP[dirver]")+"','"+map.get("APP[hurry]")+"'"
							+ ",'"+map.get("APP[leader_name]")+"','"+map.get("APP[master_name]")+"','"+map.get("APP[member_name]")+"','"+map.get("APP[dirver_name]")+"','"+map.get("APP[hurry_name]")+"')";
							
		    
		}else{
			sqlstr = "update FXYW_duty set leader = '"+map.get("APP[leader]")+"',master='"+map.get("APP[master]")+"',member='"+map.get("APP[member]")+"',dirver='"+map.get("APP[dirver]")+"',hurry='"+map.get("APP[hurry]")+"' "
					+ ",  leader_name = '"+map.get("APP[leader_name]")+"',master_name = '"+map.get("APP[master_name]")+"',member_name = '"+map.get("APP[member_name]")+"',dirver_name = '"+map.get("APP[dirver_name]")+"',hurry_name = '"+map.get("APP[hurry_name]")+"' where id='"+map.get("APP[first]")+"'";
		}
		Boolean isOK = dao.executeSQL(sqlstr);

		return isOK;
	}
	//获取指定人员id的信息
	public List<Map<String, Object>> queryFORperson(String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
		String sql = "select * from FXYW_men where id = " + id;
		list = dao.executeQuery(sql);
		return list;
	}
	//获取抢险队伍
	public List<Map<String, Object>> queryFORteam(String region) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
		String sql = "";
		if(region.equals("nbslj")){			
			sql = "select * from ZT_ResTeamStatics";
		}else{
			sql = "select * from ZT_ResTeamStatics where regionid = '"+region+"'";
		}
		list = dao.executeQuery(sql);
		return list;
	}	
	//获取防汛物资
	public List<Map<String, Object>> queryFORmaterial(String region) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
		String sql = "";
		if(region.equals("nbslj")){			
			sql = "select * from ZT_SuppliesStatics";
		}else{
			sql = "select * from ZT_SuppliesStatics where regionid = '" + region + "'";
		}
		list = dao.executeQuery(sql);
		return list;
	}	
	
	//执行excel的跟新或者插入操作
	public void importTargetExcel(List<Map<String, Object>> list) {
		//获取全部人员信息
		List<Map<String, Object>> memlist = this.queryFORmember();
		List<Map<String, Object>> isIN = new ArrayList<Map<String, Object>>();	
		//遍历每一条记录，执行更新或者插入操作
		for(Map<String, Object> map:list){			 
             String id = map.get("id").toString();
             String leader_name = map.get("leader").toString();
             String leader = this.queryFORid(leader_name, memlist);
             String master_name = map.get("master").toString();            
             String master = this.queryFORid(master_name, memlist);                       
             String dirver_name = map.get("dirver").toString();
             String dirver = this.queryFORid(dirver_name, memlist);   
             String member1_name =map.get("member1").toString();
             String member1 = this.queryFORid(member1_name, memlist);  
             String member2_name =map.get("member2").toString();
             String member2 = this.queryFORid(member2_name, memlist);  
             String member3_name =map.get("member3").toString();
             String member3 = this.queryFORid(member3_name, memlist);  
             String member = "",member_name = "";
             if(member1.length()>0){
            	 member += member1 + ";";
            	 member_name += member1_name +";";
             }
             if(member2.length()>0){
            	 member += member2 + ";";
            	 member_name += member2_name +";";
             }
             if(member3.length()>0){
            	 member += member3 + ";";
            	 member_name += member3_name +";";
             }
             /*------------ 查询数据库，执行更新或者插入操作--------------*/
             String query = "select * from FXYW_duty where id = "+ id;
             isIN = dao.executeQuery(query);
             if(isIN.size()>0){
            	 String update = "update FXYW_duty set leader=?,leader_name=?,master=?,master_name=?,member=?,member_name=?"
            	 		+ ",dirver=?,dirver_name=?  where  id=?";
            	 Object[] args = { leader,leader_name,master,master_name,member,member_name,dirver,dirver_name,id };
            	 dao.executeSQL(update, args);
             }else{
            	 String insert = "insert into FXYW_duty (id,leader,leader_name,master,master_name,member,member_name,dirver,dirver_name) values (?,?,?,?,?,?,?,?,?)";
            	 Object[] args = {id,leader,leader_name,master,master_name,member,member_name,dirver,dirver_name,};
            	 dao.executeSQL(insert, args);
             }
        }		
	}
	//根据人员名字，获取id
	public String  queryFORid(String name,List<Map<String, Object>> memlist) {
		String idString = "";
		for(Map map : memlist){
			//两个字的名字可能存在空格的情况
			if(map.get("name").toString().replace(" ", "").equals(name.replace(" ", ""))){
				idString = map.get("id").toString();
				break;
			}
		}	
		return idString;		
	}
	//删除某日值班安排
	public boolean deteteFORzbap(String id){
		String sqlstr = "delete from FXYW_duty where id = "+id;
		Boolean isOK = dao.executeSQL(sqlstr);
		return isOK;
	}
	//更新台风数据
	public boolean updateFORtyphoon(String id,String year,String num,String name,String ordby){
		String sqlstr = "update FXYW_typhoon set year = '"+year+"',num='"+num+"',typhoon_name='"+name+"',ordby = '"+ordby+"' where id="+id;
		Boolean isOK = dao.executeSQL(sqlstr);
		return isOK;
	}
	//删除台风消息
	public boolean deleteFORtyphoon(String id){
		//删除台风消息已经对应上报的数据
		String sqlstr = "delete FXYW_person_transfer where typhoon_id = "+id;
		String sql = "delete FXYW_typhoon where id = "+id;
		dao.executeSQL(sqlstr);
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//删除抢险队伍
	public boolean deleteFORteam(String id){
		String sqlstr = "delete from ZT_ResTeamStatics where id = "+id;
		Boolean isOK = dao.executeSQL(sqlstr);
		return isOK;
	}
	//获取转移人员的数据
	public List<Map<String, Object>> getFORtransfer(String year,String type,String region){
 		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		if( type.equals("edit") ){
			if(region.equals("nbslj")){
				Map<String, Object> map = new java.util.HashMap<String, Object>();
				map.put("typhoon_name", "该权限暂无数据");
				map.put("year", "");
				map.put("num","");
				map.put("id", "");
				list.add(map);
			}else{
				sql = "select * from FXYW_typhoon where year = '"+year+"' order by ordby";
				list = dao.executeQuery(sql);
			}		
		}else{
			sql = "select * from FXYW_typhoon where year = '"+year+"' order by ordby";
			list = dao.executeQuery(sql);
		}	
		return list; 
	}
	//获取区县下面上报转移人口的数据
	public List<Map<String, Object>> getFORper_transfer(String id,String region) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from FXYW_person_transfer where typhoon_id = '"+id+"' and regionid = '"+region+"'";
		list = dao.executeQuery(sql);
		return list;
	}
	//根据权限获取闸门
	public List<Map<String, Object>> getALLgateBYregion(String region) {
		String sql = "";
		if(region.equals("nbslj")){
			sql = "select ENNM from FXYW_drainage_gate";
		}else{
			String sql1 = "select regionnm from sys_region where regionid = '"+region+"'";
			String regionnm = dao.executeQueryObject(sql1).get("regionnm").toString();
			sql = "select ENNM from FXYW_drainage_gate where city = '"+regionnm+"'";
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);	
		return list;
	}
	//根据权限获取水库
	public List<Map<String, Object>> getALLresBYregion(String region) {
		String sql = "";
		if(region.equals("nbslj")){
			sql = "select ENNM from FXYW_drainage_res";
		}else{
			String sql1 = "select regionnm from sys_region where regionid = '"+region+"'";
			String regionnm = dao.executeQueryObject(sql1).get("regionnm").toString();
			sql = "select ENNM from FXYW_drainage_res where CITY = '"+regionnm+"'";
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);	
		return list;
	}
	//获取人口转移关于某一条台风的转移人口数据
	public List<Map<String, Object>> getFORtrandferbyid(String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select a.*,b.regionnm from FXYW_person_transfer as a,sys_region as b where a.regionid = b.regionid and a.typhoon_id = "+id;
		list = dao.executeQuery(sql);	
		return list;	
	}
	//获取某一条预排预泄的数据
	public List<Map<String, Object>> getFORdrainferbyid(String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from FXYW_drainage where job_id = '"+id+"'order by city";
		list = dao.executeQuery(sql);	
		return list;	
	}
	//预排预泄前台展示
	public List<Map<String, Object>> queryFORdrainageRead(String start,String end,String token) {
	
		String sql1 = "select id from sys_user where loginname = '"+token+"'";
		String id = ","+dao.executeQueryObject(sql1).get("id").toString()+",";
		String sql = "select * from FXYW_drainage where eTime>= '"+start+"' and sTime <= '"+end+"' order by sTime";		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);	
		for (int i = 0; i < list.size(); i++) {
			if( list.get(i).get("isRead").toString().contains( id ) ){
				list.get(i).put("isRead", "1");
			}else{
				list.get(i).put("isRead", "0");
			}
		}
		return list;	
	}	
	//获取所有预排预泄闸门数据
	public List<Map<String, Object>> queryFORdrainageGate() {		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select ENNM,area,forwhere from FXYW_drainage_gate";
		list = dao.executeQuery(sql);	
		return list;	
	}
	//新建上报任务
	public boolean insertFORdrainage(String year){
		String sql = "insert into FXYW_drainage_job (job_name,start,year,ordby,res,gate) values ('上报任务名称','','"+year+"','0','1','1')";
		boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//新增预排预泄-闸门
	/*public boolean insertFORdrainageG(String ENNM,String start,String end,String num,String jobid){
		//获取水库的县市区
		String sql1 = "select top 1 CITY from P203_GATE where ENNM = '"+ENNM+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao_slpc.executeQuery(sql1);
		if( list.size() > 0 ){
			String CITY = list.get(0).get("CITY").toString();
 			String sql = "insert into FXYW_drainage (ENNM,sTime,eTime,num,type,CITY,isRead,job_id) values ('"+ENNM+"','"+start+"','"+end+"','"+num+"','0','"+CITY+"',',','"+jobid+"')";
			return dao.executeSQL(sql);	
		}else{
			return false;
		}	
	}*/
	//预排预泄编辑
	/*public boolean updateFORdrainage(String type,String id,String ENNM,String start,String end,String num){
		//编辑预排预泄
		String sql = "";
		if(type.equals("1")){
			//水库
			String sql1 = "select top 1 CITY from FXYW_drainage_res where ENNM = '"+ENNM+"'";
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = dao.executeQuery(sql1);
			if( list.size() > 0 ){
				String CITY = list.get(0).get("CITY").toString();
	 			sql = "update FXYW_drainage set ENNM = '"+ENNM+"',CITY='"+CITY+"',sTime='"+start+"',eTime='"+end+"',num='"+num+"' where id="+id;
				return dao.executeSQL(sql);	
			}else{
				return false;
			}	
		}
		if(type.equals("0")){
			//闸门
			String sql1 = "select top 1 CITY from P203_GATE where ENNM = '"+ENNM+"'";
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = dao_slpc.executeQuery(sql1);
			if( list.size() > 0 ){
				String CITY = list.get(0).get("CITY").toString();
				sql = "update FXYW_drainage set ENNM = '"+ENNM+"',CITY='"+CITY+"',sTime='"+start+"',eTime='"+end+"',num='"+num+"' where id="+id;
				return dao.executeSQL(sql);	
			}else{
				return false;
			}	
		}
		return true;
	}*/
	public boolean insertFORdrainageR(String ENNM,String start,String end,String num,String jobid){
		//获取水库的县市区
		String sql1 = "select top 1 CITY from FXYW_drainage_res where ENNM = '"+ENNM+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql1);
		if( list.size() > 0 ){
			String CITY = list.get(0).get("CITY").toString();
 			String sql = "insert into FXYW_drainage (ENNM,sTime,eTime,num,type,CITY,isRead,job_id) values ('"+ENNM+"','"+start+"','"+end+"','"+num+"','1','"+CITY+"',',','"+jobid+"')";
			return dao.executeSQL(sql);	
		}else{
			return false;
		}	
	}
	//修改预排预泄任务
	public boolean updateFORdrainagejob(String id,String name,String year,String res,String gate,String start,String ordby){
		//获取水库的县市区
		String isres = "1";
		String isgate = "1";
		if( res.equals("fasle") ){
			isres = "0";
		}
		if( gate.equals("false") ){
			isgate = "0";
		}
		String sql = "update FXYW_drainage_job set job_name='"+name+"',start='"+start+"',ordby='"+ordby+"',res='"+isres+"',gate='"+isgate+"' where id="+id;		
		boolean isok = dao.executeSQL(sql);
		return isok;
	}
	//获取预排预泄数据
	public List<Map<String, Object>> queryFORdrainage(String type,String year,String region) {
		String sql = "";
		if( !type.equals("") ){
			//获取权限下面的数据
			String sql1 = "select regionnm from sys_region where regionid = '"+region+"'";
			String regionnm = dao.executeQueryObject(sql1).get("regionnm").toString();
			sql = "select * from FXYW_drainage where CITY = '"+regionnm+"' and job_id = "+type;
		}else{
			sql = "select * from FXYW_drainage_job where year = '"+year+"' order by ordby";
		}		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);	
		return list;
	}
	//获取转移人员信息
	public boolean insertFORtransfer(String year){
		//插入数据库
		String sql = "insert into FXYW_typhoon (typhoon_name,year,num,ordby) values ('新建台风','"+year+"','0','0') ";
		dao.executeSQL(sql);
		//获取插入的id
		String sql1 = "select max(id) as max from FXYW_typhoon";
		Map<String, Object> map = dao.executeQueryObject(sql1);
		String id = map.get("max").toString();
		//获取所有县市区
		String sql2 = "select regionid,regionnm from sys_region where regionid != 'nbslj' ";
		List<Map<String, Object>> list = dao.executeQuery(sql2);
		for (Map<String, Object> reg : list) {
			String insert = "insert into FXYW_person_transfer (typhoon_id,regionid,htw,htn,cxht,wf,cz,sd,sk,qt,hgbf,zgbf) values ('"+id+"','"+reg.get("regionid").toString()+"',0,0,0,0,0,0,0,0,0,0)";
			dao.executeSQL(insert);
		}
		return true;
	}
	//编辑转移人员信息														
	public boolean updateFORtransfer(String id,String htw,String htn,String cxht,String wf,String cz,String sd,String sk,String qt,String hgbf,String zgbf){		
		String update = "update FXYW_person_transfer set htw='"+htw+"',htn='"+htn+"',cxht='"+cxht+"',wf='"+wf+"',cz='"+cz+"',sd='"+sd+"',sk='"+sk+"',qt='"+qt+"',hgbf='"+hgbf+"',zgbf='"+zgbf+"' where id="+id;
		return dao.executeSQL(update);
	}
	//删除防汛物资
	public boolean deleteFORmaterial(String id){
		String sqlstr = "delete from ZT_SuppliesStatics where id = "+id;
		Boolean isOK = dao.executeSQL(sqlstr);
		return isOK;
	}
	//删除预排预泄
	public boolean deleteFORdrainage(String id){
		String sqlstr = "delete from FXYW_drainage where id = "+id;
		Boolean isOK = dao.executeSQL(sqlstr);
		return isOK;
	}	
	public boolean deleteFORdrainagejob(String id){
		String sqlstr = "delete from FXYW_drainage_job where id = '"+id+"'";
		Boolean isok = dao.executeSQL(sqlstr);
		return isok;
	}
	//删除人口转移
	public boolean deleteFORtransfer(String id){
		String sqlstr = "delete from FXYW_person_transfer where id = "+id;
		Boolean isOK = dao.executeSQL(sqlstr);
		return isOK;
	}
	//更新水闸所属流域
	public boolean updateFORdrainageG(String list){
		String sql1 = "update FXYW_drainage_gate set area = 0";
		dao.executeSQL(sql1);
		String[] arry = list.split(";");
		for (int i = 0; i < arry.length; i++) {
			String[] gate = arry[i].split(",");
			//执行sql语句
			String sql = "update FXYW_drainage_gate set area ='"+gate[1]+"' where ENNM = '"+gate[0]+"'" ;
			dao.executeSQL(sql);
		}
		return true;
	}
	//更新水闸排往地
	public boolean updateFORdrainageG_f(String list){
		String sql1 = "update FXYW_drainage_gate set forwhere = 0";
		dao.executeSQL(sql1);
		String[] arry = list.split(";");
		for (int i = 0; i < arry.length; i++) {
			String[] gate = arry[i].split(",");
			//执行sql语句
			String sql = "update FXYW_drainage_gate set forwhere ='"+gate[1]+"' where ENNM = '"+gate[0]+"'" ;
			dao.executeSQL(sql);
		}
		return true;
	}
	//转移人口阅读
	public boolean updateFORtransferREAD(String id,String token){
		String sql1 = "select id from sys_user where loginname = '"+token+"'";
		String str = dao.executeQueryObject(sql1).get("id").toString()+",";
		String sqlstr = "update FXYW_person_transfer set isREAD = isREAD+'"+str+"' where id ="+id;
		Boolean isOK = dao.executeSQL(sqlstr);
		return isOK;
	}
	//删除预排预泄阅读
	public boolean updateFORdrainageREAD(String id,String token){
		String sql1 = "select id from sys_user where loginname = '"+token+"'";
		String str = dao.executeQueryObject(sql1).get("id").toString()+",";
		String sqlstr = "update FXYW_drainage set isRead = isRead+'"+str+"' where id ="+id;
		Boolean isOK = dao.executeSQL(sqlstr);
		return isOK;
	}
	//修改抢险队伍
	public boolean updateFORteam(Map<String, String> data){
		Map< String , Object> ids = new java.util.HashMap<String, Object>();
		ids.put("id", data.get("id"));
		data.remove("id");
		//将空项补充为0
		for(Entry<String, String> entry : data.entrySet()){			  
	           String strval = entry.getValue();
	           if(strval.equals("")){
	        	   data.put(entry.getKey(), "0");
	           }
	    }	
		dao.updateMap("ZT_ResTeamStatics", ids, data);
		return true;
	}
	//修改防汛物资
	public boolean updateFORmaterial(Map<String, String> data){
		Map< String , Object> ids = new java.util.HashMap<String, Object>();
		ids.put("id", data.get("id"));
		data.remove("id");
		//将空项补充为0
		for(Entry<String, String> entry : data.entrySet()){			  
	           String strval = entry.getValue();
	           if(strval.equals("")){
	        	   data.put(entry.getKey(), "0");
	           }
	    }	
		dao.updateMap("ZT_SuppliesStatics", ids, data);
		return true;
	}
	//新增抢险队伍
	public boolean insertFORteam(Map<String, String> data){
		System.out.println(data);
		String regionid = data.get("regionid").toString();
		List<Map<String, Object>> city = this.queryFORcity("nbslj", "");		
		for (int i = 0; i < city.size(); i++) {
			if( city.get(i).get("regionid").toString().equals( regionid ) ){
				data.put("resTeamPlace", city.get(i).get("regionnm").toString());
			}
		}
		for(Entry<String, String> entry : data.entrySet()){			  
	           String strval = entry.getValue();
	           if(strval.equals("")){
	        	   data.put(entry.getKey(), "0");
	           }
	    }	
		dao.saveMap("ZT_ResTeamStatics", data);
		return true;
	}
	//新建防汛物资
	public boolean insertFORmaterial(Map<String, String> data){
		System.out.println(data);
		String regionid = data.get("regionid").toString();
		List<Map<String, Object>> city = this.queryFORcity("nbslj", "");		
		for (int i = 0; i < city.size(); i++) {
			if( city.get(i).get("regionid").toString().equals( regionid ) ){
				data.put("reservePoint", city.get(i).get("regionnm").toString());
			}
		}
		for(Entry<String, String> entry : data.entrySet()){			  
	           String strval = entry.getValue();
	           if(strval.equals("")){
	        	   data.put(entry.getKey(), "0");
	           }
	    }	
		dao.saveMap("ZT_SuppliesStatics", data);
		return true;
	}
	//删除防汛通知
	public boolean deleteFORnoticelist(String id){
		//删除文件,同时删除文件		
		deleteFORfilename(id);				
		String sql = "delete from FXYW_notice_list where list_id = " + id;
		boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	
	
	
	//短信发送
	public void sendmessage(String PhoneNum, String Message) {
		String result = new SendMessage().YSendMessage(PhoneNum, Message);
		new FxywImpl().savemessage(PhoneNum, Message, result);
	}
	//获取快报当前是第几期
	public int queryFORquickNUM() {
		Calendar now = Calendar.getInstance();  
		int year =  now.get(Calendar.YEAR);
		int month = (now.get(Calendar.MONTH) + 1);
		String sql = "select max(count) as count from  FXYW_quick_report where year ='"+year+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		if(list.get(0).get("count") != null){
			return Integer.parseInt(list.get(0).get("count").toString())+1;
		}else {
			return 1;
		}
	}
	
	//防汛业务短息发送
	public int sendQUNmessage(String name, String phone,String content,String id) {
		//先发短信，然后将数据保存进数据库
		String result = new SendMessage().YSendMessage(phone, content);	
		//String result = "success";
		Date date = new Date();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd hh:mm" );
		String sqlstr = "insert into FXYW_notice_message (name,phone,mes_content,list_id,result,time) values ('"+name+"','"+phone+"','"+content+"','"+id+"','"+result+"','"+sdf.format(date)+"')";
		Boolean isOK = dao.executeSQL(sqlstr);
		if(result.equals("success") && isOK){
			//短信发送成功，数据插入成功，将短信状态改为1
			String update = "update FXYW_notice_list set duanxin = 1 where list_id = " + id;
			dao.executeSQL(update);
			return 1;
		}
		if( result == "success" && ! isOK ){
			//短信发送成功，数据插入失败
			return 2;
		}
		else{
			//短信发送失败
			return 3;
		}
		
	}
	//将短信保存进数据库
	public boolean savemessage(String PhoneNum, String Message,String result) {
		Date date = new Date();
		SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd hh:mm" );	
		String sql = "insert into FXYW_duty_message (phone,message,time,result) values ('"+PhoneNum+"','"+Message+"','"+sdf.format(date)+"','"+result+"')";
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	public boolean updateFORpdfname(String id, String filename) {
		String sql = "update FXYW_yjya set pdfname = '"+filename+"' where id=" + id;
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	
	//获取某日的值班信息
	public Map<String, Object>queryFORreportbyid(String id){
		String sql = "select * from FXYW_quick_report where id = " + id;
		Map<String, Object> map = dao.executeQueryObject(sql);		
		return map;
	}
	//获取山洪快报
	public Map<String, Object>queryFORzbapBYid(String id){
		String sql = "select * from FXYW_duty where id = " + id;
		Map<String, Object> map = dao.executeQueryObject(sql);		
		return map;
	}
	public List<Map<String, Object>> query_for_address(){
		String sql = "select * from FXYW_address_menu order by _order";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;
	}
	public List<Map<String, Object>> query_for_address_edit(String region){
		String sql = "";
		if(region.equals("nbslj")){
			sql = "select * from FXYW_address_menu order by _order";
		}else{
			sql = "select * from FXYW_address_menu where region = '"+region+"' order by _order";
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;
	}
	public List<Map<String, Object>> queryFORmesBYlistid(String id){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from FXYW_notice_message where list_id ="+ id + " order by time desc";
		list = dao.executeQuery(sql);
		return list;
	}
	//获取某个单位通讯录的信息
	public List<Map<String, Object>> queryFORaddmes(String id){
		String sql = "select * from FXYW_address where id = "+ id + "order by _order";
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = dao.executeQuery(sql);
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map = list.get(i);
			
		}
		return list;
	}
	public List<Map<String, Object>> queryFORcity(String region,String type){
		//根据进来的权限，返回不同的县市区
		String sql="";
		if( type.equals("edit") && !region.equals("nbslj")){	
			sql = "select * from sys_region where regionid = '"+region+"' order by ordby";
		}else{
			sql = "select * from sys_region order by ordby";
		}	
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();		
		list = dao.executeQuery(sql);
		for ( Map<String, Object> map : list ) {			
				if(map.get("regionid").equals("nbslj")){
					map.put("regionnm", "市本级");
				}										
		}
		return list;	
	}
	//调查评价
	public List<Map<String, Object>> queryFORpingjia(String region,String type){
		String sql = "";
		if( type.equals("edit") && !region.equals("nbslj")){	
			sql = "select a.*,b.regionnm from FXYW_pingjia as a,sys_region as b where a.regionid = b.regionid and a.regionid = '"+region+"' and b.isprogram=1";
		}else{
			sql = "select a.*,b.regionnm from FXYW_pingjia as a,sys_region as b where a.regionid = b.regionid  and b.isprogram=1";
		}	
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("src", "http://www.htwater.net:8080/shanhong_file/yuan_release/"+list.get(i).get("filename"));
		}
		
		return list;
	}
	public List<Map<String, Object>> queryFORhead(){
		String sql = "select * from FXYW_head order by _order";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;
	}
	//防汛责任人信息
	public List<Map<String, Object>> queryFORheadmes(){
		//token,用户名，region，所在地，根据所在地先获取权限		
		String sql = "";
		sql = "select * from ZT_AllDuty order by ordby";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;
	}
	
	//通知模版列表
	public List<Map<String, Object>> queryFORnoticeTlist(String type){
		String sql;
		if(type.length() == 0){
			 sql = "select temp_id,temp_title,temp_mes,edit_time,type from FXYW_notice ";
		}else{
			 sql = "select temp_id,temp_title,temp_mes,edit_time,type from FXYW_notice where type = " + type;
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;
	}
	//单张模版信息
	public List<Map<String, Object>> queryFORnoticeT(String temp_id){
		String sql = "select * from FXYW_notice where temp_id = " + temp_id;	
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;
	}
	//插入新的通知
	public boolean insertFORnotic_list(String title,String author,String html,String time,String type,String grade){
		String sql = "insert into FXYW_notice_list ( list_title,list_content,author,time,temp_type,grade ) values ( '"+title+"','"+html+"','"+author+"','"+time+"','"+type+"','"+grade+"')";
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//更新防汛责任人信息
	public boolean updateFORhead(String id,String Name,String Unit,String Position,String OfficeTell,String Mobile,String ResType,String Remark,String ordby){
		String sql = "update ZT_AllDuty set Name = '"+Name+"',Unit='"+Unit+"',Position='"+Position+"',OfficeTell='"+OfficeTell+"',Mobile='"+Mobile+"',ResType='"+ResType+"',Remark='"+Remark+"',ordby='"+ordby+"' where ID= '"+id+"' ";
		boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//插入通讯录
	public boolean insertADDRESS(String id){
		String sql = "insert into FXYW_address (id,_order) values ('"+id+"',0) ";
		boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//删除通讯录信息
	public boolean deleteADDRESS(String id){
		String sql = "delete from FXYW_address where mem_id= " + id;
		boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//编辑通讯录信息
	public boolean updateFORaddress(String type,String id,String command,String mem_name,String position,String office,String phone,String home_phone,String email,String _order,String virtual,String _function,String _function_,String _duty){
		String sql = null;
		boolean isOK = false;
		switch (type) {
		case "4":
			sql = "update FXYW_address set command = '"+command+"',mem_name = '"+mem_name+"',position='"+position+"',office='"+office+"',phone='"+phone+"',home_phone='"+home_phone+"',email='"+email+"',_order='"+_order+"' where mem_id='"+id+"'";				
			break;
		case "2":
			sql = "update FXYW_address set office = '"+office+"',phone = '"+phone+"',virtual='"+virtual+"',position='"+position+"',mem_name='"+mem_name+"',_order='"+_order+"' where mem_id='"+id+"'";
			break;
		case "3":
			String function = "";
			if(_function.equals("leader")){
				function = _function;
			}else{
				function = _function_;
			}
			sql = "update FXYW_address set _function = '"+function+"',_duty='"+_duty+"',mem_name = '"+mem_name+"',position='"+position+"',office='"+office+"',phone='"+phone+"',virtual='"+virtual+"',_order='"+_order+"' where mem_id='"+id+"'";
			break;
		case "5":
			sql="update FXYW_address set mem_name='"+mem_name+"',position='"+position+"',office='"+office+"',phone='"+phone+"',email='"+email+"',_order='"+_order+"',home_phone='"+home_phone+"' where mem_id='"+id+"'";
			break;
		default:
			break;
		}
		isOK = dao.executeSQL(sql);	
		return isOK;
	}	
	//通讯录目录
	public boolean updateFORaddress_menu(String type,String id,String unit_name,String postcode,String address,String tel,String email,String fax,String zf_address,String zf_postcode,String zf_phone,String zf_fax,String other){
		String sql = null;
		boolean isOK = false;
		switch (type) {
		case "4":
			sql = "update FXYW_address_menu set unit_name = '"+unit_name+"',postcode='"+postcode+"',address='"+address+"',tel='"+tel+"',email='"+email+"',fax='"+fax+"',zf_address='"+zf_address+"',zf_postcode='"+zf_postcode+"',zf_phone='"+zf_phone+"',zf_fax='"+zf_fax+"' where id="+id;				
			break;
		case "2":
		case "3":
			sql = "update FXYW_address_menu set address = '"+address+"',fax='"+fax+"',tel='"+tel+"',other='"+other+"'  where id="+id;	
			break;
		case "5":
			sql = "update FXYW_address_menu set unit_name = '"+unit_name+"',address='"+address+"',tel='"+tel+"',email='"+email+"',postcode='"+postcode+"',fax='"+fax+"' where id="+id;	
			break;
		default:
			break;
		}
		isOK = dao.executeSQL(sql);	
		return isOK;
	}		
	//编辑通讯录信息 updateFORaddress为类型为5，宁波农业处。。
	public boolean updateFORaddress_2(String id,String office,String phone,String virtual,String position,String mem_name,String _order){
		String sql = "update FXYW_address set office = '"+office+"',phone = '"+phone+"',virtual='"+virtual+"',position='"+position+"',mem_name='"+mem_name+"',_order='"+_order+"' where mem_id='"+id+"'";
		boolean isOK = dao.executeSQL(sql);
		return isOK;		
	}	
	//删除防汛责任人
	public boolean deleteFORhead(String id){
		String sql = "delete from ZT_AllDuty where ID = " + id;
		boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	public boolean addFORhead(String city,String type,String Name,String Unit,String Position,String OfficeTell,String Mobile,String ResType,String Remark,String ordby){
		String sql = "insert into ZT_AllDuty (Name,Unit,Position,OfficeTell,Country,Mobile,Type,Remark,ResType,ordby) values ('"+Name+"','"+Unit+"','"+Position+"','"+OfficeTell+"','"+city+"','"+Mobile+"','"+type+"','"+Remark+"','"+ResType+"','"+ordby+"') ";
		boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//防汛通知模版插入
	public boolean insertFORnoticT(String title,String message,String html,String type){
		
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );				
		Date date = new Date();
				
		String sql = "insert FXYW_notice (temp_title,temp_mes,temp_content,type,edit_time) values ('"+title+"','"+message+"','"+html+"','"+type+"','"+sdf.format(date)+"')";
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//更新模板
	//防汛通知模版插入
	public boolean updateFORnoticeT(String temp_id,String temp_title,String temp_mes,String temp_content,String type){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );				
		Date date = new Date();
		
		String sql = "update FXYW_notice set temp_title ='"+temp_title + "',temp_mes = '"+temp_mes+"',temp_content='"+temp_content+"',type='"+type+"',edit_time='"+sdf.format(date)+"'  where temp_id= '" +temp_id+"'";
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//删除扫描件
	public boolean deleteFORfilename(String id){
		//删除对应文件
		/*遇到问题，如果进程中打开了这个文件，则删除失败，但png还是能删除，此处有时间后期修改     creat by fanghaojie*/		
		String query = "select filename,fnString,count from FXYW_notice_list where list_id = " + id;
		Map< String , Object> result = new java.util.HashMap<String, Object>();
		result = dao.executeQueryObject(query);
		//删除tif，删除png
		String fnString = result.get("fnString").toString();
		int count =  Integer.parseInt(result.get("count").toString());
		String filename = result.get("filename").toString();		
		if( !filename.equals("0")){
			deleteFile(uploadPath+filename);
			//删除所有png
			for (int i = 0; i < count; i++) {
				deleteFile( uploadPath + fnString + "_" + i + ".png");
			}
			String sql = "update FXYW_notice_list set orgname = '0',filename ='0',fnString = '0',count='0' where list_id = '"+id+"'";
			boolean isOK = dao.executeSQL(sql);		
			return isOK;
		}else{
			return true;
		}
		
	}
	//更新通知
	public boolean updateFORnotic(String title,String author,String html,String id){
		String sql = "update FXYW_notice_list set list_title = '"+title+"',author='"+author+"',list_content = '"+html+"' where list_id = "+id;
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//删除模板信息
	public boolean deleteFORnoticeT(String temp_id){
		String sql = "delete from FXYW_notice where temp_id ="+temp_id;
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//更新扫描件
	public boolean updateFORfile(String orgname,String filename,String id,String count,String fnString){
		String sql = "update FXYW_notice_list set orgname = '"+orgname+"',filename = '"+filename+"',count='"+count+"',fnString='"+fnString+"' where list_id = '"+id+"'";
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//更新防汛预案信息
	public boolean deleteFORfxya(String id){
		String sql = "delete from FXYW_yjya where id="+id;
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	
	/*public boolean updateFORdutyin(String id,String type,String password,String man_name,String man_id){
		man_name =  man_name.replaceAll(" ", "");
		String sql = "select * from UserInfo where replace(TrueName,' ','') = '"+man_name+"' and Passwd='"+password+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao_USERS.executeQuery(sql);
		if( list.size() > 0 ){
			//登陆成功，开始按照类型，操作数据库
			String sqlin = "";
			man_id = ","+man_id+",";
			if(type.equals("T")){
				sqlin = "update FXYW_duty set isduty = '"+man_id+"' where id = " + id;
			}else{
				sqlin = "update FXYW_duty set isduty = isduty + '"+man_id+"' where id =" + id;
			}
			dao.executeSQL(sqlin);
			return true;
		}else{
			return false;
		}	
	}*/
	/*public boolean updateFORdutychange(String id,String name,String password,String duty){
		name =  name.replaceAll(" ", "");
		String sql = "select * from UserInfo where replace(TrueName,' ','') = '"+name+"' and Passwd='"+password+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao_USERS.executeQuery(sql);
		if( list.size() > 0 ){
			id = ","+id+",";
			String sqlin = "update FXYW_duty set isduty = '"+id+"' where id = " + duty;
			dao.executeSQL(sqlin);
			return true;
		}else{
			return false;
		}				
	}*/
	/*public boolean updateFORdutyout(String id,String password,String man_name,String man_id){
		man_name =  man_name.replaceAll(" ", "");
		String sql = "select * from UserInfo where replace(TrueName,' ','') = '"+man_name+"' and Passwd='"+password+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao_USERS.executeQuery(sql);
		if( list.size() > 0 ){
			//退出值班
			man_id = ","+man_id+",";
			String sqlout = "update FXYW_duty set isduty = replace(isduty,'"+man_id+"','') where id =" + id;
			dao.executeSQL(sqlout);
			return true;
		}else{
			return false;
		}		
	}*/

	//新建山洪快报
	public boolean insertFORquickreport(String report){
		Calendar now = Calendar.getInstance();  
		int year =  now.get(Calendar.YEAR);
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );				
		Date date = new Date();
		int count = new FxywImpl().queryFORquickNUM();
		String sql = "insert into FXYW_quick_report (year,time,report,count) values ('"+year+"','"+sdf.format(date)+"','"+report+"','"+count+"')";
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//获取通知模版列表
	public List<Map<String, Object>> queryFORNotice_List(String time1,String time2,String key){
		String sql = "select temp_type,list_id,list_title,author,time,saomiao,chuanzhen,duanxin,weixin,gongzhong,filename from FXYW_notice_list where time >= '"+time1+"' and time <= '"+time2+"' and list_title like '%"+key+"%' order by time desc";		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;				
	}
	//获取具体通知列表的信息
	public List<Map<String, Object>> queryFORNotice(String id){
		String sql = "select * from FXYW_notice_list where list_id =" + id;		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;	
	}
	//未处理日志
	public List<Map<String, Object>> queryFORlog(){
		String sql = "select * from FXYW_duty_log where radio_d = 'no' order by time desc";		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;
	}
	public boolean updateFORlogdo(String id){
		String sql = "update FXYW_duty_log set radio_d = 'yes' where id = " + id;			
		boolean isok = dao.executeSQL(sql);
		return isok;
	}
	//提交日志
	public boolean updateFORlog(String type,String shijian,String radio_d,String radio_n,String yijian,String time){
		String sql = "insert into FXYW_duty_log (type,shijian,radio_d,radio_n,yijian,time) values ('"+type+"','"+shijian+"','"+radio_d+"','"+radio_n+"','"+yijian+"','"+time+"')";
		boolean isok = dao.executeSQL(sql);
		return isok;
	}
	//获取山洪快报
	public List<Map<String, Object>> queryFORquickreport(String start,String end){
		
		String sql = "select year,id,time,count from FXYW_quick_report where time>='"+start+"' and time <='"+end+"' order by time desc,count desc";		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;	
	}
	//根据id获取值班情况
public List<Map<String, Object>> queryFORdutybyid(String id){
		
		String sql = "select * from FXYW_duty where id ="+id;		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;	
	}
	//是否允许交接班
	public boolean queryFORchange(String id){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		java.util.Date d2;
		try {		
			d2 = sdf.parse("2015-01-01");
			Date date = new Date(); 
			Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
			int hour = c.get(Calendar.HOUR_OF_DAY); 
		    long diff = date.getTime() - d2.getTime();    
		    long days = diff / (1000 * 60 * 60 * 24);
		    //今天
		    if( Float.parseFloat(id) < days && hour >= 8){
		    	return true;
		    }else{
		    	return false;
		    }
		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	//获取当天值班的人员
	public List<Map<String, Object>> queryFORduty(){
		//当前时间与2015-1-1的时间差
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		java.util.Date d2;
		try {		
			d2 = sdf.parse("2015-01-01");
			Date date = new Date(); 		
		    long diff = date.getTime() - d2.getTime();    
		    long days = diff / (1000 * 60 * 60 * 24);
		    //days为今日值班编号，判断是显示昨日还是今日	    
			String sql = "select * from FXYW_duty where id >= '"+(days-1)+"' and id <= '"+days+"' order by id";
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = dao.executeQuery(sql);
			if( list.size() == 2 ){
				if( list.get(1).get("isduty") == null  ){		
					if( list.get(0).get("isduty") == null ){
						list.remove(0);
					}else{
						list.remove(1);
					}		
				}else{
					list.remove(0);
				}
			}
			return list;		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
		
	public List<Map<String, Object>> queryFORfxya(String type,String region){
		String sql = "select * from [防汛预案] order by id";		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		return list;	
	}
	public List<Map<String, Object>> queryforlogbytime(String time1,String time2){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from FXYW_duty_log where time >='"+time1+"' and time <= '"+time2+"' order by time";
		list = dao.executeQuery(sql);
		return list;
	}
	public List<Map<String, Object>> queryFORpubliclist(String id){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from FXYW_notice_public where list_id = '"+id+"' order by time";
		list = dao.executeQuery(sql);
		return list;
	}	
	public boolean updateFORpublic(String title,String id,String isCommend,String newsType,String also,String grade,String content){
		String urlString = "http://fxfh.nbwater.gov.cn/admin/publicNewsAdd.php";		
		try {
			title = java.net.URLEncoder.encode(title,"utf-8");
			content = content.replaceAll("\\s{1,}", " ");
			content = java.net.URLEncoder.encode(content,"utf-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String param = "title="+title+"&content="+content+"&isCommend="+isCommend+"&newsType="+newsType+"&key=abc123";		
		String resultString = "";	
		resultString = sendPost(urlString,param);			
		System.out.println(resultString);
		if( resultString.contains("e") ){
			return false;
		}else{
			//提交成功，保存类型与id
			String gid = "";
			String type= "";
			if(newsType.equals("5")){
				gid = "0";
				type = "通知公告";
			}else {
				gid = "1";
				type = "应急响应";
			}
			String sqlString = "update FXYW_notice_list set gid = '"+gid+"',arc_id='"+resultString+"',gongzhong ='1' where list_id = '"+id+"'";
			Boolean isOK = dao.executeSQL(sqlString);
			//创建这条发布的记录用来保存信息
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			Date date = new Date();
			String updateString = "insert FXYW_notice_public (list_id,type,time) values ('"+id+"','"+type+"','"+sdf.format(date)+"')";
			dao.executeSQL(updateString);
			//同步更新汛情等级
			if( also.equals("1") ){
				SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				String insertstr = "insert into YJXY (TM,GRADE) values('"+sdf1.format(date)+"','"+grade+"')";
				dao.executeSQL(insertstr);
			}
		}
		return true;
	}
	//传真文件绑定
	public boolean updateListFax(String id,String fax_name){
		//获取tif文件，将文件与传真对应起来
		File[] files = new File("D://shanhong_fxyw/file_bak/").listFiles();
		for (File file : files) {
			String name = file.getName();
			if(name.equals(fax_name)){
				//将文件取出
				FileOutputStream fout = null;  
				FileInputStream fin = null;  
				try {
					fout = new FileOutputStream("D://shanhong_fxyw/file/"+fax_name);
					fin = new FileInputStream("D://shanhong_fxyw/file_bak/"+fax_name);  
					byte[] buffer = new byte[1444];
					int byteread = 0;  
					while( (byteread = fin.read(buffer)) != -1){  	                    
	                    fout.write(buffer,0,byteread);  
	                } 
					if(fin != null){  
	                    fin.close();//如果流不关闭,则删除不了旧文件  
	                } 
					/*-----------------将tif全部转为jpg，方便前台显示-----------*/
					FileSeekableStream ss = new FileSeekableStream("D://shanhong_fxyw/file/"+fax_name);
					TIFFDecodeParam param0 = null;
					TIFFEncodeParam param = new TIFFEncodeParam();
					JPEGEncodeParam param1 = new JPEGEncodeParam();
					ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss, param0);
					int count = dec.getNumPages();
					param.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
					param.setLittleEndian(false); // Intel
					String fnString = fax_name.replace(".tif", "");
					System.out.println("This TIF has " + count + " image(s)");
					for (int j = 0; j < count; j++) {
			            RenderedImage page = dec.decodeAsRenderedImage(j);
			            File f = new File(uploadPath+ fnString + "_" +j + ".png");
			            System.out.println("Saving " + f.getCanonicalPath());
			            ParameterBlock pb = new ParameterBlock();
			            pb.addSource(page);
			            pb.add(f.toString());
			            pb.add("JPEG");
			            pb.add(param1);
			            //JAI.create("filestore",pb);
			            RenderedOp r = JAI.create("filestore",pb);
			            r.dispose();	            
			            //RenderedOp op = JAI.create("filestore", page, "./zhaoming_" + i + ".jpg", "JPEG", param1);
			        }
					//信息保存到数据库
					String sql = "update FXYW_notice_list set orgname = '"+fax_name+"',filename = '"+fax_name+"',fnString='"+fnString+"',count='"+count+"' where list_id = '"+id+"'";
					dao.executeSQL(sql);			
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		return true;
	}
	
	
	//查询传真发送状态
	public String  queryFORfaxresult(String id){		
		String sql = "select fax_name,fax_phone,fax_result from FXYW_notice_list where list_id = "+id;			
		Map<String, Object> map = dao.executeQueryObject(sql);	
		String result = map.get("fax_name").toString() + "%&" + map.get("fax_phone").toString() + "%&" + map.get("fax_result").toString();			
		return result;		
		/*String sql = "select fax_name,fax_phone,fax_result from FXYW_notice_list where list_id = "+id;			
		Map<String, Object> map = dao.executeQueryObject(sql);	
		String fax_result = map.get("fax_result").toString();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String result[] = fax_result.split(",");
		String name[] = map.get("fax_name").toString().split(",");
		//根据fax_result 查询传真发送状态
		//发送失败:Busy,发送失败:Busy,
		String state = "";
		
		try {
			
			URL url=new URL("http://10.19.36.150:8080");
			URLConnection conn = url.openConnection();			
			String str=conn.getHeaderField(0);
			if ( str!=null && str.indexOf( "OK ")> 0){
				state = sendGet("http://10.19.36.150:8080/fxyw/sendMes!UserManage","res="+fax_result);	
			}else{
								
			}		
			System.out.println(state);
			//String state = "发送失败:Busy,发送失败:Busy,";
			if( state != "" ){	
				String[] arr_state = state.split(",");
				Map<String, Object> map1 = new java.util.HashMap<String, Object>();;
				for (int i = 0; i < result.length; i++ ) {	
					map1.put("name", name[i] );
					map1.put("state", arr_state[i] );
					list.add(map1);
				}
			}else{
				Map<String, Object> map1 = new java.util.HashMap<String, Object>();;
				for (int i = 0; i < result.length; i++ ) {				
					map1.put("name", name[i] );				
					list.add(map1);
				}	
			}
			
			return list;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		*/
			
	}
	
	//发送微信
	public boolean updateFORisweixin(String id,String first,String warnno,String warn_level,String warn_area,String time,String remark){
		
		
		try {
			first = java.net.URLEncoder.encode(first,   "utf-8");
			warnno = java.net.URLEncoder.encode(warnno,   "utf-8");
			warn_level = java.net.URLEncoder.encode(warn_level,   "utf-8");
			warn_area = java.net.URLEncoder.encode(warn_area,   "utf-8");
			time = java.net.URLEncoder.encode(time,   "utf-8");
			remark = java.net.URLEncoder.encode(remark,   "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		StringBuffer readOneLineBuff = new StringBuffer();
		String content ="";
		try {
			
			
			    String gid = "0";
			    String arc_id = "";
				//根据id获取获取微信连接中的两个参数
				if( this.queryFORNotice(id).get(0).get("gid")!= null ){
					gid = this.queryFORNotice(id).get(0).get("gid").toString();
					arc_id = this.queryFORNotice(id).get(0).get("arc_id").toString();
				}else{
					
				}
										
				
				String urlString = "http://www.htwater.net/weixin_shanhong/send.php?first="+first+"&warnno="+warnno+"&warn_level="+warn_level+"&warn_area="+warn_area+
						"&time="+time+"&remark="+remark+"&gid="+gid+"&arc="+arc_id;
				URL url = new URL(urlString);
				URLConnection conn = url.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));		
				String line = "";
				while ((line = reader.readLine()) != null) {
					readOneLineBuff.append(line);
				}
				content = readOneLineBuff.toString();		 
				reader.close();								
					
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		//将这条通知的微信状态修改为1
		String update = "update FXYW_notice_list set weixin = 1 where list_id = "+id;
		Boolean isOK = dao.executeSQL(update);
		/*String sql = "select * from weixin_openid";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dao.executeQuery(sql);
		for (int i = 0; i < list.size(); i++) {
			
			String result = getURLContent(urlString);
		}	*/
		
		
		//String urlString = "http://1.nbfx.sinaapp.com/4/sendall.php?content=" + content;
		//String result = getURLContent(urlString);
		//String sql = "update FXYW_notice_list";
		//Boolean isOK = dao.executeSQL(sql);
		return true;
	}
	
	public String queryFORXML(String id) {
		String sql = "select xml from FXYW_notice_list where list_id = " + id;
		Map<String, Object> map = dao.executeQueryObject(sql);		
		return map.get("xml").toString();
	}
	//获取一段时间内的雨量数据
	/*public Map<String, Object> queryFORrain(String strat,String end ) {
		Map< String , Object> map = new java.util.HashMap<String, Object>();
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {strat,end};
		rtList = dao_nbsqdb.callProcedure("{call proczhsl_雨量站日雨量(?,?)}",args);
		String result = "";
		if (rtList != null) {
			  Collections.sort(rtList, new Comparator<Map<String, Object>>() {		  
		            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		                float map1value = Float.parseFloat(o1.get("VALUE").toString());
		                float map2value = Float.parseFloat(o2.get("VALUE").toString());
		                if((map1value-map2value)<0)   
	                        return 1;  
	                    else if((map1value-map2value)>0)  
	                        return -1;  
	                    else return 0;  
		            }
		        });		
			//获取最大的前5项
			//从2015-05-20 08:00:00至2015-05-27 08:00:00累计降雨量最大的5个测站分别是：黄避岙乡黄避岙G测站45.0毫米,贤庠镇下庄G测站7.0毫米,石浦镇新港区G测站3.0毫米,茅洋乡界河G测站2.5毫米,爵溪街道燕山C测站1.0毫米。
			result += "从"+strat+"至"+end+"累计降雨量最大的5个测站分别是：";
			for (int i = 0; i < 5; i++) {
				result += rtList.get(i).get("STNM").toString()+rtList.get(i).get("VALUE").toString()+"毫米，";
			}
			result = result.substring(0, result.length()-1) + "。";
			
		}else{
			result = "暂时无法获取雨量数据";
		}
		
		String html = "<p style='text-align:center;'><span style='font-family:宋体;font-size:29px;line-height:20px;'>宁波市山洪灾害快报</span></p><p style='text-align:left;'><span style='font-family:宋体;font-size:29px;line-height:20px;'><span style='font-size:12px;'></span><strong><span style='font-size:12px;'>一、水雨情简述</span></strong></span></p><p style='text-indent:2em;'>"+
				"<span style='font-size:12px;'>"+ result +
				"</span></p><strong><span style='font-size:12px;'>二、灾情简述</span></strong> <p style='text-indent:2em;'><span style='font-size:12px;'>暂无灾情描述</span> </p><strong><span style='font-size:12px;'>三、当前采用的防汛应急措施</span></strong><p style='text-indent:2em;color:#CCCCCC;'><span style='font-size:12px;'>请填写当前采取的应急响应措施</span></p><strong><span style='font-size:12px;'>四、下一步需要重点做好的工作</span></strong><p style='text-indent:2em;color:#CCCCCC;'><span style='font-size:12px;'>请填写下一步需要重点做好的工作</span></p>";
		map.put("html", html);
		return map;
	}*/
	
	public boolean updateFAXresult(String result,String id){
		//大于0 SubSendFaxListID序号 等于0 插入不成功 小于0 异常
		//<SubSendFaxListIDTable>
		//<SubSendFaxListIDRow>
		//<SubSendFaxListID>1258</SubSendFaxListID>
		//</SubSendFaxListIDRow>……			
		//</SubSendFaxListIDTable>		
		//result = "<SubSendFaxListIDTable><SubSendFaxListIDRow><SubSendFaxListID>1258</SubSendFaxListID></SubSendFaxListIDRow><SubSendFaxListIDRow><SubSendFaxListID>1259</SubSendFaxListID></SubSendFaxListIDRow></SubSendFaxListIDTable>";
		
/*		返回类型
  		<SubSendFaxListTable>
		<SubSendFaxListIDRow>
		<SubSendFaxListID>1599</SubSendFaxListID>
		</SubSendFaxListIDRow>		
		<SubSendFaxListIDRow>
		<SubSendFaxListID>1600</SubSendFaxListID>
		</SubSendFaxListIDRow>		
		</SubSendFaxListTable>*/	
		//result = result.replaceAll("<SubSendFaxListTable>", "").replaceAll("</SubSendFaxListTable>", "").replaceAll("<SubSendFaxListIDRow><SubSendFaxListID>", "").replaceAll("</SubSendFaxListID></SubSendFaxListIDRow>", ",");	
		String sql = "update FXYW_notice_list set fax_result = '"+result+"' , chuanzhen = 1 where list_id =" + id;
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	
	//发送传真
	public boolean sendQUNfax(String id,String name,String phone,String id_list){
		/*----------------是否已经上传传真文件--------------*/
		String sql_sm = "select * from FXYW_notice_list where list_id = " + id;
		Map<String, Object> smMap = dao.executeQueryObject(sql_sm);
		if(smMap.get("orgname") != null ){
			/*-------------------发送单位----------------*/
			String xml_unit = "";
			String[] arr_phone = phone.split(",");
			String[] arr_name = name.split(",");
			for (int i = 0; i < arr_phone.length; i++) {
				xml_unit += "<SubSendFaxListRow><Receiver></Receiver><FaxNumber>"
							+arr_phone[i]+
							"</FaxNumber><FaxType>0</FaxType><ReceiverCompany></ReceiverCompany></SubSendFaxListRow>";		
			}
			xml_unit = "<SubSendFaxListTable>"+xml_unit+"</SubSendFaxListTable>";
			/*-----------------发送文件------------------*/
			String path = "D://shanhong_fxyw/file/";
			String filename =  smMap.get("filename").toString();
			String fileName = path+"/"+filename;
			try {
			
			FileInputStream in = new FileInputStream(fileName);
			byte[] b=new byte[in.available()];
			
			in.read(b);//将文件中的内容读取到字节数组中		
			int FaxFileSize = in.available();
			String bits = "";
			BASE64Encoder encoder = new BASE64Encoder();  
			bits =  encoder.encode(b);
			in.close();
			
			//时间
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );				
			Date date = new Date();
			String FaxFileType = new MimetypesFileTypeMap().getContentType(fileName);				
			/*----------------传真发送测试-------------*/		
				String XML = "";
				String faxfile = 
						"<FaxFileTable>"+
						"<File>"+
						"<FaxFileName>"+smMap.get("orgname").toString()+"</FaxFileName>"+
						"<FaxFile>"+bits+"</FaxFile>"+
						"<FaxFileType>"+FaxFileType+"</FaxFileType>"+
						"<FaxFileSize>"+FaxFileSize+"</FaxFileSize>"+
						"</File>"+
						"</FaxFileTable>";
				XML = "<SendFaxListTable>"
						+ "<SendFaxListRow>"
						+ "<Subject>"+filename+"</Subject>"
						+ "<Message>"+filename+"</Message>"
						+ "<SendFaxDateTime>"+sdf.format(date)+"</SendFaxDateTime>"
						+ "<ScheduledDateTime></ScheduledDateTime>"
						+ "<Priority>3</Priority>"
						+ "<Barcode></Barcode>"
						+ xml_unit
						+ faxfile		
						+ "</SendFaxListRow>"
						+ "</SendFaxListTable>";				
				String update = "update FXYW_notice_list set xml = '" + XML + "',fax_name='"+name+"',fax_phone='"+phone+"' where list_id = " + id;
				Boolean ok = dao.executeSQL(update);							
				return ok;			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}else{
			return false;
		}
		return false;		
	}	
	public static String byte2bits(byte b) {
		  int z = b; z |= 256;
		  String str = Integer.toBinaryString(z);
		  int len = str.length(); 
		  return str.substring(len-8, len);
	}
	//新增防汛预案
	public boolean insertFORfxya(String title,String regionnm,String filename){
		//[regionid],[pdfname],[ordby],[edittime]
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );				
		Date date = new Date();
		//sdf.format(date)
		String sql = "insert into FXYW_yjya (title,regionid,pdfname,ordby,edittime) values ('"+title+"','"+regionnm+"','"+filename+"','0','"+sdf.format(date)+"') ";
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//更新防汛预案
	public boolean updateFORpingjia(String city,String filename){
		//[regionid],[pdfname],[ordby],[edittime]
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );				
		Date date = new Date();
		//sdf.format(date)
		String sql = "update FXYW_pingjia set filename = '"+filename+"',edittime='"+sdf.format(date)+"' where regionid = '"+city+"'";
		Boolean isOK = dao.executeSQL(sql);
		return isOK;
	}
	//获取url返回结果
	public static String getURLContent(String urlStr) {               
	       /** 网络的url地址 */        
	    URL url = null;              
	       /** http连接 */    
	    HttpURLConnection httpConn = null;            
	        /**//** 输入流 */   
	    BufferedReader in = null;   
	    StringBuffer sb = new StringBuffer();   
	    try{     
	     url = new URL(urlStr);     
	     in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") );   
	     String str = null;    
	     while((str = in.readLine()) != null) {    
	      sb.append( str );     
	            }     
	        } catch (Exception ex) {   
	            
	        } finally{    
	         try{             
	          if(in!=null) {  
	           in.close();     
	                }     
	            }catch(IOException ex) {      
	            }     
	        }     
	        String result =sb.toString();     
	        System.out.println(result);     
	        return result;    
	        }  
	//删除文件
	public boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    

	@Override
	public List<Map<String, Object>> getBizai() {
		String sql="select * from FXYW_BIZAI";
		List<Map<String,Object>> result = dao.executeQuery(sql);
		return result;
	}
	
	@Override
	public Map<String, Object> publishYJXY(String TM,String GRADE,String TITLE,String Source,String CONTENT) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = " insert into [dbo].[YJXY] ([TM],[GRADE],[TITLE],[Source],[CONTENT]) values(?,?,?,?,?)";
		if(dao.executeSQL(sql, new Object[]{TM,GRADE,TITLE,Source,CONTENT})){
			result.put("msg", "success");
		}else{
			result.put("msg", "保存失败");
		}
		return result;
	}
	@Override
	public Map<String, Object> getCurrentResponse() {
		Map<String,Object> result = new HashMap<String,Object>();
		Date today = new Date();
		String nowString = DateUtil.getToday();
		String nowYear = nowString.substring(0, 4);
		Date start = DateUtil.str2Date(nowYear + "-04-16", "yyyy-MM-dd");
		Date end = DateUtil.str2Date(nowYear + "-10-16", "yyyy-MM-dd");
		if(today.after(start) && today.before(end)){//汛期
			String sql = "select top 1 a.TM,a.GRADE,b.NAME,b.INFO,b.DETAIL1,b.DETAIL2,a.TITLE,a.CONTENT from yjxy as a left join yjxy_grade as b on a.grade=b.grade order by tm desc";
			result = dao.executeQueryObject(sql);
			if(!result.get("TM").toString().substring(0, 4).equals(nowYear)){//汛期最新数据居然不是今年
				result.put("GRADE", 0);
				result.put("INFO", "汛期-非应急响应状态");
				result.put("NAME", "汛期");
			}
		}else{//非汛期
			result.put("GRADE", -1);
			result.put("INFO", "非汛期");
			result.put("NAME", "非汛期");
		}
		return result;
	}
	
}

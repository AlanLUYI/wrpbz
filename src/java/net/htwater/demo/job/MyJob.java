package net.htwater.demo.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.htwater.hos.service.impl.FxywImpl;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ess.OtherSigningCertificate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;



public class MyJob implements Job {	

	Logger logger = Logger.getLogger(this.getClass());
	public void execute(JobExecutionContext arg0) {
		logger.info("定时发送短信");
		List<Map<String,Object>> list = null;
		//超级管理员已安排您在。。。年。月。日的组员岗位值班，如有疑问，请与值班安排人员联系，谢谢
		//new FxywImpl().sendmessage(PhoneNum, Message);
		list = new FxywImpl().queryFORmember();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		try {
			java.util.Date d2 = sdf.parse("2015-01-01");
			String date = sdf.format( new Date() );
			java.util.Date d1 = sdf.parse( date );
			long diff = d1.getTime() - d2.getTime();    
		    long days = diff / (1000 * 60 * 60 * 24) + 1;
			//获取id = days的值班信息
		    Map<String, Object> map = new FxywImpl().queryFORzbapBYid(String.valueOf(days));
		    //组装    
		    if(map==null)return;
		    if(map.size()>0){
		    	 Calendar cal = Calendar.getInstance();	   
	    		 cal.add(Calendar.DATE, 1);
		    	 String leader = map.get("leader").toString();
		    	 if(leader.length()>0){
		    		 String PhoneNum = query_phone(leader,list);
		    		 String Message ="已安排您在"+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+(cal.get(Calendar.DATE))+"日的带班领导岗位值班，如有疑问，请与值班安排人员联系，谢谢。";		
		    		 new FxywImpl().sendmessage(PhoneNum, Message);
		    	 }
		    	 String master = map.get("master").toString();
		    	 if(master.length()>0){
		    		  String PhoneNum = query_phone(master,list);
		    		  String Message ="已安排您在"+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+(cal.get(Calendar.DATE))+"日的组长岗位值班，如有疑问，请与值班安排人员联系，谢谢。";		    	  		    		
		    		  new FxywImpl().sendmessage(PhoneNum, Message);
		    	 }
		    	 String member = map.get("member").toString();
		    	 if(member.length()>0){
			    	 String[] mem = member.split(";");
			    	 for(int i=0;i<mem.length;i++){
			    		String PhoneNum = query_phone(mem[i],list);
			    		String Message ="已安排您在"+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+(cal.get(Calendar.DATE))+"日的组员岗位值班，如有疑问，请与值班安排人员联系，谢谢。";		    	  		    		
			    		new FxywImpl().sendmessage(PhoneNum, Message);		    		
			    	 }		    		 
		    	 }
		    	 String dirver = map.get("dirver").toString();
		    	 if(dirver.length()>0){
		    		 String PhoneNum = query_phone(dirver, list);
		    		 	    	  		    		
		    		 //判断是否是开始值周
		    		 Map<String, Object> map_last = new FxywImpl().queryFORzbapBYid(String.valueOf(days-1));
		    		 if(map_last != null){
		    			 String phone_last = map_last.get("dirver").toString();
		    			 if( !phone_last.equals(dirver) ){
		    				 //此处为新值周人员，发送值周短信，获取他在接下来的值周天数count_day
		    				 int count_day = 1;
		    				 Map<String, Object> map1 = null;
		    				 boolean isOthers = false;
		    				 do {							
		    					 map1  = new FxywImpl().queryFORzbapBYid(String.valueOf(days+count_day)); 
		    					 if(map1 != null){
		    						 if(map1.get("dirver").toString().equals(dirver)){
			    						 //为同一人				 
			    						 count_day++;
			    					 }else{
			    						 //人员变化
			    						 isOthers = true;
			    					 }	
		    					 }else{
		    						 isOthers = true;
		    					 }		    					    							
		    				 } while ( !isOthers );   		    				 
		    				 Calendar _cal = Calendar.getInstance();	
		    				 _cal.add(Calendar.DATE, (count_day));
		    				 String Message ="已安排您在"+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+(cal.get(Calendar.DATE))+"日至"+(_cal.get(Calendar.MONTH)+1)+"月"+(_cal.get(Calendar.DATE))+"日值周岗位值班，如有疑问，请与值班安排人员联系，谢谢。";	
		    				 new FxywImpl().sendmessage(PhoneNum, Message);
		    			 }
		    		 }
		    		 
		    	 }	    	 	    	 
		    }		   		    		    		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String  query_phone(String id , List<Map<String,Object>> list) {
		String r_id = "";
		for(Map<String, Object>map : list){
			if(map.get("id").toString().equals(id)){
				r_id =  map.get("tel").toString();
			}
		}
		return r_id;
	}
}

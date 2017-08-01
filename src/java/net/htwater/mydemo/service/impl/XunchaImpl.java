/**
 * fxyw  巡查
 * @author fhj
 * 2015-03-19
 */
package net.htwater.mydemo.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;













import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import net.htwater.mydemo.service.XunchaService;
import net.sf.json.JSONArray;


public class XunchaImpl implements XunchaService {
//	BaseDao YUNRUN = DaoFactory.getDao(HT_YUNRUN);
	BaseDao BLZHSL = DaoFactory.getDao(HT_BLZHSL);
//	BaseDao SHANHONG = DaoFactory.getDao(HT_SHANHONG);
//	BaseDao SLPC = DaoFactory.getDao(HT_SLPC);
	@Override
	public Boolean queryFORUserid(String userid) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = "select * from xuncha_Member where UserID = '"+userid+"'";
		rtList = BLZHSL.executeQuery(sql);
		if( rtList.size() >0 ){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public Boolean insertFORXunchaUser(String Name, String UserID,String Password, String Type, String TypeValue, String Telephone){
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		map.put("Name", Name);
		map.put("UserID", UserID);
		map.put("Password", Password);
		map.put("Type", Type);
		map.put("TypeValue", TypeValue);
		map.put("Telephone", Telephone);
		map.put("ResID", ";");
		return BLZHSL.saveMap("xuncha_Member", map); 
	}
	@Override
	public List<Map<String, Object>> queryFORALLmember() {
		// TODO Auto-generated method stub
		String sql = "select * from xuncha_Member order by Type,TypeValue desc";			
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		List<Map<String, Object>> jiedao = new ArrayList<Map<String, Object>>();
//		jiedao = new SyqServiceImpl().Queryjiedao("yuan", "bl");
		String sql_res = "select id,ResName from xuncha_Res";
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		res = BLZHSL.executeQuery(sql_res);	
		for (int i = 0; i < list.size(); i++) {
			String type = list.get(i).get("Type").toString();
			if(type.equals("jiedao")){
				//遍历jiedao。增加一个字段为街道名称。
				String TypeValue = list.get(i).get("TypeValue").toString();
				for (int j = 0; j < jiedao.size(); j++) {
					String region = jiedao.get(j).get("regionid").toString();
					if (region.equals(TypeValue)) {
						list.get(i).put("regionnm", jiedao.get(j).get("regionnm"));
						break;
					}
				}			
			}
			String resid = list.get(i).get("ResID").toString();
			String a[] = resid.split(";");
			if( a.length>0 ){	
				String resnmString = "";
				for (int j = 1; j < a.length; j++) {
					String resString = a[j];
					for (int k = 0; k < res.size(); k++) {
						String resString2 = res.get(k).get("id").toString();
						if( resString.equals(resString2) ){
							resnmString += res.get(k).get("ResName").toString()+",";
						}
					}
				}
				list.get(i).put("resNM",resnmString.substring(0,resnmString.length()-1) );
			}	
		}
		return list;	
	}
	@Override
	public List<Map<String, Object>> queryMemberRes(String id) {
		// TODO Auto-generated method stub
		String sql = "select * from xuncha_Member where id = '"+id+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		String resid = list.get(0).get("ResID").toString();
		//按";"分割resid字段
		String sql_res = "select id,ResName,isB=0 from xuncha_Res";
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		res = BLZHSL.executeQuery(sql_res);
		String a[] = resid.split(";");
		if( a.length>0 ){
			for (int i = 0; i < a.length; i++) {
				String id1 = a[i];
				for (int j = 0; j < res.size(); j++) {
					String id2 = res.get(j).get("id").toString();
					if( id1.equals(id2) ){
						res.get(j).put("isB", "1");
					}
				}
			}
		}
		return res;
	}
	@Override
	public Boolean addMemberRes(String id, String res) {
		// TODO Auto-generated method stub
		String sql = "update xuncha_Member set ResID = ResID + '"+(res+";")+"' where id='"+id+"'";	
		return BLZHSL.executeSQL(sql);
	}
	@Override
	public Boolean deleteMemberRes(String id, String res) {
		// TODO Auto-generated method stub
		String sql = "update xuncha_Member set ResID = replace(ResID,'"+(res+";")+"','')  where id='"+id+"'";	
		return BLZHSL.executeSQL(sql);
	}
	@Override
	public List<Map<String, Object>> queryFORALLres() {
		// TODO Auto-generated method stub
		String sql = "select * from xuncha_Res";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> queryFORNewTask() {
		// TODO Auto-generated method stub
		String sql = " select MAX(creat_time) as creat_time,res_id from xuncha_task group by res_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> queryFORAllRes() {
		// TODO Auto-generated method stub
		String sql = "select * from xuncha_Res";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> queryFOROverTask(String TM1,String TM2) {
		// TODO Auto-generated method stub
		String sql = "select a.*,c.Name,d.ResName,(select count(*)  from xuncha_result as b where a.task_id = b.task_id and b.case_id !='46') as aa,(select count(*)  from xuncha_result as b where a.task_id = b.task_id and b.case_id ='46') as bb,(select count(*) from xuncha_path as e where e.task_id = a.task_id ) as pp from xuncha_task as a,xuncha_Member as c,xuncha_Res as d where (over_time>='"+TM1+"' and over_time<='"+TM2+"' and isOver = '1'  and a.mem_id = c.id and a.res_id = d.id) or (isOver = '2'  and a.mem_id = c.id and a.res_id = d.id) order by isOver desc,over_time desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> queryFORUNOverTask(String TM1,String TM2) {
		// TODO Auto-generated method stub
		String sql = "select a.*,c.Name,d.ResName,c.Telephone from xuncha_task as a,xuncha_Member as c,xuncha_Res as d where creat_time>='"+TM1+"' and creat_time<='"+TM2+"' and isOver = '0' and a.mem_id = c.id and a.res_id = d.id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		return list;
	}
	
	@Override
	public Boolean refashFORsecret(String id) {
		String sql = "update xuncha_Member set Password = 'ac59075b964b0715' where id='"+id+"'";	
		// TODO Auto-generated method stub
		return BLZHSL.executeSQL(sql);
	}
	@Override
	public Boolean insertTask(Map<String, Object> map) {
		// TODO Auto-generated method stub,发送提醒短信
		String mem_id = map.get("mem_id").toString();
		//获取手机号，
		String sql = "select * from xuncha_Member where id = '"+mem_id+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		if( list.size() > 0 ){
			String phone = list.get(0).get("Telephone").toString();
			String message = "您有一条新的水库巡查任务，请及时登陆系统查看。您的上条巡查任务如未完成，请尽快完成。";
			String insert = "insert into xuncha_message (phone,message) values ('"+phone+"','"+message+"')";			
			BLZHSL.executeSQL(insert);
			//new YjServiceImpl().sendmessage(message, phone, "2", "4");
		}		
		return BLZHSL.saveMap("xuncha_task", map);
	}
	@Override
	public Boolean updateFORmember(String id, String Name, String UserID, String Telephone) {
		// TODO Auto-generated method stub
		//判断userID是否已经存在
		String sql = "select * from xuncha_Member where UserID='"+UserID+"' and id !='"+id+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		if(list.size()>0 ){
			return false;
		}else{
			//账号唯一
			String _sql = "update xuncha_Member set Name='"+Name+"',UserID='"+UserID+"',Telephone='"+Telephone+"' where id='"+id+"'";
			return BLZHSL.executeSQL(_sql);
		}
	}
	@Override
	public Boolean updateFORLengthRes(String id, String pinlv,String isOPEN) {
		String sql = "update xuncha_Res set length = '"+pinlv+"',isOPEN='"+isOPEN+"' where id='"+id+"'";
		// TODO Auto-generated method stub
		return BLZHSL.executeSQL(sql);
	}
	@Override
	public Boolean deleteFORmember(String id) {
		// TODO Auto-generated method stub
		String sql = "delete from xuncha_Member where id='"+id+"'";
		return BLZHSL.executeSQL(sql);
	}
	@Override
	public Boolean updateFORLengthP(String days, String sel) {
		// TODO Auto-generated method stub
		String sql = "";
		if( sel.equals("all") ){
			sql = "update xuncha_Res set length = '"+days+"' ";
		}else{
			sql = "update xuncha_Res set length = '"+days+"' where restype = '"+sel+"'";
		}
		return BLZHSL.executeSQL(sql);
	}
	@Override
	public Map<String, Object> Login(String UserID, String Password) {
		// TODO Auto-generated method stub
		String sql="select * from xuncha_Member where UserID='"+UserID+"' and Password='"+Password+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql);
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		
		if(list.size() == 0){
			map.put("error", 1);
			map.put("islogin", 0);
			map.put("erroMes", "账户名不存在或密码错误！");
		}else{
			map.put("error", 0);
			map.put("islogin", 1);
			Map<String, Object> user = new java.util.HashMap<String, Object>();
			if(list.get(0).get("head") == null){
				String src = "";
				user.put("head", src);
			}else{
				String src = "http://www.htwater.net:8080/blsh_file/xuncha/head/"+list.get(0).get("head").toString();
				user.put("head", src);
			}
			user.put("id", list.get(0).get("id"));
			user.put("name", list.get(0).get("Name"));
			user.put("Telephone", list.get(0).get("Telephone"));
			
			String resID = list.get(0).toString();
			String resname = resID.split(";")[1];
			String sql1 = "select stcd,ENNMCD from xuncha_Res where id = '"+resname+"' and stcd is not null";	
			String rain="0",water="0";
			List<Map<String, Object>> stcdList = BLZHSL.executeQuery(sql1);
			if( stcdList.size() > 0 ){
				String stcd = stcdList.get(0).get("stcd").toString();
				String swString = "select top 1 * from WATER where stcd='"+stcd+"' order by TM desc ";
				List<Map<String, Object>> l_sw = new ArrayList<Map<String, Object>>()/*YUNRUN.executeQuery(swString)*/;
				if( l_sw.size() >0 ){				
					if( null == l_sw.get(0).get("Z")){
						water = "暂无数据";
					}else{
						water = l_sw.get(0).get("Z").toString();
					}
				}	
				Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
				int hour = c.get(Calendar.HOUR_OF_DAY); 
				SimpleDateFormat ss=new SimpleDateFormat("yyyy-MM-dd 08:00");
				SimpleDateFormat ee=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date now = new Date();

				Date yes = new Date( new Date().getTime()-24*60*60*1000 );
				String start="",end="";
				if( hour>=8 ){
					//今天8点到现在
					start = ss.format(now);
					end = ee.format(now);				
				}else{
					//昨天8点到现在
					start = ss.format(yes);
					end = ee.format(now);							
				}
				String rainString = " select SUM(R5) as R from [RAIN] where TM>'"+start+"' and TM<='"+end+"' and stcd='"+stcd+"'";
				List<Map<String, Object>> l_rain = new ArrayList<Map<String, Object>>()/*YUNRUN.executeQuery(rainString)*/;
				if( l_rain.size() >0 ){
					if(null == l_rain.get(0).get("R")){
						rain = "暂无数据";
					}else{					
						rain = l_rain.get(0).get("R").toString();
					}
				}
				
			}else{
				rain = "暂无数据";
				water = "暂无数据";
			}	
			user.put("water", water);
			user.put("rain", rain);
			
			
			
			String sql2 = "select ENNMCD from xuncha_Res where id = '"+resname+"'";	
			List<Map<String, Object>> ENNMList = BLZHSL.executeQuery(sql2);
			String ENNM = ENNMList.get(0).get("ENNMCD").toString();
			String slpcString = "select ENNM,RSVTP,PROJ_GRAD,DAM_GRAD,DAM_HIGHT,DAM_LEN,DAM_ELEV,CHECK_FLOOD_Z,DESIGN_FLOOD_Z,FLOOD_TOP_Z,FLOOD_NORMAL_Z,FLOOD_LIMIT_Z,DEAD_Z,TOTAL_S,REGU_S,FLOOD_S,UTIL_S,DEAD_S,NORMAL_Z_AREA from P201_RSV where ENNMCD = '"+ENNM+"'";		
			List<Map<String, Object>> ssList = new ArrayList<Map<String, Object>>()/*SLPC.executeQuery(slpcString)*/;
			if( ssList.size()>0 ){
				List<Map<String, Object>> gcxx = new ArrayList<Map<String, Object>>();
				Map<String, Object> gc =   new java.util.HashMap<String, Object>();
				Map<String, Object> re_gc = ssList.get(0);
				
				Map<String, Object> gc1 =   new java.util.HashMap<String, Object>();
				gc1.put("name", "水库名称");
				gc1.put("key", getKEY(re_gc,"ENNM"));
				gc1.put("order", "1");
				gcxx.add(gc1);
				
				Map<String, Object> gc2 =   new java.util.HashMap<String, Object>();
				gc2.put("name", "水库类型");
				gc2.put("key", getKEY(re_gc,"RSVTP"));
				gc2.put("order", "2");
				gcxx.add(gc2);
				
				Map<String, Object> gc3 =   new java.util.HashMap<String, Object>();
				gc3.put("name", "工程等别");
				gc3.put("key", getKEY(re_gc,"PROJ_GRAD"));
				gc3.put("order", "3");
				gcxx.add(gc3);
				
				Map<String, Object> gc4 =   new java.util.HashMap<String, Object>();
				gc4.put("name", "主坝级别");
				gc4.put("key", getKEY(re_gc,"DAM_GRAD"));
				gc4.put("order", "4");
				gcxx.add(gc4);
				
				Map<String, Object> gc5 =   new java.util.HashMap<String, Object>();
				gc5.put("name", "主坝坝高（m）");
				gc5.put("key", getKEY(re_gc,"DAM_HIGHT"));
				gc5.put("order", "5");
				gcxx.add(gc5);
				
				Map<String, Object> gc6 =   new java.util.HashMap<String, Object>();
				gc6.put("name", "主坝坝长（m）");
				gc6.put("key", getKEY(re_gc,"DAM_LEN"));
				gc6.put("order", "6");
				gcxx.add(gc6);
				
				Map<String, Object> gc7 =   new java.util.HashMap<String, Object>();
				gc7.put("name", "坝顶高程");
				gc7.put("key", getKEY(re_gc,"DAM_ELEV"));
				gc7.put("order", "7");
				gcxx.add(gc7);
				
				Map<String, Object> gc8 =   new java.util.HashMap<String, Object>();
				gc8.put("name", "校核洪水位（m）");
				gc8.put("key", getKEY(re_gc,"CHECK_FLOOD_Z"));
				gc8.put("order", "8");
				gcxx.add(gc8);
				
				Map<String, Object> gc9 =   new java.util.HashMap<String, Object>();
				gc9.put("name", "设计洪水位（m）");
				gc9.put("key", getKEY(re_gc,"DESIGN_FLOOD_Z"));
				gc9.put("order", "9");
				gcxx.add(gc9);
				
				Map<String, Object> gc10 =   new java.util.HashMap<String, Object>();
				gc10.put("name", "防洪高水位（m）");
				gc10.put("key", getKEY(re_gc,"FLOOD_TOP_Z"));
				gc10.put("order", "10");
				gcxx.add(gc10);
				
				Map<String, Object> gc11 =   new java.util.HashMap<String, Object>();
				gc11.put("name", "正常蓄水位（m）");
				gc11.put("key", getKEY(re_gc,"FLOOD_NORMAL_Z"));	
				gc11.put("order", "11");
				gcxx.add(gc11);
				
				Map<String, Object> gc12 =   new java.util.HashMap<String, Object>();
				gc12.put("name", "防洪限制水位（m）");
				gc12.put("key", getKEY(re_gc,"FLOOD_LIMIT_Z"));
				gc12.put("order", "12");
				gcxx.add(gc12);
						
				Map<String, Object> gc13 =   new java.util.HashMap<String, Object>();
				gc13.put("name", "死水位（m）");
				gc13.put("key", getKEY(re_gc,"FLOOD_LIMIT_Z"));	
				gc13.put("order", "13");
				gcxx.add(gc13);
				
				Map<String, Object> gc14 =   new java.util.HashMap<String, Object>();
				gc14.put("name", "总库容（万立方米）");
				gc14.put("key", getKEY(re_gc,"TOTAL_S"));
				gc14.put("order", "14");
				gcxx.add(gc14);
				
				Map<String, Object> gc15 =   new java.util.HashMap<String, Object>();
				gc15.put("name", "调洪库容（万立方米）");
				gc15.put("key",getKEY(re_gc,"REGU_S"));
				gc15.put("order", "15");
				gcxx.add(gc15);
				
				Map<String, Object> gc16 =   new java.util.HashMap<String, Object>();
				gc16.put("name", "防洪库容（万立方米）");
				gc16.put("key",getKEY(re_gc,"FLOOD_S"));
				gc16.put("order", "16");
				gcxx.add(gc16);
				
				Map<String, Object> gc17 =   new java.util.HashMap<String, Object>();
				gc17.put("name", "兴利库容（万立方米）");
				gc17.put("key",getKEY(re_gc,"UTIL_S"));
				gc17.put("order", "17");
				gcxx.add(gc17);
				
				Map<String, Object> gc18 =   new java.util.HashMap<String, Object>();
				gc18.put("name", "死库容（万立方米）");
				gc18.put("key",getKEY(re_gc,"DEAD_S"));
				gc18.put("order", "18");
				gcxx.add(gc18);
				
				Map<String, Object> gc19 =   new java.util.HashMap<String, Object>();
				gc19.put("name", "正常蓄水位相应水面面积\n（平方公里）");
				gc19.put("key", getKEY(re_gc,"DEAD_S"));
				gc19.put("order", "19");
				gcxx.add(gc19);
				
				
				
				user.put("gcxx", gcxx);
			}
			
			
			
			//创建个新的时间戳来覆盖之前的时间戳		
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				long times = df.parse(df.format(new java.util.Date())).getTime();
				String visit = "update xuncha_Member set last_visit = '"+times+"' where id = '"+list.get(0).get("id")+"'";
				BLZHSL.executeSQL(visit);			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			result.add(user);
			map.put("data", result);
		}
		return map;
	}
	
	public String getKEY(Map<String, Object> gc,String key){
		if( gc.get(key) != null){
			return gc.get(key).toString();
		}else{
			return "";
		}

	}
	
	@Override
	public Map<String, Object> queryForTasklist(String mem_id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			//三天延时时间		
			String sql = "select task_id,task_name,res_id,creat_time,limit_time,dateadd(day,3,convert(datetime,limit_time,120)) as tt from xuncha_task where mem_id = '"+mem_id+"' and GETDATE()<dateadd(day,3,convert(datetime,limit_time,120)) and isOver!=1";
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = BLZHSL.executeQuery(sql);
			//遍历result，添加子任务
			List<Map<String, Object>> child = new ArrayList<Map<String, Object>>();
			String child_str = "select * from xuncha_case where pid = 0";
			child = BLZHSL.executeQuery(child_str);
			for (int i = 0; i < result.size(); i++) {
				result.get(i).put("childtask",  child);
			}
			
			map.put("islogin", 1);
			map.put("length", result.size());
			map.put("data", result);		
		}else{
			map.put("islogin", 0);
		}
		return map;	
	}
	
	public Map<String, Object>queryFORXunchaPath(String task_id){
		Map<String, Object> map = new java.util.HashMap<String, Object>();	
		String sql = "select * from xuncha_path where task_id = '"+task_id+"' order by time";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = BLZHSL.executeQuery(sql);
		map.put("data", result);
		return map;
	}
	
	
	public Map<String, Object> queryFORTaskStat(String TM1,String TM2) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		String sql = "  select b.Telephone,a.task_name,a.task_id,a.creat_time,a.limit_time,a.isOver,a.over_time,b.Name from xuncha_task as a,xuncha_Member as b where a.creat_time>='"+TM1+"' and a.limit_time<='"+TM2+"' and a.mem_id = b.id order by b.Name";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list_wwc = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list_aswc = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list_cswc = new ArrayList<Map<String, Object>>();
		result = BLZHSL.executeQuery(sql);
		if( result.size()>0 ){
			String name = result.get(0).get("Name").toString();
			int t_wwc=0,t_aswc=0,t_cswc=0;
			int wwc =0,aswc=0,cswc=0;
			Map<String, Object> map_name = new java.util.HashMap<String, Object>();
			for (Map<String, Object> mm : result) {
				String nn = mm.get("Name").toString();
				if( !nn.equals(name) ){	
					map_name.put("name", name);
					map_name.put("wwc", wwc);
					map_name.put("aswc", aswc);
					map_name.put("cswc", cswc);
					list.add(map_name);		
					map_name = new java.util.HashMap<String, Object>();
					wwc =0;
					aswc=0;
					cswc=0;	
					name = nn;
				}			
				String isover = mm.get("isOver").toString();
				if( isover.equals("1") ){
					//是按时完成还是超时完成
					String limit = mm.get("limit_time").toString();
					String over = mm.get("over_time").toString();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					try {
						if( df.parse(limit).getTime() > df.parse(over).getTime()  ){
							aswc++;
							t_aswc++;
							list_aswc.add(mm);
						}else{
							cswc++;
							t_cswc++;
							list_cswc.add(mm);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}else{
					wwc++;
					t_wwc++;
					list_wwc.add(mm);
				}			
			}
			map_name.put("name", name);
			map_name.put("wwc", wwc);
			map_name.put("aswc", aswc);
			map_name.put("cswc", cswc);
			list.add(map_name);			
			Map<String, Object> total = new java.util.HashMap<String, Object>();
			
			float tt = (t_wwc+t_aswc+t_cswc);
			if( tt > 0 ){
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
				total.put("t_wwc", df.format( t_wwc/tt*100)   );				
				total.put("t_aswc", df.format( t_aswc/tt*100)  );
				total.put("t_cswc", df.format( t_cswc/tt*100)  );	
				total.put("wwc", t_wwc );				
				total.put("aswc", t_aswc );
				total.put("cswc", t_cswc );	
				total.put("list_wwc", list_wwc);
				total.put("list_aswc", list_aswc);
				total.put("list_cswc", list_cswc);
			}else{
				total.put("t_wwc", 0);
				total.put("t_aswc", 0);
				total.put("t_cswc", 0);	
				total.put("list_wwc", list_wwc);
				total.put("list_aswc", list_aswc);
				total.put("list_cswc", list_cswc);
			}
			
			
			map.put("data", total);						
			map.put("result", list);
		}	
		return map;	
	}
	
	public Map<String, Object> queryWeiHuTaskStat(String TM) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		String sql = "  select regionid, task_name,time,task_id,money,Name from weihuProgram_task where time ='"+TM+"' order by time";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = BLZHSL.executeQuery(sql);
		map.put("data", result);
		return map;	
	}
	
	public Map<String, Object> addWxyhProgram(String task_id,String regionid,String task_name,String time
			,String money,String Name) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[weihuProgram_task](task_id,regionid,task_name,time,money,Name) values(?,?,?,?,?,?)";
		if(BLZHSL.executeSQL(sql,new Object[]{task_id,regionid,task_name,time,money,Name})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	
	public Map<String, Object> addWxyhFeeUse(String task_id,String regionid,String yr,String mth,String finished,String payed) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[维养计划资金使用情况](task_id,regionid,yr,mth,finished,payed) values(?,?,?,?,?,?)";
		if(BLZHSL.executeSQL(sql,new Object[]{task_id,regionid,yr,mth,finished,payed})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
	
	@Override
	public Map<String, Object> queryForCaselist(String mem_id,String pid) {
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			// TODO Auto-generated method stub
			String sql = "select * from xuncha_case where pid='"+pid+"'";		
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = BLZHSL.executeQuery(sql);
			map.put("length", result.size());
			map.put("data", result);
			map.put("islogin", 1);			
		}else{			
			map.put("islogin", 0);		
		}
		return map;		
	}
	@Override
	public Boolean islogin(String id) {
		// TODO Auto-generated method stub
		String sql  =  "select * from xuncha_Member where id = '"+id+"'";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = BLZHSL.executeQuery(sql);
			String visitString  = result.get(0).get("last_visit").toString();
			long tt = Long.parseLong(visitString);
			long now = df.parse(df.format(new java.util.Date())).getTime();
			if( now - tt <= 3*60*60*1000 ){		
				//修改时间并返回true
				String update = "update xuncha_Member set last_visit = '"+now+"' where id = '"+id+"'";
				BLZHSL.executeSQL(update);
				return true;				
			}else{
				//超时
				return false;
			}		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<String, Object> isLogin(String id) {
		// TODO Auto-generated method stub
		String sql  =  "select * from xuncha_Member where id = '"+id+"'";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		try {
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = BLZHSL.executeQuery(sql);
			if( result.get(0).get("last_visit") != null  ){
				String visitString  = result.get(0).get("last_visit").toString();
				
				long tt = Long.parseLong(visitString);
				long now = df.parse(df.format(new java.util.Date())).getTime();
				if( now - tt <= 3*60*60*1000 ){		
					//修改时间并返回true
					String update = "update xuncha_Member set last_visit = '"+now+"' where id = '"+id+"'";
					BLZHSL.executeSQL(update);
					map.put("islogin", 1);
					
				}else{
					map.put("islogin", 0);
				}
			}else{
				map.put("islogin", 0);
			}		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public Boolean updateFORFile(String param, String param2, String param3) {
		// TODO Auto-generated method stub
		String sql = "insert into xuncha_file (file_name,file_type,task_id) values ()";
		Boolean isOk = BLZHSL.executeSQL(sql);
		return isOk;
	}
	@Override
	public Map<String, Object> queryState() {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new java.util.HashMap<String, Object>();		
			String sql = "select * from xuncha_state";		
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = BLZHSL.executeQuery(sql);
			map.put("length", result.size());
			map.put("data", result);

		return map;
	}
	@Override
	public Map<String, Object> updateFORsecret(String mem_id,String old_password, String new_password) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			// TODO Auto-generated method stub
			//判断用户旧密码是否填写正确
			String sql = "select * from xuncha_Member where id='"+mem_id+"' and Password='"+old_password+"'";
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = BLZHSL.executeQuery(sql);
			if( result.size() == 0 ){
				//没有这个用户
				map.put("error", 1);
				map.put("erroMes", "原密码输入错误！");
			}else{
				//更新密码
				String update = "update xuncha_Member set Password = '"+new_password+"' where id = '"+mem_id+"'";
				BLZHSL.executeSQL(update);
				map.put("error", 0);
			}		
			map.put("islogin", 1);			
		}else{			
			map.put("islogin", 0);		
		}
		return map;

	}
	
	
	@Override
	public Map<String, Object> selectFORXunchaResultList(String task_id,String PageNum,String type) {
		 Map<String, Object> mm = new java.util.HashMap<String, Object>();
		 String sql = "";
		 if( type.equals("0") ){
			//刚进来是加载
			sql = "select top "+( (Integer.parseInt(PageNum) ) )+" a.task_id,a.over_time,c.Name,c.head,d.ResName from xuncha_task as a,xuncha_Member as c,xuncha_Res as d where  isOver = '1' and a.mem_id = c.id and a.res_id = d.id order by over_time desc";	 
		 }else if(type.equals("1")){
			 //向下拉去
			sql = "select top "+( (Integer.parseInt(PageNum) ) )+" a.task_id,a.over_time,c.Name,c.head,d.ResName from xuncha_task as a,xuncha_Member as c,xuncha_Res as d where  isOver = '1' and a.mem_id = c.id and a.res_id = d.id and over_time < ( select over_time from xuncha_task as e where e.task_id = '"+task_id+"' ) order by over_time desc";
		 }else{
		 	sql = "select a.task_id,a.over_time,c.Name,c.head,d.ResName from xuncha_task as a,xuncha_Member as c,xuncha_Res as d where  isOver = '1' and a.mem_id = c.id and a.res_id = d.id and over_time > ( select over_time from xuncha_task as e where e.task_id = '"+task_id+"' ) order by over_time desc";	 
		 }
		 
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		 result = BLZHSL.executeQuery(sql);
		 for (int i = 0; i < result.size(); i++) {
			String tid = result.get(i).get("task_id").toString();
			String sql1 = " select a.state,a.message,b.case_name,a.task_id,(select case_name from xuncha_case as c where b.pid = c.id) as pname from xuncha_result as a,xuncha_case as b where a.case_id = b.id and a.task_id = '"+tid+"'";
			List<Map<String, Object>> rr = BLZHSL.executeQuery(sql1);
			
			if( rr.size() == 0 ){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("state", "安全");
				map.put("message", "");
				map.put("case_name", "");
				map.put("task_id", "");
				map.put("pname", "");
				rr.add(map);
				result.get(i).put("result", rr);
			}else{
				result.get(i).put("result", rr);
			}
			
			
			
			if( result.get(i).get("head") != null ){
				result.get(i).put("img_src", "http://www.htwater.net:8080/blsh_file/xuncha/head/"+result.get(i).get("head").toString() );
			}else{
				result.get(i).put("img_src", "");
			}
			
			String sql2 = "  select file_name,file_type,task_id from xuncha_file where task_id = '"+tid+"'";
			List<Map<String, Object>> ff = BLZHSL.executeQuery(sql2);
			for (int j = 0; j < ff.size(); j++) {
				String src = "http://www.htwater.net:8080/blsh_file/xuncha/"+ff.get(j).get("file_type").toString()+"/"+ff.get(j).get("file_name").toString();
				ff.get(j).put("src", src);
			}		
			result.get(i).put("file", ff);
			
			
			list.add( result.get(i) );
			
		 } 
    	 mm.put("result", list);
		 return mm;	
	}
	
	@Override
	public Map<String, Object> insertResult(String result,String mem_id) {
		//{type:'f',task_id:1,case_id:2,state:不安全,message:发现异常}
		//{type:'r',task_id:1,file_name:2,file_type:3,case_pid=4}
		Map<String, Object> mm = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			
			mm.put("islogin", 1);	
			JSONArray json1 = JSONArray.fromObject(result);
			int count = 0;
	        for (Object object : json1) {
	        	Map<String,String> map = (Map<String,String>)object;
	        	//将map保存数据库
	        	String type = map.get("type").toString();
	        	if( type.equals("f") ){
	        		String task_id = map.get("task_id").toString();
	        		String case_id = map.get("case_id").toString();
	        		String state = map.get("state").toString();
	        		String message = map.get("message").toString();	
	        		String sql = "insert into xuncha_result (task_id,case_id,state,message,mem_id) values ('"+task_id+"','"+case_id+"','"+state+"','"+message+"','"+mem_id+"')";
	        		boolean isOK = BLZHSL.executeSQL(sql);   
	        		if( !isOK ){
	        			count ++;    			
	        		}
	        	}else if( type.equals("r") ){
	        		String task_id = map.get("task_id").toString();
	        		String file_name = map.get("file_name").toString();
	        		String file_type = map.get("file_type").toString();
	        		String case_pid = map.get("case_pid").toString(); 
	        		String sql = "insert into xuncha_file (task_id,file_name,file_type,case_pid,mem_id) values ('"+task_id+"','"+file_name+"','"+file_type+"','"+case_pid+"','"+mem_id+"')";
	        		boolean isOK = BLZHSL.executeSQL(sql);
	        		if( !isOK ){
	        			count ++;
	        		}
	        	}
	        	
			}
			
	        if( count==0 ){
	        	mm.put("error", 0);
	        }else{
	        	mm.put("error", 1);
				mm.put("erroMes", count+"项提交失败");
	        }
			
		}else{
			
			mm.put("islogin", 0);	
			
		}
		    
		return mm;
		
		
	}
	
	@Override
	public Map<String, Object> insertTaskFile(String task_id, String file_name,String file_type,String case_pid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		String sql = "insert into xuncha_file (task_id,file_name,file_type,case_pid) values ('"+task_id+"','"+file_name+"','"+file_type+"','"+case_pid+"')";
		boolean isOK = BLZHSL.executeSQL(sql);
		int bool = 0;
		if( isOK ){
			bool = 1;
		}	
		map.put("result", bool);	
		return map;
	}
	
	@Override
	public Map<String, Object> updateForhead(String mem_id, String file) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			map.put("islogin", 1);
			map.put("img_src", "http://www.htwater.net:8080/blsh_file/xuncha/head/"+file);
			String sql = "update xuncha_Member set head = '"+file+"' where id = '"+mem_id+"'";
			boolean isOK = BLZHSL.executeSQL(sql);
			int bool = 0;
			if( isOK ){
				bool = 1;
			}	
			map.put("result", bool);
		}else{
			map.put("islogin", 0);
		}		
		return map;
	}
	
	@Override
	public Map<String, Object> insertTaskPath(String task_id,String mem_id, String lon,String lat) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			map.put("islogin", 1);	
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format( new java.util.Date() );
			String sql = "insert into xuncha_path (task_id,lon,lat,time) values('"+task_id+"','"+lon+"','"+lat+"','"+time+"')";
			boolean isOK = BLZHSL.executeSQL(sql);
			int bool = 0;
			if( isOK ){
				bool = 1;
			}	
			map.put("result", bool);			
		}else{
			map.put("islogin", 0);
		}
		return map;
	}
	
	public Map<String, Object> updateTaskResult(String task_id,String mem_id,String place) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		
		if( new XunchaImpl().islogin(mem_id) ){
		map.put("islogin", 1);	
		int bool = 0;		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format( new java.util.Date() );
		String sql = "update xuncha_task set isOver=1,over_time = '"+time+"',place='"+place+"' where task_id='"+task_id+"' and mem_id='"+mem_id+"'";
		boolean isOK = BLZHSL.executeSQL(sql);
		
		if( isOK ){
			bool = 1;
		}	
		map.put("result", bool);
		}else{
			map.put("islogin", 0);
		}
		return map;
	}
	
	public Map<String, Object> queryChildResult(String task_id,String case_pid,String mem_id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			map.put("islogin", 1);	
			String sql = "select * from xuncha_case as a,xuncha_result as b where a.pid ='"+case_pid+"' and a.id = b.case_id and b.task_id ='"+task_id+"'";
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = BLZHSL.executeQuery(sql);
			
			if( result.size() == 0 ){
				map.put("isUpload", 0);		
			}else{
				map.put("isUpload", 1);
				map.put("result", result);	
			}	
		}else{
			map.put("islogin", 0);	
		}
		
	
		return map;
		
		
	}
	
	public Map<String, Object> queryChildFile(String task_id,String case_pid,String mem_id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			map.put("islogin", 1);		
		String sql = "select * from xuncha_file where task_id = '"+task_id+"' and case_pid = '"+case_pid+"'";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = BLZHSL.executeQuery(sql);		
		if( result.size() == 0 ){
			map.put("isUpload", 0);		
		}else{
			map.put("isUpload", 1);
			map.put("result", result);	
		}	
		}else{
			map.put("islogin", 0);	
		}		
		return map;
	}
	
	public Map<String, Object> queryFOEresWaterRain(String res_id) {
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		
		
		
		return map;
	}
	
	
	public List<Map<String, Object>> queryFORresultByid(String id){
		String sql = "select a.*,b.case_name from xuncha_result as a,xuncha_case as b where a.task_id = '"+id+"' and a.case_id = b.id";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = BLZHSL.executeQuery(sql);			
		return result;
	}
	public List<Map<String, Object>> queryFORfileByid(String id){
		String sql = "select * from xuncha_file where task_id = '"+id+"' order by file_type";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = BLZHSL.executeQuery(sql);			
		return result;
	}
	@Override
	public Map<String, Object> updateTaskState(String task_id,String mem_id) {
		// TODO Auto-generated method stub
		//修改巡查任务正在巡查中
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			map.put("islogin", 1);
			int bool = 0;		
			String sql = "update xuncha_task set isOver = '2' where task_id='"+task_id+"'";		
			boolean isOK = BLZHSL.executeSQL(sql);
			if( isOK ){
				bool = 1;
			}	
			map.put("result", bool);
		}else{
			map.put("islogin", 0);
		}
		return map;
		
	}
	@Override
	public Boolean insertLoginlog(String mem_id,String mobile_id, String sys_version,
			String mobile_type, String mobile_brand, String mobile_system,
			String client_version, String client_version_int) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		map.put("mem_id", mem_id);
		map.put("mobile_id", mobile_id);
		map.put("sys_version", sys_version);
		map.put("mobile_type", mobile_type);
		map.put("mobile_brand", mobile_brand);
		map.put("mobile_system", mobile_system);
		map.put("client_version", client_version);
		map.put("client_version_int", client_version_int);
		return BLZHSL.saveMap("Xuncha_login_log", map);
	}
	@Override
	public Boolean insertFORjmxc(String res_id, String mem_id,String creat_time, String limit_time) {
		// TODO Auto-generated method stub
		String sql1 = "select * from xuncha_Res where id = '"+res_id+"'";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = BLZHSL.executeQuery(sql1);
		String name = result.get(0).get("ResName").toString();
		
		String sql = "insert into xuncha_task(task_name,res_id,mem_id,creat_time,limit_time,isOver) values ('"+name+"加密巡查任务','"+res_id+"','"+mem_id+"','"+creat_time+"','"+limit_time+"','0')";
		boolean isOK = BLZHSL.executeSQL(sql);
		return isOK;
	}
	public Map<String, Object> queryResList(String mem_id){
		String sql = "select * from xuncha_Member where id = '"+mem_id+"'";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = BLZHSL.executeQuery(sql);
		String resString = result.get(0).get("ResID").toString();
		String sql1 = "select id,ResName from xuncha_Res where  charindex((';'+replace(STR(id),' ','')+';'),'"+resString+"')>0";
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		result = BLZHSL.executeQuery(sql1);
		map.put("data", result);
		return map;
	}
	public Map<String, Object> insertFORenTask(String res_id, String mem_id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new java.util.HashMap<String, Object>();
		if( new XunchaImpl().islogin(mem_id) ){
			
			map.put("islogin", 1);		
			String sql1 = "select * from xuncha_Res where id = '"+res_id+"'";
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			result = BLZHSL.executeQuery(sql1);
			String name = result.get(0).get("ResName").toString();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String creat_time = df.format( new java.util.Date() );
						
			Date d2;
			try {
				d2 = df.parse( df.format( new Date() ) );
				long ll = d2.getTime() + 4*24*60*60*1000;
				String limit_time = df.format(new Date(ll));	
				String sql = "insert into xuncha_task(task_name,res_id,mem_id,creat_time,limit_time,isOver) values ('"+name+"加密巡查任务','"+res_id+"','"+mem_id+"','"+creat_time+"','"+limit_time+"','0')";
				int bool = 0;
				boolean isOK = BLZHSL.executeSQL(sql);
				if( isOK ){
					bool = 1;
				}	
				map.put("result", bool);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}else{
			map.put("islogin", 0);
		}
		return map;
		
		
	}
	@Override
	public Boolean updateFORisread(String id, String type) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = new Date();
		String time1 = df.format( d );
		
		String sql = "update xuncha_task set " +type +"='"+time1+"' where task_id='"+id+"'";
		boolean isOK = BLZHSL.executeSQL(sql);
		return isOK;

	}
	
	@Override
	public List<Map<String, Object>> queryTaskMoney(String TM1,String TM2) {
		// TODO Auto-generated method stub
		Object[] args = {TM1,TM2};
		String sql = " SELECT a.regionid,a.task_id,a.task_name,a.time, a.money,b.regionid,b.yr,b.mth, b.finished,b.payed "
				+ " FROM [dbo].[weihuProgram_task] a "
				+ " left join [dbo].[维养计划资金使用情况] b on a.task_id=b.task_id"
				+ " where b.yr=? and  b.mth=? and a.time=b.yr and a.regionid=b.regionid";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = BLZHSL.executeQuery(sql,args);
		return list;
	}
	@Override
	public Map<String, Object> addDocs(String docNO, String docNM, String type,
			String docType, String pages, String bzTM, String bzUnit,
			String deadline, String secLvl, String refer) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="insert into [dbo].[档案检索] (档案号,案卷题名,type,案卷类别,页数,编制时间,编制单位,保管期限,密级,备注) values(?,?,?,?,?,?,?,?,?,?)";
		if(BLZHSL.executeSQL(sql,new Object[]{docNO, docNM, type,docType, pages,bzTM, bzUnit,deadline, secLvl, refer})){
			map.put("msg","success");
		}else {
			map.put("msg","fail");
		}
		return map;
	}
}

package net.htwater.mydemo.service.impl;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.ApiService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

public class ApiServiceImpl implements ApiService {

	BaseDao daoMssql = DaoFactory.getDao("ht_nbsqdb");
	@Override
	public String getPathIsExit(String path) {
		String strSql="select ID,State,MaxRain from [ce].[dbo].[数据接口表] where DatPath=?";
		Map<String,Object>map=daoMssql.executeQueryObject(strSql, new Object[]{path});
		if(map!=null){
			//存在
			if(map.get("State").toString().equals("2")){
				//已经成功创建了的
				
				return 2+","+map.get("MaxRain").toString();  //成功创建
			}
			else{
				return 1+","+map.get("MaxRain").toString();  //正在生成中
			}
		}
		return -1+",-1"; //不存在
	}

	@Override
	public  Map<String,Object> getRainDatetoTxt(List<Map<String,Object>> lst,String pathHeader,String path, String tm_begin, String tm_end ,String city,String clipLayer,String step,String Coordinate,String Stationtype) {
		String strSql;
		//Stationtype="0";
		Map<String,Object> mresmap=new HashMap<String,Object>();
		//List<Map<String,Object>>lst=new ArrayList<Map<String,Object>>();
		int res=1;
		double maxValue=0;
		try {
			/*if(city.equals("all")){
				strSql="use ht_nbsqdb;select a.STNM,VALUE,CITY,Address,STX,STY from "+ 
				"( "+ 
				"select stnm as STNM,convert(decimal(12,1),sum(drp)) as VALUE, c.stcd as STCD ,city as CITY, b.ISSTATE from HT_RAIN_B "+ 
				"a left join HT_STBPRP_B b on  a.ST_ID=b.ID left join HT_PPTN_R c on a.stcd=c.stcd  "+ 
				"where enabled=1 and c.tm > ? and c.tm <=?  "+ 
				"group by c.stcd,stnm,city,ordno,ISSTATE  "+ 
				") as a,t_STCoordinate as b "+ 
				"where a.STNM=b.STNM and b.ISGJZ>=?";  //国家站 3 正常的 2  0 表示全部
				lst=daoMssql.executeQuery(strSql, new Object[]{tm_begin,tm_end,Stationtype});
			}
			else{
				strSql="use ht_nbsqdb;select a.STNM,VALUE,CITY,Address,STX,STY from "+ 
						"( "+ 
						"select stnm as STNM,convert(decimal(12,1),sum(drp)) as VALUE, c.stcd as STCD ,city as CITY, b.ISSTATE from HT_RAIN_B "+ 
						"a left join HT_STBPRP_B b on  a.ST_ID=b.ID left join HT_PPTN_R c on a.stcd=c.stcd and city=? "+
						"where enabled=1 and c.tm > ? and c.tm <=?  "+ 
						"group by c.stcd,stnm,city,ordno,ISSTATE  "+ 
						") as a,t_STCoordinate as b "+ 
						"where a.STNM=b.STNM and b.ISGJZ>=?";
				lst=daoMssql.executeQuery(strSql, new Object[]{city,tm_begin,tm_end,Stationtype});
			}
			*/
			//生成TXT 没事就找找那个大茅岙水库
			String strTxt="";
			double dtemp=0;
			if(lst!=null){
				for (Map<String, Object> map : lst) {
					if(map==null)continue;
					//更新最大值
					dtemp=Double.parseDouble(map.get("VALUE").toString());
					if(dtemp>maxValue)maxValue=dtemp;
					
					if(strTxt.equals("")){
						strTxt+=map.get("STX").toString().trim()+" "+map.get("STY").toString().trim()+" "+map.get("VALUE").toString().trim();
					}
					else{
						strTxt+="\r\n"+map.get("STX").toString().trim()+" "+map.get("STY").toString().trim()+" "+map.get("VALUE").toString().trim();
					}
					
					/*
					if(map.get("STNM").toString().equals("大茅岙水库")){
						if(map.get("Address").toString().indexOf(map.get("CITY").toString())>=0){
							if(strTxt.equals("")){
								strTxt+=map.get("STX").toString().trim()+" "+map.get("STY").toString().trim()+" "+map.get("VALUE").toString().trim();
							}
							else{
								strTxt+="\r\n"+map.get("STX").toString().trim()+" "+map.get("STY").toString().trim()+" "+map.get("VALUE").toString().trim();
							}
						}
						else{
							//lst.remove(map);
						}
					}
					else{
						if(strTxt.equals("")){
							strTxt+=map.get("STX").toString().trim()+" "+map.get("STY").toString().trim()+" "+map.get("VALUE").toString().trim();
						}
						else{
							strTxt+="\r\n"+map.get("STX").toString().trim()+" "+map.get("STY").toString().trim()+" "+map.get("VALUE").toString().trim();
						}
					}*/
				}
				
				//输出 //转成TXT
		        RandomAccessFile mm = null;
	            mm = new RandomAccessFile(pathHeader+path+".dat", "rw");
	            mm.writeBytes(strTxt);
	            mm.close();

	            if(maxValue<0.00001){
	            	//这个是没有雨量
	            	res=0;
	            }
	            else{
	               //插入一条数据
			       strSql="insert into [ce].[dbo].[数据接口表](CreateTM,DatPath,Coordinate,Layer,ContainStation,State,Step,DatFile,MaxRain) "+
			        		"values(GETDATE(),?,?,?,1,0,?,?,?)";
			       boolean rb=daoMssql.executeSQL(strSql, new Object[]{path,Coordinate,clipLayer,step,pathHeader,maxValue});
			       if(!rb){
			    	   res=-1;
			       }
	            }
			}
			else{
				//没有数据 雨量为0
				res=0;
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			res=-1;
		}
		mresmap.put("lst", lst);
		mresmap.put("res", res);
		mresmap.put("maxValue", maxValue);
		return mresmap;
	}
    
}

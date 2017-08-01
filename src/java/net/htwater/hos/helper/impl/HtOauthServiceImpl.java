package net.htwater.hos.helper.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.DateUtil;
import cn.miao.framework.util.PinyinUtil;
import cn.miao.framework.util.StringUtil;
import net.htwater.hos.helper.HtOauthHelper;
import net.htwater.hos.helper.RainWaterHelper;
import net.htwater.hos.helper.entity.RiverInfo;
import net.htwater.hos.helper.entity.STRain;
import net.htwater.hos.helper.entity.STWater;
import net.htwater.hos.helper.entity.TideInfo;
import net.htwater.hos.helper.other.HTMLFormatter;

public class HtOauthServiceImpl{
	BaseDao daohtnbsqdb = DaoFactory.getDao("HT_NBSQDB");

	public List<STWater> getSTBigMidRsvrInfo(String tm) {
		String sql = "declare @pTM8 datetime, @curtm datetime;set @curtm = ?;set @pTM8 = dbo.getWater8Point(@curtm,2);"
				+ " declare @startTM datetime,@curtmMX1 datetime,@curtmMX2 datetime ;"
				+ " set @curtmMX2=convert(varchar(5),@curtm,120)+'10-16';"
				+ " set @starttm=dateadd(minute,-560,@curtm);"
				+ " select rtrim(b.stnm) as 'stnm',  "
				+ " z8 = convert(decimal(12, 2), (select top 1 rz from st_rsvr_r where stcd=a.stcd and tm <= @ptm8 order by tm desc)),"
				+ " dbo.getreskr(a.stcd,convert(decimal(12, 2), "
				+ " (select top 1 rz from st_rsvr_r where stcd=a.stcd and tm <= @ptm8 order by tm desc))) as z8kr,"
				+ "  ctz=case when @curtm>=convert(varchar(5),@curtm,120)+a.mxendtime and @curtm<=@curtmMX2 then convert(decimal(12, 2), a.txsw) when @curtm<convert(varchar(5),@curtm,120)+a.mxendtime or @curtm>@curtmMX2 then convert(decimal(12, 2), a.mxsw) end,"
				+ " kzkr=case when @curtm>=convert(varchar(5),@curtm,120)+a.mxendtime and @curtm<=@curtmMX2 then convert(decimal(12, 2), a.txkr) when @curtm<convert(varchar(5),@curtm,120)+a.mxendtime or @curtm>@curtmMX2 then convert(decimal(12, 2), a.mxkr) end,"
				+ " rtrim(a.stcd) as 'stcd',a.latd,a.lgtd,b.city,a.nor_level,a.floodhigh_level,a.ennmcd,a.type as rsv_type,"
				+ " (select  top 1 rz from st_rsvr_r where stcd=a.stcd and tm <=@curtm order by tm desc)z,"
				+ " dbo.getreskr(a.stcd,(select  top 1 rz from st_rsvr_r where stcd=a.stcd and tm <=@curtm order by tm desc)) as rtkr,"
				+ " (select  top 1 tm from st_rsvr_r where stcd=a.stcd and tm <=@curtm order by tm desc)tm from dbo.ht_rsvrbm_b as a "
				+ " left outer join  dbo.ht_stbprp_b as b on a.st_id = b.id "
				+ " where enabled = 1  order by a.ordno;";
		List<STWater> rtList = new ArrayList<STWater>();
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(
				sql, new Object[]{tm});
		STWater stWater = null;
		for(Map<String, Object> map : list) {
			stWater = new STWater();
			String stcd = null==map.get("stcd")?"":map.get("stcd").toString();
			String stnm = null==map.get("stnm")?"":map.get("stnm").toString();
			String TM = null==map.get("tm")?"":map.get("tm").toString();
			String district  =  null==map.get("city")?"":map.get("city").toString();
			String ennmcd = null==map.get("ennmcd")?"":map.get("ennmcd").toString();
			String nor_level = null==map.get("nor_level")
					?"":map.get("nor_level").toString();
			String floodhigh_level = null==map.get("floodhigh_level")
					?"":map.get("floodhigh_level").toString();
			String rsv_type = null==map.get("rsv_type")
					?"":map.get("rsv_type").toString();
			double kzkr = (null==map.get("kzkr") || "".equals(map.get("kzkr")))
					?0.0:Double.parseDouble(map.get("kzkr").toString());
			double ctz = (null==map.get("ctz") || "".equals(map.get("ctz")))
					?0.0:Double.parseDouble(map.get("ctz").toString());
			double z = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double z8 = (null==map.get("z8") || "".equals(map.get("z8")))
					?0.0:Double.parseDouble(map.get("z8").toString());
			double z8kr = (null==map.get("z8kr") || "".equals(map.get("z8kr")))
					?0.0:Double.parseDouble(map.get("z8kr").toString());
			double rtkr = (null==map.get("rtkr") || "".equals(map.get("rtkr")))
					?0.0:Double.parseDouble(map.get("rtkr").toString());
			double latd = (null==map.get("latd") || "".equals(map.get("latd")))
					?0.0:Double.parseDouble(map.get("latd").toString());
			double lgtd = (null==map.get("lgtd") || "".equals(map.get("lgtd")))
					?0.0:Double.parseDouble(map.get("lgtd").toString());
			
			stWater.setStcd(stcd);
			stWater.setCode(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			stWater.setStnm(stnm);
			stWater.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			stWater.setDistrict(district);
			stWater.setennmcd(ennmcd);
			stWater.setnor_level(nor_level);
			stWater.setfloodhigh_level(floodhigh_level);
			stWater.setrsv_type(rsv_type);
			stWater.setType(rsv_type);
			stWater.setKzkr("" + kzkr);
			stWater.setKzsw("" + ctz);
			stWater.setM8sw("" + z8);
			stWater.setStsw("" + z);
			stWater.setTm(TM);
			stWater.setM8kr("" + z8kr);
			stWater.setStkr("" + rtkr);
			stWater.setRatio("" + ((kzkr==0.0)?"0":rtkr/kzkr));
			stWater.setLatitude(latd);
			stWater.setLongitude(lgtd);
			stWater.setRiver("");////////////////////////////待增加
			rtList.add(stWater);
		}
		return rtList;
	}
	
	public List<STWater> getSTSmallRsvrInfo(String tbName,String tm) {
		String sql = "declare @curtm datetime,@pTM8 datetime;set @curtm = ?;set @pTM8 = dbo.getWater8Point(@curtm,2);"
				+ " declare @startTM datetime,@curtmMX1 datetime,@curtmMX2 datetime ;"
				+ " set @curtmmx1=convert(varchar(5),@curtm,120)+'7-14'; set @curtmmx2=convert(varchar(5),@curtm,120)+'10-15';"
				+ " set @starttm=dateadd(minute,-560,@curtm);"
				+ " select b.stnm as 'stnm',z8 = convert(decimal(12, 2),"
				+ " (select top 1 rz from st_rsvr_r where stcd=a.stcd and tm <= @ptm8 order by tm desc)),"
				+ " dbo.getreskr(a.stcd,convert(decimal(12, 2), (select top 1 rz from st_rsvr_r where stcd=a.stcd and tm <= @ptm8 order by tm desc))) as z8kr,"
				+ " ctz=case when @curtm>=@curtmmx1 and @curtm<=@curtmmx2 then convert(decimal(12, 2), a.txsw)when @curtm<@curtmmx1 or @curtm>@curtmmx2 then convert(decimal(12, 2), a.mxsw) end,"
				+ " kzkr=case when @curtm>=@curtmmx1 and @curtm<=@curtmmx2 then convert(decimal(12, 2), a.txkr)when @curtm<@curtmmx1 or @curtm>@curtmmx2 then convert(decimal(12, 2), a.mxkr)end,"
				+ " rtrim(a.stcd) as 'stcd',a.latd,a.lgtd,a.nor_level,a.floodhigh_level,a.ennmcd,b.city,(select  top 1 rz from st_rsvr_r where stcd=a.stcd and tm <=@curtm order by tm desc)z,"
				+ " dbo.getreskr(a.stcd,(select  top 1 rz from st_rsvr_r where stcd=a.stcd and tm <=@curtm order by tm desc)) as rtkr,"
				+ " (select  top 1 tm from st_rsvr_r where stcd=a.stcd and tm <=@curtm order by tm desc)tm"
				+ " from dbo."+tbName+" as a left outer join dbo.ht_stbprp_b as b on a.st_id = b.id where enabled = 1  order by a.id;";
		List<STWater> rtList = new ArrayList<STWater>();
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(
				sql, new Object[]{tm});
		STWater stWater = null;
		for(Map<String, Object> map : list) {
			stWater = new STWater();
			String stcd = null==map.get("stcd")?"":map.get("stcd").toString();
			String stnm = null==map.get("stnm")?"":map.get("stnm").toString();
			String TM = null==map.get("tm")?"":map.get("tm").toString();
			String district  =  null==map.get("city")?"":map.get("city").toString();
			String ennmcd = null==map.get("ennmcd")?"":map.get("ennmcd").toString();
			String nor_level = null==map.get("nor_level")
					?"":map.get("nor_level").toString();
			String floodhigh_level = null==map.get("floodhigh_level")
					?"":map.get("floodhigh_level").toString();
			String rsv_type = tbName.equals("HT_RSVRS1_B")?"s1":"s2";
			double kzkr = (null==map.get("kzkr") || "".equals(map.get("kzkr")))
					?0.0:Double.parseDouble(map.get("kzkr").toString());
			double ctz = (null==map.get("ctz") || "".equals(map.get("ctz")))
					?0.0:Double.parseDouble(map.get("ctz").toString());
			double z = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double z8 = (null==map.get("z8") || "".equals(map.get("z8")))
					?0.0:Double.parseDouble(map.get("z8").toString());
			double z8kr = (null==map.get("z8kr") || "".equals(map.get("z8kr")))
					?0.0:Double.parseDouble(map.get("z8kr").toString());
			double rtkr = (null==map.get("rtkr") || "".equals(map.get("rtkr")))
					?0.0:Double.parseDouble(map.get("rtkr").toString());
			double latd = (null==map.get("latd") || "".equals(map.get("latd")))
					?0.0:Double.parseDouble(map.get("latd").toString());
			double lgtd = (null==map.get("lgtd") || "".equals(map.get("lgtd")))
					?0.0:Double.parseDouble(map.get("lgtd").toString());
			
			stWater.setStcd(stcd);
			stWater.setCode(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			stWater.setStnm(stnm);
			stWater.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			stWater.setDistrict(district);
			stWater.setennmcd(ennmcd);
			stWater.setnor_level(nor_level);
			stWater.setfloodhigh_level(floodhigh_level);
			stWater.setrsv_type(rsv_type);
			stWater.setType(rsv_type);
			stWater.setKzkr("" + kzkr);
			stWater.setKzsw("" + ctz);
			stWater.setM8sw("" + z8);
			stWater.setStsw("" + z);
			stWater.setTm(TM);
			stWater.setM8kr("" + z8kr);
			stWater.setStkr("" + rtkr);
			stWater.setRatio("" + ((kzkr==0.0)?"0":rtkr/kzkr));
			stWater.setLatitude(latd);
			stWater.setLongitude(lgtd);
			stWater.setRiver("");////////////////////////////待增加
			rtList.add(stWater);
		}
		
		return rtList;
	}
	
	public List<RiverInfo> getSTRiverInfo() {
		String sql = "declare @curtm datetime;set @curtm = getdate();"
				+ "select rtrim(rb.stcd) as stcd,"
				+ " (select top 1 z from st_river_r where stcd=rb.stcd and z<>-99 and tm<=@curtm order by tm desc)z,"
				+ " (select top 1 tm from st_river_r where stcd=rb.stcd and z<>-99 and tm<=@curtm order by tm desc)tm,"
				+ " (select top 1 z from (SELECT TOP 2 z,tm FROM st_river_r WHERE stcd=RTrim(RB.STCD) AND z<>-99 and tm<=@curtm ORDER BY TM DESC)a order by tm asc)z1,"
				+ " rb.latd,rb.lgtd,rb.guard,rb.isrcontrol, rb.ensure, sb.city, sb.stnm, rb.ennmcd from ht_river_b as rb "
				+ " left join ht_stbprp_b as sb on rb.st_id=sb.id "
				+ " where enabled=1 and city is not null order by rb.ordno";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(sql);
		List<RiverInfo> allList = new ArrayList<RiverInfo>();
		RiverInfo riverInfo = null;
		for (Map<String, Object> map : list) {
			riverInfo = new RiverInfo();
			String district = (null==map.get("city") || "".equals(map.get("city")))
					?"":map.get("city").toString();
			String stcd = (null==map.get("stcd") || "".equals(map.get("stcd")))
					?"":map.get("stcd").toString();
			String stnm = (null==map.get("stnm") || "".equals(map.get("stnm")))
					?"":map.get("stnm").toString();
			String ennmcd = (null==map.get("ennmcd") || "".equals(map.get("ennmcd")))
					?"":map.get("ennmcd").toString();
			String ensure = (null==map.get("ensure") || "".equals(map.get("ensure")))
					?"":map.get("ensure").toString();
			String guard = (null==map.get("guard") || "".equals(map.get("guard")))
					?"":map.get("guard").toString();
			String TM = (null==map.get("tm") || "".equals(map.get("tm")))
					?"":map.get("tm").toString();
			double sw85 = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double z1 = (null==map.get("z1") || "".equals(map.get("z1")))
					?0.0:Double.parseDouble(map.get("z1").toString());
			String isrcontrol = (null==map.get("isrcontrol") || "".equals(map.get("isrcontrol")))
					?"":map.get("isrcontrol").toString();
			double latd = (null==map.get("latd") || "".equals(map.get("latd")))
					?0.0:Double.parseDouble(map.get("latd").toString());
			double lgtd = (null==map.get("lgtd") || "".equals(map.get("lgtd")))
					?0.0:Double.parseDouble(map.get("lgtd").toString());
			
			riverInfo.setDistrict(district);
			riverInfo.setStcd(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			riverInfo.setennmcd(ennmcd);
			riverInfo.setEnsure(ensure);
			riverInfo.setGuard(guard);
			riverInfo.setStnm(stnm);
			riverInfo.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			riverInfo.setTm(TM);
			riverInfo.setSw85("" + sw85);
			riverInfo.setSwws("" + (sw85 + 1.87));
			//三目判断趋势，z1表示top2 的第二条数据
			riverInfo.setTrend((sw85>z1)?"↑":((sw85==z1)?"--":"↓"));
			riverInfo.setIsRControl(("".equals(isrcontrol) || "false".equals(isrcontrol))
					?false:true);
			riverInfo.setLatitude(latd);
			riverInfo.setLongitude(lgtd);
			riverInfo.setRiver("");////////////////////////////待增加
			allList.add(riverInfo);
		}
		return allList;
	}
	
	public List<TideInfo> getSTTideInfo() {
		String sql = "declare @curtm datetime;set @curtm = getdate();"
				+ " select rtrim(rb.stcd) as stcd,"
				+ " (select top 1 tdz from st_tide_r where stcd=rtrim(rb.stcd) and tdz<>-99 and tm<=@curtm order by tm desc)z,"
				+ " (select top 1 tm from st_tide_r where stcd=rtrim(rb.stcd) and tdz<>-99 and tm<=@curtm order by tm desc)tm,"
				+ " (select top 1 tdz from (select top 2 tdz,tm from st_tide_r "
				+ " where stcd=rtrim(rb.stcd) and tdz<>-99 and tm<=@curtm order by tm desc)a order by tm asc)z1,"
				+ " rb.latd,rb.lgtd,rb.guard,rb.isgcontrol, rb.ensure, sb.city, sb.stnm,rb.polnm,rb.poltp, rb.ennmcd from ht_tide_b as rb "
				+ " left join ht_stbprp_b as sb on rb.st_id=sb.id where enabled=1 and city is not null order by rb.ordno	";

		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(sql);
		List<TideInfo> allList = new ArrayList<TideInfo>();
		TideInfo tideInfo = null;
		for (Map<String, Object> map : list) {
			tideInfo = new TideInfo();
			String district = (null==map.get("city") || "".equals(map.get("city")))
					?"":map.get("city").toString();
			String stcd = (null==map.get("stcd") || "".equals(map.get("stcd")))
					?"":map.get("stcd").toString();
			String stnm = (null==map.get("stnm") || "".equals(map.get("stnm")))
					?"":map.get("stnm").toString();
			String ennmcd = (null==map.get("ennmcd") || "".equals(map.get("ennmcd")))
					?"":map.get("ennmcd").toString();
			String ensure = (null==map.get("ensure") || "".equals(map.get("ensure")))
					?"":map.get("ensure").toString();
			String guard = (null==map.get("guard") || "".equals(map.get("guard")))
					?"":map.get("guard").toString();
			String tm = (null==map.get("tm") || "".equals(map.get("tm")))
					?"":map.get("tm").toString();
			double sw85 = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double z1 = (null==map.get("z1") || "".equals(map.get("z1")))
					?0.0:Double.parseDouble(map.get("z1").toString());
			String polnm = (null==map.get("polnm") || "".equals(map.get("polnm")))
					?"":map.get("polnm").toString(); 
			String poltp = (null==map.get("poltp") || "".equals(map.get("poltp")))
					?"":map.get("poltp").toString();
			double latd = (null==map.get("latd") || "".equals(map.get("latd")))
					?0.0:Double.parseDouble(map.get("latd").toString());
			double lgtd = (null==map.get("lgtd") || "".equals(map.get("lgtd")))
					?0.0:Double.parseDouble(map.get("lgtd").toString());
			
			tideInfo.setStcd(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			tideInfo.setDistrict(district);	
			tideInfo.setennmcd(ennmcd);
			tideInfo.setEnsure(ensure);
			tideInfo.setGuard(guard);
			tideInfo.setStnm(stnm);
			tideInfo.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			tideInfo.setTm(tm);
			tideInfo.setSw85("" + sw85);
			tideInfo.setSwws("" + (sw85 + 1.87));
			tideInfo.setPolName(polnm);
			tideInfo.setPolTop(poltp);
			//三目判断趋势，z1表示top2 的第二条数据
			tideInfo.setTrend((sw85>z1)?"↑":((sw85==z1)?"--":"↓"));
			tideInfo.setLatitude(latd);
			tideInfo.setLongitude(lgtd);
			tideInfo.setRiver("");////////////////////////////待增加

			allList.add(tideInfo);
		}
		return allList;
	}
	
	public List<STRain> getSTRainInfo() {
		List<STRain> allList = new ArrayList<STRain>();
		String sql = "declare @curtm datetime,@pTM8 datetime;set @curtm = ?;"
				+" set @pTM8 = dbo.getWater8Point(@curtm,2);"
				+ " select rtrim(rb.stcd) as stcd,sb.stnm,rb.valley,sb.city,rb.latd,rb.lgtd,"
				+ " rb.ennmcd ,sb.isstate,(select top 1 tm from st_pptn_r "
				+ " where stcd=rb.stcd)tm,(select sum(drn) as adrn from rainday "
				+ " where stcd=rb.stcd and tm>@pTM8 and tm<=DATEADD(D,1,@pTM8))tdrain,"
				+ " (select sum(drn) as adrn from rainday where stcd=rb.stcd "
				+ " and tm>DATEADD(D,-1,@pTM8) and tm<=@pTM8)ydrain,"
				+ " (select sum(drn) as adrn from rainday where stcd=rb.stcd "
				+ " and tm>DATEADD(D,-2,@pTM8) and tm<=DATEADD(D,-1,@pTM8))bydrain,"
				+ " (select sum(drn) as adrn from rainday where stcd=rb.stcd "
				+ " and tm>DATEADD(D,-7,@pTM8) and tm<=@pTM8)sum7rain "
				+ " from ht_rain_b as rb with (nolock) left join ht_stbprp_b as sb "
				+ " on rb.st_id=sb.id where enabled=1 order by rb.id";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(
				sql,new Object[]{DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")});
		STRain stDayRain = null;
		for(Map<String, Object> map : list) {
			stDayRain = new STRain();
			String stcd = null==map.get("stcd")?"":map.get("stcd").toString();
			String stnm = null==map.get("stnm")?"":map.get("stnm").toString();
			String TM = null==map.get("tm")?"":map.get("tm").toString();
			String district = null==map.get("city")?"":map.get("city").toString();
			String ennmcd = null==map.get("ennmcd")?"":map.get("ennmcd").toString();
			String valley = null==map.get("valley")?"":map.get("valley").toString();
			boolean isstate = (null==map.get("isstate") || "false".equals(map.get("isstate").toString())
					|| "".equals(map.get("isstate").toString()))?false:true;
			double tdrain = (null==map.get("tdrain") || "".equals(map.get("tdrain")))
					?0.0:Double.parseDouble(map.get("tdrain").toString());
			double ydrain = (null==map.get("ydrain") || "".equals(map.get("ydrain")))
					?0.0:Double.parseDouble(map.get("ydrain").toString());
			double bydrain = (null==map.get("bydrain") || "".equals(map.get("bydrain")))
					?0.0:Double.parseDouble(map.get("bydrain").toString());
			double sum7rain = (null==map.get("sum7rain") || "".equals(map.get("sum7rain")))
					?0.0:Double.parseDouble(map.get("sum7rain").toString());
			double latd = (null==map.get("latd") || "".equals(map.get("latd")))
					?0.0:Double.parseDouble(map.get("latd").toString());
			double lgtd = (null==map.get("lgtd") || "".equals(map.get("lgtd")))
					?0.0:Double.parseDouble(map.get("lgtd").toString());
			
			stDayRain.setStcd(stcd);
			stDayRain.setTm(TM);
			stDayRain.setName(stnm);
			stDayRain.setCode(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			stDayRain.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			stDayRain.setTodayRain(""+tdrain);
			stDayRain.setYestodayRain(""+ydrain);
			stDayRain.setByestodayRain(""+bydrain);
			stDayRain.setSum7(""+sum7rain);
			stDayRain.setOtherInfo(valley);
			stDayRain.setDistrict(district);
			stDayRain.setennmcd(ennmcd);
			stDayRain.setISSTATE(isstate);
		
			stDayRain.setLatitude(latd);
			stDayRain.setLongitude(lgtd);
			stDayRain.setRiver("");////////////////////////////待增加

			allList.add(stDayRain);
		}
		return allList;
	}
	
	
	public List<STRain> getSTRainInfoByTm(String tm1,String tm2) {
		String sqlAll = "SELECT rtrim(RB.STCD) as STCD,SB.STNM,RB.VALLEY,SB.CITY,RB.ennmcd ,SB.ISSTATE,rb.latd,rb.lgtd,"
				+ " (select sum(drn) as adrn from rainday  where stcd=rb.stcd and tm>? and tm<=?)rain  "
				+ " FROM HT_RAIN_B AS RB with (nolock) LEFT JOIN HT_STBPRP_B AS SB "
				+ " ON RB.ST_ID=SB.ID WHERE ENABLED=1 ORDER BY RB.ID";
		tm1=DateUtil.objDate2Str(DateUtil.str2Date(tm1, "yyyy-MM-dd"), "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date _tm1 = DateUtil.str2Date(tm1,"yyyy-MM-dd");
		calendar.setTime(_tm1);
		calendar.add(Calendar.DATE, 1);
		tm1=DateUtil.date2Str(calendar.getTime(), "yyyy-MM-dd");
		
		Date _tm2 = DateUtil.str2Date(tm2, "yyyy-MM-dd");
		calendar.setTime(_tm2);
		calendar.add(Calendar.DATE, 1);
		tm2=DateUtil.date2Str(calendar.getTime(), "yyyy-MM-dd 8:00");
		
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(
				sqlAll,new Object[]{tm1,tm2});
		List<STRain> allList = new ArrayList<STRain>();
		STRain stDayRain = null;
		
		for (Map<String, Object> map : list) {
			stDayRain = new STRain();
			String stcd = map.get("STCD").toString();
			String stnm = map.get("STNM").toString();
			stDayRain.setStcd(stcd);
			stDayRain.setCode(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			stDayRain.setName(stnm);
			stDayRain.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			stDayRain.setTodayRain((null==map.get("rain") || "".equals(map.get("rain")))
				?"0":map.get("rain").toString());

			stDayRain.setByestodayRain("0");
			stDayRain.setYestodayRain("0");
			stDayRain.setSum7("0");
			stDayRain.setOtherInfo(StringUtil.parseNull(map.get("VALLEY")));
			String district = map.get("CITY").toString();
			stDayRain.setDistrict(district);
			stDayRain.setennmcd(null==map.get("ennmcd")?"":map.get("ennmcd").toString());
			stDayRain.setISSTATE(Boolean.parseBoolean(map.get("ISSTATE").toString()));
			
			double latd = (null==map.get("latd") || "".equals(map.get("latd")))
					?0.0:Double.parseDouble(map.get("latd").toString());
			double lgtd = (null==map.get("lgtd") || "".equals(map.get("lgtd")))
					?0.0:Double.parseDouble(map.get("lgtd").toString());
			stDayRain.setLatitude(latd);
			stDayRain.setLongitude(lgtd);
			stDayRain.setRiver("");
			// 保存所有站点
			allList.add(stDayRain);
		}
		return allList;
	}
	
	
	public List<STRain> getSTRsvrRainInfo() {
		String sqlAll = "declare @curtm datetime,@pTM8 datetime;set @curtm = ?;"
				+ " set @pTM8 = dbo.getWater8Point(@curtm,2); "
				+ " select rtrim(rb.stcd) as stcd,sb.stnm,rb.valley,rb.rsnm,rb.latd,rb.lgtd "
				+ " ,(select top 1 tm from ST_PPTN_r order by tm desc)tm,"
				+ " (select sum(drn) as adrn from rainday  where stcd=rb.stcd "
				+ " and tm>@pTM8 and tm<=DATEADD(D,1,@pTM8))tdrain,"
				+ " (select sum(drn) as adrn from rainday where stcd=rb.stcd  "
				+ " and tm>DATEADD(D,-1,@pTM8) and tm<=@pTM8)ydrain, "
				+ " (select sum(drn) as adrn from rainday where stcd=rb.stcd  "
				+ " and tm>DATEADD(D,-2,@pTM8) and tm<=DATEADD(D,-1,@pTM8))bydrain, "
				+ " (select sum(drn) as adrn from rainday where stcd=rb.stcd  "
				+ " and tm>DATEADD(D,-7,@pTM8) and tm<=@pTM8)sum7rain"
				+ " FROM HT_RAIN_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID"
				+ " WHERE ISRSVR=1 AND ENABLED=1 ORDER BY RB.ID";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(sqlAll,
				new Object[]{DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")});
		// List<STRain> rtList = new ArrayList<STRain>();
		List<STRain> allList = new ArrayList<STRain>();
		STRain stDayRain = null;
		// Map<String, List<STRain>> cacheMap = new HashMap<String,
		// List<STRain>>();
		for (Map<String, Object> map : list) {
			stDayRain = new STRain();
			String stcd = null==map.get("stcd")?"":map.get("stcd").toString();
			String stnm = null==map.get("stnm")?"":map.get("stnm").toString();
			String rsvr = null==map.get("rsnm")?"":map.get("rsnm").toString();
			String TM = null==map.get("tm")?"":map.get("tm").toString();
			String valley = null==map.get("valley")?"":map.get("valley").toString();
			double tdrain = (null==map.get("tdrain") || "".equals(map.get("tdrain")))
					?0.0:Double.parseDouble(map.get("tdrain").toString());
			double ydrain = (null==map.get("ydrain") || "".equals(map.get("ydrain")))
					?0.0:Double.parseDouble(map.get("ydrain").toString());
			double bydrain = (null==map.get("bydrain") || "".equals(map.get("bydrain")))
					?0.0:Double.parseDouble(map.get("bydrain").toString());
			double sum7rain = (null==map.get("sum7rain") || "".equals(map.get("sum7rain")))
					?0.0:Double.parseDouble(map.get("sum7rain").toString());
			
			stDayRain.setTm(TM);
			stDayRain.setCode(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			stDayRain.setStcd(stcd);
			stDayRain.setName(stnm);
			stDayRain.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			stDayRain.setTodayRain(""+tdrain);
			stDayRain.setYestodayRain(""+ydrain);
			stDayRain.setByestodayRain(""+bydrain);
			stDayRain.setSum7(""+sum7rain);

			stDayRain.setOtherInfo(valley);
			stDayRain.setDistrict(rsvr+"水库");
			
			double latd = (null==map.get("latd") || "".equals(map.get("latd")))
					?0.0:Double.parseDouble(map.get("latd").toString());
			double lgtd = (null==map.get("lgtd") || "".equals(map.get("lgtd")))
					?0.0:Double.parseDouble(map.get("lgtd").toString());
			stDayRain.setLatitude(latd);
			stDayRain.setLongitude(lgtd);
			allList.add(stDayRain);
		}
		return allList;
	}
	
	public List<STRain> getDistrictStatics() {
		List<STRain> rtList = new ArrayList<STRain>();
		STRain stDayRain = null;
		List<Map<String, Object>> byestoday = getDistrictAvgRain(
				RainWaterHelper.getWaterBYestorday(),
				RainWaterHelper.getWaterYestorday());
		List<Map<String, Object>> yestodayList = getDistrictAvgRain(
				RainWaterHelper.getWaterYestorday(),
				RainWaterHelper.getWaterToday());
		List<Map<String, Object>> todayList = getDistrictAvgRain(
				RainWaterHelper.getWaterToday(),
				RainWaterHelper.getWaterTommorow());
		List<Map<String, Object>> sevenDayList = null;
		if (RainWaterHelper.isWaterToday()) {
			sevenDayList = getDistrictAvgRain(RainWaterHelper.getBDayByDay(-6),
					RainWaterHelper.getWaterTommorow());
		} else {
			sevenDayList = getDistrictAvgRain(RainWaterHelper.getBDayByDay(-7),
					RainWaterHelper.getWaterTommorow());
		}
		// @20121024 today是空的,则手动置其中数据为空
		if ((null==todayList || todayList.size() < 3) && null!=byestoday) { // 数据不全，不缓存
			for(Map<String , Object> map:byestoday){
				Map<String , Object> tmpmap = new HashMap<String, Object>();
				tmpmap.put("CITY", map.get("CITY"));
				tmpmap.put("VALUE", "0");
				tmpmap.put("COUNT_POINT", "0");
				todayList.add(tmpmap);
			}
		}
		for (int i = 0; i < todayList.size(); i++) {
			stDayRain = new STRain();
			String district = todayList.get(i).get("CITY").toString();
			stDayRain.setName(district);
			stDayRain.setHtmlName(HTMLFormatter.formatTypeHTML(district,
					"district"));
			stDayRain.setTodayRain(todayList.get(i).get("VALUE").toString());
			stDayRain.setYestodayRain(yestodayList.get(i).get("VALUE")
					.toString());
			stDayRain
					.setByestodayRain(byestoday.get(i).get("VALUE").toString());
			stDayRain.setSum7(sevenDayList.get(i).get("VALUE").toString());
			String otherInfo = formatStation(getMax2StationByDistrict(district));
			stDayRain.setOtherInfo(otherInfo);
			if (stDayRain.getName().contains("市区")) {
				stDayRain.setOrder(1);
			} else if (stDayRain.getName().contains("镇海")) {
				stDayRain.setOrder(2);
			} else if (stDayRain.getName().contains("北仑")) {
				stDayRain.setOrder(3);
			} else if (stDayRain.getName().contains("鄞州")) {
				stDayRain.setOrder(4);
			} else if (stDayRain.getName().contains("余姚")) {
				stDayRain.setOrder(5);
			} else if (stDayRain.getName().contains("慈溪")) {
				stDayRain.setOrder(6);
			} else if (stDayRain.getName().contains("宁海")) {
				stDayRain.setOrder(7);
			} else if (stDayRain.getName().contains("奉化")) {
				stDayRain.setOrder(8);
			} else if (stDayRain.getName().contains("象山")) {
				stDayRain.setOrder(9);
			} else {
				stDayRain.setOrder(10);
			}
			rtList.add(stDayRain);
		}
		Collections.sort(rtList, new Comparator<STRain>() {
			@Override
			public int compare(STRain o1, STRain o2) {
				if (o1.getOrder() > o2.getOrder()) {
					return 1;
				} else if (o1.getOrder() == o2.getOrder()) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		
		return rtList;
	}
	
	/**
	 * 获取区域平均雨量 时间是早上8点来计算的
	 * 
	 * @param startTime
	 * @param endTime
	 * @return String
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getDistrictAvgRain(String startTime,
			String endTime) {
		String sql = "select c.CITY, CONVERT(decimal(12, 1),sum(drn)/COUNT(distinct(stnm))) as 'VALUE' ,COUNT(distinct(stnm))as 'COUNT_POINT'"
				+ "from HT_RAIN_B a "
				+ "left join HT_STBPRP_B  c on a.ST_ID=c.ID "
				+ "left join rainday b on a.stcd =b.stcd "
				+ "where b.tm>? and b.tm<=? and enabled=1 and isstate=1 "
				+ "group by c.city";
		return daohtnbsqdb.executeQuery(sql, new Object[] { startTime, endTime });
	}
	
	/**
	 * 根据地区查最大两个站点
	 * 
	 * @param district
	 * @return List<Map<String,Object>>
	 * @since v 1.0
	 */
	private List<Map<String, Object>> getMax2StationByDistrict(String district) {
		String sql = "SELECT TOP 2 SUM(SR.drp) AS ADRN,SR.STCD AS ST,HSB.STNM AS NM "
				+ "FROM HT_PPTN_R AS SR LEFT JOIN HT_RAIN_B AS HRB ON SR.STCD=HRB.STCD LEFT JOIN HT_STBPRP_B AS HSB ON HRB.ST_ID=HSB.[ID] "
				+ "WHERE LTRIM(RTRIM(HSB.CITY))='"
				+ district
				+ "' AND HRB.ENABLED=1 "
				+ " AND SR.TM>'"
				+ RainWaterHelper.getWaterToday()
				+ "' AND SR.TM<='"
				+ RainWaterHelper.getWaterTommorow()
				+ "' GROUP BY SR.STCD,HSB.STNM ORDER BY ADRN DESC ";// AND
		// SR.drn>0
		return daohtnbsqdb.executeQuery(sql);
	}
	
	/**
	 * 格式化最大两个站点显示
	 * 
	 * @param list
	 * @return String
	 * @since v 1.0
	 */
	private String formatStation(List<Map<String, Object>> list) {
		String rtString = "";
		for (Map<String, Object> map : list) {
			rtString += "、" + map.get("ST") + "-" + map.get("NM") + "("
					+ map.get("ADRN") + ")";
		}
		return rtString.length() < 2 ? "" : "<span style='font-size:13px'>"
				+ rtString.substring(1) + "</span>";
	}
	
	public String outputVideoXML() {
		String sql = "select videoxml from VIDEO_XML";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(sql);
		if(list!=null && list.size()>0){
			return list.get(0).get("videoxml").toString();
		}
		else {
			return "";
		}
	}
	
	public List<Map<String, Object>> getWarnRivers() {
		String sql = "declare @curtm datetime;set @curtm=GETDATE();"
				+ "select rtrim(rb.stcd) as STCD,(select top 1 z "
				+ "from st_river_r where stcd=rb.stcd and z<>-99 and tm<=@curtm order by tm desc)z,"
				+ "(select top 1 tm from st_river_r where stcd=rb.stcd and z<>-99 and tm<=@curtm order by tm desc)tm,"
				+ " rb.GUARD, rb.ENSURE, sb.CITY, sb.STNM from ht_river_b as rb "
				+ " left join ht_stbprp_b as sb on rb.st_id=sb.id where enabled=1 and city is not null order by rb.ordno";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(sql);
		List<Map<String, Object>> rtRivers = new ArrayList<Map<String, Object>>();
		Map<String, Object> riverMap = null;
		for (Map<String, Object> map : list) {
			double latestSW = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double guard = (null==map.get("GUARD") || "".equals(map.get("GUARD"))) 
					? 0 : Double.parseDouble(map.get("GUARD").toString());
			if ( guard > 0 && latestSW >= guard ) {
				riverMap = new HashMap<String, Object>();
				riverMap.putAll(map);
				//setStationLnglat("river", stcd, riverMap);
				rtRivers.add(riverMap);
			}
		}
		return rtRivers;
	}
	
	public List<Map<String, Object>> getWarnBigRsvrs() {
		String sql = "SELECT RTrim(RB.STCD) as STCD, RB.MXSW, RB.MXKR, RB.TXSW, "
				+ " RB.TXKR, RTrim(RB.TYPE) AS TYPE, RB.MXENDTIME, RB.TXENDTIME, RB.LATD,rb.LGTD,"
				+ " SB.CITY, SB.STNM,(SELECT  top 1 rz FROM ST_RSVR_R WHERE stcd=RB.stcd order by tm desc)z,"
				+ " (SELECT  top 1 tm FROM ST_RSVR_R WHERE stcd=RB.stcd order by tm desc)tm "
				+ " FROM HT_RSVRBM_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID "
				+ " WHERE ENABLED=1 and city is not null order by RB.ORDNO";
		List<Map<String, Object>> listRsvrs = daohtnbsqdb.executeQuery(sql);
		List<Map<String, Object>> rtRsvrs = new ArrayList<Map<String, Object>>();
		Map<String, Object> rsvrMap = null;
		for (Map<String, Object> map : listRsvrs) {
			String stcd = map.get("STCD").toString();
			double latestSW = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double warnSW = 0;
			if(null!=map.get("MXENDTIME") && null!=map.get("TXENDTIME")){
				if (HtOauthHelper.isTX(map.get("MXENDTIME").toString(), map.get("TXENDTIME")
						.toString())) {
					// 台汛期
					warnSW = Double.parseDouble(map.get("TXSW").toString());
					map.put("TXMX", "TX");
				} else {
					// 梅汛期
					warnSW = Double.parseDouble(map.get("MXSW").toString());
					map.put("TXMX", "MX");
				}
			}
			else{
				map.put("TXMX", "");//无值，即默认 为空
			}
			if (latestSW >= warnSW) {
				rsvrMap = new HashMap<String, Object>();
				rsvrMap.putAll(map);
				double latd = (null==map.get("LATD") || "".equals(map.get("LATD")))
						?0.0:Double.parseDouble(map.get("LATD").toString());
				double lgtd = (null==map.get("LGTD") || "".equals(map.get("LGTD")))
						?0.0:Double.parseDouble(map.get("LGTD").toString());
				rsvrMap.put("LATITUDE",latd);
				rsvrMap.put("LONGITUDE",lgtd);
				rsvrMap.put("RIVER", "");
				rtRsvrs.add(rsvrMap);
			}
		}
		return rtRsvrs;
	}
	
	public List<Map<String, Object>> getWarnSmallRsvrs(String type) {
		if ("s1".equals(type)) {
			type = "HT_RSVRS1_B";
		} else {
			// s2
			type = "HT_RSVRS2_B";
		}
		String sql = "SELECT RTrim(RB.STCD) as STCD, RB.MXSW, RB.MXKR, RB.TXSW, RB.TXKR, SB.CITY, SB.STNM, RB.LATD,rb.LGTD,"
				+ " (SELECT top 1 rz FROM ST_RSVR_R WHERE stcd=RB.STCD order by tm desc)z,"
				+ " (SELECT top 1 tm FROM ST_RSVR_R WHERE stcd=RB.STCD order by tm desc)tm FROM "
				+ type
				+ " AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE ENABLED=1 and city is not null order by RB.ORDNO";
		List<Map<String, Object>> listRsvrs = daohtnbsqdb.executeQuery(sql);
		List<Map<String, Object>> rtRsvrs = new ArrayList<Map<String, Object>>();
		Map<String, Object> rsvrMap = null;
		for (Map<String, Object> map : listRsvrs) {
			String stcd = map.get("STCD").toString();
			//Map<String, Object> latestMap = getRsvrSWByStcd(stcd);
			double latestSW = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double warnSW = 0;
			if (HtOauthHelper.isTX("07-15", "10-16")) {
				// 台汛期
				try{
					warnSW = Double.parseDouble(map.get("TXSW").toString());
				}catch(Exception e){
					warnSW=9999;
				}
				map.put("TXMX", "TX");
			} else {
				// 梅汛期
				try{
					warnSW = Double.parseDouble(map.get("MXSW").toString());
				}catch(Exception e){
					warnSW = 9999;
				}
				map.put("TXMX", "MX");
			}
			if (latestSW >= warnSW) {
				rsvrMap = new HashMap<String, Object>();
				rsvrMap.putAll(map);
				double latd = (null==map.get("LATD") || "".equals(map.get("LATD")))
						?0.0:Double.parseDouble(map.get("LATD").toString());
				double lgtd = (null==map.get("LGTD") || "".equals(map.get("LGTD")))
						?0.0:Double.parseDouble(map.get("LGTD").toString());
				rsvrMap.put("LATITUDE",latd);
				rsvrMap.put("LONGITUDE",lgtd);
				rsvrMap.put("RIVER", "");
				rtRsvrs.add(rsvrMap);
			}
		}
		return rtRsvrs;
	}
	
	public List<RiverInfo> getSTRiverInfoByTm(String tm) {
		String sql = "declare @curtm datetime;set @curtm = ?;"
				+ "select rtrim(rb.stcd) as stcd,rb.latd,rb.lgtd,"
				+ " (select top 1 z from st_river_r where stcd=rb.stcd and z<>-99 and tm<=@curtm order by tm desc)z,"
				+ " (select top 1 tm from st_river_r where stcd=rb.stcd and z<>-99 and tm<=@curtm order by tm desc)tm,"
				+ " (select top 1 z from (SELECT TOP 2 z,tm FROM st_river_r WHERE stcd=RTrim(RB.STCD) AND z<>-99 and tm<=@curtm ORDER BY TM DESC)a order by tm asc)z1,"
				+ " rb.guard,rb.isrcontrol, rb.ensure, sb.city, sb.stnm, rb.ennmcd from ht_river_b as rb "
				+ " left join ht_stbprp_b as sb on rb.st_id=sb.id "
				+ " where enabled=1 and city is not null order by rb.ordno";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(
				sql,new Object[]{tm});
		List<RiverInfo> allList = new ArrayList<RiverInfo>();

		RiverInfo riverInfo = null;
		for (Map<String, Object> map : list) {
			riverInfo = new RiverInfo();
			String district = (null==map.get("city") || "".equals(map.get("city")))
					?"":map.get("city").toString();
			String stcd = (null==map.get("stcd") || "".equals(map.get("stcd")))
					?"":map.get("stcd").toString();
			String stnm = (null==map.get("stnm") || "".equals(map.get("stnm")))
					?"":map.get("stnm").toString();
			String ennmcd = (null==map.get("ennmcd") || "".equals(map.get("ennmcd")))
					?"":map.get("ennmcd").toString();
			String ensure = (null==map.get("ensure") || "".equals(map.get("ensure")))
					?"":map.get("ensure").toString();
			String guard = (null==map.get("guard") || "".equals(map.get("guard")))
					?"":map.get("guard").toString();
			String TM = (null==map.get("tm") || "".equals(map.get("tm")))
					?"":map.get("tm").toString();
			double sw85 = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double z1 = (null==map.get("z1") || "".equals(map.get("z1")))
					?0.0:Double.parseDouble(map.get("z1").toString());
			String isrcontrol = (null==map.get("isrcontrol") || "".equals(map.get("isrcontrol")))
					?"":map.get("isrcontrol").toString();
			double latd = (null==map.get("latd") || "".equals(map.get("latd")))
					?0.0:Double.parseDouble(map.get("latd").toString());
			double lgtd = (null==map.get("lgtd") || "".equals(map.get("lgtd")))
					?0.0:Double.parseDouble(map.get("lgtd").toString());
			
			riverInfo.setDistrict(district);
			riverInfo.setStcd(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			riverInfo.setennmcd(ennmcd);
			riverInfo.setEnsure(ensure);
			riverInfo.setGuard(guard);
			riverInfo.setStnm(stnm);
			riverInfo.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			riverInfo.setTm(TM);
			riverInfo.setSw85("" + sw85);
			riverInfo.setSwws("" + (sw85 + 1.87));
			//三目判断趋势，z1表示top2 的第二条数据
			riverInfo.setTrend((sw85>z1)?"↑":((sw85==z1)?"--":"↓"));
			riverInfo.setIsRControl(("".equals(isrcontrol) || "false".equals(isrcontrol))
					?false:true);
			riverInfo.setLatitude(latd);
			riverInfo.setLongitude(lgtd);
			riverInfo.setRiver("");

			allList.add(riverInfo);
		}
		return allList;
	}
	
	/**
	 * @see net.htwater.htpms.service.RainWaterService#getSTTideInfo()
	 */
	
	public List<TideInfo> getSTTideInfoByTm(String tm) {
		String sql = "declare @curtm datetime;set @curtm = ?;"
				+ " select rtrim(rb.stcd) as stcd,rb.latd,rb.lgtd,"
				+ " (select top 1 tdz from st_tide_r where stcd=rtrim(rb.stcd) and tdz<>-99 and tm<=@curtm order by tm desc)z,"
				+ " (select top 1 tm from st_tide_r where stcd=rtrim(rb.stcd) and tdz<>-99 and tm<=@curtm order by tm desc)tm,"
				+ " (select top 1 tdz from (select top 2 tdz,tm from st_tide_r "
				+ " where stcd=rtrim(rb.stcd) and tdz<>-99 and tm<=@curtm order by tm desc)a order by tm asc)z1,"
				+ " rb.guard,rb.isgcontrol, rb.ensure, sb.city, sb.stnm,rb.polnm,rb.poltp, rb.ennmcd from ht_tide_b as rb "
				+ " left join ht_stbprp_b as sb on rb.st_id=sb.id where enabled=1 and city is not null order by rb.ordno	";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(
				sql, new Object[]{tm});
		List<TideInfo> allList = new ArrayList<TideInfo>();
		TideInfo tideInfo = null;
		for (Map<String, Object> map : list) {
			tideInfo = new TideInfo();
			String district = (null==map.get("city") || "".equals(map.get("city")))
					?"":map.get("city").toString();
			String stcd = (null==map.get("stcd") || "".equals(map.get("stcd")))
					?"":map.get("stcd").toString();
			String stnm = (null==map.get("stnm") || "".equals(map.get("stnm")))
					?"":map.get("stnm").toString();
			String ennmcd = (null==map.get("ennmcd") || "".equals(map.get("ennmcd")))
					?"":map.get("ennmcd").toString();
			String ensure = (null==map.get("ensure") || "".equals(map.get("ensure")))
					?"":map.get("ensure").toString();
			String guard = (null==map.get("guard") || "".equals(map.get("guard")))
					?"":map.get("guard").toString();
			String TM = (null==map.get("tm") || "".equals(map.get("tm")))
					?"":map.get("tm").toString();
			double sw85 = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double z1 = (null==map.get("z1") || "".equals(map.get("z1")))
					?0.0:Double.parseDouble(map.get("z1").toString());
			String polnm = (null==map.get("polnm") || "".equals(map.get("polnm")))
					?"":map.get("polnm").toString(); 
			String poltp = (null==map.get("poltp") || "".equals(map.get("poltp")))
					?"":map.get("poltp").toString(); 
			
			tideInfo.setStcd(stcd + "-" + PinyinUtil.converterToPinYin(stnm));
			tideInfo.setDistrict(district);	
			tideInfo.setennmcd(ennmcd);
			tideInfo.setEnsure(ensure);
			tideInfo.setGuard(guard);
			tideInfo.setStnm(stnm);
			tideInfo.setHtmlName(HTMLFormatter.formatStationHTML(stnm, stcd));
			tideInfo.setTm(TM);
			tideInfo.setSw85("" + sw85);
			tideInfo.setSwws("" + (sw85 + 1.87));
			tideInfo.setPolName(polnm);
			tideInfo.setPolTop(poltp);
			//三目判断趋势，z1表示top2 的第二条数据
			tideInfo.setTrend((sw85>z1)?"↑":((sw85==z1)?"--":"↓"));
			
			double latd = (null==map.get("latd") || "".equals(map.get("latd")))
					?0.0:Double.parseDouble(map.get("latd").toString());
			double lgtd = (null==map.get("lgtd") || "".equals(map.get("lgtd")))
					?0.0:Double.parseDouble(map.get("lgtd").toString());

			tideInfo.setLatitude(latd);
			tideInfo.setLongitude(lgtd);
			tideInfo.setRiver("");
			
			allList.add(tideInfo);
		}
		return allList;
	}
	
	
	public List<Map<String, Object>> getAlertRsvrInfo(String district) {
		String sql = "declare @startTM datetime declare @pTM8 datetime "
				+ "declare @tmMX1 datetime,@tmMX2 datetime declare @TM datetime"
				+ " set @TM ='"
				+ DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "' set @pTM8=case  when datepart(Hour,@TM)<8"
				+ " then dateadd(day,-1,convert(varchar(11),@TM,120)+'8:00')  when datepart(Hour,@TM)>=8"
				+ " then convert(varchar(11),@TM,120)+'8:00'  end "
				+ "set @tmMX1=convert(varchar(5),@TM,120)+'7-14' set @tmMX2=convert(varchar(5),@TM,120)+'10-15' "
				+ " set @startTM=dateadd(minute,-240,@TM) "
				+ "select n.STCD,k.STNM,k.CITY,latd LATITUDE,lgtd LONGITUDE,'' RIVER,CONVERT(decimal(12, 2), m.rz) as Z,CONVERT(decimal(12, 2), n.CTZ) as CTZ from ST_RSVR_R as m INNER join"
				+ " (select latd,lgtd,a.STCD,MAX(c.TM) AS TM,a.ST_ID,CTZ=case when @TM>=@tmMX1 and @TM<=@tmMX2 then CONVERT(decimal(12, 2), a.TXSW) "
				+ " when @TM<@tmMX1 or @TM>@tmMX2 then CONVERT(decimal(12, 2), a.MXSW) 	end FROM "
				+ "(SELECT [ID] ,[ST_ID] ,[STCD] ,[MXSW] ,[MXKR] ,[TXSW],[TXKR],[ORDNO],[ENABLED],latd,lgtd FROM HT_RSVRBM_B where ENABLED='1') AS a  LEFT OUTER JOIN "
				+ "ST_RSVR_R AS c ON a.STCD = c.stcd where c.tm>=@startTM and c.tm<= @TM "
				+ "group by a.STCD,a.MXSW,a.TXSW,ST_ID,latd,lgtd) as n  on m.stcd=n.STCD AND m.tm=n.TM LEFT JOIN "
				+ "HT_STBPRP_B as k ON k.ID=n.ST_ID " + "WHERE m.rz>=n.CTZ ";

		if (null != district) {
			sql += " and k.CITY='" + district + "'";
		}

		List<Map<String, Object>> listMap = daohtnbsqdb.executeQuery(sql);

		return listMap;
	}
	
	
	public List<Map<String, Object>> getAlertRiverInfo(String district) {
		String sql = "declare @startTM datetime declare @TM datetime set @TM ='"
				+ DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "' set @startTM=dateadd(minute,-240,@TM) "
				+ "select rtrim(stcd) as stcd,z,f.STNM,e.GUARD,f.CITY,e.latd LATITUDE,e.lgtd LONGITUDE,'' as RIVER from (select n.latd,n.lgtd,m.stcd,CONVERT(decimal(12, 2), m.z) as z,n.ST_ID,n.GUARD from ST_RIVER_R as m inner join (select a.latd,a.lgtd,max(b.tm) as tm,a.stcd,a.ST_ID,a.GUARD "
				+ "from HT_RIVER_B as a inner join ST_RIVER_R as b on a.STCD=b.stcd  where tm>=@startTM and tm<@TM and ENABLED=1 "
				+ "group by a.stcd,a.ST_ID,a.GUARD,a.LATD,a.lgtd) as n on m.stcd=n.stcd and m.tm=n.tm) as e left join HT_STBPRP_B as f on e.ST_ID=f.ID where z>GUARD ";
		if (null != district) {
			sql += "and f.CITY='" + district + "'";
		}
		List<Map<String, Object>> listMap = daohtnbsqdb.executeQuery(sql);

		return listMap;
	}
	
	public List<Map<String, Object>> getWarnTides() {
		String sql = "SELECT RTrim(RB.STCD) as STCD, RB.GUARD, RB.ENSURE, SB.CITY, SB.STNM,"
				+ " rb.latd LATITUDE,rb.lgtd LONGITUDE,'' RIVER,"
				+ " (SELECT TOP 1 tdz FROM ST_TIDE_R WHERE stcd=RB.STCD AND tdz<>-99 ORDER BY TM DESC)z,"
				+ " (SELECT TOP 1 tm FROM ST_TIDE_R WHERE stcd=RB.STCD AND tdz<>-99 ORDER BY TM DESC)tm"
				+ " FROM HT_TIDE_B AS RB LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID WHERE ENABLED=1 and city is not null order by RB.ORDNO";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(sql);
		List<Map<String, Object>> rtTides = new ArrayList<Map<String, Object>>();
		Map<String, Object> tideMap = null;
		for (Map<String, Object> map : list) {
			String stcd = map.get("STCD").toString();
			//Map<String, Object> latestMap = getTideSWByStcd(stcd);
			double latestSW = (null==map.get("z") || "".equals(map.get("z")))
					?0.0:Double.parseDouble(map.get("z").toString());
			double guard = null == map.get("GUARD") ? 0 : Double
					.parseDouble(map.get("GUARD").toString());
			if (guard > 0 && latestSW >= guard) {
				tideMap = new HashMap<String, Object>();
				tideMap.putAll(map);
				rtTides.add(tideMap);
			}
		}
		return rtTides;
	}
	
	public List<Map<String, Object>> getRainsByHour(Date curTime, int hour) {
		List<Map<String, Object>> rtRains = new ArrayList<Map<String, Object>>();
		Map<String, Object> rainMap = null;
		String sqlAll = "SELECT rtrim(RB.STCD) as STCD,rb.latd LATITUDE,rb.lgtd LONGITUDE,'' RIVER,rtrim(SB.STNM) as STNM,SB.CITY, HRB.VALLEY, HRB.RSNM"
				+ ",(SELECT SUM(drp) FROM HT_PPTN_R WHERE rtrim(stcd)=RB.STCD AND drp>0 AND tm>? AND tm<=?)rain "
				+ " FROM HT_RAIN_B AS RB with (nolock) LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID "
				+ " left join HT_RAIN_B as HRB on RB.ST_ID=HRB.ST_ID WHERE RB.ENABLED=1 ORDER BY RB.ID";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(sqlAll,
				new Object[]{HtOauthHelper.getTimeByHourOffset(curTime, -1 * hour,
						"yyyy-MM-dd HH:mm:ss"),DateUtil.date2Str(curTime,
								"yyyy-MM-dd HH:mm:ss")});
		for (Map<String, Object> map : list) {
			String stcd = map.get("STCD").toString();
			double stationRain = (null==map.get("rain") || "".equals(map.get("rain")))
					?0:Double.parseDouble(map.get("rain").toString());
			
			rainMap = new HashMap<String, Object>();
			map.remove("rain");
			rainMap.putAll(map);
			rainMap.put("drp", stationRain);
			// if (stationRain > 100)
			// System.out.println(rainMap);
			rtRains.add(rainMap);
		}
		return rtRains;
	}
	
	public List<Map<String, Object>> getRainsByDay(Date curTime, int day) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(curTime);
		if (RainWaterHelper.isNotWaterToday()) {
		} else {
			cal.add(Calendar.DATE, 1);
		}

		List<Map<String, Object>> rtRains = new ArrayList<Map<String, Object>>();
		Map<String, Object> rainMap = null;
		String sqlAll = "SELECT rtrim(RB.STCD) as STCD,rb.latd LATITUDE,rb.lgtd LONGITUDE,'' RIVER,rtrim(SB.STNM) as STNM,SB.CITY, HRB.VALLEY, HRB.RSNM "
				+ " ,(SELECT SUM(drp)FROM ht_pptn_r WHERE rtrim(stcd)=RB.STCD AND tm>? AND tm<=?)rain"
				+ " FROM HT_RAIN_B AS RB with (nolock) LEFT JOIN HT_STBPRP_B AS SB ON RB.ST_ID=SB.ID "
				+ " left join HT_RAIN_B as HRB on RB.ST_ID=HRB.ST_ID WHERE RB.ENABLED=1 ORDER BY RB.ID";
		List<Map<String, Object>> list = daohtnbsqdb.executeQuery(sqlAll,
				new Object[]{
						HtOauthHelper.getTimeByDayOffset(cal.getTime(), -1 * day, "yyyy-MM-dd 08:00:00"),
				DateUtil.date2Str(cal.getTime(), "yyyy-MM-dd 08:00:00")});
		for (Map<String, Object> map : list) {
			String stcd = map.get("STCD").toString();
			double stationRain = (null==map.get("rain") || "".equals(map.get("rain")))
					?0:Double.parseDouble(map.get("rain").toString());
			rainMap = new HashMap<String, Object>();
			map.remove("rain");
			rainMap.putAll(map);
			rainMap.put("drp", stationRain);
			rtRains.add(rainMap);
		}
		return rtRains;
	}
	
	public List<Map<String, Object>> getWarnRains(Date curTime, int dayOrHour,
			int mm, String type) {
		if ("hour".equals(type)) {
			return getWarnRainsByHour(curTime, dayOrHour, mm);
		} else {
			return getWarnRainsByDay(curTime, dayOrHour, mm);
		}
	}
	
	public List<Map<String, Object>> getWarnRainsByHour(Date curTime, int hour,
			int mm) {
		List<Map<String, Object>> rtRains = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = getRainsByHour(curTime, hour);
		for (Map<String, Object> map : list) {
			double stationRain = (Double) map.get("drp");
			if (stationRain >= mm) {
				rtRains.add(map);
			}
		}
		return rtRains;
	}
	
	public List<Map<String, Object>> getWarnRainsByDay(Date curTime, int day,
			int mm) {
		List<Map<String, Object>> rtRains = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = getRainsByDay(curTime, day);
		for (Map<String, Object> map : list) {
			double stationRain = (Double) map.get("drp");
			if (stationRain >= mm) {
				rtRains.add(map);
			}
		}
		return rtRains;
	}
}

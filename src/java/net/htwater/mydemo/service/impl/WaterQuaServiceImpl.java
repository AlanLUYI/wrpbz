package net.htwater.mydemo.service.impl;

import java.util.List;
import java.util.Map;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.PinyinUtil;
import net.htwater.mydemo.service.WaterQuaService;

public class WaterQuaServiceImpl implements WaterQuaService{
	private BaseDao WATERQUA = DaoFactory.getDao(DB_WATERQUA);
	
	@Override
	public List<Map<String, Object>> QueryValidTime(){
		List<Map<String, Object>> times = null;
//		String sql = "SELECT  temp.[Year] as [year],"
//				+ " months = STUFF(("
//					+ " SELECT ',' + innertemp.[Month]"
//					+ " FROM (SELECT DISTINCT [Year] ,[Month] FROM [HTGQ].[dbo].[SZ_SYD]) innertemp"
//					+ " WHERE innertemp.[Year] = temp.[Year]"
//					+ " FOR XML PATH(''),TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, '')"
//				+ " FROM (SELECT DISTINCT [Year] FROM [HTGQ].[dbo].[SZ_SYD]) temp";
		String sql = "SELECT DISTINCT [Year] as [year]"
				+ " ,[Month] as months"
				+ " FROM [HTGQ].[dbo].[SZ_SYD]";
		times = WATERQUA.executeQuery(sql);		
		return times;
	}	
	
	@Override
	public List<Map<String, Object>> QuerySydInfo(String year, String month) {
		// TODO Auto-generated method stub
		int m = Integer.parseInt(month,10);
		String months = "";
		if(m % 2 == 0){
			months = String.valueOf(m -1) + "~" + month;
		}else {
			months = month + "~" + String.valueOf(m+1);
		}	
		
		String subStr = (m % 2 == 0 ? ",[QualityB] as qua,[ThreeB] as three" : ",[QualityA] as qua,[ThreeA] as three");
		
		List<Map<String, Object>> syds = null;
		String sql = "SELECT [Syd] as sydnm"
				+ " ,[FunNo] as funcno"
				+ " ,[Position] as pos"
				+  subStr
				+ " ,b.[LGTD] as lng"
				+ " ,b.[LATD] as lat"
				+ " FROM [HTGQ].[dbo].[SZ_SYD] as a"
				+ " LEFT JOIN HTGQ.dbo.SZ_LOCAL as b on a.[Syd] = b.[NAME] and b.[TYPE] = 'SYD'"
				+ " WHERE a.[Year] ="
				+ "\'" + year + "\'"
				+ " and a.[Month] ="
				+ "\'" + months + "\'";
		syds = WATERQUA.executeQuery(sql);
		if (syds != null) {
			for (Map<String, Object> map : syds) {
				String sydnm = map.get("sydnm").toString(); 
				String pos = map.get("pos").toString();
				map.put("_s", sydnm + PinyinUtil.converterToPinYin(sydnm)+pos+PinyinUtil.converterToPinYin(pos));
			}
		}
		return syds;
	}

	@Override
	public List<Map<String, Object>> QueryRivInfo(String year, String month) {
		// TODO Auto-generated method stub
		int m = Integer.parseInt(month,10);
		String months = "";
		if(m % 2 == 0){
			months = String.valueOf(m -1) + "~" + month;
		}else {
			months = month + "~" + String.valueOf(m+1);
		}
		
		List<Map<String, Object>> rivs = null;
		String sql = "SELECT [DMNM] as dmnm"
				+ " ,[FunNo] as funcno"
				+ " ,[Position] as pos"
				+ " ,[RiverSys] as rivsys"
				+ " ,[River] as riv"
				+ " ,[Quality] as qua"
				+ " ,[Three] as three"
				+ " ,b.[LGTD] as lng"
				+ " ,b.[LATD] as lat"
				+ " FROM [HTGQ].[dbo].[SZ_RV] as a"
				+ " LEFT JOIN HTGQ.dbo.SZ_LOCAL as b on a.[DMNM] = b.[NAME] and b.[TYPE] = 'RIVER'"
				+ " WHERE a.[Year] ="
				+ "\'" + year + "\'"
				+ " and a.[Month] ="
				+ "\'" + months + "\'";
		rivs = WATERQUA.executeQuery(sql);
		if (rivs != null) {
			for (Map<String, Object> map : rivs) {
				String dmnm = map.get("dmnm").toString();
				String pos = map.get("pos").toString();
				map.put("_s", dmnm + PinyinUtil.converterToPinYin(dmnm)+pos+PinyinUtil.converterToPinYin(pos));
			}
		}
		return rivs;
	}

	@Override
	public List<Map<String, Object>> QueryPlainRivInfo(String year, String month) {
		// TODO Auto-generated method stub
		int m = Integer.parseInt(month,10);
		String months = "";
		if(m % 2 == 0){
			months = String.valueOf(m -1) + "~" + month;
		}else {
			months = month + "~" + String.valueOf(m+1);
		}
		
		List<Map<String, Object>> plainRivs = null;
		String sql = "SELECT [STNM] as stnm"
				+ " ,[FunNo] as funcno"
				+ " ,[Position] as pos"
				+ " ,[RiverSys] as rivsys"
				+ " ,[River] as riv"
				+ " ,[Quality] as qua"
				+ " ,[Three] as three"
				+ " ,b.[LGTD] as lng"
				+ " ,b.[LATD] as lat"
				+ " FROM [HTGQ].[dbo].[SZ_PLAIN_RV] as a"
				+ " LEFT JOIN HTGQ.dbo.SZ_LOCAL as b on a.[STNM] = b.[NAME] and b.[TYPE] = 'PLAINRIVER'"
				+ " WHERE a.[Year] ="
				+ "\'" + year + "\'"
				+ " and a.[Month] ="
				+ "\'" + months + "\'";
		plainRivs = WATERQUA.executeQuery(sql);
		if (plainRivs != null) {
			for (Map<String, Object> map : plainRivs) {
				String stnm = map.get("stnm").toString(); 
				String pos = map.get("pos").toString();
				map.put("_s", stnm + PinyinUtil.converterToPinYin(stnm)+pos+PinyinUtil.converterToPinYin(pos));
			}
		}
		return plainRivs;
	}

}

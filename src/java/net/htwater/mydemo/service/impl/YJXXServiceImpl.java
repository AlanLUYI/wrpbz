package net.htwater.mydemo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.mydemo.service.YJXXService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import cn.miao.framework.util.PinyinUtil;

public class YJXXServiceImpl implements YJXXService {
	BaseDao daoZHSL = DaoFactory.getDao(DB_ZHSL); 

	 
	public List<Map<String, Object>> Queryyjxxlist(String TM1,String TM2) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {TM1,TM2};
		String sql = "     select a.ID,a.TITLE,a.TM,a.Source,a.CONTENT,b.GRADE,b.INFO,b.BZ  "
					+"  from [dbo].[NEW_FXYJ] a"
					+"  left join dbo.NEW_FXYJ_GRADE b on a.GRADE=b.GRADE "
					+"  where  a.TM>=? and a.TM<=?  order by ID desc"; 
		rtList = daoZHSL.executeQuery(sql,args); 
		if (rtList != null) {
			for (Map<String, Object> map : rtList) {
				String cname = map.get("TITLE").toString(); 
				map.put("_s", cname + PinyinUtil.converterToPinYin(cname));
			}
		}
		return rtList; 
	}
	
	public List<Map<String, Object>> QueryyjxxbyID(String ID) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		Object[] args = {ID};
		rtList = daoZHSL.callProcedure("{call proc_单条预警信息(?)}",args);
		return rtList; 
	}
	
	public List<Map<String, Object>> Queryrealyjxx() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		/*rtList = daoZHSL.callProcedure("{call proc_实时预警状态()}");*/
		String sql = "     if exists  (SELECT *  FROM [dbo].[NEW_FXYJ] )"
				+"	begin"
				+"			SELECT  [GRADE]"
				+"				  ,[INFO] "
				+"			FROM [dbo].[NEW_FXYJ_GRADE] where GRADE=(SELECT top 1 GRADE  FROM [dbo].[NEW_FXYJ]  order by ID desc)"
				+"	end"
				+"	else"
				+"	begin "
				+"		   SELECT  [GRADE]"
				+"				  ,[INFO] "
				+"			FROM [dbo].[NEW_FXYJ_GRADE] where GRADE=0"
				+"	end  "; 
		rtList = daoZHSL.executeQuery(sql); 
		return rtList; 
	}
	
	
	public Map<String, Object> addyjxx(String TM,String GRADE,String TITLE,String Source,String CONTENT) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = " insert into [dbo].[NEW_FXYJ] ([TM],[GRADE],[TITLE],[Source],[CONTENT]) values(?,?,?,?,?)";
		if(daoZHSL.executeSQL(sql, new Object[]{TM,GRADE,TITLE,Source,CONTENT})){
			result.put("msg", "success");
		}else{
			result.put("msg", "保存失败");
		}
		return result;
	}
}

package net.htwater.hos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.hos.service.Allocation;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

public class AllocationImpl implements Allocation {
	BaseDao dao = DaoFactory.getDao(DB_HOS);
	@Override
	public Map<String, Object> getlifechartdata(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		String ids1 = convertCD(ids);
		String ids2 = convertStrCODE(ids);
		String sql = "select cd, name "
				+ " from sys_code "
				+ " where cd in " + ids1 + " order by cd"; 
		List<Map<String, Object>> plst1 = dao.executeQuery(sql);
		sql = "select a.CityPop, a.VilPop, a.CityLifeQuota, a.VilLifeQuota, a.CitySupLos, a.VilSupLos "
				+ " from Water_Parameter a "
				+ " left join sys_code_mapping b on a.StrCODE =  b.strcode"
				+ " where a.StrCODE in " + ids2 + " order by b.cd";
		List<Map<String, Object>> plst2 = dao.executeQuery(sql);
		result.put("categories", plst1);
		result.put("data", plst2);		
		return result;
	}
	

	public Map<String, Object> getindustrychartdata(String ids){
		Map<String, Object> result = new HashMap<String, Object>();
		String ids1 = convertCD(ids);
		String ids2 = convertStrCODE(ids);
		String sql = "select cd, name "
				+ " from sys_code "
				+ " where cd in " + ids1 + " order by cd"; 
		List<Map<String, Object>> plst1 = dao.executeQuery(sql);
		sql = "select a.CityLifeSigIndR, a.CityLifeGenIndR, a.VilLifeGenIndR,a.SigIndWater,GenIndWater "
				+ " from Water_Parameter a "
				+ " left join sys_code_mapping b on a.StrCODE =  b.strcode"
				+ " where a.StrCODE in " + ids2 + " order by b.cd";
		List<Map<String, Object>> plst2 = dao.executeQuery(sql);
		result.put("categories", plst1);
		result.put("data", plst2);		
		return result;
	}
	
	public Map<String, Object> getagriculturechartdata(String ids){
		Map<String, Object> result = new HashMap<String, Object>();
		String ids1 = convertCD(ids);
		String ids2 = convertStrCODE(ids);
		String sql = "select cd, name "
				+ " from sys_code "
				+ " where cd in " + ids1 + " order by cd"; 
		List<Map<String, Object>> plst1 = dao.executeQuery(sql);
		sql = "select a.PaddyArea, a.DryArea, a.ForArea,a.IrrCoe, "
				+ " a.BigAnimal, a.SmalAnimal, a.BigAniQuo, a.SmaAniQuo,"
				+ " a.FreshWater, a.FreWaterQuo"
				+ " from Water_Parameter a "
				+ " left join sys_code_mapping b on a.StrCODE =  b.strcode"
				+ " where a.StrCODE in " + ids2 + " order by b.cd";
		List<Map<String, Object>> plst2 = dao.executeQuery(sql);
		result.put("categories", plst1);
		result.put("data", plst2);		
		return result;
	}
	public Map<String, Object> getagriculturechartdata50(String ids){
		Map<String, Object> result = new HashMap<String, Object>();
		String ids1 = convertCD(ids);
		String ids2 = convertStrCODE(ids);
		String sql = "select cd, name "
				+ " from sys_code "
				+ " where cd in " + ids1 + " order by cd"; 
		List<Map<String, Object>> plst1 = dao.executeQuery(sql);
		sql = "select a.PaddyQuo50, a.DryQuo50, a.ForQuo50 "
				+ " from Water_Parameter a "
				+ " left join sys_code_mapping b on a.StrCODE =  b.strcode"
				+ " where a.StrCODE in " + ids2 + " order by b.cd";
		List<Map<String, Object>> plst2 = dao.executeQuery(sql);
		result.put("categories", plst1);
		result.put("data", plst2);		
		return result;
	}
	public Map<String, Object> getagriculturechartdata75(String ids){
		Map<String, Object> result = new HashMap<String, Object>();
		String ids1 = convertCD(ids);
		String ids2 = convertStrCODE(ids);
		String sql = "select cd, name "
				+ " from sys_code "
				+ " where cd in " + ids1 + " order by cd"; 
		List<Map<String, Object>> plst1 = dao.executeQuery(sql);
		sql = "select a.PaddyQuo75, a.DryQuo75, a.ForQuo75 "
				+ " from Water_Parameter a "
				+ " left join sys_code_mapping b on a.StrCODE =  b.strcode"
				+ " where a.StrCODE in " + ids2 + " order by b.cd";
		List<Map<String, Object>> plst2 = dao.executeQuery(sql);
		result.put("categories", plst1);
		result.put("data", plst2);		
		return result;
	}

	public Map<String, Object> getagriculturechartdata90(String ids){
		Map<String, Object> result = new HashMap<String, Object>();
		String ids1 = convertCD(ids);
		String ids2 = convertStrCODE(ids);
		String sql = "select cd, name "
				+ " from sys_code "
				+ " where cd in " + ids1 + " order by cd"; 
		List<Map<String, Object>> plst1 = dao.executeQuery(sql);
		sql = "select a.PaddyQuo90, a.DryQuo90, a.ForQuo90 "
				+ " from Water_Parameter a "
				+ " left join sys_code_mapping b on a.StrCODE =  b.strcode"
				+ " where a.StrCODE in " + ids2 + " order by b.cd";
		List<Map<String, Object>> plst2 = dao.executeQuery(sql);
		result.put("categories", plst1);
		result.put("data", plst2);		
		return result;
	}

	public Map<String, Object> getagriculturechartdata95(String ids){
		Map<String, Object> result = new HashMap<String, Object>();
		String ids1 = convertCD(ids);
		String ids2 = convertStrCODE(ids);
		String sql = "select cd, name "
				+ " from sys_code "
				+ " where cd in " + ids1 + " order by cd"; 
		List<Map<String, Object>> plst1 = dao.executeQuery(sql);
		sql = "select a.PaddyQuo95, a.DryQuo95, a.ForQuo95 "
				+ " from Water_Parameter a "
				+ " left join sys_code_mapping b on a.StrCODE =  b.strcode"
				+ " where a.StrCODE in " + ids2 + " order by b.cd";
		List<Map<String, Object>> plst2 = dao.executeQuery(sql);
		result.put("categories", plst1);
		result.put("data", plst2);		
		return result;
	}
	public Map<String, Object> getecologicalchartdata(String ids){
		Map<String, Object> result = new HashMap<String, Object>();
		String ids1 = convertCD(ids);
		String ids2 = convertStrCODE(ids);
		String sql = "select cd, name "
				+ " from sys_code "
				+ " where cd in " + ids1 + " order by cd"; 
		List<Map<String, Object>> plst1 = dao.executeQuery(sql);
		sql = "select a.EcoTotalR50, a.EcoTotalR75, a.EcoTotalR90,a.EcoTotalR95 "
				+ " from Water_Parameter a "
				+ " left join sys_code_mapping b on a.StrCODE =  b.strcode"
				+ " where a.StrCODE in " + ids2 + " order by b.cd";
		List<Map<String, Object>> plst2 = dao.executeQuery(sql);
		result.put("categories", plst1);
		result.put("data", plst2);		
		return result;
	}
	
	
	
	/**
	 * 将客户端传来的编码转换为sql查询内容
	 * @param ids
	 * @return
	 */
	public String convertCD(String ids){
		String _r = "(";
		String[] arr = ids.split("\\,");
		for(int i=0;i<arr.length;i++){
			if(_r.equals("(")){
				_r+=("'"+arr[i]+"'");
			}else{
				_r+=(",'"+arr[i]+"'");
			}
		}
		if(_r.equals("(")){
			_r+=("'')");
		}else{
			_r+=(")");
		}
		return _r;
	}
	/**
	 * 将客户端传来的编码转换为sql查询内容
	 * @param ids
	 * @return
	 */
	public String convertStrCODE(String ids){
		String _r = "(";
		String[] arr = ids.split("\\,");
		for(int i=0;i<arr.length;i++){
			String sql = "select strcode from sys_code_mapping where cd = ?";
			Map<String,Object>map = dao.executeQueryObject(sql, new Object[]{arr[i]});
			if(_r.equals("(")){
				_r+=("'"+map.get("strcode")+"'");
			}else{
				_r+=(",'"+map.get("strcode")+"'");
			}
		}
		if(_r.equals("(")){
			_r+=("'')");
		}else{
			_r+=(")");
		}
		return _r;
	}

	public Map<String, Object> getbaseinfo(String cd){
		String sql = "select a.* "
				+ " from Water_Parameter a "
				+ " left join sys_code_mapping b on a.StrCODE =  b.strcode"
				+ " where b.cd = '" + cd + "'";
		return dao.executeQueryObject(sql);
	}
}

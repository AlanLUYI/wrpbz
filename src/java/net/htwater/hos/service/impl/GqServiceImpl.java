package net.htwater.hos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htwater.hos.service.GqService;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
//import net.htwater.cms.util.HttpUtil;
import cn.miao.framework.util.PinyinUtil;

public class GqServiceImpl implements GqService {
	private BaseDao ShiPin = DaoFactory.getDao(DB_SHANHONG);

	public List<Map<String, Object>> getsplist() {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		String sql = "     SELECT  *  FROM  [dbo].[视频点] "; 
		rtList = ShiPin.executeQuery(sql); 
		return rtList; 
	}
}
